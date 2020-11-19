$(document).ready(function() {
   	   
	//ckeditor 셋팅
	// 엔터시 <br/>
	/*CKEDITOR.replace("CMNT", {
		enterMode:'2',
		shiftEnterMode:'3'
	});*/
	
   	// body부분 scrollbar 생성
   	var window_height = $(window).height(),
    	content_height = window_height - 190;
    	$('.panel-body').height(content_height);
    	
   	//답변
   	$("#btnReply").on("click", function(e){    	
   		debugger;
   		var viewId = $(e.currentTarget).attr("viewId");
   		
   		var data={
   				KIND_CD:reqParam['KIND_CD'],
   				GO_PAGE:reqParam['GO_PAGE'],
   				B_SEQ:reqParam['B_SEQ'],
   				B_GRP_SEQ:B_GRP_SEQ,
   				B_SORT:B_SORT,
   				B_LVL:B_LVL   				
   			};
   		
   		var post = {"viewId": viewId,"data": JSON.stringify(data)};   		
   		pageMove("../board/write", post);	   			
   		
   	})
   	
   	//수정
   	$("#btnUpdate").on("click", function(e){    	
   		var viewId = $(e.currentTarget).attr("viewId");
   		var data={
   				KIND_CD:reqParam['KIND_CD'],
   				GO_PAGE:reqParam['GO_PAGE'],
   				B_SEQ:reqParam['B_SEQ']
   		};	
   		var post = {"viewId": viewId,"data": JSON.stringify(data)};   		
   		pageMove("../board/update", post);
   		    		
   		
   	})
   	
   	//취소
   	$("#btnList").on("click", function(e){
   		var viewId = $(e.currentTarget).attr("viewId");
   		var data={
   				KIND_CD:reqParam['KIND_CD'],
   				GO_PAGE:reqParam['GO_PAGE']   				
   		};
   		var post = {"viewId": viewId,"data": JSON.stringify(data)};   		
   		pageMove("../board/list", post);
   		
	 });
   	
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
		$(".box-header .box-title").empty().prepend("<i class=\"fa fa-chevron-right\" aria-hidden=\"true\"></i>"+menuNm+"상세보기");
		
		
	}
	
	init();
   	
})
   
function pageMove(url, post){
	
   	$(".content-wrapper").load(url, post, function(response, status, xhr) {     	  		     	  		
  		$(window).trigger("resize");
	});
}