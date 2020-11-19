package kr.co.penta.dataeye.spring.web.portal.service;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.meta.util.TermModel;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface BizStdService {
    List<TermModel> termVerify(String objTypeId, String objId, String term);
    void jdbcConnectionTest(CamelMap parameter) throws Exception;
    List<Map<String,Object>> getStdTermMenu();
    
    List<Map<String, Object>> wordVerifyCheck(Map<String, Object> data);
    Map<String, Object> wordImport(Map<String, Object> data);
    List<Map<String, Object>> wordUpload(InputStream is);    
    
    Map<String, Object> termVerifyCheck(Map<String, Object> data);
    Map<String, Object> termImport(Map<String, Object> data);
    List<Map<String, Object>> termUpload(InputStream is);    
  
    List<Map<String, Object>> dmnVerifyCheck(Map<String, Object> data);
    Map<String, Object> dmnImport(Map<String, Object> data);
    List<Map<String, Object>> dmnUpload(InputStream is);    
}
