package kr.co.penta.dataeye.spring.web.portal.service.impl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.spring.mybatis.dao.PortalNoticeDao;
import kr.co.penta.dataeye.spring.web.portal.service.PortalNoticeService;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class PortalNoticeServiceImpl implements PortalNoticeService {
    @Autowired PortalNoticeDao portalNoticeDao;

	@Override
	public void insertPortalNotice(Map<String, Object> paraMap) {
		Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
		
		paraMap.put("ntc_id", transFormat.format(from) + UUID.randomUUID().toString().replace("-", ""));
		paraMap.put("file_id", transFormat.format(from) + UUID.randomUUID().toString().replace("-", ""));
		
		portalNoticeDao.insertPortalNotice(paraMap);
		if(paraMap.get("file_nm")!=null) {
			portalNoticeDao.insertPortalNoticeFile(paraMap);
		}
	}

	@Override
	public void updatePortalNotice(Map<String, Object> paraMap) {
		portalNoticeDao.updatePortalNotice(paraMap);
		if(paraMap.get("file_nm")!=null) {
			if(paraMap.get("file_id").equals("0")) {
				Date from = new Date();
		        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
				paraMap.put("file_id", transFormat.format(from) + UUID.randomUUID().toString().replace("-", ""));
				portalNoticeDao.insertPortalNoticeFile(paraMap);
			} else {
				//기존 업로드된 파일 삭제처리 로직필요
				portalNoticeDao.updatePortalNoticeFile(paraMap);	
			}		
		}
		
	}

	@Override
	public void deletePortalNotice(Map<String, Object> paraMap) {
		portalNoticeDao.deletePortalNotice(paraMap);
		portalNoticeDao.deletePortalNoticeFile(paraMap);
	}

	@Override
	public void updateNoticeReadCnt(Map<String, Object> paraMap) {
		portalNoticeDao.updateNoticeReadCnt(paraMap);
	}

	@Override
	public Map<String, String> getPortalNoticeFileInfo(String file_id) {
		return portalNoticeDao.getPortalNoticeFileInfo(file_id);
	}

	@Override
	public Map<String, String> getPortalNoticeFileInfo2(String ntc_id) {
		return portalNoticeDao.getPortalNoticeFileInfo2(ntc_id);
	}
    
	
	
}
