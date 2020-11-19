package kr.co.penta.dataeye.spring.mybatis.dao.impl;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.*;
import kr.co.penta.dataeye.spring.mybatis.dao.MetaReqDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.mybatis.dao.support.DataEyeDaoSupport;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.nio.channels.SeekableByteChannel;
import java.util.List;
import java.util.Map;

@Repository
public class MetaReqDaoImpl extends DataEyeDaoSupport implements MetaReqDao {
    private static final Logger LOG = LoggerFactory.getLogger(MetaReqDaoImpl.class);

    @Override
    public CamelMap getObjMCretInfo(String objTypeId, String objId, String aprvId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId).put("aprvId", aprvId);

        return sqlSession.selectOne("metacore.getObjMReqCretInfo", daoParam);
    }

    @Override
    public PenObjMTKey insertPenObjMT(PenObjMT penObjMT, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(penObjMT, sessionInfo);
        sqlSession.insert("PEN_OBJ_M_T.insert", daoParam);

        return new PenObjMTKey(penObjMT.getObjTypeId(), penObjMT.getObjId(), penObjMT.getAprvId());
    }

    @Override
    public void updateKeyPenObjMT(PenObjMT penObjMT, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(penObjMT, sessionInfo);
        sqlSession.update("PEN_OBJ_M_T.updateKey", daoParam);
    }

    @Override
    public void updatePrCdPenObjMT(PenObjMT penObjMT, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(penObjMT, sessionInfo);
        sqlSession.update("PEN_OBJ_M_T.updatePrCd", daoParam);
    }

    @Override
    public void updateKeyPenObjDT(PenObjDT penObjDT, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(penObjDT, sessionInfo);
        sqlSession.update("PEN_OBJ_D_T.updateKey", daoParam);
    }

    @Override
    public void updateObjKeyPenObjRT(PenObjRT penObjRT, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(penObjRT, sessionInfo);
        sqlSession.update("PEN_OBJ_R_T.updateObjIdKey", daoParam);
    }

    @Override
    public void updateRelKeyPenObjRT(PenObjRT penObjRT, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(penObjRT, sessionInfo);
        sqlSession.update("PEN_OBJ_R_T.updateRelObjIdKey", daoParam);
    }

    @Override
    public void updatePenObjMT(PenObjMT penObjMT, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(penObjMT, sessionInfo);
        sqlSession.update("PEN_OBJ_M_T.update", daoParam);
    }

    @Override
    public void removePenObjMT(String objTypeId, String objId, String aprvId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId).put("aprvId", aprvId);

        sqlSession.delete("PEN_OBJ_M_T.delete", daoParam);
    }

    @Override
    public void removePenObjMDT(String aprvId, String userId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("aprvId", aprvId).put("userId", userId);

        sqlSession.delete("PEN_OBJ_M_T.deleteAll", daoParam);
    }

    @Override
    public void insertPenObjDT(PenObjDT penObjDT, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(penObjDT, sessionInfo);
        sqlSession.insert("PEN_OBJ_D_T.insert", daoParam);
    }

    @Override
    public void removePenObjDT(String objTypeId, String objId, String aprvId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId).put("aprvId", aprvId);

        sqlSession.delete("PEN_OBJ_D_T.delete", daoParam);
    }

    @Override
    public void insertPenObjRT(PenObjRT penObjRT, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(penObjRT, sessionInfo);
        sqlSession.insert("PEN_OBJ_R_T.insert", daoParam);
    }

    @Override
    public void removePenObjRT(String objTypeId, String objId, String relObjTypeId, String relObjId, String aprvId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId)
                .put("objId", objTypeId)
                .put("relObjTypeId", relObjTypeId)
                .put("relObjId", relObjId)
                .put("aprvId", aprvId);

        sqlSession.delete("PEN_OBJ_R_T.delete", daoParam);
    }

    @Override
    public void approvePenObjMT(String aprvId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("aprvId", aprvId);

        sqlSession.insert("PEN_OBJ_M_T.approve", daoParam);
    }

    @Override
    public void approvePenObjDT(String aprvId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("aprvId", aprvId);

        sqlSession.insert("PEN_OBJ_D_T.approve", daoParam);
    }

    @Override
    public void approvePenObjRT(String aprvId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("aprvId", aprvId);

        sqlSession.insert("PEN_OBJ_R_T.approve", daoParam);
    }

    @Override
    public void removeObjDAtr(String objTypeId, String objId, Integer atrIdSeq, Integer beginAtrValSeq, Integer endAtrValSeq) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId)
                .put("objId", objId)
                .put("atrIdSeq", atrIdSeq)
                .put("beginAtrValSeq", beginAtrValSeq)
                .put("endAtrValSeq", endAtrValSeq);

        sqlSession.delete("PEN_OBJ_D_T.removeObjDAtrRange", daoParam);
    }

    @Override
    public void updatePenObjDT(String objTypeId, String objId, Integer atrIdSeq, Integer atrValSeq, String objAtrVal, String aprvId, SessionInfo sessionInfo) {
        PenObjDT penObjDT = new PenObjDT();
        penObjDT.setObjTypeId(objTypeId);
        penObjDT.setObjId(objId);
        penObjDT.setAtrIdSeq(atrIdSeq);
        penObjDT.setAtrValSeq(atrValSeq);
        penObjDT.setObjAtrVal(objAtrVal);
        penObjDT.setAprvId(aprvId);

        DaoParam daoParam = new DaoParam(penObjDT, sessionInfo);
        sqlSession.update("PEN_OBJ_D_T.update", daoParam);
    }

	/* (non-Javadoc)
	 * @see kr.co.penta.dataeye.spring.mybatis.dao.MetaReqDao#removePenObjMTByAprvId(java.lang.String)
	 */
	@Override
	public void removePenObjMTByAprvId(String aprvId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("aprvId", aprvId);
        sqlSession.update("PEN_OBJ_M_T.deleteByAprvId", daoParam);
	}

	/* (non-Javadoc)
	 * @see kr.co.penta.dataeye.spring.mybatis.dao.MetaReqDao#removePenObjDTByAprvId(java.lang.String)
	 */
	@Override
	public void removePenObjDTByAprvId(String aprvId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("aprvId", aprvId);
        sqlSession.update("PEN_OBJ_D_T.deleteByAprvId", daoParam);
	}

	/* (non-Javadoc)
	 * @see kr.co.penta.dataeye.spring.mybatis.dao.MetaReqDao#removePenObjRTByAprvId(java.lang.String)
	 */
	@Override
	public void removePenObjRTByAprvId(String aprvId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("aprvId", aprvId);
        sqlSession.update("PEN_OBJ_R_T.deleteByAprvId", daoParam);
	}

    /* (non-Javadoc)
	 * @see kr.co.penta.dataeye.spring.mybatis.dao.MetaReqDao#removePenAprvMByAprvId(java.lang.String)
	 */
	@Override
	public void removePenAprvMByAprvId(String aprvId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("aprvId", aprvId);
        sqlSession.update("PEN_APRV_M.deleteByAprvId", daoParam);
	}

	/* (non-Javadoc)
	 * @see kr.co.penta.dataeye.spring.mybatis.dao.MetaReqDao#removePenAprvDByAprvId(java.lang.String)
	 */
	@Override
	public void removePenAprvDByAprvId(String aprvId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("aprvId", aprvId);
        sqlSession.update("PEN_APRV_D.deleteByAprvId", daoParam);
	}

	/* (non-Javadoc)
	 * @see kr.co.penta.dataeye.spring.mybatis.dao.MetaReqDao#removePenAprvFByAprvId(java.lang.String)
	 */
	@Override
	public void removePenAprvFByAprvId(String aprvId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("aprvId", aprvId);
        sqlSession.update("PEN_APRV_F.deleteByAprvId", daoParam);
	}

	@Override
    public List<Map<String, Object>> getAtrValInfo(String objTypeId, String objId, String objAtrValSelectStatment, String objAtrValJoinStatment) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId);
        daoParam.put("objAtrValSelectStatment", objAtrValSelectStatment).put("objAtrValJoinStatment", objAtrValJoinStatment);
        
        return sqlSession.selectList("metareq.getAtrValInfo", daoParam);
    }
    
    @Override
    public PenObjM getObjM(String objTypeId, String objId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId);
        
        return sqlSession.selectOne("metareq.getObjM", daoParam);
    }
}
