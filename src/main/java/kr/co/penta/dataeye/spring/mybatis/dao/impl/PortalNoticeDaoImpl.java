package kr.co.penta.dataeye.spring.mybatis.dao.impl;


import java.util.Map;

import org.springframework.stereotype.Repository;


import kr.co.penta.dataeye.spring.mybatis.dao.PortalNoticeDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.mybatis.dao.support.DataEyeDaoSupport;


@Repository
public class PortalNoticeDaoImpl extends DataEyeDaoSupport implements PortalNoticeDao {


	@Override
	public void insertPortalNotice(Map<String, Object> paraMap) {
		sqlSession.insert("portal.notice.insertPortalNotice", paraMap);
		
	}

	@Override
	public void insertPortalNoticeFile(Map<String, Object> paraMap) {
		sqlSession.insert("portal.notice.insertPortalNoticeFile", paraMap);
		
	}

	@Override
	public void updatePortalNotice(Map<String, Object> paraMap) {
		sqlSession.update("portal.notice.updatePortalNotice", paraMap);
		
	}

	@Override
	public void updatePortalNoticeFile(Map<String, Object> paraMap) {
		sqlSession.update("portal.notice.updatePortalNoticeFile", paraMap);
		
	}

	@Override
	public void deletePortalNotice(Map<String, Object> paraMap) {
		sqlSession.update("portal.notice.deletePortalNotice", paraMap);
		
	}

	@Override
	public void deletePortalNoticeFile(Map<String, Object> paraMap) {
		sqlSession.update("portal.notice.deletePortalNoticeFile", paraMap);
		
	}

	@Override
	public void updateNoticeReadCnt(Map<String, Object> paraMap) {
		sqlSession.update("portal.notice.updateNoticeReadCnt", paraMap);
		
	}

	@Override
	public Map<String, String> getPortalNoticeFileInfo(String file_id) {
		DaoParam daoParam = new DaoParam();		
		daoParam.put("file_id",file_id);
		return sqlSession.selectOne("portal.notice.getPortalNoticeFileInfo",daoParam );
	}

	@Override
	public Map<String, String> getPortalNoticeFileInfo2(String ntc_id) {
		DaoParam daoParam = new DaoParam();		
		daoParam.put("ntc_id",ntc_id);
		return sqlSession.selectOne("portal.notice.getPortalNoticeFileInfo2",daoParam );
	}
}
