package kr.co.penta.dataeye.spring.security.authentication;

import kr.co.penta.dataeye.spring.security.core.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
public class DataeyeWebAuthenticationDetailsSource extends WebAuthenticationDetailsSource {

    private static final Logger logger = LoggerFactory.getLogger(DataeyeWebAuthenticationDetailsSource.class);

    private static final String URL_SSO_ENTRY_POINT = "application.security.sso-entry-point";

    @Resource
    private Environment env;

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new DataeyeWebAuthenticationDetails(context);
    }

    @SuppressWarnings("serial")
    class DataeyeWebAuthenticationDetails extends WebAuthenticationDetails {

        private final String referer;
        private final boolean trustedAccess;
        private final String hostUrl;

        @Autowired
        UserService userService;

        public DataeyeWebAuthenticationDetails(HttpServletRequest request) {
            super(request);

            String trustUrl;
            String tempReferrer = request.getHeader("Referer");
            this.hostUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            this.referer = tempReferrer != null ? tempReferrer.substring(tempReferrer.indexOf("/") + 2) : "";

            if (tempReferrer != null) {
                trustUrl = request.getHeader("host") + request.getContextPath() + env.getProperty(URL_SSO_ENTRY_POINT);

                if (trustUrl.equalsIgnoreCase(this.referer)) {
                    this.trustedAccess = true;
                } else {
                    this.trustedAccess = false;
                }
            } else {
                this.trustedAccess = true;
            }

        }

        public String getReferer() {
            return referer;
        }

        public boolean isTrustedAccess() {
            return trustedAccess;
        }

        public String getHostUrl() {
            return hostUrl;
        }
    }
}