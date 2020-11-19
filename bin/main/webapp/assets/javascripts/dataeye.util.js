(function () {
    'use strict';

    (function (root, factory) {
        if ((typeof define === 'function') && define.amd) {
            return define(['jquery'], factory);
        } else {
            return factory(root.DE, root.jQuery);
        }
    })(window, function (DE, $) {

        DE = DE || {};

        /**
         * @literal    util literal
         * @memberOf module:DE.util.object
         * @return    {object}    DE.util
         * @description        새로운 DE.util 인스턴스를 생성한다.
         */
        DE.util = {
    		selectBox : function($target, data, opts) {
        		var defaults = {
        			value:"CODE",
        			text:"NAME",
        			isAll:true,
        			callback:false
        		};
        		
        		$($target).find("option").remove();
        		var opts = $.extend({}, defaults, opts);
        		if (opts["isAll"]) {
        			$target.append($("<option>").attr("value", "_all").text("[전체]")); 
        		}
        		$.each(data, function(index, value) {
        			$target.append($("<option>").attr("value", value[opts["value"]]).text(value[opts["text"]]));
        		});
    		},
        	dateTimePicker : {
        		picker : function($dt, opts) {
            		var defaults = {
        	            locale: "ko",
        	            viewMode: 'days',
        	            format: 'YYYY-MM-DD',
        	            extraFormats: ['YYYYMMDD']
        	        };
            		var opts = $.extend({}, defaults, opts);
            		
            		$dt.datetimepicker(opts);
        		},
        		linkedPicker : function($sdt, $edt, opts) {
            		var defaults = {
        	            locale: "ko",
        	            viewMode: 'days',
        	            format: 'YYYY-MM-DD',
        	            extraFormats: ['YYYYMMDD']
        	        };
            		var opts = $.extend({}, defaults, opts);
            		
            		$sdt.datetimepicker(opts);
                    $edt.datetimepicker(opts);
                    $sdt.on("dp.change", function (e) {
                    	$edt.data("DateTimePicker").minDate(e.date);
                    });
                    $("#endDt").on("dp.change", function (e) {
                    	$sdt.data("DateTimePicker").maxDate(e.date);
                    });
        		},
        		getValue : function(target) {
                	var date = $(target).datetimepicker().data("DateTimePicker").date();
                	if (date === null) {
                		return "";
                	} else {
                		return date.format("YYYYMMDD");
                	}
                },
        	},
    		dateFormat : function(_date, opts) {
        		var defaults = {
    	            format: 'yyyyMMdd',
    	            addDays: 0
    	        };
        		var opts = $.extend({}, defaults, opts);
        		if (opts["addDays"] === 0) {
        			return _date.format(opts["format"]);
        		} else {
        			_date.setDate(_date.getDate() + opts["addDays"]);
        			return _date.format(opts["format"]);
        		}
    		}    		
        };

        return DE.util;
    });

}).call(this);
