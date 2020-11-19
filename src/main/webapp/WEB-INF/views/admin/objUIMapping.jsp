<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><spring:message code="app.title" text="DateEye" /></title>
  <link type="text/css" rel="stylesheet" href="<spring:url value="/assets/javascripts-lib/bootstrap.treeview/bootstrap-treeview.min.css"/>">
</head>
<body>
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1><small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class=""></i></a></li>
        <li class="active"></li>
      </ol>
    </section>

    <!-- Main content -->
	<section class="content">
		<div class="content-body" de-resize-wrapper="left-right">
			<div class="col-xs-9" style="float: right;">
				<div class="box">
		            <div class="box-header">
		              <h3 class="box-title"><i class="fa fa-chevron-right" aria-hidden="true"></i>객체 목록</h3>
		              <div class="navbar-form navbar-right">
		              	<button type="button" id="btnUP" class="btn btn-default">UP</button>
						<button type="button" id="btnDOWN" class="btn btn-default">DOWN</button>
						<button type="button" id="btnAdd" class="btn btn-default">추가</button>
						<button type="button" id="btnRemove" class="btn btn-default">삭제</button>
						<button type="button" id="btnSave" class="btn btn-default">저장</button>
					  </div>
		            </div>
		            <div class="box-body" de-resize-pos="right">
		              <table id="jqGrid" de-role="grid"></table>
					  <div id="jqGridPager" de-role="grid-pager"></div>
		            </div>
				</div>					
			</div>
			<div class="col-xs-3" style="float: right;">
				<div class="box">
		            <div class="box-header">
		              <h3 class="box-title"><i class="fa fa-chevron-right" aria-hidden="true"></i>UI 유형</h3>
		            </div>
		            <div class="box-body" de-resize-pos="left">
		              <div id="objTypeTreeview" style="height: 100%;overflow-y:scroll;"></div>
		            </div>
				</div>
			</div>
		</div>
	</section>
	<!-- /.content -->
	
	<!-- UI 선택 뷰 -->
	<div class="modal modal-fullscreen fade" id="ui-selected-view" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  		<div class="modal-dialog" style="width: 900px;">
    		<div class="modal-content">
      			<div class="modal-header">
        			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">닫기</span></button>
        			<h4 class="modal-title" id="myModalLabel">UI 선택</h4>
      			</div>
	      		<div class="modal-body">
	      			<div class="box">
	           			<div class="box-header">
							<div class="navbar-form navbar-right">
								<button type="button" id="btnUIAdd" class="btn btn-default">추가</button>
							</div>
						</div>
		      			<div class="box-body">
							<table id="jqGrid2" de-role="grid" de-resize=false></table>
							<div id="jqGridPager2" de-role="grid-pager"></div>
						</div>
					</div>
	      		</div>
	      		<div class="modal-footer">
	        		<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
	      		</div>
	    	</div>
   	 	</div>
    </div>
    
    <script src="<spring:url value="/app/admin/pages/objUIMapping.js"/>"></script>
</body>
</html>