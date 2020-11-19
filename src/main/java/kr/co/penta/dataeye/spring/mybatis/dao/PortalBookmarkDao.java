package kr.co.penta.dataeye.spring.mybatis.dao;

import java.util.Map;

public interface PortalBookmarkDao {

	void proc(Map<String, Object> paraMap);

	String objMngNmString(Map<String, Object> paraMap);

	void update(Map<String, Object> paraMap);

	void delete(Map<String, Object> paraMap);

}
