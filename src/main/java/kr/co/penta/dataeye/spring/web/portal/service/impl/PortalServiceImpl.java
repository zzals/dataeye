package kr.co.penta.dataeye.spring.web.portal.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.common.entities.BaseAuth;
import kr.co.penta.dataeye.common.entities.PortalMenuEntity;
import kr.co.penta.dataeye.common.util.AuthMenuBuilder;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.PortalDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.portal.service.PortalService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class PortalServiceImpl implements PortalService {
    @Autowired PortalDao portalDao;
   
    @Autowired 
	private CommonDao commonDao; 
    
    public List<PortalMenuEntity> getPortalMenu(SessionInfo sessionInfo, String appId) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.put("appId", appId);
        
        List<PortalMenuEntity> portalMenuEntities =  portalDao.findAllMenuByApp(appId);
        List<BaseAuth> baseAuths = portalDao.findMenuAuthByApp(appId, sessionInfo);
        
        AuthMenuBuilder builder = new AuthMenuBuilder(portalMenuEntities, baseAuths);
        if (sessionInfo.isAdminRole() || sessionInfo.isSystemRole()) {
        	builder.build(true);
        } else {
        	builder.build(false);
        }

        return portalMenuEntities;
    }
    
	public BaseAuth findMenuAuthByUser(SessionInfo sessionInfo, String appId, String menuId) {
		return portalDao.findMenuAuthByUser(sessionInfo, appId, menuId);
	}
    
	@Override
	public PortalMenuEntity getMenuAuth(HttpSession session, String menuId) {
		List<PortalMenuEntity> portalMenuEntities = SessionInfoSupport.getPortalMenuAuth(session);
		return getMenuAuth(portalMenuEntities, menuId);
	}
	
	private PortalMenuEntity getMenuAuth(List<PortalMenuEntity> portalMenuEntities, String menuId) {
		for(PortalMenuEntity portalMenuEntity : portalMenuEntities) {
			if (portalMenuEntity.getMenuId().equals(menuId)) {
				return portalMenuEntity;
			}
		}
		
		return null;
	}
	
	@Override
	public List<Map<String, String>> getReportDetail(String reportId) {
		List<Map<String,String>> reportDetail =  portalDao.getReportDetail(reportId);
		return reportDetail;
	}
	
	@Override
	public Map<String, String> getDatasetDetail(String datasetId) {
		Map<String,String> datasetDetail =  portalDao.getDatasetDetail(datasetId);
		return datasetDetail;
	}
	
	@Override
	public Map<String, String> getDatasetSampleSql(String datasetId) {
		Map<String,String> datasetSampleSql =  portalDao.getDatasetSampleSql(datasetId);
		return datasetSampleSql;
	}
	
	
	@Override
	public List<Map<String,String>> getDatasetSampleColumnList(String datasetId) {
		List<Map<String,String>> datasetDetail =  portalDao.getDatasetSampleColumnList(datasetId);
		return datasetDetail;
	}
	
	@Override
	public List<Map<String,Object>> getSampleList(Map<String,String> datasetSampleSql) {
		
		List<Map<String,Object>> sampleList = new ArrayList<Map<String,Object>>(); 
		
    	String JDBC = (String)datasetSampleSql.get("JDBC");
    	String URL = (String)datasetSampleSql.get("URL");
    	String ID = (String)datasetSampleSql.get("ID");
    	String PWD = (String)datasetSampleSql.get("PWD");
    	String SQL = (String)datasetSampleSql.get("SQL");
    	Connection conn = null;
    	PreparedStatement psmt = null;
    	ResultSet rs = null;
		try {
			Class.forName(JDBC);			
			
			conn = DriverManager.getConnection(URL, ID, PWD);
			
			SQL = "SELECT * FROM (" + SQL + ") WHERE ROWNUM <=" + datasetSampleSql.get("cnt");
			
			System.out.println("SQL" + SQL);
			psmt = conn.prepareStatement(SQL);			
			rs = psmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();			
			int colCnt = meta.getColumnCount();
			
			
			while(rs.next()) {
				Map<String,Object> cont = new HashMap<String,Object>();
				for(int i = 1;i<=colCnt;i++) {
					String name = meta.getColumnName(i);
					Object value = rs.getObject(i);		
					if(value == null)value = "";
					cont.put(name, value);
				}				
				sampleList.add(cont);
			}
			
			
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("jdbc driver 로딩 실패");
		} catch (SQLException e) {
			System.out.println("오라클 연결 실패" + e.getMessage());
		}finally {
			if (rs!=null) {
				   try {
				      rs.close();
				   } catch (SQLException e) {
				   }
				}
				 
				if(psmt!=null) {
				   try {
					   psmt.close();
				   } catch (SQLException e) {
				   }
				}
				 
				if(conn!=null) {
				   try {
				      conn.close();
				   } catch (SQLException e) {
				   }
				}
		}
		
		
		
		return sampleList;
	}
	
	@Override
	public Map<String,String> getToolLoginUserDetail(String userId, String reportId) {
		Map<String,String> toolLoginUserDetail =  portalDao.getToolLoginUserDetail(userId,reportId);
		return toolLoginUserDetail;
	}

	@Override
	public Map<String,String> datasetSampleFile(String datasetId) {
		Map<String,String> datasetSampleFile =  portalDao.datasetSampleFile(datasetId);
		return datasetSampleFile;
	}
	
	@Override
	public Map<String,String> taskDetail(String taskId) {
		Map<String,String> taskDetail =  portalDao.taskDetail(taskId);
		return taskDetail;
	}
	
	@Override
    public void updateMyPwd(String userId, String userPassword, SessionInfo sessionInfo) {
    	DaoParam daoParam = new DaoParam();
        daoParam.put("userId", userId);
        daoParam.put("userPassword", userPassword);
        daoParam.put("sessionInfo", sessionInfo);
        
        commonDao.update("PEN_USER_M.updateUserPwdM", daoParam);
    }

	
	

	
}
