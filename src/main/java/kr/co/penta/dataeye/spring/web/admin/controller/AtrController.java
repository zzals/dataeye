package kr.co.penta.dataeye.spring.web.admin.controller;

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
import kr.co.penta.dataeye.spring.web.admin.service.AtrService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;
import kr.co.penta.dataeye.spring.web.validate.AtrForm;

@Controller
@RequestMapping(value="/admin/atr", method={RequestMethod.POST, RequestMethod.GET})
public class AtrController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private AtrService atrService;
		
	@RequestMapping(params="oper=atrIdDuplChk")
    public ResponseEntity<StdResponse> atrIdDuplChk(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String atrId = CastUtils.toString(reqParam.get("atrId"));
        if ("".equals(atrId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        boolean b = atrService.isValidAtrId(atrId);
        if (b) {
            return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("msg.atr.valid.atrid")), HttpStatus.OK);
        } else {
            return new ResponseEntity<StdResponse>(stdResponse.setFail(MessageHolder.getInstance().get("msg.atr.dupl.atrid")), HttpStatus.OK);
        }
    }     
    
    @RequestMapping(params="oper=get")
    public ResponseEntity<StdResponse> processGet(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String atrId = CastUtils.toString(reqParam.get("atrId"));
        if ("".equals(atrId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        CamelMap map = atrService.get(atrId);
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(map), HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=regist")
    public ResponseEntity<StdResponse> processAdd(HttpSession session,
            @Valid AtrForm atrForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
	    if (bindingResult.hasErrors()) {
	        LOG.debug(bindingResult.toString());
	        
	        throw new ValidationException(bindingResult);
	    }
	    atrService.regist(atrForm, SessionInfoSupport.getSessionInfo(session));
	        
	    return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }	
	
	@RequestMapping(params="oper=update")
    public ResponseEntity<StdResponse> processEdit(HttpSession session,
            Model model,
            @Valid AtrForm atrForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
	    if (bindingResult.hasErrors()) {
            LOG.debug(bindingResult.toString());
            
            throw new ValidationException(bindingResult);
        }
	    atrService.update(atrForm, SessionInfoSupport.getSessionInfo(session));
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.update.complete")), HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=remove")
    public ResponseEntity<StdResponse> processDel(HttpSession session,
            @RequestParam(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String atrId = CastUtils.toString(reqParam.get("atrId"));
        if ("".equals(atrId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        atrService.remove(atrId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.remove.complete")), HttpStatus.OK);
    }
}

