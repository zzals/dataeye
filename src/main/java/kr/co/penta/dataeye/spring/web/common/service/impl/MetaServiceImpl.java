package kr.co.penta.dataeye.spring.web.common.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Splitter;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.AtrInfoEntity;
import kr.co.penta.dataeye.common.entities.meta.PenCdEntity;
import kr.co.penta.dataeye.common.entities.meta.PenHstEntity;
import kr.co.penta.dataeye.common.entities.meta.PenObjD;
import kr.co.penta.dataeye.common.entities.meta.PenObjDT;
import kr.co.penta.dataeye.common.entities.meta.PenObjM;
import kr.co.penta.dataeye.common.entities.meta.PenObjMKey;
import kr.co.penta.dataeye.common.entities.meta.PenObjMT;
import kr.co.penta.dataeye.common.entities.meta.PenObjR;
import kr.co.penta.dataeye.common.entities.meta.PenObjRT;
import kr.co.penta.dataeye.common.entities.meta.PenObjReqD;
import kr.co.penta.dataeye.common.entities.meta.PenObjReqDKey;
import kr.co.penta.dataeye.common.entities.meta.PenObjTypeM;
import kr.co.penta.dataeye.common.entities.meta.PenRHstEntity;
import kr.co.penta.dataeye.common.entities.meta.PenRelTypeM;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.meta.util.AtrMSqlSbstMemoryMap;
import kr.co.penta.dataeye.common.meta.util.AtrMSqlSbstMemoryMap.CodeEntity;
import kr.co.penta.dataeye.common.meta.util.DeFileUtils;
import kr.co.penta.dataeye.common.meta.util.QueryBuilder;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.common.util.JSONUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.MetaDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.common.service.MetaService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class MetaServiceImpl implements MetaService {
    private static final Logger LOG = LoggerFactory.getLogger(MetaServiceImpl.class);
    
    @Autowired private MetaDao metaDao;
    @Autowired private CommonDao commonDao;
    
    @Override
    public PenObjM getObjM(String objTypeId, String objId) {
        return  metaDao.getObjM(objTypeId, objId);
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
        
        List<Map<String, Object>> atrValInfos = metaDao.getAtrValInfo(objTypeId, objId, objAtrValSelectStatment, objAtrValJoinStatment);
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
    public Map<String, Object> getSubObjectInfo(String pObjTypeId, String pObjId, String objTypeId) {
        if ("".equals(pObjTypeId) || "".equals(objTypeId)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }
        
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", pObjTypeId).put("relObjTypeId", objTypeId).put("relTypeCd", "IR");
        PenRelTypeM relTypeM = metaDao.getRelTypeMOne(daoParam);
        
        daoParam.clear();
        daoParam.put("objTypeId", pObjTypeId);
        daoParam.put("objId", pObjId);
        daoParam.put("relObjTypeId", objTypeId);
        daoParam.put("relTypeId", relTypeM.getRelTypeId());
        PenObjR penObjR = metaDao.getObjROne(daoParam);
        
        return getObjectInfo(objTypeId, penObjR==null?"":penObjR.getRelObjId());
    }

    @Override
    public Map<String, Object> objRelTabs(String objTypeId, String mode) {
        if ("".equals(objTypeId) || "".equals(mode)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }
        final Map<String, Object> resultMap = new HashMap<String, Object>();

        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("mode", mode);
        List<Map<String, Object>> tabInfoList = metaDao.getObjRelViewGroup(daoParam);
        resultMap.put("tabInfo", tabInfoList);

        return resultMap;
    }

    @Override
    public List<Map<String, Object>> objRelTabFilter(String objTypeId, String relTypeNm, String relDv, String metaRelCd) {
        return metaDao.objRelTabFilter(objTypeId, relTypeNm, relDv, metaRelCd);
    }

    /*
     * reqParam : {objInfo{penObjM:{}, penObjD:[]}, subObjInfo:{penObjD:[]}}
     * @see kr.co.penta.dataeye.spring.web.common.service.MetaService#saveObjectInfo(java.util.Map, kr.co.penta.dataeye.spring.web.session.SessionInfo)
     */
    @SuppressWarnings({ "unchecked" })
    @Override
    @Transactional
    public PenObjMKey saveObjectInfo(Map<String, Object> reqParam, SessionInfo sessionInfo) {
        String hstSeq = null;
        Map<String, Object> objInfo = (Map<String, Object>)reqParam.get("objInfo");
        Map<String, Object> subObjInfo = (Map<String, Object>)reqParam.get("subObjInfo");
        if (null == objInfo) {
            throw new ServiceRuntimeException("요청정보 오류");
        }
        
        /*
         * 이력 객체 생성
         */
        PenObjM penObjM = new PenObjM((Map<String, Object>)objInfo.get("penObjM"), sessionInfo);
        /* 기본객체 로깅 */
        PenObjTypeM penObjTypeMEntity = metaDao.getObjTypeM(penObjM.getObjTypeId());
        
        //승인객체인경우
        if ("Y".equals(penObjTypeMEntity.getStlmYn())) {
        	
        } else {
        //승인객체 아닌 경우
	        if ("Y".equals(penObjTypeMEntity.getHstYn())) {
	            hstSeq = loggingObjectInfo(penObjM.getObjTypeId(), penObjM.getObjId(), sessionInfo, null);
	        }
	        
	        /* 기본객체 저장 */
	        savePenObjM(penObjM);
	        
	        List<Map<String, Object>> penObjDs = (List<Map<String, Object>>)objInfo.get("penObjD");
	        savePenObjDs(penObjM, penObjDs, sessionInfo);
	        
	        /*
	         * 상속객체 logging
	         */
	        PenObjR penObjR = metaDao.getInheritanceObjR(penObjM.getObjTypeId(), penObjM.getObjId());
	        if (penObjR != null) {
	            //상속개체 logging
	            if ("Y".equals(penObjTypeMEntity.getHstYn())) {
	                loggingObjectInfo(penObjR.getRelObjTypeId(), penObjR.getRelObjId(), sessionInfo, hstSeq);
	            }
	        }
	        
	        /*
	         * 상속객체 등록
	         */
	        if (null != subObjInfo && !subObjInfo.isEmpty()) {
	            removePenObjRByIR(penObjM);
	            
	            /*
	             * 상속객체 등록
	             */
	            PenObjM subPenObjM = new PenObjM((Map<String, Object>)subObjInfo.get("penObjM"), sessionInfo);
	            savePenObjM(subPenObjM);
	            
	            List<Map<String, Object>> subPenObjDs = (List<Map<String, Object>>)subObjInfo.get("penObjD");
	            savePenObjDs(subPenObjM, subPenObjDs, sessionInfo);
	            
	            savePenObjRByIR(penObjM, subPenObjM, sessionInfo);
	        }
	        
	        //공통코드인 경우
	        if ("010502L".equals(penObjM.getObjTypeId())) {
	        	for(Map<String, Object> map : penObjDs) {
	        		Integer atrIdSeq = CastUtils.toInteger(map.get("ATR_ID_SEQ"));
	        		LOG.debug("{}", atrIdSeq);
	        	}
	        }
	        	
        }
        
        return new PenObjMKey(penObjM.getObjTypeId(), penObjM.getObjId());
    }
    
    @Override
    public PenObjMKey deleteObjectInfo(String objTypeId, String objId, SessionInfo sessionInfo) {
        if (DataEyeSettingsHolder.getInstance().getMetaConfig().getLogicalDeleteEnable()) {
            String hstSeq = null;
            /* 
             * logging
             */
            PenObjTypeM penObjTypeMEntity = metaDao.getObjTypeM(objTypeId);
            PenObjR penObjR = metaDao.getInheritanceObjR(objTypeId, objId);
            if ("Y".equals(penObjTypeMEntity.getHstYn())) {
                hstSeq = loggingObjectInfo(objTypeId, objId, sessionInfo, null);
                if (penObjR != null) {
                    //상속개체 logging
                    loggingObjectInfo(penObjR.getRelObjTypeId(), penObjR.getRelObjId(), sessionInfo, hstSeq);
                }
            }
            
            /*
             * 논리삭제 처리
             */
            metaDao.updatePenObjMDelYn(objTypeId, objId, sessionInfo);
            metaDao.updatePenObjDDelYn(objTypeId, objId, sessionInfo);
            metaDao.updatePenObjRDelYnAll(objTypeId, objId, sessionInfo);
            //상속개체 처리
            if (penObjR != null) {
                metaDao.updatePenObjMDelYn(penObjR.getRelObjTypeId(), penObjR.getRelObjId(), sessionInfo);
                metaDao.updatePenObjDDelYn(penObjR.getRelObjTypeId(), penObjR.getRelObjId(), sessionInfo);
            }
            
            return new PenObjMKey(objTypeId, objId);
        } else {
            return removeObjectInfo(objTypeId, objId, sessionInfo);
        }
    }

    @Override
    public PenObjMKey removeObjectInfo(String objTypeId, String objId, SessionInfo sessionInfo) {
        String hstSeq = null;
        /* 
         * logging
         */
        PenObjTypeM penObjTypeMEntity = metaDao.getObjTypeM(objTypeId);
        PenObjR penObjR = metaDao.getInheritanceObjR(objTypeId, objId);
        if ("Y".equals(penObjTypeMEntity.getHstYn())) {
            hstSeq = loggingObjectInfo(objTypeId, objId, sessionInfo, null);
            if (penObjR != null) {
                //상속개체 logging
                loggingObjectInfo(penObjR.getRelObjTypeId(), penObjR.getRelObjId(), sessionInfo, hstSeq);
            }
        }
        
        /*
         * 물리삭제 처리
         */
        metaDao.removePenObjM(objTypeId, objId);
        metaDao.removePenObjD(objTypeId, objId);
        metaDao.removePenObjRAll(objTypeId, objId);
        
        //상속개체 처리
        if (penObjR != null) {            
            metaDao.removePenObjM(penObjR.getRelObjTypeId(), penObjR.getRelObjId());
            metaDao.removePenObjD(penObjR.getRelObjTypeId(), penObjR.getRelObjId());
        }
        
        return new PenObjMKey(objTypeId, objId);
    }

    @Override
    public void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq, SessionInfo sessionInfo) {
        /* 
         * logging
         */
        PenObjTypeM penObjTypeMEntity = metaDao.getObjTypeM(objTypeId);
        if ("Y".equals(penObjTypeMEntity.getHstYn())) {
            PenHstEntity penHstEntity = new PenHstEntity();
            metaDao.loggingPenObjDH(penHstEntity, sessionInfo, atrIdSeq);
        }
        
        metaDao.removeObjDAtr(objTypeId, objId, atrIdSeq);
    }

    @Override
    public void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq, Integer atrValSeq, SessionInfo sessionInfo) {
        /* 
         * logging
         */
        PenObjTypeM penObjTypeMEntity = metaDao.getObjTypeM(objTypeId);
        if ("Y".equals(penObjTypeMEntity.getHstYn())) {
            PenHstEntity penHstEntity = new PenHstEntity();
            metaDao.loggingPenObjDH(penHstEntity, sessionInfo, atrIdSeq, atrValSeq);
        }

        metaDao.removeObjDAtr(objTypeId, objId, atrIdSeq, atrValSeq);
    }

    @Override
    public void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq, Integer beginAtrValSeq, Integer endAtrValSeq, SessionInfo sessionInfo) {
        /* 
         * logging
         */
        PenObjTypeM penObjTypeMEntity = metaDao.getObjTypeM(objTypeId);
        if ("Y".equals(penObjTypeMEntity.getHstYn())) {
            PenHstEntity penHstEntity = new PenHstEntity();
            metaDao.loggingPenObjDH(penHstEntity, sessionInfo, atrIdSeq, beginAtrValSeq, endAtrValSeq);
        }

        metaDao.removeObjDAtr(objTypeId, objId, atrIdSeq, beginAtrValSeq, endAtrValSeq);
    }
    
    @Override
    @Transactional
    public void removePenObjByPath(String objTypeId, String pathObjTypeId, String pathObjId) {
    	DaoParam daoParam = new DaoParam();
    	daoParam.put("objTypeId", objTypeId);
    	daoParam.put("pathObjTypeId", pathObjTypeId);
    	daoParam.put("pathObjId", pathObjId);
    	
    	metaDao.removePenObjM(daoParam);
    	metaDao.removePenObjD(daoParam);
    	metaDao.removePenObjRRefPath(pathObjTypeId, pathObjId);
    	metaDao.removePenObjRByRelRefPath(pathObjTypeId, pathObjId);
    }
    
    @Override
    public List<Map<String, Object>> objectRelInfluence(Map<String, Object> reqParam) {
        DaoParam daoParam = new DaoParam(reqParam);
        return metaDao.objectRelInfluence(daoParam);
    }
    
    @Override
    public List<Map<String, Object>> objectRelInfluenceSub(Map<String, Object> reqParam) {
        DaoParam daoParam = new DaoParam(reqParam);
        return metaDao.objectRelInfluenceSub(daoParam);
    }
    
    /*
     * 이력저장
     */
    public String loggingObjectInfo(String objTypeId, String objId, SessionInfo sessionInfo, String reqSeq) {
        if (objId == null || "".equals(objId)) return null;
        
        //기본정보 logging
        PenHstEntity penHstEntity = new PenHstEntity();
        penHstEntity.init(objTypeId, objId, reqSeq);
        metaDao.loggingPenObjMH(penHstEntity, sessionInfo);
        metaDao.loggingPenObjDH(penHstEntity, sessionInfo);
        
        PenRHstEntity penRHstEntity = new PenRHstEntity();
        penRHstEntity.allLoggingInit(penHstEntity.getHstSeq(), objTypeId, objId, penHstEntity.getReqSeq());
        metaDao.loggingPenObjRHAll(penRHstEntity, sessionInfo);
        
        return penHstEntity.getHstSeq();
    }
    
    public void loggingObjectInfoRel(PenRHstEntity penRHstEntity, SessionInfo sessionInfo) {
        metaDao.loggingPenObjRH(penRHstEntity, sessionInfo);
    }
    
    public void loggingObjectInfoAllRel(PenRHstEntity penRHstEntity, SessionInfo sessionInfo) {
        metaDao.loggingPenObjRHAll(penRHstEntity, sessionInfo);
    }
    
    private void tempRemoveObjectInfo(String objTypeId, String objId, boolean isRelRemove) {
        metaDao.removePenObjD(objTypeId, objId);
        metaDao.removePenObjM(objTypeId, objId);
        if (isRelRemove) {
            metaDao.removePenObjRAll(objTypeId, objId);
        }
    }
    
    private void procUiCompCdFLE(Map<String, Object> objDMap, PenObjD penObjD, SessionInfo sessionInfo) {
        String tempPath = DataEyeSettingsHolder.getInstance().getAttachFile().getUploadTempPath();
        String realPath = DataEyeSettingsHolder.getInstance().getAttachFile().getUploadReadPath();
        
        Map<String, Object> fileMap = (Map<String, Object>)objDMap.get("OBJ_ATR_VAL");
        String filePhyName = CastUtils.toString(fileMap.get("filePhyName"));
        String fileLocName = CastUtils.toString(fileMap.get("fileLocName"));
        String fileSize = CastUtils.toString(fileMap.get("fileSize"));
        String fileType = CastUtils.toString(fileMap.get("fileType"));
        String path = CastUtils.toString(fileMap.get("path"));
    
        if (!CastUtils.toString(fileMap.get("path")).startsWith(realPath)) {
            String realPathDir = DeFileUtils.getInstance().mkTargetPathDir(penObjD.getObjTypeId(), penObjD.getObjId());            
            String sourceFile = path+File.separatorChar+filePhyName;
            String targetFile = realPathDir+File.separatorChar+filePhyName;
            File f = new File(path);
            if (f.isFile()) {
                sourceFile = path;
                path = f.getParent();
                filePhyName = f.getName();
            }
            
            DeFileUtils.getInstance().move(sourceFile, targetFile);
           // DeFileUtils.getInstance().deleteDir(sourceFile);
            path = realPathDir;
        }
        
        penObjD.setObjAtrVal(filePhyName);
        metaDao.insertPenObjD(penObjD);
        
        penObjD.setObjAtrVal(fileLocName);
        penObjD.setAtrValSeq(penObjD.getAtrValSeq()+1);
        metaDao.insertPenObjD(penObjD);
        
        penObjD.setObjAtrVal(fileSize);
        penObjD.setAtrValSeq(penObjD.getAtrValSeq()+1);
        metaDao.insertPenObjD(penObjD);
        
        penObjD.setObjAtrVal(fileType);
        penObjD.setAtrValSeq(penObjD.getAtrValSeq()+1);
        metaDao.insertPenObjD(penObjD);
        
        penObjD.setObjAtrVal(path);
        penObjD.setAtrValSeq(penObjD.getAtrValSeq()+1);
        metaDao.insertPenObjD(penObjD);
    }
    
    private void procCnctAtrY(PenObjD penObjD, SessionInfo sessionInfo) {
        Iterable<String> objAtrVals =  Splitter.fixedLength(2000).split(CastUtils.toString(penObjD.getObjAtrVal()));
        for(String objAtrVal : objAtrVals) {
            penObjD.setObjAtrVal(objAtrVal);
            metaDao.insertPenObjD(penObjD);
            penObjD.setAtrValSeq(penObjD.getAtrValSeq()+1);
        }
    }
    
    private void savePenObjM(PenObjM penObjM) {
        if ("".equals(penObjM.getObjId())) {
            penObjM.genObjId();
            metaDao.insertPenObjM(penObjM);
        } else {
            CamelMap cretInfo = metaDao.getObjMCretInfo(penObjM.getObjTypeId(), penObjM.getObjId());
            if (cretInfo == null) {
                metaDao.insertPenObjM(penObjM);       
            } else {
                penObjM.setCretDt(cretInfo.getString("cretDt"));
                penObjM.setCretrId(cretInfo.getString("cretrId"));
                metaDao.updatePenObjM(penObjM);
            }
        }
    }
    
    private void savePenObjDs(PenObjM penObjM, List<Map<String, Object>> penObjDs, SessionInfo sessionInfo) {
        metaDao.removePenObjD(penObjM.getObjTypeId(), penObjM.getObjId());
        
        for(Map<String, Object> map : penObjDs) {
            CamelMap camelMap = new CamelMap(map);
            PenObjD penObjD = new PenObjD(penObjM, camelMap);
            
            if ("FLE".equals(penObjD.getUiCompCd())) {
                procUiCompCdFLE(camelMap, penObjD, sessionInfo);
            } else {
                if ("Y".equals(penObjD.getCnctAtrYn())) {
                    procCnctAtrY(penObjD, sessionInfo);
                } else {
                    metaDao.insertPenObjD(penObjD);
                }
            }
        }
    }
    
    private void removePenObjRByIR(PenObjM penObjM) {
        DaoParam daoParam = new DaoParam();
        daoParam.clear();
        daoParam.put("objTypeId", penObjM.getObjTypeId()).put("relTypeCd", "IR");
        List<PenRelTypeM> relTypeMs = metaDao.getRelTypeM(daoParam);
        List<String> l = new ArrayList<>();
        for(PenRelTypeM relTypeM: relTypeMs) {
            l.add(relTypeM.getRelTypeId());
        }
        
        /*
         * 기존 상속객체 삭제
         */
        daoParam.clear();
        daoParam.put("objTypeId", penObjM.getObjTypeId());
        daoParam.put("objId", penObjM.getObjId());
        daoParam.put("relTypeIds", l);
        metaDao.removePenObjR(daoParam);
    }
    
    private void savePenObjRByIR(PenObjM penObjM, PenObjM subPenObjM, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", penObjM.getObjTypeId()).put("relObjTypeId", subPenObjM.getObjTypeId()).put("relTypeCd", "IR");;
        PenRelTypeM relTypeM = metaDao.getRelTypeMOne(daoParam);
        
        PenObjR penObjR = new PenObjR(sessionInfo);
        penObjR.setObjTypeId(penObjM.getObjTypeId());
        penObjR.setObjId(penObjM.getObjId());
        penObjR.setRelObjTypeId(subPenObjM.getObjTypeId());
        penObjR.setRelObjId(subPenObjM.getObjId());
        penObjR.setCretDt(subPenObjM.getCretDt());
        penObjR.setCretrId(subPenObjM.getCretrId());
        penObjR.setRelTypeId(relTypeM.getRelTypeId());
        
        metaDao.insertPenObjR(penObjR);
    }

    @Override
    @Transactional
    public void objectRelSave(Map<String, Object> reqParam, SessionInfo sessionInfo) {
        String relDv = CastUtils.toString(reqParam.get("relDv"));
        String relTypeNm = CastUtils.toString(reqParam.get("relTypeNm"));
        String metaRelCd = CastUtils.toString(reqParam.get("metaRelCd"));
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        
        metaDao.clearObjR(objTypeId, objId, relTypeNm, metaRelCd, relDv, sessionInfo);
        List<Map<String, Object>> datas = (List<Map<String, Object>>)reqParam.get("data");
        for(Map<String, Object> map : datas) {
            CamelMap penObjRMap = new CamelMap(map);
            PenObjR penObjR = new PenObjR(penObjRMap, sessionInfo);
            metaDao.insertPenObjR(penObjR);
        }
    }

    @Override
    @Transactional
    public void objectRelDirectSave(List<Map<String, Object>> reqParam, SessionInfo sessionInfo) {
        for(Map<String, Object> map : reqParam) {
            CamelMap penObjRMap = new CamelMap(map);
            PenObjR penObjR = new PenObjR(penObjRMap, sessionInfo);
            metaDao.removePenObjR(penObjR.getObjTypeId(), penObjR.getObjId(), penObjR.getRelObjTypeId(), penObjR.getRelObjId());
            
            DaoParam daoParam = new DaoParam();
            daoParam.put("objTypeId", penObjR.getObjTypeId()).put("relObjTypeId", penObjR.getRelObjTypeId());
            PenRelTypeM penRelTypeM = metaDao.getRelTypeMOne(daoParam);
            penObjR.setRelTypeId(penRelTypeM.getRelTypeId());
            penObjR.setObjRelAnalsCd("MC");

            metaDao.insertPenObjR(penObjR);
        }
    }

    @Override
    public PenRelTypeM getObjRelTypeM(String relTypeId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("relTypeId", relTypeId);
        return metaDao.getRelTypeMOne(daoParam);
    }    

    @Override
    public Map<String, Object> uiviewRender(String mode, String objTypeId, String objId, String uiId, SessionInfo sessionInfo) {
        Map<String, Object> rtMap = new HashMap<>();
        CamelMap uiInfo = metaDao.getUiviewInfo(uiId);
        String jsonStr = uiInfo.getString("confSbst");
        Map<String, Object> confSbst = JSONUtils.getInstance().jsonToMap(jsonStr);
        
        
        Set<String> keySet = confSbst.keySet();
        for(String key : keySet) {
            if (key.startsWith("CONF_JS")) {
                rtMap.put(key, CastUtils.toString(confSbst.get(key)));
                break;
            }
        }
        
        return rtMap;
    }

    @Override
    public List<Map<String, Object>> uiviewExecQuery(Map<String, Object> reqParam, SessionInfo sessionInfo) {
        List<Map<String, Object>> rsp = new ArrayList<>();
        String uiId = CastUtils.toString(reqParam.get("uiId"));
        String confSqlKey = CastUtils.toString(reqParam.get("confSqlKey"));
        
        CamelMap uiInfo = metaDao.getUiviewInfo(uiId);
        String jsonStr = uiInfo.getString("confSbst");
        Map<String, Object> confSbst = JSONUtils.getInstance().jsonToMap(jsonStr);
                
        Set<String> keySet = confSbst.keySet();
        for(String key : keySet) {
            if (key.equals(confSqlKey)) {
                DaoParam daoParam = new DaoParam(reqParam);
                daoParam.put("userId", sessionInfo.getUserId());
                
//                daoParam.put("metaRelCd", "FT");
//                rsp = commonDao.selectList("metacore.getRelObjLineageView", daoParam);
                String sql = CastUtils.toString(confSbst.get(key));
                rsp = commonDao.dynamicSqlList(daoParam, sql);
                break;
            }
        }
        
        return rsp;
    }
    
    @Override
    public Map<String, Object> uiobjlistRender(String objTypeId, String uiId, SessionInfo sessionInfo) {
        Map<String, Object> rtMap = new HashMap<>();
        CamelMap uiInfo = metaDao.getUiviewInfo(uiId);
        String jsonStr = uiInfo.getString("confSbst");
        Map<String, Object> confSbst = JSONUtils.getInstance().jsonToMap(jsonStr);
        
        Set<String> keySet = confSbst.keySet();
        for(String key : keySet) {
            if (key.startsWith("CONF_JS")) {
                rtMap.put(key, CastUtils.toString(confSbst.get(key)));
                break;
            }
        }
        
        return rtMap;
    }

    @Override
    public List<Map<String, Object>> uiobjlistExecQuery(Map<String, Object> reqParam, SessionInfo sessionInfo) {
        List<Map<String, Object>> rsp = new ArrayList<>();
        String uiId = CastUtils.toString(reqParam.get("uiId"));
        String confSqlKey = CastUtils.toString(reqParam.get("confSqlKey"));
        String searchKey = CastUtils.toString(reqParam.get("searchKey"));
        String searchValue = CastUtils.toString(reqParam.get("searchValue"));
        
        CamelMap uiInfo = metaDao.getUiviewInfo(uiId);
        String jsonStr = uiInfo.getString("confSbst");
        Map<String, Object> confSbst = JSONUtils.getInstance().jsonToMap(jsonStr);
                
        Set<String> keySet = confSbst.keySet();
        for(String key : keySet) {
            if (key.equals(confSqlKey)) {
                DaoParam daoParam = new DaoParam(reqParam);
                daoParam.put("userId", sessionInfo.getUserId());
                
                String sql = CastUtils.toString(confSbst.get(key));
                if (!"".equals(searchValue)) {
                	StringBuilder sb = new StringBuilder();
                	sb.append("SELECT T101.* FROM (").append("\n");
                	sb.append("\t").append(sql).append("\n");
                	sb.append(") T101").append("\n");
                	sb.append("WHERE T101.").append(searchKey).append(" LIKE ").append("'%").append(searchValue).append("%'");
                	
                	sql = sb.toString();
                }
                rsp = commonDao.dynamicSqlList(daoParam, sql);
                break;
            }
        }
        
        return rsp;
    }
}
