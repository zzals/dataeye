package kr.co.penta.dataeye.common.entities.meta;

/**
 * Created by Administrator on 2014-09-04.
 */
public class OBJ_TYPE_ID {
    public static enum BIZ_STD {
        WORD("010301L"), TERM("010302L"), DOMAIN("010303L"), DOMAIN_GROUP("010304L");
        private final String value;
        BIZ_STD(final String value) {
            this.value = value;
        }
        public String value() {
            return value;
        }
    };
}
