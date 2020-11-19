package kr.co.penta.dataeye.spring.web.aprv.service.impl;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.MetaObjectAprvInfo;
import kr.co.penta.dataeye.common.entities.meta.PenAprvD;
import kr.co.penta.dataeye.common.entities.meta.PenAprvF;
import kr.co.penta.dataeye.common.entities.meta.PenAprvM;
import kr.co.penta.dataeye.common.entities.meta.PenObjMT;
import kr.co.penta.dataeye.common.meta.util.DeFileUtils;
import kr.co.penta.dataeye.common.util.TimeUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.AprvDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.aprv.enums.AprvCodeType;
import kr.co.penta.dataeye.spring.web.aprv.enums.AprvQueryIdType;
import kr.co.penta.dataeye.spring.web.aprv.enums.AprvResultType;
import kr.co.penta.dataeye.spring.web.aprv.service.AprvService;
import kr.co.penta.dataeye.spring.web.common.service.MetaReqService;
import kr.co.penta.dataeye.spring.web.credit.service.AnaTaskService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AprvServiceImpl implements AprvService {
    private static final Logger LOG = LoggerFactory.getLogger(AprvServiceImpl.class);

    private static final String ACT00_0 = AprvCodeType.ACT00_0.getId();
    private static final String ACT01_0 = AprvCodeType.ACT01_0.getId();
    private static final String ACT01_1 = AprvCodeType.ACT01_1.getId();
    private static final String ACT04_0 = AprvCodeType.ACT04_0.getId();
    private static final String ACT04_1 = AprvCodeType.ACT04_1.getId();
    private static final String ACT04_2 = AprvCodeType.ACT04_2.getId();
    private static final String ACT05_0 = AprvCodeType.ACT05_0.getId();

    @Autowired
    private AprvDao aprvDao;
    @Autowired
    private MetaReqService metaReqService;
    
    @Autowired 
    private AnaTaskService anaTaskService;
    
    /*
    @Autowired
    public AprvServiceImpl(AprvDao aprvDao, MetaReqService metaReqService) {
        this.aprvDao = aprvDao;
        this.metaReqService = metaReqService;
    }*/

    @Value("${file.upload.path.root}")
    private String uploadRoot;

    @Value("${file.upload.path.root}")
    private String uploadTmpry;

    @Value("${file.upload.path.real}")
    private String uploadReal;

    private String getTid() {
        Timestamp from = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        Random r = new Random();
        int numbers = 100000 * (int)(r.nextFloat() * 899900);

        return transFormat.format(from) + "T" + String.valueOf(numbers);
    }

    private String getDataTime() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(now);
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveObjInfo(MetaObjectAprvInfo metaObjectAprvInfo, String userId) throws Exception {
        DaoParam daoParam;
        PenAprvM penAprvM = metaObjectAprvInfo.getPenAprvM();

        if ("".equals(penAprvM.getAprvId())) {
            penAprvM.genAprvId();
        }
        else {
            daoParam = new DaoParam();
            daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_M_INFO.getId());
            daoParam.put("aprvId", penAprvM.getAprvId());
            daoParam.put("userId", userId);
            CamelMap aprvInfo = aprvDao.selectOne(daoParam);
            if (aprvInfo != null) {
                penAprvM.setCretDt(aprvInfo.getString("cretDt"));
                penAprvM.setCretrId(aprvInfo.getString("cretrId"));
                penAprvM.setAprvAtr01Val(aprvInfo.getString("aprvAtr01Val"));

                daoParam = new DaoParam();
                daoParam.put("queryId", AprvQueryIdType.PEN_APRV_M__DELETE_BY_APRV_ID.getId());
                daoParam.put("aprvId", penAprvM.getAprvId());
                aprvDao.delete(daoParam);
                daoParam.put("queryId", AprvQueryIdType.PEN_APRV_F__DELETE_BY_APRV_ID.getId());
                aprvDao.delete(daoParam);
            }
        }

        daoParam = new DaoParam(penAprvM);
        daoParam.put("queryId", AprvQueryIdType.PEN_APRV_M__INSERT.getId());
        daoParam.put("userId", userId);
        aprvDao.insert(daoParam);

        List<PenAprvD> penAprvDs = metaObjectAprvInfo.getPenAprvDs();
        for (PenAprvD penAprvD : penAprvDs) {
            penAprvD.setCretDt(penAprvM.getCretDt());
            penAprvD.setCretrId(penAprvM.getCretrId());

            daoParam = new DaoParam(penAprvD);
            daoParam.put("queryId", AprvQueryIdType.PEN_APRV_D__INSERT.getId());
            daoParam.put("userId", userId);
            aprvDao.insert(daoParam);
        }
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveObjFileInfo(MetaObjectAprvInfo metaObjectAprvInfo, String userId) throws Exception {
        List<PenAprvF> penAprvFs = metaObjectAprvInfo.getPenAprvFs();
        final String nowMonth = TimeUtils.getInstance().formatDate(TimeUtils.getInstance().getCurrentTimestamp(), "/yyyyMM");
        final String tempFilePath = uploadRoot + uploadTmpry;
        final String realFilePath = uploadReal + "/aprv" + nowMonth;
        List<CamelMap> fileList = new ArrayList<>();

        for (PenAprvF penAprvF : penAprvFs) {
            if (penAprvF.getFilePath().startsWith(tempFilePath)) {
                CamelMap fileMap = new CamelMap();
                fileMap.put("FILE_PATH", penAprvF.getFilePath());
                fileMap.put("FILE_NAME", penAprvF.getFileId());
                fileList.add(fileMap);

                penAprvF.setFilePath(realFilePath);
                DaoParam daoParam = new DaoParam(penAprvF);
                daoParam.put("queryId", AprvQueryIdType.PEN_APRV_F__INSERT.getId());
                daoParam.put("userId", userId);
                aprvDao.insert(daoParam);
            }
        }

        for (CamelMap fMap : fileList) {
            String fileNm = fMap.getString("FILE_NAME");
            String src = fMap.getString("FILE_PATH") + File.separator + fileNm;
            String dst = realFilePath + File.separator + fileNm;
            DeFileUtils.getInstance().move(src, dst);
        }
    }

    private void putReqAprvUserMap(CamelMap srcMap, CamelMap dstMap, String userId, String aprvOrder, String aprvGubun) {
        dstMap.put("USER_ID", userId);
        dstMap.put("USER_NO", srcMap.getString("CODE"));
        dstMap.put("USER_NM", srcMap.getString("USER_NM"));
        dstMap.put("EMAIL", srcMap.getString("EMAIL"));
        dstMap.put("MOBILE", srcMap.getString("MOBILE"));
        dstMap.put("APRV_ORDER", aprvOrder);
        dstMap.put("APRV_GUBUN", aprvGubun);
    }

    private void putDoAprvUserMap(CamelMap srcMap, CamelMap dstMap, String userId, String aprvLineDesc, String aprvDesc, String aprvOrder, String aprvTypeCd, String aprvRsltCd, String aprvGubun) {
        dstMap.put("USER_ID", userId);
        dstMap.put("USER_NO", srcMap.getString("CODE"));
        dstMap.put("USER_NM", srcMap.getString("USER_NM"));
        dstMap.put("EMAIL", srcMap.getString("EMAIL"));
        dstMap.put("MOBILE", srcMap.getString("MOBILE"));
        dstMap.put("APRV_LINE_DESC", aprvLineDesc);
        dstMap.put("APRV_DESC", aprvDesc);
        dstMap.put("APRV_ORDER", aprvOrder);
        dstMap.put("APRV_TYPE_CD", aprvTypeCd);
        dstMap.put("APRV_RSLT_CD", aprvRsltCd);
        dstMap.put("APRV_GUBUN", aprvGubun);
    }

    private CamelMap getAprvUserDtlInfo(String userId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_USER_DTL_INFO.getId());
        daoParam.put("userId", userId);

        return aprvDao.selectOne(daoParam);
    }

    private void putDataMap(CamelMap dataMap, String aprvGubun, String aprvUserGubun, String aprvLineMngrId, String aprvProcNm, String aprvLineDesc, String aprvOrdNo, String aprvPrcsSortNo, String doAprvChk, String aprvDesc, String userId, String userNm, String aprvDt) {
        dataMap.put("APRV_GUBUN", aprvGubun);
        dataMap.put("APRV_USER_GUBUN", aprvUserGubun);
        dataMap.put("APRV_LINE_MNGR_ID", aprvLineMngrId);
        dataMap.put("APRV_PROC_NM", aprvProcNm);
        dataMap.put("APRV_LINE_DESC", aprvLineDesc);
        dataMap.put("APRV_ORD_NO", aprvOrdNo);
        dataMap.put("APRV_PRCS_SORT_NO", aprvPrcsSortNo);
        dataMap.put("DO_APRV_CHK", doAprvChk);
        dataMap.put("APRV_DESC", aprvDesc);
        dataMap.put("USER_ID", userId);
        dataMap.put("USER_NM", userNm);
        dataMap.put("APRV_DT", aprvDt);
    }

    private void putAddFile(MetaObjectAprvInfo metaObjectAprvInfo, List<?> aprvFileDataList, String aprvId, String aprvDetlId, String userId) {
        if (aprvFileDataList != null && aprvFileDataList.size() > 0) {
            DaoParam daoParam = new DaoParam();
            daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_USER_MAX_FILE_NO.getId());
            daoParam.put("aprvId", aprvId);
            daoParam.put("aprvDetlId", aprvDetlId);
            daoParam.put("userId", userId);
            CamelMap fileMaxNoMap = aprvDao.selectOne(daoParam);

            int fileMaxNo = fileMaxNoMap.getInteger("FILE_NO") + 1;

            for (Object aprvFileDataObj : aprvFileDataList) {
                CamelMap aprvFileDataMap = (CamelMap) aprvFileDataObj;
                PenAprvF penAprvF = new PenAprvF();
                penAprvF.setAprvId(aprvId);
                penAprvF.setAprvDetlId(aprvDetlId);
                penAprvF.setFileNo(fileMaxNo);
                penAprvF.setFileId(aprvFileDataMap.getString("apndFileNm"));
                penAprvF.setFileNm(aprvFileDataMap.getString("apndFileOcpyNm"));
                penAprvF.setFileSize(aprvFileDataMap.getString("apndFileSiz"));
                penAprvF.setFileType(aprvFileDataMap.getString("apndFileExtsNm"));
                penAprvF.setFilePath(aprvFileDataMap.getString("apndFilePathNm"));
                metaObjectAprvInfo.addFile(penAprvF);
                fileMaxNo++;
            }
        }
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CamelMap getAprvMInfo(CamelMap aprvData) throws Exception {
        DaoParam daoParam;
        final String aprvId = aprvData.getString("aprvId");
        final String userId = aprvData.getString("userId");

        daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_M_INFO.getId());
        daoParam.put("aprvId", aprvId);
        daoParam.put("userId", userId);

        return aprvDao.selectOne(daoParam);
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<CamelMap> getAprvLineInfo(CamelMap aprvData) throws Exception {
        DaoParam daoParam;
        final String aprvTypeCd = aprvData.getString("aprvTypeCd");

        daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_LINE_INFO.getId());
        daoParam.put("aprvTypeCd", aprvTypeCd);

        return aprvDao.selectList(daoParam);
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CamelMap getAprvDoInfo(CamelMap aprvData) throws Exception {
        DaoParam daoParam;
        final String userId = aprvData.getString("userId");
        final String dpUserNm = aprvData.getString("dpUserNm");
        final String aprvId = aprvData.getString("aprvId");
        final String aprvDetlId = aprvData.getString("aprvDetlId");

        CamelMap resultMap = new CamelMap();
        resultMap.put("rstCd", AprvResultType.FAIL.getId());

        daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_PRE_MD_INFO.getId());
        daoParam.put("aprvId", aprvId);
        daoParam.put("aprvDetlId", aprvDetlId);
        daoParam.put("userId", userId);

        CamelMap aprvPreMdInfo = aprvDao.selectOne(daoParam);

        if (aprvPreMdInfo == null || aprvPreMdInfo.isEmpty()) {
            resultMap.put("rstMsg", AprvResultType.DO_ERR_MSG_005.getName());
            return resultMap;
        }

        final String aprvTypeCd = aprvPreMdInfo.getString("APRV_TYPE_CD");
        final String aprvLineId = aprvPreMdInfo.getString("APRV_LINE_ID");
        final String aprvPrcsSortNo = aprvPreMdInfo.getString("APRV_PRCS_SORT_NO");
        final String aprvReqrId = aprvPreMdInfo.getString("APRV_REQR_ID");
        final String aprvReqDt = aprvPreMdInfo.getString("APRV_REQ_DT");
        final String aprvDesc = aprvPreMdInfo.getString("APRV_DESC");
        final String aprvRsltCd = aprvPreMdInfo.getString("APRV_RSLT_CD");
        final String lastStusCd = aprvPreMdInfo.getString("APRV_STUS_CD");

        daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_LINE_INFO.getId());
        daoParam.put("aprvTypeCd", aprvTypeCd);

        List<CamelMap> aprvLineInfo = aprvDao.selectList(daoParam);

        if (aprvLineInfo == null || aprvLineInfo.isEmpty()) {
            resultMap.put("rstMsg", AprvResultType.ERR_MSG_001.getName());
            return resultMap;
        }

        String prevAprvOrdNo = "";
        String chkYn = "N";
        boolean doAprvChk = true;
        List<CamelMap> resultList = new ArrayList<>();
        CamelMap dataMap = new CamelMap();

        for (CamelMap aprvLineMap : aprvLineInfo) {
            String aprvGubun = aprvLineMap.getString("APRV_GUBUN");
            String aprvUserGubun = aprvLineMap.getString("APRV_USER_GUBUN");
            String aprvLineMngrId = aprvLineMap.getString("APRV_LINE_MNGR_ID");
            String aprvProcNm = aprvLineMap.getString("APRV_PROC_NM");
            String aprvLineDesc = aprvLineMap.getString("APRV_LINE_DESC");
            String aprvOrdNo = aprvLineMap.getString("APRV_ORD_NO");

            if (ACT05_0.equals(aprvGubun)) {
                chkYn = "Y";
            }

            if ("1".equals(aprvOrdNo)) {
                CamelMap aprvUserDtlInfo = getAprvUserDtlInfo(aprvReqrId);

                putDataMap(dataMap, aprvGubun, aprvUserGubun, aprvLineMngrId, aprvProcNm, aprvLineDesc, aprvOrdNo, "1", "N", aprvDesc, aprvReqrId, aprvUserDtlInfo.getString("USER_NM"), aprvReqDt);
                resultList.add(dataMap);
            }
            else {
                if ("USER".equals(aprvUserGubun)) {
                    daoParam = new DaoParam();
                    daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_USER_INFO.getId());
                    daoParam.put("aprvId", aprvId);
                    daoParam.put("aprvLineMngrId", aprvLineMngrId);

                    List<CamelMap> aprvUserInfo = aprvDao.selectList(daoParam);

                    for (CamelMap aprvUserMap : aprvUserInfo) {
                        String aprvUserId = aprvUserMap.getString("APRV_USER_ID");

                        if (aprvLineId.equals(aprvLineMngrId) && aprvPrcsSortNo.equals(aprvUserMap.getString("APRV_PRCS_SORT_NO")) && ACT01_0.equals(aprvRsltCd)) {
                            if (doAprvChk || aprvOrdNo.equals(prevAprvOrdNo)) {
                                dataMap = new CamelMap();
                                putDataMap(dataMap, aprvGubun, aprvUserGubun, aprvLineMngrId, aprvProcNm, aprvLineDesc, aprvOrdNo, aprvPrcsSortNo, "Y", "", userId, dpUserNm, "");

                                resultList.add(dataMap);
                                resultMap.put("aprvGubun", aprvGubun);
                            }
                        }
                        else {
                            CamelMap aprvUserDtlInfo = getAprvUserDtlInfo(aprvUserId);

                            dataMap = new CamelMap();
                            putDataMap(dataMap, aprvGubun, aprvUserGubun, aprvLineMngrId, aprvProcNm, aprvLineDesc, aprvOrdNo, aprvPrcsSortNo, "N", "", aprvUserId, aprvUserDtlInfo.getString("USER_NM"), "");

                            if (!ACT04_0.equals(aprvUserMap.getString("APRV_RSLT_CD")) && !ACT05_0.equals(aprvUserMap.getString("APRV_RSLT_CD")) && !ACT04_1.equals(aprvUserMap.getString("APRV_RSLT_CD"))) {
                                doAprvChk = false;
                            }
                            else {
                                dataMap.put("APRV_DT", aprvUserMap.getString("APRV_DT"));
                                dataMap.put("APRV_DESC", aprvUserMap.getString("APRV_DESC"));
                            }

                            resultList.add(dataMap);
                        }

                        prevAprvOrdNo = aprvOrdNo;
                    }
                }
                else if ("ROLE".equals(aprvUserGubun)) {
                    if (aprvLineId.equals(aprvLineMngrId) && ACT01_0.equals(aprvRsltCd)) {
                        if (doAprvChk || aprvOrdNo.equals(prevAprvOrdNo)) {
                            dataMap = new CamelMap();
                            putDataMap(dataMap, aprvGubun, aprvUserGubun, aprvLineMngrId, aprvProcNm, aprvLineDesc, aprvOrdNo, "1", "Y", "", "", aprvLineDesc, "");

                            if (ACT01_1.equals(lastStusCd)) {
                                daoParam = new DaoParam();
                                daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_STS_PROC_INFO.getId());
                                daoParam.put("aprvId", aprvId);
                                daoParam.put("aprvLineMngrId", aprvLineMngrId);
                                daoParam.put("rtyMode", "Y");

                                CamelMap cntMap = aprvDao.selectOne(daoParam);

                                dataMap.put("USER_ID", userId);
                                dataMap.put("USER_NM", dpUserNm);
                                dataMap.put("APRV_DT", cntMap.getString("APRV_DT"));
                                dataMap.put("APRV_DESC", cntMap.getString("APRV_DESC"));
                            }

                            resultList.add(dataMap);
                            resultMap.put("aprvGubun" ,aprvGubun);
                        }
                    }
                    else {
                        dataMap = new CamelMap();
                        putDataMap(dataMap, aprvGubun, aprvUserGubun, aprvLineMngrId, aprvProcNm, aprvLineDesc, aprvOrdNo, "1", "N", "", "", aprvLineDesc, "");

                        daoParam = new DaoParam();
                        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_PROC_INFO.getId());
                        daoParam.put("aprvId", aprvId);
                        daoParam.put("aprvLineMngrId", aprvLineMngrId);

                        CamelMap cntMap = aprvDao.selectOne(daoParam);

                        int cnt = 0;
                        if (cntMap != null && !cntMap.isEmpty()) {
                            cnt = cntMap.getInteger("CNT");
                        }

                        if (cnt == 0) {
                            doAprvChk = false;
                        }
                        else {
                            daoParam = new DaoParam();
                            daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_USER_DTL_INFO.getId());
                            daoParam.put("userId", cntMap.getString("APRV_USER_ID"));

                            CamelMap aprvUserDtlInfo = aprvDao.selectOne(daoParam);

                            dataMap.put("USER_ID", cntMap.getString("APRV_USER_ID"));
                            dataMap.put("USER_NM", aprvUserDtlInfo.getString("USER_NM"));
                            dataMap.put("APRV_DT", cntMap.getString("APRV_DT"));
                            dataMap.put("APRV_DESC", cntMap.getString("APRV_DESC"));

                            if (ACT04_1.equals(cntMap.getString("APRV_RSLT_CD"))) {
                                doAprvChk = false;
                            }
                        }

                        resultList.add(dataMap);
                    }

                    prevAprvOrdNo = aprvOrdNo;
                }
            }
        }

        resultMap.put("rstCd", AprvResultType.SUCC.getId());
        resultMap.put("aprvData", resultList);
        resultMap.put("aprvTypeCd", aprvTypeCd);
        resultMap.put("chkYn", chkYn);

        return resultMap;
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CamelMap getAprvStsInfo(CamelMap aprvData) throws Exception {
        DaoParam daoParam;
        final String aprvId = aprvData.getString("aprvId");

        CamelMap resultMap = new CamelMap();
        resultMap.put("rstCd", AprvResultType.FAIL.getId());

        daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_STS_M_INFO.getId());
        daoParam.put("aprvId", aprvId);

        CamelMap aprvStsMInfo = aprvDao.selectOne(daoParam);

        final String aprvTypeCd = aprvStsMInfo.getString("APRV_TYPE_CD");
        final String aprvReqrId = aprvStsMInfo.getString("APRV_ REQR_ID");
        final String aprvReqDt = aprvStsMInfo.getString("APRV_REQ_DT");
        final String aprvDesc = aprvStsMInfo.getString("APRV_DESC");
        final String lastStusCd = aprvStsMInfo.getString("LAST_STUS_CD");

        daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_LINE_INFO.getId());
        daoParam.put("aprvTypeCd", aprvTypeCd);

        List<CamelMap> aprvLineInfo = aprvDao.selectList(daoParam);

        if (aprvLineInfo == null || aprvLineInfo.isEmpty()) {
            resultMap.put("rstMsg", AprvResultType.ERR_MSG_001.getName());
            return resultMap;
        }

        List<CamelMap> resultList = new ArrayList<>();
        CamelMap dataMap = new CamelMap();

        for (CamelMap aprvLineMap : aprvLineInfo) {
            String aprvGubun = aprvLineMap.getString("APRV_GUBUN");
            String aprvUserGubun = aprvLineMap.getString("APRV_USER_GUBUN");
            String aprvLineMngrId = aprvLineMap.getString("APRV_LINE_MNGR_ID");
            String aprvProcNm = aprvLineMap.getString("APRV_PROC_NM");
            String aprvLineDesc = aprvLineMap.getString("APRV_LINE_DESC");
            String aprvOrdNo = aprvLineMap.getString("APRV_ORD_NO");

            if ("1".equals(aprvOrdNo)) {
                CamelMap aprvUserDtlInfo = getAprvUserDtlInfo(aprvReqrId);

                putDataMap(dataMap, aprvGubun, aprvUserGubun, aprvLineMngrId, aprvProcNm, aprvLineDesc, aprvOrdNo, "1", "N", aprvDesc, aprvReqrId, aprvUserDtlInfo.getString("USER_NM"), aprvReqDt);
                resultList.add(dataMap);
            }
            else {
                if ("USER".equals(aprvUserGubun)) {
                    daoParam = new DaoParam();
                    daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_USER_INFO.getId());
                    daoParam.put("aprvId", aprvId);
                    daoParam.put("aprvLineMngrId", aprvLineMngrId);

                    List<CamelMap> aprvUserInfo = aprvDao.selectList(daoParam);

                    for (CamelMap aprvUserMap : aprvUserInfo) {
                        String aprvUserId = aprvUserMap.getString("APRV_USER_ID");

                        CamelMap aprvUserDtlInfo = getAprvUserDtlInfo(aprvUserId);
                        dataMap = new CamelMap();
                        putDataMap(dataMap, aprvGubun, aprvUserGubun, aprvLineMngrId, aprvProcNm, aprvLineDesc, aprvOrdNo, aprvUserMap.getString("APRV_PRCS_SORT_NO"), "N", "", aprvUserId, aprvUserDtlInfo.getString("USER_NM"), "");

                        if (ACT04_0.equals(aprvUserMap.getString("APRV_RSLT_CD")) || ACT04_2.equals(aprvUserMap.getString("APRV_RSLT_CD")) || ACT05_0.equals(aprvUserMap.getString("APRV_RSLT_CD")) || ACT04_1.equals(aprvUserMap.getString("APRV_RSLT_CD"))) {
                            dataMap.put("APRV_DT", aprvUserMap.getString("APRV_DT"));
                            dataMap.put("APRV_DESC", aprvUserMap.getString("APRV_DESC"));
                            dataMap.put("APRV_LINE_DESC", AprvCodeType.getEnum(aprvUserMap.getString("APRV_RSLT_CD")).getName());
                        }
                    }
                }
                else if ("ROLE".equals(aprvUserGubun)) {
                    dataMap = new CamelMap();
                    putDataMap(dataMap, aprvGubun, aprvUserGubun, aprvLineMngrId, aprvProcNm, aprvLineDesc, aprvOrdNo, "1", "N", "", "", aprvLineDesc, "");

                    String rtyMode = "";
                    if (rtyMode.equals(lastStusCd)) {
                        rtyMode = "Y";
                    }

                    daoParam = new DaoParam();
                    daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_STS_PROC_INFO.getId());
                    daoParam.put("aprvId", aprvId);
                    daoParam.put("aprvLineMngrId", aprvLineMngrId);
                    daoParam.put("rtyMode", rtyMode);

                    CamelMap cntMap = aprvDao.selectOne(daoParam);

                    int cnt = 0;
                    if (cntMap != null && !cntMap.isEmpty()) {
                        cnt = cntMap.getInteger("CNT");
                    }

                    if (cnt > 0) {
                        CamelMap aprvUserDtlInfo = getAprvUserDtlInfo(cntMap.getString("APRV_USER_ID"));
                        dataMap.put("USER_ID", cntMap.getString("APRV_USER_ID"));
                        dataMap.put("USER_NM", aprvUserDtlInfo.getString("USER_NM"));
                        dataMap.put("APRV_DT", cntMap.getString("APRV_DT"));
                        dataMap.put("APRV_DESC", cntMap.getString("APRV_DESC"));

                        if (ACT04_0.equals(cntMap.getString("APRV_RSLT_CD")) || ACT04_2.equals(cntMap.getString("APRV_RSLT_CD")) || ACT05_0.equals(cntMap.getString("APRV_RSLT_CD")) || ACT04_1.equals(cntMap.getString("APRV_RSLT_CD"))) {
                            dataMap.put("APRV_LINE_DESC", aprvLineDesc + " (" + AprvCodeType.getEnum(cntMap.getString("APRV_RSLT_CD")).getName() + ")");
                        }
                    }

                    resultList.add(dataMap);
                }
            }
        }

        resultMap.put("rstCd", AprvResultType.SUCC.getId());
        resultMap.put("aprvData", resultList);

        return resultMap;
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<CamelMap> getAprvFileInfo(CamelMap aprvData) throws Exception {
        DaoParam daoParam;
        final String aprvId = aprvData.getString("aprvId");

        daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_FILE_INFO.getId());
        daoParam.put("aprvId", aprvId);

        return aprvDao.selectList(daoParam);
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CamelMap reqAprv(CamelMap aprvData) throws Exception {
        DaoParam daoParam;
        final String userId = aprvData.getString("userId");
        final String aprvId = aprvData.getString("aprvId");
        final String aprvDesc = aprvData.getString("aprvDesc");
        List<?> aprvDataList = (List<?>) aprvData.get("aprvDataList");
        //List<CamelMap> aprvDataList = (List<Map>) aprvData.get("aprvDataList");
        List<?> aprvFileDataList = (List<?>) aprvData.get("aprvFileDataList");

        CamelMap resultMap = new CamelMap();
        resultMap.put("rstCd", AprvResultType.FAIL.getId());
        resultMap.put("rstMsg", AprvResultType.REQ_ERR_MSG.getName());

        daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_M_INFO.getId());
        daoParam.put("aprvId", aprvId);
        daoParam.put("userId", userId);

        CamelMap aprvInfo = aprvDao.selectOne(daoParam);

        if (aprvInfo == null || aprvInfo.isEmpty()) {
            resultMap.put("rstMsg", AprvResultType.REQ_ERR_MSG_001.getName());
            return resultMap;
        }

        if (!ACT00_0.equals(aprvInfo.getString("LAST_STUS_CD"))) {
            resultMap.put("rstMsg", AprvResultType.REQ_ERR_MSG_002.getName());
            return resultMap;
        }

        final String aprvTypeCd = aprvInfo.getString("aprvTypeCd");

        daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_LINE_INFO.getId());
        daoParam.put("aprvTypeCd", aprvTypeCd);

        List<CamelMap> aprvLineInfo = aprvDao.selectList(daoParam);

        if (aprvLineInfo == null || aprvLineInfo.isEmpty()) {
            resultMap.put("rstMsg", AprvResultType.ERR_MSG_001.getName());
            return resultMap;
        }

        PenAprvM penAprvM = new PenAprvM(aprvInfo);
        penAprvM.setAprvDesc(aprvDesc);
        penAprvM.setLastStusCd(ACT01_0);

        MetaObjectAprvInfo metaObjectAprvInfo = new MetaObjectAprvInfo();
        String nextAprvOrdNo = "";

        final String dt = getDataTime();

        CamelMap reqAprvMap = new CamelMap();
        reqAprvMap.put("TR_ID", getTid());
        reqAprvMap.put("APRV_ID", aprvId);
        reqAprvMap.put("OBJ_NM", aprvInfo.getString("objNm"));
        reqAprvMap.put("APRV_DESC", aprvDesc);
        reqAprvMap.put("APRV_REQ_DT", dt);

        List<CamelMap> reqAprvUserList = new ArrayList<>();

        String aprvTypeNm = "";
        String aprvMailSubject = "";
        String aprvMailBody = "";
        String aprvSmsBody = "";
        LOG.info(aprvTypeNm, aprvMailSubject, aprvMailBody, aprvSmsBody);

        for (CamelMap aprvLineMap : aprvLineInfo) {
            String aprvUserGubun = aprvLineMap.getString("APRV_USER_GUBUN");
            String aprvLineMngrId = aprvLineMap.getString("APRV_LINE_MNGR_ID");
            String grpRoleId = aprvLineMap.getString("GRP_ROLE_ID");
            String aprvProcNm = aprvLineMap.getString("APRV_PROC_NM");
            String aprvOrdNo = aprvLineMap.getString("APRV_ORD_NO");
            int ordNo = 1;

            if ("1".equals(aprvOrdNo)) {
                aprvTypeNm = aprvLineMap.getString("APRV_TYPE_NM");
                PenAprvD penAprvD = new PenAprvD();
                penAprvD.setAprvId(aprvId);
                penAprvD.setDelYn("N");
                penAprvD.setAprvDetlId(aprvLineMngrId + ordNo);
                penAprvD.setAprvLineId(aprvLineMngrId);
                penAprvD.setAprvPrcsNm(aprvProcNm);
                penAprvM.setAprvNm(aprvTypeNm);
                penAprvM.setAprvReqDt(dt);
                penAprvM.setLastLineId(penAprvD.getAprvDetlId());

                metaObjectAprvInfo.add(penAprvD);

                resultMap.put("rstDetlId", penAprvD.getAprvDetlId());

                aprvMailSubject = aprvLineMap.getString("MAIL_SUBJECT");
                aprvMailBody = aprvLineMap.getString("MAIL_BODY");
                aprvSmsBody = aprvLineMap.getString("SMS");
                LOG.info(aprvMailSubject, aprvMailBody, aprvSmsBody);

                CamelMap reqAprvUserMap = new CamelMap();
                putReqAprvUserMap(aprvInfo, reqAprvUserMap, aprvInfo.getString("APRV_REQR_ID"), aprvOrdNo, "R01");

                reqAprvUserList.add(reqAprvUserMap);

                putAddFile(metaObjectAprvInfo, aprvFileDataList, aprvId, penAprvD.getAprvDetlId(), userId);
            }
            else {
                if ("USER".equals(aprvUserGubun)) {
                    for (Object aprvDataObj : aprvDataList) {
                        Map aprvDataMap = (Map) aprvDataObj;
                        if (aprvLineMngrId.equals(aprvDataMap.get("APRV_LINE_MNGR_ID"))) {
                            if ("".equals(nextAprvOrdNo)) {
                                nextAprvOrdNo = aprvOrdNo;
                            }
                            PenAprvD penAprvD = new PenAprvD();
                            penAprvD.setAprvId(aprvId);
                            penAprvD.setDelYn("N");
                            penAprvD.setAprvDetlId(aprvLineMngrId + ordNo);
                            penAprvD.setAprvLineId(aprvLineMngrId);
                            if (nextAprvOrdNo.equals(aprvOrdNo) && ordNo == 1) {
                                penAprvD.setAprvRsltCd(ACT01_0);

                                daoParam = new DaoParam();
                                daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_USER_DTL_INFO.getId());
                                daoParam.put("userId", userId);
                                CamelMap aprvUserDtlInfo = aprvDao.selectOne(daoParam);

                                CamelMap reqAprvUserMap = new CamelMap(aprvUserDtlInfo);
                                putReqAprvUserMap(aprvUserDtlInfo, reqAprvUserMap, aprvUserDtlInfo.getString("USER_ID"), aprvOrdNo, "R02");

                                reqAprvUserList.add(reqAprvUserMap);
                            }
                            penAprvD.setAprvPrcsSortNo(ordNo);
                            penAprvD.setAprvPrcsNm(aprvProcNm);
                            penAprvD.setAprvUserId((String) aprvDataMap.get("USER_ID"));

                            metaObjectAprvInfo.add(penAprvD);
                            ordNo++;
                        }
                    }
                }
                else if ("ROLE".equals(aprvUserGubun)) {
                    daoParam = new DaoParam();
                    daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_USER_GRP_INFO.getId());
                    daoParam.put("userGrpId", grpRoleId);
                    List<CamelMap> userList = aprvDao.selectList(daoParam);

                    if (userList == null || userList.isEmpty()) {
                        throw new Exception(grpRoleId + " ROLE User is null");
                    }

                    if ("".equals(nextAprvOrdNo)) {
                        nextAprvOrdNo = aprvOrdNo;
                    }

                    for (CamelMap userMap : userList) {
                        PenAprvD penAprvD = new PenAprvD();
                        penAprvD.setAprvId(aprvId);
                        penAprvD.setDelYn("N");
                        penAprvD.setAprvDetlId(aprvLineMngrId + ordNo);
                        penAprvD.setAprvLineId(aprvLineMngrId);
                        if (nextAprvOrdNo.equals(aprvOrdNo)) {
                            penAprvD.setAprvRsltCd(ACT01_0);

                            CamelMap reqAprvUserMap = new CamelMap();
                            putReqAprvUserMap(userMap, reqAprvUserMap, userMap.getString("USER_ID"), aprvOrdNo, "R02");

                            reqAprvUserList.add(reqAprvUserMap);
                        }
                        penAprvD.setAprvPrcsSortNo(ordNo);
                        penAprvD.setAprvPrcsNm(aprvProcNm);
                        penAprvD.setAprvUserId((String) userMap.get("USER_ID"));

                        metaObjectAprvInfo.add(penAprvD);
                        ordNo++;
                    }
                }
            }
        }

        metaObjectAprvInfo.setPenAprvM(penAprvM);
        saveObjInfo(metaObjectAprvInfo, userId);
        saveObjFileInfo(metaObjectAprvInfo, userId);

        reqAprvMap.put("USER_LIST", reqAprvUserList);
        
        /* 메일, SMS, 기타 연동 부분 기능 추가 */
        try {
            LOG.info("reqAprvMap : {}", reqAprvMap);
            LOG.info("resultMap : {}", resultMap);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        resultMap.put("rstCd", AprvResultType.SUCC.getId());
        resultMap.put("rstMsg", AprvResultType.REQ_SUC_MSG.getName());
        return resultMap;
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CamelMap rtyAprv(CamelMap aprvData) throws Exception {
        DaoParam daoParam;
        final String userId = aprvData.getString("userId");
        final String aprvId = aprvData.getString("aprvId");
        final String aprvDesc = aprvData.getString("aprvDesc");
        List<?> aprvFileDataList = (List<?>) aprvData.get("aprvFileDataList");

        CamelMap resultMap = new CamelMap();
        resultMap.put("rstCd", AprvResultType.FAIL.getId());
        resultMap.put("rstMsg", AprvResultType.REQ_ERR_MSG.getName());

        daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_M_INFO.getId());
        daoParam.put("aprvId", aprvId);
        daoParam.put("userId", userId);

        CamelMap aprvInfo = aprvDao.selectOne(daoParam);

        if (aprvInfo == null || aprvInfo.isEmpty()) {
            resultMap.put("rstMsg", AprvResultType.REQ_ERR_MSG_001.getName());
            return resultMap;
        }

        if (!ACT00_0.equals(aprvInfo.getString("LAST_STUS_CD"))) {
            resultMap.put("rstMsg", AprvResultType.REQ_ERR_MSG_002.getName());
            return resultMap;
        }

        final String aprvTypeCd = aprvInfo.getString("aprvTypeCd");

        daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_LINE_INFO.getId());
        daoParam.put("aprvTypeCd", aprvTypeCd);

        List<CamelMap> aprvLineInfo = aprvDao.selectList(daoParam);

        if (aprvLineInfo == null || aprvLineInfo.isEmpty()) {
            resultMap.put("rstMsg", AprvResultType.ERR_MSG_001.getName());
            return resultMap;
        }

        final String dt = getDataTime();

        CamelMap reqAprvMap = new CamelMap();
        reqAprvMap.put("TR_ID", getTid());
        reqAprvMap.put("APRV_ID", aprvId);
        reqAprvMap.put("OBJ_NM", aprvInfo.getString("objNm"));
        reqAprvMap.put("APRV_DESC", aprvDesc);
        reqAprvMap.put("APRV_REQ_DT", dt);

        List<CamelMap> reqAprvUserList = new ArrayList<>();

        MetaObjectAprvInfo metaObjectAprvInfo = new MetaObjectAprvInfo();

        String aprvDetlId = "";
        String aprvMailSubject = "";
        String aprvMailBody = "";
        String aprvSmsBody = "";
        LOG.info(aprvMailSubject, aprvMailBody, aprvSmsBody);

        for (CamelMap aprvLineMap : aprvLineInfo) {
            String aprvLineMngrId = aprvLineMap.getString("APRV_LINE_MNGR_ID");
            String aprvOrdNo = aprvLineMap.getString("APRV_ORD_NO");

            if ("1".equals(aprvOrdNo)) {
                aprvMailSubject = aprvLineMap.getString("MAIL_SUBJECT");
                aprvMailBody = aprvLineMap.getString("MAIL_BODY");
                aprvSmsBody = aprvLineMap.getString("SMS");
                LOG.info(aprvMailSubject, aprvMailBody, aprvSmsBody);
                aprvDetlId = aprvLineMngrId + aprvOrdNo;

                CamelMap reqAprvUserMap = new CamelMap();
                putReqAprvUserMap(aprvInfo, reqAprvUserMap, aprvInfo.getString("APRV_REQR_ID"), aprvOrdNo, "R01");

                reqAprvUserList.add(reqAprvUserMap);

                putAddFile(metaObjectAprvInfo, aprvFileDataList, aprvId, aprvDetlId, userId);
            }
            else {
                daoParam = new DaoParam();
                daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_DA_INFO.getId());
                daoParam.put("aprvId", aprvId);
                List<CamelMap> aprvDaInfo = aprvDao.selectList(daoParam);

                for (CamelMap aprvDaInfoMap : aprvDaInfo) {
                    CamelMap reqAprvUserMap = new CamelMap();
                    putReqAprvUserMap(aprvDaInfoMap, reqAprvUserMap, aprvDaInfoMap.getString("APRV_USER_ID"), aprvOrdNo, "R02");

                    reqAprvUserList.add(reqAprvUserMap);
                }
                break;
            }
        }

        PenAprvM penAprvM = new PenAprvM();
        penAprvM.setAprvId(aprvId);
        penAprvM.setLastLineId(aprvDetlId);
        penAprvM.setAprvDesc(aprvDesc);
        penAprvM.setLastStusCd(ACT01_1);

        daoParam = new DaoParam(penAprvM);
        daoParam.put("queryId", AprvQueryIdType.PEN_APRV_M__UPDATE.getId());
        daoParam.put("userId", userId);
        aprvDao.update(daoParam);

        PenAprvD penAprvD = new PenAprvD();
        penAprvD.setAprvId(aprvId);

        daoParam = new DaoParam(penAprvD);
        daoParam.put("queryId", AprvQueryIdType.PEN_APRV_D__UPDATE_APRV_DA_INFO.getId());
        daoParam.put("userId", userId);
        aprvDao.update(daoParam);

        saveObjFileInfo(metaObjectAprvInfo, userId);

        reqAprvMap.put("USER_LIST", reqAprvUserList);

        /* 메일, SMS, 기타 연동 부분 기능 추가 */
        try {
            LOG.info("reqAprvMap : {}", reqAprvMap);
            LOG.info("resultMap : {}", resultMap);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        resultMap.put("rstCd", AprvResultType.SUCC.getId());
        resultMap.put("rstMsg", AprvResultType.REQ_SUC_MSG.getName());
        return resultMap;
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CamelMap doAprv(CamelMap aprvData) throws Exception {
        DaoParam daoParam;
        final String userId = aprvData.getString("userId");
        final String aprvId = aprvData.getString("aprvId");
        final String aprvDetlId = aprvData.getString("aprvDetlId");
        final String aprvRsltCd = aprvData.getString("aprvRsltCd");
        final String aprvDesc = aprvData.getString("aprvDesc");
        List<?> aprvFileDataList = (List<?>) aprvData.get("aprvFileDataList");

        CamelMap resultMap = new CamelMap();
        resultMap.put("rstCd", AprvResultType.FAIL.getId());
        resultMap.put("rstMsg", AprvResultType.DO_ERR_MSG.getName());

        daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_MD_INFO.getId());
        daoParam.put("aprvId", aprvId);
        daoParam.put("aprvDetlId", aprvDetlId);
        daoParam.put("userId", userId);

        CamelMap aprvMdInfo = aprvDao.selectOne(daoParam);

        if (aprvMdInfo == null || aprvMdInfo.isEmpty()) {
            resultMap.put("rstMsg", AprvResultType.DO_ERR_MSG_001.getName());
            return resultMap;
        }

        final String lastStusCd = aprvMdInfo.getString("LAST_STUS_CD");

        if (ACT00_0.equals(lastStusCd)) {
            resultMap.put("rstMsg", AprvResultType.DO_ERR_MSG_002.getName());
            return resultMap;
        }
        else if (ACT04_0.equals(lastStusCd) || ACT04_2.equals(lastStusCd) || ACT04_1.equals(lastStusCd)) {
            resultMap.put("rstMsg", AprvResultType.DO_ERR_MSG_001.getName());
            return resultMap;
        }
        else if (!ACT01_0.equals(lastStusCd) && !ACT01_1.equals(lastStusCd)) {
            resultMap.put("rstMsg", AprvResultType.DO_ERR_MSG_003.getName());
            return resultMap;
        }

        final String aprvTypeCd = aprvMdInfo.getString("aprvTypeCd");
        final String aprvLineId = aprvMdInfo.getString("aprvLineId");
        final String aprvPrcsSortNo = aprvMdInfo.getString("aprvPrcsSortNo");
/*
        final String sbYn = aprvMdInfo.getString("sbYn");
        final String dsYn = aprvMdInfo.getString("dsYn");
        final String objTypeId = aprvMdInfo.getString("objTypeId");
        final String objId = aprvMdInfo.getString("objId");
*/
        daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_LINE_INFO.getId());
        daoParam.put("aprvTypeCd", aprvTypeCd);

        List<CamelMap> aprvLineInfo = aprvDao.selectList(daoParam);

        if (aprvLineInfo == null || aprvLineInfo.isEmpty()) {
            resultMap.put("rstMsg", AprvResultType.ERR_MSG_001.getName());
            return resultMap;
        }

        String nextAprvOrdNo = "";
        String prevAprvOrdNo = "";
        boolean retAprvChk = false;
        boolean recAprvChk = false;
        boolean doAprvChk = true;
        boolean doAprvNextChk = false;

        CamelMap reqAprvMap = new CamelMap();
        reqAprvMap.put("TR_ID", getTid());
        reqAprvMap.put("APRV_ID", aprvId);
        reqAprvMap.put("OBJ_NM", aprvMdInfo.getString("objNm"));
        reqAprvMap.put("APRV_DESC", aprvMdInfo.getString("aprvDesc"));
        reqAprvMap.put("APRV_TYPE_CD", aprvTypeCd);
        reqAprvMap.put("APRV_REQ_DT", aprvMdInfo.getString("aprvReqDt"));

        List<CamelMap> reqAprvUserList = new ArrayList<>();

        CamelMap doAprvMap = new CamelMap();
        doAprvMap.put("TR_ID", getTid());
        doAprvMap.put("APRV_ID", aprvId);
        doAprvMap.put("OBJ_NM", aprvMdInfo.getString("objNm"));
        doAprvMap.put("APRV_DESC", aprvMdInfo.getString("aprvDesc"));
        doAprvMap.put("APRV_TYPE_CD", aprvTypeCd);
        doAprvMap.put("APRV_REQ_DT", aprvMdInfo.getString("aprvReqDt"));
        doAprvMap.put("APRV_RSLT_CD", aprvRsltCd);
        doAprvMap.put("DO_APRV_CHK", "N");

        List<CamelMap> doAprvUserList = new ArrayList<>();

        MetaObjectAprvInfo metaObjectAprvInfo = new MetaObjectAprvInfo();

        String aprvTypeNm = "";
        String aprvMailSubject = "";
        String aprvMailBody = "";
        String aprvSmsBody = "";
        String aprvGubun = "";

        for (CamelMap aprvLineMap : aprvLineInfo) {
            if (retAprvChk || recAprvChk) {
                break;
            }

            String aprvUserGubun = aprvLineMap.getString("APRV_USER_GUBUN");
            String aprvLineMngrId = aprvLineMap.getString("APRV_LINE_MNGR_ID");
            String aprvLineDesc = aprvLineMap.getString("APRV_LINE_DESC");
            String aprvProcNm = aprvLineMap.getString("APRV_PROC_NM");
            String aprvOrdNo = aprvLineMap.getString("APRV_ORD_NO");

            if ("1".equals(aprvOrdNo)) {
                aprvTypeNm = aprvLineMap.getString("APRV_TYPE_NM");

                CamelMap doAprvUserMap = new CamelMap();
                putDoAprvUserMap(aprvMdInfo, doAprvUserMap, aprvMdInfo.getString("APRV_REQR_ID"), aprvLineDesc, aprvMdInfo.getString("APRV_DESC"), aprvOrdNo, aprvTypeCd, ACT01_0, "R01");

                doAprvUserList.add(doAprvUserMap);
                reqAprvUserList.add(doAprvUserMap);
            }
            else {
                if ("USER".equals(aprvUserGubun)) {
                    daoParam = new DaoParam();
                    daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_USER_INFO.getId());
                    daoParam.put("aprvId", aprvId);
                    daoParam.put("aprvLineMngrId", aprvLineMngrId);

                    List<CamelMap> aprvUserInfo = aprvDao.selectList(daoParam);

                    for (CamelMap aprvUserMap : aprvUserInfo) {
                        if (aprvLineId.equals(aprvLineMngrId) && aprvPrcsSortNo.equals(aprvUserMap.getString("APRV_PRCS_SORT_NO"))) {
                            if (doAprvChk || aprvOrdNo.equals(prevAprvOrdNo)) {
                                aprvMailSubject = aprvLineMap.getString("MAIL_SUBJECT");
                                aprvMailBody = aprvLineMap.getString("MAIL_BODY");
                                aprvSmsBody = aprvLineMap.getString("MOBILE");
                                aprvGubun = aprvLineMap.getString("APRV_GUBUN");

                                daoParam = new DaoParam();
                                daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_D_INFO.getId());
                                daoParam.put("aprvId", aprvId);

                                List<CamelMap> aprvDInfo = aprvDao.selectList(daoParam);

                                PenAprvD penAprvD = new PenAprvD();
                                penAprvD.setAprvDesc(aprvDesc);
                                penAprvD.setAprvRsltCd(aprvRsltCd);
                                penAprvD.setAprvId(aprvId);
                                penAprvD.setAprvDetlId(aprvDetlId);

                                daoParam = new DaoParam(penAprvD);
                                daoParam.put("queryId", AprvQueryIdType.PEN_APRV_D__UPDATE.getId());
                                daoParam.put("userId", aprvId);

                                aprvDao.update(daoParam);

                                putAddFile(metaObjectAprvInfo, aprvFileDataList, aprvId, penAprvD.getAprvDetlId(), userId);

                                PenAprvM penAprvM = new PenAprvM();
                                penAprvM.setAprvId(aprvId);
                                penAprvM.setLastLineId(aprvDetlId);
                                if (ACT04_2.equals(aprvRsltCd)) {
                                    penAprvM.setLastStusCd(aprvRsltCd);

                                    daoParam = new DaoParam(penAprvM);
                                    daoParam.put("queryId", AprvQueryIdType.PEN_APRV_M__UPDATE.getId());
                                    daoParam.put("userId", userId);

                                    aprvDao.update(daoParam);

                                    for (CamelMap aprvDInfoMap : aprvDInfo) {
                                        CamelMap doAprvUserMap = new CamelMap();
                                        putDoAprvUserMap(aprvDInfoMap, doAprvUserMap, aprvDInfoMap.getString("APRV_USER_ID"), aprvLineDesc, aprvDesc, aprvOrdNo, aprvTypeCd, aprvRsltCd, "R02");

                                        doAprvUserList.add(doAprvUserMap);
                                    }

                                    retAprvChk = true;
                                    break;
                                }

                                daoParam = new DaoParam(penAprvM);
                                daoParam.put("queryId", AprvQueryIdType.PEN_APRV_M__UPDATE.getId());
                                daoParam.put("userId", userId);

                                aprvDao.update(daoParam);

                                for (CamelMap aprvDInfoMap : aprvDInfo) {
                                    CamelMap doAprvUserMap = new CamelMap();
                                    putDoAprvUserMap(aprvDInfoMap, doAprvUserMap, aprvDInfoMap.getString("APRV_USER_ID"), aprvLineDesc, aprvDesc, aprvOrdNo, aprvTypeCd, aprvRsltCd, "R02");

                                    doAprvUserList.add(doAprvUserMap);
                                }

                                doAprvNextChk = true;
                            }
                            else {
                                resultMap.put("rstMsg", AprvResultType.DO_ERR_MSG_004.getName());
                                return resultMap;
                            }
                        }
                        else {
                            if ((doAprvNextChk && doAprvChk && !aprvOrdNo.equals(prevAprvOrdNo)) || (aprvOrdNo.equals(nextAprvOrdNo) && "1".equals(aprvUserMap.getString("APRV_PRCS_SORT_NO")))) {
                                PenAprvD penAprvD = new PenAprvD();
                                penAprvD.setAprvPrcsSortNo(Integer.parseInt(aprvUserMap.getString("APRV_PRCS_SORT_NO")));
                                penAprvD.setAprvLineId(aprvLineMngrId);
                                penAprvD.setAprvId(aprvId);

                                daoParam = new DaoParam(penAprvD);
                                daoParam.put("queryId", AprvQueryIdType.PEN_APRV_D__UPDATE_NEXT.getId());
                                daoParam.put("userId", userId);

                                aprvDao.update(daoParam);

                                daoParam = new DaoParam();
                                daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_D_INFO.getId());
                                daoParam.put("aprvId", aprvId);

                                List<CamelMap> aprvDInfo = aprvDao.selectList(daoParam);
                                for (CamelMap aprvDInfoMap : aprvDInfo) {
                                    CamelMap doAprvUserMap = new CamelMap();
                                    putDoAprvUserMap(aprvDInfoMap, doAprvUserMap, aprvDInfoMap.getString("APRV_USER_ID"), "", "", aprvOrdNo, aprvTypeCd, aprvRsltCd, "R02");

                                    reqAprvUserList.add(doAprvUserMap);
                                }

                                doAprvNextChk = false;
                                nextAprvOrdNo = aprvOrdNo;
                            }

                            if (ACT04_0.equals(aprvUserMap.getString("APRV_RSLT_CD")) || ACT05_0.equals(aprvUserMap.getString("APRV_RSLT_CD")) || ACT04_1.equals(aprvUserMap.getString("APRV_RSLT_CD"))) {
                                CamelMap doAprvUserMap = new CamelMap();
                                putDoAprvUserMap(aprvUserMap, doAprvUserMap, aprvUserMap.getString("APRV_USER_ID"), aprvLineDesc, aprvUserMap.getString("APRV_DESC"), aprvOrdNo, aprvTypeCd, aprvUserMap.getString("APRV_RSLT_CD"), "R11");

                                doAprvUserList.add(doAprvUserMap);
                            }
                            else {
                                doAprvChk = false;
                            }
                        }
                    }
                    prevAprvOrdNo = aprvOrdNo;
                }
                else if ("ROLE".equals(aprvUserGubun)) {
                    if (aprvLineId.equals(aprvLineMngrId)) {
                        if (doAprvChk || aprvOrdNo.equals(prevAprvOrdNo)) {
                            aprvMailSubject = aprvLineMap.getString("MAIL_SUBJECT");
                            aprvMailBody = aprvLineMap.getString("MAIL_BODY");
                            aprvSmsBody = aprvLineMap.getString("MOBILE");
                            aprvGubun = aprvLineMap.getString("APRV_GUBUN");

                            daoParam = new DaoParam();
                            daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_D_INFO.getId());
                            daoParam.put("aprvId", aprvId);

                            List<CamelMap> aprvDInfo = aprvDao.selectList(daoParam);

                            PenAprvD penAprvD = new PenAprvD();
                            penAprvD.setAprvDesc(aprvDesc);
                            penAprvD.setAprvRsltCd(aprvRsltCd);
                            penAprvD.setAprvId(aprvId);
                            penAprvD.setAprvDetlId(aprvDetlId);

                            daoParam = new DaoParam(penAprvD);
                            daoParam.put("queryId", AprvQueryIdType.PEN_APRV_D__UPDATE.getId());
                            daoParam.put("userId", userId);

                            aprvDao.update(daoParam);

                            penAprvD = new PenAprvD();
                            penAprvD.setAprvLineId(aprvLineMngrId);
                            penAprvD.setAprvId(aprvId);

                            daoParam = new DaoParam(penAprvD);
                            daoParam.put("queryId", AprvQueryIdType.PEN_APRV_D__UPDATE_ALL.getId());
                            daoParam.put("userId", userId);

                            aprvDao.update(daoParam);

                            putAddFile(metaObjectAprvInfo, aprvFileDataList, aprvId, aprvDetlId, userId);

                            PenAprvM penAprvM = new PenAprvM();
                            penAprvM.setAprvId(aprvId);
                            penAprvM.setLastLineId(aprvDetlId);
                            if (ACT04_2.equals(aprvRsltCd) || ACT05_0.equals(aprvRsltCd)) {
                                penAprvM.setLastStusCd(aprvRsltCd);

                                daoParam = new DaoParam(penAprvM);
                                daoParam.put("queryId", AprvQueryIdType.PEN_APRV_M__UPDATE.getId());
                                daoParam.put("userId", userId);

                                aprvDao.update(daoParam);

                                for (CamelMap aprvDInfoMap : aprvDInfo) {
                                    CamelMap doAprvUserMap = new CamelMap();
                                    putDoAprvUserMap(aprvDInfoMap, doAprvUserMap, aprvDInfoMap.getString("APRV_USER_ID"), aprvLineDesc, aprvDesc, aprvOrdNo, aprvTypeCd, aprvRsltCd, "R02");

                                    doAprvUserList.add(doAprvUserMap);
                                }

                                if (ACT04_2.equals(aprvRsltCd)) {
                                    retAprvChk = true;
                                }
                                else if (ACT05_0.equals(aprvRsltCd)) {
                                    recAprvChk = true;
                                }
                                break;
                            }

                            daoParam = new DaoParam(penAprvM);
                            daoParam.put("queryId", AprvQueryIdType.PEN_APRV_M__UPDATE.getId());
                            daoParam.put("userId", userId);

                            aprvDao.update(daoParam);

                            for (CamelMap aprvDInfoMap : aprvDInfo) {
                                CamelMap doAprvUserMap = new CamelMap();
                                putDoAprvUserMap(aprvDInfoMap, doAprvUserMap, aprvDInfoMap.getString("APRV_USER_ID"), aprvLineDesc, aprvDesc, aprvOrdNo, aprvTypeCd, aprvRsltCd, "R02");

                                doAprvUserList.add(doAprvUserMap);
                            }

                            doAprvNextChk = true;
                        }
                        else {
                            resultMap.put("rstMsg", AprvResultType.DO_ERR_MSG_004.getName());
                            return resultMap;
                        }
                    }
                    else {
                        if ((doAprvNextChk && doAprvChk && !aprvOrdNo.equals(prevAprvOrdNo)) || aprvOrdNo.equals(nextAprvOrdNo)) {
                            PenAprvD penAprvD = new PenAprvD();
                            penAprvD.setAprvLineId(aprvLineMngrId);
                            penAprvD.setAprvId(aprvId);

                            if (ACT05_0.equals(aprvRsltCd) && ACT01_1.equals(lastStusCd)) {
                                daoParam = new DaoParam(penAprvD);
                                daoParam.put("queryId", AprvQueryIdType.PEN_APRV_D__UPDATE_NEXT_RTY.getId());
                                daoParam.put("userId", userId);

                                aprvDao.update(daoParam);
                            }
                            else {
                                daoParam = new DaoParam(penAprvD);
                                daoParam.put("queryId", AprvQueryIdType.PEN_APRV_D__UPDATE_NEXT.getId());
                                daoParam.put("userId", userId);

                                aprvDao.update(daoParam);
                            }

                            daoParam = new DaoParam();
                            daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_D_INFO.getId());
                            daoParam.put("aprvId", aprvId);
                            daoParam.put("aprvLineMngrId", aprvLineMngrId);

                            List<CamelMap> aprvDInfo = aprvDao.selectList(daoParam);
                            for (CamelMap aprvDInfoMap : aprvDInfo) {
                                CamelMap doAprvUserMap = new CamelMap();
                                putDoAprvUserMap(aprvDInfoMap, doAprvUserMap, aprvDInfoMap.getString("APRV_USER_ID"), "", "", aprvOrdNo, aprvTypeCd, aprvRsltCd, "R02");

                                reqAprvUserList.add(doAprvUserMap);
                            }

                            doAprvNextChk = false;
                            nextAprvOrdNo = aprvOrdNo;
                        }

                        daoParam = new DaoParam();
                        daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_PROC_INFO.getId());
                        daoParam.put("aprvId", aprvId);
                        daoParam.put("aprvLineMngrId", aprvLineMngrId);

                        CamelMap cntMap = aprvDao.selectOne(daoParam);
                        int cnt = 0;
                        if (cntMap != null && !cntMap.isEmpty()) {
                            cnt = cntMap.getInteger("CNT");
                        }

                        if (cnt > 0) {
                            CamelMap doAprvUserMap = new CamelMap();
                            putDoAprvUserMap(cntMap, doAprvUserMap, cntMap.getString("APRV_USER_ID"), aprvLineDesc, cntMap.getString("APRV_DESC"), aprvOrdNo, aprvTypeCd, aprvRsltCd, "R11");

                            doAprvUserList.add(doAprvUserMap);
                        }
                        else {
                            doAprvChk = false;
                        }
                    }
                    prevAprvOrdNo = aprvOrdNo;
                }
            }
        }

        saveObjFileInfo(metaObjectAprvInfo, userId);

        doAprvMap.put("USER_LIST", doAprvUserList);
        reqAprvMap.put("USER_LIST", reqAprvUserList);

        LOG.info("doAprvMap : {}", doAprvMap);
        LOG.info("reqAprvMap : {}", reqAprvMap);

        if (retAprvChk) {
            try {

            }
            catch (Exception e) {
                LOG.info(e.getMessage());
            }
        }
        else if (recAprvChk) {
            try {

            }
            catch (Exception e) {
                LOG.info(e.getMessage());
            }
        }
        else if (doAprvChk) {
            PenAprvM penAprvM = new PenAprvM();
            penAprvM.setAprvId(aprvId);
            penAprvM.setLastLineId(aprvDetlId);
            penAprvM.setLastStusCd(aprvRsltCd);

            daoParam = new DaoParam(penAprvM);
            daoParam.put("queryId", AprvQueryIdType.PEN_APRV_M__UPDATE.getId());
            daoParam.put("userId", userId);

            aprvDao.update(daoParam);

            daoParam = new DaoParam(penAprvM);
            daoParam.put("aprvId", aprvId);
            daoParam.put("queryId", AprvQueryIdType.PEN_OBJ_M_DELETE.getId());
            aprvDao.delete(daoParam);
            daoParam.put("queryId", AprvQueryIdType.PEN_OBJ_D_DELETE.getId());
            aprvDao.delete(daoParam);
            daoParam.put("queryId", AprvQueryIdType.PEN_OBJ_R_DELETE.getId());
            aprvDao.delete(daoParam);

            metaReqService.approve(aprvId);
        }

        resultMap.put("rstCd", AprvResultType.SUCC.getId());
        if (ACT04_0.equals(aprvRsltCd)) {
            resultMap.put("rstMsg", AprvResultType.DO_SUC_MSG_001.getName());
        }
        else if (ACT04_2.equals(aprvRsltCd)) {
            resultMap.put("rstMsg", AprvResultType.DO_SUC_MSG_002.getName());
        }
        else if (ACT05_0.equals(aprvRsltCd)) {
            resultMap.put("rstMsg", AprvResultType.DO_SUC_MSG_003.getName());
        }
        else if (ACT04_1.equals(aprvRsltCd)) {
            resultMap.put("rstMsg", AprvResultType.DO_SUC_MSG_004.getName());
        }
        else {
            resultMap.put("rstMsg", AprvResultType.DO_SUC_MSG.getName());
        }

        return resultMap;
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void removePenAprvF(CamelMap aprvData) throws Exception {
        DaoParam daoParam;
        final String aprvId = aprvData.getString("aprvId");
        final String aprvDetlId = aprvData.getString("aprvDetlId");
        final Integer fileNo = aprvData.getInteger("fileNo");
        final String userId = aprvData.getString("userId");

        daoParam = new DaoParam();
        daoParam.put("queryId", AprvQueryIdType.PEN_APRV_F__DELETE.getId());
        daoParam.put("aprvId", aprvId);
        daoParam.put("aprvDetlId", aprvDetlId);
        daoParam.put("fileNo", fileNo);
        daoParam.put("userId", userId);

        aprvDao.delete(daoParam);
    }
}