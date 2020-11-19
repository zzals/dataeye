
$(document).ready(function() {
	var objTypeId = "060101L";
	var colModel = [	             
      	{ index:'OBJ_NM', name: 'OBJ_NM', label: '과제명', width: 200, align:'left' ,
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
      	{ index:'OBJ_DESC', name: 'OBJ_DESC', label: '과제설명', width:300, align:'left'},
      	{ index:'ADM_OBJ_ID', name: 'ADM_OBJ_ID', label: '관리 ID', width: 100, align:'center'},
      	{ index:'CRETR_NM', name: 'CRETR_NM', label: '등록자', width: 100, align:'left'},
      	{ index:'CRET_DT', name: 'CRET_DT', label: '등록일시', width: 100, align:'left'},
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
		beforeSelectRow: function (rowid, e) {
        	var $radio = $('input[type=radio]', $(e.target).closest('tr'));
            $radio.prop('checked', 'checked');
            return true;
        }
	};
	
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"));
	
	/*$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"portal.report.reportList",
			"searchKey":"",
			"searchValue":""
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	*/
	function gridLoad() {
		var postData = {
			"queryId":"portal.task.taskList",
			"searchKey":"",
			"searchValue":""
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	};
	
	
	$("button#btnInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			[termTypeId],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:objTypeId,
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
				objTypeId:objTypeId
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
	
/*	
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
	}).trigger("autoResize");*/

	var init = function() {
		gridLoad();
	};
	init();
});