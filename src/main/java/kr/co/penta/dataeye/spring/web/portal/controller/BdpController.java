package kr.co.penta.dataeye.spring.web.portal.controller;

import java.util.HashMap;
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

import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.portal.service.BdpService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Controller
@RequestMapping(value="/portal/bdp", method={RequestMethod.POST, RequestMethod.GET})
public class BdpController {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BdpService bdpService;
    
    @RequestMapping(params="oper=dsetAtrImport")
    public ResponseEntity<StdResponse> dsetAtrImport(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);

        final String dsetTypeId = CastUtils.toString(reqParam.get("dsetTypeId"));
        final String dsetId = CastUtils.toString(reqParam.get("dsetId"));
        final String dsetAtrTypeId = CastUtils.toString(reqParam.get("dsetAtrTypeId"));
        final List<Map<String, Object>> data = (List<Map<String, Object>>)reqParam.get("data");
        if ("".equals(dsetTypeId) || "".equals(dsetId) || "".equals(dsetAtrTypeId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        bdpService.dsetAtrImport(
                dsetTypeId, 
                dsetId, 
                dsetAtrTypeId,
                data,
                sessionInfo);

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.save.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(params="oper=dsgnTabColImport")
    public ResponseEntity<StdResponse> dsgnTabColImport(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);

        final String dsgnTabTypeId = CastUtils.toString(reqParam.get("dsgnTabTypeId"));
        final String dsgnTabId = CastUtils.toString(reqParam.get("dsgnTabId"));
        final String dsgnTabColTypeId = CastUtils.toString(reqParam.get("dsgnTabColTypeId"));
        final List<Map<String, Object>> data = (List<Map<String, Object>>)reqParam.get("data");
        if ("".equals(dsgnTabTypeId) || "".equals(dsgnTabId) || "".equals(dsgnTabColTypeId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        bdpService.dsgnTabColImport(
                dsgnTabTypeId, 
                dsgnTabId, 
                dsgnTabColTypeId,
                data,
                sessionInfo);

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(MessageHolder.getInstance().get("common.message.save.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(params="oper=dsgnTabColStdCheck")
    public ResponseEntity<StdResponse> dsgnTabColStdCheck(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();

        final String dbmsType = CastUtils.toString(reqParam.get("dbmsType"));
        final List<Map<String, Object>> data = (List<Map<String, Object>>)reqParam.get("data");
        if ("".equals(dbmsType)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        List<Map<String, Object>> newdata = bdpService.dsgnTabColStdCheck(dbmsType, data);

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(newdata), HttpStatus.OK);
    }
    
    @RequestMapping(params="oper=dsgnTabNmStdTrans")
    public ResponseEntity<StdResponse> dsgnTabNmStdTrans(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();

        final String entityNm = CastUtils.toString(reqParam.get("entityNm"));
        if ("".equals(entityNm)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        String stdTabNm = bdpService.dsgnTabNmStdTrans(entityNm);
        Map<String, String> map = new HashMap<>();
        map.put("stdTabNm", stdTabNm);
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(map), HttpStatus.OK);
    }
}

