package kr.co.penta.dataeye.common.meta.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;

/**
 * Time(Date, Timestamp) Util Class
 * <br/><b>History</b><br/>
 * <pre>
 * 2012. 5. 4. 최초작성
 * </pre>
 * @author cjsxowls
 * @version 1.0
 */
public final class DeFileUtils {
    private static final DeFileUtils FILE_UTIL = new DeFileUtils();

	/**
	 * TimeUtil 생성자<br>
	 * @param 
	 * @return 
	 */ 
	private DeFileUtils() {
		super();
	}

	/**
	 * TimeUtil 인스턴스 리턴 <br>
	 * @param 
	 * @return TimeUtil
	 */ 
	public static DeFileUtils getInstance() {
		return FILE_UTIL;
	}

	/**
     * mkdirs<br>
     * @param 
     * @return String
     */ 
    public String mkTargetPathDir(final String objTypeId, String objId) {
        String realPath = DataEyeSettingsHolder.getInstance().getAttachFile().getUploadReadPath();
        String realDir = realPath + File.separatorChar+objTypeId+File.separatorChar+objId;
        
        new File(realDir).mkdirs();
        
        return realDir;
    }
    
    /**
	 * mkdirs<br>
	 * @param 
	 * @return boolean
	 */ 
	public boolean mkdirs(final String dir) {
		return new File(dir).mkdirs();
	}

	/**
	 * move<br>
	 * @param 
	 * @return boolean
	 */ 
	public boolean move(final String source, final String target) {
		final File f = new File(source);
		return f.renameTo(new File(target));
	}

	/**
	 * copy<br>
	 * @param 
	 * @return void
	 */ 
	public void copy(final String source, final String target) {
		try {
			FileInputStream fis = new FileInputStream(source);
			FileOutputStream fos = new FileOutputStream(target);

			int data = 0;
			while((data=fis.read())!=-1) {
				fos.write(data);
			}
			fis.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean deleteDir(String path) {
        return deleteDir(new File(path));
    }
	
	public boolean deleteDir(File path) {
		if (!path.exists()) {
			return false;
		}
		File[] files = path.listFiles();
		for(File file : files) {
			if (file.isDirectory()) {
				deleteDir(file);
			} else {
				file.delete();
			}
		}
		
		return path.delete();
	}
	
	public boolean deleteFile(File file) {
		return file.delete();
	}
}
