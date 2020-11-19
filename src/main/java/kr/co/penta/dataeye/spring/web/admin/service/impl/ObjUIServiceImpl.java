package kr.co.penta.dataeye.spring.web.admin.service.impl;


import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.admin.service.ObjUIService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.validate.UIForm;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class ObjUIServiceImpl implements ObjUIService {

	@Autowired
	private CommonDao commonDao;

    @Override
    public CamelMap get(String uiId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("uiId", uiId);
        
        return commonDao.selectOne("objui.getObjUI", daoParam);
    }

    @Override
    public void regist(UIForm uiForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(uiForm), sessionInfo);
        
        commonDao.insert("PEN_UI_M.insert", daoParam);
    }

    @Override
    public void update(UIForm uiForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(uiForm), sessionInfo);
        
        commonDao.insert("PEN_UI_M.update", daoParam);
    }

    @Override
    public void remove(String uiId, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.put("uiId", uiId);
        
        commonDao.insert("PEN_UI_M.delete", daoParam);
    }

    @Override
    @Transactional
    public void saveMapping(String objTypeId, List<Map<String, Object>> data, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId);
        
        commonDao.insert("PEN_OBJ_TYPE_UI_D.delete", daoParam);
        int sortNo = 1;
        for(Map<String, Object> mappingMap : data) {
            DaoParam mappingDaoParam = new DaoParam(mappingMap, sessionInfo);
            
            mappingDaoParam.put("OBJ_TYPE_ID", objTypeId);
            String uiIdSeq = mappingDaoParam.getString("uiIdSeq");
            if ("".equals(uiIdSeq)) {
                mappingDaoParam.put("UI_ID_SEQ", UUID.randomUUID().toString());
            }
            mappingDaoParam.put("DEL_YN", "N");
            mappingDaoParam.put("SORT_NO", sortNo++);
            
            commonDao.insert("PEN_OBJ_TYPE_UI_D.insert", mappingDaoParam);
        }
    }

    @Override
    public void copy(String uiId, String newUiId, String uiNm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.put("uiId", uiId).put("newUiId", newUiId).put("uiNm", uiNm);
        
        commonDao.insert("PEN_UI_M.copy", daoParam);
    }
}
