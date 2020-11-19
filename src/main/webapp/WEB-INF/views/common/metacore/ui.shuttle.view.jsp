<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
</head>
<body class="de-popup">
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
					<div class="navbar-form navbar-right">
						<button type="submit" id="btnFlowView" class="btn btn-primary">흐름분석</button>
					</div>
				</div>
			</div>
		</nav>
		<div class="container-fluid col-xs-12">
			<div class="col-xs-6">
				<label class="control-label mgb" for="inputSuccess"><i class="fa fa-check"></i> Lineage 분석</label>													
			</div>
			<div class="col-xs-6">
				<label class="control-label mgb" for="inputSuccess"><i class="fa fa-check"></i> Impact 분석</label>	
			</div>
		</div>
		<div class="container-fluid">
			<div class="col-xs-12">
				<div class="box">
		            <div class="box-body" de-boxbody-type="shuttle">						
						<div class="col-xs-6 pdl">
							<table id="jqGrid1" de-role="grid"></table>
						    <div id="jqGridPager1" de-role="grid-pager"></div>
						</div>
						<div class="col-xs-6 pdr">
							<table id="jqGrid2" de-role="grid"></table>
						    <div id="jqGridPager2" de-role="grid-pager"></div>
						</div>						
					</div>
				</div>			
			</div>
		</div>
	</section>

<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>
<script src="<spring:url value="/app/common/metacore/ui.view.main.js"/>"></script>
</body>
</html>