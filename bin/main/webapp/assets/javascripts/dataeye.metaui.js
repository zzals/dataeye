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

        var componentFileName, moduleName, _keyStr, _utf8Encode, _utf8Decode, _fillZeros;
        componentFileName = 'dataeye.metaui.js';
        moduleName = 'DE.metaui';

        /**
         * @private
         * @description        private 속성.
         * base64인코딩에 쓰일 키문자열
         */
        _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

        _utf8Encode = function (string) {
            var c, l, n, ref, utftext;
            string = string.replace(/\r\n/g, "\n");
            utftext = [];
            for (n = l = 0, ref = string.length - 1; 0 <= ref ? l <= ref : l >= ref; n = 0 <= ref ? ++l : --l) {
                c = string.charCodeAt(n);
                if (c < 128) {
                    utftext.push(String.fromCharCode(c));
                } else if ((c > 127) && (c < 2048)) {
                    utftext.push(String.fromCharCode((c >> 6) | 192));
                    utftext.push(String.fromCharCode((c & 63) | 128));
                } else {
                    utftext.push(String.fromCharCode((c >> 12) | 224));
                    utftext.push(String.fromCharCode(((c >> 6) & 63) | 128));
                    utftext.push(String.fromCharCode((c & 63) | 128));
                }
            }
            return utftext.join();
        };

        _utf8Decode = function (utftext) {
            var c, c1, c2, i, string;
            string = [];
            i = 0;
            c = 0;
            c1 = 0;
            c2 = 0;
            while (i < utftext.length) {
                c = utftext.charCodeAt(i);
                if (c < 128) {
                    string.push(String.fromCharCode(c));
                    i++;
                } else if ((c > 191) && (c < 224)) {
                    c1 = utftext.charCodeAt(i + 1);
                    string.push(String.fromCharCode(((c & 31) << 6) | (c1 & 63)));
                    i += 2;
                } else {
                    c1 = utftext.charCodeAt(i + 1);
                    c2 = utftext.charCodeAt(i + 2);
                    string.push(String.fromCharCode(((c & 15) << 12) | ((c1 & 63) << 6) | (c2 & 63)));
                    i += 3;
                }
            }
            return string.join();
        };

        _fillZeros = function (input, digits) {
            var i, l, ref, zero;
            zero = [];
            input = input.toString();
            if (input.length < digits) {
                for (i = l = 1, ref = digits - input.length; 1 <= ref ? l <= ref : l >= ref; i = 1 <= ref ? ++l : --l) {
                    zero.push("0");
                }
            }
            return zero.join() + input;
        };

        /**
         * @literal    DE.metaui literal
         * @memberOf module:DE.metaui.object
         * @return    {object}    DE.metaui
         * @description        새로운 DE.metaui 인스턴스를 생성한다.
         */
        DE.metaui = {
            SBST_MAX_LENGTH: 2000,
            subjectLen: 2,
            contentLen: 10,
            authInfo: {"cauth":"N","rauth":"N","uauth":"N","dauth":"N","eauth":"N"},
            initFn: "DE.validator.",
            getValidatorFunc: function(func) {
            	if (func.indexOf(this.initFn) == 0) {
            		return func;
            	} else {
            		return this.initFn+func;
            	}
            },
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
            base64Encode: function (input) {
                var chr1, chr2, chr3, enc1, enc2, enc3, enc4, i, output, selfArgs;
                selfArgs = arguments;
                output = [];
                i = 0;
                input = _utf8Encode(input);
                while (i < input.length) {
                    chr1 = input.charCodeAt(i++);
                    chr2 = input.charCodeAt(i++);
                    chr3 = input.charCodeAt(i++);
                    enc1 = chr1 >> 2;
                    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                    enc4 = chr3 & 63;
                    if (isNaN(chr2)) {
                        enc3 = enc4 = 64;
                    } else if (isNaN(chr3)) {
                        enc4 = 64;
                    }
                    output.push(_keyStr.charAt(enc1 + _keyStr.charAt(enc2 + _keyStr.charAt(enc3 + _keyStr.charAt(enc4)))));
                }
                return output.join();
            },
            base64Decode: function (input) {
                var chr1, chr2, chr3, enc1, enc2, enc3, enc4, i, output, selfArgs;
                selfArgs = arguments;
                output = [];
                i = 0;
                input = input.replace(/[^A-Za-z0-9+/=]/g, "");
                while (i < input.length) {
                    enc1 = _keyStr.indexOf(input.charAt(i++));
                    enc2 = _keyStr.indexOf(input.charAt(i++));
                    enc3 = _keyStr.indexOf(input.charAt(i++));
                    enc4 = _keyStr.indexOf(input.charAt(i++));
                    chr1 = (enc1 << 2) | (enc2 >> 4);
                    chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                    chr3 = ((enc3 & 3) << 6) | enc4;
                    output = output + String.fromCharCode(chr1);
                    if (enc3 !== 64) {
                        output.push(String.fromCharCode(chr2));
                    }
                    if (enc4 !== 64) {
                        output.push(String.fromCharCode(chr3));
                    }
                }
                return _utf8Decode(output.join());
            },
            getAtrCode: function (atrIdSeq) {
                var atrCode = null;
                $.each(this.atrCodeInfo, function () {
                    if (this.hasOwnProperty("ATR_CODE_" + atrIdSeq)) {
                        atrCode = this;
                        return false;
                    }
                });
                if (atrCode == null) {
                    return atrCode;
                } else {
                    return eval("atrCode.ATR_CODE_" + atrIdSeq);
                }
            },
            getAtrVal: function (atrIdSeq) {
                var datas = [];
                $.each(this.atrValInfo, function () {
                    if (this.ATR_ID_SEQ == atrIdSeq) {
                        datas.push(this);
                    }
                });
                if (datas.length == 0) {
                    datas.push({OBJ_ATR_VAL: ''});
                    return datas;
                } else {
                    return datas;
                }
            },
            dateTimePicker: {
            	defaultOptions : {
                    locale: "ko",
                    viewMode: 'days',
                    format: 'YYYY-MM-DD',
                    extraFormats: ['YYYYMMDD']
                },
                getValue : function(target) {
                	var date = $(target).datetimepicker().data("DateTimePicker").date();
                	if (date === null) {
                		return "";
                	} else {
                		return date.format("YYYYMMDD");
                	}
                }
            },
            removeItem: function (e) {
            	$(e.currentTarget).closest("[de-obj-role=atr]").removeClass();
            	$(e.currentTarget).closest("[de-obj-role=atr]").remove();
            },
            multiplus: function(_div) {
            	var _this = this;
            	$("span[role=multiplus]", _div).not("[exception=true]").on("click", function (e) {
                    var atrGroup = $(e.target).closest("[ATR_ID_SEQ]");
                    var atrIdSeq = atrGroup.attr("ATR_ID_SEQ");
                    var uiCompCd = atrGroup.attr("UI_COMP_CD");
                    var target = $("div[id^=obj_info_det]", atrGroup);
                    
                    var newSource = $("[de-obj-role=atr]:eq(0)", atrGroup).clone(false);
                    
                    
                    $("[role=multiplus]", newSource).attr("role", "multiminus").find("i").removeClass("fa-plus-square-o").addClass("fa-minus-square-o");
                    $("input[type=text], select, textarea", newSource).val("").removeAttr("USE_DATA");
                    $("input[type=checkbox], input[type=radio]", newSource).removeAttr("checked").removeAttr("selected");
                    $("[role=multiminus]", newSource).on("click", function (e) {
                        DE.metaui.removeItem(e);
                    });

                    var guid = "ATR_ID_SEQ_" + atrIdSeq + "_" + DE.fn.guid.get();

                    switch (uiCompCd) {
                        case "SB":
                        	newSource.prop("id", guid).prop("name", guid);
                        	$("#" + guid, newSource).selectpicker('render');
                            newSource.appendTo(target);
                            break;
                        case "CAL":
                        	newSource.prop("id", guid).prop("name", guid);
                        	newSource.datetimepicker(_this.dateTimePicker.defaultOptions);
                            newSource.appendTo(target);
                            break;
                        case "RCAL":
                        	newSource.prop("id", guid).prop("name", guid);
                        	$("div.date:eq(0)", newSource).datetimepicker(_this.dateTimePicker.defaultOptions);
                        	$("div.date:eq(1)", newSource).prop("id", guid);
                        	$("div.date:eq(1)", newSource).datetimepicker(_this.dateTimePicker.defaultOptions);
                        	$("div.date:eq(0)", newSource).on("dp.change", function (e) {
                                $('div.date:eq(1)', newSource).data("DateTimePicker").minDate(e.date);
                            });
                            $("div.date:eq(1)", newSource).on("dp.change", function (e) {
                                $('div.date:eq(0)', newSource).data("DateTimePicker").maxDate(e.date);
                            });

                            newSource.appendTo(target);
                            break;
                        case "BDP_BDS":
                        	newSource.prop("id", guid).prop("name", guid);
                        	$("input:eq(1)", newSource).closest("div.date").datetimepicker(_this.dateTimePicker.defaultOptions);
                        	$("input:eq(2)", newSource).closest("div.date").datetimepicker(_this.dateTimePicker.defaultOptions);
                        	$("input:eq(1)", newSource).closest("div.date").on("dp.change", function (e) {
                                $('input:eq(2)', newSource).closest("div.date").data("DateTimePicker").minDate(e.date);
                            });
                            $("input:eq(2)", newSource).closest("div.date").on("dp.change", function (e) {
                                $('input:eq(1)', newSource).closest("div.date").data("DateTimePicker").maxDate(e.date);
                            });
                            
                            newSource.appendTo(target);
                            break;
                            
                        case "IB+BTN":
                        	newSource.prop("id", guid).prop("name", guid);                        	
                        	$("input:eq(0)", newSource).prop("id", guid);                        	
                            newSource.appendTo(target);
                            break;
                        default :                        	
                        	newSource.prop("id", guid).prop("name", guid);
                    		newSource.appendTo(target);
                            break;
                    }
                });


            },
            multiminus: function(_div) {
            	$("span[role=multiminus]", _div).not("[exception=true]").on("click", function (e) {
            		DE.metaui.removeItem(e);
                });
            },
            create: function (parameter, authInfo, body) {
                this.parameter = parameter;
                $.extend(this.authInfo, authInfo);
                this.body = body;
                var _self = this;

                var html = [];
                html.push("<div class=\"basic_info_div\">");

                // html.push("<div class=\"navbar navbar-blend navbar-fixed-bottom\">");
                // html.push("\t<div id=\"actionDiv\" class=\"text-right nav\">");
                // if (this.parameter.mode == "C" && this.parameter.authInfo.C_AUTH == "Y") {
                //     html.push("\t\t<a id=\"btnAction\" class=\"btn btn-default btn-sm\" de-obj-action=\"save\" role=\"button\" href=\"#\"><i class=\"fa fa-floppy-o\"></i> 저장</a>");
                // }
                // else if (this.parameter.mode == "R") {
                //     if (this.parameter.authInfo.U_AUTH == "Y") {
                //         html.push("\t\t<a id=\"btnAction\" class=\"btn btn-default btn-sm\" de-obj-action=\"modify\" role=\"button\" href=\"#\"><i class=\"fa fa-pencil\"></i> 수정</a>");
                //     }
                //     if (this.parameter.authInfo.D_AUTH == "Y") {
                //         html.push("\t\t<a id=\"btnAction\" class=\"btn btn-default btn-sm\" de-obj-action=\"delete\" role=\"button\" href=\"#\"><i class=\"fa fa-trash-o\"></i> 삭제</a>");
                //     }
                // }
                // else if (this.parameter.mode == "U" && this.parameter.authInfo.U_AUTH == "Y") {
                //     html.push("\t\t<a id=\"btnAction\" class=\"btn btn-default btn-sm\" de-obj-action=\"modify\" role=\"button\" href=\"#\"><i class=\"fa fa-floppy-o\"></i> 저장</a>");
                // }
                // else if (this.parameter.mode == "D") {
                //     // 버튼 없음
                // }
                // html.push("\t</div>");
                // html.push("</div>");

                html.push("\t<div id=\"obj_info\" class=\"panel panel-default hidden\" role=\"object-area\">");
                // html.push("\t\t<div class=\"panel-heading\">");
                // html.push("\t\t\t<h3 class=\"panel-title\">기본 정보</h3>");
                // html.push("\t\t</div>");
                html.push("\t\t<div class=\"panel-body\">");
                html.push("\t\t\t<div id=\"obj_info_m\"></div>");
                html.push("\t\t\t<div id=\"obj_info_s\"></div>");
                html.push("\t\t\t<div class=\"basic_sub_info_div\"></div>");
                html.push("\t\t</div>");
                html.push("\t</div>");

                html.push("</div>");

                $(this.body).empty();
                $(html.join("\n")).appendTo($(this.body));

                if (_self.parameter["isReq"]) {
                	if (this.parameter.mode == "C" || this.parameter.mode == 'U') {
	                	var btn = [];
	                    btn.push('<nav class="navbar navbar-default">');
	                    btn.push('\t<div class="container-fluid">');
	                    btn.push('\t\t<div class="collapse navbar-collapse">');
	                    btn.push('\t\t\t<div id="actionDiv" class="navbar-form navbar-right de-obj-btn">');
	                    btn.push('\t\t\t\t<a id="btnAction" class="btn btn-default btn-sm" de-obj-action="save" role="button" href="#"><i class="fa fa-floppy-o"></i> 결재요청</a>');
	                    btn.push('\t\t\t</div>');
	                    btn.push('\t\t</div>');
	                    btn.push('\t</div>');
	                    btn.push('</nav>');
	                    $('#container > .content').prepend(btn.join("\n"));
                	}
                } else {
	                if (this.parameter.mode == "C" || this.parameter.mode == 'U') {
	                    if(this.authInfo.cauth == "Y" || this.authInfo.uauth == "Y") {
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
	                        /*$('.basic_info_div .panel').css('height', '462px');*/
	                    }
	                }
                }

                var baseUrl = DE.contextPath+"/metacore/objectInfo/get";
                if (_self.parameter["isReq"]) {
                	baseUrl = DE.contextPath+"/metareq/objectInfo/get";
                }
                
                $.ajax({
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    url: baseUrl,
                    data: JSON.stringify(_self.parameter),
                    dataType: "json",
                    success: function (data, textStatus) {
                        var objTypeId = data["data"]["objTypeId"];
                        var objId = data["data"]["objId"];
                        $("div#obj_info").attr("objTypeId", objTypeId);
                        $("div#obj_info").attr("objId", objId);

                        var atrInfo = data["data"]["atrInfo"];
                        var atrValInfo = data["data"]["atrValInfo"];
                        var atrCodeInfo = data["data"]["atrSqlSbstResult"];
                        _self.render("obj_info", atrInfo, atrValInfo, atrCodeInfo);

                        //INIT Func 처리
                        var inits = $("[init]", $("div#obj_info"));
                        $.each(inits, function () {
                            eval($(this).attr("init"));
                        });

                        //MULTI_ATR_YN 처리$("i").not("[exception=true]")
                        DE.metaui.multiplus($("div#obj_info"));
                        DE.metaui.multiminus($("div#obj_info"));

                        //SAVE
                        $("#actionDiv").find("a[id=btnAction]").on("click", function (e) {
                            var str = "등록";
                            switch (_self.parameter.mode) {
                                case "C":
                                	if (_self.parameter["isReq"]) {
                                		str = "결재요청";
                                	} else {
                                		str = "등록";
                                	}
                                    break;
                                case "U":
                                case "M":
                                    str = "수정";
                                    break;
                                case "D":
                                    str = "삭제";
                                    break;
                            }
                            
                            if (_self.parameter["isReq"]) {
                            	DE.box.confirm(str + " 하시겠습니까?", function (b) {
                                    if (b === true) {
                                    	var $body = $(document.body);
                                        var objInfo = _self.makeObjInfo($body);
                                        if (!objInfo) {
                                        	return;
                                        }

                                        //요청객체 param 형태로 변경
                                        objInfo["penObjMT"] = objInfo["objInfo"]["penObjM"];
                                        objInfo["objInfo"]["penObjM"]["PATH_OBJ_ID"]
                                        if ($("[id^=ATR_ID_SEQ_8_]", $body).is("select")) {
                                        	objInfo["penObjMT"]["PATH_OBJ_NM"] = $("[id^=ATR_ID_SEQ_8_] option:selected", $body).text();
                                        } else {
                                        	objInfo["penObjMT"]["PATH_OBJ_NM"] = $("[id^=ATR_ID_SEQ_8_]", $body).val();
                                        }
                                        
                                        objInfo["penObjDTs"] = objInfo["objInfo"]["penObjD"];
                                        $.each(objInfo["penObjDTs"], function(index, value){
                                        	var atrIdSeq = value["ATR_ID_SEQ"];
                                        	
                                        	if ($("[id^=ATR_ID_SEQ_"+atrIdSeq+"_]", $body).is("select")) {
                                        		value["OBJ_ATR_VAL_NM"] = $("[id^=ATR_ID_SEQ_"+atrIdSeq+"_] option:selected", $body).text();
                                            } else {
                                            	value["OBJ_ATR_VAL_NM"] = $("[id^=ATR_ID_SEQ_"+atrIdSeq+"_]", $body).val();
                                            }
                                        });
                                        
                                        delete objInfo["objInfo"];
                                        delete objInfo["subObjInfo"];
                                        
                                    	parent.objinfo_callback(objInfo);
                                    	top.close();
                                    }
                                });
                            } else {
	                            if (_self.parameter.mode == 'R') {
	                                if ($(this).attr("de-obj-action") == 'delete') {
	                                    DE.box.confirm("삭제 하시겠습니까?", function (b) {
	                                        if (b === true) {
	                                            var result = DE.ajax.call({
	                                                async: false,
	                                                url: DE.contextPath+"/metacore/objectInfo/delete",
	                                                data: {objTypeId: objTypeId, objId: objId}
	                                            });
	
	                                            if (result["status"] == "SUCCESS") {
	                                                DE.box.alert("삭제 되었습니다.", function () {
	                                                	var parentObj = opener || parent;
	                                                	if (parentObj !== null) {
	                                                		parentObj.opener.$("[id=btnSearch]").trigger("click");
	                                                	}
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
	                                        "objTypeId": objTypeId,
	                                        "objId": objId,
	                                        "mode": "U"
	                                    });
	                                }
	                            } else {
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
	                                                	var parentObj = opener || parent;
	                                                	if (parentObj !== null) {
	                                                		parentObj.opener.$("[id=btnSearch]").trigger("click");
	                                                	}                                                	
	                                                	top.close();
	                                                	
	                                                    /*if (_self.parameter.mode == "D") {
	                                                        top.close();
	                                                    }
	                                                    else {*/
	                                                        // if (!DE.fn.isempty(_self.parameter.formObj) && !DE.fn.isempty(_self.parameter.formObjUrl)) {
	                                                        //
	                                                        //     var $frm = $("#_returnForm");
	                                                        //
	                                                        //     if ($frm.length == 0) {
	                                                        //         $frm = $("<form id='_returnForm' name='_returnForm' method='post'></form>").css("display", "none");
	                                                        //         $frm.attr("action", _self.parameter.formObjUrl);
	                                                        //         $frm.attr("target", top.name);
	                                                        //         $frm.attr("enctype", "multipart/form-data");
	                                                        //         $frm.appendTo("body");
	                                                        //     }
	                                                        //
	                                                        //     var form_data = _self.parameter.formObj.split('&');
	                                                        //     var input = {};
	                                                        //     var $input;
	                                                        //
	                                                        //     $.each(form_data, function(key, value) {
	                                                        //         var data = value.split('=');
	                                                        //         $input = $("<input type='hidden' id='" + data[0] + "' name='" + data[0] + "'/>");
	                                                        //         $input.appendTo($frm);
	                                                        //         $input.attr("value", decodeURIComponent(data[1]));
	                                                        //     });
	                                                        //
	                                                        //     var $menuId = $("<input type='hidden' id='menuId' name='menuId'/>");
	                                                        //     $menuId.attr("value", _self.parameter.menuId);
	                                                        //     $menuId.appendTo($frm);
	                                                        //
	                                                        //     $frm.submit();
	                                                        //     $frm.remove();
	                                                        // }
	                                                        // else {
	                                                        //     DE.ui.open.frame("common/metacore/objectInfoTab", top.name, {
	                                                        //         "menuId": _self.parameter.menuId,
	                                                        //         "objTypeId": result["data"]["objTypeId"],
	                                                        //         "objId": result["data"]["objId"],
	                                                        //         "mode": "R"
	                                                        //     });
	                                                        // }
	                                                    /*}*/
	                                                }
	                                            });
	                                        }
	                                    }
	                                    return;
	                                });
	                            }
                            }
                        });
                    }
                });
            },
            subCreate: function (parameter) {
                if ($("div#sub_obj_info").length !== 0) {
                    $("div#sub_obj_info", "div.basic_sub_info_div").empty();
                }

                var html = [];
                html.push("\t\t\t\t<div id=\"sub_obj_info\" role=\"object-area\">");
                html.push("\t\t\t\t\t<div id=\"sub_obj_info_m\"></div>");
                html.push("\t\t\t\t\t<div id=\"sub_obj_info_s\"></div>");
                html.push("\t\t\t\t</div>");

                $(html.join("\n")).appendTo($("div.basic_sub_info_div"));

                this.subParam = parameter;
                var _self = this;

                $.ajax({
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    url: DE.contextPath+"/metacore/subObjectInfo/get",
                    data: JSON.stringify(_self.subParam),
                    dataType: "json",
                    success: function (data, textStatus) {
                        var objTypeId = data["data"]["objTypeId"];
                        var objId = data["data"]["objId"];
                        $("div#sub_obj_info").attr("objTypeId", objTypeId);
                        $("div#sub_obj_info").attr("objId", objId);

                        var atrInfo = data["data"]["atrInfo"];
                        var atrValInfo = data["data"]["atrValInfo"];
                        var atrCodeInfo = data["data"]["atrSqlSbstResult"];
                        _self.render("sub_obj_info", atrInfo, atrValInfo, atrCodeInfo);

                        //INIT Func 처리
                        var inits = $("[init]", $("div#sub_obj_info"));
                        $.each(inits, function () {
                            eval($(this).attr('init'));
                        });

                        //MULTI_ATR_YN 처리$("i").not("[exception=true]")
                        DE.metaui.multiplus($("div#obj_info"));
                        DE.metaui.multiminus($("div#obj_info"));
                    }
                });
            },
            render: function (oId, atrInfo, atrValInfo, atrCodeInfo) {
                this.atrInfo = atrInfo;
                if (["C"].indexOf(this.parameter.mode) !== -1) {
                    var obj = {
                        ATR_ID_SEQ: 1,
                        EXTEND_OBJ_ATR_VAL: this.parameter.objTypeId,
                        OBJ_ATR_VAL: this.parameter.objTypeId
                    };
                    atrValInfo.push(obj);

                    if (!DE.fn.isempty(this.parameter.objNm)) {
                        obj = {
                            ATR_ID_SEQ: 4,
                            EXTEND_OBJ_ATR_VAL: this.parameter.objNm,
                            OBJ_ATR_VAL: this.parameter.objNm
                        };
                        atrValInfo.push(obj);
                    }
                }
                this.atrValInfo = atrValInfo;
                this.atrCodeInfo = atrCodeInfo;
                var _self = this;

                this.setSubjectLen(2);

                $("#obj_info").removeClass("hidden");

                var htmlInline = "";
                var htmlInlineDet = "";

                //OBJ_BAS VALUE SETTING
                $.each(this.atrInfo, function () {
                    var atrCode = _self.getAtrCode(this.ATR_ID_SEQ);
                    var atrVal = _self.getAtrVal(this.ATR_ID_SEQ);
                    var objId = "";
                    var objIdDet = "";
                    if (this.ATR_ID_SEQ < 101) {
                        objId = oId + "_m";
                        objIdDet = oId + "_det_";
                    }
                    else {
                        objId = oId + "_s";
                        objIdDet = oId + "_det_";
                    }

                    if ((_self.parameter.mode == "C" || _self.parameter.mode == "U") && this.ATR_ID_SEQ > 10 && this.ATR_ID_SEQ < 101) {
                    }
                    else {
                    	if (this.indcYn === "R" && _self.parameter.mode !== "R") return true;
                    	if (this.indcYn === "U" && (_self.parameter.mode !== "C" && _self.parameter.mode !== "U")) return true;
                    	
                        htmlInline = "<div id=\"" + objId + "_" + this.ATR_ID_SEQ + "\" class=\"col-sm-12 form-group\" />";
                        $("#" + objId).append(htmlInline);                        
                        _self.atrUI($("#" + objId + "_" + this.ATR_ID_SEQ), this, atrCode, atrVal);
                        
                        htmlInlineDet = "<div id=\"" + objIdDet + this.ATR_ID_SEQ + "\" class=\"col-sm-" + _self.getContentLen() + "\" />";
                        $("#" + objId + "_" + this.ATR_ID_SEQ).append(htmlInlineDet);
                        _self.atrValUI($("#" + objIdDet + this.ATR_ID_SEQ), this, atrCode, atrVal);
                    }
                });
            },
            atrUI: function ($e, atr, atrCode, atrVal) {
                var html = "";
                var titleId = "ATR_NM_" + atr.ATR_ID_SEQ;
                var mandTag = atr.MAND_YN == "Y" ? "<i class=\"fa fa-star\" aria-hidden=\"true\" />" : "";
                var indcTag = atr.INDC_YN == "N" ? "display:none;" : "";

                if (indcTag === "display:none;") {
                    $e.attr("style", indcTag);
                }

                $e.attr("ATR_ID_SEQ", atr.ATR_ID_SEQ);
                $e.attr("MAND_YN", atr.MAND_YN);
                $e.attr("MULTI_YN", atr.MULTI_ATR_YN);
                $e.attr("UI_COMP_CD", atr.UI_COMP_CD);
                $e.attr("CNCT_ATR_YN", atr.CNCT_ATR_YN);

                html += "<label class=\"col-sm-" + this.getSubjectLen() + " control-label\" id=\"ATR_ID_SEQ_" + atr.ATR_ID_SEQ + "\">";
                html += (mandTag + atr.ATR_NM);
                html += "</label>";

                $e.append(html);
            },
            atrValUI: function ($e, atr, atrCode, atrVal) {
                if (["C", "U"].indexOf(this.parameter.mode) !== -1 && atr.ATR_ID_SEQ > 10 && atr.ATR_ID_SEQ < 101) {
                    return;
                }

                switch (atr["UI_COMP_CD"]) {
                    case "SB":
                        this.atrValUI_SelectBox($e, atr, atrCode, atrVal);
                        break;
                    case "IB":
                    case "IB+BTN":
                        this.atrValUI_InputText($e, atr, atrCode, atrVal);
                        break;
                    case "PW":
                        this.atrValUI_Password($e, atr, atrCode, atrVal);
                        break;
                    case "TA":
                    	this.atrValUI_EditorTextarea($e, atr, atrCode, atrVal);
                        break;
                    case "TA+BTN":
                        this.atrValUI_Textarea($e, atr, atrCode, atrVal);
                        break;
                    case "CB":
                        this.atrValUI_Checkbox($e, atr, atrCode, atrVal);
                        break;
                    case "RB":
                        this.atrValUI_Radio($e, atr, atrCode, atrVal);
                        break;
                    case "CAL":
                        this.atrValUI_Calender($e, atr, atrCode, atrVal);
                        break;
                    case "RCAL":
                        this.atrValUI_RangeCalender($e, atr, atrCode, atrVal);
                        break;
                    case "BRP":
                        this.atrValUI_BRP($e, atr, atrCode, atrVal);
                        break;
                    case "LB1":
                        this.atrValUI_LB($e, atr, atrCode, atrVal);
                        break;
                    case "LK1":
                        this.atrValUI_LK($e, atr, atrCode, atrVal);
                        break;
                    case "FLE":
                        this.atrValUI_FILE($e, atr, atrCode, atrVal);
                        break;
                    case "DC1":
                        this.atrValUI_DOC($e, atr, atrCode, atrVal);
                        break;
                    case "COL":
                        this.atrValUI_COL($e, atr, atrCode, atrVal);
                        break;
                    case "TAB":
                        this.atrValUI_TAB($e, atr, atrCode, atrVal);
                        break;
                    case "CHOSEN":
                        this.atrValUI_CHOSEN($e, atr, atrCode, atrVal);
                        break;
                    case "BTN":
                        this.atrValUI_BTN($e, atr, atrCode, atrVal);
                        break;
                    case "BDP_BDS":
                        this.atrValUI_BDP_BDS($e, atr, atrCode, atrVal);
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
                    tag = "<span role=\"multiplus\" class=\"multi_square input-group-addon label-addon\"><a href=\"#\"><i class=\"fa fa-plus-square-o fa-sm\" /></a></span>";
                } else if (action == "minus") {
                    // tag = "<div class='m_left5'><!-- fa-minus-square-o --><a href='#'><img role='multiminus' src='../resources/images/meta-viewer/minus_btn.png'></a></div>";
                    tag = "<span role=\"multiminus\" class=\"multi_square input-group-addon label-addon\"><a href=\"#\"><i class=\"fa fa-minus-square-o fa-sm\" /></a></span>";
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
                                fn.push(this.getValidatorFunc(funcs.init[i].func) + "(this, " + JSON.stringify(funcs.init[i].param) + ");");
                            }
                            target.attr("init", fn.join());
                        } else {
                            fn.push(this.getValidatorFunc(funcs.init.func) + "(this, " + JSON.stringify(funcs.init.param) + ");");
                            target.attr("init", fn.join());
                        }
                    }
                    if (!DE.fn.isempty(funcs.change)) {
                        fn = [];
                        if (funcs.change instanceof Array) {
                            for (var i = 0, len = funcs.change.length; i < len; i++) {
                                fn.push(this.getValidatorFunc(funcs.change[i].func) + "(this, " + JSON.stringify(funcs.change[i].param) + ");");
                            }
                            target.attr("onchange", fn.join());
                        } else {
                            fn.push(this.getValidatorFunc(funcs.change.func) + "(this, " + JSON.stringify(funcs.change.param) + ");")
                            target.attr("onchange", fn.join());
                        }
                    }
                    if (!DE.fn.isempty(funcs.beforeSubmit)) {
                        fn = [];
                        if (funcs.beforeSubmit instanceof Array) {
                            for (var i = 0, len = funcs.beforeSubmit.length; i < len; i++) {
                                fn.push(this.getValidatorFunc(funcs.beforeSubmit[i].func) + "(this, " + JSON.stringify(funcs.beforeSubmit[i].param) + ");");
                            }
                            target.attr("onBeforeSubmit", fn.join());
                        } else {
                            fn.push(this.getValidatorFunc(funcs.beforeSubmit.func) + "(this, " + JSON.stringify(funcs.beforeSubmit.param) + ");");
                            target.attr("onBeforeSubmit", fn.join());
                        }
                    }
                    if (!DE.fn.isempty(funcs.keyup)) {
                        fn = [];
                        if (funcs.keyup instanceof Array) {
                            for (var i = 0, len = funcs.keyup.length; i < len; i++) {
                                fn.push(this.getValidatorFunc(funcs.keyup[i].func) + "(this, " + JSON.stringify(funcs.keyup[i].param) + ");");
                            }
                            target.attr("onkeyup", fn.join());
                        } else {
                            fn.push(this.getValidatorFunc(funcs.keyup.func) + "(this, " + JSON.stringify(funcs.keyup.param) + ");");
                            target.attr("onkeyup", fn.join());
                        }
                    }
                }
            },
            atrValUI_SelectBox: function ($e, atr, atrCode, atrVal) {
                var tpl = $("<div class=\"input-group\" de-obj-role=\"atr\"/>").append("<select class=\"form-control selectpicker select-sm\" data-live-search=\"true\" data-style=\"select-sm\" />");
                tpl.find("select").append("<optgroup class=\"font-default\" />");
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

                var prefixAtrValSeq = "0";
                var multiFirst = true;

                for (var i = 0, len = atrVal.length; i < len; i++) {
                    if (prefixAtrValSeq != ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2)) {
                        var source = tpl.clone();
                        var id = this.getObjectId(atr.ATR_ID_SEQ);
                        source.find("select").prop("id", id).prop("name", id);
                        source.find("select").val(atrVal[i]["OBJ_ATR_VAL"]);
                        this.setDefaultAtrVal(atrVal[i], source.find("select"));
                        if (["C", "U"].indexOf(this.parameter.mode) !== -1 && atr.MULTI_ATR_YN == "Y") {
                            if (multiFirst) {
                                source.append(this.getMultiActionTag("plus"));
                                multiFirst = false;
                            } else {
                                source.append(this.getMultiActionTag("minus"));
                            }
                        }

                        $e.append(source);
                        $("#" + id).selectpicker();

                        prefixAtrValSeq = ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2);
                    }
                }

            },
            atrValUI_InputText: function ($e, atr, atrCode, atrVal) {
                var tpl = $("<div class=\"input-group\" de-obj-role=\"atr\"/>").append("<input class=\"form-control input-sm\" type=\"text\" value=\"\" />");
                if (this.parameter.mode == "R" || this.parameter.mode == "D") {
                	this.setEventHandler(atr, tpl.find("input[type=text]"));
                    var prefixAtrValSeq = "0";

                    for (var i = 0, len = atrVal.length; i < len; i++) {
                        if (prefixAtrValSeq != ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2)) {
                            var source = tpl.clone();
                            var id = this.getObjectId(atr.ATR_ID_SEQ);
                            var objAtrVal = (DE.fn.isempty(atrVal[i]["OBJ_ATR_VAL"])) ? "" : atrVal[i]["OBJ_ATR_VAL"];
                            var extendObjAtrVal = (DE.fn.isempty(atrVal[i]["EXTEND_OBJ_ATR_VAL"])) ? "" : atrVal[i]["EXTEND_OBJ_ATR_VAL"];
                            source.find("input[type=text]").prop("id", id).prop("name", id);
                            source.find("input[type=text]").val(extendObjAtrVal);
                            source.find("input[type=text]").prop("disabled", this.getDisabled());
                            $e.append(source);
                            prefixAtrValSeq = ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2);
                        }
                    }
                } else {
                	if (atr.UI_COMP_CD == "IB+BTN") {
                        tpl.append("<span class=\"input-group-btn\"><input type=\"button\" value=\"\" class=\"btn btn-primary btn-sm\" /></span>");
                    }
                    
                    this.setEventHandler(atr, tpl.find("input[type=text]"));
                    if (!DE.fn.isempty(atr["AVAIL_CHK_PGM_ID"])) {
                        var funcs = JSON.parse(atr["AVAIL_CHK_PGM_ID"]);
                        if (atr["UI_COMP_CD"] == "IB+BTN" && !DE.fn.isempty(funcs.click)) {
                            tpl.find("input:button").attr("onclick", "javascript:" + this.getValidatorFunc(funcs.click.func) + "(this, " + JSON.stringify(funcs.click.param) + ");");
                            tpl.find("input:button").val(funcs.click.caption);
                        }
                    }

                    var prefixAtrValSeq = "0";
                    var multiFirst = true;
                    for (var i = 0, len = atrVal.length; i < len; i++) {
                        var objAtrVal = (DE.fn.isempty(atrVal[i]["OBJ_ATR_VAL"])) ? "" : atrVal[i]["OBJ_ATR_VAL"];
                        var extendObjAtrVal = (DE.fn.isempty(atrVal[i]["EXTEND_OBJ_ATR_VAL"])) ? "" : atrVal[i]["EXTEND_OBJ_ATR_VAL"];

                        if (prefixAtrValSeq != ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2)) {
                            var source = tpl.clone();
                            var id = this.getObjectId(atr.ATR_ID_SEQ);
                            source.find("input[type=text]").prop("id", id).prop("name", id);
                            source.find("input[type=text]").val(extendObjAtrVal);
                            if (atr["UI_COMP_CD"] == "IB+BTN" || atr["ATR_ID"].indexOf("M")==0) {
                                source.find("input[type=text]").attr("USE_DATA", objAtrVal);
                            }
                            this.setDefaultAtrVal(atrVal[i], source.find("input[type=text]"));
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
            atrValUI_Password: function ($e, atr, atrCode, atrVal) {
                var tpl = [];
                if (this.parameter.mode == "R" || this.parameter.mode == "D") {
                    tpl = $("<div class=\"input-group\" de-obj-role=\"atr\"/>");

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
                    tpl = $("<div class=\"input-group\" de-obj-role=\"atr\"/>").append("<input class=\"form-control input-sm\" type=\"password\" value=\"\" />");
                    this.setEventHandler(atr, tpl.find("input[type=password]"));
                    if (!DE.fn.isempty(atr["AVAIL_CHK_PGM_ID"])) {
                        tpl = $("<div class=\"col-sm-" + this.getContentLen() + "\" />").append("<div class=\"input-group\"><input class=\"form-control input-sm\" type=\"password\" value=\"\" /><span class=\"input-group-btn\"><input type=\"button\" value=\"\" class=\"btn btn-primary btn-sm\" /></span></div>");
                        var funcs = JSON.parse(atr["AVAIL_CHK_PGM_ID"]);
                        tpl.find("input:button").attr("onclick", "javascript:" + this.getValidatorFunc(funcs.click.func) + "(this, " + JSON.stringify(funcs.click.param) + ");");
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
            atrValUI_Textarea: function ($e, atr, atrCode, atrVal) {
                var height = 90;
                if (!DE.fn.isempty(atr["UI_COMP_HEIGHT_PX"])) {
                    height = atr["UI_COMP_HEIGHT_PX"];
                }
                var tpl = $("<div class=\"input-group\" de-obj-role=\"atr\"/>").append("<textarea class=\"form-control\" style=\"height: " + height + "px;\"></textarea>");
                if ((this.parameter.mode != "R" && this.parameter.mode != "D") && atr["UI_COMP_CD"] == "TA+BTN") {
                    tpl.append("<span class=\"input-group-btn\"><input type=\"button\" value=\"\" class=\"btn btn-primary btn-sm\" /></span>");
                }

//                if ((this.parameter.mode != "R" && this.parameter.mode != "D") && !DE.fn.isempty(atr["AVAIL_CHK_PGM_ID"])) {
                if (!DE.fn.isempty(atr["AVAIL_CHK_PGM_ID"])) {
                    var funcs = JSON.parse(atr["AVAIL_CHK_PGM_ID"]);
                    if (!DE.fn.isempty(funcs.init)) {
                        tpl.find("textarea").attr("init", "javascript:" + this.getValidatorFunc(funcs.init.func) + "(this, " + JSON.stringify(funcs.init.param) + ");");
                    }
                    if (!DE.fn.isempty(funcs.change)) {
                        tpl.find("textarea").attr("onchange", "javascript:" + this.getValidatorFunc(funcs.change.func) + "(this, " + JSON.stringify(funcs.change.param) + ");");
                    }
                    if (!DE.fn.isempty(funcs.beforeSubmit)) {
                        tpl.find("textarea").attr("onBeforeSubmit", "javascript:" + this.getValidatorFunc(funcs.beforeSubmit.func) + "(this, " + JSON.stringify(funcs.change.param) + ");");
                    }
                    if (atr.UI_COMP_CD == "TA+BTN" && !DE.fn.isempty(funcs.click)) {
                        tpl.find("input:button").attr("onclick", "javascript:" + this.getValidatorFunc(funcs.click.func) + "(this, " + JSON.stringify(funcs.click.param) + ");");
                        tpl.find("input:button").val(funcs.click.caption);
                    }
                }
                if (this.getDisabled() === "disabled") {
                	tpl.find("textarea").prop("readonly", true);
                }
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
                        if (["C", "U"].indexOf(this.parameter.mode) !== -1 && atr["MULTI_ATR_YN"] == "Y") {
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
            atrValUI_EditorTextarea: function ($e, atr, atrCode, atrVal) {
            	var height = 90;
                if (!DE.fn.isempty(atr["UI_COMP_HEIGHT_PX"])) {
                    height = atr["UI_COMP_HEIGHT_PX"];
                }
                var tpl = $("<div class=\"input-group\" de-obj-role=\"atr\"/>").append("<textarea class=\"form-control\" style=\"height: " + height + "px;\"></textarea>");
                if (!DE.fn.isempty(atr["AVAIL_CHK_PGM_ID"])) {
                    var funcs = JSON.parse(atr["AVAIL_CHK_PGM_ID"]);
                    if (!DE.fn.isempty(funcs.init)) {
                        tpl.find("textarea").attr("init", "javascript:" + this.getValidatorFunc(funcs.init.func) + "(this, " + JSON.stringify(funcs.init.param) + ");");
                    }
                    if (!DE.fn.isempty(funcs.change)) {
                        tpl.find("textarea").attr("onchange", "javascript:" + this.getValidatorFunc(funcs.change.func) + "(this, " + JSON.stringify(funcs.change.param) + ");");
                    }
                    if (!DE.fn.isempty(funcs.beforeSubmit)) {
                        tpl.find("textarea").attr("onBeforeSubmit", "javascript:" + this.getValidatorFunc(funcs.beforeSubmit.func) + "(this, " + JSON.stringify(funcs.change.param) + ");");
                    }
                    if (atr.UI_COMP_CD == "TA+BTN" && !DE.fn.isempty(funcs.click)) {
                        tpl.find("input:button").attr("onclick", "javascript:" + this.getValidatorFunc(funcs.click.func) + "(this, " + JSON.stringify(funcs.click.param) + ");");
                        tpl.find("input:button").val(funcs.click.caption);
                    }
                }
                if (this.getDisabled() === "disabled") {
                	tpl.find("textarea").prop("readonly", true);
                }
                var prefixAtrValSeq = "0";
                var multiFirst = true;
                var source = [];

                for (var i = 0, len = atrVal.length; i < len; i++) {
                    if (prefixAtrValSeq != ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2)) {
                        source = tpl.clone();
                        prefixAtrValSeq = ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2);

                        var id = this.getObjectId(atr.ATR_ID_SEQ);

//                        setTimeout(function () {
//                        	CKEDITOR.replace(id, {
//                        		enterMode:'2',
//                        		shiftEnterMode:'3'
//                        	});
//                        }, 1000);
                        
                        source.find("textarea").prop("id", id).prop("name", id);
                        source.find("textarea").val("");
                        this.setDefaultAtrVal(atrVal[i], source.find("textarea"));
                        if (["C", "U"].indexOf(this.parameter.mode) !== -1 && atr["MULTI_ATR_YN"] == "Y") {
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
            atrValUI_Checkbox: function ($e, atr, atrCode, atrVal) {
                var tpl = $("<div class=\"input-group\" de-obj-role=\"atr\"/>");
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
                        if (["C", "U"].indexOf(this.parameter.mode) !== -1 && atr["MULTI_ATR_YN"] == "Y") {
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
            atrValUI_Radio: function ($e, atr, atrCode, atrVal) {
                var tpl = $("<div class=\"input-group\" de-obj-role=\"atr\"/>");
                var mLeft = "";
                for (var i = 0, len = atrCode.length; i < len; i++) {
                    tpl.append("<label class=\"radio-inline\"><input type=\"radio\" value=\"" + atrCode[i]["CODE"] + "\" /><span class=\"font-default\">" + atrCode[i]["DISP_NAME"] + "</span></label>");
                }

                this.setEventHandler(atr, tpl.find("input[type=radio]"));
                tpl.find("input[type=radio]").prop("disabled", this.getDisabled());

                var prefixAtrValSeq = "0";
                var multiFirst = true;
                var source = [];

                for (var i = 0, len = atrVal.length; i < len; i++) {
                    if (prefixAtrValSeq != ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2)) {
                        source = tpl.clone();
                        var id = this.getObjectId(atr.ATR_ID_SEQ);
                        source.find("input[type=radio]").prop("id", id).prop("name", id);
                        source.find("input[type=radio][value='" + atrVal[i]["OBJ_ATR_VAL"] + "']").prop("checked", "checked");
                        this.setDefaultAtrVal(atrVal[i], source.find("input[type=radio]"));
                        if (["C", "U"].indexOf(this.parameter.mode) !== -1 && atr.MULTI_ATR_YN == "Y") {
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
                        source.find("input[type=radio][value='" + atrVal[i]["OBJ_ATR_VAL"] + "']").prop("checked", "checked");
                    }
                }
            },
            atrValUI_Calender: function ($e, atr, atrCode, atrVal) {
            	var tpl = $("<div class=\"input-group\" de-obj-role=\"atr\"/>");
            	tpl.append("<div class=\"input-group date\"><input class=\"form-control input-sm\" type=\"text\" value=\"\" /><span class=\"input-group-addon\"><i class=\"fa fa-calendar\" /></span></div>");
                var _this = this;

                if (this.parameter.mode == "R" || this.parameter.mode == "D") {
                	var subCall = function (val) {
                    	var source = tpl.clone();
                    	var id = _this.getObjectId(atr.ATR_ID_SEQ);
                    	$(source).prop("id", id);
                        $(source).datetimepicker(_this.dateTimePicker.defaultOptions);
                        $(source).data("DateTimePicker").date(val);
                        $(source).datetimepicker().data("DateTimePicker").disable();
                        
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
                        $(source).prop("id", id);
                        $(source).datetimepicker(_this.dateTimePicker.defaultOptions);
                        $(source).data("DateTimePicker").date(val);

                        if (["C", "U"].indexOf(_this.parameter.mode) !== -1 && atr.MULTI_ATR_YN == "Y") {
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
            atrValUI_RangeCalender: function ($e, atr, atrCode, atrVal) {
            	var _this = this;
            	var tpl = $("<div class=\"input-group\" de-obj-role=\"atr\"/>");
            	tpl.append("<div class=\"input-group date\" />");
            	tpl.append("<span class=\"input-group-addon label-addon\">&nbsp;~&nbsp;</span>");
                tpl.append("<div class=\"input-group date\" />");
                $("div.date:eq(0)", tpl).append("<input class=\"form-control input-sm\" type=\"text\" value=\"\" /><span class=\"input-group-addon\"><i class=\"fa fa-calendar\" /></span>");
                $("div.date:eq(1)", tpl).append("<input class=\"form-control input-sm\" type=\"text\" value=\"\" /><span class=\"input-group-addon\"><i class=\"fa fa-calendar\" /></span>");

                if (this.parameter.mode == "R" || this.parameter.mode == "D") {
                    var subCall = function (vals) {
                    	var source = tpl.clone();
                        var id = _this.getObjectId(atr.ATR_ID_SEQ);
                        $(source).prop("id", id);
                        
                        $("div.date:eq(0)", source).datetimepicker(_this.dateTimePicker.defaultOptions);
                        $("div.date:eq(0)", source).data("DateTimePicker").date(vals[0]);
                        $("div.date:eq(0)", source).datetimepicker().data("DateTimePicker").disable();

                        $("div.date:eq(1)", source).datetimepicker(_this.dateTimePicker.defaultOptions);
                        $("div.date:eq(1)", source).data("DateTimePicker").date(vals[1]);
                        $("div.date:eq(1)", source).datetimepicker().data("DateTimePicker").disable();
                        
                        $e.append(source);
                    }
                    var stack = ["", ""];
                    var preMultiIdSeq = 0;
                    for (var i = 0, len = atrVal.length; i < len; i++) {
                    	var multiIdSeq = ("" + atrVal[i]["ATR_VAL_SEQ"]).substring(0, ("" + atrVal[i]["ATR_VAL_SEQ"]).length - 2);
                    	var extendObjAtrVal = (DE.fn.isempty(atrVal[i]["EXTEND_OBJ_ATR_VAL"])) ? "" : atrVal[i]["EXTEND_OBJ_ATR_VAL"];
                        if (multiIdSeq === preMultiIdSeq) {
                        	stack[1] = extendObjAtrVal;
                    	} else {
                    		if (preMultiIdSeq !== 0) {
                    			subCall(stack);
                    		}
                    		stack = ["", ""];
                    		stack[0] = extendObjAtrVal;
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
                        $(source).prop("id", id);
                        
                        $("div.date:eq(0)", source).datetimepicker(_this.dateTimePicker.defaultOptions);
                        $("div.date:eq(0)", source).data("DateTimePicker").date(vals[0]);

                        $("div.date:eq(1)", source).datetimepicker(_this.dateTimePicker.defaultOptions);
                        $("div.date:eq(1)", source).data("DateTimePicker").date(vals[1]);

                        $("div.date:eq(0)", source).on("dp.change", function (e) {
                            $("div.date:eq(1)", source).data("DateTimePicker").minDate(e.date);
                        });
                        $("div.date:eq(1)", source).on("dp.change", function (e) {
                            $("div.date:eq(0)", source).data("DateTimePicker").maxDate(e.date);
                        });

                        if (["C", "U"].indexOf(_this.parameter.mode) !== -1 && atr.MULTI_ATR_YN == "Y") {
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
                    var stack = ["", ""];
                    var preMultiIdSeq = 0;
                    for (var i = 0, len = atrVal.length; i < len; i++) {
                    	var multiIdSeq = ("" + atrVal[i]["ATR_VAL_SEQ"]).substring(0, ("" + atrVal[i]["ATR_VAL_SEQ"]).length - 2);
                    	var extendObjAtrVal = (DE.fn.isempty(atrVal[i]["EXTEND_OBJ_ATR_VAL"])) ? "" : atrVal[i]["EXTEND_OBJ_ATR_VAL"];
                        if (multiIdSeq === preMultiIdSeq) {
                    		stack[1] = extendObjAtrVal;
                    	} else {
                    		if (preMultiIdSeq !== 0) {
                    			subCall(stack);
                    		}
                    		stack = ["", ""];
                    		stack[0] = extendObjAtrVal;
                    		preMultiIdSeq = multiIdSeq;
                    	}
                    }
                    if (stack.length !== 0 || preMultiIdSeq===0) {
                    	subCall(stack);
                    }
                }
            },
            atrValUI_BRP: function ($e, atr, atrCode, atrVal) {
                $e.addClass("padding-left-0 padding-right-0");
                var tpl = $("<div class=\"input-group\" de-obj-role=\"atr\"/>");
                tpl.append("<div class=\"col-sm-4\"><input class=\"form-control input-sm\" type=\"text\" value=\"\" readonly /></div><div class=\"col-sm-8\"><input class=\"form-control input-sm\" type=\"text\" placeholder=\"설명\" />");

                this.setEventHandler(atr, tpl.find("input[type=text]"));
                tpl.find("input[type=text]").prop("disabled", this.getDisabled());

                var prefixAtrValSeq = "0";
                var multiFirst = true;
                var source = [];
                for (var i = 0, len = atrVal.length; i < len; i++) {
                    var objAtrVal = (DE.fn.isempty(atrVal[i].OBJ_ATR_VAL)) ? "" : atrVal[i].OBJ_ATR_VAL;
                    var extendObjAtrVal = (DE.fn.isempty(atrVal[i].EXTEND_OBJ_ATR_VAL)) ? "" : atrVal[i].EXTEND_OBJ_ATR_VAL;

                    if (prefixAtrValSeq != ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2)) {
                        if (extendObjAtrVal == "") {
                            extendObjAtrVal = "파라미터없음";
                        }
                        source = tpl.clone();
                        var id = this.getObjectId(atr.ATR_ID_SEQ);
                        source.find("input[type=text]").prop("id", id).prop("name", id);
                        source.find("input[type=text]").eq(0).val(extendObjAtrVal);
                        this.setDefaultAtrVal(atrVal[i], source.find("input[type=text]").eq(0));

                        prefixAtrValSeq = ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2);
                        if (atrVal.length == 1) {
                            $e.append(source);
                        }
                    } else {
                        source.find("input[type=text]").eq(1).val(extendObjAtrVal);
                        this.setDefaultAtrVal(atrVal[i], source.find("input[type=text]").eq(1));

                        $e.append(source);
                    }
                }
            },
            atrValUI_LB: function ($e, atr, atrCode, atrVal) {
                var id = this.getObjectId(atr.ATR_ID_SEQ);
                var $td = $e.find("tr[ATR_ID_SEQ=" + atr.ATR_ID_SEQ + "]>td");
                $td.prop("id", id);
                if (!DE.fn.isempty(atr.AVAIL_CHK_PGM_ID)) {
                    var funcs = JSON.parse(atr.AVAIL_CHK_PGM_ID);
                    if (!DE.fn.isempty(funcs.init)) {
                        $td.attr("init", "javascript:" + this.getValidatorFunc(funcs.init.func) + "(this, '" + this.parameter.objTypeId + "', '" + this.parameter.objId + "', '" + atr.ATR_ID_SEQ + "', " + JSON.stringify(funcs.init.param) + ");");
                    }
                }
            },
            atrValUI_LK: function ($e, atr, atrCode, atrVal) {
                var tpl = $("<ul />");
                if (this.parameter.mode == "R" || this.parameter.mode == "D") {
                    tpl.append("<li><a></a></li>");

                    var prefixAtrValSeq = "0";
                    for (var i = 0, len = atrVal.length; i < len; i++) {
                        if (prefixAtrValSeq != ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2)) {
                            var source = tpl.clone();
                            var id = this.getObjectId(atr.ATR_ID_SEQ);
                            source.find("li > a").prop("id", id).prop("name", id);
                            if ($.trim(atrVal[i].EXTEND_OBJ_ATR_VAL).indexOf("http://") == 0) {
                                source.find("li > a").attr("href", atrVal[i].EXTEND_OBJ_ATR_VAL).prop("target", "_blank");
                            } else {
                                source.find("li > a").attr("href", "http://" + atrVal[i].EXTEND_OBJ_ATR_VAL).prop("target", "_blank");
                            }
                            source.find("li > a").html(atrVal[i].EXTEND_OBJ_ATR_VAL);

                            $e.find("tr[ATR_ID_SEQ=" + atr.ATR_ID_SEQ + "]>td").append(source);
                            prefixAtrValSeq = ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2);
                        }
                    }
                } else {
                    tpl.append("<li><input class='popup_keyword' type='text' value=''></li>");
                    this.setEventHandler(atr, tpl.find("input[type=text]"));

                    tpl.find("input[type=text]").addClass(this.getSpanSize(atr));
                    tpl.find("input[type=text]").prop("disabled", this.getDisabled());

                    var prefixAtrValSeq = "0";
                    var multiFirst = true;
                    for (var i = 0, len = atrVal.length; i < len; i++) {
                        var objAtrVal = (DE.fn.isempty(atrVal[i].OBJ_ATR_VAL)) ? "" : atrVal[i].OBJ_ATR_VAL;
                        var extendObjAtrVal = (DE.fn.isempty(atrVal[i].EXTEND_OBJ_ATR_VAL)) ? "" : atrVal[i].EXTEND_OBJ_ATR_VAL;

                        if (prefixAtrValSeq != ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2)) {
                            var source = tpl.clone();
                            var id = this.getObjectId(atr.ATR_ID_SEQ);
                            source.find("input[type=text]").prop("id", id).prop("name", id);
                            source.find("input[type=text]").val(extendObjAtrVal);
                            source.find("input[type=text]").attr("USE_DATA", objAtrVal);
                            this.setDefaultAtrVal(atrVal[i], source.find("input[type=text]"));
                            if (atr.MULTI_ATR_YN == "Y") {
                                if (multiFirst) {
                                    source.append(this.getMultiActionTag("plus"));
                                    multiFirst = false;
                                } else {
                                    source.append(this.getMultiActionTag("minus"));
                                }
                            }

                            $e.find("tr[ATR_ID_SEQ=" + atr.ATR_ID_SEQ + "]>td").append(source);
                            prefixAtrValSeq = ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2);
                        }
                    }
                }
            },
            atrValUI_DOC: function ($e, atr, atrCode, atrVal) {
                var _self = this;
                var id = this.getObjectId(atr.ATR_ID_SEQ);
                var $td = $e.find("tr[ATR_ID_SEQ=" + atr.ATR_ID_SEQ + "]>td");
                $td.prop("id", id);

                var controls = $("<ul><li class='popup_grid'><table id='list' class='scroll'></table></li></ul>");
                $td.append(controls);
                var $grid = controls.find("table#list");
                $grid.jqGrid({
                    colNames: ['산출물명', '관리ID', '필수', '제출', '첨부파일', '<a id="addDoc" href="#"><img src="../resources/images/meta-viewer/plus_btn.png"></a>', '', '', '', '', '', '', '', ''],
                    colModel: [
                        {name: 'OBJ_NM', index: 'OBJ_NM', width: 100, classes: 'pointer'},
                        {name: 'ADM_OBJ_NM', index: 'ADM_OBJ_NM', width: 80},
                        {name: 'MAND_YN', index: 'MAND_YB', width: 50, align: 'center'},
                        {name: 'SUBS_YN', index: 'SUBS_YN', width: 50, align: 'center'},
                        {name: 'FILE_LOC_NAME', index: 'FILE_LOC_NAME', width: 150, classes: 'pointer'},
                        {
                            name: 'ACTION',
                            index: 'ACTION',
                            width: 50,
                            formatter: 'minusAction',
                            sortable: false,
                            align: 'center'
                        },
                        {name: 'UP_OBJ_TYPE_ID', index: 'UP_OBJ_TYPE_ID', hidden: true},
                        {name: 'UP_OBJ_ID', index: 'UP_OBJ_ID', hidden: true},
                        {name: 'UP_ATR_ID_SEQ', index: 'UP_ATR_ID_SEQ', hidden: true},
                        {name: 'UP_ATR_VAL_SEQ', index: 'UP_ATR_VAL_SEQ', hidden: true},
                        {name: 'OBJ_TYPE_ID', index: 'OBJ_TYPE_ID', hidden: true},
                        {name: 'OBJ_ID', index: 'OBJ_ID', hidden: true},
                        {name: 'FILE_PHY_NAME', index: 'FILE_PHY_NM', hidden: true},
                        {name: 'PATH', index: 'PATH', hidden: true}
                    ],
                    autowidth: true,
                    shrinkToFit: true,
                    forceFit: true,
                    rownumbers: false,
                    height: 100,
                    onCellSelect: function (rowid, index, contents, event) {
                        var colModel = $(this).jqGrid('getGridParam', 'colModel');
                        var rowData = $(this).getLocalRow(rowid);

                        if (colModel[index].name == "OBJ_NM") {
                            call_objInfoPopup(
                                $td,
                                rowData.UP_OBJ_TYPE_ID,
                                rowData.UP_OBJ_ID,
                                rowData.UP_ATR_ID_SEQ,
                                rowData.OBJ_TYPE_ID,
                                rowData.OBJ_ID,
                                "U",
                                "opener.$(\\\"td#" + id + " table#list\\\").jqGrid().trigger(\\\"reloadGrid\\\");"
                            );
                        } else if (colModel[index].name == "FILE_LOC_NAME") {
                            var file = rowData.PATH + "/" + rowData.FILE_PHY_NAME;
                            var fileLocName = rowData.FILE_LOC_NAME;
                            DE.ui.fileDownload(file, fileLocName);
                        }
                    },
                    gridComplete: function () {
                        if (_self.parameter.mode == "R" || _self.parameter.mode == "D") {
                            $grid.closest("div.ui-jqgrid-view").find("div.ui-jqgrid-hdiv table.ui-jqgrid-htable tr.ui-jqgrid-labels > th.ui-th-column > div.ui-jqgrid-sortable > a#addDoc").hide();
                            $grid.jqGrid('hideCol', "ACTION");
                        }
                    }
                });
                $grid.jqGrid(
                    'setGridParam', {
                        url: "json/list",
                        datatype: function (postData) {
                            $.ajax({
                                type: "POST",
                                contentType: "application/json; charset=utf-8",
                                url: "json/list",
                                data: JSON.stringify(postData),
                                dataType: "json",
                                success: function (data, textStatus) {
                                    $grid[0].addJSONData(data.rows);
                                    $grid.find("tr > td > a#removeAction").each(function () {
                                        $(this).on("click", function (e) {
                                            if (confirm("삭제 하시겠습니까?")) {
                                                var rowId = $(this).closest("tr.ui-widget-content").prop("id");
                                                var rowData = $grid.jqGrid('getRowData', rowId);

                                                var param = [];
                                                param.push({
                                                    type: "DB",
                                                    queryId: "PEN_OBJ_M.delete",
                                                    parameter: [{objTypeId: rowData.OBJ_TYPE_ID, objId: rowData.OBJ_ID}]
                                                });
                                                param.push({
                                                    type: "DB",
                                                    queryId: "PEN_OBJ_D.delete",
                                                    parameter: [{
                                                        objTypeId: rowData.OBJ_TYPE_ID,
                                                        objId: rowData.OBJ_ID,
                                                        between: [1, 200]
                                                    }]
                                                });
                                                param.push({
                                                    type: "DB",
                                                    queryId: "PEN_OBJ_D.delete",
                                                    parameter: [{
                                                        objTypeId: rowData.UP_OBJ_TYPE_ID,
                                                        objId: rowData.UP_OBJ_ID,
                                                        filterAtrIdSeq: [rowData.UP_ATR_ID_SEQ],
                                                        filterAtrValSeq: [Number(rowData.UP_ATR_VAL_SEQ) + 1, Number(rowData.UP_ATR_VAL_SEQ) + 2]
                                                    }]
                                                });
                                                var penFileList = [];
                                                var fileInfo = {};
                                                fileInfo.action = "delete";
                                                fileInfo.sourcePath = "MANAGED_FILE/" + rowData.OBJ_TYPE_ID;
                                                fileInfo.sourceName = rowData.OBJ_ID;
                                                fileInfo.targetPath = "";
                                                fileInfo.targetName = "";
                                                penFileList.push(fileInfo);
                                                param.push({type: "FILE", parameter: penFileList});

                                                DE.ui.ajax.execute("json/saveObjInfo", param);
                                                $grid.jqGrid('delRowData', rowId);
                                            }
                                            ;
                                        });
                                    });
                                }
                            });
                        },
                        postData: {
                            queryId: 'objectcore.docListByObj',
                            upObjTypeId: _self.parameter.objTypeId,
                            upObjId: _self.parameter.objId,
                            upAtrIdSeq: atr.ATR_ID_SEQ,
                            objTypeId: "000105L"
                        }
                    }
                ).trigger('reloadGrid');
                $grid.closest("div.ui-jqgrid-view").find("div.ui-jqgrid-hdiv table.ui-jqgrid-htable tr.ui-jqgrid-labels > th.ui-th-column > div.ui-jqgrid-sortable > a#addDoc").each(function () {
                    $(this).on("click", function (e) {
                        call_objInfoPopup(
                            $td,
                            _self.parameter.objTypeId,
                            _self.parameter.objId,
                            atr.ATR_ID_SEQ,
                            "000105L",
                            "",
                            "C",
                            "opener.$(\\\"td#" + id + " table#list\\\").jqGrid().trigger(\\\"reloadGrid\\\");"
                        );
                    });
                });
            },
            atrValUI_COL: function ($e, atr, atrCode, atrVal) {
                var _self = this;
                var id = this.getObjectId(atr.ATR_ID_SEQ);
                var $td = $e.find("tr[ATR_ID_SEQ=" + atr.ATR_ID_SEQ + "]>td");
                $td.prop("id", id);

                var controls = $("<ul><li class='popup_grid'><table id='" + id + "' class='scroll'></table></li></ul>");
                $td.append(controls);
                var $grid = controls.find("table#" + id);
                $grid.jqGrid({
                    colNames: ['테이블명', '컬럼명', '속성명', '<a id="addCol" href="#"><img src="../resources/images/meta-viewer/plus_btn.png"></a>', 'COL_OBJ_TYPE_ID', 'COL_OBJ_ID'],
                    colModel: [
                        {name: 'TAB_OBJ_NM', index: 'TAB_OBJ_NM', width: 100, align: 'left'},
                        {name: 'COL_OBJ_NM', index: 'COL_OBJ_NM', width: 100, align: 'left'},
                        {name: 'COL_OBJ_DESC', index: 'COL_OBJ_DESC', width: 100, align: 'left'},
                        {
                            name: 'ACTION',
                            index: 'ACTION',
                            width: 50,
                            formatter: 'minusAction',
                            sortable: false,
                            align: 'center'
                        },

                        {name: 'COL_OBJ_TYPE_ID', index: 'COL_OBJ_TYPE_ID', hidden: true},
                        {name: 'COL_OBJ_ID', index: 'COL_OBJ_ID', hidden: true}
                    ],
                    autowidth: true,
                    shrinkToFit: true,
                    forceFit: true,
                    rownumbers: false,
                    height: 100,
                    gridComplete: function () {
                        $grid.find("tr > td > a#removeAction").each(function () {
                            $(this).on("click", function (e) {
                                $grid.jqGrid('delRowData', $(this).closest("tr").prop("id"));
                            });
                        });

                        if (_self.parameter.mode == "R" || _self.parameter.mode == "D" || _self.parameter.objTypeId == "040104L") {
                            $grid.closest("div.ui-jqgrid-view").find("div.ui-jqgrid-hdiv table.ui-jqgrid-htable tr.ui-jqgrid-labels > th.ui-th-column > div.ui-jqgrid-sortable > a#addCol").hide();
                            $grid.jqGrid('hideCol', "ACTION");
                        }
                    }
                });
                var _postData = {
                    objTypeId: _self.parameter.objTypeId,
                    objId: _self.parameter.objId,
                    atrIdSeq: atr.ATR_ID_SEQ,
                };
                if (_self.parameter.objTypeId == "040103L") {
                    _postData.queryId = 'common.designColListByObj';
                    _postData.colObjTypeId = "040104L";
                } else {
                    _postData.queryId = 'common.colListByObj';
                    _postData.colObjTypeId = "020104L";
                }
                $grid.jqGrid(
                    'setGridParam', {
                        url: "json/list",
                        datatype: function (postData) {
                            $.ajax({
                                type: "POST",
                                contentType: "application/json; charset=utf-8",
                                url: "json/list",
                                data: JSON.stringify(postData),
                                dataType: "json",
                                success: function (data, textStatus) {
                                    $grid[0].addJSONData(data.rows);
                                    $grid.jqGrid('setGridParam', {datatype: 'local'});
                                }
                            });
                        },
                        postData: _postData
                    }
                ).trigger('reloadGrid');
                $grid.closest("div.ui-jqgrid-view").find("div.ui-jqgrid-hdiv table.ui-jqgrid-htable tr.ui-jqgrid-labels > th.ui-th-column > div.ui-jqgrid-sortable > a#addCol").each(function () {
                    $(this).on("click", function (e) {
                        call_selectedColumn($grid, {"uiCompCd": "COL"});
                    });
                });
            },
            atrValUI_TAB: function ($e, atr, atrCode, atrVal) {
                var _self = this;
                var id = this.getObjectId(atr.ATR_ID_SEQ);
                var $td = $e.find("tr[ATR_ID_SEQ=" + atr.ATR_ID_SEQ + "]>td");
                $td.prop("id", id);

                var controls = $("<ul><li class='popup_grid'><table id='" + id + "' class='scroll'></table></li></ul>");
                $td.append(controls);
                var $grid = controls.find("table#" + id);
                $grid.jqGrid({
                    colNames: ['DB명', '테이블명', '엔티티명', '<a id="addTab" href="#"><img src="../resources/images/meta-viewer/plus_btn.png"></a>', 'TAB_OBJ_TYPE_ID', 'TAB_OBJ_ID'],
                    colModel: [
                        {name: 'DB_OBJ_NM', index: 'DB_OBJ_NM', width: 100, align: 'left'},
                        {name: 'TAB_OBJ_NM', index: 'TAB_OBJ_NM', width: 100, align: 'left'},
                        {name: 'TAB_OBJ_DESC', index: 'TAB_OBJ_DESC', width: 100, align: 'left'},
                        {
                            name: 'ACTION',
                            index: 'ACTION',
                            width: 50,
                            formatter: 'minusAction',
                            sortable: false,
                            align: 'center'
                        },

                        {name: 'TAB_OBJ_TYPE_ID', index: 'TAB_OBJ_TYPE_ID', hidden: true},
                        {name: 'TAB_OBJ_ID', index: 'TAB_OBJ_ID', hidden: true}
                    ],
                    autowidth: true,
                    shrinkToFit: true,
                    forceFit: true,
                    rownumbers: false,
                    height: 100,
                    gridComplete: function () {
                        $grid.find("tr > td > a#removeAction").each(function () {
                            $(this).on("click", function (e) {
                                $grid.jqGrid('delRowData', $(this).closest("tr").prop("id"));
                            });
                        });

                        if (_self.parameter.mode == "R" || _self.parameter.mode == "D" || _self.parameter.objTypeId == "040102L") {
                            $grid.closest("div.ui-jqgrid-view").find("div.ui-jqgrid-hdiv table.ui-jqgrid-htable tr.ui-jqgrid-labels > th.ui-th-column > div.ui-jqgrid-sortable > a#addTab").hide();
                            $grid.jqGrid('hideCol', "ACTION");
                        }
                    }
                });
                var _postData = {
                    queryId: 'common.tabListByObj',
                    objTypeId: _self.parameter.objTypeId,
                    objId: _self.parameter.objId,
                    atrIdSeq: atr.ATR_ID_SEQ,
                };
                if (_self.parameter.objTypeId == "040102L") {
                    _postData.tabObjTypeId = "040102L";
                } else {
                    _postData.tabObjTypeId = "020102L";
                }
                $grid.jqGrid(
                    'setGridParam', {
                        url: "json/list",
                        datatype: function (postData) {
                            $.ajax({
                                type: "POST",
                                contentType: "application/json; charset=utf-8",
                                url: "json/list",
                                data: JSON.stringify(postData),
                                dataType: "json",
                                success: function (data, textStatus) {
                                    $grid[0].addJSONData(data.rows);
                                    $grid.jqGrid('setGridParam', {datatype: 'local'});
                                }
                            });
                        },
                        postData: _postData
                    }
                ).trigger('reloadGrid');
                $grid.closest("div.ui-jqgrid-view").find("div.ui-jqgrid-hdiv table.ui-jqgrid-htable tr.ui-jqgrid-labels > th.ui-th-column > div.ui-jqgrid-sortable > a#addTab").each(function () {
                    $(this).on("click", function (e) {
                        call_selectedTable($grid, {"uiCompCd": "TAB"});
                    });
                });
            },
            atrValUI_FILE: function ($e, atr, atrCode, atrVal) {
                var tpl = $("<div class=\"input-group\" de-obj-role=\"atr\"/>");
                var prefixAtrValSeq = "0";
                var source = [];
                var files = [];
                var file = {}, findSpanA;

                if (this.parameter.mode == "R" || this.parameter.mode == "D") {
                    source = $('<div class="upload-wrapper" />');
                    source.append('<div id="error_output" />');
                    files = $('<table role="presentation" class="table table-striped"><tbody id="files_' + atr.ATR_ID_SEQ + '" class="files" /></table>');
                    source.append(files);
                    source = tpl.clone().append(source);

                    $e.append(source);

                    var fileRow = $('<tr class="template-upload fade in file-row" />');

                    prefixAtrValSeq = "0";
                    for (var i = 0, len = atrVal.length; i < len; i++) {
                        var objAtrVal = (DE.fn.isempty(atrVal[i].OBJ_ATR_VAL)) ? "" : atrVal[i].OBJ_ATR_VAL;
                        var extendObjAtrVal = (DE.fn.isempty(atrVal[i].EXTEND_OBJ_ATR_VAL)) ? "" : atrVal[i].EXTEND_OBJ_ATR_VAL;
                        var suffixAtrValSeq = "";
                        if (DE.fn.isempty(atrVal[i].ATR_VAL_SEQ)) {
                            break;
                        } else {
                            suffixAtrValSeq = (atrVal[i].ATR_VAL_SEQ.toString()).substring((atrVal[i].ATR_VAL_SEQ.toString()).length - 1);
                        }

                        if (prefixAtrValSeq != ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2)) {
                            var fileRowClon = fileRow.clone();

                            fileRowClon.append("<td><span><a href='javascript:;'></a></span></td><td class=\"width-35\"></td>");

                            findSpanA = fileRowClon.find("span > a");

                            findSpanA.on("click", function (e) {
                                var $file = $(this).data();
                                var file = $file["path"] + "/" + $file["filePhyName"];
                                var fileLocName = $file["fileLocName"];
                                DE.ui.fileDownload(file, fileLocName);
                            });

                            var id = this.getObjectId(atr.ATR_ID_SEQ);
                            findSpanA.prop("id", id).prop("name", id);
                            this.setDefaultAtrVal(atrVal[i], findSpanA);

                            files.append(fileRowClon);

                            prefixAtrValSeq = ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2);
                        }

                        switch (suffixAtrValSeq) {
                            case "1": 	//filePhyName
                                file["filePhyName"] = extendObjAtrVal;
                                break;
                            case "2":		//fileLocName
                                file["fileLocName"] = extendObjAtrVal;
                                findSpanA.html(extendObjAtrVal);
                                break;
                            case "3":		//fileSize
                                file["fileSize"] = extendObjAtrVal;
                                fileRowClon.find('td:eq(1)').html('<span>' + extendObjAtrVal + '</span>');
                                break;
                            case "4":		//fileType
                                file["fileType"] = extendObjAtrVal;
                                break;
                            case "5":		//path
                                file["path"] = extendObjAtrVal;
                                break;
                        }

                        findSpanA.data(file);
                    }
                } else {
                    source = $('<div class="upload-wrapper" />');
                    source.append('<div id="error_output" />');
                    source.append('<div id="dropzone"><span class="btn btn-success fileinput-button"><i class="fa fa-plus" aria-hidden="true"></i><span>파일추가</span><input id="fileupload_' + atr.ATR_ID_SEQ + '" type="file" name="files[]" multiple /></span></div>');
                    files = $('<table role="presentation" class="table table-striped"><tbody id="files_' + atr.ATR_ID_SEQ + '" class="files" /></table>');
                    source.append(files);
                    source = tpl.clone().append(source);

                    if (atr.MULTI_ATR_YN == "N") {
                        source.find('#fileupload_' + atr.ATR_ID_SEQ).removeAttr('multiple');
                    }

                    $e.append(source);

                    var processUrl = DE.contextPath+'/file/upload/temp';
                    var progressBar = $('<div/>').addClass('progress').append($('<div/>').addClass('progress-bar')); //create progress bar
                    var uploadBtn = $('<button/>').addClass('btn btn-primary btn-sm start').append('<i class="fa fa-upload" aria-hidden="true"></i>').append('<span>업로드</span>');
                    var removeBtn = $('<button/>').addClass('btn btn-danger btn-sm delete').append('<i class="fa fa-trash-o" aria-hidden="true"></i>').append('<span>삭제</span>');
                    var cancelBtn = $('<button/>').addClass('btn btn-warning btn-sm cancel').append('<i class="fa fa-ban" aria-hidden="true"></i>').append('<span>취소</span>');
                    var fileRow = $('<tr class="template-upload fade in file-row" />');

                    prefixAtrValSeq = "0";
                    for (var i = 0, len = atrVal.length; i < len; i++) {
                        var objAtrVal = (DE.fn.isempty(atrVal[i].OBJ_ATR_VAL)) ? "" : atrVal[i].OBJ_ATR_VAL;
                        var extendObjAtrVal = (DE.fn.isempty(atrVal[i].EXTEND_OBJ_ATR_VAL)) ? "" : atrVal[i].EXTEND_OBJ_ATR_VAL;
                        var suffixAtrValSeq = "";
                        if (DE.fn.isempty(atrVal[i].ATR_VAL_SEQ)) {
                            break;
                        } else {
                            suffixAtrValSeq = (atrVal[i].ATR_VAL_SEQ.toString()).substring((atrVal[i].ATR_VAL_SEQ.toString()).length - 1);
                        }

                        if (prefixAtrValSeq != ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2)) {
                            var fileRowClon = fileRow.clone();
                            var removeBtnClon = removeBtn.clone();

                            fileRowClon.append('<td class=\"width-35\"><span><a href="javascript:;"></a></span></td><td class=\"width-25\"></td><td class=\"width-25\"></td>');
                            fileRowClon.append($('<td class=\"width-15\" />').append(removeBtnClon));

                            findSpanA = fileRowClon.find("span > a");

                            findSpanA.on("click", function (e) {
                                var $file = $(this).data();
                                var file = $file["path"] + "/" + $file["filePhyName"];
                                var fileLocName = $file["fileLocName"];
                                DE.ui.fileDownload(file, fileLocName);
                            });
                            removeBtnClon.on("click", function (e) {
                                var _self = $(this);
                                DE.box.confirm("삭제 하시겠습니까?\n서버에서 삭제 됩니다.", function (b) {
                                    if(b === true) {
                                        var obj = $("span > a", _self.closest("tr")).data();
                                        if (!DE.fn.isempty(obj)) {
                                            $.ajax({
                                                type: "POST",
                                                contentType: "application/json; charset=utf-8",
                                                url: DE.contextPath+"/file/remove",
                                                data: JSON.stringify(obj),
                                                dataType: "json",
                                                success: function (data, textStatus) {
                                                    DE.box.alert("삭제 되었습니다.", function () {
                                                        _self.parent().parent().remove();
                                                    });
                                                },
                                                fail: function (e, data) {
                                                    // data.errorThrown
                                                    // data.textStatus;
                                                    // data.jqXHR;
                                                    DE.box.alert('서버와 통신 중 문제가 발생했습니다');
                                                }
                                            });
                                        }
                                    }
                                    return;
                                });
                            });

                            var id = this.getObjectId(atr.ATR_ID_SEQ);
                            findSpanA.prop("id", id).prop("name", id);
                            this.setDefaultAtrVal(atrVal[i], fileRow);

                            files.append(fileRowClon);
                            prefixAtrValSeq = ("" + atrVal[i].ATR_VAL_SEQ).substring(0, ("" + atrVal[i].ATR_VAL_SEQ).length - 2);
                        }

                        file["objTypeId"] = this.parameter.objTypeId;
                        file["objId"] = this.parameter.objId;
                        file["atrIdSeq"] = atr.ATR_ID_SEQ;

                        //val info (filePhyName : fileLocName : fileSize : fileType : path) 순서로 저장
                        switch (suffixAtrValSeq) {
                            case "1": 	//filePhyName
                                file["atrValSeq"] = atrVal[i].ATR_VAL_SEQ;
                                file["filePhyName"] = extendObjAtrVal;
                                break;
                            case "2":		//fileLocName
                                file["fileLocName"] = extendObjAtrVal;
                                findSpanA.html(extendObjAtrVal);
                                break;
                            case "3":		//fileSize
                                file["fileSize"] = extendObjAtrVal;
                                fileRowClon.find('td:eq(1)').html('<span>' + extendObjAtrVal + '</span>');
                                break;
                            case "4":		//fileType
                                file["fileType"] = extendObjAtrVal;
                                break;
                            case "5":		//path
                                file["path"] = extendObjAtrVal;
                                break;
                        }

                        findSpanA.data(file);
                    }

                    $('#fileupload_' + atr.ATR_ID_SEQ).fileupload({
                        type: "POST",
                        async: true,
                        url: processUrl,
                        dataType: "json",
                        singleFileUploads: true,
                        autoUpload: false,
                        acceptFileTypes: /(\.|\/)(gif|jpe?g|png|mp4|mp3)$/i,
                        maxFileSize: 1048576, //1MB
                        disableImageResize: /Android(?!.*Chrome)|Opera/
                            .test(window.navigator.userAgent),
                        previewMaxWidth: 50,
                        previewMaxHeight: 50,
                        previewCrop: true,
                        dropZone: $('#dropzone' + atr.ATR_ID_SEQ),
                        maxNumberOfFiles: 1,
                        add: function (e, data) {
                            if (atr.MULTI_ATR_YN == "N") {
                                $('#files_' + atr.ATR_ID_SEQ).empty();
                            }
                            data.context = $('<tr class="template-upload fade in" />').addClass('file-row').appendTo('#files_' + atr.ATR_ID_SEQ); //create new DIV with "file-wrapper" class
                            $.each(data.files, function (index, file) {
                                // if (!(/png|jpe?g|gif|js|zip/i).test(file.name)) {
                                //     DE.box.alert('png, jpg, gif 만 가능합니다');
                                //     return;
                                // } else if (file.size > 524288000) { // 500mb
                                //     DE.box.alert('파일 용량은 500메가를 초과할 수 없습니다.');
                                //     return;
                                // }

                                var iSize = (Math.round((file.size / 1024) * 100) / 100);

                                var file_txt = $('<td class="width-35"><span>' + file.name + '</span></td><td class="width-25"><span>' + iSize + ' KB</span></td><td class="width-25"></td><td class="width-15"></td>');
                                file_txt.appendTo(data.context);
                                data.context.find('td:eq(2)').append(progressBar.clone());

                                data.submit();
                            });
                        },
                        progress: function (e, data) {
                            var progress = parseInt(data.loaded / data.total * 100, 10);
                            if (data.context) {
                                data.context.each(function () {
                                    $(this).find('.progress').attr('aria-valuenow', progress).children().first().css('width', progress + '%').text(progress + '%');
                                });
                            }
                        },
                        done: function (e, data) {
                            $.each(data.result, function (index, file) {
                                if (file.status == "S") {
                                    var removeBtnClon = removeBtn.clone();
                                    removeBtnClon.on('click', function (e) {
                                        var _self = $(this);
                                        DE.box.confirm("삭제 하시겠습니까?\n서버에서 삭제 됩니다.", function (b) {
                                            if (b === true) {
                                                var obj = $("span > a", _self.closest("tr")).data();
                                                if (!DE.fn.isempty(obj)) {
                                                    $.ajax({
                                                        type: "POST",
                                                        contentType: "application/json; charset=utf-8",
                                                        url: DE.contextPath+"/file/removeTemp",
                                                        data: JSON.stringify(obj),
                                                        dataType: "json",
                                                        success: function (data, textStatus) {
                                                            DE.box.alert("삭제 되었습니다.", function () {
                                                                _self.parent().parent().remove();
                                                            });
                                                        },
                                                        fail: function (e, data) {
                                                            // data.errorThrown
                                                            // data.textStatus;
                                                            // data.jqXHR;
                                                            DE.box.alert('서버와 통신 중 문제가 발생했습니다');
                                                        }
                                                    });
                                                }
                                            }
                                        })
                                    });

                                    var link = $('<a>').prop('href', 'javascript:;');
                                    var str = $(data.context).find("td:eq(0) > span").html();
                                    link.append(str);
                                    $(data.context).find("td:eq(0) > span").html(link);
                                    $(data.context).find('td:eq(3)').append(removeBtnClon);
                                    $(data.context).find("td:eq(0) > span > a").data(file);
                                    var id = DE.metaui.getObjectId(atr.ATR_ID_SEQ);
                                    $(data.context).find("td:eq(0) > span > a").prop("id", id).prop("name", id);

                                    $('#' + id).on("click", function (e) {
                                        var $file = $(this).data();
                                        var file_path = $file["path"] + "/" + $file["filePhyName"];
                                        var fileLocName = $file["fileLocName"];
                                        DE.ui.fileDownload(file_path, fileLocName);
                                    });
                                } else {
                                    $(data.context).find('td:eq(2)').html('<span class="text-danger">파일 업로드 실패</span>');
                                    var cancelBtnClon = cancelBtn.clone();
                                    cancelBtnClon.on('click', function (e) {
                                        $(this).parent().parent().remove();
                                    });
                                    $(data.context).find('td:eq(3)').append(cancelBtnClon);
                                }
                            });
                        },
                        fail: function (e, data) {
                            // data.errorThrown
                            // data.textStatus;
                            // data.jqXHR;
                            DE.box.alert('서버와 통신 중 문제가 발생했습니다');
                        }
                    });

                    // uploadBtn.on('click', function () { //button click function
                    //     var $this = $(this), data = $this.data();
                    //     $('#fileupload').fileupload('send', data);
                    //     console.log(data);
                    //     data.submit().always(function () { //upload the file
                    //         $this.remove(); //remove this button
                    //     });
                    // });

                }
            },
            atrValUI_CHOSEN: function ($e, atr, atrCode, atrVal) {
                var tpl = $("<ul />").append("<li><select class='popup_list' width='500px'/></li>");
                if (atrCode != null) {
                    for (var i = 0, len = atrCode.length; i < len; i++) {
                        var option = $("<option />");
                        option.val(atrCode[i].CODE);
                        option.text(atrCode[i].DISP_NAME);
                        if (!DE.fn.isempty(atrCode[i].PARAM1)) {
                            option.attr("param1", atrCode[i].PARAM1);
                        }
                        if (!DE.fn.isempty(atrCode[i].PARAM2)) {
                            option.attr("param2", atrCode[i].PARAM2);
                        }
                        tpl.find("select").append(option);
                    }
                }
                this.setEventHandler(atr, tpl.find("select"));
                tpl.find("select").prop("disabled", this.getDisabled());

                var prefixAtrValSeq = "0";

                var vals = [];
                $.each(atrVal, function (key, value) {
                    vals.push(value.EXTEND_OBJ_ATR_VAL);
                });
                var source = tpl.clone();
                if (["C", "U"].indexOf(this.parameter.mode) !== -1 && atr.MULTI_ATR_YN == "Y") {
                    source.find("select").attr("multiple", true);
                }
                var id = this.getObjectId(atr.ATR_ID_SEQ);
                source.find("select").prop("id", id).prop("name", id);
                source.find("select").val(vals);
                if (atrVal.length < 0) {
                    this.setDefaultAtrVal(atrVal[0], source.find("select"));
                }
                source.find("select").chosen({width: "500px"});

                $e.find("tr[ATR_ID_SEQ=" + atr.ATR_ID_SEQ + "]>td").append(source);
            },
            atrValUI_BTN: function ($e, atr, atrCode, atrVal) {
                var tpl = $("<div de-obj-role=\"atr\"/>").append("<input type=\"button\" value=\"\" class=\"btn btn-primary btn-sm\" />");
                this.setEventHandler(atr, tpl.find("input[type=button]"));
                if (!DE.fn.isempty(atr.AVAIL_CHK_PGM_ID)) {
                    var funcs = JSON.parse(atr.AVAIL_CHK_PGM_ID);
                    if (!DE.fn.isempty(funcs.click)) {
                        tpl.find("input:button").attr("onclick", "javascript:" + this.getValidatorFunc(funcs.click.func) + "(this, " + JSON.stringify(funcs.click.param) + ");");
                        tpl.find("input:button").val(funcs.click.caption);
                    }
                }

                var source = tpl.clone();
                $e.append(source);
            },
            atrValUI_BDP_BDS: function ($e, atr, atrCode, atrVal) {
            	var _this = this;
            	var tpl = $("<div class=\"input-group\" de-obj-role=\"atr\"/>");
            	tpl.append("<span class=\"input-group-addon label-addon\">전문가</span>");
                tpl.append("<input class=\"form-control input-sm\" type=\"text\" value=\"\" />");
            	tpl.append("<span class=\"input-group-addon label-addon\">기간</span>");
            	tpl.append("<div class=\"input-group date\"><input class=\"form-control input-sm\" type=\"text\" value=\"\" /><span class=\"input-group-addon\"><i class=\"fa fa-calendar\" /></span></div>");
            	tpl.append("<span class=\"input-group-addon label-addon\">~</span>");
            	tpl.append("<div class=\"input-group date\"><input class=\"form-control input-sm\" type=\"text\" value=\"\" /><span class=\"input-group-addon\"><i class=\"fa fa-calendar\" /></span></div>");
                tpl.append("<span class=\"input-group-addon label-addon\">권한</span>");
                tpl.append("<input class=\"form-control input-sm\" type=\"text\" value=\"\" />");

                if (this.parameter.mode == "R" || this.parameter.mode == "D") {
                    var subCall = function (vals) {
                    	var source = tpl.clone();
                    	var id = _this.getObjectId(atr.ATR_ID_SEQ);
                    	$(source).prop("id", id);
                    	
                    	for(var i=0; i<4; i++) {
                    		if (i === 0 || i === 3) {
    	                        $("input:eq("+i+")", source).prop("disabled", "disabled");
    	                        $("input:eq("+i+")", source).val((DE.fn.isempty(vals[i])) ? "" : vals[i]);
                    		} else if (i === 1 || i === 2) {
                    			$("input:eq("+i+")", source).closest("div.date").datetimepicker(_this.dateTimePicker.defaultOptions);
                    			$("input:eq("+i+")", source).closest("div.date").data("DateTimePicker").date(vals[i]);
                    			$("input:eq("+i+")", source).closest("div.date").datetimepicker().data("DateTimePicker").disable();
                    		}
                    	}
                    	$e.append(source);
                    }
                    var stack = ["","","",""];
                    var valSeq = 0;
                    var preMultiIdSeq = 0;
                    for (var i = 0, len = atrVal.length; i < len; i++) {
                    	var multiIdSeq = ("" + atrVal[i]["ATR_VAL_SEQ"]).substring(0, ("" + atrVal[i]["ATR_VAL_SEQ"]).length - 2);
                    	var extendObjAtrVal = (DE.fn.isempty(atrVal[i]["EXTEND_OBJ_ATR_VAL"])) ? "" : atrVal[i]["EXTEND_OBJ_ATR_VAL"];
                        if (multiIdSeq === preMultiIdSeq) {
                        	stack[valSeq++] = extendObjAtrVal;
                    	} else {
                    		if (preMultiIdSeq !== 0) {
                    			subCall(stack);
                    		}
                    		stack = ["","","",""];
                    		valSeq = 0;
                    		stack[valSeq++] = extendObjAtrVal;
                    		preMultiIdSeq = multiIdSeq;
                    	}
                    }
                    subCall(stack);
                } else {
                    var subCall = function (vals) {
                    	var source = tpl.clone();
                    	var id = _this.getObjectId(atr.ATR_ID_SEQ);
                    	$(source).prop("id", id);
                    	
                    	for(var i=0; i<4; i++) {
                    		if (i === 0 || i === 3) {
    	                        $("input:eq("+i+")", source).val((DE.fn.isempty(vals[i])) ? "" : vals[i]);
                    		} else if (i === 1 || i === 2) {
                    			$("input:eq("+i+")", source).closest("div.date").datetimepicker(_this.dateTimePicker.defaultOptions);
                    			$("input:eq("+i+")", source).closest("div.date").data("DateTimePicker").date(vals[i]);
                    		}
                    	}
                    	$("input:eq(1)", source).closest("div.date").on("dp.change", function (e) {
                            $('input:eq(2)', source).closest("div.date").data("DateTimePicker").minDate(e.date);
                        });
                        $("input:eq(2)", source).closest("div.date").on("dp.change", function (e) {
                            $('input:eq(1)', source).closest("div.date").data("DateTimePicker").maxDate(e.date);
                        });

                        if (["C", "U"].indexOf(_this.parameter.mode) !== -1 && atr.MULTI_ATR_YN == "Y") {
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
                    var stack = ["","","",""];
                    var valSeq = 0;
                    var preMultiIdSeq = 0;
                    for (var i = 0, len = atrVal.length; i < len; i++) {
                    	var multiIdSeq = ("" + atrVal[i]["ATR_VAL_SEQ"]).substring(0, ("" + atrVal[i]["ATR_VAL_SEQ"]).length - 2);
                    	var extendObjAtrVal = (DE.fn.isempty(atrVal[i]["EXTEND_OBJ_ATR_VAL"])) ? "" : atrVal[i]["EXTEND_OBJ_ATR_VAL"];
                        if (multiIdSeq === preMultiIdSeq) {
                        	stack[valSeq++] = extendObjAtrVal;
                    	} else {
                    		if (preMultiIdSeq !== 0) {
                    			subCall(stack);
                    		}
                    		stack= ["","","",""];
                    		var valSeq = 0;
                            stack[valSeq++] = extendObjAtrVal;
                    		preMultiIdSeq = multiIdSeq;
                    	}
                    }
                    subCall(stack);
                }
            },
            mandCheck: function (body) {
                var mandList = body.find("[MAND_YN=Y]");
                for (var i = 0, mandListLen = mandList.length; i < mandListLen; i++) {
                    var atrIdSeq = mandList.eq(i).attr("ATR_ID_SEQ");
                    var uiCompDivCd = mandList.eq(i).attr("UI_COMP_CD");
                    var atrNm = $.trim(mandList.eq(i).find("[id=ATR_ID_SEQ_" + atrIdSeq + "]").text());
                    var controlGroup = $("[de-obj-role=atr]", mandList.eq(i));
                    var controlGroupLen = controlGroup.length;

                    switch (uiCompDivCd) {
                        case "SB":
                        case "IB":
                        case "PW":
                        case "TA+BTN":
                        case "TA":
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
                        case "CAL":
                            for (var j = 0; j < controlGroupLen; j++) {
                                if (DE.fn.isempty(this.dateTimePicker.getValue(controlGroup.eq(j)))) {
                                    DE.box.alert("속성값 [" + atrNm + "]은 필수 항목입니다.", function () {
                                    	$("input", controlGroup.eq(j)).focus();
                                    });
                                    return false;
                                }
                            }
                            break;
                        case "RCAL":
                            for (var j = 0; j < controlGroupLen; j++) {
                                if (DE.fn.isempty(this.dateTimePicker.getValue($("div.date:eq(0)", controlGroup.eq(j)))) || DE.fn.isempty(this.dateTimePicker.getValue($("div.date:eq(1)", controlGroup.eq(j))))) {
                                    DE.box.alert("속성값 [" + atrNm + "]은 필수 항목입니다.", function () {
                                    	$("div.date:eq(0) > input", controlGroup.eq(j)).focus()
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
                                if (controlGroup.eq(j).find("li").length < 1) {
                                    DE.box.alert("속성값 [" + atrNm + "]은 필수 항목입니다.");
                                    return false;
                                }
                            }
                            break;
                        case "FLE":
                            if (controlGroup.eq(0).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").length < 1) {
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
                            break;;
                        case "BDP_BDS":
                            for (var j = 0; j < controlGroupLen; j++) {
                                if (
                                		DE.fn.isempty($("input:eq(0)", controlGroup.eq(j)))
                                     || DE.fn.isempty(this.dateTimePicker.getValue($("div.date:eq(0)", controlGroup.eq(j)))) 
                                     || DE.fn.isempty(this.dateTimePicker.getValue($("div.date:eq(1)", controlGroup.eq(j)))) 
                                	 || DE.fn.isempty($("input:eq(3)", controlGroup.eq(j)))
                                ) {
                                    DE.box.alert("속성값 [" + atrNm + "]은 필수 항목입니다.", function () {
                                    	$("input:eq(0) > input", controlGroup.eq(j)).focus()
                                    });
                                    return false;
                                }
                            }
                            break;
                    }
                }

                return true;
            },
            makeForm: function (objTypeId, objId, divPenObjM, divPenObjD) {
            	var _this = this;
                var penObjM = DE.schema.PenObjM();
                penObjM.setObjTypeId(objTypeId);
                penObjM.setObjId(objId);
                penObjM.setDelYn('N');
                penObjM.setAdmObjId(divPenObjM.find("[id^=ATR_ID_SEQ_3_]").val());
                penObjM.setObjNm(divPenObjM.find("[id^=ATR_ID_SEQ_4_]").val());
                penObjM.setObjAbbrNm(divPenObjM.find("[id^=ATR_ID_SEQ_5_]").val());
                penObjM.setObjDesc(divPenObjM.find("[id^=ATR_ID_SEQ_6_]").val());
                penObjM.setPathObjTypeId(divPenObjM.find("[id^=ATR_ID_SEQ_7_]").val());

                if (divPenObjM.find("[id^=ATR_ID_SEQ_8_]").length != 0) {
                    if (divPenObjM.find("[id^=ATR_ID_SEQ_8_]")[0].tagName.toUpperCase() == "SELECT") {
                        penObjM.setPathObjId(divPenObjM.find("[id^=ATR_ID_SEQ_8_]").val());
                    } else {
                        penObjM.setPathObjId(divPenObjM.find("[id^=ATR_ID_SEQ_8_]").attr("USE_DATA"));
                    }
                }

                var penObjDLit = [];
                var items = $("[ATR_ID_SEQ]", divPenObjD);
                for (var i = 0, itemsLen = items.length; i < itemsLen; i++) {
                    var atrIdSeq = items.eq(i).attr("ATR_ID_SEQ");
                    var uiCompDivCd = items.eq(i).attr("UI_COMP_CD");
                    var cnctAtrYn = items.eq(i).attr("CNCT_ATR_YN");
                    var controls = $("[de-obj-role=atr]", items.eq(i));
                    
                    var atrValSeq = 101;
                    for (var j = 0, controlsLen = controls.length; j < controlsLen; j++) {
                        switch (uiCompDivCd) {
                            case "SB":
                                var penObjD = DE.schema.PenObjD();

                                penObjD.setObjTypeId(objTypeId);
                                penObjD.setObjId(objId);
                                penObjD.setAtrIdSeq(atrIdSeq);
                                penObjD.setAtrValSeq(atrValSeq + (100 * j));
                                penObjD.setDelYn("N");
                                penObjD.setObjAtrVal(controls.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").val());
                                penObjD.setUiCompCd(uiCompDivCd);
                                penObjD.setCnctAtrYn(cnctAtrYn);
                                penObjDLit.push(penObjD);

                                break;
                            case "IB":
                            case "PW":
                            case "LK":
                                var penObjD = DE.schema.PenObjD();

                                penObjD.setObjTypeId(objTypeId);
                                penObjD.setObjId(objId);
                                penObjD.setAtrIdSeq(atrIdSeq);
                                penObjD.setAtrValSeq(atrValSeq + (100 * j));
                                penObjD.setDelYn("N");
                                var useData = controls.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").attr("USE_DATA");
                                if (DE.fn.isempty(useData)) {
                                    penObjD.setObjAtrVal(controls.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").val());
                                } else {
                                    penObjD.setObjAtrVal(useData);
                                }
                                penObjD.setUiCompCd(uiCompDivCd);
                                penObjD.setCnctAtrYn(cnctAtrYn);
                                penObjDLit.push(penObjD);

                                break;
                            case "CAL":
                                var penObjD = DE.schema.PenObjD();

                                penObjD.setObjTypeId(objTypeId);
                                penObjD.setObjId(objId);
                                penObjD.setAtrIdSeq(atrIdSeq);
                                penObjD.setAtrValSeq(atrValSeq + (100 * j));
                                penObjD.setDelYn("N");
                                penObjD.setObjAtrVal(_this.dateTimePicker.getValue($(controls.eq(j))));
                                penObjD.setUiCompCd(uiCompDivCd);
                                penObjD.setCnctAtrYn(cnctAtrYn);
                                penObjDLit.push(penObjD);

                                break;
                            case "RCAL":
                            	var vals = [
									_this.dateTimePicker.getValue($("div.date:eq(0)", controls.eq(j))),
									_this.dateTimePicker.getValue($("div.date:eq(1)", controls.eq(j)))
                            	];
                                for (var k = 0, len = vals.length; k < len; k++) {
                                    var penObjD = DE.schema.PenObjD();

                                    penObjD.setObjTypeId(objTypeId);
                                    penObjD.setObjId(objId);
                                    penObjD.setAtrIdSeq(atrIdSeq);
                                    penObjD.setAtrValSeq(atrValSeq + (100 * j) + (k));
                                    penObjD.setDelYn("N");
                                    penObjD.setObjAtrVal(vals[k]);
                                    penObjD.setUiCompCd(uiCompDivCd);
                                    penObjD.setCnctAtrYn(cnctAtrYn);
                                    penObjDLit.push(penObjD);
                                }

                                break;
                            case "IB+BTN":
                                var penObjD = DE.schema.PenObjD();

                                penObjD.setObjTypeId(objTypeId);
                                penObjD.setObjId(objId);
                                penObjD.setAtrIdSeq(atrIdSeq);
                                penObjD.setAtrValSeq(atrValSeq + (100 * j));
                                penObjD.setDelYn("N");
                                penObjD.setObjAtrVal(controls.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").attr("USE_DATA"));
                                penObjD.setUiCompCd(uiCompDivCd);
                                penObjD.setCnctAtrYn(cnctAtrYn);
                                penObjDLit.push(penObjD);

                                break;
                            case "TA":
                            case "TA+BTN":
                                var penObjD = DE.schema.PenObjD();

                                penObjD.setObjTypeId(objTypeId);
                                penObjD.setObjId(objId);
                                penObjD.setAtrIdSeq(atrIdSeq);
                                penObjD.setAtrValSeq(atrValSeq + (100 * j));
                                penObjD.setDelYn("N");
                                penObjD.setObjAtrVal(controls.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").val());
                                penObjD.setUiCompCd(uiCompDivCd);
                                penObjD.setCnctAtrYn(cnctAtrYn);
                                penObjDLit.push(penObjD);

                                break;
                            case "CB":
                            case "RB":
                                var vals = controls.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").filter(':checked').map(function () {
                                    return this.value;
                                }).get();
                                for (var k = 0, len = vals.length; k < len; k++) {
                                    var penObjD = DE.schema.PenObjD();

                                    penObjD.setObjTypeId(objTypeId);
                                    penObjD.setObjId(objId);
                                    penObjD.setAtrIdSeq(atrIdSeq);
                                    penObjD.setAtrValSeq(atrValSeq + (100 * j) + (k));
                                    penObjD.setDelYn("N");
                                    penObjD.setObjAtrVal(vals[k]);
                                    penObjD.setUiCompCd(uiCompDivCd);
                                    penObjD.setCnctAtrYn(cnctAtrYn);
                                    penObjDLit.push(penObjD);
                                }
                                break;
                            case "BRP":
                                var vals = controls.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").map(function () {
                                    return this.value;
                                }).get();
                                for (var k = 0, len = vals.length; k < len; k++) {
                                    if (vals[0] === "파라메터없음") break;

                                    var penObjD = DE.schema.PenObjD();

                                    penObjD.setObjTypeId(objTypeId);
                                    penObjD.setObjId(objId);
                                    penObjD.setAtrIdSeq(atrIdSeq);
                                    penObjD.setAtrValSeq(atrValSeq + (100 * j) + (k));
                                    penObjD.setDelYn("N");
                                    penObjD.setObjAtrVal(vals[k]);
                                    penObjD.setUiCompCd(uiCompDivCd);
                                    penObjD.setCnctAtrYn(cnctAtrYn);
                                    penObjDLit.push(penObjD);
                                }
                                break;
                            case "FLE":
                                var file = controls.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]");
                                var file_len = file.length;

                                for (var k = 0, len = file_len; k < len; k++) {
                                    var $file = $(file[k]).data();
                                    //val info (filePhyName : fileLocName : fileSize : fileType : path) 순서로 저장
                                    var penObjD = DE.schema.PenObjD();
                                    penObjD.setObjTypeId(objTypeId);
                                    penObjD.setObjId(objId);
                                    penObjD.setAtrIdSeq(atrIdSeq);
                                    penObjD.setAtrValSeq(atrValSeq + (100 * k));
                                    penObjD.setDelYn("N");
                                    var obj = {
                                        "filePhyName": $file["filePhyName"],
                                        "fileLocName": $file["fileLocName"],
                                        "fileSize": $file["fileSize"],
                                        "fileType": $file["fileType"],
                                        "path": $file["path"]
                                    };
                                    penObjD.setObjAtrVal(obj);
                                    penObjD.setUiCompCd(uiCompDivCd);
                                    penObjD.setCnctAtrYn(cnctAtrYn);
                                    penObjDLit.push(penObjD);
                                }

                                break;
                            case "COL":
                                var $grid = controls.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]");
                                var data = $grid.jqGrid("getGridParam", "data");
                                for (var k = 0, len = data.length; k < len; k++) {
                                    var penObjD = DE.schema.PenObjD();

                                    penObjD.setObjTypeId(objTypeId);
                                    penObjD.setObjId(objId);
                                    penObjD.setAtrIdSeq(atrIdSeq);
                                    penObjD.setAtrValSeq(atrValSeq + (100 * j) + (k));
                                    penObjD.setDelYn("N");
                                    penObjD.setObjAtrVal(data[k].COL_OBJ_ID);
                                    penObjD.setUiCompCd(uiCompDivCd);
                                    penObjD.setCnctAtrYn(cnctAtrYn);
                                    penObjDLit.push(penObjD);
                                }
                                break;
                            case "TAB":
                                var $grid = controls.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]");
                                var data = $grid.jqGrid("getGridParam", "data");
                                for (var k = 0, len = data.length; k < len; k++) {
                                    var penObjD = DE.schema.PenObjD();

                                    penObjD.setObjTypeId(objTypeId);
                                    penObjD.setObjId(objId);
                                    penObjD.setAtrIdSeq(atrIdSeq);
                                    penObjD.setAtrValSeq(atrValSeq + (100 * j) + (k));
                                    penObjD.setDelYn("N");
                                    penObjD.setObjAtrVal(data[k].TAB_OBJ_ID);
                                    penObjD.setUiCompCd(uiCompDivCd);
                                    penObjD.setCnctAtrYn(cnctAtrYn);
                                    penObjDLit.push(penObjD);
                                }
                                break;
                            case "IOP":
                            case "PW":
                                var penObjD = DE.schema.PenObjD();

                                penObjD.setObjTypeId(objTypeId);
                                penObjD.setObjId(objId);
                                penObjD.setAtrIdSeq(atrIdSeq);
                                penObjD.setAtrValSeq(atrValSeq + (100 * j));
                                penObjD.setDelYn("N");
                                var useData = controls.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").attr("USE_DATA");
                                if (DE.fn.isempty(useData)) {
                                    penObjD.setObjAtrVal(controls.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").val());
                                } else {
                                    penObjD.setObjAtrVal(useData);
                                }
                                penObjD.setUiCompCd(uiCompDivCd);
                                penObjD.setCnctAtrYn(cnctAtrYn);
                                penObjDLit.push(penObjD);
                                break;
                            case "CHOSEN":
                                var data = controls.eq(j).find("[id^=ATR_ID_SEQ_" + atrIdSeq + "_]").val();
                                data = (DE.fn.isempty(data)) ? [] : data;
                                for (var k = 0, len = data.length; k < len; k++) {
                                    var penObjD = DE.schema.PenObjD();

                                    penObjD.setObjTypeId(objTypeId);
                                    penObjD.setObjId(objId);
                                    penObjD.setAtrIdSeq(atrIdSeq);
                                    penObjD.setAtrValSeq(atrValSeq + (100 * j) + (k));
                                    penObjD.setDelYn("N");
                                    penObjD.setObjAtrVal(data[k]);
                                    penObjD.setUiCompCd(uiCompDivCd);
                                    penObjD.setCnctAtrYn(cnctAtrYn);
                                    penObjDLit.push(penObjD);
                                }
                                break;
                            case "BDP_BDS":
                            	var vals = [
                            	    $("input:eq(0)", controls.eq(j)).val(),
                            	    _this.dateTimePicker.getValue($("div.date:eq(0)", controls.eq(j))),
                            	    _this.dateTimePicker.getValue($("div.date:eq(1)", controls.eq(j))),
                            	    $("input:eq(3)", controls.eq(j)).val()
                            	];
                                for (var k = 0, len = vals.length; k < len; k++) {
                                    var penObjD = DE.schema.PenObjD();

                                    penObjD.setObjTypeId(objTypeId);
                                    penObjD.setObjId(objId);
                                    penObjD.setAtrIdSeq(atrIdSeq);
                                    penObjD.setAtrValSeq(atrValSeq + (100 * j) + (k));
                                    penObjD.setDelYn("N");
                                    penObjD.setObjAtrVal(vals[k]);
                                    penObjD.setUiCompCd(uiCompDivCd);
                                    penObjD.setCnctAtrYn(cnctAtrYn);
                                    penObjDLit.push(penObjD);
                                }

                                break;
                            default:
                                break;
                        }
                    }
                }

                return {
                    "penObjM": penObjM,
                    "penObjD": penObjDLit
                };
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

                var objTypeId = this.parameter.objTypeId;
                var objId = this.parameter.objId;
                var divPenObjM = body.find("#obj_info_m");
                var divPenObjD = body.find("#obj_info_s");
                var objInfo = this.makeForm(objTypeId, objId, divPenObjM, divPenObjD);
                var subObjInfo = {};
                if ($("div#sub_obj_info").length !== 0) {
                    var subObjTypeId = $("#sub_obj_info").attr("objTypeId");
                    var subObjId = $("#sub_obj_info").attr("objId");
                    var divSubPenObjM = body.find("#sub_obj_info_m");
                    var divSubPenObjD = body.find("#sub_obj_info_s");
                    subObjInfo = this.makeForm(subObjTypeId, subObjId, divSubPenObjM, divSubPenObjD);
                }

                return {
                    "objInfo": objInfo,
                    "subObjInfo": subObjInfo
                };
            },
            save: function (body) {
                var param = this.makeObjInfo(body);

                if (param == false) {
                    return;
                }
                return DE.ui.ajax.execute(DE.contextPath+"/metacore/objectInfo/save", param);
            }
        };

        return DE.metaui;
    });

}).call(this);
