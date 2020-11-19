$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
	

	var makeForm = function() {
		return {
			"USER_ID":$("#USER_ID").val(),
			"USER_PASSWORD":$("#USER_PASSWORD").val(),
			"USER_NEW_PASSWORD":$("#USER_NEW_PASSWORD").val(),
			"USER_NEW_PASSWORD_CHK":$("#USER_NEW_PASSWORD_CHK").val()
		};
	};
	
	
	
	// update new password
	$("button#btnUpdate").on("click", function(e) {
		var callback = {
			success : function(response) {
				DE.box.alert(response["message"], function(){ self.close();});
			},
			error : function(response) {
				DE.box.alert(response["responseJSON"]["message"]);
			}
		};
		debugger;
		var opts = {
			url : "portal/myPwd?oper=updateMyPwd",
			data : $.param(makeForm())
		};
		
		DE.ajax.formSubmit(opts, callback.success, callback.error);
	
	});	
	
	$("#USER_ID").val()
	var init = function() {
	
		$(".popup_title").html("사용자 비밀번호 수정");
		$(".popup_title").show();
		$("button#btnUpdate").css("display", "inline-block");

	};
	
	init();  
	
});