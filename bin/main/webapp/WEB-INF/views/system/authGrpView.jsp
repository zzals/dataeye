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
<title>Big Data Platform</title>
<c:import url="/deresources/commonlib/popup_css" />
<c:import url="/deresources/commonlib/js" />
<script src="<spring:url value="/assets/javascripts/dataeye.resizer.js"/>"></script>
</head>
<body class="de-popup">
	<section id="header">
        <div class="popup_Title_Area">
            <div class="popup_title" style="display: none;">ROLE그룹 관리</div>
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
						<div class="form-group">
		                  <label for="USER_GRP_ID" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 사용자 그룹ID</label>
		                  <div class="col-sm-10">
		                    <div class="input-group">
				                <input type="text" class="form-control" id="USER_GRP_ID" name="USER_GRP_ID" placeholder="">
				                <div class="input-group-btn">
				                  <button id="userGrpIdDupCheck" type="button" class="btn btn-warning">중복체크</button>
				                </div>
				              </div>
		                  </div>
		                </div>
		                <div class="form-group">
		                  <label for="USER_GRP_NM" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 사용자 그룹명</label>
		                  <div class="col-sm-10">
		                    <input type="text" class="form-control" id="USER_GRP_NM" name="USER_GRP_NM" placeholder="">
		                  </div>
		                </div>
			            <div class="form-group">
			            	<label for="USER_GRP_DESC" class="col-sm-2 control-label"> 사용자 그룹설명</label>
			                <div class="col-sm-10">
			                	<textarea id="USER_GRP_DESC" name="USER_GRP_DESC" class="form-control" rows="5" placeholder=""></textarea>
			                </div>
			            </div>
		                <div class="form-group">
			                <label for="USER_GRP_TYPE" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 권한유형</label>
			                <div class="col-sm-10">
				                <select id="USER_GRP_TYPE" name="USER_GRP_TYPE" class="form-control" style="width: 100%;">
				                	<option value="FUNC_ACCESS">FUNC_ACCESS</option>
				                	<!-- <option value="OBJ_FILTER">OBJ_FILTER</option> -->
				                </select>
				            </div>
			            </div>
		                <div class="form-group">
			            	<label for="DEL_YN" class="col-sm-2 control-label"> 삭제여부</label>
			                <div class="col-sm-10">
				            	<select id="DEL_YN" name="DEL_YN" class="form-control" style="width: 100%;">
				            		<option selected="selected" value="N">N</option>
				            		<option>Y</option>
				            	</select>
				            </div>
			            </div>
		                <div class="form-group">
			            	<label for="PRIV_YN" class="col-sm-2 control-label"> 권한여부</label>
			                <div class="col-sm-10">
				            	<select id="PRIV_YN" name="PRIV_YN" class="form-control" style="width: 100%;">
				            		<option>N</option>
				            		<option selected="selected" value="Y">Y</option>
				            	</select>
				            </div>
			            </div>
		                <div class="form-group">
			            	<label for="USE_YN" class="col-sm-2 control-label"> 사용여부</label>
			                <div class="col-sm-10">
				            	<select id="USE_YN" name="USE_YN" class="form-control" style="width: 100%;">
				            		<option>N</option>
				            		<option selected="selected" value="Y">Y</option>
				            	</select>
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
<script src="<spring:url value="/app/system/pages/authGrpView.js"/>"></script>
</body>
</html>

