$(document).ready(function() {
	var parentObj = opener || parent;
	var reqParam = $("input#reqParam").data();
    
    var colModel = [
		{name:'ORG_NM',index:'ORG_NM', label:'부서명', width:100, align:'left', classes: 'pointer', formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $(this).jqGrid("getRowData", rowid);
	    			parentObj.$("[id="+reqParam.targetObjectName+"]").attr("USE_DATA", rowData.ORG_ID);
	                parentObj.$("[id="+reqParam.targetObjectName+"]").val(rowData.ORG_NM);
	                self.close();
	    		}
	    	}
      	},
		{name:'ORG_PATH_NM',index:'ORG_PATH_NM', label:'부서경로', width:300, align:'left'},
		{name:'ORG_ID',index:'ORG_ID', label:'ORG_ID', hidden:true}
   	];

	var opts = {
		"colModel":colModel,
        pager:"#jqGridPager",
        isPaging:false,
		autowidth:true,
		shrinkToFit: true
	};
	
	var navOpts = {
		navOptions:{
			cloneToTop:false,
			add:false,
			del:false,
			edit:false,
			refresh:false,
			search:false,
			view:false
		}
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"bdp_custom.getOrgTree",
			"searchKey":$("#searchValue").attr("de-search-key"),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	$("#searchValue").keydown(function (key) {
		 
        if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
        	$("button#btnSearch").click();
        }
 
    });
     
	
	var init = function() {
		$("button#btnSearch").trigger("click");
		$(window).trigger("resize");
	};
	init();
});