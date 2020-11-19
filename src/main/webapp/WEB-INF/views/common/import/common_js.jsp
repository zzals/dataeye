<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
    <!--
    (function() {
    	DE = {};	
    	DE.contextPath = "";
    	DE.setContextPath = function(contextPath) {
    		DE.contextPath = contextPath;
    	};
    })();
    DE.setContextPath('${pageContext.request.contextPath}');    
    //-->
</script>
<!-- jQuery -->
<script src="<spring:url value="/webjars/jquery/3.1.1/jquery.min.js"/>"></script>
<!-- Bootstrap -->
<!-- <script src="<spring:url value="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script> -->
<script src="<spring:url value="/assets/javascripts-lib/bootstrap.js"/>"></script>
<script src="<spring:url value="/webjars/bootbox/4.4.0/bootbox.js"/>"></script>
<!-- jquery-ui -->
<script src="<spring:url value="/webjars/jquery-ui/1.12.1/jquery-ui.min.js"/>"></script>
<!-- jqgrid -->
<script src="<spring:url value="/assets/javascripts-lib/Guriddo_jqGrid_JS_5.2.0/js/i18n/grid.locale-kr.js"/>"></script>
<%-- <script src="<spring:url value="/assets/javascripts-lib/Guriddo_jqGrid_JS_5.2.0/js/jquery.jqGrid.min.js"/>"></script> --%>
<script src="<spring:url value="/assets/javascripts-lib/Guriddo_jqGrid_JS_5.2.0/src/jquery.jqGrid.js"/>"></script>

<!-- jquery-i18n-properties -->
<script src="<spring:url value="/webjars/jquery-i18n-properties/1.2.2/jquery.i18n.properties.min.js"/>"></script>

<!-- jquery layout -->
<script src="<spring:url value="/assets/javascripts-lib/jquery.layout/jquery.layout_and_plugins.js"/>"></script>
<!-- jquery treeview -->
<script src="<spring:url value="/assets/javascripts-lib/bootstrap.treeview/bootstrap-treeview.js"/>"></script>
<!-- jquery mask -->
<script src="<spring:url value="/assets/javascripts-lib/jquery.mask/jquery.mask.min.js"/>"></script>
<!-- Sparkline -->
<script src="<spring:url value="/assets/javascripts-lib/sparkline/jquery.sparkline.min.js"/>"></script>
<!-- SlimScroll 1.3.0 -->
<script src="<spring:url value="/assets/javascripts-lib/slimScroll/jquery.slimscroll.min.js"/>"></script>
<!-- filedownload -->
<script src="<spring:url value="/assets/javascripts-lib/jquery.fileDownload/jquery.fileDownload.js"/>"></script>

<!-- date time picker -->
<script src="<spring:url value="/assets/javascripts-lib/datetimepicker/moment.min.js"/>"></script>
<script src="<spring:url value="/assets/javascripts-lib/datetimepicker/bootstrap-datetimepicker.min.js"/>"></script>
<script src="<spring:url value="/assets/javascripts-lib/datetimepicker/ko.js"/>"></script>

<script src="<spring:url value="/assets/javascripts-lib/iCheck/icheck.min.js"/>"></script>

<script src="<spring:url value="/assets/javascripts-lib/select2/select2.full.min.js"/>"></script>

<!-- PostMessage -->
<script src="<spring:url value="/assets/javascripts-lib/postMessage/postmessage.min.js"/>"></script>
<!-- resize sensor -->
<script src="<spring:url value="/assets/javascripts-lib/resize-sensor/ResizeSensor.js"/>"></script>
<script src="<spring:url value="/assets/javascripts-lib/resize-sensor/ElementQueries.js"/>"></script>
<!-- DataEye lib -->
<script src="<spring:url value="/assets/javascripts/dataeye.common.js"/>"></script>
<script src="<spring:url value="/assets/javascripts/dataeye.core.js"/>"></script>
<!-- DataEye for lib -->
<script src="<spring:url value="/assets/javascripts/dataeye.ui.js"/>"></script>
<script src="<spring:url value="/assets/javascripts/dataeye.util.js"/>"></script>
<script src="<spring:url value="/assets/javascripts/dataeye.schema.js"/>"></script>
<script src="<spring:url value="/assets/javascripts/jquery.default.js"/>"></script>
<script src="<spring:url value="/assets/javascripts/jqgrid.default.js"/>"></script>
<script src="<spring:url value="/assets/javascripts/dataeye.aprv.js"/>"></script>
<script src="<spring:url value="/assets/javascripts/map.js"/>"></script>
<!-- DataEye for main -->
<script src="<spring:url value="/app/dataeye.main.js"/>"></script>

<!-- ckeditor -->
<script src="<spring:url value="/assets/javascripts-lib/ckeditor/ckeditor.js"/>"></script>