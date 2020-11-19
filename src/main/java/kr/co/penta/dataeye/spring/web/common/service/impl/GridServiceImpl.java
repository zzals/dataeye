package kr.co.penta.dataeye.spring.web.common.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.common.entities.JqGridEntity;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings;
import kr.co.penta.dataeye.spring.mybatis.dao.GridDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.JqGridDaoParam;
import kr.co.penta.dataeye.spring.web.common.service.GridService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

@Service
public class GridServiceImpl implements GridService {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private GridDao gridDao;
        
    @Autowired
    private DataEyeSettings dataEyeSettings;
    
    @Override
    public JqGridEntity getJqGridList(Map<String, Object> reqParam, SessionInfo sessionInfo) {
        String queryId = CastUtils.toString(reqParam.get("queryId"));
        if ("".equals(queryId)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }
        
        JqGridDaoParam jqGridDaoParam = new JqGridDaoParam(reqParam, sessionInfo);
        List<Map<String, Object>> list;
        Integer totalCount;
        boolean isPaging = CastUtils.toBoolean(reqParam.get("isPaging"));
        
        if (dataEyeSettings.getJqgrid().getPagingEnble() && isPaging) {
            list = gridDao.getJqGridPagingList(queryId, jqGridDaoParam);
            totalCount = gridDao.totalCount(queryId, jqGridDaoParam);    
        } else {
            boolean isSortable = CastUtils.toBoolean(reqParam.get("sortable"));
            if (isSortable) {
                list = gridDao.getJqGridNonPagingList(queryId, jqGridDaoParam);
                totalCount = list.size();
            } else {
                list = gridDao.getJqGridList(queryId, jqGridDaoParam);
                totalCount = list.size();
            }
        }

        return new JqGridEntity(jqGridDaoParam, list, totalCount);
    }
}
