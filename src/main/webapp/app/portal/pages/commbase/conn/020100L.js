$(document).ready(function() {
	var dbinstTypeId = "020100L";
	
	var colModel = [
		{ index:'CHK', name:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
		{ index:'APPSYS_NM', name: 'APPSYS_NM', label: 'APP 시스템', width: 150, align:'left', editable: true, editrules: {required: true, readonly: "readonly"},
		    formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);
	    			DE.ui.open.popup(
    					"view",
    					[rowData["APPSYS_TYPE_ID"], rowData["APPSYS_ID"]],
    					{
    						viewname:'common/metacore/objectInfoTab',
    						objTypeId:rowData["APPSYS_TYPE_ID"],
    						objId:rowData["APPSYS_ID"],
    						mode:'RO'
    					},
    					null
    				);
	    		}
	    	}
	    },
	    { index:'OBJ_NM', name: 'OBJ_NM', label: 'DB 인스턴스명', width: 150, align:'left', editable: true, editrules: {required: true, readonly: "readonly"},
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
		beforeSelectRow: function (rowid, e) {
        	var $radio = $('input[type=radio]', $(e.target).closest('tr'));
            $radio.prop('checked', 'checked');
            return true;
        }
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"));
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"portal.commbase.findDbInst",
			"objTypeId":dbinstTypeId,
			"searchKey":$("#searchValue").attr("de-search-key"),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
		
	$("button#btnInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			[dbinstTypeId],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:dbinstTypeId,
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