package kr.co.penta.dataeye.spring.web.credit.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.penta.dataeye.common.entities.meta.PenAprvM;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.aprv.enums.AprvCodeType;
import kr.co.penta.dataeye.spring.web.credit.service.AnaTaskService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

@Service
public class AnaTaskServiceImpl implements AnaTaskService {
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CommonDao commonDao;
    
    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveCreditAprvStsm(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		
        DaoParam daoParam = new DaoParam();
        daoParam.put("OBJ_TYPE_ID", param.get("OBJ_TYPE_ID"));
        daoParam.put("OBJ_ID", param.get("OBJ_ID"));
        daoParam.put("REQ_TYPE_CD", param.get("REQ_TYPE_CD"));
        
        daoParam.put("APRV_ID", param.get("APRV_ID"));
        daoParam.put("STATUS_CD", param.get("STATUS_CD"));
        daoParam.put("CRETR_ID", param.get("CRETR_ID"));
        
        Map<String, Object> map = commonDao.selectOne("credit_anatask.checkCreditAprvStm", daoParam);
        
        //상태 테이블에 이미 등록되어져 있다면..
        if(Integer.parseInt(map.get("CNT").toString()) >0 ) {
        	commonDao.insert("credit_anatask.updateCreditAprvStsm", daoParam);        	
        }else {
        	commonDao.insert("credit_anatask.insertCreditAprvStsm", daoParam);
        }
        
	}
	
	/**
	 * sandbox 저장시 라이센스 정보르 사전에 예약상태로 변경한다.
	 */
    @Override
    @Transactional(value = "dataeyePlatformTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateTmpSaveLicense(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		
		DaoParam daoParam = new DaoParam();
        daoParam.put("SB_OBJ_TYPE_ID", param.get("OBJ_TYPE_ID"));
        daoParam.put("SB_OBJ_ID", param.get("OBJ_ID"));
        daoParam.put("APRV_ID", param.get("APRV_ID"));
        
        String status_cd=param.get("STATUS_CD").toString();
        
        if(status_cd.equals(AprvCodeType.ACT00_0.getId())) {//저장
        	//sandbox에 할당된 라이센스가 있는지 여부를 체크한다.
            Map<String, Object> map = commonDao.selectOne("credit_anatask.getCheckLicenseMng", daoParam);
            if(Integer.parseInt(map.get("CNT").toString()) >0 ) {
            	
            	//sandbox에 할당된 모든 라이센스를 초기화 한다.
        		commonDao.insert("credit_anatask.updateCreditLicenseMngAll", daoParam);
            }
        }
        
        
        
        //사용중인 분석가 정보를 얻는다.
        List<Map<String, Object>> anaList = commonDao.selectList("credit_anatask.getUseAnaUserList", daoParam);
        
        //사용중인 tool정보를 얻는다.
        List<Map<String, Object>> toolList = commonDao.selectList("credit_anatask.getUseToolNM", daoParam);
        
        
        for(Map<String, Object> anaMap:anaList) {
        	for(Map<String, Object> toolMap:toolList) {
        		
        		//tool 라이센스 정보를 예약상태로 변경한다.
        		daoParam = new DaoParam();
                daoParam.put("SB_OBJ_TYPE_ID", param.get("OBJ_TYPE_ID"));
                daoParam.put("SB_OBJ_ID", param.get("OBJ_ID"));
                daoParam.put("TOOL_NM", toolMap.get("TOOL_NM"));
                daoParam.put("USER_ID", anaMap.get("ANA_USER"));
                
                if(status_cd.equals(AprvCodeType.ACT00_0.getId())) {//저장
                	
                	
                	commonDao.update("credit_anatask.updateCreditLicenseMng", daoParam);
                	
                }else if(status_cd.equals(AprvCodeType.ACT04_0.getId())) {//승인
                	
                	commonDao.update("credit_anatask.updateCreditLicenseMngAprvDo", daoParam);
                	
                }else if(status_cd.equals(AprvCodeType.ACT04_2.getId())) {//반려
                	
                	commonDao.update("credit_anatask.updateCreditLicenseMngAprvReject", daoParam);
                }
        		
        	}
        	
        }
        
        
	}

	@Override
	public void saveAprvObjMD(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		
		
		
		
		DaoParam daoParam = new DaoParam();
        daoParam.put("NEW_APRV_ID",param.get("NEW_APRV_ID"));
        //daoParam.put("NEW_OBJ_ID", param.get("NEW_OBJ_ID"));
        daoParam.put("APRV_ID",param.get("APRV_ID"));
        daoParam.put("CRETR_ID", param.get("CRETR_ID"));
        daoParam.put("APRV_TYPE_CD", param.get("REQ_TYPE_CD"));
        
        daoParam.put("LAST_STUS_CD", "00");
        
        
        commonDao.update("credit_anatask.insertPenAprvM", daoParam);
        commonDao.update("credit_anatask.insertPenObjMT", daoParam);
        commonDao.update("credit_anatask.insertPenObjDT", daoParam);
        
	}

	@Override
	public int checkUseAnaTask(Map<String, Object> param) {
		// TODO Auto-generated method stub
		
		
		
		return 0;
	}

}
