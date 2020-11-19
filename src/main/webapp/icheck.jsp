<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/deresources/commonlib/css" />
<link rel="stylesheet" href="<spring:url value="/assets/stylesheets/dataeye-tsearch.css"/>">



	<div class="row">
		<div class="col-sm-12 col-md-12" style="text-align: center;font: 15px bold;color: #D6607E;">
			<div class="navbar-form" role="search">
	        	<div class="input-group col-sm-6 col-md-6">
	        		<div class="form-group">
	                  <div class="iradio_minimal-red checked" aria-checked="false" aria-disabled="false" style="position: relative;">
	                  	<input type="radio" name="r2" class="minimal-red" checked style="position: absolute; opacity: 0;">
	                  	<ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;">
	                  	</ins>
	                  </div>
	                  <div class="iradio_minimal-red" aria-checked="false" aria-disabled="false" style="position: relative;"><input type="radio" name="r2" class="minimal-red" style="position: absolute; opacity: 0;"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins></div>
	                  <div class="iradio_minimal-red disabled" aria-checked="false" aria-disabled="true" style="position: relative;"><input type="radio" name="r2" class="minimal-red" disabled style="position: absolute; opacity: 0;"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins></div>
		            </div>
	        	</div>
			</div>
		</div>
	</div>
	

<c:import url="/deresources/commonlib/js" />
<script src="<spring:url value="/assets/javascripts/dataeye.resizer.js"/>"></script>
<script src="<spring:url value="/app/portal/dataeye.portal.js"/>"></script>
<script type="text/javascript">
debugger;
$('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
  	checkboxClass: 'icheckbox_minimal-red',
  	radioClass: 'iradio_minimal-red'
});
</script>