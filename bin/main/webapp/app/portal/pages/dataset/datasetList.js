	var objTypeId = "010102L";
	var colModel = [	   
	    { index:'PATH_OBJ_NM', name: 'PATH_OBJ_NM', label: '분류체계명', width: 100, align:'center',
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);
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
	    { index:'OBJ_NM', name: 'OBJ_NM', label: '데이타셋 명', width: 200, align:'left',formatter: titleFormat,unformat:titleUnFormat},            	
      	{ index:'ADM_OBJ_ID', name: 'ADM_OBJ_ID', label: '물리테이블명', width: 200, align:'left'},
      	{ index:'OBJ_DESC', name: 'OBJ_DESC', label: '데이터셋 설명', width:500, align:'left'},
      	{ index:'CRETR_ID', name: 'CRETR_ID', label: '등록자', width: 100, align:'center'},    
      	{ index:'OBJ_ID', name: 'OBJ_ID', label: 'OBJ_ID', hidden:true}, 	
        { index:'PATH_OBJ_TYPE_ID', name: 'PATH_OBJ_TYPE_ID', label: 'PATH_OBJ_TYPE_ID', hidden:true},
        { index:'PATH_OBJ_ID', name: 'PATH_OBJ_ID', label: 'PATH_OBJ_ID', hidden:true}
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
		beforeSelectRow: function (rowid, e) {
        	var $radio = $('input[type=radio]', $(e.target).closest('tr'));
            $radio.prop('checked', 'checked');
            return true;
        },
        loadComplete : function (data) {
			var heightMargin = $(".content-body .box-body").offset().top - $(".content-header").offset().top,
			bottomMargin = 10, // bottom margin 10
			gridHeadHeight = 60; // grid header height 30 + grid footer height 30
		
			
	    	$("#jqGrid").setGridWidth($(".content-body #listType").width(),opts["autowidth"]);
	    	$("#jqGrid").setGridHeight($(".content").height() - (heightMargin + bottomMargin + gridHeadHeight));
	      	if(opts["autowidth"]==true){
		    	$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
		    }
        }
	};
	
	
	function titleFormat (cellvalue, options, rowObject){
		var link = "/dataeye/dataset/datasetDetail?datasetId=" + rowObject["OBJ_ID"];
		return "<i class=\"fa fa-star-o\"></i>&nbsp;<a class=\"dynamicLink\" href=\"#\" onClick=\"window.open('" + link + "')\" >" + cellvalue + "</a>";
	}
	
	function titleUnFormat (cellvalue, options, rowObject){
		return cellvalue;
	}
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"));
	
	/*$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"portal.report.reportList",
			"searchKey":"",
			"searchValue":""
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	*/
	function gridLoad(cateIds) {
		var postData = {
			"queryId":"portal.dataset.datasetList",
			"searchKey":"",
			"searchValue":"",
			"cateIds":cateIds
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	};
	
	
	$("button#btnInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			[termTypeId],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:objTypeId,
				mode:'C'
			},
			null
		);
	});
	
	$("button#btnBulkInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			["std.p0004"],
			{
				viewname:'portal/biz/std/std.p0004',
				objTypeId:objTypeId
			},
			null
		);
	});
	
	$("button#btnUpdate").on("click", function(e) {
		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $grid.jqGrid("getRowData", rowid);
		
		DE.ui.open.popup(
			"view",
			[rowData["OBJ_TYPE_ID"], rowData["OBJ_ID"]],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:rowData["OBJ_TYPE_ID"],
				objId:rowData["OBJ_ID"],
				mode:'U'
			},
			null
		);
	});
	
	$("button#btnDelete").on("click", function(e) {
		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
        
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $grid.jqGrid("getRowData", rowid);
        var removeAction = function() {
			var opts = {
				url : "metacore/objectInfo/delete",
				data : {"objTypeId":rowData["OBJ_TYPE_ID"], "objId":rowData["OBJ_ID"]}
			};	
			var callback = {
				success : function(response) {
					DE.box.alert(response["message"], function(){
						DE.jqgrid.reload($grid);
					});
				},
				error : function(response) {
					DE.box.alert(response["responseJSON"]["message"]);
				}
			};
			debugger;
			DE.ajax.call(opts, callback.success, callback.error);
		};
        DE.box.confirm(DE.i18n.prop("common.message.remove.confirm"), function (b) {
        	if (b === true) {
        		removeAction();
        	}
        });
	});
	
	$("#btnAprvReq").on("click", function(e){
		DE.ui.open.popup(
			"view",
			["stdTermAprvReq"],
			{
				"viewname":"portal/biz/std/req.010302L",
				"mode":"C"
			},
			null
		);
	});
	
	/*
	$(this).off("autoResize").on( "autoResize", function( event ) {
	    setTimeout(function () {
	    	var heightMargin = $(".content-body .box-body").offset().top - $(".content-body").offset().top,
	    		bottomMargin = 10, // bottom margin 10
	    		gridHeadHeight = 60; // grid header height 30 + grid footer height 30
	    	
	    		
	    	$("#jqGrid").setGridWidth($(".content-body #listType").width(),opts["autowidth"]);
	    	$("#jqGrid").setGridHeight($(".content").height() - (heightMargin + bottomMargin + gridHeadHeight));
	      	if(opts["autowidth"]==true){
		    	$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
		    }
    	}, 500);
	}).trigger("autoResize");
*/
	var init = function() {
		gridLoad();
	};
	init();
