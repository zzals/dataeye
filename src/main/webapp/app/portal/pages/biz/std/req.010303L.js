$(document).ready(function () {
	var parentObj = opener || parent;
	var reqParam = $("#reqParam").data();
	
	var isChange = false;
	var dmnTypeId = "010303L";
	
	var colModel = [
	    { index:'OBJ_NM', name: 'OBJ_NM', label: '표준도메인명', width: 100, align:'left',
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
    					["req."+dmnTypeId+"_1"],
    					{
    						viewname:'common/metacore/objectInfoTab',
    						objTypeId:rowData["OBJ_TYPE_ID"],
    						objId:rowData["OBJ_ID"],
    						collback:"aprvStdDmnChange",
    						mode:reqParam.mode,
    						isReq: true
    					},
    					null
    				);
	    		}
	    	}
      	},
	    { index:'ORACLE_DATA_TYPE', name: 'ORACLE_DATA_TYPE', label: 'ORACLE 타입', width: 100, align:'left'},
	    { index:'PDA_DATA_TYPE', name: 'PDA_DATA_TYPE', label: 'PDA 타입', width: 100, align:'left'},
	    { index:'TIBERO_DATA_TYPE', name: 'TIBERO_DATA_TYPE', label: 'TIBERO 타입', width: 100, align:'left'},
	    { index:'SYBASE_DATA_TYPE', name: 'SYBASE_DATA_TYPE', label: 'SYBASE 타입', width: 100, align:'left'},
	    { index:'DATA_LEN', name: 'DATA_LEN', label: '길이', width: 70, align:'left'},
	    { index:'DATA_SCALE', name: 'DATA_SCALE', label: '소수점', width: 70, align:'left'},
	    { index:'PATH_OBJ_NM', name: 'PATH_OBJ_NM', label: '도메인그룹명', width: 100, align:'left'},
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
      	{ index:'OBJ_DESC', name: 'OBJ_DESC', label: 'OBJ_DESC', hidden:true},
        { index:'OBJ_TYPE_ID', name: 'OBJ_TYPE_ID', label: 'OBJ_TYPE_ID', hidden:true},
        { index:'OBJ_ID', name: 'OBJ_ID', label: 'OBJ_ID', hidden:true},
        { index:'PATH_OBJ_TYPE_ID', name: 'PATH_OBJ_TYPE_ID', label: 'PATH_OBJ_TYPE_ID', hidden:true},
        { index:'PATH_OBJ_ID', name: 'PATH_OBJ_ID', label: 'PATH_OBJ_ID', hidden:true},
        { index:'IS_CHANGE', name: 'IS_CHANGE', label: 'IS_CHANGE', hidden:true}
    ];
	
	if (["R", "RO"].indexOf(reqParam.mode) !== -1) {
		colModel[8]["hidden"] = true;
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
	
	$("#btnAddDmn").on("click", function(e){
		DE.ui.open.popup(
			"view",
			[dmnTypeId, "APRV"],
			{
				viewname:"common/metacore/objectInfoTab",
				objTypeId:dmnTypeId,
				mode:"C",
				collback:"aprvStdDmnChange",
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
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 102, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["DATA_LEN"]},
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 103, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["DATA_SCALE"]},
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 111, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["ORACLE_DATA_TYPE"]},
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 112, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["PDA_DATA_TYPE"]},
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 113, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["TIBERO_DATA_TYPE"]},
				{"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"], "OBJ_ID" : obj["OBJ_ID"], "ATR_ID_SEQ" : 114, "ATR_VAL_SEQ" : 101, "OBJ_ATR_VAL": obj["SYBASE_DATA_TYPE"]}
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
				"OBJ_TYPE_ID" :dmnTypeId
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
					"OBJ_TYPE_ID" :dmnTypeId
				}
			});
			
			if(rsp.status = "SUCCESS") {
				$("#APRV_ID").val(rsp["data"]["aprvId"]);
				reqParam["mode"] = "U";
			} else {
				DE.box.alert("결재요청중 오류가 발생하였습니다.\n관리자에게 문의하세요.");
			}
		}
		
		APRV.open.aprvPopup($("#APRV_ID").val(), "req.010303L");		
	});

	$("#btnAprvDo").on("click", function() {
		APRV.open.aprvDoPopup($("#APRV_ID").val(), $("#APRV_DETL_ID").val(), "req.010303L");
	});
	
	var search = function(e) {
		var postData = {
			"queryId":"portal.biz.std.reqListForStdDmn",
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
			$("#btnAddDmn").show();
			$("#btnTempSave").show();
			$("#btnAprvReq").show();
			$("#btnAprvDo").remove();
		} else if (["R", "RO"].indexOf(reqParam.mode) !== -1) {
			$("#btnAddDmn").remove();
			$("#btnTempSave").remove();
			$("#btnAprvReq").remove();
			
			if (reqParam.aprvDo) {
				$("#btnAprvDo").show();
			} else {
				$("#btnAprvDo").remove();
			}
			
			search();
		} else if (reqParam.mode === "U") {
			$("#btnAddDmn").show();
			$("#btnTempSave").show();
			$("#btnAprvReq").show();
			$("#btnAprvDo").remove();

			search();
		}
	};
	init();
});

function aprvStdDmnChange(objInfo) {
	debugger;
	var rowid = $.jgrid.randId("jqg");
	var oracleDataType="", pdaDataType="", tiberoDataType="", sybaseDataType="";
	var dataLen="", dataScale="";
	
	$.each(objInfo["penObjDTs"], function(index, value){
		if (value["ATR_ID_SEQ"] == "102") {
			dataLen = value["OBJ_ATR_VAL"];
		} else if (value["ATR_ID_SEQ"] == "103") {
			dataScale = value["OBJ_ATR_VAL"];
		} else if (value["ATR_ID_SEQ"] == "111") {
			oracleDataType = value["OBJ_ATR_VAL"];
		} else if (value["ATR_ID_SEQ"] == "112") {
			pdaDataType = value["OBJ_ATR_VAL"];
		} else if (value["ATR_ID_SEQ"] == "113") {
			tiberoDataType = value["OBJ_ATR_VAL"];
		} else if (value["ATR_ID_SEQ"] == "114") {
			sybaseDataType = value["OBJ_ATR_VAL"];
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
		"DATA_LEN"         : dataLen,
		"DATA_SCALE"       : dataScale,
		"ORACLE_DATA_TYPE" : oracleDataType,
		"PDA_DATA_TYPE"    : pdaDataType,
		"TIBERO_DATA_TYPE" : tiberoDataType,
		"SYBASE_DATA_TYPE" : sybaseDataType,
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