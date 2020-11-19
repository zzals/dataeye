<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title><spring:message code="app.title" text="DateEye" /></title>
<style type="text/css">
 .search_Area2 {padding:5px; margin-bottom:10px;}
.Grid_Area2 .table_Type3 { border-collapse:collapse; }
.Grid_Area2 .table_Type3 th {width:50px;  }
.Grid_Area2 .table_Type3 td {padding-left:30px;}
.Grid_Area2 .table_Type3 td input { width:100px; }
.Grid_Area2 .table_Type3 td span.cal_btn {border:1px solid #ccc; background:#efefef; padding:5px 8px; cursor:pointer; font-size:12px; margin-left:-1px;}
</style>
    <script language="javascript">

  	$(function() {
  	    $( "#fromData" ).datepicker({
  	    	 dateFormat: 'yy-mm-dd' //Input Display Format 변경
                 ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
                 ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
                 ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
                 ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
                 ,buttonImageOnly: false //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
                 ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
                 ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
                 ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
                 ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
                 ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
                             
  	    });
  	  $('#fromData').datepicker('setDate', 'today');
  	 $('.ui-datepicker-trigger').hide();
  	});


  </script>
</head>
<body>
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1><small></small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class=""></i></a></li>
        <li class="active"></li>
    </ol>
</section>

<!-- Main content -->
<section class="content">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="collapsed navbar-toggle" data-toggle="collapse" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span> 
                </button>
                <a href="#" class="navbar-brand"></a>
            </div>
            <div class="search_Area2">
				<div class="Grid_Area2">
					<table class="table_Type3">
						<tr>
							<th>기준일자</th>
							<td><input type="text" id="fromData" name="fromData" placeholder=""><span class="cal_btn" onclick="$('#fromData').focus()">
							<i class="glyphicon glyphicon-calendar"></i></span></td>
							<td><button type="submit" id="btnSearch" class="btn btn-default">조회</button>
							</td>
						</tr>
					</table>
				</div>
			</div>	
        </div>
    </nav>
    <div class="row">
        <div class="col-xs-12">
            <div class="box">
                <!-- /.box-header -->
                <div class="box-body">
                    <table id="jqGrid" de-role="grid"></table>
                    <div id="jqGridPager" de-role="grid-pager"></div>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </div>
    </div>
</section>
<!-- /.content -->

<script src="<spring:url value="/app/system/pages/logMenu.js"/>"></script>
</body>
</html>