
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
					<table class="Notice_write">
					<form name="form"  id="form" method="post" enctype="multipart/form-data">
					<input type="hidden" name="ntc_type_id" id="ntc_type_id" value="notice">
						<colgroup>
							<col width="150"/> 
							<col />
						</colgroup>
						<tr>
							<th>제목</th>
							<td class="notice_title"><input type="text" id="ntc_nm" name="ntc_nm" style="width:95%;"></td>
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
									<textarea  rows="20" id="ntc_sbst" name="ntc_sbst"></textarea>
									<script>
										CKEDITOR.replace("ntc_sbst", {
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
							<th>공지 노출</th>
							<td>
								<input type="checkbox" id="" name="ntcDsply" />	
							</td>                             
						</tr>
						<tr>
							<th>팝업 노출</th>
							<td>
								<input type="checkbox" id="" name="popDsply" />			
							</td>                             
						</tr>
						<tr>
							<th>메인 게시일</th>
							<td>
									<input type="text" class="form-control" id="sdate" name="sdate" placeholder="" style="width:100px">
									<span class="cal_btn" onclick="$('#sdate').focus()"><i class="glyphicon glyphicon-calendar"></i></span> 						
									
									~
									<input type="text" class="form-control" id="edate" name="edate" placeholder="" style="width:100px">
									<span class="cal_btn" onclick="$('#edate').focus()"><i class="glyphicon glyphicon-calendar"></i></span>
							</td>                             
						</tr>				
					</form>
					</table>
				</div>
				<div style="text-align:right; margin-top:10px;">
					<button type="submit" id="" onClick="doWriteProc()" class="board_btn">등록</button>		
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
		"viewId": "portal/notice/boardList",
		"menuId": "DATAEYE1_MENU_7ce36c54-5ff1-4b80-a51b-1bc1404fdf14"
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
	let eType = $("input[name=editor_type]:checked").val();
	let ntc_sbst = "";
		
	if($("#ntc_nm").val()==""){
		alert("제목을 입력해주세요.");
		$("#ntc_nm").focus();
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

	var formData = new FormData();
	formData.append("ntc_nm",$("#ntc_nm").val());
	
	formData.append("ntc_sbst",ntc_sbst);
	formData.append("ntc_type_id","notice");
	formData.append("ntc_st_date",$("#sdate").val());
	formData.append("ntc_ed_date",$("#edate").val());
//	formData.append("attacheFile",$("#attacheFile")[0].files[0]);

	formData.append("ntc_dsply",$("input[name=ntcDsply]").is(":checked") ? "Y" : "N");
	formData.append("pop_dsply",$("input[name=popDsply]").is(":checked") ? "Y" : "N");
	formData.append("ntc_sbst_type", eType);
	
    $.ajax({
       url: 'notice/noticeWriteProc',
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