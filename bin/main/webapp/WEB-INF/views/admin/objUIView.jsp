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
		                  	<label for="UI_ID" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>화면ID</label>
		                  	<div class="col-sm-10">
		                    	<input type="text" class="form-control" id="UI_ID" name="UI_ID" placeholder="" readonly="readonly">
							</div>
		                </div>
		                <div class="form-group">
		                  	<label for="UI_NM" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>화면명</label>
		                  	<div class="col-sm-10">
		                    	<input type="text" class="form-control" id="UI_NM" name="UI_NM" placeholder="">
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
			                <label for="USE_TYPE_CD" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>UI 활용유형</label>
			                <div class="col-sm-10">
				                <select id="USE_TYPE_CD" name="USE_TYPE_CD" class="form-control" style="width: 100%;">
				                  	<option selected="selected" value="WD">윈도우</option>
				                  	<option value="SD">독립형</option>
				                  	<option value="LY" style="display: none;">레이어</option>
				                </select>
				            </div>
			            </div>
		                <div class="form-group" style="display: none;">
							<label for="UI_MODE_CD" class="col-sm-2 control-label">UI 모드</label>
			                <div class="col-sm-10">
				                <select id="UI_MODE_CD" name="UI_MODE_CD" class="form-control" style="width: 100%;">
				                  	<option selected="selected" value="R">조회</option>
				                	<option value="U" style="display: none;">수정</option>
				            	</select>
				            </div>
			            </div>
		                <div class="form-group">
							<label for="UI_TYPE_CD" class="col-sm-2 control-label">UI 유형</label>
			                <div class="col-sm-10">
				                <select id="UI_TYPE_CD" name="UI_TYPE_CD" class="form-control" style="width: 100%;">
				                  	<option selected="selected" value="SIMPLE_UI" pgm-id="common/metacore/ui.default.view">기본 샘플(ONE GRID)형</option>
				                	<option value="SHUTTLE_LR_UI" pgm-id="common/metacore/ui.shuttle.view">기본 셔틀(LEFT-RIGHT)형</option>
				                	<option value="INFLUENCE_UI" pgm-id="common/metacore/ui.influence.view">영향도 기본형</option>
				                	<option value="CUST_UI">사용자정의</option>
				            	</select>
				            </div>
			            </div>
		                <div class="form-group">
			                <label for="PGM_ID" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i>프로그램 ID</label>
			                <div class="col-sm-10">
				                <input type="text" class="form-control" id="PGM_ID" name="PGM_ID" placeholder="" disabled="disabled">
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
<script src="<spring:url value="/app/admin/pages/objUIView.js"/>"></script>
</body>
</html>

