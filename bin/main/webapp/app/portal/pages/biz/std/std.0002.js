$(document).ready(function() {
	var wordTypeId = "010301L";
	var colModel = [
	    { index:'CHK', name:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
	    { index:'WORD_NM', name: 'WORD_NM', label: '표준단어명', width: 150, align:'left',
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);
	    			DE.ui.open.popup(
    					"view",
    					[rowData["WORD_TYPE_ID"], rowData["WORD_ID"]],
    					{
    						viewname:'common/metacore/objectInfoTab',
    						objTypeId:rowData["WORD_TYPE_ID"],
    						objId:rowData["WORD_ID"],
    						mode:'R'
    					},
    					null
    				);
	    		}
	    	}
      	},
      	{ index:'WORD_ENG_ABBR_NM', name: 'WORD_ENG_ABBR_NM', label: '영문약어명', width: 150, align:'left'},
      	{ index:'WORD_ENG_NM', name: 'WORD_ENG_NM', label: '영문정식명', width: 200, align:'left'},
      	{ index:'CLS_YN', name: 'CLS_YN', label: '분류어여부', width: 100, align:'center', edittype:'checkbox',formatter:'checkbox',editoptions:{value:"Y:N"},editable:true, formatoptions: {disabled : true}},
        { index:'TERM_USED_CNT', name: 'TERM_USED_CNT', label: '사용현황', width: 100, align:'right',
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $grid.jqGrid("getRowData", rowid);
	    			DE.ui.open.popup(
    					"view",
    					["TERM_USED_CNT"],
    					{
    						viewname:'portal/biz/std/std.p0002',
    						"wordId":rowData["WORD_ID"],
    						mode:'R'
    					},
    					null
    				);
	    		}
	    	}
      	},
        { index:'CRETR_NM', name: 'CRETR_NM', label: '등록자', width: 100, align:'center'},
        { index:'WORD_TYPE_ID', name: 'WORD_TYPE_ID', label: 'WORD_TYPE_ID', hidden:true},
        { index:'WORD_ID', name: 'WORD_ID', label: 'WORD_ID', hidden:true}
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
        }
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"));
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"portal.biz.std.findStdWord",
			"searchKey":$("#searchKey").val(),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	$("button#btnInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			[wordTypeId],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:wordTypeId,
				mode:'C'
			},
			null
		);
	});
	
	$("button#btnBulkInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			["std.p0003"],
			{
				viewname:'portal/biz/std/std.p0003',
				objTypeId:wordTypeId,
				cAuth:'Y'
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
			[rowData["WORD_TYPE_ID"], rowData["WORD_ID"]],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:rowData["WORD_TYPE_ID"],
				objId:rowData["WORD_ID"],
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
				data : {"objTypeId":rowData["WORD_TYPE_ID"], "objId":rowData["WORD_ID"]}
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
		debugger;
		DE.ui.open.popup(
			"view",
			["stdWordAprvReq"],
			{
				"viewname":"portal/biz/std/req.010301L",
				"mode":"C"
			},
			null
		);
	});
	
	$(this).off("autoResize").on( "autoResize", function( event ) {
	    setTimeout(function () {
	    	var heightMargin = $(".content-body .box-body").offset().top - $(".content-header").offset().top,
    		bottomMargin = 10, // bottom margin 10
    		gridHeadHeight = 60; // grid header height 30 + grid footer height 30

	    	$("#jqGrid").setGridWidth($(".content-body .box-body").width(),opts["autowidth"]);
	    	$("#jqGrid").setGridHeight($(".content").height() - (heightMargin + bottomMargin + gridHeadHeight));
	      	if(opts["autowidth"]==true){
		    	$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
		    }
    	}, 500);
	}).trigger("autoResize");

	var init = function() {
		$("button#btnSearch").trigger("click");
	};
	init();
});