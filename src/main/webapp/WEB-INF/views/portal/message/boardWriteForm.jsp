
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
	<h1 style="font-size:18px;">&nbsp;메세지</h1>
    <ol class="breadcrumb" style="top:30px;"><li><a href="#"><i class="fa fa-folder-o"></i>HOME</a></li><li class="active">&nbsp;메세지</li></ol>
</section>
<section class="content" style="padding-top:5px;">
	<div class="content-body" style="margin-top:10px;">
    	<div class="row">
			<div class="col-xs-12">
				<div class="Grid_Area">
					<form name="msg_frm" id="msg_frm" method="post">
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
									<input type="text" id="" name="send_target"  />	
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
					<button type="" id="" onClick="doWriteProc()" class="board_btn">등록</button>		
					<button type="" id="" onClick="goList()" class="board_btn">취소</button>					
				</div>
			</div>
		</div>
	</div>
</section>
</body>
</html>

<script language="javascript">
	var template_html = {} ;
	
	 $(function() {
	 	var param = {"queryId":"metapublic.getObjTypeAtrVal",
	 			"rows":100,
				"objTypeId":"090201L",
				"atrIdSeq":'101'
		}
		$.ajax({ 
		    url:'jqgrid/list', //request 보낼 서버의 경로
		    type:'post', // 메소드(get, post, put 등)
		    contentType: 'application/json; charset=utf-8',
		    dataType: 'json' ,  
		    data:JSON.stringify(param), //보낼 데이터
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
	
	 $("#editor_btn").on("click", () => {
		$("#editorArea").show();
		$("#templateArea").hide();
	 });
	
	 $("#template_btn").on("click", () => {
			$("#editorArea").hide();
			$("#templateArea").show();
		 });
	
	
	 $("#templateFrm").on("change keyup input", ".template_frm", function(){	 
		var val = $(this).val();
		($(this).get(0)).value = val;
		$(this).next('div').html(val);
	 })
	
	 $("#templateSel").on("change", function(){
		let val = $(this).val();
		$("#templateFrm").html(template_html[val]);
	 })	
	 
	function goList() {
		var post = {
			"viewId": "portal/message/boardList",
			"menuId": localStorage.getItem("menu_id")
		};
		$(".content-wrapper").load(DE.contextPath+"/portal/view", post, function(response, status, xhr) {
			$(document).off("autoResize");
			if (status === "error") {				
				$(".content-wrapper").html(response);
			} else {
		  		DE.content.setHeader($(e.currentTarget));
		  		$(window).trigger("resize");
			}
		});
	 }
	
	
	 function doWriteProc() {
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
			cntnt = document.querySelector("#templateFrm").innerHTML;
		}else{
			cntnt = CKEDITOR.instances.msg_cntnt.getData();
		}
		
		if(cntnt == ""){
			alert("내용을 입력해주세요.");
			$("#cntnt").focus();
			return;
		}

		formData.append("send_type", "P");
		formData.append("cntnt",cntnt);
		
		for (var pair of formData.entries()) { console.log(pair[0]+ ', ' + pair[1]); }
		
	    $.ajax({
	       url: 'board/messageWriteProc',
	       processData: false,
	       contentType: false,
	       data: formData,
	       type: 'POST',
	       success: function(result){
	           alert("등록되었습니다.");
	           goList();
	       }
	   });
		 
	}
		
</script>