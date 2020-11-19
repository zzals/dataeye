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
            <div class="popup_title">코드그룹 관리</div>
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
		                  <label for="CD_GRP_ID" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 그룹코드 ID</label>
		                  <div class="col-sm-10">
		                    <div class="input-group">
				                <input type="text" class="form-control" id="CD_GRP_ID" name="CD_GRP_ID" placeholder="">
				                <div class="input-group-btn">
				                  <button id="cdGrpIdDupCheck" type="button" class="btn btn-warning">중복체크</button>
				                </div>
				              </div>
		                  </div>
		                </div>
		                <div class="form-group">
		                  <label for="CD_GRP_NM" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 그룹코드 명</label>
		                  <div class="col-sm-10">
		                    <input type="text" class="form-control" id="CD_GRP_NM" name="CD_GRP_NM" placeholder="">
		                  </div>
		                </div>
			            <div class="form-group">
			            	<label for="CD_GRP_DESC" class="col-sm-2 control-label"> 설명</label>
			                <div class="col-sm-10">
			                	<textarea id="CD_GRP_DESC" name="CD_GRP_DESC" class="form-control" rows="5" placeholder=""></textarea>
			                </div>
			            </div>
		                <div class="form-group">
			                <label for="DEL_YN" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>삭제여부</label>
			                <div class="col-sm-10">
				                <select id="DEL_YN" name="DEL_YN" class="form-control" style="width: 100%;">
				                  	<option selected="selected" value="N">N</option>
				                  	<option value="Y">Y</option>
				                </select>
				            </div>
						</div>
		                <div class="form-group">
			                <label for="UP_CD_GRP_NM" class="col-sm-2 control-label"> 상위 그룹코드</label>
		                  	<div class="col-sm-10">
		                    	<div class="input-group">
					                <input type="text" class="form-control" id="UP_CD_GRP_NM" name="UP_CD_GRP_NM" placeholder="">
					                <div class="input-group-btn">
					                  <button id="btnUpCdGrpIdSelect" type="button" class="btn btn-warning">선택</button>
					                </div>
								</div>
							</div>
			            </div>
		                <div class="form-group">
			                <label for="EFCT_ST_DATE" class="col-sm-2 control-label"> 적용시작일</label>
			                <div class="col-sm-10">
			                	<div id="EFCT_ST_DATE" class="input-group">
					                <div class="input-group date">
					                	<input type="text" class="form-control" placeholder="">
					                	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
					                </div>
					            </div>
				            </div>
			            </div>
			            <div class="form-group">
			            	<label for="EFCT_ED_DATE" class="col-sm-2 control-label"> 적용종료일</label>
			                <div class="col-sm-10">
			                	<div id="EFCT_ED_DATE" class="input-group">
				                	<div class="input-group date">
						                <input type="text" class="form-control" placeholder="">
					                	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
					                </div>
								</div>
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
	
	<!-- 상위그룹 선택 뷰 -->
	<div class="modal modal-fullscreen fade" id="upgrp-selected-view" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  		<div class="modal-dialog" style="width: 600px;">
    		<div class="modal-content">
      			<div class="modal-header">
        			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">닫기</span></button>
        			<h4 class="modal-title" id="myModalLabel">상위그룹 선택</h4>
      			</div>
	      		<div class="modal-body">
	      			<div class="box">
	           			<div class="box-header">
							<div class="navbar-form navbar-right">
								<button type="button" id="btnUpCdGrpAdd" class="btn btn-default">선택</button>
							</div>
						</div>
		      			<div class="box-body">
							<table id="jqGridModal" de-role="grid" de-resize=false></table>
							<div id="jqGridPagerModal" de-role="grid-pager"></div>
						</div>
					</div>
	      		</div>
	      		<div class="modal-footer">
	        		<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
	      		</div>
	    	</div>
   	 	</div>
    </div>
<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>	
<script src="<spring:url value="/app/admin/pages/codeGrpView.js"/>"></script>
</body>
</html>

