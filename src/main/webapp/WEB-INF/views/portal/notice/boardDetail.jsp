
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
 		var data = JSON.parse('${data}')
 		var post = {
				"viewId": "portal/notice/boardUpdateForm",
				"menuId": "DATAEYE1_MENU_7ce36c54-5ff1-4b80-a51b-1bc1404fdf14",
				"data":"{\"ntc_id\":\"" + data["ntc_id"] + "\",\"ntc_type_id\":\"notice\"}"
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
<style>
.th_tab {    table-layout: fixed;
    width: 100%;}
.th_tab th, .th_tab td {border:none !important;}
.template_frm {display:none !important;}
.template_val {display:block !important;}
</style>
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
					<form name="frm>">
					   <input type="hidden" name="ntc_id" id="ntc_id">
					   <input type="hidden" name="file_id" id="file_id">
					   <input type="hidden" name="ntc_type_id" id="ntc_type_id">
					</form>
					<table class="Notice_write">
						<colgroup>
							<col width="150"/> 
							<col />
						</colgroup>
						<tr>
							<th>제목</th>
							<td class="notice_title"><div id="ntcNmDiv"></div></td>
						</tr>
						<tr>
							<th>작성자</th>
							<td>
								<table class="th_tab"> 
									<colgroup>
										<col />
										<col width="80"/> 
										<col />
										<col width="80"/>
										<col />
										<col width="80"/>
										<col />
										<col width="80"/>
										<col />
									</colgroup>
									<tr>
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
								</table>
							</td>
                   		</tr>
						<tr>
							<td colspan="2" class="notice_txt"><div id="ntcSbstDiv"></div></td>
						</tr>
						<c:if test="${AUTH.getUAuth() == 'Y'}" >
						<tr>
							<th>공지 노출</th>
							<td>
								<div id="ntcDsply"></div>
							</td>                             
						</tr>
						<tr>
							<th>팝업 노출</th>
							<td>
								<div id="popDsply"></div>
							</td>                             
						</tr>		
						<tr>
							<th>메인 게시일</th>
							<td><div id="stedDateDiv"></div></td>                             
						</tr>		
						</c:if>		
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
			"queryId":"portal.notice.getBoardDetail",
			"ntc_type_id":"notice",
			"ntc_id":data["ntc_id"]
	}
	$.ajax({ 
	    url:'jqgrid/list', //request 보낼 서버의 경로
	    type:'post', // 메소드(get, post, put 등)
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json' ,  
	    data:JSON.stringify(param), //보낼 데이터
	    success: function(data) {
			var detail = data["rows"][0];
		    console.log(detail);
	    	$("#ntc_id").val(detail["NTC_ID"]);
	    	$("#file_id").val(detail["FILE_ID"]);
	    	$("#ntc_type_id").val(detail["NTC_TYPE_ID"]);
	    	$("#ntcNmDiv").html(detail["NTC_NM"]);
	    	$("#cretrIdDiv").html(detail["CRETR_ID"]);
	    	$("#cretDtDiv").html(detail["CRET_DT"]);
	    	$("#chgrIdDiv").html(detail["CHGR_ID"]);
	    	$("#chgDtDiv").html(detail["CHG_DT"]);
	    	$("#readCntDiv").html(detail["READ_CNT"]);
	    	$("#ntcSbstDiv").html(detail["NTC_SBST"]);
	    	$("#ntcDsply").html(detail["NTC_DSPLY"]);
	    	$("#popDsply").html(detail["NTC_POPUP_DSPLY"]);
	    	$("#stedDateDiv").html(detail["NTC_ST_DATE"] + " ~ " + detail["NTC_ED_DATE"]);

			/* 2020-11-09 kse		
   	 		* 조회수 체크 ?
   	    	*/ 
		     $.ajax({
               url: 'notice/doReadCntProc?ntc_id=' + $("#ntc_id").val() + '&ntc_type_id=' + $("#ntc_type_id").val() + '&boardAuthKey=' + encodeURIComponent('<%=request.getAttribute("boardAuthKey")%>') ,                
               data: '',
               contentType: 'application/json; charset=utf-8',
               type: 'POST',
               success: function(result){
					//do nothing                                     
               }
           });
	    },
	    error: function(err) {
	        alert("error");
	    }
	});
		
	//alert(data["BOARD_NO"]);
	console.log(data);
});
</script>
</body>
</html>
