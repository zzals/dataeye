<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <title><spring:message code="app.title" text="DateEye" /></title>
<c:import url="/deresources/commonlib/popup_css" />
<c:import url="/deresources/commonlib/js" />
<script src="<spring:url value="/assets/javascripts/dataeye.resizer.js"/>"></script>
<style type="text/css">
.content .box-body {
	height: -moz-calc(100vh - 140px);
    height: -webkit-calc(100vh - 140px);
    height: calc(100vh - 140px);
}
</style>
</head>
<body class="de-popup">
	<section id="header">
        <div class="popup_Title_Area">
            <div class="popup_title">부서선택</div>
	        <div class="box-tools pull-right">
	        	<button type="button" class="close" onclick="window.close()">x</button>
	        </div>
        </div>
    </section>

    <!-- Maincontent -->
	<section class="content">
		<nav class="navbar navbar-default">				
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
						<input id="searchValue" class="form-control" placeholder="Search" de-search-key="ORG_NM">
					</div>
					<button type="submit" id="btnSearch" class="btn btn-default">검색</button>
				</div>
			</div>
		</nav>
		<div class="container-fluid">
           	<div class="col-md-12">
	        	<div class="box">
	          		<div class="box-body" de-role="grid-wrapper">
	              		<table id="jqGrid" de-role="grid" de-resize=false></table>
				  		<div id="jqGridPager" de-role="grid-pager"></div>
	            	</div>
	            </div>
	        </div>
        </div>
	</section>
	<!-- //Maincontent -->

<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>
<script src="<spring:url value="/app/common/metacore/bdp/deptSelected.js"/>"></script>
</body>
</html>