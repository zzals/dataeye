package kr.co.penta.dataeye.spring.web;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.penta.dataeye.customizing.service.SystemCommonSetupService;



public class SessionListener implements HttpSessionListener {
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private SystemCommonSetupService systemCommonSetupService;
	
	/**
	 * 세션 생성 및 세션 타임아웃 설정을 불러온다.
	 */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
    	LOG.info("#sessionCreated");
    	
    	//세션 타임아웃 설정을 불러온다.
        int sessionTimeOut = systemCommonSetupService.selectSessionTimeOut();
    	se.getSession().setMaxInactiveInterval(sessionTimeOut); //세션만료 기본 15분
    	LOG.info("sessionTimeOut : " + sessionTimeOut);
    	LOG.info("#sessionCreated");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }

}

