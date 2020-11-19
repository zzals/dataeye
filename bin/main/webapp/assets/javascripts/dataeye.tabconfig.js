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
        componentFileName = 'dataeye.tabconfig.js';
        moduleName = 'DE.tabconfig';

        /**
         * @literal    DE.tabconfig literal
         * @memberOf module:DE.tabconfig.object
         * @return    {object}    DE.tabconfig
         * @description        새로운 DE.tabconfig 인스턴스를 생성한다.
         */
        DE.tabconfig = {
            tabTemplate: "<li><a href=\"#{tabId}\" data-toggle=\"tab\">#{label}</a></li>",
            contentTemplate: "<div id=\"#{tabId}\" action=\"#{action}\" class=\"tab-pane\"><iframe id=\"#{frameId}\" name=\"#{frameId}\" style=\"border:0;width:100%;height:100%;\" frameborder=\"0\" marginwidth=\"0\" scrollbar=\"no\" scrolling=\"no\"></iframe></div>",
            initialized: [false, false, false, false, false, false, false, false, false, false],
            tabId: ['tab-001', 'tab-002', 'tab-003', 'tab-004', 'tab-005', 'tab-006', 'tab-007', 'tab-008', 'tab-009', 'tab-010'],
            authInfo: {},
            menuId: "",
            getTabInfo: function (objTypeId) {
                var tabInfo = new Object();
                $.each(this.tabInfo, function () {
                    if (this.objTypeId == objTypeId) {
                        tabInfo = this;
                        return false;
                    }
                });
                return tabInfo;
            },
            getObjM: function(objTypeId, objId) {
            	var rsp = DE.ajax.call({"async":false, "url":DE.contextPath+"/metacore/objectInfo?oper=getObjM", "data":{"objTypeId":objTypeId, "objId":objId}});
                return rsp["data"];
            },
            getReqObjM: function(objTypeId, objId) {
            	var rsp = DE.ajax.call({"async":false, "url":DE.contextPath+"/metareq/objectInfo?oper=getObjM", "data":{"objTypeId":objTypeId, "objId":objId}});
                return rsp["data"];
            },
            setTitle: function (parameter) {
                if (DE.fn.isempty(parameter.objId)) {
                	if (parameter["isReq"]) {
                		$(".popup_title").append("결재 요청");
                	} else if (parameter.mode === "C") {
                		$(".popup_title").append("신규 등록");
                	}
                } else {
                    var objM = {};
                    if (parameter["isReq"]) {
                    	objM = this.getReqObjM(parameter["objTypeId"], parameter["objId"]);
                    } else {
                    	objM = this.getObjM(parameter["objTypeId"], parameter["objId"]);
                    }
                    var encodeObjNm = DE.fn.htmlEncode(objM["objNm"]);
                    $(".popup_title").append(encodeObjNm);
                }
            },
            createTab: function (tabsId, parameter, authInfo, menuId) {
            	this.tabsId = tabsId;
                this.parameter = parameter;
                this.authInfo = authInfo;
                this.menuId = menuId;
                
                var tabsInfo = [{"label": "기본정보", "action": "common/metacore/tabContent"}];
                if (!DE.fn.isempty(this.parameter.action)) {
                    tabsInfo[0]["action"] = this.parameter.action;
                }
                if (["C"].indexOf(this.parameter.mode) === -1) {
                	//test code
                	if (DE.config.config.dummyFlag === true && this.parameter.mode == "R") {
                		var rsp = DE.ajax.call({async:false, url:"metapublic?oper=getObjUIInfo", data:{objTypeId:this.parameter.objTypeId}});
                		$.each(rsp["data"], function (index, value) {
                            value["action"] = value["pgmId"];
                            value["label"] = value["uiAliasNm"];
                            tabsInfo.push(value);
                        });
                	} else {
	                    if (!DE.fn.isempty(this.parameter.objTypeId)) {
	                        var relGroup = DE.ui.ajax.execute(DE.contextPath+"/metacore/objRelTabs/get", {
	                            "objTypeId": parameter.objTypeId,
	                            "mode": this.parameter.mode
	                        });
	                        console.log("tab renderer");
	                        if (this.parameter.mode == "U") {
	                        	
	                        	
	                            $.each(relGroup["data"]["tabInfo"], function (index, value) {
	                                value["action"] = "common/metacore/objRModify";
	                                value["label"] = value["relTypeNm"];
	                                tabsInfo.push(value);
	                            });

	                        	   var secValue = {};
	                        	   secValue["action"] = "admin/objAuthList";
	                        	   secValue["label"] = "<span class=\"glyphicon glyphicon-lock\" ></span> 보안";
	                               tabsInfo.push(secValue);
	                        }/* else {
	                            $.each(relGroup["data"]["tabInfo"], function (index, value) {
	                                console.log(value);
	                                if ("IMPCT" == value["relTypeId"]) {
	                                    value["action"] = "common/metacore/objRIMPCTView";
	                                    value["label"] = value["relTypeNm"];
	                                } else if ("PT" == value["relDv"]) {
	                                    value["action"] = "common/metacore/objRPTView";
	                                    value["label"] = value["relTypeNm"];
	                                } else {
	                                    value["action"] = "common/metacore/objRView";
	                                    value["label"] = value["relTypeNm"];
	                                }
	                                tabsInfo.push(value);
	                            });
	                        }*/
	                    }
                	}
                }

                var _self = this;
                $.each(tabsInfo, function (index, value) {
                    var guid = DE.fn.guid.get();
                    var li = $(_self.tabTemplate.replace(/#\{tabId\}/g, "#" + _self.tabId[index]).replace(/#\{label\}/g, value["label"]));
                    var content = $(_self.contentTemplate.replace(/#\{tabId\}/g, _self.tabId[index]).replace(/#\{action\}/g, value["action"]).replace(/#\{frameId\}/g, "frame_" + guid));
                    content.attr("metaRelCd", value["META_REL_CD"])
                        .attr("relDv", value["REL_DV"])
                        .attr("relTypeNm", value["REL_TYPE_NM"])
                        .attr("uiId", value["uiId"]);
                    $("#" + tabsId).find("ul").append(li);
                    $("#" + tabsId).find(".tab-content").append(content);
                });

                $(".nav-tabs a").click(function (e) {
                    if ($(e.target).closest('li').attr('class') === 'active') {
                        return false;
                    }
                    var mytabindex = $(e.target).closest('li').index();
                    if (!_self.initialized[mytabindex]) {
                        var tabId = $(this).attr('href');
                        _self.createContent(tabsId, tabId, tabsInfo[mytabindex]["action"]);
                        _self.initialized[mytabindex] = true;
                    }
                    $(this).tab("show");
                });
            },
            createContent: function (tabsId, tabId, action) {
                var tabContent = $(tabId, "#" + tabsId);
                var relDv = tabContent.attr("relDv");
                var relTypeNm = tabContent.attr("relTypeNm");
                var metaRelCd = tabContent.attr("metaRelCd");
                var uiId = tabContent.attr("uiId");

                var frame = $("iframe", tabContent);
                frame.css("height", "100%");
                var tabParam = this.parameter;
                tabParam.relDv = relDv;
                tabParam.relTypeNm = relTypeNm;
                tabParam.metaRelCd = metaRelCd;
                tabParam.uiId = uiId;
                tabParam.objTypeId = this.parameter.objTypeId;

                DE.ui.open.frame(action, frame.prop("id"), tabParam, this.menuId);
            }
        };

        return DE.tabconfig;
    });

}).call(this);