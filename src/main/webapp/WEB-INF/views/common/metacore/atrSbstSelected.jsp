<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><spring:message code="app.title" text="DateEye" /></title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<c:import url="/deresources/commonlib/popup_css" />
<c:import url="/deresources/commonlib/js" />
<script src="<spring:url value="/assets/javascripts/dataeye.resizer.js"/>"></script>
<style type="text/css">
.box-body {
	height: -moz-calc(100vh - 100px);
    height: -webkit-calc(100vh - 100px);
    height: calc(100vh - 100px);
}
</style>
</head>
<body class="de-popup">
    <!-- HEADER -->
    <section id="header">
        <div class="popup_Title_Area">
            <div class="popup_title"></div>
	        <div class="box-tools pull-right">
	        	<button type="button" class="close" onclick="window.close()">x</button>
	        </div>
        </div>
    </section>
    <!-- //HEADER -->
	<section class="content">
		<div class="container-fluid">
			<div class="col-xs-12">
				<div class="box">
		            <div class="box-body">
		                <table id="jqGrid"></table>
					  	<div id="jqGridPager"></div>
		            </div>
		            <!-- /.box-body -->
		          </div>
		          <!-- /.box -->				
			</div>
		</div>
	</section>

<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>
<script src="<spring:url value="/app/common/metacore/atrSbstSelected.js"/>"></script>
</body>
</html>