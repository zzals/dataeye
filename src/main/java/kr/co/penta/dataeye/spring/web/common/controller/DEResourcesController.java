package kr.co.penta.dataeye.spring.web.common.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.penta.dataeye.common.entities.meta.PenCdEntity;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings;
import kr.co.penta.dataeye.spring.web.common.service.ResourcesService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;

@Controller
@RequestMapping(value={"/deresources"}, method={RequestMethod.POST, RequestMethod.GET})
public class DEResourcesController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private ResourcesService resourcesService;
	
	@RequestMapping("/i18n/{propertiesName}")
	public void geti18n(@PathVariable String propertiesName, HttpServletResponse response) throws IOException {
		OutputStream outputStream = response.getOutputStream();
		Resource resource = new ClassPathResource("/i18n/" + propertiesName + ".properties");
		
		InputStream inputStream = null;
		try {
			inputStream = resource.getInputStream();
	
			List<String> readLines = IOUtils.readLines(inputStream, Charset.defaultCharset());
			IOUtils.writeLines(readLines, null, outputStream, Charset.defaultCharset());
		} catch (IOException e){
			log.info("message file not found : {}", e.getMessage());
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}
	}
	
	@RequestMapping("/locale/lang")
	public void getLocale(HttpSession session
			, HttpServletResponse response) throws IOException {
		OutputStream outputStream = response.getOutputStream();
		Locale locale = LocaleContextHolder.getLocale();
		log.debug("current locale : {}", locale);
		
        if (locale == null ) {
            locale = Locale.KOREA;
            LocaleContextHolder.setLocale(locale);
        }
        
        IOUtils.write(locale.getLanguage(), outputStream, Charset.defaultCharset());
        IOUtils.closeQuietly(outputStream);
	}
	
	@Autowired private DataEyeSettings dataEyeSettings;
	@RequestMapping("/dataeye/settings")
    public ResponseEntity<StdResponse> getSettingsDefault() throws IOException {
	    StdResponse stdResponse = new StdResponse();
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(dataEyeSettings), HttpStatus.OK);
    }
	
	@RequestMapping("/commonlib/js")
    public String getCommonlibJs() {
        return "common/import/common_js";
    }
    
    @RequestMapping("/commonlib/css")
    public String getCommonlibCss() {
        return "common/import/common_css";
    }
    
    @RequestMapping("/commonlib/popup_css")
    public String getCommonlibPopupCss() {
        return "common/import/common_popup_css";
    }
    
    @RequestMapping("/findAllCdByGroup")
    public ResponseEntity<StdResponse> findAllCdByGroup() {
        StdResponse stdResponse = new StdResponse();
        List<PenCdEntity> penCdEntities = resourcesService.findAllCdByGroup();
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(penCdEntities), HttpStatus.OK);
    }
    
    @RequestMapping("/objType")
    public ResponseEntity<StdResponse> getObjTypeInfo() throws IOException {
        StdResponse stdResponse = new StdResponse();
        List<Map<String, Object>> rsp = resourcesService.getObjTypeInfo();
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(rsp), HttpStatus.OK);
    }
}