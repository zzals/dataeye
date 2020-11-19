$(document).ready(function() {
	var parentObj = opener || parent;
	var reqParam = $("input#reqParam").data();
    $(".popup_title").append(reqParam["displayName"]);
    
    var rsp = DE.ajax.call({async:false, url:"metapublic/getAtrSbstRslt", data:{"objTypeId":reqParam["objTypeId"], "atrIdSeq":reqParam["atrIdSeq"]}});
    var keys = [];
	if (rsp["data"].length != 0) {
		keys = $.keys(rsp["data"][0]);
	};
	var colModel = [];
	$.each(keys, function(i, val) {
		if (val == "CODE") {
			colModel.push({name:val, index:val, label:val, hidden:true, key:true});
		} else if (val == "PCODE" || val == "LVL" || val == "ISLEAF" || val == "EXPANDED" || val == "DISP_PATH_NAME") {
			colModel.push({name:val, index:val, label:val, hidden:true});
		} else if (val == "NAME") {
			colModel.push({name:'NAME', index:'NAME', label:reqParam["displayName"], width:200, classes: 'pointer', formatter: "dynamicLink", 
		    	formatoptions: {
		    		onClick: function (rowid, irow, icol, cellvalue, e) {
		    			var rowData = $(this).jqGrid("getLocalRow", rowid);
		    			parentObj.$("[id="+reqParam.targetObjectName+"]").attr("USE_DATA", rowData["CODE"]);
		                parentObj.$("[id="+reqParam.targetObjectName+"]").val(rowData["DISP_NAME"]);
		                self.close();
		    		}
		    	}
	      	});
		} else if (val == "DISP_NAME") {
			colModel.push({name:'DISP_NAME', index:'DISP_NAME', label:val, hidden:true});
		} else {
			colModel.push({name:val, index:val, label:val, width:100});
		}
	});

	if ($.inArray("PCODE", keys) == -1) {
		colModel.push({name:'PCODE',index:'PCODE', label:'PCODE', hidden:true});
	}

	var opts = {
		"colModel":colModel, 
		datatype: function (postData) {
			$("#jqGrid")[0].addJSONData(rsp["data"]);
        },
        pager:"#jqGridPager",
		toppager: false,
		loadonce: true,
		scroll:false,
		height:348,
		rownumbers: false,
		shrinkToFit:true,
		forceFit:false,
		sortable: false,
        treeGridModel: 'adjacency',
        treeReader : {
            level_field: "LVL",
            parent_id_field: "PCODE",
            leaf_field: "ISLEAF",
            expanded_field: "EXPANDED"
        },
        treeGrid: true,
        ExpandColumn : 'NAME'
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
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"admin.getAtrM",
			"searchKey":$("#searchValue").attr("de-search-key"),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});

	var init = function() {
		$("button#btnSearch").trigger("click");
		$(window).trigger("resize");
	};
	init();
});