$(document).ready(function() {
	var appSysTypeId = "050102L";
	var dbInstTypeId = "020100L";
	var databaseTypeId = "020101L";
	var tabTypeId = "020102L";
	var tabColTypeId = "020104L";
	var viewTypeId = "020103L";
	var viewColTypeId = "020105L";
	var procTypeId = "020106L";
	
	_layout = $('#container').layout({
		center : {
			onresize: function() {
				$(document).trigger("autoResize");
			}
		}
		,	south : {
			size : 400,
			onresize: function() {
				$(document).trigger("autoResize");
			}
		}
	});
	
	var colModel = [	          
		{ index:'OBJ_NM', name: 'OBJ_NM', label: '뷰물리명', width: 100, align:'left', 
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $(this).jqGrid("getRowData", rowid);
	    			DE.ui.open.popup(
    					"view",
    					[rowData["OBJ_TYPE_ID"], rowData["OBJ_ID"]],
    					{
    						viewname:'common/metacore/objectInfoTab',
    						objTypeId:rowData["OBJ_TYPE_ID"],
    						objId:rowData["OBJ_ID"],
    						mode:'RO'
    					},
    					null
    				);
	    		}
	    	}
      	},
      	{ index:'OBJ_ABBR_NM', name: 'OBJ_ABBR_NM', label: '뷰논리명', width: 100, align:'left'},
      	{ index:'DB_NM', name: 'DB_NM', label: '데이터베이스명', width: 100, align:'left'},
      	{ index:'SCHEMA_NM', name: 'SCHEMA_NM', label: '스키마명', width: 100, align:'left'},
      	{ index:'OWNER_NM', name: 'OWNER_NM', label: '소유자명', width: 100, align:'left'},
      	{ index:'CRET_DT', name: 'CRET_DT', label: '생성일시', width: 100, align:'center', formatter: 'dateTimeFormat'},
      	{ index:'CHG_DT', name: 'CHG_DT', label: '수정일시', width: 100, align:'center', formatter: 'dateTimeFormat'},
      	{ index:'OBJ_TYPE_ID', name: 'OBJ_TYPE_ID', label: 'OBJ_TYPE_ID', hidden:true},
      	{ index:'OBJ_ID', name: 'OBJ_ID', label: 'OBJ_ID', hidden:true},
      	{ index:'DB_OBJ_TYPE_ID', name: 'DB_OBJ_TYPE_ID', label: 'DB_OBJ_TYPE_ID', hidden:true},
      	{ index:'DB_OBJ_ID', name: 'DB_OBJ_ID', label: 'DB_OBJ_ID', hidden:true}
    ];

	var lastSelectedRowId = "";
	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		autowidth:true,
		shrinkToFit:true,
		toppager: false,
		loadonce: true,
		scroll:-1,
		isPaging:false,
		onSelectRow: function(rowid, status, e) {
			lastSelectedRowId = rowid;
			searchDetail();
        },
		loadComplete: function (data) {
			if (!lastSelectedRowId) {
				lastSelectedRowId = $(this).jqGrid("getDataIDs")[0];
				
				if (lastSelectedRowId) {
					$(this).jqGrid("setSelection", lastSelectedRowId);
				} else {
					$("#jqGrid2").jqGrid("clearGridData");
				}				
			}
        }
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"));
	
	$("button#btnSearch").on("click", function(e) {
		if (!$("#selDatabase").val()) {
			DE.box.alert("선택된 데이터베이스가 없습니다.");
			return;
		}
		lastSelectedRowId = "";
		
		var postData = {
			"queryId":"tech.db.findProc",
			"objTypeId":procTypeId, 
			"pathObjTypeId":databaseTypeId, 
			"pathObjId":$("#selDatabase").val(), 
			"searchKey":$("#searchKey").val(),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	var searchDetail = function() {
		var rowid = $("#jqGrid").jqGrid("getGridParam", "selrow");
		var tabIndex = $("#subTab1 li.active").index();
		
		if (tabIndex === 0) {
			var getDDLScript = function(postData) {
				var opts = {
					url : "portal/metapublic?oper=getObjAtrVal",
					data : postData
				};	
				var callback = {
					success : function(response) {
						var data = response["data"];
						if (data.length === 0 || (data.length === 1 && !data[0]["OBJ_ATR_VAL"])) {
							$("#ddlScript").text("데이터가 없습니다.");							
						} else {
							var script = "";
							$.each(data, function(index, value){
								script += value["OBJ_ATR_VAL"]
							});
							$("#ddlScript").text(script);
						}
					},
					error : function(response) {
						DE.box.alert(response["responseJSON"]["message"]);
					}
				};
				DE.ajax.call(opts, callback.success, callback.error);
			};
			
			if (rowid) {
				var rowObject = $("#jqGrid").jqGrid("getRowData", rowid);
				var postData = {
					"objTypeId": rowObject["OBJ_TYPE_ID"],
					"objId": rowObject["OBJ_ID"],
					"atrIdSeq": 105
				};
				getDDLScript(postData);
			} else {
				$("#ddlScript").text("데이터가 없습니다.");
			}
		}
	};
	
	$(this).off("autoResize").on( "autoResize", function( event ) {
	    setTimeout(function () {
	    	var heightMargin = 70;
	    	var heightMargin2 = 110;
	    	var heightMargin3 = 50;
	    	
	    	$("#jqGrid").setGridWidth($(".ui-layout-center .box-body").width(),opts["autowidth"]);
	    	$("#jqGrid").setGridHeight($(".ui-layout-center").height() - ($(".ui-layout-center .box-body").offset().top-$(".ui-layout-center").offset().top)-heightMargin);
	    	
	    	if(opts["autowidth"]==true){
				$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
			  }
	    		    	
	    	$("#ddlScript").css("height", $(".ui-layout-south").height() - ($(".ui-layout-south .box-body").offset().top-$(".ui-layout-south").offset().top)-heightMargin3);
    	}, 500);
	}).trigger("autoResize");

	$("#selAppSys").on("change", function(e){
		if(!$(this).val()) {
			$("#selDbInst option").remove();
			clearGridData();
			return;
		}
		makeDbInstSelect();
	});
	
	$("#selDbInst").on("change", function(e){
		if(!$(this).val()) {
			$("#selDatabase option").remove();
			clearGridData();
			return;
		}
		makeDatabaseSelect();
	});
	
	var clearGridData = function() {
		$("#jqGrid2").jqGrid("clearGridData");
		$("#jqGrid").jqGrid("clearGridData");
	};
	
	var makeAppSysSelect = function(e) {
		DE.ajax.call({async:true, url:"metapublic/list", data:{"queryId":"metapublic.findObjSelectBox", "objTypeId":appSysTypeId}}, function(rsp) {
			DE.util.selectBox($("#selAppSys"), rsp["data"], {"value":"objId", "text":"objNm", "isAll":false});
			$("#selAppSys").trigger("change");
		});
	};
	
	var makeDbInstSelect = function(e) {
		DE.ajax.call({async:true, url:"metapublic/list", data:{"queryId":"metapublic.findObjSelectBox", "objTypeId":dbInstTypeId, "pathObjTypeId":appSysTypeId, "pathObjId":$("#selAppSys").val()}}, function(rsp) {
			DE.util.selectBox($("#selDbInst"), rsp["data"], {"value":"objId", "text":"objNm", "isAll":false});
			$("#selDbInst").trigger("change");
		});
	};
	
	var makeDatabaseSelect = function(e) {
		DE.ajax.call({async:true, url:"metapublic/list", data:{"queryId":"metapublic.findObjSelectBox", "objTypeId":databaseTypeId, "pathObjTypeId":dbInstTypeId, "pathObjId":$("#selDbInst").val()}}, function(rsp) {
			DE.util.selectBox($("#selDatabase"), rsp["data"], {"value":"objId", "text":"objNm", "isAll":false});
			$("button#btnSearch").trigger("click");
		});
	};
	
	$("#subTab1").on("shown.bs.tab", function (e) {
		searchDetail();
	});
	
	var init = function() {
		$("#selAppSys").select2();
		$("#selDbInst").select2();
		$("#selDatabase").select2();
		makeAppSysSelect();
	};
	init();
});