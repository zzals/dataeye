$(document).ready(function() {
	var colModel = [
      	{ index:'APRV_REQ_DT', name: 'APRV_REQ_DT', label: '요청일시', width: 100, align:'center', formatter: 'dateTimeFormat'},
	    { index:'LAST_STUS_CD_NM', name: 'LAST_STUS_CD_NM', label: '진행상태', width: 80, align:'center'},
      	{ index:'APRV_REQ_DESC', name: 'APRV_REQ_DESC', label: '요청내용', width: 300, align:'left'},
      	{ index:'REQ_CNT', name: 'REQ_CNT', label: '요청건수', width: 80, align:'right'},
      	{ index:'APRV_DESC', name: 'APRV_DESC', label: '처리내용', width: 300, align:'left'},
      	{ index:'APRV_REQR_NM', name: 'APRV_REQR_NM', label: '요청자', width: 80, align:'center'},
      	{ index:'APRV_DT', name: 'APRV_DT', label: '처리일시', width: 100, align:'center', formatter: 'dateTimeFormat'},
      	{ index:'REAL_APRV_USER_NM', name: 'APRV_REQR_NM', label: '처리자', width: 80, align:'center'},
      	{ index:'OBJ_TYPE_NM', name: 'OBJ_TYPE_NM', label: '요청유형', width: 80, align:'left'},
      	{ index:'ACTION', name: 'ACTION', label: 'ACTION', width: 80, align:'center', title:false,
	    	formatter: function(cellvalue, options, rowObject) {
	    		var btnEl = "<input type=\"button\" id=\"btnGridView\" value=\"보기\"/>";
	    		return btnEl; 
	    	}
      	},
      	{ index:'LAST_STUS_CD', name: 'LAST_STUS_CD', label: 'LAST_STUS_CD', hidden:true},
      	{ index:'APRV_ID', name: 'APRV_ID', label: 'APRV_ID', hidden:true},
      	{ index:'APRV_DETL_ID', name: 'APRV_DETL_ID', label: 'APRV_DETL_ID', hidden:true},
        { index:'OBJ_TYPE_ID', name: 'OBJ_TYPE_ID', label: 'OBJ_TYPE_ID', hidden:true}
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
			"queryId":"portal.biz.std.todoList",
			"searchKey":$("#searchKey").val(),
			"searchValue":$("#searchValue").val(),
			"lastStusCd":$("#selLastStusCd").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	$("#jqGrid").on("click", "input:button", function(e) {
		if ($(e.currentTarget).prop("id") === "btnGridView") {
			var rowData = $("#jqGrid").jqGrid("getRowData", $(e.currentTarget).closest("tr").prop("id"));
			var aprvDo = false;
			if (rowData["LAST_STUS_CD"] === "10") {
				aprvDo = true;
			}
			
			DE.ui.open.popup(
				"view",
				["stdWordAprvReq"],
				{
					"viewname":"portal/biz/std/req."+rowData["OBJ_TYPE_ID"],
					"aprvId": rowData["APRV_ID"],
					"aprvDetlId": rowData["APRV_DETL_ID"],
					"mode":"RO",
					"aprvDo": aprvDo
				},
				null
			);
		}
	});
	
	$(this).off("autoResize").on( "autoResize", function( event ) {
	    setTimeout(function () {
	    	var heightMargin = $(".content-body .box-body").offset().top - $(".content-header").offset().top,
    		bottomMargin = 10, // bottom margin 10
    		gridHeadHeight = 60; // grid header height 30 + grid footer height 30

	    	$("#jqGrid").setGridWidth($(".content-body .box-body").width(),opts["autowidth"]);
	    	$("#jqGrid").setGridHeight($(".content").height() - (heightMargin + bottomMargin + gridHeadHeight));
	      	if(opts["autowidth"]==true){
		    	$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
		    }
	    	
    	}, 500);
	}).trigger("autoResize");

	var init = function() {
		$("#selLastStusCd").select2();
		DE.util.selectBox($("#selLastStusCd"), [{"cdId":"00", "cdNm":"임시저장"},{"cdId":"10", "cdNm":"요청"},{"cdId":"40", "cdNm":"승인"},{"cdId":"42", "cdNm":"반려"}], {"value":"cdId", "text":"cdNm"});
		
		$("button#btnSearch").trigger("click");
	};
	init();
});