
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
<script language="javascript">
$(function() {
    $( "#sdate" ).datepicker({
    	 dateFormat: 'yy-mm-dd' //Input Display Format 변경
         ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
         ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
         ,showOn: "button" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
         ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
         ,buttonImageOnly: false //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
         ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
         ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
         ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
         ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
         ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
                     
    });
    $('.ui-datepicker-trigger').hide();
  $('#sdate').datepicker('setDate', 'today');

  $( "#edate" ).datepicker({
    	 dateFormat: 'yy-mm-dd' //Input Display Format 변경
      ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
      ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
      ,showOn: "button" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
      ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
      ,buttonImageOnly: false //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
      ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
      ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
      ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
      ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
      ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
                  
    });
    $('.ui-datepicker-trigger').hide();
  $('#edate').datepicker('setDate', 15);
   
});

 	function goList() {
 		var post = {
				"viewId": "portal/qna/boardList",
				"menuId": "DATAEYE1_MENU_d596ebad-1410-4777-9b4a-09725495dbf6"
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

 	function goUpdateForm() {
 		var data = JSON.parse('${data}')
 		var post = {
				"viewId": "portal/qna/boardUpdateForm",
				"menuId": "DATAEYE1_MENU_d596ebad-1410-4777-9b4a-09725495dbf6",
				"data":"{\"brd_id\":\"" + data["brd_id"] + "\",\"brd_type_id\":\"qna\"}"
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


 	 function doDeleteProc() {
 		
  		var cf = confirm("삭제 하시겠습니까?");
		if(cf) {	 		
	 		     $.ajax({
	                url: 'board/doDeleteProc?brd_id=' + $("#brd_id").val() + '&brd_type_id=qna&boardAuthKey=' + encodeURIComponent('<%=request.getAttribute("boardAuthKey")%>') ,                
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

	 function viewReply(){
		 var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.board.getBoardReplyList","brd_type_id":"qna","brd_id":$("#brd_id").val()}
			$.ajax({ 
			    url:'jqgrid/list', //request 보낼 서버의 경로
			    type:'post', // 메소드(get, post, put 등)
			    contentType: 'application/json; charset=utf-8',
			    dataType: 'json' ,  
			    data:JSON.stringify(param), //보낼 데이터
			    success: function(data) {			    						
			    	var replyHtml = "<table border=\"0\" width=\"100%\">";

			    	$.each(data["rows"], function (index, item) {
			    		replyHtml =  replyHtml + "<tr> ";
			    		replyHtml =  replyHtml + "	<td height=\"30px\">" + item["RPL_SBST"].replace(/\n/g, "<br/>")  + "</BR><b>" + item["CRET_DT"] + "&nbsp;&nbsp;&nbsp;" +  item["CRETR_ID"] + "</b></td>";			    					    		
			    		replyHtml =  replyHtml + "</tr>";
			    	});
			    	replyHtml =  replyHtml + "</table>";
			    	
			    	$("#replyCont").html(replyHtml);
			    },
			    error: function(err) {
			        alert("error");
			    }
			});			
	 } 
	 
 	 function doReplyWriteProc() {
 		if($("#cont").val()==""){
 			alert("내용을 입력해주세요.");
 			$("#cont").focus();
 			return;
 		}
 		
 		var formData = new FormData();
 		formData.append("brd_id",$("#brd_id").val());
 		formData.append("brd_type_id",$("#brd_type_id").val()); 		
 		formData.append("rpl_sbst",$("#cont").val());
 		
             $.ajax({
                url: 'board/boardReplyWriteProc',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                success: function(result){
                	$("#cont").val("");                    
                    alert("등록되었습니다.");
                    viewReply();
                }
            });
 		 
 	}
 	
</script>
<body>
<section class="content-header">
	<h1 style="font-size:18px;">&nbsp;Q&A</h1>
    <ol class="breadcrumb" style="top:30px;"><li><a href="#"><i class="fa fa-folder-o"></i>HOME</a></li><li class="active">&nbsp;Q&A</li></ol>
</section>
<section class="content" style="padding-top:5px;">
	<div class="content-body" style="margin-top:10px;">
    	<div class="row">
			<div class="col-xs-12">
				<div class="Grid_Area">
					<table class="Notice_write">
					<form name="frm>">
					   <input type="hidden" name="brd_id" id="brd_id">
					   <input type="hidden" name="brd_type_id" id="brd_type_id">
					   <input type="hidden" name="file_id" id="file_id">
					</form>
						<colgroup>
							<col width="150"/> 
							<col />
							<col width="100"/>
							<col />
							<col width="100"/>
							<col />
							<col width="100"/>
							<col />
							<col width="100"/>
							<col />
						</colgroup>
						<tr>
							<th>제목</th>
							<td colspan="9" class="notice_title"><div id="brdNmDiv"></div></td>
						</tr>
						<tr>
							<th>작성자</th>
							<td><div id="cretrIdDiv"></div></td>
							<th>등록일</th>
							<td><div id="cretDtDiv"></div></td>
							<th>수정자</th>
							<td><div id="chgrIdDiv"></div></td>
							<th>수정일</th>
							<td><div id="chgDtDiv"></div></td>
							<th>조회수</th>
							<td><div id="readCntDiv"></div></td>
                   		</tr>
						<tr>
							<td colspan="10" class="notice_txt"><div id="brdSbstDiv"></div></td>
						</tr> 						
						<tr>
							<th>첨부파일</th>
							<td colspan="9"><div id="attachFileDiv"></div></td>                             
						</tr>
					</table>
				</div>
				<div style="text-align:right; margin-top:10px;">
					<button type="submit" id="" class="board_btn" onClick="goList()">목록</button>
					<button type="submit" id="" class="board_btn" onClick="goUpdateForm()">수정</button>
					<button type="submit" id="" class="board_etc_btn"  onClick="doDeleteProc()">삭제</button>
					<!-- 
					<button type="submit" id="" class="board_btn">글쓰기</button>
					<button type="submit" id="" class="board_btn">수정</button>
					<button type="submit" id="" class="board_etc_btn">삭제</button>
					 -->
				</div>
				<br>
				<div class="Grid_Area">
					<table class="Notice_write"> 
					<colgroup>
							<col width="150"/> 
					</colgroup>
					<tr>
						<th style="text-align:left;padding-left:15px">답변</th>
					</tr>					
					<tr>
						<td style="padding-top: 10px;padding-bottom: 10px;">
							<div id="replyCont"></div>											
						</td>
					</tr>
					</table>
				</div>
				<br>
				<table class="Notice_write">
				<tr>
					<td  class="notice_txt">
						<textarea  rows="5" style="width:1400px" id="cont" name="cont"></textarea>
					</td>
				</tr>
				</table>	
					<div style="text-align:right; margin-top:10px;">
					<button type="submit" id="" class="board_btn" onClick="doReplyWriteProc()">답변등록</button>
				</div>			
			</div>
		</div>
	</div>
</section>
<script language="javascript">
$(document).ready(function() {
	var data = JSON.parse('${data}');

	var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.board.getBoardDetail","brd_type_id":"qna","brd_id":data["brd_id"]}
	$.ajax({ 
	    url:'jqgrid/list', //request 보낼 서버의 경로
	    type:'post', // 메소드(get, post, put 등)
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json' ,  
	    data:JSON.stringify(param), //보낼 데이터
	    success: function(data) {

	    	$("#brd_id").val(data["rows"][0]["BRD_ID"]);
	    	$("#brd_type_id").val(data["rows"][0]["FILE_ID"]);
	    	$("#brd_type_id").val(data["rows"][0]["BRD_TYPE_ID"]);
	    	$("#brdNmDiv").html(data["rows"][0]["BRD_NM"]);
	    	$("#cretrIdDiv").html(data["rows"][0]["CRETR_ID"]);
	    	$("#cretDtDiv").html(data["rows"][0]["CRET_DT"]);
	    	$("#chgrIdDiv").html(data["rows"][0]["CHGR_ID"]);
	    	$("#chgDtDiv").html(data["rows"][0]["CHG_DT"]);
	    	$("#readCntDiv").html(data["rows"][0]["READ_CNT"]);
	    	$("#brdSbstDiv").html(data["rows"][0]["BRD_SBST"]);
	    	
	    	if(data["rows"][0]["FILE_NM"] !=null) {
	    		$("#attachFileDiv").html("<a href=board/download?file_id=" + data["rows"][0]["FILE_ID"] + ">" + data["rows"][0]["FILE_NM"] + "</a>");
		    }
	 		
		     $.ajax({
               url: 'board/doReadCntProc?brd_id=' + $("#brd_id").val() + '&brd_type_id=qna&boardAuthKey=' + encodeURIComponent('<%=request.getAttribute("boardAuthKey")%>') ,                
               data: '',
               contentType: 'application/json; charset=utf-8',
               type: 'POST',
               success: function(result){
					//do nothing                                      
               }
           });
		     viewReply();
	    },
	    error: function(err) {
	        alert("error");
	    }
	});
		
	//alert(data["BOARD_NO"]);
});
</script>
</body>
</html>
