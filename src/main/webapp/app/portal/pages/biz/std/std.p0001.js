$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
	
	var colModel = [
	    { index:'TERM_OBJ_NM', name: 'TERM_OBJ_NM', label: '표준용어명', width: 200, align:'left',
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);
	    			DE.ui.open.popup(
    					"view",
    					[rowData["TERM_OBJ_TYPE_ID"], rowData["TERM_OBJ_ID"]],
    					{
    						viewname:'common/metacore/objectInfoTab',
    						objTypeId:rowData["TERM_OBJ_TYPE_ID"],
    						objId:rowData["TERM_OBJ_ID"],
    						mode:'R'
    					},
    					null
    				);
	    		}
	    	}
      	},
      	{ index:'TERM_ENG_ABBR_NM', name: 'TERM_ENG_ABBR_NM', label: '영문약어명', width: 200, align:'left'},
      	{ index:'CRETR_NM', name: 'CRETR_NM', label: '등록자', width: 100, fixed: true, align:'center'},
        { index:'TERM_OBJ_TYPE_ID', name: 'TERM_OBJ_TYPE_ID', label: 'TERM_OBJ_TYPE_ID', hidden:true},
        { index:'TERM_OBJ_ID', name: 'TERM_OBJ_ID', label: 'TERM_OBJ_ID', hidden:true}
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
	
	var search = function() {
		var postData = {
			"queryId":"portal.biz.std.findDomainUsed",
			"dmnObjId":reqParam["dmnObjId"]
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	};
	
	$(this).off("autoResize").on( "autoResize", function( event ) {
	    setTimeout(function () {
	    	var heightMargin = 50;
	    		
	    	$("#jqGrid").setGridWidth($(".content-body .box-body").width(),opts["autowidth"]);
	    	$("#jqGrid").setGridHeight($(".content-body").height() - ($(".content-body .box-body").offset().top-$(".content-body").offset().top)-heightMargin);
	    	
	      	if(opts["autowidth"]==true){
			$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
		  }
	    	
    	}, 500);
	}).trigger("autoResize");

	var init = function() {
		search();
	};
	init();
});