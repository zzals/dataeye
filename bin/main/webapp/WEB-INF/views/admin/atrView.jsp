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
            <div class="popup_title">속성 관리</div>
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
		                  <label for="ATR_ID" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>속성 ID</label>
		                  <div class="col-sm-10">
		                    <div class="input-group">
				                <input type="text" class="form-control" id="ATR_ID" name="ATR_ID" placeholder="">
				                <div class="input-group-btn">
				                  <button id="atrIdDupCheck" type="button" class="btn btn-warning">중복체크</button>
				                </div>
				              </div>
		                  </div>
		                </div>
		                <div class="form-group">
		                  <label for="ATR_NM" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>속성 명</label>
		                  <div class="col-sm-10">
		                    <input type="text" class="form-control" id="ATR_NM" name="ATR_NM" placeholder="">
		                  </div>
		                </div>
			            <div class="form-group">
			            	<label for="ATR_DESC" class="col-sm-2 control-label">속성 설명</label>
			                <div class="col-sm-10">
			                	<textarea id="ATR_DESC" name="ATR_DESC" class="form-control" rows="5" placeholder=""></textarea>
			                </div>
			            </div>
		                <div class="form-group">
			                <label for="DATA_TYPE_CD" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>데이터 유형</label>
			                <div class="col-sm-10">
				                <select id="DATA_TYPE_CD" name="DATA_TYPE_CD" class="form-control" style="width: 100%;"></select>
				            </div>
			            </div>
		                <div class="form-group">
			                <label for="COL_LEN" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>컬럼 길이</label>
			                <div class="col-sm-10">
				                <input type="text" class="form-control" id="COL_LEN" name="COL_LEN" placeholder="">
				            </div>
			            </div>
		                <div class="form-group">
			                <label for="ATR_RFRN_CD" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>속성참조 유형</label>
			                <div class="col-sm-10">
				                <select id="ATR_RFRN_CD" name="ATR_RFRN_CD" class="form-control" style="width: 100%;"></select>
				            </div>
			            </div>
			            <div class="form-group">
			            	<label for="SQL_SBST" class="col-sm-2 control-label">속성 SQL</label>
			                <div class="col-sm-10">
			                	<textarea id="SQL_SBST" name="SQL_SBST" class="form-control" rows="5" placeholder=""></textarea>
			                </div>
			            </div>
		                <div class="form-group">
			            	<label for="UI_COMP_CD" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>UI 구분</label>
			                <div class="col-sm-10">
				            	<select id="UI_COMP_CD" name="UI_COMP_CD" class="form-control" style="width: 100%;"></select>
				            </div>
			            </div>
		                <div class="form-group">
			            	<label for="UI_COMP_WIDTH_RADIO" class="col-sm-2 control-label">UI 기본 Width</label>
			                <div class="col-sm-10">
				            	<select id="UI_COMP_WIDTH_RADIO" name="UI_COMP_WIDTH_RADIO" class="form-control" style="width: 100%;">
				            		<option selected="selected" value="12">100%</option>
				            	</select>
				            </div>
			            </div>
		                <div class="form-group">
			                <label for="UI_COMP_HEIGHT_PX" class="col-sm-2 control-label">UI 기본 Height</label>
			                <div class="col-sm-10">
				                <input type="text" class="form-control" id="UI_COMP_HEIGHT_PX" name="UI_COMP_HEIGHT_PX" placeholder="">
				                <span class="help-block">단위 : px</span>
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
<script src="<spring:url value="/app/admin/pages/atrView.js"/>"></script>
</body>
</html>

