<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><spring:message code="app.title" text="DateEye" /></title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <c:import url="../../common/import/common_css.jsp"/>
    <link type="text/css" rel="stylesheet" href="<spring:url value="/assets/javascripts-lib/datetimepicker/bootstrap-datetimepicker.min.css"/>">
    <link type="text/css" rel="stylesheet" href="<spring:url value="/assets/javascripts-lib/selectpicker/bootstrap-select.min.css"/>">

    <c:import url="../../common/import/common_js.jsp"/>
</head>
<body>
<!-- UI Object -->
<div id="wrap">
    <!-- Container -->
    <div id="container">
        <div style="width: 49%;float: left;">
            <div class="popup_subtitle_Area">
                <div class="subtitle_title">Lineage 분석</div>
            </div>
            <div id="lineage_result" class="popup_Table_Area">
                <div class="popup_table_TOP_Area">
                    <div id="resultCnt" class="popup_table_TOP_comment"><span class="num">0</span> 건이 검색되었습니다.</div>
                    <div class="popup_table_TOP_Btn">
                        <a id="excelExport" class="popup_button_small popup_whitishBtn" href="#"><span class="popup_fontawesome_Btn fa-th-list"></span>엑셀</a>
                    </div>
                </div>
                <div role="grid-area">
                    <table id="lineage_list"></table>
                </div>
            </div>
        </div>
        <div style="width: 49%;float: right;">
            <div class="popup_subtitle_Area">
                <div class="subtitle_title">Impact 분석</div>
            </div>
            <div id="impact_result" class="popup_Table_Area">
                <div class="popup_table_TOP_Area">
                    <div id="resultCnt" class="popup_table_TOP_comment"><span class="num">0</span> 건이 검색되었습니다.</div>
                    <div class="popup_table_TOP_Btn">
                        <a id="flowView" class="popup_button_small popup_whitishBtn" href="#"><span class="popup_fontawesome_Btn fa-random"></span>흐름분석</a>
                        <a id="excelExport" class="popup_button_small popup_whitishBtn" href="#"><span class="popup_fontawesome_Btn fa-th-list"></span>엑셀</a>
                    </div>
                </div>
                <div role="grid-area">
                    <table id="impact_list"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function (){
        $.receiveMessage(function(e) {
            var pHeight = $(document.body).outerHeight(true);
            console.log(pHeight);
            if (e.data === 'getResize' && pHeight !== 0) {
                $.postMessage({action: 'setResize', height: pHeight}, '*', window.parent);
            }
        });

        $(document.body).on('DOMSubtreeModified', function(e) {
            var pHeight = $(document.body).outerHeight(true);
            console.log(pHeight);
            if(pHeight !== 0) {
                $.postMessage({action: 'setResize', height: pHeight}, '*', window.parent);
            }
        });

        console.log("objRImpactView");
        var parameter = JSON.parse('${data}');

        var colModels = [
            {name:'ID', index:'ID', label:'ID', width:100, hidden:true, key:true},
            {name:'OBJ_NM', index:'OBJ_NM', label:'명칭', width:200, classes:'pointer', sortable:false},
            {name:'OBJ_TYPE_NM', index:'OBJ_TYPE_NM', label:'유형명', width:150, align:"left", sortable:false},
            {name:'OBJ_TYPE_ID', index:'OBJ_TYPE_ID', width:1, hidden:true},
            {name:'OBJ_ID', index:'OBJ_ID', width:1, hidden:true},
            {name:'LVL', index:'LVL', width:1, hidden:true}
        ];

        var opts = {
            colModel: colModels,
            pager: false,
            editurl: 'atr/process',
            navOptions: {
                cloneToTop: false
            },
            editOptions: {
                addCaption:"속성 등록",
                editCaption:"속성 수정",
                width: 500,
                beforeShowForm: function(form, oper) {
                    debugger;
                    if (oper === "add") {

                    } else if (oper === "edit") {
                        $('#ATR_ID', form).prop('disabled', true);
                    }
                }
            },
            addOptions: {

            },
            delOptions: {
                caption:"속성 삭제",
                width: 500,
                onclickSubmit:function(params, rowid){
                    var rowData = $(this).jqGrid("getRowData", rowid)
                    return {"APP_ID":rowData["APP_ID"]};
                }
            },
            searchOptions: {

            },
            viewOptions: {
                caption: "속성 상세",
                bClose: "닫기",
                beforeShowForm: function(form) {
                    var val = $("#v_SQL_SBST span", form).html();
                    $("#v_SQL_SBST span", form).remove();
                    $("<textarea>").appendTo("#v_SQL_SBST", form).val(val);
                },
                afterclickPgButtons: function(whichbutton, form, rowid) {
                    var rowData = $(this).jqGrid("getRowData", rowid);
                    console.log(rowData);
                    DE.ui.open.popup(
                            "view",
                            [rowData.OBJ_TYPE_ID, rowData.OBJ_ID],
                            {
                                viewname:'common/metacore/objectInfoTab',
                                objTypeId:'030103L',
                                objId:'030103L_000000001',
                                mode:'R'
                            },
                            null
                    );
                    $("#v_SQL_SBST textarea", form).val(rowData["SQL_SBST"]);
                }
            }
        };

        var $ligrid = DE.jqgrid.render($("#lineage_list"), opts);
        DE.jqgrid.navGrid($("#lineage_list"), opts.pager, opts);

        var $imgrid = DE.jqgrid.render($("#impact_list"), opts);
        DE.jqgrid.navGrid($("#impact_list"), opts.pager, opts);
/*
        var $lineageGrid = $("#lineage_list");
        $lineageGrid.jqGrid({
            postData:{
                objTypeId:parameter.objTypeId,
                objId:parameter.objId
            },
            url:"commonCore/objRLineage",
            datatype:"json",
            colNames:colNames,
            colModel:colModels,
            height: 445,
            autowidth: true,
            shrinkToFit: true,
            altRows:true,
            scroll : false,
            loadonce : false,
            rownumbers:false,
            viewrecords: false,
            sortable: false,
            onCellSelect: function (rowid, index, contents, event) {
                var colModel = $(this).jqGrid('getGridParam','colModel');
                if (colModel[index].name == "OBJ_NM") {
                    var row = $(this).getLocalRow(rowid);

                    UI.open.popup(
                            "goPage",
                            [row.OBJ_TYPE_ID, row.OBJ_ID],
                            {
                                viewname:'object/objectInfoTab',
                                objTypeId:row.OBJ_TYPE_ID,
                                objId:row.OBJ_ID,
                                mode:'R'
                            },
                            null
                    );
                }
            },
            treeGridModel: 'adjacency',
            treeReader : {
                level_field: "LVL",
                parent_id_field: "PARENT_ID",
                leaf_field: "ISLEAF",
                expanded_field: "EXPANDED"
            },
            treeGrid: true,
            ExpandColumn : 'OBJ_NM',
            ExpandColClick : false,
            gridComplete: function () {
                $("[aria-describedby=lineage_list_OBJ_NM]").css("color", UI.GRID_LINK_FONT_COLOR);
            }
        });

        var $impactGrid = $("#impact_list");
        $impactGrid.jqGrid({
            postData:{
                objTypeId:parameter.objTypeId,
                objId:parameter.objId
            },
            url:"commonCore/objRImpact",
            datatype:"json",
            colNames:colNames,
            colModel:colModels,
            height: 445,
            autowidth: true,
            shrinkToFit: true,
            altRows:true,
            scroll : false,
            loadonce : false,
            rownumbers:false,
            viewrecords: false,
            sortable: false,
            onCellSelect: function (rowid, index, contents, event) {
                var colModel = $(this).jqGrid('getGridParam','colModel');
                if (colModel[index].name == "OBJ_NM") {
                    var row = $(this).getLocalRow(rowid);

                    UI.open.popup(
                            "goPage",
                            [row.OBJ_TYPE_ID, row.OBJ_ID],
                            {
                                viewname:'object/objectInfoTab',
                                objTypeId:row.OBJ_TYPE_ID,
                                objId:row.OBJ_ID,
                                mode:'R'
                            },
                            null
                    );
                }
            },
            treeGridModel: 'adjacency',
            treeReader : {
                level_field: "LVL",
                parent_id_field: "PARENT_ID",
                leaf_field: "ISLEAF",
                expanded_field: "EXPANDED"
            },
            treeGrid: true,
            ExpandColumn : 'OBJ_NM',
            ExpandColClick : false,
            gridComplete: function () {
                $("[aria-describedby=lineage_list_OBJ_NM]").css("color", UI.GRID_LINK_FONT_COLOR);
            }
        });

        var orgExpandNode = $.fn.jqGrid.expandNode;
        var orgCollapseNode = $.fn.jqGrid.collapseNode;
        $.jgrid.extend({
            expandNode: function (rc) {
                var postData = {
                    objTypeId : rc.OBJ_TYPE_ID,
                    objId : rc.OBJ_ID,
                    lvl : rc.LVL+1,
                    parentId : rc.ID
                };
                var id = rc["ID"];
                var gridId = $("[id='"+id+"']").closest("table").prop("id");
                var $grid = $("table#"+gridId);
                $grid.jqGrid("setGridParam", {"postData":postData});

                return orgExpandNode.call(this, rc);
            },
            collapseNode: function (rc) {
                return orgCollapseNode.call(this, rc);
            }
        });

        $("a#flowView").on("click", function(e){
            var diagramData = {};
            var nodeDataArray = [];
            var linkDataArray = [];

            var lineageData = $(".ui-jqgrid-btable").eq(0).jqGrid("getGridParam", "data");
            for(var i=0; i<lineageData.length; i++) {
                var link = {};
                if (lineageData[i].PARENT_PATH == undefined) {
                    link.from = "DOWN"+lineageData[i].PATH;
                    link.to = "ROOT";
                } else {
                    link.from = "DOWN"+lineageData[i].PATH;
                    link.to = "DOWN"+lineageData[i].PARENT_PATH;
                }
                lineageData[i].key = "DOWN"+lineageData[i].PATH;
                linkDataArray.push(link);
            }
            var rootData = ObjInfoConfig.getData({
                objTypeId:parameter.objTypeId,
                objId:parameter.objId
            }).rows;
            rootData.PATH = "ROOT";
            rootData.key = "ROOT";

            var impactData = $(".ui-jqgrid-btable").eq(1).jqGrid("getGridParam", "data");
            for(var i=0; i<impactData.length; i++) {
                var link = {};
                if (impactData[i].PARENT_PATH == undefined) {
                    link.from = "ROOT";
                    link.to = "UP"+impactData[i].PATH;
                } else {
                    link.from = "UP"+impactData[i].PARENT_PATH;
                    link.to = "UP"+impactData[i].PATH;
                }
                impactData[i].key = "UP"+impactData[i].PATH;
                linkDataArray.push(link);
            }
            nodeDataArray.push.apply(nodeDataArray, lineageData);
            nodeDataArray.push.apply(nodeDataArray, impactData);
            nodeDataArray.push.apply(nodeDataArray, [rootData]);

            diagramData.nodeDataArray = nodeDataArray;
            diagramData.linkDataArray = linkDataArray;

            UI.open.popup("goPage", ["impactView", parameter.OBJ_TYPE_ID, parameter.OBJ_ID], {viewname:"object/flowView", title:"흐름 분석", data:diagramData}, "width=1300, height=800, toolbar=non, menubar=no");
        });
*/
    });
</script>
</body>
</html>