package kr.co.penta.dataeye.spring.web.system.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.system.service.SystemLogService;
import kr.co.penta.dataeye.spring.web.validate.SysLogForm;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {

	@Autowired 
	private CommonDao commonDao;

    
    @Override
    public Map<String, Object> getLogMenu(String fromData) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("fromData", fromData);

        return commonDao.selectOne("system.logMenu.getlogMenu", daoParam);
    }
    
    @Override
    public Map<String, Object> getLogUser(String fromData) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("fromData", fromData);

        return commonDao.selectOne("system.logUser.getlogUser", daoParam);
    }
    
    @Override
    public Map<String, Object> getLogObj(String fromData) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("fromData", fromData);

        return commonDao.selectOne("system.logUser.getlogObj", daoParam);
    }
	
}
