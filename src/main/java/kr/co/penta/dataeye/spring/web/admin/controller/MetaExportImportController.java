package kr.co.penta.dataeye.spring.web.admin.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.consts.META_EXP_CONST.RUN_STATUS;
import kr.co.penta.dataeye.spring.web.admin.executor.MetaExportExecutor;
import kr.co.penta.dataeye.spring.web.admin.executor.MetaImportExecutor;
import kr.co.penta.dataeye.spring.web.admin.service.MetaExpImpService;
import kr.co.penta.dataeye.spring.web.http.response.FileResponse;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Controller
@RequestMapping(value = "/admin", method={RequestMethod.POST, RequestMethod.GET})
@Secured(value={"ROLE_ADMIN", "ROLE_SYSTEM"})
public class MetaExportImportController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	private String EXP_FILE_NM = "de-export.exp";
	
	private Thread exportThread = null;
	private MetaExportExecutor metaExportExecutor= null;
	
	private Thread importThread = null;
	private MetaImportExecutor metaImportExecutor= null;
	
	
	@Autowired
	private MetaExpImpService metaExpImpService;
	
	@Autowired
	private DataSource dataeyeDataSource;
	
	@RequestMapping(value="/export/meta", params="oper=status")
    public ResponseEntity<StdResponse> exportMetaStatus(HttpSession session,
            @RequestBody(required=false) Map<String, Object> reqParam) throws JsonProcessingException {
		StdResponse stdResponse = new StdResponse();
		if (exportThread == null) {
			return new ResponseEntity<>(stdResponse.setFail("Meta Export 진행중인 작업이 없습니다."), HttpStatus.OK);
		} else {
			if (RUN_STATUS.ERROR.equals(metaExportExecutor.getRunnStatus())) {
				return new ResponseEntity<>(stdResponse.setFail("Meta Export 작업중 오류가 발생하였습니다.\n관리자에게 문의 하시기 바랍니다."), HttpStatus.OK);
			}
		}
		
		Map<String, Object> resultMap = metaExportExecutor.getResponse().getProcStatus();
		return new ResponseEntity<>(stdResponse.setSuccess(resultMap), HttpStatus.OK);
    }
	
	@RequestMapping(value="/export/meta", params="oper=download")
    public ResponseEntity<?> exportMetaDownload(HttpSession session,
            @RequestBody(required=false) Map<String, Object> reqParam) throws JsonProcessingException {
		StdResponse stdResponse = new StdResponse();
		if (exportThread == null) {
			return new ResponseEntity<>(stdResponse.setFail("Meta Export 대상이 없습니다.\nMeta Export 후 다운로드 하시기 바랍니다."), HttpStatus.OK);
		} else {
			if (RUN_STATUS.INIT.equals(metaExportExecutor.getRunnStatus()) || RUN_STATUS.RUNNING.equals(metaExportExecutor.getRunnStatus())) {
				return new ResponseEntity<>(stdResponse.setFail("Meta Export 작업이 진행중 입니다.\nMeta Export 완료 후 다운로드 하시기 바랍니다."), HttpStatus.OK);
			} else if (RUN_STATUS.ERROR.equals(metaExportExecutor.getRunnStatus())) {
				return new ResponseEntity<>(stdResponse.setFail("Meta Export 작업중 오류가 발생하였습니다.\n관리자에게 문의 하시기 바랍니다."), HttpStatus.OK);
			}
		}
		
		Map<String, Object> resultMap = metaExportExecutor.getResponse().getData();
		ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        byte[] buf = mapper.writeValueAsBytes(resultMap);
        return ResponseEntity
                .ok()
                .contentLength(buf.length)
                .header("Content-Disposition", "attachment; filename=\""+EXP_FILE_NM+"\"")
                .contentType(
                        MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new ByteArrayInputStream(buf)));
    }
	
	@RequestMapping(value="/export", params="oper=meta")
    public ResponseEntity<StdResponse> exportMeta(HttpSession session,
            @RequestBody(required=false) Map<String, Object> reqParam) {
		if (reqParam == null) {
			reqParam = new HashMap<>();
		}
		
        List<String> objTypeIds = new ArrayList<>();
        objTypeIds.add("010102L");
        objTypeIds.add("010103L");
        
        reqParam.put("COMM_CD", true);
        reqParam.put("OBJ_TYPE", true);
        reqParam.put("REL_TYPE", true);
        reqParam.put("ATR", true);
        reqParam.put("OBJ", objTypeIds);        
        
        SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session);
        StdResponse stdResponse = new StdResponse();
        if (exportThread == null || (!exportThread.isAlive() && metaExportExecutor.isDownload())) {
        	Connection conn = null;
			try {
				conn = dataeyeDataSource.getConnection();
			} catch (SQLException e) {
				throw new ServiceRuntimeException("unable to get connection");
			}
        	
        	metaExportExecutor = new MetaExportExecutor(reqParam, sessionInfo.getUserId(), conn);
        	exportThread = new Thread(metaExportExecutor);
        	exportThread.setDaemon(true);
        	
        	exportThread.start();

        	return new ResponseEntity<>(stdResponse.setSuccess(), HttpStatus.OK);
        } else {
        	return new ResponseEntity<>(stdResponse.setFail("진행중인 Meta Export 작업이 있습니다.\n기존 작업 완료 후 진행하시기 바랍니다."), HttpStatus.OK);
        }
    }
	
	@RequestMapping(value="/import", params="oper=meta", method = RequestMethod.POST)
    public ResponseEntity<StdResponse> importMeta(MultipartHttpServletRequest request) throws JsonProcessingException {
		StdResponse stdResponse = new StdResponse();
        SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(request.getSession());

        Iterator<String> itr = request.getFileNames();
        if (itr.hasNext()) {
            MultipartFile mpf = request.getFile(itr.next());
            if (mpf.isEmpty()) {
            	
            } else {
                InputStream is = null;
                Map<String, Object> dataMap = new HashMap<>();
				try {
					is = mpf.getInputStream();
	                ObjectMapper mapper = new ObjectMapper();
	                dataMap = mapper.readValue(is, Map.class);
				} catch (IOException e) {
					throw new ServiceRuntimeException("파일 포멧이 올바르지 않습니다.\n관리자에게 문의하세요.");
				} finally {
					try { is.close(); } catch (IOException e) {}
				}
				
				if (importThread == null || (!importThread.isAlive() && metaImportExecutor.isFinished())) {
                	Connection conn = null;
        			try {
        				conn = dataeyeDataSource.getConnection();
        			} catch (SQLException e) {
        				throw new ServiceRuntimeException("unable to get connection");
        			}
                	
                	metaImportExecutor = new MetaImportExecutor(dataMap, sessionInfo.getUserId(), conn);
                	importThread = new Thread(metaImportExecutor);
                	importThread.setDaemon(true);
                	
                	importThread.start();

                	return new ResponseEntity<>(stdResponse.setSuccess(), HttpStatus.OK);
                } else {
                	return new ResponseEntity<>(stdResponse.setFail("진행중인 Meta Import 작업이 있습니다.\n기존 작업 완료 후 진행하시기 바랍니다."), HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(stdResponse.setSuccess(), HttpStatus.OK);
    }
}
