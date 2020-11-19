$(document).ready(function () {
	var parentObj = opener || parent;
	var reqParam = $("#reqParam").data();
	
	var isChange = false;
	var wordTypeId = "010301L";
	
	var colModel = [
	    { index:'OBJ_NM', name: 'OBJ_NM', label: '표준단어명', width: 250, align:'left',
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
    					["req."+wordTypeId+"_1"],
    					{
    						viewname:'common/metacore/objectInfoTab',
    						objTypeId:rowData["OBJ_TYPE_ID"],
    						objId:rowData["OBJ_ID"],
    						collback:"aprvStdWordChange",
    						mode:reqParam.mode,
    						isReq: true
    					},
    					null
    				);
	    		}
	    	}
      	},
	    { index:'ADM_OBJ_ID', name: 'ADM_OBJ_ID', label: '영문약어명', width: 250, align:'left'},
	    { index:'OBJ_ABBR_NM', name: 'OBJ_ABBR_NM', label: '영문정식명', width: 250, align:'left'},
	    { index:'OBJ_ATR_VAL_101', name: 'OBJ_ATR_VAL_101', label: '분류어여부', width: 100, align:'center', fixed:true},
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
        { index:'IS_CHANGE', name: 'IS_CHANGE', label: 'IS_CHANGE', hidden:true}
    ];
	
	if (["R", "RO"].indexOf(reqParam.mode) !== -1) {
		colModel[4]["hidden"] = true;
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
	
	$("#btnAddWord").on("click", function(e){
		DE.ui.open.popup(
			"view",
			[wordTypeId, "APRV"],
			{
				viewname:"common/metacore/objectInfoTab",
				objTypeId:wordTypeId,
				mode:"C",
				collback:"aprvStdWordChange",
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
				"PATH_OBJ_TYPE_ID"  : "",
				"PATH_OBJ_ID"       : ""	
			};
			var penObjDTs = [{
				"OBJ_TYPE_ID" : obj["OBJ_TYPE_ID"],
				"OBJ_ID"      : obj["OBJ_ID"],
				"ATR_ID_SEQ"  : 101,
				"ATR_VAL_SEQ" : 101,
				"OBJ_ATR_VAL": obj["OBJ_ATR_VAL_101"]
			}];
			
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
				"OBJ_TYPE_ID" :wordTypeId
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
					"OBJ_TYPE_ID" :wordTypeId
				}
			});
			
			if(rsp.status = "SUCCESS") {
				$("#APRV_ID").val(rsp["data"]["aprvId"]);
				reqParam["mode"] = "U";
			} else {
				DE.box.alert("결재요청중 오류가 발생하였습니다.\n관리자에게 문의하세요.");
			}
		}
		
		APRV.open.aprvPopup($("#APRV_ID").val(), "req.010301L");		
	});

	$("#btnAprvDo").on("click", function() {
		APRV.open.aprvDoPopup($("#APRV_ID").val(), $("#APRV_DETL_ID").val(), "req.010301L");
	});
	
	var search = function(e) {
		var postData = {
			"queryId":"portal.biz.std.reqListForStdWord",
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
			$("#btnAddWord").show();
			$("#btnTempSave").show();
			$("#btnAprvReq").show();
			$("#btnAprvDo").remove();
		} else if (["R", "RO"].indexOf(reqParam.mode) !== -1) {
			$("#btnAddWord").remove();
			$("#btnTempSave").remove();
			$("#btnAprvReq").remove();
			
			if (reqParam.aprvDo) {
				$("#btnAprvDo").show();
			} else {
				$("#btnAprvDo").remove();
			}
			
			search();
		} else if (reqParam.mode === "U") {
			$("#btnAddWord").show();
			$("#btnTempSave").show();
			$("#btnAprvReq").show();
			$("#btnAprvDo").remove();

			search();
		}
	};
	init();
});

function aprvStdWordChange(objInfo) {
	var rowid = $.jgrid.randId("jqg");
	var rowData = {
		"OBJ_TYPE_ID"     : objInfo["penObjMT"]["OBJ_TYPE_ID"],
		"OBJ_ID"          : objInfo["penObjMT"]["OBJ_ID"],
		"ADM_OBJ_ID"      : objInfo["penObjMT"]["ADM_OBJ_ID"],
		"OBJ_NM"          : objInfo["penObjMT"]["OBJ_NM"],
		"OBJ_ABBR_NM"     : objInfo["penObjMT"]["OBJ_ABBR_NM"],
		"OBJ_DESC"        : objInfo["penObjMT"]["OBJ_DESC"],
		"OBJ_ATR_VAL_101" : objInfo["penObjDTs"][0]["OBJ_ATR_VAL"],
	    "IS_CHANGE"       : "Y"
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