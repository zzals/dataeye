package kr.co.penta.dataeye.common.util.file;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016-11-24.
 */
public class DownloadServlet extends HttpServlet {
    private static final long serialVersionUID = -1528203097841588588L;
    private static final Log logger = LogFactory.getLog(DownloadServlet.class);
    private static final String[] trueValues = {"on", "yes", "true"};
    private boolean useDirect = false;
    private String encoding = "UTF-8";

    @Override
    public void init() throws ServletException {
        ServletConfig config = getServletConfig();
        String strDirect = config.getInitParameter("useDirect");
        String encoding = config.getInitParameter("encoding");
        if(StringUtils.isNotEmpty(strDirect))
            this.useDirect = ArrayUtils.contains(trueValues, strDirect);
        if(StringUtils.isNotEmpty(encoding))
            this.encoding = encoding;

        logger.info("******* Download Servlet *********");
        logger.info("useDirect: " + this.useDirect);
        logger.info("encoding: " + this.encoding);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        execute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        execute(req, resp);
    }

    private void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
        String policyId = null, filePath = null, fileName = null, downloadName = null;
        OutputStream out = null;
        InputStream in = null;

        try {
            req.setCharacterEncoding(this.encoding);
            FileService fileService = FileService.getInstance();
            out = resp.getOutputStream();

            String fileId = req.getParameter("fileId");
            downloadName = req.getParameter("downloadName");
            if(StringUtils.isNotEmpty(fileId)
                    && StringUtils.isNotEmpty(downloadName)){
                setResponseHeader(resp, downloadName);
                in = fileService.getInputStream(fileId);
                IOUtils.copy(in, out);
                return;
            }


            if(this.useDirect){
                policyId = req.getParameter("policyId");
                filePath = req.getParameter("filePath");
                fileName = req.getParameter("fileName");
                if(StringUtils.isEmpty(policyId)){
                    throw new IllegalArgumentException("policyId가 존재하지 않습니다.");
                }
            }else{
                policyId = (String) req.getAttribute("policyId");
                filePath = (String) req.getAttribute("filePath");
                fileName = (String) req.getAttribute("fileName");
                downloadName = (String) req.getAttribute("downloadName");
            }
            validate(filePath, fileName);

            File dir = null;
            if(StringUtils.isEmpty(policyId))
                dir = new File(filePath);
            else{
                FileSavePolicy policy =  fileService.getPolicy(policyId);
                dir = new File(policy.getRoot(), filePath);
            }

            if(StringUtils.isEmpty(downloadName))
                downloadName = fileName;
            File file = new File(dir, fileName);
            if(!file.exists())
                throw new IllegalStateException("파일이 존재하지 않습니다. [" + file.getAbsolutePath() + "]");

            setResponseHeader(resp, downloadName);
            in = new FileInputStream(file);
            IOUtils.copy(in, out);
        } catch (Exception e) {
            logger.error("파일다운로드 에러!", e);
        } finally{
            if(in != null)
                try{
                    in.close();
                }catch(IOException ex){
                    logger.error("socket close error!", ex);
                }
            if(out != null)
                try{
                    out.close();
                }catch(IOException ex){
                    logger.error("socket close error!", ex);
                }
        }
    }

    private void validate(String filePath, String fileName) {
        if(StringUtils.isEmpty(filePath))
            throw new IllegalArgumentException("filePath가 존재하지 않습니다.");
        if(StringUtils.isEmpty(fileName))
            throw new IllegalArgumentException("fileName이 존재하지 않습니다.");
        if(StringUtils.containsAny(filePath, '\\', '&', '.'))
            throw new IllegalArgumentException("filePath에 허용되지 않는 문자열이 존재합니다.");
        if(StringUtils.contains(filePath, ".."))
            throw new IllegalArgumentException("filePath에 사용할 수 없는 문자열이 존재합니다.");
        if(StringUtils.contains(fileName, ".."))
            throw new IllegalArgumentException("fileName에 사용할 수 없는 문자열이 존재합니다.");
    }

    private void setResponseHeader(HttpServletResponse resp, String downloadName)
            throws UnsupportedEncodingException {
        resp.setHeader("Pragma", "public");
        resp.setHeader("Expires", "0");
        resp.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        resp.setHeader("Cache-Control", "private");
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downloadName, this.encoding));
    }

}
