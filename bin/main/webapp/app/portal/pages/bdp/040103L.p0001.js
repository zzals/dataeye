$(document).ready(function() {
	//dsetTypeId, dsetId
	var reqParam = $("input#reqParam").data();
	var dsgnTabColTypeId = "040103L";
	
	var colArray = [
   		"컬럼 논리명", "컬럼 물리명", "컬럼 설명", "데이터타입", "길이", 
   		"소수점", "NOT NULL",  "PK 순번", "FK 여부", "분산키<br />순번", "Organized키<br />순번", 
   		"비식별<br />대상여부", "개인정보<br />항목", "ATR_ID_SEQ_101", "OBJ_TYPE_ID", "OBJ_ID"
   	];
	
	var requiredHeaderSetting = function() {
		var instance = $container.data("handsontable");
		for(var i=0; i<instance.countCols(); i++) {
  			if (instance.getCellMeta(0,i)["required"]) {  
  				$(".ht_clone_top.handsontable table thead tr th:eq("+(i+1)+") div", "#handsonTable").css("color", "#0000ff!ant");
  			} else {
  				$(".ht_clone_top.handsontable table thead tr th:eq("+(i+1)+") div", "#handsonTable").css("color", "#000000!ant");
  			}
  		}
	};
	
	var bmStdDmnDataType = [];
	var diItem = [];
	var ValidatorQueue = function() {  
		return [];
	};
	var objNmValidatorQueue = null;
	var columnSetting = function() {
		return [
			{data:'OBJ_NM', type:'text', required:true, allowInvalid:true},		//컬럼논리명
			{data:'OBJ_ABBR_NM', type:'text', required:true, renderer: customRenderer},		//컬럼물리명
			{data:'OBJ_DESC', type:'text', required:false, renderer: customRenderer},		//컬럼설명
			{data:'ATR_ID_SEQ_102', type:'text', renderer: customRenderer, editor: "chosen", chosenOptions: {data: bmStdDmnDataType}, required:true},			//데이터타입
			{data:'ATR_ID_SEQ_103', type:'numeric', allowInvalid:false},			//길이
			{data:'ATR_ID_SEQ_104', type:'numeric', allowInvalid:false},		//소수점
			{data:'ATR_ID_SEQ_105', type:'checkbox', renderer: customRenderer, checkedTemplate:"Y", uncheckedTemplate:"N", className:"htCenter", allowInvalid:true},			//NOT NULL
			{data:'ATR_ID_SEQ_107', type:'numeric', allowInvalid:false},		//PK 순번
			{data:'ATR_ID_SEQ_108', type:'checkbox', renderer: customRenderer, checkedTemplate:"Y", uncheckedTemplate:"N", className:"htCenter", allowInvalid:true},		//FK여부
			{data:'ATR_ID_SEQ_111', type:'numeric', allowInvalid:false},		//분산키순번
			{data:'ATR_ID_SEQ_115', type:'numeric', allowInvalid:false},	//Organized키순번
			{data:'ATR_ID_SEQ_112', type:'checkbox', renderer: customRenderer, checkedTemplate:"Y", uncheckedTemplate:"N", className:"htCenter", allowInvalid:true},	//비식별대상여부
			{data:'ATR_ID_SEQ_113', type:'text', renderer: customRenderer, editor: "chosen", chosenOptions: {data: diItem}, required:false},		//개인정보항목	
			{data:'ATR_ID_SEQ_101', type:'numeric', readOnly:true, className:"htHidden", copyable:false},	//순번
			{data:'OBJ_TYPE_ID', type:'text', readOnly:true, className:"htHidden", copyable:false},	
			{data:'OBJ_ID', type:'text', readOnly:true, className:"htHidden", copyable:false}
		];
	};
	
	var $container = $("div#handsonTable");
	$container.handsontable({
      	startRows: 1,
      	rowHeaders: true,
      	colHeaders: function (col) {
      		switch (col) {
            default:
            	return "<b>"+colArray[col]+"</b>";
      		}
    	},
    	colWidths:[
   	   		150, 150, 200, 100, 50, 50, 
   	   		80, 80, 80, 80, 80, 
   	   		100, 150, 0.1, 0.1, 0.1
   		],
      	rowHeights:function (row) {
			return 25;
      	},
      	width:1600,
      	minSpareRows: 1,
      	currentRowClassName: 'currentRow',
        currentColClassName: 'currentCol',
        manualColumnResize: true,
        manualColumnMove: false,
        manualRowResize: true,
        manualRowMove: false,
        columnSorting: false,
        contextMenu: {
            items:{
                'row_above':{name:'위로 삽입'}, 
                'row_below':{name:'아래 삽입'}, 
                'hsep1':"---------", 
                'remove_row':{name:'로 삭제'}, 
                'hsep2':"---------", 
                'undo':{name:'취소'}, 
                'redo':{name:'재 실행'}
            }
        },
        columns: columnSetting(),
      	beforeChange: function (changes, source) {
      		if (source != "edit") {
      			var instance = $container.data("handsontable");
          		$.each(changes, function(index, value){
          			if (instance.getCellMeta(1,instance.propToCol(value[1]))["type"] == "checkbox") {
          				if (value[3] != "Y") {
          					value[3] = "N";
          				}
          			} else if (instance.getCellMeta(1,instance.propToCol(value[1]))["type"] == "numeric") {
          				if (!$.isNumeric(value[3])) {
          					value[3] = null;
          				}
          			}
      				var chosenOptions = instance.getCellMeta(1,instance.propToCol(value[1]))["chosenOptions"];
      				if (chosenOptions != null) {
      					var isNotMatched = true;
      					$.each(chosenOptions["data"], function(idx2, val2) {
	      					if ((source == "autofill" && val2["id"] == value[3]) || val2["label"] == value[3]) {
	      						value[3] = val2["id"];
	      						isNotMatched = false;
	      						return false;
	      					}
	      				});
      					if (isNotMatched) {
      						value[3] = "";
      					}
      				}
	      		});
      		}
      	},
      	afterChange: function (changes, source) {
      		if ("loadData" == source) {
      		} else {
      			var instance = $("div#handsonTable").data("handsontable");
  				$.each(changes, function(index, value){
	      			if (reqParam["dataLayer"] === "L0" && changes[0][1] === "OBJ_NM") {
	      				cellMeta = instance.getCellMeta(changes[0][0], instance.propToCol("OBJ_NM"));
	      				if (changes[0][3] === "") {
	      					cellMeta.valid = false;
	      				} else {
	      					cellMeta.valid = true;
	      				}
	      				instance.render();
	      			} else if (changes[0][1] === "OBJ_NM" && (changes[0][2] != changes[0][3])) {
	      				cellMeta = instance.getCellMeta(changes[0][0], instance.propToCol("OBJ_NM"));
	      				cellMeta.valid = false;
	      				instance.render();
	      			}
	      		});
      		}
      	},
      	cell: [],
        cells: function (row, col, prop) {},
      	afterInit: function() {
      		requiredHeaderSetting();
      	}
	});
    
    function customRenderer(instance, td, row, col, prop, value, cellProperties) {
    	//required checked
    	if ((instance.countRows()-1) != row) {
    		if (cellProperties.type === "text") {
		    	if (cellProperties["required"] && (value == null || value == "")) {
		        	cellProperties["valid"] = false;
		        } else {
		        	cellProperties["valid"] = true;
		        }
		    	
		    	//selectbox checked
		        var optionsList = cellProperties.chosenOptions == undefined ? [] : cellProperties.chosenOptions.data;
		        if(typeof optionsList === "undefined" || typeof optionsList.length === "undefined" || !optionsList.length) {
		            Handsontable.TextCell.renderer(instance, td, row, col, prop, value, cellProperties);
		        } else {
			        var values = (value + "").split(",");
			        var value = [];
			        var isMatched = false;
			        for (var index = 0; index < optionsList.length; index++) {
			            if (values.indexOf(optionsList[index].id + "") > -1) {
			                value.push(optionsList[index].label);
			                isMatched = true;
			            }
			        }
			        if (cellProperties.prop === "ATR_ID_SEQ_113" && $.isEmptyObject(value)) {
			        	cellProperties["valid"] = true;
			        } else if (!isMatched) {
			        	cellProperties["valid"] = false;
			        }
			        value = value.join(", ");
			        Handsontable.TextCell.renderer(instance, td, row, col, prop, value, cellProperties);
		        }
    		} else if (cellProperties["type"] == "numeric") {
    			if (!$.isNumeric(value)) {
	    			cellProperties["valid"] = false;
	    		}
    			Handsontable.NumericCell.renderer(instance, td, row, col, prop, value, cellProperties);
    		} else if (cellProperties["type"] == "checkbox") {
    			if (value !== "Y") {
    				value = "N";
    			}
    			Handsontable.CheckboxCell.renderer(instance, td, row, col, prop, value, cellProperties);
    		}

            return td;
    	} else {
    		if (cellProperties.type === "text") {
	    	    Handsontable.TextCell.renderer(instance, td, row, col, prop, value, cellProperties);
    		} else if (cellProperties["type"] == "numeric") {
    			Handsontable.NumericCell.renderer(instance, td, row, col, prop, value, cellProperties);
    		} else if (cellProperties["type"] == "checkbox") {
    			Handsontable.CheckboxCell.renderer(instance, td, row, col, prop, value, cellProperties);
    		}
    		cellProperties["valid"] = true;
    		return td;
    	}
    }
    	
	var loadDsgnTabColInfo = function(dataLayer) {
		DE.ajax.call({
			"url":"metapublic/list", 
			"data":{
				"queryId":"bdp_custom.dsgnTabCol", 
				"dsgnTabTypeId":reqParam["dsgnTabTypeId"], 
				"dsgnTabId":reqParam["dsgnTabId"], 
				"dsgnTabColTypeId":dsgnTabColTypeId
			}
		}, 
		function(dsgnTabColInfo){
			var colModel = columnSetting();
			if (reqParam["dataLayer"] === "L0") {
			} else {
				$.each(colModel, function(index, value){
					if (value["data"] === "OBJ_ABBR_NM") { //컬럼물리명
						value["readOnly"] = true;
					} else if (value["data"] === "ATR_ID_SEQ_102") { //데이터타입
						value["readOnly"] = true;
					} else if (value["data"] === "ATR_ID_SEQ_103") { //길이
						value["readOnly"] = true;
					} else if (value["data"] === "ATR_ID_SEQ_104") { //소수점
						value["readOnly"] = true;
					}
				});
			}
			
			var instance = $container.data("handsontable");
			instance.updateSettings({
				"columns" : colModel,
				"data" : dsgnTabColInfo["data"]
			}, false);
		});
	};
	
    $("button#bntStdCheckAction").on("click", function(e){
    	var instance = $container.data("handsontable");
    	if (instance.countRows() === 1) return;
    	
    	var data = instance.getSourceData(0, 0, instance.countRows()-2, instance.countCols()-1);
	    var rsp = DE.ajax.call({
	    	"async":false,
	    	"url":"portal/bdp?oper=dsgnTabColStdCheck", 
	    	"data":{
	    		"dbmsType":reqParam["dbmsType"], 
	    		"data":data
	    	}
	    });
	    if (rsp["status"] === "SUCCESS") {
	    	instance.updateSettings({
				"data" : rsp["data"]
			}, false);
		    instance.render();
	    	
		    DE.box.alert(DE.i18n.prop("msg.tabcol.stdcheck.complete"));
	    }
    });
    
    $("button#bntBatchSaveAction").on("click", function(e) {
		var instance = $container.data("handsontable");
    	var invalidCnt = $('.htInvalid ').length;
    	if (invalidCnt) {
    		DE.box.alert("입력항목 오류 ["+invalidCnt+"건]");
			return;
    	}
    	
    	instance.validateCells(function(result) {
    		if(result) {
    			DE.box.confirm("저장 하시겠습니까?", function(e){
    				if (!e) return;
    				var data = [];
    				if (instance.countRows() !== 1) {
    					data = instance.getSourceData(0, 0, instance.countRows()-2, instance.countCols()-1);
    				}
    				
    				var rsp = DE.ajax.call({
    	        		"async":false,
    	        		"url":"portal/bdp?oper=dsgnTabColImport", 
	        			"data":{
	        				"dsgnTabTypeId":reqParam["dsgnTabTypeId"], 
	        				"dsgnTabId":reqParam["dsgnTabId"],
	        				"dsgnTabColTypeId":dsgnTabColTypeId,
	        				"data":data
	        			}
    	        	});
    	        	if (rsp["status"] === "SUCCESS") {
    	        		DE.box.alert(rsp["message"]);
    	        		loadDsgnTabColInfo();
    	        	}
    			});
    		}
    	});
    });
    
    $(window).resize(function(){
		var wrapperHeight = parseInt($(".box-body").css("height"));
		var instance = $container.data("handsontable");
		instance.updateSettings({height:4000});
	}).trigger("resize");
	
	var clearTable = function() {
		$container.data("handsontable").updateSettings({
		    data : []
		});
	};
	
	var init = function() {
		var rsp = DE.ajax.call({
			"async":false, 
			"url":"metapublic/map", 
			"data":{
				"queryId":"bdp_custom.dsgnTab", 
				"dsgnTabTypeId":reqParam["dsgnTabTypeId"], 
				"dsgnTabId":reqParam["dsgnTabId"]
			}}
		);

		$(".box-title").append(rsp["data"]["objNm"]);
		
		var dbmsType = rsp["data"]["dbmsType"];
		var dataLayer = rsp["data"]["dataLayer"];
		if (dbmsType === null || dbmsType === "") {
			DE.box.alert(DE.i18n.prop("msg.tabcol.undefined.databasetype"), function(){
				self.close();
				return;
			});
		} else {
			if (dataLayer === null || dataLayer === "") {
				DE.box.alert(DE.i18n.prop("msg.tabcol.undefined.datalayer"), function(){
					self.close();
					return;
				});
			} else {
				$("input#reqParam").data({
					"dbmsType":dbmsType,
					"dataLayer":dataLayer
				});
				if (dataLayer === "L0") {
					$("#bntStdCheckAction").remove();
				} else {
					$("#bntStdCheckAction").css("display", "inline-block");
				}
				
				$.each(DE.code.get("BM_STD_DMNDATATYPE_"+dbmsType), function(index, value){
					bmStdDmnDataType.push({"id":value["cdId"], "label":value["cdNm"]});
				});
				DE.ajax.call({"async":false, "url":"metapublic/list", "data":{"queryId":"metacore.getObjM", "objTypeId":"010402L"}}, function(diItemInfo){
					$.each(diItemInfo["data"], function(index, value){
						diItem.push({"id":value["objId"], "label":value["objNm"]});
					});
				});
				
				$(window).trigger("resize");
				loadDsgnTabColInfo(dataLayer);
			}
		}
	};
	init();
});