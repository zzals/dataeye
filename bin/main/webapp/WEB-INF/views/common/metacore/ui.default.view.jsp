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
</head>
<body>
<!-- UI Object -->
<div id="container">
	<section class="content">
		<div class="row">
			<div class="col-xs-12">
				<div class="box">
		            <div class="box-body" de-boxbody-type="default">
		              <table id="jqGrid" de-role="grid"></table>
					  <div id="jqGridPager" de-role="grid-pager"></div>
		            </div>
		            <!-- /.box-body -->
		          </div>
		          <!-- /.box -->				
			</div>
		</div>
	</section>
</div>

<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>
<script src="<spring:url value="/app/common/metacore/ui.view.main.js"/>"></script>
</body>
</html>