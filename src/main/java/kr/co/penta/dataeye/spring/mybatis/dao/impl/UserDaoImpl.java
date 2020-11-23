package kr.co.penta.dataeye.spring.mybatis.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.co.penta.dataeye.common.entities.meta.PenSysUserGrpM;
import kr.co.penta.dataeye.spring.mybatis.dao.UserDao;
import kr.co.penta.dataeye.spring.mybatis.dao.support.DataEyeDaoSupport;
import kr.co.penta.dataeye.spring.security.core.User;

@Repository
public class UserDaoImpl extends DataEyeDaoSupport implements UserDao {
	@Override
	public User getUser(String userId) {
		return sqlSession.selectOne("user.getUser", userId);
	}

	@Override
	public List<Map<String, Object>> getActionAuth(String userId) {
		return sqlSession.selectList("user.getActionAuth", userId);
	}

	@Override
	public List<PenSysUserGrpM> getAuthority(String userId) {
		return sqlSession.selectList("user.getAuthority", userId);
	}

	@Override
	public void incrementFailCount(String userId) {
		sqlSession.update("user.incrementFailCount", userId);
	}

	@Override
	public void setLoginSuccess(String userId) {
		sqlSession.update("user.setLoginSuccess", userId);
	}

	@Override
	public void setAccountExpired(String userId) {
		sqlSession.update("user.setAccountExpired", userId);
	}

	@Override
	public void setcAccountLock(String userId) {
		sqlSession.update("user.setcAccountLock", userId);
	}

	@Override
	public void setCredentialsExpired(String userId) {
		sqlSession.update("user.setCredentialsExpired", userId);
	}

	@Override
	public void setAccountDisabled(String userId) {
		sqlSession.update("user.setAccountDisabled", userId);
	}

	@Override
	public void updateAccountAuth(Map<String, Object> user) {
		sqlSession.update("PEN_USER_M.updateAccountAuth", user);
	}

	@Override
	public int pUserCnt(String sabun) {
		return sqlSession.selectOne("user.pUserCnt", sabun);
	}

	@Override
	public void insertAccountAuth(Map<String, Object> userMap) {
		sqlSession.update("PEN_USER_M.insertAccountAuth", userMap);	
	}
}
