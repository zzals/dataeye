package kr.co.penta.dataeye.spring.web.credit.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings;
import kr.co.penta.dataeye.spring.web.credit.service.BoardService;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;

@Controller
@RequestMapping(value="/board", method={RequestMethod.POST, RequestMethod.GET})
public class BoardController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

    

	@Autowired 
	private BoardService boardService;
	@Autowired
    private DataEyeSettings dataEyeSettings;

	
	
	
	@RequestMapping("/list")
    public String list(final Model model, HttpServletRequest request,
                        @RequestParam(required=true, value="viewId") final String viewId,
                        @RequestParam(required=false, value="data", defaultValue="{}") final String data
            ) {

        LOG.info("data : {}", data);
        model.addAttribute("data", data);
        HttpSession session = request.getSession(false);
        LOG.info("viewId : {}", viewId);
        LOG.info("session id !!!: {}", session.getId());
        Gson gson = new Gson();
        Map<String, Object> param = gson.fromJson(data, new TypeToken<Map<String, Object>>(){}.getType());
        
        Map<String, Object> retMap=boardService.getBoardList(param);
        String json = gson.toJson(param); 
        
        model.addAttribute("boardList", retMap.get("boardList"));
        model.addAttribute("pageNavi", retMap.get("pageNavi"));
        model.addAttribute("tot_cnt", retMap.get("tot_cnt"));
        model.addAttribute("page_size", retMap.get("page_size"));
        model.addAttribute("data", json);
        
        return viewId;
    }
	
	@RequestMapping("/view")
    public String view(final Model model, HttpServletRequest request,
                        @RequestParam(required=true, value="viewId") final String viewId,
                        @RequestParam(required=false, value="data", defaultValue="{}") final String data
            ) {

       
        model.addAttribute("data", data);
        HttpSession session = request.getSession(false);       
        Gson gson = new Gson();
        Map<String, Object> param = gson.fromJson(data, new TypeToken<Map<String, Object>>(){}.getType());
        String json = gson.toJson(param);
        
        
        Map<String, Object> retMap=boardService.getBoardView(param, "view");
        
         
        
        model.addAttribute("boardView", retMap.get("boardView"));
        model.addAttribute("fileList", retMap.get("fileList"));        
        model.addAttribute("data", json);
        
        return viewId;
    }
	
	@RequestMapping("/write")
    public String write(final Model model, HttpServletRequest request,
                        @RequestParam(required=true, value="viewId") final String viewId,
                        @RequestParam(required=false, value="data", defaultValue="{}") final String data
            ) {

        
        Gson gson = new Gson();
        Map<String, Object> param = gson.fromJson(data, new TypeToken<Map<String, Object>>(){}.getType());
        String json = gson.toJson(param);
        model.addAttribute("data", json);
        
        
        return viewId;
    }
	
	@RequestMapping("/update")
    public String update(final Model model, HttpServletRequest request,
                        @RequestParam(required=true, value="viewId") final String viewId,
                        @RequestParam(required=false, value="data", defaultValue="{}") final String data
            ) {
		
		
		
        
        Gson gson = new Gson();
        Map<String, Object> param = gson.fromJson(data, new TypeToken<Map<String, Object>>(){}.getType());
        String json = gson.toJson(param);
        model.addAttribute("data", json);
        
        Map<String, Object> retMap=boardService.getBoardView(param, "update");
        
        List<Map<String,Object>> list=(List<Map<String, Object>>) retMap.get("fileList");
        String fileList = gson.toJson(list);
        
        model.addAttribute("boardView", retMap.get("boardView"));
        model.addAttribute("fileList", fileList);        
        model.addAttribute("data", json);
        
        return viewId;
    }
	
	@RequestMapping("/writeDo")
    public ResponseEntity<StdResponse> writeDo (
    		@RequestBody(required=true) CamelMap reqParam,
    		HttpSession session) throws Exception {
        
		StdResponse stdResponse = new StdResponse();
        Map<String, Object> resultMap= new HashMap<String, Object>();
        
        boardService.insertBoard(reqParam, session);
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(resultMap), HttpStatus.OK);
    }
	
	@RequestMapping("/replyDo")
    public ResponseEntity<StdResponse> replyDo (
    		@RequestBody(required=true) CamelMap reqParam,
    		HttpSession session) throws Exception {
        
		StdResponse stdResponse = new StdResponse();
        Map<String, Object> resultMap= new HashMap<String, Object>();
        
        boardService.insertReplyBoard(reqParam, session);
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(resultMap), HttpStatus.OK);
    }
	
	@RequestMapping("/updateDo")
    public ResponseEntity<StdResponse> updateDo (
    		@RequestBody(required=true) CamelMap reqParam,
    		HttpSession session) throws Exception {
        
		StdResponse stdResponse = new StdResponse();
        Map<String, Object> resultMap= new HashMap<String, Object>();
        
        boardService.updateBoard(reqParam, session);
        
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(resultMap), HttpStatus.OK);
    }
	
	/**
     * 파일업로드 처리
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(path = "/fileUpload")
    public ResponseEntity<StdResponse> datasetFileupload(HttpServletRequest request) throws IOException {
        
		StdResponse stdResponse = new StdResponse();
		Map<String, Object> resultMap= new HashMap<String, Object>();

		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
		MultipartFile uploadfile = multi.getFile("uploadfile");
		
		//파일경로 얻기
		String tmp_path=dataEyeSettings.getAttachFile().getUploadTempPath();
		
		
		if (uploadfile != null) {

			System.out.println("파라미터명" + uploadfile.getName());
			System.out.println("파일크기" + uploadfile.getSize());
			System.out.println("파일 존재" + uploadfile.isEmpty());
			System.out.println("오리지날 파일 이름" + uploadfile.getOriginalFilename());
			

			// 2. File 사용
            File file = new File(tmp_path +File.separator+ uploadfile.getOriginalFilename());
            uploadfile.transferTo(file);
            
            resultMap.put("FILE_NM", uploadfile.getOriginalFilename());
            resultMap.put("FILE_PATH", tmp_path +File.separator);
            
		}    
		
		
        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(resultMap), HttpStatus.OK);
    }
	
	
}