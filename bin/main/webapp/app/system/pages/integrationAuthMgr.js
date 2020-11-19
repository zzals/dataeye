var isReflash = false;
$(document).ready(function () {
    var userGrpId;
    var colModel = [
        {name:'CHK',index:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
		{ index: 'USER_GRP_ID', name: 'USER_GRP_ID', label: '사용자/그룹ID', width: 200, align: 'left', editable: true, editrules: {required: false}, key:true},
        { index: 'USER_GRP_NM', name: 'USER_GRP_NM', label: '사용자/그룹명', width: 200, align: 'left', editable: true, editrules: {required: false}},
        /*, formatter: "dynamicLink",
            formatoptions: {
                onClick: function (rowid, irow, icol, cellvalue, e) {
                    var rowData = $(this).jqGrid("getRowData", rowid);
                    DE.ui.open.popup(
                        "view",
                        ['authGrpView'],
                        {
                        	viewname: 'system/authGrpView',
                            userGrpId: rowData["USER_GRP_ID"],
                            mode: 'R'
                        },
                        null
                    );
                }
            }
        },*/
        { index: 'UP_USER_GRP_ID', name: 'UP_USER_GRP_ID', label: '상위 사용자 그룹ID', width: 200, align: 'left', editable: true, editrules: {required: false}, hidden:true},
        {name:'DEPTH',index:'DEPTH',  label: 'DEPTH', hidden:true}
    ];
    var opts = {
	    		"colModel":colModel, 
				pager:"#jqGridPager",
				multiselect: false,
				rownumbers: false,
				autowidth:true,
				height:620,
				treeGrid:true,
				ExpandColumn:"USER_GRP_ID",
				treedatatype:"json",
				treeGridModel:"adjacency", //"adjacency, nested",
				treeReader : {
		            level_field: "DEPTH",
		            parent_id_field: "UP_USER_GRP_ID",
		            leaf_field: "IS_LEAF",
		            expanded_field: false
		        },
		        ExpandColClick : false,
		        localReader: {
					id: "USER_GRP_ID"
				},
				onSelectRow : function (rowid, status, e) {
		        	var $radio = $('input[type=radio]', $("#"+rowid));
		            $radio.prop('checked', 'checked');
		            $("#"+rowid).addClass("success");
		            
		            var rowData = $(this).getRowData(rowid);
		            userGrpId = rowData.USER_GRP_ID;
		            //gridDetailList(userGrpId);
		        },
		        gridComplete: function() {
		            var ids = $("#jqGrid").jqGrid("getDataIDs");
		            if(ids.length > 0) {
		                $("#jqGrid").jqGrid("setSelection", ids[0]);
		            }
		            else {
		                userGrpId = "";
		                //$("#jqGrid2").clearGridData();
		            }
		        }
    };

    var navOpts = {
        navOptions: {
            add: false,
            del: false,
            edit: false,
            refresh: false,
            search: false,
            view: false,
            cloneToTop: false
        }
    };

    var $grid = DE.jqgrid.render($("#jqGrid"), opts);
    DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);

    var colModel2 = [
    	{name:'CHK',index:'CHK', label:"선택", width:55, align:"center", 
    		formatter: function (cellvalue, options, rowObject) { 
    			return "<input type='checkbox' class='itmchk' name='itmchk' value='"+rowObject["MENU_ID"]+"'>";
    		}, 
    		fixed:true
    	},
		{name:'MENU_ID',index:'MENU_ID',  label: '메뉴ID',  width:50, align:'center', hidden:true, key:true},
		{name:'MENU_NM',index:'MENU_NM',  label: '메뉴명', width:280, align: 'left'},
		{name:'MENU_TYPE',index:'MENU_TYPE',  label: '메뉴유형',  width:100, align: 'left'},	        
        {name:'UP_MENU_ID',index:'UP_MENU_ID', label: 'UP_MENU_ID', hidden:true},
        {name:'PGM_ID',index:'PGM_ID',  label: 'PGM_ID', hidden:true},
        {name:'DEPTH',index:'DEPTH',  label: 'DEPTH', hidden:true},
        {name:'CRET_DT',index:'CRET_DT',  label: 'CRET_DT', hidden:true},
		{name:'CRETR_ID',index:'CRETR_ID', label: 'CRETR_ID', hidden:true},
		{name:'CHG_DT',index:'CHG_DT', label: 'CHG_DT', hidden:true},
		{name:'CHGR_ID',index:'CHGR_ID', label: 'CHGR_ID', hidden:true},			
		{name:'IS_EXPANDED',index:'IS_EXPANDED', label: 'IS_EXPANDED',  hidden:true},
		{name:'IS_LEAF',index:'IS_LEAF', label: 'IS_LEAF',  hidden:true}
    ];
	
    var opts2 = {	
		"colModel":colModel2, 
		pager:"#jqGridPager2",
		multiselect: false,
        rownumbers: false,
        height:575,
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
        beforeSelectRow: function (rowid, e) {
        	var $this = $(this),
            isLeafName = $this.jqGrid("getGridParam", "treeReader").leaf_field,
            localIdName = $this.jqGrid("getGridParam", "localReader").id,
            localData,
            state,
            setChechedStateOfChildrenItems = function (children) {
                $.each(children, function () {
                    $("#" + this[localIdName] + " input.itmchk").prop("checked", state);
                    if (!this[isLeafName]) {
                        setChechedStateOfChildrenItems($this.jqGrid("getNodeChildren", this));
                    }
                });
            };
	        if (e.target.nodeName === "INPUT" && $(e.target).hasClass("itmchk")) {
	            state = $(e.target).prop("checked");
	            localData = $this.jqGrid("getLocalRow", rowid);
	            setChechedStateOfChildrenItems($this.jqGrid("getNodeChildren", localData), state);
	        }
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
			cloneToTop:false
		}
	};
	
    var $grid2 = DE.jqgrid.render($("#jqGrid2"), opts2);
    DE.jqgrid.navGrid($("#jqGrid2"), $("#jqGridPager2"), navOpts2);

    var colModel3 = [
    	{name:'CHK',index:'CHK', label:"<input type='checkbox' id='chkAll3' name='chkAll3' onclick='javascript:DE.jqgrid.checkAll(this, event);'/>", width:55, align:"center",
			sortable:false,
			formatter: 'checkbox',
			formatoptions: {disabled:false},
			edittype:'checkbox',
			editoptions:{value:"true:false"},
			editable:true,
			fixed:true
		},
        { index: 'USER_GRP_ID', name: 'USER_GRP_ID', label: '권한그룹명', width: 150, align: 'left', editable: true, editrules: {required: false}},
        { index: 'USER_GRP_NM', name: 'USER_GRP_NM', label: '소유자', width: 150, align: 'center', hidden:true},
        { index: 'USER_GRP_DESC', name: 'USER_GRP_DESC', label: '생성일', width: 150, align: 'center', hidden:true}
    ];

    var opts3 = {	
    		"colModel":colModel3, 
    		pager:"#jqGridPager3",
    		editurl: 'obj/process',
    		rownumbers: false,
			multiselect: false,
			loadonce: true,
			isPaging: false,
			scroll: 1,
			autowidth:true,
			height:575,
			shrinkToFit: true,
	        gridComplete: function() {
	            DE.jqgrid.checkedHandler($(this));
	        }
    	};

    var navOpts3 = {
        navOptions: {
            add: false,
            del: false,
            edit: false,
            refresh: false,
            search: false,
            view: false,
            cloneToTop: false
        }
    };

    var $grid3 = DE.jqgrid.render($("#jqGrid3"), opts3);
    DE.jqgrid.navGrid($("#jqGrid3"), $("#jqGridPager3"), navOpts3);
    var pagesize = $("#jqGrid3").getGridParam('records');
    var addData = [{"USER_GRP_ID":"Smile Biz", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"Traffic", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"결제", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"광고", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"배송/물류", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"상품", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"수수료", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"주문", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"할인/쿠폰", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"회원", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"}
    				]
    $("#jqGrid3").addRowData(pagesize+10, addData)

    var colModel4 = [
    	{name:'CHK',index:'CHK', label:"<input type='checkbox' id='chkAll4' name='chkAll4' onclick='javascript:DE.jqgrid.checkAll(this, event);'/>", width:55, align:"center",
			sortable:false,
			formatter: 'checkbox',
			formatoptions: {disabled:false},
			edittype:'checkbox',
			editoptions:{value:"true:false"},
			editable:true,
			fixed:true
		},
        { index: 'USER_GRP_ID', name: 'USER_GRP_ID', label: '권한그룹명', width: 150, align: 'left', editable: true, editrules: {required: false}},
        { index: 'USER_GRP_NM', name: 'USER_GRP_NM', label: '소유자', width: 150, align: 'center', hidden:true},
        { index: 'USER_GRP_DESC', name: 'USER_GRP_DESC', label: '생성일', width: 150, align: 'center', hidden:true}
    ];

    var opts4 = {	
    		"colModel":colModel4, 
    		pager:"#jqGridPager4",
    		editurl: 'obj/process',
    		rownumbers: false,
			multiselect: false,
			loadonce: true,
			isPaging: false,
			scroll: 1,
			autowidth:true,
			height:575,
			shrinkToFit: true,
	        gridComplete: function() {
	            DE.jqgrid.checkedHandler($(this));
	        }
    	};

    var navOpts4 = {
        navOptions: {
            add: false,
            del: false,
            edit: false,
            refresh: false,
            search: false,
            view: false,
            cloneToTop: false
        }
    };

    var $grid4 = DE.jqgrid.render($("#jqGrid4"), opts4);
    DE.jqgrid.navGrid($("#jqGrid4"), $("#jqGridPager4"), navOpts4);
    
    pagesize = $("#jqGrid4").getGridParam('records');
    addData = [
    				{"USER_GRP_ID":"회원", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"할인/쿠폰", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"주문", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"수수료", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"상품", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"Smile Biz", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"Traffic", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"결제", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"},
    				{"USER_GRP_ID":"광고", "USER_GRP_NM":"Administrator", "USER_GRP_DESC":"2020-05-29 16:18:45"}
    				]
    $("#jqGrid4").addRowData(pagesize+10, addData)
    
    var clearGridData = function() {
		$("#jqGrid2").jqGrid("clearGridData");
		$("#jqGrid3").jqGrid("clearGridData");
		$("#jqGrid4").jqGrid("clearGridData");
	}
    
    $("button#btnSearch").on("click", function (e) {    	
    	if(isReflash) {
    		//gridDetailList(userGrpId);
    	} else {
            var postData = {
                "queryId": "system.user.getSysUserGrpM2",
                "searchKey": $("#searchKey").val(),
                "searchValue": $("#searchValue").val(),
                //"notUserGrpType":"ROLE"
                "userGrpType":"ROLE"
            };
            DE.jqgrid.reload($("#jqGrid"), postData);
            
            var postData2 = {
					"queryId":"system.menu.getMenuList2",
					"appId":$("#systemApp").val()
			};
			DE.jqgrid.reload($("#jqGrid2"), postData2);
    	}
    	
    });

    $("button#btnInsert1").on("click", function (e) {
    	DE.box.confirm("저장 하시겠습니까?", function (b) {
    		if (b === true) {
    			var checkedData = [];
	    		$('[name=itmchk]').each(function(index,result){
	                if(result.checked){
	                    checkedData.push(result.value);
	                }
	            });
	    		
	    		if (checkedData.length === 0) {
                	DE.box.alert(DE.i18n.prop("common.message.selected.none"));
        			return;
                }
	    		
	    		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
        		if ($selRadio.length === 0) {
        			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
        			return;
        		}
        		var rowid = $selRadio.closest("tr").prop("id");
        		var rowData = $grid.jqGrid("getRowData", rowid);
        		
        		var postData = {
                    "userGrpId": rowData["USER_GRP_ID"],
                    "menuIds": checkedData
                };
        		
        		alert(checkedData);
        		
        		var saveComplete = function(rsp) {
                    if ("SUCCESS" === rsp["status"]) {
                        DE.box.alert(rsp["message"], function () {
                            //gridDetailList(userGrpId);
                        });
                    }
                };
                //DE.ajax.call({url:"system/grpUserR?oper=remove", data:postData}, saveComplete);
    		}
    	});
    	
    });
    
    $("input[id=searchValue]").on("keypress", function (e) {
        if (e.which === 13) {
        	$("button#btnSearch").trigger("click");
        }
    });

	$("#jqGrid").delegate("[name=radio_jqGrid]", "change", function(e){
    	$("#jqGrid").jqGrid("setSelection", $(this).closest("tr").prop("id"));
    });

    var init = function () {
    	var rsp = DE.ajax.call({async:false, url:"system/app?oper=simpleList", data:{}});
		var data = rsp["data"];
		$.each(data, function(index, value){
			$("#systemApp").append("<option value=\""+value["APP_ID"]+"\">"+value["APP_NM"]+"</option>");
		});

		$("button", "#btn-wrapper").prop("disabled", true);
        $("button#btnSearch").trigger("click");
        $(".ui-jqgrid-bdiv").css("overflow-x","hidden");
    };
    init();
    
    var viewAuthList = function() {
		var rowid = $("#jqGrid").jqGrid("getGridParam", "selrow");
		if (rowid === null) {
			return;
		}
		var rowData = $("#jqGrid").jqGrid("getLocalRow", rowid);
		if($(".tab-pane.active", $("#tapMain").next(".tab-content")).prop("id") === "tab-001") {
			var postData2 = {
					"queryId":"system.menu.getMenuList2",
					"appId":$("#systemApp").val()
			};
			DE.jqgrid.reload($("#jqGrid2"), postData2);
		} else if($(".tab-pane.active", $("#tapMain").next(".tab-content")).prop("id") === "tab-002") {
//			var postData3 = {
//					"queryId":"system.user.getUserMenuGrp",
//					"searchValue":"",
//					"menuId":rowData["MENU_ID"]
//				};			
//				DE.jqgrid.reload($("#jqGrid3"), postData3);
		}  else if($(".tab-pane.active", $("#tapMain").next(".tab-content")).prop("id") === "tab-003") {
//			var postData3 = {
//					"queryId":"system.user.getUserMenuGrp",
//					"searchValue":"",
//					"menuId":rowData["MENU_ID"]
//				};			
//				DE.jqgrid.reload($("#jqGrid3"), postData3);
		}
	};
	
	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
		viewAuthList();
	});
	
	$(window).resize(function () {
	    var outerwidth = $('.box-body').width();
        $('#jqGrid, #jqGrid2, #jqGrid3, #jqGrid4').setGridWidth(outerwidth/3.25); // setGridWidth method sets a new width to the grid dynamically
	});
});