$(document).ready(function() {
    	
    	
	/* paging click */
    $("a[detag=link]", $(".pagination")).on("click", function(e){
  	  	e.preventDefault();			      	  	
  	  	var viewId = $(e.currentTarget).attr("viewId");
  	  	var data = $(e.currentTarget).attr("viewParam");      	  	
  	  	if (data !== undefined) {
	  		data = JSON.stringify(JSON.parse('{"' + decodeURI(data).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g,'":"') + '"}'));
	  	}      	  	
  	  	var post = {"viewId": viewId,"data": data};
  	  	
  	  	pageMove("../board/list", post);      	  	
  	  	
    });	
	
	
	$("#btnSave").on("click", function(e){    		
		var data={
				KIND_CD:reqParam['KIND_CD'],
				GO_PAGE:'1'
			};
		var viewId = $(e.currentTarget).attr("viewId");
		var post = {"viewId": viewId,"data": JSON.stringify(data)};    		    		
		pageMove("../board/write", post);
	})
	
	
	var init=function(){
		var menuNm;
		if(reqParam['KIND_CD']=="notice"){
			menuNm="공지사항";			
		}else if(reqParam['KIND_CD']=="qa"){
			menuNm="Q&A";
		}else if(reqParam['KIND_CD']=="faq"){
			menuNm="FAQ";
		}
		
		
		$(".content-wrapper .content-header h1").empty().text(menuNm);
		$(".content-wrapper .content-header .breadcrumb").empty().prepend("<li class=\"active\">"+menuNm+"</li>");
		$(".content-wrapper .content-header .breadcrumb").prepend("<li><a href=\"#\"><i class=\"fa fa-dashboard\"></i>의사소통</a></li>");
		$(".box-header .box-title").empty().prepend("<i class=\"fa fa-chevron-right\" aria-hidden=\"true\"></i>"+menuNm+"조회");
		
		
	}
	
	init();
	
});
    
function viewDetail(b_seq, viewId){
	var data={
			KIND_CD:reqParam['KIND_CD'],
			GO_PAGE:'1',
			B_SEQ:b_seq
		};
	var post = {"viewId": viewId,"data": JSON.stringify(data)};
	pageMove("../board/view", post);
}

function pageMove(url, post){
	
	$(".content-wrapper").load(url, post, function(response, status, xhr) {     	  		     	  		
  		$(window).trigger("resize");
	});
}