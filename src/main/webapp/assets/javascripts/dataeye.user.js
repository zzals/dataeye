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
        componentFileName = 'dataeye.user.js';
        moduleName = 'DE.user';

        DE.user = {
            SBST_MAX_LENGTH: 2000,
            subjectLen: 2,
            contentLen: 10,
            initFn: "DE.validator.user.",
            getSubjectLen: function () {
                return this.subjectLen;
            },
            setSubjectLen: function (len) {
                var conLen = 12 - len;
                if (conLen === 0) {
                    conLen = 12;
                }
                this.setContentLen(conLen);
                this.subjectLen = len;
            },
            getContentLen: function () {
                return this.contentLen;
            },
            setContentLen: function (conLen) {
                this.contentLen = conLen;
            },
            removeItem: function (e) {
                $(e.target).closest(".input-group").remove();
            },
            create: function (parameter, body, atrInfo) {
                this.parameter = parameter;
                this.body = body;
                var _self = this;

                var html = [];
                html.push("<div class=\"basic_info_div\">");
                html.push("\t<div id=\"obj_info\" class=\"panel panel-default hidden\" role=\"object-area\">");
                // html.push("\t\t<div class=\"panel-heading\">");
                // html.push("\t\t\t<h3 class=\"panel-title\">기본 정보</h3>");
                // html.push("\t\t</div>");
                html.push("\t\t<div class=\"panel-body\">");
                html.push("\t\t\t<div id=\"obj_info_m\"></div>");
                html.push("\t\t</div>");
                html.push("\t</div>");

                // html.push("<div class=\"navbar navbar-blend navbar-fixed-bottom\">");
                // html.push("\t<div id=\"actionDiv\" class=\"text-right nav\">");
                // if (this.parameter.mode == "C") {
                //     html.push("\t\t<a id=\"btnAction\" class=\"btn btn-default btn-sm\" de-obj-action=\"save\" role=\"button\" href=\"#\"><i class=\"fa fa-floppy-o\"></i> 저장</a>");
                // }
                // else if (this.parameter.mode == "R") {
                //     html.push("\t\t<a id=\"btnAction\" class=\"btn btn-default btn-sm\" de-obj-action=\"modify\" role=\"button\" href=\"#\"><i class=\"fa fa-pencil\"></i> 수정</a>");
                //     html.push("\t\t<a id=\"btnAction\" class=\"btn btn-default btn-sm\" de-obj-action=\"delete\" role=\"button\" href=\"#\"><i class=\"fa fa-trash-o\"></i> 삭제</a>");
                // }
                // else if (this.parameter.mode == "U") {
                //     html.push("\t\t<a id=\"btnAction\" class=\"btn btn-default btn-sm\" de-obj-action=\"modify\" role=\"button\" href=\"#\"><i class=\"fa fa-floppy-o\"></i> 저장</a>");
                // }
                // else if (this.parameter.mode == "D") {
                //     // 버튼 없음
                // }
                // html.push("\t</div>");
                // html.push("</div>");

                html.push("</div>");

                $(this.body).empty();
                $(html.join("\n")).appendTo($(this.body));

                if (this.parameter.mode == "C" || this.parameter.mode == 'U') {
                    if(this.parameter.authInfo.C_AUTH == "Y" || this.parameter.authInfo.U_AUTH == "Y") {
                        var btn = [];
                        btn.push('<nav class="navbar navbar-default">');
                        btn.push('\t<div class="container-fluid">');
                        btn.push('\t\t<div class="collapse navbar-collapse">');
                        btn.push('\t\t\t<div id="actionDiv" class="navbar-form navbar-right de-obj-btn">');
                        btn.push('\t\t\t\t<a id="btnAction" class="btn btn-default btn-sm" de-obj-action="save" role="button" href="#"><i class="fa fa-floppy-o"></i> 저장</a>');
                        btn.push('\t\t\t</div>');
                        btn.push('\t\t</div>');
                        btn.push('\t</div>');
                        btn.push('</nav>');
                        $('#container > .content').prepend(btn.join("\n"));
                        $('.basic_info_div .panel').css('height', '462px');
                    }
                }

                _self.render("obj_info", atrInfo);

                //INIT Func 처리
                var inits = $("[init]", $("div#obj_info"));
                $.each(inits, function () {
                    eval($(this).attr("init"));
                });

                //SAVE
                $("#actionDiv").find("a[id=btnAction]").on("click", function (e) {
                    var str = "등록";
                    switch (_self.parameter.mode) {
                        case "C":
                            str = "등록";
                            break;
                        case "U":
                        case "M":
                            str = "수정";
                            break;
                        case "D":
                            str = "삭제";
                            break;
                    }
                    if (_self.parameter.mode == 'R') {
                        if ($(this).attr("de-obj-action") == 'delete') {
                        	alert("삭제로직");
                            DE.box.confirm("삭제 하시겠습니까?", function (b) {
                                if (b === true) {
                                    var result = DE.ajax.call({
                                        async: false,
                                        url:"userRole/delete",
                                        data: {keyVal:_self.parameter.keyVal}
                                    });

                                    if (result["status"] == "SUCCESS") {
                                        DE.box.alert("삭제 되었습니다.", function () {
                                            $.postMessage({
                                                action: 'refresh',
                                                mode: 'D'
                                            }, '*', window.parent.opener);
                                            top.close();
                                        });
                                    }
                                    else {
                                        DE.box.alert("삭제 실패하였습니다.");
                                    }
                                }
                                return;
                            });


                        }
                        else if ($(this).attr("de-obj-action") == 'modify') {
                            DE.ui.open.frame("common/metacore/objectInfoTab", top.name, {
                                action:'common/metacore/user/userContent',
                                schema:_self.parameter.schema,
                                keyVal:_self.parameter.keyVal,
                                mode:'U'
                            });
                        }
                    }
                    else {
                        DE.box.confirm(str + " 하시겠습니까?", function (b) {
                            if (b === true) {
                                var $body = $(document.body);
                                var result = _self.save($body);
                                if (!DE.fn.isempty(result) && result["status"] == "SUCCESS") {
                                    DE.box.alert(str + " 되었습니다.", function () {
                                        if (_self.parameter.callback != undefined) {
                                            eval(_self.parameter.callback);
                                            top.close();
                                        }
                                        else {
                                            if (_self.parameter.mode == "D") {
                                                top.close();
                                            }
                                            else {

                                                console.log(result["data"]["keyVal"]);
                                                DE.ui.open.frame("common/metacore/objectInfoTab", top.name, {
                                                    action:'common/metacore/user/userContent',
                                                    schema:_self.parameter.schema,
                                                    keyVal:result["data"]["keyVal"],
                                                    mode:'R'
                                                });
                                            }
                                        }
                                    });
                                }
                            }
                            return;
                        });
                    }
                });
            },
            render: function (oId, atrInfo) {
                this.atrInfo = atrInfo;
                var _self = this;

                this.setSubjectLen(2);

                $("#obj_info").removeClass("hidden");

                var htmlInline = "";
                var htmlInlineDet = "";

                //OBJ_BAS VALUE SETTING
                $.each(this.atrInfo, function () {
                    var objId = oId + "_m";
                    var objIdDet = oId + "_det_";

                    htmlInline = "<div id=\"" + objId + "_" + this.ATR_ID_SEQ + "\" class=\"col-sm-12 form-group\" />";
                    $("#" + objId).append(htmlInline);
                    _self.atrUI($("#" + objId + "_" + this.ATR_ID_SEQ), this);

                    htmlInlineDet = "<div id=\"" + objIdDet + this.ATR_ID_SEQ + "\" class=\"col-sm-" + _self.getContentLen() + "\" />";
                    $("#" + objId + "_" + this.ATR_ID_SEQ).append(htmlInlineDet);
                    _self.atrValUI($("#" + objIdDet + this.ATR_ID_SEQ), this);
                });
            },
            atrUI: function ($e, atr) {
                var html = "";
                var mandTag = atr.MAND_YN == "Y" ? "<i class=\"fa fa-star\" aria-hidden=\"true\" />" : "";
                var indcTag = atr.INDC_YN == "N" ? "display:none;" : "";

                if (this.parameter.mode == "C" || this.parameter.mode == "U") {
                    if (atr.INDC_YN == "R") {
                        indcTag = "display:none;";
                    }
                }
                if (indcTag === "display:none;") {
                    $e.attr("style", indcTag);
                }

                $e.attr("ATR_ID_SEQ", atr.ATR_ID_SEQ);
                $e.attr("MAND_YN", atr.MAND_YN);
                $e.attr("UI_COMP_CD", atr.UI_COMP_CD);

                html += "<label class=\"col-sm-" + this.getSubjectLen() + " control-label\" id=\"ATR_ID_SEQ_" + atr.ATR_ID_SEQ + "\">";
                html += (mandTag + atr.ATR_NM);
                html += "</label>";

                $e.append(html);
            },
            atrValUI: function ($e, atr) {

                switch (atr["UI_COMP_CD"]) {
                    case "SB":
                        this.atrValUI_SelectBox($e, atr);
                        break;
                    case "IB":
                    case "IB+BTN":
                        this.atrValUI_InputText($e, atr);
                        break;
                    case "PW":
                        this.atrValUI_Password($e, atr);
                        break;
                    case "TA":
                    case "TA+BTN":
                        this.atrValUI_Textarea($e, atr);
                        break;
                    case "CB":
                        this.atrValUI_Checkbox($e, atr);
                        break;
                    case "RB":
                        this.atrValUI_Radio($e, atr);
                        break;
                    case "CAL":
                        this.atrValUI_Calender($e, atr);
                        break;
                    case "RCAL":
                        this.atrValUI_RangeCalender($e, atr);
                        break;
                    case "FLE":
                        this.atrValUI_FILE($e, atr);
                        break;
                }
            },
            getSpanSize: function (atr) {
                var span = "span2";
                switch (atr.UI_COMP_CD) {
                    case "SB":
                    case "IB":
                    case "CAL":
                    case "PW":
                        span = "span4";
                        break;
                    default:
                        span = "span4";
                }
                return span;
            },
            getMultiActionTag: function (action) {
                var tag = "";
                if (action == "plus") {
                    // tag = "<div class='m_left5'><!-- fa-plus-square-o --><a href='#'><img role='multiplus' src='../resources/images/meta-viewer/plus_btn.png'></a></div>";
                    tag = "<span role=\"multiplus\" class=\"multi_square\"><a href=\"#\"><i class=\"fa fa-plus-square-o fa-sm\" /></a></span>";
                } else if (action == "minus") {
                    // tag = "<div class='m_left5'><!-- fa-minus-square-o --><a href='#'><img role='multiminus' src='../resources/images/meta-viewer/minus_btn.png'></a></div>";
                    tag = "<span role=\"multiminus\" class=\"multi_square\"><a href=\"#\"><i class=\"fa fa-minus-square-o fa-sm\" /></a></span>";
                }
                return tag;
            },
            getDisabled: function () {
                var disabled = "";
                if (this.parameter.mode == "R" || this.parameter.mode == "D") {
                    disabled = "disabled";
                }
                return disabled;
            },
            getObjectId: function (atrIdSeq) {
                var objectId = "";
                objectId = "ATR_ID_SEQ_" + atrIdSeq + "_" + DE.fn.guid.get();
                return objectId;
            },
            setDefaultAtrVal: function (atrVal, source) {
                var cretDt = (DE.fn.isempty(atrVal.CRET_DT)) ? "" : atrVal.CRET_DT;
                var cretrId = (DE.fn.isempty(atrVal.CRETR_ID)) ? "" : atrVal.CRETR_ID;

                source.attr("CRET_DT", cretDt);
                source.attr("CRETR_ID", cretrId);
            },
            setEventHandler: function (atr, target) {
                if (!DE.fn.isempty(atr.AVAIL_CHK_PGM_ID)) {
                    var funcs = JSON.parse(atr.AVAIL_CHK_PGM_ID);
                    var fn = [];
                    if (!DE.fn.isempty(funcs.init)) {
                        if (funcs.init instanceof Array) {
                            for (var i = 0, len = funcs.init.length; i < len; i++) {
                                fn.push(this.initFn + funcs.init[i].func + "(this, " + JSON.stringify(funcs.init[i].param) + ");");
                            }
                            target.attr("init", fn.join());
                        } else {
                            fn.push(this.initFn + funcs.init.func + "(this, " + JSON.stringify(funcs.init.param) + ");");
                            target.attr("init", fn.join());
                        }
                    }
                    if (!DE.fn.isempty(funcs.change)) {
                        fn = [];
                        if (funcs.change instanceof Array) {
                            for (var i = 0, len = funcs.change.length; i < len; i++) {
                                fn.push(this.initFn + funcs.change[i].func + "(this, " + JSON.stringify(funcs.change[i].param) + ");");
                            }
                            target.attr("onchange", fn.join());
                        } else {
                            fn.push(this.initFn + funcs.change.func + "(this, " + JSON.stringify(funcs.change.param) + ");")
                            target.attr("onchange", fn.join());
                        }
                    }
                    if (!DE.fn.isempty(funcs.beforeSubmit)) {
                        fn = [];
                        if (funcs.beforeSubmit instanceof Array) {
                            for (var i = 0, len = funcs.beforeSubmit.length; i < len; i++) {
                                fn.push(this.initFn + funcs.beforeSubmit[i].func + "(this, " + JSON.stringify(funcs.beforeSubmit[i].param) + ");");
                            }
                            target.attr("onBeforeSubmit", fn.join());
                        } else {
                            fn.push(this.initFn + funcs.beforeSubmit.func + "(this, " + JSON.stringify(funcs.beforeSubmit.param) + ");");
                            target.attr("onBeforeSubmit", fn.join());
                        }
                    }
                    if (!DE.fn.isempty(funcs.keyup)) {
                        fn = [];
                        if (funcs.keyup instanceof Array) {
                            for (var i = 0, len = funcs.keyup.length; i < len; i++) {
                                fn.push(this.initFn + funcs.keyup[i].func + "(this, " + JSON.stringify(funcs.keyup[i].param) + ");");
                            }
                            target.attr("onkeyup", fn.join());
                        } else {
                            fn.push(this.initFn + funcs.keyup.func + "(this, " + JSON.stringify(funcs.keyup.param) + ");");
                            target.attr("onkeyup", fn.join());
                        }
                    }
                }
            },
            atrValUI_SelectBox: function ($e, atr) {
                var tpl = $("<div class=\"input-group\" />").append("<select class=\"form-control selectpicker select-sm\" data-live-search=\"true\" data-style=\"select-sm\" />");
                tpl.find("select").append("<optgroup class=\"font-default\" />");
                var atrCode = atr["ATR_CODE"];
                if (!DE.fn.isempty(atrCode)) {
                    for (var i = 0, len = atrCode.length; i < len; i++) {
                        if (i == 0) {
                            var option = $("<option value=\"\">::선택하세요::</option>");
                            tpl.find("select > optgroup").append(option);
                        }
                        var option = $("<option value=\"" + atrCode[i]["CODE"] + "\">" + atrCode[i]["DISP_NAME"].replace(/ /g, "&nbsp;") + "</option>");

                        if (!DE.fn.isempty(atrCode[i]["PARAM1"])) {
                            option.attr("param1", atrCode[i]["PARAM1"]);
                        }
                        if (!DE.fn.isempty(atrCode[i]["PARAM2"])) {
                            option.attr("param2", atrCode[i]["PARAM2"]);
                        }
                        tpl.find("select > optgroup").append(option);
                    }
                }
                this.setEventHandler(atr, tpl.find("select"));
                tpl.find("select").prop("disabled", this.getDisabled());

                var source = tpl.clone();
                var id = this.getObjectId(atr.ATR_ID_SEQ);
                source.find("select").prop("id", id).prop("name", id);
                source.find("select").val(atr["OBJ_ATR_VAL"]);
                this.setDefaultAtrVal(atr, source.find("select"));

                $e.append(source);
                $("#" + id).selectpicker();
            },
            atrValUI_InputText: function ($e, atr) {
                var tpl = [];
                if (this.parameter.mode == "R" || this.parameter.mode == "D") {
                    tpl = $("<div class=\"input-group\" />");

                    var source = tpl.clone();
                    var id = this.getObjectId(atr.ATR_ID_SEQ);
                    var text = (!DE.fn.isempty(atr["EXTEND_OBJ_ATR_VAL"])) ? atr["EXTEND_OBJ_ATR_VAL"] : "";
                    source.append("<input type=\"text\" id=\"" + id + "\" name=\"" + id + "\" class=\"form-control input-sm\" />");
                    source.find("input[type=text]").prop("disabled", this.getDisabled());
                    $e.append(source);
                    $("#" + id).val(text);
                } else {
                    tpl = $("<div class=\"input-group\" />").append("<input class=\"form-control input-sm\" type=\"text\" value=\"\" />");
                    if (atr.UI_COMP_CD == "IB+BTN") {
                        tpl.append("<span class=\"input-group-btn\"><input type=\"button\" value=\"\" class=\"btn btn-primary btn-sm\" /></span>");
                    }
                    this.setEventHandler(atr, tpl.find("input[type=text]"));
                    if (!DE.fn.isempty(atr["AVAIL_CHK_PGM_ID"])) {
                        var funcs = JSON.parse(atr["AVAIL_CHK_PGM_ID"]);
                        if (atr["UI_COMP_CD"] == "IB+BTN" && !DE.fn.isempty(funcs.click)) {
                            tpl.find("input:button").attr("onclick", "javascript:" + this.initFn + funcs.click.func + "(this, " + JSON.stringify(funcs.click.param) + ");");
                            tpl.find("input:button").val(funcs.click.caption);
                        }
                    }

                    var objAtrVal = (DE.fn.isempty(atr["OBJ_ATR_VAL"])) ? "" : atr["OBJ_ATR_VAL"];
                    var extendObjAtrVal = (DE.fn.isempty(atr["EXTEND_OBJ_ATR_VAL"])) ? "" : atr["EXTEND_OBJ_ATR_VAL"];

                    var source = tpl.clone();
                    var id = this.getObjectId(atr.ATR_ID_SEQ);
                    source.find("input[type=text]").prop("id", id).prop("name", id);
                    source.find("input[type=text]").val($("<div />").html(extendObjAtrVal).text());
                    if (atr["UI_COMP_CD"] == "IB+BTN" && !DE.fn.isempty(funcs.click)) {
                        source.find("input[type=text]").attr("USE_DATA", objAtrVal);
                    }
                    this.setDefaultAtrVal(atr, source.find("input[type=text]"));
                    $e.append(source);
                }
            },
            atrValUI_Password: function ($e, atr) {
                var tpl = [];
                if (this.parameter.mode == "R" || this.parameter.mode == "D") {
                    tpl = $("<div class=\"input-group\" />");

                    var prefixAtrValSeq = "0";
                    for (var i = 0, len = atrVal.length; i < len; i++) {
                        if (prefixAtrValSeq != ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2)) {
                            var source = tpl.clone();
                            var id = this.getObjectId(atr.ATR_ID_SEQ);
                            var text = (DE.fn.isempty(atrVal[i]["EXTEND_OBJ_ATR_VAL"])) ? "" : atrVal[i]["EXTEND_OBJ_ATR_VAL"];
                            source.append("<input type=\"password\" id=\"" + id + "\" name=\"" + id + "\" class=\"form-control input-sm\" />");
                            source.find("input[type=password]").prop("disabled", this.getDisabled());
                            $e.append(source);
                            $("#" + id).val(text);
                            prefixAtrValSeq = ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2);
                        }
                    }
                } else {
                    tpl = $("<div class=\"input-group\" />").append("<input class=\"form-control input-sm\" type=\"password\" value=\"\" />");
                    this.setEventHandler(atr, tpl.find("input[type=password]"));
                    if (!DE.fn.isempty(atr["AVAIL_CHK_PGM_ID"])) {
                        tpl = $("<div class=\"col-sm-" + this.getContentLen() + "\" />").append("<div class=\"input-group\"><input class=\"form-control input-sm\" type=\"password\" value=\"\" /><span class=\"input-group-btn\"><input type=\"button\" value=\"\" class=\"btn btn-primary btn-sm\" /></span></div>");
                        var funcs = JSON.parse(atr["AVAIL_CHK_PGM_ID"]);
                        tpl.find("input:button").attr("onclick", "javascript:" + this.initFn + funcs.click.func + "(this, " + JSON.stringify(funcs.click.param) + ");");
                        tpl.find("input:button").val(funcs.click.caption);
                    }

                    var prefixAtrValSeq = "0";
                    var multiFirst = true;
                    for (var i = 0, len = atrVal.length; i < len; i++) {
                        var objAtrVal = (DE.fn.isempty(atrVal[i]["OBJ_ATR_VAL"])) ? "" : atrVal[i]["OBJ_ATR_VAL"];
                        var extendObjAtrVal = (DE.fn.isempty(atrVal[i]["EXTEND_OBJ_ATR_VAL"])) ? "" : atrVal[i]["EXTEND_OBJ_ATR_VAL"];
                        if (prefixAtrValSeq != ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2)) {
                            var source = tpl.clone();
                            var id = this.getObjectId(atr.ATR_ID_SEQ);
                            source.find("input[type=password]").prop("id", id).prop("name", id);
                            source.find("input[type=password]").val(extendObjAtrVal);
                            this.setDefaultAtrVal(atrVal[i], source.find("input[type=password]"));
                            if (atr["MULTI_ATR_YN"] == "Y") {
                                if (multiFirst) {
                                    source.append(this.getMultiActionTag("plus"));
                                    multiFirst = false;
                                } else {
                                    source.append(this.getMultiActionTag("minus"));
                                }
                            }

                            $e.append(source);
                            prefixAtrValSeq = ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2);
                        }
                    }
                }
            },
            atrValUI_Textarea: function ($e, atr) {
                var height = 90;
                switch (atr["UI_COMP_CD"]) {
                    case "TB1":
                    case "TO1":
                        height = 40;
                        break;
                    case "TB2":
                    case "TO2":
                        height = 60;
                        break;
                    case "TB3":
                    case "TO3":
                        height = 240;
                        break;
                }
                var tpl = $("<div class=\"input-group\" />").append("<textarea class=\"form-control\" style=\"height: " + height + "px;\"></textarea>");
                if ((this.parameter.mode != "R" && this.parameter.mode != "D") && atr["UI_COMP_CD"] == "TA+BTN") {
                    tpl = $("<div class=\"input-group\" />");
                    tpl.append("<textarea class=\"form-control\" style=\"height: " + height + "px;\"></textarea><span class=\"input-group-btn\"><input type=\"button\" value=\"\" class=\"btn btn-primary btn-sm\" /></span>");
                }

                if ((this.parameter.mode != "R" && this.parameter.mode != "D") && !DE.fn.isempty(atr["AVAIL_CHK_PGM_ID"])) {
                    var funcs = JSON.parse(atr["AVAIL_CHK_PGM_ID"]);
                    if (!DE.fn.isempty(funcs.init)) {
                        tpl.find("textarea").attr("init", "javascript:" + this.initFn + funcs.init.func + "(this, " + JSON.stringify(funcs.init.param) + ");");
                    }
                    if (!DE.fn.isempty(funcs.change)) {
                        tpl.find("textarea").attr("onchange", "javascript:" + this.initFn + funcs.change.func + "(this, " + JSON.stringify(funcs.change.param) + ");");
                    }
                    if (!DE.fn.isempty(funcs.beforeSubmit)) {
                        tpl.find("textarea").attr("onBeforeSubmit", "javascript:" + this.initFn + funcs.beforeSubmit.func + "(this, " + JSON.stringify(funcs.change.param) + ");");
                    }
                    if (atr.UI_COMP_CD == "TA+BTN" && !DE.fn.isempty(funcs.click)) {
                        tpl.find("input:button").attr("onclick", "javascript:" + this.initFn + funcs.click.func + "(this, " + JSON.stringify(funcs.click.param) + ");");
                        tpl.find("input:button").val(funcs.click.caption);
                    }
                }
                tpl.find("textarea").prop("disabled", this.getDisabled());

                var prefixAtrValSeq = "0";
                var multiFirst = true;
                var source = [];

                for (var i = 0, len = atrVal.length; i < len; i++) {
                    if (prefixAtrValSeq != ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2)) {
                        source = tpl.clone();
                        prefixAtrValSeq = ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2);

                        var id = this.getObjectId(atr.ATR_ID_SEQ);
                        source.find("textarea").prop("id", id).prop("name", id);
                        source.find("textarea").val("");
                        this.setDefaultAtrVal(atrVal[i], source.find("textarea"));
                        if ((this.parameter.mode == "C" || this.parameter.mode == "U") && atr["MULTI_ATR_YN"] == "Y") {
                            if (multiFirst) {
                                source.append(this.getMultiActionTag("plus"));
                                multiFirst = false;
                            } else {
                                source.append(this.getMultiActionTag("minus"));
                            }
                        }

                        $e.append(source);
                    }
                    source.find("textarea").val(source.find("textarea").val() + ((!DE.fn.isempty(atrVal[i]["EXTEND_OBJ_ATR_VAL"])) ? atrVal[i]["EXTEND_OBJ_ATR_VAL"] : ""));
                }
            },
            atrValUI_Checkbox: function ($e, atr) {
                var tpl = $("<div class=\"input-group\" />");
                var mLeft = "";
                for (var i = 0, len = atrCode.length; i < len; i++) {
                    tpl.append("<label class=\"checkbox-inline\"><input type=\"checkbox\" value=\"" + atrCode[i]["CODE"] + "\" /><span class=\"font-default\">" + atrCode[i]["DISP_NAME"] + "</span></label>");
                }

                this.setEventHandler(atr, tpl.find("input[type=checkbox]"));
                tpl.find("input[type=checkbox]").prop("disabled", this.getDisabled());

                var prefixAtrValSeq = "0";
                var multiFirst = true;
                var source = [];
                for (var i = 0, len = atrVal.length; i < len; i++) {
                    if (prefixAtrValSeq != ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2)) {
                        source = tpl.clone();
                        var id = this.getObjectId(atr.ATR_ID_SEQ);
                        source.find("input[type=checkbox]").prop("id", id).prop("name", id);
                        source.find("input[type=checkbox][value='" + atrVal[i]["OBJ_ATR_VAL"] + "']").prop("checked", "checked");
                        this.setDefaultAtrVal(atrVal[i], source.find("input[type=checkbox]"));
                        if ((this.parameter.mode == "C" || this.parameter.mode == "U") && atr["MULTI_ATR_YN"] == "Y") {
                            if (multiFirst) {
                                source.append(this.getMultiActionTag("plus"));
                                multiFirst = false;
                            } else {
                                source.append(this.getMultiActionTag("minus"));
                            }
                        }

                        $e.append(source);
                        prefixAtrValSeq = ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2);
                    } else {
                        source.find("input[type=checkbox][value='" + atrVal[i]["OBJ_ATR_VAL"] + "']").prop("checked", "checked");
                    }
                }
            },
            atrValUI_Radio: function ($e, atr) {
                var tpl = $("<div class=\"input-group\" />");
                var atrCode = atr["ATR_CODE"];
                for (var i = 0, len = atrCode.length; i < len; i++) {
                    tpl.append("<label class=\"radio-inline\"><input type=\"radio\" value=\"" + atrCode[i]["CODE"] + "\" /><span class=\"font-default\">" + atrCode[i]["DISP_NAME"] + "</span></label>");
                }

                this.setEventHandler(atr, tpl.find("input[type=radio]"));

                tpl.find("input[type=radio]").prop("disabled", this.getDisabled());

                var source = tpl.clone();
                var id = this.getObjectId(atr.ATR_ID_SEQ);
                source.find("input[type=radio]").prop("id", id).prop("name", id);
                source.find("input[type=radio][value='" + atr["OBJ_ATR_VAL"] + "']").prop("checked", "checked");

                $e.append(source);
            },
            atrValUI_Calender: function ($e, atr) {
                var tpl = $("<div class=\"input-group date\" />");
                tpl.append("<input class=\"form-control input-sm\" type=\"text\" value=\"\" /><span class=\"input-group-addon\"><i class=\"fa fa-calendar\" /></span>");
                var _this = this;

                if (this.parameter.mode == "R" || this.parameter.mode == "D") {
                    var subCall = function (val) {
                        var source = tpl.clone();
                        var id = _this.getObjectId(atr.ATR_ID_SEQ);
                        $("input:eq(0)", source).prop("id", id);
                        $("input:eq(0)", source).prop("disabled", _this.getDisabled());
                        $("input:eq(0)", source).val((DE.fn.isempty(val)) ? "" : val);

                        $e.append(source);
                    }

                    for (var i = 0, len = atrVal.length; i < len; i++) {
                        var extendObjAtrVal = (DE.fn.isempty(atrVal[i].EXTEND_OBJ_ATR_VAL)) ? "" : atrVal[i].EXTEND_OBJ_ATR_VAL;
                        subCall(extendObjAtrVal);
                    }
                } else {
                    var subCall = function (val) {
                        var source = tpl.clone();
                        var id = _this.getObjectId(atr.ATR_ID_SEQ);
                        $("input:eq(0)", source).prop("id", id);
                        $("input:eq(0)", source).prop("disabled", _this.getDisabled());
                        $("input:eq(0)", source).val((DE.fn.isempty(val)) ? "" : val);

                        $("input:eq(0)", source).datetimepicker({
                            locale: "ko",
                            viewMode: 'days',
                            format: 'YYYY-MM-DD'
                        });

                        if ((_this.parameter.mode == "C" || _this.parameter.mode == "U") && atr.MULTI_ATR_YN == "Y") {
                            if (multiFirst) {
                                source.append(_this.getMultiActionTag("plus"));
                                multiFirst = false;
                            } else {
                                source.append(_this.getMultiActionTag("minus"));
                            }
                        }

                        $e.append(source);
                    }

                    var multiFirst = true;
                    for (var i = 0, len = atrVal.length; i < len; i++) {
                        var extendObjAtrVal = (DE.fn.isempty(atrVal[i]["EXTEND_OBJ_ATR_VAL"])) ? "" : atrVal[i]["EXTEND_OBJ_ATR_VAL"];
                        subCall(extendObjAtrVal);
                    }
                }
            },
            atrValUI_RangeCalender: function ($e, atr) {
                var _this = this;
                var tpl = $("<div class=\"input-group date\" />");
                tpl.append("<input class=\"form-control input-sm\" type=\"text\" value=\"\" /><span class=\"input-group-addon\"><i class=\"fa fa-calendar\" /></span>");
                tpl.append("<span class=\"input-group-addon label-addon\">&nbsp;~&nbsp;</span>");
                tpl.append("<input class=\"form-control input-sm\" type=\"text\" value=\"\" /><span class=\"input-group-addon\"><i class=\"fa fa-calendar\" /></span>");

                if (this.parameter.mode == "R" || this.parameter.mode == "D") {
                    var subCall = function (vals) {
                        var source = tpl.clone();
                        var id = _this.getObjectId(atr.ATR_ID_SEQ);
                        $("input:eq(0)", source).prop("id", id);
                        $("input:eq(0)", source).prop("disabled", _this.getDisabled());
                        $("input:eq(0)", source).val((DE.fn.isempty(vals[0])) ? "" : vals[0]);

                        var id2 = _this.getObjectId(atr.ATR_ID_SEQ);
                        $("input:eq(1)", source).prop("id", id2);
                        $("input:eq(1)", source).prop("disabled", _this.getDisabled());
                        $("input:eq(1)", source).val((DE.fn.isempty(vals[1])) ? "" : vals[1]);
                        $e.append(source);
                    }
                    var stack = [];
                    var preMultiIdSeq = 0;
                    for (var i = 0, len = atrVal.length; i < len; i++) {
                        var multiIdSeq = ("" + atrVal[i]["ATR_VAL_SEQ"]).substring(0, ("" + atrVal[i]["ATR_VAL_SEQ"]).length - 2);
                        var extendObjAtrVal = (DE.fn.isempty(atrVal[i]["EXTEND_OBJ_ATR_VAL"])) ? "" : atrVal[i]["EXTEND_OBJ_ATR_VAL"];
                        if (multiIdSeq === preMultiIdSeq) {
                            stack.push(extendObjAtrVal);
                        } else {
                            if (preMultiIdSeq !== 0) {
                                subCall(stack);
                            }
                            stack.length = 0;
                            stack.push(extendObjAtrVal);
                            preMultiIdSeq = multiIdSeq;
                        }
                    }
                    if (stack.length !== 0 || preMultiIdSeq===0) {
                        subCall(stack);
                    }
                } else {
                    var subCall = function (vals) {
                        var source = tpl.clone();
                        var id = _this.getObjectId(atr.ATR_ID_SEQ);
                        $("input:eq(0)", source).prop("id", id);
                        $("input:eq(0)", source).prop("disabled", _this.getDisabled());
                        $("input:eq(0)", source).val((DE.fn.isempty(vals[0])) ? "" : vals[0]);

                        var id2 = _this.getObjectId(atr.ATR_ID_SEQ);
                        $("input:eq(1)", source).prop("id", id2);
                        $("input:eq(1)", source).prop("disabled", _this.getDisabled());
                        $("input:eq(1)", source).val((DE.fn.isempty(vals[1])) ? "" : vals[1]);

                        $("#" + id, source).datetimepicker({
                            locale: "ko",
                            viewMode: 'days',
                            format: 'YYYY-MM-DD'
                        });
                        $("#" + id2, source).datetimepicker({
                            locale: "ko",
                            viewMode: 'days',
                            format: 'YYYY-MM-DD'
                        });
                        $("#" + id, source).on("dp.change", function (e) {
                            $('#'+id2, source).data("DateTimePicker").minDate(e.date);
                        });
                        $("#" + id2, source).on("dp.change", function (e) {
                            $('#'+id, source).data("DateTimePicker").maxDate(e.date);
                        });

                        if ((_this.parameter.mode == "C" || _this.parameter.mode == "U") && atr.MULTI_ATR_YN == "Y") {
                            if (multiFirst) {
                                source.append(_this.getMultiActionTag("plus"));
                                multiFirst = false;
                            } else {
                                source.append(_this.getMultiActionTag("minus"));
                            }
                        }

                        $e.append(source);
                    }

                    var multiFirst = true;
                    var stack = [];
                    var preMultiIdSeq = 0;
                    for (var i = 0, len = atrVal.length; i < len; i++) {
                        var multiIdSeq = ("" + atrVal[i]["ATR_VAL_SEQ"]).substring(0, ("" + atrVal[i]["ATR_VAL_SEQ"]).length - 2);
                        var extendObjAtrVal = (DE.fn.isempty(atrVal[i]["EXTEND_OBJ_ATR_VAL"])) ? "" : atrVal[i]["EXTEND_OBJ_ATR_VAL"];
                        if (multiIdSeq === preMultiIdSeq) {
                            stack.push(extendObjAtrVal);
                        } else {
                            if (preMultiIdSeq !== 0) {
                                subCall(stack);
                            }
                            stack.length = 0;
                            stack.push(extendObjAtrVal);
                            preMultiIdSeq = multiIdSeq;
                        }
                    }
                    if (stack.length !== 0 || preMultiIdSeq===0) {
                        subCall(stack);
                    }
                }
            },
            mandCheck: function (body) {
                var mandList = body.find("[MAND_YN=Y]");
                for (var i = 0, mandListLen = mandList.length; i < mandListLen; i++) {
                    var atrIdSeq = mandList.eq(i).attr("ATR_ID_SEQ");
                    var uiCompDivCd = mandList.eq(i).attr("UI_COMP_CD");
                    var atrNm = $.trim(mandList.eq(i).find("[id=ATR_ID_SEQ_" + atrIdSeq + "]").text());
                    var controlGroup = mandList.eq(i).find(".input-group");
                    var controlGroupLen = controlGroup.length;

                    switch (uiCompDivCd) {
                        case "SB":
                        case "IB":
                        case "PW":
                        case "TA+BTN":
                        case "TA":
                        case "CAL":
                        case "LK":
                            for (var j = 0; j < controlGroupLen; j++) {
                                var val = controlGroup.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").val();
                                if (DE.fn.isempty(val)) {
                                    DE.box.alert("속성값 [" + atrNm + "]은 필수 항목입니다.", function () {
                                        controlGroup.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").focus();
                                    });
                                    return false;
                                }
                            }
                            break;
                        case "RCAL":
                            for (var j = 0; j < controlGroupLen; j++) {
                                var val = controlGroup.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").val();
                                if (DE.fn.isempty($("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]:eq(0)", controlGroup.eq(j)).val()) || DE.fn.isempty($("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]:eq(1)", controlGroup.eq(j)).val())) {
                                    DE.box.alert("속성값 [" + atrNm + "]은 필수 항목입니다.", function () {
                                        $("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]:eq(0)", controlGroup.eq(j)).focus()
                                    });
                                    return false;
                                }
                            }
                            break;
                        case "IB+BTN":
                            for (var j = 0; j < controlGroupLen; j++) {
                                var val = controlGroup.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").attr("USE_DATA");
                                if (DE.fn.isempty(val)) {
                                    DE.box.alert("속성값 [" + atrNm + "]에 대한 정합성 체크가 필요합니다.", function () {
                                        controlGroup.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").focus();
                                    });
                                    return false;
                                }
                            }
                            break;
                        case "CB":
                        case "RB":
                            for (var j = 0; j < controlGroupLen; j++) {
                                var vals = controlGroup.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").filter(':checked').map(function () {
                                    return this.value;
                                }).get();
                                if (DE.fn.isempty(vals)) {
                                    DE.box.alert("속성값 [" + atrNm + "]은 필수 항목입니다.", function () {
                                        controlGroup.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").focus();
                                    });
                                    return false;
                                }
                            }
                            break;
                        case "BRP":
                            //자동셋팅값으로 필수입력 체크 여부 패스
                            break;
                        case "LB1":
                            for (var j = 0; j < controlGroupLen; j++) {
                                if (controlGroup.eq(j).find("li").length == 1) {
                                    DE.box.alert("속성값 [" + atrNm + "]은 필수 항목입니다.");
                                    return false;
                                }
                            }
                            break;
                        case "FLE":
                            if (controlGroup.eq(0).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").length == 1) {
                                DE.box.alert("속성값 [" + atrNm + "]은 필수 항목입니다.");
                                return false;
                            }
                            break;
                        case "DC1":
                        case "COL":
                        case "TAB":
                            for (var j = 0; j < controlGroupLen; j++) {
                                if (controlGroup.eq(j).find(".ui-jqgrid-view").find(".ui-jqgrid-bdiv table.ui-jqgrid-btable").jqGrid("getGridParam", "records") == 0) {
                                    DE.box.alert("속성값 [" + atrNm + "]은 필수 항목입니다.");
                                    return false;
                                }
                            }
                            break;
                    }
                }

                return true;
            },
            makeForm: function (param, divObj) {
                var objInfo = {};
                
                    objInfo.USER_GRP_ID = divObj.find("[id^=ATR_ID_SEQ_1_]").val();
                    objInfo.USER_GRP_NM = divObj.find("[id^=ATR_ID_SEQ_2_]").val();
                    objInfo.USER_GRP_DESC = divObj.find("[id^=ATR_ID_SEQ_3_]").val();
                    objInfo.USER_GRP_TYPE = divObj.find("[id^=ATR_ID_SEQ_4_]").val();
                    objInfo.USE_YN = divObj.find("[id^=ATR_ID_SEQ_5_]").val();
                 return objInfo;
            },
            makeObjInfo: function (body) {
                var beforeSubmitEvents = $("[onBeforeSubmit]");
                var b = true;
                $.each(beforeSubmitEvents, function () {
                    b = eval($(this).attr('onBeforeSubmit'));
                    if (!b) {
                        return false;
                    }
                });
                if (!b) {
                    return b;
                }
                if (!this.mandCheck(body)) {
                    return false;
                }

                console.log(this.parameter);
                var divObj = body.find("#obj_info_m");
                var objInfo = this.makeForm(this.parameter, divObj);

                return {
                    "objInfo": objInfo
                };
            },
            save: function (body) {
                var param = this.makeObjInfo(body);

                if (param == false) {
                    return;
                }
                console.log(param);
                return DE.ui.ajax.execute("userRole/save", param);
            }
        };

        return DE.metaui;
    });

}).call(this);
