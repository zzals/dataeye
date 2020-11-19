$(document).ready(function() {
	var colModel = [
	    {name:'CHK',index:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
	    { index:'HIER_LEV_NO', name: 'HIER_LEV_NO', label: '레벨', width: 80, align:'center', fixed:true},
	    { index:'OBJ_TYPE_ID', name: 'OBJ_TYPE_ID', label: '객체유형ID', width: 120, align:'left', fixed:true},
		{ index:'OBJ_TYPE_NM', name: 'OBJ_TYPE_NM', label: '객체유형명', width: 200, align:'left', 
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getLocalRow", rowid);
	    			DE.ui.open.popup(
    					"view",
    					["objUIView"],
    					{
    						viewname:'admin/objTypeView',
    						"objTypeId":rowData["OBJ_TYPE_ID"],
    						mode:'R'
    					},
    				    "width=1300, height=800, toolbar=non, menubar=no"
    				);
	    		}
	    	}
      	},
      	{ index:'OBJ_TYPE_DESC', name: 'OBJ_TYPE_DESC', label: '설명', width: 300, align:'left'},
		{ index:'DEL_YN', name: 'DEL_YN', label: '삭제여부', width: 80, align:'center', fixed:true},
		{ index:'SORT_NO', name: 'SORT_NO', label: '정렬순서', width: 80, align:'center', fixed:true},
      	{ index:'UP_OBJ_TYPE_NM', name: 'UP_OBJ_TYPE_NM', label: '상위객체유형', width: 100, align:'left'},
      	{ index:'LST_LEV_YN', name: 'LST_LEV_YN', label: 'LEAF 여부', width: 80, align:'center', fixed:true},
      	{ index:'STLM_YN', name: 'STLM_YN', label: '결제 여부', width: 80, align:'center', fixed:true},
      	{ index:'HST_YN', name: 'HST_YN', label: '이력 여부', width: 80, align:'center', fixed:true},
      	{ index:'TAG_YN', name: 'TAG_YN', label: '해쉬태그여부', width: 80, align:'center', fixed:true},
      	{ index:'CMMT_YN', name: 'CMMT_YN', label: '코멘트여부', width: 80, align:'center', fixed:true}
    ];

	var subOpts = {
		"colModel":colModel,
		toppager: false,
		shrinkToFit:true,
		isPaging:false
	};
	
    var showSubGrid = function(pRowid, pRowkey) {
    	var rowData = $("#jqGrid").jqGrid("getLocalRow", pRowkey);
        if (rowData["HIER_LEV_NO"] < 3) {
        	var subColModel = [
	       	    { index:'HIER_LEV_NO', name: 'HIER_LEV_NO', label: '레벨', width: 80, align:'center', fixed:true},
	       	    { index:'OBJ_TYPE_ID', name: 'OBJ_TYPE_ID', label: '객체유형ID', width: 120, align:'left', fixed:true},
	       		{ index:'OBJ_TYPE_NM', name: 'OBJ_TYPE_NM', label: '객체유형명', width: 200, align:'left'},
             	{ index:'OBJ_TYPE_DESC', name: 'OBJ_TYPE_DESC', label: '설명', width: 300, align:'left'},
	       		{ index:'DEL_YN', name: 'DEL_YN', label: '삭제여부', width: 80, align:'center', fixed:true},
	       		{ index:'SORT_NO', name: 'SORT_NO', label: '정렬순서', width: 80, align:'center', fixed:true},
             	{ index:'UP_OBJ_TYPE_NM', name: 'UP_OBJ_TYPE_NM', label: '상위객체유형', width: 100, align:'left'},
             	{ index:'LST_LEV_YN', name: 'LST_LEV_YN', label: 'LEAF 여부', width: 80, align:'center', fixed:true},
             	{ index:'STLM_YN', name: 'STLM_YN', label: '결제 여부', width: 80, align:'center', fixed:true},
             	{ index:'HST_YN', name: 'HST_YN', label: '이력 여부', width: 80, align:'center', fixed:true},
             	{ index:'TAG_YN', name: 'TAG_YN', label: '해쉬태그여부', width: 80, align:'center', fixed:true},
             	{ index:'CMMT_YN', name: 'CMMT_YN', label: '코멘트여부', width: 80, align:'center', fixed:true}
           ];
       	
           var childGridID = pRowid + "_table";
           var childGridPagerID = pRowid + "_pager";
           
           $('#' + pRowid).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');
           $("#" + childGridID).jqGrid({
        	   url: "jqgrid/list",
               datatype:"json",
               colModel: subColModel,
               loadonce: true,
               height: '100%',
			   scroll:0,
			   scrollOffset:0,
			   autowidth:true,
			   shrinkToFit: true,
			   rownumbers: false,
               postData:{
            	   "queryId":"objtype.getObjTypeM",
            	   "upObjTypeId":rowData["OBJ_TYPE_ID"]
               }/*,
               pager: "#" + childGridPagerID*/
           });
    	} else {
    		var subColModel = [
	       		{ index:'ATR_ID_SEQ', name: 'ATR_ID_SEQ', label: '속성ID순번', width: 80, align:'center', fixed:true},
	       		{ index:'ATR_ID', name: 'ATR_ID', label: '속성ID', width: 60, align:'center', fixed:true},
	       	    { index:'ATR_NM', name: 'ATR_NM', label: '속성명', width: 120, align:'left', fixed:true},
	       		{ index:'ATR_ALIAS_NM', name: 'ATR_ALIAS_NM', label: '속성별칭명', width: 150, align:'left'},
	            { index:'SORT_NO', name: 'SORT_NO', label: '정렬순서', width: 80, align:'right', fixed:true},
	       		{ index:'MULTI_ATR_YN', name: 'MULTI_ATR_YN', label: '멀티속성', width: 80, align:'center', fixed:true},
	       		{ index:'CNCT_ATR_YN', name: 'CNCT_ATR_YN', label: '연결속성', width: 80, align:'center', fixed:true},
	            { index:'MAND_YN', name: 'MAND_YN', label: '필수여부', width: 80, align:'center', fixed:true},
	            { index:'ATR_ADM_TGT_YN', name: 'ATR_ADM_TGT_YN', label: '관리대상여부', width: 100, align:'center', fixed:true},
	            { index:'DEGR_NO', name: 'DEGR_NO', label: '관리차수', width: 80, align:'right', fixed:true},
	            { index:'INDC_YN', name: 'INDC_YN', label: '디스플레이여부', width: 100, align:'center', fixed:true},
	            { index:'FIND_TGT_NO', name: 'FIND_TGT_NO', label: '검색구분', width: 80, align:'center', fixed:true},
	            { index:'AVAIL_CHK_PGM_ID', name: 'AVAIL_CHK_PGM_ID', label: '체크PGM', width: 80, align:'center', fixed:true},
	            { index:'UI_COMP_WIDTH_RADIO_DISP', name: 'UI_COMP_WIDTH_RADIO_DISP', label: 'UI WIDTH', width: 80, align:'center', fixed:true},
	            { index:'UI_COMP_HEIGHT_PX_DISP', name: 'UI_COMP_HEIGHT_PX_DISP', label: 'UI HEIGHT', width: 80, align:'center', fixed:true}
	        ];
	      	
	        var childGridID = pRowid + "_table";
	        var childGridPagerID = pRowid + "_pager";
	          
	        $('#' + pRowid).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');
	        $("#" + childGridID).jqGrid({
	       		url: "jqgrid/list",
	            datatype:"json",
	            colModel: subColModel,
	            loadonce: true,
	            height: '100%',
	            scroll:0,
	            scrollOffset:0,
	            autowidth:true,
	            shrinkToFit: true,
	            rownumbers: false,
	            postData:{
	           	   "queryId":"objtype.getObjTypeAtrD",
	           	   "objTypeId":rowData["OBJ_TYPE_ID"]
	            }/*,
              	pager: "#" + childGridPagerID*/
          });
    	}
    };
    
	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		toppager: false,
	   	autowidth: true,
		shrinkToFit: true,
		isPaging:false,
		onSelectRow : function (rowid, status, e) {
        	var $radio = $('input[type=radio]', $("#"+rowid));
            $radio.prop('checked', 'checked');
        },
        rownumbers: true,
	    subGrid:true,
        subGridOptions:{
        	/*expandOnLoad: false,*/
        	reloadOnExpand :false,
        	selectOnExpand :true
        },
        subGridRowExpanded:showSubGrid
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
		var postData = {
			"queryId":"objtype.getObjTypeM",
			"searchKey":$("#searchValue").attr("de-search-key"),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	$("button#btnInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			["objTypeView"],
			{
				viewname:'admin/objTypeView',
				mode:'C'
			},
		    "width=1300, height=800, toolbar=non, menubar=no"
		);
	});
	
	$("button#btnUpdate").on("click", function(e) {
		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $grid.jqGrid("getLocalRow", rowid);
		
		DE.ui.open.popup(
			"view",
			["objTypeView"],
			{
				viewname:'admin/objTypeView',
				"objTypeId":rowData["OBJ_TYPE_ID"],
				mode:'U'
			},
		    "width=1300, height=800, toolbar=non, menubar=no"
		);
	});
	
	$("button#btnRemove").on("click", function(e) {
		var callback = {
			success : function(response) {
				DE.box.alert(response["message"], function(){
					DE.jqgrid.reload($("#jqGrid"));
				});
			},
			error : function(response) {
				DE.box.alert(response["responseJSON"]["message"]);
			}
		};
		
		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var removeAction = function() {
			var rowid = $selRadio.closest("tr").prop("id");
			var rowData = $grid.jqGrid("getLocalRow", rowid);
			
			var formData = {
				"OBJ_TYPE_ID":rowData["OBJ_TYPE_ID"]
			};
			
			var opts = {
				url : "admin/objtype?oper=remove",
				data : $.param(formData)
			};	
			
			DE.ajax.formSubmit(opts, callback.success, callback.error);
		};
		
		DE.box.confirm(DE.i18n.prop("common.message.remove.confirm"), function (b) {
        	if (b === true) {
        		removeAction();
        	}
        });
	});
	
	$("button#btnAtrMgrd").on("click", function(e) {		
			
			var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
			if ($selRadio.length === 0) {
				DE.box.alert(DE.i18n.prop("common.message.selected.none"));
				return;
			}
			var rowid = $selRadio.closest("tr").prop("id");
			var rowData = $grid.jqGrid("getLocalRow", rowid);
			
		DE.ui.open.popup(
				"view",
				["atrMgrView"],
				{
					viewname:'admin/atrMgrView',
					OBJ_TYPE_ID:rowData["OBJ_TYPE_ID"]
				},
			    "width=1300, height=800, toolbar=non, menubar=no"
			);
	});
	
	var init = function() {
		$("button#btnSearch").trigger("click");
		
		$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
	};
	init();
});