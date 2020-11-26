package kr.co.penta.dataeye.customizing.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.co.penta.dataeye.customizing.dao.RecentUsgReportDao;
import kr.co.penta.dataeye.spring.mybatis.dao.support.DataEyeDaoSupport;

@Repository
public class RecentUsgReportDaoImpl extends DataEyeDaoSupport implements RecentUsgReportDao {

	@Override
	public void insertReport(Map<String, Object> paraMap) {
		sqlSession.insert("recentUsgReport.insertReport", paraMap);
	}

}
