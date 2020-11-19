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
            <div class="popup_title">도메인 일괄등록</div>
        </div>
    </div>
    <!-- //HEADER -->
    <section class="content">    
    <nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="collapse navbar-collapse">
			<div id="actionDiv" class="navbar-form navbar-right de-obj-btn">
				<a id="dmnVerifyChkAction" class="btn btn-default btn-sm"  role="button" href="#"><i class="fa fa-floppy-o"></i> 도메인검증</a>					
				<a id="batchSaveAction" class="btn btn-default btn-sm"  role="button" href="#"><i class="fa fa-floppy-o"></i> 일괄등록</a>
			</div>
		</div>
	</div>
</nav>
     <div class="row">
	<div class="col-xs-12">
			<div id="auto-dmn-list" role="grid-area" style="height: 500px; overflow: scroll;"></div>
	</div>
	</div>
         
    <span class="pop_close"><button type="button" class="close" onclick="window.close()">x</button></span>
</div>	    
<script>
$(document).ready(function (){
	request = JSON.parse('${data}');
	postData = {};
	
	var dmnGrpList = DE.ajax.call({async:false, url:"metapublic/list", data:{"queryId":"portal_biz.objBasInfo","objTypeId":"010304L"}})
	
	dmnGrpSource = [];
	for(var i=0; i<dmnGrpList.data.length; i++) {
		dmnGrpSource.push(dmnGrpList.data[i].OBJ_NM);
	}
	
	var dmnGroup_validator = function (value, callback) {
		for(var i=0; i<dmnGrpSource.length; i++) {
			if (dmnGrpSource[i] == value) {
				callback(true);
				return;
			}
		}
   		callback(false);
   	};
	
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
   	$container = $("div#auto-dmn-list");    
    $container.handsontable({
      	startRows: 1,
      	startCols: 7,
      	rowHeaders: true,
      	colHeaders: function (col) {
            switch (col) {
            case 0:
                var txt = "<input type='checkbox' class='allChk' ";
                txt += isChecked() ? 'checked="checked"' : '';
                txt += ">";
                return txt;
            case 1:
                return "<b>표준도메인명</b>";
            case 2:
                return "<b>데이터타입</b>";
            case 3:
                return "<b>도메인그룹명</b>";
            case 4:
                return "<b>중복여부</b>";
            case 5:
                return "<b>타입유효성</b>";
            case 6:
                return "<b>그룹유효성</b>";
            case 7:
                return "<b>검증결과</b>";
        	}
    	},
      	colWidths:[50, 200, 200, 200, 70, 70, 70, 70],
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
			{data:'DMN_NM', type:'text'},
			{data:'DMN_DATA_TYPE', type:'text'},
			{
			    data:'DMN_GRP_CD', 
            	editor: 'select',
                selectOptions:dmnGrpSource,
                strict: true,
                allowInvalid: true,
                validator: dmnGroup_validator
			},
			{data:'DUPL_YN',readOnly: true, type:'text', renderer:readonlyCellRenderer},
			{data:'DATA_TYPE_YN',readOnly: true, type:'text', renderer:readonlyCellRenderer},
			{data:'DMN_GRP_YN',readOnly: true, type:'text', renderer:readonlyCellRenderer},
			{data:'VRF_RSLT',readOnly: true, type:'text', renderer:readonlyCellRenderer}   
		],
      	beforeChange: function (changes, source) {},
      	afterChange: function (change, source) {},
      	cell: [],
        cells: function (row, col, prop) {
            if (col == 0) {
            } else if (col < 4) {
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
    
    $("a#dmnVerifyChkAction").on("click", function(e) {
        if (gridReadOnly) {
    		gridReadOnly = false;
    		$container.data("handsontable").render();
    		$("a#dmnVerifyChkAction").html('<span class="fontawesome_Btn fa-edit"></span>도메인검증');
    		return;
    	}
    	if ($container.data("handsontable").countRows() == 1) {
    		return;
    	}
    	data = $container.data("handsontable").getData();
        data = data.slice(0, data.length-1);
    	
    	var result = DE.ajax.call({async:false, url:DE.contextPath + "/portal/biz/std/bulkDmnVerify", data:{'data':data}});
    	
    	$container.data("handsontable").loadData(result.data);
    	$("a#dmnVerifyChkAction").html('<span class="fontawesome_Btn fa-edit"></span>편집');
    	gridReadOnly = true;
    });
    
    $("a#batchSaveAction").on("click", function(e) {
        if (!gridReadOnly) {
            alert("도메인검증을 수행하세요.");
            return;
        }
        if ($("input:checkbox.htCheckboxRendererInput:checked").length == 0) {
            alert("등록 대상이 없습니다.");
            return;
        }
       	if (confirm("등록하시겠습니까?")) {
			data = data = $container.data("handsontable").getData();
            data = data.slice(0, data.length-1);
            
	    	var result = DE.ajax.call({async:false, url:DE.contextPath + "/portal/biz/std/bulkDmnImport", data:{'data':data}});
	    	
	    	
	    	var totalCnt = result.data.result.targetCnt;
            var registCnt = result.data.result.registCnt;
            var errorCnt = result.data.result.errorCnt;
            
            alert("등록 되었습니다.\n전체건수:"+totalCnt+", 등록건수:"+registCnt+", 오류건수:"+errorCnt);
            $container.data("handsontable").loadData(result.data.data);
	    	
	    	gridReadOnly = false;
	    	$("a#dmnVerifyChkAction").html('<span class="fontawesome_Btn fa-edit"></span>도메인검증');
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