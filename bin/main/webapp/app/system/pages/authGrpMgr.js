var isReflash = false;
$(document).ready(function () {
    var userGrpId;
    var colModel = [
        {name:'CHK',index:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
		{ index: 'USER_GRP_ID', name: 'USER_GRP_ID', label: '사용자 그룹ID', width: 200, align: 'left', editable: true, editrules: {required: false}},
        { index: 'USER_GRP_NM', name: 'USER_GRP_NM', label: '사용자 그룹명', width: 200, align: 'left', editable: true, editrules: {required: false}, formatter: "dynamicLink",
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
        },
        { index: 'USER_GRP_DESC', name: 'USER_GRP_DESC', label: '사용자그룹 설명', width: 300, align: 'left', editable: true, editrules: {required: false}},
        { index: 'USER_GRP_TYPE', name: 'USER_GRP_TYPE', label: '사용자 그룹 유형', width: 150, align: 'left', editable: true, editrules: {required: false}},
        { index: 'PRIV_YN', name: 'PRIV_YN', label: '권한 적용 여부', width: 120, fixed:true, align: 'center', editable: true, edittype: "select", editoptions: {value: "Y:Y;N:N"}, editrules: {required: true}},
        { index: 'USE_YN', name: 'USE_YN', label: '사용여부', width: 120, fixed:true,  align: 'center', editable: true, edittype: "select", editoptions: {value: "Y:Y;N:N"}, editrules: {required: true}}
    ];
    var opts = {
	    		"colModel":colModel, 
				pager:"#jqGridPager",
				editurl: 'obj/process',
				multiselect: false,
				loadonce: true,
				isPaging: false,
				scroll: 1,
				autowidth:true,
				height:260,
				shrinkToFit: true,
				onSelectRow : function (rowid, status, e) {
		        	var $radio = $('input[type=radio]', $("#"+rowid));
		            $radio.prop('checked', 'checked');
		            $("#"+rowid).addClass("success");
		            
		            var rowData = $(this).getRowData(rowid);
		            userGrpId = rowData.USER_GRP_ID;
		            gridDetailList(userGrpId);
		        },
		        gridComplete: function() {
		            var ids = $("#jqGrid").jqGrid("getDataIDs");
		            if(ids.length > 0) {
		                $("#jqGrid").jqGrid("setSelection", ids[0]);
		            }
		            else {
		                userGrpId = "";
		                $("#jqGrid2").clearGridData();
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
    	{name:'CHK',index:'CHK', label:"<input type='checkbox' id='chkAll' name='chkAll' onclick='javascript:DE.jqgrid.checkAll(this, event);'/>", width:18, align:"center",
            sortable:false,
            formatter: 'checkbox',
            formatoptions: {disabled:false},
            edittype:'checkbox',
            editoptions:{value:"true:false"},
            editable:true
        },
        { index: 'USER_GRP_ID', name: 'USER_GRP_ID', label: '사용자그룹ID', width: 150, align: 'left', editable: true, editrules: {required: false}},
        { index: 'USER_GRP_NM', name: 'USER_GRP_NM', label: '사용자그룹명', width: 150, align: 'left'},
        { index: 'USER_GRP_DESC', name: 'USER_GRP_DESC', label: '사용자그룹설명', width: 400, align: 'left', editable: true, editrules: {required: false}}
    ];

    var opts2 = {
    		"colModel":colModel2, 
			pager:"#jqGridPager2",
			editurl: 'obj/process',
			multiselect: false,
			loadonce: true,
			isPaging: false,
			scroll: 1,
			autowidth:true,
			height:260,
			shrinkToFit: true,
        gridComplete: function() {
            DE.jqgrid.checkedHandler($(this));
        }
    };

    var navOpts2 = {
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

    var $grid2 = DE.jqgrid.render($("#jqGrid2"), opts2);
    DE.jqgrid.navGrid($("#jqGrid2"), $("#jqGridPager2"), navOpts2);

    $("button#btnSearch").on("click", function (e) {    	
    	if(isReflash) {
    		gridDetailList(userGrpId);
    	} else {
            var postData = {
                "queryId": "system.user.getSysUserGrpM",
                "searchKey": $("#searchKey").val(),
                "searchValue": $("#searchValue").val(),
                "notUserGrpType":"ROLE"
            };
            DE.jqgrid.reload($("#jqGrid"), postData);
    	}
    	
    });

    $("input[id=searchValue]").on("keypress", function (e) {
        if (e.which === 13) {
        	$("button#btnSearch").trigger("click");
        }
    });

    $("button#btnAuthGrpInsert").on("click", function (e) {
        DE.ui.open.popup(
			"view",
			["authGrpView"],
			{
				viewname:'system/authGrpView',
				mode:'C'
			},
			null
		);
    });

    $("button#btnRoleGrpInsert").on("click", function (e) {
    	var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $grid.jqGrid("getRowData", rowid);
		
    	DE.ui.open.popup(
            "view",
            ['userRoleRel'],
            {
                viewname: 'common/metacore/user/userAuthRel',
                userGrpId: rowData["USER_GRP_ID"],
                mode: 'R'
            },
            null
        );
    });
    

    $("button#btnRoleGrpDelete").on("click", function (e) {
    	DE.box.confirm("삭제 하시겠습니까?", function (b) {
            if (b === true) {
                var data = $("#jqGrid2").jqGrid("getGridParam", "data");
                var checkedData = [];
                $.each(data, function(index, value){
                    if (value["CHK"] === "true") {
                        checkedData.push(value["USER_GRP_ID"]);
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
                    "relUserGrpIds": checkedData
                };

                var saveComplete = function(rsp) {
                    if ("SUCCESS" === rsp["status"]) {
                        DE.box.alert(rsp["message"], function () {
                            gridDetailList(userGrpId);
                        });
                    }
                };
                DE.ajax.call({url:"system/grpR?oper=remove", data:postData}, saveComplete);
            }
        });
    });

    $("button#btnAuthGrpUpdate").on("click", function (e) {
    	var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $grid.jqGrid("getRowData", rowid);
		
		DE.ui.open.popup(
			"view",
			["authGrpView"],
			{
				viewname:'system/authGrpView',
				"userGrpId":rowData["USER_GRP_ID"],
				mode:'U'
			},
			null
		);
    });

    $("button#btnAuthGrpRemove").on("click", function (e) {
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
				"userGrpId":rowData["USER_GRP_ID"]
			};
			
			var opts = {
				url : "system/userGrp?oper=remove",
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
    
    var gridDetailList = function (id) {
        var postData = {
            "queryId": "system.user.getAuthUser",
            "userGrpId": id
        };

        DE.jqgrid.reload($("#jqGrid2"), postData);
    };

	$("#jqGrid").delegate("[name=radio_jqGrid]", "change", function(e){
    	$("#jqGrid").jqGrid("setSelection", $(this).closest("tr").prop("id"));
    });

    var init = function () {
        $("button#btnSearch").trigger("click");
    };
    init();
    //$(window).trigger("resize");
    
    $(window).resize(function () {
	    var outerwidth = $('.box-body').width();
        $('#jqGrid, #jqGrid2').setGridWidth(outerwidth); // setGridWidth method sets a new width to the grid dynamically
	});
});