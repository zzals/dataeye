package kr.co.penta.dataeye.spring.web.portal.service;


import java.util.Map;


/**
 * Created by Administrator on 2016-11-30.
 */
public interface PortalNoticeService {    
    void insertPortalNotice(Map<String,Object> paraMap);
  
    
    void updatePortalNotice(Map<String,Object> paraMap);
    
    void deletePortalNotice(Map<String,Object> paraMap);  
    
    void updateNoticeReadCnt(Map<String,Object> paraMap);

    Map<String,String> getPortalNoticeFileInfo(String fileNo);
    Map<String,String> getPortalNoticeFileInfo2(String boardNo);
    
}

