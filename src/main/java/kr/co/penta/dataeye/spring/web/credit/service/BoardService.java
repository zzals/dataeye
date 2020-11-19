package kr.co.penta.dataeye.spring.web.credit.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import kr.co.penta.dataeye.spring.web.session.SessionInfo;

public interface BoardService {

	
    Map<String, Object> getBoardList(Map<String, Object> reqParam) ;
    
    Map<String, Object> getBoardView(Map<String, Object> reqParam, String gubun) ;
    
    
    void insertBoard(Map<String, Object> param, HttpSession session) throws Exception;
    
    void insertReplyBoard(Map<String, Object> param, HttpSession session) throws Exception;
        
    void updateBoard(Map<String, Object> param, HttpSession session) throws Exception;
    
}
