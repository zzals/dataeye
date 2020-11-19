package kr.co.penta.dataeye.spring.web.credit.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.penta.dataeye.common.entities.meta.PenSysObjL;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.common.util.JSONUtils;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings;
import kr.co.penta.dataeye.spring.config.properties.PortalSettings;
import kr.co.penta.dataeye.spring.security.core.LoggingService;
import kr.co.penta.dataeye.spring.web.portal.service.PortalService;

@Controller
@RequestMapping(value="/credit", method={RequestMethod.POST, RequestMethod.GET})
public class CreditMainController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired LoggingService loggingService;

	@Autowired private PortalService portalService;

	@Autowired
	private PortalSettings portalSettings;
			
	@Autowired
	private DataEyeSettings dataEyeSettings;
	
	@RequestMapping("")
	public String defaultView() {
		return "redirect:"+portalSettings.getPortalMainView();
	}
	
	@RequestMapping("/main")
    public String main(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		LOG.info("portal main session id !!: {}", session.getId());
		return portalSettings.getPortalMainView();
    }
	
	@RequestMapping("/view")
    public String view(final Model model, HttpServletRequest request,
                        @RequestParam(required=true, value="viewId") final String viewId,
                        @RequestParam(required=false, value="data", defaultValue="{}") final String data
            ) {

        LOG.info("data : {}", data);
        model.addAttribute("data", data);
        HttpSession session = request.getSession(false);
        LOG.info("viewId : {}", viewId);
        LOG.info("session id !!!: {}", session.getId());
        
        if (DataEyeSettingsHolder.getInstance().getConfig().getObjectAccessLoggingEnable()) {
            Map<String, Object> dataMap = JSONUtils.getInstance().jsonToMap(data);
            String viewname = CastUtils.toString(dataMap.get("viewname"));
            String objTypeId = CastUtils.toString(dataMap.get("objTypeId"));
            String objId = CastUtils.toString(dataMap.get("objId"));
            String mode = CastUtils.toString(dataMap.get("mode"));
            
            if ("R".equals(mode) && "common/metacore/tabContent".equals(viewname)) {
                boolean isLoggingTargetObject = false;
                String[] targetObjects = DataEyeSettingsHolder.getInstance().getConfig().getObjectAccessLoggingTargetObjects();
                for(String targetObject : targetObjects) {
                    if (objTypeId.equals(targetObject)) {
                        isLoggingTargetObject = true;
                        break;
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
	
	/*
	@RequestMapping("/menu")
    public ResponseEntity<List<PortalMenuEntity>> menu(HttpSession session) {
        LOG.info("portal main session id !!: {}", session.getId());
        
        SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
        List<PortalMenuEntity> portalMenuEntities = portalService.getPortalMenu(sessionInfo, dataEyeSettings.getAppId());
        
        return new ResponseEntity<List<PortalMenuEntity>>(portalMenuEntities, HttpStatus.OK);
    }*/
}