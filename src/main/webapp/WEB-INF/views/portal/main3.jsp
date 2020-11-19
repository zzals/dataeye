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

</style>
</head>
<body class="hold-transition skin-blue sidebar-mini fixed">
<div class="wrapper">

  <header class="main-header">

    <!-- Logo -->
    <a href="<spring:url value="../portal/main"/>" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>D</b>-Eye</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>DataEye</b>Portal</span> 
    </a>

    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <div id="tSearchHeaderLayer" class="col-sm-6 col-md-6" style="display: none;">
            <form class="navbar-form" role="search">
                <div class="input-group col-sm-12 col-md-12">
                    <input id="tSearch" name="tSearch" type="text" class="form-control" placeholder="Search" value="">
                    <div class="input-group-btn">
                        <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                    </div>
                </div>
            </form>
	  </div>
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <!-- Messages: style can be found in dropdown.less-->
          <li class="dropdown messages-menu">
           <!--  <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <i class="fa fa-envelope-o"></i>
              <span class="label label-success">4</span>
            </a> -->
            <ul class="dropdown-menu">
              <li class="header">You have 4 messages</li>
              <li>
                <!-- inner menu: contains the actual data -->
                <ul class="menu">
                  <li><!-- start message -->
                    <a href="#">
                      <div class="pull-left">
                        <img src="<spring:url value="/assets/images/user2-160x160.jpg"/>" class="img-circle" alt="User Image">
                      </div>
                      <h4>
                        Support Team
                        <small><i class="fa fa-clock-o"></i> 5 mins</small>
                      </h4>
                      <p>Why not buy a new awesome theme?</p>
                    </a>
                  </li>
                  <!-- end message -->
                  <li>
                    <a href="#">
                      <div class="pull-left">
                        <img src="<spring:url value="/assets/images/user3-128x128.jpg"/>" class="img-circle" alt="User Image">
                      </div>
                      <h4>
                        AdminLTE Design Team
                        <small><i class="fa fa-clock-o"></i> 2 hours</small>
                      </h4>
                      <p>Why not buy a new awesome theme?</p>
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <div class="pull-left">
                        <img src="<spring:url value="/assets/images/user4-128x128.jpg"/>" class="img-circle" alt="User Image">
                      </div>
                      <h4>
                        Developers
                        <small><i class="fa fa-clock-o"></i> Today</small>
                      </h4>
                      <p>Why not buy a new awesome theme?</p>
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <div class="pull-left">
                        <img src="<spring:url value="/assets/images/user3-128x128.jpg"/>" class="img-circle" alt="User Image">
                      </div>
                      <h4>
                        Sales Department
                        <small><i class="fa fa-clock-o"></i> Yesterday</small>
                      </h4>
                      <p>Why not buy a new awesome theme?</p>
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <div class="pull-left">
                        <img src="<spring:url value="/assets/images/user4-128x128.jpg"/>" class="img-circle" alt="User Image">
                      </div>
                      <h4>
                        Reviewers
                        <small><i class="fa fa-clock-o"></i> 2 days</small>
                      </h4>
                      <p>Why not buy a new awesome theme?</p>
                    </a>
                  </li>
                </ul>
              </li>
              <li class="footer"><a href="#">See All Messages</a></li>
            </ul>
          </li>
          <!-- Notifications: style can be found in dropdown.less -->
          <li class="dropdown notifications-menu">
            <!-- <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <i class="fa fa-bell-o"></i>
              <span class="label label-warning">10</span>
            </a> -->
            <ul class="dropdown-menu">
              <li class="header">You have 10 notifications</li>
              <li>
                <!-- inner menu: contains the actual data -->
                <ul class="menu">
                  <li>
                    <a href="#">
                      <i class="fa fa-users text-aqua"></i> 5 new members joined today
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-warning text-yellow"></i> Very long description here that may not fit into the
                      page and may cause design problems
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-users text-red"></i> 5 new members joined
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-shopping-cart text-green"></i> 25 sales made
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-user text-red"></i> You changed your userNm
                    </a>
                  </li>
                </ul>
              </li>
              <li class="footer"><a href="#">View all</a></li>
            </ul>
          </li>
          <!-- Tasks: style can be found in dropdown.less -->
          <li class="dropdown tasks-menu">
            <!-- <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <i class="fa fa-flag-o"></i>
              <span class="label label-danger">9</span>
            </a> -->
            <ul class="dropdown-menu">
              <li class="header">You have 9 tasks</li>
              <li>
                <!-- inner menu: contains the actual data -->
                <ul class="menu">
                  <li><!-- Task item -->
                    <a href="#">
                      <h3>
                        Design some buttons
                        <small class="pull-right">20%</small>
                      </h3>
                      <div class="progress xs">
                        <div class="progress-bar progress-bar-aqua" style="width: 20%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                          <span class="sr-only">20% Complete</span>
                        </div>
                      </div>
                    </a>
                  </li>
                  <!-- end task item -->
                  <li><!-- Task item -->
                    <a href="#">
                      <h3>
                        Create a nice theme
                        <small class="pull-right">40%</small>
                      </h3>
                      <div class="progress xs">
                        <div class="progress-bar progress-bar-green" style="width: 40%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                          <span class="sr-only">40% Complete</span>
                        </div>
                      </div>
                    </a>
                  </li>
                  <!-- end task item -->
                  <li><!-- Task item -->
                    <a href="#">
                      <h3>
                        Some task I need to do
                        <small class="pull-right">60%</small>
                      </h3>
                      <div class="progress xs">
                        <div class="progress-bar progress-bar-red" style="width: 60%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                          <span class="sr-only">60% Complete</span>
                        </div>
                      </div>
                    </a>
                  </li>
                  <!-- end task item -->
                  <li><!-- Task item -->
                    <a href="#">
                      <h3>
                        Make beautiful transitions
                        <small class="pull-right">80%</small>
                      </h3>
                      <div class="progress xs">
                        <div class="progress-bar progress-bar-yellow" style="width: 80%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                          <span class="sr-only">80% Complete</span>
                        </div>
                      </div>
                    </a>
                  </li>
                  <!-- end task item -->
                </ul>
              </li>
              <li class="footer">
                <a href="#">View all tasks</a>
              </li>
            </ul>
          </li>
          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <!-- <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="<spring:url value="/assets/images/user2-160x160.jpg"/>" class="user-image" alt="User Image">
              <span class="hidden-xs"><c:out value="${sessionScope['dataeye-session']['userNm']}"/></span>
            </a> -->
            <a href="<c:url value="/login?evt=logout"></c:url>" class="logout"><i class="fa fa-sign-out" aria-hidden="true"></i>로그아웃</a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
                <img src="<spring:url value="/assets/images/user2-160x160.jpg"/>" class="img-circle" alt="User Image">

                <p>
                  Alexander Pierce - Web Developer
                  <small>Member since Nov. 2012</small>
                </p>
              </li>
              <!-- Menu Body -->
              <li class="user-body">
                <div class="row">
                  <div class="col-xs-4 text-center">
                    <a href="#">Followers</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Sales</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Friends</a>
                  </div>
                </div>
                <!-- /.row -->
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <a href="#" class="btn btn-default btn-flat">Profile</a>
                </div>
                <div class="pull-right">
                  <a href="#" class="btn btn-default btn-flat">Sign out</a>
                </div>
              </li>
            </ul>
          </li>
          <!-- Control Sidebar Toggle Button -->
          <li>
            <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
          </li>
        </ul>
      </div>

    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <!-- <div class="user-panel">
        <div class="pull-left image">
          <img src="<spring:url value="/assets/images/user2-160x160.jpg"/>" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p><c:out value="${sessionScope['dataeye-session']['userNm']}"/></p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div> -->
      <!-- search form -->
      <form action="#" method="get" class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="Search...">
              <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
        </div>
      </form>
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <!-- <ul class="sidebar-menu">
        <li class="header">MAIN NAVIGATION</li>
      </ul> -->
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

	<div class="row">
		<div class="col-sm-12 col-md-12" style="text-align: center;font: 15px bold;color: #D6607E; margin-top: 150px;">
			<div class="navbar-form" role="search">
	        	<div class="input-group col-sm-6 col-md-6">
	        		<span style="color: blueviolet;font-size: 30px;font-weight: bold;">Big Data Flatform</span>
	        	</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-sm-12 col-md-12" style="text-align: center;">
	    	<div class="navbar-form" role="search">
	        	<div class="input-group col-sm-6 col-md-6">
	        		<div id="tSearchLayer" style="width: 100%;border-color: #272424;border-style: solid;border-width: 1px;" class="input-group">
		            	<input id="tSearch" type="text" name="tSearch" class="form-control" placeholder="Search" value="" style="height: 50px;border-right-color: #ffffff;">
		            		
		                <div id="myFavorite" class="input-group-btn" style="width: 150px;">
		                	<button class="btn btn-default" type="submit" style="height: 50px; width: 50px;background-color: #ffffff;border-left-color: #ffffff;border-right-color: #ffffff;"><i class="glyphicon glyphicon-star"></i></button>
		                	<button class="btn btn-default" type="submit" style="height: 50px; width: 50px;background-color: #ffffff;border-left-color: #ffffff;border-right-color: #ffffff;" data-toggle="dropdown" aria-expanded="false"><i class="glyphicon glyphicon-triangle-bottom"></i></button>
		                	<div id="myFavoriteLayer" class="dropdown-menu dropdown-menu-right" role="menu" style="border-color: none;">
                                <ul class="nav nav-tabs">
							      <li class="active"><a href="#home" data-toggle="tab">최근 검색어</a></li>
							      <li><a href="#profile" data-toggle="tab">나의 검색어</a></li>
							    </ul>
							    
							    <!-- Tab panes -->
							    <div class="tab-content">
							      <div class="tab-pane active" id="home">
							      	<ul class="list-group">
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">New <span class="label label-danger" style="float: right;">X</span></li>
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">Deleted <span class="label label-danger" style="float: right;">X</span></li> 
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">Warnings <span class="label label-danger" style="float: right;">X</span></li> 
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">New <span class="label label-danger" style="float: right;">X</span></li>
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">Deleted <span class="label label-danger" style="float: right;">X</span></li> 
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">Warnings <span class="label label-danger" style="float: right;">X</span></li> 
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">New <span class="label label-danger" style="float: right;">X</span></li>
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">Deleted <span class="label label-danger" style="float: right;">X</span></li> 
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">Warnings <span class="label label-danger" style="float: right;">X</span></li> 
									</ul>
							      </div>
							      <div class="tab-pane" id="profile">
							      	<ul class="list-group">
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">New <span class="label label-danger" style="float: right;">X</span></li>
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">Deleted <span class="label label-danger" style="float: right;">X</span></li> 
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">Warnings <span class="label label-danger" style="float: right;">X</span></li> 
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">New <span class="label label-danger" style="float: right;">X</span></li>
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">Deleted <span class="label label-danger" style="float: right;">X</span></li> 
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">Warnings <span class="label label-danger" style="float: right;">X</span></li> 
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">New <span class="label label-danger" style="float: right;">X</span></li>
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">Deleted <span class="label label-danger" style="float: right;">X</span></li> 
									  <li class="list-group-item" style="border-bottom-style: none;border-top-style: none; padding: 5px 15px;">Warnings <span class="label label-danger" style="float: right;">X</span></li> 
									</ul>
							      </div>
							    </div>
                            </div>
		                	<button class="btn btn-default" type="submit" style="height: 50px; width: 50px;"><i class="glyphicon glyphicon-search"></i></button>
		        		</div>
		        	</div>
	        	</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-sm-12 col-md-12" style="text-align: center;font: 15px bold;color: #D6607E;">
			<div class="navbar-form" role="search">
	        	<div class="input-group col-sm-6 col-md-6">
	        		<span style="color: blueviolet;">Hot</span><span style="padding: 0 10px 0 50px;cursor: pointer;">고객</span><span style="padding: 0 10px 0 10px;cursor: pointer;">가입</span><span style="padding: 0 10px 0 10px;cursor: pointer;">매출</span><span style="padding: 0 10px 0 10px;cursor: pointer;">시스템</span><span style="padding: 0 10px 0 10px;cursor: pointer;">테이터</span>
	        	</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-sm-12 col-md-12" style="text-align: center;font: 15px bold;color: #D6607E;">
			<div class="navbar-form" role="search">
	        	<div class="input-group col-sm-8 col-md-8">
	        		<div class="form-group pull-left">
		                <label>
		                  <input type="radio" name="r2" class="minimal-red" checked>데이터셋
		                </label>
		                <label>
		                  <input type="radio" name="r2" class="minimal-red">정형 보고서
		                </label>
		                <label>
		                  <input type="radio" name="r2" class="minimal-red">지표
		            	</label>
		                <label>
		                  <input type="radio" name="r2" class="minimal-red">관점
		            	</label>
					</div>
	        	</div>
			</div>
		</div>
	</div>
		
	<div class="row">
		<div class="col-sm-12 col-md-12" style="text-align: center;font: 15px bold;color: #D6607E;">
			<div class="navbar-form" role="search">
	        	<div class="input-group col-sm-8 col-md-8">
	        		<div id="chart" class="d3-treemap" style="width: 100%; height: 500px;"><svg width="1120" height="448" style="margin-left: 0px;"><g transform="translate(0,24)" style="shape-rendering: crispEdges;"><g class="depth"><g><g><rect class="child" x="1120" y="218.50306748466264" width="0" height="205.49693251533736" style="fill: rgb(49, 130, 189);"><title>IT (0)</title></rect><text class="ctext" x="1099.78125" y="418" style="opacity: 0;">IT</text></g><rect class="parent" x="1120" y="218.50306748466264" width="0" height="205.49693251533736" style="fill: rgb(49, 130, 189);"></rect><text class="ptext" dy=".75em" x="1126" y="224.50306748466264" style="opacity: 0;"><tspan x="1126">IT</tspan><tspan dy="1.0em" x="1126">0</tspan></text></g><g><g><rect class="child" x="1120" y="218.50306748466264" width="0" height="205.49693251533736" style="fill: rgb(107, 174, 214);"><title>운영 (0)</title></rect><text class="ctext" x="1082" y="418" style="opacity: 0;">운영</text></g><rect class="parent" x="1120" y="218.50306748466264" width="0" height="205.49693251533736" style="fill: rgb(107, 174, 214);"></rect><text class="ptext" dy=".75em" x="1126" y="224.50306748466264" style="opacity: 0;"><tspan x="1126">운영</tspan><tspan dy="1.0em" x="1126">0</tspan></text></g><g class="children"><g><rect class="child" x="1120" y="218.50306748466264" width="0" height="0" style="fill: rgb(158, 202, 225);"><title>재무1 (0)</title></rect><text class="ctext" x="1073.1015625" y="212.50306748466264" style="opacity: 0;">재무1</text></g><g><rect class="child" x="1120" y="218.50306748466264" width="0" height="0" style="fill: rgb(198, 219, 239);"><title>재무2 (0)</title></rect><text class="ctext" x="1073.1015625" y="212.50306748466264" style="opacity: 0;">재무2</text></g><rect class="parent" x="1120" y="218.50306748466264" width="0" height="205.49693251533736" style="fill: rgb(230, 85, 13);"></rect><text class="ptext" dy=".75em" x="1126" y="224.50306748466264" style="opacity: 0;"><tspan x="1126">재무</tspan><tspan dy="1.0em" x="1126">0</tspan></text></g><g><g><rect class="child" x="1120" y="218.50306748466264" width="0" height="205.49693251533736" style="fill: rgb(253, 141, 60);"><title>조달 (0)</title></rect><text class="ctext" x="1082" y="418" style="opacity: 0;">조달</text></g><rect class="parent" x="1120" y="218.50306748466264" width="0" height="205.49693251533736" style="fill: rgb(253, 141, 60);"></rect><text class="ptext" dy=".75em" x="1126" y="224.50306748466264" style="opacity: 0;"><tspan x="1126">조달</tspan><tspan dy="1.0em" x="1126">0</tspan></text></g><g class="children"><g><rect class="child" x="921.5189873417722" y="291.8948290972831" width="198.48101265822777" height="132.1051709027169" style="fill: rgb(253, 174, 107);"><title>영업1 (9)</title></rect><text class="ctext" x="1073.1015625" y="418" style="opacity: 1;">영업1</text></g><g><rect class="child" x="723.0379746835446" y="291.8948290972831" width="198.48101265822766" height="132.1051709027169" style="fill: rgb(253, 208, 162);"><title>영업2 (9)</title></rect><text class="ctext" x="874.6205498417722" y="418" style="opacity: 1;">영업2</text></g><g><rect class="child" x="723.0379746835446" y="218.50306748466264" width="396.9620253164554" height="73.39176161262046" style="fill: rgb(49, 163, 84);"><title>영업3 (10)</title></rect><text class="ctext" x="1073.1015625" y="285.8948290972831" style="opacity: 1;">영업3</text></g><rect class="parent" x="723.0379746835446" y="218.50306748466264" width="396.9620253164554" height="205.49693251533736" style="fill: rgb(116, 196, 118);"></rect><text class="ptext" dy=".75em" x="729.0379746835446" y="224.50306748466264" style="opacity: 1;"><tspan x="729.0379746835446">영업</tspan><tspan dy="1.0em" x="729.0379746835446">28</tspan></text></g><g class="children"><g><rect class="child" x="0" y="387.73583543846985" width="723.0379746835446" height="36.26416456153015" style="fill: rgb(161, 217, 155);"><title>콜센터1 (9)</title></rect><text class="ctext" x="660.1395371835446" y="418" style="opacity: 1;">콜센터1</text></g><g><rect class="child" x="0" y="218.50306748466264" width="723.0379746835446" height="169.2327679538072" style="fill: rgb(199, 233, 192);"><title>콜센터2 (42)</title></rect><text class="ctext" x="660.1395371835446" y="381.73583543846985" style="opacity: 1;">콜센터2</text></g><rect class="parent" x="0" y="218.50306748466264" width="723.0379746835446" height="205.49693251533736" style="fill: rgb(117, 107, 177);"></rect><text class="ptext" dy=".75em" x="6" y="224.50306748466264" style="opacity: 1;"><tspan x="6">콜센터</tspan><tspan dy="1.0em" x="6">51</tspan></text></g><g class="children"><g><rect class="child" x="831.9999999999999" y="127.46012269938652" width="288.0000000000001" height="91.04294478527612" style="fill: rgb(158, 154, 200);"><title>인사1 (9)</title></rect><text class="ctext" x="1073.1015625" y="212.50306748466264" style="opacity: 1;">인사1</text></g><g><rect class="child" x="0" y="127.46012269938652" width="831.9999999999999" height="91.04294478527612" style="fill: rgb(188, 189, 220);"><title>인사3 (26)</title></rect><text class="ctext" x="785.1015624999999" y="212.50306748466264" style="opacity: 1;">인사3</text></g><g><rect class="child" x="0" y="0" width="1120" height="127.46012269938652" style="fill: rgb(218, 218, 235);"><title>인사2 (49)</title></rect><text class="ctext" x="1073.1015625" y="121.46012269938652" style="opacity: 1;">인사2</text></g><rect class="parent" x="0" y="0" width="1120" height="218.50306748466264" style="fill: rgb(99, 99, 99);"></rect><text class="ptext" dy=".75em" x="6" y="6" style="opacity: 1;"><tspan x="6">인사</tspan><tspan dy="1.0em" x="6">84</tspan></text></g></g><g class="grandparent"><rect y="-24" width="1120" height="24"></rect><text x="6" y="-18" dy=".75em">Total (163)</text></g></g></svg></div>
	        	</div>
			</div>
		</div>
	</div>
		
  </div>
  <!-- /.content-wrapper -->

  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 3.0.1
    </div>
    <strong>Copyright &copy; 2014-2016 <a href="http://www.penta.co.kr">Pennta Systems Technology Inc</a>.</strong> All rights reserved.
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
<c:import url="/deresources/commonlib/js" />
<script src="<spring:url value="/assets/javascripts/dataeye.resizer.js"/>"></script>
<script src="<spring:url value="/app/portal/dataeye.portal.js"/>"></script>
<script type="text/javascript">
debugger;
$('#myFavorite').on('show.bs.dropdown', function () {
	$("#myFavoriteLayer").css({width: $("#tSearchLayer").css("width")});
});


$('#myFavorite').on('hide.bs.dropdown', function(e) {
    if ( $(this).hasClass('dontClose') ){
        e.preventDefault();
    }
    $(this).removeClass('dontClose');
});

$('#myFavoriteLayer').on('click', function(){
    $(this).closest('#myFavorite').addClass('dontClose');
});

$('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
    checkboxClass: 'icheckbox_minimal-red',
    radioClass: 'iradio_minimal-red'
});
</script>
</body>
</html>
