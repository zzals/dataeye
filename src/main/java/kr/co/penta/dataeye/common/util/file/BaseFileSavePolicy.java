package kr.co.penta.dataeye.common.util.file;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Calendar;

/**
 * Created by Administrator on 2016-11-24.
 */
public class BaseFileSavePolicy implements FileSavePolicy {
    private static File userHome = null;
    static{
        userHome = new File(System.getProperty("user.home"));
    }

    @Override
    public File getRoot() {
        return new File(userHome, "upload");
    }

    @Override
    public String getPath() {
        Calendar today = Calendar.getInstance();
        String year = String.valueOf(today.get(Calendar.YEAR));
        int mm = today.get(Calendar.MONTH) + 1;
        int dd = today.get(Calendar.DAY_OF_MONTH);
        String month = mm > 9? String.valueOf(mm): "0" + mm;
        String day = dd > 9? String.valueOf(dd): "0" + dd;
        return year + "/" + month + "/" + day;
    }


    @Override
    public File getDirectory() {
        return new File(getRoot(), getPath());
    }

    public String rename(String fileName){
        String baseName = FilenameUtils.getBaseName(fileName);
        String ext = FilenameUtils.getExtension(fileName);
        String[] names = baseName.split("_");
        if(names.length > 1){
            try{
                int num = Integer.parseInt(names[names.length-1]) + 1;
                String [] prefix = new String[names.length-1];
                System.arraycopy(names, 0, prefix, 0, prefix.length);
                return StringUtils.join(prefix, '_') + "_" + num + "." + ext;
            }catch(Exception ex){
                return baseName + "_1." + ext;
            }
        }
        return baseName + "_1." + ext;
    }

    @Override
    public String getFileName(String fileId, String orgName) {
        return orgName;
    }

    @Override
    public boolean isOverwrite() {
        return false;
    }

}
