(function () {
    'use strict';

    (function (root, factory) {
        if ((typeof define === 'function') && define.amd) {
            return define(['jquery'], factory);
        } else {
            return factory(root.DE, root.jQuery);
        }
    })(window, function (DE, $) {

        DE = DE || {};

        var componentFileName, moduleName;
        componentFileName = 'dataeye.validator.js';
        moduleName = 'DE.validator';

        /**
         * @literal    DE.validator literal
         * @memberOf module:DE.validator.object
         * @return    {object}    DE.validator
         * @description        새로운 DE.validator 인스턴스를 생성한다.
         */
        DE.validator = {
            call_subjectAreaPopup: function (target) {
            	debugger;
                DE.ui.open.popup(
                    "view",
                    ["subjectSelected"],
                    {
                        viewname: 'popup/subjectSelected',
                        objTypeId: request.param.objTypeId,
                        targetObjectName: $(target).closest("div").children(":first").prop("id")
                    },
                    "width=800, height=500, toolbar=non, menubar=no"
                );
            },
            call_ownerPopup: function (target) {
                DE.ui.open.popup(
                    "view",
                    ["userSelected"],
                    {
                        viewname: 'common/metacore/bdp/userSelected',
                        targetObjectName: $(target).closest("div").children(":first").prop("id")
                    },
                    "width=800, height=500, toolbar=non, menubar=no"
                );
            },
            call_deptPopup: function (target) {
                DE.ui.open.popup(
                    "view",
                    ["deptSelected"],
                    {
                        viewname: 'popup/deptSelected',
                        targetObjectName: $(target).closest("div").children(":first").prop("id")
                    },
                    "width=800, height=500, toolbar=non, menubar=no"
                );
            },
            call_objInfoPopup: function (target, pObjTypeId, pObjId, pAtrIdSeq, objTypeId, objId, mode, callback) {
                DE.ui.open.popup(
                    "view",
                    ["objInfoSelected"],
                    {
                        viewname: 'object/tabContent',
                        targetObjectName: $(target).prop("id"),
                        pObjTypeId: pObjTypeId,
                        pObjId: pObjId,
                        pAtrIdSeq: pAtrIdSeq,
                        objTypeId: objTypeId,
                        objId: objId,
                        action: "object/tabContent",
                        mode: mode,
                        callback: callback
                    },
                    "width=800, height=400, toolbar=non, menubar=no"
                );
            },
            call_removeObjInfo: function (target, pObjTypeId, pObjId, pAtrIdSeq, pAtrValSeq, objTypeId, objId) {
                var param = [];
                param.push({
                    type: "DB",
                    queryId: "PEN_OBJ_M.delete",
                    parameter: [{objTypeId: objTypeId, objId: objId}]
                });
                param.push({
                    type: "DB",
                    queryId: "PEN_OBJ_D.delete",
                    parameter: [{objTypeId: objTypeId, objId: objId, between: [1, 200]}]
                });
                param.push({
                    type: "DB",
                    queryId: "PEN_OBJ_D.delete",
                    parameter: [{
                        objTypeId: pObjTypeId,
                        objId: pObjId,
                        filterAtrIdSeq: [pAtrIdSeq],
                        filterAtrValSeq: [Number(pAtrValSeq) + 1, Number(pAtrValSeq) + 2]
                    }]
                });
                var penFileList = [];
                var fileInfo = {};
                fileInfo.action = "delete";
                fileInfo.sourcePath = "MANAGED_FILE/" + objTypeId;
                fileInfo.sourceName = objId;
                fileInfo.targetPath = "";
                fileInfo.targetName = "";
                penFileList.push(fileInfo);
                param.push({type: "FILE", parameter: penFileList});

                DE.ui.ajax.execute("json/saveObjInfo", param);
                call_tipList(target, pObjTypeId, pObjId, pAtrIdSeq, objTypeId);
            },
            call_tipList: function (target, objTypeId, objId, atrIdSeq, param) {
                $(target).html("");
                var result = DE.ui.ajax.list(new Object({
                    queryId: "objectcore.tipListByObj",
                    objTypeId: objTypeId,
                    objId: objId,
                    atrIdSeq: atrIdSeq
                }));
                var $tpl = $("<ul />");

                if (result.rows.length == 0) {
                    var controls = $tpl.clone();
                    if (request.param.mode == "R" || request.param.mode == "D") {
                        controls.append("<li class='m_left5'></li>");
                    } else {
                        controls.append("<li class='m_left5'><a href='#'><img role='multiplus' exception='true' src='resources/images/meta-viewer/plus_btn.png'></a></li>");
                        controls.find("img[role=multiplus]").on("click", function (e) {
                            e.preventDefault();
                            call_objInfoPopup(
                                target,
                                objTypeId,
                                objId,
                                atrIdSeq,
                                param.objTypeId,
                                "",
                                "C",
                                "opener.call_tipList(opener.$(\\\"td#" + $(target).prop("id") + "\\\"), \\\"" + objTypeId + "\\\", \\\"" + objId + "\\\", \\\"" + atrIdSeq + "\\\")"
                            );
                        });
                    }
                    $(target).append(controls);
                } else {
                    $tpl.append("<li><a id='tipLink' href='#'></a></li>");
                    var isFirst = true;
                    for (var i = 0, len = result.rows.length; i < len; i++) {
                        var data = result.rows[i];
                        var controls = $tpl.clone();
                        controls.find("a#tipLink").html(data.OBJ_NM);
                        controls.find("a#tipLink").attr("pObjTypeId", data.P_OBJ_TYPE_ID);
                        controls.find("a#tipLink").attr("pObjId", data.P_OBJ_ID);
                        controls.find("a#tipLink").attr("pAtrIdSeq", data.ATR_ID_SEQ);
                        controls.find("a#tipLink").attr("pAtrValSeq", data.ATR_VAL_SEQ);
                        controls.find("a#tipLink").attr("objTypeId", data.OBJ_TYPE_ID);
                        controls.find("a#tipLink").attr("objId", data.OBJ_ID);
                        controls.find("a#tipLink").attr("mode", "U");

                        controls.find("a#tipLink").on("click", function (e) {
                            e.preventDefault();
                            call_objInfoPopup(
                                target,
                                $(this).attr("pObjTypeId"),
                                $(this).attr("pObjId"),
                                $(this).attr("pAtrIdSeq"),
                                $(this).attr("objTypeId"),
                                $(this).attr("objId"),
                                $(this).attr("mode"),
                                "opener.call_tipList(opener.$(\\\"td#" + $(target).prop("id") + "\\\"), \\\"" + $(this).attr("pObjTypeId") + "\\\", \\\"" + $(this).attr("pObjId") + "\\\", \\\"" + $(this).attr("pAtrIdSeq") + "\\\")"
                            );
                        });

                        if (request.param.mode == "R" || request.param.mode == "D") {
                        } else {
                            if (isFirst) {
                                isFirst = false;
                                controls.append("<li class='m_left5'><a href='#'><img role='multiminus' exception='true' src='resources/images/meta-viewer/minus_btn.png'></a></li>");
                                controls.append("<li class='m_left5'><a href='#'><img role='multiplus' exception='true' src='resources/images/meta-viewer/plus_btn.png'></a></li>");

                                controls.find("img[role=multiplus]").on("click", function (e) {
                                    e.preventDefault();
                                    var $a = $(this).closest("div").find("a#tipLink");
                                    call_objInfoPopup(
                                        target,
                                        $a.attr("pObjTypeId"),
                                        $a.attr("pObjId"),
                                        $a.attr("pAtrIdSeq"),
                                        $a.attr("objTypeId"),
                                        "",
                                        "C",
                                        "opener.call_tipList(opener.$(\\\"td#" + $(target).prop("id") + "\\\"), \\\"" + $a.attr("pObjTypeId") + "\\\", \\\"" + $a.attr("pObjId") + "\\\", \\\"" + $a.attr("pAtrIdSeq") + "\\\")"
                                    );
                                });
                            } else {
                                controls.append("<li class='m_left5'><a href='#'><img role='multiminus' exception='true' src='resources/images/meta-viewer/minus_btn.png'></a></li>");
                            }
                            controls.find("img[role=multiminus]").on("click", function (e) {
                                if (!confirm("삭제시 해당 HOW-TO(Tip)도 삭제됩니다.\n삭제 하시겠습니까?")) {
                                    return;
                                }
                                var $tipLink = $(this).closest("ul").children(":first").find("a#tipLink");
                                var pObjTypeId = $tipLink.attr("pObjTypeId");
                                var pObjId = $tipLink.attr("pObjId");
                                var pAtrIdSeq = $tipLink.attr("pAtrIdSeq");
                                var pAtrValSeq = $tipLink.attr("pAtrValSeq");
                                var objTypeId = $tipLink.attr("objTypeId");
                                var objId = $tipLink.attr("objId");

                                call_removeObjInfo(target, pObjTypeId, pObjId, pAtrIdSeq, pAtrValSeq, objTypeId, objId);
                            });
                        }
                        $(target).append(controls);
                    }
                }
            },
            call_disabled: function (target, atrIdSeq) {
                $("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").prop("disabled", "disabled");
            },
            call_brCheckData: function (target, atrIdSeq) {
                if (atrIdSeq == "") {
                    return;
                } else {
                    var val = $(target).val();
                    if (val != "") {
                        $("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").prop("disabled", "");
                        $("[id^=ATR_ID_SEQ_" + atrIdSeq + "_] option").remove();
                        $("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").append("<option>::선택하세요::</option>");

                        var atrCodes = DE.metaui.getAtribCode(atrIdSeq);

                        $.each(atrCodes, function () {
                            if (this.UP_CD == val) {
                                $("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").append("<option value='" + this.CODE + "'>" + this.DISP_NAME + "</option>");
                            }
                        });
                    } else {
                        $("[id^=ATR_ID_SEQ_" + atrIdSeq + "_] option").remove();
                        $("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").append("<option>::선택하세요::</option>");
                        $("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").prop("disabled", "disabled");
                    }
                }
            },
            InputMask: function (target, param) {
                var format = param["format"];
                if (typeof format !== "undefined") {
                    $(target).mask(format, {reverse: true});
                }
            },
            ForceReadOnly: function (target) {
                $(target).attr("readonly", "readonly");
            },
            ObjIdDisplay: function (target) {
                $(target).attr("readonly", "readonly");
                if (["C", "RC"].indexOf(request.param.mode) !== -1) {
                    $(target).val("자동생성");
                }
            },
            SetPathTypeModeC: function (target) {
                if (["C", "RC"].indexOf(request.param.mode) !== -1 || $(target).val() == "") {
                    $(target).find("option").filter(function () {
                        return $(this).val() != "";
                    }).prop("selected", true);
                }
            },
            InitSelectBox: function (target, param) {
                if (!DE.fn.isempty(param.value)) {
                    $(target).val(param.value);
                }
                if (!DE.fn.isempty(param.disabled)) {
                    $(target).prop("disabled", param.disabled);
                }
            },
            IsChange: function (target, param) {
                $(target).attr("isChange", "Y");
                $(target).attr("isChangeCheck", "N");
            },
            IsChangeCheckAll: function (target, param) {
                return true;
            },
            IsChangeCheck: function (target, param) {
                if ($(target).attr("isChange") == "Y") {
                    if ($(target).attr("isChangeCheck") == "Y") {
                        return true;
                    } else {
                        DE.box.alert($(target).closest("div").parent().siblings("label").text() + " 값이 확인 되지 않았습니다.", function () {
                            $(target).focus();
                        });
                        return false;
                    }
                } else {
                    return true;
                }
            },
            SqlVerify: function (target, param) {
                var query = $.trim($(target).closest("div").children(":first").val());
                if (query == "") {
                    DE.box.alert($.trim($(target).closest("div").parent().siblings("label").text()) + " 값이 없습니다.", function () {
                        var uls = $("[id^=ATR_ID_SEQ_" + param["paramAtrIdSeq"] + "_]", $(target).closest("[role=object-area]")).closest("div.input-group");
                        for (var i = uls.length - 1; i >= 0; i--) {
                            if (i == 0) {
                                uls.eq(i).find("input").eq(0).val("");
                                uls.eq(i).find("input").eq(1).val("");
                            } else {
                                uls.eq(i).remove();
                            }
                        }
                    });
                    $(target).closest("div").children(":first").attr("isChangeCheck", "Y");
                } else {

                    var regex = /#\{\s?(.*?)\s?\}/g;
                    var matches, queryParamList = [];
                    while (matches = regex.exec(query)) {
                        queryParamList.push($.trim(matches[1]));
                    }
                    var curParamKeyArray = new Array();
                    var curParamDescArray = new Array();
                    var uls = $("[id^=ATR_ID_SEQ_" + param["paramAtrIdSeq"] + "_]", $(target).closest("[role=object-area]")).closest("div.input-group");
                    for (var i = 0, len = uls.length; i < len; i++) {
                        curParamKeyArray.push(uls.eq(i).find("input").eq(0).val());
                        curParamDescArray.push(uls.eq(i).find("input").eq(1).val());
                    }

                    var uniqParmaList = new Array();
                    if (queryParamList.length === 0) {
                    	uniqParmaList.push("파라미터없음");
                        for (var j = uls.length - 1; j >= 0; j--) {
                            if (j == 0) {
                                uls.eq(j).find("input").eq(0).val("파라미터없음");
                                uls.eq(j).find("input").eq(1).val("");
                            } else {
                                uls.eq(j).remove();
                            }
                        }
                    } else {
                        for (var i = 0, subLen = queryParamList.length; i < subLen; i++) {
                            var queryParam = queryParamList[i];
                            var isChk = false;
                            for (var j = 0; j < uniqParmaList.length; j++) {
                                if (uniqParmaList[j] == queryParam) {
                                    isChk = true;
                                    break;
                                }
                            }
                            if (!isChk) {
                                uniqParmaList.push(queryParam);
                            }
                        }

                        var i = 0, subLen = uniqParmaList.length;
                        for (; i < subLen; i++) {
                            var ul;
                            if (uls.eq(i).length == 0) {
                                ul = uls.eq(0).clone();
                                uls.parent().append(ul);
                            } else {
                                ul = uls.eq(i);
                            }

                            ul.find("input").eq(0).val(uniqParmaList[i]);
                            var isMatch = false;
                            for (var j = 0, curParamKeyLen = curParamKeyArray.length; j < curParamKeyLen; j++) {
                                if (uniqParmaList[i] == curParamKeyArray[j]) {
                                    ul.find("input").eq(1).val(curParamDescArray[j]);
                                    isMatch = true;
                                }
                            }
                            if (!isMatch) {
                                ul.find("input").eq(1).val("");
                            }
                        }


                        if (uniqParmaList.length > 0) {
                            for (var j = uls.length - 1; j >= i; j--) {
                                if (uls.eq(j).length != 0) {
                                    uls.eq(j).remove();
                                } else {
                                    break;
                                }
                            }
                        }


                    }

                    /*if (uniqParmaList.length == 0) {
                        uls.eq(0).find("input").eq(0).val("파라미터없음");
                        uls.eq(0).find("input").eq(1).val("");
                        uniqParmaList.push("파라미터없음");
                    } else {
                        for (var j = 0; j < uniqParmaList.length - 1; j++) {
                            if (j == 0) {
                                uls.eq(j).find("input").eq(0).val(uniqParmaList[j]);
                                uls.eq(j).find("input").eq(1).val("");
                            }
                        }
                    }*/

                    var html = "<div id='queryValidCheck-form' title='SQL 구문체크'>";
                    html += "    <table class='Layer_popup_table' cellspacing='0' cellpadding='0'>";
                    html += "        <colgroup>";
                    html += "            <col />";
                    html += "            <col />";
                    html += "        </colgroup>";
                    html += "        <tbody> ";
                    html += "            <tr>";
                    html += "                <th>Parameter</th>";
                    html += "                <th>Value</th>";
                    html += "            </tr>";
                    for (var i = 0; i < uniqParmaList.length; i++) {
                        html += "            <tr>";
                        html += "               <td>" + uniqParmaList[i] + "</td>";
                        html += "               <td><input type=text size=10/></td>";
                        html += "            </tr>";
                    }
                    html += "        </tbody>";
                    html += "    </table>";
                    html += "</div>";
                    $(document.body).append($(html));
                    $("#queryValidCheck-form").dialog({
                        autoOpen: false,
                        height: 300,
                        width: 350,
                        modal: true,
                        buttons: {
                            "확인": function () {
                                var queryParam = [];
                                var list = $("#queryValidCheck-form").find("table tbody tr");
                                for (var i = 1; i < list.length; i++) {
                                    var key = list.eq(i).find("td").eq(0).html();
                                    var value = list.eq(i).find("td").eq(1).find("input").val();
                                    queryParam.push(JSON.parse('{"' + key + '":"' + value + '"}'));
                                }

                                var jdbcId = $("[id^=ATR_ID_SEQ_" + param["jdbcAtrIdSeq"] + "_]", $(target).closest("[role=object-area]")).val()
                                var reqParam = {
                                    "query": query,
                                    "queryParam": queryParam,
                                    "jdbcId": jdbcId
                                };

                                var result = DE.ui.ajax.execute("../portal/qm/br/qmBr/queryValidCheck", reqParam);
                                if (result["status"] == "SUCCESS") {
                                    alert(result["message"]);
                                    $("textarea", $(target).closest("div.input-group")).attr("isChangeCheck", "Y");
                                    $(this).dialog("close");
                                } else {
                                    alert(result["message"]);
                                }
                            },
                            "닫기": function () {
                                $(this).dialog("close");
                            }
                        },
                        close: function () {
                            $("#queryValidCheck-form").remove();
                        }
                    });
                    $("#queryValidCheck-form").dialog("open");
                }
            },
            call_dpmFormatSetting: function (target, param) {
                if (request.param.mode != "C") {
                    $(target).prop("disabled", "disabled");
                }
                var isChk = false;
                for (var i = 0, len = param.value.length; i < len; i++) {
                    if ($(target).val() == param.value[i]) {			//날짜형태
                        for (var j = 0, subLen = param.atrIdSeq.length; j < subLen; j++) {
                            $("[id^=ATR_ID_SEQ_" + param.atrIdSeq[j] + "_]").closest("div.input-group").parent().show();
                        }
                        isChk = true;
                        break;
                    }
                }
                if (!isChk) {
                    for (var j = 0, len = param.atrIdSeq.length; j < len; j++) {
                        $("[id^=ATR_ID_SEQ_" + param.atrIdSeq[j] + "_]").closest("div.input-group").parent().hide();
                    }
                }
            },
            datePatternVerify: function (target, param) {
                var parameter = {
                    format: $(target).closest("ul").find("li:first > input").val()
                };
                var result = DE.ui.ajax.execute("json/datePatternVerify", parameter);
                if (result.rows == true) {
                    $(target).closest("ul").find("li:nth-child(1) > input").attr("isChangeCheck", "Y");
                    $(target).closest("ul").find("li:nth-child(1) > input").attr("USE_DATA", $(target).closest("ul").find("li:nth-child(1) > input").val());
                    DE.box.alert("유효한 Date Format 입니다.");
                } else {
                    $(target).closest("ul").find("li:nth-child(1) > input").attr("isChangeCheck", "N");
                    $(target).closest("ul").find("li:nth-child(1) > input").attr("USE_DATA", $(target).closest("ul").find("li:nth-child(1) > input").val());
                    DE.box.alert("유효하지 않은 Date Format 입니다.");
                }
            },
            call_selectedColumn: function (target, param) {
                var uiCompCd = "";
                var targetObjectName = "";
                if (!DE.fn.isempty(param)) {
                    uiCompCd = param.uiCompCd;
                    targetObjectName = $(target).prop("id");
                } else {
                    targetObjectName = $(target).closest("div").children(":first").prop("id");
                }
                DE.ui.open.popup(
                    "view",
                    ["selectedColumn"],
                    {
                        viewname: 'popup/columnSelected',
                        targetObjectName: targetObjectName,
                        uiCompCd: uiCompCd
                    },
                    "width=800, height=500, toolbar=non, menubar=no"
                );
            },
            call_selectedTable: function (target, param) {
                var uiCompCd = "";
                var targetObjectName = "";
                if (!DE.fn.isempty(param)) {
                    uiCompCd = param.uiCompCd;
                    targetObjectName = $(target).prop("id");
                } else {
                    targetObjectName = $(target).closest("div").children(":first").prop("id");
                }
                DE.ui.open.popup(
                    "view",
                    ["selectedTable"],
                    {
                        viewname: 'popup/tableSelected',
                        targetObjectName: targetObjectName,
                        uiCompCd: uiCompCd
                    },
                    "width=800, height=500, toolbar=non, menubar=no"
                );
            },
            set_defaultValue: function (target, val) {
                $(target).val(val);
            },
            call_TermCheckPopup: function (target, param) {
            	var targetObjectName = $(target).closest("span").prev().prop("id");
                var termName = $(target).closest("span").prev().val();
                DE.ui.open.popup(
                    "view",
                    ["termChecker"],
                    {
                        viewname: 'common/metacore/termChecker',
                        targetObjectName: targetObjectName,
                        objTypeId: request.param.objTypeId,
                        objId: request.param.objId,
                        termNm: termName
                    },
                    "width=1000, height=500, toolbar=non, menubar=no"
                );
            },
            call_CommGrpStdTermPopup: function (target, param) {
            	var targetObjectName = $(target).closest("span").prev().prop("id");
                var termName = $(target).closest("span").prev().val();
                DE.ui.open.popup(
                    "view",
                    ["termChecker"],
                    {
                        viewname: 'common/metacore/termChecker',
                        targetObjectName: targetObjectName,
                        objTypeId: request.param.objTypeId,
                        objId: request.param.objId,
                        termNm: termName,
                        "callback":"DE.validator.callback_setCommCdGrpStdNm"
                    },
                    "width=1000, height=500, toolbar=non, menubar=no"
                );
            },
            callback_setCommCdGrpStdNm: function () {
            	var rowData = arguments[0];
            	$("[id^=ATR_ID_SEQ_4_]").val(rowData["KOR_TERM"].replace(/[_]/g, ""));
            	$("[id^=ATR_ID_SEQ_4_]").attr("USE_DATA", rowData["KOR_TERM"].replace(/[_]/g, ""));
            	$("[id^=ATR_ID_SEQ_101_], [id^=ATR_ID_SEQ_109_]").val(rowData["ENG_TERM"]);
            },
            call_SelectedDomainPopup: function (target, param) {
                var targetObjectName = $(target).closest("span").prev().prop("id");
                var displayName = $(target).closest("div").parent().siblings("label").text();
                displayName = displayName.trim();
                DE.ui.open.popup(
                    "view",
                    ["domainSelected"],
                    {
                        viewname: 'common/metacore/domainSelected',
                        targetObjectName: targetObjectName,
                        "displayName": displayName
                    },
                    "width=800, height=500, toolbar=non, menubar=no"
                );
            },
            call_stdDataLenInputSet: function (target, param) {
                var val = $(target).val();
                if (val == "" || val == "DATE") {
                    $("[id^=ATR_ID_SEQ_" + param.atrIdSeq[0] + "]").val("");
                    ForceReadOnly($("[id^=ATR_ID_SEQ_" + param.atrIdSeq[0] + "]"));
                    $("[id^=ATR_ID_SEQ_" + param.atrIdSeq[0] + "]").prop("readonly", true);
                } else {
                    if (!DE.fn.isempty(param.isInit) && param.isInit == "true") {
                        $("[id^=ATR_ID_SEQ_" + param.atrIdSeq[0] + "]").val("");
                    }
                    $("[id^=ATR_ID_SEQ_" + param.atrIdSeq[0] + "]").prop("readonly", false);
                    InputMask($("[id^=ATR_ID_SEQ_" + param.atrIdSeq[0] + "]"), [188]);
                }
            },
            call_makeStdDomainName: function (target, param) {
                var targetAtrIdSeq = param.targetAtrIdSeq;
                var target = $("[id^=ATR_ID_SEQ_" + targetAtrIdSeq + "_]");
                var atrIdSeqs = param.atrIdSeq;
                var domainName = "";
                if ($("[id^=ATR_ID_SEQ_" + atrIdSeqs[0] + "_]").val() != "") {
                    domainName += $("[id^=ATR_ID_SEQ_" + atrIdSeqs[0] + "_] option:selected").attr("param1");
                }
                if ($("[id^=ATR_ID_SEQ_" + atrIdSeqs[1] + "_]").val() != "") {
                    domainName += $("[id^=ATR_ID_SEQ_" + atrIdSeqs[1] + "_]").val();
                }
                if ($("[id^=ATR_ID_SEQ_" + atrIdSeqs[2] + "_]").val() != "") {
                    domainName += $("[id^=ATR_ID_SEQ_" + atrIdSeqs[2] + "_]").val().replace(/,/g, ".");
                }
                target.val(domainName);
            },
            call_stdWordVerify: function (target, param) {
                var engTarget = $("[id^=ATR_ID_SEQ_" + param["engAtrIdSeq"] + "_]");
                var korTarget = $("[id^=ATR_ID_SEQ_" + param["korAtrIdSeq"] + "_]");

                if (engTarget.val().trim() == "") {
                    DE.box.alert($(engTarget).closest("div").parent().siblings("label").text() + " 값을 입력하세요.", function () {
                        $(engTarget).focus();
                    });
                    return;
                } else {
                    if (!/^([A-Z0-9])+$/.test(engTarget.val())) {
                        DE.box.alert($(engTarget).closest("div").parent().siblings("label").text() + " 은(는) 영대문자 or 숫자의 조합만 가능합니다.");
                        return;
                    }
                }

                if (korTarget.val().trim() == "") {
                    DE.box.alert($(korTarget).closest("div").parent().siblings("label").text() + " 값을 입력하세요.", function () {
                        $(korTarget).focus();
                    });
                    return;
                }

                var rsp = DE.ajax.call({
                    async: false,
                    url: "../portal/biz/std?oper=wordDuplCheck",
                    data: {
                        objTypeId: request.param.objTypeId,
                        objId: request.param.objId,
                        engNm: engTarget.val().trim(),
                        korNm: korTarget.val().trim()
                    }
                });
                if (rsp.status == "SUCCESS") {
                    if (!DE.fn.isempty(rsp.data.similarWords)) {
                        var message = "[유사단어]\n";
                        message += "--------------------------\n";
                        for (var i = 0, len = rsp.data.similarWords.length; i < len; i++) {
                            message += rsp.data.similarWords[i].OBJ_NM;
                            message += " | ";
                            message += rsp.data.similarWords[i].ADM_OBJ_ID;
                            message += "\n";
                        }
                        message += "--------------------------\n";
                        message += "표준화관리 담당자에게 문의하세요.\n";
                        DE.box.alert(message);
                    } else {
                        DE.box.alert(rsp.message);
                    }

                    $(target).closest("div").children(":first").attr("USE_DATA", $(target).closest("div").children(":first").val());
                    $(engTarget).attr("isChange", "N");
                    $(korTarget).attr("isChange", "N");
                    $(engTarget).attr("isChangeCheck", "Y");
                    $(korTarget).attr("isChangeCheck", "Y");
                } else {
                    DE.box.alert(rsp.message, function () {
                        $(engTarget).attr("isChangeCheck", "N");
                        $(korTarget).attr("isChangeCheck", "N");
                    });
                }
            },
            call_stdEngAbbrInputSet: function (target, param) {
                //$(target).css("ime-mode", "disabled");
                //$(target).css("text-transform","uppercase");
                $(target).on("keyup", function (e) {
                    if (!(event.keyCode >= 37 && event.keyCode <= 40)) {
                        var inputVal = $(this).val();
                        //$(this).val(inputVal.replace(/[^a-z]/gi,'').toUpperCase());
                        $(this).val(inputVal.replace(/[^a-z0-9]/gi, '').toUpperCase());
                    }
                });
            },
            connectionTest: function (target, param) {
                var params = [
                    $("[id^=ATR_ID_SEQ_" + param.jdbcDriver + "_]"),
                    $("[id^=ATR_ID_SEQ_" + param.jdbcUrl + "_]"),
                    $("[id^=ATR_ID_SEQ_" + param.userId + "_]"),
                    $("[id^=ATR_ID_SEQ_" + param.password + "_]")
                ];

                var p;
                for (var i = 0, len = params.length; i < len; i++) {
                    if (params[i].val() == "") {
                        DE.box.alert(params[i].closest("div").parent().siblings("label").text() + " 값을 입력하세요.", function () {
                            params[i].focus();
                        });
                        return;
                    }
                }
                p = {
                    jdbcDriver: params[0].val(),
                    jdbcUrl: params[1].val(),
                    userId: params[2].val(),
                    password: params[3].val()
                };

                var result = DE.ui.ajax.execute("../portal/biz/std?oper=jdbcConnectTest", p);

                DE.box.alert(result["message"]);
                if (result["status"] == "SUCCESS") {
                    params[0].attr("isChangeCheck", "Y");
                    params[1].attr("isChangeCheck", "Y");
                    params[2].attr("isChangeCheck", "Y");
                    params[3].attr("isChangeCheck", "Y");
                }
            },
            call_controlViewHandler: function (target, param) {
                var targetValue = $(target).val();
                var keys = $.keys(param);
                $.each(keys, function (key, value) {
                    var tv = eval("param[" + value + "]");
                    if (targetValue == tv) {
                        $("[id^=ATR_ID_SEQ_" + value + "_").closest("div.input-group").parent().show();
                    } else {
                        $("[id^=ATR_ID_SEQ_" + value + "_").val("");
                        $("[id^=ATR_ID_SEQ_" + value + "_").closest("div.input-group").parent().hide();
                    }
                });
            },
            call_externalSchemaList: function (target, param) {
                var connAtrIdSeq = param['connAtrIdSeq'];
                var connAtrIdSeqObj = null;
                if ("" == $("[id^=ATR_ID_SEQ_" + connAtrIdSeq + "_").val()) {
                    DE.box.alert($("[id^=ATR_ID_SEQ_" + connAtrIdSeq + "_").closest("div").parent().siblings("label").text() + " 값을 선택하세요.", function () {
                        $("[id^=ATR_ID_SEQ_" + connAtrIdSeq + "_").focus();
                    });
                    return;
                }

                var connObjId = $("[id^=ATR_ID_SEQ_" + connAtrIdSeq + "_").val();
                DE.ui.open.popup(
                    "view",
                    ["externalSchemaSelected"],
                    {
                        viewname: 'popup/externalSchemaSelected',
                        connObjId: connObjId,
                        schemas: $(target).closest("div").children(":first").val(),
                        targetObjectName: $(target).closest("div").children(":first").prop("id")
                    },
                    "width=520, height=420, toolbar=non, menubar=no"
                );
            },
            call_externalTableList: function (target, param) {
                var connAtrIdSeq = param['connAtrIdSeq'];
                var connAtrIdSeqObj = null;
                if ("" == $("[id^=ATR_ID_SEQ_" + connAtrIdSeq + "_").val()) {
                    DE.box.alert($("[id^=ATR_ID_SEQ_" + connAtrIdSeq + "_").closest("div").parent().siblings("label").text() + " 값을 선택하세요.", function () {
                        $("[id^=ATR_ID_SEQ_" + connAtrIdSeq + "_").focus();
                    });
                    return;
                }

                var connObjId = $("[id^=ATR_ID_SEQ_" + connAtrIdSeq + "_").val();
                DE.ui.open.popup(
                    "view",
                    ["jobTableSelected"],
                    {
                        viewname: 'popup/jobTableSelected',
                        connObjId: connObjId,
                        targetObjectName: $(target).closest("div").children(":first").prop("id")
                    },
                    "width=800, height=500, toolbar=non, menubar=no"
                );
            },
            /**
             * system etl job 파라메터는 배치 작업으로 수행되므로 startDate, endDate 두개의 파라메터만 가진다.
             */
            jobSqlParamCheck: function (target, param) {
                var sql = $.trim($(target).closest("div").children(":first").val());
                var regex = /#{([^{]*)[}]/g;

                if (sql == "") {
                    DE.box.alert($.trim($(target).closest("div").parent().siblings("label").text()) + " 값이 없습니다.", function () {
                        $(target).closest("li").prev().children(":first").attr("isChangeCheck", "Y");
                        var uls = $("[id^=ATR_ID_SEQ_" + param.atrIdSeq + "_]").closest("div.input-group");
                        for (var i = uls.length - 1; i >= 0; i--) {
                            if (i == 0) {
                                uls.eq(i).find("input").eq(0).val("파라미터없음");
                                uls.eq(i).find("input").eq(1).val("");
                            } else {
                                uls.eq(i).remove();
                            }
                        }
                    });
                } else {
                    var matches, sqlParamList = [];
                    while (matches = regex.exec(sql)) {
                        sqlParamList.push(matches[1].trim());
                    }

                    var curParamKeyArray = new Array();
                    var curParamDescArray = new Array();
                    var uls = $("[id^=ATR_ID_SEQ_" + param.paramAtrIdSeq + "_]").closest("div.input-group");
                    for (var i = 0, len = uls.length; i < len; i++) {
                        curParamKeyArray.push(uls.eq(i).find("input").eq(0).val());
                        curParamDescArray.push(uls.eq(i).find("input").eq(1).val());
                    }

                    var uniqParmaList = [];
                    $.each(sqlParamList, function (i, e) {
                        if ($.inArray(e, uniqParmaList) == -1) uniqParmaList.push(e);
                    });
                    if (sqlParamList != null) {
                        for (var i = 0, len = uniqParmaList.length; i < len; i++) {
                            var ul;
                            if (uls.eq(i).length == 0) {
                                ul = uls.eq(0).clone();
                                uls.parent().append(ul);
                            } else {
                                ul = uls.eq(i);
                            }

                            ul.find("input").eq(0).val(uniqParmaList[i]);
                            var isMatch = false;
                            for (var j = 0, subLen = curParamKeyArray.length; j < subLen; j++) {
                                if (uniqParmaList[i] == curParamKeyArray[j]) {
                                    ul.find("input").eq(1).val(curParamDescArray[j]);
                                    isMatch = true;
                                }
                            }
                            if (!isMatch) {
                                ul.find("input").eq(1).val("");
                            }
                        }
                        for (var j = uls.length - 1; j >= i; j--) {
                            if (uls.eq(j).length != 0) {
                                uls.eq(j).remove();
                            } else {
                                break;
                            }
                        }
                    } else {
                        uniqParmaList.push("파라미터없음");
                        for (var j = uls.length - 1; j >= 0; j--) {
                            if (j == 0) {
                                uls.eq(j).find("input").eq(0).val("파라미터없음");
                                uls.eq(j).find("input").eq(1).val("");
                            } else {
                                uls.eq(j).remove();
                            }
                        }
                    }
                    DE.box.alert("성공적으로 수행하였습니다.!");
                    $(target).closest("div").children(":first").attr("isChangeCheck", "Y");
                }
            },
            call_dbPassChg: function (target, atrIdSeq) {
                var id = $(target).closest("div").children(":first").prop("id");
                var atrIdSeq = id.substring("ATR_ID_SEQ_".length).substring(0, id.substring("ATR_ID_SEQ_".length).indexOf("_"));
                var passChgDialog = $("<div id='passChg-form' title='패스워드 변경'/>").load("view", {viewname: "simple/common/passwdChg2"}).appendTo('body').dialog({
                    autoOpen: false,
                    height: 250,
                    width: 450,
                    modal: true,
                    buttons: {
                        "변경하기": function () {
                            if (confirm("변경 하시겠습니까?")) {
                                if ($("div#passChg-form input#oldPassword").val() == "") {
                                    DE.box.alert("현재비밀번호를 입력하세요.", function () {
                                        $("div#passChg-form input#oldPassword").focus();
                                    });
                                    return;
                                }
                                if ($("div#passChg-form input#newPassword").val() == "") {
                                    DE.box.alert("변경비밀번호를 입력하세요.", function () {
                                        $("div#passChg-form input#newPassword").focus();
                                    });
                                    return;
                                }

                                var result = DE.ui.ajax.execute("json/passwordAtrChg", {
                                    objTypeId: request.param.objTypeId,
                                    objId: request.param.objId,
                                    atrIdSeq: atrIdSeq,
                                    atrValSeq: "101",
                                    oldPassword: $("div#passChg-form input#oldPassword").val(),
                                    newPassword: $("div#passChg-form input#newPassword").val()
                                });
                                if (result["status"] == "SUCCESS") {
                                    DE.box.alert(result.response.message, function () {
                                        passChgDialog.dialog("close");
                                    });
                                }
                            }
                        },
                        "닫기": function () {
                            passChgDialog.dialog("close");
                        }
                    },
                    close: function () {
                        passChgDialog.remove();
                    }
                });
                passChgDialog.dialog("open");
                $(target).closest("div").children(":first").attr("USE_DATA", $(target).closest("div").children(":first").val());
            },
            setDefaultValue: function (target, param) {
                $(target).val(param["value"]);
            },
            call_pathObjectPopup: function (target, objTypeId) {
                if (DE.fn.isempty(objTypeId)) {
                    objTypeId = $(target).closest("body").find("[id^=ATR_ID_SEQ_1_]").val();
                }

                var id = $(target).closest("span").prev().prop("id");
                var atrIdSeq = id.substring(11).substring(0, id.substring(11).indexOf("_"));
                var displayName = $(target).closest("div").parent().siblings("label").text();
                displayName = displayName.trim();
                DE.ui.open.popup(
                    "view",
                    ["pathObjectTreePopup"],
                    {
                        "viewname": 'common/metacore/pathObjectTreeSelected',
                        "targetObjectName": id,
                        "objTypeId": objTypeId,
                        "atrIdSeq": atrIdSeq,
                        "displayName": displayName
                    },
                    "width=800, height=500, toolbar=non, menubar=no"
                );
            },
            call_pathObjectTreePopup: function (target, objTypeId) {
                if (DE.fn.isempty(objTypeId)) {
                    objTypeId = $(target).closest("body").find("[id^=ATR_ID_SEQ_1_]").val();
                }

                var id = $(target).closest("span").prev().prop("id");
                var atrIdSeq = id.substring(11).substring(0, id.substring(11).indexOf("_"));
                var displayName = $(target).closest("div").parent().siblings("label").text();
                displayName = displayName.trim();
                DE.ui.open.popup(
                    "view",
                    ["pathObjectTreePopup"],
                    {
                        "viewname": 'common/metacore/pathObjectTreeSelected',
                        "targetObjectName": id,
                        "objTypeId": objTypeId,
                        "atrIdSeq": atrIdSeq,
                        "displayName": displayName
                    },
                    "width=800, height=500, toolbar=non, menubar=no"
                );
            },
            call_atrSbstPopup: function (target, objTypeId) {		//call_subjectAtrePopup
            	debugger;
                if (DE.fn.isempty(objTypeId)) {
                    objTypeId = $(target).closest("body").find("[id^=ATR_ID_SEQ_1_]").val();
                }

                var id = $(target).closest("span").prev().prop("id");
                var atrIdSeq = id.substring(11).substring(0, id.substring(11).indexOf("_"));
                var displayName = $(target).closest("div").parent().siblings("label").text();
                displayName = displayName.trim();
                DE.ui.open.popup(
                    "view",
                    ["subjectAtrePopup"],
                    {
                        "viewname": 'common/metacore/atrSbstSelected',
                        "targetObjectName": id,
                        "objTypeId": objTypeId,
                        "atrIdSeq": atrIdSeq,
                        "displayName": displayName
                    },
                    "width=800, height=500, toolbar=non, menubar=no"
                );
            },
            setButtonCaption: function (target, param) {
                if (!DE.fn.isempty(param["caption"])) {
                    $(target).val(param["caption"]);
                }
            },
            call_innerObject: function (target, param) {
                var pObjTypeId = $("div#obj_info").attr("objTypeId");
                var pObjId = $("div#obj_info").attr("objId");
                var objTypeId = $(target).val();
                if ("" == objTypeId) {
                    return;
                }
                var parameter = {
                    "pObjTypeId": pObjTypeId,
                    "pObjId": pObjId,
                    "objTypeId": objTypeId
                };

                DE.metaui.subCreate(parameter);
            },
            user: {
                ForceReadOnly: function (target) {
                    $(target).attr("readonly", "readonly");
                },
                dupCheck: function (target, param) {
                    var obj = $(target).closest(".input-group").find("[id^=ATR_ID_SEQ_]");
                    var displayName = $(target).closest("div").parent().siblings("label").text();
                    if (DE.fn.isempty(obj.val().trim())) {
                        DE.box.alert(displayName + " 값을 입력하세요.", function () {
                            obj.focus();
                        });
                        return;
                    }

                    var rsp = DE.ajax.call({
                        async: false,
                        url: "system/userRole/duplCheck",
                        data: {keyVal: obj.val().trim()}
                    });

                    var message;
                    if (rsp.status == "SUCCESS") {
                        message = "등록가능한 " + displayName + " 입니다.";
                        DE.box.alert(message);

                        obj.attr("USE_DATA", obj.val());
                        obj.attr("isChange", "N");
                        obj.attr("isChangeCheck", "Y");
                    } else {
                        message = displayName + " 가 존재합니다.";
                        DE.box.alert(message);
                        obj.attr("isChangeCheck", "N");
                    }
                }
            },
            useDataSync: function(target) {
            	$(target).attr("USE_DATA", $(target).val());
            },
            BDP: {
            	userSelected : function(target) {
            		DE.ui.open.popup(
                        "view",
                        ["userSelected"],
                        {
                            viewname: 'common/metacore/bdp/userSelected',
                            targetObjectName: $("input:eq(0)", $(target).closest("[de-obj-role=atr]")).prop("id")
                        },
                        "width=780, height=500, toolbar=non, menubar=no"
                    );
            	},
            	deptSelected : function(target) {
            		DE.ui.open.popup(
                        "view",
                        ["deptSelected"],
                        {
                            viewname: 'common/metacore/bdp/deptSelected',
                            targetObjectName: $("input:eq(0)", $(target).closest("[de-obj-role=atr]")).prop("id")
                        },
                        "width=800, height=400, toolbar=non, menubar=no"
                    );
            	},
            	serviceCatgory : function(target) {
            		DE.ajax.call(
            			{
	                        async: true,
	                        url: "metapublic/list",
	                        data: {
	                        	"queryId":"bdp_custom.datasetServiceClass",
	                        	"dsetTypeId":request.param["objTypeId"],
	                        	"dsetId":request.param["objId"]
	                        }
            			},
	                    function(rsp){
            				var serviceNms = [];
            				$.each(rsp.data, function(index, value){
            					if (index === 7) {
            						serviceNms.push("...");
            						return false;
            					} else {
            						serviceNms.push(value["serviceNm"]);
            					}
            				});
            				var serviceNmsStr = serviceNms.join(", ");
            				$(target).val(serviceNmsStr);
	                    }
                    );
            	},
            	objRByRelObj : function(target, params) {
            		DE.ajax.call(
            			{
	                        async: true,
	                        url: "metapublic/list",
	                        data: {
	                        	"queryId":"metacore.getObjRByRelObj",
	                        	"relObjTypeId":request.param["objTypeId"],
	                        	"relObjId":request.param["objId"],
	                        	"objTypeIds":params["objTypeIds"]
	                        }
            			},
	                    function(rsp){
            				var objNms = [];
            				$.each(rsp.data, function(index, value){
            					objNms.push(value["objNm"]);
            				});
            				var objNmsStr = objNms.join(", ");
            				$(target).val(objNmsStr);
	                    }
                    );
            	},
            	dsgnTabNmStdTrans: function(target, params) {
            		var entityNm = $("input[id^=ATR_ID_SEQ_4_]").val();
            		DE.ajax.call(
            			{
	                        async: true,
	                        url: "portal/bdp?oper=dsgnTabNmStdTrans",
	                        data: {
	                        	"entityNm":entityNm
	                        }
            			},
	                    function(rsp){
            				$("input:text:eq(0)", $(target).closest("[de-obj-role=atr]")).val(rsp["data"]["stdTabNm"]);
            				$("input:text:eq(0)", $(target).closest("[de-obj-role=atr]")).attr("USE_DATA", rsp["data"]["stdTabNm"]);
	                    }
                    );
            	}
            }
        };

        return DE.validator;
    });

}).call(this);