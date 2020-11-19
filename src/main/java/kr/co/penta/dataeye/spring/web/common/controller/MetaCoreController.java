package kr.co.penta.dataeye.spring.web.common.controller;

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

import kr.co.penta.dataeye.common.entities.JqGridEntity;
import kr.co.penta.dataeye.common.entities.meta.PenObjM;
import kr.co.penta.dataeye.common.entities.meta.PenObjMKey;
import kr.co.penta.dataeye.common.entities.meta.PenRelTypeM;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.param.JqGridDaoParam;
import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.spring.web.common.service.MetaService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Controller
@RequestMapping(value={"/metacore", "/dataeye/metacore", "/portal/metacore", "/admin/metacore", "/system/metacore"}, method={RequestMethod.POST, RequestMethod.GET})
public class MetaCoreController {
	private Logger LOG = LoggerFactory.getLogger(MetaCoreController.class);
	
	@Autowired
	private MetaService metaService;
	
	@RequestMapping(value="/objectInfo", params="oper=getObjM")
    public ResponseEntity<StdResponse> getObjM (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        LOG.debug("objectInfo?oper=getObjM resuest param : {}", reqParam);
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        if ("".equals(objTypeId) || "".equals(objId)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }
        PenObjM penObjM = metaService.getObjM(objTypeId, objId);
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(penObjM), HttpStatus.OK);
    }
	
	@RequestMapping("/objectInfo/get")
	public ResponseEntity<StdResponse> getObjectInfo (
	        @RequestBody(required=true) Map<String, Object> reqParam
	        , HttpSession session) {
	    StdResponse stdResponse = new StdResponse();
	    LOG.debug("objectInfo resuest param : {}", reqParam);
	    Map<String, Object> objInfo = metaService.getObjectInfo(CastUtils.toString(reqParam.get("objTypeId")), CastUtils.toString(reqParam.get("objId")));
	    
	    return new ResponseEntity<StdResponse>(stdResponse.setSuccess(objInfo), HttpStatus.OK);
	}
	
	@RequestMapping("/subObjectInfo/get")
    public ResponseEntity<StdResponse> getSubObjectInfo (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
	    StdResponse stdResponse = new StdResponse();
        LOG.debug("subObjectInfo resuest param : {}", reqParam);
        Map<String, Object> objInfo = metaService.getSubObjectInfo(CastUtils.toString(reqParam.get("pObjTypeId")), CastUtils.toString(reqParam.get("pObjId")), CastUtils.toString(reqParam.get("objTypeId")));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(objInfo), HttpStatus.OK);
    }

    @RequestMapping(value="/objRelTabs/get")
    public ResponseEntity<StdResponse> objRelTabs (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        LOG.debug("tabInfo resuest param : {}", reqParam);
        Map<String, Object> objInfo = metaService.objRelTabs(CastUtils.toString(reqParam.get("objTypeId")), CastUtils.toString(reqParam.get("mode")));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(objInfo), HttpStatus.OK);
    }
    
    @RequestMapping(value="/objRelTabs/filter")
    public ResponseEntity<StdResponse> objRelTabsFilter (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        LOG.debug("objRelTabsFilter resuest param : {}", reqParam);
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String relTypeNm = CastUtils.toString(reqParam.get("relTypeNm"));
        String relDv = CastUtils.toString(reqParam.get("relDv"));
        String metaRelCd = CastUtils.toString(reqParam.get("metaRelCd"));
        if ("".equals(objTypeId) || "".equals(relTypeNm) || "".equals(relDv) || "".equals(metaRelCd)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }    
        List<Map<String, Object>> filters = metaService.objRelTabFilter(objTypeId, relTypeNm, relDv, metaRelCd);
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(filters), HttpStatus.OK);
    }
	
	@RequestMapping("/objectInfo/save")
    public ResponseEntity<StdResponse> saveObjectInfo (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
	    StdResponse stdResponse = new StdResponse();
        LOG.debug("objectInfo resuest param : {}", reqParam);
        PenObjMKey penObjMKey = metaService.saveObjectInfo(reqParam, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(penObjMKey), HttpStatus.OK);
    }
    
	@RequestMapping("/objectInfo/delete")
    public ResponseEntity<StdResponse> deleteObjectInfo (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        
        if ("".equals(objTypeId) || "".equals(objId)) {
            return new ResponseEntity<StdResponse>(stdResponse.setFail("요청이 올바르지 않습니다."), HttpStatus.OK);
        }
        
        LOG.debug("objectInfo resuest param : {}", reqParam);
        PenObjMKey penObjMKey = metaService.deleteObjectInfo(objTypeId, objId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(penObjMKey, "삭제 되었습니다."), HttpStatus.OK);
    }
	
	@RequestMapping("/objectInfo/remove")
    public ResponseEntity<StdResponse> removeObjectInfo (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        
        LOG.debug("objectInfo resuest param : {}", reqParam);
        PenObjMKey penObjMKey = metaService.removeObjectInfo(objTypeId, objId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(penObjMKey, "삭제 되었습니다."), HttpStatus.OK);
    }
    
    @RequestMapping("/objectInfo/removeAtrId")
    public ResponseEntity<StdResponse> removeObjectAtrId (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        Integer atrIdSeq = CastUtils.toInteger(reqParam.get("atrIdSeq"));
        
        LOG.debug("objectInfo resuest param : {}", reqParam);
        metaService.removeObjDAtr(objTypeId, objId, atrIdSeq, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(), HttpStatus.OK);
    }
    
    @RequestMapping("/objectInfo/removeAtrVal")
    public ResponseEntity<StdResponse> removeObjectAtrVal (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        Integer atrIdSeq = CastUtils.toInteger(reqParam.get("atrIdSeq"));
        Integer atrValSeq = CastUtils.toInteger(reqParam.get("atrValSeq"));
        
        LOG.debug("objectInfo resuest param : {}", reqParam);
        metaService.removeObjDAtr(objTypeId, objId, atrIdSeq, atrValSeq, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(), HttpStatus.OK);
    }
    
    @RequestMapping("/objectInfo/removeAtrValRange")
    public ResponseEntity<StdResponse> removeObjectAtrValRange (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        Integer atrIdSeq = CastUtils.toInteger(reqParam.get("atrIdSeq"));
        Integer beginAtrValSeq = CastUtils.toInteger(reqParam.get("beginAtrValSeq"));
        Integer endAtrValSeq = CastUtils.toInteger(reqParam.get("endAtrValSeq"));
        
        LOG.debug("objectInfo resuest param : {}", reqParam);
        metaService.removeObjDAtr(objTypeId, objId, atrIdSeq, beginAtrValSeq, endAtrValSeq, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(), HttpStatus.OK);
    }
    
    /*
     * 객체 기본정보 상세 팝업에서 저장시
     */
    @RequestMapping(value="/objectrel", params="oper=save")
    public ResponseEntity<StdResponse> objectRelSave (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        LOG.debug("objectInfo resuest param : {}", reqParam);
        
        metaService.objectRelSave(reqParam, SessionInfoSupport.getSessionInfo(session));
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(), HttpStatus.OK);
    }
    
    /*
     * 객체 관계 정보
     */
    @RequestMapping(value="/objectrel", params="oper=objInfluence")
    public ResponseEntity<StdResponse> objectRelInfluence (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        LOG.debug("oper=objInfluence resuest param : {}", reqParam);
        
        List<Map<String, Object>> rsp = metaService.objectRelInfluence(reqParam);
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(rsp), HttpStatus.OK);
    }
    
    /*
     * 객체 관계 하위 정보
     */
    @RequestMapping(value="/objectrel", params="oper=objInfluenceSub")
    public ResponseEntity<StdResponse> objectRelInfluenceSub (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        LOG.debug("oper=objInfluence resuest param : {}", reqParam);
        
        List<Map<String, Object>> rsp = metaService.objectRelInfluenceSub(reqParam);
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(rsp), HttpStatus.OK);
    }
	
	@RequestMapping(value="/objRelTypeM", params="oper=get")
    public ResponseEntity<StdResponse> getObjRelTypeM(
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        
        String relTypeId = CastUtils.toString(reqParam.get("relTypeId"));
        if ("".equals(relTypeId)) {
            return new ResponseEntity<StdResponse>(stdResponse.setFail("요청이 올바르지 않습니다."), HttpStatus.OK);
        }
        
        PenRelTypeM penRelTypeM = metaService.getObjRelTypeM(relTypeId);
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(penRelTypeM), HttpStatus.OK);
    }
    
    @RequestMapping(value="/uiview", params="oper=render")
    public ResponseEntity<StdResponse> uiviewRander(
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        
        String mode = CastUtils.toString(reqParam.get("mode"));
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        String uiId = CastUtils.toString(reqParam.get("uiId"));
        
        if ("".equals(mode) || "".equals(objTypeId) || "".equals(objId) || "".equals(uiId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        Map<String, Object> infoMap = metaService.uiviewRender(mode, objTypeId, objId, uiId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(infoMap), HttpStatus.OK);
    }
    
    @RequestMapping(value="/uiview", params="oper=execQueryByGrid")
    public ResponseEntity<JqGridEntity> uiviewExecQueryByGrid(
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {        
        String mode = CastUtils.toString(reqParam.get("mode"));
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        String uiId = CastUtils.toString(reqParam.get("uiId"));
        String confSqlKey = CastUtils.toString(reqParam.get("confSqlKey"));
        
        if ("".equals(mode) || "".equals(objTypeId) || "".equals(objId) || "".equals(uiId) || "".equals(confSqlKey)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        List<Map<String, Object>> list = metaService.uiviewExecQuery(reqParam, SessionInfoSupport.getSessionInfo(session));
        
        JqGridDaoParam  jqGridDaoParam = new JqGridDaoParam(reqParam);

        JqGridEntity jqGridEntity = new JqGridEntity(jqGridDaoParam, list, list.size());
        
        return new ResponseEntity<JqGridEntity>(jqGridEntity, HttpStatus.OK);
    }
    
    @RequestMapping(value="/uiview", params="oper=execQueryByData")
    public ResponseEntity<StdResponse> uiviewExecQueryByData (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        
        String mode = CastUtils.toString(reqParam.get("mode"));
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        String uiId = CastUtils.toString(reqParam.get("uiId"));
        String confSqlKey = CastUtils.toString(reqParam.get("confSqlKey"));
        
        if ("".equals(mode) || "".equals(objTypeId) || "".equals(objId) || "".equals(uiId) || "".equals(confSqlKey)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        List<Map<String, Object>> list = metaService.uiviewExecQuery(reqParam, SessionInfoSupport.getSessionInfo(session));

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(list), HttpStatus.OK);
    }
    
    @RequestMapping(value="/uiobjlist", params="oper=render")
    public ResponseEntity<StdResponse> uiobjlistRander(
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String uiId = CastUtils.toString(reqParam.get("uiId"));
        
        if ("".equals(objTypeId) || "".equals(uiId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        Map<String, Object> infoMap = metaService.uiobjlistRender(objTypeId, uiId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(infoMap), HttpStatus.OK);
    }
    
    @RequestMapping(value="/uiobjlist", params="oper=execQueryByGrid")
    public ResponseEntity<JqGridEntity> uiobjlistExecQueryByGrid(
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String uiId = CastUtils.toString(reqParam.get("uiId"));
        String confSqlKey = CastUtils.toString(reqParam.get("confSqlKey"));
        
        if ("".equals(objTypeId) || "".equals(uiId) || "".equals(confSqlKey)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
        
        List<Map<String, Object>> list = metaService.uiobjlistExecQuery(reqParam, SessionInfoSupport.getSessionInfo(session));
        
        JqGridDaoParam  jqGridDaoParam = new JqGridDaoParam(reqParam);

        JqGridEntity jqGridEntity = new JqGridEntity(jqGridDaoParam, list, list.size());
        
        return new ResponseEntity<JqGridEntity>(jqGridEntity, HttpStatus.OK);
    }
}