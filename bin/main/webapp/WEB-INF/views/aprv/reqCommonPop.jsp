<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title><spring:message code="app.title" text="DateEye" /></title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link type="text/css" rel="stylesheet" href="<spring:url value="/assets/stylesheets/dataeye-aprv.css"/>">
    <script src="<spring:url value="/assets/javascripts/dataeye.aprv.js"/>"></script>
</head>
<body class="de-popup">
<div class="aprv-modal" style="margin-top: 0px;">
    <div class="modal">
        <div class="modal-dialog" style="width: 920px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">x</span>
                    </button>
                    <h2 class="modal-title">결재 정보</h2>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="box box-info" style="padding: 15px;">
                            <form class="form-horizontal">
                                <table style="margin: 0px; border: 1px solid #ccc; width: 100%;">
                                    <tr style="border-bottom: 1px solid #ccc;">
                                        <td width="15%" style="background: #f5f5f5; font-weight: bold; padding: 10px;">결재라인</td>
                                        <td width="85%" style="padding: 10px;">
                                            <div id="btnAction" style="text-align: right; margin-bottom: 5px;"></div>
                                            <div id="gridAprv" class="box-body" style="padding-right: 0px;">
                                                <table id="jqGridAprv" style="width: 90%; text-align: center;"></table>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="15%" style="background: #f5f5f5; font-weight: bold; padding: 10px;">결재의견<br/>(<span style="color: #ff0084;" id="aprvByteCheck">0</span>/300)</td>
                                        <td width="85%" style="padding: 10px;">
                                            <textarea class="form-control" rows="3" id="aprvDesc" placeholder="승인요청 드립니다."></textarea>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                        <div style="float: right; margin-top: 10px;">
                            <button type="button" class="main_btn" id="btnAprvReqConfirm"><i class="fa_btn fa-save"></i>요청</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var aprvInfo = ${aprvInfo};
    var aprvLineInfo = ${aprvLineInfo};
    var pageContext = '${pageContext.request.contextPath}';
    var aprvIndex;
    var first = true;

    $(document).ready(function () {
        $('.aprv-modal').hide();
        
        if (APRV.fn.isempty(aprvInfo) || APRV.fn.isempty(aprvLineInfo)) {
            AprvDialog.alertInfo("요청 값이 올바르지 않습니다.", function () {
                $('#aprvReqPopModal').removeClass('in');
                $('.modal-backdrop').remove();
                $('#aprvReqPopModal').hide();
            });
        }
        else {
        	debugger;
            $('.aprv-modal').show();
            var userInfo = '<c:out value="${userNm}" />';
            var name = '<c:out value="${userNm}" />';
            var userId = '<c:out value="${userId}" />';
            var rowData = [];

            if (!APRV.fn.isempty(aprvInfo['APRV_DESC'])) {
                $('#aprvDesc').val(aprvInfo['APRV_DESC']);
            }

            APRV.fn.byteCheck('#aprvDesc', '#aprvByteCheck', 300);

            $.each(aprvLineInfo, function (index, value) {            
               if (index === 0) {
                   rowData.push({
                       APRV_GBN: '기안',
                       APRV_DESC: userInfo,
                       USER_ID: userId,
                       APRV_INDEX: index,
                       APRV_LINE_MNGR_ID: value['APRV_LINE_MNGR_ID']
                   });
               }
               else {
                   if (value['APRV_USER_GUBUN'] === 'USER') {
                       var btnStr = (value['APRV_LINE_DESC'] === '승인') ? '' : '(' + value['APRV_LINE_DESC'] + ')';
                       $('#btnAction').append('<button type="button" class="btn bg-purple btn-xs" style="margin: 0px;" id="btnMemberAdd' + index + '">+ 결재자' + btnStr + '추가</button> ');
                       $('#btnAction #btnMemberAdd' + index).attr('MULTI_YN', value['MULTI_YN']);
                       $('#btnAction #btnMemberAdd' + index).attr('APRV_LINE_MNGR_ID', value['APRV_LINE_MNGR_ID']);
                       $('#btnAction #btnMemberAdd' + index).attr('APRV_INDEX', index);
                   }
                   else if (value['APRV_USER_GUBUN'] === 'ROLE') {
                       rowData.push({
                           APRV_GBN: value['APRV_PROC_NM'],
                           APRV_DESC: value['APRV_LINE_DESC'],
                           USER_ID: '',
                           APRV_INDEX: index,
                           APRV_LINE_MNGR_ID: value['APRV_LINE_MNGR_ID']
                       });
                   }
               }
            });

            if ($('#btnAction > button[id^=btn').length > 0) {
                $('#btnAction').append('<button type="button" class="btn bg-purple btn-xs" style="margin: 0px;" id="btnMemberDel">- 결재자삭제</button>');
            }

            /* var fn_aprv_callback=function(data){
            	alert(" bbbb");
            	if(data.rstCd==='SUCC'){
            		
            	}
            } */
            
            var fnSuccess = function (data) {
                debugger;
            	if (data.rstCd === 'SUCC') {
                    AprvDialog.alertSuccess(data.rstMsg, function () {
                        $('#aprvReqPopModal').removeClass('in');
                        $('.modal-backdrop').remove();
                        $('#aprvReqPopModal').hide();
                        
                        if (typeof fn_aprv_callback === 'function') {
                            fn_aprv_callback(data);
                        }
                    });
                }
                else {
                    AprvDialog.alertInfo(data.rstMsg, function (fn_aprv_callback) {
                        $('#aprvReqPopModal').removeClass('in');
                        $('.modal-backdrop').remove();
                        $('#aprvReqPopModal').hide();
                        
                        if (typeof fn_aprv_callback === 'function') {
                            fn_aprv_callback(data);
                        }
                    });
                }
            };

            $('#jqGridAprv').jqGrid({
                datatype: 'local',
                data: rowData,
                colModel: [
                    {name: 'CHK', index: 'CHK', label: '<input type="checkbox" id="chkAll" name="chkAll" onclick="javascript:APRV.jqgrid.checkAll(this, event);" />', width: 55, align: 'center',
                        sortable: false,
                        formatter: 'checkbox',
                        formatoptions: {disabled: false},
                        edittype: 'checkbox',
                        editoptions: {value: 'true:false'},
                        editable: true
                    },
                    {name: 'USER_ID', index: 'USER_ID', label: 'USER_ID', hidden: true},
                    {name: 'APRV_INDEX', index: 'APRV_INDEX', label: 'APRV_INDEX', hidden: true},
                    {name: 'APRV_LINE_MNGR_ID', index: 'APRV_LINE_MNGR_ID', label: 'APRV_LINE_MNGR_ID', hidden: true},
                    {name: 'APRV_GBN', index: 'APRV_GBN', label: '결재자 구분', width: 250, sortable: false, align: 'left'},
                    {name: 'APRV_DESC', index: 'APRV_DESC', label: '결재자 정보', width: 450, sortable: false, align: 'left'}
                ],
                autowidth: true,
                height: 220,
                viewrecords: true,
                regional: 'kr',
                rownumbers: false,
                gridComplete: function () {
                    APRV.jqgrid.checkedHandler($(this));
                    if (first) {
                        $('tr.jqgrow > td > input[type=checkbox]', $('#jqGridAprv')[0]).remove();
                        first = false;
                    }
                }
            });

            $('tr.jqgrow > td > input[type=checkbox]', $('#jqGridAprv')[0]).remove();

            $('button[id^=btn]').on('click', function (e) {
            	e.preventDefault();
            	
                if (this.id.indexOf('btnMemberAdd') > -1) {
                    $('#memPopModal').css('z-index', '999999999');
                    aprvIndex = $(this).attr('APRV_INDEX');
                    ajaxOpts.dataType = 'html';
                    ajaxOpts.data = {popGubun: 'MEMBER'};
                    ajaxOpts.success = function (data) {
                        $('#memPopModal').html(data);
                        $('#memPopModal').modal('show');
                    };
                    $.ajax(pageContext + '/getMemberList', ajaxOpts);
                }
                else if (this.id === 'btnMemberDel') {
                    var data = $('#jqGridAprv').jqGrid('getGridParam', 'data');
                    var chkData = [];
                    $.each(data, function (index, value) {
                        if (value['CHK'] === 'true') {
                            chkData.push(this.id);
                        }
                    });

                    if (APRV.fn.isempty(chkData)) {
                        AprvDialog.alertInfo("삭제 할 항목을 선택해주세요.");
                    }
                    else {
                        AprvDialog.confirm('결재자를 삭제하시겠습니까?', function (result) {
                            if (result) {
                                for (var i = 0, len = chkData.length; i < len; i++) {
                                    $('#jqGridAprv').jqGrid('delRowData', chkData[i]);
                                }
                            }
                        });
                    }
                }
                else if (this.id === 'btnAprvReqConfirm') {
                    
                	AprvDialog.confirm('결재 요청하시겠습니까?', function (result) {
                		debugger;
                        if (result === true) {
                            if (APRV.fn.byteCheckYn('#aprvDesc', 300)) {
                                var aprvDesc = (APRV.fn.isempty($('#aprvDesc').val())) ? $('#aprvDesc').attr('placeholder') : $('#aprvDesc').val();
                                var jsonData = {
                                    aprvInfo: aprvInfo,
                                    aprvData: $('#jqGridAprv').jqGrid('getGridParam', 'data'),
                                    aprvDesc: aprvDesc
                                };
                                
                                

                                APRV.ajax.sendJson(pageContext + '/aprv/reqAprv', jsonData, fnSuccess);
                            }
                        }
                    });
                }

                return false;
            });
        }
    });

    var fn_callBack = function (type, result) {
        var ids = $('#jqGridAprv').jqGrid('getDataIDs');
        var rowIndex = ids.length;
        var userInfo = result.NAME;

        if (aprvLineInfo[aprvIndex]['MULTI_YN'] !== 'Y') {
            if (fn_findRowData(aprvIndex) > 0) {
                AprvDialog.alertInfo('이미 등록되었습니다.');
                return false;
            }
        }

        if (fn_findData('USER_ID', result.USER_ID, aprvIndex)) {
            AprvDialog.alertInfo('이미 등록된 사용자입니다.');
            return false;
        }

        var rowInsData = [{
            APRV_GBN: aprvLineInfo[aprvIndex]['APRV_PROC_NM'],
            APRV_DESC: userInfo,
            USER_ID: result.USER_ID,
            APRV_INDEX: aprvIndex,
            APRV_LINE_MNGR_ID: aprvLineInfo[aprvIndex]['APRV_LINE_MNGR_ID']
        }];

        var nextAprvIndex = parseInt(aprvIndex) + 1;
        var rowId;
        for (var i = 0; i < rowIndex; i++) {
            var rowDataChk = $('#jqGridAprv').jqGrid('getRowData', ids[i]);
            if (nextAprvIndex === parseInt(rowDataChk.APRV_INDEX)) {
                var rowId = ids[i];
                break;
            }
        }

        if (!APRV.fn.isempty(rowId)) {
            $('#jqGridAprv').jqGrid('addRowData', (rowIndex + 1), rowInsData, 'before', rowId);
        }
        else {
            $('#jqGridAprv').jqGrid('addRowData', (rowIndex + 1), rowInsData, 'last');
        }
    };

    var fn_findRowData = function (aprvIndex) {
        var rstVal = 0;
        var chkbox = $('tr.jqgrow > td > input[type=checkbox]', $('#jqGridAprv')[0]);

        for (var i = 0, len = chkbox.length; i < len; i++) {
            var rowId = chkbox.eq(i).closest('tr').prop('id');
            var row = $('#jqGridAprv').getLocalRow(rowId);

            if (aprvLineInfo[aprvIndex]['APRV_LINE_MNGR_ID'] === row['APRV_LINE_MNGR_ID']) {
                rstVal += 1;
            }
        }

        return rstVal;
    };

    var fn_findData = function (col, value, aprvIndex) {
        var rstVal = 0;
        var chkbox = $('tr.jqgrow > td > input[type=checkbox]', $('#jqGridAprv')[0]);

        for (var i = 0, len = chkbox.length; i < len; i++) {
            var rowId = chkbox.eq(i).closest('tr').prop('id');
            var row = $('#jqGridAprv').getLocalRow(rowId);

            if (aprvLineInfo[aprvIndex]['APRV_LINE_MNGR_ID'] === row['APRV_LINE_MNGR_ID'] && row[col] === value) {
                return true;
            }
        }

        return rstVal;
    };
</script>
</body>
</html>