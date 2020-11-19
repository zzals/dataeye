<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title><spring:message code="app.title" text="DateEye" /></title>
</head>
<body>
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1><i class="glyphicon glyphicon-th-list"></i> 최근 조회 보고서</h1>
    <ol class="breadcrumb"><li><a href="#"><i class="glyphicon glyphicon-list-alt"></i>&nbsp;최근 조회 보고서</a></li></ol>
</section>

<!-- Main content -->
<section class="content">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="collapsed navbar-toggle" data-toggle="collapse" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span> 
                </button>
                <a href="#" class="navbar-brand"></a>
            </div>
            <div class="collapse navbar-collapse">
                <div class="navbar-form navbar-left">
                    <div class="form-group">
                        <select id="searchKey" class="form-control">
                            <option value="REPORT_ID">리포트ID</option>
                            <option value="REPORT_NM">리포트명</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <input id="searchValue" class="form-control" placeholder="Search">
                    </div>
                    <button type="submit" id="btnSearch" class="btn btn-default">검색</button>
                </div>
            </div>
        </div>
    </nav>
    <div class="row">
        <div class="col-xs-12">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">최근 조회 보고서 목록</h3>
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <table id="jqGrid" de-role="grid"></table>
                    <div id="jqGridPager" de-role="grid-pager"></div>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </div>
    </div>
</section>
<!-- /.content -->
<script>
$(document).ready(function(){
	$("#jqGrid").jqGrid({
        url: '../mstr/latest/reportList',
		mtype: "POST",
		styleUI : 'Bootstrap',
        datatype: "json",
        colModel: [
        	{ index:'STD_DT', name: 'STD_DT', label: '실행일자', width: 75, align:'center' },
            { index:'GUBUN', name: 'GUBUN', label: '리포트구분', width: 120 },
            { index:'REPORT_ID', name: 'REPORT_ID', label: '리포트ID', width: 300},
            { index:'REPORT_NM', name: 'REPORT_NM', label: '리포트명', width: 120,
    	    	formatter: "dynamicLink", 
    	    	formatoptions: {
    	    		onClick: function (rowid, irow, icol, cellvalue, rowobject, e) {
    	    			var rowData = $(this).jqGrid("getRowData", rowid);
    	    			var popupUrl = 'http://192.168.100.73:8080/MicroStrategy/servlet/mstrWeb?evt=3140&src=mstrWeb.3140&server=WIN-4FNG27AEFGQ&project=eBay&documentID='+rowData["REPORT_ID"];
    	    			window.open('','popup_window','');
    	    			$("#popupForm").attr('action',popupUrl).submit();
    	    		}
    	    	}
	    	},
            { index:'USER_ID', name: 'USER_ID', label: '사용자ID', width: 180 },
            { index:'USER_NM', name: 'USER_NM', label: '사용자명', width: 80, align:'center'}
        ],
        rownumbers: true,
		autowidth: true,
		shrinkToFit: true,
		cmTemplate: { sortable: true },
        pager: "#jqGridPager",
        viewrecords: true,
        pgbuttons : $.jgrid.defaults.isPaging,
        pginput : $.jgrid.defaults.isPaging,
        sortname: 'STD_DT',
        sortorder: "DESC",
        loadonce: true,
        scroll: true,
        onCellSelect: function(rowid, index, contents, event){
        	var rowData = $("#jqGrid").jqGrid("getRowData", rowid);
        	$('#log').text(rowData["LOG_FIELD"]);
        },
        gridComplete: function () {
        	/*var firstRowId = $("tr.jqgrow:first","#jqGrid").attr("id");
        	if (firstRowId != undefined) {
        		$("#jqGrid").jqGrid('setSelection', firstRowId);
        	}*/
		}
    });

	$("button#btnSearch").on("click", function(e) {
		$("#jqGrid").setGridParam({
					datatype:'json',
					postData:{
						searchKey:$("#searchKey").val(),
						searchValue:$("#searchValue").val()
					}
				}
			).trigger('reloadGrid');
	});
});
</script>
<form id="popupForm" name="popupForm" method="post" action="" target="popup_window">
  <input type="hidden" name="uid" value="Administrator" />
  <input type="hidden" name="pwd" value="" />
</form>
</body>
</html>