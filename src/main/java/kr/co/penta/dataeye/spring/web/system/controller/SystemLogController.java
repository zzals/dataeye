package kr.co.penta.dataeye.spring.web.system.controller;

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

import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.spring.web.common.service.CommonService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.system.service.SystemLogService;
import kr.co.penta.dataeye.spring.web.system.service.SystemService;

@Controller
@RequestMapping(value = "/system", method={RequestMethod.POST, RequestMethod.GET})
public class SystemLogController {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommonService commonService;
    
    @Autowired
    private SystemService systemService;

    @Autowired
    private SystemLogService systemLogService;

    @RequestMapping(path = "/logMenu", params="oper=getLogMenu")
    public ResponseEntity<StdResponse> getLogMenu(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
         
        String fromData = CastUtils.toString(reqParam.get("fromData"));
        if ("".equals(fromData)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        Map<String, Object> map = systemLogService.getLogMenu(fromData);
               
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(map), HttpStatus.OK);
    }  
    
    @RequestMapping(path = "/logUser", params="oper=getLogUser")
    public ResponseEntity<StdResponse> getLogUser(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
         
        String fromData = CastUtils.toString(reqParam.get("fromData"));
        if ("".equals(fromData)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        Map<String, Object> map = systemLogService.getLogUser(fromData);
               
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(map), HttpStatus.OK);
    }  

    @RequestMapping(path = "/logObj", params="oper=getLogObj")
    public ResponseEntity<StdResponse> getLogObj(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        System.out.println("----------------------"+reqParam.get("fromData"));
        String fromData = CastUtils.toString(reqParam.get("fromData"));
        System.out.println("----------------------"+fromData);
        if ("".equals(fromData)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        Map<String, Object> map = systemLogService.getLogUser(fromData);
               
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(map), HttpStatus.OK);
    }  
    
} 
