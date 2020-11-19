var GRID_BOTTOM_MARGIN = 45;

var browser = (function() {
    var s = navigator.userAgent.toLowerCase();
    var match = /(webkit)[ \/](\w.]+)/.exec(s) ||
                /(opera)(?:.*version)?[ \/](\w.]+)/.exec(s) ||
                /(msie) ([\w.]+)/.exec(s) ||
               !/compatible/.test(s) && /(mozilla)(?:.*? rv:([\w.]+))?/.exec(s) ||
               [];
    return { name: match[1] || "", version: match[2] || "0" };
}());

$( document ).ajaxError(function( event, jqxhr, settings, exception ) {
	var $msgDiv = [];
	//901 : session timeout
	if (jqxhr.status != 0 && jqxhr.status != 901) {
		if ($(document.body).find("div#ajaxErrorMessage").length == 0) {
		    $msgDiv = $("<div id='ajaxErrorMessage' title='ERROR'></div>");
            $msgDiv.append("<p />");
            $(document.body).append($msgDiv);
			//$msgDiv = $("<div id='ajaxErrorMessage' align='center' style='width:100%; height:100%; position:relative;z-index:10000;'></div>");
			//$msgDiv.append("<div id='message' style='width:400px; height:200px;position: absolute; top: 50%; left: 50%; margin: -100px -200px 0;overflow:auto;'></div>");
			//$msgDiv.find("div#message").append("<div id='error'></div>");
			//$msgDiv.find("div#message").append("<input type='button' value='확인' onclick='javascript:$(\"div#ajaxErrorMessage\").remove();'>");
		} else {
			$msgDiv = $(document.body).find("div#ajaxErrorMessage");
		}
		
		var resp = JSON.parse(jqxhr.responseText);
		$msgDiv.find("p").html(resp.response.message);
		$("div#ajaxErrorMessage").dialog();
	}
});

/*Array.prototype.remove = function(idx) {
    return (idx<0 || idx>this.length) ? this : this.slice(0, idx).concat(this.slice(idx+1, this.length));
};*/

Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
 
    var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    var d = this;
     
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};
 
String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};

var UI = {
    GRID_LINK_FONT_COLOR : "blue",
    GRID_ERR_BG_COLOR : "red",
	PROGR_STTUS : [
      {id:"11", value:"요청작성중"},
      {id:"19", value:"요청완료"},
      {id:"21", value:"승인검토중"},
      {id:"29", value:"승인완료"},
      {id:"39", value:"승인기각"}
	],
	RQT_DATA_CHG_STTUS : [
      {id:"C", value:"등록요청"},
      {id:"U", value:"수정요청"},
      {id:"D", value:"삭제요청"}
	],
	SYS_SEARCH : [
  	    {id:0, value:"OBJ_BAS"},
  		{id:1, value:"OBJ_TYPE_ID"},
  		{id:2, value:"OBJ_ID"},
  		{id:3, value:"ADM_OBJ_ID"},
  		{id:4, value:"OBJ_NM"},
  		{id:5, value:"OBJ_ABBR_NM"},
  		{id:6, value:"OBJ_DESC"},
  		{id:7, value:"PATH_OBJ_TYPE_ID"},
  		{id:8, value:"PATH_OBJ_ID"}
  	],
  	OBJ_TYPE : [
  	     /* 개발방법론 */
  	     {id:"000101L", 		icon:"../assets/images/icon/objtype/000101L.png", 			name:"Project",					type:"PDM"},
  	     {id:"000102L", 		icon:"../assets/images/icon/objtype/000102L.png", 			name:"Phase",					type:"PDM"},
  	     {id:"000103L", 		icon:"../assets/images/icon/objtype/000103L.png", 			name:"Activity",				type:"PDM"},
  	 	 {id:"000104L", 		icon:"../assets/images/icon/objtype/000104L.png", 			name:"Task",					type:"PDM"},
  	 	 {id:"000105L", 		icon:"../assets/images/icon/objtype/000105L.png", 			name:"산출물",					type:"PDM"},
  	 	 /* 비즈니스-정보정의 */
  	 	 {id:"010101L", 		icon:"../assets/images/icon/objtype/010101L.png", 			name:"보고서주제영역", 			type:"BIZ-INFO"},
  	 	 {id:"010102L", 		icon:"../assets/images/icon/objtype/010102L.png", 			name:"리포트", 					type:"BIZ-INFO"},
  	 	 {id:"010103L", 		icon:"../assets/images/icon/objtype/010103L.png", 			name:"데이터셋", 				type:"BIZ-INFO"},
  	 	 {id:"010104L", 		icon:"../assets/images/icon/objtype/010104L.png", 			name:"지표", 					type:"BIZ-INFO"},
  	 	 {id:"010105L", 		icon:"../assets/images/icon/objtype/010105L.png", 			name:"관점", 					type:"BIZ-INFO"},
  	 	 {id:"010106L", 		icon:"../assets/images/icon/objtype/010106L.png", 			name:"관점그룹", 				type:"BIZ-INFO"},
  	 	 {id:"010107L",         icon:"../assets/images/icon/objtype/010107L.png",             name:"지표주제영역",      		type:"BIZ-INFO"},
         /* 비즈니스-표준화 */
  	 	 {id:"010301L", 		icon:"../assets/images/icon/objtype/010301L.png", 			name:"표준단어", 				type:"BIZ-STD"},
  	 	 {id:"010302L", 		icon:"../assets/images/icon/objtype/010302L.png", 			name:"표준용어", 				type:"BIZ-STD"},
  	 	 {id:"010303L", 		icon:"../assets/images/icon/objtype/010303L.png", 			name:"표준도메인", 				type:"BIZ-STD"},
  	 	 {id:"010304L", 		icon:"../assets/images/icon/objtype/010304L.png", 			name:"도메인그룹", 				type:"BIZ-STD"},
  	 	 /* 비즈니스-보안관리 */
  	 	 {id:"010401L", 		icon:"../assets/images/icon/objtype/010401L.png", 			name:"보안주제영역", 				type:"BIZ-SECU"},
  	 	 {id:"010402L", 		icon:"../assets/images/icon/objtype/010402L.png", 			name:"보안항목", 				type:"BIZ-SECU"},
  	 	 /* 비즈니스-코드 */
  	 	 {id:"010501L", 		icon:"../assets/images/icon/objtype/010501L.png", 			name:"코드시스템", 				type:"BIZ-CODE"},
  	 	 {id:"010502L", 		icon:"../assets/images/icon/objtype/010502L.png", 			name:"코드그룹", 				type:"BIZ-CODE"},
  	 	 /* 설계-OLAP설계 */
 	 	 {id:"040201L", 		icon:"../assets/images/icon/objtype/040201L.png", 			name:"OLAP주제영역", 			type:"DESIGN-OLAP"},
 	 	 {id:"040202L", 		icon:"../assets/images/icon/objtype/040202L.png", 			name:"리포트", 					type:"DESIGN-OLAP"},
 	 	 {id:"040203L", 		icon:"../assets/images/icon/objtype/040203L.png", 			name:"팩트", 					type:"DESIGN-OLAP"},
 	 	 {id:"040204L", 		icon:"../assets/images/icon/objtype/040204L.png", 			name:"디멘젼", 					type:"DESIGN-OLAP"},
 	 	 {id:"040205L", 		icon:"../assets/images/icon/objtype/040205L.png", 			name:"계층", 					type:"DESIGN-OLAP"},
 	 	 /* 기술-DBMS */
 	 	 {id:"02010IL", 		icon:"../assets/images/icon/objtype/02010IL.png", 			name:"DB인스턴스", 				type:"SYSTEM-DBMS"},
	 	 {id:"020101L", 		icon:"../assets/images/icon/objtype/020101L.png", 			name:"데이터베이스", 				type:"SYSTEM-DBMS"},
	 	 {id:"020102L", 		icon:"../assets/images/icon/objtype/020102L.png", 			name:"테이블", 					type:"SYSTEM-DBMS"},
	 	 {id:"020103L", 		icon:"../assets/images/icon/objtype/020103L.png", 			name:"뷰", 						type:"SYSTEM-DBMS"},
	 	 {id:"020104L", 		icon:"../assets/images/icon/objtype/020104L.png", 			name:"컬럼", 					type:"SYSTEM-DBMS"},
	 	 {id:"020105L", 		icon:"../assets/images/icon/objtype/020105L.png", 			name:"뷰컬럼", 					type:"SYSTEM-DBMS"},
	 	 {id:"020106L", 		icon:"../assets/images/icon/objtype/020106L.png", 			name:"프로시저", 				type:"SYSTEM-DBMS"},
	 	 /* 기술-Hadoop */
         {id:"02020IL", 		icon:"", 													name:"HADOOP-INSTANCE",			type:"SYSTEM-HADOOP"},
         {id:"020201L", 		icon:"resources/images/meta/object/ic_project.png", 		name:"데이터베이스",				type:"SYSTEM-HADOOP"},
         {id:"020202L", 		icon:"resources/images/meta/object/ic_folder.png", 			name:"테이블",					type:"SYSTEM-HADOOP"},
         {id:"020203L", 		icon:"resources/images/meta/object/ic_datasetT.png", 		name:"뷰",						type:"SYSTEM-HADOOP"},
         {id:"020204L", 		icon:"resources/images/meta/object/ic_document.png", 		name:"컬럼",						type:"SYSTEM-HADOOP"},
         {id:"020205L", 		icon:"resources/images/meta/object/ic_grid.png", 			name:"뷰컬럼",					type:"SYSTEM-HADOOP"},

         {id:"020206L", 		icon:"resources/images/meta/object/ic_metric.png", 			name:"메트릭",					type:"SYSTEM-MSTR"},
         {id:"020207L", 		icon:"resources/images/meta/object/ic_attribute.png", 		name:"어트리뷰트",				type:"SYSTEM-MSTR"},
         {id:"020208L", 		icon:"resources/images/meta/object/ic_filter.png", 			name:"필터",						type:"SYSTEM-MSTR"},
         {id:"020209L", 		icon:"resources/images/meta/object/ic_prompt.png", 			name:"프롬프트",					type:"SYSTEM-MSTR"},
         {id:"020210L", 		icon:"resources/images/meta/object/ic_mstrfact.png", 		name:"팩트",						type:"SYSTEM-MSTR"},
         {id:"020211L", 		icon:"resources/images/meta/object/ic_entitytable.png", 	name:"논리테이블",				type:"SYSTEM-MSTR"},
         {id:"020212L", 		icon:"resources/images/meta/object/ic_physicaltable.png", 	name:"물리테이블",				type:"SYSTEM-MSTR"},
         {id:"020213L", 		icon:"resources/images/meta/object/ic_tableCol.png", 		name:"열별칭",					type:"SYSTEM-MSTR"},
         {id:"020214L", 		icon:"resources/images/meta/object/ic_shortcut.png", 		name:"바로가기",					type:"SYSTEM-MSTR"},
         {id:"020215L", 		icon:"resources/images/meta/object/ic_reportindex.png", 	name:"유도메트릭",				type:"SYSTEM-MSTR"},
         /* 기술-Informatica */
         {id:"02030IL", 		icon:"", 													name:"INFA-INSTANCE",			type:"SYSTEM-INFA"},
         {id:"020301L", 		icon:"resources/images/meta/object/ic_repository.png", 		name:"Repository",				type:"SYSTEM-INFA"},
         {id:"020302L", 		icon:"resources/images/meta/object/ic_subject.png", 		name:"Subject",					type:"SYSTEM-INFA"},
         {id:"020303L", 		icon:"resources/images/meta/object/ic_mapping.png", 		name:"Mapping",					type:"SYSTEM-INFA"},
         {id:"020304L", 		icon:"resources/images/meta/object/ic_source.png", 			name:"Source",					type:"SYSTEM-INFA"},
         {id:"020305L", 		icon:"resources/images/meta/object/ic_target.png", 			name:"Target",					type:"SYSTEM-INFA"},
         {id:"020306L", 		icon:"resources/images/meta/object/ic_transformation.png", 	name:"Transformation",			type:"SYSTEM-INFA"},
         {id:"020307L", 		icon:"resources/images/meta/object/ic_session.png", 		name:"Session",					type:"SYSTEM-INFA"},
         {id:"020308L", 		icon:"resources/images/meta/object/ic_workflow.png", 		name:"Workflow",				type:"SYSTEM-INFA"},
         /* 기술-Control-M Job */
         {id:"020401L", 		icon:"resources/images/meta/object/ic_ctrmjob.png", 		name:"Ctrm-Job",				type:"SYSTEM-CTRM"},
         /* 기술-Application */
         {id:"020501L", 		icon:"resources/images/meta/object/ic_appsystem.png", 		name:"App시스템",				type:"SYSTEM-APP"},
         {id:"020502L", 		icon:"resources/images/meta/object/ic_appgroup.png", 		name:"App그룹",					type:"SYSTEM-APP"},
         {id:"020503L", 		icon:"resources/images/meta/object/ic_database.png", 		name:"DBMS접속정보",				type:"SYSTEM-APP"},
         /* 기술-설계문서 */
         {id:"020701L", 		icon:"resources/images/meta/object/ic_appsystem.png", 		name:"프로그램처리프로그램목록",	type:"SYSTEM-DESIGNDOC"},
         /* 기술-COGNOS */
         {id:"02080IL", 		icon:"../assets/images/icon/objtype/02080IL.png", 			name:"Cognos인스턴스",			type:"SYSTEM-COGNOS"},
         {id:"020801L", 		icon:"../assets/images/icon/objtype/020801L.png", 			name:"프로젝트",					type:"SYSTEM-COGNOS"},
         {id:"020802L", 		icon:"../assets/images/icon/objtype/020802L.png", 			name:"폴더",						type:"SYSTEM-COGNOS"},
         {id:"020803L", 		icon:"../assets/images/icon/objtype/020803L.png", 			name:"리포트",					type:"SYSTEM-COGNOS"},
         {id:"020804L", 		icon:"../assets/images/icon/objtype/020804L.png", 			name:"대쉬보드",					type:"SYSTEM-COGNOS"},
         /* 기술-DATASTAGE */
         {id:"02090IL", 		icon:"../assets/images/icon/objtype/02090IL.png", 			name:"DataStage인스턴스",		type:"SYSTEM-DATASTAGE"},
         {id:"020901L", 		icon:"../assets/images/icon/objtype/020901L.png", 			name:"프로젝트",					type:"SYSTEM-DATASTAGE"},
         {id:"020902L", 		icon:"../assets/images/icon/objtype/020902L.png", 			name:"폴더",						type:"SYSTEM-DATASTAGE"},
         {id:"020903L", 		icon:"../assets/images/icon/objtype/020903L.png", 			name:"JOB",						type:"SYSTEM-DATASTAGE"},
         
         /*	기타-Tip */
         {id:"090101L", 		icon:"../assets/images/icon/objtype/090101L.png", 			name:"TIP",						type:"ETC-TIP"},
         
         /* 설계-DB설계 */
  	 	 /*{id:"040101L", 		icon:"../assets/images/icon/objtype/040101L.png", 			name:"DB주제영역", 				type:"DESIGN-DB"},*/
 	 	 {id:"040102L", 		icon:"../assets/images/icon/objtype/040102L.png", 			name:"테이블", 					type:"DESIGN-DB"},
 	 	 {id:"040103L", 		icon:"../assets/images/icon/objtype/040103L.png", 			name:"컬럼", 					type:"DESIGN-DB"},
 	 	 /*{id:"040104L", 		icon:"../assets/images/icon/objtype/040104L.png", 			name:"소스컬럼", 				type:"DESIGN-DB"},*/
 	 	  /* 유관시스템 */
         {id:"050101L", 		icon:"", 													name:"APP그룹",					type:"ANAL_TASK"},
         {id:"050102L", 		icon:"", 													name:"APP시스템",				type:"ANAL_TASK"},
         {id:"050103L", 		icon:"", 													name:"연결정보",					type:"ANAL_TASK"}
         
	],
	setTitle : function(request) {
		var o = $("#" + request.id.replace(/[\\]/gi,'\\\\').replace(/[=]/gi,'\\=').replace(/[/]/gi,'\\/').replace(/[+]/gi,'\\+'));		
		$(".content_header > .msitemap").html($(".navi li span.on").text() + " > " + o.closest("div").prev().text() + " > <span>" + request.original.title+"</span>");
		$(".content_header > li > span.title").html(request.original.title);
    },
	cutStrToArray: function(str, maxLength) {
        var ret = [];
        var sPoint = 0; ePoint = maxLength;
        var val = str.substring(sPoint, ePoint);
        while ("" != val){
            ret.push(val);
            sPoint = ePoint, ePoint = ePoint + maxLength;
            val = str.substring(sPoint, ePoint);
        }
        
        return ret;
    },
	makeChekcBox : function (target, params, id, valueKey, textKey, callback) {
	    $.ajax({
	        type: "POST",
	        contentType: "application/json; charset=utf-8",
	        url: "json/list",
	        data: JSON.stringify(params),
	        dataType: "json",
	        success: function (data, textStatus) {
	        	for(var i=0; i<data.length; i++) {
	        		target.eq(0).append("<input type='checkbox' id='"+id+"' name='"+id+"' value='"+eval("data[i]."+valueKey)+"'><span class='txt'>"+eval("data[i]."+textKey)+"</span>");
	    		};
	    		
	    		if (callback != undefined || callback != null) {
	    			callback(data);
	    		}
	        }
		});
	},
	makeSelectBox : function (target, params, valueKey, textKey, isAll, callback) {
		$.ajax({
	        type: "POST",
	        contentType: "application/json; charset=utf-8",
	        url: "json/list",
	        data: JSON.stringify(params),
	        dataType: "json",
	        success: function (data, textStatus) {
	    		var rows = data;
	    		target.find("option").remove();
	    		var option;
	    		if (isAll == undefined || isAll == true) {
		    		option = document.createElement('option');
		    		option.value = "";
		    		option.text = "- 전체 -";
		    		target.append(option);
	    		}
	    		for (var i=0; i<rows.length; i++) {
	    			option = document.createElement('option');
	    			option.value = eval("rows[i]."+valueKey);
	    			option.text = eval("rows[i]."+textKey);
	    			target.append(option);
	    		}
	    		
	    		if (callback != undefined && callback != null) {
	    			callback();
	    		}
	        }
		});
	},
	datePicker : function (target) {
		target.datepicker({
		   defaultDate: "",
		   dateFormat: "yy-mm-dd",
		   changeMonth: false,
		   numberOfMonths: 1,
		   buttonImage:'resources/images/btn/btn_calendar.gif',
		   showOn: 'button',
		   beforeShow: function(input) {
			   var i_offset= $(input).offset();
			   setTimeout(function(){
				   var h1 = $(document.body).height();
				   var h2 = $('#ui-datepicker-div').height()+i_offset.top;
				   if (h1 < h2) {
					   $('#ui-datepicker-div').css({'top':i_offset.top-210, 'left':i_offset.left});
				   } else {
					   $('#ui-datepicker-div').css({'top':i_offset.top+30, 'left':i_offset.left});
				   }
			    });
			} 
		});		
	},
	dateRangePicker : function (from, to) {
		 from.datepicker({
		   defaultDate: "-1w",
		   dateFormat: "yy-mm-dd",
		   changeMonth: false,
		   numberOfMonths: 1,
		   buttonImage:'resources/images/btn/btn_calendar.gif',
		   showOn: 'button',
		   onClose: function( selectedDate ) {
		     to.datepicker( "option", "minDate", selectedDate );
		   },
		   beforeShow: function(input) {
		    var i_offset= $(input).offset();
			    setTimeout(function(){
			       $('#ui-datepicker-div').css({'top':i_offset.top+30, 'left':i_offset.left});
			    });
			} 
		 });
		 to.datepicker({
		   defaultDate: "",
		   dateFormat: "yy-mm-dd",
		   changeMonth: false,
		   numberOfMonths: 1,
		   buttonImage:'resources/images/btn/btn_calendar.gif',
		   showOn: 'button',
		   onClose: function( selectedDate ) {
		     from.datepicker( "option", "maxDate", selectedDate );
		   },
		   beforeShow: function(input) {
		    var i_offset= $(input).offset(); //클占쏙옙占쏙옙 input占쏙옙 占쏙옙치占쏙옙 체크
			    setTimeout(function(){
			       $('#ui-datepicker-div').css({'top':i_offset.top+30, 'left':i_offset.left});
			    });
			} 
		 });	
	},
	getDatePickerVal : function (target) {
		return $.datepicker.formatDate('yymmdd', target.datepicker("getDate"));
	},
	setDatePickerVal : function (target, value, format) {
		if (value instanceof Date) {
			target.datepicker("setDate", value);
		} else {
			if (typeof(value) == "number") {
				target.datepicker("setDate", new Date(value));
			} else {
				target.datepicker("setDate", $.datepicker.parseDate(format,  value));
			}
		}
	},
	toggleChecked : function (source, target) {
		target.prop('checked', source.checked);
	},
	checkedValue : function (source) {
		return source.filter(':checked').map(function () { return this.value; }).get();
	},
	selectValues : function (source) {
		var vals = new Array();
		if (source.val() == "") {
			vals = source.find('option').map(function () { return this.value; }).get();
		} else {
			vals.push(source.val());
		}
		
		return vals.filter(function(x){return x==""?false:true;});
	},
	multiSelectedValue : function ($source, isAll) {
		var ret = $source.multiselect("getChecked").map(function(){
			return this.value;    
		}).get();
		if (isAll != undefined && isAll == true) {
			if (ret.length == 0) {
				ret = $("#selObjTypeId option").map(function(){
					return this.value;    
				}).get();
			}
		}
		
		return ret;
	},
	sysSearchValues : function (source) {
		var array = new Array();
		var vals = new Array();
		if (source.val() == "") {
			vals = source.find('option').map(function () { return this.value; }).get();
		} else {
			vals.push(source.val());
		}
		for(var i=0; i<vals.length; i++) {
			if (vals[i] != "") {
				for (var j=0; j<UI.SYS_SEARCH.length; j++) {
					if (UI.SYS_SEARCH[j].id == vals[i]) {
						array.push(UI.SYS_SEARCH[j].value);
						break;
					}
				}
			}
		}
		return array;
	},
	getProgrSttus : function (value, options, rowObject) {
		for(var i=0; i<UI.PROGR_STTUS.length; i++) {
			if (UI.PROGR_STTUS[i].id == value) {
				return UI.PROGR_STTUS[i].value;
			}
		}
	},
	getRqtDataChgSttus : function (value, options, rowObject) {
		for(var i=0; i<UI.RQT_DATA_CHG_STTUS.length; i++) {
			if (UI.RQT_DATA_CHG_STTUS[i].id == value) {
				return UI.RQT_DATA_CHG_STTUS[i].value;
			}
		}
	},
	radio: function (value, options, rowObject) {
		var radioHtml = '<input type="radio" value="chk" name="radioid" />';
		return radioHtml;
	},
	open: {
	    popupWindows: {},
	    popupAllClose: function() {
	        var popupNames = Object.getOwnPropertyNames(UI.open.popupWindows);
	        for(var i=0; i<popupNames.length; i++) {
	            try {
	                var win = UI.open.popupWindows[popupNames[i]];
	                if(typeof win != "undefined" && !win.closed) {
	                    win.close();
	                }
	            } catch(e){}
	        }
	        UI.open.popupWindows = {};
	    },
		getId: function (keys) {
			var specialChars = /[~!#$^&*=+|:;?"<,.>']/;
			var s = "popup";
			if (typeof(keys) == "string") {
				s += "_"+keys;
			} else {
				$.each(keys, function() {
					s += "_"+this;
				});
			}
			return s.split(specialChars).join("");;
		},
		frame : function(viewname, target, param){
			var $frm =  $("#_frameForm");
		    var $viewname =  $("#viewname");

		    if ($frm.length == 0) {
		    	$frm =  $("<form id='_frameForm' name='_frameForm' action='goPage' target='"+target+"' method='post'></form>").css("display", "none");
		        $frm.appendTo("body");
		    }

		    if ($frm.find("#viewname").length == 0) {
	    		$viewname =  $("<input type='hidden' id='viewname' name='viewname'/>");
	    		$viewname.appendTo($frm);
		    }
	    	$viewname.attr("value", "simple/"+viewname);
	    	
	    	if ($frm.find("#data").length == 0) {
	    		$data =  $("<input type='hidden' id='data' name='data'/>");
	    		$data.appendTo($frm);
		    }
	    	$data.attr("value", JSON.stringify(param));
		    $frm.submit();
		    $frm.remove();
		},
		frameNew : function(viewname, target, param){
			var $frm =  $("#_frameForm");
			var $viewname =  $("#viewname");

			if ($frm.length == 0) {
				$frm =  $("<form id='_frameForm' name='_frameForm' action='goPage' target='"+target+"' method='post'></form>").css("display", "none");
				$frm.appendTo("body");
			}

			if ($frm.find("#viewname").length == 0) {
				$viewname =  $("<input type='hidden' id='viewname' name='viewname'/>");
				$viewname.appendTo($frm);
			}
			$viewname.attr("value", "popup/"+viewname);

			if ($frm.find("#data").length == 0) {
				$data =  $("<input type='hidden' id='data' name='data'/>");
				$data.appendTo($frm);
			}
			$data.attr("value", JSON.stringify(param));
			$frm.submit();
			$frm.remove();
		},
		popup: function (url, keys, param, options) {
			console.log(param);
			var winId = this.getId(keys);
			if (options == undefined) {
				options = "width=1000, height=650, toolbar=no, menubar=no, location=no";
			}
			UI.open.popupWindows[winId] = window.open("about:blank", winId, options);
			
			var $frm = $("#objectInfoForm");
	    	var $viewname = $("#viewname");
	    	var $data = $("#data");
	    	
	    	if ($frm.length == 0) {
		    	$frm =  $("<form id='objectInfoForm' name='objectInfoForm' action='"+url+"' target='"+winId+"' class='objectInfoForm' method='post'></form>").css("display", "none");
		        $frm.appendTo("body");
		    }
	    	if ($frm.find("#viewname").length == 0) {
	    		$viewname =  $("<input type='hidden' id='viewname' name='viewname'/>");
	    		$viewname.appendTo($frm);
		    }
	    	$viewname.attr("value", param.viewname);
	    	if ($frm.find("#data").length == 0) {
	    		$data =  $("<input type='hidden' id='data' name='data'/>");
	    		$data.appendTo($frm);
		    }
	    	$data.attr("value", JSON.stringify(param));
	    	
	    	$frm.submit();
	    	$frm.remove();
	    	/**
	    	while("mainWindow" != name) {
	    	    if (window.opener)
	    	}
	    	**/
	    	UI.open.popupWindows[winId].focus();
            return UI.open.popupWindows[winId];
		},
		popupNew: function (url, keys, param, options) {
			console.log(param);
			var winId = this.getId(keys);
			if (options == undefined) {
				options = "width=1000, height=650, toolbar=no, menubar=no, location=no";
			}
			UI.open.popupWindows[winId] = window.open("about:blank", winId, options);

			var $frm = $("#objectInfoForm");
			var $viewname = $("#viewname");
			var $data = $("#data");

			if ($frm.length == 0) {
				$frm =  $("<form id='objectInfoForm' name='objectInfoForm' action='"+url+"' target='"+winId+"' class='objectInfoForm' method='post'></form>").css("display", "none");
				$frm.appendTo("body");
			}
			if ($frm.find("#viewname").length == 0) {
				$viewname =  $("<input type='hidden' id='viewname' name='viewname'/>");
				$viewname.appendTo($frm);
			}
			$viewname.attr("value", "popup/"+param.viewname);
			if ($frm.find("#data").length == 0) {
				$data =  $("<input type='hidden' id='data' name='data'/>");
				$data.appendTo($frm);
			}
			$data.attr("value", JSON.stringify(param));

			$frm.submit();
			$frm.remove();
			/**
			 while("mainWindow" != name) {
	    	    if (window.opener)
	    	}
			 **/
			UI.open.popupWindows[winId].focus();
			return UI.open.popupWindows[winId];
		}
	},
	objType: {
		getImgTag : function (objTypeId, a, b) {
			for(var i=0; i<UI.OBJ_TYPE.length; i++) {
				if (UI.OBJ_TYPE[i].id == objTypeId) {
					if (undefined == UI.OBJ_TYPE[i].icon || "" == UI.OBJ_TYPE[i].icon) {
						return UI.OBJ_TYPE[i].name;
					} else {
						return "<img src=\""+UI.OBJ_TYPE[i].icon+"\" title=\""+UI.OBJ_TYPE[i].name+"\">";
					}
				}
			}
			return "";
		},
		getId : function (name) {
			for(var i=0; i<UI.OBJ_TYPE.length; i++) {
				if (UI.OBJ_TYPE[i].name == name) {
					return UI.OBJ_TYPE[i].id;
				}
			}
			return "";
		},
		getNm : function (id) {
			for(var i=0; i<UI.OBJ_TYPE.length; i++) {
				if (UI.OBJ_TYPE[i].id == id) {
					return UI.OBJ_TYPE[i].name;
				}
			}
			return "";
		},
		getIcon : function (id) {
			for(var i=0; i<UI.OBJ_TYPE.length; i++) {
				if (UI.OBJ_TYPE[i].id == id) {
					return UI.OBJ_TYPE[i].icon;
				}
			}
			return "";
		}
	},
	fileDownload: function (file, fileLocName) {
		var $frm = $("#fileDownloadForm");
    	var $file = $("#file");
    	var $fileLocName = $("#fileLocName");
    	var $frame =  $("#_frame");

    	if ($frm.length == 0) {
	    	$frm =  $("<form id='fileDownloadForm' name='fileDownloadForm' action='file/download' target='_frame' class='fileDownloadForm' method='post'></form>").css("display", "none");
	        $frm.appendTo("body");
	    }
    	if ($frm.find("#file").length == 0) {
	    	$file =  $("<input type='hidden' id='file' name='file'/>");
	    	$file.appendTo($frm);
	    }
    	$file.val(file);
    	if (fileLocName != undefined && fileLocName != null && fileLocName != "") {
	    	if ($frm.find("#fileLocName").length == 0) {
		    	$fileLocName =  $("<input type='hidden' id='fileLocName' name='fileLocName'/>");
		    	$fileLocName.appendTo($frm);
		    }
	    	$fileLocName.val(fileLocName);
    	}
    	if ($frame.length == 0) {
        	$frame =  $("<iframe id='_frame' name='_frame'></iframe>").css("display", "none");
            $frame.appendTo("body");
        }

    	$frm.submit();
	},
	jqgrid : {
		resetFilters : function (grid) {
			grid.jqGrid('setGridParam', {search: false, postData: { "filters": null} }).trigger("reloadGrid");
			grid.closest(".ui-jqgrid-view").find(">.ui-jqgrid-hdiv .ui-search-toolbar th input").val("");
		    grid.closest(".ui-jqgrid-view").find(">.ui-jqgrid-hdiv .ui-search-toolbar th select").val("");
		},
		getUniqueValue : function (grid, columnName) {
			var data = grid.jqGrid('getGridParam', 'data');
			var uniqueTexts = [], text, textsMap = {};
			
			for (var i=0; i<data.length; i++) {
				text = data[i][columnName];
				if (text !== undefined && textsMap[text] === undefined) {
					textsMap[text] = true;
			        uniqueTexts.push(text);
				}
			}

			return uniqueTexts.sort();
		},
		setSelectSearchOption: function (grid, columnName, func) {
			var valStr = ":-전체-";
			var uniqueVals = UI.jqgrid.getUniqueValue(grid, columnName);
			var option;
			var target =  $("tr.ui-search-toolbar", grid[0].grid.hDiv).find("[id=gs_"+columnName+"]");
			target.find("option[value !='']").remove();
			
			if (func == undefined) {
				$.each(uniqueVals, function() {
					option = document.createElement('option');
		    		option.value = this;
		    		option.text = this;
		    		target.append(option);
				});
			} else {
				$.each(uniqueVals, function() {
					option = document.createElement('option');
		    		option.value = this;
		    		option.text = func(this);
		    		target.append(option);
				});
			}
			grid.jqGrid("setColProp", columnName, {
				stype: "select",
				searchoptions: {sopt: ['eq'], value:valStr}
			});
		},
		getData: function (grid) {
			var data = [];
			if (grid.jqGrid("getGridParam", "treeGrid")) {
//				data = grid.jqGrid("getRowData");
				data = grid.jqGrid("getGridParam", "data");
			} else {
				data = grid.jqGrid("getGridParam", "data");
			}
			
			var newData = new Array();
			var filters = grid.jqGrid("getGridParam", "postData").filters;
			if (filters == undefined) {
				return data;
			} else {
				filters = JSON.parse(grid.jqGrid("getGridParam", "postData").filters);
				if (filters.rules.length == 0) {
					return data;
				} else {
					$.each(data, function() {
						var row = this;
						var isBreak = false;
						$.each(filters.rules, function() {
							if (eval("row."+this.field).indexOf(this.data) == -1) {
								isBreak = true;
								return false;
							}
						});
						
						if (!isBreak) {
							newData.push(row);
						}
					});
					
					return newData;
				}
			}
		},
		exportExcel: function (grid) {
			var $frm = $("#excelExportForm");
	    	var $file = $("#file");
	    	var $colNames = $("#colNames");
	    	var $colModel = $("#colModel");
	    	var $data = $("#data");
	    	var $filters = $("#filters");
	    	var $frame =  $("#_frame");
	    	
	    	var colNames = grid.jqGrid("getGridParam", 'colNames');
	    	var colModel = grid.jqGrid("getGridParam", 'colModel');
	    	var data = UI.jqgrid.getData(grid);
	    	var select = $("tr.ui-search-toolbar", grid[0].grid.hDiv).find("select");
	    	var filters = new Array();
	    	$.each(select, function() {
	    		var obj = new Object();
	    		obj.name = $(this).prop("name");
	    		var option = $(this).find("option");
	    		var options = new Array();
	    		$.each(option, function() {
	    			var obj2 = new Object();
	    			obj2.text = $(this).prop("text");
	    			obj2.value = $(this).prop("value");
	    			options.push(obj2);
	    		});
	    		obj.option = options;
	    		filters.push(obj);
	    	});
	    	
	    	if ($frm.length == 0) {
		    	$frm =  $("<form id='excelExportForm' name='excelExportForm' action='download/excel' target='_frame' class='excelExportForm' method='post'></form>").css("display", "none");
		        $frm.appendTo("body");
		    }
	    	if ($frm.find("#file").length == 0) {
		    	$file =  $("<input type='hidden' id='file' name='file'/>");
		    	$file.appendTo($frm);
		    }
	    	$file.attr("value", "grid_data_"+GUID.get()+".xls");
	    	if ($frm.find("#colNames").length == 0) {
		    	$colNames =  $("<input type='hidden' id='colNames' name='colNames'/>");
		    	$colNames.appendTo($frm);
		    }
	    	$colNames.attr("value", JSON.stringify(colNames));
	    	if ($frm.find("#colModel").length == 0) {
		    	$colModel =  $("<input type='hidden' id='colModel' name='colModel'/>");
		    	$colModel.appendTo($frm);
		    }
	    	$colModel.attr("value", JSON.stringify(colModel));
	    	if ($frm.find("#data").length == 0) {
		    	$data =  $("<input type='hidden' id='data' name='data'/>");
		    	$data.appendTo($frm);
		    }
	    	$data.attr("value", JSON.stringify(data));
	    	if ($frm.find("#filters").length == 0) {
		    	$filters =  $("<input type='hidden' id='filters' name='filters'/>");
		    	$filters.appendTo($frm);
		    }
	    	$filters.attr("value", JSON.stringify(filters));
	    	
	    	if ($frame.length == 0) {
	        	$frame =  $("<iframe id='_frame' name='_frame'></iframe>").css("display", "none");
	            $frame.appendTo("body");
	        }
	    	
	    	$("form.excelExportForm").on("submit", function (e) {
	    	    $.fileDownload($(this).prop('action'), {
	    	        preparingMessageHtml: "엑셀 다운로드 진행중 ...",
	    	        failMessageHtml: "엑셀 다운로드 진행중 오류가 발생하였습니다. 다시 시도하여 주시기 바랍니다.",
	    	        httpMethod: "POST",
	    	        data: $(this).serialize()
	    	    });
	    	    e.preventDefault();
	    	    $("form.excelExportForm").off("submit");
	    	});
	    	
	    	$frm.submit();
		},
		navGrid : function (grid, pager, navOption, filterToolbarOption) {
			grid.jqGrid('navGrid', pager, navOption);
			
			if (filterToolbarOption.toolbar) {
				grid.jqGrid(
					'filterToolbar',{
						stringResult: true, 
						groupOp:'AND',
						searchOnEnter: true, 
						defaultSearch: 'cn'
					}
				);
				
				if (filterToolbarOption.filter) {
					grid.jqGrid('navButtonAdd', pager, {
				        caption: "Filter",
				        title: "Toggle Searching Toolbar",
				        buttonicon: 'ui-icon-pin-s',
				        onClickButton: function () {
				        	jQuery(this)[0].toggleToolbar();
				        	if ($("tr.ui-search-toolbar", jQuery(this)[0].grid.hDiv).is(':visible')) {
				        		$(this).parents('div.ui-jqgrid-bdiv').css("height", $(this).jqGrid("getGridParam").height-30+"px");
				        	} else {
				        		$(this).parents('div.ui-jqgrid-bdiv').css("height", $(this).jqGrid("getGridParam").height+"px");
				        		UI.jqgrid.resetFilters($(this));
				        	}
				        }
				    });
				}
				
				if (filterToolbarOption.excel) {
					grid.jqGrid('navButtonAdd', pager, {
				        caption: "Excel Export",
				        title: "Excel Export",
				        buttonicon: 'ui-icon-excel-export',
				        onClickButton: function () {
				        	UI.jqgrid.exportExcel($(this));
				        }
				    });
				}
			}
		},
		filter : function (grid, filterToolbarOption) {
			grid.jqGrid(
				'filterToolbar',{
					stringResult: true, 
					groupOp:'AND',
					searchOnEnter: true, 
					defaultSearch: 'cn'
				}
			);
		},
		displayTextarea: function(contents, event, width, height) {
			var tpl = [];
			if ($("div#jqgrid_cell_tooltip__textarea").length == 0) {
				tpl = $("<div id='jqgrid_cell_tooltip__textarea' style='position: absolute;'><textarea></textarea></div>");
				$(document.body).append(tpl);
			} else {
				tpl = $("div#jqgrid_cell_tooltip__textarea");
			}
			tpl.find("textarea").css("width", width);
			tpl.find("textarea").css("height", height);
			if (contents == "&nbsp;") contents = "";
			tpl.find("textarea").val(contents);

			var offset = $(event.target).offset();
			if ($(document.body).height() > ($(event.target).offset().top + parseInt(height))) {
				tpl.css("top", offset.top);
				tpl.css("left", offset.left);
			} else {
				tpl.css("top", offset.top-parseInt(height)+20);
				tpl.css("left", offset.left);
			}
			tpl.show();
			
			$(document).off("click");
			$(document).on("click", function(e) {
				if ($(e.target).parent().prop("id") != "jqgrid_cell_tooltip__textarea" && e.target != event.target) {
					tpl.hide();
				}
			});
		},
		checkAll: function(source, event) {
            var grid = $(source).closest(".ui-jqgrid-view").find(".ui-jqgrid-bdiv table.ui-jqgrid-btable").jqGrid();
            var chk = $(source).is(":checked");
            if (chk) {
                grid.jqGrid("setLabel", "chk", "<input type='checkbox' id='"+$(source).prop("id")+"' name='"+$(source).prop("name")+"' onclick='javascript:UI.jqgrid.checkAll(this, event);' checked/>");
            } else {
                grid.jqGrid("setLabel", "chk", "<input type='checkbox' id='"+$(source).prop("id")+"' name='"+$(source).prop("name")+"' onclick='javascript:UI.jqgrid.checkAll(this, event);'/>");
            }
            var data = grid.jqGrid("getGridParam", "data");
            for(var i=0; i<data.length; i++) {
                data[i].chk = chk.toString();
            }
            grid.jqGrid().trigger('reloadGrid', {fromServer:true});
            
            var chkbox = grid.jqGrid().find("input[type=checkbox]");
            for(var i=0; i<chkbox.length; i++) {
                var rowid = chkbox.eq(i).closest("tr").prop("id");
                var row = grid.getRowData(rowid);
                if (row.chk == false.toString()) {
                    chkbox.eq(i).prop("checked", false);
                } else {
                    chkbox.eq(i).prop("checked", true);
                }
            }
        },
        checkedColValues : function(grid, colId) {
        	var data = grid.getGridParam("data");
            var chkObjIds = [];
            for(var i=0; i<data.length; i++) {
                if (data[i].chk == true.toString()) {
                    chkObjIds.push(eval("data[i]."+colId));
                }
            }
            
            return chkObjIds;
        },
        checkedLength : function(grid) {
        	debugger;
        	var data = grid.getGridParam("data");
            var len = 0;
            for(var i=0; i<data.length; i++) {
                if (data[i].chk == true.toString()) {
                	len++;
                }
            }
            
            return len;
        },
        checkedRowObject : function(grid) {
        	var data = grid.getGridParam("data");
        	var rows = [];
            for(var i=0; i<data.length; i++) {
                if (data[i].chk == true.toString()) {
                	rows.push(data[i]);
                }
            }
            
            return rows;
        },
        checkedHandler : function(grid, chk) {
        	if (chk == undefined) chk = "chk";
            var id = grid.prop("id");
            var chkbox = grid.jqGrid().find("td[aria-describedby="+id+"_"+chk+"] > input[type=checkbox]");
            for(var i=0; i<chkbox.length; i++) {
                var rowid = chkbox.eq(i).closest("tr").prop("id");
                var row = grid.getRowData(rowid);
                if (row.chk == false.toString()) {
                    chkbox.eq(i).prop("checked", false);
                } else {
                    chkbox.eq(i).prop("checked", true);
                }
            }
            
            grid.find("input[type=checkbox]").off("click");
            grid.find("input[type=checkbox]").on("click", function (e) {
                var rowid = $(e.target).closest("tr").prop("id");
                var row = grid.getLocalRow(rowid);
                row.chk = $(e.target).is(":checked").toString();
            });
        }
	},
	ajax : {
		getCurrentDate : function (format) {
			if (format == undefined) {
				format = "yyyy-MM-dd hh:mm:ss";
			}
		    return JSON.parse($.ajax({
		        type: "POST",
		        contentType: "application/json; charset=utf-8",
		        url: "json/currentTimestamp",
		        async: false,
		        dataType: "json",
		        data: format
			}).responseText).value;
		},
		getSessionInfo : function () {
			return JSON.parse($.ajax({
		        type: "POST",
		        contentType: "application/json; charset=utf-8",
		        url: "json/sessionInfo",
		        async: false,
		        dataType: "json"
			}).responseText).value;
		},
		getNewObjId : function (objTypeId) {
			return JSON.parse($.ajax({
		        type: "POST",
		        contentType: "application/json; charset=utf-8",
		        url: "json/map",
		        async: false,
		        dataType: "json",
		        data: JSON.stringify({
		        	queryId:"common.newObjId",
		        	objTypeId:objTypeId
		        })
			}).responseText).OBJ_ID;
		},
		getNextKey : function (table, column, prefix) {
            return JSON.parse($.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: "commonCore/getObjUUID",
                async: false,
                dataType: "json",
                data: JSON.stringify({
                    prefix:prefix
                })
            }).responseText).NEXT_KEY;
        },
		execute : function (url, param, callFunc) {
			var callbacks = $.Callbacks();
        	var ret = $.ajax({
		        type: "POST",
		        contentType: "application/json; charset=utf-8",
		        url: url,
		        async: false,
		        dataType: "json",
		        data: JSON.stringify(param)
			}).done(function(data, textStatus, jqXHR) {
				if (callFunc != undefined) {
					callbacks.add(callFunc);
	                callbacks.fire(data);
				}
			}).fail(function(jqXHR, textStatus, errorThrown){
				//alert('false');
			}).always(function(data, textStatus, jqXHR){
				//alert('always');
			});
        	return JSON.parse(ret.responseText);
		},
		list : function (param) {
			return JSON.parse($.ajax({
		        type: "POST",
		        contentType: "application/json; charset=utf-8",
		        url: "json/list",
		        async: false,
		        dataType: "json",
		        data: JSON.stringify(param)
			}).responseText);
		},
		asynclist : function (param, callFunc) {
			var callbacks = $.Callbacks();
        	$.ajax({
		        type: "POST",
		        contentType: "application/json; charset=utf-8",
		        url: "json/list",
		        async: true,
		        dataType: "json",
		        data: JSON.stringify(param)
			}).done(function(data, textStatus, jqXHR) {
				callbacks.add(callFunc);
                callbacks.fire(data);
			}).fail(function(jqXHR, textStatus, errorThrown){
				//alert('false');
			}).always(function(data, textStatus, jqXHR){
				//alert('always');
			});
		},
		map : function (param) {
			return JSON.parse($.ajax({
		        type: "POST",
		        contentType: "application/json; charset=utf-8",
		        url: "json/map",
		        async: false,
		        dataType: "json",
		        data: JSON.stringify(param)
			}).responseText);
		},
		queryValidCheck : function (param) {
			return JSON.parse($.ajax({
		        type: "POST",
		        contentType: "application/json; charset=utf-8",
		        url: "json/queryValidCheck",
		        async: false,
		        dataType: "json",
		        data: JSON.stringify(param)
			}).responseText);
		}
	},
	jquery : {
		load: function (target, viewname, data, callback) {

			$.blockUI({ message: '<img src="../assets/images/loading.gif" />' });
			$(target).load("../goPage", {viewname:viewname, data:JSON.stringify(data)}, function(response, status, xhr) {
				$.unblockUI();
			});
		}
	},
	layout : {
	    type1 : function(_name, _center, _south) {
	    	contentLayout = $(mainLayout.options.center.children.center.paneSelector).layout({
	            name:_name
	            , closable:true
	            , slidable:true
	            , resizable:true
	            , onresize: function(paneName, paneElement, paneState, paneOptions, layoutName) {
	                alert(11);
	            }
	            , livePaneResizing:true
	            , spacing : {
	                open:20,
	                closed:20
	            }
	            , center : {
	                paneSelector:_center,
	                resizable:true,
	                onresize_end: function(paneName, paneElement, paneState, paneOptions, layoutName) {
	                	var $grids = paneElement.find("div.ui-jqgrid-view");
	                    for(var i=0; i<$grids.length; i++) {
	                    	if ($grids.eq(i).closest("div[role=grid-area]").attr("no-resize") == true.toString()) continue;
	                    	
	                    	var id = $grids.eq(i).prop("id").substring("gview_".length);
	                    	if ($grids.eq(i).parents(".ui-subgrid").length != 0) {
	                            var width = parseInt($grids.eq(i).parent().parent().css("width"));
	                            $("#"+id).setGridWidth(width);
	                    	} else {
		                    	var id = $grids.eq(i).prop("id").substring("gview_".length);
		                        var height = parseInt(paneElement.css("height"))-$grids.eq(i).offset().top+10;
		                        $("#"+id).setGridHeight(height);
		                        
		                        var width = parseInt(paneElement.css("width"))-23;
		                        $("#"+id).setGridWidth(width);
	                    	}
	                    }
	                }
	            }
	        });
	    },
	    type2 : function(_name, _north, _west, _center) {
	    	contentLayout = $(mainLayout.options.center.children.center.paneSelector).layout({
	            name:_name
	            , closable:true
	            , slidable:true
	            , resizable:true
	            , onresize: function(paneName, paneElement, paneState, paneOptions, layoutName) {
	                alert(11);
	            }
	            , livePaneResizing:true
	            , spacing : {
	                open:20,
	                closed:20
	            }
	            , center : {
	                paneSelector:_center,
	                resizable:true,
	                onresize_end: function(paneName, paneElement, paneState, paneOptions, layoutName) {
	                	/*var $grids = paneElement.find("div.ui-jqgrid-view");
	                    for(var i=0; i<$grids.length; i++) {
	                    	if ($grids.eq(i).closest("div[role=grid-area]").attr("no-resize") == true.toString()) continue;
	                    	
	                    	var id = $grids.eq(i).prop("id").substring("gview_".length);
	                    	if ($grids.eq(i).parents(".ui-subgrid").length != 0) {
	                            var width = parseInt($grids.eq(i).parent().parent().css("width"));
	                            $("#"+id).setGridWidth(width);
	                    	} else {
		                    	var id = $grids.eq(i).prop("id").substring("gview_".length);
		                        var height = parseInt(paneElement.css("height"))-$grids.eq(i).offset().top+10;
		                        $("#"+id).setGridHeight(height);
		                        
		                        var width = parseInt(paneElement.css("width"))-23;
		                        $("#"+id).setGridWidth(width);
	                    	}
	                    }*/
	                }
	            }
	        });
	    },
	    type3 : function(_name, _north, _center, _south) {
	        contentLayout = $(mainLayout.options.center.children.center.paneSelector).layout({
	            name:_name
	            , closable:true
	            , slidable:true
	            , resizable:true
	            , onresize: function(paneName, paneElement, paneState, paneOptions, layoutName) {
	                alert(11);
	            }
	            , livePaneResizing:true
	            , spacing : {
	                open:20,
	                closed:20
	            }
	            , center : {
	                paneSelector:_center,
	                resizable:true,
	                onresize_end: function(paneName, paneElement, paneState, paneOptions, layoutName) {
	                	/*var $grids = paneElement.find("div.ui-jqgrid-view");
	                    for(var i=0; i<$grids.length; i++) {
	                    	if ($grids.eq(i).closest("div[role=grid-area]").attr("no-resize") == true.toString()) continue;
	                    	
	                    	var id = $grids.eq(i).prop("id").substring("gview_".length);
	                    	if ($grids.eq(i).parents(".ui-subgrid").length != 0) {
	                            var width = parseInt($grids.eq(i).parent().parent().css("width"));
	                            $("#"+id).setGridWidth(width);
	                    	} else {
		                    	var id = $grids.eq(i).prop("id").substring("gview_".length);
		                        var height = parseInt(paneElement.css("height"))-$grids.eq(i).offset().top+10;
		                        $("#"+id).setGridHeight(height);
		                        
		                        var width = parseInt(paneElement.css("width"))-23;
		                        $("#"+id).setGridWidth(width);
	                    	}
	                    }*/
	                }
	            }
	        });
	    }
	},
	file: {
	    download:function(action, param) {
	        var $form = $("#_dumyForm");
	        var $iframe = $("#_dumyIframe");
	        if ($iframe.length == 0) {
	            $iframe =  $("<iframe id='_dumyIframe' name='_dumyIframe'></iframe>").css("display", "none").appendTo("body");
            }
            if ($form.length == 0) {
                $form =  $("<form id='_dumyForm' name='dumyForm' action='"+action+"' target='_dumyIframe' class='_dumyForm' method='post'></form>").css("display", "none").appendTo("body");
            }
            $form.empty();
            
            var objKeys = [];
            for(var k in param) objKeys.push(k);
            for(var i=0; i<objKeys.length; i++) {
                var $input =  $("<input type='hidden' id='"+objKeys[i]+"' name='"+objKeys[i]+"'/>");
                $input.appendTo($form);
                if (typeof eval("param."+objKeys[i]) == "object") {
                    $input.attr("value", JSON.stringify(eval("param."+objKeys[i])));
                } else {
                    $input.attr("value", eval("param."+objKeys[i]));
                }
            }
            
            $form.on("submit", function (e) {
                $.fileDownload($(this).prop('action'), {
                    preparingMessageHtml: "We are preparing your report, please wait...",
                    failMessageHtml: "There was a problem generating your report, please try again.",
                    httpMethod: "POST",
                    data: $(this).serialize()
                });
                e.preventDefault();
                $form.off("submit");
            });
            
            $form.submit();
	    }
	}
};

var GUID = {
	s4 : function() {
		return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
	},
	get : function() {
		return this.s4() + this.s4() + '-' + this.s4() + '-' + this.s4() + '-' + this.s4() + '-' + this.s4() + this.s4() + this.s4();
	}
};