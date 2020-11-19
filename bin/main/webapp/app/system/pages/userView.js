$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
	
	$("input#USER_ID").on("change", function(e) {
		$("input#USER_ID").attr("IS_VALID_CHECK", "N");
	});
	
	$("button#userIdDupCheck").on("click", function(e) {
		if ($("#USER_ID").val() === "") {
			DE.box.alert(DE.i18n.prop("msg.user.required.USER_ID"), function(){
				$("[id=USER_ID]").focusin();
			});
			return;
		}
		
		var rsp = DE.ajax.call({"async":false, "url":"system/user?oper=dupCheck", "data":{"userId":$("#USER_ID").val()}});
		if (rsp["status"] === "SUCCESS") {
			DE.box.alert(rsp["message"]);
			$("input#USER_ID").attr("IS_VALID_CHECK", "Y");
		} else {
			DE.box.alert(rsp["message"]);	
		}
	});
	
	var makeForm = function() {
		return {
			"USER_ID":$("#USER_ID").val(),
			"USER_NM":$("#USER_NM").val(),
			"ORG_ID":$("#ORG_ID").val(),
			"UP_ORG_ID":$("#UP_ORG_ID").val(),
			"EMAIL_ADDR":$("#EMAIL_ADDR").val(),
			"TEL_NO":$("#TEL_NO").val(),
			"HP_NO":$("#HP_NO").val(),
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
		
		if ($("#USER_ID").attr("IS_VALID_CHECK") !== "Y") {
			DE.box.alert(DE.i18n.prop("msg.user.required.USER_ID"));
			return;
		}
		var opts = {
			async:false,
			url : "system/user?oper=regist",
			data : $.param(makeForm())
		};
		
		DE.ajax.formSubmit(opts, callback.success, callback.error);
		DE.jqgrid.reload($("#jqGrid"), opts);
	});		
	
	// update
	$("button#btnUpdate").on("click", function(e) {
		var callback = {
			success : function(response) {
				DE.box.alert(response["message"], function(){ self.close();});
			},
			error : function(response) {
				DE.box.alert(response["responseJSON"]["message"]);
			}
		};
		var opts = {
			url : "system/user?oper=update",
			data : $.param(makeForm())
		};	
		
		DE.ajax.formSubmit(opts, callback.success, callback.error);
	
	});	
	
	

	var loadOrgNmList = function() {
		
		var rsp = DE.ajax.call({"async":false, "url":"system/user?oper=getOrgNm", "data":{}});
		var data = rsp["data"];
		var space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        $.each(data, function(index, value){
			$("#ORG_ID").append("<option value=\""+value["ORG_ID"]+"\">"+space.substring(0, (parseInt(this.LVL)-1)*24)+value["ORG_NM"]+"</option>");
		});
	};
	
	var init = function() {


		
		loadOrgNmList();
		
		if (reqParam["mode"] === "C") {
			$(".popup_title").html("사용자 등록");
			$(".popup_title").show();
			$("button#btnInsert").css("display", "inline-block");
			$("button#btnUpdate").remove();
			$("button#btnRemove").remove();
			
		} else if (reqParam["mode"] === "R" || reqParam["mode"] === "U") {
			$(".popup_title").html("사용자 수정");
			$(".popup_title").show();
			$("button#btnInsert").remove();
			$("button#btnRemove").remove();

			if (reqParam["mode"] === "R") {
				$("button#btnUpdate").remove();
				$("input, select, textarea, button[id=userIdDupCheck]", "[de-role=form]").prop( "disabled", true );
			} else {
				$("input[id=USER_ID], button[id=userIdDupCheck]", "[de-role=form]").prop( "disabled", true );
				$("button#btnUpdate").css("display", "inline-block");
			}

			DE.ajax.call({"url":"system/user?oper=get", "data":{"userId":reqParam["userId"]}}, function(rsp){
				$("#USER_ID").val(rsp["data"]["userId"]);
				$("#USER_NM").val(rsp["data"]["userNm"]);
				$("#ORG_ID").val(rsp["data"]["orgId"]);
				$("#UP_ORG_ID").val(rsp["data"]["upOrgId"]);
				$("#EMAIL_ADDR").val(rsp["data"]["emailAddr"]);
				$("#TEL_NO").val(rsp["data"]["telNo"]);
				$("#HP_NO").val(rsp["data"]["hpNo"]);	
			});
	    }
	};
	init();  
	
	
});