package kr.co.penta.dataeye.spring.web.admin.service.impl;


import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.exception.AppException;
import kr.co.penta.dataeye.common.exception.BizException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.spring.web.admin.service.CodeService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.validate.CdGrpForm;
import kr.co.penta.dataeye.spring.web.validate.UIForm;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class CodeServiceImpl implements CodeService {

	@Autowired
	private CommonDao commonDao;

    @Override
    public CamelMap getCdGrp(String cdGrpId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("cdGrpId", cdGrpId);
        
        return commonDao.selectOne("code.getCdGrp", daoParam);
    }

    @Override
    public boolean isValidCdGrpId(String cdGrpId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("cdGrpId", cdGrpId);
        Integer cnt = commonDao.selectOne("code.getDuplCntByCdGrpId", daoParam);
        if (cnt == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean isValidCdId(String cdId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("cdId", cdId);
        Integer cnt = commonDao.selectOne("code.getDuplCntByCdId", daoParam);
        if (cnt == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void registCdGrp(CdGrpForm cdGrpForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(cdGrpForm), sessionInfo);
        
        commonDao.insert("PEN_CD_GRP_M.insert", daoParam);
    }

    @Override
    public void updateCdGrp(CdGrpForm cdGrpForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(cdGrpForm), sessionInfo);
        
        commonDao.insert("PEN_CD_GRP_M.update", daoParam);
    }

    @Override
    public void removeCdGrp(String cdGrpId, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.put("cdGrpId", cdGrpId);
        
        Integer cnt = commonDao.selectOne("code.getChildCntByCdGrpId", daoParam);
        if (cnt != 0) {
            throw new BizException(MessageHolder.getInstance().get("msg.cdgrp.CD_GRP_ID.child.exist"));
        }
        
        commonDao.insert("PEN_CD_GRP_M.delete", daoParam);
    }

    @Override
    @Transactional
    public void saveCd(String cdGrpId, List<Map<String, Object>> data, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("cdGrpId", cdGrpId);
        
        commonDao.insert("PEN_CD_M.delete", daoParam);
        int sortNo = 1;
        for(Map<String, Object> cdMap : data) {
            DaoParam cdDaoParam = new DaoParam(cdMap, sessionInfo);
            
            cdDaoParam.put("CD_GRP_ID", cdGrpId);
            cdDaoParam.put("DEL_YN", "N");
            cdDaoParam.put("SORT_NO", sortNo++);
            
            commonDao.insert("PEN_CD_M.insert", cdDaoParam);
        }
    }
}
