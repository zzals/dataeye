package kr.co.penta.dataeye.spring.web.admin.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.common.entities.meta.PenHstEntity;
import kr.co.penta.dataeye.common.entities.meta.PenObjR;
import kr.co.penta.dataeye.common.entities.meta.PenObjTypeM;
import kr.co.penta.dataeye.common.entities.meta.PenRHstEntity;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.MetaDao;
import kr.co.penta.dataeye.spring.web.admin.service.AdminObjService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class AdminObjServiceImpl implements AdminObjService {

	@Autowired
	private MetaDao metaDao;

    public void delete(List<Map<String, Object>> datas, SessionInfo sessionInfo) {
        String preObjTypeId = "";
        PenObjTypeM penObjTypeMEntity = null;
        PenObjR penObjR = null;
        
        for(Map<String, Object> objKeyMap : datas) {
            String objTypeId = CastUtils.toString(objKeyMap.get("OBJ_TYPE_ID"));
            String objId = CastUtils.toString(objKeyMap.get("OBJ_ID"));
            String hstSeq = null;
            /* 
             * logging
             */
            if (!preObjTypeId.equals(objTypeId)) {
                penObjTypeMEntity = metaDao.getObjTypeM(objTypeId);
                penObjR = metaDao.getInheritanceObjR(objTypeId, objId);
            }
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
            
            preObjTypeId = objTypeId;
        }
    }

    @Override
    public void remove(List<Map<String, Object>> datas, SessionInfo sessionInfo) {
        String preObjTypeId = "";
        PenObjTypeM penObjTypeMEntity = null;
        PenObjR penObjR = null;
        
        for(Map<String, Object> objKeyMap : datas) {
            String objTypeId = CastUtils.toString(objKeyMap.get("OBJ_TYPE_ID"));
            String objId = CastUtils.toString(objKeyMap.get("OBJ_ID"));
            String hstSeq = null;
            /* 
             * logging
             */
            if (!preObjTypeId.equals(objTypeId)) {
                penObjTypeMEntity = metaDao.getObjTypeM(objTypeId);
                penObjR = metaDao.getInheritanceObjR(objTypeId, objId);
            }
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
            
            preObjTypeId = objTypeId;
        }
    }
    
    /*
     * 이력저장
     */
    private String loggingObjectInfo(String objTypeId, String objId, SessionInfo sessionInfo, String reqSeq) {
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
}
