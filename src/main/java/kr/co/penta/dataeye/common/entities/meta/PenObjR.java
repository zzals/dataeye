package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2014-09-04.
 */
public class PenObjR extends DaoParam {
    /**
     * 
     */
    private static final long serialVersionUID = -5478296429568026336L;

    public PenObjR() {
        put("timestampFormat", DataEyeSettingsHolder.getInstance().getConfig().getDbTimestampFormat());
        setDelYn("N");
    }
    
    public PenObjR(CamelMap camelMap, SessionInfo sessionInfo) {
        this();
        setObjTypeId(camelMap.getString("objTypeId"));
        setObjId(camelMap.getString("objId"));
        setRelObjTypeId(camelMap.getString("relObjTypeId"));
        setRelObjId(camelMap.getString("relObjId"));
        setRelTypeId(camelMap.getString("relTypeId"));
        setObjRelAnalsCd(camelMap.getString("objRelAnalsCd"));
        putSessionInfo(sessionInfo);
    }
    
    public PenObjR(SessionInfo sessionInfo) {
        this();
        putSessionInfo(sessionInfo);
    }
    
    public String getObjTypeId() {
        return getString("objTypeId");
    }

    public void setObjTypeId(String objTypeId) {
        put("OBJ_TYPE_ID", objTypeId);
    }

    public String getObjId() {
        return getString("objId");
    }

    public void setObjId(String objId) {
        put("OBJ_ID", objId);
    }
    
    public String getRelObjTypeId() {
        return getString("relObjTypeId");
    }

    public void setRelObjTypeId(String relObjTypeId) {
        put("REL_OBJ_TYPE_ID", relObjTypeId);
    }

    public String getRelObjId() {
        return getString("relObjId");
    }

    public void setRelObjId(String relObjId) {
        put("REL_OBJ_ID", relObjId);
    }
    
    public String getDelYn() {
        return getString("delYn");
    }

    public void setDelYn(String delYn) {
        put("DEL_YN", delYn);
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

    public String getChgDt() {
        return getString("chgDt");
    }

    public void setChgDt(String chgDt) {
        put("CHG_DT", chgDt);
    }

    public String getChgrId() {
        return getString("chgrId");
    }

    public void setChgrId(String chgrId) {
        put("CHGR_ID", chgrId);
    }

    public String getRelTypeId() {
        return getString("relTypeId");
    }

    public void setRelTypeId(String relTypeId) {
        put("REL_TYPE_ID", relTypeId);
    }

    public String getObjRelAnalsCd() {
        return getString("objRelAnalsCd");
    }

    public void setObjRelAnalsCd(String objRelAnalsCd) {
        put("OBJ_REL_ANALS_CD", objRelAnalsCd);
    }
}
