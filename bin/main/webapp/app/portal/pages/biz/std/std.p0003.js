$(document).ready(function (){
	var reqParam = $("#reqParam").data();
	postData = {};
	
	if (reqParam["popup_title"] != undefined) {
		$(".popup_title").text(reqParam["popup_title"]);
	}
	
   	var gridReadOnly = false;
   	var readonlyCellRenderer = function (instance, td, row, col, prop, value, cellProperties) {
        Handsontable.TextCell.renderer.apply(this, arguments);
        $(td).css("background-color", "#EFEFEF !important");
        if ("VRF_RSLT" == prop) {
        	if ("Y" == value) {
        		$(td).css("color", "##777 !important");
        	} else {
        		$(td).css("color", "#ff0000 !important");
        	}
        }
    };
    $container = $("div#auto-word-list");    
    $container.handsontable({
      	startRows: 1,
      	startCols: 8,
      	rowHeaders: true,
      	colHeaders: function (col) {
            switch (col) {
            case 0:
                var txt = "<input type='checkbox' class='allChk' ";
                txt += isChecked() ? 'checked="checked"' : '';
                txt += ">";
                return txt;
            case 1:
                return "<b>표준단어명</b>";
            case 2:
                return "<b>영문약어명</b>";
            case 3:
                return "<b>영문정식명</b>";
            case 4:
                return "<b>분류어여부</b>";
            case 5:
                return "<b>금지문자포함</b>";
            case 6:
                return "<b>중복여부</b>";
            case 7:
                return "<b>검증결과</b>";
        	}
    	},
      	colWidths:[50, 200, 200, 150, 70, 100, 70, 70],
      	minSpareRows: 1,
      	currentRowClassName: 'currentRow',
        currentColClassName: 'currentCol',
        manualColumnResize:true,
        columnSorting:true,
        data:[{CHK:null}],
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
      	columns: [
			{data:'CHK', type: 'checkbox',renderer:checkboxRenderer},
			{data:'WORD_NM', type:'text'},
			{data:'WORD_ABBR_NM', type:'text'},
			{data:'WORD_ENG_NM', type:'text'},
			{
                data:'CLASS_YN', 
                editor: 'select',
                selectOptions:function(row, col) {
                    return ["Y", "N"];
                },
                strict: true,
                allowInvalid: false,
                validator: function(value, callback) {
                    if (value == "Y" || value == "N") {
                        callback(true);
                    } else {
                        callback(false);
                    }
                },
                type:'text'
            },
            {data:'PHB_CHAR_YN',readOnly: true, type:'text', renderer:readonlyCellRenderer},
			{data:'DUPL_YN',readOnly: true, type:'text', renderer:readonlyCellRenderer},
            {data:'VRF_RSLT',readOnly: true, type:'text', renderer:readonlyCellRenderer}   
		],
      	beforeChange: function (changes, source) {},
      	afterChange: function (change, source) {},
      	cells: function (row, col, prop) {
      	    if (col == 0) {
      	    } else if (col < 5) {
      			return {readOnly: gridReadOnly};
      		} else {
      			return {readOnly: true};
      		}
      	}
	});
    
    $container.on('mouseup', 'input.allChk', function (event) {
        var current = !$('input.allChk').is(':checked');
        var data = $container.data("handsontable").getData();
        
        for (var i = 0, len = data.length-1; i < len; i++) {
            data[i].CHK = current;
        }
        $container.handsontable('render');
    });
    
    function isChecked() {
        var data = $container.data("handsontable").getData();
        for (var i = 0, len = data.length-1; i < len; i++) {
            if (!data[i].CHK) {
                return false;
            }
        }
        return true;
    }
    
    $("a#wordVerifyChkAction").on("click", function(e) {
    	if (gridReadOnly) {
    		gridReadOnly = false;
    		$container.data("handsontable").render();
    		$("a#wordVerifyChkAction").html('단어검증');
    		return;
    	}
    	if ($container.data("handsontable").countRows() == 1) {
    		return;
    	}
    	data = $container.data("handsontable").getData();
        data = data.slice(0, data.length-1);
        
        var result = DE.ajax.call({async:false, url:DE.contextPath + "/portal/biz/std/bulkWordVerify", data:{'data':data}});
        
    	
    	$container.data("handsontable").loadData(result.data);
    	$("a#wordVerifyChkAction").html('편집');
    	gridReadOnly = true;
    });
    
    $("a#batchSaveAction").on("click", function(e) {
        if (!gridReadOnly) {
            alert("단어검증을 수행하세요.");
            return;
        }
        if ($("input:checkbox.htCheckboxRendererInput:checked").length == 0) {
            alert("등록 대상이 없습니다.");
            return;
        }
       	if (confirm("등록하시겠습니까?")) {
       	    data = data = $container.data("handsontable").getData();
            data = data.slice(0, data.length-1);
	    	
	    	 var result = DE.ajax.call({async:false, url:DE.contextPath + "/portal/biz/std/bulkWordImport", data:{'data':data}});
	         
	    	
	    	var totalCnt = result.data.result.targetCnt;
            var registCnt = result.data.result.registCnt;
            var errorCnt = result.data.result.errorCnt;
            
            alert("등록 되었습니다.\n전체건수:"+totalCnt+", 등록건수:"+registCnt+", 오류건수:"+errorCnt);
            $container.data("handsontable").loadData(result.data.data);
	    	
	    	gridReadOnly = false;
	    	$("a#wordVerifyChkAction").html('<span class="fontawesome_Btn fa-edit"></span>단어검증');
        }
    });
    
	$(this).off("autoResize").on( "autoResize", function( event ) {
	    setTimeout(function () {
	    	$container.css("height", $(".content-body").height());
    	}, 500);
	}).trigger("autoResize");
});
function checkboxRenderer(instance, TD, row, col, prop, value, cellProperties){
	TD.style['text-align'] = 'center'; // !!Center align here!!
	Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
}