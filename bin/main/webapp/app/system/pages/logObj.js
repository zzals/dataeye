$(document).ready(function() {
    var colModel = [
    	{index:'REQ_DT',    name: 'REQ_DT', label: '접속일시', width: 140, align:'left', editable: true, editrules: {required: false}},
    	{index:'APP_NM',    name: 'APP_NM', label: 'APP명', width: 120, align:'center', editable: true, editrules: {required: false}},
        {index:'USER_ID',   name: 'USER_ID', label: '사용자ID', width: 100, align:'left', editable: true, editrules: {required: false}},
        {index:'USER_NM',   name: 'USER_NM', label: '사용자명', width: 100, align:'center', editable: true, editrules: {required: false}},
        {index:'ORG_ID',    name: 'ORG_ID', label: '소속', width: 100, align:'center', editable: true, editrules: {required: false}},
        {index:'SESS_ID',   name: 'SESS_ID', label: '세션ID', width: 130, align:'center', editable: true, editrules: {required: false}},
        {index:'USER_IP',   name: 'USER_IP', label: '사용자IP', width: 100, align:'left', editable: true, editrules: {required: false}},
		{index:'SRV_IP',    name: 'SRV_IP', label: '서비스IP', width: 100, align:'left', editable: true, editrules: {required: false}},
		{index:'SRV_PORT',  name: 'SRV_PORT', label: '서비스포트', width: 100, align:'center', editable: true, editrules: {required: false}}, 
        {index:'OBJ_TYPE_ID', name: 'OBJ_TYPE_ID', label: '객체유형ID', width: 100, align:'center', editable: true, editrules: {required: false}},
        {index:'OBJ_TYPE_NM', name: 'OBJ_TYPE_NM', label: '객체유형명', width: 100, align:'center', editable: true, editrules: {required: false}},
        {index:'OBJ_ID',    name: 'OBJ_ID', label: '객체ID', width: 110, align:'center', editable: true, editrules: {required: false}},
        {index:'OBJ_NM',    name: 'OBJ_NM', label: '객체명', width: 110, align:'center', editable: true, editrules: {required: false}},
        {index:'REFE_INFO', name: 'REFE_INFO', label: '참조정보', width: 100, align:'center', editable: true, editrules: {required: false}},
		{index:'RSLT_CD',   name: 'RSLT_CD', label: '결과코드', width: 100, align:'center', editable: true, editrules: {required: false}}
    ];

	var opts = {
			"colModel":colModel, 
			pager:"#jqGridPager",
			editurl: 'obj/process',
			multiselect: false,
			loadonce: false,
			isPaging: true,
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
			"queryId":"system.log.getLogObj",
            "fromData": $("#fromData").val(),
			"appId":$("#systemApp").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});

    
	var init = function() {
		var rsp = DE.ajax.call({async:false, url:"system/logObj?oper=getLogObj", data:{}});
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