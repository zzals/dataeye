package kr.co.penta.dataeye.spring.web.portal.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.penta.dataeye.common.exception.ValidationException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.web.common.service.CommonService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;
import kr.co.penta.dataeye.spring.web.validate.ApplicationForm;

@Controller
@RequestMapping(value="/portal/basesrc/app", method={RequestMethod.POST, RequestMethod.GET})
public class BasesrcAppController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private CommonService commonService;
	
	@RequestMapping(path="/process", params="oper=add")
    public ResponseEntity<StdResponse> processAdd(HttpSession session,
            @Valid ApplicationForm applicationForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
	    if (bindingResult.hasErrors()) {
	        LOG.debug(bindingResult.toString());
	        
	        throw new ValidationException(bindingResult);
	    }
	    commonService.insert("PEN_APP_M.insert", CastUtils.toMap(applicationForm), SessionInfoSupport.getSessionInfo(session));
	        
	    return new ResponseEntity<StdResponse>(stdResponse.setSuccess(), HttpStatus.OK);
    }	
	
	@RequestMapping(path="/process", params="oper=edit")
    public ResponseEntity<StdResponse> processEdit(HttpSession session,
            Model model,
            @Valid ApplicationForm applicationForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
	    if (bindingResult.hasErrors()) {
            LOG.debug(bindingResult.toString());
            
            throw new ValidationException(bindingResult);
        }
	    commonService.update("PEN_APP_M.update", CastUtils.toMap(applicationForm), SessionInfoSupport.getSessionInfo(session));
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(), HttpStatus.OK);
    }
	
	@RequestMapping(path="/process", params="oper=del")
    public ResponseEntity<StdResponse> processDel(HttpSession session,
            @RequestParam(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
	    commonService.update("PEN_APP_M.delete", reqParam, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(), HttpStatus.OK);
    }
}

