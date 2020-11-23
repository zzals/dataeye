
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title><spring:message code="app.title" text="DateEye" /></title>
</head>
<style>
.th_tab {    table-layout: fixed;
    width: 100%;}
.th_tab th, .th_tab td {border:none !important;}
.template_frm {display:none !important;}
.template_val {display:block !important;}
</style>
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
					<form name="frm>">
					   <input type="hidden" id="msgId" name="msg_id" />
					   <input type="hidden" name="ntc_type_id" id="ntc_type_id">
					</form>
					<table class="Notice_write">
						<colgroup>
							<col width="150"/> 
							<col />
						</colgroup>
						<tr>
							<th>제목</th>
							<td class="notice_title"><div id="msgTitle"></div></td>
						</tr>
						<tr>
							<th>작성자</th>
							<td>
								<table class="th_tab"> 
									<colgroup>
										<col />
										<col width="80"/> 
										<col />
									</colgroup>
									<tr>
										<td><div id="cretrIdDiv"></div></td>
										<th>작성일</th>
										<td><div id="cretDtDiv"></div></td>
									</tr>
								</table>
							</td>
                   		</tr>
                   		<tr>
							<th>발송상태</th>
							<td>
								<table class="th_tab"> 
									<colgroup>
										<col />
										<col width="80"/> 
										<col />
									</colgroup>
									<tr>
										<td><div id="sendStts"></div></td>
										<th>발송일</th>
										<td><div id="sendDtDiv"></div></td>
									</tr>
								</table>
							</td>
                   		</tr>
						<tr>
							<th>발송 대상</th>
							<td>
								<div id="sendTargetDiv"></div>
							</td>                             
						</tr>
						<tr>
							<th>발송 방법</th>
							<td>
								<div id="sendMothodDiv"></div>
							</td>                             
						</tr>
						<tr>
							<th>내용</th>
							<td colspan="" class="notice_txt"><div id="msgCntnDiv"></div></td>
						</tr>
					</table>
				</div>
				<div style="text-align:right; margin-top:10px;">
					<button type="submit" id="" class="board_btn" onClick="goList()">목록</button>
					<c:if test="${AUTH.getUAuth() == 'Y'}" >
					<button type="submit" id="" class="board_btn" onClick="goUpdateForm()">수정</button>
					</c:if>
					<c:if test="${AUTH.getDAuth() == 'Y'}" >
					<button type="submit" id="" class="board_etc_btn"  onClick="doDeleteProc()">삭제</button>
					</c:if>
				</div>
							
			</div>
		</div>
	</div>
</section>
<script language="javascript">
$(document).ready(function() {
	var data = JSON.parse('${data}');
	var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc",
			"queryId":"portal.board.getMessageDetail",
			"msg_id":data["msg_id"]
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
	    	$("#msgTitle").html(detail["TITLE"]);
	    	$("#cretrIdDiv").html(detail["WRIT_ID"]);
	    	$("#cretDtDiv").html(detail["WRIT_DT"]);
	    	$("#sendStts").html(detail["SEND_STTS"]);
	    	$("#sendDtDiv").html(detail["SEND_DT"]);
	    	$("#sendTargetDiv").html(detail["SEND_TARGET"]);
	    	$("#sendMothodDiv").html(detail["SEND_MOTHOD"]);
	    	$("#msgCntnDiv").html(detail["CNTNT"]);

			/* 2020-11-23 kse		
   	 		* 메세지 확인 업데이트
   	    	*/ 
		     <%-- 
		     $.ajax({
               url: 'notice/doReadCntProc?ntc_id=' + $("#ntc_id").val() + '&ntc_type_id=' + $("#ntc_type_id").val() + '&boardAuthKey=' + encodeURIComponent('<%=request.getAttribute("boardAuthKey")%>') ,                
               data: '',
               contentType: 'application/json; charset=utf-8',
               type: 'POST',
               success: function(result){
					//do nothing                                     
               } 
           }); --%>
	    },
	    error: function(err) {
	        alert("error");
	    }
	});
		
});

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

function goUpdateForm() {
	var msgId = $("#msgId").val();
	var post = {
		"viewId": "portal/message/boardUpdateForm",
		"menuId": localStorage.getItem("menu_id"),
		"data":JSON.stringify({"msg_id": msgId})
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


function doDeleteProc() {
	var cf = confirm("삭제 하시겠습니까?");
	if(cf) {	 		
 		     $.ajax({
                url: 'notice/doDeleteProc?ntc_id=' + $("#ntc_id").val() + '&ntc_type_id=' + $("#ntc_type_id").val() + '&file_id=' + $("#file_id").val() + '&boardAuthKey=' + encodeURIComponent('<%=request.getAttribute("boardAuthKey")%>') ,                
                data: '',
                contentType: 'application/json; charset=utf-8',
                type: 'POST',
                success: function(result){
                    alert("삭제되었습니다.");
                    goList();
                    
                }
            });
	}
}
</script>
</body>
</html>
