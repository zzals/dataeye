package kr.co.penta.dataeye.spring.web.portal.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import kr.co.penta.dataeye.common.entities.BaseAuth;
import kr.co.penta.dataeye.common.entities.PortalMenuEntity;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface PortalService {
    List<PortalMenuEntity> getPortalMenu(SessionInfo sessionInfo, String appId);
    
    PortalMenuEntity getMenuAuth(HttpSession session, String menuId);
    
    List<Map<String,String>> getReportDetail(String reportId);
    
    Map<String,String> getDatasetDetail(String datasetId);
    
    Map<String,String> getDatasetSampleSql(String datasetId);
    
    List<Map<String,String>> getDatasetSampleColumnList(String datasetId);
    
    List<Map<String,Object>> getSampleList(Map<String,String> datasetSampleSql);
        
    Map<String,String> getToolLoginUserDetail(String userId,String reportId);

    Map<String,String> datasetSampleFile(String datasetId);
    
    Map<String,String> taskDetail(String taskId);
    
    void updateMyPwd(String userId, String userPassword, SessionInfo sessionInfo);

	BaseAuth findMenuAuthByUser(SessionInfo sessionInfo, String appId, String menuId);
}

