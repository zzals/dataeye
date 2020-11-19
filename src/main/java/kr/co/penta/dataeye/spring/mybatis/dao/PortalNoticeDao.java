package kr.co.penta.dataeye.spring.mybatis.dao;

import java.util.Map;


public interface PortalNoticeDao {  

   void insertPortalNotice(Map<String,Object> paraMap);
      
   void insertPortalNoticeFile(Map<String,Object> paraMap);
   
   void updatePortalNotice(Map<String,Object> paraMap);
   void updatePortalNoticeFile(Map<String,Object> paraMap);
   
   void deletePortalNotice(Map<String,Object> paraMap);   
   void deletePortalNoticeFile(Map<String,Object> paraMap);
   
   void updateNoticeReadCnt(Map<String,Object> paraMap);
   
   Map<String,String> getPortalNoticeFileInfo(String fileNo);
   
   Map<String,String> getPortalNoticeFileInfo2(String noticeNo);
   
    
}
