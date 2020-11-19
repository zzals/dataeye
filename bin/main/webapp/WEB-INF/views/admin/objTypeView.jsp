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
            <div class="popup_title">유형 관리</div>
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
		                  <label for="ATR_ID" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>객체유형 ID</label>
		                  <div class="col-sm-10">
		                    <div class="input-group">
				                <input type="text" class="form-control" id="OBJ_TYPE_ID" name="OBJ_TYPE_ID" placeholder="">
				                <div class="input-group-btn">
				                  <button id="objTypeIdDupCheck" type="button" class="btn btn-warning">중복체크</button>
				                </div>
				              </div>
		                  </div>
		                </div>
		                <div class="form-group">
		                  	<label for="UI_NM" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>객체유형명</label>
		                  	<div class="col-sm-10">
		                    	<input type="text" class="form-control" id="OBJ_TYPE_NM" name="OBJ_TYPE_NM" placeholder="">
							</div>
		                </div>
		                <div class="form-group">
		                  	<label for="UI_NM" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>설명</label>
		                  	<div class="col-sm-10">
		                    	<input type="text" class="form-control" id="OBJ_TYPE_DESC" name="OBJ_TYPE_DESC" placeholder="">
							</div>
		                </div>
		                <div class="form-group">
		                  	<label for="UI_NM" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>정렬순서</label>
		                  	<div class="col-sm-10">
		                    	<input type="text" class="form-control" id="SORT_NO" name="SORT_NO" placeholder="">
							</div>
		                </div>
		                <div class="form-group">
		                  	<label for="UI_NM" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>상위객체유형</label>
		                  	<div class="col-sm-10">
		                    	<select id="UP_OBJ_TYPE_ID" name="UP_OBJ_TYPE_ID" class="form-control" style="width: 100%;"></select>
							</div>
		                </div>
		                <div class="form-group">
		                  	<label for="UI_NM" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>레벨</label>
		                  	<div class="col-sm-10">
		                    	<input type="text" class="form-control" id="HIER_LEV_NO" name="HIER_LEV_NO" placeholder="">
							</div>
		                </div>
		                <div class="form-group">
		                  	<label for="UI_NM" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>Leaf여부</label>
		                  	<div class="col-sm-10">		                    	
		                    	<select id="LST_LEV_YN" name="LST_LEV_YN" class="form-control" style="width: 100%;">
		                    		<option value="Y">Y</option>
		                    		<option value="N">N</option>
		                    	</select>
							</div>
		                </div>	
		                <div class="form-group">
		                  	<label for="UI_NM" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>속성관리구분</label>
		                  	<div class="col-sm-10">		                    	
		                    	<select id="ATR_ADM_CD" name="ATR_ADM_CD" class="form-control" style="width: 100%;"></select>
							</div>
		                </div>		             
		            </div>
		            <div class="box-footer">
		                <button id="btnInsert" type="button" class="btn btn-default pull-right" style="display: none;"><i class="fa fa-trash-o"></i>등록</button>
		                <button id="btnUpdate" type="button" class="btn btn-default pull-right" style="display: none;"><i class="fa fa-pencil"></i>수정</button>
		                <button id="btnRemove" type="button" class="btn btn-default pull-right" style="display: none;"><i class="fa fa-trash-o"></i>삭제</button>
		            </div>	
		        </div>				
			</div>
		</div>
	</section>
	
<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>	
<script src="<spring:url value="/app/admin/pages/objTypeView.js"/>"></script>
</body>
</html>

