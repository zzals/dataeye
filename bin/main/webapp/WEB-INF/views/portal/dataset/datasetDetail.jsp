<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ page import="java.util.*" %>

<%
Map<String,String> datasetDetail = (Map<String,String>)request.getAttribute("datasetDetail");

List<Map<String,String>> datasetSampleColumnList = (List<Map<String,String>>)request.getAttribute("datasetSampleColumnList");
List<Map<String,String>> datasetSampleFileColumnList = (List<Map<String,String>>)request.getAttribute("datasetSampleFileColumnList");

String datasetSampleDiv = (String)request.getAttribute("datasetSampleDiv");

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



  </script>
  
</head>

<body class="de-popup">

	<section id="header">
        <div class="popup_Title_Area">
            <span class="fav_star"><i class="fa fa-star"></i></span><div class="popup_title"><%=datasetDetail.get("OBJ_NM") %></div>
            <div class="box-tools pull-right">
           
            </div>	             
        </div>      
    </section>
    <!-- Maincontent -->
	<section class="content" style="margin:0 20px;">
		
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
  				<div class="panel-body" style="padding:15px;"  id="infoDiv">  
  					<div class="Grid_Area">
						<table class="table_Type2">
							<tr>
								<th>데이터분석명</th>
								<td colspan="5">
									<a href="javascript:datasetInfo('<%=datasetDetail.get("OBJ_TYPE_ID") %>','<%=datasetDetail.get("OBJ_ID") %>')"><%=datasetDetail.get("OBJ_NM") %> <i class="fa fa-files-o" style="font-weight:normal"></i></a>
								</td>
							</tr>
							<tr>
								<th>설명</th>
								<td colspan="5"><%=datasetDetail.get("OBJ_DESC") %></td>
							</tr>
							<tr>
								<th>데이터갱신주기</th>
								<td><%=datasetDetail.get("ETL_IFCYCL") %></td>
								<th>보관기간</th>
								<td><%=datasetDetail.get("DATAKEEPCYCLE") %></td>
								<th>관리 책임 부서</th>
								<td><%=datasetDetail.get("TEAM_NM") %></td>
							</tr>
							<tr>
								<th>분류체계명</th>
								<td><%=datasetDetail.get("PATH_OBJ_NM") %></td>
								<th>물리테이블명</th>
								<td><%=datasetDetail.get("ADM_OBJ_ID") %></td>
								<th>등록자</th>
								<td><%=datasetDetail.get("CRETR_ID") %></td>
							</tr>
						</table>
					</div>			          
				</div>
			</div>
		</div>
	<div class="popup_Content_Area">
	<ul class="nav nav-tabs">
			<li class="active"><a href="#tab-001" data-toggle="tab" aria-expanded="true"  onclick="tabClick(1)">샘플 데이터</a></li>
			<li><a href="#tab-002" data-toggle="tab" onclick="tabClick(2)">구성항목</a></li>
		</ul>
			
				
				<div class="panel-body"  style="width:100%; height:450px;"  id="listDiv">
				<br>   		
		           	<div class="col-md-12">
		           		<div>
		           			<select id="listCnt">
		           				<option value="10">10건</option>
		           				<option value="100">100건</option>
		           				<option value="1000">1000건</option>
		           				
		           			</select>
		           		</div>
						<div class="box">
				            <div class="box-body" de-resize-pos="middle">
				             <table id="sampleData"></table>	
				             <div class="ui-pg-div" id="export">
    							<span class="glyphicon fa fa-file-excel-o" ></span>
    						 </div>	
				             		            
				            </div>
				            <!-- /.box-body -->
				        </div>
				          				
					</div>			
			</div>
			
			<div class="panel-body"  style="width:100%; height:450px;display:none"  id="columListDiv">
			<br>  		
		           	<div class="col-md-12">		           		
						 <table id="jqGrid"></table>
						  <div id="jqGridPager" ></div>	
					</div>			
			</div>
			
		<iframe name="sampleIFrm" id="sampleIFrm" width="0" height="0" style="display:none"></iframe>
		<form name="frm" method="post" action="/dataeye_ebay/jqgrid/excelExport" target="sampleIFrm">
			<input type="hidden" name="gridParam" id="gridParam">
			<input type="hidden" name="file" id="file">
		</form>		

	</section>
	
	
<script language="javascript">
function tabClick(div) {
	if(div==1) {
		$("#listDiv").show()
		$("#columListDiv").hide()
	} else if(div==2) {
		$("#listDiv").hide()
		$("#columListDiv").show()
		
	}
	
}
$(document).ready(function() {


	$("#export").on("click", function(){

		var gridParam = JSON.stringify($("#sampleData").jqGrid("getGridParam"));
			
		$("#gridParam").val(gridParam);
		$("#file").val("sampleExport.xls");
		frm.submit();
	});
	
	

	$('#listCnt').change(function() {	
		var cnt = $('#listCnt option:selected').val();		
		viewSampleData(cnt);	
	});


	
	viewColumData();
	viewSampleData(10);
	
	
	function viewSampleData (cnt) {
		$.jgrid.gridUnload('#sampleData');


		<%
		if(datasetSampleDiv.equals("db")) {
		%>
			
			$("#sampleData").jqGrid({
			   	url:'/dataeye_ebay/dataset/datasetSampleData?datasetId=<%=request.getParameter("datasetId")%>&cnt=' + cnt,
				datatype: "json",			
			
		<%
			
			boolean isFirst = true;		
			%>		
			   	colModel:[

			   		<%		   		
			   		for(Map cont:datasetSampleColumnList){
			   			if(isFirst) {
			   	%>
			   	{label:'<%=cont.get("OBJ_NM")%>', name:'<%=cont.get("ADM_OBJ_ID")%>',index:'<%=cont.get("ADM_OBJ_ID")%>', width:150}
			   	<%
			   	isFirst = false;
			   			} else {
			   	%>
			   	,{label:'<%=cont.get("OBJ_NM")%>',name:'<%=cont.get("ADM_OBJ_ID")%>',index:'<%=cont.get("ADM_OBJ_ID")%>', width:150}
			   	<%
			   			}
			   		}
			   	%>		   			
			   	],
				autowidth:false,
				shrinkToFit:true,
				toppager: false,
				loadonce: true,
				scroll:-1,
				isPaging:false
				    
			}).trigger('reloadGrid');
				
			<%
				} else  {
			%>
			$("#sampleData").jqGrid({
			   	url:'/dataeye_ebay/dataset/datasetSampleFileData?datasetId=<%=request.getParameter("datasetId")%>&cnt=' + cnt,
				datatype: "json",
				colModel:[
				<%
				int i=0;
				for(Map cont2:datasetSampleFileColumnList){
					if(i==0){
				%>
					{label:'<%=cont2.get("COL"+i)%>', name:'COL<%=i%>',index:'COL<%=i%>', width:150}
				<%
					}else{
				%>
					,{label:'<%=cont2.get("COL"+i)%>', name:'COL<%=i%>',index:'COL<%=i%>', width:150}
				<%		
					}
					i++;
				}
				%>
				],
				autowidth:false,
				shrinkToFit:true,
				toppager: false,
				loadonce: true,
				scroll:-1,
				isPaging:false
			}).trigger('reloadGrid');

		<%
			}
		 %>
		

		DE.jqgrid.navGrid($("#sampleData"), $("#sampleDataPager"));


		
		$(".ui-jqgrid").css("width","100%");
		$(".ui-jqgrid-view").css("width","100%");
		$(".ui-jqgrid-hdiv").css("width","100%");				
		$(".ui-jqgrid-bdiv").css("height","380px");

	}


	function viewColumData() {
		var colModel = [	   		    
		    { index:'ADM_OBJ_ID', name: 'ADM_OBJ_ID', label: '컬럼물리명', width: 300, align:'left'},            	
	      	{ index:'OBJ_NM', name: 'OBJ_NM', label: '컬럼논리명', width: 320, align:'left'},
	      	{ index:'OBJ_NM', name: 'OBJ_DESC', label: '컬럼설명', width: 600, align:'left'},
	      	{ index:'COL_ORD', name: 'COL_ORD', label: '컬럼순서', width:115, align:'center'},
	      	{ index:'DATA_TYPE_NM', name: 'DATA_TYPE_NM', label: '데이타타입', width: 300, align:'center'},    
	      	{ index:'PK_YN', name: 'PK_YN', label: 'PK 여부', width: 200, align:'center'},
	      	{ index:'OBJ_TYPE_ID', name: 'OBJ_TYPE_ID', label: 'OBJ_TYPE_ID', hidden:true},
	      	{ index:'OBJ_ID', name: 'OBJ_ID', label: 'OBJ_ID', hidden:true}, 		        
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
				"queryId":"portal.dataset.getDatasetColumnList",
				"datasetId":"<%=request.getParameter("datasetId")%>",
			};
			DE.jqgrid.reload($("#jqGrid"), postData);

			$(".ui-jqgrid").css("width","100%");
			$(".ui-jqgrid-view").css("width","100%");
			$(".ui-jqgrid-hdiv").css("width","100%");				
			$(".ui-jqgrid-bdiv").css("height","380px");

			$(".table-responsive").css("overflow-x","hidden");

			
			
	}


	
});


</script>
</body>
</html>

