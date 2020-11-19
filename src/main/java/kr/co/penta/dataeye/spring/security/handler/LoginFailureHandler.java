package kr.co.penta.dataeye.spring.security.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.penta.dataeye.common.entities.meta.PenSysSessL;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.common.util.UrlUtils;
import kr.co.penta.dataeye.model.ResultValueObject;
import kr.co.penta.dataeye.spring.config.properties.SecuritySettings;
import kr.co.penta.dataeye.spring.security.core.LoggingService;

@Component
public class LoginFailureHandler  extends SimpleUrlAuthenticationFailureHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private SecuritySettings securitySettings;
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired LoggingService loggingService;
    
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        logger.info("onAuthenticationFailure : {}", exception.getMessage());
        MDC.put("URL", UrlUtils.urlDecode(request.getRequestURL().toString()));

        if (DataEyeSettingsHolder.getInstance().getConfig().getLoginLoggingEnable()) {
            PenSysSessL penSysSessL = new PenSysSessL(request, DataEyeSettingsHolder.getInstance().getAppId(), PenSysSessL.RSLT_CD.F, PenSysSessL.CONN_TYPE.LOGIN, PenSysSessL.LOGIN_TYPE.DIR);
            penSysSessL.setUserId(request.getParameter(securitySettings.getUsernameParameter()));
            penSysSessL.setRsltDesc(exception.getMessage());
            loggingService.loginLogging(penSysSessL);
        }
        
        if (securitySettings.getAjaxRequestValue().equals(request.getHeader(securitySettings.getAjaxRequestHeader()))) {
            this.logger.debug("AJAX request detected - sending error code.");
            ResultValueObject<String> resultValueObject = new ResultValueObject<String>("");
            resultValueObject.setError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());

            ObjectMapper om = new ObjectMapper();
            String jsonString = om.writeValueAsString(resultValueObject);

            OutputStream out = response.getOutputStream();
            out.write(jsonString.getBytes("UTF-8"));
        }
        else {
            //redirectStrategy.sendRedirect(request, response, securitySettings.getLoginPage());
            request.setAttribute("exception", exception);
            request.getRequestDispatcher("../login?evt=error").forward(request, response);
        }



//        logger.info(LoggingMessageHelper.formatAuthLoggingMessage("Login Failed : {}"), request.getParameter("username"));
    }
}