$(document).ready(function() {
	 var colModel = [
	    {name:'MENU_ID',index:'MENU_ID',  label: '메뉴ID',  width:50, align:'center', hidden:true, key:true},
		{name:'MENU_NM',index:'MENU_NM',  label: '메뉴명', width:280, align: 'left'},
		{name:'MENU_TYPE',index:'MENU_TYPE',  label: '메뉴유형',  width:100, align: 'left'},	        
        {name:'UP_MENU_ID',index:'UP_MENU_ID', label: 'UP_MENU_ID',  hidden:true},
        {name:'PGM_ID',index:'PGM_ID',  label: 'PGM_ID', hidden:true},
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
		shrinkToFit: false,
		altRows:false,
		scroll:false,
        rownumbers: false,
        multiselect:true,
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
		onSelectRow : function (rowid, status, e) {
			debugger;
			var rowData = $(this).jqGrid("getLocalRow", rowid);
			if (rowData["MENU_TYPE"] === "FOLDER") {
				clearGridData();
				$("button", "#btn-wrapper").prop("disabled", true);
			} else {
				$("button", "#btn-wrapper").prop("disabled", false);
				viewAuthList();
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
			cloneToTop:false
		}
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);

	var colModel2 = [
	    {name:'CHK',index:'CHK', label:"<input type='checkbox' id='chkAll' name='chkAll' onclick='javascript:DE.jqgrid.checkAll(this, event);'/>", width:55, align:"center",
			sortable:false,
			formatter: 'checkbox',
			formatoptions: {disabled:false},
			edittype:'checkbox',
			editoptions:{value:"true:false"},
			editable:true
		},
		{ index: 'USER_GRP_ID', name: 'USER_GRP_ID', label: '사용자 그룹ID', width: 300, align: 'left'},	        
		{name:'USER_GRP_NM',index:'USER_GRP_NM',  label: '사용자그룹명',  width:270, align: 'left'},
		{name:'C_AUTH',index:'C_AUTH',  label: '등록', width:100, align: 'center', edittype:'checkbox',formatter:'checkbox',editoptions:{value:"Y:N"},editable:true, formatoptions: {disabled : false}},
        {name:'R_AUTH',index:'R_AUTH',  label: '읽기', width:100, align:"center", edittype:'checkbox',formatter:'checkbox',editoptions:{value:"Y:N"},editable:true, formatoptions: {disabled : false}},
        {name:'U_AUTH',index:'U_AUTH', label: '수정',  width:100, align:"center", edittype:'checkbox',formatter:'checkbox',editoptions:{value:"Y:N"},editable:true, formatoptions: {disabled : false}},
        {name:'D_AUTH',index:'D_AUTH', label: '삭제',  width:100, align:"center", edittype:'checkbox',formatter:'checkbox',editoptions:{value:"Y:N"},editable:true, formatoptions: {disabled : false}},
        {name:'E_AUTH',index:'E_AUTH', label: '실행',  width:100, align:"center", edittype:'checkbox',formatter:'checkbox',editoptions:{value:"Y:N"},editable:true, formatoptions: {disabled : false}},
        {name:'ACTION',index:'ACTION', label:'적용', width:80, align:'center', title: false,
			formatter:"customButton",
			formatoptions: {
				caption:"적용",
				onClick: function (rowid, irow, icol, cellvalue, e) {
					var rowData = $(this).jqGrid("getRowData", rowid);
					DE.ajax.call({
            			url:"system/menu?oper=updateUserGrp", 
            			data:{
            				"APP_ID":rowData["APP_ID"],
                        	"MENU_ID":rowData["MENU_ID"],
                        	"PRIV_RCVR_GBN":rowData["PRIV_RCVR_GBN"],
                        	"PRIV_RCVR_ID":rowData["PRIV_RCVR_ID"],
                        	"PRIV_OPER_GBN":rowData["C_AUTH"]+":"+rowData["R_AUTH"]+":"+rowData["U_AUTH"]+":"+rowData["D_AUTH"]+":"+rowData["E_AUTH"]
            			}
            		},
            		function(){
                		DE.box.alert("적용 되었습니다.");
        			});
				}
			}
    	},
        {name:'MENU_ID',index:'MENU_ID', label: 'MENU_ID',  hidden:true},
		{name:'MENU_NM',index:'MENU_NM', label: 'MENU_NM',  hidden:true},
		{name:'PRIV_RCVR_ID',index:'PRIV_RCVR_ID', label: 'PRIV_RCVR_ID',  hidden:true},
		{name:'PRIV_RCVR_GBN',index:'PRIV_RCVR_GBN', label: 'PRIV_RCVR_GBN',  hidden:true},
		{name:'APP_ID',index:'APP_ID', label: 'APP_ID',  hidden:true}
   ];

	var opts2 = {
		"colModel":colModel2, 
		pager:"#jqGridPager2",
		multiselect: false,
		loadonce: true,
		isPaging: false,
		scroll: 1,
		autowidth:true,
		shrinkToFit: true,
        gridComplete: function() {
            DE.jqgrid.checkedHandler($(this));
        }
	};

	var navOpts2 = {
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
		
	
	var $grid2 = DE.jqgrid.render($("#jqGrid2"), opts2);
	DE.jqgrid.navGrid($("#jqGrid2"), $("#jqGridPager2"), navOpts2);
	
	var colModel3 = [
		{name:'CHK',index:'CHK', label:"<input type='checkbox' id='chkAll' name='chkAll' onclick='javascript:DE.jqgrid.checkAll(this, event);'/>", width:55, align:"center",
			sortable:false,
			formatter: 'checkbox',
			formatoptions: {disabled:false},
			edittype:'checkbox',
			editoptions:{value:"true:false"},
			editable:true
		},
		{ index: 'USER_ID', name: 'USER_ID', label: '사용자 ID', width: 300, align: 'left'},	        
		{name:'USER_NM',index:'USER_NM',  label: '사용자명',  width:280, align: 'left'},
		{name:'C_AUTH',index:'C_AUTH',  label: '등록', width:100, align: 'center', edittype:'checkbox',formatter:'checkbox',editoptions:{value:"Y:N"},editable:true, formatoptions: {disabled : false}},
        {name:'R_AUTH',index:'R_AUTH',  label: '읽기', width:100, align:"center", edittype:'checkbox',formatter:'checkbox',editoptions:{value:"Y:N"},editable:true, formatoptions: {disabled : false}},
        {name:'U_AUTH',index:'U_AUTH', label: '수정',  width:100, align:"center", edittype:'checkbox',formatter:'checkbox',editoptions:{value:"Y:N"},editable:true, formatoptions: {disabled : false}},
        {name:'D_AUTH',index:'D_AUTH', label: '삭제',  width:100, align:"center", edittype:'checkbox',formatter:'checkbox',editoptions:{value:"Y:N"},editable:true, formatoptions: {disabled : false}},
        {name:'E_AUTH',index:'E_AUTH', label: '실행',  width:100, align:"center", edittype:'checkbox',formatter:'checkbox',editoptions:{value:"Y:N"},editable:true, formatoptions: {disabled : false}},
        {name:'ACTION',index:'ACTION', label:'적용', width:80, align:'center', title: false,
			formatter:"customButton",
			formatoptions: {
				caption:"적용",
				onClick: function (rowid, irow, icol, cellvalue, e) {
					var rowData = $(this).jqGrid("getRowData", rowid);
					DE.ajax.call({
            			url:"system/menu?oper=updateUserGrp", 
            			data:{
            				"APP_ID":rowData["APP_ID"],
                        	"MENU_ID":rowData["MENU_ID"],
                        	"PRIV_RCVR_GBN":rowData["PRIV_RCVR_GBN"],
                        	"PRIV_RCVR_ID":rowData["PRIV_RCVR_ID"],
                        	"PRIV_OPER_GBN":rowData["C_AUTH"]+":"+rowData["R_AUTH"]+":"+rowData["U_AUTH"]+":"+rowData["D_AUTH"]+":"+rowData["E_AUTH"]
            			}
            		},
            		function(){
                		DE.box.alert("적용 되었습니다.");
        			});
				}
			}
    	},
        {name:'MENU_ID',index:'MENU_ID', label: 'MENU_ID',  hidden:true},
		{name:'MENU_NM',index:'MENU_NM', label: 'MENU_NM',  hidden:true},
		{name:'PRIV_RCVR_ID',index:'PRIV_RCVR_ID', label: 'PRIV_RCVR_ID',  hidden:true},
		{name:'PRIV_RCVR_GBN',index:'PRIV_RCVR_GBN', label: 'PRIV_RCVR_GBN',  hidden:true},
		{name:'APP_ID',index:'APP_ID', label: 'APP_ID',  hidden:true}
   ];


	var opts3 = {
			"colModel":colModel3, 
			pager:"#jqGridPager3",
			multiselect: false,
			loadonce: true,
			isPaging: false,
			scroll: 1,
			autowidth:true,
			shrinkToFit: true,
	        gridComplete: function() {
	            DE.jqgrid.checkedHandler($(this));
	        }
	};

	var navOpts3 = {
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
	
	var $grid3 = DE.jqgrid.render($("#jqGrid3"), opts3);
	DE.jqgrid.navGrid($("#jqGrid3"), $("#jqGridPager3"), navOpts3);
	
	var clearGridData = function() {
		$("#jqGrid2").jqGrid("clearGridData");
		$("#jqGrid3").jqGrid("clearGridData");
	}
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"system.menu.getMenuList",
			"appId":$("#systemApp").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
		
	$("button#btnRemove").on("click", function(e) {
		var $grid;
		if($(".tab-pane.active", $("#tapMain").next(".tab-content")).prop("id") === "tab-001") {
			$grid = $("#jqGrid2");
		} else if($(".tab-pane.active", $("#tapMain").next(".tab-content")).prop("id") === "tab-002") {
			$grid = $("#jqGrid3");
		} else {
			return;
		}
		var data = $grid.jqGrid("getGridParam", "data");
        var checkedData = [];
        $.each(data, function(index, value){
            if (value["CHK"] === "true") {
                checkedData.push({
                	"appId":value["APP_ID"],
                	"menuId":value["MENU_ID"],
                	"privRcvrGbn":value["PRIV_RCVR_GBN"],
                	"privRcvrId":value["PRIV_RCVR_ID"]
                });
                
            }
        });

        if (checkedData.length === 0) {
        	DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
        }
		
	    if(checkedData.length === 0) {
	    	DE.box.alert("삭제할 항목을 선택해주세요.");
	    	return;
	    }else {
	    	DE.box.confirm("삭제 하시겠습니까?", function (b) {
            	if (b === true) {
            		DE.ajax.call({
            			url:"system/menu?oper=removeUserGrp", 
            			data:checkedData
            		},
            		function(){
                		DE.box.alert("삭제 되었습니다.", function(){
                			viewAuthList();
                		});
        			});
            	}
            });
	    }
	});	
	
	$("button#btnInsert").on("click", function(e) {
		var rowid = $("#jqGrid").jqGrid("getGridParam", "selrow");
		if(rowid == null) {
			DE.box.alert("선택된 메뉴가 없습니다.");
			return;
		} 			

		var rowData = $("#jqGrid").jqGrid("getLocalRow", rowid);
		if($(".tab-pane.active", $("#tapMain").next(".tab-content")).prop("id") === "tab-001") {
			DE.ui.open.popup(
				"view",
				["menuAuthAddGrpPop"],
				{
					viewname:'system/menuAuthAddGrpPop',
					menuId:rowData["MENU_ID"],
					menuNm:rowData["MENU_NM"],
					appId:$("#systemApp").val()
				},
				"width=1000, height=352, left=100, top=300,toolbar=no, menubar=no, location=no"
			);
		} else if($(".tab-pane.active", $("#tapMain").next(".tab-content")).prop("id") === "tab-002") {
			DE.ui.open.popup(
					"view",
					["menuAuthAddUsrPop"],
					{
						viewname:'system/menuAuthAddUsrPop',
						menuId:rowData["MENU_ID"],
						menuNm:rowData["MENU_NM"],
						appId:$("#systemApp").val()
					},
					"width=1000, height=352, left=100, top=300,toolbar=no, menubar=no, location=no"
				);
		}
	});
	
	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
		$(window).trigger("resize");
	});
	
	var init = function() {
		var rsp = DE.ajax.call({async:false, url:"system/app?oper=simpleList", data:{}});
		var data = rsp["data"];
		$.each(data, function(index, value){
			$("#systemApp").append("<option value=\""+value["APP_ID"]+"\">"+value["APP_NM"]+"</option>");
		});

		$("button", "#btn-wrapper").prop("disabled", true);
		$("button#btnSearch").trigger("click");
	};
	init();
	//$(window).trigger("resize");
	
	var viewAuthList = function() {
		var rowid = $("#jqGrid").jqGrid("getGridParam", "selrow");
		if (rowid === null) {
			return;
		}
		var rowData = $("#jqGrid").jqGrid("getLocalRow", rowid);
		if($(".tab-pane.active", $("#tapMain").next(".tab-content")).prop("id") === "tab-001") {
			var postData2 = {
				"queryId":"system.user.getUserMenuGrp",
				"searchValue":"",
				"menuId":rowData["MENU_ID"]
			};
		
			DE.jqgrid.reload($("#jqGrid2"), postData2);
		} else if($(".tab-pane.active", $("#tapMain").next(".tab-content")).prop("id") === "tab-002") {
			var postData3 = {
					"queryId":"system.user.getUserMenuUsr",
					"searchValue":"",
					"menuId":rowData["MENU_ID"]
				};			
				DE.jqgrid.reload($("#jqGrid3"), postData3);
		}
	};
	
	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
		viewAuthList();
	});
	
	$("#systemApp").on("change", function(e) {
		$("button#btnSearch").trigger("click");
	});
	
	$("#searchValue").keypress(function (event){
		if(event.which==13) {
			$("button#btnSearch").trigger("click");
		}
	});
	
	$(window).resize(function () {
	    var outerwidth = $('.box-body').width();
        $('#jqGrid').setGridWidth(outerwidth/3.19);
        $('#jqGrid2').setGridWidth(outerwidth);// setGridWidth method sets a new width to the grid dynamically
        $('#jqGrid3').setGridWidth(outerwidth);
	});
	
});