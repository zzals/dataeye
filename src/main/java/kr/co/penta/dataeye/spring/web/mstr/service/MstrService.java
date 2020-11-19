package kr.co.penta.dataeye.spring.web.mstr.service;

import java.util.List;
import java.util.Map;

public interface MstrService {

	List<Map<String, Object>> reportList(Map<String, Object> paraMap);

}
