$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
		
	$("input#USER_GRP_ID").on("change", function(e) {
		$("input#USER_GRP_ID").attr("IS_VALID_CHECK", "N");
	});
	
	$("button#userGrpIdDupCheck").on("click", function(e) {
		if ($("#USER_GRP_ID").val() === "") {
			DE.box.alert(DE.i18n.prop("msg.usergrp.required.USER_GRP_ID"), function(){
				$("[id=USER_GRP_ID]").focusin();
			});
			return;
		}
		
		var rsp = DE.ajax.call({"async":false, "url":"system/userGrp?oper=dupCheck", "data":{"userGrpId":$("#USER_GRP_ID").val()}});
		if (rsp["status"] === "SUCCESS") {
			DE.box.alert(rsp["message"]);
			$("input#USER_GRP_ID").attr("IS_VALID_CHECK", "Y");
		} else {
			DE.box.alert(rsp["message"]);	
		}
	});

	var makeForm = function() {
		return {
			"USER_GRP_ID":$("#USER_GRP_ID").val(),
			"USER_GRP_NM":$("#USER_GRP_NM").val(),
			"USER_GRP_DESC":$("#USER_GRP_DESC").val(),
			"USER_GRP_TYPE":$("#USER_GRP_TYPE").val(),
			"PRIV_YN":$("#PRIV_YN").val(),
			"USE_YN":$("#USE_YN").val(),
			"DEL_YN":"N"
		};
	};
	
	$("button#btnInsert").on("click", function(e) {
		var callback = {
			success : function(response) {
				DE.box.alert(response["message"], function(){ self.close();});
			},
			error : function(response) {
				DE.box.alert(response["responseJSON"]["message"]);
			}
		};
		
		if ($("#USER_GRP_ID").attr("is_valid_check") !== "Y") {
			DE.box.alert(DE.i18n.prop("msg.usergrp.required.USER_GRP_ID"));
			return;
		}
		var opts = {
			async:false,
			url : "system/userGrp?oper=regist",
			data : $.param(makeForm())
		};	
		
		DE.ajax.formSubmit(opts, callback.success, callback.error);
	});	

	$("button#btnUpdate").on("click", function(e) {
		var callback = {
			success : function(response) {
				DE.box.alert(response["message"], function(){ self.close();});
			},
			error : function(response) {
				DE.box.alert(response["responseJSON"]["message"]);
			}
		};
		
		if ($("#USER_GRP_ID").attr("is_valid_check") === "N") {
			DE.box.alert(DE.i18n.prop("msg.usergrp.required.USER_GRP_ID"));
			return;
		}
		var opts = {
			url : "system/userGrp?oper=update",
			data : $.param(makeForm())
		};	
		
		DE.ajax.formSubmit(opts, callback.success, callback.error);
	});	
	
	var init = function() {
		DE.ui.render.selectBox($("#DATA_TYPE_CD"), DE.code.get("SYS_DATATYP"), {"valueKey":"cdId", "nameKey":"cdNm"});
		DE.ui.render.selectBox($("#ATR_RFRN_CD"), DE.code.get("SYS_REFTYPE"), {"valueKey":"cdId", "nameKey":"cdNm"});
		DE.ui.render.selectBox($("#UI_COMP_CD"), DE.code.get("SYS_UI"), {"valueKey":"cdId", "nameKey":"cdNm"});
		$("#COL_LEN").mask("00000", {reverse: true});
		$("#UI_COMP_HEIGHT_PX").mask("00000", {reverse: true});
		
		if (reqParam["mode"] === "C") {
			$(".popup_title").html("사용자그룹 등록");
			$(".popup_title").show();
			$("button#btnInsert").css("display", "inline-block");
			$("button#btnUpdate").remove();
			$("button#btnRemove").remove();			
		} else if (reqParam["mode"] === "R" || reqParam["mode"] === "U") {
			$(".popup_title").html("사용자그룹 수정");
			$(".popup_title").show();
			$("button#btnInsert").remove();
			$("button#btnRemove").remove();
			if (reqParam["mode"] === "R") {
				$("button#btnUpdate").remove();
				$("input, select, textarea, button[id=userGrpIdDupCheck]", "[de-role=form]").prop( "disabled", true );
			} else {
				$("input[id=USER_GRP_ID], button[id=userGrpIdDupCheck]", "[de-role=form]").prop( "disabled", true );
				$("button#btnUpdate").css("display", "inline-block");
			}
			
			DE.ajax.call({"url":"system/userGrp?oper=get", "data":{"userGrpId":reqParam["userGrpId"]}}, function(rsp){
				$("#USER_GRP_ID").val(rsp["data"]["userGrpId"]);
				$("#USER_GRP_NM").val(rsp["data"]["userGrpNm"]);
				$("#USER_GRP_DESC").val(rsp["data"]["userGrpDesc"]);
				$("#USER_GRP_TYPE").val(rsp["data"]["userGrpType"]);
				$("#DEL_YN").val(rsp["data"]["delYn"]);
				$("#PRIV_YN").val(rsp["data"]["privYn"]);
				$("#USE_YN").val(rsp["data"]["useYn"]);
			});
		}
	};
	init();
});