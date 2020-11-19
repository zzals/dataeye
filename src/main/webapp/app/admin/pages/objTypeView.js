$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
	

	$("button#objTypeIdDupCheck").on("click", function(e) {
		if ($("#OBJ_TYPE_ID").val() === "") {
			DE.box.alert(DE.i18n.prop("msg.objtypoe.required.objtypeid"), function(){
				$("[id=OBJ_TYPE_ID]").focusin();
			});
			return;
		}
		
		var rsp = DE.ajax.call({"async":false, "url":"admin/objtype?oper=objTypeIdDuplChk", "data":{"objTypeId":$("#OBJ_TYPE_ID").val()}});
		if (rsp["status"] === "SUCCESS") {
			DE.box.alert(rsp["message"]);
		} else {
			DE.box.alert(rsp["message"]);	
		}
	});
	
	
	
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
			"OBJ_TYPE_ID":$("#OBJ_TYPE_ID").val(),
			"OBJ_TYPE_NM":$("#OBJ_TYPE_NM").val(),
			"OBJ_TYPE_DESC":$("#OBJ_TYPE_DESC").val(),
			"SORT_NO":$("#SORT_NO").val(),
			"UP_OBJ_TYPE_ID":$("#UP_OBJ_TYPE_ID").val(),
			"HIER_LEV_NO":$("#HIER_LEV_NO").val(),
			"LST_LEV_YN":$("#LST_LEV_YN").val(),
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
		
		var opts = {
			async:false,
			url : "admin/objtype?oper=regist",
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
			url : "admin/objtype?oper=update",
			data : $.param(makeForm())
		};	
		
		DE.ajax.formSubmit(opts, callback.success, callback.error);
	});	
	
	var init = function() {
		DE.ui.render.selectBox($("#ATR_ADM_CD"), DE.code.get("SYS_ATRMA"), {"valueKey":"cdId", "nameKey":"cdNm"});
		
		
		
		//상위 객체 유형
		var rsp = DE.ajax.call({"async":false, "url":"admin/objtype?oper=getTobObjType", "data":{}});
		
	
		$("#UP_OBJ_TYPE_ID").append("<option value=''>ROOT</option>");
		
		$.each( rsp.data, function( i, topObjType ){
			var OBJ_TYPE_ID =  topObjType["OBJ_TYPE_ID"];
			var OBJ_TYPE_NM =  topObjType["OBJ_TYPE_NM"];
			var HIER_LEV_NO =  topObjType["HIER_LEV_NO"];
			
			var space = "";
			for(var i =0;i <HIER_LEV_NO;i++ ){
				space += "&nbsp;&nbsp;&nbsp;&nbsp;"
			}
			
			$("#UP_OBJ_TYPE_ID").append("<option value='" + OBJ_TYPE_ID + "'>" + space + OBJ_TYPE_NM + "</option>");
		});

		
	if (reqParam["mode"] === "C") {
		$("button#btnInsert").css("display", "inline-block");
		$("button#btnUpdate").remove();
		$("button#btnRemove").remove();
		
		
		
		$("#OBJ_TYPE_ID").attr("readonly" ,false); //해제
		
		

		
	} else if (reqParam["mode"] === "R" || reqParam["mode"] === "U") {
		
	
		
		$("#OBJ_TYPE_ID").attr("readonly" ,true); //설정
		$("button#btnInsert").remove();
		$("button#btnRemove").remove();
		if (reqParam["mode"] === "R") {
			$("button#btnUpdate").remove();
		} else {
			$("button#btnUpdate").css("display", "inline-block");
		}
		
			var rsp = DE.ajax.call({"async":false, "url":"admin/objtype?oper=get", "data":{"objTypeId":reqParam["objTypeId"]}});
			$("#OBJ_TYPE_ID").val(rsp["data"]["objTypeId"]);
			$("#OBJ_TYPE_NM").val(rsp["data"]["objTypeNm"]);
			$("#OBJ_TYPE_DESC").val(rsp["data"]["objTypeDesc"]);
			
			$("#UP_OBJ_TYPE_ID").val(rsp["data"]["upObjTypeId"]).attr("selected", "selected");
			$("#SORT_NO").val(rsp["data"]["sortNo"]);
			$("#HIER_LEV_NO").val(rsp["data"]["hierLevNo"]);
			$("#LST_LEV_YN").val(rsp["data"]["lstLevYn"]);
	}
	
	
	};
	init();
});