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
<script language="javascript">
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


    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
    	<div class="top_logo" style="position:relative; z-index:150;">
    	<a href="/dataeye/portal/main2"><img src="../assets/images/dataeye_logo.png"></a>
    	</div>
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <div class="btn_area">
          	<ul>
          		<!-- <li class="ico"><a href="javascript:void(0);" viewId="portal/credit/board/boardList" viewParam="KIND_CD=notice&GO_PAGE=1" detag="toplink"><i class="fa fa-fw fa-bell-o" title="공지사항"></i><br>NOTICE</a></li>
          		<li class="ico"><a href="javascript:void(0);" viewId="portal/credit/board/boardList" viewParam="KIND_CD=qa&GO_PAGE=1" detag="toplink" ><i class="fa fa-fw fa-question-circle-o" title="Q&A"></i><br>Q&A</a> </li>
          		<li class="ico"><a href="javascript:void(0);" viewId="portal/credit/board/boardList" viewParam="KIND_CD=faq&GO_PAGE=1" detag="toplink" ><i class="fa fa-fw fa-question-circle-o" title="FAQ"></i><br>FAQ</a> </li> -->
          		<li class="ico"><a href="<spring:url value="../portal/main2"/>" ><i class="fa fa-fw fa-question-circle-o" title="Portal 홈"></i><br>Portal 홈</a> </li>
          		<!-- <li class="ico"><a href="<c:url value="/login?evt=logout"></c:url>"><i class="fa fa-power-off" aria-hidden="true" title="로그아웃"></i></a></li> -->
          	</ul>
      </div>
    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar" style="margin-top:55px; z-index:1030;">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <div class="input-group">
      	<div class="user_Area">
      		<li class="user"><i class="fa fa-user" style="border-left:none; padding-left:0px;"></i> <span class="name"><c:out value="${sessionScope[SessionInfoSupport.SESSION_USERINFO_NAME]['userNm']}"/></span> 님 환영합니다.</li>
      		<!-- <a href="<c:url value="javascript:openPwdChange();"></c:url>"><i class="glyphicon glyphicon-cog" style="margin-top:7px;" aria-hidden="true" title="비밀번호변경"></i></a> -->
      		<a href="<c:url value="/login?evt=logout"></c:url>"><i class="fa fa-power-off" style="margin-top:7px;margin-left:7px;"  aria-hidden="true" title="로그아웃"></i></a></li>
      	 </div>
      </div>
      <ul class="sidebar-menu">
        <!-- <li class="header">MAIN NAVIGATION</li> -->
        <li class="active treeview">
          <a href="#">
            <i class="fa fa-dashboard"></i> <span>메타 관리</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li>
              <a href="#"><i class="fa fa-circle-o"></i> 객체유형 관리
                <span class="pull-right-container">
                  <i class="fa fa-angle-left pull-right"></i>
                </span>
              </a>
              <ul class="treeview-menu">
                <li class="active"><a href="javascript:void(0);" viewId="admin/atr" detag="link"><i class="fa fa-circle-o"></i> 속성 관리</a></li>
                <li><a href="javascript:void(0);" viewId="admin/objType" detag="link"><i class="fa fa-circle-o"></i> 유형 관리</a></li>
              </ul>
            </li>
            <li><a href="javascript:void(0);" viewId="admin/obj" detag="link"><i class="fa fa-circle-o"></i> 객체 관리</a></li>
            <!-- <li><a href="javascript:void(0);" viewId="admin/objRelType" detag="link"><i class="fa fa-circle-o"></i> 객체관계 관리</a></li>-->
            <li>
              <a href="javascript:void(0);" viewId="admin/code" detag="link"><i class="fa fa-circle-o"></i> 코드 관리</a>
            </li>
            <li>
              <a href="#"><i class="fa fa-circle-o"></i> 객체 UI
                <span class="pull-right-container">
                  <i class="fa fa-angle-left pull-right"></i>
                </span>
              </a>
              <ul class="treeview-menu">
                <li><a href="javascript:void(0);" viewId="admin/objUI" detag="link"><i class="fa fa-circle-o"></i> UI 관리</a></li>
                <li><a href="javascript:void(0);" viewId="admin/objUIMapping" detag="link"><i class="fa fa-circle-o"></i> UI 매핑</a></li>
              </ul>
            </li>
          </ul>
        </li>
         <li class="treeview">
          <a href="#">
            <i class="fa fa-dashboard"></i> <span>포털관리</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
	       		<li class="active"><a href="javascript:void(0);" viewId="system/user" detag="link"><i class="fa fa-circle-o"></i> 사용자관리</a></li>
	       		<li><a href="javascript:void(0);" viewId="system/menuMgr" detag="link"><i class="fa fa-circle-o"></i> 메뉴관리</a></li>
	       		<li><a href="javascript:void(0);" viewId="system/roleGrpMgr" detag="link"><i class="fa fa-circle-o"></i> ROLE  그룹관리 </a></li>
	       		<li><a href="javascript:void(0);" viewId="system/authGrpMgr" detag="link"><i class="fa fa-circle-o"></i> 권한 그룹 관리</a></li>
	       		<li><a href="javascript:void(0);" viewId="system/menuAuthMgr" detag="link"><i class="fa fa-circle-o"></i> 메뉴 권한 관리</a></li>
	       		<li><a href="javascript:void(0);" viewId="system/integrationAuthMgr" detag="link"><i class="fa fa-circle-o"></i> 통합 권한 관리</a></li>
	       		<li> <a href="#"><i class="fa fa-circle-o"></i> 포탈 활용 현황
                <span class="pull-right-container">
                  <i class="fa fa-angle-left pull-right"></i>
                </span>
              </a>      
              <ul class="treeview-menu">
                <li><a href="javascript:void(0);" viewId="system/logUser" detag="link"><i class="fa fa-circle-o"></i> 접속 활용 현황</a></li>
                <li><a href="javascript:void(0);" viewId="system/logMenu" detag="link"><i class="fa fa-circle-o"></i> 메뉴 활용 현황</a></li>
                <li><a href="javascript:void(0);" viewId="system/logObj" detag="link"><i class="fa fa-circle-o"></i> 객체 활용 현황</a></li>
              </ul>
            </li>
	       	</ul>
        </li>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper"></div>
  <!-- /.content-wrapper -->

  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 3.0.1
    </div>
    <strong>Copyright &copy; 2014-2020 <a href="http://www.penta.co.kr">Penta Systems Technology Inc</a>.</strong> All rights reserved.
  </footer>

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
            <a href="<spring:url value="../portal/main"/>">
              <i class="menu-icon fa fa-birthday-cake bg-red"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">포탈 홈</h4>
                <p>Portal</p>
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
<c:import url="/deresources/commonlib/js" />
<script src="<spring:url value="/assets/javascripts/dataeye.resizer.js"/>"></script>
<script src="<spring:url value="/app/admin/dataeye.admin.js"/>"></script>
<script type="text/javascript">
//main page check용 dummy function
var de_main_check = function() {
	return true;
}

$(document).ready(function() {
	chkMstrMaintain();
});

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
