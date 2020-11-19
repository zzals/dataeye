$(document).ready(function () {
	var reqParam = $("input#objInfoReqParam").data("data");
    var authInfo = $("input#objInfoReqParam").data("authInfo");
    request = {
        "param" : reqParam,
        "authInfo" : authInfo
    };
    DE.metaui.create(reqParam, authInfo, $("div#tabContentBody"));
    
    /* 
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
            DE.metaui.create(reqParam, $("div#tabContentBody"));
        }
	});
    */
});