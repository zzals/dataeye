package kr.co.penta.dataeye.spring.mybatis.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.co.penta.dataeye.common.entities.BaseAuth;
import kr.co.penta.dataeye.common.entities.PortalMenuEntity;
import kr.co.penta.dataeye.spring.mybatis.dao.PortalDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.mybatis.dao.support.DataEyeDaoSupport;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

@Repository
public class PortalDaoImpl extends DataEyeDaoSupport implements PortalDao {
	@Override
	public List<PortalMenuEntity> getPortalMenu(DaoParam daoParam) {
		return sqlSession.selectList("portal.getAuthMenu", daoParam);
	}

	@Override
	public List<PortalMenuEntity> findAllMenuByApp(String appId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("appId", appId);
		return sqlSession.selectList("portal.common.findAllMenuByApp", daoParam);
	}

	@Override
	public List<BaseAuth> findMenuAuthByApp(String appId, SessionInfo sessionInfo) {
		DaoParam daoParam = new DaoParam(sessionInfo);
		daoParam.put("appId", appId);
		return sqlSession.selectList("portal.common.findMenuAuthByApp", daoParam);
	}

	@Override
	public List<Map<String, String>> getReportDetail(String reportId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("reportId", reportId);
		return sqlSession.selectList("portal.report.getReportDetail", daoParam);
	}

	@Override
	public Map<String, String> getDatasetDetail(String datasetId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("datasetId", datasetId);
		return sqlSession.selectOne("portal.dataset.getDatasetDetail", daoParam);
	}

	@Override
	public Map<String, String> getDatasetSampleSql(String datasetId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("datasetId", datasetId);
		return sqlSession.selectOne("portal.dataset.getDatasetSampleSql", daoParam);
	}

	@Override
	public List<Map<String, String>> getDatasetSampleColumnList(String datasetId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("datasetId", datasetId);
		return sqlSession.selectList("portal.dataset.getDatasetSampleColumnList", daoParam);
	}

	@Override
	public Map<String, String> getToolLoginUserDetail(String userId, String reportId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("userId", userId);
		daoParam.put("reportId", reportId);
		return sqlSession.selectOne("portal.report.getToolLoginUserDetail", daoParam);
	}

	@Override
	public Map<String, String> datasetSampleFile(String datasetId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("datasetId", datasetId);
		return sqlSession.selectOne("portal.dataset.getDatasetSampleFile", daoParam);
	}

	@Override
	public Map<String, String> taskDetail(String taskId) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("taskId", taskId);
		return sqlSession.selectOne("portal.task.taskDetail", daoParam);
	}

	@Override
	public BaseAuth findMenuAuthByUser(SessionInfo sessionInfo, String appId, String menuId) {
		DaoParam daoParam = new DaoParam(sessionInfo);
		daoParam.put("appId", appId);
		daoParam.put("menuId", menuId);
		return sqlSession.selectOne("portal.common.findMenuAuthByUser", daoParam);
	}

}
