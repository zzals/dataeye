package kr.co.penta.dataeye.common.util;

public enum PROCESS_RUN_STATUS {
    /*
     * SUCCESS : 실행성공
     * FAIL : 실행실패
     */
    SUCCESS("S"), FAIL("F");
    public final String value;
    private PROCESS_RUN_STATUS(final String value) {
        this.value = value;
    }
}