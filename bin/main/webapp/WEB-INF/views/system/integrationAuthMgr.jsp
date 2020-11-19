<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><spring:message code="app.title" text="DateEye" /> </title>
<style type="text/css">
[de-resize-pos=rightTab] .tab-pane {
	height : calc(100Vh - 265px);
}
</style>
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
		                  <label>APP : </label>
		                  <select id="systemApp" class="form-control"></select>
		                </div>
						<!-- <div class="form-group">
							<input id="searchValue" class="form-control" placeholder="Search" de-search-key="OBJ_NM" >
						</div> -->
						<button type="submit" id="btnSearch" class="btn btn-default">검색</button>
					</div>
				</div> 
			</div>
		</nav>
		
		<div class="content-body" >
			<div class="row">
				<div class="col-xs-9" style="float: right;">
					<div class="box">
			            <div class="box-header">
			              <h3 class="box-title">&nbsp;</h3>
			              <!-- <div class="navbar-form navbar-right" id="btn-wrapper">
			              	<button type="button" id="btnInsert" class="btn btn-default">저장</button>
							<button type="button" id="btnRemove" class="btn btn-default">삭제</button>
						  </div> -->
			            </div>
			            <div class="box-body" de-resize-pos="rightTab">
			            	<div class="col-xs-4">
				             	<div class="box">
				             	<div class="box-header">
				             		<h4 class="box-title"><b>Portal</b> <!-- 그룹 --></h4>
				              		<button type="button" id="btnInsert1" class="btn btn-default" style="float:right;">저장</button>
				            	</div>
				            	<div class="box-body" de-resize-pos="left">
			        				<table id="jqGrid2" de-role="grid"></table>
			              			<div id="jqGridPager2" de-role="grid-pager"></div>
			             		</div>
				             	</div>
			             	</div>
			             	<div class="col-xs-4">
				             	<div class="box">
				             	<div class="box-header">
				             		<h4 class="box-title"><b>MicroStrategy</b></h4>
				              		<button type="button" id="btnInsert2" class="btn btn-default" style="float:right;">저장</button>
				            	</div>
				            	<div class="box-body" de-resize-pos="left">
			        				<table id="jqGrid3" de-role="grid"></table>
			              			<div id="jqGridPager3" de-role="grid-pager"></div>
			             		</div>
				             	</div>
			             	</div>
			             	<div class="col-xs-4">
				             	<div class="box">
				             	<div class="box-header">
				              		<h4 class="box-title"><b>Tableau</b></h4>
				              		<button type="button" id="btnInsert3" class="btn btn-default" style="float:right;">저장</button>
				            	</div>
				            	<div class="box-body" de-resize-pos="left">
			        				<table id="jqGrid4" de-role="grid"></table>
			              			<div id="jqGridPager4" de-role="grid-pager"></div>
			             		</div>
				             	</div>
			             	</div>
			              	<!-- <ul class="nav nav-tabs" id="tapMain" >
				            	<li class="active"><a href="#tab-001" data-toggle="tab" aria-expanded="true">Portal 권한</a></li>
				            	<li><a href="#tab-002" data-toggle="tab">MicroStrategy 권한</a></li>
				            	<li><a href="#tab-003" data-toggle="tab">Tableau 권한</a></li>
				            </ul> -->
			            </div>
					</div>					
				</div>
				<div class="col-xs-3" style="float: left;">
					<div class="box">
			            <div class="box-header">
			              <h3 class="box-title"><i class="fa fa-chevron-right" aria-hidden="true"></i>사용자/그룹</h3>
			            </div>
			            <div class="box-body" de-resize-pos="left">
							<table id="jqGrid" de-role="grid"></table>
		        		  	<div id="jqGridPager" de-role="grid-pager"></div>
			            </div>
					</div>
				</div>
			</div>
		</div>
	</section>	
	<!-- /.content -->
	<script src="<spring:url value="/app/system/pages/integrationAuthMgr.js"/>"></script>
</body>
</html>