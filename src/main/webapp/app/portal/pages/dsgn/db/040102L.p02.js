$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
	$(".popup_Title_Area > div.popup_title").html("테이블정의서 업로드");
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
        url: DE.contextPath+'/portal/dsgn/db?oper=uploadDsgnCol',
        dataType: 'json',
        done: function (e, data) {
        	$("#jqGrid1").jqGrid("clearGridData", true).jqGrid("setGridParam", {"datatype":"local", "data":data.result["DSGN_COL"]}).trigger("reloadGrid");
        	
        	uploadResult = data.result["UPLOAD_RESULT"];
        	$("#uploadMessage").text(uploadResult["DSGN_COL_ERR"]+" 건의 오류가 발생하였습니다.");
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
		{ index:'TAB_ENTITY_NM', name:'TAB_ENTITY_NM', label: '테이블논리명', width: 100, align:'left'},
		{ index:'TAB_NM', name:'TAB_NM', label: '테이블물리명', width: 120, align:'left'},
		{ index:'COL_ATR_NM', name:'COL_ATR_NM', label: '논리컬럼명', width: 120, align:'left'},
		{ index:'COL_NM', name:'COL_NM', label: '물리컬럼명', width: 120, align:'left'},
		{ index:'DATA_TYPE', name:'DATA_TYPE', label: '데이터타입', width: 120, align:'left'},
		{ index:'DATA_LEN', name:'DATA_LEN', label: '길이', width: 120, align:'left'},
		{ index:'DATA_SCALE', name:'DATA_SCALE', label: '소수점', width: 100, align:'left'},
		{ index:'NOT_NULL_YN', name:'NOT_NULL_YN', label: 'NOT NULL 여부', width: 100, align:'left'},
		{ index:'PK_ORD', name:'PK_ORD', label: 'PK순번', width: 100, align:'left'},
		{ index:'FK_YN', name:'FK_YN', label: 'FK여부', width: 100, align:'left'},
		{ index:'DISTRIB_KEY_ORD', name:'DISTRIB_KEY_ORD', label: '분산키순번', width: 150, align:'left'},
		{ index:'DID_YN', name:'DID_YN', label: '비식별대상여부', width: 120, align:'left'},
		{ index:'DESC', name:'DESC', label: '설명', width: 100, align:'left'},
		
		{ index:'TAB_OBJ_ID', name:'TAB_OBJ_ID', label: 'TAB_OBJ_ID', hidden:true},
		{ index:'COL_ORD', name:'COL_ORD', label: 'COL_ORD', hidden:true},
		
		{ index:'SEQ_VALID', name:'SEQ_VALID', label: 'SEQ_VALID', hidden:true},
		{ index:'TAB_ENTITY_NM_VALID', name:'TAB_ENTITY_NM_VALID', label: 'TAB_ENTITY_NM_VALID', hidden:true},
		{ index:'TAB_NM_VALID', name:'TAB_NM_VALID', label: 'TAB_NM_VALID', hidden:true},
		{ index:'COL_ATR_NM_VALID', name:'COL_ATR_NM_VALID', label: 'COL_ATR_NM_VALID', hidden:true},
		{ index:'COL_NM_VALID', name:'COL_NM_VALID', label: 'COL_NM_VALID', hidden:true},
		{ index:'DATA_TYPE_VALID', name:'DATA_TYPE_VALID', label: 'DATA_TYPE_VALID', hidden:true},
		{ index:'DATA_LEN_VALID', name:'DATA_LEN_VALID', label: 'DATA_LEN_VALID', hidden:true},
		{ index:'DATA_SCALE_VALID', name:'DATA_SCALE_VALID', label: 'DATA_SCALE_VALID', hidden:true},
		{ index:'NOT_NULL_VALID', name:'NOT_NULL_VALID', label: 'NOT_NULL_VALID', hidden:true},
		{ index:'PK_ORD_VALID', name:'PK_ORD_VALID', label: 'PK_ORD_VALID', hidden:true},
		{ index:'FK_YN_VALID', name:'FK_YN_VALID', label: 'FK_YN_VALID', hidden:true},
		{ index:'DISTRIB_KEY_ORD_VALID', name:'DISTRIB_KEY_ORD_VALID', label: 'DISTRIB_KEY_ORD_VALID', hidden:true},
		{ index:'DID_YN_VALID', name:'DID_YN_VALID', label: 'DID_YN_VALID', hidden:true},
		{ index:'DESC_VALID', name:'DESC_VALID', label: 'DESC_VALID', hidden:true},
		
		{ index:'IS_VALID', name:'IS_VALID', label: 'IS_VALID', hidden:true},
		{ index:'INVALID_CNT', name:'INVALID_CNT', label: 'INVALID_CNT', hidden:true}
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
					if ("TAB_ENTITY_NM_VALID" === index && value === "N") {
						msg += "테이블논리명, "
					} else if ("TAB_NM_VALID" === index && value === "N") {
						msg += "테이블물리명, "
					} else if ("COL_ATR_NM_VALID" === index && value === "N") {
						msg += "논리컬럼명, "
					} else if ("COL_NM_VALID" === index && value === "N") {
						msg += "물리컬럼명, "
					} else if ("DATA_TYPE_VALID" === index && value === "N") {
						msg += "데이터타입, "
					} else if ("DATA_LEN_VALID" === index && value === "N") {
						msg += "길이, "
					} else if ("DATA_SCALE_VALID" === index && value === "N") {
						msg += "소수점, "
					} else if ("NOT_NULL_VALID" === index && value === "N") {
						msg += "NOT NULL 여부, "
					} else if ("PK_ORD_VALID" === index && value === "N") {
						msg += "PK순번, "
					} else if ("FK_YN_VALID" === index && value === "N") {
						msg += "FK여부, "
					} else if ("DISTRIB_KEY_ORD_VALID" === index && value === "N") {
						msg += "분산키순번, "
					} else if ("DID_YN_VALID" === index && value === "N") {
						msg += "비식별대상여부, "
					}
				});
				if (msg !== "") {
					msg = msg.substring(0, msg.length-2);
					$('#'+rowid, "#jqGrid1").attr('title', "오류항목 : " + msg);
				}
			};
			
		    var ids = $(this).jqGrid('getDataIDs');
		    $.each(ids,function(idx,rowid) {
		    	rowData=$("#jqGrid1").getRowData(rowid);
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
			var errCnt = uploadResult["DSGN_COL_ERR"];
			if (errCnt !== 0) {
				DE.box.alert(errCnt + " 건의 오류가 있습니다.");
				return;
			}
		}
		
		var saveAction = function() {
			var postData = {
				"DSGN_COL_DATA":$("#jqGrid1").jqGrid("getGridParam", "data")
			};
			var rsp = DE.ajax.call({"async":false, "url":"portal/dsgn/db?oper=importDsgnCol", "data":postData});
			if (rsp["status"] === "SUCCESS") {
				var dsgnTabImportCnt = rsp["data"]["DSGN_COL_IMPORT_CNT"];
				DE.box.alert("설계 테이블 정의서 "+dsgnTabImportCnt+"건 "+rsp["message"], function(){self.close();});
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