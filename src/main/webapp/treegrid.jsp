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

</head>
<body>
jqgrid test
<table id="jqGrid"></table>
<div id="jqGridPager"></div>
<button type="button" id="btnSearch" class="btn btn-theme"><spring:message code="danial.app.button.search.text" text="검색" /></button>
<script type="text/javascript">
    	$(document).ready(function(){
            $("#jqGrid").jqGrid({
                url: 'jqgrid/list',
				datatype:"local",
                mtype: "POST",
				colModel: [
                    { index:'ID', name: 'ID', label: 'Job Id', key: true, width: 75 },
                    { index:'CMD', name: 'CMD', label: 'Command', width: 150 },
                    { index:'CRON_EXPRESSION', name: 'CRON_EXPRESSION', label: 'Cron Exp', width: 150 },
                    { index:'TRIGGER_STATE', name: 'TRIGGER_STATE', label: 'State', width: 150 },
                    { index:'ACTION', name: 'ACTION', label: 'ACTION', width: 80, fixed: true, align:'center', sortable:false, title:false},
                    { index:'TRIGGER_STATE', name: 'TRIGGER_STATE', label: 'TRIGGER_STATE', hidden:true}
                ],
				viewrecords: true,
                autowidth: true,
        		shrinkToFit: true,
                pager: "#jqGridPager",
        	   	ajaxGridOptions: {
        	   		async:true, 
        	   		contentType: "application/json; charset=utf-8"
        	   	},
            });
            
            $("button#btnSearch").on("click", function(e) {
            	$("#jqGrid").jqGrid(
       				'setGridParam', {
       					datatype: 'json',
       					postData:JSON.stringify({
       						metaRelCd:"FT",
       						objId:"010201L_8e00e946-8aff-41ab-9c68-460f287eaf3b",
       						objTypeId:"010201L",
       						queryId:"metacore.getRelObjImpactView"
       					})
       				}
       			).trigger('reloadGrid', [{current:true, page:1}]);
        	});
            
        });
	</script>
</body>
</html>
