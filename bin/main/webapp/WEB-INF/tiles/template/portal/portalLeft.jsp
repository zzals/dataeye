<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

	<script type="text/javascript">
		$(document).ready(function(){
			$("#sidebar .sidebar-menu .sub-menu .sub a").on("click", function(e){
				$(".sidebar-menu").find(".active").removeClass("active");
				$(this).closest("li").addClass("active");
				$(this).closest(".sub-menu").find(":first").addClass("active");
				var $a = $(this);
				$("#container #main-content .wrapper").load( $(this).attr("target-url"), function() {
					/* $( "#content-area .page-header" ).text($a.text().trim()); */
				});
			})
		})
	</script>
</head>
<body>
	  <!-- **********************************************************************************************************************************************************
      MAIN SIDEBAR MENU
      *********************************************************************************************************************************************************** -->
      <!--sidebar start-->
      <aside>
          <div id="sidebar"  class="nav-collapse ">
              <!-- sidebar menu start-->
              <ul class="sidebar-menu" id="nav-accordion">
              
              	  <%-- <p class="centered"><a href="javascript:;"><img src="${pageContext.request.contextPath}/resources/assets/img/cjsxowls.jpg" class="img-circle" width="60"></a></p> --%>
              	  <h5 class="centered">Administrator</h5>
              	  	
                  <li class="sub-menu">
                      <a class="active" href="javascript:;" >
                          <i class="fa fa-cogs"></i>
                          <span><spring:message code="danial.app.sched.title" text="스케줄러" /></span>
                      </a>
                      <ul class="sub">
                          <li class="active"><a href="javascript:;" target-url="../sched/list"><spring:message code="danial.app.sched.list.title" text="스케줄 목록" /></a></li>
                          <li><a href="javascript:;" target-url="../sched/history"><spring:message code="danial.app.sched.history.title" text="실행  이력" /></a></li>
                      </ul>
                  </li>
                  <li class="sub-menu">
                      <a href="javascript:;" >
                          <i class="fa fa-book"></i>
                          <span><spring:message code="danial.app.kettle.title" text="Kettle" /></span>
                      </a>
                      <ul class="sub">
                          <li><a href="javascript:;" target-url="../kettle/list"><spring:message code="danial.app.kettle.job.list.title" text="Job 목록" /></a></li>
                          <li><a href="javascript:;" target-url="../kettle/joblog"><spring:message code="danial.app.kettle.job.log.title" text="Job 로그" /></a></li>
                          <li><a href="javascript:;" target-url="../kettle/transformationlog"><spring:message code="danial.app.kettle.trans.log.title" text="Transformation 로그" /></a></li>
                          <li><a href="javascript:;" target-url="../kettle/onetoone"><spring:message code="danial.app.kettle.job.onetoone.title" text="1:1 Job 생성" /></a></li>
                      </ul>
                  </li>
              </ul>
              <!-- sidebar menu end-->
          </div>
      </aside>
      <!--sidebar end-->
		    
</body>
</html>
