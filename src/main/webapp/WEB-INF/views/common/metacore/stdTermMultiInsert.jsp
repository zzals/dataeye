<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><spring:message code="app.title" text="DateEye" /></title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<c:import url="/deresources/commonlib/popup_css" />
	<c:import url="/deresources/commonlib/js" />
  
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.handsontable/jquery.handsontable.css">
	<script src="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.handsontable/jquery.handsontable.full.js"></script>
	<style type="text/css">
	.htCheckboxRendererInput {
		vertical-align: middle;
		margin-left: 14px;
	}
	</style>
    
</head>
<body>
<!-- UI Object -->
<div id="container" de-tag="object-info-pop">
    <!-- HEADER -->
    <div id="header">
        <div class="popup_Title_Area">
            <div class="popup_title">용어 일괄등록</div>
        </div>
    </div>
    <!-- //HEADER -->
    <section class="content">    
    <nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="collapse navbar-collapse">
			<div id="actionDiv" class="navbar-form navbar-right de-obj-btn">
				<a id="termVerifyChkAction" class="btn btn-default btn-sm"  role="button" href="#"><i class="fa fa-floppy-o"></i> 용어검증</a>
				<a id="batchSaveAction" class="btn btn-default btn-sm"  role="button" href="#"><i class="fa fa-floppy-o"></i> 일괄등록</a>
			</div>
		</div>
	</div>
</nav>
     <div class="row">
	<div class="col-xs-12">
			<div id="auto-term-list" role="grid-area" style="height: 500px; overflow: scroll;"></div>
	</div>
	</div>
         
    <span class="pop_close"><button type="button" class="close" onclick="window.close()">x</button></span>
</div>	    
<script>
$(document).ready(function (){
	request = JSON.parse('${data}');
	postData = {};
	
	if (request["popup_title"] != undefined) {
		$(".popup_title").text(request["popup_title"]);
	}
	if(request.cAuth=="Y") {
		$("#batchSaveAction").show();
	} else {
		$("#batchSaveAction").hide();
	}

	
	
	var engTerm_validator = function (value, callback) {
		console.log(value);
		var a = eval("engTermList.row_"+this.row);
    	if (a == undefined) return callback(false);
    	var len = value.length;
    	var maxLen = 30;
		for(var i=0; i<a.length; i++) {
			if (a[i].ENG_TERM == value) {
				$container.handsontable('setDataAtCell', this.row, COL_POS.CLASS_YN, a[i].CLASS_WORD_ID==""?"N":"Y");
				$container.handsontable('setDataAtCell', this.row, COL_POS.KOR_CB_NM, a[i].KOR_TERM);
				$container.handsontable('setDataAtCell', this.row, COL_POS.TERM_LEN, len);
				
				if (a[i].VALID && len <= maxLen) {
					callback(true);
					
					var YYN = $container.handsontable('getDataAtCell', this.row, COL_POS.CLASS_YN)+$container.handsontable('getDataAtCell', this.row, COL_POS.DMN_YN)+$container.handsontable('getDataAtCell', this.row, COL_POS.DUPL_YN);
					if (YYN == "YYN") {
						$container.handsontable('setDataAtCell', this.row, COL_POS.VRF_RSLT, "Y");
					}
				} else {
					callback(false);
					$container.handsontable('setDataAtCell', this.row, COL_POS.VRF_RSLT, "N");
				}
				return;
			}
		}
		$container.handsontable('setDataAtCell', this.row, COL_POS.CLASS_YN, "N");
		$container.handsontable('setDataAtCell', this.row, COL_POS.KOR_CB_NM, "");
		$container.handsontable('setDataAtCell', this.row, COL_POS.TERM_LEN, len);
		$container.handsontable('setDataAtCell', this.row, COL_POS.VRF_RSLT, "N");
   		callback(false);
   	};
   	
   	COL_POS = {"TERM_CHECK":0, "KOR_TERM_NM":1, "DMN_NM":2, "KOR_CB_NM":3, "ENG_TERM_NM":4, "TERM_CNT":5, "TERM_LEN":6, "CLASS_YN":7, "DMN_YN":8, "DUPL_YN":9, "VRF_RSLT":10};
   	engTermList = {};
   	gridReadOnly = false;
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
   	$container = $("div#auto-term-list");    
    $container.handsontable({
      	startRows: 1,
      	startCols: 10,
      	rowHeaders: true,
      	colHeaders: function (col) {
            switch (col) {
            case COL_POS.TERM_CHECK:
                var txt = "<input type='checkbox' class='allChk' ";
                txt += isChecked() ? 'checked="checked"' : '';
                txt += ">";
                return txt;
            case COL_POS.KOR_TERM_NM:
                return "<b>표준용어명</b>";
            case COL_POS.DMN_NM:
                return "<b>표준도메인명</b>";
            case COL_POS.KOR_CB_NM:
                return "<b>한글조합</b>";
            case COL_POS.ENG_TERM_NM:
                return "<b>영문약어명</b>";
            case COL_POS.TERM_LEN:
                return "<b>영문약어 길이</b>";
            case COL_POS.TERM_CNT:
                return "<b>조합건수</b>";
            case COL_POS.DMN_YN:
                return "<b>도메인검증</b>";
            case COL_POS.CLASS_YN:
                return "<b>분류어검증</b>";
            case COL_POS.DUPL_YN:
                return "<b>중복여부</b>";
            case COL_POS.VRF_RSLT:
                return "<b>검증결과</b>";
        	}
    	},
      	colWidths:[50, 200, 150, 200, 200, 70, 70, 70, 70, 70, 70],
      	minSpareRows: 1,
      	fillHandle:false,
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
            {data:'KOR_TERM_NM', type:'text'},
            {data:'DMN_NM', type:'text'},
            {data:'KOR_CB_NM', readOnly: true, type:'text', renderer:readonlyCellRenderer},
			{
                data:'ENG_TERM_NM', 
                editor: 'select',
                selectOptions:function(row, col) {
                	var a = eval("engTermList.row_"+row);
                	if (a == undefined) return [];
                	var r = [];
                	for(var i=0; i<a.length; i++) {
                		if (a[i].VALID) {
                			r.push(a[i].ENG_TERM);
                		} else {
                			r.push(a[i].ENG_TERM+"_???");
                		}
                	}
                	return r;
                },
                strict: true,
                allowInvalid: true,
                validator: engTerm_validator,
                type:'text', 
                renderer:readonlyCellRenderer
			},
			{data:'TERM_CNT',readOnly: true, type:'numeric', renderer:readonlyCellRenderer},
			{data:'TERM_LEN',readOnly: true, type:'numeric', renderer:readonlyCellRenderer},
			{data:'CLASS_YN',readOnly: true, type:'text', renderer:readonlyCellRenderer},
			{data:'DMN_YN',readOnly: true, type:'text', renderer:readonlyCellRenderer},
			{data:'DUPL_YN',readOnly: true, type:'text', renderer:readonlyCellRenderer},
            {data:'VRF_RSLT',readOnly: true, type:'text', renderer:readonlyCellRenderer}
		],
		beforeChange: function (changes, source) {
     		for(var i=0; i<changes.length; i++) {
      			if (changes[i][1] == COL_POS.KOR_TERM_NM || changes[i][1] == COL_POS.DMN_NM) {
      				if (changes[i][2] != changes[i][3]) {
      					gridReadOnly = false;
      					break;
      				}
      			}
      		}
      	},
      	afterChange: function (change, source) {},
      	cell: [],
        cells: function (row, col, prop) {
      		if (col == COL_POS.KOR_TERM_NM || col == COL_POS.DMN_NM) {
      			return {readOnly: gridReadOnly};
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
    
    $("a#termVerifyChkAction").on("click", function(e) {
        if (gridReadOnly) {
    		gridReadOnly = false;
    		$container.data("handsontable").render();
    		$("a#termVerifyChkAction").html('<span class="fontawesome_Btn fa-edit"></span>용어검증');
    		return;
    	}
    	if ($container.data("handsontable").countRows() == 1) {
    		return;
    	}
    	data = $container.data("handsontable").getData();
    	data = data.slice(0, data.length-1);
    	
    	var result = DE.ajax.call({async:false, url:DE.contextPath + "/portal/biz/std/bulkTermVerify", data:{'data':data}});
    	
    	
    	$container.data("handsontable").loadData(result.data.grid);
    	engTermList = result.data.select;
    	$("a#termVerifyChkAction").html('<span class="fontawesome_Btn fa-edit"></span>편집');
    	gridReadOnly = true;
    });
    
    $("a#batchSaveAction").on("click", function(e) {
        if (!gridReadOnly) {
            alert("용어검증을 수행하세요.");
            return;
        }
        if ($("input:checkbox.htCheckboxRendererInput:checked").length == 0) {
            alert("등록 대상이 없습니다.");
    		return;
    	}
        if (confirm("등록하시겠습니까?")) {
	    	var data = $container.data("handsontable").getData();
	    	data = data.slice(0, data.length-1);
	    	for(var i=0; i<data.length; i++) {
                if (data[i].VRF_RSLT == "Y") {
                    var a = eval("engTermList.row_"+i);
                    if (a == undefined) continue;
                    
                    for(var j=0; j<a.length; j++) {
                        if (a[j].ENG_TERM == data[i].ENG_TERM_NM) {
                            data[i].TERM_OBJECT = a[j];
                            break;
                        }
                    }
                }
            }
	    	
	    	var result = DE.ajax.call({async:false, url:DE.contextPath + "/portal/biz/std/bulkTermImport", data:{'data':data}});
	    	
	    	var totalCnt = result.data.result.targetCnt;
	    	var registCnt = result.data.result.registCnt;
	    	var errorCnt = result.data.result.errorCnt;
	    	
	    	alert("등록 되었습니다.\n전체건수:"+totalCnt+", 등록건수:"+registCnt+", 오류건수:"+errorCnt);
	    	$container.data("handsontable").loadData(result.data.data);
	    	
	    	gridReadOnly = false;
	    	$("a#termVerifyChkAction").html('<span class="fontawesome_Btn fa-edit"></span>용어검증');
        }
    });
    
    
    refreshContent = function(param, id) {};
    
	$(window).resize(function(){
	    var body = $(document.body).height();
        var top = $container.position().top;
        var margin = 55;
        $container.css("height", body-top-margin);
	}).resize();
});
function checkboxRenderer(instance, TD, row, col, prop, value, cellProperties){
	TD.style['text-align'] = 'center'; // !!Center align here!!
	Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
}
</script>
</body>
</html>