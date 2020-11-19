$(document).ready(function() {
	var parentObj = opener || parent;
	var reqParam = $("input#reqParam").data();
    
	var postData = {
		"mode":reqParam["mode"],
		"objTypeId":reqParam["objTypeId"],
		"objId":reqParam["objId"],
		"uiId":reqParam["uiId"]
	};
	DE.ajax.call({async:true, url:"metacore/uiview?oper=render", data:postData}, function(rsp){
		var confJS = rsp["data"]["CONF_JS"];
		
		var $gridOptsScript = $("<script type=\"text/javascript\">");
		$gridOptsScript.append(confJS);
		$gridOptsScript.appendTo(document.body);
	});
});