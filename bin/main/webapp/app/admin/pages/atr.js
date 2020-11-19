$(document).ready(function() {
	var colModel = [
	    { index:'CHK', name:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
	    { index:'ATR_ID', name: 'ATR_ID', label: '속성 ID', width: 100, align:'center', editable: true, editrules: {required: true, readonly: "readonly"}},
      	{ index:'ATR_NM', name: 'ATR_NM', label: '속성명', width: 200, align:'left', editable: true, editrules: {required: true}, 
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);
	    			DE.ui.open.popup(
    					"view",
    					["atrViewPopup"],
    					{
    						viewname:'admin/atrView',
    						"atrId":rowData["ATR_ID"],
    						mode:'R'
    					},
    					null
    				);
	    		}
	    	}
      	},
      	{ index:'ATR_DESC', name: 'ATR_DESC', label: '설명', width: 400, align:'left', editable: true, edittype:'textarea'},
      	{ index:'DATA_TYPE_NM', name: 'DATA_TYPE_NM', label: '데이터유형', width: 100, fixed: true, align:'center', editable: true, edittype:"select", editoptions: {value:DE.code.getGridEditOptions("SYS_DATATYP")}, editrules: {required: true}},
        { index:'COL_LEN', name: 'COL_LEN', label: '컬럼길이', width: 100, fixed: true, align:'center', editable: true, editrules: {required: true}},
        { index:'SQL_SBST', name: 'SQL_SBST', label: 'SQL', width: 100, fixed: true, align:'center', editable: true, edittype:"textarea", editoptions: {value:"Y:Y;N:N"}, editrules: {required: false}},
        { index:'UI_COMP_NM', name: 'UI_COMP_NM', label: 'UI구분', width: 100, fixed: true, align:'center', editable: true, edittype:"select", editoptions: {value:DE.code.getGridEditOptions("SYS_UI")}, editrules: {required: true}},
        { index:'UI_COMP_WIDTH_RADIO', name: 'UI_COMP_WIDTH_RADIO', label: 'UI Width', width: 100, fixed: true, align:'center', editable: true, edittype:"select", editoptions: {value:"6:50%;12:100%"}, editrules: {required: true}},
        { index:'UI_COMP_HEIGHT_PX', name: 'UI_COMP_HEIGHT_PX', label: 'UI Height', width: 100, fixed: true, align:'center', editable: true, editrules: {required: true}, editoptions: { dataInit: function (elem) { 
            //$(elem).numeric(); 
        }}}
    ];

	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		toppager: false,
	   	autowidth: true,
		shrinkToFit: true,
		isPaging:false,
		sortname:"ATR_ID",
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
			"queryId":"atr.getAtrM",
			"searchKey":$("#searchValue").attr("de-search-key"),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	$("button#btnInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			["atrViewPopup"],
			{
				viewname:'admin/atrView',
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
			["atrViewPopup"],
			{
				viewname:'admin/atrView',
				"atrId":rowData["ATR_ID"],
				mode:'U'
			},
			null
		);
	});
	
	$("button#btnRemove").on("click", function(e) {
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
		
		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var removeAction = function() {
			var rowid = $selRadio.closest("tr").prop("id");
			var rowData = $grid.jqGrid("getRowData", rowid);
			
			var formData = {
				"atrId":rowData["ATR_ID"]
			};
			
			var opts = {
				url : "admin/atr?oper=remove",
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
	
	var init = function() {
		$("button#btnSearch").trigger("click");
		
		$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
	};
	init();
});