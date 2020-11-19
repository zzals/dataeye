<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title><spring:message code="app.title" text="DateEye" /></title>
	<style type="text/css">
		.content-body {
			height : calc(100Vh - 220px);
		}
		.effect img {
		    border-radius: 10px;
		}
		.effect2 img {
		    border-radius: 5px;
		}
		
		.fav_star {float:left; color:#bfbfbf; cursor:pointer; margin-left:5px;}
		.fav_star:hover {color:#f5b933;}

 	</style>
	<link type="text/css" rel="stylesheet" href="<spring:url value="/assets/stylesheets/cardList.css"/>">
	<script type="text/javascript">
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
			window.open('/dataeye_ebay/report/reportPrompt?reportId=' + reportId,'_new','')
		}

		function goCate(no) {

		//	$(".carList_tab_Area span").removeClass("active");
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
					$(".carList_tab_Area span").eq(cateLen-1).html($(".carList_tab_Area span").eq(i).attr("cateDesc"));
				}				
			}
			if(!check){
				$(".carList_tab_Area span").eq(0).addClass("active");
				$(".carList_tab_Area span").eq(cateLen-1).html("");
			}
			
			cardLoad = false;
	
			goPage("card",cateIds);
		}
		

	</script>
	
</head>

<body>
    <!-- Main content -->
	<section class="content">
	
		<div class="content-body" >
	    	<div class="row">
				<div class="col-xs-12">
					<div class="box">
			            <div class="box-header" style="margin-bottom:5px;">
			              <h3 class="box-title">분석과제</h3>
			              
			            </div>
			            <!-- /.box-header -->
			            <div class="box-body" style="margin-top:0px;">
			             
			           
			            <div id="listType">
			              <table id="jqGrid" de-role="grid"></table>
						  <div id="jqGridPager" de-role="grid-pager"></div>
						</div>
						 
			            </div>
			            <!-- /.box-body -->
			          </div>
			          <!-- /.box -->
			          				
				</div> 
			</div>
		</div>
	</section>
	<!-- /.content -->
	<input type="hidden" id="reqParam">
	<script type="text/javascript">
	$(document).ready(function() {
				
	});
	
	$("input#reqParam").data("viewId", '${viewId}');
	$("input#reqParam").data("menuId", '${menuId}'); 
	</script>	
	<script src="<spring:url value="/app/portal/pages/task/taskList.js"/>"></script>
</body>
</html>