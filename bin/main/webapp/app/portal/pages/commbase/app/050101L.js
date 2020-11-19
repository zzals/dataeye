$(document).ready(function() {
	var appGrpTypeId = "050101L";
	
	var colModel = [
		{ index:'CHK', name:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
	    { index:'OBJ_NM', name: 'OBJ_NM', label: 'APP그룹명', width: 75, align:'left', editable: true, editrules: {required: true, readonly: "readonly"},
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
      	{ index:'OBJ_DESC', name: 'OBJ_DESC', label: '설명', width: 250, align:'left', editable: true, edittype:'textarea'},
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
		var objId = rowData.OBJ_ID;
		
		var subgrid_table_id = subgrid_id + "_t";
		$("#" + subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table>");		
		
		jQuery("#" + subgrid_table_id).jqGrid({
			datatype : "local",
			pager:false,
			toppager:false,
			autowidth:true,
			height: '100%',
			colModel: [
							{name : "OBJ_NM",index : "OBJ_NM" ,label: "APP 시스템", width : 150 , sortable:false},
							{name : "OBJ_DESC", index : "OBJ_DESC" ,label: "설명", width : 150 ,sortable:false, align:"center"},
							/*{name : "SYSGRP", index : "SYSGRP" ,label: "시스템그룹", width : 150 ,sortable:false, align:"center"},
							{name : "IMPLMETHD", index : "IMPLMETHD" ,label: "구현방식", width : 150 ,sortable:false, align:"center"},
							{name : "BDP_DATA_ENGNR_UP", index : "BDP_DATA_ENGNR_UP" ,label: "Data Enginner(U+)", width : 150 ,sortable:false, align:"center"},
							{name : "BDP_CHRGR_CNS", index : "BDP_CHRGR_CNS" ,label: "BDP담당자(CNS)", width : 150 ,sortable:false, align:"center"},*/
							{name : "OPRNCHRAG", index : "OPRNCHRAG" ,label: "운영담당자", width : 150 ,sortable:false, align:"center"}/*,
							{name : "OPRNTEAM", index : "OPRNTEAM" ,label: "운영팀", width : 150 ,sortable:false, align:"center"}		*/					
						],
			
			regional : "kr"
		});
		
		var postData = {
			"queryId":"portal.commbase.getAppGroupSub",
			"objId":objId			
		};
		DE.jqgrid.reload($("#" + subgrid_table_id), postData);
		
		$(".ui-jqgrid .ui-jqgrid-bdiv").css("overflow-x","hidden");
	};

	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"));
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"portal.commbase.getAppGroup",
			"searchKey":$("#searchValue").attr("de-search-key"),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
		
	$("button#btnInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			[appGrpTypeId],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:appGrpTypeId,
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
	    	
	      	
		    	$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
		  
	      	$('[class*=" .ui-jqgrid"]').css("overflow-x","hidden");	
	    	
    	}, 500);
	}).trigger("autoResize");
	
	var init = function() {
		$("button#btnSearch").trigger("click");
	};
	init();
	
});