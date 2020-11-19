$(document).ready(function() {
    var colModel = [
    	{name:'STD_DT',index:'STD_DT', label:"선택", width:40, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
        {index:'STD_HOUR', name: 'STD_HOUR', label: '사용자ID', width: 100, align:'left', editable: true, editrules: {required: false}},
        {index:'STD_MINUTE', name: 'STD_MINUTE', label: '사용자명', width: 100, align:'left', editable: true, editrules: {required: false}},
        {index:'GUBUN',  name: 'GUBUN', label: '조직명', width: 130, align:'left', editable: true, editrules: {required: false}},
        {index:'REPORT_ID', name: 'REPORT_ID', label: '이메일주소', width: 120, align:'left', editable: true, editrules: {required: false}},
        {index:'REPORT_NM', name: 'REPORT_NM', label: '전화번호', width: 110, align:'left', editable: true, editrules: {required: false}},
        {index:'USER_ID', name: 'USER_ID', label: '휴대폰', width: 110, align:'left', editable: true, editrules: {required: false}},
        {index:'USER_NM', name: 'USER_NM', label: '계정비밀번호사용기간만료여부', width: 100, align:'left', editable: true, editrules: {required: false}}
		
    ];

	var opts = {
			"colModel":colModel, 
			pager:"#jqGridPager",
			editurl: 'obj/process',
			multiselect: false,
			loadonce: false,
			isPaging: false,
			scroll:true,
			autowidth:true,
			height:680,
			shrinkToFit: true,
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
				search:true,
				view:false,
				cloneToTop:false,
			}
		};
		
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);

    $("button#btnSearch").on("click", function(e) {
        var postData = {
            "queryId":"mstr.latest.reportList",
            "searchKey":$("#searchKey").val(),
            "searchValue":$("#searchValue").val()
        };
        DE.jqgrid.reload($("#jqGrid"), postData);
    });

    $("input[id=searchValue]").on("keypress", function (e) {
        if (e.which === 13) {
            var postData = {
                "queryId": "mstr.latest.reportList",
                "searchKey": $("#searchKey").val(),
                "searchValue": $("#searchValue").val()
            };
            DE.jqgrid.reload($("#jqGrid"), postData);
        }
    });
    
    $("button#btnInsert").on("click", function(e) {
		var appId =$("input[name=appId]:checked").val();
		DE.ui.open.popup(
			"view",
			["userView"],
			{
				viewname:'system/userView',
				mode:'C',
				appId:$("#systemApp").val()
			},
		    "width=1000, height=800, toolbar=non, menubar=no"
		);
	});
	
	$("button#btnUpdate").on("click", function(e) {
		debugger;
		var appId =$("input[name=appId]:checked").val();
		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $grid.jqGrid("getRowData", rowid);

		DE.ui.open.popup(
			"view",
			["userView"],
			{
				viewname:'system/userView',
				mode:'U',
				appId:$("#systemApp").val(),
				userId:rowData["USER_ID"]
			},
		    "width=1000, height=800, toolbar=non, menubar=no"
		);
	});
	
	$("button#btnRemove").on("click", function(e) {	
		debugger;
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
				"userId":rowData["USER_ID"]
			};
			
			
			var opts = {
				url : "system/user?oper=remove",
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

	//비밀번호 초기화
	$("button#btnInitpwd").on("click", function(e) {	
		debugger;
		var callback = {
				success : function(response) {
					DE.box.alert(response["message"], function(){
						DE.jqgrid.reload($grid);
					});
				},
				error : function(response) {
					DE.box.alert(response["responseJSON"]["message"]);
					DE.jqgrid.reload($grid);
				}
			};
		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		var initPwdAction = function() {
			var rowid = $selRadio.closest("tr").prop("id");
			var rowData = $grid.jqGrid("getRowData", rowid);

			var formData = {
				"userId":rowData["USER_ID"]
			};
			
			
			var opts_pwd = {
				url : "system/user?oper=pwdInit",
				data : $.param(formData)
			};	
			
			DE.ajax.formSubmit(opts_pwd, callback.success, callback.error);
			
		};
		
		DE.box.confirm(DE.i18n.prop("common.message.initpwd.confirm"), function (b) {
			
        	if (b === true) {
        		initPwdAction();
        	}
        });
		
	});

    var init = function() {
        $("button#btnSearch").trigger("click");
        $(window).trigger("resize");
    };
    
    init();
});