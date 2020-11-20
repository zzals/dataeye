package kr.co.penta.dataeye.spring.mybatis.dao;

import java.util.Map;


public interface PortalBoardDao {
    
   int getBoardNo();
   int getBoardFileNo();
   int getBoardReplyNo();
   void insertPortalBoard(Map<String,Object> paraMap);
   
   void insertPortalBoardReply(Map<String,Object> paraMap);
   
   void insertPortalBoardFile(Map<String,Object> paraMap);
   
   void updatePortalBoard(Map<String,Object> paraMap);
   void updatePortalBoardFile(Map<String,Object> paraMap);
   
   void deletePortalBoard(Map<String,Object> paraMap);   
   void deletePortalBoardReply(Map<String,Object> paraMap);
   void deletePortalBoardFile(Map<String,Object> paraMap);
   
   void updateBoardReadCnt(Map<String,Object> paraMap);
   
   Map<String,String> getPortalBoardFileInfo(String fileNo);
   
   Map<String,String> getPortalBoardFileInfo2(String boardNo);
   
   // message 
   void insertPortalMessage(Map<String,Object> paraMap);
   
    
}
