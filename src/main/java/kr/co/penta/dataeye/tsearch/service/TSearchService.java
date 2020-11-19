package kr.co.penta.dataeye.tsearch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TSearchService {
	/*
	 * 메인 데쉬보드 화면에 적용될 객체유형를 가져온다
	 */
	Map<String, Object> findDashboardObjTypes();
	
	/*
	 * 경로객체별 데이터맵
	 */
	List<Map<String, Object>> findTreeMapByPath(String pathObjTypeId);
	
	/*
	 * 검색 tab 정보를 불러온다.
	 */
	List<Map<String, Object>> getTabCategory();
	
	/*
	 * suggest.
	 */
	Map<String, Object> suggest(String keyword);
	

	/*
	 * msearch.
	 */
	Map<String, Object> msearch(String type, String keyword, Integer page);
	
	/*
	 * elsearch.
	 */
	Map<String, Object> elsearch(String type, String keyword, Integer page);
	/*
	 * elsuggest.
	 */
	List<String> elsuggest(String keyword);
}
