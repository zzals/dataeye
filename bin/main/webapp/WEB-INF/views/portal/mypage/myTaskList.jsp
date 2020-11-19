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
		.subcard {
			overflow-y:auto; 
			width:100%; 
			height:165px;
			margin-top:20px;
			margin-left:20px;
			
			
		}
		.subtask { 
			width:100%; 
			height:30px;
			border: 0px;
		
			font-size:13px;
			font-weight:bold;
			padding-top:5px;
			padding-left:5px;		
			background:#48637b;						
		}
		
 	</style>
	<link type="text/css" rel="stylesheet" href="<spring:url value="/assets/stylesheets/cardList.css"/>">
	<script type="text/javascript">
	function goMstr() {
		window.open('http://192.168.100.73:8080/MicroStrategy/servlet/mstrWeb?Server=WIN-4FNG27AEFGQ&Project=ebay&Port=0&evt=2001&src=mstrWeb.shared.fbb.fb.2001&folderID=63C13DC9472AD4CB1478FAA950482D3A','','');
	}

	function goCognos() {
		window.open('http://210.108.181.116/ibmcognos','','');		
	}

	function goJupyter() {
		window.open(' http://192.168.100.75:8888/tree?token=6088711131b2b4f5023f23d41dc058b67a39bda7592e91c9','','');
	}

	function goHive() {
		alert("hive 접속");
	}

	function goOracle() {
		alert("Oracle 접속");
	}
	var toggle1 = true;
	var toggle2 = true;
	
  	function procInfo(div) {

  	  	
  	  	if(toggle) {
  	  		$("#infoDiv" + div).hide();
  	  		toggle = false;

	  	  	$("#infoIcon" + div).removeClass("fa fa-chevron-down")
	  	  	$("#infoIcon" + div).addClass("fa fa-chevron-up")
	  
  	  
  	  	 } else{
  	  		$("#infoDiv" + div).show();
  	  		toggle = true;
	  	  	$("#infoIcon" + div).removeClass("fa fa-chevron-up")
	  	  	$("#infoIcon" + div).addClass("fa fa-chevron-down")
	  		
  	  	 }
		
  	}

  	function openHelp(id) {
		 DE.ui.open.popup(
					"/dataeye_ebay/portal/view",
					["", ""],
					{
						viewname:'common/metacore/objectInfoHelpTab',
						objTypeId:"",
						objId:"",
						viewId:id,
						mode:'R'
					},
					null
				);
	 	 
	 }
	$(document).ready(function() {

		
		function viewPage() {
			
					
					    	cardLoad = true;
					        var cardHtml = "";					 
					     

								
					        	cardHtml = cardHtml +  "<div class=\"carList_tab_Area\" id=\"cateDiv\" >";
					        	/*cardHtml = cardHtml +  "<div class=\"subtask\">";
					        	cardHtml = cardHtml +  " <font color=\"#fff\"> <i class=\"fa fa-line-chart\" aria-hidden=\"true\"></i>&nbsp;분석 서버</font>";						        	
					        	cardHtml = cardHtml +  "</div>";*/								                    	
					        	cardHtml = cardHtml +  "	<div id=\"cardList\"><br>"; 


					        	var param2 = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.task.serviceList","serverDiv":"01"};
								$.ajax({ 
								    url:'jqgrid/list', //request 보낼 서버의 경로
								    type:'post', // 메소드(get, post, put 등)
								    contentType: 'application/json; charset=utf-8',
								    dataType: 'json' ,  
								    data:JSON.stringify(param2), //보낼 데이터
								    success: function(data2) {
								    	 $.each(data2["rows"], function (index2, item2) {									    	 
										    	cardHtml = cardHtml +  "			<div class=\"card_Area\">";
										    	
										    	var guideId = item2["GUIDE_ID"];

												var guideHtml = "";
												if(guideId!=""&&guideId!=null) {
													guideHtml = "<span style=\"float:right;font-size:11px;font-weight:normal;padding: 0 0 0 0;border:0px;\" onclick=\"openHelp('" +  guideId + "')\"><i class=\"fa fa-info-circle\" aria-hidden=\"true\"></i> 가이드</span>";
												}	
										    											    	
									        	if(item2["OBJ_NM"].toUpperCase().indexOf("MSTR")>=0) {
									        		cardHtml = cardHtml +  "				<div class=\"cardList_title\"><a href=\"javascript:goMstr()\">" + item2["OBJ_NM"] +  "</a> " + guideHtml + "</div>";
									        		cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/mstr.png\"></div>"; 
									        	} else if(item2["OBJ_NM"].toUpperCase().indexOf("TABLEAU")>=0) {
										        	cardHtml = cardHtml +  "				<div class=\"cardList_title\"><a href=\"javascript:goCognos()\">" + item2["OBJ_NM"] +  "</a> " + guideHtml + "</div>";
										        	cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/tableau.png\"></div>";
										        } else if(item2["OBJ_NM"].toUpperCase().indexOf("COGNOS")>=0) {
										        	cardHtml = cardHtml +  "				<div class=\"cardList_title\"><a href=\"javascript:goCognos()\">" + item2["OBJ_NM"] +  "</a> " + guideHtml + "</div>";
										        	cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/cognos.png\"></div>";
									        	} else if(item2["OBJ_NM"].toUpperCase().indexOf("ORACLE")>=0) {
										        	cardHtml = cardHtml +  "				<div class=\"cardList_title\"><a href=\"javascript:goOracle()\">" + item2["OBJ_NM"] +  "</a> " + guideHtml + "</div>";
										        	cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/oracle.jpg\" width=\"60\" height=\"70\"></div>";
									        	} else if(item2["OBJ_NM"].toUpperCase().indexOf("JUPYTER")>=0) {
									        		cardHtml = cardHtml +  "				<div class=\"cardList_title\"><a href=\"javascript:goJupyter()\">" + item2["OBJ_NM"] +  "</a> " + guideHtml + "</div>";
										        	cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/jupyter.png\" width=\"60\" height=\"70\"></div>";
									        	} else if(item2["OBJ_NM"].toUpperCase().indexOf("HIVE")>=0) {
									        		cardHtml = cardHtml +  "				<div class=\"cardList_title\"><a href=\"javascript:goHive()\">" + item2["OBJ_NM"] +  "</a> " + guideHtml + "</div>";
										        	cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/hive.png\" width=\"60\" height=\"70\"></div>";
											    }
											    
											    
											    											    
									        	cardHtml = cardHtml +  "				<div class=\"cardList_Contents_Area\">";
									        	cardHtml = cardHtml +  "					<div class=\"cardList_comment\">" + item2["OBJ_DESC"] +  "</div>";	
									        	cardHtml = cardHtml +  "				</div>";								                    				
									        	cardHtml = cardHtml +  "			</div>";
								    	 });

								    },
								    error: function(err) {
								        alert("error");
								    }
								});


								cardHtml = cardHtml +  "			</div>";
								cardHtml = cardHtml +  "			</div>";


								/*cardHtml = cardHtml +  "<div class=\"carList_tab_Area\" id=\"cateDiv\" >";
					        	cardHtml = cardHtml +  "<div class=\"subtask\">";
					        	cardHtml = cardHtml +  " <font color=\"#fff\"> <i class=\"fa fa-database\" aria-hidden=\"true\"></i>&nbsp; DB 서버</font>";						        	
					        	cardHtml = cardHtml +  "</div>";								                    	
					        	cardHtml = cardHtml +  "	<div id=\"cardList\"><br>"; 


					        	var param3 = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.task.serviceList","serverDiv":"02"};
								$.ajax({ 
								    url:'jqgrid/list', //request 보낼 서버의 경로
								    type:'post', // 메소드(get, post, put 등)
								    contentType: 'application/json; charset=utf-8',
								    dataType: 'json' ,  
								    data:JSON.stringify(param3), //보낼 데이터
								    success: function(data2) {
								    	 $.each(data2["rows"], function (index2, item2) {									    	 
										    	cardHtml = cardHtml +  "			<div class=\"card_Area\">";
									        	
										    	var guideId = item2["GUIDE_ID"];

												var guideHtml = "";
												if(guideId!=""&&guideId!=null) {
													guideHtml = "<span style=\"float:right;font-size:11px;font-weight:normal;padding: 0 0 0 0;border:0px;\" onclick=\"openHelp('" +  guideId + "')\"><i class=\"fa fa-info-circle\" aria-hidden=\"true\"></i> 가이드</span>";
												}		
										    											    	
									        	if(item2["OBJ_NM"].toUpperCase().indexOf("MSTR")>=0) {
									        		cardHtml = cardHtml +  "				<div class=\"cardList_title\"><a href=\"javascript:goMstr()\">" + item2["OBJ_NM"] +  "</a> " + guideHtml + "</div>";
									        		cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/mstr.png\"></div>";
									        	} else if(item2["OBJ_NM"].toUpperCase().indexOf("COGNOS")>=0) {
										        	cardHtml = cardHtml +  "				<div class=\"cardList_title\"><a href=\"javascript:goCognos()\">" + item2["OBJ_NM"] +  "</a> " + guideHtml + "</div>";
										        	cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/cognos.png\"></div>";
									        	} else if(item2["OBJ_NM"].toUpperCase().indexOf("ORACLE")>=0) {
										        	cardHtml = cardHtml +  "				<div class=\"cardList_title\"><a href=\"javascript:goOracle()\">" + item2["OBJ_NM"] +  "</a> " + guideHtml + "</div>";
										        	cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/oracle.jpg\" width=\"60\" height=\"70\"></div>";
									        	} else if(item2["OBJ_NM"].toUpperCase().indexOf("JUPYTER")>=0) {
									        		cardHtml = cardHtml +  "				<div class=\"cardList_title\"><a href=\"javascript:goJupyter()\">" + item2["OBJ_NM"] +  "</a> " + guideHtml + "</div>";
										        	cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/jupyter.png\" width=\"60\" height=\"70\"></div>";
									        	} else if(item2["OBJ_NM"].toUpperCase().indexOf("HIVE")>=0) {
									        		cardHtml = cardHtml +  "				<div class=\"cardList_title\"><a href=\"javascript:goHive()\">" + item2["OBJ_NM"] +  "</a> " + guideHtml + "</div>";
										        	cardHtml = cardHtml +  "<div class=\"cardList_IMG effect\"><img src=\"../assets/images/hive.png\" width=\"60\" height=\"70\"></div>";
											    }
											    
											    											    
									        	cardHtml = cardHtml +  "				<div class=\"cardList_Contents_Area\">";
									        	cardHtml = cardHtml +  "					<div class=\"cardList_comment\">" + item2["OBJ_DESC"] +  "</div>";	
									        	cardHtml = cardHtml +  "				</div>";								                    				
									        	cardHtml = cardHtml +  "			</div>";
								    	 });

								    },
								    error: function(err) {
								        alert("error");
								    }
								});


								cardHtml = cardHtml +  "			</div>";
								cardHtml = cardHtml +  "			</div>";*/
					        	
					      

					        $("#cardList").html(cardHtml);		
					   
				
		}

		viewPage();
		
	});
	
	</script>
	
</head>

<body>
   <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>비정형 분석</h1>
      <ol class="breadcrumb"><li><a href="#"><i class="glyphicon glyphicon-list-alt"></i>&nbsp;비정형 분석</a></li></ol>
    </section>

    <!-- Main content -->
	<section class="content">
	
		<div class="content-body" >
	    	<div class="row">
				<div class="col-xs-12">
					<div class="box">
			            
			            <!-- /.box-header -->
			            <div class="box-body" style="margin-top:0px;">
			             			           
						<div id="cardType" >
			             			<div id="DataSet_Main" style="margin-top:0px;">
							                <ul class="DataSet_panel">
							                    <!--1페이지-->
							                    <li id="stab1" style="padding-top:0px;">
							                    	<div id="cardList"></div>
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
		</div>
	</section>
	<!-- /.content -->
	<input type="hidden" id="reqParam">
	<script type="text/javascript">
	$("input#reqParam").data("viewId", '${viewId}');
	$("input#reqParam").data("menuId", '${menuId}'); 
	</script>	
	
</body>
</html>