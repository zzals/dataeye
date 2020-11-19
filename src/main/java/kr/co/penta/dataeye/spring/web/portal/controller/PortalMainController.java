package kr.co.penta.dataeye.spring.web.portal.controller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.penta.dataeye.common.entities.PortalMenuEntity;
import kr.co.penta.dataeye.common.entities.meta.PenSysObjL;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.AES256Util;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.common.util.JSONUtils;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings;
import kr.co.penta.dataeye.spring.config.properties.PortalSettings;
import kr.co.penta.dataeye.spring.security.core.LoggingService;
import kr.co.penta.dataeye.spring.security.core.User;
import kr.co.penta.dataeye.spring.security.core.UserService;
import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.portal.service.PortalService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Controller
@RequestMapping(value="/portal", method={RequestMethod.POST, RequestMethod.GET})
public class PortalMainController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired LoggingService loggingService;
    
    @Autowired 
    private UserService userService;

	@Autowired private PortalService portalService;
	
	@Autowired 
    private PasswordEncoder passwordEncoder;

	@Autowired
	private DataEyeSettings dataEyeSettings;

	@Autowired
	private PortalSettings portalSettings;
			
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
	
	@RequestMapping("/main2")
    public String main2(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		LOG.info("portal main session id !!: {}", session.getId());
		return portalSettings.getPortalMainView()+2;
    }
	
	@RequestMapping("/view")
    public ModelAndView view(final ModelAndView modelAndView, HttpServletRequest request,
                        @RequestParam(required=true, value="viewId") final String viewId,
                        @RequestParam(required=false, value="menuId") final String menuId,
                        @RequestParam(required=false, value="data", defaultValue="{}") final String data
            ) {
		ModelMap model = modelAndView.getModelMap();
        HttpSession session = request.getSession(false);
		SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
		
		model.addAttribute("data", data);
        LOG.debug("data : {}", data);
        model.addAttribute("viewId", viewId);
        LOG.debug("viewId : {}", viewId);
        model.addAttribute("menuId", menuId);
        LOG.debug("menuId : {}", menuId);
        
        //유저 아이디 추가
        model.addAttribute("userId", sessionInfo.getUserId());
        LOG.debug("userId : {}", sessionInfo.getUserId());
        
        Map<String, Object> dataMap = JSONUtils.getInstance().jsonToMap(data);
        
        // 조회수 체크를 위한? 	
        if(viewId.indexOf("boardDetail")>0) {
        	try {
        		if(dataMap.get("ntc_id")==null) {
	        		String boardAuthKey = (new AES256Util(session.getId())).encrypt( dataMap.get("brd_id") + "" );        		
					model.addAttribute("boardAuthKey",boardAuthKey );
        		} else {
        			String boardAuthKey = (new AES256Util(session.getId())).encrypt( dataMap.get("ntc_id") + "" );        		
					model.addAttribute("boardAuthKey",boardAuthKey );
        		}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        // Auth Check
        PortalMenuEntity portalMenuEntity =  new PortalMenuEntity();
        if("common/metacore/objectInfoTab".equals(viewId) || "common/metacore/tabContent".equals(viewId)) {
        	String mode =  (String)dataMap.get("mode");
            if (null == mode || "".equals(mode)) {
            	mode = "R";
            }
        	portalMenuEntity.setMode(mode);
        } else {
        	portalMenuEntity = portalService.getMenuAuth(session, menuId);
        	
	    	if (menuId == null || "".equals(menuId)) {
	    		dataMap.put("mode", "R");
	    	} else {
	    		
	        	if (!"Y".equals(portalMenuEntity.getRAuth())) {
		        	LOG.debug(portalMenuEntity.toString());
		        	throw new ServiceRuntimeException(MessageHolder.getInstance().get("access.denied"));
		        } else {
		        	model.addAttribute("data", JSONUtils.getInstance().objectToJson(dataMap));
		        }
	    	}
        }
        
        model.addAttribute("AUTH", portalMenuEntity); 
		model.addAttribute("AUTH_JSON", JSONUtils.getInstance().objectToJson(portalMenuEntity));
        
        if (DataEyeSettingsHolder.getInstance().getConfig().getObjectAccessLoggingEnable()) {
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
        
        modelAndView.setViewName(viewId);
        
        return modelAndView;
    }
	
	
	
	@RequestMapping("/menu")
    public ResponseEntity<List<PortalMenuEntity>> menu(HttpSession session) {
        LOG.info("portal main session id !!: {}", session.getId());
        
        SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
        List<PortalMenuEntity> portalMenuEntities = portalService.getPortalMenu(sessionInfo, dataEyeSettings.getAppId());
        SessionInfoSupport.setPortalMenuAuth(session, portalMenuEntities);
        
        return new ResponseEntity<List<PortalMenuEntity>>(portalMenuEntities, HttpStatus.OK);
    }
	
	@RequestMapping(path = "/myPwd", params="oper=updateMyPwd")
    public ResponseEntity<StdResponse> updateMyPwd(HttpSession session,
            @RequestParam(required=true) Map<String, Object> reqParam) {
		
		
		SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);

        StdResponse stdResponse = new StdResponse();
        
        String userPassword       = CastUtils.toString(reqParam.get("USER_PASSWORD"));
        String userNewPassword    = CastUtils.toString(reqParam.get("USER_NEW_PASSWORD"));
        String userNewPasswordChk = CastUtils.toString(reqParam.get("USER_NEW_PASSWORD_CHK"));
       
        /* 기존비밀번호 입력여부 확인 */
        if ("".equals(userPassword)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.userpassword.parameter"));
        }
        if ("".equals(userNewPassword)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.new_userpassword.parameter"));
        }
        if ("".equals(userNewPasswordChk)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.new_userpassword_chk.parameter"));
        }

        /* 신규비밀번호 및 신규비밀번호재확인이 서로 일치하는지 확인 */
        if (!userNewPassword.equals(userNewPasswordChk)) {
        	throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.no_new_password.parameter"));
        }
        
        User user = userService.loadUserByUsername(sessionInfo.getUserId());
        
        
        try {
            if (passwordEncoder.matches(userPassword, user.getPassword())) {
            	
            	String hashpw = BCrypt.hashpw(userNewPassword, BCrypt.gensalt(12));
            	portalService.updateMyPwd(sessionInfo.getUserId(), hashpw, SessionInfoSupport.getSessionInfo(session));
        	    return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.update.complete")), HttpStatus.OK);	
	    	} else {
	    		throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.not_match.parameter"));
	    	}
        } catch(BadCredentialsException e) {
            throw e;
        } catch(Exception e) {
        	LOG.error("", e);
        	throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
    }
	
	
	
	
}