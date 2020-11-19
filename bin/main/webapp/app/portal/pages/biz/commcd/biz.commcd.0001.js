$(document).ready(function() {
	let theme = getCookie("theme");
	let containerTop = $("#container").offset().top - $(".content-header").offset().top;
	let containerHeight = $(".content").height() - containerTop;
	if(theme=="theme/se"){
		$("#container").height(containerHeight);
	}
	
	var commcdGrpTypeId = "010502L";
	
	_layout = $('#container').layout({
		center : {
			onresize: function() {
				$(document).trigger("autoResize");
			}
		}
		,	south : {
			size : 300,
			onresize: function() {
				$(document).trigger("autoResize");
			}
		}
	});
	
	var colModel = [	          
		{ index:'CHK', name:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
	    { index:'COMMCD_GRP_NM', name: 'COMMCD_GRP_NM', label: '코드그룹명', width: 100, align:'left', 
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $(this).jqGrid("getRowData", rowid);
	    			DE.ui.open.popup(
    					"view",
    					[rowData["COMMCD_GRP_TYPE_ID"], rowData["COMMCD_GRP_ID"]],
    					{
    						viewname:'common/metacore/objectInfoTab',
    						objTypeId:rowData["COMMCD_GRP_TYPE_ID"],
    						objId:rowData["COMMCD_GRP_ID"],
    						mode:'R'
    					},
    					null
    				);
	    		}
	    	}
      	},
      	{ index:'COMMCD_GRP_DESC', name: 'COMMCD_GRP_DESC', label: '설명', width: 200, align:'left'},
      	{ index:'STD_TERM_NM', name: 'STD_TERM_NM', label: '표준용어명', width: 100, align:'left'},
      	{ index:'CD_TBL_NM', name: 'CD_TBL_NM', label: '코드테이블명', width: 100, align:'left'},
      	{ index:'INPUT_YN', name: 'INPUT_YN', label: '입력가능여부', width: 100, align:'center', fixed:true, edittype:'checkbox',formatter:'checkbox',editoptions:{value:"Y:N"},editable:true, formatoptions: {disabled : true}},
      	{ index:'COMMCD_GRP_TYPE_ID', name: 'COMMCD_GRP_TYPE_ID', label: 'COMMCD_GRP_TYPE_ID', width: 80, align:'left',hidden:true},
      	{ index:'COMMCD_GRP_ID', name: 'COMMCD_GRP_ID', label: 'COMMCD_GRP_ID', width: 80, align:'left',hidden:true}
    ];

	var lastSelectedRowId = "";
	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		autowidth:true,
		shrinkToFit:true,
		toppager: false,
		loadonce: true,
		scroll:-1,
		isPaging:false,
		onSelectRow: function(rowid, status, e) {
			lastSelectedRowId = rowid;
			var rowData = $(this).jqGrid("getRowData", rowid);
            searchDetail(rowData["COMMCD_GRP_TYPE_ID"], rowData["COMMCD_GRP_ID"]);
        },
		beforeSelectRow: function (rowid, e) {
        	var $radio = $('input[type=radio]', $(e.target).closest('tr'));
            $radio.prop('checked', 'checked');
            return true;
        },
		loadComplete: function (data) {
			if (!lastSelectedRowId) {
				lastSelectedRowId = $(this).jqGrid("getDataIDs")[0];
				
				if (lastSelectedRowId) {
					$(this).jqGrid("setSelection", lastSelectedRowId);
				} else {
					$("#jqGrid2").jqGrid("clearGridData");
				}				
			}
        }
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"));

	var lastSelectedRowId2 = "";
	var colModel2 = [
	    { index:'CD_ID', name: 'CD_ID', label: '코드ID', width: 100, align:'left', editable:true, edittype:"text"},
      	{ index:'CD_NM', name: 'CD_NM', label: '코드값', width: 100, align:'left', editable:true, edittype:"text"},
      	{ index:'CD_DESC', name: 'CD_DESC', label: '설명', width: 150, align:'left', editable:true, edittype:"textarea"},      	      
      	/*{ index:'SORT_NO', name: 'SORT_NO', label: '정렬', width: 80, align:'center'},*/
      	{ index:'CD_GRP_ID', name: 'CD_GRP_ID', label: 'CD_GRP_ID', hidden:true}
    ];

	var opts2 = {
		url:"biz/commcd?oper=findCommcd",
		"colModel":colModel2, 
		pager:"#jqGridPager2",
		autowidth:true,
		shrinkToFit:true,
		toppager: false,
		loadonce: true,
		scroll:-1,
		isPaging:false,
		onSelectRow : function (rowid, status, e) {
	        if (rowid) {
	        	if (rowid !== lastSelectedRowId2) {
	        		$(this).jqGrid('saveRow', lastSelectedRowId2);
	        	}
	            $(this).jqGrid('editRow', rowid);
	            lastSelectedRowId2 = rowid;
	        }
		}
	};
	
	var $grid2 = DE.jqgrid.render($("#jqGrid2"), opts2);
	DE.jqgrid.navGrid($("#jqGrid2"), $("#jqGridPager2"));
	
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"portal.biz.commcd.findCommCdGrp",
			"commcdGrpTypeId":commcdGrpTypeId, 
			"searchKey":$("#searchKey").val(),
			"searchValue":$("#searchValue").val()
		};
		lastSelectedRowId = "";
		
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	var searchDetail = function(objTypeId, objId) {
		var postData = {
			"objTypeId":objTypeId, 
			"objId":objId
		};
		DE.jqgrid.reload($("#jqGrid2"), postData);
	};	
	
	$("button#btnCdGrpInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			[commcdGrpTypeId],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:commcdGrpTypeId,
				mode:'C'
			},
			null
		);
	});
	
	$("button#btnCdGrpUpdate").on("click", function(e) {
		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $grid.jqGrid("getRowData", rowid);
		
		DE.ui.open.popup(
			"view",
			[rowData["COMMCD_GRP_TYPE_ID"], rowData["COMMCD_GRP_ID"]],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:rowData["COMMCD_GRP_TYPE_ID"],
				objId:rowData["COMMCD_GRP_ID"],
				mode:'U'
			},
			null
		);
	});
	
	$("button#btnCdGrpDelete").on("click", function(e) {
		var $selRadio = $('input[name=radio_' + $grid.prop("id") + ']:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
        
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $grid.jqGrid("getRowData", rowid);
        var removeAction = function() {
			var opts = {
				url : "portal/biz/commcd?oper=deleteCommcdGrp",
				data : {"objTypeId":rowData["COMMCD_GRP_TYPE_ID"], "objId":rowData["COMMCD_GRP_ID"]}
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
			
			DE.ajax.call(opts, callback.success, callback.error);
		};
        DE.box.confirm(DE.i18n.prop("common.message.remove.confirm"), function (b) {
        	if (b === true) {
        		removeAction();
        	}
        });
	});
	
	$("button#btnCommcdAdd").on("click", function(e) {
		if (lastSelectedRowId2) {
			$('#jqGrid2').jqGrid('saveRow', lastSelectedRowId2);
        }
		var newRowId = $.jgrid.uidPref+DE.fn.guid.get();
		$("#jqGrid2").jqGrid('addRowData', newRowId, {});
		$("#jqGrid2").jqGrid("setSelection", newRowId);
	});	
	$("button#btnCommcdDel").on("click", function(e) {
		var rowid = $("#jqGrid2").jqGrid("getGridParam", "selrow");
		if (rowid === null) return;
		
		$("#jqGrid2").jqGrid('delRowData', rowid);
	});
	var gridRowUpAndDown = function(direction) {
		console.log("lastSelectedRowId2 => " + lastSelectedRowId2)
		if (lastSelectedRowId2) {
			$('#jqGrid2').jqGrid('saveRow', lastSelectedRowId2);
        }
		
		if($('#jqGrid2').getGridParam('selrow')){
			var currRowId = $('#jqGrid2').getGridParam('selrow');
            var prevRowId = $("#"+currRowId).prev().prop("id");
            var nextRowId = $("#"+currRowId).next().prop("id");
            
            if(direction === 'up' && prevRowId != "") {            	
				var r1 = $('#jqGrid2').getRowData(prevRowId);
				var r2 = $('#jqGrid2').getRowData(currRowId);
				$('#jqGrid2').setRowData(prevRowId, r2);
				$('#jqGrid2').setRowData(currRowId, r1);

	    		$("#jqGrid2").jqGrid("setSelection", prevRowId);
            } else if (direction === 'down' && nextRowId != "") {
            	var r1 = $('#jqGrid2').getRowData(currRowId);
				var r2 = $('#jqGrid2').getRowData(nextRowId);				
				$('#jqGrid2').setRowData(currRowId, r2);
				$('#jqGrid2').setRowData(nextRowId, r1);

	    		$("#jqGrid2").jqGrid("setSelection", nextRowId);
            }
        }
	};
	
	$("button#btnCommcdUp").on("click", function(e) {
		gridRowUpAndDown("up");
	});
	$("button#btnCommcdDown").on("click", function(e) {
		gridRowUpAndDown("down");
	});
	$("button#btnCommcdSave").on("click", function(e) {
		var cdGrpSelRow =  $("#jqGrid").jqGrid("getGridParam", "selrow");
		if (cdGrpSelRow === undefined || cdGrpSelRow === "") {
			DE.box.alert("선택된 코드그룹이 없습니다.");
			return;
		}
		if (lastSelectedRowId2) {
			$('#jqGrid2').jqGrid('saveRow', lastSelectedRowId2);
        }
		
		var data = $("#jqGrid2").jqGrid("getGridParam", "data");
		var invalidRowId = "";
		$.each(data, function(index, value) {
			var cdId = value["CD_ID"];
			var cdNm = value["CD_NM"];
			
			if (cdId === undefined || cdId === "") {
				invalidRowId = value["_id_"];
				DE.box.alert("코드ID 값은 필수 입력값 입니다.");
				return false;
			}
			if (cdNm === undefined || cdNm === "") {
				invalidRowId = value["_id_"];
				DE.box.alert("코드명 값은 필수 입력값 입니다.");
				return false;
			}
		});
		if (invalidRowId !== "") {
			$("#jqGrid2").jqGrid("setSelection", invalidRowId);
			return;
		}
		
		var saveAction = function() {
			var rowData = $("#jqGrid").getRowData($("#jqGrid").jqGrid("getGridParam", "selrow"));
			var opts = {
				url : "portal/biz/commcd?oper=saveCommcd",
				data : {"cdGrpTypeId":rowData["COMMCD_GRP_TYPE_ID"], "cdGrpId":rowData["COMMCD_GRP_ID"], "data":data}
			};	
			var callback = {
				success : function(response) {
					DE.box.alert(response["message"]);
				},
				error : function(response) {
					DE.box.alert(response["responseJSON"]["message"]);
				}
			};
			
			DE.ajax.call(opts, callback.success, callback.error);
		};
        DE.box.confirm(DE.i18n.prop("common.message.save.confirm"), function (b) {
        	if (b === true) {
        		saveAction();
        	}
        });
	});
	
	$(this).off("autoResize").on( "autoResize", function( event ) {
	    setTimeout(function () {
	    	var heightMargin = 70;
	    	var heightMargin2 = 70;
	    	
	    	containerHeight = $(".content").height() - containerTop;
	    	if(theme=="theme/se"){
	    		$("#container").height(containerHeight);
	    	}
	    	$("#jqGrid").setGridWidth($(".content-body .box-body").width(),opts["autowidth"]);
	    	$("#jqGrid").setGridHeight($(".ui-layout-center").height() - ($(".ui-layout-center .box-body").offset().top-$(".ui-layout-center").offset().top)-heightMargin);
	    	
	    	if(opts["autowidth"]==true){
		    	$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
		    }
	    	
	    	$("#jqGrid2").setGridWidth($(".content-body .box-body").width(),opts2["autowidth"]);
	    	$("#jqGrid2").setGridHeight($(".ui-layout-south").height() - ($(".ui-layout-south .box-body").offset().top-$(".ui-layout-south").offset().top)-heightMargin2);
	    	
	    	if(opts["autowidth"]==true){
		    	$(".ui-jqgrid2-bdiv").css("overflow-x","hidden")
		    }
	    	
    	}, 500);
	}).trigger("autoResize");

	var init = function() {
		$("button#btnSearch").trigger("click");
	};
	init();
});