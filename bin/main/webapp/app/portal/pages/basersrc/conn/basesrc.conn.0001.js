$(document).ready(function() {
	var colModel = [
	    { index:'OBJ_NM', name: 'OBJ_NM', label: '접속명', width: 150, align:'left', editable: true, editrules: {required: true, readonly: "readonly"},
		    formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			$(this).jqGrid("viewGridRow", rowid, navOpts.viewOptions);
	    		}
	    	}
	    },      		    
      	{ index:'ADM_OBJ_ID', name: 'ADM_OBJ_ID', label: '관리접속ID', width: 100, align:'left', editable: true},
      	{ index:'DBTYPE', name: 'DBTYPE', label: 'DB유형', width: 100, align:'center', editable: true, edittype:'textarea'},
      	{ index:'APP_CONNTYPE', name: 'APP_CONNTYPE', label: '접속유형', width: 100, align:'center', editable: true, edittype:'textarea'},
      	{ index:'SVR_NM', name: 'SVR_NM', label: '서버명', width: 150, align:'left', editable: true, edittype:'textarea'},
      	{ index:'SVR_IP', name: 'SVR_IP', label: '서버IP', width: 150, align:'left', editable: true, edittype:'textarea'},
      	{ index:'SVR_LOC', name: 'SVR_LOC', label: '서버위치', width: 100, align:'left', editable: true, edittype:'textarea'}
    ];

	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		editurl: 'atr/process',
		toppager:false,
		loadonce: false,
		scroll:false
	};
	
	var navOpts = {
		navOptions:{
			cloneToTop:true,
		},
		editOptions:{
			editCaption:"속성 수정",
	        width: 500,
	        beforeShowForm: function(form, oper) { 
	        	if (oper === "add") {
	        		
	        	} else if (oper === "edit") {
	        		$('#ATR_ID', form).prop('disabled', true); 
	        	}
	        }
		},
		addOptions:{
			addCaption:"속성 등록",
			editCaption:"속성 수정",
	        width: 500,
	        beforeShowForm: function(form, oper) { 
	        	if (oper === "add") {
	        		
	        	} else if (oper === "edit") {
	        		$('#ATR_ID', form).prop('disabled', true); 
	        	}
	        }
		},
		delOptions:{
			caption:"속성 삭제",
			width: 500,
			onclickSubmit:function(params, rowid){
				var rowData = $(this).jqGrid("getRowData", rowid)
				return {"APP_ID":rowData["APP_ID"]};
			}
		},
		searchOptions:{},
		viewOptions:{
			caption: "속성 상세",
			bClose: "닫기",
			beforeShowForm: function(form) {
				var val = $("#v_SQL_SBST span", form).html();
	            $("#v_SQL_SBST span", form).remove();
	            $("<textarea>").appendTo("#v_SQL_SBST", form).val(val);
	        },
	        afterclickPgButtons: function(whichbutton, form, rowid) {
	        	var rowData = $(this).jqGrid("getRowData", rowid);
				$("#v_SQL_SBST textarea", form).val(rowData["SQL_SBST"]);
	        }
		}
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"portal_basesrc.getConnInfo",
			"searchKey":$("#searchValue").attr("de-search-key"),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	$(window).resize(function () {
	    var outerwidth = $('.box-body').width();
        $('#jqGrid').setGridWidth(outerwidth); // setGridWidth method sets a new width to the grid dynamically
	});
	
	var init = function() {
		$(window).trigger("resize");
		$("button#btnSearch").trigger("click");
	};
	init();
});