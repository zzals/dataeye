<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><spring:message code="app.title" text="DateEye" /></title>
<style class="cp-pen-styles" id="">
	#container {
		height:		calc(100Vh - 220px);
		margin:		0 auto;
	}
	.pane {
		display:	none; /* will appear when layout inits */
	}
	.ui-layout-pane {
		border: none;
		overflow: hidden;
		padding: inherit;
		width: 100%;
	}
	.ui-layout-resizer {
    	background: #ec068d;
    }
    .ui-layout-toggler {
	    border: 1px solid #444;
	    background-color: #444;
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
						<label class="control-label" for="inputSuccess">
							<i class="glyphicon glyphicon-ok"></i>APP 시스템
						</label>
						<div class="form-group">
							<select id="selAppSys" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;"></select>
						</div>
					</div>
					<div class="navbar-form navbar-left">
						<label class="control-label" for="inputSuccess">
							<i class="glyphicon glyphicon-ok"></i>DB 인스턴스
						</label>
						<div class="form-group">
							<select id="selDbInst" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;"></select>
						</div>
					</div>
					<div class="navbar-form navbar-left">
						<label class="control-label" for="inputSuccess">
							<i class="glyphicon glyphicon-ok"></i>데이터베이스
						</label>
						<div class="form-group">
							<select id="selDatabase" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;"></select>
						</div>
					</div>
					<div class="navbar-form navbar-right">
						<div class="form-group">
							<select id="searchKey" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;">
			                  	<option value="_all">[전체]</option>
			                  	<option value="OBJ_ABBR_NM">테이블 논리명</option>
			                  	<option value="OBJ_NM">테이블 물리명</option>
			                </select>
							<input id="searchValue" class="form-control" placeholder="Search">
						</div>
						<button type="submit" id="btnSearch" class="btn btn-default">검색</button>
					</div>
				</div>
			</div>
		</nav>
		<div class="content-body">
	    	<div class="row">
				<div class="col-xs-12">
					<div  id="container">
						<div class="box pane ui-layout-center">
							<div class="box-header">
				              	<h3 class="box-title"><i class="fa fa-chevron-right" aria-hidden="true"></i>테이블 목록</h3>
				              	<div class="navbar-form navbar-right"></div>
				            </div>
				            <div class="box-body">
				              	<table id="jqGrid"></table>
							  	<div id="jqGridPager"></div>
				            </div>
						</div>
						<div class="box pane ui-layout-south">
							<div class="box-header">
				              	<h3 class="box-title"><i class="fa fa-chevron-right" aria-hidden="true"></i>컬럼 정보</h3>
				              	<div class="navbar-form navbar-right"></div>
				            </div>
				            <div class="box-body">
					            <ul class="nav nav-tabs" id="subTab1" >
					            	<li class="active"><a href="#subTab1-001" data-toggle="tab" aria-expanded="true">기본정보</a></li>
					            	<li><a href="#subTab1-002" data-toggle="tab">스크립트</a></li>
					            </ul>
					            <div class="tab-content" style="padding:10px 0px 0px 0px">
				             		<div class="active tab-pane" id="subTab1-001">
				               			<table id="jqGrid2"></table>
				              			<div id="jqGridPager2"></div>
				             		</div>
				             		<div class="tab-pane" id="subTab1-002">
				        				<textarea id="ddlScript" rows="" cols="" style="width: 100%;" readonly="readonly"></textarea>
				             		</div>
				            	</div>
				            </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<input type="hidden" id="reqParam">
	<script type="text/javascript">
		$("input#reqParam").data("viewId", '${viewId}');
		$("input#reqParam").data("menuId", '${menuId}');
	</script>
	<script src="<spring:url value="/app/portal/pages/tech/db/020102L.js"/>"></script>
</body>
</html>