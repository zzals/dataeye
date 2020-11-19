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
		                  	<label for="MENU_NM" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 메뉴명</label>
		                  	<div class="col-sm-10">
		                    	<input type="text" class="form-control" id="MENU_NM" name="MENU_NM" placeholder="">
							</div>
		                </div>
		                <div class="form-group">
		                  	<label for="MENU_ADM_ID" class="col-sm-2 control-label"> 관리 메뉴명</label>
		                  	<div class="col-sm-10">
		                    	<input type="text" class="form-control" id="MENU_ADM_ID" name="MENU_ADM_ID" placeholder="">
							</div>
		                </div>
		                <div class="form-group">
			                <label for="MENU_TYPE" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 메뉴유형</label>
			                <div class="col-sm-10">
				                <select id="MENU_TYPE" name="MENU_TYPE" class="form-control" style="width: 100%;">
				                  	<option selected="selected" value="FOLDER">폴더</option>
				                  	<option value="LINK">LINK</option>
				                  	<option value="NEWOPEN">팝업</option>
				                </select>
				            </div>
						</div>
						<div class="form-group">
			                <label for="UP_MENU_ID" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 상위메뉴</label>
			                <div class="col-sm-10">
			                	<select id="UP_MENU_ID" name="UP_MENU_ID" class="form-control" style="width: 100%;">
			                		<option value="">[최상위]</option>
			                	</select>
				            </div>
						</div>
						<div class="form-group">
			                <label for="MENU_GRP_CD" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 메뉴그룹</label>
			                <div class="col-sm-10">
			                	<select id="MENU_GRP_CD" name="MENU_GRP_CD" class="form-control" style="width: 100%;">
			                		<option value="LEFT">LEFT</option>
			                		<option value="TOP">TOP</option>
			                	</select>
				            </div>
						</div>
		                <div class="form-group">
			                <label for="PGM_ID" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 페이지링크</label>
			                <div class="col-sm-10">
				                  <input type="text" class="form-control" id="PGM_ID" name="PGM_ID" placeholder="" >
				            </div>
			            </div>
		                <div class="form-group" ">
							<label for="SORT_NO" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 정렬순서</label>
			                <div class="col-sm-10">
				               <input type="text" class="form-control" id="SORT_NO" name="SORT_NO" placeholder="" >
				            </div>
			            </div>
		                <div class="form-group">
							<label for="ICON_CLASS" class="col-sm-2 control-label">ICON_CLASS</label>
			                <div class="col-sm-10">
				                <input type="text" class="form-control" id="ICON_CLASS" name="ICON_CLASS" placeholder="" >
				            </div>
			            </div>
		                <div class="form-group">
			                <label for="DEL_YN" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 삭제여부</label>
			                <div class="col-sm-10">
				                <select id="DEL_YN" name="DEL_YN" class="form-control" style="width: 100%;">
				                  	<option value="Y">Y</option>
				                  	<option selected="selected" value="N">N</option>
				                </select>
				            </div>
						</div>
		                <div class="form-group">
			                <label for="USE_YN" class="col-sm-2 control-label"><i class="fa fa-star" aria-hidden="true"></i> 사용여부</label>
			                <div class="col-sm-10">
				                <select id="USE_YN" name="USE_YN" class="form-control" style="width: 100%;">
				                  	<option selected="selected" value="Y">Y</option>
				                  	<option value="N">N</option>
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
<script src="<spring:url value="/app/system/pages/menuView.js"/>"></script>
</body>
</html>

