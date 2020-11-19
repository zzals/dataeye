package kr.co.penta.dataeye.spring.web.system.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.exception.ValidationException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;
import kr.co.penta.dataeye.spring.web.system.service.SystemAppService;
import kr.co.penta.dataeye.spring.web.validate.ApplicationForm;

@Controller
@RequestMapping(value="/system/app", method={RequestMethod.POST, RequestMethod.GET})
public class SystemAppController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private SystemAppService systemAppService;
	
	@RequestMapping(params="oper=appIdDupCheck")
    public ResponseEntity<StdResponse> appIdDupCheck(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String appId = CastUtils.toString(reqParam.get("appId"));
        if ("".equals(appId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        boolean b = systemAppService.isValidAppId(appId);
        if (b) {
            return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("msg.app.valid.appid")), HttpStatus.OK);
        } else {
            return new ResponseEntity<StdResponse>(stdResponse.setFail(MessageHolder.getInstance().get("msg.app.dupl.appid")), HttpStatus.OK);
        }
    } 
	
	@RequestMapping(params={"oper=get"})
    public ResponseEntity<StdResponse> get(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String appId = CastUtils.toString(reqParam.get("appId"));
        if ("".equals(appId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        Map<String, Object> map = systemAppService.get(appId);
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(map), HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=regist")
    public ResponseEntity<StdResponse> regist(HttpSession session,
            @Valid ApplicationForm applicationForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
	    if (bindingResult.hasErrors()) {
	        LOG.debug(bindingResult.toString());
	        
	        throw new ValidationException(bindingResult);
	    }
	    systemAppService.regist(applicationForm, SessionInfoSupport.getSessionInfo(session));
	    
	    return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }	
	
	@RequestMapping(params="oper=update")
    public ResponseEntity<StdResponse> update(HttpSession session,
            Model model,
            @Valid ApplicationForm applicationForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
	    if (bindingResult.hasErrors()) {
            LOG.debug(bindingResult.toString());
            
            throw new ValidationException(bindingResult);
        }
	    systemAppService.update(applicationForm, SessionInfoSupport.getSessionInfo(session));
        
	    return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.update.complete")), HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=remove")
    public ResponseEntity<StdResponse> remove(HttpSession session,
            @RequestParam(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String appId = CastUtils.toString(reqParam.get("appId"));
        if ("".equals(appId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        systemAppService.remove(appId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.remove.complete")), HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=simpleList")
    public ResponseEntity<StdResponse> simpleList(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
                
        List<Map<String, Object>> list = systemAppService.getSimpleApp();
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(list), HttpStatus.OK);
    }
}

