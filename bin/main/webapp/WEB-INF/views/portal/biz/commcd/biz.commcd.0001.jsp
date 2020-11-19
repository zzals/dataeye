<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><spring:message code="app.title" text="DateEye" /></title>
<style class="cp-pen-styles" id="">
	
	#container {
		height:		calc(100Vh - 100px);
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
	.listType {margin:0 15px;}
</style>	
</head>
<body>
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
					<div class="navbar-form navbar-left"></div>
					<div class="navbar-form navbar-right">
						<div class="form-group">
							<select id="searchKey" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;">
			                  	<option value="_all">[전체]</option>
			                  	<option value="COMMCD_GRP_NM">코드그룹명</option>
			                  	<option value="STD_TERM_NM">표준용어명</option>
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
					<div id="container">
						<div class="box pane ui-layout-center">
							<div class="box-header">
				              	<h3 class="box-title"><i class="fa fa-chevron-right" aria-hidden="true"></i>공통코드 그룹 목록</h3>
				              	<div class="navbar-form navbar-right">
					              	<c:if test="${AUTH.getCAuth() == 'Y'}">
										<button type="button" id="btnCdGrpInsert" class="btn btn-default">등록</button>
									</c:if>
									<c:if test="${AUTH.getUAuth() == 'Y'}">
										<button type="button" id="btnCdGrpUpdate" class="btn btn-default">수정</button>
									</c:if>
									<c:if test="${AUTH.getDAuth() == 'Y'}">
										<button type="button" id="btnCdGrpDelete" class="btn btn-default">삭제</button>
									</c:if>
						            
								</div>
								
				            </div>
				            
			
				            <div class="box-body">
					              	<table id="jqGrid"></table>
								  	<div id="jqGridPager"></div>
				            </div>
						</div>
						<div class="box pane ui-layout-south">
							<div class="box-header">
				              	<h3 class="box-title"><i class="fa fa-chevron-right" aria-hidden="true"></i>공통코드 목록</h3>
				              	<div class="navbar-form navbar-right">
					              	<c:if test="${AUTH.getCAuth() == 'Y' or AUTH.getUAuth() == 'Y'}">
										<button type="button" id="btnCommcdAdd" class="btn btn-default">추가</button>
									</c:if>
					              	<c:if test="${AUTH.getCAuth() == 'Y' or AUTH.getUAuth() == 'Y'}">
										<button type="button" id="btnCommcdDel" class="btn btn-default">삭제</button>
									</c:if>
									<c:if test="${AUTH.getCAuth() == 'Y' or AUTH.getUAuth() == 'Y'}">
										<button type="button" id="btnCommcdUp" class="btn btn-default">위로</button>
									</c:if>
									<c:if test="${AUTH.getCAuth() == 'Y' or AUTH.getUAuth() == 'Y'}">
										<button type="button" id="btnCommcdDown" class="btn btn-default">아래로</button>
									</c:if>
									<c:if test="${AUTH.getCAuth() == 'Y' or AUTH.getUAuth() == 'Y'}">
										<button type="button" id="btnCommcdSave" class="btn btn-default">저장</button>
									</c:if>
								</div>
				            </div>
				            <div class="box-body">
					              	<table id="jqGrid2"></table>
								  	<div id="jqGridPager2"></div>
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
	<script src="<spring:url value="/app/portal/pages/biz/commcd/biz.commcd.0001.js"/>"></script>
</body>
</html>