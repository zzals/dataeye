package kr.co.penta.dataeye.common.entities;

import java.util.Collection;

import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.param.JqGridDaoParam;


public class JqGridEntity {
    private String root = "rows";
    private Integer page;    /*현재 페이지*/
    private Integer total;   /*페이지수*/
    private Integer records; /*전체 건수*/
    
    private Collection<?> rows;
	
	public JqGridEntity(JqGridDaoParam jqGridDaoParam, Collection<?> rows, Integer totalCount) {
        super();
        this.page = jqGridDaoParam.getPage();
        this.records = rows.size();
        this.rows = rows;
        this.total = CastUtils.toInteger(Math.ceil(totalCount/(double)jqGridDaoParam.getRows()));
    }
    public void setPage(Integer page) {
        this.page = page;
    }
	public void setTotal(Integer total) {
        this.total = total;
    }
	public void setRecords(Integer records) {
        this.records = records;
    }
	public void setRows(Collection<?> rows) {
		this.rows = rows;
	}
	
	public String getRoot() {
        return root;
    }
	public Integer getPage() {
        return page;
    }
    public Integer getTotal() {
        return total;
    }
    public Integer getRecords() {
        return records;
    }
    public Collection<?> getRows() {
      return rows;
    }
}
