package kr.co.penta.dataeye.spring.web.aprv.enums;

import java.util.ArrayList;
import java.util.List;

public enum AprvCodeType {
    ACT00_0("00", "임시저장"),
    ACT01_0("10", "요청"),
    ACT01_1("11", "재요청"),
    ACT04_0("40", "승인"),
    ACT04_1("41", "재검토"),
    ACT04_2("42", "반려"),
    ACT05_0("50", "검토");

    private String id;
    private String name;

    AprvCodeType(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public static AprvCodeType getEnum(final String id) {
        if ("00".equals(id)) {
            return ACT00_0;
        }
        else if ("10".equals(id)) {
            return ACT01_0;
        }
        else if ("11".equals(id)) {
            return ACT01_1;
        }
        else if ("40".equals(id)) {
            return ACT04_0;
        }
        else if ("41".equals(id)) {
            return ACT04_1;
        }
        else if ("42".equals(id)) {
            return ACT04_2;
        }
        else if ("50".equals(id)) {
            return ACT05_0;
        }
        else {
            throw new IllegalArgumentException("Unknown value: " + id);
        }
    }

    public static List<AprvCodeType> getCodes() {
        List<AprvCodeType> codes = new ArrayList<>();
        codes.add(ACT00_0);
        codes.add(ACT01_0);
        codes.add(ACT01_1);
        codes.add(ACT04_0);
        codes.add(ACT04_1);
        codes.add(ACT04_2);
        codes.add(ACT05_0);

        return codes;
    }
}
