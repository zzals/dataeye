$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
		
	$("input#ATR_ID").on("change", function(e) {
		$("input#ATR_ID").attr("IS_VALID_CHECK", "N");
	});
	
	$("button#atrIdDupCheck").on("click", function(e) {
		if ($("#ATR_ID").val() === "") {
			DE.box.alert(DE.i18n.prop("msg.atr.required.atrid"), function(){
				$("[id=ATR_ID]").focusin();
			});
			return;
		}
		
		var rsp = DE.ajax.call({"async":false, "url":"admin/atr?oper=atrIdDuplChk", "data":{"atrId":$("#ATR_ID").val()}});
		if (rsp["status"] === "SUCCESS") {
			DE.box.alert(rsp["message"]);
			$("input#ATR_ID").attr("IS_VALID_CHECK", "Y");
		} else {
			DE.box.alert(rsp["message"]);	
		}
	});

	var makeForm = function() {
		return {
			"ATR_ID":$("#ATR_ID").val(),
			"ATR_NM":$("#ATR_NM").val(),
			"ATR_DESC":$("#ATR_DESC").val(),
			"DATA_TYPE_CD":$("#DATA_TYPE_CD").val(),
			"COL_LEN":$("#COL_LEN").val(),
			"ATR_RFRN_CD":$("#ATR_RFRN_CD").val(),
			"SQL_SBST":$("#SQL_SBST").val(),
			"UI_COMP_CD":$("#UI_COMP_CD").val(),
			"UI_COMP_WIDTH_RADIO":$("#UI_COMP_WIDTH_RADIO").val(),
			"UI_COMP_HEIGHT_PX":$("#UI_COMP_HEIGHT_PX").val(),
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
		
		if ($("#ATR_ID").attr("is_valid_check") === "N") {
			DE.box.alert(DE.i18n.prop("msg.atr.validcheck.atrid"));
			return;
		}
		var opts = {
			async:false,
			url : "admin/atr?oper=regist",
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
		
		if ($("#ATR_ID").attr("is_valid_check") === "N") {
			DE.box.alert(DE.i18n.prop("sqlpool.SQL_ID.valid.check"));
			return;
		}
		var opts = {
			url : "admin/atr?oper=update",
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
			$(".popup_title").html("속성 등록");
			$("button#btnInsert").css("display", "inline-block");
			$("button#btnUpdate").remove();
			$("button#btnRemove").remove();			
		} else if (reqParam["mode"] === "R" || reqParam["mode"] === "U") {
			$(".popup_title").html("속성 수정");
			$("button#btnInsert").remove();
			$("button#btnRemove").remove();
			if (reqParam["mode"] === "R") {
				$("button#btnUpdate").remove();
				$("input, select, textarea, button[id=atrIdDupCheck]", "[de-role=form]").prop( "disabled", true );
			} else {
				$("input[id=ATR_ID], button[id=atrIdDupCheck]", "[de-role=form]").prop( "disabled", true );
				$("button#btnUpdate").css("display", "inline-block");
			}
			
			DE.ajax.call({"url":"admin/atr?oper=get", "data":{"atrId":reqParam["atrId"]}}, function(rsp){
				console.log(rsp);
				$("#ATR_ID").val(rsp["data"]["atrId"]);
				$("#ATR_NM").val(rsp["data"]["atrNm"]);
				$("#ATR_DESC").val(rsp["data"]["atrDesc"]);
				$("#DATA_TYPE_CD").val(rsp["data"]["dataTypeCd"]);
				$("#COL_LEN").val(rsp["data"]["colLen"]);
				$("#ATR_RFRN_CD").val(rsp["data"]["atrRfrnCd"]);
				$("#SQL_SBST").val(rsp["data"]["sqlSbst"]);
				$("#UI_COMP_CD").val(rsp["data"]["uiCompCd"]);
				$("#UI_COMP_WIDTH_RADIO").val(rsp["data"]["uiCompWidthRadio"]);
				$("#UI_COMP_HEIGHT_PX").val(rsp["data"]["uiCompHeightPx"]);
			});
		}
	};
	init();
});