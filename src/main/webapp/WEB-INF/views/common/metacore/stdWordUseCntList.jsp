<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><spring:message code="app.title" text="DateEye" /></title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<c:import url="/deresources/commonlib/popup_css" />
	<c:import url="/deresources/commonlib/js" />
    
</head>
<body>
<!-- UI Object -->
<div id="container" de-tag="object-info-pop">
    <!-- HEADER -->
    <div id="header">
        <div class="popup_Title_Area">
            <div class="popup_title">표준단어 사용현황</div>
        </div>
    </div>
    <!-- //HEADER -->

    <div>
	 <table id="jqGrid" de-role="grid"></table>
	   <div id="jqGridPager" de-role="grid-pager"></div>
    </div>
    <span class="pop_close"><button type="button" class="close" onclick="window.close()">x</button></span>
</div>	

<script type="text/javascript">
    $(document).ready(function (){
    	
    	var reqParam = JSON.parse('${data}');
    	var colModel = [
    		{name : "OBJ_TYPE_ID",index : "OBJ_TYPE_ID" ,label: "OBJ_TYPE_ID", width : 150 , sortable:false,hidden:true},
    		{name : "OBJ_ID",index : "OBJ_ID" ,label: "OBJ_ID", width : 150 , sortable:false,hidden:true},		
    		{ index:'OBJ_NM', name: 'OBJ_NM', label: '표준용어명', width: 150, align:'left', 
    	    	formatter: "dynamicLink", 
    	    	formatoptions: {
    	    		onClick: function (rowid, irow, icol, cellvalue, e) {
    	    			var rowData = $(this).jqGrid("getRowData", rowid);
    	    			DE.ui.open.popup(
        					"view",
        					[rowData["OBJ_TYPE_ID"], rowData["OBJ_ID"]],
        					{
        						viewname:'common/metacore/objectInfoTab',
        						objTypeId:rowData["OBJ_TYPE_ID"],
        						objId:rowData["OBJ_ID"],
        						mode:'R'
        					},
        					null
        				);
    	    		}
    	    	}
          	},      	
    		{name : "OBJ_ENG_ABBR_NM", index : "OBJ_ENG_ABBR_NM" ,label: "영문약어명", width : 150 ,sortable:false, align:"center"},
    		{name : "DMN_NM", index : "DMN_NM" ,label: "도메인명", width : 150 ,sortable:false, align:"center"},    		
    		{name : "CRETR_NM", index : "CRETR_NM" ,label: "등록자", width : 150 ,sortable:false, align:"center"}			
        ];

    	var opts = {
    			"colModel":colModel, 
    			pager:"#jqGridPager",
    			editurl: 'atr/process',
    			toppager:false,
    			autowidth: true,
    			loadonce: true,				
    			scroll:-1,
    			isPaging:false,
    			height:550
    		};

    	var navOpts = {
    			navOptions:{
    				add:false,
    				del:false,
    				edit:false,
    				refresh:false,
    				search:false,
    				view:false,
    				cloneToTop:false
    			},
    		editOptions:{
    			editCaption:"속성 수정",
    	        width: 500,
    	        beforeShowForm: function(form, oper) { 
    	        	if (oper === "add") {
    	        		
    	        	} else if (oper === "edit") {
    	        		$('#ATR_ID', form).prop('disabled', true); 
    	        	}
    	        }
    		},
    		addOptions:{
    			addCaption:"속성 등록",
    			editCaption:"속성 수정",
    	        width: 500,
    	        beforeShowForm: function(form, oper) { 
    	        	return false;
    	        	if (oper === "add") {
    	        		
    	        	} else if (oper === "edit") {
    	        		$('#ATR_ID', form).prop('disabled', true); 
    	        	}
    	        }
    		},
    		delOptions:{
    			caption:"속성 삭제",
    			width: 500,
    			onclickSubmit:function(params, rowid){
    				var rowData = $(this).jqGrid("getRowData", rowid)
    				return {"APP_ID":rowData["APP_ID"]};
    			}
    		},
    		searchOptions:{},
    		viewOptions:{
    			caption: "속성 상세",
    			bClose: "닫기",
    			beforeShowForm: function(form) {
    				var val = $("#v_SQL_SBST span", form).html();
    	            $("#v_SQL_SBST span", form).remove();
    	            $("<textarea>").appendTo("#v_SQL_SBST", form).val(val);
    	        },
    	        afterclickPgButtons: function(whichbutton, form, rowid) {
    	        	var rowData = $(this).jqGrid("getRowData", rowid);
    				$("#v_SQL_SBST textarea", form).val(rowData["SQL_SBST"]);
    	        }
    		}
    	};
    	
    	var $grid = DE.jqgrid.render($("#jqGrid"), opts);
    	DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);
    	
    	
    	var init = function() {
    		var postData = {
    				"queryId":"portal_biz.getStdWordUseCntList",
    				"objTypeId":reqParam.objTypeId ,
    				"objId":reqParam.objId 
    			};
    			DE.jqgrid.reload($("#jqGrid"), postData);
    	};
    	
    	init();

    });
</script>
</body>
</html>