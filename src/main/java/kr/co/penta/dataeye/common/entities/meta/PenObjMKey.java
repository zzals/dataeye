package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.common.entities.CamelMap;

/**
 * Created by Administrator on 2014-09-04.
 */
public class PenObjMKey extends CamelMap {
    public PenObjMKey() {
        
    }
    
    public PenObjMKey(String objTypeId, String objId) {
        setObjTypeId(objTypeId);
        setObjId(objId);
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
}