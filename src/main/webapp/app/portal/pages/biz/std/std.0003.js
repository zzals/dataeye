$(document).ready(function() {
	var dmnObjTypeId = "010303L";
	var colModel = [
	    { index:'CHK', name:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
	    { index:'DMN_OBJ_NM', name: 'DMN_OBJ_NM', label: '표준도메인명', width: 200, align:'left',
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);
	    			DE.ui.open.popup(
    					"view",
    					[rowData["DMN_OBJ_TYPE_ID"], rowData["DMN_OBJ_ID"]],
    					{
    						viewname:'common/metacore/objectInfoTab',
    						objTypeId:rowData["DMN_OBJ_TYPE_ID"],
    						objId:rowData["DMN_OBJ_ID"],
    						mode:'R'
    					},
    					null
    				);
	    		}
	    	}
      	},
      	{ index:'ORACLE_DATA_TYPE', name: 'ORACLE_DATA_TYPE', label: 'ORACLE 데이터타입', width: 140, align:'left'},
      	{ index:'PDA_DATA_TYPE', name: 'PDA_DATA_TYPE', label: 'PDA 데이터타입', width: 140, align:'left'},
      	{ index:'TIBERO_DATA_TYPE', name: 'TIBERO_DATA_TYPE', label: 'TIBERO 데이터타입', width: 140, align:'left'},
      	{ index:'SYBASE_DATA_TYPE', name: 'SYBASE_DATA_TYPE', label: 'SYBASE 데이터타입', width: 140, align:'left'},
      	{ index:'COL_LEN', name: 'COL_LEN', label: '길이', width: 100, align:'right'},
      	{ index:'COL_POINT_LEN', name: 'COL_POINT_LEN', label: '소수점', width: 100, align:'right'},
        { index:'DMN_GRP_OBJ_NM', name: 'DMN_GRP_OBJ_NM', label: '도메인그룹명', width: 150, align:'left',
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);
	    			DE.ui.open.popup(
    					"view",
    					[rowData["DMN_GRP_OBJ_TYPE_ID"], rowData["DMN_GRP_OBJ_ID"]],
    					{
    						viewname:'common/metacore/objectInfoTab',
    						objTypeId:rowData["DMN_GRP_OBJ_TYPE_ID"],
    						objId:rowData["DMN_GRP_OBJ_ID"],
    						mode:'RO'
    					},
    					null
    				);
	    		}
	    	}
      	},
        { index:'DMN_USED_CNT', name: 'DMN_USED_CNT', label: '사용현황', width: 100, align:'right',
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);
	    			DE.ui.open.popup(
    					"view",
    					["DMN_USED_CNT"],
    					{
    						viewname:'portal/biz/std/std.p0001',
    						"dmnObjId":rowData["DMN_OBJ_ID"],
    						mode:'R'
    					},
    					null
    				);
	    		}
	    	}
      	},
        { index:'CRETR_NM', name: 'CRETR_NM', label: '등록자', width: 100, align:'center'},
        { index:'DMN_OBJ_TYPE_ID', name: 'DMN_OBJ_TYPE_ID', label: 'DMN_OBJ_TYPE_ID', hidden:true},
        { index:'DMN_OBJ_ID', name: 'DMN_OBJ_ID', label: 'DMN_OBJ_ID', hidden:true},
        { index:'DMN_GRP_OBJ_TYPE_ID', name: 'DMN_GRP_OBJ_TYPE_ID', label: 'DMN_GRP_OBJ_TYPE_ID', hidden:true},
        { index:'DMN_GRP_OBJ_ID', name: 'DMN_GRP_OBJ_ID', label: 'DMN_GRP_OBJ_ID', hidden:true}
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
			"queryId":"portal.biz.std.findStdDmn",
			"dmnGrpObjId":$("#selDmnGrp").val(), 
			"searchKey":$("#searchKey").val(),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	$("button#btnInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			[dmnObjTypeId],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:dmnObjTypeId,
				mode:'C'
			},
			null
		);
	});
	
	$("button#btnBulkInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			["std.p0005"],
			{
				viewname:'portal/biz/std/std.p0005',
				objTypeId:dmnObjTypeId,
				cAuth:'Y'
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
			[rowData["DMN_OBJ_TYPE_ID"], rowData["DMN_OBJ_ID"]],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:rowData["DMN_OBJ_TYPE_ID"],
				objId:rowData["DMN_OBJ_ID"],
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
				data : {"objTypeId":rowData["DMN_OBJ_TYPE_ID"], "objId":rowData["DMN_OBJ_ID"]}
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
			["stdDomainAprvReq"],
			{
				"viewname":"portal/biz/std/req.010303L",
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
		$("#selDmnGrp").select2();
		var rsp = DE.ajax.call({async:false, url:"metapublic/list", data:{"queryId":"portal.common.findObjM", "objTypeId":"010304L"}});
		DE.util.selectBox($("#selDmnGrp"), rsp["data"], {"value":"objId", "text":"objNm"});
		$("button#btnSearch").trigger("click");
	};
	init();
});