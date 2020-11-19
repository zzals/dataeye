<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="page-id" content="req.list">
	<title><spring:message code="app.title" text="DateEye" /></title>
	<style type="text/css">
		.content-body {
			height : calc(100Vh - 220px);
		}
	</style>
</head>
<body>
    <!-- Content Header (Page header) -->
    <section class="content-header">
     <c:set var="curMenuSize" value="${fn:length(curMenu)}" />
      <h1>${curMenu[curMenuSize-1].menuNm}</h1>
      <ol class="breadcrumb">
       <!--  <li><a href="#"><i class=""></i></a></li>
        <li class="active"></li> -->
        
        
        <c:forEach items="${curMenu}" var="map" varStatus="i">
        	<c:choose>
	        	<c:when test="${i.first}">
	        		<li><a href="#"><i class="${map.iconClass}"></i>${map.menuNm}</a></li>
	        	</c:when>
	        	<c:otherwise>
					<li class="active">${map.menuNm}</li> 
				</c:otherwise>
        	</c:choose>
		</c:forEach>
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
						<label class="control-label" for="inputSuccess">
							<i class="glyphicon glyphicon-ok"></i>진행상태
						</label>
						<div class="form-group">
							<select id="selLastStusCd" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;"></select>
						</div>
					</div>
					<div class="navbar-form navbar-right">
						<div class="form-group">
							<select id="searchKey" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;">
			                  	<option value="_all">[전체]</option>
			                  	<option value="APRV_REQ_DESC">요청내용</option>
			                </select>
							<input id="searchValue" class="form-control" placeholder="Search">
						</div>
						<button type="submit" id="btnSearch" class="btn btn-default">검색</button>
					</div>
				</div>
			</div>
		</nav>
		<div class="content-body" >
	    	<div class="row">
				<div class="col-xs-12">
					<div class="box">
			            <div class="box-header">
			              <h3 class="box-title"><i class="fa fa-chevron-right" aria-hidden="true"></i>요청 목록</h3>
			              <div class="navbar-form navbar-right"></div>
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
		</div>
	</section>
	<!-- /.content -->
	<input type="hidden" id="reqParam">
	<script type="text/javascript">
		$("input#reqParam").data("viewId", '${viewId}');
		$("input#reqParam").data("menuId", '${menuId}');
	</script>	
	<script src="<spring:url value="/app/portal/pages/biz/std/req.list.js"/>"></script>
</body>
</html>