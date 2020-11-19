package kr.co.penta.dataeye.spring.mybatis.dao;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.meta.PenSysUserGrpM;
import kr.co.penta.dataeye.spring.security.core.User;

public interface UserDao {
	User getUser(String userId);
	List<Map<String, Object>> getActionAuth(String userId);
	List<PenSysUserGrpM> getAuthority(String userId);
	void incrementFailCount(String userId);
	void setLoginSuccess(String userId);
	void setAccountExpired(String userId);
	void setcAccountLock(String userId);
	void setCredentialsExpired(String userId);
	void setAccountDisabled(String userId);
	void updateAccountAuth(Map<String, Object> user);
}
