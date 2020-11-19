package kr.co.penta.dataeye.spring.web.portal.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.penta.dataeye.spring.web.portal.service.PortalService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Controller
@RequestMapping(value="/report", method={RequestMethod.POST, RequestMethod.GET})
public class ReportController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ReportController.class);
	
	@Autowired private PortalService portalService;
	
    @RequestMapping("/reportPrompt")
    public String reportPrompt(final Model model, HttpServletRequest request) {
    	
    	String reportId = request.getParameter("reportId");
    	List<Map<String,String>> reportDetailList = portalService.getReportDetail(reportId);    	
    	model.addAttribute("reportDetailList",reportDetailList);
    	
    	String filterHtml = "<table class=\"table_Type1\"><tr>";

    	for(int i=0;i<reportDetailList.size();i++) {
    		Map<String,String> filter = reportDetailList.get(i);
    		if(filter.get("F101_ATR_VAL") != null) {
    			
    			if(filter.get("F101_ATR_VAL").equals("CAL_MM")){
    				filterHtml += "<th>"+filter.get("D_NAME")+"</th>";
    				filterHtml += "<td>";
    				filterHtml += " <input type=\"text\" id=\"fromData\" name=\"fromData\" placeholder=\"\"><span class=\"cal_btn\" onclick=\"$('#"+filter.get("ADM_OBJ_ID")+"').focus()\"><i class=\"glyphicon glyphicon-calendar\"></i></span>";
    				filterHtml += "<script>";
    				filterHtml += "$(function() {";
    				filterHtml += "	$('#fromData').monthpicker({";
    				filterHtml += "	pattern: 'yyyymm',";
    				filterHtml += "	monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']";
    				filterHtml += " });";
    				filterHtml += "});";
    				filterHtml += "</script>";
    				filterHtml += "</td>";
    			}
    			
    			if(filter.get("F101_ATR_VAL").equals("CAL_FR")){
    				filterHtml += "<th>"+filter.get("D_NAME")+"</th>";
    				filterHtml += "<td>";
    				filterHtml += " <input type=\"text\" id=\"fromData\" name=\"fromData\" placeholder=\"\"><span class=\"cal_btn\" onclick=\"$('#"+filter.get("ADM_OBJ_ID")+"').focus()\"><i class=\"glyphicon glyphicon-calendar\"></i></span>";
    				filterHtml += "<script>";
    				filterHtml += "$(function() {";
    				filterHtml += "    $('#fromData').datepicker({";
    				filterHtml += "    	 dateFormat: 'yy-mm-dd'";
    				filterHtml += "             ,showOtherMonths: true";
    				filterHtml += "             ,showMonthAfterYear:true";
    				filterHtml += "             ,showOn: \"both\"";  
    				filterHtml += "             ,buttonImage: \"http://jqueryui.com/resources/demos/datepicker/images/calendar.gif\"";
    				filterHtml += "             ,buttonImageOnly: false";
    				filterHtml += "             ,yearSuffix: \"년\"";
    				filterHtml += "             ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12']";
    				filterHtml += "             ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']";
    				filterHtml += "             ,dayNamesMin: ['일','월','화','수','목','금','토']";
    				filterHtml += "             ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일']";
    				filterHtml += "    });";
    				filterHtml += " $('#fromData').datepicker('setDate', 'today');";
    				filterHtml += " $('.ui-datepicker-trigger').hide();";
    				filterHtml += "});";
    				filterHtml += "</script>";
    				filterHtml += "</td>";
    			}
    			
    			if(filter.get("F101_ATR_VAL").equals("CAL_TO")){
    				filterHtml += "<th>"+filter.get("D_NAME")+"</th>";
    				filterHtml += "<td>";
    				filterHtml += " <input type=\"text\" id=\"toData\" name=\"toData\" placeholder=\"\"><span class=\"cal_btn\" onclick=\"$('#"+filter.get("ADM_OBJ_ID")+"').focus()\"><i class=\"glyphicon glyphicon-calendar\"></i></span>";
    				filterHtml += "<script>";
    				filterHtml += "$(function() {";
    				filterHtml += "    $('#toData').datepicker({";
    				filterHtml += "    	 dateFormat: 'yy-mm-dd'";
    				filterHtml += "             ,showOtherMonths: true";
    				filterHtml += "             ,showMonthAfterYear:true";
    				filterHtml += "             ,showOn: \"both\"";  
    				filterHtml += "             ,buttonImage: \"http://jqueryui.com/resources/demos/datepicker/images/calendar.gif\"";
    				filterHtml += "             ,buttonImageOnly: false";
    				filterHtml += "             ,yearSuffix: \"년\"";
    				filterHtml += "             ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12']";
    				filterHtml += "             ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']";
    				filterHtml += "             ,dayNamesMin: ['일','월','화','수','목','금','토']";
    				filterHtml += "             ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일']";
    				filterHtml += "    });";
    				filterHtml += " $('#toData').datepicker('setDate', 'today');";
    				filterHtml += " $('.ui-datepicker-trigger').hide();";
    				filterHtml += "});";
    				filterHtml += "</script>";
    				filterHtml += "</td>";
    			}
    			
    			if(filter.get("F101_ATR_VAL").equals("SEL_01")){
    				String selOption = filter.get("F104_ATR_VAL");
    				
    				filterHtml += "<th>"+filter.get("D_NAME")+"</th>";
    				filterHtml += "<td>";
					try {
						filterHtml += this.filterHtml("", selOption);
					} catch (IOException e) {
						LOG.error("ReportController.reportPrompt filterHtml create error.", e);
					}
					filterHtml += "	</td>";
    			}
    		}
    	}
    	filterHtml += "	<td>";
		filterHtml += "		<div style=\"float:right;\">";
		filterHtml += "			<button id=\"btnSearch\" class=\"search_df_btn\" style=\"font-size:15px\" onclick=\"doProc();\">실행</button>";
		filterHtml += "		</div>";
		filterHtml += "	</td>";
    	filterHtml += "</tr></table>";
    	
    	model.addAttribute("filterHtml",filterHtml);
    	return "portal/report/reportPrompt";
    }
    
    
    @RequestMapping("/reportExec")
	public String reportExec(HttpSession session, final Model model, HttpServletRequest request,
			@RequestParam(required = false, value = "selectBox") String selectBox,
			@RequestParam(required = false, value = "fromData") String fromData,
			@RequestParam(required = false, value = "toData") String toData) {
		SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);

		String reportId = request.getParameter("reportId");
		
		List<Map<String, String>> reportDetailList = portalService.getReportDetail(reportId);
		model.addAttribute("reportDetailList",reportDetailList);
		
		String connUrl = "";
		String filterHtml = "<table class=\"table_Type1\"><tr>";
		String promptsAnswerXML = "<rsl>";

    	for(int i=0;i<reportDetailList.size();i++) {
    		Map<String,String> filter = reportDetailList.get(i);
    		
    		connUrl = filter.get("OBJ_ATR_VAL_109_NM");
    		
    		if(filter.get("F101_ATR_VAL") != null) {
    			
    			if(filter.get("F101_ATR_VAL").equals("CAL_MM")){
    				filterHtml += "<th>"+filter.get("D_NAME")+"</th>";
    				filterHtml += "<td>";
    				filterHtml += " <input type=\"text\" id=\"fromData\" name=\"fromData\" placeholder=\"\"><span class=\"cal_btn\" onclick=\"$('#"+filter.get("ADM_OBJ_ID")+"').focus()\"><i class=\"glyphicon glyphicon-calendar\"></i></span>";
    				filterHtml += "<script>";
    				filterHtml += "$(function() {";
    				filterHtml += "	$('#fromData').monthpicker({";
    				filterHtml += "	pattern: 'yyyymm',";
    				filterHtml += "	monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']";
    				filterHtml += " });";
    				filterHtml += " $('#fromData').val('"+fromData+"');";
    				filterHtml += "});";
    				filterHtml += "</script>";
    				filterHtml += "</td>";
    				
    				promptsAnswerXML += "<pa pt='' pin='0' did='"+filter.get("ADM_OBJ_ID")+"' tp='10'>";
					promptsAnswerXML += fromData;
					promptsAnswerXML += "</pa>";
    			}
    			
    			if(filter.get("F101_ATR_VAL").equals("CAL_FR")){
    				filterHtml += "<th>"+filter.get("D_NAME")+"</th>";
    				filterHtml += "<td>";
    				filterHtml += " <input type=\"text\" id=\"fromData\" name=\"fromData\" placeholder=\"\"><span class=\"cal_btn\" onclick=\"$('#"+filter.get("ADM_OBJ_ID")+"').focus()\"><i class=\"glyphicon glyphicon-calendar\"></i></span>";
    				filterHtml += "<script>";
    				filterHtml += "$(function() {";
    				filterHtml += "    $('#fromData').datepicker({";
    				filterHtml += "    	 dateFormat: 'yy-mm-dd'";
    				filterHtml += "             ,showOtherMonths: true";
    				filterHtml += "             ,showMonthAfterYear:true";
    				filterHtml += "             ,showOn: \"both\"";  
    				filterHtml += "             ,buttonImage: \"http://jqueryui.com/resources/demos/datepicker/images/calendar.gif\"";
    				filterHtml += "             ,buttonImageOnly: false";
    				filterHtml += "             ,yearSuffix: \"년\"";
    				filterHtml += "             ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12']";
    				filterHtml += "             ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']";
    				filterHtml += "             ,dayNamesMin: ['일','월','화','수','목','금','토']";
    				filterHtml += "             ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일']";
    				filterHtml += "    });";
    				filterHtml += " $('#fromData').datepicker('setDate', 'today');";
    				filterHtml += " $('.ui-datepicker-trigger').hide();";
    				filterHtml += " $('#fromData').val('"+fromData+"');";
    				filterHtml += "});";
    				filterHtml += "</script>";
    				filterHtml += "</td>";
    				
    				promptsAnswerXML += "<pa pt='' pin='0' did='"+filter.get("ADM_OBJ_ID")+"' tp='10'>";
					promptsAnswerXML += fromData;
					promptsAnswerXML += "</pa>";
    			}
    			
    			if(filter.get("F101_ATR_VAL").equals("CAL_TO")){
    				filterHtml += "<th>"+filter.get("D_NAME")+"</th>";
    				filterHtml += "<td>";
    				filterHtml += " <input type=\"text\" id=\"toData\" name=\"toData\" placeholder=\"\"><span class=\"cal_btn\" onclick=\"$('#"+filter.get("ADM_OBJ_ID")+"').focus()\"><i class=\"glyphicon glyphicon-calendar\"></i></span>";
    				filterHtml += "<script>";
    				filterHtml += "$(function() {";
    				filterHtml += "    $('#toData').datepicker({";
    				filterHtml += "    	 dateFormat: 'yy-mm-dd'";
    				filterHtml += "             ,showOtherMonths: true";
    				filterHtml += "             ,showMonthAfterYear:true";
    				filterHtml += "             ,showOn: \"both\"";  
    				filterHtml += "             ,buttonImage: \"http://jqueryui.com/resources/demos/datepicker/images/calendar.gif\"";
    				filterHtml += "             ,buttonImageOnly: false";
    				filterHtml += "             ,yearSuffix: \"년\"";
    				filterHtml += "             ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12']";
    				filterHtml += "             ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']";
    				filterHtml += "             ,dayNamesMin: ['일','월','화','수','목','금','토']";
    				filterHtml += "             ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일']";
    				filterHtml += "    });";
    				filterHtml += " $('#toData').datepicker('setDate', 'today');";
    				filterHtml += " $('.ui-datepicker-trigger').hide();";
    				filterHtml += " $('#toData').val('"+toData+"');";
    				filterHtml += "});";
    				filterHtml += "</script>";
    				filterHtml += "</td>";
    				
    				promptsAnswerXML += "<pa pt='' pin='0' did='"+filter.get("ADM_OBJ_ID")+"' tp='10'>";
					promptsAnswerXML += toData;
					promptsAnswerXML += "</pa>";
    			}
    			
    			if(filter.get("F101_ATR_VAL").equals("SEL_01")){
    				String selOption = filter.get("F104_ATR_VAL");
    				
    				filterHtml += "<th>"+filter.get("D_NAME")+"</th>";
    				filterHtml += "<td>";
					try {
						filterHtml += this.filterHtml(selectBox, selOption);
					} catch (IOException e) {
						LOG.error("ReportController.reportPrompt filterHtml create error.", e);
					}
					filterHtml += "	</td>";
					
					promptsAnswerXML += "<pa pt='' pin='0' did='"+filter.get("ADM_OBJ_ID")+"' tp='10'>";
					if(selectBox != null && !selectBox.equals("")) {
						promptsAnswerXML += "<mi>";
						promptsAnswerXML += "<es>";
						try {
							promptsAnswerXML += this.promptsAnswerXML(selectBox, filter, selOption);
						} catch (IOException e) {
							LOG.error("ReportController.reportExec promptsAnswerXML create error.", e);
						}
						promptsAnswerXML += "</es>";
						promptsAnswerXML += "</mi>";
					} else {
						promptsAnswerXML += "<mi>";
						promptsAnswerXML += "<es/>";
						promptsAnswerXML += "</mi>";
					}
					promptsAnswerXML += "</pa>";
    			}
    		}
    	}
    	filterHtml += "	<td>";
		filterHtml += "		<div style=\"float:right;\">";
		filterHtml += "			<button id=\"btnSearch\" class=\"search_df_btn\" style=\"font-size:15px\" onclick=\"doProc();\">실행</button>";
		filterHtml += "		</div>";
		filterHtml += "	</td>";
    	filterHtml += "</tr></table>";
    	
    	promptsAnswerXML += "</rsl>";

		Map<String, String> toolLoginUserDetail = portalService.getToolLoginUserDetail(sessionInfo.getUserId(), reportId);

		model.addAttribute("toolLoginUserDetail", toolLoginUserDetail);
		model.addAttribute("filterHtml",filterHtml);
		model.addAttribute("connUrl", connUrl);
		try {
			model.addAttribute("promptsAnswerXML", URLEncoder.encode(promptsAnswerXML, "UTF-8").replaceAll("\\+", "%20"));
		} catch (UnsupportedEncodingException e) {
			LOG.error("ReportController.reportExec xml parsing encoding error.", e);
		}
		model.addAttribute("selectBox", selectBox);
		model.addAttribute("fromData", fromData);
		model.addAttribute("toData", toData);
		return "portal/report/reportExec";
	}
    
    @RequestMapping("/reportExec2")
    public String reportExec2(HttpSession session,final Model model, HttpServletRequest request) {
    	SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
    	String reportId = request.getParameter("reportId");
    	List<Map<String,String>> reportDetailList = portalService.getReportDetail(reportId);

    	model.addAttribute("reportDetailList",reportDetailList);
    	return "portal/report/reportExec2";
    }
    
    public Boolean containsYn(Map mapList, String value) {
    	return mapList.containsValue(value);
    }
    
	public String filterHtml(String selectBox, String selOption) throws JsonParseException, JsonMappingException, IOException {
		String filterHtml = "<select id=\"selectBox\" name=\"selectBox\" multiple=\"multiple\" style=\"width:50%;margin:5px;\">";
		ObjectMapper mapper = new ObjectMapper();
		List<Map> list = Arrays.asList(mapper.readValue(selOption, Map[].class));
		for (Map mapList : list) {
			filterHtml += "		<option value=\"" + mapList.get("value") + "\">" + mapList.get("name") + "</option>";
		}
		filterHtml += "</select>";
		filterHtml += "<script>";
		filterHtml += " $(function(){\n";
			
		for (Map mapList : list) {
			if(selectBox != null && selectBox.split(",").length > 0) {
				String[] str = selectBox.split(",");
				for(int j=0;j<str.length;j++) {
					if(this.containsYn(mapList, str[j])) {
						filterHtml += " $('#selectBox option[value=\""+mapList.get("value")+"\"]').attr('selected', true);\n";
					}
				}
			}
		}
		filterHtml += " $('#selectBox').attr('size', '5')";
		filterHtml += " });";
		filterHtml += "</script>";
		return filterHtml;
	}
	
	public String promptsAnswerXML(String selectBox, Map<String,String> filter, String selOption) throws JsonParseException, JsonMappingException, IOException {
		String promptsAnswerXML = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<Map> list = Arrays.asList(mapper.readValue(selOption, Map[].class));
			promptsAnswerXML += "<at did='"+filter.get("F102_ATR_VAL")+"' tp='12'/>";
			for(Map mapList : list) {
				if(selectBox != null && selectBox.split(",").length > 0) {
					String[] str = selectBox.split(",");
					for(int j=0;j<str.length;j++) {
						if(this.containsYn(mapList, str[j])) {
							promptsAnswerXML +=	"<e emt='1' ei='"+filter.get("F102_ATR_VAL")+":"+mapList.get("value")+"' art='1' disp_n='"+mapList.get("name")+"'/>";
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error("ReportController.reportExec xml parsing error.", e);
		}
		return promptsAnswerXML;
	}
	
}