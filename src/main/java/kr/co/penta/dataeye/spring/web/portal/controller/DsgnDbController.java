package kr.co.penta.dataeye.spring.web.portal.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.spring.web.common.service.CommonService;
import kr.co.penta.dataeye.spring.web.common.service.FileService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.portal.service.DsgnDbService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Controller
@RequestMapping(value = "/portal/dsgn/db", method={RequestMethod.POST, RequestMethod.GET})
public class DsgnDbController {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    private String tempPath = DataEyeSettingsHolder.getInstance().getAttachFile().getUploadTempPath();
    
    @Autowired
    private CommonService commonService;

    @Autowired
    private FileService fileService;
    
    @Autowired
    private DsgnDbService dsgnDbService;
    
    @RequestMapping(params = "oper=downloadTabUploadTpl")
    public void downloadTabUploadTpl(HttpServletRequest request, HttpServletResponse response) {
    	String path = DataEyeSettingsHolder.getInstance().getAttachFile().getTemplatePath();
    	String template = DataEyeSettingsHolder.getInstance().getTemplate().getDsgnTabUploadTemplate();
    	
    	fileService.download(request, response, path+File.separatorChar+template, template);
    }
    
    @RequestMapping(params = "oper=downloadColUploadTpl")
    public void downloadColUploadTpl(HttpServletRequest request, HttpServletResponse response) {
    	String path = DataEyeSettingsHolder.getInstance().getAttachFile().getTemplatePath();
    	String template = DataEyeSettingsHolder.getInstance().getTemplate().getDsgnColUploadTemplate();
    	
    	fileService.download(request, response, path+File.separatorChar+template, template);
    }    

    @RequestMapping(params = "oper=uploadDsgnTab", method = RequestMethod.POST)
    public ResponseEntity<?> uploadDsgnTab(MultipartHttpServletRequest request) {
        Iterator<String> itr = request.getFileNames();

        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String curMonth = sdf.format(dt);

        final String tPath = tempPath + File.separatorChar + curMonth;
        try {
            FileUtils.forceMkdir(new File(tPath));
        } catch (IOException e) {
            LOG.error("upload/temp error :: {}", e);
            throw new ServiceRuntimeException(e);
        }

        Map<String, Object> result = null;
        if (itr.hasNext()) {
            MultipartFile mpf = request.getFile(itr.next());

            if (!mpf.isEmpty()) {
                try {
                	String databaseId = request.getParameter("databaseId");
                	if (databaseId == null || "".equals(databaseId)) {
                		throw new ServiceRuntimeException("선택된 데이터베이스 정보가 없습니다.");
                	}
                	
                    File f = new File(tPath + File.separatorChar + UUID.randomUUID().toString());
                    mpf.transferTo(f);
                    
                    /*
                     * DRM 해제
                     */
                    result = dsgnDbService.uploadDsgnTab(f, databaseId);
                } catch (IOException e) {
                    throw new ServiceRuntimeException(e);                    
                }
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @RequestMapping(params = "oper=importDsgnTab")
    public ResponseEntity<StdResponse> importDsgnTab(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
        Map<String, Object> result = dsgnDbService.importDsgnTab(reqParam, sessionInfo);

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(result, MessageHolder.getInstance().get("common.message.save.complete")), HttpStatus.OK);
    }    

    @RequestMapping(params = "oper=uploadDsgnCol", method = RequestMethod.POST)
    public ResponseEntity<?> uploadDsgnCol(MultipartHttpServletRequest request) {
        Iterator<String> itr = request.getFileNames();

        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String curMonth = sdf.format(dt);

        final String tPath = tempPath + File.separatorChar + curMonth;
        try {
            FileUtils.forceMkdir(new File(tPath));
        } catch (IOException e) {
            LOG.error("upload/temp error :: {}", e);
            throw new ServiceRuntimeException(e);
        }

        Map<String, Object> result = null;
        if (itr.hasNext()) {
            MultipartFile mpf = request.getFile(itr.next());

            if (!mpf.isEmpty()) {
                try {
                	String databaseId = request.getParameter("databaseId");
                	if (databaseId == null || "".equals(databaseId)) {
                		throw new ServiceRuntimeException("선택된 데이터베이스 정보가 없습니다.");
                	}
                	
                    File f = new File(tPath + File.separatorChar + UUID.randomUUID().toString());
                    mpf.transferTo(f);
                    
                    /*
                     * DRM 해제
                     */
                    result = dsgnDbService.uploadDsgnCol(f, databaseId);
                } catch (IOException e) {
                    throw new ServiceRuntimeException(e);                    
                }
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @RequestMapping(params = "oper=importDsgnCol")
    public ResponseEntity<StdResponse> importDsgnCol(HttpSession session,
            @RequestBody(required=true) Map<String, Object> reqParam) {
        StdResponse stdResponse = new StdResponse();
        SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
        Map<String, Object> result = dsgnDbService.importDsgnCol(reqParam, sessionInfo);

        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(result, MessageHolder.getInstance().get("common.message.save.complete")), HttpStatus.OK);
    }
    
    @RequestMapping(params="oper=genDDL")
    public ResponseEntity<StdResponse> genDDL(HttpSession session,
            @RequestBody(required=true) List<Map<String, Object>> datas) {
        StdResponse stdResponse = new StdResponse();
        
        String ddlScript = dsgnDbService.genDDL(datas);
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(ddlScript), HttpStatus.OK);
    }
}

