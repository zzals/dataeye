$(document).ready(function() {
	var colModel = [
	    {name:'CHK',index:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
		{ index:'REL_TYPE_ID', name: 'REL_TYPE_ID', label: '관계유형ID', width: 120, align:'left'},
      	{ index:'REL_TYPE_NM', name: 'REL_TYPE_NM', label: '관계유형명', width: 150, align:'left'},
      	{ index:'DOWN_REL_TYPE_NM', name: 'DOWN_REL_TYPE_NM', label: '관계유형명(OBJ)', width: 150, align:'left'},
      	{ index:'UP_REL_TYPE_NM', name: 'UP_REL_TYPE_NM', label: '관계유형명(REL)', width: 150, align:'left'},
      	DE.jqgrid.colModel.objTypeIcon({ label: '객체유형(OBJ)', width:100}),
      	DE.jqgrid.colModel.objTypeIcon({ index:'REL_OBJ_TYPE_ID', name: 'REL_OBJ_TYPE_ID', label: '객체유형(REL)', width:100}),
      	{ index:'PARENT_REL_TYPE_NM', name: 'PARENT_REL_TYPE_NM', label: '상위관계유형', width: 120, align:'left'},
      	{ index:'ATR_ADM_NM', name: 'ATR_ADM_NM', label: '속성관리코드', width: 90, fixed:true, align:'left'},
      	{ index:'ATR_ADM_CD', name: 'ATR_ADM_CD', label: 'ATR_ADM_CD', hidden:true},
      	{ index:'PARENT_REL_TYPE_ID', name: 'PARENT_REL_TYPE_ID', label: 'PARENT_REL_TYPE_ID', hidden:true}
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
			cloneToTop:true,
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
			"queryId":"objreltype.getObjRelType",
			"searchKey":$("#searchValue").attr("de-search-key"),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	$("button#btnInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			["sqlPoolPopup"],
			{
				viewname:'admin/sqlPoolView',
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
			["sqlPoolPopup"],
			{
				viewname:'admin/sqlPoolView',
				"sqlId":rowData["SQL_ID"],
				mode:'U'
			},
			null
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
			var rowData = $grid.jqGrid("getRowData", rowid);
			
			var formData = {
				"sqlId":rowData["SQL_ID"]
			};
			
			var opts = {
				url : "../admin/sqlPool/process?oper=remove",
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
	};
	init();
});