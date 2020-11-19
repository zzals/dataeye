$(document).ready(function() {
	var reqParam = $("input#reqParam").data();	
	var lastSel;
	console.log("reqParam:", reqParam);
		
	
	
	
	var colModelTool = [
	    { index:'TOOL_NM', name: 'TOOL_NM', label: '분석도구명', width: 100, align:'center', editable: false},
	    { index:'DOWNLOAD', name: 'DOWNLOAD', label: '다운로드', width: 100, align:'center', editable: false,
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $gridTool.jqGrid("getRowData", rowid);
	    			if(!checkEndDate()){
	    				
	    				var param={
			    				SB_OBJ_TYPE_ID:reqParam["SB_OBJ_TYPE_ID"],    						
	    						ANATASK_OBJ_TYPE_ID:reqParam["ANATASK_OBJ_TYPE_ID"],    						
	    						APRV_ID:reqParam['APRV_ID'],
	    						APRV_MODE:"SB_REQ_REQ",
	    						REQ_TYPE_CD:"SB_REQ",
	    						ANATASK_OBJ_ID:rowData['ANATASK_OBJ_ID'],
	    						mode:'U'
			    		}
			    		
	    				
	    				bootbox.confirm({
	    				    title: "연장승인",
	    				    message: "기간이 만료되었습니다<br/>연장하시겠습니까",
	    				    buttons: {cancel: {label: '<i class="fa fa-times"></i> 기간연장요청'},
	    				    		confirm: {label: '<i class="fa fa-check"></i> 회수요청'}
	    				    },
	    				    callback: function (result) {
	    				    	if(!result){//기간연장요청
	    				    			
	    				    		$("#data").val(JSON.stringify(param));
	    				    		$("#_popupForm").submit();
	    				    	}else{//회수요청
	    				    		param['CHANGE_MODE']="RET";	    				    		
	    				    		$("#data").val(JSON.stringify(param));
	    				    		$("#_popupForm").submit();
	    				    	}
	    				        
	    				    }
	    				});
	    				return;
	    			}
	    			
	    			var tool_nm=rowData["TOOL_NM"];
	    			//$("#"+tool_nm+"_DOWN").click();
	    			
	    			document.getElementById(tool_nm+"_DOWN").click();
	    			
	    		}
	    	}
	    },
      	{ index:'LICENSE_NUM', name: 'LICENSE_NUM', label: '라이선스', width: 100, align:'left', editable: false},
      	{ index:'EXE', name: 'EXE', label: '실행', width: 100, align:'left', editable: false,
      		formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $gridTool.jqGrid("getRowData", rowid);
	    			if(!checkEndDate()){
	    				alert("ㅁㅁㅁ ");
	    				return;
	    			}
	    			var tool_nm=rowData["TOOL_NM"];
	    			//$("#"+tool_nm+"_EXE").click();
	    			
	    			var objWSH = new ActiveXObject("WScript.Shell");
	    		    var retval = objWSH.Run("C:/Windows/System32/notepad.exe",1,true);
	    			//document.getElementById(tool_nm+"_EXE").click();
	    			
	    			
	    		}
	    	}
      	}
    ];
	
	var optsTool = {
		"colModel":colModelTool, 
		pager:"#jqGridPagerTool",
		autowidth:true,		
		height:150,
		sortable: false,
		shrinkToFit:true,
		toppager: false,
		loadonce: true,
		scroll:-1,
		isPaging:false,
		sortname:"ATR_ID",
		beforeSelectRow: function (rowid, e) {
        	var $radio = $('input[type=radio]', $(e.target).closest('tr'));
            $radio.prop('checked', 'checked');
            return true;
        },
    	onSelectRow: function(rowid, e){			
			if(rowid && rowid!==lastSel){ 
		    	$(this).jqGrid('saveRow', lastSel, true, 'clientArray'); 
		        lastSel=rowid; 
		    }
			$(this).jqGrid('editRow',rowid, true);
		}
	};
	
	
	
	var $gridTool = DE.jqgrid.render($("#jqGridTool"), optsTool);
	
	
	var checkEndDate=function(){
		var end_date2=end_date.replace(/-/gi, "");
		
		
		var now_date=new Date();		
		var now_mon, now_day, now_year;		
		if(((now_date.getMonth()+1)+"").length==1){
			now_mon="0"+(now_date.getMonth()+1);	
		}else{
			now_mon=now_date.getMonth()+1;
		}
		
		if( (now_date.getDate()+"").length==1){
			now_day="0"+now_date.getDate();
		}else{
			now_day=now_date.getDate();
		}
		now_year=now_date.getFullYear();
		var now_date2=now_year+now_mon+now_day;
		
		var retVal=true;
		if(parseInt(end_date2)<parseInt(now_date2)){
			retVal=false;
		}
		
		return retVal;
		
	}
	
	var start_date, end_date;
	var init = function() {
		
		 
		//Sandbox 정보를 얻어온다.
		var param={		
			"queryId":"credit_anatask.getSBReqView",
			"ANATASK_OBJ_TYPE_ID":reqParam["ANATASK_OBJ_TYPE_ID"],
			"SB_OBJ_TYPE_ID":reqParam["SB_OBJ_TYPE_ID"],
			"APRV_ID":reqParam["APRV_ID"]
		}	
		
		DE.ajax.call({async:false, "url":"metapublic/list", "data": param }, function(rsp){			
			if(rsp.status==="SUCCESS"){
				
				$("#TASK_NM").val(rsp.data[0]['ANATASK_NM']);
				$("#PERIOD_DATE").val(rsp.data[0]['START_DT'] +"~"+ rsp.data[0]['END_DT']);
				end_date=rsp.data[0]['END_DT'];				
				
			}			
		});
		
		
		
		//라이센스 정보를 얻어온다.
		param={		
			"queryId":"credit_anatask.getUserLicense",
			"SB_OBJ_TYPE_ID":reqParam["SB_OBJ_TYPE_ID"],
			"SB_OBJ_ID":reqParam["SB_OBJ_ID"],
			"USER_ID":user_id
		}	
		
		DE.ajax.call({async:false, "url":"metapublic/list", "data": param }, function(rsp){			
			if(rsp.status==="SUCCESS"){	
				
				DE.jqgrid.reloadLocal($("#jqGridTool"), rsp.data);
				
				
			}			
		});	
		
		
		
		
	};
	init();
});