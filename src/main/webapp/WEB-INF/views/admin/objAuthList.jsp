<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<title>Big Data Platform</title>
<c:import url="/deresources/commonlib/popup_css" />
<c:import url="/deresources/commonlib/js" />
<script src="<spring:url value="/assets/javascripts/dataeye.resizer.js"/>"></script>
</head>
<body class="de-popup">
    <!-- Maincontent -->
	<section class="content">
		<div class="container-fluid">
           	<div class="col-md-12 box">  
					<div class="box">
					<div id="main_list">
					    <div class="box-header">			              
			               <div class="navbar-right" style="margin-top:5px;margin-right:10px">							
							<button type="button" id="btnUserAdd" class="btn btn-default">거부 추가</button>							
							<button type="button" id="btnRemoveAttr" class="btn btn-default">삭제</button>							
						  </div>
			            </div>
					<!-- /.box-header -->
			         <!-- /.box-header -->
		            <div class="box-body" de-resize-pos="top">
		              <table id="jqGrid" de-role="grid"></table>
					  <div id="jqGridPager" de-role="grid-pager"></div>
		            </div>
			            <!-- /.box-body -->		            
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

<script src="<spring:url value="/app/admin/pages/objAuthList.js"/>"></script>
</body>
</html>

