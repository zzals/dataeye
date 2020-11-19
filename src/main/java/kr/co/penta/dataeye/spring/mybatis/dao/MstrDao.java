package kr.co.penta.dataeye.spring.mybatis.dao;

import java.util.List;
import java.util.Map;

public interface MstrDao {

	List<Map<String, Object>> reportList(Map<String, Object> paraMap);

}
