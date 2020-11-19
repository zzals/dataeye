<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Big Data Platform</title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">



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

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/dataeye-main.css">
<!-- Theme style -->
<link type="text/css" rel="stylesheet" href="/dataeye_ebay/theme/bdpportal/stylesheets/dataeye-main.css">

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/javascripts-lib/iCheck/all.css">

<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/javascripts-lib/select2/select2.css">

<!-- DataEye main Skins. Choose a skin from the css/skins
 	folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/stylesheets/skins/_all-skins.min.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

<!-- jquery-ui 1.10.3 -->


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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.layout/layout-default.css">
<!-- jQuery -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.layout/jquery.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.layout/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.layout/jquery.layout.js"></script>
<style class="cp-pen-styles">
	html, body {
	  height: 100%;
	  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
	  padding: 0;
	  margin: 0;
	}
</style>	
</head>
<body>
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1><small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class=""></i></a></li>
        <li class="active"></li>
      </ol>
    </section>

    <!-- Main content -->
	<section class="content">
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="collapsed navbar-toggle" data-toggle="collapse" aria-expanded="false">
						<span class="sr-only">Toggle navigation</span> 
						<span class="icon-bar"></span> 
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a href="#" class="navbar-brand"></a>
				</div>
				<div class="collapse navbar-collapse">
					<div class="navbar-form navbar-left">
						<label class="control-label" for="inputSuccess">
							<i class="glyphicon glyphicon-ok"></i>실행시작일시
						</label>
						<div class="form-group">
							<div class="input-group date" id="startDt">
								<input class="form-control input-sm" type="text" value="">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="form-group">
							<span class="label-addon">&nbsp;~&nbsp;</span>
						</div>
						<div class="form-group">
							<div class="input-group date" id="endDt">
								<input class="form-control input-sm" type="text" value="">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>
					<div class="navbar-form navbar-left">
						<label class="control-label" for="inputSuccess">
							<i class="glyphicon glyphicon-ok"></i>검증룰유형
						</label>
						<div class="form-group">
							<select id="selBrType" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;"></select>
						</div>
					</div>
					<div class="navbar-form navbar-left">
						<label class="control-label" for="inputSuccess">
							<i class="glyphicon glyphicon-ok"></i>최근실행결과
						</label>
						<div class="form-group">
							<select id="selDqVerrslt" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;"></select>
						</div>
					</div>
					<div class="navbar-form navbar-right">
						<div class="form-group">
							<select id="searchKey" class="form-control select2" data-placeholder="선택하세요." style="width: 150px;">
			                  	<option value="_all">[전체]</option>
			                  	<option value="BR_GRP_NM">검증그룹명</option>
			                  	<option value="BR_NM">검증룰명</option>
			                </select>
							<input id="searchValue" class="form-control" placeholder="Search">
						</div>
						<button type="submit" id="btnSearch" class="btn btn-default">검색</button>
					</div>
				</div>
			</div>
		</nav>
		<div class="content-body" >
	    	<div class="row" style="height: 500px;; width: 100%;">
				<div class="col-xs-12" id="container" style="height: 500px">
					<div class="pane ui-layout-center">
						Center
						<p><a href="http://layout.jquery-dev.com/demos.cfm"><B>Go to the Demos page</B></a></p>
					</div>
					<div class="pane ui-layout-north">North</div>
					<div class="pane ui-layout-south">South</div>
					<div class="pane ui-layout-east">East</div>
					<div class="pane ui-layout-west">West</div>
				</div>
			</div>
		</div>
	</section>

	
<script type="text/javascript">	    
$(document).ready( function() {
	var fn = function() {
		myLayout = $('#container').layout({
			center : {
			//size:			500
		}
		,	south : {
			size : 500
		}
			// RESIZE Accordion widget when panes resize
		});
	}
	// if a new theme is applied, it could change the height of some content,
	// so call resizeAll to 'correct' any header/footer heights affected
	// NOTE: this is only necessary because we are changing CSS *AFTER LOADING* using themeSwitcher
	//setTimeout( fn, 1000 ); /* allow time for browser to re-render with new theme */
fn()
});
</script>
</body>
</html>