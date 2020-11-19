package kr.co.penta.dataeye.spring.web.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.co.penta.dataeye.common.entities.JqGridEntity;
import kr.co.penta.dataeye.common.entities.PortalMenuEntity;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.JSONUtils;
import kr.co.penta.dataeye.spring.web.common.service.GridService;
import kr.co.penta.dataeye.spring.web.portal.service.PortalService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;
import kr.co.penta.dataeye.spring.web.view.GridXlsView;

@Controller
@RequestMapping(value={"/jqgrid", "/portal/jqgrid", "/admin/jqgrid", "/system/jqgrid"}, method={RequestMethod.POST, RequestMethod.GET})
public class JqGridController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired 
	private PortalService portalService;
	
	@Autowired
	private GridService gridService;
	
	@RequestMapping("/list")
	public ResponseEntity<JqGridEntity> list (
	        @RequestBody(required=true) Map<String, Object> reqParam
	        , HttpSession session) {
	    LOG.debug("jqgrid resuest param : {}", reqParam);
	    SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
	    
	    if(reqParam.get("menuId") != null) {
	    	String menuId = reqParam.get("menuId").toString();
	    	PortalMenuEntity auth = portalService.getMenuAuth(session, menuId);
	    	reqParam.put("cAuth", auth.getCAuth());
	    }
	    JqGridEntity jqGridEntity = gridService.getJqGridList(reqParam, sessionInfo);
	    
	    return new ResponseEntity<JqGridEntity>(jqGridEntity, HttpStatus.OK);
	}
	
	@RequestMapping("/excelExport")
    public ModelAndView excelExport (
            HttpServletResponse response,
            @RequestParam(required=true, value="file") final String file,
            @RequestParam(required=true, value="gridParam") final String gridParamStr
            , HttpSession session) {
        LOG.debug("jqgrid excel export file param : {}", file);
        SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
        Map<String, Object> gridParam = JSONUtils.getInstance().jsonToMap(gridParamStr);
        boolean isPaging = CastUtils.toBoolean(gridParam.get("isPaging"));
        if (isPaging) {
            Map<String, Object> postData = (Map<String, Object>)gridParam.get("postData");
            postData.put("isPaging", false);
            JqGridEntity jqGridEntity = gridService.getJqGridList(postData, sessionInfo);
            gridParam.put("data", jqGridEntity.getRows());
        }

        GridXlsView gridXlsView = new GridXlsView(file, gridParam);
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        
        return new ModelAndView(gridXlsView);
    }
}