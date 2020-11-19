<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title><spring:message code="app.title" text="DateEye" /></title>
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
		
		function goPage(div,cateIds) {

			if(div=="list") {
				$("#listType").css("visibility","visible");
				$("#listType").show();
				$("#cardType").hide();
				gridLoad();
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

					        	cardHtml = cardHtml +  "<div class=\"card_Area\">";
					        	cardHtml = cardHtml +  "<div class=\"ribbon\" title=\"즐겨찾기\"><i class=\"fa fa-star\"></i></div>";
					        	cardHtml = cardHtml +  "<div class=\"cardList_title\" onclick=\"goDetail('" + item["OBJ_ID"] + "')\" style=\"cursor:pointer\" >[" + item["PATH_OBJ_NM"] + "] " + OBJ_NM +  " </div>";

					        	if(item["DD108_OBJ_TYPE_ID"]=="020600L") {
					        		cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/cognos.png\"></div>"; 
						        } else {
						        	cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/mstr.png\"></div>";
							    }
					        	cardHtml = cardHtml +  "<div class=\"cardList_Contents_Area\">";
					        	cardHtml = cardHtml +  "	<div class=\"cardList_comment\">" + OBJ_DESC + "</div>";
					        	cardHtml = cardHtml +  "	<div class=\"cardList_impor\">중요도 : <span class=\"txt_box\">" + item["OBJ_ATR_VAL_101_NM"] + "</span></div>";
					        	cardHtml = cardHtml +  "</div>";
					        	cardHtml = cardHtml +  "<div class=\"cardList_line\"></div>";
					        	cardHtml = cardHtml +  "<div class=\"cardList_per_Area\">";
					        	cardHtml = cardHtml +  "	<div class=\"per\">담당자</div>";
					        	//cardHtml = cardHtml +  "	<div class=\"per_name\">홍길동 <span class=\"phone\">(부서 / 전화번호)</span></div>";
					        	cardHtml = cardHtml +  "	<div class=\"per_name\">" + strVal(item["OBJ_ATR_VAL_106_NM"]) + " </span></div>";
					        	cardHtml = cardHtml +  "</div>";
					        	cardHtml = cardHtml +  "</div>";
					        	
					        })

					        $("#cardList").html(cardHtml);		
					    },
					    error: function(err) {
					        alert("error");
					    }
					});
				}
			}
			$(this).off("autoResize").on( "autoResize", function( e ) {
				var caw = 270;
				$(".card_Area").width(caw - 40);
				setTimeout(function () {
					var maxW = 340,
						gap = 10 + 2, // 2: border width
						pad = 40, 
						cnt = 3;
						
					var caow = $(".cardArea").width() - 20; // 20: scroll width

					do{
						caw = Math.floor(caow/cnt) - gap;
						cnt ++;
					} while(caw > maxW);
					//transition: transform 0.3s ease-in-out, width 0.3s ease-in-out;
					$(".card_Area").width(caw - 40);
				
					var heightMargin = $(".content-body").offset().top - $(".content-header").offset().top,
		    		bottomMargin = 20, // top margin 10 + bottom margin 10
		    		gridHeadHeight = 60; // grid header height 30 + grid footer height 30
		    	
		    		
			    	$("#jqGrid").setGridWidth($(".content-body .gridArea").width(),opts["autowidth"]);
			    	$("#jqGrid").setGridHeight($(".content").height() - (heightMargin + bottomMargin + gridHeadHeight));
			      	if(opts["autowidth"]==true){
				    	$(".ui-jqgrid-bdiv").css("overflow-x","hidden")
				    }
				}, 300);
			}).trigger("autoResize");
		}
	</script>
	<style type="text/css">
		.row {margin:0;}
		.effect img {
		    border-radius: 10px;
		}

		.cardArea {overflow-y:auto; width:auto; 
			position:absolute; top:60px; bottom:0;
			border-top: 1px solid #ddd;
		    padding: 10px 0 0 20px;
		}
		#caedList {overflow: hidden;}
 	</style>
</head>

<body>


    <!-- Main content -->
	<section class="content-header">
      <h1>정형분석</h1>
       <div class="navbar-form navbar-right">
              <a href="javascript:goPage('card','')"><div class="cardList_btn"><i detag="icon-class" class="glyphicon glyphicon-th-large"></i></div></a>
            	<a href="javascript:goPage('list','')"><div class="cardList_btn"><i detag="icon-class" class="glyphicon glyphicon-list"></i></div></a> 
	  </div>
   	</section>
	<section class="content">
		<div class="content-body" >
	    	<div class="row">
					<div class="box">
			            <div class="box-body">
			             
							<div id="cardType" class="h100p">
		             			<div id="DataSet_Main" style="margin-top:0px;">
					                <ul class="DataSet_panel">
					                    <!--1페이지-->
					                    <li id="stab1" style="padding-top:0px;">
					                    	<div class="carList_tab_Area" id="cateDiv">
					                    		
					                    	</div>
					                    	<div class="carList_count">총 0건<span>이 검색되었습니다.</span></div>
					                    	<div class="cardArea">
					                    		<div id="cardList"></div>							                    	
					                    	</div>
					                    </li>
					                </ul>
					            </div>
							</div> 
						  	<div id="listType" style="visibility: hidden;">
						  		<div class="gridArea" style="margin-top:10px;">
						              <table id="jqGrid" de-role="grid"></table>
									  <div id="jqGridPager" de-role="grid-pager"></div>
						  		</div>
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
					cateHtml =  cateHtml + "<span class=\"info\"></span>";
					$("#cateDiv").html(cateHtml);

		    },
		    error: function(err) {
		        alert("error");
		    }
		});
		
		goPage('card','');
	});
	
	$("input#reqParam").data("viewId", '${viewId}');
	$("input#reqParam").data("menuId", '${menuId}'); 
	</script>	
	<script src="<spring:url value="/app/portal/pages/report/reportList.js"/>"></script>
</body>
</html>