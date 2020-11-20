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
		.effect {
			padding-left:15px;
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

	function goDetail(taskId) {
		window.open('/dataeye/task/taskDetail?taskId=' + taskId,'_new','')
	}
	

	function taskInfo(OBJ_TYPE_ID,OBJ_ID) {
  		DE.ui.open.popup(
				"/dataeye/portal/view",
				[OBJ_TYPE_ID, OBJ_ID],
				{
					viewname:'common/metacore/objectInfoTab',
					objTypeId:OBJ_TYPE_ID,
					objId:OBJ_ID,
					mode:'R'
				},
				null
			);
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

		goPage();
	}
	
	
	function goPage() {


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

		
		var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.task.taskCardList","cateIds":cateIds}
		$.ajax({ 
		    url:'jqgrid/list', //request 보낼 서버의 경로
		    type:'post', // 메소드(get, post, put 등)
		    contentType: 'application/json; charset=utf-8',
		    dataType: 'json' ,  
		    data:JSON.stringify(param), //보낼 데이터
		    success: function(data) {
		    	 var cardHtml = "";
		    	    
			        $(".carList_count").html("총<em> " + data["rows"].length+"</em>건<span>이 검색되었습니다.</span>" );
			        
		    	 $.each(data["rows"], function (index, item) {

		    		 var sdate = item["SDATE"];
		    		 if(sdate !=""||sdate.length==8){
		    			 sdate = sdate.substring(0,4) + "-" + sdate.substring(4,6) + "-" + sdate.substring(6);   
			    	  }
			    	  
		    		 var edate = item["EDATE"];
		    		 if(edate !=""||edate.length==8){
		    			 edate = edate.substring(0,4) + "-" + edate.substring(4,6) + "-" + edate.substring(6);   
			    	  }

		    		 var tools = item["TOOLS"];
		    		 
		    		 
			    	 
			    	 cardHtml = cardHtml +  "<div class=\"card_Area\">";
			    	 
			    	 cardHtml = cardHtml +  "		<div class=\"cardList_title\" onclick=\"goDetail('" + item["OBJ_ID"] + "')\" style=\"cursor:pointer\">" + item["OBJ_NM"] + " </div>";
			    	 cardHtml = cardHtml +  "		<div class=\"cardList_Contents_Area\" style=\"padding-left:0px;height:100px\">";	
			    	 cardHtml = cardHtml +  "			<div class=\"cardList_comment\" style=\"height:35px\">" + item["OBJ_DESC"] + " </div>	";
			    	 cardHtml = cardHtml +  "			<div class=\"cardList_impor\" style=\"margin-bottom:15px\">기간 : " + sdate + "  ~ " + edate + " </div>";
			    	 cardHtml = cardHtml +  "			<div class=\"cardList_per_Area\">	";
			    	 cardHtml = cardHtml +  "				<div class=\"per\">PM</div>";	
			    	 cardHtml = cardHtml +  "				<div class=\"per_name\" style=\"padding-right:20px\">" + item["PM_USER"] + "</div>";
			    	 				
			    	 cardHtml = cardHtml +  "			</div>";
			    	 cardHtml = cardHtml +  "		</div>";
			    	 cardHtml = cardHtml +  "		<div class=\"cardList_line\"></div>";
			    	 cardHtml = cardHtml +  "		<div class=\"cardList_comment \" style=\"height:80px\">";
			    	 if(tools!=null&&tools.toUpperCase().indexOf("MSTR")>=0) {
			    		 cardHtml = cardHtml +  "			<span class=\"effect\"><img src=\"../assets/images/mstr.png\" width=\"50\" height=\"60\"></span>";
				    }

			    	 if(tools!=null&&tools.toUpperCase().indexOf("COGNOS")>=0) {
			    		 cardHtml = cardHtml +  "			<span class=\"effect\"><img src=\"../assets/images/cognos.png\" width=\"50\" height=\"60\"></span>";
				    }
			    	

			    	 if(tools!=null&&tools.toUpperCase().indexOf("ORACLE")>=0) {
			    		 cardHtml = cardHtml +  "			<span class=\"effect\"><img src=\"../assets/images/oracle.jpg\" width=\"50\" height=\"60\"></span>";
				    }	    

			    	 if(tools!=null&&tools.toUpperCase().indexOf("JUPYTER")>=0) {
			    		 cardHtml = cardHtml +  "			<span class=\"effect\"><img src=\"../assets/images/jupyter.png\" width=\"50\" height=\"60\"></span>";
				    }

			    	 if(tools!=null&&tools.toUpperCase().indexOf("HIVE")>=0) {
			    		 cardHtml = cardHtml +  "			<span class=\"effect\"><img src=\"../assets/images/hive.png\" width=\"50\" height=\"60\"></span>";
				    }	

			    	 
			    	 cardHtml = cardHtml +  "		</div>	";
			    	 cardHtml = cardHtml +  "	</div>";
		    	  });

				 $("#cardList").html(cardHtml);		
				
		    },
		    error: function(err) {
		        alert("error");
		    }
		});

	}
	goPage();

	</script>
	
</head>

<body>
  <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1><small></small></h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class=""></i></a></li>
        <li class="active"></li>
      </ol>
    </section>
  
	<section class="content">
		<div class="content-body" >
	    	<div class="row">
	    		<div class="col-xs-12">
					<div class="box">
			            <div class="box-body">			             						              
								  	<div id="DataSet_Main" style="margin-top:0px;">
					                <ul class="DataSet_panel">
					                    <!--1페이지-->
					                    <li id="stab1" style="padding-top:0px;">
					                    	<div class="carList_tab_Area" id="cateDiv">			
					                    	</div>
					                    		<div class="carList_count">총 0건<span>이 검색되었습니다.</span></div>                    		                    	
									            <div class="cardArea" style="overflow-y:auto; width:100%; height:709px;">
													<div id="cardList" style="height:270px;"></div>
									            <!-- /.box-body -->
									          </div>
									          <!-- /.box -->
			          				
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
		var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.task.cateList"}
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
						cateHtml =  cateHtml + "<span onclick=\"goCate(" + (index+2) + ")\" cateId=\"" + item["CODE"] + "\" cateDesc=\"" + item["DISP_NAME"] + "\">" + item["DISP_NAME"] + " </span>";			
				    });
					$("#cateDiv").html(cateHtml);

		    },
		    error: function(err) {
		        alert("error");
		    }
		});
		
		displayCard();
	});
	
	$("input#reqParam").data("viewId", '${viewId}');
	$("input#reqParam").data("menuId", '${menuId}'); 
	</script>	
</body>
</html>