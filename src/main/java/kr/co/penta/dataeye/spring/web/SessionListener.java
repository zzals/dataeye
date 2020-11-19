package kr.co.penta.dataeye.spring.web;


import javax.servlet.http.HttpSessionEvent;

import javax.servlet.http.HttpSessionListener;



public class SessionListener implements HttpSessionListener {
	
	
    @Override
    public void sessionCreated(HttpSessionEvent se) {
    	System.out.println("#sessionCreated");    	
        se.getSession().setMaxInactiveInterval(360*60); //세션만료60분
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }

}

