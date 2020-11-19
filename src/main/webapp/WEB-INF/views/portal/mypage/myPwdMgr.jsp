<%@page import="kr.co.penta.dataeye.spring.web.session.SessionInfoSupport"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<title>Big Data Platform</title>
<c:import url="/deresources/commonlib/popup_css" />
<c:import url="/deresources/commonlib/js" />
<script src="<spring:url value="/assets/javascripts/dataeye.resizer.js"/>"></script>
</head>
<body class="de-popup">
	<section id="header">
        <div class="popup_Title_Area">
            <div class="popup_title">메뉴</div>
	        <div class="box-tools pull-right">
	        	<button type="button" class="close" onclick="window.close()">x</button>
	        </div>
        </div>
    </section>
    <!-- Maincontent -->
	<section class="content">
		<div class="container-fluid">
           	<div class="col-md-12 box">  
				<div class="form-horizontal">
					<div class="box-body" de-role="form">
						<input type="hidden" class="form-control" id="APP_ID" name="APP_ID" placeholder="">
						<input type="hidden" class="form-control" id="MENU_ID" name="MENU_ID" >	
		                <div class="form-group">
		                  	<label for="USER_PASSWORD" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="false"></i> 기존 비밀번호</label>
		                  	<div class="col-sm-7">
		                    		<input type="password" class="form-control" id="USER_PASSWORD" name="USER_PASSWORD" placeholder="">
							</div>
		                </div>
		                <div class="form-group">
			                <label for="USER_NEW_PASSWORD" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="false"></i> 변경할 비밀번호</label>
			               <div class="col-sm-7">
				               <input type="password" class="form-control" id="USER_NEW_PASSWORD" name="USER_NEW_PASSWORD" placeholder="" >
				            </div>
						</div>
			            <div class="form-group">
							<label for="USER_NEW_PASSWORD_CHK" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="false"></i> 비밀번호 재확인</label>
			                <div class="col-sm-7">
				               <input type="password" class="form-control" id="USER_NEW_PASSWORD_CHK" name="USER_NEW_PASSWORD_CHK" placeholder="" >
				           </div>
						</div>
		            </div>
		            <div class="box-footer">
		                <button id="btnUpdate" type="button" class="btn btn-default pull-right" style="display: none;"><i class="fa fa-pencil"></i>저장</button>
		            </div>	
		        </div>				
			</div>
		</div>
	</section>
<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>	
<script src="<spring:url value="/app/portal/pages/mypage/myPwdMgr.js"/>"></script>
</body>
</html>