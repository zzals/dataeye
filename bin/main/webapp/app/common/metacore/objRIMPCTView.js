$(document).ready(function() {
	var parentObj = opener || parent;
	var reqParam = $("input#reqParam").data();
    
	var renderGrid = function($grid, $gridPager) {
	    var colModel = [
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
			DE.jqgrid.colModel.objTypeIcon({ label: '유형'}),
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
			{name:'OBJ_ID',index:'OBJ_ID', label:'OBJ_ID', hidden:true},
			{name:'PATH_OBJ_ID',index:'PATH_OBJ_ID', label:'PATH_OBJ_ID', hidden:true},
			{name:'ID',index:'ID', label:'ID', width:100, hidden:true, key:true},
			{name:'UP_ID',index:'UP_ID', label:'UP_ID', width:100, hidden:true},
			{name:'LVL',index:'LVL', label:'LVL', width:100, hidden:true}
	   	];
	
		var opts = {
			"colModel":colModel,
			pager:"#"+$gridPager.prop("id"),
	        loadonce: false,
			scroll:false,
			autowidth:true,
			shrinkToFit: false,
			height:200,
			rownumbers: false,
			sortable: false,
			viewrecords: false,
			treeGrid:true,
			ExpandColumn:"OBJ_NM",
			treedatatype:"json",
			treeGridModel:"adjacency", //"adjacency, nested",
	        treeReader : {
	            level_field: "LVL",
	            parent_id_field: "UP_ID",
	            leaf_field: "IS_LEAF",
	            expanded_field: "IS_EXPANDED"
	        },
	        ExpandColClick : false,
			localReader: {
				id: "ID"
			},
			beforeRequest : function() {
				if(this.p.postData.nodeid != null) {
					var rowData = $(this).jqGrid("getLocalRow", this.p.postData.nodeid);
					this.p.postData["objTypeId"] = rowData["OBJ_TYPE_ID"];
					this.p.postData["objId"] = rowData["OBJ_ID"];
					this.p.postData["upId"] = rowData["ID"];
				}
			}
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
		
		DE.jqgrid.render($grid, opts);
		DE.jqgrid.navGrid($grid, $gridPager, navOpts);
	}
	
	var expendLineageNodeEvent = function($grid) {
		var orgExpandNode = $.fn.jqGrid.expandNode;
		var orgCollapseNode = $.fn.jqGrid.collapseNode;
		$.jgrid.extend({
			expandNode: function (rc) {
				return orgExpandNode.call(this, rc);
			},
			collapseNode: function (rc) {
			    return orgCollapseNode.call(this, rc);
			}
		});
	};
	
	var getRelLineageObj = function() {
		var postData = {
			"queryId":"metacore.getRelObjLineageView",
			"metaRelCd":"FT",
			"objTypeId":reqParam["objTypeId"],
			"objId":reqParam["objId"]
		};
		DE.jqgrid.reload($("#jqGrid1"), postData);
	};
	
	var getRelImpactObj = function() {
		var postData = {
			"queryId":"metacore.getRelObjImpactView",
			"metaRelCd":"FT",
			"objTypeId":reqParam["objTypeId"],
			"objId":reqParam["objId"]
		};
		DE.jqgrid.reload($("#jqGrid2"), postData);
	};

	$("#btnFlowView").on("click", function(e){
		DE.ui.open.popup(
            "view",
            ["flowView"],
            {
                viewname: 'common/metacore/influenceView',
                "objTypeId": reqParam["objTypeId"],
                "objId": reqParam["objId"]
            },
            "width=1300, height=800, toolbar=non, menubar=no"
        );
	});
	
	var init = function() {
		renderGrid($("#jqGrid1"), $("#jqGridPager1"));
		renderGrid($("#jqGrid2"), $("#jqGridPager2"));
		expendLineageNodeEvent($("#jqGrid1"));
		expendLineageNodeEvent($("#jqGrid2"));
		getRelLineageObj();
		getRelImpactObj();
	};
	init();
});