package kr.co.penta.dataeye.spring.web.portal.service;

import java.util.Map;

public interface PortalBookmarkService {

	void proc(Map<String, Object> paraMap);

	String objMngNmString(Map<String, Object> paraMap);

	void update(Map<String, Object> paraMap);

	void delete(Map<String, Object> paraMap);

}
