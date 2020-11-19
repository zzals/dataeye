package kr.co.penta.dataeye.spring.web.common.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.meta.PenCdEntity;
import kr.co.penta.dataeye.common.entities.meta.PenObjM;
import kr.co.penta.dataeye.common.entities.meta.PenObjMKey;
import kr.co.penta.dataeye.common.entities.meta.PenRelTypeM;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

public interface MetaService {
    PenObjM getObjM(String objTypeId, String objId);
    Map<String, Object> getObjectInfo(String objTypeId, String objId);
    Map<String, Object> getSubObjectInfo(String pObjTypeId, String pObjId, String objTypeId);
    Map<String, Object> objRelTabs(String objTypeId, String mode);
    List<Map<String, Object>> objRelTabFilter(String objTypeId, String relTypeNm, String relDv, String metaRelCd);
    PenObjMKey saveObjectInfo(Map<String, Object> reqParam, SessionInfo sessionInfo);
    PenObjMKey deleteObjectInfo(String objTypeId, String objId, SessionInfo sessionInfo);
    PenObjMKey removeObjectInfo(String objTypeId, String objId, SessionInfo sessionInfo);
    void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq, SessionInfo sessionInfo);
    void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq, Integer atrValSeq, SessionInfo sessionInfo);
    void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq, Integer beginAtrValSeq, Integer endAtrValSeq, SessionInfo sessionInfo);
    void removePenObjByPath(String objTypeId, String pathObjTypeId, String pathObjId);
    
    void objectRelSave(Map<String, Object> reqParam, SessionInfo sessionInfo);
    void objectRelDirectSave(List<Map<String, Object>> reqParam, SessionInfo sessionInfo);
    
    List<Map<String, Object>> objectRelInfluence(Map<String, Object> reqParam);
    List<Map<String, Object>> objectRelInfluenceSub(Map<String, Object> reqParam);
    
    PenRelTypeM getObjRelTypeM(String relTypeId);
    Map<String, Object> uiviewRender(String mode, String objTypeId, String objId, String uiId, SessionInfo sessionInfo);
    List<Map<String, Object>> uiviewExecQuery(Map<String, Object> reqParam, SessionInfo sessionInfo);
    Map<String, Object> uiobjlistRender(String objTypeId, String uiId, SessionInfo sessionInfo);
    List<Map<String, Object>> uiobjlistExecQuery(Map<String, Object> reqParam, SessionInfo sessionInfo);
}
