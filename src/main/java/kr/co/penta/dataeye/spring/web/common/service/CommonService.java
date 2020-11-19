package kr.co.penta.dataeye.spring.web.common.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.spring.web.session.SessionInfo;

public interface CommonService {
    void insert(Map<String, Object> reqParam, SessionInfo sessionInfo);
    void insert(String queryId, Map<String, Object> reqParam, SessionInfo sessionInfo);
    void update(Map<String, Object> reqParam, SessionInfo sessionInfo);
    void update(String queryId, Map<String, Object> reqParam, SessionInfo sessionInfo);
    void delete(Map<String, Object> reqParam, SessionInfo sessionInfo);
    void delete(String queryId, Map<String, Object> reqParam, SessionInfo sessionInfo);
    
    Map<String, Object> get(String queryId, Map<String, Object> reqParam);
    List<Map<String, Object>> find(String queryId, Map<String, Object> reqParam);
}
