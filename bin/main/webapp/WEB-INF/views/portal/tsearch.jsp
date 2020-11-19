 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style class="cp-pen-styles" id="">
#tsearch-content {
	height:		calc(100Vh - 170px);
	margin:		0 auto;
}
.nav-tabs-custom {
   	margin-bottom: 0px;
}
.ui-widget.ui-autocomplete {
	z-index: 10000;
}
</style>
</head>
<body>

<section class="content">

<input type="hidden" id="tSearch" value="test">
<div class="row">
	<div class="col-md-12">
          <!-- Custom Tabs -->
		<div class="nav-tabs-custom">
            <ul id="tsearch-header" class="nav nav-tabs">
				<li class="active"><a href="#ALL_SEARCH" objTypeId="ALL" objTypeNm="전체" data-toggle="tab">전체</a></li>
				<li class="pull-right"><a href="#" class="text-muted"><i class="fa fa-gear"></i></a></li>
            </ul>
			<div id="tsearch-content" class="tab-content">
<!--               	<div class="tab-pane active" id="ALL_SEARCH">
              	
					<div class="col-xs-12">
						<div class="box">
				            <div class="box-header">
				              <h5 class="box-title"><i class="fa fa-list-ul" aria-hidden="true"></i>보고서<small><i class="fa fa-search" aria-hidden="true"></i>"매출 "에 관한  <label>121</label>개의 검색 결과를 찾았습니다.</small></h5>
				              <button type="button" class="btn btn-info pull-right"><span class="caret"></span>검색결과 더보기</button>
				            </div>
				            <div class="box-body">
				        
<div class="item-box">    
	<div class="item-box-header">
		<h5 class="item-box-title"><a>위드미 점포 오픈현황-년도별</a><small>2017.10.11</small></h5>
	</div>
	<div class="item-box-body">           	
        <strong>[설명/목적]</strong>
        <p class="text-muted">
        B.S. in Computer Science from the University of Tennessee at Knoxville
        </p>
              
        <strong>[주제영역]</strong>
        <p class="text-muted">
        B.S. in Computer Science from the University of Tennessee at Knoxville
        </p>
              
        <strong>[활용목적]</strong>
		<p class="text-muted">
        B.S. in Computer Science from the University of Tennessee at Knoxville
        </p>
    </div>
</div>

<div class="item-box">    
	<div class="item-box-header">
		<h5 class="item-box-title"><a>위드미 점포 오픈현황-년도별</a><small>2017.10.11</small></h5>
	</div>
	<div class="item-box-body">           	
        <strong>[설명/목적]</strong>
        <p class="text-muted">
        B.S. in Computer Science from the University of Tennessee at Knoxville
        </p>
              
        <strong>[주제영역]</strong>
        <p class="text-muted">
        B.S. in Computer Science from the University of Tennessee at Knoxville
        </p>
              
        <strong>[활용목적]</strong>
		<p class="text-muted">
        B.S. in Computer Science from the University of Tennessee at Knoxville
        </p>
    </div>
</div>



				            </div>
						</div>
					</div>
					<div class="col-xs-12">
						<div class="box">
				            <div class="box-header">
				              <h3 class="box-title"><i class="fa fa-list-ul" aria-hidden="true"></i>데이터 검증룰 목록</h3>
				            </div>
				            /.box-header
				            <div class="box-body">
				            	검색결과
				            </div>
				          </div>
					</div>

				</div> -->
				
            </div>
    	</div>
    </div>
</div>
</section>
<input type="hidden" id="reqParam">
<script type="text/javascript" src="<spring:url value="/assets/javascripts-lib/jquery.bootpag/jquery.bootpag.min.js"/>"></script>
<script type="text/javascript">
$("input#reqParam").data("viewId", '${viewId}');
$("input#reqParam").data("menuId", '${menuId}');
$("input#reqParam").data(JSON.parse('${data}'));
</script>
<script type="text/javascript">
$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
	$("#tSearchHeaderLayer").show();
	$("#tSearch").val(reqParam["keyword"]);

	// 검색 내용 클릭
	$("#tsearch-content").on("click", ".item-box-title > a", function(e) {
		var objTypeId = $(this).attr("objTypeId");
		var objId = $(this).attr("objId");
		if(objTypeId=="010102L") {
			window.open('/dataeye_ebay/report/reportPrompt?reportId=' + objId,'_new','')
		} else {
			DE.ui.open.popup(
					"view",
					[objTypeId, objId],
					{
						"viewname":'common/metacore/objectInfoTab',
						"objTypeId":objTypeId,
						"objId":objId,
						"mode":"RO"
					},
					null
				);
		}
		
	});



//	DE.tsearch.findDashboardObjTypes();
	$("input[name=tsearchObjType]", "#tsearchObjTypeGroup").iCheck({
	    checkboxClass: 'icheckbox_minimal-red',
	    radioClass: 'iradio_minimal-red'
	});
	$("input[name=tsearchObjType]:checked", "#tsearchObjTypeGroup").trigger("ifChanged");
	
	var getSearchType = function() {
		var type = $("ul#tsearch-header li.active a").attr("objTypeId");
		if (type === undefined) {
			type = "ALL";
		}
		return type;
	}

	//main 통합검색 - end
	
	$("#btnTSearch", "#tSearchLayer").on("click", function(e) {
		var keyword = $("#tSearch", "#tSearchLayer").val().trim();
		if ("" === keyword) {
			DE.box.alert("검색어를 입력하세요.", function(){
				setTimeout(function() {
					$("#tSearch", "#tSearchLayer").focus();
				}, 500);
			});
			return;
		}
		DE.tsearch.searchMain(getSearchType(), keyword);
		
	});
	

	$("#tSearch", "#tSearchLayer").on("keypress", function(e){
		if (e.which == 13) {
			$("#btnTSearch", "#tSearchLayer").trigger("click");
		    return false;
		}
	});
	
	$("#tsearch-content").on("click", "#btnResultMore", function(e) {
		var objTypeId = $(this).attr("objTypeId");
		$("#tsearch-header a[href=\"#tab_"+objTypeId+"\"]").tab("show");
	});
	
	var init = function() {
		DE.tsearch.makeSearchTab(reqParam["type"]);
		// tab click by  bootstrap js
		$("a[data-toggle=tab]", "#tsearch-header").on("shown.bs.tab", function (e) {
			var objTypeId = $(e.target).attr("objTypeId")
			DE.tsearch.elsearch(objTypeId, reqParam["keyword"], 1);

			$("#tsearch-content").scrollTop(0);
		});
		DE.tsearch.elsearch(reqParam["type"], reqParam["keyword"], 1);
	};
	init();
});
</script>
</body>
</html>
