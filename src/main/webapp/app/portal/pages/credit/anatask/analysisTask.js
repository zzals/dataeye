
var ANATASK_OBJ_TYPE_ID="CR0202L";
    
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
        						"APRV_MODE":"APRV_REQ",
        						"APRV_ID":rowData['APRV_ID'],
        						mode:'U'
        					},
        					"width=1000, height=700, toolbar=no, menubar=no, location=no, resizable=no"
        				);
	    			
	    			
	    		}
	    	}
	    },
	    { index:'ASKER', name: 'ASKER', label: '요청자', width: 100, align:'left', editable: false},
	    { index:'ANATASK_KIND', name: 'ANATASK_KIND', label: '분석유형', width: 100, align:'center', editable: false},
      	{ index:'CRET_DT', name: 'CRET_DT', label: '요청일', width: 100, align:'left', editable: false},
      	{ index:'LAST_STUS_CD', name: 'LAST_STUS_CD', label: '진행상태', width: 100, align:'center', editable: false},
      	{ index:'ANATASK_OBJ_ID', name: 'ANATASK_OBJ_ID', label: 'ANATASK_OBJ_ID', width: 100, align:'center', editable: false, hidden:true},
      	{ index:'APRV_ID', name: 'APRV_ID', label: 'APRV_ID', width: 100, align:'center', editable: false, hidden:true}
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
	
	//파일 popup
	$("button#btnSave").on("click", function(e) {
		
      	
		DE.ui.open.popup(
			"view",
			["analysisTaskSavePopup"],
			{
				viewname:'portal/credit/anatask/analysisTaskSavePopup',
				ANATASK_OBJ_TYPE_ID:ANATASK_OBJ_TYPE_ID,
				"APRV_MODE":"APRV_REQ",
				mode:'C'
			},
			"width=1000, height=700, toolbar=no, menubar=no, location=no, resizable=0"
		);
	});
	
	
	
	var init = function() {
				
		gridReload();
		
	};
	init();
	
	$(this).off("autoResize").on( "autoResize", function( event ) {
	    setTimeout(function () {
	    	var heightMargin = $(".content-body .box-body").offset().top - $(".content-header").offset().top,
	    		bottomMargin = 20, // bottom margin 10
	    		gridHeadHeight = 60; // grid header height 30 + grid footer height 30
	    	
	    		
	    	$("#jqGrid").setGridWidth($(".content-body .gridArea").width(),opts["autowidth"]);
	    	$("#jqGrid").setGridHeight($(".content").height() - (heightMargin + bottomMargin + gridHeadHeight));
	      	if(opts["autowidth"]==true){
		    	$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
		    }
    	}, 500);
	}).trigger("autoResize");


function gridReload(){
	//상단 그리드를 조회한다.
	var postData = {
		"queryId":"credit_anatask.getAnaTaskList",
		"ANATASK_OBJ_TYPE_ID":ANATASK_OBJ_TYPE_ID
	};
	DE.jqgrid.reload($("#jqGrid"), postData);
}
