package kr.co.penta.dataeye.spring.web.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.common.service.MetaService;
import kr.co.penta.dataeye.spring.web.portal.service.BizCommcdService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class BizCommcdServiceImpl implements BizCommcdService {
    @Autowired CommonDao commonDao;
    
    @Autowired MetaService metaService;
	
    @Override
    public List<Map<String,Object>> findCommcd(String objTypeId, String objId) {
    	DaoParam daoParam = new DaoParam();
    	daoParam.put("objTypeId", objTypeId).put("objId", objId);
    	Map<String,Object> cdGrp = commonDao.selectOne("portal.biz.commcd.getCommCdGrp",daoParam);
    	
    	String cdGrpNm = CastUtils.toString(cdGrp.get("COMMCD_GRP_NM"));
//    	String stdTermNm = CastUtils.toString(cdGrp.get("STD_TERM_NM"));
//    	String inputYn = CastUtils.toString(cdGrp.get("INPUT_YN"));
		String cdTblNm = CastUtils.toString(cdGrp.get("CD_TBL_NM"));
		String cdIdCol = CastUtils.toString(cdGrp.get("CD_ID_COL"));
		String cdValCol = CastUtils.toString(cdGrp.get("CD_VAL_COL"));
		String cdDescCol = CastUtils.toString(cdGrp.get("CD_DESC_COL"));
		String cdSortCol = CastUtils.toString(cdGrp.get("CD_SORT_COL"));
		String cdGrpIdCol = CastUtils.toString(cdGrp.get("CD_GRP_ID_COL"));
//		String cdGrpVal = CastUtils.toString(cdGrp.get("CD_GRP_VAL"));
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T101.").append(cdGrpIdCol).append(" AS CD_GRP_ID").append("\n");
		sb.append("     , '").append(cdGrpNm).append("' AS CD_GRP_NM").append("\n");
		sb.append("     , T101.").append(cdIdCol).append(" AS CD_ID").append("\n");
		sb.append("     , T101.").append(cdValCol).append(" AS CD_NM").append("\n");
		sb.append("     , T101.").append(cdDescCol).append(" AS CD_DESC").append("\n");
		sb.append("     , T101.").append(cdSortCol).append(" AS SORT_NO").append("\n");
		sb.append("FROM ").append(cdTblNm).append(" T101").append("\n");
		sb.append("WHERE T101.").append(cdGrpIdCol).append(" = '").append(objId).append("'\n");
		sb.append("ORDER BY T101.").append(cdSortCol).append(" ASC").append("\n");
		
		daoParam.clear();
		daoParam.put("SQL", sb.toString());		
    	return commonDao.selectList("common.dynamicSql",daoParam);
    }

	@Override
    @Transactional
	public void deleteCommcdGrp(String objTypeId, String objId, SessionInfo sessionInfo) {
		DaoParam daoParam = new DaoParam();
    	daoParam.put("objTypeId", objTypeId).put("objId", objId);
		Map<String,Object> cdGrp = commonDao.selectOne("portal.biz.commcd.getCommCdGrp",daoParam);
		String cdTblNm = CastUtils.toString(cdGrp.get("CD_TBL_NM"));
		String cdGrpIdCol = CastUtils.toString(cdGrp.get("CD_GRP_ID_COL"));
		
		metaService.deleteObjectInfo(objTypeId, objId, sessionInfo);
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ").append(cdTblNm).append("\n");
		sb.append("SET    DEL_YN = ").append("'Y'").append("\n");
		sb.append("WHERE ").append(cdGrpIdCol).append(" = '").append(objId).append("'\n");
		
		daoParam.clear();
		daoParam.put("SQL", sb.toString());		
    	commonDao.update("common.dynamicSql",daoParam);
	}

	@Override
    @Transactional
	public void saveCommcd(String cdGrpTypeId, String cdGrpId, List<Map<String, Object>> data, SessionInfo sessionInfo) {
		DaoParam daoParam = new DaoParam();
    	daoParam.put("objTypeId", cdGrpTypeId).put("objId", cdGrpId);
		Map<String,Object> cdGrp = commonDao.selectOne("portal.biz.commcd.getCommCdGrp",daoParam);
		String cdTblNm = CastUtils.toString(cdGrp.get("CD_TBL_NM"));
		String cdGrpIdCol = CastUtils.toString(cdGrp.get("CD_GRP_ID_COL"));
		String cdIdCol = CastUtils.toString(cdGrp.get("CD_ID_COL"));
		String cdValCol = CastUtils.toString(cdGrp.get("CD_VAL_COL"));
		String cdDescCol = CastUtils.toString(cdGrp.get("CD_DESC_COL"));
		String cdSortCol = CastUtils.toString(cdGrp.get("CD_SORT_COL"));
		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ").append(cdTblNm).append("\n");
		sb.append("WHERE ").append(cdGrpIdCol).append(" = '").append(cdGrpId).append("'\n");
		daoParam.clear();
		daoParam.put("SQL", sb.toString());		
    	commonDao.delete("common.dynamicSql",daoParam);
    	
    	if (data != null) {
    		int sortNo = 1;
    		for(Map<String, Object> map : data) {
    			String CD_ID = CastUtils.toString(map.get("CD_ID"));
    			String CD_NM = CastUtils.toString(map.get("CD_NM"));
    			String CD_DESC = CastUtils.toString(map.get("CD_DESC"));
    			
    			sb.setLength(0);
    			sb.append("INSERT INTO ").append(cdTblNm);
    			sb.append("(");
    			sb.append(cdGrpIdCol).append(", ");
    			sb.append(cdIdCol).append(", ");
    			sb.append(cdValCol).append(", ");
    			sb.append(cdDescCol).append(", ");
    			sb.append(cdSortCol).append(", ");
    			
    			sb.append("DEL_YN, CRET_DT, CRETR_ID, CHG_DT, CHGR_ID, USE_YN, UP_CD_GRP_ID, UP_CD_ID, EFCT_ST_DATE, EFCT_ED_DATE");
    			sb.append(")").append("\n");
    			
    			sb.append("VALUES (");
    			sb.append("#{cdGrpId}").append(", ");
    			sb.append("#{cdId}").append(", ");
    			sb.append("#{cdNm}").append(", ");
    			sb.append("#{cdDesc}").append(", ");
    			sb.append("#{sordNo}").append(", ");
    			
    			sb.append("'N', CURRENT_TIMESTAMP, '").append(sessionInfo.getUserId()).append("', CURRENT_TIMESTAMP, '").append(sessionInfo.getUserId()).append("', 'Y', NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP+1000");
    			sb.append(")").append("\n");
    			
    			daoParam.clear();
    			daoParam.put("cdGrpId", cdGrpId);	
    			daoParam.put("cdId", CD_ID);	
    			daoParam.put("cdNm", CD_NM);	
    			daoParam.put("cdDesc", CD_DESC);	
    			daoParam.put("sordNo", sortNo++);	
    			daoParam.put("SQL", sb.toString());		
    	    	commonDao.delete("common.dynamicSql",daoParam);
    		}
    	}
	}
}
