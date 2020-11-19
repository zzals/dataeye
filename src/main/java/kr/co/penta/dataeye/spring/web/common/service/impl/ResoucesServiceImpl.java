package kr.co.penta.dataeye.spring.web.common.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.common.entities.meta.PenCdEntity;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.web.common.service.ResourcesService;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class ResoucesServiceImpl implements ResourcesService {
    private static final Logger LOG = LoggerFactory.getLogger(ResoucesServiceImpl.class);
    
    @Autowired private CommonDao commonDao;
    
    @Override
    public List<PenCdEntity> findAllCdByGroup() {
        return commonDao.selectList("common.findAllCdByGroup", null);
    }
    
    @Override
    public List<Map<String, Object>> getObjTypeInfo() {
        return commonDao.selectList("common.objTypeInfo", null);
    }
}
