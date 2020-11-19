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
<script src="<spring:url value="/assets/javascripts/dataeye.tabconfig.js"/>"></script>
<style type="text/css">
#container[de-tag=object-info-pop] {
	height: -moz-calc(100vh - 50px);
    height: -webkit-calc(100vh - 50px);
    height: calc(100vh - 50px);
	/* border: 1px solid red; */
}

#objInfo_tabs {
	height: -moz-calc(100vh - 160px);
    height: -webkit-calc(100vh - 160px);
    height: calc(100vh - 160px);
	/* border: 1px solid blue; */
}

iframe {
	height: 100%;
    width: 100%;
	border: 1px solid red;
}
</style>    
</head>
<body>
<!-- UI Object -->
<div id="container" de-tag="object-info-pop">
    <!-- HEADER -->
    <div id="header">
        <div class="popup_Title_Area">
            <div class="popup_title"></div>
        </div>
    </div>
    <!-- //HEADER -->
    <div id="objInfo_tabs" class="popup_Content_Area" data-tabs="tabs">
        <ul class="nav nav-tabs"></ul>
        <div id="tab-content" class="tab-content"></div>
    </div>

    <div class="navbar navbar-blend navbar-fixed-bottom">
        <div id="actionDiv" class="text-right nav de-obj-btn"></div>
    </div>

    <span class="pop_close"><button type="button" class="close" onclick="window.close()">x</button></span>
</div>

<input type="hidden" id="reqParam">
<script type="text/javascript">
$("input#reqParam").data(JSON.parse('${data}'));
</script>
<script type="text/javascript">
$(document).ready(function () {
    var reqParam = JSON.parse('${data}');
    if (reqParam["isReq"] === undefined) {
    	reqParam["isReq"] = false;	
    }
    
    var authInfo = JSON.parse('${AUTH_JSON}');
    var menuId = '${menuId}';
    
    $.receiveMessage(function(e) {
        if (e.data.action === 'alert') {
            DE.box.alert(e.data.message);
        }
    });

    $(window).resize(function () {
        var tab_id = $("#objInfo_tabs > ul > li.active > a").attr("href");
        $.postMessage('getResize', '*', $("iframe", tab_id).get(0).contentWindow);
    });

    if (reqParam.mode == "C") {
        if (authInfo.cauth == "N") {
            DE.box.alert("등록 권한이 없습니다.", function () {
                self.close();
            });
        }
        else {
            $('#actionDiv').append('<a id="btnAction" class="btn btn-default btn-sm" de-obj-action="cancel" role="button" href="#"><i class="fa fa-ban" aria-hidden="true"></i> 취소</a>');
        }
    }
    else if (reqParam.mode == "R") {
        if (authInfo.rauth == "N") {
            DE.box.alert("읽기 권한이 없습니다.", function () {
                self.close();
            });
        }
        else {
            if (authInfo.uauth == "Y") {
                $('#actionDiv').append('<a id="btnAction" class="btn btn-default btn-sm" de-obj-action="modify" role="button" href="#">수정</a> ');
            }
            if (authInfo.dauth == "Y") {
                $('#actionDiv').append('<a id="btnAction" class="btn btn-default btn-sm" de-obj-action="delete" role="button" href="#">삭제</a>');
            }
        }
    }
    else if (reqParam.mode == "RO") {		//read only
    	reqParam.mode = "R";
        if (authInfo.rauth == "N") {
            DE.box.alert("읽기 권한이 없습니다.", function () {
                self.close();
            });
        }
    }
    else if (reqParam.mode == "U") {
        if (authInfo.uauth == "N") {
            DE.box.alert("수정 권한이 없습니다.", function () {
                self.close();
            });
        }
        else {
            $('#actionDiv').append('<a id="btnAction" class="btn btn-default btn-sm" de-obj-action="back" role="button" href="#"><i class="fa fa-undo" aria-hidden="true"></i> 돌아가기</a>');
        }
    }
    else if (reqParam.mode == "RC") { //결재요청
    	
    }

    DE.tabconfig.setTitle(reqParam);
    DE.tabconfig.createTab("objInfo_tabs", reqParam, authInfo, menuId);

    $("#objInfo_tabs > ul > li:first > a").click();

    /* $(window).resize(function () {
        $("#container").height($(this).height());
        $(".tab-content").height($("#container").height() - $(".tab-content").position().top);
    }).resize(); */

    $("#actionDiv").find("a[id=btnAction]").on("click", function (e) {
        if ($(this).attr("de-obj-action") == 'delete') {
        	DE.box.confirm("삭제 하시겠습니까?", function (b) {
                if (b === true) {
                    var callData = {
                        async: false,
                        url: "metacore/objectInfo/delete",
                        data: {objTypeId: reqParam.objTypeId, objId: reqParam.objId}
                    };

                    if (!DE.fn.isempty(reqParam.schema)) {
                        callData = {
                            async: false,
                            url: reqParam.schema + "/delete",
                            data: {keyVal: reqParam.keyVal}
                        };
                    }

                    var result = DE.ajax.call(callData);

                    if (result["status"] == "SUCCESS") {
                        DE.box.alert("삭제 되었습니다.", function () {
                        	var parentObj = opener || parent;
                        	if (parentObj !== null) {
                        		parentObj.$("[id=btnSearch]").trigger("click");
                        	}
                            self.close();
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
            reqParam.mode = 'U';
            DE.ui.open.frame("common/metacore/objectInfoTab", top.name, reqParam, menuId);
        }
        else if ($(this).attr("de-obj-action") == 'back') {
            if (!DE.fn.isempty(reqParam.formObj) && !DE.fn.isempty(reqParam.formObjUrl)) {

                var $frm = $("#_returnForm");

                if ($frm.length == 0) {
                    $frm = $("<form id='_returnForm' name='_returnForm' method='post'></form>").css("display", "none");
                    $frm.attr("action", reqParam.formObjUrl);
                    $frm.attr("enctype", "multipart/form-data");
                    $frm.appendTo("body");
                }

                var form_data = reqParam.formObj.split('&');
                var input = {};
                var $input;

                $.each(form_data, function(key, value) {
                    var data = value.split('=');
                    $input = $("<input type='hidden' id='" + data[0] + "' name='" + data[0] + "'/>");
                    $input.appendTo($frm);
                    $input.attr("value", decodeURIComponent(data[1]));
                });

                var $menuId = $("<input type='hidden' id='menuId' name='menuId'/>");
                $menuId.attr("value", reqParam.menuId);
                $menuId.appendTo($frm);

                $frm.submit();
                $frm.remove();
            }
            else {
                reqParam.mode = 'R';
                DE.ui.open.frame("common/metacore/objectInfoTab", top.name, reqParam, menuId);
            }
        }
        else if ($(this).attr("de-obj-action") == 'cancel') {
            self.close();
        }

        return;
    });
});
//opener callback toss function
var objinfo_callback = function(objInfo) {
	var reqParam = $("input#reqParam").data();
	eval("opener."+reqParam.collback+"(objInfo);");
}
</script>
</body>
</html>