package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.common.entities.CamelMap;

public class PenObjMTKey extends CamelMap {
    public PenObjMTKey() {

    }

    public PenObjMTKey(String objTypeId, String objId, String aprvId) {
        setObjTypeId(objTypeId);
        setObjId(objId);
        setAprvId(aprvId);
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

    public String getAprvId() {
        return getString("aprvId");
    }

    public void setAprvId(String aprvId) {
        put("APRV_ID", aprvId);
    }
}
