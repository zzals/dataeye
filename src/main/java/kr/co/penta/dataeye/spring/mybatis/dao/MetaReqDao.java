package kr.co.penta.dataeye.spring.mybatis.dao;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.*;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

import java.util.List;
import java.util.Map;

public interface MetaReqDao {
    CamelMap getObjMCretInfo(String objTypeId, String objId, String aprvId);
    PenObjMTKey insertPenObjMT(PenObjMT penObjMT, SessionInfo sessionInfo);
    void updateKeyPenObjMT(PenObjMT penObjMT, SessionInfo sessionInfo);
    void updatePrCdPenObjMT(PenObjMT penObjMT, SessionInfo sessionInfo);
    void updateKeyPenObjDT(PenObjDT penObjDT, SessionInfo sessionInfo);
    void updateObjKeyPenObjRT(PenObjRT penObjRT, SessionInfo sessionInfo);
    void updateRelKeyPenObjRT(PenObjRT penObjRT, SessionInfo sessionInfo);
    void updatePenObjMT(PenObjMT penObjMT, SessionInfo sessionInfo);
    void removePenObjMT(String objTypeId, String objId, String aprvId);
    void removePenObjMDT(String aprvId, String userId);
    void insertPenObjDT(PenObjDT penObjDT, SessionInfo sessionInfo);
    void removePenObjDT(String objTypeId, String objId, String aprvId);
    void insertPenObjRT(PenObjRT penObjRT, SessionInfo sessionInfo);
    void removePenObjRT(String objTypeId, String objId, String relObjTypeId, String relObjId, String aprvId);
    void approvePenObjMT(String aprvId);
    void approvePenObjDT(String aprvId);
    void approvePenObjRT(String aprvId);
    void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq, Integer beginAtrValSeq, Integer endAtrValSeq);
    void updatePenObjDT(String objTypeId, String objId, Integer atrIdSeq, Integer atrValSeq, String objAtrVal, String aprvId, SessionInfo sessionInfo);
    void removePenObjMTByAprvId(String aprvId);
    void removePenObjDTByAprvId(String aprvId);
    void removePenObjRTByAprvId(String aprvId);
    void removePenAprvMByAprvId(String aprvId);
    void removePenAprvDByAprvId(String aprvId);
    void removePenAprvFByAprvId(String aprvId);
    
    List<Map<String, Object>> getAtrValInfo(String objTypeId, String objId, String objAtrValSelectStatment, String objAtrValJoinStatment);
    PenObjM getObjM(String objTypeId, String objId);
}
