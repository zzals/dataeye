package kr.co.penta.dataeye.spring.web.admin.service;

import java.util.Map;

import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface MetaExpImpService {
	Map<String, Object> exp(Map<String, Object> reqParam, SessionInfo sessionInfo);
    void imp(Map<String, Object> reqParam, SessionInfo sessionInfo);
}
