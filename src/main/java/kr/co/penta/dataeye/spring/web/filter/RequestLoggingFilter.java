package kr.co.penta.dataeye.spring.web.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import kr.co.penta.dataeye.common.entities.meta.PenSysMenuL;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.security.core.LoggingService;
import kr.co.penta.dataeye.spring.web.filter.utils.FilterMenuEntity;
import kr.co.penta.dataeye.spring.web.filter.utils.MenuAccessLoggingMap;

public class RequestLoggingFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private boolean menuAccessLoggingEnable;
    
    @Autowired LoggingService loggingService;
    
	public RequestLoggingFilter(boolean menuAccessLoggingEnable) {
		this.menuAccessLoggingEnable = menuAccessLoggingEnable;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if (menuAccessLoggingEnable) {
			ServletContext servletContext = filterConfig.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			
			loggingService = webApplicationContext.getBean(LoggingService.class);
			CommonDao commonDao = webApplicationContext.getBean(CommonDao.class);
			List<FilterMenuEntity> filterMenuEntities = commonDao.selectList("portal.common.findMenuAccessLoggingMap", null);
			MenuAccessLoggingMap menuAccessLoggingMap = MenuAccessLoggingMap.getInstance();
			menuAccessLoggingMap.init(filterMenuEntities);
		}
		
		logger.info("RequestLoggingFilter init.");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (menuAccessLoggingEnable) {
			HttpServletRequest httpServletRequest = (HttpServletRequest)request;		
			HttpServletResponse httpServletResponse = (HttpServletResponse)response;
			
			String servletPath = httpServletRequest.getServletPath();
			String viewId = StringUtils.defaultString(httpServletRequest.getParameter("viewId"), "");
			String queryStr = requestParams2String(httpServletRequest);
			if (FilterMenuEntity.URL_PATTRENS.PORTAL_MAIN.value().equals(servletPath)) {
				logger.debug("포탈 메인 => {}", httpServletRequest.getRequestURI());
			} else if (FilterMenuEntity.URL_PATTRENS.ADMIN_MAIN.value().equals(servletPath)) {
				logger.debug("어드민 메인 => {}", httpServletRequest.getRequestURI());
			} else if (FilterMenuEntity.URL_PATTRENS.SYSTEM_MAIN.value().equals(servletPath)) {
				logger.debug("시스템 메인 => {}", httpServletRequest.getRequestURI());
			} else if (FilterMenuEntity.URL_PATTRENS.PORTAL_VIEW.value().equals(servletPath)) {
				logger.debug("포탈 메인 => {}", httpServletRequest.getRequestURI());
			} else if (FilterMenuEntity.URL_PATTRENS.ADMIN_VIEW.value().equals(servletPath)) {
				logger.debug("포탈 메인 => {}", httpServletRequest.getRequestURI());
			} else if (FilterMenuEntity.URL_PATTRENS.SYSTEM_VIEW.value().equals(servletPath)) {
				logger.debug("포탈 메인 => {}", httpServletRequest.getRequestURI());
			} else {
				
			}
			
			chain.doFilter(request, response);
			
			FilterMenuEntity filterMenuEntity = MenuAccessLoggingMap.getInstance().get(servletPath, viewId);
			if (null == filterMenuEntity) {
				filterMenuEntity = new FilterMenuEntity("undefined_menu_id", "undefined_menu_nm", servletPath, viewId, queryStr);
			}
			
			filterMenuEntity.queryStr = queryStr;
			PenSysMenuL penSysMenuL = new PenSysMenuL(httpServletRequest, DataEyeSettingsHolder.getInstance().getAppId(), filterMenuEntity, httpServletResponse.getStatus());
			loggingService.menuAccessLogging(penSysMenuL);			
		} else {
			chain.doFilter(request, response);
		}
	}
	
	private String requestParams2String(ServletRequest request) {
	    StringBuilder sb = new StringBuilder(); 
		Map<String, String[]> params = request.getParameterMap();
		boolean isFirst = true;
	    for (Iterator<String> it=params.keySet().iterator(); it.hasNext();) {
	        String k = it.next();
	        String[] v = params.get(k);
	        if (v==null || v.length==0) {
	            sb.append(isFirst?"":"&").append(k).append("=");
		        isFirst = false;
	            continue;
	        } else {
	        	for(String value : v) {
	        		sb.append(isFirst?"":"&").append(k).append("=").append(value);	    	        
	    	        isFirst = false;
	        	}
	        }
	    }
	    return sb.toString();
	}
	
	@Override
	public void destroy() {
		logger.info("RequestLoggingFilter destroy.");
	}
}
