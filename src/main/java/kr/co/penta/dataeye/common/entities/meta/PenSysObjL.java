package kr.co.penta.dataeye.common.entities.meta;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

/**
 * Created by Administrator on 2014-09-04.
 */
public class PenSysObjL extends DaoParam {
    /**
     * 
     */
    private static final long serialVersionUID = -5478296429568026336L;

    public enum RSLT_CD {
        S, F
    }
    
    protected PenSysObjL() {
        put("OBJ_REQ_UUID", "OBJ_REQ_"+UUID.randomUUID().toString());
        put("timestampFormat", DataEyeSettingsHolder.getInstance().getConfig().getDbTimestampFormat());
    }
    
    public PenSysObjL(String appId, RSLT_CD rsltCd) {
        this();
        setAppId(appId);
        setRsltCd(rsltCd.name());
    }
    
    public PenSysObjL(HttpServletRequest request, String appId, String objTypeId, String objId, RSLT_CD rsltCd) {
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
        setObjTypeID(objTypeId);
        setObjId(objId);
        setRsltCd(rsltCd.name());
    }
    
    public String getObjReqUuid() {
        return getString("objReqUuid");
    }

    public void setObjReqUuid(String objReqUuid) {
        put("OBJ_REQ_UUID", objReqUuid);
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

    public String getObjTypeId() {
        return getString("objTypeId");
    }

    public void setObjTypeID(String objTypeId) {
        put("OBJ_TYPE_ID", objTypeId);
    }

    public String getObjId() {
        return getString("objId");
    }

    public void setObjId(String objId) {
        put("OBJ_ID", objId);
    }

    public String getReqDt() {
        return getString("reqDt");
    }

    public void setReqDt(String reqDt) {
        put("REQ_DT", reqDt);
    }

    public String getEndDt() {
        return getString("endDt");
    }

    public void setEndDt(String endDt) {
        put("END_DT", endDt);
    }

    public String getRefeInfo() {
        return getString("refeInfo");
    }

    public void setRefeInfo(String refeInfo) {
        put("REFE_INFO", refeInfo);
    }

    public String getRsltCd() {
        return getString("rsltCd");
    }

    public void setRsltCd(String rsltCd) {
        put("RSLT_CD", rsltCd);
    }
}
