package kr.co.penta.dataeye.spring.mybatis.dao;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.BaseAuth;
import kr.co.penta.dataeye.common.entities.PortalMenuEntity;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

public interface PortalDao {
	List<PortalMenuEntity> getPortalMenu(DaoParam daoParam);

	List<PortalMenuEntity> findAllMenuByApp(String appId);

	List<BaseAuth> findMenuAuthByApp(String appId, SessionInfo sessionInfo);

	List<Map<String, String>> getReportDetail(String reportId);

	Map<String, String> getDatasetDetail(String datasetId);

	Map<String, String> getDatasetSampleSql(String datasetId);

	List<Map<String, String>> getDatasetSampleColumnList(String datasetId);

	Map<String, String> getToolLoginUserDetail(String userId, String reportId);

	Map<String, String> datasetSampleFile(String datasetId);

	Map<String, String> taskDetail(String taskId);

	BaseAuth findMenuAuthByUser(SessionInfo sessionInfo, String appId, String menuId);

}
