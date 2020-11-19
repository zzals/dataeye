<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.*" %>
<%
	
	List<Map<String,String>> reportDetailList = (List<Map<String,String>>)request.getAttribute("reportDetailList");

	Map reportDetail = (Map)reportDetailList.get(0);
%>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>DataEye</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/icon/favicon_32_deye.ico"/>
<!-- Bootstrap 3.3.7 -->
<link type="text/css" rel="stylesheet" href="/dataeye_ebay/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
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
<link type="text/css" rel="stylesheet" href="/dataeye_ebay/theme/kcis/stylesheets/dataeye-main.css">
<link type="text/css" rel="stylesheet" href="/dataeye_ebay/theme/kcis/stylesheets/dataeye-objectinfo.css"/>

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
    DE.setContextPath('/dataeye');    
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
 <style type="text/css">
 body
    {
        width:80%;
        margin-left:auto;
        margin-right:auto;
        font-size:1.5rem;
    }

.ui-datepicker-trigger{
	margin-left:5px;
}
  </style> 
    <script language="javascript">
 	function doProc() { 	 	
 		//$("#ifrm").attr("src","http://192.168.100.73:9300/bi/<%=reportDetail.get("OBJ_ATR_VAL_109_NM")%>"); 	 		 
 		$("#ifrm").attr("src","<%=reportDetail.get("OBJ_ATR_VAL_109_NM")%>");
   }
  	$(function() {
  	    $( "#fromData" ).datepicker({
  	    	 dateFormat: 'yy-mm-dd' //Input Display Format 변경
                 ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
                 ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
                 ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
                 ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
                 ,buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
                 ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
                 ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
                 ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
                 ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
                 ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
                             
  	    });
  	  $('#fromData').datepicker('setDate', 'today');
  	   
  	});
  </script>
  
</head>
	
<body class="de-popup">
	<section id="header">
        <div class="popup_Title_Area">
            <div class="popup_title"><%=reportDetail.get("OBJ_NM") %></div>
            <div class="box-tools pull-right">
           
            </div>	             
        </div>      
    </section>
    <!-- Maincontent -->
	<section class="content">
		
		<div class="container-fluid">		
			<div class="panel panel-default">
				
  				<div class="panel-body">  		
		           	<div class="col-sm-10">
						<div class="form-horizontal">
							<div class="box-body" de-role="form">				              				              				              
				                  <label for="DB_PWD" class="col-sm-2 control-label">기준일자</label>
				                  <div class="col-sm-2">
				                    	<input type="text" class="form-control" id="fromData" name="fromData" placeholder="" style="width:100px"> 				                    
				                  </div>				                         				            
				        </div>				
					</div>
				</div>
				 <div class="col-sm-2">
				 
				 	<div class="form-horizontal">
							<div class="box-body" de-role="form">
				 			 <button id="btnSearch" class="btn btn-default" style="font-size:15px" onclick="doProc()">실행</button>
				 	</div>				
					</div>
				 </div>
				
			</div>
		</div>	
		<div class="container-fluid">		
			<div class="panel panel-default">				
  				<div class="panel-body">  				           		
  						<iframe src="<%=reportDetail.get("OBJ_ATR_VAL_109_NM")%>" width="1535px" height="800px" frameborder="0" gesture="media" allow="encrypted-media" allowfullscreen=""></iframe>  														
				</div>
			</div>
		</div>		
		</div>	
	</section>


</body>
</html>

