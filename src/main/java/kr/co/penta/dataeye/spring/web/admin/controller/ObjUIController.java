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
import kr.co.penta.dataeye.spring.web.admin.service.ObjUIService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;
import kr.co.penta.dataeye.spring.web.validate.UIForm;

@Controller
@RequestMapping(value="/admin/objui", method={RequestMethod.POST, RequestMethod.GET})
public class ObjUIController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ObjUIService objUIService;
	
    @RequestMapping(params="oper=get")
    public ResponseEntity<StdResponse> get(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String uiId = CastUtils.toString(reqParam.get("uiId"));
        if ("".equals(uiId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        CamelMap map = objUIService.get(uiId);
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(map), HttpStatus.OK);
    } 
	
	@RequestMapping(params="oper=regist")
    public ResponseEntity<StdResponse> regist(HttpSession session,
            @Valid UIForm uiForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
        uiForm.genUI_ID();
	    if (bindingResult.hasErrors()) {
	        LOG.debug(bindingResult.toString());
	        
	        throw new ValidationException(bindingResult);
	    }
	    
	    objUIService.regist(uiForm, SessionInfoSupport.getSessionInfo(session));
	    
	    return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }	
	
	@RequestMapping(params="oper=update")
    public ResponseEntity<StdResponse> update(HttpSession session,
            @Valid UIForm uiForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
	    if (bindingResult.hasErrors()) {
            LOG.debug(bindingResult.toString());
            
            throw new ValidationException(bindingResult);
        }
	    
	    objUIService.update(uiForm, SessionInfoSupport.getSessionInfo(session));
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.update.complete")), HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=remove")
    public ResponseEntity<StdResponse> remove(HttpSession session,
            @RequestParam(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String uiId = CastUtils.toString(reqParam.get("uiId"));
        if ("".equals(uiId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        objUIService.remove(uiId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.remove.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(params="oper=saveMapping")
    public ResponseEntity<StdResponse> saveMapping(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        if ("".equals(objTypeId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        List<Map<String, Object>> data = (List<Map<String, Object>>)reqParam.get("data");
        
        objUIService.saveMapping(objTypeId, data, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(params="oper=copy")
    public ResponseEntity<StdResponse> copy(HttpSession session,
            @RequestParam(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String uiId = CastUtils.toString(reqParam.get("uiId"));
        String uiNm = CastUtils.toString(reqParam.get("uiNm"));
        if ("".equals(uiId) || "".equals(uiNm)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        String newUiId = "UI_"+UUID.randomUUID();
        objUIService.copy(uiId, newUiId, uiNm, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.save.complete")), HttpStatus.OK);
    }
    
}