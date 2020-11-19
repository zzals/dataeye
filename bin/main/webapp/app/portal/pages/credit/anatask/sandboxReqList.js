
var SB_OBJ_TYPE_ID="CR0203L";
var ANATASK_OBJ_TYPE_ID="CR0202L";	 
var REQ_TYPE_CD="SB_REQ";
$(document).ready(function() {
	
	
	var colModel = [
	    { index:'ANATASK_NM', name: 'ANATASK_NM', label: '분석과제', width: 100, align:'left', editable: false,
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);	    			
	    			var view_name;	    			
    				view_name='portal/credit/anatask/sandboxReqPopup';
    				DE.ui.open.popup(
    					"view",
    					["sandboxReqPopupView"],
    					{
    						viewname:view_name,
    						SB_OBJ_TYPE_ID:SB_OBJ_TYPE_ID,    						
    						ANATASK_OBJ_TYPE_ID:ANATASK_OBJ_TYPE_ID,    						
    						APRV_ID:rowData['APRV_ID'],
    						APRV_MODE:"SB_REQ_REQ",
    						REQ_TYPE_CD:REQ_TYPE_CD,
    						ANATASK_OBJ_ID:rowData['ANATASK_OBJ_ID'],
    						mode:'U'
    					},
    					"left=1, top=1, width=950, height=1000,toolbar=no, menubar=no, location=no, resizeable=no"
    				);
	    			
	    		}
	    	}
	    },
	    { index:'ASKER', name: 'ASKER', label: '신청자', width: 100, align:'left', editable: false},
	    { index:'START_DT', name: 'START_DT', label: '시작일', width: 100, align:'center', editable: false},
      	{ index:'END_DT', name: 'END_DT', label: '종료일', width: 100, align:'left', editable: false},      	
      	{ index:'STATUS_NM', name: 'STATUS_NM', label: '상태', width: 100, align:'center', editable: false},
      	{ index:'APRV_REQR_CD_NM', name: 'APRV_REQR_CD_NM', label: '요청구분', width: 100, align:'center', editable: false},      	
      	{ index:'TOOL_OBJ_ID', name: 'TOOL_OBJ_ID', label: '분석도구보기', width: 100, align:'center', formatter:getBtn},
      	{ index:'APRV_ID', name: 'APRV_ID', label: 'APRV_ID', width: 100, align:'center', editable: false, hidden:true},
      	{ index:'ANATASK_OBJ_ID', name: 'ANATASK_OBJ_ID', label: 'ANATASK_OBJ_ID', width: 100, align:'center', editable: false, hidden:true},
      	{ index:'SB_OBJ_ID', name: 'SB_OBJ_ID', label: 'SB_OBJ_ID', width: 100, align:'center', editable: false, hidden:true},
      	{ index:'STATUS_CD', name: 'STATUS_CD', label: 'STATUS_CD', width: 100, align:'center', editable: false, hidden:true},
      	{ index:'APRV_REQR_CD', name: 'APRV_REQR_CD', label: 'APRV_REQR_CD', width: 100, align:'center', editable: false, hidden:true}      	
      	
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
	$("button#btnFile").on("click", function(e) {
		
      	
		DE.ui.open.popup(
			"view",
			["sandboxReqPopup"],
			{
				viewname:'portal/credit/anatask/sandboxReqPopup',
				ANATASK_OBJ_TYPE_ID:ANATASK_OBJ_TYPE_ID,
				SB_OBJ_TYPE_ID:SB_OBJ_TYPE_ID,
				APRV_MODE:"SB_REQ_REQ",
				REQ_TYPE_CD:REQ_TYPE_CD,
				mode:'C'
			},
			"left=1, top=1, width=950, height=1000, toolbar=no, menubar=no, location=no, resizable=no"
		);
	});
	
	
	
	$("button#btnRemove").on("click", function(e) {
		
		
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
		"queryId":"credit_anatask.getSBReqList",
		"SB_OBJ_TYPE_ID":SB_OBJ_TYPE_ID,
		"ANATASK_OBJ_TYPE_ID":ANATASK_OBJ_TYPE_ID,
		"USER_ID":'admin', //차후에 session 정보를 얻어서 할당한다.
		"REQ_TYPE_CD":REQ_TYPE_CD
	};
	DE.jqgrid.reload($("#jqGrid"), postData);
}


var getBtn = function(val,opt, row) {
	var str="";
	var aprv_reqr_cd=row['APRV_REQR_CD'];
	debugger;
	if(row['TOOL_OBJ_ID'] && aprv_reqr_cd!="30") {
		str = str + "<button onclick=viewTool('"+row['TOOL_OBJ_ID']+"','"+row['APRV_ID']+"','"+row['ANATASK_OBJ_ID']+"')>도구보기</button>";		
		//str = str + "<button onclick=viewTool('"+row['TOOL_OBJ_ID']+"','"+row['APRV_ID']+"')>도구보기</button>";
	}

    return str;
};


var viewTool=function(obj_id, aprv_id, anatask_obj_id){	
	debugger;
	DE.ui.open.popup(
			"view",
			["sandboxReqPopup"],
			{
				viewname:'portal/credit/anatask/sandboxTool',				
				SB_OBJ_TYPE_ID:SB_OBJ_TYPE_ID,
				ANATASK_OBJ_TYPE_ID:ANATASK_OBJ_TYPE_ID,
				SB_OBJ_ID:obj_id,
				APRV_ID:aprv_id,
				ANATASK_OBJ_ID:anatask_obj_id				
			},
			"left=1, top=1, width=900, height=550, toolbar=no, menubar=no, location=no, resizable=no"
		);
}
