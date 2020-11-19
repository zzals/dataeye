package kr.co.penta.dataeye.spring.web.admin.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.validate.ObjTypeForm;
import kr.co.penta.dataeye.spring.web.validate.UIForm;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface ObjTypeService {
    boolean isValidObjTypeId(String atrId);
    CamelMap get(String objTypeId);
    void regist(ObjTypeForm objTypeForm, SessionInfo sessionInfo);
    void update(ObjTypeForm objTypeForm, SessionInfo sessionInfo);
    void remove(String uiId, SessionInfo sessionInfo);    
    List getTobObjType();
    void atrMgr(Map<String, Object> paramMap, SessionInfo sessionInfo);
    
}
