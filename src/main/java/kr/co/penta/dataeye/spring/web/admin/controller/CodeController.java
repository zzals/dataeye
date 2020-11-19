package kr.co.penta.dataeye.spring.web.admin.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
import kr.co.penta.dataeye.spring.web.admin.service.CodeService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;
import kr.co.penta.dataeye.spring.web.validate.CdGrpForm;
import kr.co.penta.dataeye.spring.web.validate.UIForm;

@Controller
@RequestMapping(value="/admin/code", method={RequestMethod.POST, RequestMethod.GET})
public class CodeController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CodeService codeService;
	
    @RequestMapping(params={"oper=get", "type=cdGrp"})
    public ResponseEntity<StdResponse> get(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String cdGrpId = CastUtils.toString(reqParam.get("cdGrpId"));
        if ("".equals(cdGrpId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        CamelMap map = codeService.getCdGrp(cdGrpId);
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(map), HttpStatus.OK);
    }	
    
    @RequestMapping(params="oper=cdGrpIdDupCheck")
    public ResponseEntity<StdResponse> atrIdDuplChk(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String cdGrpId = CastUtils.toString(reqParam.get("cdGrpId"));
        if ("".equals(cdGrpId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        boolean b = codeService.isValidCdGrpId(cdGrpId);
        if (b) {
            return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("msg.cdgrp.valid.cdgrpid")), HttpStatus.OK);
        } else {
            return new ResponseEntity<StdResponse>(stdResponse.setFail(MessageHolder.getInstance().get("msg.cdgrp.valid.cdgrpid")), HttpStatus.OK);
        }
    }  
    
	@RequestMapping(params={"oper=regist", "type=cdGrp"})
    public ResponseEntity<StdResponse> regist(HttpSession session,
            @Valid CdGrpForm cdGrpForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
        if (bindingResult.hasErrors()) {
	        LOG.debug(bindingResult.toString());
	        
	        throw new ValidationException(bindingResult);
	    }
	    
	    codeService.registCdGrp(cdGrpForm, SessionInfoSupport.getSessionInfo(session));
	    
	    return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }	
	
	@RequestMapping(params={"oper=update", "type=cdGrp"})
    public ResponseEntity<StdResponse> update(HttpSession session,
            @Valid CdGrpForm cdGrpForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
	    if (bindingResult.hasErrors()) {
            LOG.debug(bindingResult.toString());
            
            throw new ValidationException(bindingResult);
        }
	    
	    codeService.updateCdGrp(cdGrpForm, SessionInfoSupport.getSessionInfo(session));
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.update.complete")), HttpStatus.OK);
    }
	
	@RequestMapping(params={"oper=remove", "type=cdGrp"})
    public ResponseEntity<StdResponse> remove(HttpSession session,
            @RequestParam(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String cdGrpId = CastUtils.toString(reqParam.get("cdGrpId"));
        if ("".equals(cdGrpId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        codeService.removeCdGrp(cdGrpId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.remove.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(params={"oper=save", "type=cd"})
    public ResponseEntity<StdResponse> saveMapping(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String cdGrpId = CastUtils.toString(reqParam.get("cdGrpId"));
        if ("".equals(cdGrpId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        List<Map<String, Object>> data = (List<Map<String, Object>>)reqParam.get("data");
        
        codeService.saveCd(cdGrpId, data, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }
}