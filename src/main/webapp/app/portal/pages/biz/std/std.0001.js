$(document).ready(function() {
	var termTypeId = "010302L";
	var colModel = [
	    { index:'CHK', name:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
	    { index:'TERM_NM', name: 'TERM_NM', label: '표준용어명', width: 150, align:'left',
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);
	    			DE.ui.open.popup(
    					"view",
    					[rowData["TERM_TYPE_ID"], rowData["TERM_ID"]],
    					{
    						viewname:'common/metacore/objectInfoTab',
    						objTypeId:rowData["TERM_TYPE_ID"],
    						objId:rowData["TERM_ID"],
    						mode:'R'
    					},
    					null
    				);
	    		}
	    	}
      	},
      	{ index:'TERM_ENG_ABBR_NM', name: 'TERM_ENG_ABBR_NM', label: '영문약어명', width: 150, align:'left'},
	    { index:'DMN_NM', name: 'DMN_NM', label: '표준도메인명', width: 150, align:'left',
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);
	    			DE.ui.open.popup(
    					"view",
    					[rowData["DMN_TYPE_ID"], rowData["DMN_ID"]],
    					{
    						viewname:'common/metacore/objectInfoTab',
    						objTypeId:rowData["DMN_TYPE_ID"],
    						objId:rowData["DMN_ID"],
    						mode:'RO'
    					},
    					null
    				);
	    		}
	    	}
      	},
      	{ index:'DISP_ORACLE_DATA_TYPE', name: 'DISP_ORACLE_DATA_TYPE', label: 'ORACLE 데이터타입', width: 150, align:'left'},
      	{ index:'DISP_PDA_DATA_TYPE', name: 'DISP_PDA_DATA_TYPE', label: 'PDA 데이터타입', width: 150, align:'left'},
      	{ index:'DISP_TIBERO_DATA_TYPE', name: 'DISP_TIBERO_DATA_TYPE', label: 'TIBERO 데이터타입', width: 150, align:'left'},
      	{ index:'DISP_SYBASE_DATA_TYPE', name: 'DISP_SYBASE_DATA_TYPE', label: 'SYBASE 데이터타입', width: 150, align:'left'},
        { index:'CRETR_NM', name: 'CRETR_NM', label: '등록자', width: 100, fixed: true, align:'center'},
        { index:'TERM_TYPE_ID', name: 'TERM_TYPE_ID', label: 'TERM_TYPE_ID', hidden:true},
        { index:'TERM_ID', name: 'TERM_ID', label: 'TERM_ID', hidden:true},
        { index:'DMN_TYPE_ID', name: 'DMN_TYPE_ID', label: 'DMN_TYPE_ID', hidden:true},
        { index:'DMN_ID', name: 'DMN_ID', label: 'DMN_ID', hidden:true}
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
			"queryId":"portal.biz.std.findStdTerm",
			"searchKey":$("#searchKey").val(),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	$("button#btnInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			[termTypeId],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:termTypeId,
				mode:'C'
			},
			null
		);
	});
	
	$("button#btnBulkInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			["std.p0004"],
			{
				viewname:'portal/biz/std/std.p0004',
				objTypeId:termTypeId
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
			[rowData["TERM_TYPE_ID"], rowData["TERM_ID"]],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:rowData["TERM_TYPE_ID"],
				objId:rowData["TERM_ID"],
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
				data : {"objTypeId":rowData["TERM_TYPE_ID"], "objId":rowData["TERM_ID"]}
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
	
	$("#btnAprvReq").on("click", function(e){
		DE.ui.open.popup(
			"view",
			["stdTermAprvReq"],
			{
				"viewname":"portal/biz/std/req.010302L",
				"mode":"C"
			},
			null
		);
	});
	
	$(this).off("autoResize").on( "autoResize", function( event ) {
	    setTimeout(function () {
	    	var heightMargin = $(".content-body .box-body").offset().top - $(".content-header").offset().top,
    		bottomMargin = 10, // bottom margin 10
    		gridHeadHeight = 60; // grid header height 30 + grid footer height 30
    	
    		
	    	$("#jqGrid").setGridWidth($(".content-body .box-body").width(),opts["autowidth"]);
	    	$("#jqGrid").setGridHeight($(".content").height() - (heightMargin + bottomMargin + gridHeadHeight));
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