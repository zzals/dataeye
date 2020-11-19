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
            <div class="popup_title">객체 UI</div>
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
		                <div class="form-group">
		                  	<label for="APP_ID" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> APP ID</label>
		                  	<div class="col-sm-10">
		                    	<div class="input-group">
					                <input type="text" class="form-control" id="APP_ID" name="APP_ID" placeholder="">
					                <div class="input-group-btn">
					                  <button id="appIdDupCheck" type="button" class="btn btn-warning">중복체크</button>
					                </div>
					        	</div>
							</div>
		                </div>
		                <div class="form-group">
		                  	<label for="APP_NM" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> APP 명</label>
		                  	<div class="col-sm-10">
		                    	<input type="text" class="form-control" id="APP_NM" name="APP_NM" placeholder="">
							</div>
		                </div>
						<div class="form-group">
						   	<label for="APP_DESC" class="col-sm-2 control-label"> 설명</label>
						   	<div class="col-sm-10">
						  		<textarea id="APP_DESC" name="APP_DESC" class="form-control" rows="5"></textarea>
						  	</div>
						</div>
		                <div class="form-group">
			                <label for="DEL_YN" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 삭제여부</label>
			                <div class="col-sm-10">
				                <select id="DEL_YN" name="DEL_YN" class="form-control" style="width: 100%;">
				                  	<option selected="selected" value="N">N</option>
				                  	<option value="Y">Y</option>
				                </select>
				            </div>
						</div>
		                <div class="form-group">
			                <label for="USE_YN" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 사용여부</label>
			                <div class="col-sm-10">
				                <select id="USE_YN" name="USE_YN" class="form-control" style="width: 100%;">
				                  	<option selected="selected" value="Y">Y</option>
				                  	<option value="Y">N</option>
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
<script src="<spring:url value="/app/system/pages/application.view.js"/>"></script>
</body>
</html>

