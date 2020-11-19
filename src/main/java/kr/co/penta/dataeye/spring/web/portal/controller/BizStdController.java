package kr.co.penta.dataeye.spring.web.portal.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.meta.util.TermModel;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.web.common.service.CommonService;
import kr.co.penta.dataeye.spring.web.common.service.FileService;
import kr.co.penta.dataeye.spring.web.common.service.MetaReqService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.portal.service.BizStdService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Controller
@RequestMapping(value = "/portal/biz/std", method={RequestMethod.POST, RequestMethod.GET})
public class BizStdController {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommonService commonService;

    @Autowired
    private FileService fileService;
    
    @Autowired
    private MetaReqService metaReqService;
    
    @Autowired
    private BizStdService bizStdService;

    @RequestMapping(params = "oper=wordDuplCheck")
    public ResponseEntity<StdResponse> wordDuplCheck(HttpSession session,
                                                     @RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();

        reqParam.put("wordNmDuplChkEnable", DataEyeSettingsHolder.getInstance().getBizStd().getWordNmDuplChkEnable());
        Map<String, Object> map = commonService.get("portal.biz.std.wordDuplCount", reqParam);
        final int cnt = Integer.parseInt(String.valueOf(map.get("CNT")));
        if (cnt > 0) {
            return new ResponseEntity<StdResponse>(stdResponse.setFail("표준단어가 존재합니다.\n표준단어 메뉴에서 확인해 주시기 바랍니다."), HttpStatus.OK);
        } else {
            List<Map<String, Object>> list = commonService.find("portal.biz.std.similarWords", reqParam);
            Map<String, Object> rspMap = new HashMap<>();
            rspMap.put("similarWords", list);

            return new ResponseEntity<StdResponse>(stdResponse.setSuccess(rspMap, "등록가능한 표준단어 입니다."), HttpStatus.OK);
        }
    }

    @RequestMapping(params = "oper=termVerify")
    public ResponseEntity<StdResponse> termVerify(HttpSession session,
                                                  @RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();

        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        String term = CastUtils.toString(reqParam.get("term"));

        if ("".equals(objTypeId) || "".equals(term)) {
            return new ResponseEntity<StdResponse>(stdResponse.setFail("요청이 올바르지 않습니다."), HttpStatus.OK);
        }

        List<TermModel> list = bizStdService.termVerify(objTypeId, objId, term);

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(list), HttpStatus.OK);
    }

    @RequestMapping(params = "oper=jdbcConnectTest")
    public ResponseEntity<StdResponse> jdbcConnectTest(HttpSession session,
                                                       @RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();
        try {
            bizStdService.jdbcConnectionTest(reqParam);
        }
        catch (Exception e) {
            return new ResponseEntity<StdResponse>(stdResponse.setFail("연결을 실패하였습니다."), HttpStatus.OK);
        }

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess("성공적으로 연결되었습니다."), HttpStatus.OK);
    }

    @RequestMapping(path = "/getStdTermMenu")
    public ResponseEntity<StdResponse> getStdTermMenu() {
        StdResponse stdResponse = new StdResponse();
        List<Map<String, Object>> resultList = bizStdService.getStdTermMenu();

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(resultList), HttpStatus.OK);
    }
        
    @RequestMapping(path = "/bulkWordVerify")
    public ResponseEntity<StdResponse> bulkWordVerify(HttpSession session,
                                                  @RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();

        List<Map<String, Object>>list = bizStdService.wordVerifyCheck(reqParam);

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(list), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/bulkWordImport")
    public ResponseEntity<StdResponse> bulkWordImport(HttpSession session,
                                                  @RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();

        SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
        reqParam.put("sessionInfo", sessionInfo);
        
        Map<String, Object> map = bizStdService.wordImport(reqParam);

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(map), HttpStatus.OK);
    }
 //////////////////////////
    @RequestMapping(path = "/bulkTermVerify")
    public ResponseEntity<StdResponse> bulkTermVerify(HttpSession session,
                                                  @RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();

        Map<String, Object> list = bizStdService.termVerifyCheck(reqParam);

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(list), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/bulkTermImport")
    public ResponseEntity<StdResponse> bulkTermImport(HttpSession session,
                                                  @RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
        reqParam.put("sessionInfo", sessionInfo);

        Map<String, Object> list = bizStdService.termImport(reqParam);

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(list), HttpStatus.OK);
    }
   ////////////////////
    @RequestMapping(path = "/bulkDmnVerify")
    public ResponseEntity<StdResponse> bulkDmnVerify(HttpSession session,
                                                  @RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();

        List<Map<String, Object>>list = bizStdService.dmnVerifyCheck(reqParam);

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(list), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/bulkDmnImport")
    public ResponseEntity<StdResponse> bulkDmnImport(HttpSession session,
                                                  @RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
        reqParam.put("sessionInfo", sessionInfo);

        Map<String, Object> list = bizStdService.dmnImport(reqParam);

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(list), HttpStatus.OK);
    }
    
    @RequestMapping(params = "oper=downloadWordUploadTpl")
    public void downloadWordUploadTpl(HttpServletRequest request, HttpServletResponse response) {
    	String path = DataEyeSettingsHolder.getInstance().getAttachFile().getTemplatePath();
    	String template = DataEyeSettingsHolder.getInstance().getTemplate().getStdWordUploadTemplate();
    	
    	fileService.download(request, response, path+File.separatorChar+template, template);
    }
    
    @RequestMapping(params = "oper=downloadTermUploadTpl")
    public void downloadTermUploadTpl(HttpServletRequest request, HttpServletResponse response) {
    	String path = DataEyeSettingsHolder.getInstance().getAttachFile().getTemplatePath();
    	String template = DataEyeSettingsHolder.getInstance().getTemplate().getStdTermUploadTemplate();
    	
    	fileService.download(request, response, path+File.separatorChar+template, template);
    }
    
    @RequestMapping(params = "oper=downloadDomainUploadTpl")
    public void downloadDomainUploadTpl(HttpServletRequest request, HttpServletResponse response) {
    	String path = DataEyeSettingsHolder.getInstance().getAttachFile().getTemplatePath();
    	String template = DataEyeSettingsHolder.getInstance().getTemplate().getStdDomainUploadTemplate();
    	
    	fileService.download(request, response, path+File.separatorChar+template, template);
    }
    
    @RequestMapping(params="oper=uploadDomainUploadTpl", method = RequestMethod.POST)
    public ResponseEntity<StdResponse> uploadDomainUploadTpl(MultipartHttpServletRequest request) throws JsonProcessingException {
		StdResponse stdResponse = new StdResponse();
        SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(request.getSession());

        Iterator<String> itr = request.getFileNames();
        if (itr.hasNext()) {
            MultipartFile mpf = request.getFile(itr.next());
            if (mpf.isEmpty()) {
            	throw new ServiceRuntimeException("빈파일 입니다.");
            } else {
                InputStream is = null;
                Map<String, Object> dataMap = new HashMap<>();
				try {
					is = mpf.getInputStream();
					bizStdService.dmnUpload(is);
				} catch (Exception e) {
					throw new ServiceRuntimeException("파일 포멧이 올바르지 않습니다.\n관리자에게 문의하세요.");
				} finally {
					try { is.close(); } catch (IOException e) {}
				}
            }
        }
        return new ResponseEntity<>(stdResponse.setSuccess(), HttpStatus.OK);
    }
    
    @RequestMapping(params="oper=aprvTemp", method = RequestMethod.POST)
    public ResponseEntity<StdResponse> aprv(@RequestBody(required=true) HashMap<String, Object> reqParam, HttpSession session) {
		StdResponse stdResponse = new StdResponse();
        
		String aprvTypeCd = CastUtils.toString(reqParam.get("APRV_TYPE_CD"));
		if ("".equals(aprvTypeCd)) {
            return new ResponseEntity<StdResponse>(stdResponse.setFail("요청이 올바르지 않습니다."), HttpStatus.OK);
        }
		
		Map<String, Object> res = null;
		try {
			res = metaReqService.saveMultiObjAprv(reqParam, SessionInfoSupport.getSessionInfo(session));
		} catch (Exception e) {
			throw new ServiceRuntimeException(e);
		}
				
        return new ResponseEntity<>(stdResponse.setSuccess(res), HttpStatus.OK);
    }
}

