package kr.co.penta.dataeye.spring.web.admin.controller;

import java.util.List;
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

import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.spring.web.admin.service.AdminObjService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Controller
@RequestMapping(value="/admin/obj", method={RequestMethod.POST, RequestMethod.GET})
public class ObjController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private AdminObjService adminObjService;
    
	@RequestMapping(params="oper=delete")
    public ResponseEntity<StdResponse> processEdit(HttpSession session,
            @RequestBody(required=true) List<Map<String, Object>> datas) {
        StdResponse stdResponse = new StdResponse();
        
        adminObjService.delete(datas, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.remove.complete")), HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=remove")
    public ResponseEntity<StdResponse> processDel(HttpSession session,
            @RequestBody(required=true) List<Map<String, Object>> datas) {
        StdResponse stdResponse = new StdResponse();
        
        adminObjService.remove(datas, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.remove.complete")), HttpStatus.OK);
    }
}