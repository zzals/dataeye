package kr.co.penta.dataeye.spring.web.aprv.enums;

public enum AprvResultType {
    SUCC("SUCC", "성공"),
    FAIL("FAIL", "실패"),
    ERR_MSG_001("ERR_MSG_001", "결재라인 정보가 올바르지 않습니다."),
    REQ_ERR_MSG_001("REQ_ERR_MSG_001", "결재 요청 정보가 올바르지 않습니다."),
    REQ_ERR_MSG_002("REQ_ERR_MSG_002", "이미 요청된 건입니다."),
    REQ_SUC_MSG("REQ_SUC_MSG", "결재 요청이 완료되었습니다."),
    REQ_ERR_MSG("REQ_ERR_MSG", "결재 요청이 실패하였습니다. 관리자에게 문의하시기 바랍니다."),
    RTY_ERR_MSG_001("RTY_ERR_MSG_001", "재검토 요청 정보가 올바르지 않습니다."),
    RTY_ERR_MSG("RTY_ERR_MSG", "재검토 요청이 실패하였습니다. 관리자에게 문의하시기 바랍니다."),
    DO_ERR_MSG_001("DO_ERR_MSG_001", "이미 결재된 건입니다."),
    DO_ERR_MSG_002("DO_ERR_MSG_002", "결재 요청 후 진행해 주시기 바랍니다."),
    DO_ERR_MSG_003("DO_ERR_MSG_003", "결재 정보가 올바르지 않습니다."),
    DO_ERR_MSG_004("DO_ERR_MSG_004", "이전 결재가 아직 처리되지 않았습니다."),
    DO_ERR_MSG_005("DO_ERR_MSG_005", "결재 승인 정보가 올바르지 않습니다."),
    DO_SUC_MSG("DO_SUC_MSG", "결재 처리가 완료되었습니다."),
    DO_SUC_MSG_001("DO_SUC_MSG_001", "승인이 완료되었습니다."),
    DO_SUC_MSG_002("DO_SUC_MSG_002", "반려가 완료되었습니다."),
    DO_SUC_MSG_003("DO_SUC_MSG_003", "검토가 완료되었습니다."),
    DO_SUC_MSG_004("DO_SUC_MSG_004", "재검토 요청이 완료되었습니다."),
    DO_ERR_MSG("DO_ERR_MSG", "결재 처리가 실패하였습니다. 관리자에게 문의하시기 바랍니다.");

    private String id;
    private String name;

    AprvResultType(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
