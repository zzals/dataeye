$(document).ready(function() {
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
      	{ index:'DEL_YN', name: 'DEL_YN', label: '삭제여부', width: 80, align:'center', fixed:true},
      	{ index:'USE_TYPE_NM', name: 'USE_TYPE_NM', label: 'UI 활용유형', width: 100, align:'left'},
      	{ index:'UI_TYPE_NM', name: 'UI_TYPE_NM', label: 'UI 유형', width: 150, align:'left'},
      	{ index:'PGM_ID', name: 'PGM_ID', label: '프로그램ID', width: 250, fixed:true, align:'left'},
      	{ index:'UI_ID', name: 'UI_ID', label: 'UI_ID', hidden:true}
    ];

	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		toppager: false,
		isPaging:false,
		beforeSelectRow: function (rowid, e) {
        	var $radio = $('input[type=radio]', $(e.target).closest('tr'));
            $radio.prop('checked', 'checked');
            return true;
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
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"objui.getObjUI",
			"searchKey":$("#searchValue").attr("de-search-key"),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	$("button#btnInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			["objUIView"],
			{
				viewname:'admin/objUIView',
				mode:'C'
			},
		    "width=1300, height=800, toolbar=non, menubar=no"
		);
	});
	
	$("button#btnUpdate").on("click", function(e) {
		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $grid.jqGrid("getLocalRow", rowid);
		
		DE.ui.open.popup(
			"view",
			["objUIView"],
			{
				viewname:'admin/objUIView',
				"uiId":rowData["UI_ID"],
				mode:'U'
			},
		    "width=1300, height=800, toolbar=non, menubar=no"
		);
	});
	
	$("button#btnRemove").on("click", function(e) {
		var callback = {
			success : function(response) {
				DE.box.alert(response["message"], function(){
					DE.jqgrid.reload($("#jqGrid"));
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
			var rowData = $grid.jqGrid("getLocalRow", rowid);
			
			var formData = {
				"uiId":rowData["UI_ID"]
			};
			
			var opts = {
				url : "admin/objui?oper=remove",
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
	
	$("button#btnCopy").on("click", function(e) {
		var callback = {
			success : function(response) {
				DE.box.alert(response["message"], function(){
					DE.jqgrid.reload($("#jqGrid"));
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
		
		var copyAction = function(uiNm) {
			var rowid = $selRadio.closest("tr").prop("id");
			var rowData = $grid.jqGrid("getLocalRow", rowid);
			
			var formData = {
				"uiId": rowData["UI_ID"],
				"uiNm": uiNm
			};
			
			var opts = {
				url : "admin/objui?oper=copy",
				data : $.param(formData)
			};	
			
			DE.ajax.formSubmit(opts, callback.success, callback.error);
		};
		
		DE.box.prompt(DE.i18n.prop("common.label.copy"), function (uiNm) {
        	if (uiNm !== null && "" !== uiNm) {
        		copyAction(uiNm);
        	}
        });
	});
	
	var init = function() {
		$("button#btnSearch").trigger("click");
	};
	init();
});