package kr.co.penta.dataeye.spring.web.portal.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.penta.dataeye.common.entities.PortalMenuEntity;
import kr.co.penta.dataeye.common.entities.meta.PenSysObjL;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.common.util.JSONUtils;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings;
import kr.co.penta.dataeye.spring.config.properties.PortalSettings;
import kr.co.penta.dataeye.spring.security.core.LoggingService;
import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.spring.web.portal.service.PortalService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Controller
@RequestMapping(value="/task", method={RequestMethod.POST, RequestMethod.GET})
public class TaskController {
	
	
	@Autowired private PortalService portalService;
	
    @RequestMapping("/taskDetail")
    public String taskDetail(final Model model, HttpServletRequest request) {
    	
    	String taskId = request.getParameter("taskId");
    	Map<String,String> taskDetail = portalService.taskDetail(taskId);
    	System.out.println("#taskDetail=" + taskDetail);
    	model.addAttribute("taskDetail",taskDetail);
    	return "portal/task/taskDetail";
    }
    
    	
}