$(document).ready(function() {
	 var tabIndex =0;
	 var menuId = "";
	var colModel = [
		 {name:'MENU_ID',index:'MENU_ID',  label: '메뉴ID',  width:150, align:'center', hidden:true, key:true},
			{name:'MENU_NM',index:'MENU_NM',  label: '메뉴명', width:250, align: 'left'},
			{name:'MENU_TYPE',index:'MENU_TYPE',  label: '메뉴유형',  width:80, align: 'left'},
			{name:'PGM_NM',index:'PGM_NM',  label: '프로그램명', width:200, align: 'left'},
	        {name:'USE_YN',index:'USE_YN',  label: '사용여부', width:80, align:"center"},
	        {name:'ICON_CLASS',index:'ICON_CLASS', label: 'ICON & CSS',  width:250, align:"left"},
	        {name:'SORT_NO',index:'SORT_NO', label: '정렬',  width:80, align:"left"},
	        {name:'UP_MENU_ID',index:'UP_MENU_ID', label: 'UP_MENU_ID',  hidden:true},
	        {name:'PGM_ID',index:'PGM_ID',  label: 'PGM_ID', hidden:true},
	        {name:'DEPTH',index:'DEPTH',  label: 'DEPTH', hidden:true},
	        {name:'CRET_DT',index:'CRET_DT',  label: 'CRET_DT', hidden:true},
			{name:'CRETR_ID',index:'CRETR_ID', label: 'CRETR_ID',  hidden:true},
			{name:'CHG_DT',index:'CHG_DT', label: 'CHG_DT',  hidden:true},
			{name:'CHGR_ID',index:'CHGR_ID', label: 'CHGR_ID',  hidden:true}	
    ];

	var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		editurl: 'atr/process',
	 	autowidth: true,
		shrinkToFit: true,
		altRows:false,
		scroll:false,
        rownumbers: false,
        loadonce: false,
        height:330,
        viewrecords: false,
        sortable: false,
        treeGridModel: 'adjacency',
        treeReader : {
            level_field: "DEPTH",
            parent_id_field: "UP_MENU_ID",
            leaf_field: "IS_LEAF",
            expanded_field: "IS_EXPANDED"
        },
     //   treeGrid: true,
        ExpandColumn : 'MENU_NM',
        onSelectRow : function (id){
        	var rowData = $(this).getRowData(id);
        	menuId = rowData.MENU_ID;
        	viewAuthList();
        }
	};
	
	var navOpts = {
			navOptions:{
				add:false,
				del:false,
				edit:false,
				refresh:false,
				search:true,
				view:false,
				cloneToTop:false,
			}
	};
	
	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);

	var colModel2 = [
			{name:'USER_GRP_ID',index:'USER_GRP_ID',  label: '사용자 그룹ID', width:100, align: 'left'},
			{name:'USER_GRP_NM',index:'USER_GRP_NM',  label: '사용자그룹명',  width:100, align: 'left'},
			{name:'C_AUTH',index:'C_AUTH',  label: '등록', width:50, align: 'center'},
	        {name:'R_AUTH',index:'R_AUTH',  label: '읽기', width:50, align:"center"},
	        {name:'U_AUTH',index:'U_AUTH', label: '수정',  width:50, align:"center"},
	        {name:'D_AUTH',index:'D_AUTH', label: '삭제',  width:50, align:"center"},
	        {name:'E_AUTH',index:'E_AUTH', label: '실행',  width:50, align:"center"}	
   ];

	var opts2 = {
		"colModel":colModel2, 
		pager:"#jqGridPager",
		editurl: 'atr/process',
	 	autowidth: true,
		shrinkToFit: true,
		altRows:false,
		scroll:false,
       rownumbers: false,
       multiselect:true,
       loadonce: false,
       height:200,
       viewrecords: false,
       sortable: false,   
	};
	
	var navOpts2 = {
			navOptions:{
				add:false,
				del:false,
				edit:false,
				refresh:false,
				search:true,
				view:false,
				cloneToTop:false,
		  }
	};
	
	var $grid2 = DE.jqgrid.render($("#jqGrid2"), opts2);
	DE.jqgrid.navGrid($("#jqGrid2"), $("#jqGridPager2"), navOpts2);
	
	var colModel3 = [
			{name:'USER_ID',index:'USER_ID',  label: '사용자 ID', width:100, align: 'left'},
			{name:'USER_NM',index:'USER_NM',  label: '사용자명',  width:100, align: 'left'},
			{name:'C_AUTH',index:'C_AUTH',  label: '등록', width:50, align: 'center'},
	        {name:'R_AUTH',index:'R_AUTH',  label: '읽기', width:50, align:"center"},
	        {name:'U_AUTH',index:'U_AUTH', label: '수정',  width:50, align:"center"},
	        {name:'D_AUTH',index:'D_AUTH', label: '삭제',  width:50, align:"center"},
	        {name:'E_AUTH',index:'E_AUTH', label: '실행',  width:50, align:"center"}	
   ];

	var opts3 = {
		"colModel":colModel3, 
		pager:"#jqGridPager",
		editurl: 'atr/process',
	 	autowidth: true,
		shrinkToFit: true,
		altRows:false,
		scroll:false,
		multiselect:true,
       rownumbers: false,
       loadonce: false,
       height:200,
       viewrecords: false,
       sortable: false,   
	};
	
	var navOpts3 = {
			navOptions:{
				add:false,
				del:false,
				edit:false,
				refresh:false,
				search:true,
				view:false,
				cloneToTop:false,
			}
	};
	
	var $grid3 = DE.jqgrid.render($("#jqGrid3"), opts3);
	DE.jqgrid.navGrid($("#jqGrid3"), $("#jqGridPager3"), navOpts3);
		
	$("button#btnSearch").on("click", function(e) {
		var postData = {
			"queryId":"system.user.getUserMenu",
			"searchKey":$("#searchValue").attr("de-search-key"),
			"searchValue":$("#searchValue").val()
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
	});
	
	$("button#btnDel").on("click", function(e) {
		if(tabIndex == 0) {
			var privRcvrId = "";
			var isFirst = true;
			var ids = $("#jqGrid2").jqGrid('getDataIDs'); //체크된 row id들을 배열로 반환
		    for(var i=0; i<ids.length; i++){
		    	if($("input:checkbox[id='jqg_jqGrid2_"+ids[i]+"']").is(":checked")){ //checkbox checked 여부 판단
			        var rowObject = $("#jqGrid2").getRowData(ids[i]); //체크된 id의 row 데이터 정보를 Object 형태로 반환
			        var value = rowObject.USER_GRP_ID; //Obejcy key값이 name인 value값 반환
			        if(isFirst) {
			        	privRcvrId = value;
			        	isFirst = false;
			        } else {
			        	privRcvrId = privRcvrId + "#" + value;
			        }
		    	}
		    }
		    if(privRcvrId == "") {
		    	DE.box.alert("삭제할 항목을 선택해주세요.");
		    }else {
		    	DE.box.confirm("삭제 하시겠습니까?", function (b) {
                	if (b === true) {
                		DE.ajax.call({url:"user/delMenuGrp", data:{"privRcvrId":privRcvrId, "menuId":menuId,"privRcvrGbn":"G"}}, function(){viewAuthList();});
                		DE.box.alert("삭제 되었습니다.");
                	}
                });
		    }
		}
		if(tabIndex == 1) {
			var privRcvrId = "";
			var isFirst = true;
			var ids = $("#jqGrid3").jqGrid('getDataIDs'); //체크된 row id들을 배열로 반환
		    for(var i=0; i<ids.length; i++){
		    	if($("input:checkbox[id='jqg_jqGrid3_"+ids[i]+"']").is(":checked")){ //checkbox checked 여부 판단
			        var rowObject = $("#jqGrid3").getRowData(ids[i]); //체크된 id의 row 데이터 정보를 Object 형태로 반환
			        var value = rowObject.USER_ID; //Obejcy key값이 name인 value값 반환
			        if(isFirst) {
			        	privRcvrId = value;
			        	isFirst = false;
			        } else {
			        	privRcvrId = privRcvrId + "#" + value;
			        }
		    	}
		    }
		    
		    if(privRcvrId == "") {
		    	DE.box.alert("삭제할 항목을 선택해주세요.");
		    }else {
		    	DE.box.confirm("삭제 하시겠습니까?", function (b) {
                	if (b === true) {
                		 DE.ajax.call({url:"user/delMenuGrp", data:{"privRcvrId":privRcvrId, "menuId":menuId,"privRcvrGbn":"U"}}, function(){viewAuthList();});
                		DE.box.alert("삭제 되었습니다.");
                	}
                });
		    }
		    
		   
		}

	});
	
	
	$("button#btnAdd").on("click", function(e) {
		var passChgDialog = $("<div id='passChg-form' title='메뉴 그룹 추가'/>").load("userMenuAddGrp", {viewId:"userMenuAddGrp"}).appendTo('body').dialog({
            autoOpen: false,
            height: 250,
            width: 400,
            modal: true,
            buttons: {
                "추가하기": function() {
                    if (confirm("추가 하시겠습니까?")) {
                    }
                },
                "닫기": function() {
                    passChgDialog.dialog("close");
                }
            },
            close: function() {
                passChgDialog.remove();
            }
        });
        passChgDialog.dialog( "open" );
	});

	$(window).resize(function () {
	    var outerwidth = $('.box-body').width();
        $('#jqGrid').setGridWidth(outerwidth); // setGridWidth method sets a new width to the grid dynamically
        $('#jqGrid2').setGridWidth(outerwidth); // setGridWidth method sets a new width to the grid dynamically
        $('#jqGrid3').setGridWidth(outerwidth); // setGridWidth method sets a new width to the grid dynamically
	});
	
	var init = function() {
		$(window).trigger("resize");
		$("button#btnSearch").trigger("click");
	};
	init();
	
	var viewAuthList = function() {
		
		if(tabIndex == 0) {
			var postData2 = {
					"queryId":"system.user.getUserMenuGrp",
					"menuId":menuId
				};
			
				DE.jqgrid.reload($("#jqGrid2"), postData2);
				
		} else if(tabIndex == 1) {
			var postData3 = {
					"queryId":"system.user.getUserMenuUsr",
					"menuId":menuId
				};			
				DE.jqgrid.reload($("#jqGrid3"), postData3);
		}
	};
	
	$("#tapMain li").click(function (index) {
		tabIndex = $(this).index();
		 viewAuthList();		 
	});
});

function reloadView(){	
	alert("reload");
	viewAuthList();
}
