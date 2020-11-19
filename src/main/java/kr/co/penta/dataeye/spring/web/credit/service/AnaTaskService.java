package kr.co.penta.dataeye.spring.web.credit.service;

import java.util.Map;


import kr.co.penta.dataeye.spring.web.session.SessionInfo;

public interface AnaTaskService {

	/* 마스터 상태 테이블 저장*/
    void saveCreditAprvStsm(Map<String, Object> reqParam) throws Exception;
    
    /* 라이센스 테이블에 업데이트 한다.*/
    void updateTmpSaveLicense(Map<String, Object> param) throws Exception;
    
    /* 승인테이블, obj_m_t, d_t에 입력한다..*/
    void saveAprvObjMD(Map<String, Object> param) throws Exception;
    
    /* 사용중인 분석과제를 체크한다.*/
    int checkUseAnaTask(Map<String, Object> param);
}
