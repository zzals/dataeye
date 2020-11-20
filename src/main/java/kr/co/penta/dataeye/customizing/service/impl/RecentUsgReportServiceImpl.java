package kr.co.penta.dataeye.customizing.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.customizing.dao.RecentUsgReportDao;
import kr.co.penta.dataeye.customizing.service.RecentUsgReportService;

@Service
public class RecentUsgReportServiceImpl implements RecentUsgReportService {

	@Autowired 
	RecentUsgReportDao recentUsgReportDao;
	
	@Override
	public void insertReport(Map<String, Object> paraMap) {
		Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
		paraMap.put("seqId", transFormat.format(from) + UUID.randomUUID().toString().replace("-", ""));
		
		recentUsgReportDao.insertReport(paraMap);
	}

}
