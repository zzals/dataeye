package kr.co.penta.dataeye.common.meta.util;

public class TermModel {
	public String DOMAIN = "";
	public String DOMAIN_ID = "";
	public String WORD_ID1 = "";
	public String WORD_ID2 = "";
	public String WORD_ID3 = "";
	public String WORD_ID4 = "";
	public String WORD_ID5 = "";
	public String WORD_ID6 = "";
	public String WORD_ID7 = "";
	public String WORD_ID8 = "";
	public String WORD_ID9 = "";
	public String CLASS_WORD_ID = "";
	public String KOR_WORD1 = "";
	public String KOR_WORD2 = "";
	public String KOR_WORD3 = "";
	public String KOR_WORD4 = "";
	public String KOR_WORD5 = "";
	public String KOR_WORD6 = "";
	public String KOR_WORD7 = "";
	public String KOR_WORD8 = "";
	public String KOR_WORD9 = "";
	public String CLASS_KOR_WORD = "";
	public String ENG_WORD1 = "";
	public String ENG_WORD2 = "";
	public String ENG_WORD3 = "";
	public String ENG_WORD4 = "";
	public String ENG_WORD5 = "";
	public String ENG_WORD6 = "";
	public String ENG_WORD7 = "";
	public String ENG_WORD8 = "";
	public String ENG_WORD9 = "";
	public String CLASS_ENG_WORD = "";
	public String KOR_TERM = "";
	public String ENG_TERM = "";
	
	public Integer STEP = 0;
	public boolean VALID = false;
	public boolean CLASS = false;
	
	public TermModel(final String wordIds, final String korTerm, final String engTerm, final String DELIMITER, final boolean isValid, final boolean isClass) {
		final String[] wt = wordIds.split(DELIMITER);
		final String[] kt = korTerm.split(DELIMITER);
		final String[] et = engTerm.split(DELIMITER);
		for(int i=0; i<(isClass ? kt.length-1 : kt.length); i++) {
			switch (i) {
			case 0:
				WORD_ID1 = wt[i];
				KOR_WORD1 = kt[i];
				ENG_WORD1 = et[i];
				break;
			case 1:
				WORD_ID2 = wt[i];
				KOR_WORD2 = kt[i];
				ENG_WORD2 = et[i];
				break;
			case 2:
				WORD_ID3 = wt[i];
				KOR_WORD3 = kt[i];
				ENG_WORD3 = et[i];				
				break;
			case 3:
				WORD_ID4 = wt[i];
				KOR_WORD4 = kt[i];
				ENG_WORD4 = et[i];
				break;
			case 4:
				WORD_ID5 = wt[i];
				KOR_WORD5 = kt[i];
				ENG_WORD5 = et[i];
				break;
			case 5:
				WORD_ID6 = wt[i];
				KOR_WORD6 = kt[i];
				ENG_WORD6 = et[i];
				break;
			case 6:
				WORD_ID7 = wt[i];
				KOR_WORD7 = kt[i];
				ENG_WORD7 = et[i];
				break;
			case 7:
				WORD_ID8 = wt[i];
				KOR_WORD8 = kt[i];
				ENG_WORD8 = et[i];
				break;
			case 8:
				WORD_ID9 = wt[i];
				KOR_WORD9 = kt[i];
				ENG_WORD9 = et[i];
				break;
			}
		}
		
		if (isClass) {
			CLASS_KOR_WORD = kt[kt.length-1];
			CLASS_ENG_WORD = et[et.length-1];
			CLASS_WORD_ID = wt[wt.length-1];
		}
		VALID = isValid;
		CLASS = isClass;
		STEP = kt.length;
		KOR_TERM = korTerm.replaceAll(DELIMITER, "_");
		ENG_TERM = engTerm.replaceAll(DELIMITER, "_");
	}
	
	public String toString() {
		final StringBuffer buf = new StringBuffer();
		
		buf.append("WORD_ID={")
		.append(WORD_ID1)
		.append("".equals(WORD_ID2) ? WORD_ID2 : " "+WORD_ID2)
		.append("".equals(WORD_ID3) ? WORD_ID3 : " "+WORD_ID3)
		.append("".equals(WORD_ID4) ? WORD_ID4 : " "+WORD_ID4)
		.append("".equals(WORD_ID5) ? WORD_ID5 : " "+WORD_ID5)
		.append("".equals(WORD_ID6) ? WORD_ID6 : " "+WORD_ID6)
		.append("".equals(WORD_ID7) ? WORD_ID7 : " "+WORD_ID7)
		.append("".equals(WORD_ID8) ? WORD_ID8 : " "+WORD_ID8)
		.append("".equals(WORD_ID9) ? WORD_ID9 : " "+WORD_ID9)
		.append("},");
		
		buf.append("TERM={")
		.append(KOR_WORD1)
		.append("".equals(KOR_WORD2) ? KOR_WORD2 : " "+KOR_WORD2)
		.append("".equals(KOR_WORD3) ? KOR_WORD3 : " "+KOR_WORD3)
		.append("".equals(KOR_WORD4) ? KOR_WORD4 : " "+KOR_WORD4)
		.append("".equals(KOR_WORD5) ? KOR_WORD5 : " "+KOR_WORD5)
		.append("".equals(KOR_WORD6) ? KOR_WORD6 : " "+KOR_WORD6)
		.append("".equals(KOR_WORD7) ? KOR_WORD7 : " "+KOR_WORD7)
		.append("".equals(KOR_WORD8) ? KOR_WORD8 : " "+KOR_WORD8)
		.append("".equals(KOR_WORD9) ? KOR_WORD9 : " "+KOR_WORD9)
		.append("},");
		
		buf.append(", CLASS_KOR_WORD=").append(CLASS_KOR_WORD);
		buf.append(", KOR_TERM=").append(KOR_TERM);
		buf.append(", ENG_TERM=").append(ENG_TERM);
		buf.append(", STEP=").append(STEP);
		buf.append(", VALID=").append(VALID);
		buf.append(", CLASS=").append(CLASS);
		
		return buf.toString();
	}
}
