package kr.co.penta.dataeye.customizing.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/ers", method = RequestMethod.POST)
public class ErsAuthGrntApiController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/authGrntApi", method = RequestMethod.POST)
	public @ResponseBody Object authGrntApi(HttpServletRequest request, @RequestBody String body) throws Exception {
		String result = "fail";
		String error = "none";

		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			log.info("data : {}", body);
			if (body != null && !body.equals("")) {
				result = "succes";
			} else {
				throw new Exception();
			}
		} catch (NullPointerException e) {
			error = e.toString();
		} finally {
			resultMap.put("result", result);
			resultMap.put("param", body);
			resultMap.put("error", error);
		}
		log.info("result : {}", resultMap);
		return resultMap;

	}

}
