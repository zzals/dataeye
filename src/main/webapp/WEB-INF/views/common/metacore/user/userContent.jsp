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
    <c:import url="../../../common/import/common_css.jsp"/>
    <link type="text/css" rel="stylesheet"
          href="<spring:url value="/assets/javascripts-lib/datetimepicker/bootstrap-datetimepicker.min.css"/>">
    <link type="text/css" rel="stylesheet"
          href="<spring:url value="/assets/javascripts-lib/selectpicker/bootstrap-select.min.css"/>">
    <link type="text/css" rel="stylesheet"
          href="<spring:url value="/assets/javascripts-lib/fileUpload/jquery.fileupload.css"/>">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/<spring:theme code="theme.base.dir"/>/stylesheets/dataeye-objectinfo.css"/>

    <c:import url="../../../common/import/common_js.jsp"/>
    <script src="<spring:url value="/assets/javascripts/dataeye.user.js"/>"></script>
    <script src="<spring:url value="/assets/javascripts/dataeye.validator.js"/>"></script>
    <script src="<spring:url value="/assets/javascripts-lib/datetimepicker/moment.min.js"/>"></script>
    <script src="<spring:url value="/assets/javascripts-lib/datetimepicker/bootstrap-datetimepicker.min.js"/>"></script>
    <script src="<spring:url value="/assets/javascripts-lib/datetimepicker/ko.js"/>"></script>
    <script src="<spring:url value="/assets/javascripts-lib/selectpicker/bootstrap-select.min.js"/>"></script>

    <script src="<spring:url value="/assets/javascripts-lib/fileUpload/jquery.fileupload.js"/>"></script>
</head>
<body>
<!-- UI Object -->
<div id="container">
    <!-- HEADER -->
    <div id="header"></div>
    <!-- //HEADER -->
    <section class="content">
        <div id="tabContentBody"></div>
    </section>
</div>
<script type="text/javascript">
    $(document).ready(function () {
    	
        var reqParam = JSON.parse('${data}');

        request = {
            param: reqParam
        };

        DE.ajax.call({url:"${pageContext.request.contextPath}/system/userMenu/getAuth", data:reqParam}, function (response) {
            reqParam.authInfo = response.data;
            if (reqParam.mode == "C" && reqParam.authInfo.C_AUTH == "N") {
                DE.box.alert("접근 권한이 없습니다.");
            }
            else if (reqParam.mode == "R" && reqParam.authInfo.R_AUTH == "N") {
                DE.box.alert("접근 권한이 없습니다.");
            }
            else if (reqParam.mode == "U" && reqParam.authInfo.U_AUTH == "N") {
                DE.box.alert("접근 권한이 없습니다.");
            }
            else {
                if (reqParam.schema === 'userRole') {
                    if (reqParam.mode === 'C') {
                        var atrInfo = [
                            {ATR_ID_SEQ:1, ATR_NM:'그룹 ID', MAND_YN:'Y', INDC_YN:'Y', UI_COMP_CD:'IB+BTN', AVAIL_CHK_PGM_ID:'{"click":{"func":"dupCheck", "caption":"유효성체크"}}', OBJ_ATR_VAL:'', EXTEND_OBJ_ATR_VAL:''},
                            {ATR_ID_SEQ:2, ATR_NM:'그룹명', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'IB', OBJ_ATR_VAL:'', EXTEND_OBJ_ATR_VAL:''},
                            {ATR_ID_SEQ:3, ATR_NM:'그룹설명', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'IB', OBJ_ATR_VAL:'', EXTEND_OBJ_ATR_VAL:''},
                            {ATR_ID_SEQ:4, ATR_NM:'그룹유형', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'SB', OBJ_ATR_VAL:'', EXTEND_OBJ_ATR_VAL:'', ATR_CODE:[{CODE:'ROLE', DISP_NAME:'ROLE'}]},
                            {ATR_ID_SEQ:5, ATR_NM:'사용여부', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'RB', OBJ_ATR_VAL:'Y', EXTEND_OBJ_ATR_VAL:'', ATR_CODE:[{CODE:'Y', DISP_NAME:'예'},{CODE:'N', DISP_NAME:'아니오'}]}
                        ];
                        DE.user.create(reqParam, $("div#tabContentBody"), atrInfo);
                    }
                    else if (reqParam.mode === 'R') {
                        var rsp = DE.ajax.call({
                            async: false,
                            url: "system/userRole/get",
                            data: {keyVal: reqParam.keyVal}
                        });

                        var atrInfo = [
                            {ATR_ID_SEQ:1, ATR_NM:'그룹 ID', MAND_YN:'Y', INDC_YN:'Y', UI_COMP_CD:'IB', OBJ_ATR_VAL:rsp['data']['USER_GRP_ID'], EXTEND_OBJ_ATR_VAL:rsp['data']['USER_GRP_ID']},
                            {ATR_ID_SEQ:2, ATR_NM:'그룹명', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'IB', OBJ_ATR_VAL:rsp['data']['USER_GRP_NM'], EXTEND_OBJ_ATR_VAL:rsp['data']['USER_GRP_NM']},
                            {ATR_ID_SEQ:3, ATR_NM:'그룹설명', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'IB', OBJ_ATR_VAL:rsp['data']['USER_GRP_DESC'], EXTEND_OBJ_ATR_VAL:rsp['data']['USER_GRP_DESC']},
                            {ATR_ID_SEQ:4, ATR_NM:'그룹유형', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'SB', OBJ_ATR_VAL:rsp['data']['USER_GRP_TYPE'], EXTEND_OBJ_ATR_VAL:'', ATR_CODE:[{CODE:'ROLE', DISP_NAME:'ROLE'}]},
                            {ATR_ID_SEQ:5, ATR_NM:'사용여부', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'RB', OBJ_ATR_VAL:rsp['data']['USE_YN'], EXTEND_OBJ_ATR_VAL:rsp['data']['USE_YN'], ATR_CODE:[{CODE:'Y', DISP_NAME:'예'},{CODE:'N', DISP_NAME:'아니오'}]}
                        ];
                        DE.user.create(reqParam, $("div#tabContentBody"), atrInfo);
                    }
                    else if (reqParam.mode === 'U') {
                        var rsp = DE.ajax.call({
                            async: false,
                            url: "system/userRole/get",
                            data: {keyVal: reqParam.keyVal}
                        });

                        var atrInfo = [
                            {ATR_ID_SEQ:1, ATR_NM:'그룹 ID', MAND_YN:'Y', INDC_YN:'Y', UI_COMP_CD:'IB', AVAIL_CHK_PGM_ID:'{"init":{"func":"ForceReadOnly"}}', OBJ_ATR_VAL:rsp['data']['USER_GRP_ID'], EXTEND_OBJ_ATR_VAL:rsp['data']['USER_GRP_ID']},
                            {ATR_ID_SEQ:2, ATR_NM:'그룹명', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'IB', OBJ_ATR_VAL:rsp['data']['USER_GRP_NM'], EXTEND_OBJ_ATR_VAL:rsp['data']['USER_GRP_NM']},
                            {ATR_ID_SEQ:3, ATR_NM:'그룹설명', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'IB', OBJ_ATR_VAL:rsp['data']['USER_GRP_DESC'], EXTEND_OBJ_ATR_VAL:rsp['data']['USER_GRP_DESC']},
                            {ATR_ID_SEQ:4, ATR_NM:'그룹유형', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'SB', OBJ_ATR_VAL:rsp['data']['USER_GRP_TYPE'], EXTEND_OBJ_ATR_VAL:'', ATR_CODE:[{CODE:'ROLE', DISP_NAME:'ROLE'}]},
                            {ATR_ID_SEQ:5, ATR_NM:'사용여부', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'RB', OBJ_ATR_VAL:rsp['data']['USE_YN'], EXTEND_OBJ_ATR_VAL:rsp['data']['USE_YN'], ATR_CODE:[{CODE:'Y', DISP_NAME:'예'},{CODE:'N', DISP_NAME:'아니오'}]}
                        ];
                        DE.user.create(reqParam, $("div#tabContentBody"), atrInfo);
                    }
                }
                else if (reqParam.schema === 'userAuth') {
                    if (reqParam.mode === 'C') {
                        var atrInfo = [
                            {ATR_ID_SEQ:1, ATR_NM:'그룹 ID', MAND_YN:'Y', INDC_YN:'Y', UI_COMP_CD:'IB+BTN', AVAIL_CHK_PGM_ID:'{"click":{"func":"dupCheck", "caption":"유효성체크"}}', OBJ_ATR_VAL:'', EXTEND_OBJ_ATR_VAL:''},
                            {ATR_ID_SEQ:2, ATR_NM:'그룹명', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'IB', OBJ_ATR_VAL:'', EXTEND_OBJ_ATR_VAL:''},
                            {ATR_ID_SEQ:3, ATR_NM:'그룹설명', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'IB', OBJ_ATR_VAL:'', EXTEND_OBJ_ATR_VAL:''},
                            {ATR_ID_SEQ:4, ATR_NM:'그룹유형', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'SB', OBJ_ATR_VAL:'', EXTEND_OBJ_ATR_VAL:'', ATR_CODE:[{CODE:'FUNC_ACCESS', DISP_NAME:'FUNC_ACCESS'},{CODE:'OBJ_FILTER', DISP_NAME:'OBJ_FILTER'}]},
                            {ATR_ID_SEQ:5, ATR_NM:'사용여부', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'RB', OBJ_ATR_VAL:'Y', EXTEND_OBJ_ATR_VAL:'', ATR_CODE:[{CODE:'Y', DISP_NAME:'예'},{CODE:'N', DISP_NAME:'아니오'}]}
                        ];
                        DE.user.create(reqParam, $("div#tabContentBody"), atrInfo);
                    }
                    else if (reqParam.mode === 'R') {
                        var rsp = DE.ajax.call({
                            async: false,
                            url: "system/userRole/get",
                            data: {keyVal: reqParam.keyVal}
                        });

                        var atrInfo = [
                            {ATR_ID_SEQ:1, ATR_NM:'그룹 ID', MAND_YN:'Y', INDC_YN:'Y', UI_COMP_CD:'IB', OBJ_ATR_VAL:rsp['data']['USER_GRP_ID'], EXTEND_OBJ_ATR_VAL:rsp['data']['USER_GRP_ID']},
                            {ATR_ID_SEQ:2, ATR_NM:'그룹명', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'IB', OBJ_ATR_VAL:rsp['data']['USER_GRP_NM'], EXTEND_OBJ_ATR_VAL:rsp['data']['USER_GRP_NM']},
                            {ATR_ID_SEQ:3, ATR_NM:'그룹설명', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'IB', OBJ_ATR_VAL:rsp['data']['USER_GRP_DESC'], EXTEND_OBJ_ATR_VAL:rsp['data']['USER_GRP_DESC']},
                            {ATR_ID_SEQ:4, ATR_NM:'그룹유형', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'SB', OBJ_ATR_VAL:rsp['data']['USER_GRP_TYPE'], EXTEND_OBJ_ATR_VAL:'', ATR_CODE:[{CODE:'FUNC_ACCESS', DISP_NAME:'FUNC_ACCESS'},{CODE:'OBJ_FILTER', DISP_NAME:'OBJ_FILTER'}]},
                            {ATR_ID_SEQ:5, ATR_NM:'사용여부', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'RB', OBJ_ATR_VAL:rsp['data']['USE_YN'], EXTEND_OBJ_ATR_VAL:rsp['data']['USE_YN'], ATR_CODE:[{CODE:'Y', DISP_NAME:'예'},{CODE:'N', DISP_NAME:'아니오'}]}
                        ];
                        DE.user.create(reqParam, $("div#tabContentBody"), atrInfo);
                    }
                    else if (reqParam.mode === 'U') {
                        var rsp = DE.ajax.call({
                            async: false,
                            url: "system/userRole/get",
                            data: {keyVal: reqParam.keyVal}
                        });

                        var atrInfo = [
                            {ATR_ID_SEQ:1, ATR_NM:'그룹 ID', MAND_YN:'Y', INDC_YN:'Y', UI_COMP_CD:'IB', AVAIL_CHK_PGM_ID:'{"init":{"func":"ForceReadOnly"}}', OBJ_ATR_VAL:rsp['data']['USER_GRP_ID'], EXTEND_OBJ_ATR_VAL:rsp['data']['USER_GRP_ID']},
                            {ATR_ID_SEQ:2, ATR_NM:'그룹명', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'IB', OBJ_ATR_VAL:rsp['data']['USER_GRP_NM'], EXTEND_OBJ_ATR_VAL:rsp['data']['USER_GRP_NM']},
                            {ATR_ID_SEQ:3, ATR_NM:'그룹설명', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'IB', OBJ_ATR_VAL:rsp['data']['USER_GRP_DESC'], EXTEND_OBJ_ATR_VAL:rsp['data']['USER_GRP_DESC']},
                            {ATR_ID_SEQ:4, ATR_NM:'그룹유형', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'SB', OBJ_ATR_VAL:rsp['data']['USER_GRP_TYPE'], EXTEND_OBJ_ATR_VAL:'', ATR_CODE:[{CODE:'FUNC_ACCESS', DISP_NAME:'FUNC_ACCESS'},{CODE:'OBJ_FILTER', DISP_NAME:'OBJ_FILTER'}]},
                            {ATR_ID_SEQ:5, ATR_NM:'사용여부', MAND_YN:'N', INDC_YN:'Y', UI_COMP_CD:'RB', OBJ_ATR_VAL:rsp['data']['USE_YN'], EXTEND_OBJ_ATR_VAL:rsp['data']['USE_YN'], ATR_CODE:[{CODE:'Y', DISP_NAME:'예'},{CODE:'N', DISP_NAME:'아니오'}]}
                        ];
                        DE.user.create(reqParam, $("div#tabContentBody"), atrInfo);
                    }
                }
            }
        });

    });
</script>
</body>
</html>