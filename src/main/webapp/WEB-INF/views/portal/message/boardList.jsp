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
<section class="content">
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="collapsed navbar-toggle" data-toggle="collapse" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a href="#" class="navbar-brand"></a>
			</div>
			<div class="collapse navbar-collapse">
				
				<div class="navbar-form navbar-right">
					<div class="form-group">
						<select id="searchKey" class="form-control select2" data-placeholder="선택하세요." style="width: 150px; height:30px; padding:4px 12px; font-size:12px;">
		                  	<option value="_all">[전체]</option>
		                  	<option value="TITLE">제목</option>
		                  	<option value="CONT">내용</option>
		                </select>
						<input id="searchValue" class="form-control" placeholder="Search">
					</div>
					<button type="submit" id="btnSearch" class="btn btn-default" onClick="doSearch()">검색</button>
				</div>
			</div>
		</div>
	</nav>
	<div class="content-body" style="margin-top:10px;">
    	<div class="row">
			<div class="col-xs-12">
				<div class="Grid_Area">
					<table class="notice_list">
						<colgroup>
							<col width="50"/>    
							<col />
							<col width="80"/>
							<col width="100"/>
							<col width="150"/>
							<col width="50"/>
							<col width="150"/>
							<col width="100"/>
							<col width="50"/>
						</colgroup>
						<thead>
							<tr>
								<th>NO</th>
								<th>제목</th>
								<th>구분</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>상태</th>
								<th>발송일</th>
								<th>발송 대상</th>
								<th>확인</th>
                   			</tr>
						</thead>
						<tbody id="boardDiv">		
							 
						</tbody>
					</table>
				</div>
				<div style="text-align:center;">
					<p class="tsearch-paging" style="margin:0px;">
						<ul class="pagination bootpag" style="margin-bottom:0px;" id="pagindLayer">
							
						</ul>
					</p>
				</div>
				<div style="text-align:right">
					<c:if test="${AUTH.getCAuth() == 'Y'}" >
					<button type="submit" id="" class="board_btn" onClick="goWrite()">글쓰기</button>
					</c:if>
					<!-- 
						<button type="submit" id="" class="board_etc_btn">삭제</button>
					 -->
				</div>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript">
	var menu_id = localStorage.getItem("menu_id");
	var pageDiv = 15;
	var pageListDiv = 10;
	var thisPage = 1;
	var listDiv;

	$(document).ready(function() {
		pageLoad(1);
	});

	function displayPaging(page) {
		
		thisPage = page;
		var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc",
					"queryId":"portal.notice.getNoticeTotalCnt",
					"menuId":localStorage.getItem("menu_id"),
					"ntc_type_id":"notice",
					"searchKey":$("#searchKey").val(),
					"searchValue":$("#searchValue").val()
		}
		$.ajax({ 
		    url:'jqgrid/list', //request 보낼 서버의 경로
		    type:'post', // 메소드(get, post, put 등)
		    contentType: 'application/json; charset=utf-8',
		    dataType: 'json' ,  
		    data:JSON.stringify(param), //보낼 데이터
		    success: function(data) {				
		    	 total_cnt = data["rows"][0]["TOTAL_CNT"];

		    	listDiv = Math.ceil(total_cnt/pageDiv);
		    	var startListPage =  (Math.ceil(thisPage/pageListDiv)-1)*pageListDiv;
		    	var endListPage =   Math.ceil(thisPage/pageListDiv)*pageListDiv;	
				
				var pagingHtml = "";
				pagingHtml =  pagingHtml + "<li data-lp=\"1\" class=\"first disabled\"><a href=\"javascript:pageLoad(1);\"><span aria-hidden=\"true\">←</span></a></li> ";
				pagingHtml =  pagingHtml + "<li data-lp=\"1\" class=\"prev disabled\"><a href=\"javascript:pageLoad(" + (( (Math.ceil(startListPage/pageListDiv)-1)*pageListDiv )+1)+ ");\">«</a></li> ";
				var listCnt = 0;
		    	for(var i=startListPage;i<listDiv;i++) {
		    		listCnt ++;		    		
		    		if(i==endListPage)break;
			    	if(thisPage==(i+1)) {
			    		pagingHtml =  pagingHtml + "<li data-lp=\"1\" class=\"active\"><a href=\"javascript:pageLoad(" + (i+1) + ");\">" + (i+1) + "</a></li> ";				
				    } else {
				    	pagingHtml =  pagingHtml + "<li data-lp=\"1\" ><a href=\"javascript:pageLoad(" + (i+1) + ");\">" + (i+1) + "</a></li> ";
					}		    		
			    }
		    	pagingHtml =  pagingHtml + "<li data-lp=\"1\" class=\"next disabled\"><a href=\"javascript:pageLoad(" + (endListPage+1) + ");\">»</a></li> ";
				pagingHtml =  pagingHtml + "<li data-lp=\"1\" class=\"last disabled\"><a href=\"javascript:pageLoad(" + (listDiv) + ");\"><span aria-hidden=\"true\">→</span></a></li> ";
		    	
		    	$("#pagindLayer").html(pagingHtml);
		    },
		    error: function(err) {
		        alert("error");
		    }
		});
		
	}
	
	function pageLoad(page){	
		if(page<0 || (listDiv!=0&&page>listDiv))return;	
		
		displayPaging(page);
		
		var startNum = ((page-1) * pageDiv) + 1;
		var endNum = (page) * pageDiv;	

		var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc",
				"queryId":"portal.board.getPortalMessageList",
				"menuId":localStorage.getItem("menu_id"),
				"ntc_type_id":"notice",
				"searchKey":$("#searchKey").val(),
				"searchValue":$("#searchValue").val(),
				"startNum":startNum,"endNum":endNum
		}
		$.ajax({ 
		    url:'jqgrid/list', //request 보낼 서버의 경로
		    type:'post', // 메소드(get, post, put 등)
		    contentType: 'application/json; charset=utf-8',
		    dataType: 'json' ,  
		    data:JSON.stringify(param), //보낼 데이터
		    success: function(data) {
		    						
		    	var boardHtml = "";

		    	$.each(data["rows"], function (index, item) {
		    		boardHtml =  boardHtml + "<tr> ";
		    		boardHtml =  boardHtml + "	<td>" + ((total_cnt - (startNum-1))- index) +  "</td>";
		    		boardHtml =  boardHtml + "	<td class=\"notice_title\"><a href=\"javascript:goDetail('" + item["MSG_ID"]  + "')\">" + item["TITLE"]  + "</a></td>";
		    		boardHtml =  boardHtml + "	<td>" + item["SEND_TYPE"]  + "</td>";
		    		boardHtml =  boardHtml + "	<td>" + item["WRIT_ID"]  + "</td>";
		    		boardHtml =  boardHtml + "	<td>" + item["WRIT_DT"]  + "</td>";
		    		boardHtml =  boardHtml + "	<td>" + item["SEND_STTS"]  + "</td>";
		    		boardHtml =  boardHtml + "	<td>" + item["SEND_DT"]  + "</td>";
		    		boardHtml =  boardHtml + "	<td>" + item["SEND_TARGET"]  + "</td>";
		    		boardHtml =  boardHtml + "	<td>" + item["INFORM_CNFRM_IS"]  + "</td>";
		    		boardHtml =  boardHtml + "</tr>";
		    	});

		    	$("#boardDiv").html(boardHtml);
		    },
		    error: function(err) {
		        alert("error");
		    }
		});

	}
	

	function doSearch() {
		pageLoad(1); 
	}

	function goDetail(msg_id) {
 		var post = {
				"viewId": "portal/message/boardDetail",
				"menuId": menu_id,
				"data": JSON.stringify({"msg_id": msg_id})
			};
			$(".content-wrapper").load(DE.contextPath+"/portal/view", post, function(response, status, xhr) {
				$(document).off("autoResize");
				if (status === "error") {				
					$(".content-wrapper").html(response);
				} else {
			  //		DE.content.setHeader($(e.currentTarget));
			 // 		$(window).trigger("resize");
				}
			});
 	 }

 	function goWrite() {
 		var post = {
				"viewId": "portal/message/boardWriteForm",
				"menuId": menu_id
			};

			$(".content-wrapper").load(DE.contextPath+"/portal/view", post, function(response, status, xhr) {
				$(document).off("autoResize");
				if (status === "error") {				
					$(".content-wrapper").html(response);
				} else {
			//  		DE.content.setHeader($(e.currentTarget));
			//  		$(window).trigger("resize");
				}
			}); 
 	 }
		 
</script>	
</body>
</html>