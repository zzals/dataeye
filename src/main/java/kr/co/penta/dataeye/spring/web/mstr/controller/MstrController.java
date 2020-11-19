package kr.co.penta.dataeye.spring.web.mstr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.penta.dataeye.spring.web.mstr.service.MstrService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Controller
@RequestMapping(value = "/mstr", method = { RequestMethod.POST, RequestMethod.GET })
public class MstrController {

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MstrService mstrService;

	@RequestMapping(value = "/latest/reportList", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<Map<String, Object>> reportList(final Model model, HttpServletRequest request,
			@RequestBody final Map<String, Object> requestParam) {
		LOG.debug("jqgrid param => {}", requestParam);
		
		HttpSession session = request.getSession(false);
		SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);

		String searchKey = requestParam.get("searchKey") == null ? "" : (String) requestParam.get("searchKey");
		String searchValue = requestParam.get("searchValue") == null ? ""	: (String) requestParam.get("searchValue");

		HashMap<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("searchKey", searchKey);
		paraMap.put("searchValue", searchValue);
		paraMap.put("sessionInfo", sessionInfo);

		List<Map<String, Object>> reportDetailList = mstrService.reportList(paraMap);
		model.addAttribute("reportList", reportDetailList);
		return reportDetailList;
	}
}
