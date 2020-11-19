package kr.co.penta.dataeye.spring.mybatis.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.co.penta.dataeye.spring.mybatis.dao.PortalBookmarkDao;
import kr.co.penta.dataeye.spring.mybatis.dao.support.DataEyeDaoSupport;

@Repository
public class PortalBookmarkDaoImpl extends DataEyeDaoSupport implements PortalBookmarkDao {

	@Override
	public void proc(Map<String, Object> paraMap) {
		sqlSession.insert("portal.report.bookmarkProc", paraMap);
	}

	@Override
	public String objMngNmString(Map<String, Object> paraMap) {
		return sqlSession.selectOne("portal.report.objMngNmString", paraMap);
	}

	@Override
	public void update(Map<String, Object> paraMap) {
		sqlSession.update("portal.report.bookmarkUpdate", paraMap);
	}

	@Override
	public void delete(Map<String, Object> paraMap) {
		sqlSession.delete("portal.report.bookmarkDelete", paraMap);
	}

}
