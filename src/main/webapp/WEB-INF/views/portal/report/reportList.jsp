<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="kr.co.penta.dataeye.spring.web.session.SessionInfoSupport"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title><spring:message code="app.title" text="DateEye" /></title>
	<style type="text/css">
		.content-body {height : calc(100Vh - 220px);}
		.effect img {border-radius: 10px;}
		.effect2 img {border-radius: 5px;}
		
		.fav_star {float:left; color:#bfbfbf; cursor:pointer; margin-left:5px;}
		.fav_star:hover {color:#f5b933;}
		.fav_star .fa-star2{float:left; color:#f5b933; cursor:pointer; }
		.fa-star2:before {content: "\f005";}
		.navbar-form.navbar-right {position: absolute;right: 20px;}

	</style>
<link type="text/css" rel="stylesheet" href="<spring:url value="/assets/stylesheets/cardList.css"/>">
<script type="text/javascript">
    var listDiv = "";
    var card_width = 0;
    
	var cardLoad = false;
	function cutString(str , len) {
		if(str==null||str=="undefined") {
			return "";
		}
		if(str.length>len) {
			return str.substring(0,len) + "...";
		} else {
			return str;
		}
	} 

	function strVal(str) {

		if(str==null||str=="undefined"||str=="null") {
			return "";
		} else {
			return str;
		}
	}
	function goDetail(reportId) {
		//window.open('../report/reportPrompt?reportId=' + reportId,'report' + reportId,'');
		$('#reportDiv').show();
		$('.content-header, .content').hide();
		$('#reportFrame').attr('src','../report/reportExec?reportId=' + reportId);
	}

	function goCate(no) {
		
		if(no>1){
			$(".carList_tab_Area span").eq(0).removeClass("active");

			if($(".carList_tab_Area span").eq(no-1).attr("class")=="active") {
				$(".carList_tab_Area span").eq(no-1).removeClass("active");
			}else {
				$(".carList_tab_Area span").eq(no-1).addClass("active");
			}
			
		} else{//전체 클릭했을때
			$(".carList_tab_Area span").removeClass("active");
			$(".carList_tab_Area span").eq(0).addClass("active");
		}

	
		cardLoad = false;

		goPage(listDiv);
	}
	
	function goPage(div) {

		
		var check = false;
		var cateIds = "";
		var cateLen = $(".carList_tab_Area span").length;

		 for(var i = 1;i<cateLen + 1;i++) {

			 if($(".carList_tab_Area span").eq(i).attr("class")=="active") {

				 check = true;
				if(cateIds == "") {
					cateIds = cateIds + "'" + $(".carList_tab_Area span").eq(i).attr("cateId") + "'";
				} else {

					cateIds = cateIds + ",'" + $(".carList_tab_Area span").eq(i).attr("cateId") + "'";
				}
				
			}
		 }				

		if(!check){
			$(".carList_tab_Area span").eq(0).addClass("active");			
		}

		listDiv = div;
		if(div=="list") {
			$("#listType").show();
			$("#cardType").hide();
			
			gridLoad(cateIds);
		} else {
			$("#listType").hide();
			$("#cardType").show();

			if(!cardLoad) {
				var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.report.reportList","searchKey":"_all","searchValue":"","cateIds":cateIds}
				$.ajax({ 
				    url:'jqgrid/list', //request 보낼 서버의 경로
				    type:'post', // 메소드(get, post, put 등)
				    contentType: 'application/json; charset=utf-8',
				    dataType: 'json' ,  
				    data:JSON.stringify(param), //보낼 데이터
				    success: function(data) {
				    	cardLoad = true;
				        var cardHtml = "";
				        
				        $(".carList_count").html("총<em> " + data["rows"].length+"</em>건<span>이 검색되었습니다.</span>" );
				        $.each(data["rows"], function (index, item) {

					        var OBJ_NM = 	cutString(item["OBJ_NM"],13);
					        var OBJ_DESC = 	cutString(item["OBJ_DESC"],40);
					        var BOOKMARK_YN = 	item["BOOKMARK_YN"];
					        var AUTH_YN = 	item["AUTH_YN"];
							
				        	cardHtml = cardHtml +  "<div class=\"card_Area\" style=\"width:"+card_width+"px;\">";

				        	/**/
				        	//cardHtml = cardHtml +  "<div class=\"ribbon\" title=\"즐겨찾기\"><i class=\"fa fa-star\"></i></div>";
				        	if(AUTH_YN == 'YES'){
				        		if(BOOKMARK_YN == 'YES'){
					        		cardHtml = cardHtml +  "<div class=\"ribbon_active\" title=\"즐겨찾기\" onclick=\"cancelBookmark(this,'" + item["OBJ_TYPE_ID"] + "','" + item["OBJ_ID"] + "')\"><i class=\"fa fa-star\"></i></div>";
					        	} else{
					        		cardHtml = cardHtml +  "<div class=\"ribbon\" title=\"즐겨찾기\" onclick=\"goBookmark(this,'" + item["OBJ_TYPE_ID"] + "','" + item["OBJ_ID"] + "')\"><i class=\"fa fa-star\"></i></div>";
							    }
				        		cardHtml = cardHtml +  "<div class=\"cardList_title\" onclick=\"goDetail('" + item["OBJ_ID"] + "')\" style=\"cursor:pointer\" >[" + item["PATH_OBJ_NM"] + "] " + OBJ_NM +  " </div>";
				        	} else{
				        		cardHtml = cardHtml +  "<div class=\"ribbon\" title=\"즐겨찾기\" onclick=\"javascript:alert('해당보고서의 실행 권한이 없습니다.');\"><i class=\"fa fa-lock\"></i></div>";
				        		cardHtml = cardHtml +  "<div class=\"cardList_title\" onclick=\"javascript:alert('해당보고서의 실행 권한이 없습니다.');\" style=\"cursor:pointer\" >[" + item["PATH_OBJ_NM"] + "] " + OBJ_NM +  " </div>";
						    }
				        	

				        	if(item["DD108_OBJ_TYPE_ID"]=="020600L") {
				        		/*cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/cognos.png\"></div>";*/
				        		cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/tableau.png\"></div>";  
					        } else {
					        	cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/mstr02.png\"></div>";
						    }
				        	cardHtml = cardHtml +  "<div class=\"cardList_Contents_Area\">";
				        	cardHtml = cardHtml +  "	<div class=\"cardList_comment\">" + OBJ_DESC + "</div>";
				        	cardHtml = cardHtml +  "	<div class=\"cardList_impor\">중요도 : <span class=\"txt_box\">" + item["OBJ_ATR_VAL_101_NM"] + "</span></div>";
				        	cardHtml = cardHtml +  "</div>";
				        	cardHtml = cardHtml +  "<div class=\"cardList_line\"></div>";
				        	cardHtml = cardHtml +  "<div class=\"cardList_per_Area\">";
				        	cardHtml = cardHtml +  "	<div class=\"per\">담당부서</div>";
				        	cardHtml = cardHtml +  "	<div class=\"per_name\">" + strVal(item["OBJ_ATR_VAL_107_NM"]) + " </span></div>";
				        	cardHtml = cardHtml +  "</div>";
				        	cardHtml = cardHtml +  "</div>";
				        	
				        })

				        
				        
				        $("#cardList").html(cardHtml);		
				        cardResize();
				    },
				    error: function(err) {
				        alert("error");
				    }
				});
			}
		}
	}

	$(this).off("autoResize").on( "autoResize", function( e ) {		
		setTimeout(function () {
			cardResize();
		}, 300);
	}).trigger("autoResize");

	function cardResize(){
		var maxW = 340,
		gap = 10 + 2, //10:margin, 2: border width
		pad = 25, 
		cnt = 3,
		cdw = 0;
		
		var caow = $(".content-body").width() - 20; // 20: scroll width
		
		do{
			cdw = Math.floor((caow-pad)/cnt) - gap;
			cnt ++;
		} while(cdw > maxW);
		//transition: transform 0.3s ease-in-out, width 0.3s ease-in-out;
		if(card_width != cdw){
			$(".card_Area").css("width",cdw+"px");
		}
		card_width = cdw;
		
		$(".ui-jqgrid-view .table-responsive").css(caow + "px");
    	$("#jqGrid").setGridWidth(caow,true);
    	$(".ui-jqgrid-bdiv").css("overflow-x","hidden");
		$(".table-responsive").css("overflow-x","hidden");
	}

	function goBookmark(object,objTypeId,objId){
		var cf = confirm("해당 보고서를 즐겨찾기 하시겠습니까?");
		var formData = new FormData();
 		formData.append("objTypeId",objTypeId);
 		formData.append("objId",objId); 		
 		
		if(cf) {	 		
			$.ajax({
				url: '../portal/bookmark/proc',              
				processData: false,
				contentType: false,
				data: formData,
				type: 'POST',
				success: function(result){
					alert("등록 되었습니다.");
					if(!$(object).hasClass('fa-star')){
						goLink('portal/report/reportList');
					}else{
						gridLoad();
					}
				}
			});
		}
	}

	function cancelBookmark(object,objTypeId,objId){
		var cf = confirm("해당 보고서를 즐겨찾기 취소 하시겠습니까?");
		var formData = new FormData();
 		formData.append("objTypeId",objTypeId);
 		formData.append("objId",objId);
 		formData.append("delYn","Y");		
 		
		if(cf) {	 		
			$.ajax({
				url: '../portal/bookmark/update',              
				processData: false,
				contentType: false,
				data: formData,
				type: 'POST',
				success: function(result){
					alert("취소 되었습니다.");
					if(!$(object).hasClass('fa-star2')){
						goLink('portal/report/reportList');
					}else{
						gridLoad();
					}
					
				}
			});
		}
	}

	function hideReportDiv(){
		$('#reportDiv').hide();
		$('.content-header, .content').show();
		$('#reportFrame').attr('src','');
	}
</script>
</head>

<body>
<div id="reportDiv" style="display:none;">
<a href="javascript:hideReportDiv();" style="position: absolute;right: 10px;padding: 20px 0;">닫기</a>
<iframe src="" width="100%" height="900px" name="reportFrame" id="reportFrame" frameborder="0"></iframe>
</div>
<section class="content-header">
      <h1><i class="glyphicon glyphicon-list-alt"></i> &nbsp;정형보고서</h1>
      <ol class="breadcrumb"><li><a href="#"><i class="glyphicon glyphicon-list-alt"></i>&nbsp;정형보고서</a></li></ol>
    </section>
    <!-- Main content -->

   

	<section class="content">
		<div class="content-body" >
	    	<div class="row">
	    		<div class="col-xs-12">
					<div class="box">
						
			            <div class="box-body dashboard">			             						              
								  	<div id="DataSet_Main" style="margin-top:0px;">
					                <ul class="DataSet_panel">
					                    <!--1페이지-->
					                    <li id="stab1" style="padding-top:0px;">
					                    	<div class="carList_tab_Area" id="cateDiv">					                    		
					                    	</div>
					                    	<div class="navbar-form navbar-right">
										           	<a href="javascript:goPage('card')"><div class="cardList_btn"><i detag="icon-class" class="glyphicon glyphicon-th-large"></i></div></a> 
										             <a href="javascript:goPage('list')"><div class="cardList_btn"><i detag="icon-class" class="glyphicon glyphicon-list"></i></div></a>
											  </div>
					                    	<div class="carList_count">총 0건<span>이 검색되었습니다.</span></div>
					                    	<div class="cardArea">
												
								  				<div id="cardType">
								  					<div id="cardList"></div>	
							                    </div>
							                    <div id="listType"  style="display:none">
					                    		 	<table id="jqGrid" de-role="grid"></table>
								  					<div id="jqGridPager" de-role="grid-pager"></div>
								  				</div>
					                    	</div>
					                    </li>
					                </ul>
					                
					            </div>
					            <c:if test="${sessionScope[SessionInfoSupport.SESSION_USERINFO_NAME]['admin']}">
					            <div style="right: 0;text-align: right;padding-top:10px;">
									<button type="button" id="btnInsert" class="btn btn-default">등록</button>
									<button type="button" id="btnUpdate" class="btn btn-default">수정</button>
								  </div>
								  </c:if>
							</div>							
		            	</div>
		            	<!-- /.box-body -->
		          </div>
		          <!-- /.box -->
          		</div>		
			</div>
	</section>
	<!-- /.content -->
	<input type="hidden" id="reqParam">
	<script type="text/javascript">
	$(document).ready(function() {
		var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.report.cateList"}
		$.ajax({ 
		    url:'jqgrid/list', //request 보낼 서버의 경로
		    type:'post', // 메소드(get, post, put 등)
		    contentType: 'application/json; charset=utf-8',
		    dataType: 'json' ,  
		    data:JSON.stringify(param), //보낼 데이터
		    success: function(data) {			    
					var cateHtml = "";
					cateHtml =  cateHtml + "<span class=\"active\"  onclick=\"goCate(1)\" cateId=\"\">전체</span>";
					$.each(data["rows"], function (index, item) {	
						cateHtml =  cateHtml + "<span onclick=\"goCate(" + (index+2) + ")\" cateId=\"" + item["OBJ_ID"] + "\" cateDesc=\"" + item["OBJ_DESC"] + "\">" + item["OBJ_NM"] + " </span>";			
				    });
					$("#cateDiv").html(cateHtml);

		    },
		    error: function(err) {
		        alert("error");
		    }
		});
		
		goPage('');
		
	});
	
	$("input#reqParam").data("viewId", '${viewId}');
	$("input#reqParam").data("menuId", '${menuId}'); 
	</script>	
	<script src="<spring:url value="/app/portal/pages/report/reportList.js"/>"></script>
</body>
</html>
