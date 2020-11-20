package kr.co.penta.dataeye.spring.web.portal.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;import kr.co.penta.dataeye.common.util.AES256Util;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.web.http.response.StdResponse;
import kr.co.penta.dataeye.spring.web.portal.service.PortalBoardService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;


@Controller
@RequestMapping(value="/portal/board", method={RequestMethod.POST, RequestMethod.GET})
public class PortalBoardController {
	
	   @Autowired private PortalBoardService portalBoardService;
	
	
	   @RequestMapping("/boardWriteProc")
	    public ResponseEntity<StdResponse> boardWriteProc(MultipartHttpServletRequest request) throws IllegalStateException, IOException {
		   StdResponse stdResponse = new StdResponse();
		   
		   HttpSession session = request.getSession(false);
		   SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session); //getSessionAttribute(request.getSession());

		   Map<String,Object> paraMap = new HashMap<String,Object>();
		   paraMap.put("brd_type_id", (String)request.getParameter("brd_type_id"));
		   paraMap.put("brd_nm", (String)request.getParameter("brd_nm"));
		   paraMap.put("brd_sbst", (String)request.getParameter("brd_sbst"));
		   paraMap.put("brd_st_date", (String)request.getParameter("brd_st_date"));
		   paraMap.put("brd_ed_date", (String)request.getParameter("brd_ed_date"));
		   paraMap.put("cretr_id", sessionInfo.getUserId());
		   paraMap.put("chgr_id", sessionInfo.getUserId());
		   
		   // 넘어온 파일을 리스트로 저장
	        List<MultipartFile> mf = request.getFiles("attacheFile");
	        if (mf.size() == 1 && mf.get(0).getOriginalFilename().equals("")) {
	             
	        } else {
	        	String realFolder = DataEyeSettingsHolder.getInstance().getAttachFile().getUploadReadPath(); //서버용
	            for (int i = 0; i < mf.size(); i++) {
	                // 파일 중복명 처리
	                String genId = UUID.randomUUID().toString();
	                // 본래 파일명
	                String originalfileName = mf.get(i).getOriginalFilename();
	                 
	                String saveFileName = genId + "." + getExtension(originalfileName);
	                // 저장되는 파일 이름
	 
	                String savePath = realFolder + File.separator + saveFileName; // 저장 될 파일 경로
	 
	                long fileSize = mf.get(i).getSize(); // 파일 사이즈
	 
	                mf.get(i).transferTo(new File(savePath)); // 파일 저장
	                
	                
	                System.out.println(originalfileName + ":" +  saveFileName + ":" + fileSize);
	 
	                paraMap.put("file_path_nm", realFolder);
	                paraMap.put("file_nm", originalfileName);
	                paraMap.put("file_sys_nm", saveFileName);
	                paraMap.put("file_size", fileSize  );
	               // 
	            }
	        }
	        
 	        portalBoardService.insertPortalBoard(paraMap);
		
		   return new ResponseEntity<StdResponse>(stdResponse.setSuccess(null), HttpStatus.OK);
	    }
	   
	   @RequestMapping("/boardReplyWriteProc")
	    public ResponseEntity<StdResponse> boardReplyWriteProc(HttpServletRequest request) throws IllegalStateException, IOException {
		   StdResponse stdResponse = new StdResponse();	
		   
		   HttpSession session = request.getSession(false);
		   SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session); //getSessionAttribute(request.getSession());

		   Map<String,Object> paraMap = new HashMap<String,Object>();
		   paraMap.put("brd_id", (String)request.getParameter("brd_id"));
		   paraMap.put("brd_type_id", (String)request.getParameter("brd_type_id"));		  
		   paraMap.put("rpl_sbst", (String)request.getParameter("rpl_sbst"));
		   paraMap.put("cretr_id", sessionInfo.getUserId());
		   paraMap.put("chgr_id", sessionInfo.getUserId());
		   
	        portalBoardService.insertPortalBoardReply(paraMap);
		
		   return new ResponseEntity<StdResponse>(stdResponse.setSuccess(null), HttpStatus.OK);
	    }
	   
	   
	   
	   @RequestMapping("/boardUpdateProc")
	    public ResponseEntity<StdResponse> boardUpdateProc(MultipartHttpServletRequest request) throws IllegalStateException, IOException {
		   StdResponse stdResponse = new StdResponse();
		   
		   HttpSession session = request.getSession(false);
		   SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session); //getSessionAttribute(request.getSession());
		   
		   Map<String,Object> paraMap = new HashMap<String,Object>();
		   paraMap.put("brd_id", (String)request.getParameter("brd_id"));
		   paraMap.put("brd_type_id", (String)request.getParameter("brd_type_id"));
		   paraMap.put("file_id", (String)request.getParameter("file_id"));
		   paraMap.put("brd_nm", (String)request.getParameter("brd_nm"));
		   paraMap.put("brd_sbst", (String)request.getParameter("brd_sbst"));
		   paraMap.put("brd_st_date", (String)request.getParameter("brd_st_date"));
		   paraMap.put("brd_ed_date", (String)request.getParameter("brd_ed_date"));
		   paraMap.put("cretr_id", sessionInfo.getUserId());
		   paraMap.put("chgr_id", sessionInfo.getUserId());
		   
		   // 넘어온 파일을 리스트로 저장
	        List<MultipartFile> mf = request.getFiles("attacheFile");
	        if (mf.size() == 1 && mf.get(0).getOriginalFilename().equals("")) {	        	
	             
	        } else {
	        	String realFolder = DataEyeSettingsHolder.getInstance().getAttachFile().getUploadReadPath();
	            for (int i = 0; i < mf.size(); i++) {
	                // 파일 중복명 처리
	                String genId = UUID.randomUUID().toString();
	                // 본래 파일명
	                String originalfileName = mf.get(i).getOriginalFilename();
	                 
	                String saveFileName = genId + "." + getExtension(originalfileName);
	                // 저장되는 파일 이름
	 
	                String savePath = realFolder + File.separator + saveFileName; // 저장 될 파일 경로
	 
	                long fileSize = mf.get(i).getSize(); // 파일 사이즈
	 
	                mf.get(i).transferTo(new File(savePath)); // 파일 저장

	                paraMap.put("file_path_nm", realFolder);
	                paraMap.put("file_nm", originalfileName);
	                paraMap.put("file_sys_nm", saveFileName);
	                paraMap.put("file_size", fileSize  );
	               
	            }
	        }
	        
	        portalBoardService.updatePortalBoard(paraMap);		
		   return new ResponseEntity<StdResponse>(stdResponse.setSuccess(null), HttpStatus.OK);
	    }
	   
	   
	   
	   @RequestMapping("/doDeleteProc")
	    public ResponseEntity<StdResponse> doDeleteProc(HttpServletRequest request) throws IllegalStateException, IOException {
		   System.out.println("##doDeleteProc");
		   StdResponse stdResponse = new StdResponse();
		   
		   HttpSession session = request.getSession(false);
		   SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session); //getSessionAttribute(request.getSession());
		   
		   String boardAuthKey = (request.getParameter("boardAuthKey")==null)?"":(String)request.getParameter("boardAuthKey");
		   String brd_id = (request.getParameter("brd_id")==null)?"0":(String)request.getParameter("brd_id");
		   String brd_type_id = (request.getParameter("brd_type_id")==null)?"":(String)request.getParameter("brd_type_id");
		   
		   String boardAuthKey2 = "";
		try {
			boardAuthKey2 = (new AES256Util(session.getId())).encrypt( brd_id);
						
		} catch (GeneralSecurityException e) {
			System.out.println("e" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		   if(boardAuthKey.equals(boardAuthKey2)) {
			   
			   Map<String,Object> paraMap = new HashMap<String,Object>();
			   paraMap.put("brd_id",brd_id);
			   paraMap.put("brd_type_id",brd_type_id);
			   paraMap.put("chgr_id", sessionInfo.getUserId());
			   		
			   Map<String,String> fileInfo = portalBoardService.getPortalBoardFileInfo2(brd_id);
		        
			   		   
			    if(fileInfo!=null) {
			    	   
			    	paraMap.put("file_id",fileInfo.get("FILE_ID"));
			        String sys_name = fileInfo.get("FILE_SYS_NM");
			        String path = fileInfo.get("FILE_PATH_NM");
		        	String savePath = path + File.separator + sys_name; // 저장 될 파일 경로
		        	
		        	File file = new File(savePath);
		        	if(file.exists()) {
		        		file.delete();
		        	}	        	
		        }	    
		        portalBoardService.deletePortalBoard(paraMap);
		        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(null), HttpStatus.OK);
			   
		   } else {
			   return new ResponseEntity<StdResponse>(stdResponse.setFail("잘못된 접근입니다."), HttpStatus.OK);
		   }
	    }
	   
	   
	   @RequestMapping("/doReadCntProc")
	    public ResponseEntity<StdResponse> doReadCntProc(HttpServletRequest request) throws IllegalStateException, IOException {
		   StdResponse stdResponse = new StdResponse();
		   
		   HttpSession session = request.getSession(false);
		   
		   String boardAuthKey = (request.getParameter("boardAuthKey")==null)?"":(String)request.getParameter("boardAuthKey");
		   String brd_id = (request.getParameter("brd_id")==null)?"0":(String)request.getParameter("brd_id");
		   String brd_type_id = (request.getParameter("brd_type_id")==null)?"":(String)request.getParameter("brd_type_id");
		   
		   
		   String boardAuthKey2 = "";
		try {
			boardAuthKey2 = (new AES256Util(session.getId())).encrypt( brd_id);
						
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		   if(boardAuthKey.equals(boardAuthKey2)) {
			   Map<String,Object> paraMap = new HashMap<String,Object>();
			   paraMap.put("brd_id",brd_id);
			   paraMap.put("brd_type_id",brd_type_id);
			   
		        portalBoardService.updateBoardReadCnt(paraMap);
		        return new ResponseEntity<StdResponse>(stdResponse.setSuccess(null), HttpStatus.OK);
			   
		   } else {
			   return new ResponseEntity<StdResponse>(stdResponse.setFail("잘못된 접근입니다."), HttpStatus.OK);
		   }
	    }
	   
	   
	   public String getExtension(String fileName) {
		   
		   return (fileName==null)?"":fileName.substring(fileName.lastIndexOf(".")+1);
	   }
	   
	  
	    @RequestMapping("/download")
	    public void download(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
	        
		   
		   String file_id = (request.getParameter("file_id")==null)?"":request.getParameter("file_id");
		   
		   Map<String,String> fileInfo = portalBoardService.getPortalBoardFileInfo(file_id);
	        
	        String filename = fileInfo.get("FILE_NM");
	        String downname = fileInfo.get("FILE_SYS_NM");
	        String path = fileInfo.get("FILE_PATH_NM");
	        
	        
	        String realPath = "";
	        System.out.println("downname: "+downname);
	        if (filename == null || "".equals(filename)) {
	            filename = downname;
	        }
	         
	        try {
	            String browser = request.getHeader("User-Agent"); 
	            //파일 인코딩 
	            if (browser.contains("MSIE") || browser.contains("Trident")
	                    || browser.contains("Chrome")) {
	                filename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+",
	                        "%20");
	            } else {
	                filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
	            }
	        } catch (UnsupportedEncodingException ex) {
	            System.out.println("UnsupportedEncodingException");
	        }
	        
	        realPath = path + File.separator + downname;
	        System.out.println(realPath);
	        File file1 = new File(realPath);
	        if (!file1.exists()) {
	            return ;
	        }
	         
	        // 파일명 지정        
	        response.setContentType("application/octer-stream");
	        response.setHeader("Content-Transfer-Encoding", "binary;");
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
	        try {
	            OutputStream os = response.getOutputStream();
	            FileInputStream fis = new FileInputStream(realPath);
	 
	            int ncount = 0;
	            byte[] bytes = new byte[512];
	 
	            while ((ncount = fis.read(bytes)) != -1 ) {
	                os.write(bytes, 0, ncount);
	            }
	            fis.close();
	            os.close();
	        } catch (FileNotFoundException ex) {
	            System.out.println("FileNotFoundException");
	        } catch (IOException ex) {
	            System.out.println("IOException");
	        }
	    }
	    
	    @RequestMapping("/messageWriteProc")
	    public ResponseEntity<StdResponse> messageWriteProc(MultipartHttpServletRequest request) throws IllegalStateException, IOException {
		   StdResponse stdResponse = new StdResponse();
		   
		   HttpSession session = request.getSession(false);
		   SessionInfo sessionInfo = SessionInfoSupport.getSessionInfo(session); //getSessionAttribute(request.getSession());
		   
		   String [] sendMothod =  request.getParameterValues("send_mothod");
		   List<String> mothods = Arrays.asList(sendMothod);
		   String mothodStr = mothods.stream().collect(Collectors.joining(","));

		   Map<String,Object> paraMap = new HashMap<String,Object>();
		   paraMap.put("title", (String)request.getParameter("title"));
		   paraMap.put("cntnt", (String)request.getParameter("cntnt"));
		   paraMap.put("send_type", (String)request.getParameter("send_type"));
		   paraMap.put("writ_id", sessionInfo.getUserId());
		   paraMap.put("send_target", (String)request.getParameter("send_target"));
		   paraMap.put("send_mothod", mothodStr);
		   paraMap.put("editor_type", (String)request.getParameter("editor_type"));
		   
		   portalBoardService.insertPortalMessage(paraMap);
		
		   return new ResponseEntity<StdResponse>(stdResponse.setSuccess(null), HttpStatus.OK);
	    }

}
