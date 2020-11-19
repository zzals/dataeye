<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html> 
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><spring:message code="app.title" text="DateEye" /></title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<link rel="stylesheet" href="<spring:url value="/assets/stylesheets/dataeye-tsearch.css"/>">
<style type="text/css">
.ui-autocomplete em {  
	color : red; 
	font-weight: bold;
}
</style>  
</head>  
<body > 
 
<div style="margin-top:100px;margin-left:50px">
	<input id="tSearch" type="text" name="tSearch" class="form-control" placeholder="Search" value="" style="width: 30%; height: 50px;border-right-color: #ffffff;">
	<input type="button"id="btnTSearch"  type="button" style="height: 50px; width: 50px;" value="검색">

	<hr>
	<b>추천검색어</b>	
   <div id="autoResult"></div>
	
	<hr>
	<b>검색결과</b>	
	<br>
	<div id="searchResult"></div>


</div>
</body>

<script type="text/javascript">
    <!--
    (function() {
    	DE = {};	
    	DE.contextPath = "";
    	DE.setContextPath = function(contextPath) {
    		DE.contextPath = contextPath;
    	};
    })();
    DE.setContextPath('/dataeye');    
    //-->
</script>
<!-- jQuery -->  
<script src="${pageContext.request.contextPath}/webjars/jquery/3.1.1/jquery.min.js"></script>
<!-- Bootstrap -->
<!-- <script src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/webjars/bootbox/4.4.0/bootbox.js"></script>   
<!-- jquery-ui -->  
<script src="${pageContext.request.contextPath}/webjars/jquery-ui/1.12.1/jquery-ui.min.js"></script>
<!-- jqgrid -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/Guriddo_jqGrid_JS_5.2.0/js/i18n/grid.locale-kr.js"></script>

<script src="${pageContext.request.contextPath}/assets/javascripts-lib/Guriddo_jqGrid_JS_5.2.0/src/jquery.jqGrid.js"></script>

<!-- jquery-i18n-properties -->
<script src="${pageContext.request.contextPath}/webjars/jquery-i18n-properties/1.2.2/jquery.i18n.properties.min.js"></script>

<!-- jquery layout -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.layout/jquery.layout_and_plugins.js"></script>
<!-- jquery treeview -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/bootstrap.treeview/bootstrap-treeview.js"></script>
<!-- jquery mask -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.mask/jquery.mask.min.js"></script>
<!-- Sparkline -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/sparkline/jquery.sparkline.min.js"></script>
<!-- SlimScroll 1.3.0 -->  
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/slimScroll/jquery.slimscroll.min.js"></script>
<!-- filedownload -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/jquery.fileDownload/jquery.fileDownload.js"></script>

<!-- date time picker -->
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/datetimepicker/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/javascripts-lib/datetimepicker/ko.js"></script>

<script src="${pageContext.request.contextPath}/assets/javascripts-lib/iCheck/icheck.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/javascripts-lib/select2/select2.full.min.js"></script>
<script type="text/javascript" src="<spring:url value="/assets/javascripts/dataeye.core.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/assets/javascripts-lib/d3/d3.v3.min.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/assets/javascripts/dataeye.tsearch.js"/>"></script>
<script type="text/javascript">

String.prototype.toKorChars = function() { 
	var cCho = [ 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' ], cJung = [ 'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ' ], cJong = [ '', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' ], cho, jung, jong; 
	 var str = this, cnt = str.length, chars = [], cCode; 
    for (var i = 0; i < cnt; i++) { 
       cCode = str.charCodeAt(i); 
       if (cCode == 32) { continue; } // 한글이 아닌 경우 
       if (cCode < 0xAC00 || cCode > 0xD7A3) {
            chars.push(str.charAt(i)); 
            continue; 
       } 
       cCode = str.charCodeAt(i) - 0xAC00; 
       jong = cCode % 28; // 종성  
       jung = ((cCode - jong) / 28 ) % 21; // 중성 
       cho = (((cCode - jong) / 28 ) - jung ) / 21; // 초성 
 
       chars.push(cCho[cho], cJung[jung]); 
       if (cJong[jong] !== '') { 
          chars.push(cJong[jong]); 
       } 
    }
    var chword = "";
    for(var p = 0; p<chars.length;p++) {
    	chword+=chars[p];
    }
    return chword; 
}



$(document).ready(function() {
	$("#tSearch").on("keyup", function(e){
			
		if (e.which == 13) {
			$("#btnTSearch").trigger("click");
		    return false;
		} else {
			var keyword = $("#tSearch").val().trim();
			var word = keyword.toKorChars();
			
			var rsp = DE.ajax.call({"async":false, "url":"tsearch?oper=autokeyword","data":{"word":word}});
			
			var result = "";
			$.each(rsp, function(index, value){
        		result = result + value["keyword"] + "<br>";
        		result = result  + "<hr>";
        	});
			if(keyword=="") {
				$("#autoResult").html("");
			} else {
				$("#autoResult").html(result);
			}
			
		}
	});
	$("#btnTSearch").on("click", function(e) {
		var keyword = $("#tSearch").val().trim();
		
		
		if ("" === keyword) {
			alert("검색어를 입력하세요.")
		}
		DE.tsearch.search( keyword,1);
	
	});
	
});

</script>
</body>
</html>
