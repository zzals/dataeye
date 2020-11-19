package kr.co.penta.dataeye.spring.web.common.service.impl;

import com.google.common.base.Splitter;
import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.*;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.meta.util.AtrMSqlSbstMemoryMap;
import kr.co.penta.dataeye.common.meta.util.AtrMSqlSbstMemoryMap.CodeEntity;
import kr.co.penta.dataeye.common.meta.util.DeFileUtils;
import kr.co.penta.dataeye.common.meta.util.QueryBuilder;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.mybatis.dao.AprvDao;
import kr.co.penta.dataeye.spring.mybatis.dao.MetaDao;
import kr.co.penta.dataeye.spring.mybatis.dao.MetaReqDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.aprv.enums.AprvCodeType;
import kr.co.penta.dataeye.spring.web.aprv.enums.AprvQueryIdType;
import kr.co.penta.dataeye.spring.web.aprv.service.AprvService;
import kr.co.penta.dataeye.spring.web.common.service.MetaReqService;
import kr.co.penta.dataeye.spring.web.credit.service.AnaTaskService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class MetaReqServiceImpl implements MetaReqService {
    private static final Logger LOG = LoggerFactory.getLogger(MetaReqServiceImpl.class);
    
    @Autowired private MetaDao metaDao;
    @Autowired private MetaReqDao metaReqDao;
    @Autowired private AprvService aprvService;
    @Autowired private AprvDao aprvDao;
    
    @Autowired private AnaTaskService anaTaskService;
    

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CamelMap saveObjAprv(Map<String, Object> reqParam, SessionInfo sessionInfo) throws Exception {
        CamelMap resultMap = new CamelMap();

        MetaObjectReqInfo metaObjectReqInfo = new MetaObjectReqInfo();

        Map<String, Object> objInfo = (Map<String, Object>) reqParam.get("objInfo");

        PenObjMT penObjMT = new PenObjMT((Map<String, Object>) objInfo.get("penObjMT"), sessionInfo);
        List<PenObjDT> penObjDTs = (List<PenObjDT>) objInfo.get("penObjDTs");

        String aprvId = StringUtils.defaultString(penObjMT.getAprvId());
        
        
        PenAprvM penAprvM = new PenAprvM();
        MetaObjectAprvInfo metaObjectAprvInfo = new MetaObjectAprvInfo();
        String lastStusCd = "";

        if (!"".equals(aprvId)) {
            penAprvM.setAprvId(aprvId);

            DaoParam daoParam = new DaoParam();
            daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_STS_M_INFO.getId());
            daoParam.put("aprvId", aprvId);

            CamelMap aprvStsMInfo = aprvDao.selectOne(daoParam);
            lastStusCd = aprvStsMInfo.getString("lastStusCd");
        }
        else {
            penAprvM.genAprvId();
        }

        penAprvM.setAprvTypeCd(reqParam.get("APRV_TYPE_CD").toString());
        penAprvM.setLastStusCd("00");
        penAprvM.setObjTypeId(penObjMT.getObjTypeId());
        
        if(penObjMT.getObjTypeId().equals("CR0203L")) {
        	
        	if(reqParam.get("CHANGE_MODE").toString().equals("CHG")) { //변경요청일 경우
            	penAprvM.setAprvReqrCd("20");
            }else if(reqParam.get("CHANGE_MODE").toString().equals("RET")) {//회수요청이라면..
            	penAprvM.setAprvReqrCd("30");
            }
        }
        

        if ("".equals(penObjMT.getObjId())) {
            penObjMT.genObjId();
        }

        penAprvM.setObjId(penObjMT.getObjId());

        metaObjectAprvInfo.setPenAprvM(penAprvM);

        if (lastStusCd.equals("00") || "".equals(lastStusCd)) {
            aprvService.saveObjInfo(metaObjectAprvInfo, sessionInfo.getUserId());
        }
        
        //승인번호를 셋팅한다.
        penObjMT.setAprvId(penAprvM.getAprvId());
        
        metaObjectReqInfo.setPenObjMT(penObjMT);
        metaObjectReqInfo.setPenObjDTs(penObjMT, penObjDTs);

        saveObjInfo(metaObjectReqInfo, sessionInfo);
        
        //sandbox 관련일경우에만  상태 마스터 테이블에 입력한다.
        if(penObjMT.getObjTypeId().equals("CR0203L")) {
        	
        	Map<String, Object> stsMap=new HashMap<String, Object>();
        	stsMap.put("APRV_ID", penAprvM.getAprvId());
        	stsMap.put("OBJ_TYPE_ID", penObjMT.getObjTypeId());
        	stsMap.put("OBJ_ID", penObjMT.getObjId());
        	stsMap.put("REQ_TYPE_CD", reqParam.get("APRV_TYPE_CD").toString());
        	//00 : 저장, 10: 승인요청, 40: 승인완료, 42: 반려
        	stsMap.put("STATUS_CD", AprvCodeType.ACT00_0.getId());
        	stsMap.put("CRETR_ID", penObjMT.getSessionInfo().getUserId());
        	
        	//10: 신규, 20:변경, 30:회수
        	stsMap.put("APRV_REQR_CD", penAprvM.getAprvReqrCd());
        	
        	
        	
        	//마스터 상태 테이블에 입력한다.
        	anaTaskService.saveCreditAprvStsm(stsMap);
        	
        	//라이센스 테이블에 예약상태로 업데이트 한다.
        	anaTaskService.updateTmpSaveLicense(stsMap);
        	
        }
        
        
        resultMap.put("APRV_ID", penAprvM.getAprvId());

        return resultMap;
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CamelMap saveMultiObjAprv(Map<String, Object> reqParam, SessionInfo sessionInfo) throws Exception {
        CamelMap resultMap = new CamelMap();

        MetaObjectReqInfo metaObjectReqInfo = new MetaObjectReqInfo();

        String aprvId = StringUtils.defaultString((String)reqParam.get("APRV_ID"), "");
        String aprvTypeCd = StringUtils.defaultString((String)reqParam.get("APRV_TYPE_CD"), "");
        String objTypeId = StringUtils.defaultString((String)reqParam.get("OBJ_TYPE_ID"), "");
                
        PenAprvM penAprvM = new PenAprvM();
        MetaObjectAprvInfo metaObjectAprvInfo = new MetaObjectAprvInfo();
        String lastStusCd = "";

        if (!"".equals(aprvId)) {
            penAprvM.setAprvId(aprvId);

            DaoParam daoParam = new DaoParam();
            daoParam.put("queryId", AprvQueryIdType.APRV__GET_APRV_STS_M_INFO.getId());
            daoParam.put("aprvId", aprvId);

            CamelMap aprvStsMInfo = aprvDao.selectOne(daoParam);
            lastStusCd = aprvStsMInfo.getString("lastStusCd");
            
            metaReqDao.removePenObjRTByAprvId(aprvId);
            metaReqDao.removePenObjDTByAprvId(aprvId);
            metaReqDao.removePenObjMTByAprvId(aprvId);
        }
        else {
            penAprvM.genAprvId();
        }

        penAprvM.setAprvTypeCd(aprvTypeCd);
        penAprvM.setObjTypeId(objTypeId);
        penAprvM.setLastStusCd("00");
        
        metaObjectAprvInfo.setPenAprvM(penAprvM);

        if (lastStusCd.equals("00") || "".equals(lastStusCd)) {
            aprvService.saveObjInfo(metaObjectAprvInfo, sessionInfo.getUserId());
        }
        
        List<Map<String, Object>> objInfos = (List<Map<String, Object>>) reqParam.get("objInfos");
        for(Map<String, Object> objInfo : objInfos) {
	        PenObjMT penObjMT = new PenObjMT((Map<String, Object>) objInfo.get("penObjMT"), sessionInfo);
	        List<PenObjDT> penObjDTs = (List<PenObjDT>) objInfo.get("penObjDTs");
	
	        //승인번호를 셋팅한다.
	        penObjMT.setAprvId(penAprvM.getAprvId());
	        
	        metaObjectReqInfo.setPenObjMT(penObjMT);
	        metaObjectReqInfo.setPenObjDTs(penObjMT, penObjDTs);
	
	        saveObjInfo(metaObjectReqInfo, sessionInfo);
        }
        
        resultMap.put("APRV_ID", penAprvM.getAprvId());

        return resultMap;
    }
    
    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveObjInfo(MetaObjectReqInfo metaObjectReqInfo, SessionInfo sessionInfo) {
        PenObjMT penObjMT = metaObjectReqInfo.getPenObjMT();
        
        
        if ("".equals(penObjMT.getObjId())) {
            penObjMT.genObjId();
        }
        else {
            CamelMap cretInfo = metaReqDao.getObjMCretInfo(penObjMT.getObjTypeId(), penObjMT.getObjId(), penObjMT.getAprvId());
            if (cretInfo != null) {
                penObjMT.setCretDt(cretInfo.getString("cretDt"));
                penObjMT.setCretrId(cretInfo.getString("cretrId"));
            }

            metaReqDao.removePenObjMT(penObjMT.getObjTypeId(), penObjMT.getObjId(), penObjMT.getAprvId());
            metaReqDao.removePenObjDT(penObjMT.getObjTypeId(), penObjMT.getObjId(), penObjMT.getAprvId());
        }

        metaReqDao.insertPenObjMT(penObjMT, sessionInfo);

        List<PenObjDT> penObjDTs = metaObjectReqInfo.getPenObjDTs();
        for (PenObjDT penObjDT : penObjDTs) {
            penObjDT.setObjTypeId(penObjMT.getObjTypeId());
            penObjDT.setObjId(penObjMT.getObjId());
            penObjDT.setCretDt(penObjMT.getCretDt());
            penObjDT.setCretrId(penObjMT.getCretrId());

            if ("FLE".equals(penObjDT.getUiCompCd())) {
                procUiCompCdFLE(penObjMT, penObjDT, sessionInfo);
            }
            else {
                if ("Y".equals(penObjDT.getCnctAtrYn())) {
                    procCnctAtrV(penObjDT, sessionInfo);
                }
                else {
                    metaReqDao.insertPenObjDT(penObjDT, sessionInfo);
                }
            }
        }
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void removePenObjMDT(String aprvId, SessionInfo sessionInfo) {
        metaReqDao.removePenObjMDT(aprvId, sessionInfo.getUserId());
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void removePenObjRT(String objTypeId, String objId, String relObjTypeId, String relObjId, String aprvId) {
        metaReqDao.removePenObjRT(objTypeId, objId, relObjTypeId, relObjId, aprvId);
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void removePenObjRTs(List<CamelMap> penObjRTs, SessionInfo sessionInfo) {
        for (CamelMap penObjRT : penObjRTs) {
            removePenObjRT(
                    penObjRT.getString("objTypeId"),
                    penObjRT.getString("objId"),
                    penObjRT.getString("relObjTypeId"),
                    penObjRT.getString("relObjId"),
                    penObjRT.getString("aprvId")
            );
        }
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertPenObjRT(String objTypeId, String objId, String relObjTypeId, String relObjId, String aprvId, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId);
        daoParam.put("relObjTypeId", relObjTypeId);
        PenRelTypeM penRelTypeM = metaDao.getRelTypeMOne(daoParam);
        PenObjRT penObjRT = new PenObjRT();
        penObjRT.setObjTypeId(objTypeId);
        penObjRT.setObjId(objId);
        penObjRT.setRelObjTypeId(relObjTypeId);
        penObjRT.setRelObjId(relObjId);
        penObjRT.setAprvId(aprvId);
        penObjRT.setRelTypeId(penRelTypeM.getRelTypeId());
        penObjRT.setObjRelAnalsCd("MC");

        metaReqDao.insertPenObjRT(penObjRT, sessionInfo);
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertPenObjRTs(List<CamelMap> penObjRTs, SessionInfo sessionInfo) {
        for (CamelMap penObjRT : penObjRTs) {
            insertPenObjRT(
                    penObjRT.getString("objTypeId"),
                    penObjRT.getString("objId"),
                    penObjRT.getString("relObjTypeId"),
                    penObjRT.getString("relObjId"),
                    penObjRT.getString("aprvId"),
                    sessionInfo
            );
        }
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void approve(String aprvId) {
        metaReqDao.approvePenObjMT(aprvId);
        metaReqDao.approvePenObjDT(aprvId);
        metaReqDao.approvePenObjRT(aprvId);
    }

    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq, Integer beginAtrValSeq, Integer endAtrValSeq) {
        metaReqDao.removeObjDAtr(objTypeId, objId, atrIdSeq, beginAtrValSeq, endAtrValSeq);
    }

    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void procUiCompCdFLE(PenObjMT penObjMT, PenObjDT penObjDT, SessionInfo sessionInfo) {
        String tempPath = DataEyeSettingsHolder.getInstance().getAttachFile().getUploadTempPath();
        String realPath = DataEyeSettingsHolder.getInstance().getAttachFile().getUploadReadPath();

        ObjFileModel objFileModel = null;
        Object o = penObjDT.getObjAtrVal();
        if (o instanceof Map) {
            CamelMap fileMap = (CamelMap) o;
            objFileModel = new ObjFileModel();
            objFileModel.setFileLocName(fileMap.getString("fileLocName"));
            objFileModel.setFilePhyName(fileMap.getString("filePhyName"));
            objFileModel.setFileSize(fileMap.getString("fileSize"));
            objFileModel.setFileType(fileMap.getString("fileType"));
            objFileModel.setPath(fileMap.getString("path"));
        }
        else {
            objFileModel = (ObjFileModel) o;
        }

        if (!realPath.equals(objFileModel.getPath())) {
            String realPathDir = DeFileUtils.getInstance().mkTargetPathDir(penObjDT.getObjTypeId(), penObjDT.getObjId());
            String sourceFile = objFileModel.getPath() + File.separatorChar + objFileModel.getFilePhyName();
            String targetFile = realPathDir + File.separatorChar + objFileModel.getFilePhyName();
            File f = new File(objFileModel.getPath());
            if (f.isFile()) {
                sourceFile = objFileModel.getPath();
                objFileModel.setPath(f.getParent());
                objFileModel.setFilePhyName(f.getName());
            }

            DeFileUtils.getInstance().move(sourceFile, targetFile);
            objFileModel.setPath(realPathDir);
        }

        penObjDT.setObjAtrVal(objFileModel.getFilePhyName());
        metaReqDao.insertPenObjDT(penObjDT, sessionInfo);

        penObjDT.setObjAtrVal(objFileModel.getFileLocName());
        penObjDT.setAtrValSeq(penObjDT.getAtrValSeq() + 1);
        metaReqDao.insertPenObjDT(penObjDT, sessionInfo);

        penObjDT.setObjAtrVal(objFileModel.getFileSize());
        penObjDT.setAtrValSeq(penObjDT.getAtrValSeq() + 1);
        metaReqDao.insertPenObjDT(penObjDT, sessionInfo);

        penObjDT.setObjAtrVal(objFileModel.getFileType());
        penObjDT.setAtrValSeq(penObjDT.getAtrValSeq() + 1);
        metaReqDao.insertPenObjDT(penObjDT, sessionInfo);

        penObjDT.setObjAtrVal(objFileModel.getPath());
        penObjDT.setAtrValSeq(penObjDT.getAtrValSeq() + 1);
        metaReqDao.insertPenObjDT(penObjDT, sessionInfo);
    }

    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void procCnctAtrV(PenObjDT penObjDT, SessionInfo sessionInfo) {
        Iterable<String> objAtrVals = Splitter.fixedLength(2000).split(penObjDT.getObjAtrVal());
        for (String objAtrVal : objAtrVals) {
            penObjDT.setObjAtrVal(objAtrVal);
            metaReqDao.insertPenObjDT(penObjDT, sessionInfo);
            penObjDT.setAtrValSeq(penObjDT.getAtrValSeq() + 1);
        }
    }
    
    @Override
    public Map<String, Object> getObjectInfo(String objTypeId, String objId) {
        if ("".equals(objTypeId)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }
        final Map<String, Object> resultMap = new HashMap<String, Object>();
        
        PenObjTypeM penObjTypeMEntity = metaDao.getObjTypeM(objTypeId);
        List<AtrInfoEntity> atrInfos = metaDao.getAtrInfo(objTypeId);
        final String objAtrValSelectStatment = QueryBuilder.getInstance().extendObjAtrValSelectStatment(atrInfos, false);
        final String objAtrValJoinStatment = QueryBuilder.getInstance().extendObjAtrValJoinStatment(atrInfos, false);
        
        List<Map<String, Object>> atrValInfos = metaReqDao.getAtrValInfo(objTypeId, objId, objAtrValSelectStatment, objAtrValJoinStatment);
        List<Map<String, List<CodeEntity>>> atrSqlSbstResults = AtrMSqlSbstMemoryMap.getInstance().getAtrMSqlSbstResultMap(atrInfos, atrValInfos, metaDao);
        LOG.debug("atrInfo : {}", atrInfos);
        
        resultMap.put("objTypeId", objTypeId);
        resultMap.put("objId", objId);
        resultMap.put("objTypeInfo", penObjTypeMEntity);
        resultMap.put("atrInfo", atrInfos);
        resultMap.put("atrValInfo", atrValInfos);
        resultMap.put("atrSqlSbstResult", atrSqlSbstResults);
        
        return resultMap;
    }
    
    @Override
    public PenObjM getObjM(String objTypeId, String objId) {
        return  metaReqDao.getObjM(objTypeId, objId);
    }

	/* (non-Javadoc)
	 * @see kr.co.penta.dataeye.spring.web.common.service.MetaReqService#removeAprv(java.lang.String, kr.co.penta.dataeye.spring.web.session.SessionInfo)
	 */
	@Override
	@Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void removeAprv(String aprvId, SessionInfo sessionInfo) {
		metaReqDao.removePenObjRTByAprvId(aprvId);
        metaReqDao.removePenObjDTByAprvId(aprvId);
        metaReqDao.removePenObjMTByAprvId(aprvId);
        metaReqDao.removePenAprvFByAprvId(aprvId);
        metaReqDao.removePenAprvDByAprvId(aprvId);
        metaReqDao.removePenAprvMByAprvId(aprvId);
	}
}
