package kr.co.penta.dataeye.spring.web.portal.service;


import java.util.Map;


/**
 * Created by Administrator on 2016-11-30.
 */
public interface PortalBoardService {    
    void insertPortalBoard(Map<String,Object> paraMap);
    
    void insertPortalBoardReply(Map<String,Object> paraMap);
    
    void updatePortalBoard(Map<String,Object> paraMap);
    
    void deletePortalBoard(Map<String,Object> paraMap);  
    
    void updateBoardReadCnt(Map<String,Object> paraMap);

    Map<String,String> getPortalBoardFileInfo(String fileNo);
    Map<String,String> getPortalBoardFileInfo2(String boardNo);
    
    //message
    void insertPortalMessage(Map<String,Object> paraMap);
    
}

