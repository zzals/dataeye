$(document).ready(function() {
	var colModel = [
	    { index:'OBJ_NM', name: 'OBJ_NM', label: 'APP시스템명', width: 150, align:'left', editable: true, editrules: {required: true, readonly: "readonly"},
		    formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			$(this).jqGrid("viewGridRow", rowid, navOpts.viewOptions);
	    		}
	    	}
	    },      		    
      	{ index:'OBJ_DESC', name: 'OBJ_DESC', label: '설명', width: 250, align:'left', editable: true},
      	{ index:'PATH_OBJ_NM', name: 'PATH_OBJ_NM', label: 'APP그룹', width: 100, align:'left', editable: true, edittype:'textarea'},
      	{ index:'SYSGRP', name: 'SYSGRP', label: '시스템그룹', width: 100, align:'center', editable: true, edittype:'textarea'},
      	{ index:'IMPLMETHD', name: 'IMPLMETHD', label: '구현방식', width: 80, align:'center', editable: true, edittype:'textarea'},
      	{ index:'BDP_DATA_ENGNR_UP', name: 'BDP_DATA_ENGNR_UP', label: 'Data Enginner(U+)', width: 80, align:'center', editable: true, edittype:'textarea'},
      	{ index:'BDP_CHRGR_CNS', name: 'BDP_CHRGR_CNS', label: 'BDP담당자(CNS)', width: 80, align:'left', editable: true, edittype:'textarea'},
      	{ index:'OPRNCHRAG', name: 'OPRNCHRAG', label: '운영담당', width: 80, align:'left', editable: true, edittype:'textarea'},
      	{ index:'OPRNTEAM', name: 'OPRNTEAM', label: '운영팀', width: 80, align:'left', editable: true, edittype:'textarea'},
      	{ index:'OBJ_ID', name: 'OBJ_ID', label: 'OBJ_ID', width: 80, align:'left', editable: true, edittype:'textarea',hidden:true}
    ];

	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		editurl: 'atr/process',
		toppager:false,
		autowidth: true,
		loadonce: true,		
		subGrid:true,
		subGridOptions: 
		{ 
		
			"plusicon" : "glyphicon glyphicon-plus",		
            "minusicon" :"glyphicon glyphicon-minus",
             "openicon" : "glyphicon glyphicon-folder-open",
             "reloadOnExpand" : false,
             "selectOnExpand" : true
             
		},
          subGridRowExpanded: function(subgrid_id, rowId) {
	      fn_subgrid("jqGrid",subgrid_id,rowId)
		},
		scroll:false
	};
	
	var fn_subgrid = function(grid_id,subgrid_id,row_id) {
		var rowData = jQuery("#" + grid_id).getRowData(row_id);
		var objId = rowData.OBJ_ID;
		
		var subgrid_table_id = subgrid_id + "_t";
		$("#" + subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table>");		
		
		jQuery("#" + subgrid_table_id).jqGrid({
			datatype : "local",
			pager:false,
			toppager:false,
			autowidth:true,
			height: '100%',
			colModel: [
							{name : "OBJ_NM",index : "OBJ_NM" ,label: "접속명", width : 150 , sortable:false},
							{name : "ADM_OBJ_ID", index : "ADM_OBJ_ID" ,label: "관리접속ID", width : 150 ,sortable:false, align:"center"},
							{name : "DBTYPE", index : "DBTYPE" ,label: "DB유형", width : 150 ,sortable:false, align:"center"},
							{name : "APP_CONNTYPE", index : "APP_CONNTYPE" ,label: "접속유형", width : 150 ,sortable:false, align:"center"},
							{name : "SVR_NM", index : "SVR_NM" ,label: "서버명", width : 150 ,sortable:false, align:"center"},
							{name : "SVR_IP", index : "SVR_IP" ,label: "서버IP", width : 150 ,sortable:false, align:"center"},
							{name : "SVR_LOC", index : "SVR_LOC" ,label: "서버위치", width : 150 ,sortable:false, align:"center"}					
						],
			
			regional : "kr"
		});
		
		var postData = {
				"queryId":"portal_basesrc.getAppSystemSub",
				"objId":objId			
			};
			DE.jqgrid.reload($("#" + subgrid_table_id), postData);
	};
	
	var navOpts = {
		navOptions:{
			cloneToTop:false,
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
			onclickSubmit:function(params, rowid) {
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
			"queryId":"portal_basesrc.getAppSystem",
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