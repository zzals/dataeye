$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
	
	$("button#appIdDupCheck").on("click", function(e) {
		if ($("#APP_ID").val() === "") {
			DE.box.alert(DE.i18n.prop("msg.app.required.appid"), function(){
				$("[id=APP_ID]").focusin();
			});
			return;
		}
		
		var rsp = DE.ajax.call({"async":false, "url":"system/app?oper=appIdDupCheck", "data":{"appId":$("#APP_ID").val()}});
		if (rsp["status"] === "SUCCESS") {
			DE.box.alert(rsp["message"]);
			$("input#APP_ID").attr("IS_VALID_CHECK", "Y");
		} else {
			$("input#APP_ID").attr("IS_VALID_CHECK", "N");
			DE.box.alert(rsp["message"]);	
		}
	});
		
	var makeForm = function() {
		return {
			"APP_ID":$("#APP_ID").val(),
			"APP_NM":$("#APP_NM").val(),
			"APP_DESC":$("#APP_DESC").val(),
			"DEL_YN":$("#DEL_YN").val(),
			"USE_YN":$("#USE_YN").val()
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
		
		if ($("#APP_ID").attr("is_valid_check") !== "Y") {
			DE.box.alert(DE.i18n.prop("msg.app.APP_ID.valid.check"));
			return;
		}
		
		var opts = {
			async:false,
			url : "system/app?oper=regist",
			data : $.param(makeForm())
		};
		
		DE.ajax.formSubmit(opts, callback.success, callback.error);
	});		

	$("button#btnUpdate").on("click", function(e) {
		var callback = {
			success : function(response) {
				DE.box.alert(response["message"], function(){ /*self.close();*/});
			},
			error : function(response) {
				DE.box.alert(response["responseJSON"]["message"]);
			}
		};
		

		var opts = {
			url : "system/app?oper=update",
			data : $.param(makeForm())
		};	
		
		DE.ajax.formSubmit(opts, callback.success, callback.error);
	});	

	var init = function() {
		if (reqParam["mode"] === "C") {
			$("button#btnInsert").css("display", "inline-block");
			$("button#btnUpdate").remove();
			$("button#btnRemove").remove();
		} else if (reqParam["mode"] === "R" || reqParam["mode"] === "U") {
			$("button#btnInsert").remove();
			$("button#btnRemove").remove();
			if (reqParam["mode"] === "R") {
				$("button#btnUpdate").remove();
				$("input, select, textarea", "[de-role=form]").prop( "disabled", true );
			} else {
				$("input#APP_ID, button[id=appIdDupCheck]").prop( "disabled", true );
				$("button#btnUpdate").css("display", "inline-block");
			}

			var rsp = DE.ajax.call({"async":false, "url":"system/app?oper=get", "data":{"appId":reqParam["appId"]}});
			$("#APP_ID").val(rsp["data"]["appId"]);
			$("#APP_NM").val(rsp["data"]["appNm"]);
			$("#APP_DESC").val(rsp["data"]["appDesc"]);
			$("#DEL_YN").val(rsp["data"]["delYn"]);
			$("#USE_YN").val(rsp["data"]["useYn"]);
		}
	};
	init();
});