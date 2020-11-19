$(document).ready(function() {
	var appGrpTypeId = "050101L";
	var appTypeId = "050102L";
	var dbInstTypeId = "020100L";
	
	var colModel = [
		{ index:'CHK', name:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
	    { index:'OBJ_NM', name: 'OBJ_NM', label: 'APP 시스템', width: 150, align:'left', editable: true, editrules: {required: true, readonly: "readonly"},
		    formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);
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
      	{ index:'OBJ_DESC', name: 'OBJ_DESC', label: '설명', width: 250, align:'left', editable: true},
      	{ index:'PATH_OBJ_NM', name: 'PATH_OBJ_NM', label: 'APP 그룹', width: 100, align:'left'},
      	/*{ index:'SYSGRP', name: 'SYSGRP', label: '시스템그룹', width: 100, align:'center'},
      	{ index:'IMPLMETHD', name: 'IMPLMETHD', label: '구현방식', width: 80, align:'center'},
      	{ index:'BDP_DATA_ENGNR_UP', name: 'BDP_DATA_ENGNR_UP', label: 'Data Enginner(U+)', width: 80, align:'center'},
      	{ index:'BDP_CHRGR_CNS', name: 'BDP_CHRGR_CNS', label: 'BDP담당자(CNS)', width: 80, align:'left'},*/
      	{ index:'OPRNCHRAG', name: 'OPRNCHRAG', label: '운영담당자', width: 80, align:'left'},
      	{ index:'OPRNTEAM', name: 'OPRNTEAM', label: '운영팀', width: 80, align:'left'},
      	{ index:'OBJ_TYPE_ID', name: 'OBJ_TYPE_ID', label: 'OBJ_TYPE_ID', hidden:true},
      	{ index:'OBJ_ID', name: 'OBJ_ID', label: 'OBJ_ID', hidden:true}
    ];

	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		autowidth:true,
		shrinkToFit:true,
		toppager: false,
		loadonce: true,
		scroll:-1,
		isPaging:false,
		subGrid:true,
		subGridOptions: 
		{ 
		
			"plusicon" : "glyphicon glyphicon-plus",		
            "minusicon" :"glyphicon glyphicon-minus",
             "openicon" : "glyphicon glyphicon-folder-open",
             "reloadOnExpand" : false,
             "selectOnExpand" : true
             
		},
        subGridRowExpanded: function(subgrid_id, rowId) {
        	fn_subgrid("jqGrid",subgrid_id,rowId)
		},
		beforeSelectRow: function (rowid, e) {
        	var $radio = $('input[type=radio]', $(e.target).closest('tr'));
            $radio.prop('checked', 'checked');
            return true;
        }
	};
	
	var fn_subgrid = function(grid_id,subgrid_id,row_id) {
		var rowData = jQuery("#" + grid_id).getRowData(row_id);
		
		var subgrid_table_id = subgrid_id + "_t";
		$("#" + subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table>");		
		
		jQuery("#" + subgrid_table_id).jqGrid({
			datatype : "local",
			pager:false,
			toppager:false,
			autowidth:true,
			height: '100%',
			colModel: [
				{ index:'OBJ_NM', name: 'OBJ_NM', label: 'DB 인스턴스명', width: 150, align:'left'},
		      	{ index:'OBJ_DESC', name: 'OBJ_DESC', label: '설명', width: 250, align:'left'},
		      	{ index:'DBTYPE', name: 'DBTYPE', label: 'DB 유형', width: 100, align:'left'},
		      	{ index:'DB_VER', name: 'DB_VER', label: 'DB 버전', width: 100, align:'left'},
		      	{ index:'DB_USAGE', name: 'DB_USAGE', label: 'DB 용도', width: 100, align:'left'},
		      	{ index:'DB_IP', name: 'DB_IP', label: 'IP', width: 100, align:'left'},
		      	{ index:'DB_PORT', name: 'DB_PORT', label: 'PORT', width: 100, align:'right'},
		      	{ index:'DB_MGR', name: 'DB_MGR', label: 'DB 관리자', width: 100, align:'center'},
		      	{ index:'OBJ_TYPE_ID', name: 'OBJ_TYPE_ID', label: 'OBJ_TYPE_ID', hidden:true},
		      	{ index:'OBJ_ID', name: 'OBJ_ID', label: 'OBJ_ID', hidden:true},
		      	{ index:'APPSYS_TYPE_ID', name: 'APPSYS_TYPE_ID', label: 'APPSYS_TYPE_ID', hidden:true},
		      	{ index:'APPSYS_ID', name: 'APPSYS_ID', label: 'APPSYS_ID', hidden:true}				
			],
			
			regional : "kr"
		});
		
		var postData = {
			"queryId":"portal.commbase.findDbInst",
			"objTypeId":dbInstTypeId,
			"pathObjTypeId":rowData.OBJ_TYPE_ID,
			"pathObjId":rowData.OBJ_ID	
		};
		DE.jqgrid.reload($("#" + subgrid_table_id), postData);
		$(".ui-jqgrid .ui-jqgrid-bdiv").css("overflow-x","hidden");
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"));
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"portal.commbase.findAppSystem",
			"searchKey":$("#searchValue").attr("de-search-key"),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
		
	$("button#btnInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			[appTypeId],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:appTypeId,
				mode:'C'
			},
			null
		);
	});
	
	$("button#btnUpdate").on("click", function(e) {
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
	
	$("button#btnDelete").on("click", function(e) {
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
			debugger;
			DE.ajax.call(opts, callback.success, callback.error);
		};
        DE.box.confirm(DE.i18n.prop("common.message.remove.confirm"), function (b) {
        	if (b === true) {
        		removeAction();
        	}
        });
	});
	
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
		$("button#btnSearch").trigger("click");
	};
	init();
});