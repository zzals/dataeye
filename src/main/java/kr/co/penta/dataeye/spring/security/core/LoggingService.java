package kr.co.penta.dataeye.spring.security.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.penta.dataeye.common.entities.meta.PenSysMenuL;
import kr.co.penta.dataeye.common.entities.meta.PenSysObjL;
import kr.co.penta.dataeye.common.entities.meta.PenSysSessL;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;

@Service
@Transactional(propagation= Propagation.SUPPORTS, readOnly=true)
public class LoggingService {
    private final Logger LOG = LoggerFactory.getLogger(LoggingService.class);

    private CommonDao commonDao;

    @Autowired
    public LoggingService(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    @Async
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void loginLogging(PenSysSessL penSysSessL) {
        try {
            commonDao.insert("PEN_SYS_SESS_L.insert", penSysSessL);
        }
        catch(Exception e) {
            LOG.error("loginLogging exception : {}", e);
        }
    }

    @Async
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void objectAccessLogging(PenSysObjL penSysObjL) {
        try {
            commonDao.insert("PEN_SYS_OBJ_L.insert", penSysObjL);
        }
        catch(Exception e) {
            LOG.error("object access logging exception : {}", e);
        }
    }

    @Async
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    public void menuAccessLogging(PenSysMenuL penSysMenuL) {
        try {
            commonDao.insert("PEN_SYS_MENU_L.insert", penSysMenuL);
        }
        catch(Exception e) {
            LOG.error("menu access logging exception : {}", e);
        }
    }
}
