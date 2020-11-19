package kr.co.penta.dataeye.common.util.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2016-11-24.
 */
public class FileService {
    private final static Log logger = LogFactory.getLog(FileService.class);
    private static FileService INSTANCE = null;
    private Map<String, FileSavePolicy> policies = new HashMap<String, FileSavePolicy>();
    private File uploadDir = null;

    private FileService(File uploadDir, InputStream in) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
        this.uploadDir = uploadDir;
        this.init(in);
    }

    public static void initialize(File uploadDir, InputStream in) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException{
        if(INSTANCE != null)
            throw new IllegalStateException("파일서비스가 이미 초기화 되었습니다.");
        INSTANCE = new FileService(uploadDir, in);
    }

    public static FileService getInstance(){
        if(INSTANCE == null)
            throw new IllegalStateException("파일서비스가 초기화 되지 않았습니다.");
        return INSTANCE;
    }

    private void init(InputStream in) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        Properties prop = new Properties();
        prop.load(in);
        Iterator<Object> keys = prop.keySet().iterator();
        while(keys.hasNext()){
            String key = (String) keys.next();
            String clazz = prop.getProperty(key);
            FileSavePolicy policy = (FileSavePolicy) Class.forName(clazz).newInstance();
            policies.put(key, policy);
        }
    }

    public FileSavePolicy getPolicy(String policyId){
        return policies.get(policyId);
    }

    public String save(String fileId, String fileName) throws IOException{
        FileSavePolicy policy = new BaseFileSavePolicy();
        return save(policy, fileId, fileName);
    }

    public String save(String policyId, String fileId, String fileName) throws IOException{
        FileSavePolicy policy = policies.get(policyId);
        return save(policy, fileId, fileName);
    }

    private String getFileName(Collection<File> files, FileSavePolicy policy, String fileName){
        for(File file : files){
            if(StringUtils.equals(file.getName(), fileName)){
                String renamed = policy.rename(fileName);
                return getFileName(files, policy, renamed);
            }
        }
        return fileName;
    }

    public void save(String fileId, File destDir, String fileName) throws IOException{
        if(StringUtils.isEmpty(fileId))
            throw new IllegalArgumentException("파일ID가 입력되지 않았습니다.");
        if(StringUtils.isEmpty(fileName))
            throw new IllegalArgumentException("저장할 파일명이 입력되지 않앗습니다.");
        if(destDir == null)
            throw new IllegalArgumentException("저장할 폴더가 입력되지 않았습니다.");

        File srcFile = new File(this.uploadDir, fileId);
        if(!srcFile.exists())
            throw new IllegalStateException("파일업로드가 존재하지 않습니다.[" + fileId + "]");
        if(!destDir.exists())
            destDir.mkdirs();
        if(!destDir.canWrite())
            throw new IllegalStateException("디렉터리에 파일을 쓸수 없는 상태입니다.[" + destDir.getAbsolutePath() + "]");
        File destFile = new File(destDir, fileName);
        logger.debug("File Save :: " + fileId + " -> " + destFile.getAbsolutePath());
        FileUtils.moveFile(srcFile, destFile);
    }

    public String save(FileSavePolicy policy, String fileId, String fileName) throws IOException{
        File destDir = policy.getDirectory();
        if(!destDir.exists())
            destDir.mkdirs();
        if(policy.isOverwrite()){
            save(fileId, destDir, fileName);
            return fileName;
        }else{
            Collection<File> files = FileUtils.listFiles(destDir, null, false);
            String renamed = getFileName(files, policy, fileName);
            save(fileId, destDir, renamed);
            return renamed;
        }
    }

    public InputStream getInputStream(String fileId) throws FileNotFoundException {
        File srcFile = new File(this.uploadDir, fileId);
        if(!srcFile.exists())
            throw new IllegalStateException("파일업로드가 존재하지 않습니다.[" + fileId + "]");
        InputStream in = new FileInputStream(srcFile);
        return in;
    }

    protected void remove(Date cutoffDate){
        IOFileFilter fileFilter = new AgeFileFilter(cutoffDate);
        Iterator<File> files = FileUtils.iterateFiles(this.uploadDir, fileFilter, null);
        while(files.hasNext()){
            File file = files.next();
            file.delete();
        }
    }
}
