package kr.co.penta.dataeye.spring.web.advice;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.exception.ValidationException;
import kr.co.penta.dataeye.spring.config.properties.PortalSettings;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
    
    @Autowired
    private PortalSettings portalSettings;
    
    @Autowired
    private MessageSource messageSource;
    
    final String DEFAULT_MESSAGE = "시스템 오류가 발생 하였습니다.<br />관리자에게 문의 하세요.";
    final String DEFAULT_ERROR_PAGE = "common/error/error";
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
    @ExceptionHandler(ServiceRuntimeException.class)
    public Object serviceRuntimeException(
            HttpServletRequest request,
            HttpServletResponse response,
            ServiceRuntimeException e) throws IOException {
    	
        if (isAjaxRequest(request)) {
            LOG.error("ServiceRuntimeException {}", e);
            
            StdResponse stdResponse = new StdResponse();
            
            return new ResponseEntity<StdResponse>(stdResponse.setFail(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            LOG.error("ServiceRuntimeException {}", e);
            return new ModelAndView(DEFAULT_ERROR_PAGE).addObject("msg", e.getMessage());
        }
    }
    
    @ExceptionHandler(ValidationException.class)
    public Object validationException(
            HttpServletRequest request,
            HttpServletResponse response,
            ValidationException e) throws IOException {
        if (isAjaxRequest(request)) {
            this.LOG.error("ValidationException {}", e);
            
            StdResponse resp = new StdResponse();            
            return new ResponseEntity<StdResponse>(resp.setFail(e.getBindingResult()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            LOG.error("ServiceRuntimeException {}", e);
            return new ModelAndView(DEFAULT_ERROR_PAGE).addObject("msg", e.getBindingResult());
        }
    }
    
    
    @ExceptionHandler(java.sql.SQLException.class)
    public Object sqlException(
            HttpServletRequest request,
            HttpServletResponse response,
            java.sql.SQLException e) throws IOException {
        if (isAjaxRequest(request)) {
            LOG.error("SQLException {}", e);
            
            StdResponse resp = new StdResponse();
            return new ResponseEntity<StdResponse>(resp.setFail(DEFAULT_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            LOG.error("ServiceRuntimeException {}", e);
            return new ModelAndView(DEFAULT_ERROR_PAGE).addObject("msg", DEFAULT_MESSAGE);
        }
    }
    
    @ExceptionHandler({NullPointerException.class, ClassCastException.class})
    public Object exception1 (
            HttpServletRequest request,
            HttpServletResponse response,
            Exception e) throws IOException {
        if (isAjaxRequest(request)) {
            LOG.error("Exception {}", e);
            
            StdResponse resp = new StdResponse();
            return new ResponseEntity<StdResponse>(resp.setFail(DEFAULT_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            LOG.error("ServiceRuntimeException {}", e);
            return new ModelAndView(DEFAULT_ERROR_PAGE).addObject("msg", DEFAULT_MESSAGE);
        }
    }
    
    @ExceptionHandler({Throwable.class, Exception.class})
    public Object exception2 (
            HttpServletRequest request,
            HttpServletResponse response,
            Exception e) throws IOException {
        if (isAjaxRequest(request)) {
            LOG.error("Exception {}", e);
            
            StdResponse resp = new StdResponse();
            return new ResponseEntity<StdResponse>(resp.setFail(DEFAULT_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            LOG.error("ServiceRuntimeException {}", e);
            return new ModelAndView(DEFAULT_ERROR_PAGE).addObject("msg", DEFAULT_MESSAGE);
        }
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object NOT_FOUND (
            HttpServletRequest request,
            HttpServletResponse response,
            Exception e) throws IOException {
        if (isAjaxRequest(request)) {
            LOG.error("Exception {}", e);
            
            StdResponse resp = new StdResponse();
            return new ResponseEntity<StdResponse>(resp.setFail(DEFAULT_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            LOG.error("ServiceRuntimeException {}", e);
            return new ModelAndView(DEFAULT_ERROR_PAGE).addObject("msg", DEFAULT_MESSAGE);
        }
    }
    
    private boolean isAjaxRequest(HttpServletRequest request) {
    	if (portalSettings.getHttpHeaderAjaxRequestValue().equals(request.getHeader(portalSettings.getHttpHeaderAjaxRequestHeader()))) {
//    		String accept = request.getHeader("Accept");
//    		if(accept != null && accept.indexOf("text/html") == -1) {
    			return true;
//    		} else {
//    			return false;
//    		}
    	} else {
    		return false;
    	}
    }
}