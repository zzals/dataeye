package kr.co.penta.dataeye.spring.web.system.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.penta.dataeye.common.entities.BaseAuth;
import kr.co.penta.dataeye.common.entities.meta.PenSysObjL;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.common.util.JSONUtils;
import kr.co.penta.dataeye.spring.config.properties.PortalSettings;
import kr.co.penta.dataeye.spring.security.core.LoggingService;
import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Controller
@RequestMapping(value="/system", method={RequestMethod.POST, RequestMethod.GET})
@Secured(value={"ROLE_SYSTEM"})
public class SystemMainController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired LoggingService loggingService;

	@Autowired
	private PortalSettings portalSettings;
			
	@RequestMapping("")
	public String defaultView() {
		return "redirect:"+portalSettings.getSystemMainView();
	}
	
	@RequestMapping("/main")
    public String main(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		LOG.info("portal main session id !!: {}", session.getId());
		return portalSettings.getSystemMainView();
    }
	
	@RequestMapping("/view")
    public String view(final Model model, HttpServletRequest request,
                        @RequestParam(required=true, value="viewId") final String viewId,
                        @RequestParam(required=false, value="data", defaultValue="{}") final String data
            ) {
        HttpSession session = request.getSession(false);
		SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);

        LOG.info("data : {}", data);
        model.addAttribute("data", data);
        LOG.info("viewId : {}", viewId);
        LOG.info("session id !!!: {}", session.getId());

        if (sessionInfo.isSystemRole()) {
        	BaseAuth baseAuth = new BaseAuth();
        	baseAuth.setAllAuth();
        	String authJson = JSONUtils.getInstance().objectToJson(baseAuth);
        	model.addAttribute("AUTH_JSON", authJson);
        } else {
        	throw new ServiceRuntimeException(MessageHolder.getInstance().get("access.denied"));
        }
        
        if (DataEyeSettingsHolder.getInstance().getConfig().getObjectAccessLoggingEnable()) {
            Map<String, Object> dataMap = JSONUtils.getInstance().jsonToMap(data);
            String viewname = CastUtils.toString(dataMap.get("viewname"));
            String objTypeId = CastUtils.toString(dataMap.get("objTypeId"));
            String objId = CastUtils.toString(dataMap.get("objId"));
            String mode = CastUtils.toString(dataMap.get("mode"));
            
            if ("R".equals(mode) && "common/metacore/objectInfoTab".equals(viewname)) {
                boolean isLoggingTargetObject = false;
                String[] targetObjects = DataEyeSettingsHolder.getInstance().getConfig().getObjectAccessLoggingTargetObjects();
                for(String targetObject : targetObjects) {
                    if (objTypeId.equals(targetObject)) {
                        isLoggingTargetObject = true;
                    }
                }
                
                if (isLoggingTargetObject) {
                   PenSysObjL penSysObjL = new PenSysObjL(request, DataEyeSettingsHolder.getInstance().getAppId(), objTypeId, objId, PenSysObjL.RSLT_CD.S);
                   loggingService.objectAccessLogging(penSysObjL);
                }
            }
        }
        
        return viewId;
    }
}
