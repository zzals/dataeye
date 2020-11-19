package kr.co.penta.dataeye.common.entities.meta;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.filter.utils.FilterMenuEntity;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

/**
 * Created by Administrator on 2014-09-04.
 */
public class PenSysMenuL extends DaoParam {
    /**
	 * 
	 */
	private static final long serialVersionUID = -9201572235796562723L;
    
    protected PenSysMenuL() {
        put("MENU_REQ_UUID", "MENU_REQ_"+UUID.randomUUID().toString());
        put("timestampFormat", DataEyeSettingsHolder.getInstance().getConfig().getDbTimestampFormat());
    }
    
    public PenSysMenuL(String appId, Integer httpStatus) {
        this();
        setAppId(appId);
        setHttpStatus(httpStatus);
    }
    
    public PenSysMenuL(HttpServletRequest request, String appId, FilterMenuEntity filterMenuEntity, Integer httpStatus) {
        this(request, appId, filterMenuEntity.menuId, filterMenuEntity.menuNm, filterMenuEntity.url, filterMenuEntity.viewId, filterMenuEntity.queryStr, httpStatus);
    }
    
    public PenSysMenuL(HttpServletRequest request, String appId, String menuId, String menuNm, String url, String viewId, String queryStr, Integer httpStatus) {
        this();
        
        setSessId(request.getSession().getId());
        setSrvPort(CastUtils.toString(request.getLocalPort()));
        setSrvIp(request.getLocalAddr());
        setRefeInfo(request.getHeader("referer"));
        String clientIp = request.getHeader("X-FORWARDED-FOR");
        if (clientIp == null)
            clientIp = request.getRemoteAddr();
        setUserIp(clientIp);
        
        SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(request.getSession());
        putSessionInfo(sessionInfo);
        
        setAppId(appId);
        setMenuId(menuId);
        setMenuNm(menuNm);
        setUrl(url);
        setViewId(viewId);
        setQueryStr(queryStr);
        setHttpStatus(httpStatus);
    }
    
    public String getMenuReqUuid() {
        return getString("menuReqUuid");
    }

    public void setMenuReqUuid(String menuReqUuid) {
        put("MENU_REQ_UUID", menuReqUuid);
    }

    public String getCretDt() {
        return getString("cretDt");
    }

    public void setCretDt(String cretDt) {
        put("CRET_DT", cretDt);
    }

    public String getCretrId() {
        return getString("cretrId");
    }

    public void setCretrId(String cretrId) {
        put("CRETR_ID", cretrId);
    }

    public String getAppId() {
        return getString("appId");
    }

    public void setAppId(String appId) {
        put("APP_ID", appId);
    }

    public String getUserId() {
        return getString("userId");
    }

    public void setUserId(String userId) {
        put("USER_ID", userId);
    }

    public String getSessId() {
        return getString("sessId");
    }

    public void setSessId(String sessId) {
        put("SESS_ID", sessId);
    }

    public String getUserIp() {
        return getString("userIp");
    }

    public void setUserIp(String userIp) {
        put("USER_IP", userIp);
    }

    public String getSrvIp() {
        return getString("srvIp");
    }

    public void setSrvIp(String srvIp) {
        put("SRV_IP", srvIp);
    }

    public String getSrvPort() {
        return getString("srvPort");
    }

    public void setSrvPort(String srvPort) {
        put("SRV_PORT", srvPort);
    }

    public String getMenuId() {
        return getString("menuId");
    }

    public void setMenuId(String menuId) {
        put("MENU_ID", menuId);
    }

    public String getMenuNm() {
        return getString("menuNm");
    }

    public void setMenuNm(String menuNm) {
        put("MENU_NM", menuNm);
    }

    public String getUrl() {
        return getString("url");
    }

    public void setUrl(String url) {
        put("URL", url);
    }

    public String getViewId() {
        return getString("viewId");
    }

    public void setViewId(String viewId) {
        put("VIEW_ID", viewId);
    }

    public String getQueryStr() {
        return getString("queryStr");
    }

    public void setQueryStr(String queryStr) {
        put("QUERY_STR", queryStr);
    }

    public String getReqDt() {
        return getString("reqDt");
    }

    public void setReqDt(String reqDt) {
        put("REQ_DT", reqDt);
    }

    public String getRefeInfo() {
        return getString("refeInfo");
    }

    public void setRefeInfo(String refeInfo) {
        put("REFE_INFO", refeInfo);
    }

    public Integer getHttpStatus() {
        return getInteger("httpStatus");
    }

    public void setHttpStatus(Integer httpStatus) {
        put("HTTP_STATUS", httpStatus);
    }
}
