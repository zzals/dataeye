package kr.co.penta.dataeye.spring.mybatis.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.AtrInfoEntity;
import kr.co.penta.dataeye.common.entities.meta.PenCdEntity;
import kr.co.penta.dataeye.common.entities.meta.PenHstEntity;
import kr.co.penta.dataeye.common.entities.meta.PenObjD;
import kr.co.penta.dataeye.common.entities.meta.PenObjDT;
import kr.co.penta.dataeye.common.entities.meta.PenObjM;
import kr.co.penta.dataeye.common.entities.meta.PenObjMT;
import kr.co.penta.dataeye.common.entities.meta.PenObjR;
import kr.co.penta.dataeye.common.entities.meta.PenObjRT;
import kr.co.penta.dataeye.common.entities.meta.PenObjReqD;
import kr.co.penta.dataeye.common.entities.meta.PenObjTypeM;
import kr.co.penta.dataeye.common.entities.meta.PenRHstEntity;
import kr.co.penta.dataeye.common.entities.meta.PenRelTypeM;
import kr.co.penta.dataeye.common.meta.util.AtrMSqlSbstMemoryMap.CodeEntity;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.common.util.XssScriptConvertor;
import kr.co.penta.dataeye.spring.mybatis.dao.MetaDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.mybatis.dao.support.DataEyeDaoSupport;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

@Repository
public class MetaDaoImpl extends DataEyeDaoSupport implements MetaDao {
    private static final Logger LOG = LoggerFactory.getLogger(MetaDaoImpl.class);
    
    @Override
    public PenObjTypeM  getObjTypeM(String objTypeId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId);
        
        return sqlSession.selectOne("metacore.getObjTypeM", daoParam);
    }
    
    @Override
    public PenObjM getObjM(String objTypeId, String objId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId);
        
        return sqlSession.selectOne("metacore.getObjM", daoParam);
    }
    
    @Override
    public List<PenObjM> getObjMsByPath(String objTypeId, String pathObjTypeId, String pathObjId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("pathObjTypeId", pathObjTypeId).put("pathObjId", pathObjId);
        
        return sqlSession.selectList("metacore.getObjM", daoParam);
    }
    
    @Override
	public List<AtrInfoEntity> getAtrInfo(String objTypeId) {
	    DaoParam daoParam = new DaoParam();
	    daoParam.put("objTypeId", objTypeId);
	    
	    return sqlSession.selectList("metacore.getAtrInfo", daoParam);
	}

    @Override
    public List<Map<String, Object>> getAtrValInfo(String objTypeId, String objId, String objAtrValSelectStatment, String objAtrValJoinStatment) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId);
        daoParam.put("objAtrValSelectStatment", objAtrValSelectStatment).put("objAtrValJoinStatment", objAtrValJoinStatment);
        
        return sqlSession.selectList("metacore.getAtrValInfo", daoParam);
    }

    @Override
    public CamelMap getObjMCretInfo(String objTypeId, String objId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId);

        return sqlSession.selectOne("metacore.getObjMCretInfo", daoParam);
    }
    
    @Override
    public CamelMap getObjDCretInfo(String objTypeId, String objId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId);

        return sqlSession.selectOne("metacore.getObjDCretInfo", daoParam);
    }
    
    @Override
    public List<CodeEntity> getAtrMSqlSbstResult(DaoParam daoParam) {
        return sqlSession.selectList("metacore.getAtrMSqlSbstResult", daoParam);
    }
    
    @Override
    public List<PenRelTypeM> getRelTypeM(DaoParam daoParam) {
        return sqlSession.selectList("metacore.getRelTypeM", daoParam);
    }
    
    @Override
    public PenRelTypeM getRelTypeMOne(DaoParam daoParam) {
        return sqlSession.selectOne("metacore.getRelTypeM", daoParam);
    }
    
    @Override
    public CamelMap getUiviewInfo(String uiId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("uiId", uiId);
        return sqlSession.selectOne("metacore.getObjUiInfo", daoParam);
    }
    
    @Override
    public PenObjR getObjROne(DaoParam daoParam) {
        return sqlSession.selectOne("metacore.getObjR", daoParam);
    }

    @Override
    public PenObjR getInheritanceObjR(String objTypeId, String objId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId);
        return sqlSession.selectOne("metacore.getInheritanceObjR", daoParam);
    }
    
    @Override
    public List<Map<String, Object>> getObjRelViewGroup(DaoParam daoParam) {
        return sqlSession.selectList("metacore.getObjRelViewGroup", daoParam);
    }
    
    @Override
    public List<Map<String, Object>> objRelTabFilter(String objTypeId, String relTypeNm, String relDv, String metaRelCd) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("relTypeNm", relTypeNm).put("relDv", relDv).put("metaRelCd", metaRelCd);
        return sqlSession.selectList("metacore.getRelObjTypeFilter", daoParam);
    }
    
    @Override
    public List<PenCdEntity> findAllCdByGroup() {
        return sqlSession.selectList("common.findAllCdByGroup");
    }
    
    @Override
    public void insertPenObjM(PenObjM penObjM) {
        if (DataEyeSettingsHolder.getInstance().getConfig().getXssFilterEnable()) {
            XssScriptConvertor.getInstance().convertPenObjM(penObjM);
        }
        sqlSession.insert("PEN_OBJ_M.insert", penObjM);
    }
    
    @Override
    public void updatePenObjM(PenObjM penObjM) {
        if (DataEyeSettingsHolder.getInstance().getConfig().getXssFilterEnable()) {
            XssScriptConvertor.getInstance().convertPenObjM(penObjM);
        }
        sqlSession.update("PEN_OBJ_M.update", penObjM);
    }
    
    @Override
    public void insertPenObjD(PenObjD penObjD) {
        if (DataEyeSettingsHolder.getInstance().getConfig().getXssFilterEnable()) {
            XssScriptConvertor.getInstance().convertPenObjD(penObjD);
        }
        sqlSession.insert("PEN_OBJ_D.insert", penObjD);
    }
    
    @Override
    public void insertPenObjR(PenObjR penObjR) {
        if (DataEyeSettingsHolder.getInstance().getConfig().getXssFilterEnable()) {
            XssScriptConvertor.getInstance().convertPenObjR(penObjR);
        }
        sqlSession.insert("PEN_OBJ_R.insert", penObjR);
    }
    
    @Override
    public void loggingPenObjMH(PenHstEntity penHstEntity, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.putAll(penHstEntity);
        sqlSession.insert("PEN_OBJ_M_H.insert", daoParam);
    }
    
    @Override
    public void loggingPenObjDH(PenHstEntity penHstEntity, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.putAll(penHstEntity);
        sqlSession.insert("PEN_OBJ_D_H.insert", daoParam);
    }
    
    @Override
    public void loggingPenObjDH(PenHstEntity penHstEntity, SessionInfo sessionInfo, Integer atrIdSeq) {
        DaoParam daoParam = new DaoParam(penHstEntity, sessionInfo);
        daoParam.put("atrIdSeq", atrIdSeq);
        sqlSession.insert("PEN_OBJ_D_H.insert", daoParam);
    }
    
    @Override
    public void loggingPenObjDH(PenHstEntity penHstEntity, SessionInfo sessionInfo, Integer atrIdSeq, Integer atrValSeq) {
        DaoParam daoParam = new DaoParam(penHstEntity, sessionInfo);
        daoParam.put("atrIdSeq", atrIdSeq).put("atrValSeq", atrValSeq);
        sqlSession.insert("PEN_OBJ_D_H.insert", daoParam);
    }
    
    @Override
    public void loggingPenObjDH(PenHstEntity penHstEntity, SessionInfo sessionInfo, Integer atrIdSeq, Integer beginAtrValSeq, Integer endAtrIdSeq) {
        DaoParam daoParam = new DaoParam(penHstEntity, sessionInfo);
        daoParam.put("atrIdSeq", atrIdSeq).put("beginAtrValSeq", beginAtrValSeq).put("endAtrIdSeq", endAtrIdSeq);
        sqlSession.insert("PEN_OBJ_D_H.insert", daoParam);
    }
    
    @Override
    public void loggingPenObjRH(PenRHstEntity penRHstEntity, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.putAll(penRHstEntity);
        sqlSession.insert("PEN_OBJ_R_H.insert", daoParam);
    }
    
    @Override
    public void loggingPenObjRHAll(PenRHstEntity penRHstEntity, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.putAll(penRHstEntity);
        sqlSession.insert("PEN_OBJ_R_H.insertAll", daoParam);
    }
    
    @Override
    public void updatePenObjMDelYn(String objTypeId, String objId, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.put("objTypeId", objTypeId).put("objId", objId);
        sqlSession.update("PEN_OBJ_M.updateDelYn", daoParam);
    }
    
    @Override
    public void updatePenObjDDelYn(String objTypeId, String objId, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.put("objTypeId", objTypeId).put("objId", objId);
        sqlSession.update("PEN_OBJ_D.updateDelYn", daoParam);
    }
    
    @Override
    public void updatePenObjRDelYn(String objTypeId, String objId, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.put("objTypeId", objTypeId).put("objId", objId);
        sqlSession.update("PEN_OBJ_R.updateDelYn", daoParam);
    }
    
    @Override
    public void updatePenObjRDelYnRel(String relObjTypeId, String relObjId, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.put("relObjTypeId", relObjTypeId).put("relObjId", relObjId);
        sqlSession.update("PEN_OBJ_R.updateDelYnRel", daoParam);
    }
    
    @Override
    public void updatePenObjRDelYnAll(String objTypeId, String objId, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.put("objTypeId", objTypeId).put("objId", objId);
        sqlSession.update("PEN_OBJ_R.updateDelYnAll", daoParam);
    }
    
    @Override
    public void removePenObjM(String objTypeId, String objId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId);
        sqlSession.delete("PEN_OBJ_M.remove", daoParam);
    }
    
    @Override
    public void removePenObjM(DaoParam daoParam) {
        sqlSession.delete("PEN_OBJ_M.remove", daoParam);
    }
    
    @Override
    public void removePenObjD(String objTypeId, String objId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId);
        sqlSession.delete("PEN_OBJ_D.remove", daoParam);
    }
    
    @Override
    public void removePenObjD(DaoParam daoParam) {
        sqlSession.delete("PEN_OBJ_D.remove", daoParam);
    }
    
    @Override
    public void removePenObjR(String objTypeId, String objId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId);
        sqlSession.delete("PEN_OBJ_R.remove", daoParam);
    }
    
    @Override
    public void removePenObjR(String objTypeId, String objId, String relObjTypeId, String relObjId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId).put("relObjTypeId", relObjTypeId).put("relObjId", relObjId);
        sqlSession.delete("PEN_OBJ_R.remove", daoParam);
    }
    
    @Override
    public void removePenObjR(DaoParam daoParam) {
        sqlSession.delete("PEN_OBJ_R.remove", daoParam);
    }
    
    @Override
    public void removePenObjRByRel(String relObjTypeId, String relObjId) {
    	DaoParam daoParam = new DaoParam();
    	daoParam.put("relObjTypeId", relObjTypeId).put("relObjId", relObjId);
        sqlSession.delete("PEN_OBJ_R.removeByRel", daoParam);
    }
    
    @Override
    public void  removePenObjRRefPath(String pathObjTypeId, String pathObjId) {
    	DaoParam daoParam = new DaoParam();
    	daoParam.put("pathObjTypeId", pathObjTypeId).put("pathObjId", pathObjId);
        sqlSession.delete("PEN_OBJ_R.removeRefPath", daoParam);
    }
    
    @Override
    public void  removePenObjRByRelRefPath(String pathObjTypeId, String pathObjId) {
    	DaoParam daoParam = new DaoParam();
    	daoParam.put("pathObjTypeId", pathObjTypeId).put("pathObjId", pathObjId);
        sqlSession.delete("PEN_OBJ_R.removeByRelRefPath", daoParam);
    }    
    
    @Override
    public void removePenObjRAll(String objTypeId, String objId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId).put("relObjTypeId", objTypeId).put("relObjId", objId);
        sqlSession.delete("PEN_OBJ_R.removeAll", daoParam);
    }
    
    @Override
    public void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId).put("atrIdSeq", atrIdSeq);
        sqlSession.delete("PEN_OBJ_D.removeObjDAtr", daoParam);
    }
    
    @Override
    public void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq, Integer atrValSeq) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId).put("atrIdSeq", atrIdSeq).put("atrValSeq", atrValSeq);
        sqlSession.delete("PEN_OBJ_D.removeObjDAtr", daoParam);
    }
    
    @Override
    public void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq, Integer beginAtrValSeq, Integer endAtrValSeq) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId).put("atrIdSeq", atrIdSeq).put("beginAtrValSeq", beginAtrValSeq).put("endAtrValSeq", endAtrValSeq);
        sqlSession.delete("PEN_OBJ_D.removeObjDAtrRange", daoParam);
    }

    @Override
    public void clearObjR(String objTypeId, String objId, String relTypeNm, String metaRelCd, String relDv, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId).put("relTypeNm", relTypeNm).put("metaRelCd", metaRelCd).put("relDv", relDv);
        sqlSession.delete("PEN_OBJ_R.clearObjR", daoParam);
    }
    
    @Override
    public List<Map<String, Object>> objectRelInfluence(DaoParam daoParam) {
        return sqlSession.selectList("metacore.objInfluence", daoParam);
    }
    
    @Override
    public List<Map<String, Object>> objectRelInfluenceSub(DaoParam daoParam) {
        return sqlSession.selectList("metacore.objInfluenceSub", daoParam);
    }
}
