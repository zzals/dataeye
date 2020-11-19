var reqParam ;
$(document).ready(function() {
	reqParam = $("input#reqParam").data();	
	$("#APRV_ID").val(reqParam['APRV_ID']);
	$("#APRV_DETL_ID").val(reqParam['APRV_DETL_ID']);
	
	var lastSel, lastSelTool;
		
	window.resizeTo(950,1000);
	
	var colModel = [		
		{name:'CHK',index:'CHK', label:"<input type='checkbox' id='chkAll' name='chkAll' onclick='javascript:DE.jqgrid.checkAll(this, event);'/>", width:18, align:"center",
            sortable:false,
            formatter: 'checkbox',
            formatoptions: {disabled:false},
            edittype:'checkbox',
            editoptions:{value:"true:false"},
            editable:true
        },		    
	    { index:'USER_NM', name: 'USER_NM', label: '이름', width: 100, align:'center', editable: false},
      	{ index:'USER_ID', name: 'USER_ID', label: 'ID', width: 100, align:'left', editable: false}
    ];

	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		autowidth:true,		
		height:50,
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
			/*if(rowid && rowid!==lastSel){ 
		    	$(this).jqGrid('saveRow', lastSel, true, 'clientArray'); 
		        lastSel=rowid; 
		    }
			$(this).jqGrid('editRow',rowid, true);*/
    		lastSel=rowid;
		}
	};
	
	var navOpts = {
		navOptions:{
			cloneToTop:false,
			view:false,
			add:false,
			edit:false,
			del:false,
			refresh:false,
			search:false
		}
	};
	
	var $gridAnal = DE.jqgrid.render($("#jqGridAnal"), opts);
	DE.jqgrid.navGrid($("#jqGridAnal"), $("#jqGridPagerAnal"), navOpts);
	
	
	
	var colModelTool = [		
		{name:'CHK',index:'CHK', label:"<input type='checkbox' id='chkAll' name='chkAll' onclick='javascript:DE.jqgrid.checkAll(this, event);'/>", width:18, align:"center",
            sortable:false,
            formatter: 'checkbox',
            formatoptions: {disabled:false},
            edittype:'checkbox',
            editoptions:{value:"true:false"},
            editable:true
        },	    
	    { index:'TOOL_NM', name: 'TOOL_NM', label: '분석도구명', width: 100, align:'center', editable: false},
      	{ index:'CNT', name: 'CNT', label: '남은갯수', width: 100, align:'left', editable: false}
      	
    ];

	var optsTool = {
		"colModel":colModelTool, 
		pager:"#jqGridPagerTool",
		autowidth:true,		
		height:120,
		sortable: false,
		shrinkToFit:true,
		toppager: false,
		loadonce: true,
		scroll:-1,
		isPaging:false,
		sortname:"ATR_ID",
		/*beforeSelectRow: function (rowid, e) {
        	var $radio = $('input[type=radio]', $(e.target).closest('tr'));
            $radio.prop('checked', 'checked');
            return true;
        },*/
    	onSelectRow: function(rowid, e){			
			/*if(rowid && rowid!==lastSelTool){ 
		    	$(this).jqGrid('saveRow', lastSelTool, true, 'clientArray'); 
		    	lastSelTool=rowid; 
		    }
			$(this).jqGrid('editRow',rowid, true);*/
			
			lastSelTool=rowid; 
		}
	};
	
	var navOptsTool = {
		navOptions:{
			cloneToTop:false,
			view:false,
			add:false,
			edit:false,
			del:false,
			refresh:false,
			search:false
		}
	};
	
	var $gridAnal = DE.jqgrid.render($("#jqGridTool"), optsTool);
	DE.jqgrid.navGrid($("#jqGridTool"), $("#jqGridPagerTool"), navOptsTool);
	
	DE.fn.dateTimePicker($('#START_DT'));
	DE.fn.dateTimePicker($('#END_DT'));
	
	
	
	function getObjInfo(){
	    var formAry = $("#tableForm").serializeArray();
	    
	    var penObjMT={}, penObjDTs=[];
	    
	    penObjMT['OBJ_TYPE_ID'] = reqParam['SB_OBJ_TYPE_ID'];
	    penObjMT['OBJ_ID'] = $('#SB_OBJ_ID').val();
	    
	    //회수나 변경이라면..
	    if(reqParam['CHANGE_MODE']=="CHG" || reqParam['CHANGE_MODE']=="RET"){	    	
	    	penObjMT['APRV_ID'] = "";	    	
	    }else{	    	
	    	penObjMT['APRV_ID'] = $('#APRV_ID').val();	    	
	    }
	    
	    penObjMT['DEL_YN'] = 'N';	    
	    
	    penObjMT['OBJ_NM'] = $("#ANATASK_NM").text();
	    penObjMT['OBJ_ABBR_NM'] = $("#ANATASK_NM").text();
	    //penObjMT['OBJ_DESC'] = '';
	    penObjMT['PATH_OBJ_TYPE_ID'] = reqParam['ANATASK_OBJ_TYPE_ID'];
	    penObjMT['PATH_OBJ_ID'] = $("#ANATASK_NM").val();
	    
	    $.map(formAry, function(data, i){
	    		    	
	    	if(data['name']=="ASK_CMNT"){
	    		penObjDTs.push(getPenObjD("101","101",data['value']));	    		
	    	}else if(data['name']=="START_DT"){
	    		penObjDTs.push(getPenObjD("102","101",data['value']));
	    	}else if(data['name']=="END_DT"){
	    		penObjDTs.push(getPenObjD("102","102",data['value']));	    	
	    	}else if(data['name']=="DB_ACCOUNT"){
	    		penObjDTs.push(getPenObjD("103","101",data['value']));
	    	}else if(data['name']=="DB_PWD"){
	    		penObjDTs.push(getPenObjD("104","101",data['value']));
	    	}	    		    	
	        
	    });
	    
	    //분석가 할당
	    var user_data=$("#jqGridAnal").getRowData();
	    for(var i=0;i<user_data.length;i++){
	    	var data=user_data[i];
	    	penObjDTs.push(getPenObjD("105","10"+(i+1),data['USER_ID']));
	    }
	    
	    
	    //tool 할당
	    var tool_data=$("#jqGridTool").getRowData();
	    for(var i=0;i<tool_data.length;i++){
	    	var data=tool_data[i];
	    	if(data['CHK']=="true"){
	    		if(data['TOOL_NM']=="SPSS"){
	    			penObjDTs.push(getPenObjD("106","101","Y"));
	    		}else if(data['TOOL_NM']=="TABLEAU"){
	    			penObjDTs.push(getPenObjD("107","101","Y"));
	    		}else if(data['TOOL_NM']=="COGNOS"){
	    			penObjDTs.push(getPenObjD("108","101","Y"));
	    		}else if(data['TOOL_NM']=="MS-R"){
	    			penObjDTs.push(getPenObjD("109","101","Y"));
	    		}	    		
	    	}
	    	
	    }
	    
		var objInfo = {
				penObjMT: penObjMT,
		        penObjDTs: penObjDTs
			};
		

	    return objInfo;
	}
	
	
	function getPenObjD(ATR_ID_SEQ, ATR_VAL_SEQ, OBJ_ATR_VAL){
		var tmp_obj={};		
		tmp_obj['OBJ_TYPE_ID']=	reqParam['SB_OBJ_TYPE_ID'];
		tmp_obj['OBJ_ID']=	$("#SB_OBJ_ID").val();
		tmp_obj['DEL_YN']=	'N';
		tmp_obj['ATR_ID_SEQ']=	ATR_ID_SEQ;
		tmp_obj['ATR_VAL_SEQ']=	ATR_VAL_SEQ;
		tmp_obj['OBJ_ATR_VAL']=	OBJ_ATR_VAL;
				
		return tmp_obj;		
	}
	
	
	
	var saveObj=function(){
		$("#jqGridAnal").jqGrid('saveRow',lastSel);
		$("#jqGridTool").jqGrid('saveRow',lastSelTool);
		
		
		
		//분석가 할당
	    var user_data=$("#jqGridAnal").getRowData();
	    var user_cnt=user_data.length;
	    
	    //tool 할당
	    var tool_data=$("#jqGridTool").getRowData();
	    var check_i=0;
	    for(var i=0;i<tool_data.length;i++){
	    	var data=tool_data[i];
	    	if(data['CHK']=="true"){
	    		if(data['TOOL_NM']=="SPSS"){
	    			var cnt=parseInt(data['CNT']);
	    			if(cnt-(user_cnt+1)<0){
	    				alert("SPSS 라이센스가 부족합니다.")
	    				return;
	    			}
	    		}else if(data['TOOL_NM']=="TABLEAU"){
	    			
	    			var cnt=parseInt(data['CNT']);
	    			if(cnt-(user_cnt+1)<0){
	    				alert("TABLEAU 라이센스가 부족합니다.")
	    				return;
	    			}
	    			
	    		}else if(data['TOOL_NM']=="COGNOS"){
	    			var cnt=parseInt(data['CNT']);
	    			if(cnt-(user_cnt+1)<0){
	    				alert("COGNOS 라이센스가 부족합니다.")
	    				return;
	    			}
	    		}else if(data['TOOL_NM']=="MS-R"){
	    			
	    		}	
	    		
	    		check_i++;
	    	}	    	
	    }
	    
	    if(check_i==0){
	    	alert("분석도구를 선택해주세요.");
	    	return;
	    }
	    
	    debugger;
		var objInfo =getObjInfo();		
		var url = 'anatask/objectAprv/save';
	    
		var param;
		if(reqParam['CHANGE_MODE']=="CHG" || reqParam['CHANGE_MODE']=="RET"){
			param={"objInfo":objInfo, APRV_TYPE_CD:reqParam['REQ_TYPE_CD'], CHANGE_MODE:reqParam['CHANGE_MODE']};
		}else{
			param={"objInfo":objInfo, APRV_TYPE_CD:reqParam['REQ_TYPE_CD'], CHANGE_MODE:'N'};
		}
		
		DE.ajax.call({"url":url, "data":param}, function(rsp){
	    	
			console.log(rsp);
			if(rsp.status=="SUCCESS"){
				alert("저장되었습니다.");
				$("#APRV_ID").val(rsp.data['APRV_ID']);				
				reqParam["mode"]="U";
				reqParam['CHANGE_MODE']="";
				viewDetail();
			}
						
		});	
	}
	/*
	#################
	## 저장
	##################
	*/
	$("#btnSave").on("click", function(){
		
		saveObj();		
		
	});
	
	/*
	#################
	## 변경요청
	##################
	*/
	$("#btnChangeSave").on("click", function(){
		reqParam['CHANGE_MODE']="CHG";
		
		$("#data").val(JSON.stringify(reqParam));
		$("#modifyForm").submit();
	})
	
	/*
	#################
	## 회수요청
	##################
	*/
	$("#btnRetSave").on("click", function(){
		saveObj();
	})
	
	
	
	/*
	#################
	## 승인처리
	##################
	*/
	$("#btnAprvDo").on("click", function(){
		
		//승인
		APRV.open.aprvDoPopup($("#APRV_ID").val(), $("#APRV_DETL_ID").val(), "asdf");
		
	});
	
	/*
	#################
	## 승인요청
	##################
	*/
	$("#btnAprvReq").on("click", function(){
		
		APRV.open.aprvPopup($("#APRV_ID").val(), "asdf");		
		
	});
	
	/*
	#################
	## 결제자 추가
	##################
	*/
	$("#btnAdd").on("click", function(e){
		userPopup();		
	});
	
	
	/*
	#################
	## 결제자 삭제
	##################
	*/
	$("#btnDel").on("click", function(e){
		userDel();		
	});
	
	defaultSetting();
	
	function defaultSetting(){
		//########################
		//데이터 이용기간 default값 한달
		//######################
		var now_date=new Date();
		var now_mon, now_day;
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
		$("input#START_DT").val(now_date.getFullYear()+"-"+now_mon+"-"+now_day);
		$("input#END_DT").val(now_date.getFullYear()+"-"+now_mon+"-"+now_day);
		
		
	}
	
	
	
	
	var init = function() {
		
		$("#data").val(JSON.stringify(reqParam));
		
		//분석과제를 조회한다.
		var param={		
			"queryId":"credit_anatask.getAnaTaskName",
			"ANATASK_OBJ_TYPE_ID":reqParam["ANATASK_OBJ_TYPE_ID"]
		}	
		
		DE.ajax.call({async:false, "url":"metapublic/list", "data": param }, function(rsp){			
			if(rsp.status==="SUCCESS"){					
				$.each(rsp.data, function() {       
					if(reqParam['mode']=="C"){
						$("select#ANATASK_NM").append("<option value='"+this.ANATASK_OBJ_ID+"'>"+this.ANATASK_NM+"</option>");
					}else{
						
						if(reqParam['ANATASK_OBJ_ID']==this.ANATASK_OBJ_ID){
							$("select#ANATASK_NM").append("<option value='"+this.ANATASK_OBJ_ID+"' selected>"+this.ANATASK_NM+"</option>");
						}else{
							$("select#ANATASK_NM").append("<option value='"+this.ANATASK_OBJ_ID+"'>"+this.ANATASK_NM+"</option>");
						}
					}								        		            
			    });			
			}
			
		});		
	
		
		if (reqParam["mode"] === "C") {
			//$(".popup_title").html("데이터셋 등록");	
			
			//라이센스 정보를 얻어온다.
			var param={		
				"queryId":"credit_anatask.getToolLicense"											
			}
			DE.ajax.call({async:false, "url":"metapublic/list", "data": param }, function(rsp){			
				if(rsp.status==="SUCCESS"){					
					DE.jqgrid.reloadLocal($("#jqGridTool"), rsp.data);		
				}			
			});	
			
			
		} else if (reqParam["mode"] === "R" || reqParam["mode"] === "U") {			
			viewDetail();			
		}
		
	};
	init();
});

function addUser(user_id, user_nm){
	
	var ids = $("#jqGridAnal").jqGrid('getDataIDs');	
	var row_data=$("#jqGridAnal").getRowData();
	var lastRow =  ids.length;
	var newRowId =  ids.length+1;
	
	var check_i=0;
	for(var i=0;i<lastRow;i++){
		var data=row_data[i];
		if(data['USER_ID']==user_id){
			check_i++;
		}
	}
	
	if(check_i==0){

		var row_data={
			"USER_ID": user_id,
			"USER_NM": user_nm
		}
		$("#jqGridAnal").jqGrid('addRowData', newRowId, row_data);
	}
			
	
}

function userPopup(){
	DE.ui.open.popup(
		"view",
		["anaUserAddPopup23232323"],
		{
			viewname:'portal/credit/anatask/anaUserAddPopup',				
			mode:'C'
		},
		"left=1, top=1, width=500, height=500, toolbar=no, menubar=no, location=no, resizable=no"
	);
}

function userDel(){
	var ids = $("#jqGridAnal").jqGrid('getDataIDs');
	var totData=$("#jqGridAnal").getRowData();
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
			$("#jqGridAnal").jqGrid('delRowData',id);
		}
	}
}
function allDisable(boolval){
	debugger;
	 
	$("#ASK_CMNT").attr("readonly", boolval); 
	$("#START_DT").attr("readonly", boolval); 
	$("#END_DT").attr("readonly", boolval); 
	$("#DB_ACCOUNT").attr("readonly", boolval); 
	$("#DB_PWD").attr("readonly", boolval); 
	
	$("#ANATASK_NM").attr("disabled", boolval);
	
	$("#btnAdd").attr("disabled", boolval); //설정
	$("#btnDel").attr("disabled", boolval); //설정
	
	if(boolval){
		$("#btnAdd").off();
		$("#btnDel").off();
	}else{
		$("#btnAdd").on("click", function(e){userPopup();});
		$("#btnDel").on("click", function(e){userDel()});
	}
	
	
}

function viewDetail(){
	
	
	//마스터 상태정보 테이블에서 sandbox처리 상태만 불러온다.
	var param={		
		"queryId":"credit_anatask.getCreditAprvStsMDo",
		"SB_OBJ_TYPE_ID":reqParam["SB_OBJ_TYPE_ID"]		
	}	
	
	var DO_STATUS_CD, DO_STS_CNT=0;
	DE.ajax.call({async:false, "url":"metapublic/list", "data": param }, function(rsp){			
		if(rsp.status==="SUCCESS"){
			DO_STS_CNT=rsp.data.length;
			if(DO_STS_CNT>0){
				DO_STATUS_CD=rsp.data[0]['STATUS_CD'];
			}			 
		}			
	});
	
	
	//sb정보를 조회한다.
	var param={		
		"queryId":"credit_anatask.getSBReqView",
		"SB_OBJ_TYPE_ID":reqParam["SB_OBJ_TYPE_ID"],
		"APRV_ID":$("#APRV_ID").val(),
		"ANATASK_OBJ_TYPE_ID":reqParam["ANATASK_OBJ_TYPE_ID"]
	}	
	
	
	DE.ajax.call({async:false, "url":"metapublic/list", "data": param }, function(rsp){			
		if(rsp.status==="SUCCESS"){
			
			debugger;
			$("#SB_OBJ_ID").val(rsp.data[0]['SB_OBJ_ID']);
			$("#ANATASK_OBJ_ID").val(rsp.data[0]['ANATASK_OBJ_ID']);
			
			$("#ASK_CMNT").val(rsp.data[0]['ASK_CMNT']);
			$("#START_DT").val(rsp.data[0]['START_DT']);
			$("#END_DT").val(rsp.data[0]['END_DT']);
			$("#DB_ACCOUNT").val(rsp.data[0]['DB_ACCOUNT']);
			$("#DB_PWD").val(rsp.data[0]['DB_PWD']);
			$("#STATUS_CD").val(rsp.data[0]['STATUS_CD']);
			$("#CRET_DT").val(rsp.data[0]['CRET_DT']);
			
			var aprv_reqr_cd=rsp.data[0]['APRV_REQR_CD'];
			
			if(reqParam['APRV_MODE']=="SB_REQ_REQ"){ //sb 요청
				
				if(reqParam['CHANGE_MODE']=="CHG"){ //변경요청상태라면..
					
					$("#btnSave").css("display","inline");
	   				$("#btnAprvReq").css("display","none");
	   				$("#btnAprvDo").css("display","none");
	   				$("#btnChangeSave").css("display","none");
	   				$("#btnRetSave").css("display","none");
	   				
	   				allDisable(false);
	   				
				}else if(reqParam['CHANGE_MODE']=="RET"){ //회수요청이라면..
						
						$("#btnSave").css("display","none");
		   				$("#btnAprvReq").css("display","none");
		   				$("#btnAprvDo").css("display","none");
		   				$("#btnChangeSave").css("display","none");
		   				$("#btnRetSave").css("display","inline");
		   				allDisable(true);
					
				}else{
					if(!$("#STATUS_CD").val() || $("#STATUS_CD").val()=="00"){ //저장 상태라면..
		   				$("#btnSave").css("display","inline");
		   				$("#btnAprvReq").css("display","inline");
		   				$("#btnAprvDo").css("display","none");
		   				$("#btnChangeSave").css("display","none");
		   				$("#btnRetSave").css("display","none");
		   				
		   				allDisable(false);
		   				
		   			}else if($("#STATUS_CD").val()=="10"){ //승인요청 상태라면..
		   				$("#btnSave").css("display","none");
		   				$("#btnAprvReq").css("display","none");
		   				$("#btnAprvDo").css("display","none");
		   				$("#btnChangeSave").css("display","none");
		   				$("#btnRetSave").css("display","none");
		   				
		   				allDisable(true);
		   				
		   			}else if($("#STATUS_CD").val()=="40"){ //승인일경우
		   				
		   				$("#btnSave").css("display","none");
		   				$("#btnAprvReq").css("display","none");
		   				$("#btnAprvDo").css("display","none");
		   				$("#btnChangeSave").css("display","none");
		   				$("#btnRetSave").css("display","none");
		   				
		   				if(aprv_reqr_cd!="30"){//회수가 아니라면(신규나 변경이라면..)
		   					
		   					if(DO_STS_CNT>0){ //sandbox 처리상태가 있다면..
			   					if(DO_STATUS_CD=="40"){ //sandbox 처리상태가 승인이라면..
			   						$("#btnChangeSave").css("display","inline");
			   					}else{
			   						$("#btnChangeSave").css("display","inline");
			   					}
			   				}
		   				}
		   				
		   				allDisable(true);
		   				
		   				
		   			}else if($("#STATUS_CD").val()=="42"){ // 반려일 경우
		   				$("#btnSave").css("display","none");
		   				$("#btnAprvReq").css("display","none");
		   				$("#btnAprvDo").css("display","none");
		   				$("#btnChangeSave").css("display","none");
		   				$("#btnRetSave").css("display","none");
		   				
		   				allDisable(true);
		   			
		   			}
				}
				
				
			}else if(reqParam['APRV_MODE']=="SB_REQ_APRV"){ //sb 요청 승인처리
				
				allDisable(true);
				
				if(!$("#STATUS_CD").val() || $("#STATUS_CD").val()=="00"){ //저장 상태라면..
	   				$("#btnSave").css("display","none");
	   				$("#btnAprvReq").css("display","none");
	   				$("#btnAprvDo").css("display","none");	
	   				$("#btnChangeSave").css("display","none");
	   				$("#btnRetSave").css("display","none");
	   			}else if($("#STATUS_CD").val()=="10"){ //승인요청 상태라면..
	   				$("#btnSave").css("display","none");
	   				$("#btnAprvReq").css("display","none");
	   				$("#btnAprvDo").css("display","inline");
	   				$("#btnChangeSave").css("display","none");
	   				$("#btnRetSave").css("display","none");
	   			}else if($("#STATUS_CD").val()=="40" || $("#STATUS_CD").val()=="42"){ //승인이나 반려일 경우
	   				$("#btnSave").css("display","none");
	   				$("#btnAprvReq").css("display","none");
	   				$("#btnAprvDo").css("display","none");
	   				$("#btnChangeSave").css("display","none");
	   				$("#btnRetSave").css("display","none");
	   			}
				
			}else if(reqParam['APRV_MODE']=="SB_DO_REQ"){ //sb 처리  승인요청
				
				allDisable(true);
				
				if(!$("#STATUS_CD").val() || $("#STATUS_CD").val()=="00"){ //저장 상태라면..
	   				$("#btnSave").css("display","none");
	   				$("#btnAprvReq").css("display","inline");
	   				$("#btnAprvDo").css("display","none");
	   				$("#btnChangeSave").css("display","none");
	   				$("#btnRetSave").css("display","none");
	   			}else if($("#STATUS_CD").val()=="10"){ //승인요청 상태라면..
	   				$("#btnSave").css("display","none");
	   				$("#btnAprvReq").css("display","none");
	   				$("#btnAprvDo").css("display","none");
	   				$("#btnChangeSave").css("display","none");
	   				$("#btnRetSave").css("display","none");
	   			}else if($("#STATUS_CD").val()=="40" || $("#STATUS_CD").val()=="42"){ //승인이나 반려일 경우
	   				$("#btnSave").css("display","none");
	   				$("#btnAprvReq").css("display","none");
	   				$("#btnAprvDo").css("display","none");
	   				$("#btnChangeSave").css("display","none");
	   				$("#btnRetSave").css("display","none");
	   			}
				
				
			}else if(reqParam['APRV_MODE']=="SB_DO_APRV"){ //sb 처리  승인처리
				
				allDisable(true);
				
				if(!$("#STATUS_CD").val() || $("#STATUS_CD").val()=="00"){ //저장 상태라면..
	   				$("#btnSave").css("display","none");
	   				$("#btnAprvReq").css("display","none");
	   				$("#btnAprvDo").css("display","none");
	   				$("#btnChangeSave").css("display","none");
	   				$("#btnRetSave").css("display","none");
	   			}else if($("#STATUS_CD").val()=="10"){ //승인요청 상태라면..
	   				$("#btnSave").css("display","none");
	   				$("#btnAprvReq").css("display","none");
	   				$("#btnAprvDo").css("display","inline");
	   				$("#btnChangeSave").css("display","none");
	   				$("#btnRetSave").css("display","none");
	   			}else if($("#STATUS_CD").val()=="40" || $("#STATUS_CD").val()=="42"){ //승인이나 반려일 경우
	   				$("#btnSave").css("display","none");
	   				$("#btnAprvReq").css("display","none");
	   				$("#btnAprvDo").css("display","none");
	   				$("#btnChangeSave").css("display","none");
	   				$("#btnRetSave").css("display","none");
	   			}
			}
			
			
			 
		}			
	});
	
	//분석가를 조회한다.
	var param={		
		"queryId":"credit_anatask.getAnaPersonList",
		"SB_OBJ_TYPE_ID":reqParam["SB_OBJ_TYPE_ID"],
		"SB_OBJ_ID":$("#SB_OBJ_ID").val(),
		"APRV_ID":$("#APRV_ID").val()
	}	
	
	DE.ajax.call({async:false, "url":"metapublic/list", "data": param }, function(rsp){			
		if(rsp.status==="SUCCESS"){
			
			DE.jqgrid.reloadLocal($("#jqGridAnal"), rsp.data);
		}			
	});
	
	//라이센스 정보를 얻어온다.
	param={		
		"queryId":"credit_anatask.getUseToolLicense",
		"SB_OBJ_TYPE_ID":reqParam["SB_OBJ_TYPE_ID"],
		"SB_OBJ_ID":$("#SB_OBJ_ID").val(),
		"APRV_ID":$("#APRV_ID").val()
	}	
	
	DE.ajax.call({async:false, "url":"metapublic/list", "data": param }, function(rsp){			
		if(rsp.status==="SUCCESS"){	
			debugger;
			DE.jqgrid.reloadLocal($("#jqGridTool"), rsp.data);
			
			
			var ids=$("#jqGridTool").jqGrid('getDataIDs');
			var tdata=$("#jqGridTool").getRowData();
			for(var i=0;i<tdata.length;i++){
				var data=tdata[i];
				
				if(data['CHK']=="true"){
					//$("#jqGridTool").find('input[type=checkbox]').prop('checked',true);
					$("#jqGridTool").find('#'+ids[i]+' input[type=checkbox]').prop('disabled', true);
				}else{							
					//$("#jqGridTool").find('input[type=checkbox]').prop('checked',false);
					
				}
				
			}
			
		}			
	});	
}



//차후 승인요청이 완료되면 호출하는 
var fn_aprv_callback=function(data){	
	if(data.rstCd==='SUCC'){
		viewDetail();
	}else{
		
	}
}
