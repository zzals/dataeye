$(document).ready(function() {
	var parentObj = opener || parent;
	var reqParam = $("input#reqParam").data();
    
	var renderGrid = function($grid, $gridPager) {
	    var colModel = [
			DE.jqgrid.colModel.objTypeIcon({ label: '유형'}),
			{name:'OBJ_NM',index:'OBJ_NM', label:'객체명', width:250, align:'left', 
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
	      	DE.jqgrid.colModel.objTypeIcon({ index:'PATH_OBJ_TYPE_ID', name: 'PATH_OBJ_TYPE_ID', label: '경로유형'}),
			{name:'PATH_OBJ_NM',index:'PATH_OBJ_NM', label:'경로객체명', width:250, align:'left', 
		    	formatter: "dynamicLink", 
		    	formatoptions: {
		    		onClick: function (rowid, irow, icol, cellvalue, e) {
		    			var rowData = $(this).jqGrid("getLocalRow", rowid);
		    			DE.ui.open.popup(
	    					"view",
	    					[rowData["PATH_OBJ_TYPE_ID"], rowData["PATH_OBJ_ID"]],
	    					{
	    						viewname:'common/metacore/objectInfoTab',
	    						objTypeId:rowData["PATH_OBJ_TYPE_ID"],
	    						objId:rowData["PATH_OBJ_ID"],
	    						mode:'R'
	    					},
	    					null
	    				);
		    		}
		    	}
			},
			{name:'REL_TYPE_ID',index:'REL_TYPE_ID', label:'REL_TYPE_ID', hidden:true},
			{name:'OBJ_ID',index:'OBJ_ID', label:'OBJ_ID', hidden:true},
			{name:'PATH_OBJ_ID',index:'PATH_OBJ_ID', label:'PATH_OBJ_ID', hidden:true}
	   	];
	
		var opts = {
			"colModel":colModel,
	        pager:"#"+$gridPager.prop("id"),
	        isPaging:false,
			multiselect: false,
			scroll:-1,
			autowidth:true,
			shrinkToFit: true,
			height:200
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
	
	var search = function() {
		var postData = {
			"queryId":"metacore.getRelObjDefaultView",
			"relDv":reqParam["relDv"],
			"upRelTypeId":reqParam["relTypeId"],
			"objTypeId":reqParam["objTypeId"],
			"objId":reqParam["objId"],
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	};

	var init = function() {
		renderGrid($("#jqGrid"), $("#jqGridPager"));
		search();
	};
	init();
});