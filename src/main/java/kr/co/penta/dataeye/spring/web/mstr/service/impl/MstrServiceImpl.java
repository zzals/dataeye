package kr.co.penta.dataeye.spring.web.mstr.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.spring.mybatis.dao.MstrDao;
import kr.co.penta.dataeye.spring.web.mstr.service.MstrService;

@Service
public class MstrServiceImpl implements MstrService {

	@Autowired 
	MstrDao mstrDao;
	
	@Override
	public List<Map<String, Object>> reportList(Map<String, Object> paraMap) {
		return mstrDao.reportList(paraMap);
	}

}
