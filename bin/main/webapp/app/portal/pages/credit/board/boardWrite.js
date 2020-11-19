$(document).ready(function() {
    	
	//ckeditor 셋팅
	// 엔터시 <br/>
	CKEDITOR.replace("CMNT", {
		enterMode:'2',
		shiftEnterMode:'3'
	});
	
	
	// body부분 scrollbar 생성
	var window_height = $(window).height(),
    content_height = window_height - 190;
 	$('.panel-body').height(content_height);
 	
 	var colModel = [
	    
 		{name:'CHK',index:'CHK', label:"<input type='checkbox' id='chkAll' name='chkAll' onclick='javascript:DE.jqgrid.checkAll(this, event);'/>", width:18, align:"center",
            sortable:false,
            formatter: 'checkbox',
            formatoptions: {disabled:false},
            edittype:'checkbox',
            editoptions:{value:"true:false"},
            editable:true
        },	  
 		{ index:'FILE_NM', name: 'FILE_NM', label: '파일명', width: 100, align:'left', editable: false},    	    
      	{ index:'FILE_PATH', name: 'FILE_PATH', label: 'FILE_PATH', width: 100, align:'center', editable: false, hidden:true}          	
    ];
	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		autowidth:true,
		height:100,
		shrinkToFit:true,
		toppager: false,
		loadonce: true,
		scroll:-1,
		isPaging:false,
		sortname:"DATASET_NM",
		beforeSelectRow: function (rowid, e) {
        	var $radio = $('input[type=radio]', $(e.target).closest('tr'));
            $radio.prop('checked', 'checked');
            return true;
        }
	};    	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	
	//파일추가
	$("#btnFileAdd").on("click", function(){
		$("#uploadfile").click();
	})
	
	//파일삭제
	$("#btnFileDel").on("click", function(){
		debugger;
		
		var ids = $grid.jqGrid('getDataIDs');
		var totData=$grid.getRowData();
		var id, data, check_i=0;
		for(var i=0;i<ids.length;i++){
			data=totData[i];    			
			if(data['CHK']=="true"){
				check_i++;
			}
		}
		if(check_i==0){
			alert("삭제할려는 항목을 선택해주세요");
			return;
		}
		
		for(var i=0;i<ids.length;i++){
			data=totData[i];
			id=ids[i];
			if(data['CHK']=="true"){
				$grid.jqGrid('delRowData',id);
			}
		}
	})
	
	
	$("#uploadfile").on("change", function(){
		var formData = new FormData();
		formData.append("uploadfile", $("input[name=uploadfile]")[0].files[0]); 
		$.ajax({ 
			url: '../board/fileUpload', 
			data: formData, 
			processData: false, 
			contentType: false, 
			type: 'POST', 
			success: function(rsp){ 
								
				var ids = $("#jqGrid").jqGrid('getDataIDs');	
				var row_data=$("#jqGrid").getRowData();
				var lastRow =  ids.length;
				var newRowId =  ids.length+1;    				

				var row_data={
					"FILE_NM": rsp['data']['FILE_NM'],
					"FILE_PATH": rsp['data']['FILE_PATH']
				}
				$("#jqGrid").jqGrid('addRowData', newRowId, row_data);
				
				
			} 
		});
	})
	
	
	//저장
	$("#btnSave").on("click", function(e){
		
		debugger;
		var viewId = $(e.currentTarget).attr("viewId");
		
		var url = 'board/writeDo';
	    if($("#TITLE").val()==""){
	    	alert("제목을 입력해주세요.");
	    	$("#TITLE").focus();
	    	return;
	    }
	    
		var cmnt=CKEDITOR.instances.CMNT.getData();
		if(cmnt==""){
	    	alert("내용을 입력해주세요.");	    	
	    	return;
	    }
		
		var board_data={
			TITLE:$("#TITLE").val(),
			CMNT:cmnt,
			KIND_CD:reqParam['KIND_CD']
		}
		
		var file_list=$("#jqGrid").getRowData();
		
		var param={"BOARD_DATA":board_data, FILE_LIST:file_list};
		DE.ajax.call({"url":url, "data":param}, function(rsp){
	    	
			if(rsp.status=="SUCCESS"){
				alert("저장되었습니다.");				
				var data={
					KIND_CD:reqParam['KIND_CD'], 
					GO_PAGE:'1'
				};
				
	    		var post = {"viewId": viewId,"data": JSON.stringify(data)};
	    		
	    		$(".content-wrapper").load("../board/list", post, function(response, status, xhr) {     	  		     	  		
	 	  	  		$(window).trigger("resize");
	 	  	  	});
				
			}
						
		});	
		
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
		$(".box-header .box-title").empty().prepend("<i class=\"fa fa-chevron-right\" aria-hidden=\"true\"></i>"+menuNm+"등록");
		
		
	}
	
	init();
	
})