	var objTypeId = "010102L";
	var colModel = [
		{name:'CHK',index:'CHK', label:"<input type='checkbox' id='chkAll' name='chkAll' onclick='javascript:DE.jqgrid.checkAll(this, event);'/>", width:55, align:"center",
			sortable:false,
			formatter: 'checkbox',
			formatoptions: {disabled:false},
			edittype:'checkbox',
			editoptions:{value:"true:false"},
			editable:true
		},
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
        { index:'ICON', name: 'ICON', label: '구분', width: 50, align:'left',formatter: iconFormat},   
	    { index:'OBJ_NM', name: 'OBJ_NM', label: '정형분석명', width: 400, align:'left',formatter: titleFormat,unformat:titleUnFormat},            	
      	{ index:'OBJ_ATR_VAL_101_NM', name: 'OBJ_ATR_VAL_101_NM', label: '중요도', width: 100, align:'center' 	},
      	{ index:'OBJ_ATR_VAL_102_NM', name: 'OBJ_ATR_VAL_102_NM', label: '활용목적', width:100, align:'center', hidden:true},
      	{ index:'OBJ_ATR_VAL_104_NM', name: 'OBJ_ATR_VAL_104_NM', label: '활용주기', width: 100, align:'center'},
      	{ index:'OBJ_ATR_VAL_107_NM', name: 'OBJ_ATR_VAL_107_NM', label: '담당부서', width:100, align:'center'},
      	{ index:'OBJ_ATR_VAL_105_NM', name: 'OBJ_ATR_VAL_105_NM', label: '사용자구분', width: 150, align:'left'},
      	{ index:'OBJ_TYPE_ID', name: 'OBJ_TYPE_ID', label: 'OBJ_TYPE_ID', hidden:true},
        { index:'OBJ_ID', name: 'OBJ_ID', label: 'OBJ_ID', hidden:true},
        { index:'PATH_OBJ_TYPE_ID', name: 'PATH_OBJ_TYPE_ID', label: 'PATH_OBJ_TYPE_ID', hidden:true},
        { index:'PATH_OBJ_ID', name: 'PATH_OBJ_ID', label: 'PATH_OBJ_ID', hidden:true},
        { index:'D108_ATR_VAL', name: 'D108_ATR_VAL', label: 'D108_ATR_VAL', hidden:true}
        
        
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
		var link = "/dataeye/report/reportPrompt?reportId=" + rowObject["OBJ_ID"];
		var htmlStr = "<span class=\"fav_star\"><i class=\"fa fa-star\" onclick=\"goBookmark(this,'" + rowObject["OBJ_TYPE_ID"] + "','" + rowObject["OBJ_ID"] + "')\"></i>&nbsp;<a class=\"dynamicLink\" href=\"#\" onClick=\"goDetail('"+rowObject["OBJ_ID"]+"');\" >" + cellvalue + "</a>";
		
		if(rowObject["AUTH_YN"] == 'YES'){
			if(rowObject["BOOKMARK_YN"] == 'YES'){
				htmlStr = "<span class=\"fav_star\"><i class=\"fa fa-star2\" onclick=\"cancelBookmark(this,'" + rowObject["OBJ_TYPE_ID"] + "','" + rowObject["OBJ_ID"] + "')\"></i>&nbsp;<a class=\"dynamicLink\" href=\"#\" onClick=\"goDetail('"+rowObject["OBJ_ID"]+"');\" >" + cellvalue + "</a>";
			}
		} else {
			htmlStr = "<span class=\"fav_star\">&nbsp;<a class=\"dynamicLink\" href=\"#\" onClick=\"alert('해당보고서의 실행 권한이 없습니다.')\" >" + cellvalue + "</a>";
			htmlStr += " <span class=\"fa fa-lock\">&nbsp;</span>";
		}
		
		return htmlStr;
	}
	
	function titleUnFormat (cellvalue, options, rowObject){
		return cellvalue;
	}
	
	function iconFormat  (cellvalue, options, rowObject){
		
		var img = ""
		if(rowObject["D108_ATR_VAL"] == "TBLU"){
			 img = "<span class=\"effect2\"><img src=\"/dataeye/assets/images/tableau_icon.gif\" width=\"18px\"></span>"			
		} else if(rowObject["D108_ATR_VAL"] == "MSTR"){
			 img = "<span class=\"effect2\"><img src=\"/dataeye/assets/images/mstr_icon.gif\" width=\"18px\" class=\"effect\"></span>"			
		}
		return img;
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
			"queryId":"portal.report.reportList",
			"searchKey":"",
			"searchValue":"",
			"cateIds":cateIds,
			"rGubun":"01"
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	};
	
	
	$("button#btnInsert").on("click", function(e) {
		DE.ui.open.popup(
			"view",
			["010102L", "C"],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:'010102L',
				objId:"",
				mode:'C',
				menuId:"BDP_ADMIN"
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
		var rowId = $grid.jqGrid("getGridParam", "selrow");
        if (rowId == undefined) {
        	DE.box.alert("선택된 객체가 없습니다.");
            return;
        }
        
        var rowData = $grid.getLocalRow(rowId);
		DE.ui.open.popup(
			"view",
			["010102L", "U"],
			{
				viewname:'common/metacore/objectInfoTab',
				objTypeId:rowData["OBJ_TYPE_ID"],
				objId:rowData["OBJ_ID"],
				mode:'U',
				menuId:"BDP_ADMIN"
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
	
	var init = function() {
		gridLoad();
	};
	init();
	
