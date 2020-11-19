$(document).ready(function() {
    var colModel = [
    	{index:'REQ_DT',    name: 'REQ_DT',    label: '로그인요청일시', width: 140, align:'center', editable: true, editrules: {required: false}},
        {index:'APP_ID',    name: 'APP_ID',    label: 'APP_ID', width: 90, align:'center', editable: true, editrules: {required: false}},
        {index:'APP_NM',    name: 'APP_NM',    label: 'APP명', width: 100, align:'center', editable: true, editrules: {required: false}},
        {index:'USER_ID',   name: 'USER_ID',   label: '사용자ID', width: 100, align:'left', editable: true, editrules: {required: false}},
        {index:'USER_NM',   name: 'USER_NM',   label: '사용자명', width: 100, align:'center', editable: true, editrules: {required: false}},
        {index:'ORG_ID',    name: 'ORG_ID',    label: '소속', width: 70, align:'center', editable: true, editrules: {required: false}},
        {index:'SESS_ID',   name: 'SESS_ID',   label: '세션ID', width: 130, align:'center', editable: true, editrules: {required: false}},
		{index:'SRV_IP',    name: 'SRV_IP',    label: '서비스IP', width: 130, align:'center', editable: true, editrules: {required: false}},
		{index:'SRV_PORT',  name: 'SRV_PORT',  label: '서비스포트', width: 70, align:'center', editable: true, editrules: {required: false}}, 
        {index:'REFE_INFO', name: 'REFE_INFO', label: '방문경로정보', width: 340, align:'left', editable: true, editrules: {required: false}},
        {index:'RSLT_CD',   name: 'RSLT_CD',   label: '결과코드', width: 110, align:'center', editable: true, editrules: {required: false}},
        {index:'RSLT_DESC', name: 'RSLT_DESC', label: '결과상세', width: 100, align:'left', editable: true, editrules: {required: false}},
		{index:'CONN_TYPE', name: 'CONN_TYPE', label: '접속유형', width: 100, align:'center', editable: true, editrules: {required: false}},
		{index:'LOGIN_TYPE',name: 'LOGIN_TYPE',label: '로그인유형', width: 100, align:'center', editable: true, editrules: {required: false}}
    ];

	var opts = {
			"colModel":colModel, 
			pager:"#jqGridPager",
			editurl: 'obj/process',
			multiselect: false,
			loadonce: true,
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
				search:false,
				view:false,
				cloneToTop:false,
			}
		};
		
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);

 
	$("#systemApp").on("change", function(e) {
		$("#btnSearch").trigger("click");
	});
	
	$("#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"system.log.getLogUser",
            "fromData": $("#fromData").val(),
			"appId":$("#systemApp").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});

    
	var init = function() {
		var rsp = DE.ajax.call({async:false, url:"system/logUser?oper=getLogUser", data:{}});
		var data = rsp["data"];
		
		$("#btnSearch").trigger("click");
		$(".ui-jqgrid-bdiv").css("overflow-x","hidden");
	};
	init();
	
	$(window).resize(function () {
	    var outerwidth = $('.box-body').width();
        $('#jqGrid').setGridWidth(outerwidth); // setGridWidth method sets a new width to the grid dynamically
	});
});