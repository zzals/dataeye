<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.util.*" %>

<%
List<Map<String,String>> reportDetailList = (List<Map<String,String>>)request.getAttribute("reportDetailList");


Map reportDetail = (Map)reportDetailList.get(0);

String d108_atr_val = (String)reportDetail.get("D108_ATR_VAL");
String filterHtml = (String)request.getAttribute("filterHtml");

%>
<%!
    public String stringValue(Object val) {
        return (val==null)?"":val.toString();
    }
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
  </style> 
    <script language="javascript">
 	function doProc() {
 		//window.open('/dataeye_ebay/report/reportExec?reportId=<%=request.getParameter("reportId") %>','_blank','')

 		$('#reportForm').attr("action", "reportExec?reportId=<%=request.getParameter("reportId") %>");
 	    $('#reportForm').attr("method", "post");
 	    $('#reportForm').attr("target", "formInfo");
 	    window.open("", "formInfo", "");
 	    $('#reportForm').submit();    
   }

  	function reportInfo(OBJ_TYPE_ID,OBJ_ID) {
  		DE.ui.open.popup(
				"/dataeye_ebay/portal/view",
				[OBJ_TYPE_ID, OBJ_ID],
				{
					viewname:'common/metacore/objectInfoTab',
					objTypeId:OBJ_TYPE_ID,
					objId:OBJ_ID,
					mode:'R'
				},
				null
			);
  	 }

	var toggle = true;
	
  	function procInfo() {
  	  	if(toggle) {
  	  		$("#infoDiv").hide();
  	  		toggle = false;

	  	  	$("#infoIcon").removeClass("fa fa-angle-down")
	  	  	$("#infoIcon").addClass("fa fa-angle-up")
	
	  	  	$("#listDiv").height(604);
  	  
  	  
  	  	 } else{
  	  		$("#infoDiv").show();
  	  		toggle = true;
	  	  	$("#infoIcon").removeClass("fa fa-angle-up")
	  	  	$("#infoIcon").addClass("fa fa-angle-down")
	  		$("#listDiv").height(400);
  	  	 }
		
  	}

  	function selChange(){
  		for( var i=0; i<$('#selectBox option').size(); i++){
  		  if( $("#selectBox option:eq("+i+")").attr("selected")){
  		   //alert($("#selectBox option:eq("+i+")").val());
  		  }
  		}
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
			<!-- <div class="panel panel-default">
				
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
		-->
		
		<div class="container-fluid">		
			<div class="panel panel-default">
				<div class="panel-heading">상세내용 <span style="float:right; cursor:pointer;" onclick="procInfo()"><i class="fa fa-angle-down" id="infoIcon"></i></span></div>
  				<div class="panel-body" style="padding:15px;" id="infoDiv">  
  					<div class="Grid_Area">
						<table class="table_Type2">
							<tr>
								<th>데이터분석명</th>
								<td colspan="5">
									<a href="javascript:reportInfo('<%=reportDetail.get("OBJ_TYPE_ID") %>','<%=reportDetail.get("OBJ_ID") %>')"><%=reportDetail.get("OBJ_NM") %> <i class="fa fa-files-o" style="font-weight:normal"></i></a>
								</td>
							</tr>
							<tr>
								<th>설명</th>
								<td colspan="5"><%=reportDetail.get("OBJ_DESC") %></td>
							</tr>
							<tr>
								<th>분류체계명</th>
								<td><%=reportDetail.get("PATH_OBJ_NM") %></td>
								<th>중요도</th>
								<td><%=reportDetail.get("OBJ_ATR_VAL_101_NM") %></td>
								<th>활용주기</th>
								<td><%=reportDetail.get("OBJ_ATR_VAL_104_NM") %></td>
							</tr>
							<tr>
								<th>활용목적</th>
								<td><%=reportDetail.get("OBJ_ATR_VAL_102_NM") %></td>
								<th>사용자명</th>
								<td><%=reportDetail.get("OBJ_ATR_VAL_105_NM") %></td>
								<th>담당자명</th>
								<td><%=stringValue((String)reportDetail.get("OBJ_ATR_VAL_106_NM"))%></td>
							</tr>
						</table>
					</div>			          
				</div>
			</div>
		</div>
		<div class="container-fluid">		
			<div class="panel panel-default">
				<div class="panel-heading"> 구성항목</div>
  				<div class="panel-body"  style="overflow-y:scroll; width:100%; height:400px;"  id="listDiv">  		
		           	<div class="col-md-12">
						<div class="box">							
				            <div class="box-body" de-resize-pos="middle">
				              <table id="jqGridAnal" width="100%" border="1" bordercolor="#9e9e9e">
				              <thead>
				              <tr height="30px" align="center">
					              <th width="2%"> 구분</th>
					              <th width="20%"> 구성항목명</th>
					              <th width="35%"> 설명</th>
					              <th> 샘플데이터/산출식</th>
					          </tr>
					          </thead>
					          <tbody>
					          <%
					      	
					          	for(Map cont : reportDetailList){
					          		if(((String)cont.get("REL_OBJ_TYPE_ID")).equals("010105L")||((String)cont.get("REL_OBJ_TYPE_ID")).equals("010104L")){
					          			
					           %>
					              <tr height="30px" align="left" >
					              	  <td><div style="margin-left:15px">
					              	  	<%
					              	  		if(((String)cont.get("REL_OBJ_TYPE_ID")).equals("010105L")) {
					              	  	%>
					              	  	 	<i class="fa fa-cube" aria-hidden="true"></i>
					              	  	<%} else { %>
					              	  		<i class="fa fa-bar-chart" aria-hidden="true"></i>
					              	  	<%} %>
					              	  
					              	  </div></td>
						              <td><div style="margin-left:15px"><%=cont.get("D_NAME") %></div></td>
						              <td><div style="margin-left:15px"><%=cont.get("D_DESC") %></div></td>
						              <td><div style="margin-left:15px"><%=cont.get("D101_ATR_VAL") %></div></td>
						          </tr>
					          <%
					          		}
					          	 } 
					          	%>
					         
					          </tbody>
				              </table>	
				              <br>
				              		  
				            </div>
				            <!-- /.box-body -->
				        </div>
				          				
					</div>
				</div>
			</div>
		</div>
		</div>
		</div>
		
	</section>


</body>
</html>

