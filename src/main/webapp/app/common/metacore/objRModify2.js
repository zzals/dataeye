$(document).ready(function() {
	var parentObj = opener || parent;
	var reqParam = $("input#reqParam").data();
	var objNm = '객체명';
    if(reqParam["objTypeId"] == '010107L'){
     objNm = '정형리포트';
    }
    
	var renderGrid = function($grid, $gridPager) {
	    var colModel = [
			{name:'OBJ_TYPE_ID',index:'OBJ_TYPE_ID', label:'유형', width:100, align:'left'},
			{name:'OBJ_NM',index:'OBJ_NM', label:objNm, width:250, align:'left'},
			{name:'PATH_OBJ_TYPE_ID',index:'PATH_OBJ_TYPE_ID', label:'경로유형', width:100, align:'left'},
			{name:'PATH_OBJ_NM',index:'PATH_OBJ_NM', label:'경로객체명', width:250, align:'left'},
			{name:'REL_TYPE_ID',index:'REL_TYPE_ID', label:'REL_TYPE_ID', hidden:true},
			{name:'OBJ_ID',index:'OBJ_ID', label:'OBJ_ID', hidden:true},
			{name:'PATH_OBJ_ID',index:'PATH_OBJ_ID', label:'PATH_OBJ_ID', hidden:true}
	   	];
	
		var opts = {
			"colModel":colModel,
	        pager:"#"+$gridPager.prop("id"),
	        isPaging:false,
			loadonce: true,
			multiselect: true,
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
	
	$("button#btnAdd, button#btnAddAll, button#btnRemove, button#btnRemoveAll").on("click", function(e) {
		debugger;
		var move = function($src, $tgt) {
			var selRows = $src.jqGrid('getGridParam','selarrrow').slice();
			$.each(selRows, function(index, rowid) {
				var rowObject = $src.jqGrid('getRowData', rowid);
				$tgt.jqGrid('addRowData', $.jgrid.uidPref+DE.fn.guid.get(), rowObject);
				$src.jqGrid('delRowData', rowid);
			});
		};
		
		if ($(e.currentTarget).prop("id") == "btnAdd") {
			move($("#jqGrid1"), $("#jqGrid2"));
		} else if ($(e.currentTarget).prop("id") == "btnAddAll") {
		
		} else if ($(e.currentTarget).prop("id") == "btnRemove") {
			move($("#jqGrid2"), $("#jqGrid1"));
		} else if ($(e.currentTarget).prop("id") == "btnRemoveAll") {
		
		}
	});
	
	$("button#btnSearch").on("click", function(e) {
		var notInObjIds = [reqParam["objId"]];
		var data = $("#jqGrid2").jqGrid("getGridParam", "data");
		$.each(data, function(index, value) {
			notInObjIds.push(value["OBJ_ID"]);
		});
		var postData = {
			"queryId":"metacore.getRelSrcObj",
			"searchValue":$("#searchValue").val(),
			"relDv":reqParam["relDv"],
			"upRelTypeId":reqParam["relTypeId"],
			"objTypeId":$("#searchKey").val(),
			"notInObjIds":notInObjIds,
			"limit": DE.config.metaConfig["objRelSearchLimit"]
		};
		DE.jqgrid.reload($("#jqGrid1"), postData);
	});
	
	var getRelTgtObj = function() {
		var postData = {
			"queryId":"metacore.getRelTgtObj",
			"relDv":reqParam["relDv"],
			"upRelTypeId":reqParam["relTypeId"],
			"objTypeId":reqParam["objTypeId"],
			"objId":reqParam["objId"]
		};
		DE.jqgrid.reload($("#jqGrid2"), postData);
	};
	
	$("button#btnSave").on("click", function(e) {
		var data = $("#jqGrid2").jqGrid("getGridParam", "data");
		var penObjRList = [];
		for(var i=0; i<data.length; i++) {
			var penObjR = new DE.schema.PenObjR();
			
			if(reqParam["relDv"] == "DOWN") {
				penObjR.setObjTypeId(reqParam["objTypeId"]);
				penObjR.setObjId(reqParam["objId"]);
				penObjR.setRelObjTypeId(data[i].OBJ_TYPE_ID);
				penObjR.setRelObjId(data[i].OBJ_ID);
			} else {
				penObjR.setObjTypeId(data[i].OBJ_TYPE_ID);
				penObjR.setObjId(data[i].OBJ_ID);
				penObjR.setRelObjTypeId(reqParam["objTypeId"]);
				penObjR.setRelObjId(reqParam["objId"]);
			}
			penObjR.setRelTypeId(data[i].REL_TYPE_ID);
			penObjR.setObjRelAnalsCd("MC");
			
			penObjRList.push(penObjR);
		}
		
		var postData = {
			"objTypeId":reqParam["objTypeId"],
			"objId":reqParam["objId"],
			"relDv":reqParam["relDv"],
			"upRelTypeId":reqParam["relTypeId"],
			"data":penObjRList
		};
		
		var saveComplete = function(rsp) {
			if ("SUCCESS" === rsp["status"]) {
				DE.box.alert("저장 되었습니다.");
			}
		};
		DE.ajax.call({url:"../metacore/objectrel?oper=save", data:postData}, saveComplete);
	});

	var init = function() {
		var makeFilter = function(rsp) {
			DE.ui.render.selectBox($("#searchKey"), rsp["data"], {"valueKey":"OBJ_TYPE_ID", "nameKey":"OBJ_TYPE_NM"});
		};
		DE.ajax.call({url:"../metacore/objRelTabs/filter", data:{"relDv":reqParam["relDv"], "upRelTypeId":reqParam["relTypeId"]}}, makeFilter);
		
		renderGrid($("#jqGrid1"), $("#jqGridPager1"));
		renderGrid($("#jqGrid2"), $("#jqGridPager2"));
		getRelTgtObj();
	};
	init();
});