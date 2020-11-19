$(document).ready(function() {
	var parentObj = opener || parent;
	var reqParam = $("input#reqParam").data();
    $(".popup_title").append("용어 적합성 검증");
    $("#searchValue").val(reqParam["termNm"]);
    
    var validYnDisp = function(v, opt, row) {
		if (row.VALID) {
			return '●';
		} else {
			return 'X';
		}
	};
	var classYnDisp = function(v, opt, row) {
		if ("" != row.CLASS_KOR_WORD) {
			return '●';
		} else {
			return 'X';
		}
	};
	var maxLenOver = function(v, opt, row) {
		if (30 >= row.ENG_TERM.length) {
			return '●';
		} else {
			return 'X';
		}
	};
	var actionDisp = function(v, opt, row) {
		if (row.VALID && (30 >= row.ENG_TERM.length)) {
			return '<input type="button" id="termSelected" name="termSelected" value="선택" style="width:50px;">';
		} else {
			return "부적합";
		}
	};
    var colModel = [
		{name:'KOR_TERM',index:'KOR_TERM', label:'표준용어명', width:250, align:'left'},
		{name:'ENG_TERM',index:'ENG_TERM', label:'영문약어명', width:250, align:'left'},
		{name:'VALID_YN',index:'VALID_YN', label:'적합성', width:100, align:'center', formatter:validYnDisp},
		{name:'CLASS_KOR_WORD_YN',index:'CLASS_KOR_WORD_YN', label:'분류어', width:100, align:'center', formatter:classYnDisp},
		{name:'MAX_LEN_OVER_YN',index:'MAX_LEN_OVER_YN', label:'Max Len', width:100, align:'center', formatter:maxLenOver},
		{name:'ACTION',index:'ACTION', label:'선택', width:80, align:'center', title: false,
			formatter:"customButton",
			formatoptions: {
				caption:"선택",
				nothingKey:"VALID",
				nothingValue:false,
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $(this).jqGrid("getRowData", rowid);
	    			if (reqParam.callback != undefined) {
	    				eval("parentObj."+reqParam.callback+"(rowData);");
	    			} else {
		    			parentObj.$("[id="+reqParam.targetObjectName+"]").attr("USE_DATA", rowData.KOR_TERM.replace(/[_]/g, ""));
		    			parentObj.$("[id="+reqParam.targetObjectName+"]").val(rowData.KOR_TERM.replace(/[_]/g, ""));
		    			parentObj.$("input[id^=ATR_ID_SEQ_3_]").attr("USE_DATA", rowData.ENG_TERM);
		    			parentObj.$("input[id^=ATR_ID_SEQ_3_]").val(rowData.ENG_TERM);
		    			parentObj.$("input[id^=ATR_ID_SEQ_7_]").val("010301L");
		    			parentObj.$("input[id^=ATR_ID_SEQ_8_]").attr("USE_DATA", rowData.CLASS_WORD_ID);
		    			parentObj.$("input[id^=ATR_ID_SEQ_8_]").val(rowData.CLASS_KOR_WORD);
		    			
		    			parentObj.$("input[id^=ATR_ID_SEQ_102_]").attr("USE_DATA", rowData.WORD_ID1);
		    			parentObj.$("input[id^=ATR_ID_SEQ_102_]").val(rowData.KOR_WORD1);
		    			parentObj.$("input[id^=ATR_ID_SEQ_103_]").attr("USE_DATA", rowData.WORD_ID2);
		    			parentObj.$("input[id^=ATR_ID_SEQ_103_]").val(rowData.KOR_WORD2);
		    			parentObj.$("input[id^=ATR_ID_SEQ_104_]").attr("USE_DATA", rowData.WORD_ID3);
		    			parentObj.$("input[id^=ATR_ID_SEQ_104_]").val(rowData.KOR_WORD3);
		    			parentObj.$("input[id^=ATR_ID_SEQ_105_]").attr("USE_DATA", rowData.WORD_ID4);
		    			parentObj.$("input[id^=ATR_ID_SEQ_105_]").val(rowData.KOR_WORD4);
		    			parentObj.$("input[id^=ATR_ID_SEQ_106_]").attr("USE_DATA", rowData.WORD_ID5);
		    			parentObj.$("input[id^=ATR_ID_SEQ_106_]").val(rowData.KOR_WORD5);
		    			parentObj.$("input[id^=ATR_ID_SEQ_107_]").attr("USE_DATA", rowData.WORD_ID6);
		    			parentObj.$("input[id^=ATR_ID_SEQ_107_]").val(rowData.KOR_WORD6);
		    			parentObj.$("input[id^=ATR_ID_SEQ_108_]").attr("USE_DATA", rowData.WORD_ID7);
		    			parentObj.$("input[id^=ATR_ID_SEQ_108_]").val(rowData.KOR_WORD7);
		    			parentObj.$("input[id^=ATR_ID_SEQ_109_]").attr("USE_DATA", rowData.WORD_ID8);
		    			parentObj.$("input[id^=ATR_ID_SEQ_109_]").val(rowData.KOR_WORD8);
		    			parentObj.$("input[id^=ATR_ID_SEQ_110_]").attr("USE_DATA", rowData.WORD_ID9);
		    			parentObj.$("input[id^=ATR_ID_SEQ_110_]").val(rowData.KOR_WORD9);
	    			}
	    	        self.close();
	    		}
	    	}
		},
		
		{name:'STEP',index:'STEP', label:'STEP', hidden:true},
		{name:'VALID',index:'VALID', label:'VALID', hidden:true},
		{name:'CLASS_KOR_WORD',index:'CLASS_KOR_WORD', label:'CLASS_KOR_WORD', hidden:true},
		{name:'CLASS_ENG_WORD',index:'CLASS_ENG_WORD', label:'CLASS_ENG_WORD', hidden:true},
		{name:'CLASS_WORD_ID',index:'CLASS_WORD_ID', label:'CLASS_WORD_ID', hidden:true},
		{name:'KOR_WORD1',index:'KOR_WORD1', label:'KOR_WORD1', hidden:true},
		{name:'KOR_WORD2',index:'KOR_WORD2', label:'KOR_WORD2', hidden:true},
		{name:'KOR_WORD3',index:'KOR_WORD3', label:'KOR_WORD3', hidden:true},
		{name:'KOR_WORD4',index:'KOR_WORD4', label:'KOR_WORD4', hidden:true},
		{name:'KOR_WORD5',index:'KOR_WORD5', label:'KOR_WORD5', hidden:true},
		{name:'KOR_WORD6',index:'KOR_WORD6', label:'KOR_WORD6', hidden:true},
		{name:'KOR_WORD7',index:'KOR_WORD7', label:'KOR_WORD7', hidden:true},
		{name:'KOR_WORD8',index:'KOR_WORD8', label:'KOR_WORD8', hidden:true},
		{name:'KOR_WORD9',index:'KOR_WORD9', label:'KOR_WORD9', hidden:true},
		{name:'ENG_WORD1',index:'ENG_WORD1', label:'ENG_WORD1', hidden:true},
		{name:'ENG_WORD2',index:'ENG_WORD2', label:'ENG_WORD2', hidden:true},
		{name:'ENG_WORD3',index:'ENG_WORD3', label:'ENG_WORD3', hidden:true},
		{name:'ENG_WORD4',index:'ENG_WORD4', label:'ENG_WORD4', hidden:true},
		{name:'ENG_WORD5',index:'ENG_WORD5', label:'ENG_WORD5', hidden:true},
		{name:'ENG_WORD6',index:'ENG_WORD6', label:'ENG_WORD6', hidden:true},
		{name:'ENG_WORD7',index:'ENG_WORD7', label:'ENG_WORD7', hidden:true},
		{name:'ENG_WORD8',index:'ENG_WORD8', label:'ENG_WORD8', hidden:true},
		{name:'ENG_WORD9',index:'ENG_WORD9', label:'ENG_WORD9', hidden:true},
		{name:'WORD_ID1',index:'WORD_ID1', label:'WORD_ID1', hidden:true},
		{name:'WORD_ID2',index:'WORD_ID2', label:'WORD_ID2', hidden:true},
		{name:'WORD_ID3',index:'WORD_ID3', label:'WORD_ID3', hidden:true},
		{name:'WORD_ID4',index:'WORD_ID4', label:'WORD_ID4', hidden:true},
		{name:'WORD_ID5',index:'WORD_ID5', label:'WORD_ID5', hidden:true},
		{name:'WORD_ID6',index:'WORD_ID6', label:'WORD_ID6', hidden:true},
		{name:'WORD_ID7',index:'WORD_ID7', label:'WORD_ID7', hidden:true},
		{name:'WORD_ID8',index:'WORD_ID8', label:'WORD_ID8', hidden:true},
		{name:'WORD_ID9',index:'WORD_ID9', label:'WORD_ID9', hidden:true}
   	];

	var opts = {
		url:"portal/biz/std?oper=termVerify",
		"colModel":colModel,
        pager:"#jqGridPager",
        isPaging:false,
		loadonce: false,
		height:310,
		scroll:-1,
		autowidth:true,
		shrinkToFit: true
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
		var searchValue = $("#searchValue").val();
		if ("" === searchValue) {
			DE.box.alert("용어명을 입력하세요");
			$("#searchValue").focus();
			return;
		}
		var rsp = DE.ajax.call({async:false, url:"portal/biz/std?oper=termVerify", data:{"objTypeId":reqParam["objTypeId"], "objId":reqParam["objId"], "term":searchValue}})
		if ("FAIL" === rsp["status"]) {
			DE.box.alert(rsp["message"]);
			$("#jqGrid").clearGridData();
			return;
		} else {
			DE.jqgrid.reloadLocal($("#jqGrid"), rsp["data"]);
		}
	});

	$(this).off("autoResize").on( "autoResize", function( event ) {
	    setTimeout(function () {
	    	var heightMargin = 50;
	    		
	    	$("#jqGrid").setGridWidth($(".content-body .box-body").width(),opts["autowidth"]);
	    	$("#jqGrid").setGridHeight($(".content-body").height() - ($(".content-body .box-body").offset().top-$(".content-body").offset().top)-heightMargin);
	    	
	      	if(opts["autowidth"]==true){
		    	$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
		    }
	    	
    	}, 500);
	}).trigger("autoResize");
	
	var init = function() {
	};
	init();
});