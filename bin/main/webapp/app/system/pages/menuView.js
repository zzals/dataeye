$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
	
	var makeForm = function() {
		var getConfSbst = function() {
			var confSbst = {};
			var confEL = $("[id^=CONF_]");
			$.each(confEL, function(index, value){
				var id = $(value).prop("id");
				var val = $(value).val();
				eval("confSbst."+id+"=\""+val.replace(/"/g, "\\\"").replace(/\r?\n|\r/g, "\\n")+"\"");
			});
			
			return confSbst;
		};
		
		return {
			"MENU_ID":$("#MENU_ID").val(),
			"MENU_ADM_ID":$("#MENU_ADM_ID").val(),
			"MENU_NM":$("#MENU_NM").val(),
			"MENU_TYPE":$("#MENU_TYPE").val(),
			"MENU_GRP_CD":$("#MENU_GRP_CD").val(),
			"PGM_ID":$("#PGM_ID").val(),
			"UP_MENU_ID":$("#UP_MENU_ID").val(),
			"SORT_NO":$("#SORT_NO").val(),
			"ICON_CLASS":$("#ICON_CLASS").val(),
			"DEL_YN":$("#DEL_YN").val(),
			"USE_YN":$("#USE_YN").val(),
			"APP_ID":$("#APP_ID").val()
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
		
		var formData = makeForm();
		if (formData["MENU_TYPE"] !== "FOLDER" && formData["PGM_ID"] === "") {
			DE.box.alert("페이지 링크를 입력하세요.");
			return;
		}
		var opts = {
			async:false,
			url : "system/menu?oper=regist",
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
		var opts = {
			url : "system/menu?oper=update",
			data : $.param(makeForm())
		};	
		
		DE.ajax.formSubmit(opts, callback.success, callback.error);
	});	

	$("select#MENU_TYPE").on("change", function(e) {
		var selectedValue = $(":selected", this).val();
		if (selectedValue === "FOLDER") {
			$("#PGM_ID").val("");
			$("#PGM_ID").prop("disabled", true);
			$("#PGM_ID").closest(".form-group").hide();
		} else {
			$("#PGM_ID").prop("disabled", false);
			$("#PGM_ID").closest(".form-group").show();
		}
	});	
	
	var loadFolderMenu = function() {
		var rsp = DE.ajax.call({"async":false, "url":"system/menu?oper=getFolderMenu", "data":{"appId":reqParam["appId"]}});
		var data = rsp["data"];
		var space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        $.each(data, function(index, value){
			$("#UP_MENU_ID").append("<option value=\""+value["MENU_ID"]+"\">"+space.substring(0, (parseInt(this.LVL)-1)*24)+value["MENU_NM"]+"</option>");
		});
	};
	
	var init = function() {
		$("#SORT_NO").mask("00000", {reverse: true});
		$("#APP_ID").val(reqParam.appId);
		loadFolderMenu();
		
		if (reqParam["mode"] === "C") {
			$("button#btnInsert").css("display", "inline-block");
			$("button#btnUpdate").remove();
			$("button#btnRemove").remove();
			
		} else if (reqParam["mode"] === "R" || reqParam["mode"] === "U") {
			$("button#btnInsert").remove();
			$("button#btnRemove").remove();
			if (reqParam["mode"] === "R") {
				$("button#btnUpdate").remove();
			} else {
				$("button#btnUpdate").css("display", "inline-block");
			}

			var rsp = DE.ajax.call({"async":false, "url":"system/menu?oper=get", "data":{"menuId":reqParam["menuId"]}});
			$("#APP_ID").val(rsp["data"]["appId"]);
			$("#MENU_ID").val(rsp["data"]["menuId"]);
			$("#MENU_NM").val(rsp["data"]["menuNm"]);
			$("#MENU_ADM_ID").val(rsp["data"]["menuAdmId"]);
			$("#MENU_TYPE").val(rsp["data"]["menuType"]);
			$("#UP_MENU_ID").val(rsp["data"]["upMenuId"]);
			$("#MENU_GRP_CD").val(rsp["data"]["menuGrpCd"]);
			$("#PGM_ID").val(rsp["data"]["pgmId"]);
			$("#SORT_NO").val(rsp["data"]["sortNo"]);
			$("#ICON_CLASS").val(rsp["data"]["iconClass"]);
			$("#DEL_YN").val(rsp["data"]["delYn"]);	
			$("#USE_YN").val(rsp["data"]["useYn"]);			
		}
		
		$("select#MENU_TYPE").trigger("change");
	};
	init();
	$(window).trigger("resize");
});