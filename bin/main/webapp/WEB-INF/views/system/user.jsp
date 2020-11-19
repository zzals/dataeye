<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title><spring:message code="app.title" text="DateEye" /></title>
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
            <div class="collapse navbar-collapse">
                <div class="navbar-form navbar-left">
                    <div class="form-group">
                        <select id="searchKey" class="form-control">
                            <option value="USER_ID">사용자ID</option>
                            <option value="USER_NM">사용자명</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <input id="searchValue" class="form-control" placeholder="Search">
                    </div>
                    <button type="submit" id="btnSearch" class="btn btn-default">검색</button>
                </div>
            </div>
        </div>
    </nav>
    <div class="row">
        <div class="col-xs-12">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">사용자 목록</h3>
                    <div class="navbar-form navbar-right">
                        <button type="button" id="btnInitpwd" class="btn btn-default">비밀번호초기화</button>
						<button type="button" id="btnInsert" class="btn btn-default">등록</button>
						<button type="button" id="btnUpdate" class="btn btn-default">수정</button>
						<button type="button" id="btnRemove" class="btn btn-default">삭제</button>
					</div>
                </div>
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

<script src="<spring:url value="/app/system/pages/user.js"/>"></script>
</body>
</html>