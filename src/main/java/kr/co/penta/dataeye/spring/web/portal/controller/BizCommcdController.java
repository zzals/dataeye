package kr.co.penta.dataeye.spring.web.portal.controller;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.JqGridEntity;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.param.JqGridDaoParam;
import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.portal.service.BizCommcdService;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/portal/biz/commcd", method={RequestMethod.POST, RequestMethod.GET})
public class BizCommcdController {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private BizCommcdService bizCommcdService;

    @RequestMapping(params = "oper=findCommcd")
    public ResponseEntity<JqGridEntity> findCommcd(HttpSession session,
    		@RequestBody(required = true) CamelMap reqParam) {
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        List<Map<String, Object>> list = bizCommcdService.findCommcd(objTypeId, objId);

        JqGridDaoParam  jqGridDaoParam = new JqGridDaoParam(reqParam);
        JqGridEntity jqGridEntity = new JqGridEntity(jqGridDaoParam, list, list.size());
        
        return new ResponseEntity<JqGridEntity>(jqGridEntity, HttpStatus.OK);
    }
    
    @RequestMapping(params = "oper=deleteCommcdGrp")
    public ResponseEntity<StdResponse> deleteCommcdGrp(HttpSession session,
    		@RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        
        if ("".equals(objTypeId) || "".equals(objId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        bizCommcdService.deleteCommcdGrp(objTypeId, objId, SessionInfoSupport.getSessionInfo(session));

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess("삭제 되었습니다."), HttpStatus.OK);
    }
    
    @RequestMapping(params = "oper=saveCommcd")
    public ResponseEntity<StdResponse> saveCommcd(HttpSession session,
    		@RequestBody(required = true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();

        String cdGrpTypeId = CastUtils.toString(reqParam.get("cdGrpTypeId"));
        String cdGrpId = CastUtils.toString(reqParam.get("cdGrpId"));
        List<Map<String, Object>> data = (List<Map<String, Object>>)reqParam.get("data");
        
        if ("".equals(cdGrpTypeId) || "".equals(cdGrpId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        bizCommcdService.saveCommcd(cdGrpTypeId, cdGrpId, data, SessionInfoSupport.getSessionInfo(session));

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess("저장 되었습니다."), HttpStatus.OK);
    }
}