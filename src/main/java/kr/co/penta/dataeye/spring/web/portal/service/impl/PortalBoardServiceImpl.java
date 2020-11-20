package kr.co.penta.dataeye.spring.web.portal.service.impl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.spring.mybatis.dao.PortalBoardDao;
import kr.co.penta.dataeye.spring.web.portal.service.PortalBoardService;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class PortalBoardServiceImpl implements PortalBoardService {
    @Autowired PortalBoardDao portalBoardDao;
    
	
	@Override
	public void insertPortalBoard(Map<String,Object> paraMap) {
		
		Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
		
		paraMap.put("brd_id", transFormat.format(from) + UUID.randomUUID().toString().replace("-", ""));
		paraMap.put("file_id", transFormat.format(from) + UUID.randomUUID().toString().replace("-", ""));
		
		portalBoardDao.insertPortalBoard(paraMap);
		if(paraMap.get("file_nm")!=null) {
			portalBoardDao.insertPortalBoardFile(paraMap);
		}
		
	}
	
	@Override
	public void insertPortalBoardReply(Map<String,Object> paraMap) {
		
		Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
        
        String rpl_no = transFormat.format(from) + UUID.randomUUID().toString().replace("-", "");

         paraMap.put("rpl_no", rpl_no);
        if(paraMap.get("up_rpl_no")==null)
        	paraMap.put("up_rpl_no", rpl_no);
		portalBoardDao.insertPortalBoardReply(paraMap);
 	}
	
	@Override
	public void updatePortalBoard(Map<String,Object> paraMap) {		
		portalBoardDao.updatePortalBoard(paraMap);
		if(paraMap.get("file_nm")!=null) {
			if(paraMap.get("file_id").equals("0")) {
				
				Date from = new Date();
		        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
				
				paraMap.put("file_id", transFormat.format(from) + UUID.randomUUID().toString().replace("-", ""));
				portalBoardDao.insertPortalBoardFile(paraMap);
			} else {
				portalBoardDao.updatePortalBoardFile(paraMap);	
			}		
		}		
	}
	
	@Override
	public void deletePortalBoard(Map<String,Object> paraMap) {		
		portalBoardDao.deletePortalBoard(paraMap);
		portalBoardDao.deletePortalBoardReply(paraMap);
		portalBoardDao.deletePortalBoardFile(paraMap);			
	}
	
	
	@Override
	public void updateBoardReadCnt(Map<String,Object> paraMap) {		
		portalBoardDao.updateBoardReadCnt(paraMap);					
	}
	
	
	@Override
	public Map<String,String> getPortalBoardFileInfo(String file_no) {
		return portalBoardDao.getPortalBoardFileInfo(file_no);
	}
	
	@Override
	public Map<String,String> getPortalBoardFileInfo2(String brd_id) {
		return portalBoardDao.getPortalBoardFileInfo2(brd_id);
	}
	
	//message
	@Override
	public void insertPortalMessage(Map<String,Object> paraMap) {
		
		Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
		
		paraMap.put("msg_id", transFormat.format(from) + UUID.randomUUID().toString().replace("-", ""));
		
		portalBoardDao.insertPortalMessage(paraMap);
	}
	
}
