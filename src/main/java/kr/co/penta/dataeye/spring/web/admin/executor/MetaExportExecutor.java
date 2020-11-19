package kr.co.penta.dataeye.spring.web.admin.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.TimeUtils;
import kr.co.penta.dataeye.consts.META_EXP_CONST;
import kr.co.penta.dataeye.consts.META_EXP_CONST.RUN_STATUS;

public class MetaExportExecutor implements Runnable {
	private String exerId = "";
	private Connection conn = null;
	private Map<String, Object> reqParam = null;
	private Response response = new Response();
	private boolean isDonwload = false;
	private RUN_STATUS runStatus;
	
	public MetaExportExecutor(Map<String, Object> reqParam, String exerId, Connection conn) {
		super();
		this.reqParam = reqParam;
		this.exerId = exerId;
		this.conn = conn;
		setRunStatus(RUN_STATUS.INIT);
	}

	@Override
	public void run() {
		try {
			setRunStatus(RUN_STATUS.RUNNING);
			//total count
			setTotalCount();
			//data export
			export();
			setRunStatus(RUN_STATUS.SUCCESS);
		} catch (Exception e) {
			setRunStatus(RUN_STATUS.ERROR);
			throw new ServiceRuntimeException(e);
		} finally {
			try { conn.close(); } catch (Exception e) {}
		}
	}
	
	public void setDownload(boolean isDownload) {
		this.isDonwload = isDownload;
	}
	
	public boolean isDownload() {
		return this.isDonwload;
	}
	
	protected void setRunStatus(RUN_STATUS runStatus) {
		this.runStatus = runStatus;
	}
	
	public RUN_STATUS getRunnStatus() {
		return this.runStatus;
	}
	
	public String getExerId() {
		return this.exerId;
	}
	
	public Response getResponse() {
		return this.response;
	}
	
	private void export() {
		for(META_EXP_CONST.KEY reqKey : META_EXP_CONST.KEY.values()) {
    		List<String> value = reqKey.value();
    		
    		if (reqKey.equals(META_EXP_CONST.KEY.OBJ)) {
    			List<String> objTypeIds = (List<String>)reqParam.get(reqKey.name());
    			for(String objTypeId : objTypeIds) {
					for(String tableName : META_EXP_CONST.KEY.OBJ.value()) {
						export(tableName, objTypeId);
					}
    			}
    		} else {
    			Boolean b = CastUtils.toBoolean(reqParam.get(reqKey.name()));
    			if (b) {
    				for(String tableName : value) {
    					export(tableName);
    				}
    			}
    		}
    	}
	}
	
	private void export(String tableName, String objTypeId) {
		if (META_EXP_CONST.TAB_NM.PEN_OBJ_M.name().equals(tableName)) {
			exportPenObjM(objTypeId);
		} else if (META_EXP_CONST.TAB_NM.PEN_OBJ_D.name().equals(tableName)) {
			exportPenObjD(objTypeId);
		} else if (META_EXP_CONST.TAB_NM.PEN_OBJ_R.name().equals(tableName)) {
			exportPenObjR(objTypeId);
		}
    }
	
	private void exportPenObjM(String objTypeId) {
		StringBuilder sb = new StringBuilder();
    	sb.append("SELECT * FROM ").append(META_EXP_CONST.TAB_NM.PEN_OBJ_M.name()).append("\n");
    	sb.append("WHERE OBJ_TYPE_ID = ?");
    	
    	List<ProgsResult> progsResult = response.objResult.get(objTypeId);
    	ProgsResult progsStatus = null;
    	for(ProgsResult ps : progsResult) {
			if (ps.tableName.equals(META_EXP_CONST.TAB_NM.PEN_OBJ_M.name())) {
				progsStatus = ps;
				break;
			}
		}
    	
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, objTypeId);
	    	rs = pstmt.executeQuery();
	    	while (rs.next()) {
	    		Map<String, Object> m = new HashMap<>();
	    		m.put("OBJ_TYPE_ID", rs.getString("OBJ_TYPE_ID"));
	    		m.put("OBJ_ID", rs.getString("OBJ_ID"));
	    		m.put("DEL_YN", rs.getString("DEL_YN"));
	    		m.put("CRET_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CRET_DT"), "yyyyMMddHHmmss"));
	    		m.put("CRETR_ID", rs.getString("CRETR_ID"));
	    		m.put("CHG_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CHG_DT"), "yyyyMMddHHmmss"));
	    		m.put("CHGR_ID", rs.getString("CHGR_ID"));
	    		m.put("ADM_OBJ_ID", rs.getString("ADM_OBJ_ID"));
	    		m.put("OBJ_NM", rs.getString("OBJ_NM"));
	    		m.put("OBJ_ABBR_NM", rs.getString("OBJ_ABBR_NM"));
	    		m.put("OBJ_DESC", rs.getString("OBJ_DESC"));
	    		m.put("PATH_OBJ_TYPE_ID", rs.getString("PATH_OBJ_TYPE_ID"));
	    		m.put("PATH_OBJ_ID", rs.getString("PATH_OBJ_ID"));
	    		
	    		progsStatus.addData(m);
	    	}
		} catch (Exception e) {
			throw new ServiceRuntimeException(e);
		} finally {
			try {rs.close(); } catch (Exception e) {}
			try {pstmt.close(); } catch (Exception e) {}
		}    	
    }
	
	private void exportPenObjD(String objTypeId) {
		StringBuilder sb = new StringBuilder();
    	sb.append("SELECT * FROM ").append(META_EXP_CONST.TAB_NM.PEN_OBJ_D.name()).append("\n");
    	sb.append("WHERE OBJ_TYPE_ID = ?");
    	
    	List<ProgsResult> progsResult = response.objResult.get(objTypeId);
    	ProgsResult progsStatus = null;
    	for(ProgsResult ps : progsResult) {
			if (ps.tableName.equals(META_EXP_CONST.TAB_NM.PEN_OBJ_D.name())) {
				progsStatus = ps;
				break;
			}
		}
    	
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, objTypeId);
	    	rs = pstmt.executeQuery();
	    	while (rs.next()) {
	    		Map<String, Object> m = new HashMap<>();
	    		m.put("OBJ_TYPE_ID", rs.getString("OBJ_TYPE_ID"));
	    		m.put("OBJ_ID", rs.getString("OBJ_ID"));
	    		m.put("ATR_ID_SEQ", rs.getInt("ATR_ID_SEQ"));
	    		m.put("ATR_VAL_SEQ", rs.getInt("ATR_VAL_SEQ"));
	    		m.put("DEL_YN", rs.getString("DEL_YN"));
	    		m.put("CRET_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CRET_DT"), "yyyyMMddHHmmss"));
	    		m.put("CRETR_ID", rs.getString("CRETR_ID"));
	    		m.put("CHG_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CHG_DT"), "yyyyMMddHHmmss"));
	    		m.put("CHGR_ID", rs.getString("CHGR_ID"));
	    		m.put("OBJ_ATR_VAL", rs.getString("OBJ_ATR_VAL"));
	    		
	    		progsStatus.addData(m);
	    	}
		} catch (Exception e) {
			throw new ServiceRuntimeException(e);
		} finally {
			try {rs.close(); } catch (Exception e) {}
			try {pstmt.close(); } catch (Exception e) {}
		}    	
    }
	
	private void exportPenObjR(String objTypeId) {
		StringBuilder sb = new StringBuilder();
    	sb.append("SELECT * FROM ").append(META_EXP_CONST.TAB_NM.PEN_OBJ_R.name()).append("\n");
    	sb.append("WHERE OBJ_TYPE_ID = ? OR REL_OBJ_TYPE_ID = ?");
    	
    	List<ProgsResult> progsResult = response.objResult.get(objTypeId);
    	ProgsResult progsStatus = null;
    	for(ProgsResult ps : progsResult) {
			if (ps.tableName.equals(META_EXP_CONST.TAB_NM.PEN_OBJ_R.name())) {
				progsStatus = ps;
				break;
			}
		}
    	
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, objTypeId);
			pstmt.setString(2, objTypeId);
	    	rs = pstmt.executeQuery();
	    	while (rs.next()) {
	    		Map<String, Object> m = new HashMap<>();
	    		m.put("OBJ_TYPE_ID", rs.getString("OBJ_TYPE_ID"));
	    		m.put("OBJ_ID", rs.getString("OBJ_ID"));
	    		m.put("REL_OBJ_TYPE_ID", rs.getString("REL_OBJ_TYPE_ID"));
	    		m.put("REL_OBJ_ID", rs.getString("REL_OBJ_ID"));
	    		m.put("DEL_YN", rs.getString("DEL_YN"));
	    		m.put("CRET_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CRET_DT"), "yyyyMMddHHmmss"));
	    		m.put("CRETR_ID", rs.getString("CRETR_ID"));
	    		m.put("CHG_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CHG_DT"), "yyyyMMddHHmmss"));
	    		m.put("CHGR_ID", rs.getString("CHGR_ID"));
	    		m.put("REL_TYPE_ID", rs.getString("REL_TYPE_ID"));
	    		m.put("OBJ_REL_ANALS_CD", rs.getString("OBJ_REL_ANALS_CD"));
	    		
	    		progsStatus.addData(m);
	    	}
		} catch (Exception e) {
			throw new ServiceRuntimeException(e);
		} finally {
			try {rs.close(); } catch (Exception e) {}
			try {pstmt.close(); } catch (Exception e) {}
		}    	
    }
	
	private void export(String tableName) {
		if (META_EXP_CONST.TAB_NM.PEN_CD_GRP_M.name().equals(tableName)) {
			exportPenCdGrpM();
		} else if (META_EXP_CONST.TAB_NM.PEN_CD_M.name().equals(tableName)) {
			exportPenCdM();
		} else if (META_EXP_CONST.TAB_NM.PEN_OBJ_TYPE_M.name().equals(tableName)) {
			exportPenObjTypeM();
		} else if (META_EXP_CONST.TAB_NM.PEN_OBJ_TYPE_ATR_D.name().equals(tableName)) {
			exportPenObjTypeAtrD();
		} else if (META_EXP_CONST.TAB_NM.PEN_REL_TYPE_M.name().equals(tableName)) {
			exportPenRelTypeM();
		} else if (META_EXP_CONST.TAB_NM.PEN_ATR_M.name().equals(tableName)) {
			exportPenAtrM();
		}
    }
	
	private void exportPenCdGrpM() {
		StringBuilder sb = new StringBuilder();
    	sb.append("SELECT * FROM ").append(META_EXP_CONST.TAB_NM.PEN_CD_GRP_M.name()).append("\n");
    	
    	ProgsResult progsStatus = response.commCdResult.get(META_EXP_CONST.TAB_NM.PEN_CD_GRP_M.name());
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
	    	rs = pstmt.executeQuery();
	    	while (rs.next()) {
	    		Map<String, Object> m = new HashMap<>();
	    		m.put("CD_GRP_ID", rs.getString("CD_GRP_ID"));
	    		m.put("DEL_YN", rs.getString("DEL_YN"));
	    		m.put("CRET_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CRET_DT"), "yyyyMMddHHmmss"));
	    		m.put("CRETR_ID", rs.getString("CRETR_ID"));
	    		m.put("CHG_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CHG_DT"), "yyyyMMddHHmmss"));
	    		m.put("CHGR_ID", rs.getString("CHGR_ID"));
	    		m.put("CD_GRP_NM", rs.getString("CD_GRP_NM"));
	    		m.put("CD_GRP_DESC", rs.getString("CD_GRP_DESC"));
	    		m.put("UP_CD_GRP_ID", rs.getString("UP_CD_GRP_ID"));
	    		m.put("EFCT_ST_DATE", TimeUtils.getInstance().formatDate(rs.getDate("EFCT_ST_DATE"), "yyyyMMddHHmmss"));
	    		m.put("EFCT_ED_DATE", TimeUtils.getInstance().formatDate(rs.getDate("EFCT_ED_DATE"), "yyyyMMddHHmmss"));
	    		
	    		progsStatus.addData(m);
	    	}
		} catch (Exception e) {
			throw new ServiceRuntimeException(e);
		} finally {
			try {rs.close(); } catch (Exception e) {}
			try {pstmt.close(); } catch (Exception e) {}
		}    	
    }
	
	private void exportPenCdM() {
		StringBuilder sb = new StringBuilder();
    	sb.append("SELECT * FROM ").append(META_EXP_CONST.TAB_NM.PEN_CD_M.name()).append("\n");
    	
    	ProgsResult progsStatus = response.commCdResult.get(META_EXP_CONST.TAB_NM.PEN_CD_M.name());
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
	    	rs = pstmt.executeQuery();
	    	while (rs.next()) {
	    		Map<String, Object> m = new HashMap<>();
	    		m.put("CD_GRP_ID", rs.getString("CD_GRP_ID"));
	    		m.put("CD_ID", rs.getString("CD_ID"));
	    		m.put("DEL_YN", rs.getString("DEL_YN"));
	    		m.put("CRET_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CRET_DT"), "yyyyMMddHHmmss"));
	    		m.put("CRETR_ID", rs.getString("CRETR_ID"));
	    		m.put("CHG_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CHG_DT"), "yyyyMMddHHmmss"));
	    		m.put("CHGR_ID", rs.getString("CHGR_ID"));
	    		m.put("CD_NM", rs.getString("CD_NM"));
	    		m.put("CD_DESC", rs.getString("CD_DESC"));
	    		m.put("SORT_NO", rs.getInt("SORT_NO"));
	    		m.put("USE_YN", rs.getString("USE_YN"));
	    		m.put("UP_CD_GRP_ID", rs.getString("UP_CD_GRP_ID"));
	    		m.put("UP_CD_ID", rs.getString("UP_CD_ID"));
	    		m.put("EFCT_ST_DATE", TimeUtils.getInstance().formatDate(rs.getDate("EFCT_ST_DATE"), "yyyyMMddHHmmss"));
	    		m.put("EFCT_ED_DATE", TimeUtils.getInstance().formatDate(rs.getDate("EFCT_ED_DATE"), "yyyyMMddHHmmss"));
	    		
	    		progsStatus.addData(m);
	    	}
		} catch (Exception e) {
			throw new ServiceRuntimeException(e);
		} finally {
			try {rs.close(); } catch (Exception e) {}
			try {pstmt.close(); } catch (Exception e) {}
		}    	
    }
	
	private void exportPenObjTypeM() {
		StringBuilder sb = new StringBuilder();
    	sb.append("SELECT * FROM ").append(META_EXP_CONST.TAB_NM.PEN_OBJ_TYPE_M.name()).append("\n");
    	
    	ProgsResult progsStatus = response.objTypeResult.get(META_EXP_CONST.TAB_NM.PEN_OBJ_TYPE_M.name());
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
	    	rs = pstmt.executeQuery();
	    	while (rs.next()) {
	    		Map<String, Object> m = new HashMap<>();
	    		m.put("OBJ_TYPE_ID", rs.getString("OBJ_TYPE_ID"));
	    		m.put("DEL_YN", rs.getString("DEL_YN"));
	    		m.put("CRET_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CRET_DT"), "yyyyMMddHHmmss"));
	    		m.put("CRETR_ID", rs.getString("CRETR_ID"));
	    		m.put("CHG_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CHG_DT"), "yyyyMMddHHmmss"));
	    		m.put("CHGR_ID", rs.getString("CHGR_ID"));
	    		m.put("OBJ_TYPE_NM", rs.getString("OBJ_TYPE_NM"));
	    		m.put("OBJ_TYPE_DESC", rs.getString("OBJ_TYPE_DESC"));
	    		m.put("SORT_NO", rs.getInt("SORT_NO"));
	    		m.put("UP_OBJ_TYPE_ID", rs.getString("UP_OBJ_TYPE_ID"));
	    		m.put("HIER_LEV_NO", rs.getString("HIER_LEV_NO"));
	    		m.put("LST_LEV_YN", rs.getString("LST_LEV_YN"));
	    		m.put("ATR_ADM_CD", rs.getString("ATR_ADM_CD"));
	    		m.put("STLM_YN", rs.getString("STLM_YN"));
	    		m.put("HST_YN", rs.getString("HST_YN"));
	    		m.put("TAG_YN", rs.getString("TAG_YN"));
	    		m.put("CMMT_YN", rs.getString("CMMT_YN"));

	    		progsStatus.addData(m);
	    	}
		} catch (Exception e) {
			throw new ServiceRuntimeException(e);
		} finally {
			try {rs.close(); } catch (Exception e) {}
			try {pstmt.close(); } catch (Exception e) {}
		}    	
    }
	
	private void exportPenObjTypeAtrD() {
		StringBuilder sb = new StringBuilder();
    	sb.append("SELECT * FROM ").append(META_EXP_CONST.TAB_NM.PEN_OBJ_TYPE_ATR_D.name()).append("\n");
    	
    	ProgsResult progsStatus = response.objTypeResult.get(META_EXP_CONST.TAB_NM.PEN_OBJ_TYPE_ATR_D.name());
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
	    	rs = pstmt.executeQuery();
	    	while (rs.next()) {
	    		Map<String, Object> m = new HashMap<>();
	    		m.put("META_TYPE_CD", rs.getString("META_TYPE_CD"));
	    		m.put("OBJ_TYPE_ID", rs.getString("OBJ_TYPE_ID"));
	    		m.put("ATR_ID_SEQ", rs.getInt("ATR_ID_SEQ"));
	    		m.put("DEL_YN", rs.getString("DEL_YN"));
	    		m.put("CRET_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CRET_DT"), "yyyyMMddHHmmss"));
	    		m.put("CRETR_ID", rs.getString("CRETR_ID"));
	    		m.put("CHG_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CHG_DT"), "yyyyMMddHHmmss"));
	    		m.put("CHGR_ID", rs.getString("CHGR_ID"));
	    		m.put("ATR_ID", rs.getString("ATR_ID"));
	    		m.put("ATR_ALIAS_NM", rs.getString("ATR_ALIAS_NM"));
	    		m.put("SORT_NO", rs.getInt("SORT_NO"));
	    		m.put("MULTI_ATR_YN", rs.getString("MULTI_ATR_YN"));
	    		m.put("CNCT_ATR_YN", rs.getString("CNCT_ATR_YN"));
	    		m.put("MAND_YN", rs.getString("MAND_YN"));
	    		m.put("ATR_ADM_TGT_YN", rs.getString("ATR_ADM_TGT_YN"));
	    		m.put("DEGR_NO", rs.getString("DEGR_NO"));
	    		m.put("INDC_YN", rs.getString("INDC_YN"));
	    		m.put("FIND_TGT_NO", rs.getString("FIND_TGT_NO"));
	    		m.put("AVAIL_CHK_PGM_ID", rs.getString("AVAIL_CHK_PGM_ID"));
	    		m.put("UI_COMP_WIDTH_RADIO", rs.getString("UI_COMP_WIDTH_RADIO"));
	    		m.put("UI_COMP_HEIGHT_PX", rs.getObject("UI_COMP_HEIGHT_PX"));
	    		
	    		progsStatus.addData(m);
	    	}
		} catch (Exception e) {
			throw new ServiceRuntimeException(e);
		} finally {
			try {rs.close(); } catch (Exception e) {}
			try {pstmt.close(); } catch (Exception e) {}
		}    	
    }
	
	private void exportPenRelTypeM() {
		StringBuilder sb = new StringBuilder();
    	sb.append("SELECT * FROM ").append(META_EXP_CONST.TAB_NM.PEN_REL_TYPE_M.name()).append("\n");
    	
    	ProgsResult progsStatus = response.relTypeResult.get(META_EXP_CONST.TAB_NM.PEN_REL_TYPE_M.name());
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
	    	rs = pstmt.executeQuery();
	    	while (rs.next()) {
	    		Map<String, Object> m = new HashMap<>();
	    		m.put("REL_TYPE_ID", rs.getString("REL_TYPE_ID"));
	    		m.put("DEL_YN", rs.getString("DEL_YN"));
	    		m.put("CRET_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CRET_DT"), "yyyyMMddHHmmss"));
	    		m.put("CRETR_ID", rs.getString("CRETR_ID"));
	    		m.put("CHG_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CHG_DT"), "yyyyMMddHHmmss"));
	    		m.put("CHGR_ID", rs.getString("CHGR_ID"));
	    		m.put("REL_TYPE_NM", rs.getString("REL_TYPE_NM"));
	    		m.put("DOWN_REL_TYPE_NM", rs.getString("DOWN_REL_TYPE_NM"));
	    		m.put("UP_REL_TYPE_NM", rs.getString("UP_REL_TYPE_NM"));
	    		m.put("REL_TYPE_DESC", rs.getString("REL_TYPE_DESC"));
	    		m.put("OBJ_TYPE_ID", rs.getString("OBJ_TYPE_ID"));
	    		m.put("REL_OBJ_TYPE_ID", rs.getString("REL_OBJ_TYPE_ID"));
	    		m.put("META_REL_CD", rs.getString("META_REL_CD"));
	    		m.put("UP_REL_TYPE_ID", rs.getString("UP_REL_TYPE_ID"));
	    		m.put("ATR_ADM_CD", rs.getString("ATR_ADM_CD"));
	    		m.put("SQL_SBST", rs.getString("SQL_SBST"));
	    		
	    		progsStatus.addData(m);
	    	}
		} catch (Exception e) {
			throw new ServiceRuntimeException(e);
		} finally {
			try {rs.close(); } catch (Exception e) {}
			try {pstmt.close(); } catch (Exception e) {}
		}    	
    }
	
	private void exportPenAtrM() {
		StringBuilder sb = new StringBuilder();
    	sb.append("SELECT * FROM ").append(META_EXP_CONST.TAB_NM.PEN_ATR_M.name()).append("\n");
    	
    	ProgsResult progsStatus = response.atrResult.get(META_EXP_CONST.TAB_NM.PEN_ATR_M.name());
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
	    	rs = pstmt.executeQuery();
	    	while (rs.next()) {
	    		Map<String, Object> m = new HashMap<>();
	    		m.put("ATR_ID", rs.getString("ATR_ID"));
	    		m.put("DEL_YN", rs.getString("DEL_YN"));
	    		m.put("CRET_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CRET_DT"), "yyyyMMddHHmmss"));
	    		m.put("CRETR_ID", rs.getString("CRETR_ID"));
	    		m.put("CHG_DT", TimeUtils.getInstance().formatDate(rs.getTimestamp("CHG_DT"), "yyyyMMddHHmmss"));
	    		m.put("CHGR_ID", rs.getString("CHGR_ID"));
	    		m.put("ATR_NM", rs.getString("ATR_NM"));
	    		m.put("ATR_DESC", rs.getString("ATR_DESC"));
	    		m.put("DATA_TYPE_CD", rs.getString("DATA_TYPE_CD"));
	    		m.put("COL_LEN", rs.getInt("COL_LEN"));
	    		m.put("ATR_RFRN_CD", rs.getString("ATR_RFRN_CD"));
	    		m.put("SQL_SBST", rs.getString("SQL_SBST"));
	    		m.put("UI_COMP_CD", rs.getString("UI_COMP_CD"));
	    		m.put("UI_COMP_WIDTH_RADIO", rs.getString("UI_COMP_WIDTH_RADIO"));
	    		m.put("UI_COMP_HEIGHT_PX", rs.getObject("UI_COMP_HEIGHT_PX"));
	    		
	    		progsStatus.addData(m);
	    	}
		} catch (Exception e) {
			throw new ServiceRuntimeException(e);
		} finally {
			try {rs.close(); } catch (Exception e) {}
			try {pstmt.close(); } catch (Exception e) {}
		}    	
    }
	
	private void setTotalCount() {
		for(META_EXP_CONST.KEY reqKey : META_EXP_CONST.KEY.values()) {
    		List<String> value = reqKey.value();
    		
    		if (reqKey.equals(META_EXP_CONST.KEY.OBJ)) {
    			List<String> objTypeIds = (List<String>)reqParam.get(reqKey.name());
    			for(String objTypeId : objTypeIds) {
					for(String tableName : META_EXP_CONST.KEY.OBJ.value()) {
						setTotalCount(tableName, objTypeId);
					}
    			}
    		} else {
    			Boolean b = CastUtils.toBoolean(reqParam.get(reqKey.name()));
    			if (b) {
    				for(String tableName : value) {
    					setTotalCount(tableName);
    				}
    			}
    		}
    	}
	}
	
	private void setTotalCount(String tableName) {
		StringBuilder sb = new StringBuilder();
    	sb.append("SELECT COUNT(1) AS CNT FROM ").append(tableName).append("\n");
    	
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
	    	if (rs.next()) {
	    		int totCnt = rs.getInt("CNT");
	    		Map<String, ProgsResult> progsStatusMap = null;
	    		META_EXP_CONST.KEY matchKey = null;
	    		for(META_EXP_CONST.KEY key : META_EXP_CONST.KEY.values()) {
	    			for(String t : key.value()) {
	    				if (t.equals(tableName)) {
	    					matchKey = key;
	    					break;
	    				}
	    			}
	    			if (matchKey != null) break;
	    		}
    			
    			if (matchKey.equals(META_EXP_CONST.KEY.COMM_CD)) {
    				progsStatusMap = response.commCdResult;
    			} else if (matchKey.equals(META_EXP_CONST.KEY.OBJ_TYPE)) {
    				progsStatusMap = response.objTypeResult;
    			} else if (matchKey.equals(META_EXP_CONST.KEY.REL_TYPE)) {
    				progsStatusMap = response.relTypeResult;
    			} else if (matchKey.equals(META_EXP_CONST.KEY.ATR)) {
    				progsStatusMap = response.atrResult;
    			}
	    		if (progsStatusMap.get(tableName) == null) {
	    			ProgsResult progsStatus = new ProgsResult(tableName);
	    			progsStatus.totCnt = totCnt;
	    			progsStatusMap.put(tableName, progsStatus);
	    		} else {
	    			progsStatusMap.get(tableName).totCnt = totCnt;
	    		}
	    	}
		} catch (Exception e) {
			throw new ServiceRuntimeException(e);
		} finally {
			try {rs.close(); } catch (Exception e) {}
			try {pstmt.close(); } catch (Exception e) {}
		}    	
    }
	
	private void setTotalCount(String tableName, String objTypeId) {
		StringBuilder sb = new StringBuilder();
    	sb.append("SELECT COUNT(1) AS CNT FROM ").append(tableName).append("\n");
    	if (META_EXP_CONST.TAB_NM.PEN_OBJ_R.name().equals(tableName)) {
    		sb.append("WHERE OBJ_TYPE_ID = ? OR REL_OBJ_TYPE_ID = ?");
    	} else {
    		sb.append("WHERE OBJ_TYPE_ID = ?");
    	}
    	
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			if (META_EXP_CONST.TAB_NM.PEN_OBJ_R.name().equals(tableName)) {
				pstmt.setString(1, objTypeId);
				pstmt.setString(2, objTypeId);
			} else {
				pstmt.setString(1, objTypeId);
			}
	    	rs = pstmt.executeQuery();
	    	if (rs.next()) {
	    		int totCnt = rs.getInt("CNT");
	    		
	    		List<ProgsResult> progsResult = response.objResult.get(objTypeId);
	    		boolean isMatch = false;
	    		if (progsResult == null) {
	    			progsResult = new ArrayList<>();
	    			response.objResult.put(objTypeId, progsResult);
	    		} else {
		    		for(ProgsResult progsStatus : progsResult) {
		    			if (progsStatus.tableName.equals(tableName)) {
		    				progsStatus.totCnt = totCnt;
		    				isMatch = true;
		    				break;
		    			}
		    		}
	    		}
	    		if (!isMatch) {
	    			ProgsResult progsStatus = new ProgsResult(tableName);
	    			progsStatus.totCnt = totCnt;
	    			progsResult.add(progsStatus);
	    		}
	    	}
		} catch (Exception e) {
			throw new ServiceRuntimeException(e);
		} finally {
			try {rs.close(); } catch (Exception e) {}
			try {pstmt.close(); } catch (Exception e) {}
		}    	
    }
    
    public class Response {
    	public Map<String, ProgsResult> commCdResult = new HashMap<>();
    	public Map<String, ProgsResult> objTypeResult = new HashMap<>();
    	public Map<String, ProgsResult> relTypeResult = new HashMap<>();
    	public Map<String, ProgsResult> atrResult = new HashMap<>();
    	public Map<String, List<ProgsResult>> objResult = new HashMap<>();
    	
    	public Response() {
    		List<String> commCds = META_EXP_CONST.KEY.COMM_CD.value();
    		for(String t : commCds) { 
    			commCdResult.put(t, new ProgsResult(t));
    		}
    		List<String> objTypes = META_EXP_CONST.KEY.OBJ_TYPE.value();
    		for(String t : objTypes) { 
    			objTypeResult.put(t, new ProgsResult(t));
    		}
    		List<String> relTypes = META_EXP_CONST.KEY.REL_TYPE.value();
    		for(String t : relTypes) { 
    			relTypeResult.put(t, new ProgsResult(t));
    		}
    		List<String> atrs = META_EXP_CONST.KEY.ATR.value();
    		for(String t : atrs) { 
    			atrResult.put(t, new ProgsResult(t));
    		}
    	}
    	
    	public Map<String, Object> getProcStatus() {
    		Map<String, Object> resultMap = new HashMap<>();
    		
    		resultMap.put(META_EXP_CONST.KEY.COMM_CD.name(), (Map<String, ProgsStatus>)(Map<?, ?>)commCdResult);
    		resultMap.put(META_EXP_CONST.KEY.OBJ_TYPE.name(), (Map<String, ProgsStatus>)(Map<?, ?>)objTypeResult);
    		resultMap.put(META_EXP_CONST.KEY.REL_TYPE.name(), (Map<String, ProgsStatus>)(Map<?, ?>)relTypeResult);
    		resultMap.put(META_EXP_CONST.KEY.ATR.name(), (Map<String, ProgsStatus>)(Map<?, ?>)atrResult);
    		resultMap.put(META_EXP_CONST.KEY.OBJ.name(), (Map<String, List<ProgsStatus>>)(Map<?, ?>)objResult);
    		return resultMap;
    	}
    	
    	public Map<String, Object> getData() {
    		Map<String, Object> resultMap = new HashMap<>();
    		
    		resultMap.put(META_EXP_CONST.KEY.COMM_CD.name(), commCdResult);
    		resultMap.put(META_EXP_CONST.KEY.OBJ_TYPE.name(), objTypeResult);
    		resultMap.put(META_EXP_CONST.KEY.REL_TYPE.name(), relTypeResult);
    		resultMap.put(META_EXP_CONST.KEY.ATR.name(), atrResult);
    		resultMap.put(META_EXP_CONST.KEY.OBJ.name(), objResult);
    		return resultMap;
    	}
    }
    
    private class ProgsResult extends ProgsStatus {
    	public List<Map<String, Object>> data = new ArrayList<>();
    	
    	public ProgsResult(String tableName) {
			super(tableName);
		}

    	public void addData(Map<String, Object> record) {
    		data.add(record);
    		this.procCnt++;
    	}
    }
    
    private class ProgsStatus {
    	public String tableName = "";
    	public int totCnt = 100;
    	public int procCnt = 0;
    	
    	public ProgsStatus(String tableName) {
			this.tableName = tableName;
		}
    }
}
