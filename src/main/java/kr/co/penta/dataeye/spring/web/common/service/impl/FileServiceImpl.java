package kr.co.penta.dataeye.spring.web.common.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.common.exception.SystemException;
import kr.co.penta.dataeye.spring.web.common.service.FileService;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);
    
    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, String fileName, String downName) {
    	File f = new File(fileName);
    	download(request, response, f, downName);
    }
    
    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, File f, String downName) {
    	try {
			if(request.getHeader("User-Agent").contains("MSIE")) {
    			downName = URLEncoder.encode(downName, "UTF-8");
    			downName = downName.replaceAll("\\+", " ");
	        } else if(request.getHeader("User-Agent").contains("Mozilla")) {
	        	downName = new String(downName.getBytes("UTF-8"), "ISO-8859-1");
	        }
    	} catch (UnsupportedEncodingException e) {
			throw new SystemException(e);
		}
        
        response.setContentType("application/octet-stream; utf-8");
        /*response.setContentLength((int)f.length());*/

        response.setHeader("Content-Disposition", "attachment; filename=\"" + downName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        
        try {
    		OutputStream out = response.getOutputStream();
			FileInputStream fis = null;
	        BufferedInputStream bis = null;
	        try {
	            fis = new FileInputStream(f);
	            
	            byte[] buf = new byte[2014];
	            int read = 0;
	            bis = new BufferedInputStream(fis);
	            
	            while((read = bis.read(buf)) != -1) {
	                out.write(buf, 0, read);
	            }
	            
	            out.flush();
	        } catch(Exception e){
	            throw e;
	        }finally{
	            if(fis != null){
	                try{
	                    fis.close();
	                }catch(Exception e){}
	            }
	            if(bis != null){
	                try{
	                    bis.close();
	                }catch(Exception e){}
	            }
	
	            if(out != null){
	                try{
	                    out.close();
	                }catch(Exception e){}
	            }
	        }
        } catch (Exception e) {
        	LOG.error("download error", e);
        	throw new SystemException(e);
		}
    }
}
