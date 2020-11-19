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
<link rel="stylesheet" href="<spring:url value="/assets/stylesheets/dataeye-tsearch.css"/>">
<style type="text/css">
.ui-autocomplete em {
	color : red;
	font-weight: bold;
}
</style>
<script language="javascript">

function openHelp() {
	 DE.ui.open.popup(
			 DE.contextPath+"/portal/view",
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
function openPwdChange() {
	DE.ui.open.popup(
			    DE.contextPath+"/portal/view",
			    ["view", "myPwdMgr"],
	            {
					viewname:'portal/mypage/myPwdMgr',
					objTypeId:"",
					objId:"",
					mode:'U'
				},
				"width=830, height=300, toolbar=no, menubar=no"
	);

 }
</script>
</head>
<body class="hold-transition skin-black fixed">
<div class="wrapper">

  <header class="main-header">

    <!-- Logo 
    <a href="<spring:url value="../portal/main"/>" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels 
      <span class="logo-mini"><b>D</b>-Eye</span>
      <!-- logo for regular state and mobile devices 
      <span class="logo-lg"><b>DataEye</b>Portal</span> 
    </a> -->

    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
    	<div class="top_logo" style="position:relative; z-index:150;">
    	<a href="<c:url value='/portal/main2' />"><img src="../assets/images/dataeye_logo.png"></a>
    	</div>
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button" style="position:relative; z-index:150">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <div class="main_menu_Area">      	
      	<div class="col-sm-12 col-md-12" style="text-align: center; position:absolute;">
	    	<div class="navbar-form" role="search" style="margin-top:10px;">
	        	<div class="input-group col-sm-6 col-md-6" style="width:500px;">
	        		<div id="tSearchLayer" style="width: 100%;" class="input-group">
		            	<input id="tSearch" type="text" name="tSearch" class="form-control" placeholder="Search" value="" style="border-right:none; border:2px solid #666; width: 100%; padding-left:10px; height: 35px;">	
		                <div id="myFavorite" class="input-group-btn" style="width: 50px;">
		                	<button id="btnTSearch" class="btn top_serch" type="button" style="height: 35px; width: 50px;float: right; font-size:16px; cursor:pointer;"><i class="glyphicon glyphicon-search"></i></button>
		        		</div>
		        	</div>
	        	</div>
			</div>
		</div>
      </div>
      
 
      <div class="btn_area">
          	<ul>
          	
          		<li class="ico"><a href="javascript:openHelp()" title="도움말"><i class="fa fa-info-circle" aria-hidden="true"  style="width:50px;"></i> <span style="font-size:11px; letter-spacing:-1; width:60px;">도움말</span></a></li>
          		
          		<li class="ico"><a href="javascript:goLink('portal/notice/boardList', 'DATAEYE1_MENU_7ce36c54-5ff1-4b80-a51b-1bc1404fdf14');" title="공지사항"><i class="fa fa-fw fa-bell-o" style="width:50px;"></i><span style="font-size:11px; letter-spacing:-1; width:60px;">공지사항</span></a></li>
          		<li class="ico"><a href="javascript:goLink('portal/faq/boardList', 'DATAEYE1_MENU_d596ebad-1410-4777-9b4a-09725495dbf6');" title="FAQ"><i class="fa fa-fw fa-question-circle-o" style="width:50px;"></i><span style="font-size:11px; letter-spacing:-1; width:60px;">FAQ</span></a></li>          		
          		<c:if test="${sessionScope[SessionInfoSupport.SESSION_USERINFO_NAME]['admin']}">
          			<li class="ico"><a href="<spring:url value="../admin/main"/>" title="Admin 홈"><i class="fa fa-fw fa-cogs"  style="width:50px;"></i><span style="font-size:11px; letter-spacing:-1; width:60px;">ADMIN</span></a></li>
          		</c:if>
          		<!--<c:if test="${sessionScope[SessionInfoSupport.SESSION_USERINFO_NAME]['system']}">
          			<li class="ico"><a href="<spring:url value="../system/main"/>" title="System 홈"><i class="fa fa-fw fa-cogs" style="width:50px;"></i><span style="font-size:11px; letter-spacing:-1; width:60px;">SYSTEM</span></a></li>
          		</c:if>
          		<li class="ico"><a href="<c:url value="/login?evt=logout"></c:url>"><i class="fa fa-power-off" aria-hidden="true" title="로그아웃" style="width:50px;"></i><span style="font-size:11px; letter-spacing:-1; width:60px;">로그아웃</span></a></li>-->
          	</ul>
          </div>
     

    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar" style="margin-top:55px; z-index:1030;">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
	      <!-- Sidebar user panel -->
      <!-- <div class="user-panel">
        <div class="pull-left image">
          <img src="<spring:url value="/assets/images/user2-160x160.jpg"/>" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p><c:out value="${sessionScope[SessionInfoSupport.SESSION_USERINFO_NAME]['userNm']}"/></p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div> -->
      <!-- search form 
      <form action="#" method="get" class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="Search...">
              <span class="input-group-btn">
                <button type="button" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
        </div>
      </form> -->
      <div class="input-group">
      	 <div class="user_Area">
      		<!-- <li class="user"><i class="fa fa-user" style="border-left:none; padding-left:0px;"></i> <span class="name"><c:out value="${sessionScope[SessionInfoSupport.SESSION_USERINFO_NAME]['userNm']}"/></span> 님 환영합니다.</li>
      		<a href="<c:url value="javascript:openPwdChange();"></c:url>"><i class="glyphicon glyphicon-cog" style="margin-top:7px;" aria-hidden="true" title="비밀번호변경"></i></a>
      		<a href="<c:url value="/login?evt=logout"></c:url>"><i class="fa fa-power-off" style="margin-top:7px;margin-left:7px;"  aria-hidden="true" title="로그아웃"></i></a></li> -->
      		
      		<li class="user"><i class="fa fa-user" style="border-left:none; padding-left:0px;"></i> <span class="name"><c:out value="${sessionScope[SessionInfoSupport.SESSION_USERINFO_NAME]['userNm']}"/></span> 님 환영합니다.</li>
      		<li><a href="<c:url value="/login?evt=logout"></c:url>"><i class="fa fa-power-off" aria-hidden="true" title=""></i></a></li>
      	 </div>
      	 <li class="header">
	      		<ul style="margin-bottom:0px;">
	      			<a href="javascript:tabProc('1')"><li class="on" style="width:115px" id="tab1"><i class="fa fa-television" aria-hidden="true"></i>&nbsp; Menu</li></a>
	      			<a href="javascript:tabProc('2')"><li style="width:115px"  id="tab2"><i class="fa fa-user" aria-hidden="true"></i>&nbsp; My Menu</li></a>	      			
	      		</ul>
	     </li>
	  </div>
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      		<div id="mainMenu"> 
      		<ul id="tui1" class="sidebar-menu">
      		</div>
      		
      		<div id="myMenu" style="display:none">
      			<!-- <ul id="tui2" >
				<li detag-menuid="12"><a href="javascript:goLink('portal/report/reportList');"><i class="glyphicon glyphicon-list-alt" aria-hidden="true"></i>&nbsp;대시보드</a></li>
				  <li detag-menuid="12"><a href="javascript:goLink('portal/report/bookmarkReportList');"><i class="glyphicon glyphicon-list-alt" aria-hidden="true"></i>&nbsp;즐겨찾기</a></li>
				  <li detag-menuid="13"><a href="javascript:goLink('portal/mypage/myTaskList');"><i class="glyphicon glyphicon-list-alt" aria-hidden="true"></i>&nbsp;분석과제</a></li>
				  <li detag-menuid="14"><a href="javascript:goLink('portal/report/reportList');"><i class="glyphicon glyphicon-list-alt" aria-hidden="true"></i>&nbsp;최근조회 정형분석</a></li>				  
				  <li detag-menuid="14"><a href="javascript:goLink('portal/dataset/datasetList');"><i class="glyphicon glyphicon-list-alt" aria-hidden="true"></i>&nbsp;최근조회 데이터셋</a></li>
				 </ul> -->
				 <ul id="tui2" >
      			  <li detag-menuid="12"><a href="javascript:goLink('portal/report/bookmarkReportList');"><i class="glyphicon glyphicon-list-alt" aria-hidden="true"></i>&nbsp;즐겨찾기</a></li>
				  <li detag-menuid="14"><a href="javascript:goLink('portal/mypage/myTaskList');"><i class="glyphicon glyphicon-list-alt" aria-hidden="true"></i>&nbsp;비정형 분석</a></li>				  
				  <li detag-menuid="14"><a href="javascript:goLink('mstr/latest/reportList');"><i class="glyphicon glyphicon-list-alt" aria-hidden="true"></i>&nbsp;최근 조회 보고서</a></li>
				 </ul>
      		</div>      		
    </section>
    <!-- /.sidebar -->
  </aside>
	
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
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
</div>
<!-- ./wrapper -->
<input type="hidden" name="reqViewId" value="${param.viewId}" />
<input type="hidden" name="reqMenuId" value="${param.menuId}" />
<c:import url="/deresources/commonlib/js" />
<script type="text/javascript" src="<spring:url value="/assets/javascripts-lib/d3/d3.v3.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/assets/javascripts/dataeye.resizer.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/app/portal/dataeye.portal.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/assets/javascripts/dataeye.tsearch.js"/>"></script>
<script type="text/javascript">
$(document).ready(function() {

	if("${param.pageDiv}"=="search") {
		DE.tsearch.searchMain("${param.searchType}","${param.keyword}");
	} else if("${param.pageDiv}"=="page") {

		var menuId = "${param.menuId}";
		var viewId = "${param.viewId}";
		
		var post = {
			"viewId": viewId,
			"menuId": menuId
		};
		// for Dev refrash F5
   	  	var urlString = location.href;
   	  	if(urlString.indexOf("#") > 0){
	  		var urlMenuInfo = urlString.split("#")[1],
	  			menuInfoArr = urlMenuInfo.split("%");
	  		if(urlMenuInfo){
	  			post.viewId = menuInfoArr[0];
	  			post.menuId = menuInfoArr[1];
	  	  	}
  	  	}
		
		$(".content-wrapper").load(DE.contextPath+"/portal/view", post, function(response, status, xhr) {
			$(document).off("autoResize");
			if (status === "error") {	
				
				$(".content-wrapper").html(response);
			} else {
				
		  		//DE.content.setHeader($(e.currentTarget));
		  		$(window).trigger("resize");
			}
		});


	} else if("${param.pageDiv}"=="mymenu") {
		tabProc(2);
	}
	
	var getSearchType = function() {
		var type = $("ul#tsearch-header li.active a").attr("objTypeId");
		if (type === undefined) {
			type = "ALL";
		}
		return type;
	}

	$("#tSearch").on("keypress", function(e){
		if (e.which == 13) {
			$("#btnTSearch").trigger("click");
		    return false;
		}
	});
	DE.tsearch.autokeyword($("#tSearch"), $("#btnTSearch"));

	$("#btnTSearch").on("click", function(e) {
		var keyword = $("#tSearch").val().trim();
		if ("" === keyword) {
			DE.box.aler;t("검색어를 입력하세요.", function(){
				setTimeout(function() {
					$("#tSearch").focus();
				}, 500);
			});
			return;
		}
		DE.tsearch.searchMain(getSearchType(), keyword);
	});
	//top 통합검색 - end
	
	$("#tSearch", "#tSearchLayer").on("keypress", function(e){
		if (e.which == 13) {
			$("#btnTSearch", "#tSearchLayer").trigger("click");
		    return false;
		}
	});
	
	chkMstrMaintain();
	$("#tSearch", "#tSearchLayer").on("keypress", function(e){
		if (e.which == 13) {
			$("#btnTSearch", "#tSearchLayer").trigger("click");
		    return false;
		}
	});
	
	
});
//main page check용 dummy function
var de_main_check = function() {
	return true;
}
</script>
<script language="javascript">

	

	function goLink(viewId, menuId) {
		var post = {
				"viewId": viewId,
				"menuId": menuId
			};
		contentLoad(post);
			
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

	function newOpen(linkStr){
		DE.ui.open.popup(
				"/dataeye/portal/view",
				["", ""],
				{
					viewname:linkStr,
					objTypeId:"",
					objId:"",
					mode:'R'
				},
				null
			);
	}

	var intervalId = "";
	function chkMstrMaintain(){
		clearInterval(intervalId);

		intervalId = setInterval(callMstrMaintain, 60*1000*5);
	}

	function callMstrMaintain(){
		$('#mstrLogin').attr('target','ifrm').submit();
	}
</script>
<form id="mstrLogin" action="http://192.168.100.73:8080/MicroStrategy/servlet/mstrWeb?evt=3010&src=mstrWeb.3010&ru=1&share=1&Server=WIN-4FNG27AEFGQ&Port=0&Project=eBay" method="post">
</form>
<iframe name="ifrm" width="0" height="0" frameborder="0" ></iframe>
</body>
</html>
