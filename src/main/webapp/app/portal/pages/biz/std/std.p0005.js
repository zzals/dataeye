$(document).ready(function (){
	var reqParam = $("#reqParam").data();
	postData = {};
	
	if (reqParam["popup_title"] != undefined) {
		$(".popup_title").text(reqParam["popup_title"]);
	}

	var dmnGrpList = DE.ajax.call({async:false, url:"metapublic/list", data:{"queryId":"portal.common.findObjM", "objTypeId":"010304L"}});
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
    
    $("#dmnUploadTplDown").on("click", function(e) {
		var action = DE.contextPath+"/portal/biz/std?oper=downloadDomainUploadTpl"
		var $frm = $("#downloadDomainUploadTplForm");
    	var $frame =  $("#_frame");
    	
    	if ($frm.length == 0) {
	    	$frm =  $("<form id='downloadDomainUploadTplForm' name='downloadDomainUploadTplForm' action='"+action+"' target='_frame' class='downloadDomainUploadTplForm' method='post'></form>").css("display", "none");
	        $frm.appendTo("body");
	    }
    	
    	if ($frame.length === 0) {
        	$frame =  $("<iframe id='_frame' name='_frame'></iframe>").css("display", "none");
            $frame.appendTo("body");
        }
    	
    	$("form.downloadDomainUploadTplForm").on("submit", function (e) {
    	    $.fileDownload($(this).prop('action'), {
    	        preparingMessageHtml: "엑셀 다운로드 진행중 ...",
    	        failMessageHtml: "엑셀 다운로드 진행중 오류가 발생하였습니다. 다시 시도하여 주시기 바랍니다.",
    	        httpMethod: "POST",
    	        data: $(this).serialize()
    	    });
    	    e.preventDefault();
    	    $("form.downloadDomainUploadTplForm").off("submit");
    	});
    	
    	$frm.submit();
	});
    
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
	    	result = UI.ajax.execute("json/bulkDmnImport", {'data':data});
	    	var totalCnt = result.rows.result.targetCnt;
            var registCnt = result.rows.result.registCnt;
            var errorCnt = result.rows.result.errorCnt;
            
            alert("등록 되었습니다.\n전체건수:"+totalCnt+", 등록건수:"+registCnt+", 오류건수:"+errorCnt);
            $container.data("handsontable").loadData(result.rows.data);
	    	
	    	gridReadOnly = false;
	    	$("a#dmnVerifyChkAction").html('<span class="fontawesome_Btn fa-edit"></span>도메인검증');
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