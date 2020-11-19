$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
	$(".popup_Title_Area > div.popup_title").html("테이블목록 업로드");
	var uploadResult = null;

	var appSysTypeId = "050102L";
	var dbInstTypeId = "020100L";
	var databaseTypeId = "020101L";
	var dsgnTabTypeId = "040102L";
	var dsgnColTypeId = "040103L";
	
	var getFormData = function() {
		return {"databaseId": $("#selDatabase").val()};
	};
	$('#btnUpload').bind('fileuploadsubmit', function (e, data) {
		data.formData = {"databaseId": $("#selDatabase").val()};
	});
	
	$('#btnUpload').fileupload({
        url: DE.contextPath+'/portal/dsgn/db?oper=uploadDsgnTab',
        dataType: 'json',
        beforeSend: function(xhr, data) {
        	$("#jqGrid1").jqGrid("clearGridData", true);
        },
        done: function (e, data) {
        	$("#jqGrid1").jqGrid("setGridParam", {"datatype":"local", "data":data.result["DSGN_TAB"]}).trigger("reloadGrid");
        	
        	uploadResult = data.result["UPLOAD_RESULT"];
        	$("#uploadMessage").text(uploadResult["DSGN_TAB_ERR"]+" 건의 오류가 있습니다.");
            /*$.each(data.result.files, function (index, file) {
                $('<p/>').text(file.name).appendTo('#files');
            });*/
        },
        fail: function (e, data) {
        	$("#jqGrid1").jqGrid("clearGridData", true).trigger("reloadGrid");
        	DE.box.alert(data.jqXHR.responseJSON["message"]);
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .progress-bar').css(
                'width',
                progress + '%'
            );
        }
    }).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');
	
	//검증그룹
	var colModel = [	     
		{ index:'SEQ', name:'SEQ', label: '순번', width: 70, align:'left'},
		{ index:'DBMS', name:'DBMS', label: 'DBMS', width: 100, align:'left'},
		{ index:'APPSYS', name:'APPSYS', label: '시스템', width: 120, align:'left'},
		{ index:'TAB_TYPE_GBN', name:'TAB_TYPE_GBN', label: '테이블유형구분', width: 120, align:'left'},
		{ index:'TAB_INFO_GBN', name:'TAB_INFO_GBN', label: '테이블정보구분', width: 120, align:'left'},
		{ index:'CLS_GBN', name:'CLS_GBN', label: '업무구분', width: 120, align:'left'},
		{ index:'CLS_SUB_AREA', name:'CLS_SUB_AREA', label: '업무별세부영역', width: 120, align:'left'},
		{ index:'DB_NM', name:'DB_NM', label: 'DB명', width: 100, align:'left'},
		{ index:'SCHEMA_NM', name:'SCHEMA_NM', label: '스키마명', width: 100, align:'left'},
		{ index:'TAB_ENTITY_NM', name:'TAB_ENTITY_NM', label: '테이블논리명', width: 150, align:'left'},
		{ index:'TAB_NM', name:'TAB_NM', label: '테이블물리명', width: 120, align:'left'},
		{ index:'MNGR', name:'MNGR', label: '설계담당자', width: 100, align:'left'},
		{ index:'DID_YN', name:'DID_YN', label: '비식별여부', width: 100, align:'left'},
		{ index:'DATA_KEEP_PERIOD', name:'DATA_KEEP_PERIOD', label: '보관기간', width: 100, align:'left'},
		{ index:'DATA_GEN_PERIOD', name:'DATA_GEN_PERIOD', label: '데이터발생주기', width: 100, align:'left'},
		{ index:'DATA_GEN_CNT', name:'DATA_GEN_CNT', label: '데이터발생건수', width: 100, align:'left'},
		{ index:'DESC', name:'DESC', label: '설명', width: 200, align:'left'},
		
		{ index:'APPSYS_VAL', name:'APPSYS_VAL', label: 'APPSYS_VAL', hidden:true},
		{ index:'DB_ID_VAL', name:'DB_ID_VAL', label: 'DB_ID_VAL', hidden:true},
		{ index:'CLS_GBN_VAL', name:'CLS_GBN_VAL', label: 'CLS_GBN_VAL', hidden:true},
		{ index:'CLS_SUB_AREA_VAL', name:'CLS_SUB_AREA_VAL', label: 'CLS_SUB_AREA_VAL', hidden:true},
		{ index:'TAB_INFO_GBN_VAL', name:'TAB_INFO_GBN_VAL', label: 'TAB_INFO_GBN_VAL', hidden:true},
		{ index:'TAB_TYPE_GBN_VAL', name:'TAB_TYPE_GBN_VAL', label: 'TAB_TYPE_GBN_VAL', hidden:true},
		
		{ index:'SEQ_VALID', name:'SEQ_VALID', label: 'SEQ_VALID', hidden:true},
		{ index:'DBMS_VALID', name:'DBMS_VALID', label: 'DBMS_VALID', hidden:true},
		{ index:'APPSYS_VALID', name:'APPSYS_VALID', label: 'APPSYS_VALID', hidden:true},
		{ index:'TAB_TYPE_GBN_VALID', name:'TAB_TYPE_GBN_VALID', label: 'TAB_TYPE_GBN_VALID', hidden:true},
		{ index:'TAB_INFO_GBN_VALID', name:'TAB_INFO_GBN_VALID', label: 'TAB_INFO_GBN_VALID', hidden:true},
		{ index:'CLS_GBN_VALID', name:'CLS_GBN_VALID', label: 'CLS_GBN_VALID', hidden:true},
		{ index:'CLS_SUB_AREA_VALID', name:'CLS_SUB_AREA_VALID', label: 'CLS_SUB_AREA_VALID', hidden:true},
		{ index:'DB_NM_VALID', name:'DB_NM_VALID', label: 'DB_NM_VALID', hidden:true},
		{ index:'SCHEMA_NM_VALID', name:'SCHEMA_NM_VALID', label: 'SCHEMA_NM_VALID', hidden:true},
		{ index:'TAB_ENTITY_NM_VALID', name:'TAB_ENTITY_NM_VALID', label: 'TAB_ENTITY_NM_VALID', hidden:true},
		{ index:'TAB_NM_VALID', name:'TAB_NM_VALID', label: 'TAB_NM_VALID', hidden:true},
		{ index:'MNGR_VALID', name:'MNGR_VALID', label: 'MNGR_VALID', hidden:true},
		{ index:'DID_YN_VALID', name:'DID_YN_VALID', label: 'DID_YN_VALID', hidden:true},
		{ index:'DATA_KEEP_PERIOD_VALID', name:'DATA_KEEP_PERIOD_VALID', label: 'DATA_KEEP_PERIOD_VALID', hidden:true},
		{ index:'DATA_GEN_PERIOD_VALID', name:'DATA_GEN_PERIOD_VALID', label: 'DATA_GEN_PERIOD_VALID', hidden:true},
		{ index:'DATA_GEN_CNT_VALID', name:'DATA_GEN_CNT_VALID', label: 'DATA_GEN_CNT_VALID', hidden:true},
		{ index:'DESC_VALID', name:'DESC_VALID', label: 'DESC_VALID', hidden:true},
		
		{ index:'IS_VALID', name:'IS_VALID', label: 'IS_VALID', hidden:true},
		{ index:'INVALID_CNT', name:'INVALID_CNT', label: 'INVALID_CNT', hidden:true},
		{ index:'TAB_OBJ_ID', name:'TAB_OBJ_ID', label: 'TAB_OBJ_ID', hidden:true}
    ];

	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager1",
		toppager:false,
		loadonce: true,
		scroll:false,
		isPaging:false,
		autowidth:true,
		shrinkToFit:false,
		rownumbers:false,
		loadComplete: function() {
			var tooltip = function(rowid, rowData) {
				var msg = "";
				$.each(rowData, function(index, value){
					if ("DBMS_VALID" === index && value === "N") {
						msg += "DBMS, "
					} else if ("APPSYS_VALID" === index && value === "N") {
						msg += "시스템, "
					} else if ("TAB_TYPE_GBN_VALID" === index && value === "N") {
						msg += "테이블유형구분, "
					} else if ("TAB_INFO_GBN_VALID" === index && value === "N") {
						msg += "테이블정보구분, "
					} else if ("CLS_GBN_VALID" === index && value === "N") {
						msg += "업무구분, "
					} else if ("CLS_SUB_AREA_VALID" === index && value === "N") {
						msg += "업무별세부영역, "
					} else if ("DB_NM_VALID" === index && value === "N") {
						msg += "DB명, "
					} else if ("SCHEMA_NM_VALID" === index && value === "N") {
						msg += "스키마명, "
					} else if ("TAB_ENTITY_NM_VALID" === index && value === "N") {
						msg += "테이블논리명, "
					} else if ("TAB_NM_VALID" === index && value === "N") {
						msg += "테이블물리명, "
					} else if ("TAB_NM_VALID" === index && value === "N") {
						msg += "테이블물리명, "
					} else if ("DID_YN_VALID" === index && value === "N") {
						msg += "비식별여부, "
					}
				});
				if (msg !== "") {
					msg = msg.substring(0, msg.length-2);
					$('#'+rowid, "#jqGrid1").attr('title', "오류항목 : " + msg);
				}
			};
			
		    var ids = $(this).jqGrid('getDataIDs');
		    $.each(ids,function(idx,rowid) {
		    	var rowData = $("#jqGrid1").getRowData(rowid);
		    	$.each($.keys(rowData), function(index, value){
		    		if (value.endsWith("_VALID") && rowData[value] === "N") {		    			
		    			$("#jqGrid1").jqGrid('setCell', rowid, value.substring(0, value.length-6), "",{'background':'#e21b1b'});
		    		}
		    	});
		    	
		    	if(rowData["IS_VALID"] === "N"){
		    		$("#jqGrid1").jqGrid('setCell', rowid, "SEQ" , "",{'background':'#e21b1b'});
		    		tooltip(rowid, rowData);
		    	} else {
		    		$("#jqGrid1").jqGrid('setCell', rowid, "SEQ" , "",{'background':'#ffffff'});
				}
		    });
		}
	};
	
	var $grid1 = DE.jqgrid.render($("#jqGrid1"), opts);
	DE.jqgrid.navGrid($("#jqGrid1"), $("#jqGridPager1"), {});
		
	$("button#btnSave").on("click", function(e) {
		if (!$("#selDatabase").val()) {
			DE.box.alert("선택된 데이터베이스가 없습니다.");
			return;
		}
		if (uploadResult === null) {
			DE.box.alert("업로드 정보가 없습니다.");
			return;
		} else {
			var errCnt = uploadResult["DSGN_TAB_ERR"];
			if (errCnt !== 0) {
				DE.box.alert(errCnt + " 건의 오류가 있습니다.");
				return;
			}
		}
		
		var saveAction = function() {
			var postData = {
				"DSGN_TAB_DATA":$("#jqGrid1").jqGrid("getGridParam", "data")
			};
			var rsp = DE.ajax.call({"async":false, "url":"portal/dsgn/db?oper=importDsgnTab", "data":postData});
			if (rsp["status"] === "SUCCESS") {
				var dsgnTabImportCnt = rsp["data"]["DSGN_TAB_IMPORT_CNT"];
				DE.box.alert("설계 테이블 목록 "+dsgnTabImportCnt+"건 "+rsp["message"], function(){self.close();});
			} else {
				DE.box.alert(rsp["message"]);	
			}	
		};
		
		DE.box.confirm(DE.i18n.prop("common.message.save.confirm"), function (b) {
        	if (b === true) {
        		saveAction();
        	}
        });
	});

	$("#selAppSys").on("change", function(e){
		if(!$(this).val()) {
			$("#selDbInst option").remove();
			clearGridData();
			return;
		}
		makeDbInstSelect();
	});
	
	$("#selDbInst").on("change", function(e){
		if(!$(this).val()) {
			$("#selDatabase option").remove();
			clearGridData();
			return;
		}
		makeDatabaseSelect();
	});
	
	var clearGridData = function() {
		$("#jqGrid2").jqGrid("clearGridData");
		$("#jqGrid").jqGrid("clearGridData");
	};
	
	var makeAppSysSelect = function(e) {
		DE.ajax.call({async:true, url:"metapublic/list", data:{"queryId":"metapublic.findObjSelectBox", "objTypeId":appSysTypeId}}, function(rsp) {
			DE.util.selectBox($("#selAppSys"), rsp["data"], {"value":"objId", "text":"objNm", "isAll":false});
			$("#selAppSys").trigger("change");
		});
	};
	
	var makeDbInstSelect = function(e) {
		DE.ajax.call({async:true, url:"metapublic/list", data:{"queryId":"metapublic.findObjSelectBox", "objTypeId":dbInstTypeId, "pathObjTypeId":appSysTypeId, "pathObjId":$("#selAppSys").val()}}, function(rsp) {
			DE.util.selectBox($("#selDbInst"), rsp["data"], {"value":"objId", "text":"objNm", "isAll":false});
			$("#selDbInst").trigger("change");
		});
	};
	
	var makeDatabaseSelect = function(e) {
		DE.ajax.call({async:true, url:"metapublic/list", data:{"queryId":"metapublic.findObjSelectBox", "objTypeId":databaseTypeId, "pathObjTypeId":dbInstTypeId, "pathObjId":$("#selDbInst").val()}}, function(rsp) {
			DE.util.selectBox($("#selDatabase"), rsp["data"], {"value":"objId", "text":"objNm", "isAll":false});
			$("button#btnSearch").trigger("click");
		});
	};
	
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
		$("#selAppSys").select2();
		$("#selDbInst").select2();
		$("#selDatabase").select2();
		makeAppSysSelect();
	};
	init();
});