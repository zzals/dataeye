var ANATASK_OBJ_TYPE_ID="CR0202L";

$(document).ready(function() {
		     
    
	var colModel = [
	    { index:'ANATASK_NM', name: 'ANATASK_NM', label: '분석과제명', width: 100, align:'left', editable: false,
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);
	    			
	    			var view_name;	    			
    				view_name='portal/credit/anatask/analysisTaskSavePopup';
    				DE.ui.open.popup(
        					"view",
        					["nonIdentFileViewPopup"],
        					{
        						viewname:view_name,
        						"ANATASK_OBJ_TYPE_ID":ANATASK_OBJ_TYPE_ID,
        						"ANATASK_OBJ_ID":rowData['ANATASK_OBJ_ID'],        						
        						"APRV_ID":rowData['APRV_ID'],
        						"APRV_DETL_ID":rowData['APRV_DETL_ID'],
        						"APRV_MODE":"APRV_DO",
        						mode:'U'
        					},
        					"width=900, height=700, toolbar=no, menubar=no, location=no, resizeable=no"
        				);
	    			
	    			
	    		}
	    	}
	    },
	    { index:'ASKER', name: 'ASKER', label: '신청자', width: 100, align:'left', editable: false},
	    { index:'ANATASK_KIND', name: 'ANATASK_KIND', label: '분석유형', width: 100, align:'center', editable: false},
      	{ index:'CRET_DT', name: 'CRET_DT', label: '요청일', width: 100, align:'left', editable: false},
      	{ index:'LAST_STUS_CD', name: 'LAST_STUS_CD', label: '진행상태', width: 100, align:'center', editable: false},
      	{ index:'ANATASK_OBJ_ID', name: 'ANATASK_OBJ_ID', label: 'ANATASK_OBJ_ID', width: 100, align:'center', editable: false, hidden:true},
      	{ index:'APRV_ID', name: 'APRV_ID', label: 'APRV_ID', width: 100, align:'center', editable: false, hidden:true},
      	{ index:'APRV_DETL_ID', name: 'APRV_DETL_ID', label: 'APRV_DETL_ID', width: 100, align:'center', editable: false, hidden:true}
    ];
	
	
	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		autowidth:true,
		shrinkToFit:true,
		toppager: false,
		loadonce: true,
		scroll:-1,
		isPaging:false,
		sortname:"DATASET_NM",
		beforeSelectRow: function (rowid, e) {
        	var $radio = $('input[type=radio]', $(e.target).closest('tr'));
            $radio.prop('checked', 'checked');
            return true;
        }
	};
	
	var navOpts = {
		navOptions:{
			cloneToTop:false,
			view:false,
			add:false,
			edit:false,
			del:false,
			refresh:false,
			search:false
		}
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);
	
	$("button#btnSearch").on("click", function(e) {
		
	});
	
	
	
	
	var init = function() {
				
		gridReload();
		
	};
	init();
	
	$(window).resize(function () {
	    var outerwidth = $('.box-body').width();
        $('#jqGrid').setGridWidth(outerwidth); // setGridWidth method sets a new width to the grid dynamically        
	});
	$(window).trigger("resize");
});



function gridReload(){
	//상단 그리드를 조회한다.
	var postData = {
		"queryId":"credit_anatask.getAnaTaskAprvList",
		"ANATASK_OBJ_TYPE_ID":ANATASK_OBJ_TYPE_ID,
		"APRV_USER_ID":"admin"
	};
	DE.jqgrid.reload($("#jqGrid"), postData);
}
