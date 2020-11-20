package kr.co.penta.dataeye.customizing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.customizing.dao.SystemCommonSetupDao;
import kr.co.penta.dataeye.customizing.service.SystemCommonSetupService;

@Service
public class SystemCommonSetupServiceImpl implements SystemCommonSetupService {

	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SystemCommonSetupDao systemCommonSetupDao;
	
	/**
	 * 세션타임아웃 설정을 불러온다.
	 */
	@Override
	public int selectSessionTimeOut() {
		return systemCommonSetupDao.selectSessionTimeOut();
	}
	
}
