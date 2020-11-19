$(document).ready(function() {
	var parentObj = opener || parent;
	var reqParam = $("input#reqParam").data();
    
	var renderGrid = function($grid, $gridPager) {
	    var colModel = [
	        DE.jqgrid.colModel.objTypeIcon({ label: '유형'}),
			{name:'OBJ_NM',index:'OBJ_NM', label:'객체명', width:150, align:'left', 
		    	formatter: "dynamicLink", 
		    	formatoptions: {
		    		onClick: function (rowid, irow, icol, cellvalue, e) {
		    			var rowData = $(this).jqGrid("getLocalRow", rowid);
		    			DE.ui.open.popup(
	    					"view",
	    					[rowData["OBJ_TYPE_ID"], rowData["OBJ_ID"]],
	    					{
	    						viewname:'common/metacore/objectInfoTab',
	    						objTypeId:rowData["OBJ_TYPE_ID"],
	    						objId:rowData["OBJ_ID"],
	    						mode:'R'
	    					},
	    					null
	    				);
		    		}
		    	}
	        },
			{name:'OBJ_DESC',index:'OBJ_DESC', label:'설명', width:250, align:'left'},
			{name:'OBJ_ID',index:'OBJ_ID', label:'OBJ_ID', hidden:true}
	   	];
	
		var opts = {
			"colModel":colModel,
	        pager:"#"+$gridPager.prop("id"),
	        isPaging:false,
			loadonce: true,
			multiselect: false,
			scroll:-1,
			autowidth:true,
			shrinkToFit: true,
			height:400,
	        gridComplete: function() {
	        	DE.jqgrid.checkedHandler($(this));
	        }
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
		
		DE.jqgrid.render($grid, opts);
		DE.jqgrid.navGrid($grid, $gridPager, navOpts);
	}

	var init = function() {
		renderGrid($("#jqGrid"), $("#jqGridPager"));
		
		var postData = {
			"queryId":"metacore.getRelObjPTView",
			"metaRelCd":reqParam["relDv"],
			"relTypeId":reqParam["relTypeId"],
			"objTypeId":reqParam["objTypeId"],
			"objId":reqParam["objId"]
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	};
	init();
});