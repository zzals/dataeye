package kr.co.penta.dataeye.spring.context;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import kr.co.penta.dataeye.common.entities.meta.PenSysSessL;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.security.core.LoggingService;
import kr.co.penta.dataeye.spring.security.core.User;

@Component
public class LogoutListener implements ApplicationListener<SessionDestroyedEvent> {
    private final Logger LOG = LoggerFactory.getLogger(LogoutListener.class);

    private LoggingService loggingService;

    @Autowired
    public LogoutListener(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        List<SecurityContext> lstSecurityContext = event.getSecurityContexts();

        if (DataEyeSettingsHolder.getInstance().getConfig().getLogoutLoggingEnable()) {
            if (event.getSource() instanceof HttpSession) {
                HttpSession session = (HttpSession)event.getSource();
                
                PenSysSessL penSysSessL = new PenSysSessL(DataEyeSettingsHolder.getInstance().getAppId(), PenSysSessL.RSLT_CD.S, PenSysSessL.CONN_TYPE.LOGOUT, PenSysSessL.LOGIN_TYPE.DIR);
                penSysSessL.setSessId(session.getId());
                try {
                    User user = (User)session.getAttribute("userInfo");
                    if (user != null) {
                        penSysSessL.setUserId(user.getUserId());
                    }
                } catch (Exception e) {
                    LOG.error("logoutLogging exception : {}", e);
                }
                loggingService.loginLogging(penSysSessL);
            }
        }

        for (SecurityContext securityContext : lstSecurityContext) {
            User user = (User) securityContext.getAuthentication().getPrincipal();
            LOG.info("SessionDestroyEvent ! : {}", user.getUserId());
        }
    }
}