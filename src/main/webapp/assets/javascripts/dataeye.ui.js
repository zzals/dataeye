(function() {
    'use strict';

    (function(root, factory) {

        if ((typeof define === 'function') && define.amd) {
            return define(['jquery'], factory);
        } else {
            return factory(root.DE, root.jQuery);
        }
    })(window, function(DE, $) {
    	

        DE = DE || {};

        var componentFileName, moduleName;
        componentFileName = 'dataeye.ui.js';
        moduleName = 'DE.ui';

        /**
         * @literal	DE.ui literal
         * @memberOf module:DE.ui.object
         * @return	{object}	DE.ui
         * @description		새로운 DE.ui 인스턴스를 생성한다.
         */
        DE.ui = {
            open: {
                popupWindows: {},
                popupAllClose: function() {
                    var popupNames = Object.getOwnPropertyNames(this.popupWindows);
                    for(var i=0; i<popupNames.length; i++) {
                        try {
                            var win = UI.open.popupWindows[popupNames[i]];
                            if(typeof win != "undefined" && !win.closed) {
                                win.close();
                            }
                        } catch(e){}
                    }
                    this.popupWindows = {};
                },
                getId: function (keys) {
                    var specialChars = /[~!#$^&*=+|:;?"<,.>']/;
                    var s = "popup";
                    if (typeof(keys) == "string") {
                        s += "_"+keys;
                    } else {
                        $.each(keys, function() {
                            s += "_"+this;
                        });
                    }
                    return s.split(specialChars).join("");
                },
                frame : function(viewId, target, param, menuId){
                    var $frm = $("#_frameForm");
                    var $viewId = $("#viewId", $frm);
                    var $menuId = $("#menuId", $frm);
                    var $data = $("#data", $frm);

                    if ($frm.length == 0) {
                        $frm =  $("<form id='_frameForm' name='_frameForm' action='view' target='"+target+"' method='post'></form>").css("display", "none");
                        $frm.appendTo("body");
                    }

                    if ($viewId.length == 0) {
                    	$viewId =  $("<input type='hidden' id='viewId' name='viewId'/>");
                    	$viewId.appendTo($frm);
                    }
                    $viewId.attr("value", viewId);

                    if ($menuId.length == 0) {
                    	$menuId =  $("<input type='hidden' id='menuId' name='menuId'/>");
                    	$menuId.appendTo($frm);
                    }
                    $menuId.attr("value", menuId);
                    
                    if ($data.length == 0) {
                        $data =  $("<input type='hidden' id='data' name='data'/>");
                        $data.appendTo($frm);
                    }
                    $data.attr("value", JSON.stringify(param));
                    $frm.submit();
                    $frm.remove();
                },
                popup: function (url, keys, param, options) {
                    var winId = this.getId(keys);
                    var optStr = "width=1000, height=702, toolbar=no, menubar=no, location=no,left=300,top=200";
                    var defaultOpt = {"width":1000, "height":702, "toolbar":"no", "menubar":"no", "location":"no"};
                    var opts = {};
                    
                    if ($.type(options) == "string") {
                    	optStr = options;
                    } else if ($.type(options) == "object") {
                    	opts = $.extend({}, defaultOpt, options);
                    	var keys = $.keys(opts);
                    	$.each(keys, function(index, value){
                    		if (index === 0) {
                    			optStr = value + "=" + opts[value];
                    		} else {
                    			optStr += ", " + value + "=" + opts[value];
                    		}
                    	});
                    }
                    
                    var win = window.open("about:blank", winId, optStr);
                    this.popupWindows[winId] = win;
                    
                    var $frm = $("#_popupForm");
                    var $viewId = $("#viewId", $frm);
                    var $menuId = $("#menuId", $frm);
                    var $data = $("#data", $frm);

                    if ($frm.length == 0) {
                        $frm =  $("<form id='_popupForm' name='_popupForm' action='"+url+"' target='"+winId+"' class='popupForm' method='post'></form>").css("display", "none");
                        $frm.appendTo("body");
                    }
                    if ($viewId.length == 0) {
                    	$viewId =  $("<input type='hidden' id='viewId' name='viewId'/>");
                    	$viewId.appendTo($frm);
                    }
                    $viewId.attr("value", param.viewname);
                    if ($menuId.length == 0) {
                    	$menuId =  $("<input type='hidden' id='menuId' name='menuId'/>");
                    	$menuId.appendTo($frm);
                    }
                    var menuId = "";
                    try {menuId = $("input#reqParam").data()["menuId"];} catch(e){};
                    $menuId.attr("value", menuId);
                    if ($data.length == 0) {
                        $data =  $("<input type='hidden' id='data' name='data'/>");
                        $data.appendTo($frm);
                    }
                    $data.attr("value", JSON.stringify(param));

                    $frm.submit();
                    $frm.remove();

                    win.focus();                    
                    return win;
                }
            },
            ajax : {
                getCurrentDate : function (format) {
                    if (format == undefined) {
                        format = "yyyy-MM-dd hh:mm:ss";
                    }
                    return JSON.parse($.ajax({
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        url: "json/currentTimestamp",
                        async: false,
                        dataType: "json",
                        data: format
                    }).responseText).value;
                },
                getSessionInfo : function () {
                    return JSON.parse($.ajax({
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        url: "json/sessionInfo",
                        async: false,
                        dataType: "json"
                    }).responseText).value;
                },
                getNewObjId : function (objTypeId) {
                    return JSON.parse($.ajax({
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        url: "json/map",
                        async: false,
                        dataType: "json",
                        data: JSON.stringify({
                            queryId:"common.newObjId",
                            objTypeId:objTypeId
                        })
                    }).responseText).rows.OBJ_ID;
                },
                getNextKey : function (table, column, prefix) {
                    return JSON.parse($.ajax({
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        url: "commonCore/getObjUUID",
                        async: false,
                        dataType: "json",
                        data: JSON.stringify({
                            prefix:prefix
                        })
                    }).responseText).rows.NEXT_KEY;
                },
                execute : function (url, param, callFunc) {
                    console.log(url);
                    var callbacks = $.Callbacks();
                    var ret = $.ajax({
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        url: url,
                        async: false,
                        dataType: "json",
                        data: JSON.stringify(param)
                    }).done(function(data, textStatus, jqXHR) {
                        if (callFunc != undefined) {
                            callbacks.add(callFunc);
                            callbacks.fire(data);
                        }
                    }).fail(function(jqXHR, textStatus, errorThrown){
                        //alert('false');
                    }).always(function(data, textStatus, jqXHR){
                        //alert('always');
                    });
                    return JSON.parse(ret.responseText);
                },
                list : function (param) {
                    return JSON.parse($.ajax({
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        url: "json/list",
                        async: false,
                        dataType: "json",
                        data: JSON.stringify(param)
                    }).responseText);
                },
                asynclist : function (param, callFunc) {
                    var callbacks = $.Callbacks();
                    $.ajax({
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        url: "json/list",
                        async: true,
                        dataType: "json",
                        data: JSON.stringify(param)
                    }).done(function(data, textStatus, jqXHR) {
                        callbacks.add(callFunc);
                        callbacks.fire(data);
                    }).fail(function(jqXHR, textStatus, errorThrown){
                        //alert('false');
                    }).always(function(data, textStatus, jqXHR){
                        //alert('always');
                    });
                },
                map : function (param) {
                    return JSON.parse($.ajax({
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        url: "json/map",
                        async: false,
                        dataType: "json",
                        data: JSON.stringify(param)
                    }).responseText);
                },
                queryValidCheck : function (param) {
                    return JSON.parse($.ajax({
                        type: "POST",
                        contentType: "application/json; charset=utf-8",
                        url: "json/queryValidCheck",
                        async: false,
                        dataType: "json",
                        data: JSON.stringify(param)
                    }).responseText);
                }
            },
            fileDownload: function (file, fileLocName) {
                var $frm = $("#fileDownloadForm");
                var $file = $("#file");
                var $fileLocName = $("#fileLocName");
                var $frame =  $("#_frame");

                if ($frm.length == 0) {
                    $frm =  $("<form id='fileDownloadForm' name='fileDownloadForm' action='"+DE.contextPath+"/file/download' target='_frame' class='fileDownloadForm' method='post'></form>").css("display", "none");
                    $frm.appendTo("body");
                }
                if ($frm.find("#file").length == 0) {
                    $file =  $("<input type='hidden' id='file' name='file'/>");
                    $file.appendTo($frm);
                }
                $file.val(file);
                if (fileLocName != undefined && fileLocName != null && fileLocName != "") {
                    if ($frm.find("#fileLocName").length == 0) {
                        $fileLocName =  $("<input type='hidden' id='fileLocName' name='fileLocName'/>");
                        $fileLocName.appendTo($frm);
                    }
                    $fileLocName.val(fileLocName);
                }
                if ($frame.length == 0) {
                    $frame =  $("<iframe id='_frame' name='_frame'></iframe>").css("display", "none");
                    $frame.appendTo("body");
                }
/*
                $("form.fileDownloadForm").on("submit", function (e) {
                    $.fileDownload($(this).prop('action'), {
                        preparingMessageHtml: "We are preparing your report, please wait...",
                        failMessageHtml: "There was a problem generating your report, please try again.",
                        httpMethod: "POST",
                        data: $(this).serialize()
                    });
                    e.preventDefault();
                    $("form.fileDownloadForm").off("submit");
                });
*/
                $frm.submit();
            },
            render: {
            	selectBox : function($target, data, options) {
            		this.defaults = {
            			valueKey:"CODE",
            			nameKey:"NAME",
            			isAll:false,
            			callback:false
            		};
            			
            		var opts = $.extend({}, this.defaults, options);
            		if (opts["isAll"]) {
            			$target.append($("<option>").attr("value", "").text("::선택하세요.::"));
            		}
            		$.each(data, function(index, value) {
            			$target.append($("<option>").attr("value", value[opts["valueKey"]]).text(value[opts["nameKey"]]));
            		});
        		}
            },
            objTypeImgNotFound : function(_target) {
            	_target.onerror = "";
            	_target.src = "../assets/images/icon/objtype/ZZZZZZL.png";
            	return true;
    		},
            getObjTypeImgTag : function(objTypeId, options) {
            	if(null === objTypeId) {
            		return "";
            	} else {
	            	var title = DE.objType.getName(objTypeId);
	        		return "<img src=\"../assets/images/icon/objtype/"+objTypeId+".png\" title=\""+title+"\" onerror=\"javascript:DE.ui.objTypeImgNotFound(this);\">";
            	}
    		}
        };

        return DE.ui;
    });

}).call(this);