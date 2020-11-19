package kr.co.penta.dataeye.common.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.JSONUtils;

public class JqGridFilters {
    enum OP {
        eq,  //equals
        ne,  //not equal
        bw,  //like(start)
        bn,  //not like(start)
        ew,  //like(end)
        en,  //not like(end)
        cn,  //like(in)
        nc,  //not like(in)
        nu,  //is null
        nn  //is not null
    }
    
    private String groupOp;
    private List<Rule> rules = new ArrayList<>();
	
	public JqGridFilters(String filterStr) {
        super();
        Map<String, Object> filterMap = JSONUtils.getInstance().jsonToMap(filterStr);
        this.groupOp = CastUtils.toString(filterMap.get("groupOp"));
        List<Map<String, Object>> ruleList = (List<Map<String, Object>>)filterMap.get("rules");
        for( Map<String, Object> map : ruleList ) {
            Rule rule = new Rule(map);
            rules.add(rule);
        }
    }
	
	class Rule {
	    String field;
	    String op;
	    String data;
	    
	    public Rule(Map<String, Object> ruleMap) {
	        this.field = CastUtils.toString(ruleMap.get("field"));
            this.op = CastUtils.toString(ruleMap.get("op"));
            this.data = CastUtils.toString(ruleMap.get("data"));
	    }
	}
	
	public String getFilterWhere() {
	    StringBuilder sb = new StringBuilder();
	    final String prepand = " "+groupOp+" ";
	    if (rules.size() == 0) return "";
	    
	    for(Rule rule : rules) {
	        if (OP.eq.name().equals(rule.op)) {
	            sb.append(prepand).append("T101.").append(rule.field).append(" = ").append("'").append(rule.data).append("' ");
	        } else if (OP.ne.name().equals(rule.op)) {
                sb.append(prepand).append("T101.").append(rule.field).append(" <> ").append("'").append(rule.data).append("' ");
            } else if (OP.bw.name().equals(rule.op)) {
                sb.append(prepand).append("T101.").append(rule.field).append(" LIKE ").append("'").append(rule.data).append("%' ");
            } else if (OP.bn.name().equals(rule.op)) {
                sb.append(prepand).append("T101.").append(rule.field).append(" NOT LIKE ").append("'").append(rule.data).append("%' ");
            } else if (OP.ew.name().equals(rule.op)) {
                sb.append(prepand).append("T101.").append(rule.field).append(" LIKE ").append("'%").append(rule.data).append("' ");
            } else if (OP.en.name().equals(rule.op)) {
                sb.append(prepand).append("T101.").append(rule.field).append(" NOT LIKE ").append("'%").append(rule.data).append("' ");
            } else if (OP.cn.name().equals(rule.op)) {
                sb.append(prepand).append("T101.").append(rule.field).append(" LIKE ").append("'%").append(rule.data).append("%' ");
            } else if (OP.nc.name().equals(rule.op)) {
                sb.append(prepand).append("T101.").append(rule.field).append(" NOT LIKE ").append("'%").append(rule.data).append("%' ");
            } else if (OP.nu.name().equals(rule.op)) {
                sb.append(prepand).append("T101.").append(rule.field).append(" IS NULL ");
            } else if (OP.nn.name().equals(rule.op)) {
                sb.append(prepand).append("T101.").append(rule.field).append(" IS NOT NULL ");
            }
	    }
	    
	    String where = sb.substring(prepand.length());
	    return where;
	}
}