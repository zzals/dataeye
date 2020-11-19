package kr.co.penta.dataeye.spring.web.portal.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.spring.mybatis.dao.PortalBookmarkDao;
import kr.co.penta.dataeye.spring.web.portal.service.PortalBookmarkService;

@Service
public class PortalBookmarkServiceImpl implements PortalBookmarkService {

	@Autowired 
	PortalBookmarkDao portalBookmarkDao;
	
	@Override
	public void proc(Map<String, Object> paraMap) {
		Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
		paraMap.put("seqId", transFormat.format(from) + UUID.randomUUID().toString().replace("-", ""));
		
		portalBookmarkDao.proc(paraMap);
	}

	@Override
	public String objMngNmString(Map<String, Object> paraMap) {
		return portalBookmarkDao.objMngNmString(paraMap);
	}

	@Override
	public void update(Map<String, Object> paraMap) {
		portalBookmarkDao.update(paraMap);
	}

	@Override
	public void delete(Map<String, Object> paraMap) {
		portalBookmarkDao.delete(paraMap);
	}

}
