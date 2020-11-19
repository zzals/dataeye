$(document).ready(function() {
	
	$('#searchValue').keydown(function(event) { 
		if (event.keyCode == '13') { 
			$('#btnSearch').click();
		}
    });

	var selectedObjTypeId = "";
	var colModel = [
	    {name:'CHK',index:'CHK', label:"<input type='checkbox' id='chkAll' name='chkAll' onclick='javascript:DE.jqgrid.checkAll(this, event);'/>", width:55, align:"center",
			sortable:false,
			formatter: 'checkbox',
			formatoptions: {disabled:false},
			edittype:'checkbox',
			editoptions:{value:"true:false"},
			editable:true
		},
	    DE.jqgrid.colModel.objTypeIcon({ label: '유형'}),
      	{ index:'OBJ_NM', name: 'OBJ_NM', label: '객체명', width: 200, align:'left', 
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $(this).jqGrid("getLocalRow", rowid);
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
      	{ index:'OBJ_DESC', name: 'OBJ_DESC', label: '설명', width: 150, align:'left'},
      	{ index:'ADM_OBJ_ID', name: 'ADM_OBJ_ID', label: '관리객체ID', width: 100, align:'left'},
        { index:'OBJ_ABBR_NM', name: 'OBJ_ABBR_NM', label: '약어명', width: 100, align:'left'},
        DE.jqgrid.colModel.objTypeIcon({ index:'PATH_OBJ_TYPE_ID', name: 'PATH_OBJ_TYPE_ID', label: '경로유형'}),
        { index:'PATH_OBJ_NM', name: 'PATH_OBJ_NM', label: '경로객체명', width: 150, align:'left', 
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $(this).jqGrid("getLocalRow", rowid);
	    			DE.ui.open.popup(
    					"view",
    					[rowData["PATH_OBJ_TYPE_ID"], rowData["PATH_OBJ_ID"]],
    					{
    						viewname:'common/metacore/objectInfoTab',
    						objTypeId:rowData["PATH_OBJ_TYPE_ID"],
    						objId:rowData["PATH_OBJ_ID"],
    						mode:'R'
    					},
    					null
    				);
	    		}
	    	}
      	},
      	{ index:'DEL_YN', name: 'DEL_YN', label: '삭제여부', width: 100, align:'center'},
      	{ index:'CRET_DT', name: 'CRET_DT', label: '생성일시', width: 150, align:'center'},
      	{ index:'CRETR_ID', name: 'CRETR_ID', label: '생성자', width: 100, align:'center'},
      	{ index:'CHG_DT', name: 'CHG_DT', label: '수정일시', width: 150, align:'center'},
      	{ index:'CHGR_ID', name: 'CHGR_ID', label: '수정자', width: 100, align:'center'},
        { index:'OBJ_ID', name: 'OBJ_ID', label: 'OBJ_ID', width: 100, hidden:true},
        { index:'PATH_OBJ_ID', name: 'PATH_OBJ_ID', label: 'PATH_OBJ_ID', width: 100, hidden:true}
    ];
	
	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		editurl: 'obj/process',
		loadonce: true,
		isPaging: false,
		scroll: 1,
		autowidth:true,
		shrinkToFit: true,
        gridComplete: function() {
        	DE.jqgrid.checkedHandler($(this));
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
			cloneToTop:false
		}
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);
	
	$("button#btnSearch").on("click", function(e) {
		if (selectedObjTypeId === "") {
			DE.box.alert("선택된 객체유형이 없습니다.");
			return;
		}
		var postData = {
			"queryId":"admin.getObj",
			"searchKey":$("#searchValue").attr("de-search-key"),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	

	$("button#btnInsert").on("click", function(e) {
		if (selectedObjTypeId === "") {
			DE.box.alert("선택된 객체유형이 없습니다.");
			return;
		}
		DE.ui.open.popup(
			"view",
			[selectedObjTypeId, "C"],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:selectedObjTypeId,
				objId:"",
				mode:'C',
				menuId:"BDP_ADMIN"
			},
			null
		);
	});
	
	$("button#btnUpdate").on("click", function(e) {
		var rowId = $grid.jqGrid("getGridParam", "selrow");
        if (rowId == undefined) {
        	DE.box.alert("선택된 객체가 없습니다.");
            return;
        }
        
        var rowData = $grid.getLocalRow(rowId);
		DE.ui.open.popup(
			"view",
			[selectedObjTypeId, "U"],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:rowData["OBJ_TYPE_ID"],
				objId:rowData["OBJ_ID"],
				mode:'U',
				menuId:"BDP_ADMIN"
			},
			null
		);
	});
	
	$("button#btnDelete, button#btnRemove").on("click", function(e) {
		var data = $grid.getGridParam("data");
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
        
        var _url = "";
		if ("btnDelete" === $(e.currentTarget).prop("id")) {
			_url = "admin/obj?oper=delete";
		} else {
			_url = "admin/obj?oper=remove";
		}
		
        var removeAction = function() {
			var opts = {
				url : _url,
				data : checkedData
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
	
	var init = function() {
		var buildObjTypeTree = function(response) {
			var searchGrid = function(objTypeId) {
//				$("#jqGrid").jqGrid("getGridParam", "postData")["filters"] = undefined
				var postData = {
					"objTypeId":objTypeId,
					"queryId":"admin.getObj",
					"searchKey":$("#searchValue").attr("de-search-key"),
					"searchValue":$("#searchValue").val()
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
						objTypeTreeview.treeview('toggleNodeExpanded', [node.nodeId, { silent: true }]);
					}
	            },
	            onNodeUnselected: function (event, node) {
	            	var selectedNodes = objTypeTreeview.treeview('getSelected');
	            	objTypeTreeview.treeview('selectNode', [node.nodeId, {silent: true}]);
	            }
			});
			$("#objTypeTreeview").delegate("li.node-selected", "click", function(){
				var node = $('#objTypeTreeview').treeview("getNode", parseInt($(this).attr("data-nodeid")));
				searchGrid(node.objTypeId);
			});
		};
		DE.ajax.call({url:"../metapublic/objTypeTree", data:{"cjsxowls":"xxxx"}}, buildObjTypeTree);
	};
	init();
});