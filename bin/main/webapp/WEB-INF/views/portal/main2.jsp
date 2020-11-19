<%@page import="kr.co.penta.dataeye.spring.web.session.SessionInfoSupport"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><spring:message code="app.title" text="DateEye" /></title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<c:import url="/deresources/commonlib/css" />
<c:import url="/deresources/commonlib/js" />
<link rel="stylesheet" href="<spring:url value="/assets/stylesheets/dataeye-tsearch.css"/>">

<style type="text/css">
.ui-autocomplete.ui-front {
	z-index:999;
}
.ui-autocomplete em {
	color : red;
	font-weight: bold;
}

#pop{
  width:300px; height:400px; background:#fff; color:#000;
  position:absolute; top:10px; left:100px; text-align:center;
  border:2px solid #000;
   }
</style>

<script language="javascript">

function openHelp() {
	 DE.ui.open.popup(
				"/dataeye_ebay/portal/view",
				["", ""],
				{
					viewname:'common/metacore/objectInfoHelpTab',
					objTypeId:"",
					objId:"",
					mode:'R'
				},
				null
			);
 	 
 }
</script>
</head>
<body class="hold-transition skin-black fixed">

<div class="bg" style="object-fit :cover;">
	<video class="movie" autoplay="true" loop="true" muted width="100%" style="margin-top:-150px; position:fixed; object-fit:cover;">
		<source src="../assets/images/test01.mp4" type="video/mp4" style="object-fit :cover;">
 	</video>
        <!--<iframe width="100%" height="800px;" src="https://www.youtube.com/embed/tgDjY26zqOA?autoplay=1" frameborder="0" allowfullscreen></iframe>-->
        <!-- <iframe style="width: 100%; height: 1070.44px; margin-left: 0px; margin-top: -220px;" frameborder="0" allowfullscreen="1" title="YouTube video player" width="854" height="480" src="https://www.youtube.com/embed/tgDjY26zqOA?hl=ko_KR&amp;version=3&amp;vq=large&amp;autoplay=1&amp;loop=1&amp;showinfo=1&amp;controls=0&amp;rel=1&amp;playlist=tgDjY26zqOA&amp;" type="application/x-shockwave-flash" width="854" height="480" allowscriptaccess="never" allowfullscreen="true" invokeurls="false" autostart="false" allownetworking="all"></iframe> -->
		<!-- <iframe id="background-player" style="width: 100%; height: 1070.44px; margin-left: 0px; margin-top: -127px;" frameborder="0" allowfullscreen="1" title="YouTube video player" width="854" height="480" src="https://www.youtube.com/embed/tgDjY26zqOA?hl=ko_KR&amp;version=3&amp;vq=large&amp;autoplay=1&amp;loop=1&amp;showinfo=1&amp;controls=0&amp;rel=1&amp;playlist=tgDjY26zqOA&amp;" type="application/x-shockwave-flash" width="854" height="480" allowscriptaccess="never" allowfullscreen="true" invokeurls="false" autostart="false" allownetworking="all"></iframe> -->
	<div class="dot" style="object-fit :cover;"></div>
</div>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper index-view-area" style="padding-top:0px; margin-left:0px;">
	<!-- <div class="row">
		<div class="col-sm-12 col-md-12" style="text-align: center;">
	    	<div class="navbar-form" role="search">
	        	<div class="input-group col-sm-6 col-md-6" style="margin-top: 200px;">
	        		<div style="width: 100%;border-color: #272424;border-style: solid;border-width: 1px;" class="input-group">
		            	<input type="text" class="form-control" placeholder="Search" name="q" value="" style="height: 50px;border-right-color: #ffffff;">
		                <div class="input-group-btn" style="width: 150px;">
		                	<button class="btn btn-default" type="submit" style="height: 50px; width: 50px;background-color: #ffffff;border-left-color: #ffffff;border-right-color: #ffffff;"><i class="glyphicon glyphicon-star"></i></button>
		                	<button class="btn btn-default" type="submit" style="height: 50px; width: 50px;background-color: #ffffff;border-left-color: #ffffff;border-right-color: #ffffff;"><i class="glyphicon glyphicon-triangle-bottom"></i></button>
		                	<button class="btn btn-default" type="submit" style="height: 50px; width: 50px;"><i class="glyphicon glyphicon-search"></i></button>
		        		</div>
		        	</div>
	        	</div>
			</div>
		</div>
	</div> -->
	<div class="index-alert-area" style="float:right; margin:20px 20px 0 0">
		<a href="javascript:goPage('page','DATAEYE1_MENU_7ce36c54-5ff1-4b80-a51b-1bc1404fdf14','portal/notice/boardList');" title="알림" style="position:relative;"><span style="font-size:18px; color:#fff;margin-right:5px;"><i class="glyphicon glyphicon-bell"></i></span><span style="position:absolute; background:#ff6600; color:#fff; font-size:8px; left:8px; top:-10px; border-radius:16px; width:16px; height:16px; padding-left:5px;display:none" id="notiCnt">1</span></a>
		<a href="javascript:openHelp();" title="도움말"><span style="font-size:18px; color:#fff;"><i class="glyphicon glyphicon-question-sign"></i></span></a>
	</div>
	<div class="row">
		<div class="col-sm-12 col-md-12 intex-title" style="text-align: center; margin-top: 130px;">
			<div class="navbar-form" role="search">
	        	<div class="input-group col-sm-6 col-md-6" style="padding-bottom:10px;">
	        		<span style="color: #fff;font-size: 42px;font-weight: bold;">eBay Korea BI Portal</span>
	        	</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-sm-12 col-md-12" style="text-align: center;">
	    	<div class="navbar-form" role="search">
	        	<div class="input-group col-sm-6 col-md-6">
	        		<div id="tSearchLayer" style="width: 100%;" class="input-group">
		            	<input id="tSearch" type="text" name="tSearch" class="form-control" placeholder="Search" value="" style="border:none; width: 100%; padding-left:10px; height: 50px;border-right-color: #ffffff; opacity:0.8;">		            		
		                <div id="myFavorite" class="input-group-btn" style="width: 50px;">		                	
		                	<button id="btnTSearch" class="btn main_serch" type="button" style="height: 50px; width: 50px;float: right; font-size:21px; cursor:pointer; opacity:0.8;"><i class="glyphicon glyphicon-search"></i></button>
		        		</div>
		        	</div>
	        	</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-sm-12 col-md-12" style="text-align: center;font: 15px bold;color: #D6607E;">
			<div class="navbar-form" role="search">
	        	<div class="input-group col-sm-6 col-md-6" style="color:#fff;">
	        		<span style="color: #fff600; font-weight:bold;;">HOT</span><span style="padding: 0 10px 0 30px;cursor: pointer;" onclick="goSearch('고객')">고객</span><span style="padding: 0 10px 0 10px;cursor: pointer;" onclick="goSearch('가입')">가입</span><span style="padding: 0 10px 0 10px;cursor: pointer;" onclick="goSearch('매출')">매출</span><span style="padding: 0 10px 0 10px;cursor: pointer;" onclick="goSearch('시스템')">시스템</span><span style="padding: 0 10px 0 10px;cursor: pointer;" onclick="goSearch('테이터')">테이터</span>
	        	</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12 col-md-12" style="margin-top:80px;">
			<div class="main_contents_Area">
				<li onclick="goPage('page','DATAEYE1_MENU_1d7bb275-1fde-44a1-8477-78ea14b11e5e','portal/report/reportList')">
					<div class="contents_IMG"><img src="../assets/images/main_img_01.png"></div>
					<div class="contents_TITLE">정형분석</div>
					<div class="contents_SubTITLE">정형분석에 대한 내용을 정리하셔서 넣어주세요.</div>
				</li>
				<!-- <li onclick="goPage('page','DATAEYE1_MENU_f98ab0cb-81b3-4f6a-83db-198b1a665e2d','portal/dataset/datasetList')">
					<div class="contents_IMG"><img src="../assets/images/main_img_02.png"></div>
					<div class="contents_TITLE">데이터셋</div>
					<div class="contents_SubTITLE">데이터셋에 대한 내용을 정리하셔서 넣어주세요.</div>
				</li> -->
				<li onclick="goPage('mymenu','DATAEYE1_MENU_1d7bb275-1fde-44a1-8477-78ea14b11e5e','portal/report/reportList')">
					<div class="contents_IMG"><img src="../assets/images/main_img_03.png"></div>
					<div class="contents_TITLE">My Menu</div>
					<div class="contents_SubTITLE">My Menu에 대한 내용을 정리하셔서 넣어주세요.</div>
				</li>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12 col-md-12" style="margin-top:80px;">
			<div class="main_bottom_contents_Area">
				<div class="back_Area"></div>
				<div class="contents_Area">
					<li>
						<div class="bottom_contents_title">최근 조회<br>정형분석
							<a href="#" onclick="goPage('page','DATAEYE1_MENU_3afa70a7-97b0-4f5f-9fab-3299e894bf91','portal/report/reportList')"><span>더보기</span></a>
						</div>
						<ul id="reportMainList">
							<font color="#fff">데이타 조회중입니다.</font>
						</ul>
					</li>
					<li>
						<div class="bottom_contents_title">최근조회<br>데이터셋
							<a href="#" onclick="goPage('page','DATAEYE1_MENU_f98ab0cb-81b3-4f6a-83db-198b1a665e2d','portal/dataset/datasetList')"><span>더보기</span></a>
						</div>
						<ul id="datasetMainList">
							<font color="#fff">데이타 조회중입니다.</font>
						</ul>
					</li>
				</div>
			</div>
			
		</div>
	</div>
  </div>
  <!-- /.content-wrapper -->

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Create the tabs -->
    <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
      <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
      <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
      <!-- Home tab content -->
      <div class="tab-pane" id="control-sidebar-home-tab">
        <h3 class="control-sidebar-heading">바로가기</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="<spring:url value="../admin/main"/>">
              <i class="menu-icon fa fa-birthday-cake bg-red"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Admin 홈</h4>
                <p>meta 관리</p>
              </div>
            </a>
          </li>
          <li>
            <a href="<spring:url value="../system/main"/>">
              <i class="menu-icon fa fa-user bg-yellow"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">System 홈</h4>
                <p>system 관리</p>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->
      </div>
      <!-- /.tab-pane -->

      <!-- Settings tab content -->
      <div class="tab-pane" id="control-sidebar-settings-tab">
        <form method="post">
          <h3 class="control-sidebar-heading">Admin Home</h3>

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Report panel usage
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Some information about this general settings option
            </p>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Allow mail redirect
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Other sets of options are available
            </p>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Expose author name in posts
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Allow the user to show his name in blog posts
            </p>
          </div>
          <!-- /.form-group -->

          <h3 class="control-sidebar-heading">Chat Settings</h3>

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Show me as online
              <input type="checkbox" class="pull-right" checked>
            </label>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Turn off notifications
              <input type="checkbox" class="pull-right">
            </label>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Delete chat history
              <a href="javascript:void(0)" class="text-red pull-right"><i class="fa fa-trash-o"></i></a>
            </label>
          </div>
          <!-- /.form-group -->
        </form>
      </div>
      <!-- /.tab-pane -->
    </div>
  </aside>
  <!-- /.control-sidebar -->
  <!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
<!--  레이어 팝업 -->
<div id="pop" style="display:none">
	<div style="height:370px; text-align:left;">
   	 	<div style="background:#000; color:#fff; font-size:12px; font-weight:bold; padding:5px 10px;text-align:left; ">알림</div>
   	 	<div style="width:100%; padding:20px;" id="notiDiv">     	 		
   	 	</div>   	 	
  </div>
  <div style="width:100%; background:#000; height:26px;">
  	<div style="color:#fff;    width: 50%; float: left; text-align: left; padding: 3px 0 0 10px;">
  		<input type="checkbox" id="dayNotView" />	
  		<span style="vertical-align: text-bottom; font-size: 0.9em;">오늘 하루 안보기</span></div>
    <div id="close" style="float:right; width:40%; text-align:right; color:#fff !important; padding:3px 6px 0 0; cursor:pointer; font-size:24px;">×</div>
  </div>
</div>


<form name="frm" method="post" action="main">
	<input type="hidden" name="pageDiv">
	<input type="hidden" name="searchType">
	<input type="hidden" name="keyword">
	<input type="hidden" name="menuId">
	<input type="hidden" name="viewId">
</form>
<!-- ./wrapper -->
<c:import url="/deresources/commonlib/js" />
<script type="text/javascript" src="<spring:url value="/assets/javascripts-lib/d3/d3.v3.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/assets/javascripts/dataeye.resizer.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/app/portal/dataeye.portal.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/assets/javascripts/dataeye.tsearch.js"/>"></script>
<script type="text/javascript">
$(document).ready(function() {

	var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.report.mainList"}
	$.ajax({ 
	    url:'jqgrid/list', //request 보낼 서버의 경로
	    type:'post', // 메소드(get, post, put 등)
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json' ,  
	    data:JSON.stringify(param), //보낼 데이터
	    success: function(data) {			    
				var reportListHtml = "";				
				$.each(data["rows"], function (index, item) {
					reportListHtml =  reportListHtml + "<li class=\"contents_list\"><a href=\"#\">[" + item["PATH_OBJ_NM"] + "]"+ item["OBJ_NM"] + "</a></li>";			
			    });
				
				$("#reportMainList").html(reportListHtml);

	    },
	    error: function(err) {
	        alert("error");
	    }
	});


	var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.dataset.mainList"}
	$.ajax({ 
	    url:'jqgrid/list', //request 보낼 서버의 경로
	    type:'post', // 메소드(get, post, put 등)
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json' ,  
	    data:JSON.stringify(param), //보낼 데이터
	    success: function(data) {			    
				var datasetListHtml = "";				
				$.each(data["rows"], function (index, item) {
					datasetListHtml =  datasetListHtml + "<li class=\"contents_list\"><a href=\"#\">[" + item["PATH_OBJ_NM"] + "]"+ item["OBJ_NM"] + "</a></li>";			
			    });
				
				$("#datasetMainList").html(datasetListHtml);

	    },
	    error: function(err) {
	        alert("error");
	    }
	});

		
	$("#mainMenu").html($("#sideMenu").html());
	$('#myFavorite').on('show.bs.dropdown', function () {
		$("#myFavoriteLayer").css({width: $("#tSearchLayer").css("width")});
	});
	
	
	$('#myFavorite').on('hide.bs.dropdown', function(e) {
	    if ( $(this).hasClass('dontClose') ){
	        e.preventDefault();
	    }
	    $(this).removeClass('dontClose');
	});
	
	$("a[detag=toplink]", $(".dropdown")).on("click", function(e){
		debugger;
		
		var viewId=$(e.currentTarget).attr("viewId");
		var data = $(e.currentTarget).attr("viewParam");
		
		if (data !== undefined) {
  	  		data = JSON.stringify(JSON.parse('{"' + decodeURI(data).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g,'":"') + '"}'));
  	  	}
  	  	var post = {
    	  		"viewId": viewId,
    	  		"data": data
    	  	};
  	  	
  	  	
 	  	$(".content-wrapper").load("../board/list", post, function(response, status, xhr) {
  	  		DE.content.setHeader($(e.currentTarget));
  	  		$(window).trigger("resize");
  	  	});
		
		
	})
	
	$('#myFavoriteLayer').on('click', function(){
	    $(this).closest('#myFavorite').addClass('dontClose');
	});
	
	$("#tsearchObjTypeGroup").on('ifChanged', "input[name=tsearchObjType]", function (event) {
	    if ($(this).is(":checked")) {
	    	DE.tsearch.renderingTreemap($(this));
	    }
	});
	
//	DE.tsearch.findDashboardObjTypes();
	$("input[name=tsearchObjType]", "#tsearchObjTypeGroup").iCheck({
	    checkboxClass: 'icheckbox_minimal-red',
	    radioClass: 'iradio_minimal-red'
	});
	$("input[name=tsearchObjType]:checked", "#tsearchObjTypeGroup").trigger("ifChanged");
	
	var getSearchType = function() {
		var type = $("ul#tsearch-header li.active a").attr("objTypeId");
		if (type === undefined) {
			type = "ALL";
		}
		return type;
	}

	//top 통합검색 - start
	$("#tSearch").on("keypress", function(e){
		if (e.which == 13) {
			$("#btnTSearch").trigger("click");
		    return false;
		}
	});
	DE.tsearch.autokeyword($("#tSearch"), $("#btnTSearch"));
	
	//통합검색 - end
	
	$("#btnTSearch", "#tSearchLayer").on("click", function(e) {
		var keyword = $("#tSearch").val().trim();
		if ("" === keyword) {
			DE.box.alert("검색어를 입력하세요.", function(){
				setTimeout(function() {
					$("#tSearch").focus();
				}, 500);
			});
			return;
		}

		frm.pageDiv.value = "search";	
		frm.searchType.value = getSearchType();
		frm.keyword.value = keyword;
		frm.submit();
	//	DE.tsearch.searchMain(getSearchType(), keyword);
	});
	
	
});
//main page check용 dummy function
var de_main_check = function() {
	return true;
}

function goSearch(keyword) {

	frm.pageDiv.value = "search";	
	frm.searchType.value = "ALL";
	frm.keyword.value = keyword;
	frm.submit();
}

function openNotice() {
	$('#pop').show();
}




function goPage(pageDiv,menuId,viewId) {

	frm.pageDiv.value = pageDiv;		
	frm.menuId.value = menuId;
	frm.viewId.value = viewId;
	frm.submit();
}
	function  tabProc(div) {			 
		if(div=="1") {			
			$("#tab1").addClass("on");
			$("#tab2").removeClass("on");

			$("#tui1").addClass("sidebar-menu");
			$("#tui2").removeClass("sidebar-menu");
			
			$("#mainMenu").show();
			$("#myMenu").hide();
			
		} else {
			$("#tab1").removeClass("on");
			$("#tab2").addClass("on");			

			$("#tui1").removeClass("sidebar-menu");
			$("#tui2").addClass("sidebar-menu");			
			
			$("#mainMenu").hide();
			$("#myMenu").show();
			
		}
	}

function noticePop(){
	var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.notice.getMainBoardList"}
	$.ajax({ 
	    url:'jqgrid/list', //request 보낼 서버의 경로
	    type:'post', // 메소드(get, post, put 등)
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json' ,  
	    data:JSON.stringify(param), //보낼 데이터
	    success: function(data) {

	    	if(data["rows"]!="") { 
		    	var notiHtml = "";		
		    	notiHtml =  notiHtml + "<div style=\"font-weight:bold; font-size:14px; color:#333;\">" + data["rows"][0]["NTC_NM"] + "</div>";
		    	notiHtml =  notiHtml + "<div class=\"Grid_Area_main\">";
		    	notiHtml =  notiHtml + "	<table class=\"table_main\">";
		    	notiHtml =  notiHtml + "		<tbody>";
		    	notiHtml =  notiHtml + "			<tr>";
		    	notiHtml =  notiHtml + "				<th>작성자</th>";
		    	notiHtml =  notiHtml + "				<td>" + data["rows"][0]["CRETR_ID"] + "</td>";
		    	notiHtml =  notiHtml + "			</tr>";
		    	notiHtml =  notiHtml + "			<tr>";
		    	notiHtml =  notiHtml + "				<th>기간</th>";
		    	notiHtml =  notiHtml + "				<td>" + data["rows"][0]["NTC_ST_DATE"] + " ~ " + data["rows"][0]["NTC_ED_DATE"] + "</td>";
		    	notiHtml =  notiHtml + "			</tr>";
		    	notiHtml =  notiHtml + "			<tr>";
		    	notiHtml =  notiHtml + "				<td colspan=\"2\" class=\"pop_contents\">";
		    	notiHtml =  notiHtml + "				<span style=\"float:left; width:100%; height:160px; overflow:auto;\">";	    	
		    	notiHtml =  notiHtml + 							data["rows"][0]["NTC_SBST"];	    	
		    	notiHtml =  notiHtml + "				</span></td>";
		    	notiHtml =  notiHtml + "			</tr>";
		    	notiHtml =  notiHtml + "			<tr>";
		    	notiHtml =  notiHtml + "				<th>첨부파일</th>";
		    	if(data["rows"][0]["FILE_NM"]!=null) {
		    		notiHtml =  notiHtml + "				<td><a href=test/download?file_id=" + data["rows"][0]["FILE_ID"] + ">" + data["rows"][0]["FILE_NM"] + "</a></td>";
			   } else {
				   notiHtml =  notiHtml + "				    <td></td>";
			  }
		    	
		    	
		    	notiHtml =  notiHtml + "			</tr>";
		    	notiHtml =  notiHtml + "		</tbody>";
		    	notiHtml =  notiHtml + "	</table>";
		    	notiHtml =  notiHtml + "</div>";
		    	$("#notiDiv").html(notiHtml);
		    	 $('#pop').show();
		    	 $('#notiCnt').show();
		    	 
		      } 

		    },
		    error: function(err) {
		        alert("error");
		    }
	});

 	$('#close').click(function() {
	   $('#pop').hide();
	});

	$("#dayNotView").click(function(){
		setCookie("day_not_view", true, 1);
		$('#pop').hide();
	});
	

}
let dayNotView = getCookie("day_not_view");
if(!dayNotView){
	noticePop();
}
</script>
</body>
</html>
