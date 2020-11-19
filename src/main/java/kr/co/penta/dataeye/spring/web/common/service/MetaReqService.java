package kr.co.penta.dataeye.spring.web.common.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.MetaObjectReqInfo;
import kr.co.penta.dataeye.common.entities.meta.PenObjM;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

public interface MetaReqService {
	CamelMap saveObjAprv(Map<String, Object> reqParam, SessionInfo sessionInfo) throws Exception;
    CamelMap saveMultiObjAprv(Map<String, Object> reqParam, SessionInfo sessionInfo) throws Exception;
    void saveObjInfo(MetaObjectReqInfo metaObjectReqInfo, SessionInfo sessionInfo);
    void removePenObjMDT(String aprvId, SessionInfo sessionInfo);
    void removePenObjRT(String objTypeId, String objId, String relObjTypeId, String relObjId, String aprvId);
    void removePenObjRTs(List<CamelMap> penObjRTs, SessionInfo sessionInfo);
    void insertPenObjRT(String objTypeId, String objId, String relObjTypeId, String relObjId, String aprvId, SessionInfo sessionInfo);
    void insertPenObjRTs(List<CamelMap> penObjRTs, SessionInfo sessionInfo);
    void approve(String aprvId);
    void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq, Integer beginAtrValSeq, Integer endAtrValSeq);
    
    Map<String, Object> getObjectInfo(String objTypeId, String objId);
    PenObjM getObjM(String objTypeId, String objId);
    void removeAprv(String aprvId, SessionInfo sessionInfo);
}
