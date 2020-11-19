package kr.co.penta.dataeye.spring.web.system.controller;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.exception.ValidationException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.security.core.User;
import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.spring.web.common.service.CommonService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;
import kr.co.penta.dataeye.spring.web.system.service.SystemService;
import kr.co.penta.dataeye.spring.web.system.service.SystemUserService;
import kr.co.penta.dataeye.spring.web.validate.SysUserGrpMForm;
import kr.co.penta.dataeye.spring.web.validate.UIForm;
import kr.co.penta.dataeye.spring.web.validate.SysUserMForm;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/system", method={RequestMethod.POST, RequestMethod.GET})
public class SystemUserController {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommonService commonService;
    
    @Autowired
    private SystemService systemService;

    @Autowired
    private SystemUserService systemUserService;
    
    @Value("${dataeye.config.algorithm}")
    private String algorithm;
    
    @Value("${dataeye.config.key}")
    private String key;
    
    @Value("${dataeye.config.initpwd}")
    private String strInitPwd;

    @RequestMapping(path = "/user", params="oper=getOrgNm")
    public ResponseEntity<StdResponse> getOrgNm(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String orgId = CastUtils.toString(reqParam.get("orgId"));
        
        
        List<CamelMap> list = systemUserService.getOrgNm();
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(list), HttpStatus.OK);
    } 
    
    @RequestMapping(path = "/user", params="oper=get")
    public ResponseEntity<StdResponse> userGet(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
         
        String userId = CastUtils.toString(reqParam.get("userId"));
        if ("".equals(userId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        Map<String, Object> map = systemUserService.getUser(userId);
               
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(map), HttpStatus.OK);
    }  
    
    @RequestMapping(path = "/user", params="oper=regist")
    public ResponseEntity<StdResponse> userRegist(HttpSession session,
            @Valid SysUserMForm sysUserMForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
 
        if (bindingResult.hasErrors()) {
            LOG.debug(bindingResult.toString());

            throw new ValidationException(bindingResult);
            
        }
        systemUserService.registUserM(sysUserMForm, SessionInfoSupport.getSessionInfo(session));

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/user", params="oper=update")
    public ResponseEntity<StdResponse> userUpdate(HttpSession session,
            @Valid SysUserMForm sysUserMForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
        if (bindingResult.hasErrors()) {
            LOG.debug(bindingResult.toString());
            
            throw new ValidationException(bindingResult);
        }
        
        systemUserService.updateUserM(sysUserMForm, SessionInfoSupport.getSessionInfo(session));
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.update.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/user", params="oper=pwdInit")
    public ResponseEntity<StdResponse> userInitPwd(HttpSession session,
            @RequestParam(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setProvider(new BouncyCastleProvider());
		encryptor.setAlgorithm(algorithm);
		encryptor.setPassword(key);
		
        String userId = CastUtils.toString(reqParam.get("userId"));
        String initPwd = encryptor.decrypt(strInitPwd);
        if ("".equals(userId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        systemUserService.userInitPwd(userId, initPwd, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.initpwd.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/user", params="oper=remove")
    public ResponseEntity<StdResponse> userRemove(HttpSession session,
            @RequestParam(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String userId = CastUtils.toString(reqParam.get("userId"));
        if ("".equals(userId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        systemUserService.removeUserM(userId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.remove.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/user", params = "oper=dupCheck")
    public ResponseEntity<StdResponse> userDuplCheck(@RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();

        String userId = CastUtils.toString(reqParam.get("userId"));
        if ("".equals(userId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        boolean isExistUser = systemUserService.isExistUserId(userId);
        if (isExistUser) {
            return new ResponseEntity<StdResponse>(stdResponse.setFail(MessageHolder.getInstance().get("msg.user.dupl.USER_ID")), HttpStatus.OK);
        } else {
            return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("msg.user.valid.USER_ID")), HttpStatus.OK);
        }
    } 
    
    @RequestMapping(path = "/userGrp", params = "oper=dupCheck")
    public ResponseEntity<StdResponse> userRoleDuplCheck(@RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();

        String userGrpId = CastUtils.toString(reqParam.get("userGrpId"));
        if ("".equals(userGrpId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        boolean isExistUserGrp = systemUserService.isExistUserGrp(userGrpId);
        if (isExistUserGrp) {
            return new ResponseEntity<StdResponse>(stdResponse.setFail(MessageHolder.getInstance().get("msg.usergrp.dupl.USER_GRP_ID")), HttpStatus.OK);
        } else {
            return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("msg.usergrp.valid.USER_GRP_ID")), HttpStatus.OK);
        }
    } 
    
    @RequestMapping(path = "/userGrp", params="oper=get")
    public ResponseEntity<StdResponse> userGrpGet(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
         
        String userGrpId = CastUtils.toString(reqParam.get("userGrpId"));
        if ("".equals(userGrpId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        Map<String, Object> map = systemUserService.getUserGrp(userGrpId);
               
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(map), HttpStatus.OK);
    }  
    
    @RequestMapping(path = "/userGrp", params="oper=regist")
    public ResponseEntity<StdResponse> userGrpRegist(HttpSession session,
            @Valid SysUserGrpMForm sysUserGrpMForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
 
        if (bindingResult.hasErrors()) {
            LOG.debug(bindingResult.toString());
            
            throw new ValidationException(bindingResult);
        }
        
        systemUserService.registUserGrp(sysUserGrpMForm, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }   
    
    @RequestMapping(path = "/userGrp", params="oper=update")
    public ResponseEntity<StdResponse> userGrpUpdate(HttpSession session,
            @Valid SysUserGrpMForm sysUserGrpMForm, 
            BindingResult bindingResult) {
        StdResponse stdResponse = new StdResponse();
        
        if (bindingResult.hasErrors()) {
            LOG.debug(bindingResult.toString());
            
            throw new ValidationException(bindingResult);
        }
        
        systemUserService.updateUserGrp(sysUserGrpMForm, SessionInfoSupport.getSessionInfo(session));
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.update.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/userGrp", params="oper=remove")
    public ResponseEntity<StdResponse> userGrpRemove(HttpSession session,
            @RequestParam(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String userGrpId = CastUtils.toString(reqParam.get("userGrpId"));
        if ("".equals(userGrpId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        systemUserService.removeUserGrp(userGrpId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.remove.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/addObjAuth")
    public ResponseEntity<StdResponse> addObjAuth(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        if ("".equals(objTypeId) || "".equals(objId) ) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));    
        }
        
        List<String> addInfo = (List<String>)reqParam.get("addInfo");
        systemUserService.addObjAuth(objTypeId,objId, addInfo, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/delObjAuth")
    public ResponseEntity<StdResponse> delObjAuth(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        if ("".equals(objTypeId) || "".equals(objId) ) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));    
        }
        
        List<String> delInfo = (List<String>)reqParam.get("delInfo");
        systemUserService.delObjAuth(objTypeId,objId, delInfo, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/grpUserR", params="oper=regist")
    public ResponseEntity<StdResponse> grpUserRRegist(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String userGrpId = CastUtils.toString(reqParam.get("userGrpId"));
        if ("".equals(userGrpId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));    
        }
        
        List<String> userIds = (List<String>)reqParam.get("userIds");
        systemUserService.registGroupUserR(userGrpId, userIds, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/grpUserR", params="oper=remove")
    public ResponseEntity<StdResponse> grpUserRRemove(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String userGrpId = CastUtils.toString(reqParam.get("userGrpId"));
        if ("".equals(userGrpId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        List<String> userIds = (List<String>)reqParam.get("userIds");
        systemUserService.removeGroupUserR(userGrpId, userIds, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.remove.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/grpR", params="oper=regist")
    public ResponseEntity<StdResponse> grpRRegist(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String userGrpId = CastUtils.toString(reqParam.get("userGrpId"));
        if ("".equals(userGrpId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));    
        }
        
        List<String> relUserGrpIds = (List<String>)reqParam.get("relUserGrpIds");
        systemUserService.registGroupR(userGrpId, relUserGrpIds, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.regist.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/grpR", params="oper=remove")
    public ResponseEntity<StdResponse> grpRRemove(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String userGrpId = CastUtils.toString(reqParam.get("userGrpId"));
        if ("".equals(userGrpId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        List<String> relUserGrpIds = (List<String>)reqParam.get("relUserGrpIds");
        systemUserService.removeGroupR(userGrpId, relUserGrpIds, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.remove.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(path = "/userAuth/delete")
    public ResponseEntity<StdResponse> userAuthDelete(@RequestBody(required = true) Map<String, Object> reqParam, HttpSession session) {
        StdResponse stdResponse = new StdResponse();

        commonService.delete("system_user.deleteUserRole", reqParam, SessionInfoSupport.getSessionInfo(session));

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(""), HttpStatus.OK);
    }

    @RequestMapping(path = "/userRoleRel/save")
    public ResponseEntity<StdResponse> userRoleRelSave(@RequestBody(required = true) Map<String, Object> reqParam, HttpSession session) {
        StdResponse stdResponse = new StdResponse();

        LOG.debug("reqParam : {}", reqParam);
        commonService.insert("system_user.insertUserRoleRel", reqParam, SessionInfoSupport.getSessionInfo(session));

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(reqParam), HttpStatus.OK);
    }

    @RequestMapping(path = "/userRoleRel/delete")
    public ResponseEntity<StdResponse> userRoleRelDelete(@RequestBody(required = true) Map<String, Object> reqParam, HttpSession session) {
        StdResponse stdResponse = new StdResponse();

        commonService.delete("system_user.deleteUserRoleRel", reqParam, SessionInfoSupport.getSessionInfo(session));

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(""), HttpStatus.OK);
    }
    
    
    @RequestMapping(path = "/userAuthRel/save")
    public ResponseEntity<StdResponse> userAuthRelSave(@RequestBody(required = true) Map<String, Object> reqParam, HttpSession session) {
        StdResponse stdResponse = new StdResponse();

        LOG.debug("reqParam : {}", reqParam);
        commonService.insert("system_user.insertUserAuthRel", reqParam, SessionInfoSupport.getSessionInfo(session));

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(reqParam), HttpStatus.OK);
    }

    @RequestMapping(path = "/userAuthRel/delete")
    public ResponseEntity<StdResponse> userAuthRelDelete(@RequestBody(required = true) Map<String, Object> reqParam, HttpSession session) {
        StdResponse stdResponse = new StdResponse();

        commonService.delete("system_user.deleteUserAuthRel", reqParam, SessionInfoSupport.getSessionInfo(session));

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(""), HttpStatus.OK);
    }

    @RequestMapping(path = "/userMenu/getAuth")
    public ResponseEntity<StdResponse> userMenuGetAuth(@RequestBody(required = true) CamelMap reqParam, HttpServletRequest request, HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        
        User user = SessionInfoSupport.getUser(session);
        List<Map<String, Object>> list = user.getActionAuth();
        Map<String, String> map = new LinkedHashMap<>();

        final String host = request.getServerName();
        String rmenuId = reqParam.getString("menuId");

        if (host.startsWith("localhost") && "admin".equals(user.getUserId())) {
            map.put("C_AUTH", "Y");
            map.put("R_AUTH", "Y");
            map.put("U_AUTH", "Y");
            map.put("D_AUTH", "Y");
            map.put("E_AUTH", "Y");
        }
        else {
            map.put("C_AUTH", "N");
            map.put("R_AUTH", "N");
            map.put("U_AUTH", "N");
            map.put("D_AUTH", "N");
            map.put("E_AUTH", "N");
            
            if (rmenuId.equals("")) {
                rmenuId = "BDP_MAIN";
            }
            
            if (rmenuId != null) {
                if("BDP_MAIN".equals(rmenuId)) {
                    map.put("R_AUTH", "Y");
                } else if("BDP_ADMIN".equals(rmenuId)) {
                    boolean isPTA = systemUserService.isPTA(SessionInfoSupport.getSessionInfo(session).getUserId());
                    if (!isPTA) {
                        throw new ServiceRuntimeException("접근 권한이 없습니다.");
                    }
                    map.put("C_AUTH", "Y");
                    map.put("R_AUTH", "Y");
                    map.put("U_AUTH", "Y");
                    map.put("D_AUTH", "Y");
                    map.put("E_AUTH", "Y");
                }
                else {
                    for (Map<String, Object> lmap : list) {
                        final String menuId = (String) lmap.get("menuId");
                        if (reqParam.getString("menuId").equals(menuId)) {
                            final String cAuth = (String) lmap.get("C_AUTH");
                            final String rAuth = (String) lmap.get("R_AUTH");
                            final String uAuth = (String) lmap.get("U_AUTH");
                            final String dAuth = (String) lmap.get("D_AUTH");
                            final String eAuth = (String) lmap.get("E_AUTH");
                            if (cAuth != null && "Y".equals(cAuth)) {
                                map.put("C_AUTH", "Y");
                            }
                            if (rAuth != null && "Y".equals(rAuth)) {
                                map.put("R_AUTH", "Y");
                            }
                            if (uAuth != null && "Y".equals(uAuth)) {
                                map.put("U_AUTH", "Y");
                            }
                            if (dAuth != null && "Y".equals(dAuth)) {
                                map.put("D_AUTH", "Y");
                            }
                            if (eAuth != null && "Y".equals(eAuth)) {
                                map.put("E_AUTH", "Y");
                            }
                            break;
                        }
                    }
                }
            }
        }

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(map), HttpStatus.OK);
    }
    
    /*@RequestMapping(path="/delMenuGrp")
    public ResponseEntity<StdResponse> delMenuGrp(HttpSession session,
            @RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        systemService.delMenuGrp(reqParam);

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(), HttpStatus.OK);
    }
    
    @RequestMapping("/userMenuAddGrp")
    public String userMenuAddGrp(final Model model, HttpServletRequest request,
                        @RequestParam(required=true, value="viewId") final String viewId,
                        @RequestParam(required=false, value="data", defaultValue="{}") final String data
            ) {
        LOG.info("data : {}", data);
        model.addAttribute("data", data);
        HttpSession session = request.getSession(false);
        LOG.info("viewId : {}", viewId);
        LOG.info("portal main session id !!!: {}", session.getId());        
        List<Map<String,Object>> grpList = systemService.getGrpList();
        request.setAttribute("grpList",grpList);
        return viewId;
    }*/
    
    @RequestMapping(path = "/grpMenuR", params="oper=regist")
    public ResponseEntity<StdResponse> grpMenuRemove(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String userGrpId = CastUtils.toString(reqParam.get("userGrpId"));
        if ("".equals(userGrpId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        List<String> userIds = (List<String>)reqParam.get("userIds");
        
        System.out.println(userIds.size());
        for(int i=0;i<userIds.size();i++) {
        	
        }
        
        //systemUserService.removeGroupUserR(userGrpId, userIds, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.remove.complete")), HttpStatus.OK);
    }
}
