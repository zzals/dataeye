<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Tell the browser to be responsive to screen width -->
<!--<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">-->
<title>Big Data Platform</title>
<c:import url="/deresources/commonlib/popup_css" />
<c:import url="/deresources/commonlib/js" />
<script src="<spring:url value="/assets/javascripts/dataeye.resizer.js"/>"></script>
</head>
<body class="de-popup">
	<section id="header">
        <div class="popup_Title_Area">
            <div class="popup_title" style="display: none;">사용자 등록</div>
	        <div class="box-tools pull-right">
	        	<button type="button" class="close" onclick="window.close()">x</button>
	        </div>
        </div>
    </section>
    <!-- Maincontent -->
	<section class="content">
		<div class="container-fluid">
           	<div class="col-md-12">  
				<div class="form-horizontal">
					<div class="box-body" de-role="form">
						<input type="hidden" class="form-control" id="APP_ID" name="APP_ID" placeholder="">
						<input type="hidden" class="form-control" id="MENU_ID" name="MENU_ID" >		                
		                <div class="form-group">
		                  	<label for="USER_ID" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 사용자ID</label>
		                  	<div class="col-sm-10">
                            	<div class="input-group">
		                    		<input type="text"  class="form-control" id="USER_ID" name="USER_ID" placeholder="">
		                    	 	<div class="input-group-btn">
				                  		<button id="userIdDupCheck" type="button" class="btn btn-warning">중복체크</button>
				                	</div>
								</div>
							</div>
		                </div>
		                <div class="form-group">
			                <label for="USER_NM" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 사용자명</label>
			               <div class="col-sm-10">
				               <input type="text" class="form-control" id="USER_NM" name="USER_NM" placeholder="" >
				            </div>
						</div>
			            <div class="form-group" ">
							<label for="ORG_ID" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 소속조직</label>
			                <div class="col-sm-10">
				                <select id="ORG_ID" name="ORG_ID" class="form-control" style="width: 100%;">
			                		<option value="">[ 조직선택 ]</option>
				                </select>
				            </div>
			            </div>
		                <div class="form-group">
							<label for="EMAIL_ADDR" class="col-sm-2 control-label">이메일주소</label>
			                <div class="col-sm-10">
				                <input type="text" class="form-control" id="EMAIL_ADDR" name="EMAIL_ADDR" placeholder="" >
				            </div>
			            </div>
			            <div class="form-group">
			                <label for="TEL_NO" class="col-sm-2 control-label">전화번호</label>
			                <div class="col-sm-10">
				               <input type="text" class="form-control" id="TEL_NO" name="TEL_NO" placeholder="" >
				            </div>
						</div>
		                <div class="form-group">
			                <label for="HP_NO" class="col-sm-2 control-label">휴대폰번호</label>
			                <div class="col-sm-10">
				               <input type="text" class="form-control" id="HP_NO" name="HP_NO" placeholder="" >
				            </div>
						</div>
		            </div>
		            <div class="box-footer">
		                <button id="btnInsert" type="button" class="btn btn-default pull-right" style="display: none;"><i class="fa fa-trash-o"></i>등록</button>
		                <button id="btnUpdate" type="button" class="btn btn-default pull-right" style="display: none;"><i class="fa fa-pencil"></i>수정</button>
		                <button id="btnDelete" type="button" class="btn btn-default pull-right" style="display: none;"><i class="fa fa-trash-o"></i>삭제</button>
		            </div>	
		        </div>				
			</div>
		</div>
	</section>
	
<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>	
<script src="<spring:url value="/app/system/pages/userView.js"/>"></script>
</body>
</html>

