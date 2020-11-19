package kr.co.penta.dataeye.common.util.file;

import java.io.File;

/**
 * Created by Administrator on 2016-11-24.
 */
public interface FileSavePolicy {

    /**
     * 파일저장 root 디렉터리
     * @return
     */
    File getRoot();
    /**
     * root 디렉터리로부터 상대경로
     * @return
     */
    String getPath();
    /**
     * 파일저장 디렉터리
     * @return
     */
    File getDirectory();
    /**
     * 덮어쓰기 여부
     * @return
     */
    boolean isOverwrite();
    /**
     * 중복파일명 존재시 파일명변경 규칙
     * @param fileName
     * @return
     */
    String rename(String fileName);
    /**
     * 변경할 파일명
     * @param fileId
     * @param orgName
     * @return
     */
    String getFileName(String fileId, String orgName);
}
