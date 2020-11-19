
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
             ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
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
          ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
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

 	function goWrite() {
 		var post = {
				"viewId": "portal/qna/boardWriteForm",
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

	 function doUpdateProc() {

		if($("#title").val()==""){
			alert("제목을 입력해주세요.");
			$("#title").focus();
			return;
		}

		var brd_sbst=CKEDITOR.instances.brd_sbst.getData();
		if(brd_sbst==""){
			alert("내용을 입력해주세요.");
			$("#brd_sbst").focus();
			return;
		}

		var data = JSON.parse('${data}');
		
		var formData = new FormData();		
		formData.append("brd_id",$("#brd_id").val());
		formData.append("file_id",$("#file_id").val());
		formData.append("brd_type_id",$("#brd_type_id").val());
		formData.append("brd_nm",$("#brd_nm").val());
		formData.append("brd_sbst",brd_sbst);
		formData.append("brd_st_date","");
		formData.append("brd_ed_date","");
		formData.append("attacheFile",$("#attacheFile")[0].files[0]);
		
		debugger;
            $.ajax({
               url: 'board/boardUpdateProc',
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
					<form name="form"  id="form" method="post" enctype="multipart/form-data">
					 <input type="hidden" name="brd_id" id="brd_id">
					 <input type="hidden" name="file_id" id="file_id">
					 <input type="hidden" name="brd_type_id" id="brd_type_id" value="qna">
						<colgroup>
							<col width="150"/> 
							<col />
							<col width="150"/>
							<col />
							<col width="150"/>
							<col />
						</colgroup>
						<tr>
							<th>제목</th>
							<td colspan="5" class="notice_title"><input type="text" size="172" id="brd_nm" name="brd_nm"></td>
						</tr>						
							<td colspan="6" class="notice_txt">
								<textarea  rows="20" id="brd_sbst" name="brd_sbst"></textarea>
							</td>
						</tr> 						
						<tr>
							<th>첨부파일</th>
							<td colspan="5">
								<input type="file" id="attacheFile" name="attacheFile">
								<div id="attachFileDiv"></div>			
							</td>                             
						</tr>
					</form>
					</table>
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

		    debugger;
		    		
	    	$("#brd_id").val(data["rows"][0]["BRD_ID"]);
	    	$("#file_id").val(data["rows"][0]["FILE_ID"]);	
	    	$("#brd_type_id").val(data["rows"][0]["BRD_TYPE_ID"]); 
	    	$("#brd_nm").val(data["rows"][0]["BRD_NM"]);   							    
	    	$("#brd_sbst").html(data["rows"][0]["BRD_SBST"]);
	    	

	    	if(data["rows"][0]["ORG_NAME"] !=null) {
	    		$("#attachFileDiv").html(data["rows"][0]["ORG_NAME"] + "");
		    }
	    	
	    },
	    error: function(err) {
	        alert("error");
	    }
	});
});

CKEDITOR.replace("brd_sbst", {
	enterMode:'2',
	shiftEnterMode:'3'
});
</script>
</body>
</html>