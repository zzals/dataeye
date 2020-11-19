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
                                    <c:set var="dMsg" value="" />
                                    <c:choose>
                                        <c:when test="${aprvGubun eq '20'}">
                                            <c:choose>
                                                <c:when test="${chkYn eq 'Y'}">
                                                    <c:set var="dMsg" value="승인/재검토/반려 처리 합니다." />
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="dMsg" value="승인/반려 처리 합니다." />
                                                </c:otherwise>
                                            </c:choose>
                                            <tr>
                                                <td width="15%" style="background: #f5f5f5; font-weight: bold; padding: 10px;">결재의견<br/>(<span style="color: #ff0084;" id="aprvByteCheck">0</span>/300)</td>
                                                <td width="85%" style="padding: 10px;">
                                                    <textarea class="form-control" rows="3" id="aprvDesc" placeholder="${dMsg}"></textarea>
                                                </td>
                                            </tr>
                                        </c:when>
                                        <c:when test="${aprvGubun eq '50'}">
                                            <c:set var="dMsg" value="검토 처리 합니다." />
                                            <tr>
                                                <td width="15%" style="background: #f5f5f5; font-weight: bold; padding: 10px;">결재의견<br/>(<span style="color: #ff0084;" id="aprvByteCheck">0</span>/300)</td>
                                                <td width="85%" style="padding: 10px;">
                                                    <textarea class="form-control" rows="3" id="aprvDesc" placeholder="${dMsg}"></textarea>
                                                </td>
                                            </tr>
                                        </c:when>
                                    </c:choose>
                                    <!-- <tr>
                                        <td width="15%" style="background: #f5f5f5; font-weight: bold; padding: 10px;">첨부파일</td>
                                        <td width="85^" style="padding: 10px;">
                                            <div class="form-group">
                                                <div class="col-sm-12 filebox" style="margin-bottom: 10px;">
                                                    <div id="aprvFileBtnArea">
                                                        <label class="btn btn-success fileinput-button">
                                                            <i clsss="fa_btn fa-plus"></i><span> 파일추가</span><input type="file" id="aprvFileUpload" name="multipartFiles" class="fileup_css" multiple="" />
                                                        </label>
                                                        <label>
                                                            <i class="fa_btn fa-trash-o"></i><span> 파일삭제</span>
                                                        </label>
                                                        <div style="float: right;">
                                                            <div style="font-size: 12px; font-weight: bold; float: left; margin: 7px 5px 0 0;">※ 첨부가능 확장자 안내(파일 크기 30MB 이하, 첨부 파일수 10개)</div>
                                                            <div class="file_Help">
                                                                <h5 style="margin-bottom: 4px;"><i class="fa fa-question-circle" aria-hidden="true" style="color: #87cefa; margin-top: -4px;"></i></h5>
                                                                <ul>
                                                                    <li>
                                                                        <table class="role_table">
                                                                            <tr>
                                                                                <th>첨부가능 확장자</th>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>xlsx, xls, doc, docx, ppt, pptx,<br/>jpg, gif, bmp, png, tif<br/>pdf, txt, csv, tsv, zip, sql</td>
                                                                            </tr>
                                                                        </table>
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="fileLine">
                                                        <table id="jqGridFile" style="width: 90px; text-align: center;"></table>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr> -->
                                </table>
                            </form>
                        </div>
                        <div style="float: right; margin-top: 10px;">
                        <c:choose>
                            <c:when test="${aprvGubun eq '20'}">
                                <button type="button" class="main_btn" id="btnDoAprv" attr-name="승인"><i class="fa_btn fa-save"></i>승인</button>
                                <c:if test="${chkYn eq 'Y'}">
                                    <button type="button" class="main_btn" id="btnReChkAprv" attr-name="재검토"><i class="fa_btn fa-save"></i>재검토</button>
                                </c:if>
                                <button type="button" class="main_btn" id="btnRetAprv" attr-name="반려"><i class="fa_btn fa-save"></i>반려</button>
                            </c:when>
                            <c:when test="${chkYn eq 'Y'}">
                                <button type="button" class="main_btn" id="btnChkAprv" attr-name="검토"><i class="fa_btn fa-check"></i>검토</button>
                            </c:when>
                        </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var rstCd = '${rstCd}';
    var rstMsg = '${rstMsg}';
    debugger;
    var pageContext = '${pageContext.request.contextPath}';
    var aprvData = ${aprvData};
    var first = 0;
    var rowId = null;

    $(document).ready(function () {
    	debugger;
        $('.aprv-modal').hide();
        if (APRV.fn.isempty(aprvData) || rstCd !== 'SUCC') {
            if (rstCd === 'FAIL') {
                AprvDialog.alertInfo(rstMsg, function () {
                    $('#aprvReqPopModal').removeClass('in');
                    $('.modal-backdrop').remove();
                    $('#aprvReqPopModal').hide();
                });
            }
            else {
                AprvDialog.alertInfo("요청 값이 올바르지 않습니다.", function () {
                    $('#aprvReqPopModal').removeClass('in');
                    $('.modal-backdrop').remove();
                    $('#aprvReqPopModal').hide();
                });
            }
        }
        else {
            $('.aprv-modal').show();
            var userInfo = '<c:out value="${dpUserNm}" />';
            var name = '<c:out value="${userNm}" />';
            var userId = '<c:out value="${userId}" />';
            var aprvLineMngrId = '';
            var rowData = [];

            var gridIndex = 0;
            $.each(aprvData, function (index, value) {
                var aprvDesc = APRV.fn.isempty(value['APRV_DESC']) ? '' : '\n[결재의견]\n' + value['APRV_DESC'];
                aprvDesc = value['APRV_LINE_DESC'] + aprvDesc;

                if (value['DO_APRV_CHK'] === 'Y') {
                    gridIndex = index;
                    aprvLineMngrId = value['APRV_LINE_MNGR_ID'];

                    if (!APRV.fn.isempty(value['APRV_DESC'])) {
                        $('#aprvDesc').val(value['APRV_DESC']);
                    }
                }

                rowData.push({
                    APRV_GBN: value['APRV_PROC_NM'],
                    USER_NM: value['USER_NM'],
                    APRV_DT: value['APRV_DT'],
                    APRV_DESC: aprvDesc
                });
            });

            APRV.fn.byteCheck('#aprvDesc', '#aprvByteCheck', 300);

            var fnSuccess = function (data) {
            	debugger;
                if (data.rstCd === 'SUCC') {
                    AprvDialog.alertSuccess(data.rstMsg, function () {
                    	
                        $('#aprvDoPopModal').removeClass('in');
                        $('.modal-backdrop').remove();
                        $('#aprvReqPopModal').hide();
                        if (typeof fn_aprv_callback === 'function') {
                            fn_aprv_callback(data);
                        }
                    });
                }
                else {
                    AprvDialog.alertInfo(data.rstMsg, function () {
                        $('#aprvDoPopModal').removeClass('in');
                        $('.modal-backdrop').remove();
                        $('#aprvDoPopModal').hide();
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
                    {name: 'APRV_GBN', index: 'APRV_GBN', label: '결재자 구분', width: 100, sortable: false, align: 'left'},
                    {name: 'USER_NM', index: 'USER_NM', label: '결재자', width: 100, sortable: false, align: 'left'},
                    {name: 'APRV_DT', index: 'APRV_DT', label: '결재 일시', width: 150, sortable: false, align: 'center'},
                    {name: 'APRV_DESC', index: 'APRV_DESC', label: '상세 정보', width: 250, sortable: false, align: 'left'}
                ],
                autowidth: true,
                height: 150,
                viewrecords: true,
                regional: 'kr',
                gridComplete: function () {
                    if (gridIndex > 0) {
                        $('#jqGridAprv').jqGrid('setSelection', (gridIndex + 1));
                    }
                }
            });

            /* $('#jqGridFile').jqGrid({
                datatype: 'local',
                colModel: [
                    {name: 'CHK', index: 'CHK', labe: '<input type="checkbox" id="chkAll" name="chkAll" onclick="javascript:APRV.jqgrid.checkAll(this, event);" />', width: 55, align: 'center',
                        sortable: false,
                        formatter: 'checkbox',
                        formatoptions: {disabled: false},
                        edittype: 'checkbox',
                        editoptions: {value: 'true:false'},
                        editable: true
                    },
                    {name: 'aprvId', index: 'aprvId', label: 'aprvId', hidden: true},
                    {name: 'apndFileNo', index: 'apndFileNo', label: '파일번호', hidden: true},
                    {name: 'apndFileSrnno', index: 'apndFileSrnno', label: '일련번호', hidden: true},
                    {name: 'aprvUserId', index: 'aprvUserId', label: 'aprvUserId', hidden: true},
                    {name: 'apndFileSiz', index: 'apndFileSiz', label: '용량(Byte)', hidden: true},
                    {name: 'apndFileExtsNm', index: 'apndFileExtsNm', label: '파일구분', hidden: true},
                    {name: 'apndFileNm', index: 'apndFileNm', label: '저장파일명', hidden: true},
                    {name: 'apndFilePathNm', index: 'apndFilePathNm', label: '파일경로', hidden: true},
                    {name: 'aprvFileNew', index: 'aprvFileNew', label: 'aprvFileNew', hidden: true},
                    {name: 'aprvPrcsNm', index: 'aprvPrcsNm', label: '결재자 구분', width: 150, sortable: false, align: 'left'},
                    {name: 'apndFileOcpyNm', index: 'apndFileOcpyNm', label: '파일명', width: 295, sortable: false, align: 'left'},
                    {name: 'apndFileOcpyNm', index: 'apndFileOcpyNm', label: '용량', width: 150, sortable: false, align: 'right'},
                    {name: 'apndFileOcpyNm', index: 'apndFileOcpyNm', label: '다운로드', width: 150, sortable: false, align: 'center', formatter: function (cellValue, options, rowObject) {
                        var innerHtml = '<button type="button" onclick="javascript:aprvGridFileDownload(\'#jqGridFile\', \'' + options.rowId + '\');" class="btn down_btn">다운로드</button>';
                        return innerHtml;
                    }}
                ],
                autowidth: true,
                height: 100,
                viewrecords: true,
                regional: 'kr',
                gridComplete: function () {
                    APRV.jqgrid.checkedHandler($(this));
                    var ids = $('#jqGridFile').jqGrid('getDataIDs');
                    var rowIndex = ids.length;
                    if (first === 1) {
                        for (var i = rowIndex - 1; i >= 0; i--) {
                            var rowDataChk = $('#jqGridFile').jqGrid('getRowData', ids[i]);

                            if (rowDataChk.apndFileNo.indexOf(aprvLineMngrId) === -1) {
                                $('#jqGridFile :checkbox').eq(i).remove();
                            }
                        }

                        for (var i = 0; i < rowIndex; i++) {
                            var rowDataChk = $('#jqGridFile').jqGrid('getRowData', ids[i]);

                            if (APRV.fn.isempty(frowId) && rowDataChk.apndFileNo.indexOf(aprvLineMngrId) === -1 && aprvLineMngrId < rowDataChk.apndFileNo) {
                                frowId = ids[i];
                            }
                        }

                        APRV.fn.fileUpload('#aprvFileUpload', '#jqGridFile', frowId);
                    }

                    first++;
                }
            }); */

            /* $('#jqGridFile').jqGrid('setGridParam', {
                url: pageContext + '/aprv/getAprvFileInfo',
                mtype: 'POST',
                postData: {
                    aprvId: '${aprvId}'
                },
                datatype: 'json'
            }).trigger('reloadGrid'); */

            $('button[id^=btn]').on('click', function () {
                var btnName = $(this).attr('attr-name');
                var afData = [];
                var ids = $('#jqGridFile').jqGrid('getDataIDs');
                var rowIndex = ids.length;

                for (var i = 0; i < rowIndex; i++) {
                    var rowDataChk = $('#jqGridFile').jqGrid('getRowData', ids[i]);
                    if (rowDataChk.aprvFileNew === 'Y') {
                        afData.push(rowDataChk);
                    }
                }

                if (this.id === 'btnDoAprv') {
                    AprvDialog.confirm(btnName + '하시겠습니까?', function (result) {
                    	
                        if (result === true) {
                            if (APRV.fn.byteCheckYn('#aprvDesc',  300)) {
                            
                                var aprvDesc = (APRV.fn.isempty($('#aprvDesc').val())) ? $('#aprvDesc').attr('placeholder') : $('#aprvDesc').val();
                                var jsonData = {
                                    aprvId: '${aprvId}',
                                    aprvDetlId: '${aprvDetlId}',
                                    aprvRsltCd: '${aprCd}',
                                    aprvFileData: afData,
                                    aprvDesc: aprvDesc
                                };
                                
                                debugger;

                                APRV.ajax.sendJson(pageContext + '/aprv/doAprv', jsonData, fnSuccess);
                            }
                        }
                        
                        
                    });
                }
                else if (this.id === 'btnRetAprv') {
                    AprvDialog.confirm(btnName + '하시겠습니까?', function (result) {
                        if (result === true) {
                            if (APRV.fn.byteCheckYn('#aprvDesc', 300)) {
                                var aprvDesc = (APRV.fn.isempty($('#aprvDesc').val())) ? $('#aprvDesc').attr('placeholder') : $('#aprvDesc').val();
                                var jsonData = {
                                    aprvId: '${aprvId}',
                                    aprvDetlId: '${aprvDetlId}',
                                    aprvRsltCd: '${retCd}',
                                    aprvFileData: afData,
                                    aprvDesc: aprvDesc
                                };

                                APRV.ajax.sendJson(pageContext + '/aprv/doAprv', jsonData, fnSuccess);
                            }
                        }
                    });
                }
                else if (this.id === 'btnChkAprv') {
                    AprvDialog.confirm(btnName + '처리하시겠습니까?', function (result) {
                        if (result === true) {
                            if (APRV.fn.byteCheckYn('#aprvDesc', 300)) {
                                var aprvDesc = (APRV.fn.isempty($('#aprvDesc').val())) ? $('#aprvDesc').attr('placeholder') : $('#aprvDesc').val();
                                var jsonData = {
                                    aprvId: '${aprvId}',
                                    aprvDetlId: '${aprvDetlId}',
                                    aprvRsltCd: '${chkCd}',
                                    aprvFileData: afData,
                                    aprvDesc: aprvDesc
                                };

                                APRV.ajax.sendJson(pageContext + '/aprv/doAprv', jsonData, fnSuccess);
                            }
                        }
                    });
                }
                else if (this.id === 'btnReChkAprv') {
                    AprvDialog.confirm(btnName + ' 요청하시겠습니까?', function (result) {
                        if (result === true) {
                            if (APRV.fn.byteCheckYn('#aprvDesc', 300)) {
                                var aprvDesc = (APRV.fn.isempty($('#aprvDesc').val())) ? $('#aprvDesc').attr('placeholder') : $('#aprvDesc').val();
                                var jsonData = {
                                    aprvId: '${aprvId}',
                                    aprvDetlId: '${aprvDetlId}',
                                    aprvRsltCd: '${recCd}',
                                    aprvFileData: afData,
                                    aprvDesc: aprvDesc
                                };

                                APRV.ajax.sendJson(pageContext + '/aprv/reqAprv', jsonData, fnSuccess);
                            }
                        }
                    });
                }

                return false;
            });

            /* if (first) {
                APRV.fn.fileUpload('#aprvFileUpload', '#jqGridFile', frowId);
            } */

            /* $('#aprvBtnDelFile').on('click', function () {
                var ids = $('#jqGridFile').jqGrid('getDataIDs');
                var rowIndex = ids.length;
                var chkFdata = [];

                for (var i = 0; i < rowIndex; i++) {
                    var value = $('#jqGridFile').jqGrid('getRowData', ids[i]);

                    if (value['CHK'] === 'true') {
                        var fObj = {
                            id: ids[i],
                            aprvId: value['aprvId'],
                            apndFileNo: value['apndFileNo'],
                            apndFileSrnno: value['apndFileSrnno'],
                            apndFileNm: value['apndFileNm'],
                            apndFilePathNm: value['apndFilePathNm']
                        };

                        chkFdata.push(fObj);
                    }
                }

                if (APRV.fn.isempty(chkFdata)) {
                    AprvDialog.alertInfo('삭제할 항목을 선택해주세요.');
                }
                else {
                    AprvDialog.confirm('삭제 하시겠습니까?', function (b) {
                        if (b === true) {
                            var resData = {
                                reqData: chkFdata
                            };

                            $.ajax({
                                type: 'POST',
                                contentType: 'application/json; charset=utf-8',
                                url: pageContext + '/aprv/removeFile',
                                async: false,
                                dataType: 'json',
                                data: JSON.stringify(resData),
                                success: function (data, textStatus) {
                                    AprvDialog.alertSuccess('삭제 되었습니다.', function () {
                                        for (var i = 0, len = chkFdata.length; i < len; i++) {
                                            $('#jqGridFile').jqGrid('delRowData', chkFdata[i].id);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }); */
            
            
            
        }
    });

</script>
</body>
</html>