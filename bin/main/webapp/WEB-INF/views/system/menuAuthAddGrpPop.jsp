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
						<label class="col-sm-2 control-label" id="ATR_ID_SEQ_3">사용자 그룹</label>
						<div id="obj_info_det_3" class="col-sm-10">
							<div class="input-group">
								<select id="userGrp" class="form-control input-sm">
									<option value="">선택</option>
								</select>
							</div>
						</div>
				</div>
						<div id="obj_info_m_4" class="col-sm-12 form-group">
							<label class="col-sm-2 control-label" id="ATR_ID_SEQ_4">권한</label>
							<div id="obj_info_det_4" class="col-sm-10">
								<div class="input-group">															
								   <div class="checkbox">
									  <label><input type="checkbox" id="roleNum" name="roleNum"  value="C" >등록</label>
									  <label><input type="checkbox" id="roleNum" name="roleNum"  value="R" checked>읽기</label>
									  <label><input type="checkbox" id="roleNum" name="roleNum"  value="U">수정</label>
									  <label><input type="checkbox" id="roleNum" name="roleNum"  value="D">삭제</label>
									  <label><input type="checkbox" id="roleNum" name="roleNum"  value="E">실행</label>
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
		
		var result = DE.ajax.call({async:false, url:"metapublic/list", data:{"queryId":"system.user.getGrpList", "notInMenuId":reqParam["menuId"]}})
		var data = result["data"];
		var userGrp = $("#userGrp");
		if(data.length>0) {
			for(var i =0;i<data.length;i++) {				
				userGrp.append("<option value='" + data[i].USER_GRP_ID + "'>" + data[i].USER_GRP_NM + "</option>");
			}				
		}				
	};
	init();		
		
	$("#btnInsert").click(function (){
		var roleAuthArr = [];
		$('input:checkbox[name="roleNum"]').each(function() {
			if ($(this).is(":checked")) {
				roleAuthArr.push("Y");	
			} else {
				roleAuthArr.push("N");
			}		      			            
		});
		 
		if($("#userGrp").val() == "") {
			DE.box.alert("사용자 그룹을 선택해주세요.");			
			return;
		}
		 
		if(roleAuthArr.join("") === "NNNNN"){
			DE.box.alert("권한을 선택해주세요.");
			return;
		} else {
			var rsp = DE.ajax.call({
				async:false, 
				url:"system/menu?oper=registUserGrp", 
				data:{
					"APP_ID":reqParam["appId"],
					"MENU_ID":reqParam["menuId"],
					"PRIV_RCVR_GBN":"G",
					"PRIV_RCVR_ID":$("#userGrp").val(),
					"PRIV_OPER_GBN":roleAuthArr.join(":"),
					"DEL_YN":"N"
				}
			});
		}
		if (rsp["status"] == "SUCCESS") {									
			DE.box.alert("등록 되었습니다.", function(e){self.close();});
		}	 
	});
});
</script>    
</body>
</html>	



