$(document).ready(function() {
	var reqParam = $("input#reqParam").data();	
	
	
	var lastSel;
		
	
	var colModel = [		
	    /*{ index:'CHK', name:'CHK', label:"선택", width:60, align:"center", sortable:false, formatter: 'customRadio', fixed:true},*/	    
	    { index:'USER_NM', name: 'USER_NM', label: '이름', width: 100, align:'center', editable: false,
	    	formatter: "dynamicLink", 
	    	formatoptions: {
	    		onClick: function (rowid, irow, icol, cellvalue, e) {
	    			var rowData = $gridAnal.jqGrid("getRowData", rowid);	    			
	    			
	    			if(opener){
	    				opener.addUser(rowData['USER_ID'], rowData['USER_NM'])
	    			}
	    				    			
	    		}
	    	}
	    },
      	{ index:'USER_ID', name: 'USER_ID', label: 'ID', width: 100, align:'left', editable: false}
    ];

	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		autowidth:true,		
		height:100,
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
	
	var $gridAnal = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);
	
	//검색
	$("#btnSearch").on("click", function(){
		searchUser();
	})
	
	function searchUser(){
		//분석과제를 조회한다.
		var param={		
			"queryId":"credit_anatask.getUser",
			"SEARCH_VAL":$("#SEARCH_VAL").val()
		}	
		
		DE.ajax.call({"url":"metapublic/list", "data": param }, function(rsp){			
			if(rsp.status==="SUCCESS"){					
				DE.jqgrid.reloadLocal($("#jqGrid"), rsp.data);			
			}			
		});		
	}
	
	
	
	
	var init = function() {
		
	};
	init();
});