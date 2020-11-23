$(document).ready(function() {
    var colModel = [
    	//{name:'CHK',index:'CHK', label:"선택", width:40, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
        {index:'USER_ID', name: 'USER_ID', label: '사용자ID', width: 100, align:'left', editable: true, editrules: {required: false}},
        {index:'USER_NM', name: 'USER_NM', label: '사용자명', width: 100, align:'left', editable: true, editrules: {required: false}},
        {index:'BUSEO',  name: 'BUSEO', label: '부서', width: 130, align:'left', editable: true, editrules: {required: false}},
        {index:'AD_ID',  name: 'AD_ID', label: 'AD ID', width: 130, align:'left', editable: true, editrules: {required: false}},
        {index:'EMAIL_ADDR', name: 'EMAIL_ADDR', label: '이메일주소', width: 150, align:'left', editable: true, editrules: {required: false}},
        {index:'HP_NO', name: 'HP_NO', label: '휴대폰', width: 110, align:'left', editable: true, editrules: {required: false}},
        {index:'ACCOUNT_NON_EXPIRED', name: 'ACCOUNT_NON_EXPIRED', label: '계정비밀번호사용기간만료여부', width: 100, align:'left', editable: true, editrules: {required: false},  hidden:true},
		{index:'ACCOUNT_NON_LOCKED', name: 'ACCOUNT_NON_LOCKED', label: '계정비밀번호잠김여부', width: 100, align:'left', editable: true, editrules: {required: false},  hidden:true},
		{index:'CREDENTIALS_NON_EXPIRED', name: 'CREDENTIALS_NON_EXPIRED', label: '계정인증만료여부', width: 100, align:'left', editable: true, editrules: {required: false},  hidden:true},
		{index:'ACCOUNT_ENABLED', name: 'ACCOUNT_ENABLED', label: '계정사용승인여부', width: 100, align:'left', editable: true, editrules: {required: false},  hidden:true},
		{index:'LOGIN_FAIL_CNT', name: 'LOGIN_FAIL_CNT', label: '로그인실패건수', width: 100, align:'left', editable: true, editrules: {required: false}},
		{index:'PASSWORD_LAST_CHG_DT', name: 'PASSWORD_LAST_CHG_DT', label: '비밀번호최종변경일시', width: 130, align:'left', editable: true, editrules: {required: false},  hidden:true},
		{index:'LOGIN_LAST_DT', name: 'LOGIN_LAST_DT', label: '최근로그인일시', width: 130, align:'left', editable: true, editrules: {required: false}},
        {index:'BUSE_CODE', name:'BUSE_CODE', label: '부서 코드',  hidden:true},
        {index:'DEPT', name:'DEPT', label: 'DEPT',  hidden:true}
    ];

	var opts = {
			"colModel":colModel, 
			pager:"#jqGridPager",
			editurl: 'obj/process',
			toppager: false,
			multiselect: false,
			loadonce: true,
			isPaging: false,
			scroll:1,
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
				search:false,
				view:false,
				cloneToTop:false,
			}
		};
		
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);

    $("button#btnSearch").on("click", function(e) {
        var postData = {
            "queryId":"system.user.getUser",
            "searchKey":$("#searchKey").val(),
            "searchValue":$("#searchValue").val()
        };
        DE.jqgrid.reload($("#jqGrid"), postData);
    });

    $("input[id=searchValue]").on("keypress", function (e) {
        if (e.which === 13) {
            var postData = {
                "queryId": "system.user.getUser",
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
        $(".ui-jqgrid-bdiv").css("overflow-x","hidden")
    };
    
    init();
    
    $(window).resize(function () {
	    var outerwidth = $('.box-body').width();
        $('#jqGrid').setGridWidth(outerwidth); // setGridWidth method sets a new width to the grid dynamically        
	});
});