<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>DataEye v3 | MAIN</title>
  	<!-- Tell the browser to be responsive to screen width -->
  	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  	<!-- Bootstrap 3.3.7 -->
	<link rel="stylesheet" href="/dataeye_dev/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
	<!-- Font Awesome -->
	<link rel="stylesheet" href="/dataeye_dev/assets/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	
	<!-- jquery-ui 1.10.3 -->
	<link rel="stylesheet" href="/dataeye_dev/assets/stylesheets/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.css">
	
	<!-- jqgrid 4.8.2(bootstrap) -->
	<link rel="stylesheet" href="/dataeye_dev/webjars/jqgrid/4.8.2/css/ui.jqgrid.css">
	<link rel="stylesheet" href="/dataeye_dev/webjars/jqgrid/4.8.2/css/ui.jqgrid-bootstarp.css">
	<link rel="stylesheet" href="/dataeye_dev/webjars/jqgrid/4.8.2/css/addons/ui.multiselect.css">
	
	<!-- jQuery -->
	<script src="/dataeye_dev/webjars/jquery/3.1.1/jquery.min.js"></script>
	<!-- Bootstrap -->
	<script src="/dataeye_dev/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- jquery-ui -->
	<script src="/dataeye_dev/webjars/jquery-ui/1.12.1/jquery-ui.min.js"></script>
	<!-- jqgrid -->
	<script src="/dataeye_dev/webjars/jqgrid/4.8.2/js/i18n/grid.locale-en.js"></script>
	<script src="/dataeye_dev/webjars/jqgrid/4.8.2/js/jquery.jqGrid.js"></script>
	<!-- jquery-i18n-properties -->
	<script src="/dataeye_dev/webjars/jquery-i18n-properties/1.2.2/jquery.i18n.properties.min.js"></script>
	<!-- FastClick -->
	<script src="/dataeye_dev/assets/javascripts-lib/fastclick/fastclick.min.js"></script>
	<!-- Sparkline -->
	<script src="/dataeye_dev/assets/javascripts-lib/sparkline/jquery.sparkline.min.js"></script>
	<!-- SlimScroll 1.3.0 -->
	<script src="/dataeye_dev/assets/javascripts-lib/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- DataEye lib -->
	<script src="/dataeye_dev/assets/javascripts/dataeye.core.js"></script>
	<script src="/dataeye_dev/assets/javascripts/jqgrid.default.js"></script>
</head>
<body>
jqgrid test
<table id="jqGrid"></table>
<div id="jqGridPager"></div>
<button type="button" id="btnSearch" class="btn btn-theme"><spring:message code="danial.app.button.search.text" text="검색" /></button>
<script type="text/javascript">
var v = 1;
    	$(document).ready(function(){
            $("#jqGrid").jqGrid({
                url: 'jqgrid/list',
                mtype:"POST",
        		colModel: [
                    { index:'CD_ID', name: 'CD_ID', label: '코드ID', width: 150, align:'left', editable:true, edittype:"text"},
		          	{ index:'CD_NM', name: 'CD_NM', label: '코드명', width: 150, align:'left', editable:true, edittype:"text"},
		    	    { index:'CD_DESC', name: 'CD_DESC', label: '설명', width: 300, align:'left', editable:true, edittype:"textarea"},
		    	    { index:'USE_YN', name: 'USE_YN', label: '사용여부', width: 70, align:'center', fixed:true, editable:true, edittype:"select", editoptions:{value:"Y:Y;N:N"}},
		    	    { index:'EFCT_ST_DATE', name: 'EFCT_ST_DATE', label: '적용시작일', width: 100, align:'center', fixed:true, editable:true, edittype:"text",
		                editoptions: {
		                    dataInit: function (element) {
		                    	$(element).datepicker({autoclose:true, dateFormat: 'yy-mm-dd' });
		                    }
		                }
		    	    },
		    	    { index:'EFCT_ED_DATE', name: 'EFCT_ED_DATE', label: '적용종료일', width: 100, align:'center', fixed:true, editable:true, edittype:"text",
		                editoptions: {
		                    dataInit: function (element) {
		                    	$(element).datepicker({autoclose:true, dateFormat: 'yy-mm-dd' });
		                    }
		                }
		    	    },
		    	    { index:'xxxxx', name: 'xxxxx', label: '적용종료일', width: 100, align:'center', fixed:true, formatter:function(cellvalue, options, rowobject) {
		    	    	debugger;
		    	    	if (rowobject["CD_ID"] === "ODS") {
		    	    		rowobject["xxxxx"] = "1";
		    	    		cellvalue = "1";
		    	    		return "1";
		    	    	} else if (rowobject["CD_ID"] === "DW") {
		    	    		rowobject["xxxxx"] = "2";
		    	    		cellvalue = "2";
		    	    		return "2";
		    	    	} else if (rowobject["CD_ID"] === "DM") {
		    	    		rowobject["xxxxx"] = "3";
		    	    		cellvalue = "3";
		    	    		return "3";
		    	    	}
		    	    }, sorttype:"float"},
		            { index:'SORT_NO', name: 'SORT_NO', label: 'SORT_NO', hidden:true}
                ],
                pager:"#jqGridPagerCd",
        		loadonce: true,
        		isPaging: false,
        		autowidth:true,
        		shrinkToFit: true,
        		cmTemplate: {sortable: true},
        		sortname:"SORT_NO",
        		sortorder: "asc",
        	   	ajaxGridOptions: {
        	   		async:true, 
        	   		contentType: "application/json; charset=utf-8"
        	   	},
        	   	serializeGridData: function (postData) {
        			return JSON.stringify(postData);
        		}
            }).navGrid('#jqGridPager',{
            	edit:true,add:true,del:true,search:true
            }).navButtonAdd('#jqGridPager',{
            	caption:"추가", 
            	buttonicon:"ui-icon-add", 
            	onClickButton: function(){
            		scheduleModalReset("A");
            		$('#scheduleModal').modal('show');
            	}, 
            	position:"last"
            });
            
            $("button#btnSearch").on("click", function(e) {
            	$("#jqGrid").jqGrid("setGridParam", {"datatype":"json"});
            	var postData = {
           			"queryId":"code.getCd",
           			"cdGrpId":"DB_DESIGN_TYPE"
           		};
           		DE.jqgrid.reload($("#jqGrid"), postData);
        	});
            
        });
	</script>
</body>
</html>
