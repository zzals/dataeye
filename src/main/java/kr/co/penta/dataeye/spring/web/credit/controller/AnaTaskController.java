package kr.co.penta.dataeye.spring.web.credit.controller;

import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.PenObjReqDKey;
import kr.co.penta.dataeye.spring.web.common.service.MetaReqService;
import kr.co.penta.dataeye.spring.web.credit.service.AnaTaskService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Controller
@RequestMapping(value="/anatask", method={RequestMethod.POST, RequestMethod.GET})
public class AnaTaskController {

	private Logger LOG = LoggerFactory.getLogger(AnaTaskController.class);
	
	@Autowired
    private MetaReqService metaReqService;
	
	@Autowired
    private AnaTaskService anaTaskService;
	
	
	@RequestMapping("/objectAprv/save")    
    public ResponseEntity<StdResponse> saveObjectAprv(@RequestBody(required=true) Map<String, Object> reqParam, HttpSession session) throws Exception {
	    
		StdResponse stdResponse = new StdResponse();
		
	    CamelMap res = metaReqService.saveObjAprv(reqParam, SessionInfoSupport.getSessionInfo(session));

	    return new ResponseEntity<StdResponse>(stdResponse.setSuccess(res), HttpStatus.OK);
    }
    
	
	@RequestMapping("/sandbox/save")    
    public ResponseEntity<StdResponse> saveSandboxAprv(@RequestBody(required=true) Map<String, Object> reqParam, HttpSession session) throws Exception {
	    
		StdResponse stdResponse = new StdResponse();
		
	    CamelMap res = metaReqService.saveObjAprv(reqParam, SessionInfoSupport.getSessionInfo(session));

	    return new ResponseEntity<StdResponse>(stdResponse.setSuccess(res), HttpStatus.OK);
    }
}
