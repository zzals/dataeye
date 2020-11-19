package kr.co.penta.dataeye.spring.web.common.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.PenObjD;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.spring.web.common.service.MetaPublicService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;

@Controller
@RequestMapping(value={"/metapublic", "/portal/metapublic", "/admin/metapublic", "/system/metapublic"}, method={RequestMethod.POST, RequestMethod.GET})
public class MetaPublicController {
	private Logger LOG = LoggerFactory.getLogger(MetaPublicController.class);
	
	@Autowired
    private MetaPublicService metaPublicService;
    
    @RequestMapping(path="/objTypeTree")
    public ResponseEntity<StdResponse> objTypeTree() {
        StdResponse stdResponse = new StdResponse();
        List<Map<String, Object>> rsp = metaPublicService.getObjTypeTree();
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(rsp), HttpStatus.OK);
    }

    @RequestMapping(path="/list")
    public ResponseEntity<StdResponse> getList(
            @RequestBody(required=true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();
        List<Map<String, Object>> rsp = metaPublicService.getList(reqParam);
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(rsp), HttpStatus.OK);
    }
    
    @RequestMapping(path="/map")
    public ResponseEntity<StdResponse> getMap(
            @RequestBody(required=true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();
        Map<String, Object> rsp = metaPublicService.getMap(reqParam);
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(rsp), HttpStatus.OK);
    }
    
    @RequestMapping(path="/getAtrSbstRslt")
    public ResponseEntity<StdResponse> getAtrSbstRslt(
            @RequestBody(required=true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();
        List<Map<String, Object>> rsp = metaPublicService.getAtrSbstRslt(CastUtils.toString(reqParam.get("objTypeId")), CastUtils.toInteger(reqParam.get("atrIdSeq")));
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(rsp), HttpStatus.OK);
    }

    @RequestMapping(path="/orgTree")
    public ResponseEntity<StdResponse> orgTree() {
        StdResponse stdResponse = new StdResponse();
        List<Map<String, Object>> rsp = metaPublicService.getOrgTree();
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(rsp), HttpStatus.OK);
    }

    @RequestMapping(params="oper=getObjUIInfo")
    public ResponseEntity<StdResponse> getObjUIInfo(
            @RequestBody(required=true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));        
        if ("".equals(objTypeId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        List<Map<String, Object>> rsp = metaPublicService.getObjUIInfo(objTypeId);
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(rsp), HttpStatus.OK);
    }

    @RequestMapping(params="oper=getObjAtrVal")
    public ResponseEntity<StdResponse> getObjAtrVal(
            @RequestBody(required=true) CamelMap reqParam) {
        StdResponse stdResponse = new StdResponse();
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        Integer atrIdSeq = CastUtils.toInteger(reqParam.get("atrIdSeq"));
        Integer atrValSeq = CastUtils.toInteger(reqParam.get("atrValSeq"));
        
        if ("".equals(objTypeId) || "".equals(objId) || atrIdSeq == null) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        List<PenObjD> rsp = metaPublicService.getObjAtrVal(objTypeId, objId, atrIdSeq, atrValSeq);
            
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(rsp), HttpStatus.OK);
    }
}