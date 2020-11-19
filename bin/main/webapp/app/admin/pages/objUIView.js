$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
	
	var selOpts = {
		"SD" : {
			"" : [],
			"" : []
		}
	};
	
	var jsTpl = "<div class=\"form-group dyn-el\">";
	   jsTpl += "    <label for=\"FOR_NAME\" class=\"col-sm-2 control-label\"></label>";
	   jsTpl += "    <div class=\"col-sm-10\">";
	   jsTpl += "        <textarea id=\"CONF_NAME\" name=\"SQL_SBST1\" class=\"form-control\" rows=\"5\"></textarea>";
	   jsTpl += "    </div>";
	   jsTpl += "</div>";
	   
	var sqlTpl = "<div class=\"form-group dyn-el\">";
	   sqlTpl += "    <label for=\"FOR_NAME\" class=\"col-sm-2 control-label\">SQL 문</label>";
	   sqlTpl += "    <div class=\"col-sm-10\">";
	   sqlTpl += "        <textarea id=\"CONF_NAME\" name=\"SQL_SBST1\" class=\"form-control\" rows=\"5\"></textarea>";
	   sqlTpl += "        <span class=\"help-block\">사용가능 parameter : &#35;&#123;objTypeId&#125;, 	&#35;&#123;objId&#125;, 	&#35;&#123;userId&#125;</span>";
	   sqlTpl += "    </div>";
	   sqlTpl += "</div>";
	
	$("#USE_TYPE_CD").on("change", function(e){
		$("#UI_TYPE_CD").empty();
		$("#PGM_ID").val("");
		
		var v = $("option:selected", this).val();
		if (v === "WD") {
			$("#UI_TYPE_CD").append('<option selected="selected" value="SIMPLE_UI" pgm-id="common/metacore/ui.default.view">기본 샘플(ONE GRID)형</option>');
			$("#UI_TYPE_CD").append('<option value="SHUTTLE_LR_UI" pgm-id="common/metacore/ui.shuttle.view">기본 셔틀(LEFT-RIGHT)형</option>');
			$("#UI_TYPE_CD").append('<option value="INFLUENCE_UI" pgm-id="common/metacore/ui.influence.view">영향도 기본형</option>');
			$("#UI_TYPE_CD").append('<option value="CUST_UI">사용자정의</option>');
		} else if (v === "SD") {
			$("#UI_TYPE_CD").append('<option selected="selected" value="OBJ_LIST_TYPE1_UI" pgm-id="common/metacore/ui.objlisttype1.view">객체조회 기본형</option>');
		}
		$("#UI_TYPE_CD").trigger("change");
	});
	
	$("#UI_TYPE_CD").on("change", function(e){
		$("#PGM_ID").val($("option:selected", this).attr("pgm-id"));
		
		var value = $("option:selected", this).val();
		$(".form-group.dyn-el").remove();
		if ("SIMPLE_UI" === value || "INFLUENCE_UI" === value || "OBJ_LIST_TYPE1_UI" === value) {
			$("#PGM_ID").prop("disabled", true);
			
			var jsEl = $(jsTpl);
			$("label", jsEl).attr("for", "CONF_JS").html("SCRIPT");
			$("textarea", jsEl).attr("id", "CONF_JS").attr("name", "CONF_JS");
			$(this).closest(".box-body").append(jsEl);
			
			var sqlEl = $(sqlTpl);
			$("label", sqlEl).attr("for", "CONF_SQL1").html("QUERY");
			$("textarea", sqlEl).attr("id", "CONF_SQL1").attr("name", "CONF_SQL1");
			$(this).closest(".box-body").append(sqlEl);
		} else if ("SHUTTLE_LR_UI" === value) {
			$("#PGM_ID").prop("disabled", true);
			
			var jsEl = $(jsTpl);
			$("label", jsEl).attr("for", "CONF_JS").html("SCRIPT");
			$("textarea", jsEl).attr("id", "CONF_JS").attr("name", "CONF_JS");
			$(this).closest(".box-body").append(jsEl);
			
			var sqlEl = $(sqlTpl);
			$("label", sqlEl).attr("for", "CONF_SQL1").html("QUERY(LEFT)");
			$("textarea", sqlEl).attr("id", "CONF_SQL1").attr("name", "CONF_SQL1");
			$(this).closest(".box-body").append(sqlEl);
			
			sqlEl = $(sqlTpl);
			$("label", sqlEl).attr("for", "CONF_SQL2").html("QUERY(RIGHT)");
			$("textarea", sqlEl).attr("id", "CONF_SQL2").attr("name", "CONF_SQL2");
			$(this).closest(".box-body").append(sqlEl);
		} else if ("CUST_UI" === value) {
			$("#PGM_ID").prop("disabled", false);
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
			"UI_ID":$("#UI_ID").val(),
			"UI_NM":$("#UI_NM").val(),
			"USE_TYPE_CD":$("#USE_TYPE_CD").val(),
			"UI_TYPE_CD":$("#UI_TYPE_CD").val(),
			"PGM_ID":$("#PGM_ID").val(),
			"CONF_SBST":JSON.stringify(getConfSbst()),
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
			url : "admin/objui?oper=regist",
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
			url : "admin/objui?oper=update",
			data : $.param(makeForm())
		};	
		
		DE.ajax.formSubmit(opts, callback.success, callback.error);
	});	

	var init = function() {
		if (reqParam["mode"] === "C") {
			$("button#btnInsert").css("display", "inline-block");
			$("button#btnUpdate").remove();
			$("button#btnRemove").remove();
			
			$("#UI_TYPE_CD").trigger("change");
		} else if (reqParam["mode"] === "R" || reqParam["mode"] === "U") {
			$("button#btnInsert").remove();
			$("button#btnRemove").remove();
			if (reqParam["mode"] === "R") {
				$("button#btnUpdate").remove();
				$("input, select, textarea", "[de-role=form]").prop( "disabled", true );
			} else {
				$("#USE_TYPE_CD").prop( "disabled", true );
				$("button#btnUpdate").css("display", "inline-block");
			}
			
			var rsp = DE.ajax.call({"async":false, "url":"admin/objui?oper=get", "data":{"uiId":reqParam["uiId"]}});
			$("#UI_ID").val(rsp["data"]["uiId"]);
			$("#UI_NM").val(rsp["data"]["uiNm"]);
			$("#DEL_YN").val(rsp["data"]["delYn"]);
			$("#USE_TYPE_CD").val(rsp["data"]["useTypeCd"]).trigger("change");
			$("#UI_MODE_CD").val(rsp["data"]["uiModeCd"]);
			$("#UI_TYPE_CD").val(rsp["data"]["uiTypeCd"]);
			$("#PGM_ID").val(rsp["data"]["pgmId"]);
			
			if ($("#UI_TYPE_CD option:selected").val() !== "CUST_UI") {
				$("#UI_TYPE_CD").trigger("change");
				
				var confSbst = JSON.parse(rsp["data"]["confSbst"]);
				var confSbstKeys = $.keys(confSbst);
				$.each(confSbstKeys, function(index, value){
					$("#"+value).val(confSbst[value]);
				});
			}
		}
	};
	init();
});