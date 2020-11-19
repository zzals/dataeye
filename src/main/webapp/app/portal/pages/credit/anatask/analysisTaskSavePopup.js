
var reqParam;
$(document).ready(function() {
	reqParam = $("input#reqParam").data();
	$("#APRV_ID").val(reqParam['APRV_ID']);
	$("#APRV_DETL_ID").val(reqParam['APRV_DETL_ID']);
	var lastSel;
	
	
	function getObjInfo(){
	    var formAry = $("#tableForm").serializeArray();
	    
	    var penObjMT={}, penObjDTs=[];
	    
	    debugger;
	    penObjMT['OBJ_TYPE_ID'] = reqParam['ANATASK_OBJ_TYPE_ID'];
	    penObjMT['OBJ_ID'] = reqParam['ANATASK_OBJ_ID'];
	    penObjMT['DEL_YN'] = 'N';	    
	    penObjMT['APRV_ID'] = $('#APRV_ID').val();
	    penObjMT['OBJ_NM'] = $("#ANATASK_NM").val();
	    penObjMT['OBJ_ABBR_NM'] = $("#ANATASK_NM").val();
	    
	    //penObjMT['OBJ_DESC'] = '';
	    //penObjMT['PATH_OBJ_TYPE_ID'] = '';
	    //penObjMT['PATH_OBJ_ID'] = '';
	    
	    $.map(formAry, function(data, i){
	    		    	
	    	if(data['name']=="ANATASK_KIND"){
	    		penObjDTs.push(getPenObjD("101","101",data['value']))	    		
	    	}else if(data['name']=="ASKER"){
	    		penObjDTs.push(getPenObjD("104","101",data['value']))
	    	}else if(data['name']=="ANATASK_PROPOSE"){
	    		penObjDTs.push(getPenObjD("102","101",data['value']))	    	
	    	}else if(data['name']=="ANATASK_EFFECT"){
	    		penObjDTs.push(getPenObjD("103","101",data['value']))
	    	}
	    		    	
	        
	    });
	    	    
		
		var objInfo = {
				penObjMT: penObjMT,
		        penObjDTs: penObjDTs
			};
		

	    return objInfo;
	}
	
	
	function getPenObjD(ATR_ID_SEQ, ATR_VAL_SEQ, OBJ_ATR_VAL){
		var tmp_obj={};		
		tmp_obj['OBJ_TYPE_ID']=	reqParam['ANATASK_OBJ_TYPE_ID'];
		tmp_obj['OBJ_ID']=	reqParam['ANATASK_OBJ_ID'];
		tmp_obj['DEL_YN']=	'N';
		tmp_obj['ATR_ID_SEQ']=	ATR_ID_SEQ;
		tmp_obj['ATR_VAL_SEQ']=	ATR_VAL_SEQ;
		tmp_obj['OBJ_ATR_VAL']=	OBJ_ATR_VAL;
				
		return tmp_obj;
		
	}
	
	APRV.fn.byteCheck('#ANATASK_PROPOSE', '#ANATASK_PROPOSECHECK', 300);
	APRV.fn.byteCheck('#ANATASK_EFFECT', '#ANATASK_EFFECTCHECK', 300);
	
	
	/*
	#################
	## 저장
	##################
	*/
	$("#btnSave").on("click", function(){
		
		if($("#ANATASK_NM").val()==""){
			AprvDialog.alertInfo("분석과제명을 입력해주세요.", function () {
				$("#ANATASK_NM").focus();
				return;
            });
			
		}
		if($("#ANATASK_PROPOSE").val()==""){
			AprvDialog.alertInfo("과제목적을 입력해주세요.", function () {
				$("#ANATASK_PROPOSE").focus();
				return;
            });
			
		}
		
		if($("#ANATASK_EFFECT").val()==""){
			AprvDialog.alertInfo("개대효과를 입력해주세요.", function () {
				$("#ANATASK_EFFECT").focus();
				return;
            });		
			
		}
		
		var objInfo =getObjInfo();		
		var url = 'anatask/objectAprv/save';
	    
	    DE.ajax.call({"url":url, "data":{"objInfo":objInfo, APRV_TYPE_CD:"SJT_REQ"}}, function(rsp){			
			console.log(rsp);
			if(rsp.status=="SUCCESS"){
				debugger;
				alert("저장되었습니다.");
				$("#APRV_ID").val(rsp.data['APRV_ID']);				
				reqParam["mode"]="U";
				searchViewInit();
			}
						
		});
	});
	
	
	
	
	/*
	#################
	## 승인요청
	##################
	*/
	$("#btnAprvReq").on("click", function(){
		//승인
		APRV.open.aprvPopup($("#APRV_ID").val(), "asdf");
	    		
	});
	
	/*
	#################
	## 승인처리
	##################
	*/
	$("#btnAprvDo").on("click", function(){
		
		//승인
		APRV.open.aprvDoPopup($("#APRV_ID").val(), $("#APRV_DETL_ID").val() , "asdf");
	    		
	});
	
	
	var init = function() {
				
		if (reqParam["mode"] === "C") {
			$(".popup_title").html("분석과제 작성");
			$("#btnSave").css("display","inline");
			$("#btnAprvReq").css("display","none");
			$("#btnAprvDo").css("display","none");
			
		} else if (reqParam["mode"] === "R" || reqParam["mode"] === "U") {
			$(".popup_title").html("분석과제 작성");				
		
			//조회된 후 화면에 데이터 매핑
			searchViewInit();
			
		}
		
	};
	init();
});


//##########
//  조회 후 화면 셋팅함수
//########
function searchViewInit(){
	
	
	var param={		
		"queryId":"credit_anatask.getAnaTaskList",
		"ANATASK_OBJ_TYPE_ID":reqParam["ANATASK_OBJ_TYPE_ID"],
		"ANATASK_OBJ_ID":reqParam["ANATASK_OBJ_ID"],				
		"APRV_ID":$("#APRV_ID").val()								
	}
	
	
	DE.ajax.call({"url":"metapublic/list", "data": param }, function(rsp){
		debugger;
		if(rsp.status==="SUCCESS"){					
			//조회된 후 화면에 데이터 매핑
			$("#ANATASK_NM").val(rsp.data[0]['ANATASK_NM']);
	   		$("#ANATASK_KIND").val(rsp.data[0]['ANATASK_KIND']);
	   		$("#ASKER").val(rsp.data[0]['ASKER']);       		
	   		$("#ANATASK_PROPOSE").val(rsp.data[0]['ANATASK_PROPOSE']);
	   		$("#ANATASK_EFFECT").val(rsp.data[0]['ANATASK_EFFECT']);
	   		$("#APRV_ID").val(rsp.data[0]['APRV_ID']);   
	   		$("#LAST_STUS_CD").val(rsp.data[0]['LAST_STUS_CD']);
	   		
	   		
	   		if(reqParam['APRV_MODE']=="APRV_REQ"){ //승인요청일경우
	   			debugger;
	   			if(!$("#LAST_STUS_CD").val() || $("#LAST_STUS_CD").val()=="00"){ //저장 상태라면..
	   				$("#btnSave").css("display","inline");
	   				$("#btnAprvReq").css("display","inline");
	   				$("#btnAprvDo").css("display","none");		
	   			}else if($("#LAST_STUS_CD").val()=="10"){ //승인요청 상태라면..
	   				$("#btnSave").css("display","none");
	   				$("#btnAprvReq").css("display","none");
	   				$("#btnAprvDo").css("display","none");
	   			}else if($("#LAST_STUS_CD").val()=="40" || $("#LAST_STUS_CD").val()=="42"){ //승인이나 반려일 경우
	   				$("#btnSave").css("display","none");
	   				$("#btnAprvReq").css("display","none");
	   				$("#btnAprvDo").css("display","none");
	   			}
	   			
	   		}else{ //승인처리일경우
	   			
	   			if(!$("#LAST_STUS_CD").val() || $("#LAST_STUS_CD").val()=="00"){ //저장 상태라면..
	   				$("#btnSave").css("display","none");
	   				$("#btnAprvReq").css("display","none");
	   				$("#btnAprvDo").css("display","none");		
	   			}else if($("#LAST_STUS_CD").val()=="10"){ //승인요청 상태라면..
	   				$("#btnSave").css("display","none");
	   				$("#btnAprvReq").css("display","none");
	   				$("#btnAprvDo").css("display","inline");
	   			}else if($("#LAST_STUS_CD").val()=="40" || $("#LAST_STUS_CD").val()=="42"){ //승인이나 반려일 경우
	   				$("#btnSave").css("display","none");
	   				$("#btnAprvReq").css("display","none");
	   				$("#btnAprvDo").css("display","none");
	   			}
	   		}
	   		
			
		}			
	});		
	
    		
}

//차후 승인요청이 완료되면 호출하는 
var fn_aprv_callback=function(data){	
	if(data.rstCd==='SUCC'){
		searchViewInit();
	}else{
		
	}
}