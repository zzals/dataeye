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
		.content-body {
			height : calc(100Vh - 220px);
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
							<i class="glyphicon glyphicon-ok"></i>수집일시
						</label>
						<div class="form-group">
							<div class="input-group date" id="startDt">
								<input class="form-control input-sm" type="text" value="">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="form-group">
							<span class="label-addon">&nbsp;~&nbsp;</span>
						</div>
						<div class="form-group">
							<div class="input-group date" id="endDt">
								<input class="form-control input-sm" type="text" value="">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>
					<div class="navbar-form navbar-left">
						<label class="control-label" for="inputSuccess">
							<i class="glyphicon glyphicon-ok"></i>오브젝트유형
						</label>
						<div class="form-group">
							<select id="selObjType" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;">
								<option value="_all">[전체]</option>
			                  	<option value="TABLE">테이블</option>
								<option value="TABLE_COLUMN">테이블 컬럼</option>
								<option value="VIEW">뷰</option>
								<option value="VIEW_COLUMN">뷰 컬럼</option>
								<option value="PROCEDURE">프로시저</option>
							</select>
						</div>
					</div>
					<div class="navbar-form navbar-left">
						<label class="control-label" for="inputSuccess">
							<i class="glyphicon glyphicon-ok"></i>작업유형
						</label>
						<div class="form-group">
							<select id="selLogType" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;">
								<option value="_all">[전체]</option>
			                  	<option value="INSERT">등록</option>
								<option value="UPDATE">수정</option>
								<option value="DELETE">삭제</option>
							</select>
						</div>
					</div>
					<div class="navbar-form navbar-right">
						<div class="form-group">
							<select id="searchKey" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;">
			                  	<option value="_all">[전체]</option>
			                  	<option value="OBJ_NM">오브젝트 물리명</option>
			                  	<option value="OBJ_ABBR_NM">오브젝트 논리면</option>
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
			              <h3 class="box-title"><i class="fa fa-chevron-right" aria-hidden="true"></i>데이터 검증룰 실행결과 목록</h3>
			              <div class="navbar-form navbar-right">
			              	<%-- <c:if test="${AUTH.getCAuth() == 'Y'}">
								<button type="button" id="btnInsert" class="btn btn-default">등록</button>
							</c:if>
							<c:if test="${AUTH.getUAuth() == 'Y'}">
								<button type="button" id="btnUpdate" class="btn btn-default">수정</button>
							</c:if>
							<c:if test="${AUTH.getDAuth() == 'Y'}">
								<button type="button" id="btnDelete" class="btn btn-default">삭제</button>
							</c:if> --%>
						  </div>
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
	
	<!-- LOG 뷰 -->
	<div class="modal modal-fullscreen fade" id="getLog-view" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  		<div class="modal-dialog" style="width: 300px;">
    		<div class="modal-content">
      			<div class="modal-header">
        			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">닫기</span></button>
        			<h4 class="modal-title" id="myModalLabel">로그</h4>
      			</div>
	      		<div class="modal-body">
	      			<div class="box">
	           			<div class="box-body">
							<textarea id="logText" style="margin: 0px; width: 100%; height: 200px; resize: none;" readonly="readonly"></textarea>
						</div>
					</div>
	      		</div>
	      		<div class="modal-footer">
	        		<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
	      		</div>
	    	</div>
   	 	</div>
    </div>
    
	<!-- /.content -->
	<input type="hidden" id="reqParam">
	<script type="text/javascript">
		$("input#reqParam").data("viewId", '${viewId}');
		$("input#reqParam").data("menuId", '${menuId}');
	</script>	
	<script src="<spring:url value="/app/portal/pages/tech/db/collectLog.js"/>"></script>
</body>
</html>