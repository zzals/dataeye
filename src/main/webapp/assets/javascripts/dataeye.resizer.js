(function($) {
	if (typeof DE === "undefined") {
		DE = {};
	}
	
	DE.resizer = {};
	DE.resizer.resize = function() {
		var $contentBody = $(".content-body");
		if ($contentBody.length !== 0) {
			var _posTop = $(".content-body").position().top;
			var _footerHeight = $(".main-footer").outerHeight();
			if (_footerHeight === undefined) {
				_footerHeight = 0;
			}
			$contentBody.css("height", "-moz-calc(100vh - "+(_posTop+_footerHeight)+"px)");
			$contentBody.css("height", "-webkit-calc(100vh - "+(_posTop+_footerHeight)+"px)");
			$contentBody.css("height", "calc(100vh - "+(_posTop+_footerHeight)+"px)");
			
			var _wrapper = $contentBody.attr("de-resize-wrapper");
			var _height = $(".content-body").outerHeight();
			var _margin = 20;
			if ("top-bottom" === _wrapper) {
				var $topDiv = $("[de-resize-pos=top]", $contentBody);
				$topDiv.css("height", (_height/2-$topDiv.position().top-_margin)+"px");
				DE.resizer.grid($topDiv);
				var $bottomDiv = $("[de-resize-pos=bottom]", $contentBody);
				$bottomDiv.css("height", (_height/2-$bottomDiv.position().top-_margin)+"px");
				DE.resizer.grid($bottomDiv);
			} else if ("left-right" === _wrapper) {
				var $leftDiv = $("[de-resize-pos=left]", $contentBody);
				$leftDiv.css("height", (_height-$leftDiv.position().top-_margin)+"px")/*.css("overflow-y", "auto")*/;
				if ($("[de-role=grid]", $leftDiv).length !== 0) {
					DE.resizer.grid($leftDiv);
				}
				var $rightDiv = $("[de-resize-pos=right]", $contentBody);
				$rightDiv.css("height", (_height-$leftDiv.position().top-_margin)+"px");
				DE.resizer.grid($rightDiv);
			} else if ("left-rightTab" === _wrapper) {
				var $leftDiv = $("[de-resize-pos=left]", $contentBody);
				$leftDiv.css("height", _height-$leftDiv.position().top+"px")/*.css("overflow-y", "auto")*/;
				if ($("[de-role=grid]", $leftDiv).length !== 0) {
					DE.resizer.grid($leftDiv);
				}
				$("[de-resize-pos=rightTab] .tab-pane").each(function() {
				 	DE.resizer.grid($(this), 15);
				})
			} else if ("middle" === _wrapper) {
				var $middleDiv = $("[de-resize-pos=middle]", $contentBody);
				$middleDiv.css("height", (_height-$middleDiv.position().top-_margin)+"px");
				DE.resizer.grid($middleDiv);
			}
		}
	};
	DE.resizer.grid = function(_parent) {
		var pHeight =$(_parent).outerHeight();
		var pWidth = $(_parent).outerWidth()
    	var $grids = $("[de-role=grid]:not([de-resize=false])", $(_parent));
    	    	
    	$.each($grids, function(index, grid) {
    		var isResize = $grids.jqGrid("getGridParam", "isResize")
    		if (!isResize) return true;
    		
    		var pgHeight = 0;
        	if ($(grid).jqGrid("getGridParam", "pager") !== false) {
        		pgHeight = $($(grid).jqGrid("getGridParam", "pager")).outerHeight();
        	}
        	var tpgHeight = 0;
        	if ($(grid).jqGrid("getGridParam", "toppager") !== false) {
        		tpgHeight = $($(grid).jqGrid("getGridParam", "toppager")).outerHeight();
        	}        	
        	
        	var schtbHeight = 0;
        	var schToolBar = $(".ui-search-toolbar", $("#gbox_"+$(grid).prop("id")));
        	if (schToolBar != undefined && schToolBar.length !== 0 && schToolBar.css("display") !== "none") {
        		schtbHeight = 35;
        	}
        	
        	$(grid).setGridHeight(pHeight-pgHeight-tpgHeight-schtbHeight-35);
        	$(grid).setGridWidth(pWidth);
    	});
	};
	
    $(window).on("resize", function(e) {
    	try {$(document).trigger("autoResize");} catch(e) {}
    	
//    	if ($("section").hasClass("content")) {
//        	var window_height = $(window).height();
//            $("section.content").css('height', window_height-$("section.content").offset().top-$('.main-footer').outerHeight()-10).css("overflow", "hidden");
//        }
    	//setTimeout(function() {DE.resizer.resize()}, 50);
    }).trigger("resize");
})(jQuery);