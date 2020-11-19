(function () {
    var originalReady = jQuery.fn.ready;
    $.fn.extend({
        ready: function () {
            if (typeof SCREEN_CRUDE != "undefined") {
                if (SCREEN_CRUDE['cauth'] != "Y") $("[auth_role=C]").remove();
                //if (SCREEN_CRUDE['rauth'] != "Y") $("[auth_role=R]").remove();
                if (SCREEN_CRUDE['uauth'] != "Y") $("[auth_role=U]").remove();
                if (SCREEN_CRUDE['dauth'] != "Y") $("[auth_role=D]").remove();
                if (SCREEN_CRUDE['eauth'] != "Y") $("[auth_role=E]").remove();
            }
            
            if ($("#searchValue").length > 0 && $("#btnSearch").length > 0) {
            	$("#searchValue").off("keypress");
            	$("#searchValue").on("keypress", function(e) {
		    		if (event.which === 13) {
		    			event.preventDefault();
		    			$("#btnSearch").trigger("click");
		    		}
		    	});
            }
            
            originalReady.apply(this, arguments);
        }
    });
})();

$.ajaxSetup({
    cache: false,
    beforeSend: function () {
        //debugger;
        /*
         if ($.isFunction($.blockUI)) {
         $.blockUI();
         }
         */
    },
    complete: function () {
        //debugger;
        /*
         if ($.isFunction($.unblockUI)) {
         $.unblockUI();
         }
         */
    },
    error: function (jqXHR, textStatus, errorThrown) {
        //debugger;
        //return  [false,DE.i18n.prop(jqXHR.responseJSON["errorCode"]),""];
    },
    statusCode: {
        401: function () {
            var version = parseInt(navigator.appVersion);
            var offset = location.href.indexOf(location.host) + location.host.length;
            var absoluteUrl = location.href.substring(offset, location.href.indexOf('/', offset + 1));
            var win = window.parent;
            if (win != null) {
                if (version >= 4 || win.top.location.replace) {
                    win.top.location.replace(absoluteUrl + "/login?evt=sessionExpired");
                } else {
                    win.top.location.href = absoluteUrl + "/login?evt=sessionExpired";
                }
            } else {
                if (version >= 4 || window.top.location.replace) {
                    window.top.location.replace(absoluteUrl + "/login?evt=sessionExpired");
                } else {
                    window.top.location.href = absoluteUrl + "/login?evt=sessionExpired";
                }
            }
        }
    }
});

$.extend({
    keys: function (obj) {
        var a = [];
        $.each(obj, function (k) {
            a.push(k)
        });
        return a;
    }
});