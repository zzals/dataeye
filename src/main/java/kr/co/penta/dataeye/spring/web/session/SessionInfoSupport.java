package kr.co.penta.dataeye.spring.web.session;

import java.util.List;

import javax.servlet.http.HttpSession;

import kr.co.penta.dataeye.common.entities.PortalMenuEntity;
import kr.co.penta.dataeye.spring.security.core.User;

public class SessionInfoSupport {
    final public static String SESSION_USERINFO_NAME = "dataeye-session";
    final public static String PORTAL_MENUAUTH_NAME = "dataeye-portal-menuauth";
//    final public static String FAKE_SESSION_USERINFO_NAME = "userInfo";

    private SessionInfoSupport() {
    }

    public static void clear(final HttpSession session) {
        session.setAttribute(SESSION_USERINFO_NAME, null);
    }

    public static void setSessionInfo(final HttpSession session, final User sessionInfo) {
        session.setAttribute(SESSION_USERINFO_NAME, sessionInfo);
    }

    public static void setPortalMenuAuth(final HttpSession session, final List<PortalMenuEntity> portalMenuEntities) {
        session.setAttribute(PORTAL_MENUAUTH_NAME, portalMenuEntities);
    }
    
    public static SessionInfo getSessionInfo(final HttpSession session) {
        User user = (User) session.getAttribute(SESSION_USERINFO_NAME);
        SessionInfo sessionInfo = new SessionInfo();
        if (user == null) {
            return null;
        } else {
            sessionInfo.setUserId(user.getUserId());
            sessionInfo.setUserName(user.getUserNm());
            sessionInfo.setAdminRole(user.isAdmin());
            sessionInfo.setSystemRole(user.isSystem());
            
            return sessionInfo;
        }
    }
    
    public static User getUser(final HttpSession session) {
        User user = (User) session.getAttribute(SESSION_USERINFO_NAME);
        return user;
    }
    
    public static List<PortalMenuEntity> getPortalMenuAuth(final HttpSession session) {
    	List<PortalMenuEntity> portalMenuEntities = (List<PortalMenuEntity>) session.getAttribute(PORTAL_MENUAUTH_NAME);
        return portalMenuEntities;
    }

    public static void clearSessionInfo(final HttpSession session) {
        session.setAttribute(SESSION_USERINFO_NAME, null);
    }
}