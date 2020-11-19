package kr.co.penta.dataeye.spring.web.admin.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.admin.service.AtrService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.validate.AtrForm;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class AtrServiceImpl implements AtrService {

	@Autowired
	private CommonDao commonDao;

    @Override
    public boolean isValidAtrId(String atrId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("atrId", atrId);
        Integer cnt = commonDao.selectOne("atr.getDuplCntByAtrId", daoParam);
        if (cnt == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CamelMap get(String atrId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("atrId", atrId);
        
        return commonDao.selectOne("atr.getAtrM", daoParam);
    }

    @Override
    public void regist(AtrForm atrForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(atrForm), sessionInfo);
        
        commonDao.insert("PEN_ATR_M.insert", daoParam);
    }

    @Override
    public void update(AtrForm atrForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(atrForm), sessionInfo);
        
        commonDao.insert("PEN_ATR_M.update", daoParam);
    }

    @Override
    public void remove(String atrId, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.put("atrId", atrId);
        
        commonDao.insert("PEN_ATR_M.delete", daoParam);
    }
}
