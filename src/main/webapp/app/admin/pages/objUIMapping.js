$(document).ready(function() {
	var selectedObjTypeId = "";
	var lastSelectedRowId = "";
	var colModel = [
	    {name:'CHK',index:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
	    { index:'UI_NM', name: 'UI_NM', label: '화면명', width: 150, align:'left', 
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getLocalRow", rowid);
	    			DE.ui.open.popup(
    					"view",
    					["objUIView"],
    					{
    						viewname:'admin/objUIView',
    						"uiId":rowData["UI_ID"],
    						mode:'R'
    					},
    				    "width=1300, height=800, toolbar=non, menubar=no"
    				);
	    		}
	    	}
      	},
      	{ index:'UI_ALIAS_NM', name: 'UI_ALIAS_NM', label: '화면별칭명', width: 150, align:'left', editable:true, edittype:"text"},
	    { index:'USE_YN', name: 'USE_YN', label: '사용여부', width: 70, align:'center', fixed:true, editable:true, edittype:"select", editoptions:{value:"N:N;Y:Y"}},
	    { index:'USE_TYPE_NM', name: 'USE_TYPE_NM', label: 'UI 활용유형', width: 100, align:'left'},
	    { index:'UI_TYPE_NM', name: 'UI_TYPE_NM', label: 'UI 유형', width: 200, align:'left'},
	    { index:'PGM_ID', name: 'PGM_ID', label: '프로그램ID', width: 200, align:'left'},
        { index:'OBJ_TYPE_ID', name: 'OBJ_TYPE_ID', label: 'OBJ_TYPE_ID', hidden:true},
        { index:'UI_ID', name: 'UI_ID', label: 'UI_ID', hidden:true},
        { index:'SORT_NO', name: 'SORT_NO', label: 'SORT_NO', hidden:true}
    ];
	
	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		loadonce: true,
		isPaging: false,
		scroll: 1,
		autowidth:true,
		shrinkToFit: true,
		cmTemplate: {sortable: false},
		sortname:"SORT_NO",
		sortorder: "asc",
		onSelectRow : function (rowid, status, e) {
	        if (rowid && rowid !== lastSelectedRowId) {
	            $grid.jqGrid('saveRow', lastSelectedRowId);
	            $grid.jqGrid('editRow', rowid);
	            lastSelectedRowId = rowid;
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
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);
	$grid.sortableRows();   
	$grid.jqGrid('gridDnD');
	
	//UI 선택  뷰
	$("button#btnAdd").on("click", function(e){
		if (selectedObjTypeId === "") {
			DE.box.alert("선택된 객체유형이 없습니다.");
			return;
		}
    	$('#ui-selected-view').modal('toggle');
    	
    	var colModel = [
    	    {name:'CHK',index:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
    		{ index:'UI_NM', name: 'UI_NM', label: '화면명', width: 150, align:'left'},
          	{ index:'DEL_YN', name: 'DEL_YN', label: '삭제여부', width: 80, align:'center', fixed:true},
          	{ index:'USE_TYPE_NM', name: 'USE_TYPE_NM', label: 'UI 활용유형', width: 100, align:'left'},
          	{ index:'UI_TYPE_NM', name: 'UI_TYPE_NM', label: 'UI 유형', width: 150, align:'left'},
          	{ index:'PGM_ID', name: 'PGM_ID', label: '프로그램ID', width: 250, fixed:true, align:'left'},
          	{ index:'UI_ID', name: 'UI_ID', label: 'UI_ID', hidden:true}
        ];
    	
    	var opts = {
    		"colModel":colModel, 
    		pager:"#jqGridPager2",
    		loadonce: true,
    		scroll: 1,
    		autowidth:true,
    		height:400,
    		shrinkToFit: true,
    		isPaging: false,
    		isResize:false
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
    	
    	var $grid = DE.jqgrid.render($("#jqGrid2"), opts);
    	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager2"), navOpts);
		DE.jqgrid.reload($("#jqGrid2"), {
			"queryId":"objui.getObjUI",
			"useTypeCd":"WD",
			"delYn":"N"
		});
		
    	setTimeout( function() {
    		$(".modal-backdrop", "#ui-selected-view").addClass("modal-backdrop-fullscreen");
    	}, 0);
	});
    $("#ui-selected-view").on('show.bs.modal', function () {
    	
	});
	$("#ui-selected-view").on('hidden.bs.modal', function () {
		$(".modal-backdrop", "#ui-selected-view").addClass("modal-backdrop-fullscreen");
	});
	
	
	$("button#btnRemove").on("click", function(e){
		var $selRadio = $('input[name=radio_jqGrid]:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var rowid = $selRadio.closest("tr").prop("id");
		$("#jqGrid").jqGrid("delRowData", rowid);
	});
	
	$("button#btnSave").on("click", function(e){
		var callback = {
			success : function(response) {
				DE.box.alert(response["message"], function(){ self.close();});
			},
			error : function(response) {
				DE.box.alert(response["responseJSON"]["message"]);
			}
		};

        if (lastSelectedRowId) {
            $grid.jqGrid('saveRow', lastSelectedRowId);
        }
		var data = $("#jqGrid").jqGrid("getRowData");
		var postData = {
			"objTypeId":selectedObjTypeId,
			"data":data
		};
		var opts = {
			async:false,
			url : "admin/objui?oper=saveMapping",
			data : postData
		};
		
		DE.ajax.call(opts, callback.success, callback.error);
	});

	$("button#btnUIAdd").on("click", function(e){
		var $selRadio = $('input[name=radio_jqGrid2]:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $("#jqGrid2").jqGrid("getLocalRow", rowid);
		var defData = {
			"UI_ALIAS_NM":rowData["UI_NM"],
			"USE_YN":"Y"
		} 
		$.extend(defData, rowData);
		
		$("#jqGrid").jqGrid("addRowData", $.jgrid.randId("jqg"), defData, "last");
		$('#ui-selected-view').modal('toggle');
		$("#jqGrid").trigger("reloadGrid");
	});

	$("button#btnUP").on("click", function(e){
		var selRowid = $("#jqGrid").jqGrid("getGridParam", "selrow");
		if (selRowid === null) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var sortNo = $("#jqGrid").jqGrid("getGridParam", "reccount");
		var rowids = $("#jqGrid").jqGrid("getDataIDs").reverse();
		
		var selRowPrev = false;
		$.each(rowids, function(index, value){
			if (selRowPrev) {
				$("#jqGrid").jqGrid('setCell', value, 'SORT_NO', sortNo--);
				sortNo--;
				selRowPrev = false;
			} else {
				if (value === selRowid) {
					$("#jqGrid").jqGrid('setCell', value, 'SORT_NO', (sortNo-1));
					selRowPrev = true;
				} else {
					$("#jqGrid").jqGrid('setCell', value, 'SORT_NO', sortNo--);
				}
			}
		});
		$("#jqGrid").trigger("reloadGrid");
		$("#jqGrid").jqGrid("setSelection", selRowid);
	});
	$("button#btnDOWN").on("click", function(e){
		var selRowid = $("#jqGrid").jqGrid("getGridParam", "selrow");
		if (selRowid === null) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var sortNo = 1;
		var rowids = $("#jqGrid").jqGrid("getDataIDs");
		
		var selRowNext = false;
		$.each(rowids, function(index, value){
			if (selRowNext) {
				$("#jqGrid").jqGrid('setCell', value, 'SORT_NO', sortNo++);
				sortNo++;
				selRowNext = false;
			} else {
				if (value === selRowid) {
					$("#jqGrid").jqGrid('setCell', value, 'SORT_NO', (sortNo+1));
					selRowNext = true;
				} else {
					$("#jqGrid").jqGrid('setCell', value, 'SORT_NO', sortNo++);
				}
			}
		});
		$("#jqGrid").trigger("reloadGrid");
		$("#jqGrid").jqGrid("setSelection", selRowid);
	});

	var init = function() {
		var buildObjTypeTree = function(response) {
			var searchGrid = function(objTypeId) {
				var postData = {
					"objTypeId":objTypeId,
					"queryId":"objui.getObjUIMapping"
				}
        		DE.jqgrid.reload($("#jqGrid"), postData);
        	};
			var makeTreeFormat = function(arr) {
				$.each(arr, function(index, value) {
   					value.text = value.objTypeNm;
   					value.href = "#";
   					if (value.children != undefined) {
   						value.nodes = value.children;
   						makeTreeFormat(value.children);
   					}
   				});	
			};
			makeTreeFormat(response.data);
			
			var objTypeTreeview = $('#objTypeTreeview').treeview({
		    	color: "#428bca",
		        enableLinks: false,
				data: response.data,
				levels:2,
				multiSelect:true,
				onNodeSelected: function(event, node) {
					var selectedNodes = objTypeTreeview.treeview('getSelected');
	            	$(selectedNodes).each(function (index, element) {
	                    if (element.nodeId !== node.nodeId) {
	                    	objTypeTreeview.treeview('unselectNode', [element.nodeId, { silent: true }]);
	                    }
	                });
	            	
	            	if (node["hierLevNo"] === "3") {
	            		selectedObjTypeId = node.objTypeId;
						searchGrid(selectedObjTypeId);
					} else {
						selectedObjTypeId = "";
						$("#jqGrid").jqGrid("clearGridData", true).trigger("reloadGrid");
						objTypeTreeview.treeview('toggleNodeExpanded', [node.nodeId, { silent: true }]);
					}
	            },
	            onNodeUnselected: function (event, node) {
	            	var selectedNodes = objTypeTreeview.treeview('getSelected');
	            	objTypeTreeview.treeview('selectNode', [node.nodeId, {silent: false}]);
	            }
			});
		};
		DE.ajax.call({url:"metapublic/objTypeTree"}, buildObjTypeTree);
	};
	init();
});