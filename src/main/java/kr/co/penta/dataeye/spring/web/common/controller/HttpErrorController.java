package kr.co.penta.dataeye.spring.web.common.controller;
 
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.penta.dataeye.spring.config.properties.PortalSettings;

@Controller
@RequestMapping(value="/error", method={RequestMethod.POST, RequestMethod.GET})
public class HttpErrorController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private PortalSettings portalSettings;
	
	@RequestMapping(value="/{status}")
	@ExceptionHandler(Exception.class)
	public String serviceRuntimeException(@PathVariable String status,
	        HttpServletRequest request, 
	        Exception exception) {
		printError(request);
		
        return portalSettings.getDefaultErrorPagePath()+"/"+status;
    }
	
	private void printError(HttpServletRequest request) {
		LOG.info("status_code", request.getAttribute("javax.servlet.error.status_code"));
		LOG.info("exception_type", request.getAttribute("javax.servlet.error.exception_type"));
		LOG.info("message", request.getAttribute("javax.servlet.error.message"));
		LOG.info("request_uri", request.getAttribute("javax.servlet.error.request_uri"));
		LOG.info("exception", request.getAttribute("javax.servlet.error.exception"));
		LOG.info("servlet_name", request.getAttribute("javax.servlet.error.servlet_name"));
	}
}