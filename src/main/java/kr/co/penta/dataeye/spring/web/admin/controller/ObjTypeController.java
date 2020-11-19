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
import kr.co.penta.dataeye.spring.web.admin.service.ObjTypeService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;
import kr.co.penta.dataeye.spring.web.validate.ObjTypeForm;
import kr.co.penta.dataeye.spring.web.validate.UIForm;

@Controller
@RequestMapping(value="/admin/objtype", method={RequestMethod.POST, RequestMethod.GET})
public class ObjTypeController {
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ObjTypeService objTypeService;
	
	
	@RequestMapping(params="oper=objTypeIdDuplChk")
    public ResponseEntity<StdResponse> atrIdDuplChk(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        if ("".equals(objTypeId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        boolean b = objTypeService.isValidObjTypeId(objTypeId);
        if (b) {
            return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("msg.objtype.valid.objtypeid")), HttpStatus.OK);
        } else {
            return new ResponseEntity<StdResponse>(stdResponse.setFail(MessageHolder.getInstance().get("msg.objtype.dupl.objtypeid")), HttpStatus.OK);
        }
    }     
	
	
	@RequestMapping(params="oper=get")
    public ResponseEntity<StdResponse> get(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        if ("".equals(objTypeId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        CamelMap map = objTypeService.get(objTypeId);
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(map), HttpStatus.OK);
    } 
	
	@RequestMapping(params="oper=regist")
    public ResponseEntity<StdResponse> regist(HttpSession session,
            @Valid ObjTypeForm objTypeForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
        
	    if (bindingResult.hasErrors()) {
	        LOG.debug(bindingResult.toString());
	        
	        throw new ValidationException(bindingResult);
	    }
	    
	    objTypeService.regist(objTypeForm, SessionInfoSupport.getSessionInfo(session));
	    
	    return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }	
	
	@RequestMapping(params="oper=update")
    public ResponseEntity<StdResponse> update(HttpSession session,
            @Valid ObjTypeForm objTypeForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
	    if (bindingResult.hasErrors()) {
            LOG.debug(bindingResult.toString());
            
            throw new ValidationException(bindingResult);
        }
	    
	    objTypeService.update(objTypeForm, SessionInfoSupport.getSessionInfo(session));
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.update.complete")), HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=remove")
    public ResponseEntity<StdResponse> remove(HttpSession session,
            @RequestParam(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String objTypeId = CastUtils.toString(reqParam.get("OBJ_TYPE_ID"));
        if ("".equals(objTypeId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        objTypeService.remove(objTypeId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.remove.complete")), HttpStatus.OK);
    }
    
 
	@RequestMapping(params="oper=getTobObjType")
    public ResponseEntity<StdResponse> getTobObjType(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
          
        List topObjType = objTypeService.getTobObjType();
        
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(topObjType), HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=atrMgr")
    public ResponseEntity<StdResponse> atrMgr(HttpSession session,
            @RequestParam(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
	    objTypeService.atrMgr(reqParam, SessionInfoSupport.getSessionInfo(session));
	    
	    return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }	
    
}