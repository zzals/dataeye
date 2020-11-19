package kr.co.penta.dataeye.common.util.file;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016-11-24.
 */
public class UploadServlet  extends HttpServlet {
    private static final long serialVersionUID = -8058691191664073850L;
    private static final Log logger = LogFactory.getLog(UploadServlet.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final static int MAX_MEMORY_SIZE = 1024 * 1024 * 5;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private String[] allows = {"png", "jpg", "jpeg", "gif", "zip", "hwp", "xls", "xlsx", "doc", "docx", "ppt", "pptx", "pdf", "txt"};
    private long maxFileSize = 1024 * 1024 * 10;
    private String encoding = "UTF-8";
    private File uploadDir = null;
    private DiskFileItemFactory factory = null;

    class FileEraser implements Runnable {
        FileService fileService = FileService.getInstance();

        @Override
        public void run() {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, -1);
            Date today = cal.getTime();
            fileService.remove(today);
            logger.info("자동파일정리!");
        }
    }

    @Override
    public void init() throws ServletException {
        ServletConfig config = getServletConfig();
        String tempLocation = config.getInitParameter("tempLocation");
        String uploadLocation = config.getInitParameter("uploadLocation");
        String maxSize = config.getInitParameter("maxFileSize");
        String allows = config.getInitParameter("allows");
        String encoding = config.getInitParameter("encoding");
        if(allows != null && !StringUtils.isEmpty(allows))
            this.allows = StringUtils.split(allows.toLowerCase(), ",");
        if(uploadLocation == null)
            throw new IllegalStateException("uploadLocation이 등록되지 않았습니다.");
        this.uploadDir = new File(uploadLocation);
        if(!this.uploadDir.exists())
            throw new IllegalStateException("폴더를 찾을 수 없습니다.");
        if(!this.uploadDir.canWrite())
            throw new IllegalStateException("파일을 쓸 수 없습니다.");
        if(encoding != null)
            this.encoding = encoding;

        InputStream configIn = null;
        try {
            configIn = getServletContext().getResourceAsStream("/WEB-INF/ixserver_conf/upload.properties");
            FileService.initialize(this.uploadDir, configIn);
        } catch (Exception ex) {
            logger.error("파일서비스 초기화 에러!", ex);
            throw new ServletException("파일서비스 초기화 에러!", ex);
        }finally{
            if(configIn != null)
                try {
                    configIn.close();
                } catch (IOException e) {
                    logger.error("socket close error!", e);
                }
        }

        File tempDir = null;
        if(tempLocation != null)
            tempDir = new File(tempLocation);
        if(maxSize != null)
            this.maxFileSize = Long.parseLong(maxSize) * 1024L * 1024L;
        factory = new DiskFileItemFactory();
        if(tempDir == null){
            ServletContext servletContext = config.getServletContext();
            tempDir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        }
        factory.setSizeThreshold(MAX_MEMORY_SIZE);
        factory.setRepository(tempDir);
        scheduler.scheduleWithFixedDelay(new FileEraser(), 1, 1, TimeUnit.HOURS);

        logger.info("******* Upload Servlet *********");
        logger.info("tempLocation: " + tempDir.getAbsolutePath());
        logger.info("uploadLocation: " + uploadDir.getAbsolutePath());
        logger.info("allows: " + StringUtils.join(this.allows, ','));
        logger.info("maxFileSize: " + this.maxFileSize);
        logger.info("encoding: " + this.encoding);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(this.encoding);
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        resp.setContentType("text/plain; charset=" + this.encoding);
        JsonGenerator generator = null;
        OutputStream out = null;

        List<Object[]> results = new ArrayList<Object[]>();
        try{
            if(!isMultipart)
                throw new IllegalArgumentException("multipart form이 아닙니다.");
            out = resp.getOutputStream();
            generator = new JsonFactory(MAPPER).createJsonGenerator(out, JsonEncoding.UTF8);
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(this.maxFileSize);

            List<FileItem> items = upload.parseRequest(req);
            for(FileItem item : items){
                if(item.isFormField())
                    continue;
                String ext = StringUtils.lowerCase(FilenameUtils.getExtension(item.getName()));
                if(!ArrayUtils.contains(this.allows, ext))
                    throw new IllegalArgumentException("허용되지 않는 확장자 입니다.[" + item.getName() + "]");
            }
            for(FileItem item : items){
                if(item.isFormField())
                    continue;
                String fileId = UUID.randomUUID().toString();
                Object[] fi = { item.getFieldName(), FilenameUtils.getName(item.getName()), fileId, item.getSize()};
                File toFile = new File(this.uploadDir, fileId);
                FileUtils.copyInputStreamToFile(item.getInputStream(), toFile);
                results.add(fi);
            }

            if(results.size() < 1)
                throw new IllegalArgumentException("업로드파일이 존재하지 않습니다.");
            generator.writeStartObject();
            generator.writeBooleanField("@issuccess", true);
            generator.writeArrayFieldStart("files");
            for(Object[] result : results){
                generator.writeStartObject();
                generator.writeStringField("fieldName", (String) result[0]);
                generator.writeStringField("fileName", (String) result[1]);
                generator.writeStringField("fileId", (String) result[2]);
                generator.writeNumberField("fileSize", (Long)result[3]);
                generator.writeEndObject();
            }
            generator.writeEndArray();
            generator.writeEndObject();
        } catch (Exception ex) {
            generator.writeStartObject();
            generator.writeBooleanField("@issuccess", false);
            generator.writeStringField("message", ex.getMessage());
            generator.writeEndObject();
            logger.error("Upload Error!", ex);
        }finally{
            if(generator != null)
                generator.close();
            if(out != null){
                out.flush();
                out.close();
            }
        }
    }
}
