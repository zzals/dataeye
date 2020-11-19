    var isReFresh = false;
$(document).ready(function () {
	
	var reqParam = $("input#reqParam").data();
	
    var userGrpId;

    var colModel = [
    	{name:'CHK',index:'CHK', label:"<input type='checkbox' id='chkAll' name='chkAll' onclick='javascript:DE.jqgrid.checkAll(this, event);'/>", width:20, align:"center",
            sortable:false,
            formatter: 'checkbox',
            formatoptions: {disabled:false},
            edittype:'checkbox',
            editoptions:{value:"true:false"},
            editable:true
        },
        
        { index: 'PRIV_RCVR_GBN', name: 'PRIV_RCVR_GBN', label: '사용자 구분', width: 80, align: 'center'},
		{ index: 'PRIV_RCVR_ID', name: 'PRIV_RCVR_ID', label: 'ID', width: 100, align: 'center'},        
		{ index: 'NM', name: 'NM', label: '명칭', width: 100, align: 'center'},
        { index: 'PRIV_OPER_GBN', name: 'PRIV_OPER_GBN', label: '권한구분', width: 100, align: 'center'}        
    ];
    var opts = {
		"colModel":colModel, 
		pager:"#jqGridPager",
		multiselect: false,
		loadonce: true,
		isPaging: false,
		scroll: 1,
		autowidth:true,
		height:260,
		shrinkToFit: true,
        onSelectRow : function (rowid, status, e) {
        	var $radio = $('input[type=radio]', $("#"+rowid));
            $radio.prop('checked', 'checked');
            $("#"+rowid).addClass("success");
            
        	var rowData = $(this).getRowData(rowid);
            userGrpId = rowData.USER_GRP_ID;
            gridDetailList(userGrpId);
        },
        gridComplete: function() {
            DE.jqgrid.checkedHandler($(this));
        }
    };

    var navOpts = {
        navOptions: {
            add: false,
            del: false,
            edit: false,
            refresh: false,
            search: false,
            view: false,
            cloneToTop: false
        }
    };

    var $grid = DE.jqgrid.render($("#jqGrid"), opts);
    DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);

    var init = function () {    	
    		var postData = {
    			"queryId":"admin.getAuthList",
    			   objTypeId: reqParam['objTypeId'],
                   objId:  reqParam['objId']    			
    		};
    		DE.jqgrid.reload($("#jqGrid"), postData);
    		
    		$(".table-responsive").css("overflow-x","hidden");	
    		

    };
    
    $("button#btnUserAdd").on("click", function (e) {
    	
    	DE.ui.open.popup(
            "view",
            ['userRoleRel'],
            {
                viewname: 'common/metacore/user/addAuthList',
                objTypeId: reqParam['objTypeId'],
                objId:  reqParam['objId'],
                mode: 'R'
            },
            null
        );
    });
    
    $("button#btnRemoveAttr").on("click", function (e) {
    	DE.box.confirm("삭제 하시겠습니까?", function (b) {
            if (b === true) {
    	
    	 var data = $("#jqGrid").jqGrid("getGridParam", "data");
         var checkedData = [];
         $.each(data, function(index, value){
             if (value["CHK"] === "true") {
                 checkedData.push(value["PRIV_RCVR_ID"]);
             }
         });

         if (checkedData.length === 0) {
         	DE.box.alert(DE.i18n.prop("common.message.selected.none"));
 			return;
         }
         
         var postData = {
             "objTypeId": reqParam["objTypeId"],
             "objId": reqParam["objId"],
             "delInfo": checkedData
         };

         var saveComplete = function(rsp) {
             if ("SUCCESS" === rsp["status"]) {
                 DE.box.alert("삭제 되었습니다.", function () {
                	  init();
					});
             }
         };
         DE.ajax.call({url:"system/delObjAuth", data:postData}, saveComplete);
            }
        });
    });
    
    init();
    $(window).trigger("resize");
});


function reload() {
	var postData = {
			"queryId":"admin.getAuthList",
			   objTypeId: reqParam['objTypeId'],
               objId:  reqParam['objId']    			
		};
		DE.jqgrid.reload($("#jqGrid"), postData);
}