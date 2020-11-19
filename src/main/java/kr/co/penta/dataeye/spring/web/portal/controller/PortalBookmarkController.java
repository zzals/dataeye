package kr.co.penta.dataeye.spring.web.portal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.portal.service.PortalBookmarkService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Controller
@RequestMapping(value="/portal/bookmark", method={RequestMethod.POST, RequestMethod.GET})
public class PortalBookmarkController {

	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	private PortalBookmarkService portalBookmarkService;
	
	@RequestMapping("/proc")
    public ResponseEntity<StdResponse> proc(HttpServletRequest request) throws IllegalStateException, IOException {
	   StdResponse stdResponse = new StdResponse();		   
	   
	   HttpSession session = request.getSession(false);
	   SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
		
	   Map<String,Object> paraMap = new HashMap<String,Object>();
	   paraMap.put("objTypeId", (String)request.getParameter("objTypeId"));
	   paraMap.put("objId", (String)request.getParameter("objId"));
	   
	   String objMngNm = portalBookmarkService.objMngNmString(paraMap);
	   
	   paraMap.put("sessionInfo", sessionInfo);
	   //paraMap.put("objMngNm", objMngNm);
	   
	   portalBookmarkService.delete(paraMap);
	   portalBookmarkService.proc(paraMap);
	
	   return new ResponseEntity<StdResponse>(stdResponse.setSuccess(null), HttpStatus.OK);
    }
	
	@RequestMapping("/update")
    public ResponseEntity<StdResponse> update(HttpServletRequest request) throws IllegalStateException, IOException {
	   StdResponse stdResponse = new StdResponse();		   
	   
	   HttpSession session = request.getSession(false);
	   SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
		
	   Map<String,Object> paraMap = new HashMap<String,Object>();
	   paraMap.put("objTypeId", (String)request.getParameter("objTypeId"));
	   paraMap.put("objId", (String)request.getParameter("objId"));
	   paraMap.put("delYn", (String)request.getParameter("delYn"));
	   paraMap.put("sessionInfo", sessionInfo);
	   
	   portalBookmarkService.update(paraMap);
	
	   return new ResponseEntity<StdResponse>(stdResponse.setSuccess(null), HttpStatus.OK);
    }
}
