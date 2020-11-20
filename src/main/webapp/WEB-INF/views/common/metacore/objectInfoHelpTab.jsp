<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String viewId = request.getParameter("viewId");
	if(viewId == null)viewId = "";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><spring:message code="app.title" text="DateEye" /></title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<c:import url="/deresources/commonlib/popup_css" />

<script src="<spring:url value="/assets/javascripts/dataeye.tabconfig.js"/>"></script>
<script src="<spring:url value="/assets/javascripts/jquery-latest.min.js"/>"></script>

 
<style type="text/css">
#container[de-tag=object-info-pop] {
	height: -moz-calc(100vh - 50px);
    height: -webkit-calc(100vh - 50px);
    height: calc(100vh - 50px);
	/* border: 1px solid red; */
}

#objInfo_tabs {
	height: -moz-calc(100vh - 160px);
    height: -webkit-calc(100vh - 160px);
    height: calc(100vh - 160px);
	/* border: 1px solid blue; */
}

</style>    
</head>
	<script type="text/javascript">

		function viewPage() {
			

			var dept1first = true;
			var dept2first = true;
			var dept3first = true;

	    	
	          var menuHtml = "";					 
	     
	        	var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.common.helpMenuList"};
				$.ajax({ 
				    url:'jqgrid/list', //request 보낼 서버의 경로
				    type:'post', // 메소드(get, post, put 등)
				    contentType: 'application/json; charset=utf-8',
				    dataType: 'json' ,  
				    data:JSON.stringify(param), //보낼 데이터
				    success: function(data) {
				    	 menuHtml = menuHtml +  "<ul>";
				    	$.each(data["rows"], function (index, item) {
				    		 if(item["LVL"] == 1) {
					    		 var OBJ_ID = item["OBJ_ID"];
					    		
					    		 if(item["LEAF"] == 0) {
					    			 menuHtml = menuHtml +  "<li id='" + item["OBJ_ID"] + "' class='active has-sub'><a href='#'><span>" + item["OBJ_NM"] + "</span></a>";
				    			 } else {
				    				 menuHtml = menuHtml +  "<li id='" + item["OBJ_ID"] + "' ><a href='javascript:viewDetail(\"" + item["OBJ_ID"] + "\")'><span>" + item["OBJ_NM"] + "</span></a>";
					    		}

					    		 if(item["LEAF"] == 0) {
					    			 menuHtml = menuHtml +  "<ul>";
						    	 }
						    		
						    		$.each(data["rows"], function (index2, item2) {
						    			if(item2["LVL"] == 2 && item2["PATH_OBJ_ID"] == OBJ_ID) {
						    				
								    		 if(item2["LEAF"] == 0) {
								    			 menuHtml = menuHtml +  "<li id='" + item2["OBJ_ID"] + "' class='active has-sub'><a href='#'><span>" + item2["OBJ_NM"] + "</span></a>";
							    			 } else {
							    				 menuHtml = menuHtml +  "<li id='" + item2["OBJ_ID"] + "' ><a href='javascript:viewDetail(\"" + item2["OBJ_ID"] + "\")'><span>" + item2["OBJ_NM"] + "</span></a>";
								    		}

								    		 if(item2["LEAF"] == 0) {
								    			 menuHtml = menuHtml +  "<ul>";
									    	 }
								    			
						    				var OBJ_ID2 = item2["OBJ_ID"];
							    			$.each(data["rows"], function (index3, item3) {
							    				if(item3["LVL"] == 3 && item3["PATH_OBJ_ID"] == OBJ_ID2) {
							    					 if(item3["LEAF"] == 0) {
										    			 menuHtml = menuHtml +  "<li id='" + item3["OBJ_ID"] + "'  class='active has-sub'><a href='#'><span>" + item3["OBJ_NM"] + "</span></a></li>";
									    			 } else {
									    				 menuHtml = menuHtml +  "<li id='" + item3["OBJ_ID"] + "' ><a href='javascript:viewDetail(\"" + item3["OBJ_ID"] + "\")'><span>" + item3["OBJ_NM"] + "</span></a></li>";
										    		}
								    			}
								    			
							    			 });

							    			if(item2["LEAF"] == 0) {
							    				menuHtml = menuHtml +  "</ul>";
						    				}	
							    			menuHtml = menuHtml +  "</li>";					    			
						    			}
						    		 });

						    		 if(item["LEAF"] == 0) {
						    			 menuHtml = menuHtml +  "</ul>";
							    	 }						    		 
						    		menuHtml = menuHtml +  "</li>";
				    		 }
				    	 });
			    			menuHtml = menuHtml +  "</ul>";

				            $("#cssmenu").html(menuHtml);	
				        	setTimeout(menuScript,100);	
				    },
				    error: function(err) {
				        alert("error");
				    }
				});
			
		}
 
	viewPage();



	function menuScript() {

	   	  $('#cssmenu li.has-sub>a').on('click', function(){
	   			$(this).css( 'font-weight' , 'bold' );  
				$(this).removeAttr('href');
				var element = $(this).parent('li');
				if (element.hasClass('open')) {
					element.removeClass('open');
					element.find('li').removeClass('open');
					element.find('ul').slideUp();
				}
				else {
					element.addClass('open');
					element.children('ul').slideDown();
					element.siblings('li').children('ul').slideUp();
					element.siblings('li').removeClass('open');
					element.siblings('li').find('li').removeClass('open');
					element.siblings('li').find('ul').slideUp();
				}
			});

			$('#cssmenu>ul>li.has-sub>a').append('<span class="holder"></span>');

			(function getColor() {
				var r, g, b;
				var textColor = $('#cssmenu').css('color');
				textColor = textColor.slice(4);
				r = textColor.slice(0, textColor.indexOf(','));
				textColor = textColor.slice(textColor.indexOf(' ') + 1);
				g = textColor.slice(0, textColor.indexOf(','));
				textColor = textColor.slice(textColor.indexOf(' ') + 1);
				b = textColor.slice(0, textColor.indexOf(')'));
				var l = rgbToHsl(r, g, b);
				if (l > 0.7) {
					$('#cssmenu>ul>li>a>span').css('border-color', 'rgba(0, 0, 0, .35)');
				}
				else
				{
					$('#cssmenu>ul>li>a>span').css('border-color', 'rgba(255, 255, 255, .35)');
				}
			})();

			function rgbToHsl(r, g, b) {
			    r /= 255, g /= 255, b /= 255;
			    var max = Math.max(r, g, b), min = Math.min(r, g, b);
			    var h, s, l = (max + min) / 2;

			    if(max == min){
			        h = s = 0;
			    }
			    else {
			        var d = max - min;
			        s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
			        switch(max){
			            case r: h = (g - b) / d + (g < b ? 6 : 0); break;
			            case g: h = (b - r) / d + 2; break;
			            case b: h = (r - g) / d + 4; break;
			        }
			        h /= 6;
			    }
			    return l;
			}
		

		}


	function viewDetail(objId) {
		
		var allMenu = $("#cssmenu ul li");
		for(var i=0;i<allMenu.length;i++) {
			var mId = $("#cssmenu ul li").get(i).id;
			$("#" + mId + " a span").css( 'font-weight' , 'normal' );
					
		}
		
		$("#" + objId + " a span").css( 'font-weight' , 'bold' ); 
		var param = {"_search":false,"nd":1566802058509,"rows":10,"page":1,"sidx":"","sord":"asc","queryId":"portal.common.helpMenuView","objId":objId};
		$.ajax({ 
		    url:'jqgrid/list', //request 보낼 서버의 경로
		    type:'post', // 메소드(get, post, put 등)
		    contentType: 'application/json; charset=utf-8',
		    dataType: 'json' ,  
		    data:JSON.stringify(param), //보낼 데이터
		    success: function(data) {

		    	var attachFileName = data["rows"][0]["ATTACH_FILE_NAME"];
		    	var attachOrgName = data["rows"][0]["ATTACH_ORG_NAME"];
		    	var attachSize = data["rows"][0]["ATTACH_SIZE"];
		    	var attachFileType = data["rows"][0]["ATTACH_FILE_TYPE"];
		    	var attachFilePath = data["rows"][0]["ATTACH_FILE_PATH"];

		    	var attachHtml = "";
		    	
		    	
				if(attachFileName!=null&&attachFileName!="") {
					
					
					attachHtml = attachHtml  + " <div class=\"Grid_Area\" style=\"margin-top:10px;\"> ";
					attachHtml = attachHtml  + " <table class=\"table_Type\"> ";
					attachHtml = attachHtml  + " 	<colgroup> ";
					attachHtml = attachHtml  + " 		<col /> ";    
					attachHtml = attachHtml  + " 		<col width=\"100\"/> ";
					attachHtml = attachHtml  + " 		<col width=\"100\"/> ";
					attachHtml = attachHtml  + " 	</colgroup> ";
					attachHtml = attachHtml  + " 		<thead> ";
					attachHtml = attachHtml  + " 		<tr> ";
					attachHtml = attachHtml  + " 			<th>파일명</th> ";
					attachHtml = attachHtml  + " 			<th>파일용량</th> ";
					attachHtml = attachHtml  + " 			<th>다운로드</th> ";
					attachHtml = attachHtml  + " 		</tr> ";
					attachHtml = attachHtml  + " 		</thead> ";
					attachHtml = attachHtml  + " 		<tbody> ";
					attachHtml = attachHtml  + " 			<td>" + attachOrgName + "</td> ";
					attachHtml = attachHtml  + " 			<td style=\"text-align:center; padding-left:0;\">" + attachSize + "</td> ";
					attachHtml = attachHtml  + " 			<td style=\"text-align:center; padding-left:0;\"><button id=\"downloadBtn\" onclick=\"doDownload('" + attachFilePath + "/" +  attachFileName +  "','" + attachOrgName + "')\" class=\"search_df_btn\" style=\"font-size:11px !important; padding:1px 10px;\" >다운로드</button></td> ";
					attachHtml = attachHtml  + " 			</tbody> ";
					attachHtml = attachHtml  + " 	</table> ";
					attachHtml = attachHtml  + " 	</div> ";
				}

				$("#attachFile").html(attachHtml);	

		    	var htmlFileName = data["rows"][0]["HTML_FILE_NAME"];
		    	var htmlOrgName = data["rows"][0]["HTML_ORG_NAME"];
		    	var htmlSize = data["rows"][0]["HTML_SIZE"];
		    	var htmlFileType = data["rows"][0]["HTML_FILE_TYPE"];
		    	var htmlFilePath = data["rows"][0]["HTML_FILE_PATH"];

				var urlAddr = data["rows"][0]["URL_ADDR"];
		    	if(htmlFileName!=null&&htmlFileName!="") {
		    		urlAddr = "/dataeye/file/pdfView?file=" + htmlFilePath + "/" + htmlFileName + "&fileLocName=" + htmlOrgName;
			    }

		    	


					
			    $("#ifrm").attr("src",urlAddr);
		    },
		    error: function(err) {
		        alert("error");
		    }
		});
	}


	function doDownload(file,fileLocName) {		
		location.href="/dataeye/file/download?file=" + file + "&fileLocName=" + fileLocName
	}



	$(window).resize(function() { 
		$("#contDiv").width($("#header").width()-240);
		$("#contDiv2").height($("#tab-content").height()-130);
	});

	 function menuDisplay() {
		var reqParam = $("input#reqParam").data();
		var viewId = reqParam["viewId"];

		if(viewId!=""&&viewId!=undefined&&viewId!=null) {
			viewDetail(viewId);

			//$("#" + viewId).parent().parent().parent().parent().children("a").click();

			var subMenu = $("#" + viewId).parent().parent().parent().parent().children("ul").children("li");

			var vid = "";
			for(var i=0;i<subMenu.length;i++) {
				
			//	$("#" + viewId).parent().parent().parent().parent().children("ul").children("li").eq(i).children("a").click();
				
				var viewList = $("#" + viewId).parent().parent().parent().parent().children("ul").children("li").eq(i).children("ul").children("li");
				for(var k=0;k<viewList.length;k++) {
					vid = $("#" + viewId).parent().parent().parent().parent().children("ul").children("li").eq(i).children("ul").children("li").get(k).id;
					if(viewId==vid) {
						$("#" + viewId).parent().parent().parent().parent().children("a").click();
						$("#" + viewId).parent().parent().parent().parent().children("ul").children("li").eq(i).children("a").click();
						
						//$("#" + viewId + " a span").css( 'color' , 'red' );
						$("#" + viewId + " a span").css( 'font-weight' , 'bold' );  
						
						
						break;
					}
				}
				if(viewId==vid) {
					break;
				}
								
			}
		}
	}
	setTimeout(menuDisplay,1000);
	</script>
<body>
<!-- UI Object -->
<div id="container" de-tag="object-info-pop">
    <!-- HEADER -->
    <div id="header" style="margin-bottom:0;">
        <div id="header" class="popup_Title_Area">        
            <div class="popup_title">도움말</div>
        </div>
    </div>
    <!-- //HEADER -->
    <div style="width:100%; height:80px;padding:0 10px;">
    	<div style="float:left; padding:20px 0 0 20px;">
    		<div style="color:#333; font-size:22px; font-weight:bold;">BI Portal 도움말</div>
    		<div style="font-size:12px; color:#999; margin-top:8px; letter-spacing:-0.5px;">BI Portal 이용도중 어려움이 있으실 때 이용해 보십시요.</div>
    	</div>
    	<div style="float:right"><img src="../assets/images/help_img.png"></div>
    </div>
    <div id="objInfo_tabs" class="popup_Content_Area" data-tabs="tabs" >
        <ul class="nav nav-tabs">
        	<li class="active">
        		<a href="#">도움말</a>
        	</li>
        	<!-- <li>
        		<a href="">첨부파일목록</a>
        	</li> -->
        </ul>
        <div id="tab-content" class="tab-content" style="padding:20px 10px;">
        	<div class="help_menu_Area">
        		<div id="cssmenu"></div>
        	</div>
        	<div id="contDiv" style="float:right; width:760px;">
        		<div id="contDiv2" style="width:100%; border:1px solid #ccc; height:420px; padding:20px;">
        			<iframe id="ifrm" width="100%" height="100%" frameborder="0"></iframe>        	 		
        		</div>
 
        		<div id="attachFile"></div>

        	</div>
        </div>
    </div>
    <span class="pop_close"><button type="button" class="close" onclick="window.close()">x</button></span>
</div>
<input type="hidden" id="reqParam">
	<script type="text/javascript">
	$("input#reqParam").data(JSON.parse('${data}'));
	</script>

</body>
</html>