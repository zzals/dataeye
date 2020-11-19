package kr.co.penta.dataeye.spring.web.common.controller;

import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.web.common.service.MetaService;
import kr.co.penta.dataeye.spring.web.http.response.FileResponse;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = {"/file", "/portal/file", "/admin/file", "/system/file"}, method={RequestMethod.POST, RequestMethod.GET})
public class FileController {
    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private MetaService metaService;

    private String tempPath = DataEyeSettingsHolder.getInstance().getAttachFile().getUploadTempPath();
    private String realPath = DataEyeSettingsHolder.getInstance().getAttachFile().getUploadReadPath();

    @RequestMapping(value = "/upload/temp", method = RequestMethod.POST)
    public ResponseEntity<?> uploadTemp(MultipartHttpServletRequest request) {
        LOG.debug("FileController!!!!");
        List<FileResponse> files = new LinkedList<>();
        Iterator<String> itr = request.getFileNames();

        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String curMonth = sdf.format(dt);

        final String tPath = tempPath + File.separatorChar + curMonth;
        try {
            FileUtils.forceMkdir(new File(tPath));
        } catch (IOException e) {
            LOG.error("upload/temp error :: {}", e);
            final FileResponse fileUploadResult = new FileResponse();
            fileUploadResult.setStatus("F");
            fileUploadResult.setMessage(e.getMessage());
            files.add(fileUploadResult);
        }

        while (itr.hasNext()) {
            MultipartFile mpf = request.getFile(itr.next());
            LOG.debug(mpf.getOriginalFilename() + " uploaded! " + files.size());

            if (!mpf.isEmpty()) {
                final FileResponse fileUploadResult = new FileResponse();
                fileUploadResult.setFileLocName(mpf.getOriginalFilename());
                fileUploadResult.setFilePhyName(UUID.randomUUID().toString());
                fileUploadResult.setPath(tPath);
                double mdf_size = (float) mpf.getSize() / 1024;
                mdf_size = Math.round(mdf_size * 100d) / 100d;
                fileUploadResult.setFileSize(mdf_size + " KB");
                fileUploadResult.setFileType(mpf.getContentType());

                try {

//                    fileUploadResult.setBytes(mpf.getBytes());
//                    final File file = new File(tPath + File.separatorChar + fileUploadResult.getFilePhyName());
//                    FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(file));

                    //BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(tPath + File.separatorChar + fileUploadResult.getFilePhyName())));
                    
                    File f = new File(tPath + File.separatorChar + fileUploadResult.getFilePhyName());
                    mpf.transferTo(f);
                    //stream.write(mpf.getBytes());
                    //stream.close();

                } catch (IOException e) {
                    LOG.error("upload/temp error :: {}", e);
                    fileUploadResult.setStatus("F");
                    fileUploadResult.setMessage(e.getMessage());
                }
                files.add(fileUploadResult);
            }
        }
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @RequestMapping(value = "/removeTemp", method = RequestMethod.POST)
    public ResponseEntity<?> removeTemp(
            @RequestBody Map<String, Object> reqParam,
            HttpSession session) {
        LOG.debug("reqParam : {}", reqParam);

        String path = (String) reqParam.get("path") + File.separatorChar + reqParam.get("filePhyName");

        File f = new File(path);

        if (f.isFile()) {
            FileUtils.deleteQuietly(f);
        }

        return new ResponseEntity<>(reqParam, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity<?> remove(
            @RequestBody Map<String, Object> reqParam,
            HttpSession session) {
        LOG.debug("reqParam : {}", reqParam);

        String objTypeId = (String) reqParam.get("objTypeId");
        String objId = (String) reqParam.get("objId");
        int atrIdSeq = (Integer) reqParam.get("atrIdSeq");
        int atrValSeq = (Integer) reqParam.get("atrValSeq");
        String path = (String) reqParam.get("path") + File.separatorChar + reqParam.get("filePhyName");

        metaService.removeObjDAtr(objTypeId, objId, atrIdSeq, atrValSeq, atrValSeq+4, SessionInfoSupport.getSessionInfo(session));

        File f = new File(path);

        if (f.isFile()) {
            FileUtils.deleteQuietly(f);
        }

//        if(f.isDirectory()){
//            try {
//                FileUtils.deleteDirectory(f);
//            } catch (IOException e) {}
//        } else if (f.isFile()){
//            FileUtils.deleteQuietly(f);
//        }

        return new ResponseEntity<>(reqParam, HttpStatus.OK);
    }

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void download(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestParam(value="file") final String file,
                         @RequestParam(value="fileLocName") String fileLocName) throws Exception {

        File f = new File(file);

        if(request.getHeader("User-Agent").contains("MSIE")) {
            fileLocName = URLEncoder.encode(fileLocName, "UTF-8");
            fileLocName = fileLocName.replaceAll("\\+", " ");
        } else if(request.getHeader("User-Agent").contains("Mozilla")) {
            fileLocName = new String(fileLocName.getBytes("UTF-8"), "ISO-8859-1");
        }
        
        response.setContentType("application/octet-stream; utf-8");
        /*response.setContentLength((int)f.length());*/

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileLocName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        
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
            e.printStackTrace();
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
    }
    
    @RequestMapping(value = "/pdfView", method = RequestMethod.POST)
    public void pdfView(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestParam(value="file") final String file,
                         @RequestParam(value="fileLocName") String fileLocName) throws Exception {

        File f = new File(file);

        if(request.getHeader("User-Agent").contains("MSIE")) {
            fileLocName = URLEncoder.encode(fileLocName, "UTF-8");
            fileLocName = fileLocName.replaceAll("\\+", " ");
        } else if(request.getHeader("User-Agent").contains("Mozilla")) {
            fileLocName = new String(fileLocName.getBytes("UTF-8"), "ISO-8859-1");
        }
        
        response.setContentType("application/pdf; utf-8");
        /*response.setContentLength((int)f.length());*/

        //response.setHeader("Content-Disposition", "attachment; filename=\"" + fileLocName + "\";");
        //response.setHeader("Content-Transfer-Encoding", "binary");
        //response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        
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
            e.printStackTrace();
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
    }
    
    
    
}
