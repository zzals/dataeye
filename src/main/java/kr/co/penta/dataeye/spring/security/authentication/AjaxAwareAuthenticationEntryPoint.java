package kr.co.penta.dataeye.spring.security.authentication;

import kr.co.penta.dataeye.spring.config.properties.SecuritySettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(value="ajaxAwareAuthenticationEntryPoint")
public class AjaxAwareAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOG = LoggerFactory.getLogger(AjaxAwareAuthenticationEntryPoint.class);

    @Autowired
    private SecuritySettings securitySettings;
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        LOG.debug("commence");
        System.out.println("commence");
        
        if (securitySettings.getAjaxRequestValue().equals(request.getHeader(securitySettings.getAjaxRequestHeader()))) {
            LOG.debug("AJAX request detected - sending error code.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session Timeout");
        }
        else {
            String viewId = request.getParameter("viewId");
            LOG.debug("Delegating processing to parent class");
            if (viewId != null && viewId.startsWith("common/metacore")) {
                redirectStrategy.sendRedirect(request, response, "/login?evt=sessionExpired");
            }
            else {
                redirectStrategy.sendRedirect(request, response, "/login?evt=login");
            }
        }
        
    }
}
