$(document).ready(function() {
	//dsetTypeId, dsetId
	var reqParam = $("input#reqParam").data();
	var dsetAtrTypeId = "010208L";
	
	var colArray = [
   		"속성명", "컬럼명", "설명", "UNIQUE ID<br />대상", "데이터타입", 
   		"비식별<br />대상여부", "개인정보 항목",  "NOT NULL", "Lookup Table", "Lookup Key", 
   		"Lookup Type", "Lookup Value", "OBJ_TYPE_ID", "OBJ_ID", "IS_STDVALID"
   	];
	
	var requiredHeaderSetting = function() {
		var instance = $container.data("handsontable");
		for(var i=0; i<instance.countCols(); i++) {
  			if (instance.getCellMeta(0,i)["required"]) {  
  				$(".ht_clone_top.handsontable table thead tr th:eq("+(i+1)+") div", "#handsonTable").css("color", "#0000ff!important");
  			} else {
  				$(".ht_clone_top.handsontable table thead tr th:eq("+(i+1)+") div", "#handsonTable").css("color", "#000000!important");
  			}
  		}
	};
	
	var bmDsetColDatatype = [];
	var diItem = [];
	var columnSetting = function() {
		return [
			{data:'OBJ_NM', type:'text', required:true, renderer: customRenderer},		//속성명
			{data:'OBJ_ABBR_NM', type:'text', required:true, renderer: customRenderer},		//컬럼명	
			{data:'OBJ_DESC', type:'text', required:false, renderer: customRenderer},		//컬럼명	
			{data:'ATR_ID_SEQ_102', type:'checkbox', renderer: customRenderer, checkedTemplate:"Y", uncheckedTemplate:"N", className:"htCenter", allowInvalid:true},			//UNIQUE ID 대상
			{data:'ATR_ID_SEQ_103', type:'text', renderer: customRenderer, editor: "chosen", chosenOptions: {data: bmDsetColDatatype}, required:true},			//데이터타입
			{data:'ATR_ID_SEQ_104', type:'checkbox', renderer: customRenderer, checkedTemplate:"Y", uncheckedTemplate:"N", className:"htCenter", allowInvalid:true},		//비식별 대상여부
			{data:'ATR_ID_SEQ_105', type:'text', renderer: customRenderer, editor: "chosen", chosenOptions: {data: diItem}, required:false, allowInvalid:true},			//개인정보 함목
			{data:'ATR_ID_SEQ_106', type:'checkbox', renderer: customRenderer, checkedTemplate:"Y", uncheckedTemplate:"N", className:"htCenter", allowInvalid:true},		//NOT NULL
			{data:'ATR_ID_SEQ_107', type:'text'},		//Lookup Table
			{data:'ATR_ID_SEQ_108', type:'text'},		//Lookup Key
			{data:'ATR_ID_SEQ_109', type:'text'},	//Lookup Type
			{data:'ATR_ID_SEQ_110', type:'text'},		//Lookup Value
			{data:'ATR_ID_SEQ_101', type:'text', readOnly:true, className:"htHidden" , copyable:false},	//속성순서
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
   	   		150, 150, 200, 80, 100, 80, 
   	   		100, 80, 150, 150, 150, 
   	   		150, 0.1, 0.1, 0.1
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
			        if (cellProperties.prop === "ATR_ID_SEQ_105" && $.isEmptyObject(value)) {
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
    	
	var loadAtrInfo = function() {
		DE.ajax.call({
			"url":"metapublic/list", 
			"data":{
				"queryId":"bdp_custom.datasetAttr", 
				"dsetTypeId":reqParam["dsetTypeId"], 
				"dsetId":reqParam["dsetId"], 
				"dsetAtrTypeId":dsetAtrTypeId
			}
		}, 
		function(atrinfo){
			$container.data("handsontable").updateSettings({
				"columns" : columnSetting(),
				"data" : atrinfo["data"]
			}, true);
		});
	};
	
    $("button#bntBatchSaveAction").on("click", function(e) {
		var instance = $container.data("handsontable");
    	var invalidCnt = $('.htInvalid ').length;
    	if (invalidCnt) {
			alert("입력항목 오류 ["+invalidCnt+"건]");
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
    				
    				var transdata = [];
    				$.each(data, function(index, value){
    					value = $.extend({
    	    				ATR_ID_SEQ_101:null,
    	    				ATR_ID_SEQ_102:null,
    	    				ATR_ID_SEQ_103:null,
    	    				ATR_ID_SEQ_104:null,
    	    				ATR_ID_SEQ_105:null,
    	    				ATR_ID_SEQ_106:null,
    	    				ATR_ID_SEQ_107:null,
    	    				ATR_ID_SEQ_108:null,
    	    				ATR_ID_SEQ_109:null,
    	    				ATR_ID_SEQ_110:null,
    	    				OBJ_ABBR_NM:null,
    	    				OBJ_DESC:null,
    	    				OBJ_ID:null,
    	    				OBJ_NM:null,
    	    				OBJ_TYPE_ID:null
        				}, value);
    					transdata.push(value);
    				});
    				var rsp = DE.ajax.call({
    	        		"async":false,
    	        		"url":"portal/bdp?oper=dsetAtrImport", 
	        			"data":{
	        				"dsetTypeId":reqParam["dsetTypeId"], 
	        				"dsetId":reqParam["dsetId"],
	        				"dsetAtrTypeId":dsetAtrTypeId,
	        				"data":transdata
	        			}
    	        	});
    	        	if (rsp["status"] === "SUCCESS") {
    	        		DE.box.alert(rsp["message"]);
    	        		loadAtrInfo();
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
		DE.ajax.call({
			"async":false, 
			"url":"metapublic/map", 
			"data":{
				"queryId":"metacore.getObjM", 
				"objTypeId":reqParam["dsetTypeId"], 
				"objId":reqParam["dsetId"]}
			}, 
			function(objInfo){
				$(".box-title").append(objInfo["data"]["objNm"]);
			}
		);
		
		
		$.each(DE.code.get("BM_DSET_COL_DATATYPE"), function(index, value){
			bmDsetColDatatype.push({"id":value["cdId"], "label":value["cdNm"]});
		});
		
		DE.ajax.call({"async":false, "url":"metapublic/list", "data":{"queryId":"metacore.getObjM", "objTypeId":"010402L"}}, function(diItemInfo){
			$.each(diItemInfo["data"], function(index, value){
				diItem.push({"id":value["objId"], "label":value["objNm"]});
			});
		});
		
		
		$(window).trigger("resize");
		loadAtrInfo();
	};
	init();
});