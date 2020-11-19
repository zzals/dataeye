	$(document).ready(function() {
	var parentObj = opener || parent;
	var reqParam = $("input#reqParam").data();
    
    var colModel = [
		{name:'USER_NM',index:'USER_NM', label:'성명', width:100, align:'left'},
		{name:'USER_ID',index:'USER_ID', label:'ID', width:100, align:'left', classes: 'pointer', formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $(this).jqGrid("getRowData", rowid);
	    			parentObj.$("[id="+reqParam.targetObjectName+"]").attr("USE_DATA", rowData.USER_ID);
	                parentObj.$("[id="+reqParam.targetObjectName+"]").val(rowData.USER_NM);
	                self.close();
	    		}
	    	}
      	},
		{name:'EX_DEPT_NAME',index:'EX_DEPT_NAME', label:'부서', width:180, align:'left'},
		{name:'EMAIL_ADDR',index:'EMAIL_ADDR', label:'이메일', width:150, align:'left'},
		{name:'HP_NO',index:'HP_NO', label:'휴대폰', width:150, align:'left'}
   	];

	var opts = {
		"colModel":colModel,
        pager:"#jqGridPager",
        isPaging:false,
		autowidth:true,
		shrinkToFit: false
		
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
			"queryId":"bdp_custom.getUser",
			"searchKey":$("#searchKey").val(),
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