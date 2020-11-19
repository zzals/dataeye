package kr.co.penta.dataeye.tsearch.controller;

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
import kr.co.penta.dataeye.spring.security.core.LoggingService;
import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.tsearch.service.TSearchService;

@Controller
@RequestMapping(value="/tsearch", method={RequestMethod.POST, RequestMethod.GET})
public class TSearchController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired LoggingService loggingService;
    @Autowired TSearchService tSearchService;
    
	@RequestMapping(params="oper=findDashboardObjTypes")
    public ResponseEntity<Map<String, Object>> findDashboardObjTypes(HttpSession session) {
        Map<String, Object> resultMap = tSearchService.findDashboardObjTypes();
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=findDashboardTreeMap")
    public ResponseEntity<List<Map<String, Object>>> findDashboardTreeMap (
    		@RequestBody(required=true) Map<String, Object> reqParam,
    		HttpSession session) {
		String objTypeId = CastUtils.toString(reqParam.get("objTypeId"));
		if ("".equals(objTypeId)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
		
        List<Map<String, Object>> resultList = tSearchService.findTreeMapByPath(objTypeId);
        
        return new ResponseEntity<List<Map<String, Object>>>(resultList, HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=getTabCategory")
    public ResponseEntity<List<Map<String, Object>>> getTabCategory (
    		@RequestBody(required=false) Map<String, Object> reqParam,
    		HttpSession session) {
		List<Map<String, Object>> resultList = tSearchService.getTabCategory();
        
        return new ResponseEntity<List<Map<String, Object>>>(resultList, HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=suggest")
    public ResponseEntity<Map<String, Object>> suggest (
    		@RequestBody(required=false) Map<String, Object> reqParam,
    		HttpSession session) {
		String keyword = CastUtils.toString(reqParam.get("keyword"));
		if ("".equals(keyword)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
		
		Map<String, Object> rsp = tSearchService.suggest(keyword);
        
        return new ResponseEntity<Map<String, Object>>(rsp, HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=msearch")
    public ResponseEntity<Map<String, Object>> msearch (
    		@RequestBody(required=false) Map<String, Object> reqParam,
    		HttpSession session) {
		String type = CastUtils.toString(reqParam.get("type"));
		String keyword = CastUtils.toString(reqParam.get("keyword"));
		Integer page = CastUtils.toInteger(reqParam.get("page"));
		if ("".equals(type) || "".equals(keyword)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
		
		Map<String, Object> rsp = tSearchService.msearch(type, keyword, page);
        
        return new ResponseEntity<Map<String, Object>>(rsp, HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=elsearch")
    public ResponseEntity<Map<String, Object>> elsearch (
    		@RequestBody(required=false) Map<String, Object> reqParam,
    		HttpSession session) {
		String type = CastUtils.toString(reqParam.get("type"));
		String keyword = CastUtils.toString(reqParam.get("keyword"));
		Integer page = CastUtils.toInteger(reqParam.get("page"));
		if ("".equals(type) || "".equals(keyword)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
		
		Map<String, Object> rsp = tSearchService.elsearch(type, keyword, page);
        
        return new ResponseEntity<Map<String, Object>>(rsp, HttpStatus.OK);
    }
	
	@RequestMapping(params="oper=elsuggest")
    public ResponseEntity<List<String>> elsuggest (
    		@RequestBody(required=false) Map<String, Object> reqParam,
    		HttpSession session) {
		String keyword = CastUtils.toString(reqParam.get("keyword"));
		if ("".equals(keyword)) {
            throw new ServiceRuntimeException(MessageHolder.getInstance().get("invalid.request.parameter"));
        }
		
		List<String> rsp = tSearchService.elsuggest(keyword);
        
        return new ResponseEntity<List<String>>(rsp, HttpStatus.OK);
    }
}