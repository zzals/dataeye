<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ page import="java.util.*" %>
<%

	Map<String,String> taskDetail = (Map<String,String>)request.getAttribute("taskDetail");


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
<link type="text/css" rel="stylesheet" href="/dataeye/webjars/bootstrap/3.3.7/css/bootstrap.min.css">
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
<link type="text/css" rel="stylesheet" href="/dataeye/theme/kcis/stylesheets/dataeye-main.css">
<link type="text/css" rel="stylesheet" href="/dataeye/theme/kcis/stylesheets/dataeye-objectinfo.css"/>

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

	<!-- Load pdfmake lib files -->
	<script type="text/javascript" language="javascript" src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.26/build/pdfmake.min.js">	</script>
	<script type="text/javascript" language="javascript" src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.26/build/vfs_fonts.js"></script>
	
<!-- jszip -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/Stuk-jszip-3109282/dist/jszip.min.js"></script>

<script src="${pageContext.request.contextPath}/webjars/jquery/3.1.1/jquery.min.js"></script>


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
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/javascripts-lib/d3/d3.v3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/javascripts/dataeye.resizer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/app/portal/dataeye.portal.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/javascripts/dataeye.tsearch.js"></script>
 <style type="text/css">
 body
    {
        width:100%;
        margin-left:auto;
        margin-right:auto;
        font-size:1.5rem;
    }
   .popup_Content_Area {
   max-width:2000px
   }
  </style> 
    <script language="javascript">
 	
  	$(function() {
  	    $( "#fromData" ).datepicker({
  	    	 dateFormat: 'yy-mm-dd' //Input Display Format 변경
                 ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
                 ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
                 ,showOn: "input" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
                 ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
                 ,buttonImageOnly: false //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
                 ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
                 ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
                 ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
                 ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
                 ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
                             
  	    });
  	  $('#fromData').datepicker('setDate', 'today');
  	   
  	});

  	function datasetInfo(OBJ_TYPE_ID,OBJ_ID) {
  		DE.ui.open.popup(
				"/dataeye/portal/view",
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



  </script>
  
</head>

<body class="de-popup">

	<section id="header">
        <div class="popup_Title_Area">
            <span class="fav_star"><i class="fa fa-star"></i></span><div class="popup_title"><%=taskDetail.get("OBJ_NM")%></div>
            <div class="box-tools pull-right">
           
            </div>	             
        </div>      
    </section>
    <!-- Maincontent -->
	
		<div class="container-fluid">		
		
  				<div class="panel-body" style="padding:15px;"  id="infoDiv">  
  					<div class="Grid_Area">
						<table class="table_Type2">
							<tr>
								<th>분석과제 코드</th>
								<td>
									<%=taskDetail.get("ADM_OBJ_ID")%>
								</td>
							</tr>
							<tr  height="60">
								<th>설명</th>
								<td><%=taskDetail.get("OBJ_DESC")%></td>
							</tr>
							<tr>
								<th>과제PM</th>
								<td><%=taskDetail.get("PM_USER")%></td>
							</tr>
							<tr>
								<th>과제 멤버</th>
								<td>
									  <div class="box-body">
							              <table id="jqGrid" de-role="grid"></table>
										  <div id="jqGridPager" de-role="grid-pager"></div>
							            </div>
								</td>
							</tr>
							<tr>
								<th>과제영역</th>
								<td><%=taskDetail.get("CATE")%></td>
							</tr>
							<tr>
								<th>과제유형</th>
								<td><%=taskDetail.get("TYPE")%></td>
							</tr>
							<tr>
								<th>수행기간</th>
								<td><%=taskDetail.get("SDATE")%> ~ <%=taskDetail.get("EDATE")%></td>
							</tr>
							<tr height="60" >
								<th>과제목적</th>
								<td><%=taskDetail.get("PURPOSE")%></td>
							</tr>
							<tr  height="60">
								<th>기대효과</th>
								<td><%=taskDetail.get("EXPECT")%></td>
							</tr>
							<tr>
								<th>공개여부</th>
								<td><%=taskDetail.get("OPEN_YN")%></td>
							</tr>
						</table>
					
					</div>			          
				</div>
		</div>
	
	
<script language="javascript">
function viewColumData() {
	var colModel = [	   		    
	    { index:'USER_NM', name: 'USER_NM', label: '성명', width: 200, align:'center'},            	
      	{ index:'USER_ID', name: 'USER_ID', label: 'ID', width: 200, align:'center'},
      	{ index:'ORG_ID', name: 'ORG_ID', label: '부서', width: 200, align:'center'},
      	{ index:'EMAIL_ADDR', name: 'EMAIL_ADDR', label: '이메일', width:200, align:'center'},
      	{ index:'HP_NO', name: 'HP_NO', label: '휴대폰', width: 200, align:'center'}   
    ];

	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		autowidth:true,
		shrinkToFit:true,
		toppager: false,
		loadonce: true,
		scroll:-1,
		isPaging:false
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"));

	var postData = {
			"queryId":"portal.task.taskMemberList",
			"taskId":"<%=request.getParameter("taskId")%>",
		};
		DE.jqgrid.reload($("#jqGrid"), postData);

		$(".ui-jqgrid").css("width","100%");
		$(".ui-jqgrid-view").css("width","100%");
		$(".ui-jqgrid-hdiv").css("width","100%");				
		$(".ui-jqgrid-bdiv").css("height","200px");
		
}
viewColumData();




$(this).off("autoResize").on( "autoResize", function( event ) {

    	setTimeout(function () {



			//$(".ui-jqgrid").css("width",$(".box-body").width() + "px");
			//$(".ui-jqgrid-view").css("width",$(".box-body").width() + "px");
			//$(".ui-jqgrid-hdiv").css("width",$(".box-body").width() + "px");
			
			
            var width = $(".popup_Title_Area").width()-200;						

			$(".ui-jqgrid-view .table-responsive").css(width + "px");
			
	    	$("#jqGrid").setGridWidth(width,true);
	    	$(".ui-jqgrid-bdiv").css("overflow-x","hidden");
			$(".table-responsive").css("overflow-x","hidden");
	    	
	    	
    	}, 1);


}).trigger("autoResize");


</script>	
</body>
</html>

