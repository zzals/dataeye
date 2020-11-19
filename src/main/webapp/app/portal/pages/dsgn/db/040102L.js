$(document).ready(function() {
	var appSysTypeId = "050102L";
	var dbInstTypeId = "020100L";
	var databaseTypeId = "020101L";
	var dsgnTabTypeId = "040102L";
	var dsgnColTypeId = "040103L";
	
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
		{name:'CHK',index:'CHK', label:"<input type='checkbox' id='chkAll' name='chkAll' onclick='javascript:DE.jqgrid.checkAll(this, event);'/>", width:55, align:"center",
			sortable:false,
			formatter: 'checkbox',
			formatoptions: {disabled:false},
			edittype:'checkbox',
			editoptions:{value:"true:false"},
			editable:true
		},
	    { index:'OBJ_NM', name: 'OBJ_NM', label: '테이블논리명', width: 100, align:'left', 
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
    						mode:'R'
    					},
    					null
    				);
	    		}
	    	}
      	},
      	{ index:'OBJ_ABBR_NM', name: 'OBJ_ABBR_NM', label: '테이블물리명', width: 100, align:'left'},
      	{ index:'OBJ_DESC', name: 'OBJ_DESC', label: '설명', width: 200, align:'left'},
      	{ index:'DB_NM', name: 'DB_NM', label: '데이터베이스명', width: 100, align:'left'},
      	{ index:'SCHEMA_NM', name: 'SCHEMA_NM', label: '스키마명', width: 100, align:'left'},
      	{ index:'OBJ_CLS', name: 'OBJ_CLS', label: '형태구분', width: 100, align:'left'},
      	{ index:'TYPE_CLS', name: 'TYPE_CLS', label: '유형구분', width: 100, align:'left'},
      	{ index:'DID_YN', name: 'DID_YN', label: '비식별여부', width: 100, align:'center', fixed:true, edittype:'checkbox',formatter:'checkbox',editoptions:{value:"Y:N"},editable:true, formatoptions: {disabled : true}},
      	{ index:'MNGR', name: 'MNGR', label: '담당자', width: 100, align:'left'},
      	{ index:'OBJ_TYPE_ID', name: 'OBJ_TYPE_ID', label: 'OBJ_TYPE_ID', hidden:true},
      	{ index:'OBJ_ID', name: 'OBJ_ID', label: 'OBJ_ID', hidden:true},
      	{ index:'DB_OBJ_TYPE_ID', name: 'DB_OBJ_TYPE_ID', label: 'DB_OBJ_TYPE_ID', hidden:true},
      	{ index:'DB_OBJ_ID', name: 'DB_OBJ_ID', label: 'DB_OBJ_ID', hidden:true},
      	{ index:'DB_TYPE', name: 'DB_TYPE', label: 'DB_TYPE', hidden:true}
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
			var rowData = $(this).jqGrid("getRowData", rowid);
            searchDetail(rowData["OBJ_TYPE_ID"], rowData["OBJ_ID"]);
        },
		beforeSelectRow: function (rowid, e) {
        	var $radio = $('input[type=radio]', $(e.target).closest('tr'));
            $radio.prop('checked', 'checked');
            return true;
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
        },
        gridComplete: function() {
        	DE.jqgrid.checkedHandler($(this));
        }
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"));

	var colModel2 = [
	    { index:'OBJ_NM', name: 'OBJ_NM', label: '컬럼논리명', width: 100, align:'left', 
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
    						mode:'R'
    					},
    					null
    				);
	    		}
	    	}
      	},
      	{ index:'OBJ_ABBR_NM', name: 'OBJ_ABBR_NM', label: '컬럼물리명', width: 100, align:'left', editable:true, edittype:"text"},
      	{ index:'DATA_TYPE', name: 'DATA_TYPE', label: '데이터타입', width: 100, align:'left', editable:true, edittype:"textarea"},      	      
      	{ index:'COL_ORD', name: 'COL_ORD', label: '순번', width: 100, align:'left', editable:true, edittype:"textarea"},      	      
      	{ index:'DATA_LEN', name: 'DATA_LEN', label: '길이', width: 100, align:'left', editable:true, edittype:"textarea"},      	      
      	{ index:'DATA_SCALE', name: 'DATA_SCALE', label: '소수점', width: 100, align:'left', editable:true, edittype:"textarea"},      	      
      	{ index:'NOT_NULL_YN', name: 'NOT_NULL_YN', label: 'NOT NUL 여부', width: 100, align:'left', editable:true, edittype:"textarea"},      	      
      	{ index:'PK_ORD', name: 'DATA_SCALE', label: 'PK순번', width: 100, align:'left', editable:true, edittype:"textarea"},      	      
      	{ index:'FK_YN', name: 'DATA_SCALE', label: 'FK여부', width: 100, align:'left', editable:true, edittype:"textarea"},      	      
      	{ index:'DISTRIB_KEY_ORD', name: 'DISTRIB_KEY_ORD', label: '분산키순번', width: 100, align:'left', editable:true, edittype:"textarea"},      	      
      	{ index:'DID_YN', name: 'DID_YN', label: '비식별대상여부', width: 100, align:'left', editable:true, edittype:"textarea"},      	      
      	{ index:'OBJ_TYPE_ID', name: 'OBJ_TYPE_ID', label: 'OBJ_TYPE_ID', hidden:true},      	      
      	{ index:'OBJ_ID', name: 'OBJ_ID', label: 'OBJ_ID', hidden:true}
    ];

	var opts2 = {
		"colModel":colModel2, 
		pager:"#jqGridPager2",
		autowidth:true,
		shrinkToFit:true,
		toppager: false,
		loadonce: true,
		scroll:-1,
		isPaging:false,
		onSelectRow : function (rowid, status, e) {
	        /*if (rowid) {
	        	if (rowid !== lastSelectedRowId) {
	        		$(this).jqGrid('saveRow', lastSelectedRowId);
	        	}
	            $(this).jqGrid('editRow', rowid);
	            lastSelectedRowId = rowid;
	        }*/
		}
	};
	
	var $grid2 = DE.jqgrid.render($("#jqGrid2"), opts2);
	DE.jqgrid.navGrid($("#jqGrid2"), $("#jqGridPager2"));
	
	$("button#btnSearch").on("click", function(e) {
		if (!$("#selDatabase").val()) {
			DE.box.alert("선택된 데이터베이스가 없습니다.");
			return;
		}
		lastSelectedRowId = "";
		
		var postData = {
			"queryId":"dsgn.db.findDsgnTab",
			"objTypeId":dsgnTabTypeId, 
			"pathObjTypeId":databaseTypeId, 
			"pathObjId":$("#selDatabase").val(), 
			"searchKey":$("#searchKey").val(),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	var searchDetail = function(objTypeId, objId) {
		var postData = {
			"queryId":"dsgn.db.findDsgnCol",
			"objTypeId":dsgnColTypeId, 
			"pathObjTypeId":objTypeId, 
			"pathObjId":objId
		};
		DE.jqgrid.reload($("#jqGrid2"), postData);
	};
    
    $("button#tabUploadTplDown").on("click", function(e) {
		var action = DE.contextPath+"/portal/dsgn/db?oper=downloadTabUploadTpl"
		var $frm = $("#downloadTabUploadTplForm");
    	var $frame =  $("#_frame");
    	
    	if ($frm.length == 0) {
	    	$frm =  $("<form id='downloadTabUploadTplForm' name='downloadTabUploadTplForm' action='"+action+"' target='_frame' class='downloadTabUploadTplForm' method='post'></form>").css("display", "none");
	        $frm.appendTo("body");
	    }
    	
    	if ($frame.length === 0) {
        	$frame =  $("<iframe id='_frame' name='_frame'></iframe>").css("display", "none");
            $frame.appendTo("body");
        }
    	
    	$("form.downloadTabUploadTplForm").on("submit", function (e) {
    	    $.fileDownload($(this).prop('action'), {
    	        preparingMessageHtml: "엑셀 다운로드 진행중 ...",
    	        failMessageHtml: "엑셀 다운로드 진행중 오류가 발생하였습니다. 다시 시도하여 주시기 바랍니다.",
    	        httpMethod: "POST",
    	        data: $(this).serialize()
    	    });
    	    e.preventDefault();
    	    $("form.downloadTabUploadTplForm").off("submit");
    	});
    	
    	$frm.submit();
	});	
    
    $("button#colUploadTplDown").on("click", function(e) {
		var action = DE.contextPath+"/portal/dsgn/db?oper=downloadColUploadTpl"
		var $frm = $("#downloadColUploadTplForm");
    	var $frame =  $("#_frame");
    	
    	if ($frm.length == 0) {
	    	$frm =  $("<form id='downloadColUploadTplForm' name='downloadColUploadTplForm' action='"+action+"' target='_frame' class='downloadColUploadTplForm' method='post'></form>").css("display", "none");
	        $frm.appendTo("body");
	    }
    	
    	if ($frame.length === 0) {
        	$frame =  $("<iframe id='_frame' name='_frame'></iframe>").css("display", "none");
            $frame.appendTo("body");
        }
    	
    	$("form.downloadColUploadTplForm").on("submit", function (e) {
    	    $.fileDownload($(this).prop('action'), {
    	        preparingMessageHtml: "엑셀 다운로드 진행중 ...",
    	        failMessageHtml: "엑셀 다운로드 진행중 오류가 발생하였습니다. 다시 시도하여 주시기 바랍니다.",
    	        httpMethod: "POST",
    	        data: $(this).serialize()
    	    });
    	    e.preventDefault();
    	    $("form.downloadColUploadTplForm").off("submit");
    	});
    	
    	$frm.submit();
	});	
	
	$("button#btnTabBatchPop").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			["040102L.p01"],
			{
				"viewname":'portal/dsgn/db/040102L.p01',
				"appSysId":$("#selAppSys").val(),
				"dbInstId":$("#selDbInst").val(),
				"databaseId":$("#selDatabase").val()
			},
			{width:1200, height:800, toolbar:"no", menubar:"no", location:"no"}
		);
	});	
	
	$("button#btnColBatchPop").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			["040102L.p02"],
			{
				"viewname":'portal/dsgn/db/040102L.p02',
				"appSysId":$("#selAppSys").val(),
				"dbInstId":$("#selDbInst").val(),
				"databaseId":$("#selDatabase").val()
			},
			{width:1200, height:800, toolbar:"no", menubar:"no", location:"no"}
		);
	});
	
	$("button#btnTabInsert").on("click", function(e) {
		debugger;
		DE.ui.open.popup(
			"view",
			[dsgnTabTypeId],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:dsgnTabTypeId,
				mode:'C'
			},
			null
		);
	});
	
	$("button#btnTabUpdate").on("click", function(e) {
		var rowid = $("#jqGrid").jqGrid("getGridParam", "selrow");
        if (rowid == undefined) {
        	DE.box.alert(DE.i18n.prop("common.message.selected.none"));
            return;
        }
        
        var rowData = $("#jqGrid").jqGrid("getRowData", rowid);
		DE.ui.open.popup(
			"view",
			[rowData["OBJ_TYPE_ID"], rowData["OBJ_ID"]],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:rowData["OBJ_TYPE_ID"],
				objId:rowData["OBJ_ID"],
				mode:'U'
			},
			null
		);
	});
	
	$("button#btnTabDelete").on("click", function(e) {
		var rowid = $("#jqGrid").jqGrid("getGridParam", "selrow");
        if (rowid == undefined) {
        	DE.box.alert(DE.i18n.prop("common.message.selected.none"));
            return;
        }
        
        var rowData = $("#jqGrid").jqGrid("getRowData", rowid);
        var removeAction = function() {
			var opts = {
				url : "metacore/objectInfo/delete",
				data : {"objTypeId":rowData["OBJ_TYPE_ID"], "objId":rowData["OBJ_ID"]}
			};	
			var callback = {
				success : function(response) {
					DE.box.alert(response["message"], function(){
						DE.jqgrid.reload($grid);
					});
				},
				error : function(response) {
					DE.box.alert(response["responseJSON"]["message"]);
				}
			};
			
			DE.ajax.call(opts, callback.success, callback.error);
		};
        DE.box.confirm(DE.i18n.prop("common.message.remove.confirm"), function (b) {
        	if (b === true) {
        		removeAction();
        	}
        });
	});
	
	$("button#btnGenDDL").on("click", function(e) {
		var data = $("#jqGrid").getGridParam("data");
		var checkedData = [];
		$.each(data, function(index, value){
			if (value["CHK"] === true.toString()) {
				checkedData.push(value);
			}
		});
        if (checkedData.length == 0) {
        	DE.box.alert("선택된 객체가 없습니다.");
            return;
        }
	    
        var genDDLAction = function() {
			var opts = {
				url : "portal/dsgn/db?oper=genDDL",
				data : checkedData
			};	
			var callback = {
				success : function(response) {
					$("#ddlScript").val(response["message"]);					
					$('#getDDL-view').modal('show');
				},
				error : function(response) {
					DE.box.alert(response["responseJSON"]["message"]);
				}
			};
			DE.ajax.call(opts, callback.success, callback.error);
		};
        genDDLAction();
	});
	
	/*$("button#btnColInsert").on("click", function(e) {
		debugger;
		DE.ui.open.popup(
			"view",
			[dsgnColTypeId],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:dsgnColTypeId,
				mode:'C'
			},
			null
		);
	});
	
	$("button#btnColUpdate").on("click", function(e) {
		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $grid.jqGrid("getRowData", rowid);
		
		DE.ui.open.popup(
			"view",
			[rowData["OBJ_TYPE_ID"], rowData["OBJ_ID"]],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:rowData["OBJ_TYPE_ID"],
				objId:rowData["OBJ_ID"],
				mode:'U'
			},
			null
		);
	});
	
	$("button#btnColDelete").on("click", function(e) {
		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
        
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $grid.jqGrid("getRowData", rowid);
        var removeAction = function() {
			var opts = {
				url : "metacore/objectInfo/delete",
				data : {"objTypeId":rowData["OBJ_TYPE_ID"], "objId":rowData["OBJ_ID"]}
			};	
			var callback = {
				success : function(response) {
					DE.box.alert(response["message"], function(){
						DE.jqgrid.reload($grid);
					});
				},
				error : function(response) {
					DE.box.alert(response["responseJSON"]["message"]);
				}
			};
			
			DE.ajax.call(opts, callback.success, callback.error);
		};
        DE.box.confirm(DE.i18n.prop("common.message.remove.confirm"), function (b) {
        	if (b === true) {
        		removeAction();
        	}
        });
	});*/
	
	$(this).off("autoResize").on( "autoResize", function( event ) {
	    setTimeout(function () {
	    	var heightMargin = 70;
	    	var heightMargin2 = 60;
	    	$("#jqGrid").setGridWidth($(".ui-layout-center .box-body").width(),opts["autowidth"]);
	    	$("#jqGrid").setGridHeight($(".ui-layout-center").height() - ($(".ui-layout-center .box-body").offset().top-$(".ui-layout-center").offset().top)-heightMargin);
	    	
	    	if(opts["autowidth"]==true){
				$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
			  }
		    	
	    	
	    	$("#jqGrid2").setGridWidth($(".ui-layout-center .box-body").width(),opts2["autowidth"]);
	    	$("#jqGrid2").setGridHeight($(".ui-layout-south").height() - ($(".ui-layout-south .box-body").offset().top-$(".ui-layout-south").offset().top)-heightMargin2);
	    	
	    	if(opts2["autowidth"]==true){
				$(".ui-jqgrid2-bdiv").css("overflow-x","hidden")
			  }
		    	
	    	
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
	
	var clearGridData = function() {
		$("#jqGrid2").jqGrid("clearGridData");
		$("#jqGrid1").jqGrid("clearGridData");
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
	
	var init = function() {
		$("#selAppSys").select2();
		$("#selDbInst").select2();
		$("#selDatabase").select2();
		makeAppSysSelect();
	};
	init();
});