$(document).ready(function() {
	var initCdGrpGrid = function() {
		var colModel = [
		    { index:'CHK', name:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
		    { index:'CD_GRP_ID', name: 'CD_GRP_ID', label: '코드그룹ID', width: 75, align:'left', "key":true},
		    { index:'CD_GRP_NM', name: 'CD_GRP_NM', label: '코드그룹명', width: 100, align:'left', 
		    	formatter: "dynamicLink", 
		    	formatoptions: {
		    		onClick: function (rowid, irow, icol, cellvalue, e) {
		    			var rowData = $grid.jqGrid("getRowData", rowid);
		    			DE.ui.open.popup(
	    					"view",
	    					["cdGrpViewPopup"],
	    					{
	    						viewname:'admin/codeGrpView',
	    						"cdGrpId":rowData["CD_GRP_ID"],
	    						mode:'R'
	    					},
	    					null
	    				);
		    		}
		    	}
	      	},
	      	{ index:'CD_GRP_DESC', name: 'CD_GRP_DESC', label: '설명', width: 250, align:'left'},
	      	{ index:'UP_CD_GRP_NM', name: 'UP_CD_GRP_NM', label: '상위코드그룹', width: 100, align:'left'},
	        { index:'UP_CD_GRP_ID', name: 'UP_CD_GRP_ID', label: 'UP_CD_GRP_ID', hidden:true}
	    ];
	
		var opts = {
			"colModel":colModel, 
			pager:"#jqGridPager",
			loadonce: true,
			isPaging:false,
			sortable: false,
			cmTemplate: { sortable: false },
			height:300,
			autowidth:true,
			shrinkToFit:true,
			rownumbers: false,
			treeGridModel: 'adjacency',//adjacency, nested
	        treeReader : {
	            level_field: "LVL",
	            parent_id_field: "UP_CD_GRP_ID",
	            leaf_field: "IS_LEAF",
	            expanded_field: "IS_EXPENDED"
	        },
	        treeGrid: true,
	        ExpandColumn : 'CD_GRP_ID',
	        onSelectRow : function (rowid, status, e) {
	        	var $radio = $('input[type=radio]', $("#"+rowid));
	            $radio.prop('checked', 'checked');
	            $("#"+rowid).addClass("success");
	            
	            var rowData = $(this).jqGrid("getLocalRow", rowid);
	            var postData = {
        			"queryId":"code.getCd",
        			"cdGrpId":rowData["CD_GRP_ID"]
        		};
        		DE.jqgrid.reload($("#jqGridCd"), postData);
			},
			loadComplete: function (data) {
				var firstRowid = $(this).jqGrid("getDataIDs")[0];
				if (firstRowid !== undefined) {
					$(this).jqGrid("setSelection", firstRowid);
				} else {
					$("#jqGridCd").jqGrid("clearGridData");
				}
	        }
		};
		
		var navOpts = {
			navOptions:{
				cloneToTop:false,
				view:false,
				add:false,
				edit:false,
				del:false,
				refresh:false,
				search:false
			}
		};
		
		var $grid = DE.jqgrid.render($("#jqGrid"), opts);
		DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);
		$grid.jqGrid(
			'filterToolbar',{
				stringResult: true, 
				groupOp:'AND',
				searchOnEnter: true, 
				defaultSearch: 'cn'
			}
		);
		
		
		$("button#btnCdGrpUpdate").on("click", function(e) {
			var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
			if ($selRadio.length === 0) {
				DE.box.alert(DE.i18n.prop("common.message.selected.none"));
				return;
			}
			
			var rowid = $selRadio.closest("tr").prop("id");
			var rowData = $grid.jqGrid("getRowData", rowid);
			
			DE.ui.open.popup(
				"view",
				["cdGrpViewPopup"],
				{
					viewname:'admin/codeGrpView',
					"cdGrpId":rowData["CD_GRP_ID"],
					mode:'U'
				},
				null
			);
		});
		
		$("button#btnCdGrpRemove").on("click", function(e) {
			var callback = {
				success : function(response) {
					DE.box.alert(response["message"], function(){
						$("button#btnSearch").trigger("click");
					});
				},
				error : function(response) {
					DE.box.alert(response["responseJSON"]["message"]);
				}
			};
			
			var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
			if ($selRadio.length === 0) {
				DE.box.alert(DE.i18n.prop("common.message.selected.none"));
				return;
			}
			
			var removeAction = function() {
				var rowid = $selRadio.closest("tr").prop("id");
				var rowData = $grid.jqGrid("getRowData", rowid);
				
				var formData = {
					"cdGrpId":rowData["CD_GRP_ID"]
				};
				
				var opts = {
					url : "admin/code?oper=remove&type=cdGrp",
					data : $.param(formData)
				};	
				
				DE.ajax.formSubmit(opts, callback.success, callback.error);
			};
			
			DE.box.confirm(DE.i18n.prop("common.message.remove.confirm"), function (b) {
	        	if (b === true) {
	        		removeAction();
	        	}
	        });
		});
	};
	
	
	
	var lastSelectedCdGridRowId = "";
	var initCdGrid = function() {
		var colModel = [
    	    { index:'CD_ID', name: 'CD_ID', label: '코드ID', width: 150, align:'left', editable:true, edittype:"text"},
          	{ index:'CD_NM', name: 'CD_NM', label: '코드명', width: 150, align:'left', editable:true, edittype:"text"},
    	    { index:'CD_DESC', name: 'CD_DESC', label: '설명', width: 300, align:'left', editable:true, edittype:"textarea"},
    	    { index:'USE_YN', name: 'USE_YN', label: '사용여부', width: 70, align:'center', fixed:true, editable:true, edittype:"select", editoptions:{value:"Y:Y;N:N"}},
    	    { index:'EFCT_ST_DATE', name: 'EFCT_ST_DATE', label: '적용시작일', width: 100, align:'center', fixed:true, editable:true, edittype:"text",
                editoptions: {
                    dataInit: function (element) {
                    	$(element).datepicker({autoclose:true, dateFormat: 'yy-mm-dd' });
                    }
                }
    	    },
    	    { index:'EFCT_ED_DATE', name: 'EFCT_ED_DATE', label: '적용종료일', width: 100, align:'center', fixed:true, editable:true, edittype:"text",
                editoptions: {
                    dataInit: function (element) {
                    	$(element).datepicker({autoclose:true, dateFormat: 'yy-mm-dd' });
                    }
                }
    	    },
            { index:'SORT_NO', name: 'SORT_NO', label: 'SORT_NO', hidden:true}
        ];
    	
    	var opts = {
    		"colModel":colModel, 
    		pager:"#jqGridPagerCd",
    		loadonce: true,
    		isPaging: false,
    		autowidth:true,
    		shrinkToFit: true,
    		cmTemplate: {sortable: false},
    		sortname:"SORT_NO",
    		sortorder: "asc",
    		onSelectRow : function (rowid, status, e) {
    	        if (rowid && rowid !== lastSelectedCdGridRowId) {
    	            $grid.jqGrid('saveRow', lastSelectedCdGridRowId);
    	            $grid.jqGrid('editRow', rowid);
    	            lastSelectedCdGridRowId = rowid;
    	        }
    		}
    	};
    	
    	var navOpts = {
    		navOptions:{
    			add:false,
    			del:false,
    			edit:false,
    			refresh:false,
    			search:false,
    			view:false,
    			cloneToTop:false,
    		}
    	};
    	
    	var $grid = DE.jqgrid.render($("#jqGridCd"), opts);
    	DE.jqgrid.navGrid($("#jqGridCd"), $("#jqGridPagerCd"), navOpts);
	};
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"code.getCdGrpTree",
			"searchKey":$("#searchValue").attr("de-search-key"),
			"searchValue":$("#searchValue").val()
		};
		$("#jqGrid").jqGrid("setGridParam", {"treedatatype":"json"});
		DE.jqgrid.reload($("#jqGrid"), postData);
		$("#jqGrid").jqGrid("setGridParam", {"treedatatype":"local"});
	});
	
	$("button#btnCdGrpInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			["atrViewPopup"],
			{
				viewname:'admin/codeGrpView',
				mode:'C'
			},
			null
		);
	});
	
	$("button#btnCdUP").on("click", function(e){
		debugger;
		var selRowid = $("#jqGridCd").jqGrid("getGridParam", "selrow");
		if (selRowid === null) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		$("#jqGridCd").jqGrid('saveRow', selRowid);
		lastSelectedCdGridRowId = "";
		var sortNo = $("#jqGridCd").jqGrid("getGridParam", "reccount");
		var rowids = $("#jqGridCd").jqGrid("getDataIDs").reverse();
		
		var selRowPrev = false;
		$.each(rowids, function(index, value){
			if (selRowPrev) {
				$("#jqGridCd").jqGrid('setCell', value, 'SORT_NO', sortNo--);
				sortNo--;
				selRowPrev = false;
			} else {
				if (value === selRowid) {
					$("#jqGridCd").jqGrid('setCell', value, 'SORT_NO', (sortNo-1));
					selRowPrev = true;
				} else {
					$("#jqGridCd").jqGrid('setCell', value, 'SORT_NO', sortNo--);
				}
			}
		});
		$("#jqGridCd").trigger("reloadGrid");
		$("#jqGridCd").jqGrid("setSelection", selRowid);
	});
	$("button#btnCdDOWN").on("click", function(e){
		var selRowid = $("#jqGridCd").jqGrid("getGridParam", "selrow");
		if (selRowid === null) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		$("#jqGridCd").jqGrid('saveRow', selRowid);
		lastSelectedCdGridRowId = "";
		var sortNo = 1;
		var rowids = $("#jqGridCd").jqGrid("getDataIDs");
		
		var selRowNext = false;
		$.each(rowids, function(index, value){
			if (selRowNext) {
				$("#jqGridCd").jqGrid('setCell', value, 'SORT_NO', sortNo++);
				sortNo++;
				selRowNext = false;
			} else {
				if (value === selRowid) {
					$("#jqGridCd").jqGrid('setCell', value, 'SORT_NO', (sortNo+1));
					selRowNext = true;
				} else {
					$("#jqGridCd").jqGrid('setCell', value, 'SORT_NO', sortNo++);
				}
			}
		});
		$("#jqGridCd").trigger("reloadGrid");
		$("#jqGridCd").jqGrid("setSelection", selRowid);
	});
	
	$("button#btnCdAdd").on("click", function(e){
		var rowid = $.jgrid.randId("jqg");
		if (lastSelectedCdGridRowId) {
        	$("#jqGridCd").jqGrid('saveRow', lastSelectedCdGridRowId);
        }
		$("#jqGridCd").jqGrid("addRowData", rowid, {}, "last");
		$("#jqGridCd").jqGrid('setSelection', rowid);
	});
	
	$("button#btnCdDelete").on("click", function(e){
		var selRowid = $("#jqGridCd").jqGrid("getGridParam", "selrow");
		if (selRowid !== null) {
			$("#jqGridCd").jqGrid("delRowData", selRowid);
		}
	});
	
	$("button#btnCdSave").on("click", function(e){
		var callback = {
			success : function(response) {
				DE.box.alert(response["message"], function(){ self.close();});
			},
			error : function(response) {
				DE.box.alert(response["responseJSON"]["message"]);
			}
		};

        if (lastSelectedCdGridRowId) {
        	$("#jqGridCd").jqGrid('saveRow', lastSelectedCdGridRowId);
        }
        lastSelectedCdGridRowId = "";
        
        var cdGrpId = $('input[type=radio]:checked', $("#jqGrid")).closest("tr").prop("id");
        if (cdGrpId === undefined) {
        	DE.box.alert(DE.i18n.prop("msg.cdgrp.CD_GRP_ID.not.selected"));
        	return;
        }
		var data = $("#jqGridCd").jqGrid("getRowData");
		var missReqVal = false;
		$.each(data, function(index, value){
			var cdId = value["CD_ID"];
			var cdNm = value["CD_NM"];
			if (cdId === null || cdId === "") {
				missReqVal = true;
				DE.box.alert(DE.i18n.prop("msg.cd.CD_ID.required"));
				$("#jqGridCd").jqGrid('setSelection', $("#jqGridCd").jqGrid("getDataIDs")[index]);
				return false;
			}
			if (cdNm === null || cdNm === "") {
				missReqVal = true;
				DE.box.alert(DE.i18n.prop("msg.cd.CD_NM.required"));
				$("#jqGridCd").jqGrid('setSelection', $("#jqGridCd").jqGrid("getDataIDs")[index]);
				return false;
			}
		});
		if (missReqVal) {
			return;
		}
		
		var postData = {
			"cdGrpId":cdGrpId,
			"data":data
		};
		var opts = {
			async:false,
			url : "admin/code?oper=save&type=cd",
			data : postData
		};
		
		DE.ajax.call(opts, callback.success, callback.error);
	});
	
	var init = function() {
		initCdGrpGrid();
		initCdGrid();
		$("button#btnSearch").trigger("click");
		$(".ui-jqgrid-bdiv").css("overflow-x","hidden");
	};
	init();
});