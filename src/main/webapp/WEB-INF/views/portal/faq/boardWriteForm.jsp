
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
	<h1 style="font-size:18px;">&nbsp;FAQ</h1>
    <ol class="breadcrumb" style="top:30px;"><li><a href="#"><i class="fa fa-folder-o"></i>HOME</a></li><li class="active">&nbsp;FAQ</li></ol>
</section>
<section class="content" style="padding-top:5px;">
	<div class="content-body" style="margin-top:10px;">
    	<div class="row">
			<div class="col-xs-12">
				<div class="Grid_Area">
					<form name="form"  id="form" method="post" enctype="multipart/form-data">
					<input type="hidden" name="brd_type_id" id="brd_type_id" value="faq">
					<table class="Notice_write">
						<colgroup>
							<col width="150"/> 
							<col />
						</colgroup>
						<tr>
							<th>Q</th>
							<td class="notice_title"><input type="text" style="width:95%;" id="brd_nm" name="brd_nm"></td>
						</tr>	
							<th>A</th>					
							<td colspan="" class="notice_txt">
								<textarea  rows="20" id="brd_sbst" name="brd_sbst"></textarea>
								<script>
									CKEDITOR.replace("brd_sbst", {
										enterMode:'2',
										shiftEnterMode:'3'
									});
                				</script>
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

 	function goList() {
 		var post = {
				"viewId": "portal/faq/boardList",
				"menuId": ""
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

	 function doWriteProc() {

		if($("#brd_nm").val()==""){
			alert("제목을 입력해주세요.");
			$("#brd_nm").focus();
			return;
		}

		var brd_sbst=CKEDITOR.instances.brd_sbst.getData();
		if(brd_sbst==""){
			alert("내용을 입력해주세요.");
			$("#brd_sbst").focus();
			return;
		}
		
		var formData = new FormData();
		formData.append("brd_type_id",$("#brd_type_id").val());
		formData.append("brd_nm",$("#brd_nm").val());
		formData.append("brd_sbst",brd_sbst);
		formData.append("brd_st_date","");
		formData.append("brd_ed_date","");
		
            $.ajax({
               url: 'board/boardWriteProc',
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