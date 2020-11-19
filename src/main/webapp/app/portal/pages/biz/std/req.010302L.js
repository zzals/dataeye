$(document).ready(function () {
	var parentObj = opener || parent;
	var reqParam = $("#reqParam").data();
	
	var isChange = false;
	var termTypeId = "010302L";
	
	var colModel = [
	    { index:'OBJ_NM', name: 'OBJ_NM', label: '표준용어명', width: 100, align:'left',
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);
	    			if (rowData["IS_CHANGE"] === "Y") {
						DE.box.alert("임시저장 후 수정하세요.");
						return;
	    			}
	    			
	    			DE.ui.open.popup(
    					"view",
    					["req."+termTypeId+"_1"],
    					{
    						viewname:'common/metacore/objectInfoTab',
    						objTypeId:rowData["OBJ_TYPE_ID"],
    						objId:rowData["OBJ_ID"],
    						collback:"aprvStdTermChange",
    						mode:reqParam.mode,
    						isReq: true
    					},
    					null
    				);
	    		}
	    	}
      	},
	    { index:'ADM_OBJ_ID', name: 'ADM_OBJ_ID', label: '영문약어명', width: 100, align:'left'},
	    { index:'DMN_NM', name: 'DMN_NM', label: '표준도메인명', width: 100, align:'left'},
	    { index:'ACTION', name: 'ACTION', label: 'ACTION', width: 80, align:'center', title:false,
	    	formatter: "customButton", 
	    	formatoptions: {
	    		caption:"삭제",
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			$("#jqGrid").jqGrid("delRowData", rowid);
	    			isChange = true;
	    		}
	    	}
      	},
      	{ index:'OBJ_TYPE_ID', name: 'OBJ_TYPE_ID', label: 'OBJ_TYPE_ID', hidden:true},
        { index:'OBJ_ID', name: 'OBJ_ID', label: 'OBJ_ID', hidden:true},
        { index:'PATH_OBJ_TYPE_ID', name: 'PATH_OBJ_TYPE_ID', label: 'PATH_OBJ_TYPE_ID', hidden:true},
        { index:'PATH_OBJ_ID', name: 'PATH_OBJ_ID', label: 'PATH_OBJ_ID', hidden:true},
        { index:'DMN_ID', name: 'DMN_ID', label: 'DMN_ID', hidden:true},
        { index:'WORD_ID_01', name: 'WORD_ID_01', label: 'WORD_ID_01', hidden:true},
        { index:'WORD_ID_02', name: 'WORD_ID_02', label: 'WORD_ID_02', hidden:true},
        { index:'WORD_ID_03', name: 'WORD_ID_03', label: 'WORD_ID_03', hidden:true},
        { index:'WORD_ID_04', name: 'WORD_ID_04', label: 'WORD_ID_04', hidden:true},
        { index:'WORD_ID_05', name: 'WORD_ID_05', label: 'WORD_ID_05', hidden:true},
        { index:'WORD_ID_06', name: 'WORD_ID_06', label: 'WORD_ID_06', hidden:true},
        { index:'WORD_ID_07', name: 'WORD_ID_07', label: 'WORD_ID_07', hidden:true},
        { index:'WORD_ID_08', name: 'WORD_ID_08', label: 'WORD_ID_08', hidden:true},
        { index:'WORD_ID_09', name: 'WORD_ID_09', label: 'WORD_ID_09', hidden:true},
        { index:'IS_CHANGE', name: 'IS_CHANGE', label: 'IS_CHANGE', hidden:true}
    ];
	
	if (["R", "RO"].indexOf(reqParam.mode) !== -1) {
		colModel[3]["hidden"] = true;
	}
	
	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		isPaging:false,
		loadonce: false,
		height:310,
		scroll:-1,
		autowidth:true,
		shrinkToFit:true
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"));
	
	$("#btnAddTerm").on("click", function(e){
		DE.ui.open.popup(
			"view",
			[termTypeId, "APRV"],
			{
				viewname:"common/metacore/objectInfoTab",
				objTypeId:termTypeId,
				mode:"C",
				collback:"aprvStdTermChange",
				isReq: true
			}
		);
	});
	
	var makeObjInfo = function() {
		var objInfos = [];
		var makeObject = function(obj) {
			var penObjMT = {
				"OBJ_TYPE_ID"       : obj["OBJ_TYPE_ID"],
				"OBJ_ID"            : obj["OBJ_ID"],
				"ADM_OBJ_ID"        : obj["ADM_OBJ_ID"],
				"OBJ_NM"            : obj["OBJ_NM"],
				"OBJ_ABBR_NM"       : obj["OBJ_ABBR_NM"],
				"OBJ_DESC"          : obj["OBJ_DESC"],
				"PATH_OBJ_TYPE_ID"  : obj["PATH_OBJ_TYPE_ID"],
				"PATH_OBJ_ID"       : obj["PATH_OBJ_ID"]	
			};
			var penObjDTs = [
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 101, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["DMN_ID"]},
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 102, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["WORD_ID_01"]},
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 103, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["WORD_ID_02"]},
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 104, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["WORD_ID_03"]},
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 105, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["WORD_ID_04"]},
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 106, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["WORD_ID_05"]},
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 107, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["WORD_ID_06"]},
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 108, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["WORD_ID_07"]},
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 109, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["WORD_ID_08"]},
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 110, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["WORD_ID_09"]}
			];
			
			return {
				"penObjMT"  : penObjMT,
				"penObjDTs" : penObjDTs
			}
		};

		var data = $("#jqGrid").jqGrid("getGridParam", "data");
		$.each(data, function(index, value) {
			objInfos.push(makeObject(value));
		});
		
		return objInfos;
	};
	
	var tempSaveAction = function() {
		DE.ajax.call({
			"url":"portal/biz/std?oper=aprvTemp",
			"data":{
				"objInfos":makeObjInfo(), 
				"APRV_ID":$("#APRV_ID").val(),
				"APRV_TYPE_CD":"APRV_STD_ALL",
				"OBJ_TYPE_ID" :termTypeId
			}
		}, function(rsp) {
			if(rsp.status = "SUCCESS") {
				$("#APRV_ID").val(rsp["data"]["aprvId"]);
				reqParam["mode"] = "U";
				DE.box.alert("저장되었습니다.");
				search();
			}
		});
	};
	
	$("#btnTempSave").on("click", function(e) {
		DE.box.confirm("임시저장 하시겠습니까?", function(b){
			if(b === true) {
				tempSaveAction();
			}
		});
	});
	
	$("#btnAprvReq").on("click", function() {
		if ($("#jqGrid").jqGrid("getGridParam", "reccount") === 0) {
			DE.box.alert("결재 대상 정보가 없습니다.");
			return;
		}
		
		if (!isChange) {
			var data = $("#jqGrid").jqGrid("getGridParam", "data");
			$.each(data, function(index, value) {
				if ("Y" === value["IS_CHANGE"]) {
					isChange = true;
					return false;
				}
			});
		}
		
		if (isChange) {
			var rsp = DE.ajax.call({
				"async":false,
				"url":"portal/biz/std?oper=aprvTemp",
				"data":{
					"objInfos":makeObjInfo(), 
					"APRV_ID":$("#APRV_ID").val(),
					"APRV_TYPE_CD":"APRV_STD_ALL",
					"OBJ_TYPE_ID" :termTypeId
				}
			});
			
			if(rsp.status = "SUCCESS") {
				$("#APRV_ID").val(rsp["data"]["aprvId"]);
				reqParam["mode"] = "U";
			} else {
				DE.box.alert("결재요청중 오류가 발생하였습니다.\n관리자에게 문의하세요.");
			}
		}
		
		APRV.open.aprvPopup($("#APRV_ID").val(), "req.010302L");		
	});

	$("#btnAprvDo").on("click", function() {
		APRV.open.aprvDoPopup($("#APRV_ID").val(), $("#APRV_DETL_ID").val(), "req.010302L");
	});
	
	var search = function(e) {
		var postData = {
			"queryId":"portal.biz.std.reqListForStdTerm",
			"aprvId":$("#APRV_ID").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
		isChange = false;
	};
	
	$(this).off("autoResize").on( "autoResize", function( event ) {
	    setTimeout(function () {
	    	var heightMargin = 50;
	    		
	    	$("#jqGrid").setGridWidth($(".content-body .box-body").width(),opts["autowidth"]);
	    	$("#jqGrid").setGridHeight($(".content-body").height() - ($(".content-body .box-body").offset().top-$(".content-body").offset().top)-heightMargin);
	    	
	      	if(opts["autowidth"]==true){
			$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
		  }
	    	
    	}, 500);
	}).trigger("autoResize");

	var init = function() {
		$("#APRV_ID").val(reqParam["aprvId"]);
		$("#APRV_DETL_ID").val(reqParam["aprvDetlId"]);
		
		if (reqParam.mode === "C") {
			$("#btnAddTerm").show();
			$("#btnTempSave").show();
			$("#btnAprvReq").show();
			$("#btnAprvDo").remove();
		} else if (["R", "RO"].indexOf(reqParam.mode) !== -1) {
			$("#btnAddTerm").remove();
			$("#btnTempSave").remove();
			$("#btnAprvReq").remove();
			
			if (reqParam.aprvDo) {
				$("#btnAprvDo").show();
			} else {
				$("#btnAprvDo").remove();
			}
			
			search();
		} else if (reqParam.mode === "U") {
			$("#btnAddTerm").show();
			$("#btnTempSave").show();
			$("#btnAprvReq").show();
			$("#btnAprvDo").remove();

			search();
		}
	};
	init();
});

function aprvStdTermChange(objInfo) {
	debugger;
	var rowid = $.jgrid.randId("jqg");
	var wordId01="", wordId02="", wordId03="", wordId04="", wordId05="", wordId06="", wordId07="", wordId08="", wordId09="";
	var dmnId="", dmnNm="";
	
	$.each(objInfo["penObjDTs"], function(index, value){
		if (value["ATR_ID_SEQ"] == "101") {
			dmnId = value["OBJ_ATR_VAL"];
			dmnNm = value["OBJ_ATR_VAL_NM"];
		} else if (value["ATR_ID_SEQ"] == "102") {
			wordId01 = value["OBJ_ATR_VAL"];
		} else if (value["ATR_ID_SEQ"] == "103") {
			wordId02 = value["OBJ_ATR_VAL"];
		} else if (value["ATR_ID_SEQ"] == "104") {
			wordId03 = value["OBJ_ATR_VAL"];
		} else if (value["ATR_ID_SEQ"] == "105") {
			wordId04 = value["OBJ_ATR_VAL"];
		} else if (value["ATR_ID_SEQ"] == "106") {
			wordId05 = value["OBJ_ATR_VAL"];
		} else if (value["ATR_ID_SEQ"] == "107") {
			wordId06 = value["OBJ_ATR_VAL"];
		} else if (value["ATR_ID_SEQ"] == "108") {
			wordId07 = value["OBJ_ATR_VAL"];
		} else if (value["ATR_ID_SEQ"] == "109") {
			wordId08 = value["OBJ_ATR_VAL"];
		} else if (value["ATR_ID_SEQ"] == "110") {
			wordId09 = value["OBJ_ATR_VAL"];
		}
	});
		
	var rowData = {
		"OBJ_TYPE_ID"      : objInfo["penObjMT"]["OBJ_TYPE_ID"],
		"OBJ_ID"           : objInfo["penObjMT"]["OBJ_ID"],
		"ADM_OBJ_ID"       : objInfo["penObjMT"]["ADM_OBJ_ID"],
		"OBJ_NM"           : objInfo["penObjMT"]["OBJ_NM"],
		"OBJ_ABBR_NM"      : objInfo["penObjMT"]["OBJ_ABBR_NM"],
		"OBJ_DESC"         : objInfo["penObjMT"]["OBJ_DESC"],
		"PATH_OBJ_TYPE_ID" : objInfo["penObjMT"]["PATH_OBJ_TYPE_ID"],
		"PATH_OBJ_ID"      : objInfo["penObjMT"]["PATH_OBJ_ID"],
		"PATH_OBJ_NM"      : objInfo["penObjMT"]["PATH_OBJ_NM"],
		"DMN_ID"           : dmnId,
		"DMN_NM"           : dmnNm,
		"WORD_ID_01"       : wordId01,
		"WORD_ID_02"       : wordId02,
		"WORD_ID_03"       : wordId03,
		"WORD_ID_04"       : wordId04,
		"WORD_ID_05"       : wordId05,
		"WORD_ID_06"       : wordId06,
		"WORD_ID_07"       : wordId07,
		"WORD_ID_08"       : wordId08,
		"WORD_ID_09"       : wordId09,
		"IS_CHANGE"        : "Y"
	};
	
	var objId = objInfo["penObjMT"]["OBJ_ID"];
	if (objId === "") {
		$("#jqGrid").jqGrid("addRowData", rowid, rowData, "last");
	} else {
		var data = $("#jqGrid").jqGrid("getGridParam", "data");
		$.each(data, function(index, value) {
			if (objId === value["OBJ_ID"]) {
				rowid = value["_id_"];
				return false;
			}
		});
		$("#jqGrid").jqGrid('setRowData', rowid, rowData);
	}
}

var fn_aprv_callback=function(data){
	if(data.rstCd==='SUCC'){
		var parentObj = opener || parent;
		if (parentObj.$("#btnSearch").length > 0) {
			parentObj.$("#btnSearch").trigger("click");
		}
		self.close();
	}else{
		
	}
}