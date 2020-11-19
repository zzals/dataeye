$(document).ready(function() {
	var colModel = [
	    { index:'CHK', name:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
	    {name:'MENU_ID',index:'MENU_ID',  label: '메뉴ID',  width:50, align:'center', hidden:true, key:true},
		{name:'MENU_NM',index:'MENU_NM',  label: '메뉴명', width:150, align: 'left'},
		{name:'MENU_TYPE',index:'MENU_TYPE',  label: '메뉴유형',  width:50, align: 'left'},	        
		{name:'PGM_ID',index:'PGM_ID',  label: '페이지링크',  width:300, align: 'left'},
		{name:'SORT_NO',index:'SORT_NO',  label: '정렬순서',  width:50, align: 'right'},
		{name:'ICON_CLASS',index:'ICON_CLASS',  label: 'ICON_CLASS',  width:100, align: 'left'},
		{name:'USE_YN',index:'USE_YN',  label: '사용여부',  width:50, align: 'center'},
        {name:'UP_MENU_ID',index:'UP_MENU_ID', label: 'UP_MENU_ID',  hidden:true},
        {name:'PGM_ID',index:'PGM_ID',  label: 'PGM_ID', hidden:true},
        {name:'APP_ID',index:'APP_ID',  label: 'APP_ID', hidden:true},
        {name:'DEPTH',index:'DEPTH',  label: 'DEPTH', hidden:true},
        {name:'CRET_DT',index:'CRET_DT',  label: 'CRET_DT', hidden:true},
		{name:'CRETR_ID',index:'CRETR_ID', label: 'CRETR_ID',  hidden:true},
		{name:'CHG_DT',index:'CHG_DT', label: 'CHG_DT',  hidden:true},
		{name:'CHGR_ID',index:'CHGR_ID', label: 'CHGR_ID',  hidden:true},			
		{name:'IS_EXPANDED',index:'IS_EXPANDED', label: 'IS_EXPANDED',  hidden:true},
		{name:'IS_LEAF',index:'IS_LEAF', label: 'IS_LEAF',  hidden:true}
	];
	
	var opts = {
			"colModel":colModel, 
			pager:"#jqGridPager",
		 	autowidth: true,
			shrinkToFit: true,
			altRows:false,
			scroll:false,
	        rownumbers: false,
	        multiselect:false,
	        loadonce: false,
	        height:620,
	        viewrecords: false,
	        sortable: false,   
			treeGrid:true,
			ExpandColumn:"MENU_NM",
			treedatatype:"json",
			treeGridModel:"adjacency", //"adjacency, nested",
	        treeReader : {
	            level_field: "DEPTH",
	            parent_id_field: "UP_MENU_ID",
	            leaf_field: "IS_LEAF",
	            expanded_field: "IS_EXPANDED"
	        },
	        ExpandColClick : false,
			localReader: {
				id: "MENU_ID"
			},
			onSelectRow : function (rowid){
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
				search:false,
				view:false,
				cloneToTop:false
			}
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);
		
	$("button#btnInsert").on("click", function(e) {
		debugger;
		DE.ui.open.popup(
			"view",
			["system.menuView"],
			{
				viewname:'system/menuView',
				mode:'C',
				appId:$("#systemApp").val()
			},
		    "width=1000, height=800, toolbar=non, menubar=no"
		);
	});
	
	$("button#btnUpdate").on("click", function(e) {
		debugger;
		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $grid.jqGrid("getRowData", rowid);
		
		DE.ui.open.popup(
			"view",
			["system.menuView"],
			{
				viewname:'system/menuView',
				mode:'U',
				appId:rowData["APP_ID"],
				menuId:rowData["MENU_ID"]
			},
		    "width=1000, height=800, toolbar=non, menubar=no"
		);
	});
	
	$("button#btnRemove").on("click", function(e) {		
		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $grid.jqGrid("getRowData", rowid);
		
		var callback = {
			success : function(response) {
				DE.box.alert(response["message"], function(){ $("#btnSearch").trigger("click");});
			},
			error : function(response) {
				DE.box.alert(response["responseJSON"]["message"]);
			}
		};
		var removeAction = function() {
			var formData = {
				menuId:rowData["MENU_ID"]
			};
			
			var opts = {
				url : "system/menu?oper=remove",
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
	
	$("#systemApp").on("change", function(e) {
		$("#btnSearch").trigger("click");
	});
	
	$("#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"system.menu.getMenuList",
			"appId":$("#systemApp").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
    $("#jqGrid").delegate("[name=radio_jqGrid]", "change", function(e){
    	$("#jqGrid").jqGrid("setSelection", $(this).closest("tr").prop("id"));
    });
    
	var init = function() {
		var rsp = DE.ajax.call({async:false, url:"system/app?oper=simpleList", data:{}});
		var data = rsp["data"];
		$.each(data, function(index, value){
			$("#systemApp").append("<option value=\""+value["APP_ID"]+"\">"+value["APP_NM"]+"</option>");
		});
		
		$("#btnSearch").trigger("click");
		$(".ui-jqgrid-bdiv").css("overflow-x","hidden");
	};
	init();

	$(window).resize(function () {
	    var outerwidth = $('.box-body').width();
        $('#jqGrid').setGridWidth(outerwidth); // setGridWidth method sets a new width to the grid dynamically        
	});
});