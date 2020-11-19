<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@page import="kr.co.penta.dataeye.spring.web.session.SessionInfoSupport"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.*" %>

<%
	Map<String,String> toolLoginUserDetail = (Map<String,String>)request.getAttribute("toolLoginUserDetail");
	List<Map<String,String>> reportDetailList = (List<Map<String,String>>)request.getAttribute("reportDetailList");
	String filterHtml = (String)request.getAttribute("filterHtml");
	String selectBox = (String)request.getAttribute("selectBox");
	String fromData = (String)request.getAttribute("fromData");
%>
<%!
    public String stringValue(Object val) {
        return (val==null)?"":val.toString();
    }
%>

<%
			
		String TOOL_ID = "";		
		String TOOL_PWD = "";
		if(toolLoginUserDetail!=null) {
			TOOL_ID = (toolLoginUserDetail.get("TOOL_ID")==null)?"":(String)toolLoginUserDetail.get("TOOL_ID");
			TOOL_PWD = (toolLoginUserDetail.get("TOOL_PWD")==null)?"":(String)toolLoginUserDetail.get("TOOL_PWD");
		}
	

		Map reportDetail = (Map)reportDetailList.get(0);

%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="app.title" text="DateEye" /></title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/icon/favicon_32_deye.ico"/>
<!-- Bootstrap 3.3.7 -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Font Awesome -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!-- jquery-ui 1.10.3 -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/jquery-ui-bootstrap/jquery-ui-1.10.3.custom.css">
<!-- jquery layout -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.layout/layout-custom.css">
<!-- jqgrid 5.2.0(bootstrap) -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/javascripts-lib/Guriddo_jqGrid_JS_5.2.0/css/ui.jqgrid-bootstrap.css">
<!-- date time picker -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/javascripts-lib/datetimepicker/bootstrap-datetimepicker.min.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/javascripts-lib/select2/select2.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/dataeye-main.css">
<!-- Theme style -->
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/theme/kcis/stylesheets/dataeye-main.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/theme/kcis/stylesheets/dataeye-objectinfo.css"/>

<!-- DataEye main Skins. Choose a skin from the css/skins
 	folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/skins/_all-skins.min.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/popup.css">
<script type="text/javascript">
    <!--
    (function() {
    	DE = {};	
    	DE.contextPath = "";
    	DE.setContextPath = function(contextPath) {
    		DE.contextPath = contextPath;
    	};
    })();
    DE.setContextPath('/dataeye_ebay');    
    //-->
</script>
<!-- jQuery -->
<script src="${pageContext.request.contextPath}/webjars/jquery/3.1.1/jquery.min.js"></script>
<!-- Bootstrap -->
<!-- <script src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/webjars/bootbox/4.4.0/bootbox.js"></script>
<!-- jquery-ui -->
<script src="${pageContext.request.contextPath}/webjars/jquery-ui/1.12.1/jquery-ui.min.js"></script>
<!-- jqgrid -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/Guriddo_jqGrid_JS_5.2.0/js/i18n/grid.locale-kr.js"></script>

<script src="${pageContext.request.contextPath}/assets/javascripts-lib/Guriddo_jqGrid_JS_5.2.0/src/jquery.jqGrid.js"></script>

<!-- jquery-i18n-properties -->
<script src="${pageContext.request.contextPath}/webjars/jquery-i18n-properties/1.2.2/jquery.i18n.properties.min.js"></script>

<!-- jquery layout -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.layout/jquery.layout_and_plugins.js"></script>
<!-- jquery treeview -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/bootstrap.treeview/bootstrap-treeview.js"></script>
<!-- jquery mask -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.mask/jquery.mask.min.js"></script>
<!-- Sparkline -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/sparkline/jquery.sparkline.min.js"></script>
<!-- SlimScroll 1.3.0 -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/slimScroll/jquery.slimscroll.min.js"></script>
<!-- filedownload -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.fileDownload/jquery.fileDownload.js"></script>

<!-- date time picker -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/datetimepicker/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/datetimepicker/ko.js"></script>

<script src="${pageContext.request.contextPath}/assets/javascripts-lib/iCheck/icheck.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/javascripts-lib/select2/select2.full.min.js"></script>

<!-- PostMessage -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/postMessage/postmessage.min.js"></script>
<!-- resize sensor -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/resize-sensor/ResizeSensor.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/resize-sensor/ElementQueries.js"></script>
<!-- DataEye lib -->
<script src="${pageContext.request.contextPath}/assets/javascripts/dataeye.common.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts/dataeye.core.js"></script>
<!-- DataEye for lib -->
<script src="${pageContext.request.contextPath}/assets/javascripts/dataeye.ui.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts/dataeye.util.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts/dataeye.schema.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts/jquery.default.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts/jqgrid.default.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts/dataeye.aprv.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts/map.js"></script>
<!-- DataEye for main -->
<script src="${pageContext.request.contextPath}/app/dataeye.main.js"></script>

<!-- ckeditor -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/ckeditor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts/dataeye.resizer.js"></script>

  <!-- Custom fonts for this template-->
  <link href="${pageContext.request.contextPath}/assets/resource/admin/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

  <!-- Page level plugin CSS-->
  <link href="${pageContext.request.contextPath}/assets/resource/admin/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="${pageContext.request.contextPath}/assets/resource/admin/css/sb-admin.css" rel="stylesheet">

   <!-- bootstrap 4-->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/resource/admin/bootstrap4/css/bootstrap.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/resource/admin/bootstrap4/css/bootstrap-grid.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/resource/admin/bootstrap4/css/bootstrap-reboot.css">

  <!-- Bootstrap core JavaScript-->
  <script src="${pageContext.request.contextPath}/assets/resource/admin/vendor/jquery/jquery.min.js"></script>
    
  <script src="${pageContext.request.contextPath}/assets/resource/admin/bootstrap4/js/bootstrap.js"></script>
  <script src="${pageContext.request.contextPath}/assets/resource/admin/bootstrap4/js/bootstrap.bundle.js"></script>

  <script src="${pageContext.request.contextPath}/assets/resource/admin/vendor/datatables/jquery.dataTables.js"></script>
  <script src="${pageContext.request.contextPath}/assets/resource/admin/vendor/datatables/dataTables.bootstrap4.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="${pageContext.request.contextPath}/assets/resource/admin/js/sb-admin.min.js"></script>
 
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  

<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts/jquery.mtz.monthpicker.js"></script>
 <style type="text/css">
 body
    {
        width:100%;
        margin-left:auto;
        margin-right:auto;
        font-size:1.5rem;
    }

.ui-datepicker-trigger{
	margin-left:5px;
}
  </style> 
    <script language="javascript">
	$(function(){
		$('#mstrForm').attr('target','ifrm').submit();
	});
    
    function doProc() {
 		$('#reportForm').attr("action", "reportExec?reportId=<%=request.getParameter("reportId") %>");
 	    $('#reportForm').attr("method", "post");
 	    $('#reportForm').submit();
   	}
  </script>
  
</head>
	
<body class="de-popup">
	<section id="header">
        <div class="popup_Title_Area">
            <span class="fav_star"><i class="fa fa-star"></i></span><div class="popup_title"><%=reportDetail.get("OBJ_NM") %></div>
            <div class="box-tools pull-right">
           
            </div>	             
        </div>      
    </section>
    <!-- Maincontent -->
	<section class="content" style="margin:0 20px;">
		
		<div class="container-fluid">
			<div class="search_Area">
				<div class="Grid_Area">
					<form id="reportForm" action="" method="post">
					<%=filterHtml %>
					</form>
				</div>
			</div>		
			
		<div class="container-fluid">		
			<div class="panel panel-default">				
  				<div class="panel-body">  				           		
						<iframe src="" width="100%" height="800px" frameborder=0 framespacing=0 marginheight=0 marginwidth=0 scrolling=no vspace=0 name="ifrm" id="ifrm"></iframe>
						<form id="mstrForm" action="http://192.168.100.73:8080/MicroStrategy/_custom/jsp/sso.jsp" method="post">
						<input type="hidden" name="uid" id="uid" value="<c:out value="${sessionScope[SessionInfoSupport.SESSION_USERINFO_NAME]['userId']}"/>" />
						<input type="hidden" name="returnUrl" id="returnUrl" value="<c:out value='${connUrl}' />" />
						<input type="hidden" name="promptsAnswerXML" value="${promptsAnswerXML}" />
						</form>											
				</div>
			</div>
		</div>		
		</div>	
	</section>

</body>
</html>

