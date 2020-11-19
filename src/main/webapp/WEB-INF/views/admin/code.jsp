<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><spring:message code="app.title" text="DateEye" /></title>
<style type="text/css">
.bootstrap-datetimepicker-widget { 
	z-index: 999999; 
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
							<input id="searchValue" class="form-control" placeholder="Search" de-search-key="SORT_PATH">
						</div>
						<button type="submit" id="btnSearch" class="btn btn-default">검색</button>
					</div>
				</div>
			</div>
		</nav>
		<div class="content-body" de-resize-wrapper="top-bottom">
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
			            <div class="box-header">
			              <h3 class="box-title"><i class="fa fa-chevron-right" aria-hidden="true"></i>코드 그룹</h3>
			              <div class="navbar-form navbar-right">
							<button type="button" id="btnCdGrpInsert" class="btn btn-default">등록</button>
							<button type="button" id="btnCdGrpUpdate" class="btn btn-default">수정</button>
							<button type="button" id="btnCdGrpRemove" class="btn btn-default">삭제</button>
						  </div>
			            </div>
			            <!-- /.box-header -->
			            <div class="box-body" de-resize-pos="top">
			              <table id="jqGrid" de-role="grid"></table>
						  <div id="jqGridPager" de-role="grid-pager"></div>
			            </div>
			            <!-- /.box-body -->
			          </div>
			          <!-- /.box -->				
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
			            <div class="box-header">
			              <h3 class="box-title"><i class="fa fa-chevron-right" aria-hidden="true"></i>코드</h3>
			              <div class="navbar-form navbar-right">
			              	<button type="button" id="btnCdUP" class="btn btn-default">UP</button>
							<button type="button" id="btnCdDOWN" class="btn btn-default">DOWN</button>
							<button type="button" id="btnCdAdd" class="btn btn-default">추가</button>
							<button type="button" id="btnCdDelete" class="btn btn-default">삭제</button>
							<button type="button" id="btnCdSave" class="btn btn-default">저장</button>
						  </div>
			            </div>
			            <!-- /.box-header -->
			            <div class="box-body" de-resize-pos="bottom">
			              <table id="jqGridCd" de-role="grid"></table>
						  <div id="jqGridPagerCd" de-role="grid-pager"></div>
			            </div>
			            <!-- /.box-body -->
			          </div>
			          <!-- /.box -->				
				</div>
			</div>
		</div>
	</section>
	<!-- /.content -->	
    <script src="<spring:url value="/app/admin/pages/code.js"/>"></script>
</body>
</html>