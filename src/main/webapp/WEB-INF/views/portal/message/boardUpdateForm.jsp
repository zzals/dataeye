
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title><spring:message code="app.title" text="DateEye" /></title>
	<link type="text/css" rel="stylesheet" href="<spring:url value="/assets/stylesheets/dataeye-main.css"/>">
</head>
<body>
<section class="content-header">
	<h1 style="font-size:18px;">&nbsp;공지사항</h1>
    <ol class="breadcrumb" style="top:30px;"><li><a href="#"><i class="fa fa-folder-o"></i>HOME</a></li><li class="active">&nbsp;공지사항</li></ol>
</section>
<section class="content" style="padding-top:5px;">
	<div class="content-body" style="margin-top:10px;">
    	<div class="row">
			<div class="col-xs-12">
				<div class="Grid_Area">
					<form name="msg_frm" id="msg_frm" method="post">
						<input type="hidden" name="send_type" value="P" />
						<input type="hidden" id="msgId" name="msg_id" />
						
						<table class="Notice_write">
							<colgroup>
								<col width="150"/> 
								<col />
							</colgroup>
							<tr>
								<th>제목</th>
								<td class="notice_title"><input type="text" id="msg_nm" name="title" style="width:95%;" /></td>
							</tr>	
							<tr>
								<th>타입</th>
								<td class="notice_title">
									<input type="radio" id="editor_btn" name="editor_type" value="E" checked="checked" /><label for="editor_btn">Editor</label>
									<input type="radio" id="template_btn" name="editor_type" value="T" /><label for="template_btn">Template</label>
								</td>
							</tr>
							<tr>				
								<td colspan="2" class="notice_txt">
									<div id="editorArea">
										<textarea  rows="20" id="" name="msg_cntnt"></textarea>
										<script>
											CKEDITOR.replace("msg_cntnt", {
												enterMode:'2',
												shiftEnterMode:'3'
											});
		                				</script>
	                				</div>
	                				<div id="templateArea" style="display:none;">
	                					<div>
	                						<select id="templateSel">
	                							<option>선택하세요</option>
	                						</select>
	                					</div>
	                					<div id="templateFrm">
		                					
	                					</div>
	                				</div>
								</td>
							</tr> 	
							<tr>
								<th>발송 대상</th>
								<td>
									<input type="text" id="sendTarget" name="send_target"  />	
								</td>                             
							</tr>
							<tr>
								<th>발송 방법</th>
								<td>
									<input type="checkbox" id="portal_msg" name="send_mothod" value="msg" /><label for="portal_msg">포털 메세지</label>	
									&nbsp;&nbsp;
									<input type="checkbox" id="ebay_email" name="send_mothod" value="mail" /><label for="ebay_email">Email</label>	
									&nbsp;&nbsp;
									<input type="checkbox" id="ebay_slack" name="send_mothod" value="slack" /><label for="ebay_slack">Slack</label>
									&nbsp;&nbsp;
									<input type="checkbox" id="ebay_mms" name="send_mothod" value="mms" /><label for="ebay_mms">MMS</label>		
								</td>                             
							</tr>
							<tr>
								<th>발송 여부</th>
								<td class="notice_title">
									<input type="radio" id="" name="send_stts" value="W" checked="checked" /><label for="template_btn">등록</label>
									<input type="radio" id="" name="send_stts" value="S" /><label for="editor_btn">등록+발송</label>
								</td>
							</tr>		
						</table>
					</form>
				</div>
				<div style="text-align:right; margin-top:10px;">
					<button type="submit" id="" onClick="doUpdateProc()" class="board_btn">등록</button>		
					<button type="submit" id="" class="board_btn" onClick="goList()">목록</button>			
				</div>
			</div>
		</div>
	</div>
</section>
<script language="javascript">
var template_html = {} ;

$(document).ready(function() {
	var data = JSON.parse('${data}');
	
	var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc"
			,"queryId":"portal.board.getMessageDetail"
			,"msg_id":data["msg_id"]
	}
	$.ajax({ 
	    url:'jqgrid/list', //request 보낼 서버의 경로
	    type:'post', // 메소드(get, post, put 등)
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json' ,  
	    data:JSON.stringify(param), //보낼 데이터
	    success: function(data) {

	    	var detail = data["rows"][0];

	    	$("#msgId").val(detail["MSG_ID"]);
	    	$("#msg_nm").html(detail["TITLE"]);
	    	$("#sendTarget").html(detail["SEND_TARGET"]);
	    	$("#sendMothodDiv").html(detail["SEND_MOTHOD"]);
	    	$("#msgCntnDiv").html(detail["CNTNT"]);

			if(detail.EDITOR_TYPE == "T"){
				$("#templateFrm").html(detail["NTC_SBST"]);
		    	$("#template_btn").prop("checked",true);
		    	templateType();
			}else{
		    	$("#ntc_sbst").html(detail["NTC_SBST"]);
				$("#editor_btn").prop("checked",true);
				editorType();
			}

	    	if(detail.NTC_DSPLY === "Y"){
				$("input[name='ntcDsply']").prop("checked",true)
	    	};

	    	if(detail.NTC_POPUP_DSPLY === "Y"){
				$("input[name='popDsply']").prop("checked",true)
	    	};
	    	

	    	$(".template_val").each(function(){
		    	var val = $(this).text();
		    	$(this).prev("input").val(val);
	    	})
		    	
	    	
	    },
	    error: function(err) {
	        alert("error");
	    }
	});


	var tparam = {"queryId":"metapublic.getObjTypeAtrVal",
 			"rows":100,
			"objTypeId":"090201L",
			"atrIdSeq":'101'
	}
	$.ajax({ 
	    url:'jqgrid/list', //request 보낼 서버의 경로
	    type:'post', // 메소드(get, post, put 등)
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json' ,  
	    data:JSON.stringify(tparam), //보낼 데이터
	    success: function(data) {
			var detail = data["rows"];
			var opts = "";
			for(let d of detail){
			    opts += "<option value='"+d.OBJ_ID+"'>"+d.OBJ_NM+"</option>"
			    template_html[d.OBJ_ID] = d.OBJ_ATR_VAL
		    }
		    $("#templateSel").append(opts);
	    },
	    error: function(err) {
	        alert("error");
	    }
	});
});

CKEDITOR.replace("ntc_sbst", {
	enterMode:'2',
	shiftEnterMode:'3'
});

$("#editor_btn").on("click", editorType);
$("#template_btn").on("click", templateType);

 function editorType(){
 	$("#editorArea").show();
	$("#templateArea").hide();
 }
 function templateType(){
	 $("#editorArea").hide();
	$("#templateArea").show();
 }

 $("#templateSel").on("change", function(){
	let val = $(this).val();
	console.log(val);
	if(val != ""){
	 if(!confirm("기존의 내용이 사라집니다.\n계속 하시겠습니까?")){
		return false;
	 }
	}
	$("#templateFrm").html(template_html[val]);
 })	

 $("#templateFrm").on("change keyup input", ".template_frm", function(){		 
	var val = $(this).val();
	($(this).get(0)).value = val;
	$(this).next('div').html(val);
 })
 
 function goList() {
 	 	
	var post = {
		"viewId": "portal/notice/boardList",
		"menuId": "DATAEYE1_MENU_7ce36c54-5ff1-4b80-a51b-1bc1404fdf14"
	};
	$(".content-wrapper").load(DE.contextPath+"/portal/view", post, function(response, status, xhr) {
		$(document).off("autoResize");
		if (status === "error") {				
			$(".content-wrapper").html(response);
		} else {
	  	//	DE.content.setHeader($(e.currentTarget));
	  	//	$(window).trigger("resize");
		}
	});
 }

 function doUpdateProc() {
 	let msgForm = document.querySelector("#msg_frm");
	let formData = new FormData(msgForm);
	let eType = $("input[name=editor_type]:checked").val();
	let cntnt = "";
	
	if($("#msg_nm").val()==""){
		alert("제목을 입력해주세요.");
		$("#msg_nm").focus();
		return;
	}

	if(eType == "T"){
		ntc_sbst = document.querySelector("#templateFrm").innerHTML;
	}else{
		ntc_sbst=CKEDITOR.instances.ntc_sbst.getData();
	}
	
	if(ntc_sbst==""){
		alert("내용을 입력해주세요.");
		$("#ntc_sbst").focus();
		return;
	}

	var data = JSON.parse('${data}');
	
	var formData = new FormData();
	formData.append("ntc_id",$("#ntc_id").val());
	formData.append("ntc_type_id",$("#ntc_type_id").val());
	formData.append("file_id",$("#file_id").val());
	formData.append("ntc_nm",$("#ntc_nm").val());
	formData.append("ntc_sbst",ntc_sbst);
	formData.append("ntc_st_date",$("#sdate").val());
	formData.append("ntc_ed_date",$("#edate").val());
//	formData.append("attacheFile",$("#attacheFile")[0].files[0]);

	formData.append("ntc_dsply",$("input[name=ntcDsply]").is(":checked") ? "Y" : "N");
	formData.append("pop_dsply",$("input[name=popDsply]").is(":checked") ? "Y" : "N");
	
           $.ajax({
              url: 'notice/noticeUpdateProc',
              processData: false,
              contentType: false,
              data: formData,
              type: 'POST',
              success: function(result){
                  alert("수정되었습니다.");
                  goList();
              }
          });
	 
}

</script>
</body>
</html>