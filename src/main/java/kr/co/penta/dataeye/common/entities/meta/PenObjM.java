package kr.co.penta.dataeye.common.entities.meta;

import java.util.Map;
import java.util.UUID;

import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2014-09-04.
 */
public class PenObjM extends DaoParam {
    /**
     * 
     */
    private static final long serialVersionUID = 679793118679808538L;

    public PenObjM() {
        put("timestampFormat", DataEyeSettingsHolder.getInstance().getConfig().getDbTimestampFormat());
        put("DEL_YN", "N");
    }
    
    public PenObjM(Map<String, Object> map, SessionInfo sessionInfo) {
        this();
        if (map != null) {
        	putAll(map);
        }
        putSessionInfo(sessionInfo);
    }
    
    public PenObjM(Map<String, Object> map) {
        this(map, null);
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

    public String getAdmObjId() {
        return getString("admObjId");
    }

    public void setAdmObjId(String admObjId) {
        put("ADM_OBJ_ID", admObjId);
    }

    public String getObjNm() {
        return getString("objNm");
    }

    public void setObjNm(String objNm) {
        put("OBJ_NM", objNm);
    }

    public String getObjAbbrNm() {
        return getString("objAbbrNm");
    }

    public void setObjAbbrNm(String objAbbrNm) {
        put("OBJ_ABBR_NM", objAbbrNm);
    }

    public String getObjDesc() {
        return getString("objDesc");
    }

    public void setObjDesc(String objDesc) {
        put("OBJ_DESC", objDesc);
    }

    public String getPathObjTypeId() {
        return getString("pathObjTypeId");
    }

    public void setPathObjTypeId(String pathObjTypeId) {
        put("PATH_OBJ_TYPE_ID", pathObjTypeId);
    }

    public String getPathObjId() {
        return getString("pathObjId");
    }

    public void setPathObjId(String pathObjId) {
        put("PATH_OBJ_ID", pathObjId);
    }

    public void genObjId() {
        put("OBJ_ID", getObjTypeId()+"_"+UUID.randomUUID());
    }
}