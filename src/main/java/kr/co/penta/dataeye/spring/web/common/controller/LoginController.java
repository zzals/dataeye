package kr.co.penta.dataeye.spring.web.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import kr.co.penta.dataeye.spring.config.properties.PortalSettings;
import kr.co.penta.dataeye.spring.security.authentication.ACCOUNT_AUTH_ERR;

@Controller
@RequestMapping(value="/login", method={RequestMethod.POST, RequestMethod.GET})
public class LoginController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private PortalSettings portalSettings;

	@RequestMapping(params="evt=login")
	public String login() {
        return portalSettings.getLoginView();
    }

	@RequestMapping(params="evt=logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			log.info("session id : {}", session.getId());
			session.invalidate();
			log.info("session is invalidate");
		}
		SecurityContextHolder.clearContext();

		return portalSettings.getLogoutView();
	}
	
	@RequestMapping(params="evt=sessionExpired")
	public String sessionExpired(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			log.info("session id : {}", session.getId());
			session.invalidate();
			log.info("session is Expired");
		}
		SecurityContextHolder.clearContext();

		return portalSettings.getExpiredView();
	}
	
	@RequestMapping(params="evt=error")
	public String error(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		SecurityContextHolder.clearContext();

		AuthenticationException exception = (AuthenticationException)request.getAttribute("exception");
		if (exception != null) {
			String errCode = exception.getMessage();
			String errMsg = "";
			
			if (ACCOUNT_AUTH_ERR.ACCOUNT_NOTFOUND.name().equals(errCode)) {
				errMsg = messageSource.getMessage(ACCOUNT_AUTH_ERR.ACCOUNT_NOTFOUND.value, null, LocaleContextHolder.getLocale());
			} else if (ACCOUNT_AUTH_ERR.ACCOUNT_EXPIRED.name().equals(errCode)) {
				errMsg = messageSource.getMessage(ACCOUNT_AUTH_ERR.ACCOUNT_EXPIRED.value, null, LocaleContextHolder.getLocale());
			} else if (ACCOUNT_AUTH_ERR.ACCOUNT_LOCKED.name().equals(errCode)) {
				errMsg = messageSource.getMessage(ACCOUNT_AUTH_ERR.ACCOUNT_LOCKED.value, null, LocaleContextHolder.getLocale());
			} else if (ACCOUNT_AUTH_ERR.ACCOUNT_DISABLED.name().equals(errCode)) {
				errMsg = messageSource.getMessage(ACCOUNT_AUTH_ERR.ACCOUNT_DISABLED.value, null, LocaleContextHolder.getLocale());
			} else if (ACCOUNT_AUTH_ERR.CREDENTIAL_EXPIRED.name().equals(errCode)) {
				errMsg = messageSource.getMessage(ACCOUNT_AUTH_ERR.CREDENTIAL_EXPIRED.value, null, LocaleContextHolder.getLocale());
			} else if (ACCOUNT_AUTH_ERR.PASSWORD_EXPIRED.name().equals(errCode)) {
				errMsg = messageSource.getMessage(ACCOUNT_AUTH_ERR.PASSWORD_EXPIRED.value, null, LocaleContextHolder.getLocale());
			}
			
			FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
			flashMap.put("err_code", errCode);
			flashMap.put("err_msg", errMsg);
		}
		
		return "redirect:/login?evt=loginError";
	}
	
	@RequestMapping(params="evt=loginError")
	public String loginError(HttpServletRequest request) {
		return portalSettings.getLoginErrorView();
	}
}