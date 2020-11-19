<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
		
		.navbar-form.navbar-right {
		    position: absolute;
		    top: 10px;
    		right: 20px;
		}
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
		function goDetail(datasetId) {
			window.open('/dataeye_ebay/dataset/datasetDetail?datasetId=' + datasetId,'dataset' + datasetId,'')
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
					var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.dataset.datasetList","searchKey":"_all","searchValue":"","cateIds":cateIds}
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

						        cardHtml = cardHtml +  "<div class=\"card_Area\" style=\"width:"+card_width+"px;\">";
					        	cardHtml = cardHtml +  "<div class=\"ribbon\" title=\"즐겨찾기\"><i class=\"fa fa-star\"></i></div>";
					        	cardHtml = cardHtml +  "<div class=\"cardList_title\" onclick=\"goDetail('" + item["OBJ_ID"] + "')\" style=\"cursor:pointer\" >[" + item["PATH_OBJ_NM"] + "] " + OBJ_NM +  " </div>";
						        cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/dataset.png\" width=60 height=70></div>";
					        	cardHtml = cardHtml +  "<div class=\"cardList_Contents_Area\">";
					        	cardHtml = cardHtml +  "	<div class=\"cardList_comment\">" + OBJ_DESC + "</div>";
					        	cardHtml = cardHtml +  "	<div class=\"cardList_impor\">" + item["ADM_OBJ_ID"] + "</div>";
					        	cardHtml = cardHtml +  "</div>";
					        	cardHtml = cardHtml +  "<div class=\"cardList_line\"></div>";
					        	cardHtml = cardHtml +  "<div class=\"cardList_per_Area\">";
					        	cardHtml = cardHtml +  "	<div class=\"per\">등록자</div>";
					        	//cardHtml = cardHtml +  "	<div class=\"per_name\">홍길동 <span class=\"phone\">(부서 / 전화번호)</span></div>";
					        	cardHtml = cardHtml +  "	<div class=\"per_name\">" + strVal(item["CRETR_ID"]) + " </span></div>";
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
		}



		$(this).off("autoResize").on( "autoResize", function( e ) {		
			setTimeout(function () {
				var maxW = 340,
					gap = 10 + 2, // 10:margin, 2: border width
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
					
			}, 300);
		}).trigger("autoResize");
	</script>
	
</head>

<body>
  
<section class="content-header">
      <h1><i class="glyphicon glyphicon-th-list"></i>&nbsp;데이터셋</h1>
      <ol class="breadcrumb"><li><a href="#"><i class="glyphicon glyphicon-list-alt"></i>&nbsp;데이터셋</a></li></ol>
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

		var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.dataset.cateList"}
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
	<script src="<spring:url value="/app/portal/pages/dataset/datasetList.js"/>"></script>
</body>
</html>