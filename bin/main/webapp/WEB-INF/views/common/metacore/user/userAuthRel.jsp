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
    <!-- HEADER -->
    <div id="header">
        <div class="popup_Title_Area">
            <div class="popup_title">사용자관계 등록</div>
        </div>
    </div>
    <!-- //HEADER -->

    <section class="content">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="collapse navbar-collapse">
                    <div class="navbar-header">
                        <button type="button" class="collapsed navbar-toggle" data-toggle="collapse" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a href="#" class="navbar-brand"></a>
                    </div>
                    <div class="navbar-form navbar-left">
                        <div class="form-group">
                            <select id="searchKey" class="form-control">
                                <option value="USER_GRP_ID">사용자 그룹 ID</option>
                                <option value="USER_GRP_NM">사용자 그룹명</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <input id="searchValue" class="form-control" placeholder="Search">
                        </div>
                        <button type="submit" id="btnSearch" class="btn btn-default">검색</button>
                    </div>
                    <div class="navbar-form navbar-right">
                        <button type="submit" id="btnSave" class="btn btn-primary"><i class="fa fa-floppy-o"></i> 저장</button>
                    </div>
                </div>
            </div>
        </nav>
        <div id="tabContentBody">
            <table id="jqGrid" de-role="grid"></table>
        </div>
    </section>

    <span class="pop_close"><button type="button" class="close" onclick="window.close()">x</button></span>

<script type="text/javascript">
$(document).ready(function (){

    var reqParam = JSON.parse('${data}');
    var colModel = [
        { name:'CHK',index:'CHK', label:"<input type='checkbox' id='chkAll' name='chkAll' onclick='javascript:DE.jqgrid.checkAll(this, event);'/>", width:55, align:"center",
            sortable:false,
            formatter: 'checkbox',
            formatoptions: {disabled:false},
            edittype:'checkbox',
            editoptions:{value:"true:false"},
            editable:true
        },
        { index: 'USER_GRP_ID', name: 'USER_GRP_ID', label: '사용자 그룹ID', width: 100, align: 'left', editable: true, editrules: {required: false} },
        { index: 'USER_GRP_NM', name: 'USER_GRP_NM', label: '사용자 그룹명', width: 100, align: 'left', editable: true, editrules: {required: false} },
        { index: 'USER_GRP_DESC', name: 'USER_GRP_DESC', label: '사용자 그룹설명', width: 100, align: 'left', editable: true, editrules: {required: false} },
        { index: 'USER_GRP_TYPE', name: 'USER_GRP_TYPE', label: '사용자 그룹유형', width: 100, align: 'left', editable: true, edittype: 'textarea' }
    ];

    var opts = {
        "colModel": colModel,
        pager: "#jqGridPager",
        isPaging:false,
        loadonce: true,
        multiselect: false,
        scroll:-1,
        autowidth:true,
        shrinkToFit: true,
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
            search: true,
            view: false,
            cloneToTop: false
        }
    };

    var $grid = DE.jqgrid.render($("#jqGrid"), opts);
    DE.jqgrid.navGrid($("#jqGrid"), $("#jqGridPager"), navOpts);

    $("button#btnSearch").on("click", function (e) {
        var postData = {
            "queryId": "system.user.getUserRoleForAuth",
            "userGrpId": reqParam["userGrpId"],
            "searchKey": $("#searchKey").val(),
            "searchValue": $("#searchValue").val()	
        };
        DE.jqgrid.reload($("#jqGrid"), postData);
    });

    $("input[id=searchValue]").on("keypress", function (e) {
        if (e.which === 13) {
        	$("button#btnSearch").trigger("click");
        }
    });

    $("button#btnSave").on("click", function(e) {
        DE.box.confirm("저장 하시겠습니까?", function (b) {
            if (b === true) {
                var data = $("#jqGrid").jqGrid("getGridParam", "data");
                var checkedData = [];
                $.each(data, function(index, value){
                    if (value["CHK"] === "true") {
                        checkedData.push(value["USER_GRP_ID"]);
                    }
                });
                
                var postData = {
                    "userGrpId": reqParam["userGrpId"],
                    "relUserGrpIds": checkedData
                };

                var saveComplete = function(rsp) {
                    if ("SUCCESS" === rsp["status"]) {
                        DE.box.alert("저장 되었습니다.", function () {
                            self.close();
                        });
                    }
                };
                DE.ajax.call({url:"system/grpR?oper=regist", data:postData}, saveComplete);
            }
        });

    });
    
    var init = function () {
        $("button#btnSearch").trigger("click");
    };
    init();
    $(window).trigger("resize");
});
</script>
</body>
</html>