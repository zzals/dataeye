package kr.co.penta.dataeye.spring.mybatis.dao;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.AtrInfoEntity;
import kr.co.penta.dataeye.common.entities.meta.PenCdEntity;
import kr.co.penta.dataeye.common.entities.meta.PenHstEntity;
import kr.co.penta.dataeye.common.entities.meta.PenObjD;
import kr.co.penta.dataeye.common.entities.meta.PenObjM;
import kr.co.penta.dataeye.common.entities.meta.PenObjR;
import kr.co.penta.dataeye.common.entities.meta.PenObjTypeM;
import kr.co.penta.dataeye.common.entities.meta.PenRHstEntity;
import kr.co.penta.dataeye.common.entities.meta.PenRelTypeM;
import kr.co.penta.dataeye.common.meta.util.AtrMSqlSbstMemoryMap.CodeEntity;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

public interface MetaDao {
    PenObjTypeM  getObjTypeM(String objTypeId);
    PenObjM getObjM(String objTypeId, String objId);
    List<PenObjM> getObjMsByPath(String objTypeId, String pathObjTypeId, String pathObjId);
    List<AtrInfoEntity> getAtrInfo(String objTypeId);
    List<Map<String, Object>> getAtrValInfo(String objTypeId, String objId, String objAtrValSelectStatment, String objAtrValJoinStatment);
    CamelMap getObjMCretInfo(String objTypeId, String objId);
    CamelMap getObjDCretInfo(String objTypeId, String objId);
    List<PenCdEntity> findAllCdByGroup();
    List<CodeEntity> getAtrMSqlSbstResult(DaoParam daoParam);
    List<PenRelTypeM> getRelTypeM(DaoParam daoParam);
    PenRelTypeM getRelTypeMOne(DaoParam daoParam);
    CamelMap getUiviewInfo(String uiId);
    
    PenObjR getObjROne(DaoParam daoParam);
    PenObjR getInheritanceObjR(String objTypeId, String objId);
    List<Map<String, Object>> getObjRelViewGroup(DaoParam daoParam);
    List<Map<String, Object>> objRelTabFilter(String objTypeId, String relTypeNm, String relDv, String metaRelCd);
    /*
     * meta insert
     */
    void insertPenObjM(PenObjM penObjM);
    void updatePenObjM(PenObjM penObjM);
    void insertPenObjD(PenObjD penObjD);
    void insertPenObjR(PenObjR penObjR);

    /*
     * logging
     */
    void loggingPenObjMH(PenHstEntity penHstEntity, SessionInfo sessionInfo);
    void loggingPenObjDH(PenHstEntity penHstEntity, SessionInfo sessionInfo);
    void loggingPenObjDH(PenHstEntity penHstEntity, SessionInfo sessionInfo, Integer atrIdSeq);
    void loggingPenObjDH(PenHstEntity penHstEntity, SessionInfo sessionInfo, Integer atrIdSeq, Integer atrValSeq);
    void loggingPenObjDH(PenHstEntity penHstEntity, SessionInfo sessionInfo, Integer atrIdSeq, Integer beginAtrIdSeq, Integer endAtrIdSeq);
    void loggingPenObjRH(PenRHstEntity penRHstEntity, SessionInfo sessionInfo);
    void loggingPenObjRHAll(PenRHstEntity penRHstEntity, SessionInfo sessionInfo);
    
    /*
     * logical delete
     */
    void updatePenObjMDelYn(String objTypeId, String objId, SessionInfo sessionInfo);
    void updatePenObjDDelYn(String objTypeId, String objId, SessionInfo sessionInfo);
    void updatePenObjRDelYn(String objTypeId, String objId, SessionInfo sessionInfo);
    void updatePenObjRDelYnRel(String relObjTypeId, String relObjId, SessionInfo sessionInfo);
    void updatePenObjRDelYnAll(String objTypeId, String objId, SessionInfo sessionInfo);
    
    /*
     * physical delete
     */
    void removePenObjM(DaoParam daoParam);
    void removePenObjM(String objTypeId, String objId);
    void removePenObjD(DaoParam daoParam);
    void removePenObjD(String objTypeId, String objId);
    void removePenObjR(String objTypeId, String objId);
    void removePenObjR(String objTypeId, String objId, String relObjTypeId, String relObjId);
    void removePenObjR(DaoParam daoParam);
    void removePenObjRByRel(String relObjTypeId, String relObjId);
    void removePenObjRRefPath(String pathObjTypeId, String pathObjId);
    void removePenObjRByRelRefPath(String pathObjTypeId, String pathObjId);
    void removePenObjRAll(String objTypeId, String objId);
    void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq);
    void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq, Integer atrValSeq);
    void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq, Integer beginAtrValSeq, Integer endAtrValSeq);
    
    void clearObjR(String objTypeId, String objId, String relTypeNm, String metaRelCd, String relDv, SessionInfo sessionInfo);
    
    List<Map<String, Object>> objectRelInfluence(DaoParam daoParam);
    List<Map<String, Object>> objectRelInfluenceSub(DaoParam daoParam);
}
