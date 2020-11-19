package kr.co.penta.dataeye.spring.web.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import kr.co.penta.dataeye.common.entities.CamelMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.penta.dataeye.common.entities.meta.PenObjM;
import kr.co.penta.dataeye.common.entities.meta.PenObjMKey;
import kr.co.penta.dataeye.common.entities.meta.PenObjReqDKey;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.web.common.service.MetaReqService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value={"/metareq", "/portal/metareq", "/admin/metareq", "/system/metareq"}, method={RequestMethod.POST, RequestMethod.GET})
public class MetaReqController {
	private Logger LOG = LoggerFactory.getLogger(MetaReqController.class);
	
	@Autowired
    private MetaReqService metaReqService;

	/*
	$.fn.getUploadFiles = function() {
	    var fileUploadKey = $(this).attr('id');
	    return JSON.stringify($('#fileList' + fileUploadKey).getRowData());
	};

	function fn_save() {
	    var fileData = [];
	    fileData = $('#fileupload').getUploadFiles();

	    var OBJ_ID_KEY - '${OBJ_ID}';

	    var penObjMT = {}, penObjDTs = [];
	    penObjMT['OBJ_TYPE_ID'] = '060301L';
	    penObjMT['OBJ_ID'] = OBJ_ID_KEY;
	    penObjMT['DEL_YN'] = 'N';
	    penObjMT['ADM_OBJ_ID'] = '';
	    penObjMT['OBJ_NM'] = '';
	    penObjMT['OBJ_ABBR_NM'] = '';
	    penObjMT['OBJ_DESC'] = '';
	    penObjMT['PATH_OBJ_TYPE_ID'] = '';
	    penObjMT['PATH_OBJ_ID'] = '';

	    var sysDsExpertgbn = $('input[name="SYS_DS_EXPERTGBN"]:checked').val();
	    var penObjDT = {};
	    penObjDT['OBJ_TYPE_ID'] = '060301L';
	    penObjDT['OBJ_ID'] = OBJ_ID_KEY;
	    penObjDT['ATR_ID_SEQ'] = '101';
	    penObjDT['ATR_VAL_SEQ'] = '101';
	    penObjDT['DEL_YN'] = 'N';
	    penObjDT['OBJ_ATR_VAL'] = sysDsExpertgbn;
	    penObjDTs.push(penObjDT);

        var checkBoxCnt = $('.chkBox').length;
        var checkCnt = $('.chkBox:checked').length;
        var atrValSeq = 101;
        for (var i = 0; i < checkBoxCnt; i++) {
            penObjDT = {};
            if ($('input:checkbox[id="SKILLSETGBN' + i + '"]').is(':checked') === true) {
                penObjDT['OBJ_TYPE_ID'] = '060301L';
	            penObjDT['OBJ_ID'] = OBJ_ID_KEY;
	            penObjDT['ATR_ID_SEQ'] = '102';
	            penObjDT['ATR_VAL_SEQ'] = atrValSeq;
	            penObjDT['DEL_YN'] = 'N';
	            penObjDT['OBJ_ATR_VAL'] = $('#SKILLSETGBN' + i).val();
	            penObjDTs.push(penObjDT);
	            atrValSeq++;
            }
        }

	    var fileList = JSON.parse(fileData);
	    $.each(fileList, function (i, f) {
	        var fileObj = {
	            filePhyName: f.apndFileNm,
	            fileLocName: f.apndFileOcpyNm,
	            fileSize: f.apndFileSizNm,
	            fileType: f.apndFileExtsNm,
	            path: f.apndFilePathNm
	        };

	        penObjDT = {};
	        penObjDT['OBJ_TYPE_ID'] = '060301L';
	        penObjDT['OBJ_ID'] = OBJ_ID_KEY;
	        penObjDT['ATR_ID_SEQ'] = '103';
	        penObjDT['ATR_VAL_SEQ'] = (i + 1) + '01';
	        penObjDT['UI_COMP_CD'] = 'FLE';
	        penObjDT['DEL_YN'] = 'N';
	        penObjDT['OBJ_ATR_VAL'] = fileObj;
	        penObjDTs.push(penObjDT);
	    });

	    var objInfo = {
	        penObjMT: penObjMT,
	        penObjDTs: penObjDTs
	    };

	    var url = '/portal/metareq/object/save';
	    var callbak = function (data) {
	        if (data.status === 'SUCC') {
	        }
	        else {
	        }
	    };
	    requestUrl(url, objInfo, callback);
	}

	var requestUrl = function (url, data, callback) {
	    var sendData = JSON.stringify(data);
	    var sendUrl = APRV.util.getContextPath() + url;
	    var opt = {xhrFields: {withCredentials: true}};
	    if (callbak === undefined || callback === null || typeof(callback) !== 'function') {
	        callback = function(result) {
	            if (result.status === 'SUCC') {
	            }
	            else {
	            }
	        };
	    }

	    APRV.ajax.sendJson(sendUrl, sendData, callback, opt);
	};

	 */
	@RequestMapping("/objectAprv/save")
    @ResponseBody
    public CamelMap saveObjectAprv(@RequestBody(required=true) Map<String, Object> reqParam, HttpSession session) throws Exception {
	    CamelMap resultMap = new CamelMap();

	    CamelMap res = metaReqService.saveObjAprv(reqParam, SessionInfoSupport.getSessionInfo(session));

	    return resultMap;
    }

	@RequestMapping("/objectInfo/get")
	public ResponseEntity<StdResponse> getObjectInfo (
	        @RequestBody(required=true) Map<String, Object> reqParam
	        , HttpSession session) {
	    StdResponse stdResponse = new StdResponse();
	    LOG.debug("objectInfo resuest param : {}", reqParam);
        Map<String, Object> objInfo = metaReqService.getObjectInfo(CastUtils.toString(reqParam.get("objTypeId")), CastUtils.toString(reqParam.get("objId")));
	    
	    return new ResponseEntity<StdResponse>(stdResponse.setSuccess(objInfo), HttpStatus.OK);
	}
	
	@RequestMapping("/subObjectInfo/get")
    public ResponseEntity<StdResponse> getSubObjectInfo (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
	    StdResponse stdResponse = new StdResponse();
        LOG.debug("subObjectInfo resuest param : {}", reqParam);
        Map<String, Object> objInfo = null;
//        Map<String, Object> objInfo = metaReqService.getSubObjectInfo(CastUtils.toString(reqParam.get("pObjTypeId")), CastUtils.toString(reqParam.get("pObjId")), CastUtils.toString(reqParam.get("objTypeId")));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(objInfo), HttpStatus.OK);
    }
	
	@RequestMapping("/objectInfo/save")
    public ResponseEntity<StdResponse> saveObjectInfo (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
	    StdResponse stdResponse = new StdResponse();
        LOG.debug("objectInfo resuest param : {}", reqParam);
        PenObjReqDKey penObjReqDKey = null;
//        PenObjReqDKey penObjReqDKey = metaReqService.saveObjectInfo(reqParam, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(penObjReqDKey), HttpStatus.OK);
    }
    
    @RequestMapping(value="/objectInfo", params="oper=approval")
    public ResponseEntity<StdResponse> objectInfoApproval (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        LOG.debug("objectInfo resuest param : {}", reqParam);
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        if ("".equals(objTypeId) || "".equals(objId)) {
            return new ResponseEntity<StdResponse>(stdResponse.setFail("요청이 올바르지 않습니다."), HttpStatus.OK);
        }

        String reqId = null;
//        String reqId = metaReqService.objectInfoApproval(reqParam, SessionInfoSupport.getSessionInfo(session));
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("reqId", reqId);
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(reqMap), HttpStatus.OK);
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
        PenObjMKey penObjMKey = null;
//        PenObjMKey penObjMKey = metaReqService.deleteObjectInfo(objTypeId, objId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(penObjMKey), HttpStatus.OK);
    }
	
	@RequestMapping("/objectInfo/remove")
    public ResponseEntity<StdResponse> removeObjectInfo (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        
        String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
        String objId = CastUtils.toString(reqParam.get("objId"));
        
        LOG.debug("objectInfo resuest param : {}", reqParam);
        PenObjMKey penObjMKey = null;
//        PenObjMKey penObjMKey = metaReqService.removeObjectInfo(objTypeId, objId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(penObjMKey), HttpStatus.OK);
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
//        metaReqService.removeObjDAtr(objTypeId, objId, atrIdSeq, SessionInfoSupport.getSessionInfo(session));
        
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
//        metaReqService.removeObjDAtr(objTypeId, objId, atrIdSeq, atrValSeq, SessionInfoSupport.getSessionInfo(session));
        
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
//        metaReqService.removeObjDAtr(objTypeId, objId, atrIdSeq, beginAtrValSeq, endAtrValSeq, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(), HttpStatus.OK);
    }
    
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
        PenObjM penObjM = metaReqService.getObjM(objTypeId, objId);
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(penObjM), HttpStatus.OK);
    }
    
	@RequestMapping(value="/aprv", params="oper=remove")
    public ResponseEntity<StdResponse> aprvRemove (
            @RequestBody(required=true) Map<String, Object> reqParam
            , HttpSession session) {
        StdResponse stdResponse = new StdResponse();
        LOG.debug("aprv?oper=remove resuest param : {}", reqParam);
        
        String aprvId = CastUtils.toString(reqParam.get("aprvId"));
        if ("".equals(aprvId)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }
        metaReqService.removeAprv(aprvId, SessionInfoSupport.getSessionInfo(session));
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess("삭제 되었습니다."), HttpStatus.OK);
    }
}