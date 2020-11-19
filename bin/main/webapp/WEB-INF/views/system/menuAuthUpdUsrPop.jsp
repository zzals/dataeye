<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><spring:message code="app.title" text="DateEye" /></title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<c:import url="/deresources/commonlib/popup_css" />
	<c:import url="/deresources/commonlib/js" />
    
</head>
<body>
<!-- UI Object -->
<div id="container" de-tag="object-info-pop">
    <!-- HEADER -->
    <div id="header">
        <div class="popup_Title_Area">
            <div class="popup_title">메뉴 권한 등록</div>
        </div>
    </div>
    <section class="content"><nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="collapse navbar-collapse">
			<div id="actionDiv" class="navbar-form navbar-right de-obj-btn">
				<a id="btnInsert" class="btn btn-default btn-sm" de-obj-action="save" role="button" href="#"><i class="fa fa-floppy-o"></i> 저장</a>
			</div>
		</div>
	</div>
</nav>
        <div id="tabContentBody"><div class="basic_info_div">
	<div id="obj_info" class="panel panel-default" role="object-area" style="height: 462px;">
		<div class="panel-body">
			<div id="obj_info_m">				
				<div id="obj_info_m_2" class="col-sm-12 form-group">
					<label class="col-sm-2 control-label" id="ATR_ID_SEQ_2">메뉴명</label>
						<div id="obj_info_det_2" class="col-sm-10">
							<div class="input-group">
								<input class="form-control input-sm" type="text" id="menuNm" name="menuNm" readonly>
							</div>
						</div>
				</div>
				<div id="obj_info_m_3" class="col-sm-12 form-group">
						<label class="col-sm-2 control-label" id="ATR_ID_SEQ_3">사용자</label>
						<div id="obj_info_det_3" class="col-sm-10">
							<div class="input-group">
								<input type="hidden" id="userId" readonly>
								<input class="form-control input-sm" type="text" id="userNm" readonly>
							</div>
						</div>
				</div>
						<div id="obj_info_m_4" class="col-sm-12 form-group">
							<label class="col-sm-2 control-label" id="ATR_ID_SEQ_4">권한</label>
							<div id="obj_info_det_4" class="col-sm-10">
								<div class="input-group">															
								   <div class="checkbox">
									  <label><input type="checkbox" id="roleNum" name="roleNum"  value="Y" >등록</label>
									  <label><input type="checkbox" id="roleNum" name="roleNum"  value="Y">읽기</label>
									  <label><input type="checkbox" id="roleNum" name="roleNum"  value="Y">수정</label>
									  <label><input type="checkbox" id="roleNum" name="roleNum"  value="Y">삭제</label>
									  <label><input type="checkbox" id="roleNum" name="roleNum" value="Y">실행</label>
									</div>																	
								</div>
							</div>
						</div>
					</div>
		</div>
	</div>
</div></div>
</section>
 <div class="navbar navbar-blend navbar-fixed-bottom">
        <div id="actionDiv" class="text-right nav de-obj-btn"><a id="btnAction" class="btn btn-default btn-sm" de-obj-action="cancel" role="button" href="javascript:window.close()"><i class="fa fa-ban" aria-hidden="true"></i> 취소</a></div>
    </div>
         
    <span class="pop_close"><button type="button" class="close" onclick="window.close()">x</button></span>
</div>

<script language="javascript">
 $(document).ready(function() {
		
		var reqParam = JSON.parse('${data}');
		
		var init = function() {
		
			$("#menuNm").val(reqParam.menuNm);
			$("#userNm").val(reqParam.userNm);
			$("#userId").val(reqParam.userId);
			
			 var roleIdx = 0;
				$('input:checkbox[name="roleNum"]').each(function() {
					roleIdx++;
				     if(roleIdx == 1){ //값 비교
				    	 if(reqParam.cAuth == "Y") {	    	 	
				            this.checked = true; //checked 처리
				    	 }
				      }
				     if(roleIdx == 2){ //값 비교
				    	 if(reqParam.rAuth == "Y") {	    	 	
				            this.checked = true; //checked 처리
				    	 }
				      }
				     if(roleIdx == 3){ //값 비교
				    	 if(reqParam.uAuth == "Y") {	    	 	
				            this.checked = true; //checked 처리
				    	 }
				      }
				     if(roleIdx == 4){ //값 비교
				    	 if(reqParam.dAuth == "Y") {	    	 	
				            this.checked = true; //checked 처리
				    	 }
				      }
				     if(roleIdx == 5){ //값 비교
				    	 if(reqParam.eAuth == "Y") {	    	 	
				            this.checked = true; //checked 처리
				    	 }
				      }

				 });			
			
			
		};
		init();
		
		
		$("#btnInsert").click(function (){

			var privOpenGbn = "";			
			
			var isFirst = true;
			
			 $('input:checkbox[name="roleNum"]').each(function() {
			            if(this.checked) {
			            	if(isFirst) {
			            		privOpenGbn = privOpenGbn  + "Y"
			            		isFirst = false;
			            	} else {
			            		privOpenGbn = privOpenGbn  + ":Y"
			            	}
			            	
			            } else {
			            	if(isFirst) {
			            		privOpenGbn = privOpenGbn  + "N"
			            		isFirst = false;
			            	} else {
			            		privOpenGbn = privOpenGbn  + ":N"
			            	}
			            }			      			            
			 });
		 
			var rsp = DE.ajax.call({async:false, url:"system/updMenuUser", data:{"appId":reqParam.appId,"menuId":reqParam.menuId,"privRcvrGbn":"U","userId":reqParam.userId,"privOpenGbn":privOpenGbn}});
			if (rsp["status"] == "SUCCESS") {									
				DE.box.alert("수정 되었습니다.");
				(opener || parent).refreshContent();		
				self.close();
			} else {
				DE.box.alert(rsp["message"]);				  
			}	 
				
		});

	});
 
	function setUser(obj) {
		
		var userId =	$("#selUserId").val();
		var userNm =$("#selUserNm").val();
		for(var i=0;i<obj.length;i++) {
			
			var isSameId = false;
			var checkUserId = userId.split(",");			
			for(var p = 0;p<checkUserId.length;p++){
				if(checkUserId[p] == obj[i].USER_ID) {
					isSameId = true;
				}	
			}			
			if(!isSameId) {				 
				if(userId == "") {
					userId = userId + obj[i].USER_ID;
					userNm = userNm + obj[i].USER_NM;
				} else {
					userId = userId + "," + obj[i].USER_ID;
					userNm = userNm +  "," + obj[i].USER_NM;	
				}			
			}				
		}		
		$("#selUserNm").val(userNm);
		$("#selUserId").val(userId);
		
	}
 </script>    
</body>
</html>	



