$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
		
	$("input#CD_GRP_ID").on("change", function(e) {
		$("input#CD_GRP_ID").attr("IS_VALID_CHECK", "N");
	});
	
	$("button#cdGrpIdDupCheck").on("click", function(e) {
		if ($("#CD_GRP_ID").val() === "") {
			DE.box.alert(DE.i18n.prop("msg.cdgrp.required.cdgrpid"), function(){
				$("[id=CD_GRP_ID]").focusin();
			});
			return;
		}
		
		var rsp = DE.ajax.call({"async":false, "url":"admin/code?oper=cdGrpIdDupCheck", "data":{"cdGrpId":$("#CD_GRP_ID").val()}});
		if (rsp["status"] === "SUCCESS") {
			DE.box.alert(rsp["message"]);
			$("input#CD_GRP_ID").attr("IS_VALID_CHECK", "Y");
		} else {
			DE.box.alert(rsp["message"]);	
		}
	});

	var makeForm = function() {
		var efctStDate = $("#EFCT_ST_DATE").data("DateTimePicker").date();
		if (efctStDate !== null) {
			efctStDate = efctStDate.format("YYYY-MM-DD");
		}
		var efctEdDate = $("#EFCT_ED_DATE").data("DateTimePicker").date();
		if (efctEdDate !== null) {
			efctEdDate = efctEdDate.format("YYYY-MM-DD");
		}
		
		return {
			"CD_GRP_ID":$("#CD_GRP_ID").val(),
			"CD_GRP_NM":$("#CD_GRP_NM").val(),
			"CD_GRP_DESC":$("#CD_GRP_DESC").val(),
			"DEL_YN":$("#DEL_YN").val(),
			"UP_CD_GRP_ID":$("#UP_CD_GRP_NM").data("upCdGrpId"),
			"EFCT_ST_DATE":efctStDate,
			"EFCT_ED_DATE":efctEdDate
		};
	};
	
	$("button#btnInsert").on("click", function(e) {
		var callback = {
			success : function(response) {
				DE.box.alert(response["message"], function(){ self.close();});
			},
			error : function(response) {
				DE.box.alert(response["responseJSON"]["message"]);
			}
		};
		
		if ($("#CD_GRP_ID").attr("is_valid_check") === "N") {
			DE.box.alert(DE.i18n.prop("msg.cdgrp.CD_GRP_ID.valid.check"));
			return;
		}
		var opts = {
			async:false,
			url : "admin/code?oper=regist&type=cdGrp",
			data : $.param(makeForm())
		};	
		
		DE.ajax.formSubmit(opts, callback.success, callback.error);
	});	

	$("button#btnUpdate").on("click", function(e) {
		var callback = {
			success : function(response) {
				DE.box.alert(response["message"], function(){ self.close();});
			},
			error : function(response) {
				DE.box.alert(response["responseJSON"]["message"]);
			}
		};
		
		if ($("#CD_GRP_ID").attr("is_valid_check") === "N") {
			DE.box.alert(DE.i18n.prop("msg.cdgrp.CD_GRP_ID.valid.check"));
			return;
		}
		var opts = {
			url : "admin/code?oper=update&type=cdGrp",
			data : $.param(makeForm())
		};	
		
		DE.ajax.formSubmit(opts, callback.success, callback.error);
	});	
	
	//상위그룹 선택  뷰
	$("button#btnUpCdGrpIdSelect").on("click", function(e){
		$("#jqGridModal").jqGrid("setGridParam", {"treedatatype":"json"});
		DE.jqgrid.reload($("#jqGridModal"), {
			"queryId":"code.getCdGrpTree",
			"delYn":"N"
		});
		$("#jqGridModal").jqGrid("setGridParam", {"treedatatype":"local"});
		
		$('#upgrp-selected-view').modal('toggle');
    	setTimeout( function() {
    		$(".modal-backdrop", "#upgrp-selected-view").addClass("modal-backdrop-fullscreen");
    	}, 0);
	});
    $("#upgrp-selected-view").on('show.bs.modal', function () {
    	
	});
	$("#upgrp-selected-view").on('hidden.bs.modal', function () {
		$(".modal-backdrop", "#upgrp-selected-view").addClass("modal-backdrop-fullscreen");
	});
	
	//상위코드그룹 추가
	$("button#btnUpCdGrpAdd").on("click", function(e){
		var $selRadio = $('input[name=radio_jqGridModal]:radio:checked');		
		if ($selRadio.length === 0) {
			DE.box.alert(DE.i18n.prop("common.message.selected.none"));
			return;
		}
		
		var rowid = $selRadio.closest("tr").prop("id");
		var rowData = $("#jqGridModal").jqGrid("getLocalRow", rowid);
		
		$("#UP_CD_GRP_NM").val(rowData["CD_GRP_NM"]);
		$("#UP_CD_GRP_NM").data({"upCdGrpId":rowData["CD_GRP_ID"]});
		$('#upgrp-selected-view').modal('toggle');
	});
	
	var makeDateTimePicker = function() {
		defaultOptions = {
            locale: "ko",
            viewMode: 'days',
            format: 'YYYY-MM-DD',
            extraFormats: ['YYYYMMDD']
        };
		
		$("#EFCT_ST_DATE").datetimepicker(defaultOptions);
		$("#EFCT_ED_DATE").datetimepicker(defaultOptions);
		
		$("#EFCT_ST_DATE").on("dp.change", function (e) {
			$("#EFCT_ED_DATE").data("DateTimePicker").minDate(e.date);
        });
		$("#EFCT_ED_DATE").on("dp.change", function (e) {
			$("#EFCT_ST_DATE").data("DateTimePicker").maxDate(e.date);
        });	
	};
	
	var upCdGrpGridInit = function() {
		var colModel = [
    	    {name:'CHK',index:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},
    		{ index:'CD_GRP_ID', name: 'CD_GRP_ID', label: '코드그룹ID', width: 250, align:'left', "key":true},
          	{ index:'CD_GRP_NM', name: 'CD_GRP_NM', label: '코드그룹명', width: 250, align:'left'}
        ];
    	
    	var opts = {
    		"colModel":colModel, 
    		pager:"#jqGridPagerModal",

    		loadonce: true,
    		isPaging:false,
    		sortable: false,
    		cmTemplate: { sortable: false },
    		isResize:false,
    		height:400,
    		autowidth:true,
    		shrinkToFit: true,
    		rownumbers: false,
    		treeGridModel: 'adjacency',//adjacency, nested
            treeReader : {
                level_field: "LVL",
                parent_id_field: "UP_CD_GRP_ID",
                leaf_field: "IS_LEAF",
                expanded_field: "IS_EXPENDED"
            },
            treeGrid: true,
            ExpandColumn : 'CD_GRP_ID',
    		beforeSelectRow: function (rowid, e) {
            	var $radio = $('input[type=radio]', $(e.target).closest('tr'));
                $radio.prop('checked', 'checked');
                return true;
            }
    	};
    	
    	var navOpts = {
    		navOptions:{
    			add:false,
    			del:false,
    			edit:false,
    			refresh:false,
    			search:false,
    			view:false,
    			cloneToTop:false,
    		}
    	};
    	
    	var $grid = DE.jqgrid.render($("#jqGridModal"), opts);
    	DE.jqgrid.navGrid($("#jqGridModal"), $("#jqGridPagerModal"), navOpts);
    	$("#jqGridModal").jqGrid(
			'filterToolbar',{
				stringResult: true, 
				groupOp:'AND',
				searchOnEnter: true, 
				defaultSearch: 'cn'
			}
		); 
	};
	
	var init = function() {
		makeDateTimePicker();
		upCdGrpGridInit();
		
		if (reqParam["mode"] === "C") {
			$(".popup_title").html("코드그룹 등록");
			$("button#btnInsert").css("display", "inline-block");
			$("button#btnUpdate").remove();
			$("button#btnRemove").remove();			
		} else if (reqParam["mode"] === "R" || reqParam["mode"] === "U") {
			$(".popup_title").html("코드그룹 수정");
			$("button#btnInsert").remove();
			$("button#btnRemove").remove();
			if (reqParam["mode"] === "R") {
				$("button#btnUpdate").remove();
				$("input, select, textarea, button[id=cdGrpIdDupCheck]", "[de-role=form]").prop( "disabled", true );
			} else {
				$("input[id=CD_GRP_ID], button[id=cdGrpIdDupCheck]", "[de-role=form]").prop( "disabled", true );
				$("button#btnUpdate").css("display", "inline-block");
			}
			
			DE.ajax.call({"url":"admin/code?oper=get&type=cdGrp", "data":{"cdGrpId":reqParam["cdGrpId"]}}, function(rsp){
				$("#CD_GRP_ID").val(rsp["data"]["cdGrpId"]);
				$("#CD_GRP_NM").val(rsp["data"]["cdGrpNm"]);
				$("#CD_GRP_DESC").val(rsp["data"]["cdGrpDesc"]);
				$("#DEL_YN").val(rsp["data"]["delYn"]);
				$("#UP_CD_GRP_NM").val(rsp["data"]["upCdGrpNm"]);
				$("#UP_CD_GRP_NM").data({"upCdGrpId":rsp["data"]["upCdGrpId"]});
				
				var efctStDate = rsp["data"]["efctStDate"];
				if(efctStDate !== undefined) {
					$("#EFCT_ST_DATE").data("DateTimePicker").date(efctStDate.substring(0,10));
				}
				var efctEdDate = rsp["data"]["efctEdDate"];
				if(efctEdDate !== undefined) {
					$("#EFCT_ED_DATE").data("DateTimePicker").date(efctEdDate.substring(0,10));
				}
			});
		}
	};
	init();
});