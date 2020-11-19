$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
	var lastSel = "";
	var colModel = [
		{ index:'ATR_ID_SEQ', name: 'ATR_ID_SEQ', label: '속성ID순번', width: 80, align:'center', fixed:true,editable : true},
   		{ index:'ATR_ID', name: 'ATR_ID', label: '속성ID', width: 60, align:'center', fixed:true},   		
   	    { index:'ATR_NM', name: 'ATR_NM', label: '속성명', width: 120, align:'left', fixed:true},
   	    { index:'SEL_ATR', name: 'SEL_ATR', label: '선택', width: 50, align:'left', fixed:true,
   	    			edittype:"button",editoptions: {value:"선택",
   	    			 dataEvents: [ 
 						{type: 'click', fn: function(e) {
 						    $("#main_list").hide({duration:500});
 						    $("#sub_list").show({duration:500});
 						    return; 						    
 						}}
 						]		 
   	    			},editable : true},
   	    
   		{ index:'ATR_ALIAS_NM', name: 'ATR_ALIAS_NM', label: '속성별칭명', width: 150, align:'left' ,fixed:true,editable : true},
        { index:'SORT_NO', name: 'SORT_NO', label: '정렬순서', width: 80, align:'right', fixed:true,editable : true},
   		{ index:'MULTI_ATR_YN', name: 'MULTI_ATR_YN', label: '멀티속성', width: 80, align:'center', fixed:true,editable : true,edittype:"select",editoptions: {value:"1:Y;2:N"}},
   		{ index:'CNCT_ATR_YN', name: 'CNCT_ATR_YN', label: '연결속성', width: 80, align:'center', fixed:true,editable : true,edittype:"select",editoptions: {value:"1:Y;2:N"}},
        { index:'MAND_YN', name: 'MAND_YN', label: '필수여부', width: 80, align:'center', fixed:true,editable : true,edittype:"select",editoptions: {value:"1:Y;2:N"}},
        { index:'ATR_ADM_TGT_YN', name: 'ATR_ADM_TGT_YN', label: '관리대상여부', width: 100, align:'center', fixed:true,editable : true,edittype:"select",editoptions: {value:"1:Y;2:N"}},
        { index:'DEGR_NO', name: 'DEGR_NO', label: '관리차수', width: 80, align:'right', fixed:true,editable : true},
        { index:'INDC_YN', name: 'INDC_YN', label: '디스플레이여부', width: 100, align:'center', fixed:true,editable : true,edittype:"select",editoptions: {value:"1:Y;2:N"}},
        { index:'FIND_TGT_NO', name: 'FIND_TGT_NO', label: '검색구분', width: 80, align:'center', fixed:true,editable : true},
        { index:'AVAIL_CHK_PGM_ID', name: 'AVAIL_CHK_PGM_ID', label: '체크PGM', width: 80, align:'center', fixed:true,editable : true},
        { index:'UI_COMP_WIDTH_RADIO_DISP', name: 'UI_COMP_WIDTH_RADIO_DISP', label: 'UI WIDTH', width: 80, align:'center', fixed:true,editable : true},
        { index:'UI_COMP_HEIGHT_PX_DISP', name: 'UI_COMP_HEIGHT_PX_DISP', label: 'UI HEIGHT', width: 80, align:'center', fixed:true,editable : true}
    ];


	var opts = {
			"colModel":colModel, 
			pager:"#jqGridPager",
			toppager: false,
		   	autowidth: true,
			shrinkToFit: true,
			editable: true,
			isPaging:false,
			onSelectRow : function (rowid, status, e) {
				if(rowid && rowid!==lastSel){ 
	                $(this).jqGrid('saveRow', lastSel, true, 'clientArray'); 
	                lastSel=rowid; 
	            }
				$("#jqGrid").jqGrid("editRow", rowid,true,"clientArray"); 
	        },
	        rownumbers: true
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
		
		var $grid = DE.jqgrid.render($("#jqGrid"), opts);
		DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);
    
		
		
		
		
		
		
		var colModel2 = [
		    { index:'ATR_ID', name: 'ATR_ID', label: '속성 ID', width: 100, align:'center', editable: true, editrules: {required: true, readonly: "readonly"}},
	      	{ index:'ATR_NM', name: 'ATR_NM', label: '속성명', width: 200, align:'left', editable: true,},
	      	{ index:'ATR_DESC', name: 'ATR_DESC', label: '설명', width: 310, align:'left', editable: true, edittype:'textarea'},
	      	{ index:'DATA_TYPE_NM', name: 'DATA_TYPE_NM', label: '데이터유형', width: 100, fixed: true, align:'center', editable: true, edittype:"select", editoptions: {value:DE.code.getGridEditOptions("SYS_DATATYP")}, editrules: {required: true}},
	        { index:'COL_LEN', name: 'COL_LEN', label: '컬럼길이', width: 100, fixed: true, align:'center', editable: true, editrules: {required: true}},
	        { index:'SQL_SBST', name: 'SQL_SBST', label: 'SQL', width: 100, fixed: true, align:'center', editable: true, edittype:"textarea", editoptions: {value:"Y:Y;N:N"}, editrules: {required: false}},
	        { index:'UI_COMP_NM', name: 'UI_COMP_NM', label: 'UI구분', width: 100, fixed: true, align:'center', editable: true, edittype:"select", editoptions: {value:DE.code.getGridEditOptions("SYS_UI")}, editrules: {required: true}},
	        { index:'UI_COMP_WIDTH_RADIO', name: 'UI_COMP_WIDTH_RADIO', label: 'UI Width', width: 100, fixed: true, align:'center', editable: true, edittype:"select", editoptions: {value:"6:50%;12:100%"}, editrules: {required: true}},
	        { index:'UI_COMP_HEIGHT_PX', name: 'UI_COMP_HEIGHT_PX', label: 'UI Height', width: 100, fixed: true, align:'center', editable: true, editrules: {required: true}, editoptions: { dataInit: function (elem) {  
	        }}}
	    ];


		var opts2 = {
				"colModel":colModel2, 
				pager:"#jqGridPager",
				toppager: false,
			   	autowidth: true,
				shrinkToFit: true,
				editable: true,
				isPaging:false,
				onSelectRow : function (rowid, status, e) {
					if(rowid && rowid!==lastSel){ 
		                $(this).jqGrid('saveRow', lastSel, true, 'clientArray'); 
		                lastSel=rowid; 
		            }
					$("#jqGrid").jqGrid("editRow", rowid,true,"clientArray"); 
		        },
		        rownumbers: true
			};
			
			var navOpts2 = {
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
			
			var $grid2 = DE.jqgrid.render($("#jqGrid2"), opts2);
		
		var init = function() {
			var postData = {
					"queryId":"objtype.getObjTypeAtrD",
					"objTypeId":reqParam["OBJ_TYPE_ID"]
				};
				DE.jqgrid.reload($("#jqGrid"), postData);
				
				var postData = {
						"queryId":"atr.getAtrM"
					};
					DE.jqgrid.reload($("#jqGrid2"), postData);
		};
		init();
		
		
		var makeNewRowId = function(rowids) {
			
			   return rowids.length + 1;
		};
		
		$("button#btnAddAttr").on("click", function(e) {
			var newRowid = makeNewRowId($("#jqGrid").jqGrid("getDataIDs"));
			var rowdata = {  id:newRowid,
							  ATR_NM:"",
							  SEL_ATR:""						 
			}
			$("#jqGrid").jqGrid("addRowData", newRowid, rowdata, 'last');			
			$("#jqGrid").jqGrid("editRow", newRowid,true,"clientArray"); 
			
			if(lastSel==""){
				lastSel=newRowid;
			} else {
				$("#jqGrid").jqGrid('saveRow', lastSel, true, 'clientArray'); 
                lastSel=newRowid; 
            }
			
			$("#jqGrid").jqGrid('setSelection', newRowid, true)
			
		});
		
		$("button#btnRemoveAttr").on("click", function(e) {
			 if($("#jqGrid").getInd(lastSel) < 9) {
		        	alert("기본 속성은 삭제할수 없습니다.");
		        } else {
		        	$("#jqGrid").jqGrid('delRowData',lastSel);
		        }
			
		});
		
		
		
		
        $("button#btnSelAttr").on("click", function(e) {
        	  var atrRowId =  $("#jqGrid2").jqGrid("getGridParam", "selrow");
  	        if (atrRowId == undefined) {
  	            alert("선택된 객체가 없습니다.");
  	            return;
  	        }
  	        var atrData = $("#jqGrid2").getLocalRow(atrRowId);
  	        var rowid =  $("#jqGrid").jqGrid("getGridParam", "selrow");
  	  
  	      
  	        $("#jqGrid").jqGrid("setCell", rowid, 'ATR_ID', atrData.ATR_ID);
  	        $("#jqGrid").jqGrid("setCell", rowid, 'ATR_NM', atrData.ATR_NM);
  	      lastSel=rowid; 
  	        $("button#btnCleAttr").trigger("click");
			
		});
        
		$("button#btnCleAttr").on("click", function(e) {
			 $("#main_list").show({duration:500}); 
			 $("#sub_list").hide({duration:500});
			
		});
		
		
		$("button#btnInsert").on("click", function(e) {
			$("#jqGrid").jqGrid('saveRow', lastSel, true, 'clientArray'); 
			var ids = $("#jqGrid").jqGrid('getDataIDs');
						
			for(var j = 0;j < ids.length;j++) {
				
				var checkData = $("#jqGrid").getRowData(ids[j]);
				if(checkData.ATR_ID_SEQ==null || checkData.ATR_ID_SEQ=="" || !$.isNumeric(checkData.ATR_ID_SEQ)) {	    		
	    			$("#jqGrid").jqGrid("editRow", ids[j],true,"clientArray"); 
	    			$("#jqGrid").jqGrid('setSelection', ids[j], true);
	    			lastSel =  ids[j];
	    			$("#" + ids[j]+"_ATR_ID_SEQ").animate({"backgroundColor": "red"}, 'slow');
	    			$("#" + ids[j]+"_ATR_ID_SEQ").animate({"backgroundColor": "white"}, 'slow');
	    			return;
	    		}
	    		if(checkData.SORT_NO==null || checkData.SORT_NO=="" || !$.isNumeric(checkData.SORT_NO)) { 
	    			$("#jqGrid").jqGrid("editRow", ids[j],true,"clientArray");
	    			$("#jqGrid").jqGrid('setSelection', ids[j], true);
	    			lastSel =  ids[j];	   

	    			$("#" + ids[j]+"_SORT_NO").animate({"backgroundColor": "red"}, 'slow');
	    			$("#" + ids[j]+"_SORT_NO").animate({"backgroundColor": "white"}, 'slow');
	    			return;
	    		}
				
			}
			
			 $("#jqGrid").jqGrid('saveRow', lastSel, true, 'clientArray');
		        if (confirm("저장 하시겠습니까?")) {
		            var data =  $("#jqGrid").jqGrid("getGridParam", "data");
		      
		            
		            var OBJ_TYPE_ID = reqParam["OBJ_TYPE_ID"];
		             	
		            var map = new Map();		          
		            
		            var list = new Array();
		            for(var i=0; i<data.length; i++) {
		            	if(data[i].ATR_ID!=null&&data[i].ATR_ID!="") {
				            	map = new Map();
				            	map.put("META_TYPE_CD", "O");
				            	map.put("OBJ_TYPE_ID",OBJ_TYPE_ID);
				            	map.put("ATR_ID_SEQ",data[i].ATR_ID_SEQ);
				            	map.put("ATR_ID",data[i].ATR_ID);
				            	map.put("ATR_NM",data[i].ATR_NM);
				            	map.put("ATR_ALIAS_NM",data[i].ATR_ALIAS_NM);
				            	map.put("SORT_NO",data[i].SORT_NO);
				            	map.put("MULTI_ATR_YN",data[i].MULTI_ATR_YN);
				            	map.put("CNCT_ATR_YN",data[i].CNCT_ATR_YN);
				            	map.put("MAND_YN",data[i].MAND_YN);
				            	map.put("ATR_ADM_TGT_YN",data[i].ATR_ADM_TGT_YN);
				            	map.put("DEGR_NO",data[i].DEGR_NO);
				            	map.put("INDC_YN",data[i].INDC_YN);
				            	map.put("FIND_TGT_NO",data[i].FIND_TGT_NO);
				            	if(data[i].AVAIL_CHK_PGM_ID==null){
									map.put("AVAIL_CHK_PGM_ID"," ");
								} else {
									map.put("AVAIL_CHK_PGM_ID",data[i].AVAIL_CHK_PGM_ID);	
								}
				            	
				            	map.put("UI_COMP_WIDTH_RADIO_DISP",data[i].UI_COMP_WIDTH_RADIO_DISP);
				            	map.put("UI_COMP_HEIGHT_PX_DISP",data[i].UI_COMP_HEIGHT_PX_DISP);
				            	list.push(map);
		            	}
		            }		            		          
		       };
		       
		       var callback = {
		   			success : function(response) {
		   				DE.box.alert(response["message"], function(){ 
		   						self.close();
		   				});
		   			},
		   			error : function(response) {
		   				DE.box.alert(response["responseJSON"]["message"]);
		   			}
		   		};
		       		       
		       var opts = {
		   			async:false,
		   			url : "admin/objtype?oper=atrMgr&OBJ_TYPE_ID=" + OBJ_TYPE_ID + "&META_TYPE_CD=O",
		   			data : "dataList=" + JSON.stringify(list)
		   			
		   		};
		   		
		   		DE.ajax.formSubmit(opts, callback.success, callback.error);
			
		});
		
		$("button#btnClose").on("click", function(e) {
			window.close();
		});
		
		
		
		
});
