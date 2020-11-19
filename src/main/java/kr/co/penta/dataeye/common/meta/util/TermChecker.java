package kr.co.penta.dataeye.common.meta.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;


public class TermChecker {
	final boolean debug = true;
	private String[][] terms = null;
	private List<TermModel> models = new ArrayList<TermModel>();
	static final String DELIMITER = DataEyeSettingsHolder.getInstance().getMetaConfig().getSplitDelimiterChar();
	
	public TermChecker() {
		super();
	}
	
	public TermChecker(List<Map<String, Object>> models) {
		super();
		setTerms(models);
	}

	public static void main(String[] args) {
		TermChecker checker = new TermChecker();
		checker.setTerms();
		checker.check("00xxx");
		if (checker.isValid()) {
			List<TermModel> validTerms = checker.getValidTerms();
			
			System.out.println("=============== VALID TERMS BEGIN =================");
			for(Iterator<TermModel> iterator = validTerms.iterator(); iterator.hasNext(); ) {
				System.out.println(iterator.next().toString());
			}
			System.out.println("=============== VALID TERMS END =================");
		} else {
			List<TermModel> invalidTerms = checker.getInvalidTerms();
			System.out.println("=============== INVALID TERMS BEGIN =================");
			for(Iterator<TermModel> iterator = invalidTerms.iterator(); iterator.hasNext(); ) {
				System.out.println(iterator.next().toString());
			}
			System.out.println("=============== INVALID TERMS END =================");
		}
	}

	private void setTerms() {
		BufferedReader reader = null;
		final List<String[]> list = new ArrayList<String[]>();
		try {
			reader = new BufferedReader(new FileReader("resource/term-list.txt"));
			String s;

			while ((s = reader.readLine()) != null) {
				list.add(s.split("\t"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {reader.close();} catch(Exception e){}
		}
		
		terms = list.toArray(new String[list.size()][2]);
	}

	private void setTerms(List<Map<String, Object>> models) {
		final List<String[]> list = new ArrayList<String[]>();
		for(Iterator<Map<String, Object>> iterator = models.iterator(); iterator.hasNext(); ) {
			final Map<String, Object> map = iterator.next();
			final String[] ss = new String[4];
			ss[0] = (String)map.get("TERM_ID");
			ss[1] = (String)map.get("KOR_TERM");
			ss[2] = (String)map.get("ENG_TERM");
			ss[3] = (String)map.get("CLASS_YN");
			
			list.add(ss);
		}
		
		terms = list.toArray(new String[list.size()][4]);
	}
	
	public void check(final String term) { 
		check(term, "", "", "");
	}
	
	private void check(final String term, String wordIds, String korTerm, String engTerm) { 
		for(int i=0; i<terms.length; i++) {
			if (term.startsWith(terms[i][1])) {
				String cutTerm = term.substring(terms[i][1].length());
				if ("".equals(cutTerm)) {
					if (debug) {      
						System.out.println(korTerm+DELIMITER+terms[i][1]);
						System.out.println(engTerm+DELIMITER+terms[i][2]);
					}
					if ("Y".equalsIgnoreCase(terms[i][3])) {
					    if ("".equals(wordIds)) {
					        addModel(wordIds+DELIMITER+terms[i][0], "".equals(korTerm) ? terms[i][1] : korTerm+DELIMITER+terms[i][1], "".equals(engTerm) ? terms[i][2] : engTerm+DELIMITER+terms[i][2], false, true);
					    } else {
					        addModel(wordIds+DELIMITER+terms[i][0], "".equals(korTerm) ? terms[i][1] : korTerm+DELIMITER+terms[i][1], "".equals(engTerm) ? terms[i][2] : engTerm+DELIMITER+terms[i][2], true, true);
					    }
					} else {
						addModel(wordIds+DELIMITER+terms[i][0], "".equals(korTerm) ? terms[i][1] : korTerm+DELIMITER+terms[i][1], "".equals(engTerm) ? terms[i][2] : engTerm+DELIMITER+terms[i][2], false, false);
					}
				} else {
				    check(cutTerm, "".equals(wordIds) ? terms[i][0] : wordIds+DELIMITER+terms[i][0], "".equals(korTerm) ? terms[i][1] : korTerm+DELIMITER+terms[i][1], "".equals(engTerm) ? terms[i][2] : engTerm+DELIMITER+terms[i][2]);
				}
			}
		}
		if (debug) {
			System.out.println("Not Matched::"+korTerm);
		}
		addModel(wordIds, korTerm, engTerm, false, false);
	}
	
	public void addModel(String wordIds, String korTerm, String engTerm, boolean isValid, boolean isClass) {
		final TermModel model = new TermModel(wordIds, korTerm, engTerm, DELIMITER, isValid, isClass);
		models.add(model);
	}
	
	public boolean isValid() {
		for(Iterator<TermModel> iterator = models.iterator(); iterator.hasNext(); ) {
			final TermModel termModel = iterator.next();
			if (termModel.VALID) {
				return true;
			}
		}
		return false;
	}
	
	public List<TermModel> getValidTerms() {
		List<TermModel> validTerms = new ArrayList<TermModel>();
		for(Iterator<TermModel> iterator = models.iterator(); iterator.hasNext(); ) {
			final TermModel termModel = iterator.next();
			if (termModel.VALID) {
				validTerms.add(termModel);
			}
		}
		Collections.sort(validTerms, new StepAscCompare());
		return validTerms;
	}
	
	public List<TermModel> getInvalidTerms() {
		int step = 0;
		List<TermModel> validTerms = new ArrayList<TermModel>();
		for(Iterator<TermModel> iterator = models.iterator(); iterator.hasNext(); ) {
			final TermModel termModel = iterator.next();
			if (!termModel.VALID && !"".equals(termModel.KOR_TERM)) {
				if (termModel.STEP == step) {
					validTerms.add(termModel);
				} else if (termModel.STEP > step) {
					step = termModel.STEP;
					validTerms.clear();
					validTerms.add(termModel);
				}
			}
		}
		Collections.sort(validTerms, new StepDescCompare());
		return validTerms;
	}
	
	static class StepAscCompare implements Comparator<TermModel> {
		@Override
		public int compare(TermModel arg0, TermModel arg1) {
			return arg0.STEP.compareTo(arg1.STEP);
		}
 
	}
	
	static class StepDescCompare implements Comparator<TermModel> {
		@Override
		public int compare(TermModel arg0, TermModel arg1) {
			return arg1.STEP.compareTo(arg0.STEP);
		}
	}
}
