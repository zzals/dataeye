$(document).ready(function() {
	var colModel = [
	    { index:'CHK', name:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
		{ index:'APP_ID', name: 'APP_ID', label: 'Application ID', width: 75, align:'left', editable: true, editrules: {required: false}},
      	{ index:'APP_NM', name: 'APP_NM', label: 'Application 명', width: 300, align:'left', editable: true, editrules: {required: false}},
      	{ index:'APP_DESC', name: 'APP_DESC', label: 'Application 설명', width: 250, align:'left', editable: true, edittype:'textarea'},
      	{ index:'USE_YN', name: 'USE_YN', label: '사용여부', width: 100, fixed: true, align:'center', editable: true, edittype:"select", editoptions: {value:"Y:Y;N:N"}, editrules: {required: true}},
        { index:'DEL_YN', name: 'DEL_YN', label: '삭제여부', width: 100, fixed: true, align:'center', editable: true, edittype:"select", editoptions: {value:"Y:Y;N:N"}, editrules: {required: true}}
    ];
	var opts = {
			"colModel":colModel,
			pager:"#jqGridPager",
			multiselect: false,
			loadonce: true,
			isPaging: false,
			scroll: 1,
			autowidth:true,
			height:680,
			shrinkToFit: true,
	        onSelectRow : function (rowid, status, e) {
	        	var $radio = $('input[type=radio]', $("#"+rowid));
	            $radio.prop('checked', 'checked');
	            $("#"+rowid).addClass("success");
			}
	};
	var navOpts = {
			navOptions:{
				add:false,
				del:false,
				edit:false,
				refresh:false,
				search:true,
				view:false,
				cloneToTop:false,
			}
		};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"system.app.getApp",
			"searchKey":$("#searchValue").attr("de-search-key"),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});

	$("button#btnInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			["system.appView"],
			{
				viewname:'system/system.appView',
				mode:'C'
			},
		    "width=800, height=600, toolbar=non, menubar=no"
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
			["system.appView"],
			{
				viewname:'system/system.appView',
				"appId":rowData["APP_ID"],
				mode:'U'
			},
		    "width=800, height=600, toolbar=non, menubar=no"
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
				"appId":rowData["APP_ID"]
			};
			
			var opts = {
				url : "system/app?oper=remove",
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
	
	$(window).resize(function () {
	    var outerwidth = $('.box-body').width();
        $('#jqGrid').setGridWidth(outerwidth); // setGridWidth method sets a new width to the grid dynamically
	});
	
	var init = function() {
		$("button#btnSearch").trigger("click");
	};
	init();
	$(window).trigger("resize");
});