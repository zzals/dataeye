$(document).ready(function() {
	var brGrpTypeId = "030199L";
	var colModel = [
	    { index:'LOG_DT', name: 'LOG_DT', label: '수집일시', width: 130, align:'center', fixed:true},
      	{ index:'LOG_TYPE_NM', name: 'LOG_TYPE_NM', label: '작업유형', width: 100, align:'center'},
      	{ index:'OBJ_TYPE_NM', name: 'OBJ_TYPE_NM', label: '오브젝트유형', width: 100, align:'center'},
      	{ index:'DB_NM', name: 'DB_NM', label: '데이터베이스', width: 100, align:'left'},
      	{ index:'OBJ_NM', name: 'OBJ_NM', label: '오브젝트 물리명', width: 150, align:'left'},      	      
      	{ index:'OBJ_ABBR_NM', name: 'OBJ_ABBR_NM', label: '오브젝트 논리명', width: 150, align:'left'},      	      
      	{ index:'DISP_PATH_OBJ_NM', name: 'DISP_PATH_OBJ_NM', label: '상위 오브젝트', width: 200, align:'left'},
      	{ index:'LOG_TEXT', name: 'LOG_TEXT', label: '이력', width: 400, align:'left',
      		formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			$("#logText").val(cellvalue);					
					$('#getLog-view').modal('show');
	    		}
	    	}	
      	},
      	{ index:'LOG_ID', name: 'LOG_ID', label: 'LOG_ID', hidden:true}
    ];

	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		autowidth:true,
		shrinkToFit:true,
		toppager: false,
		loadonce: true,
		scroll:-1,
		isPaging:false
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"));
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"tech.db.findCollectLog",
			"startDt":DE.util.dateTimePicker.getValue("#startDt"), 
			"endDt":DE.util.dateTimePicker.getValue("#endDt"), 
			"objType":$("#selObjType").val(), 
			"logType":$("#selLogType").val(), 
			"searchKey":$("#searchKey").val(),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	$(this).off("autoResize").on( "autoResize", function( event ) {
		setTimeout(function () {
	    	var heightMargin = 50;
	    	$("#jqGrid").setGridWidth($(".content-body .box-body").width());
	    	$("#jqGrid").setGridHeight($(".content-body").height() - ($(".content-body .box-body").offset().top-$(".content-body").offset().top)-heightMargin);
    	}, 500);
	}).trigger("autoResize");

	var init = function() {
		DE.util.dateTimePicker.linkedPicker($("#startDt"), $("#endDt"));
        $("#startDt").data("DateTimePicker").date(DE.util.dateFormat(new Date(), {addDays:-7}));
        $("#endDt").data("DateTimePicker").date(DE.util.dateFormat(new Date()));
        
        $("#selObjType").select2();		
		$("#selLogType").select2();
		
		$("button#btnSearch").trigger("click");
	};
	init();
});