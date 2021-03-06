package kr.co.penta.dataeye.spring.web.system.controller;

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
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;
import kr.co.penta.dataeye.spring.web.system.service.SystemMenuService;
import kr.co.penta.dataeye.spring.web.validate.MenuForm;


@Controller
@RequestMapping(value="/system/menu", method={RequestMethod.POST, RequestMethod.GET})
public class SystemMenuController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SystemMenuService systemMenuService;
	    
    @RequestMapping(params="oper=getFolderMenu")
    public ResponseEntity<StdResponse> getFolderMenu(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String appId = CastUtils.toString(reqParam.get("appId"));
        if ("".equals(appId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        List<CamelMap> list = systemMenuService.getFolderMenu(appId);
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(list), HttpStatus.OK);
    } 
    
    @RequestMapping(params="oper=get")
    public ResponseEntity<StdResponse> get(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String menuId = CastUtils.toString(reqParam.get("menuId"));
        if ("".equals(menuId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        CamelMap map = systemMenuService.get(menuId);
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(map), HttpStatus.OK);
    } 
	
	@RequestMapping(params="oper=regist")
    public ResponseEntity<StdResponse> regist(HttpSession session,
            @Valid MenuForm menuForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
        menuForm.genMENU_ID();
	    if (bindingResult.hasErrors()) {
	        LOG.debug(bindingResult.toString());
	        
	        throw new ValidationException(bindingResult);
	    }
	    
	    systemMenuService.regist(menuForm, SessionInfoSupport.getSessionInfo(session));
	    
	    return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }	
	
	@RequestMapping(params="oper=update")
    public ResponseEntity<StdResponse> update(HttpSession session,
            @Valid MenuForm menuForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
	    if (bindingResult.hasErrors()) {
            LOG.debug(bindingResult.toString());
            
            throw new ValidationException(bindingResult);
        }
	    
	    systemMenuService.update(menuForm, SessionInfoSupport.getSessionInfo(session));
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.update.complete")), HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=remove")
    public ResponseEntity<StdResponse> remove(HttpSession session,
            @RequestParam(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String menuId = CastUtils.toString(reqParam.get("menuId"));
        if ("".equals(menuId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        boolean isExistChildMenu = systemMenuService.isExistChildMenu(menuId);
        if (isExistChildMenu) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("msg.menu.exist.child"));
        }
        
        systemMenuService.remove(menuId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.remove.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(params="oper=registUserGrp")
    public ResponseEntity<StdResponse> registUserGrp(HttpSession session,
            @RequestBody(required = true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();        
        
        systemMenuService.registUserGrp(reqParam, SessionInfoSupport.getSessionInfo(session));
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(), HttpStatus.OK);
    }
    
    @RequestMapping(params="oper=updateUserGrp")
    public ResponseEntity<StdResponse> updateUserGrp(HttpSession session,
            @RequestBody(required = true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();        
        
        systemMenuService.updateUserGrp(reqParam, SessionInfoSupport.getSessionInfo(session));
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(), HttpStatus.OK);        
    }
    
    @RequestMapping(params="oper=removeUserGrp")
    public ResponseEntity<StdResponse> removeUserGrp(HttpSession session,
            @RequestBody(required = true) List<Map<String, Object>> datas) {
        StdResponse stdResponse = new StdResponse();        
        
        systemMenuService.removeUserGrp(datas, SessionInfoSupport.getSessionInfo(session));
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(), HttpStatus.OK);        
    }

    /*
    @RequestMapping(params="oper=registUser")
    public ResponseEntity<StdResponse> registUser(HttpSession session,
            @RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();        
        
        systemMenuService.addMenuUser(reqParam);
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(), HttpStatus.OK);
    
    }
    
    @RequestMapping(params="oper=updateUser")
    public ResponseEntity<StdResponse> updateUser(HttpSession session,
            @RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();        
        
        systemMenuService.updMenuUser(reqParam);
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(), HttpStatus.OK);
    
    }*/
}