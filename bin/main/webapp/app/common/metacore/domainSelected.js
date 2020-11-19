$(document).ready(function() {
	var parentObj = opener || parent;
	var reqParam = $("input#reqParam").data();
    $(".popup_title").append(reqParam["displayName"]);
    
    var colModel = [
		{name:'DMN_GRP_OBJ_NM',index:'DMN_GRP_OBJ_NM', label:'표준도메인그룹명', width:250, align:'left'},
		{name:'DMN_OBJ_NM',index:'DMN_OBJ_NM', label:'표준도메인명', width:250, align:'left', classes: 'pointer', formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $(this).jqGrid("getRowData", rowid);
	    			parentObj.$("[id="+reqParam.targetObjectName+"]").attr("USE_DATA", rowData.DMN_OBJ_ID);
	                parentObj.$("[id="+reqParam.targetObjectName+"]").val(rowData.DMN_OBJ_NM);
	                self.close();
	    		}
	    	}
      	},
		{name:'BAS_DISP_DATA_TYPE',index:'BAS_DISP_DATA_TYPE', label:'데이터타입(기본)', width:250, align:'left'},
		{name:'ORACLE_DATA_TYPE',index:'ORACLE_DATA_TYPE', label:'데이터타입(ORACLE)', width:250, align:'left', hidden:true},
		{name:'PDA_DATA_TYPE',index:'PDA_DATA_TYPE', label:'데이터타입(PDA)', width:250, align:'left', hidden:true},
		{name:'IMPALA_DATA_TYPE',index:'IMPALA_DATA_TYPE', label:'데이터타입(IMPALA)', width:250, align:'left'},
		{name:'DMN_GRP_OBJ_TYPE_ID',index:'DMN_GRP_OBJ_TYPE_ID', label:'DMN_GRP_OBJ_TYPE_ID', hidden:true},
		{name:'DMN_GRP_OBJ_ID',index:'DMN_GRP_OBJ_ID', label:'DMN_GRP_OBJ_ID', hidden:true},
		{name:'DMN_OBJ_TYPE_ID',index:'DMN_OBJ_TYPE_ID', label:'DMN_OBJ_TYPE_ID', hidden:true},
		{name:'DMN_OBJ_ID',index:'DMN_OBJ_ID', label:'DMN_OBJ_ID', hidden:true}
   	];

	var opts = {
		"colModel":colModel,
        pager:"#jqGridPager",
        isPaging:false,
		loadonce: true,
		height:310,
		scroll:-1,
		autowidth:true,
		shrinkToFit: false
	};
	
	var navOpts = {
		navOptions:{
			cloneToTop:false,
			add:false,
			del:false,
			edit:false,
			refresh:true,
			search:false,
			view:false
		}
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"portal.biz.std.findStdDmn",
			"searchKey":$("#searchKey").val(),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});


	
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
		$("button#btnSearch").trigger("click");
	};
	init();
});