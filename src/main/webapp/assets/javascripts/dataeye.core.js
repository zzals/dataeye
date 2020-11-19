(function($) {
	if (typeof DE === "undefined") {
		DE = {};
	}
	
	DE.ajax = {};
	DE.ajax.call = function(options, successCallBack, errorCollBack) {
		this.defaults = {
			async:true,
		    dataType:"json",
		    type:"POST",
		    contentType: 'application/json; charset=utf-8',
	    	defaultSuccessCallBack : function(data, textStatus) {
	    		var message = data["message"];
	    		if (opts["async"] && message !== undefined && message !== null && message !== "") {
//	    			DE.box.alert(message);
	    		}
			},
			defaultErrorCallBack : function(response, textStatus, errorThrown) {
                console.debug(response);
                var message = response.responseJSON["message"];
                if (opts["async"] && message !== undefined && message !== null && message !== "") {
	    			DE.box.alert(message);
	    		}
			}
		};
		
		var opts = $.extend({}, this.defaults, options);
		if (opts["data"] != null && typeof (opts["data"]) === "object") {
			opts["data"] = JSON.stringify(opts["data"]);
		}
		if (successCallBack == null) {
			successCallBack = opts.defaultSuccessCallBack;
		}
		if (errorCollBack == null) {
			errorCollBack = opts.defaultErrorCallBack;
		}
		if (opts["url"].indexOf("/") != 0) {
			if (opts["url"].indexOf(".") != 0) {
				opts["url"] = DE.contextPath+"/"+opts["url"];
			}
		}
		
		var response = $.ajax({
			type: opts["type"],
			contentType: opts["contentType"],
			url: opts["url"],
	        async: opts["async"],
	        dataType: opts["dataType"],
	        data: opts["data"],
	        success: successCallBack,
            error: errorCollBack
		});
		
		if (!opts["async"]) {
			return response["responseJSON"];
		}
	};
	DE.ajax.formSubmit = function(options, successCallBack, errorCollBack) {
		this.defaults = {
			async:true,
			cache : false,
		    type:"POST",
		    contentType: 'application/x-www-form-urlencoded; charset=utf-8',
	    	defaultSuccessCallBack : function(data, textStatus) {
			},
			defaultErrorCallBack : function(response, textStatus, errorThrown) {
				console.debug(output);
			}
		};
		
		var opts = $.extend({}, this.defaults, options);
		if (successCallBack == null) {
			successCallBack = opts.defaultSuccessCallBack;
		}
		if (errorCollBack == null) {
			errorCollBack = opts.defaultErrorCallBack;
		}
		if (opts["url"].indexOf("/") != 0) {
			if (opts["url"].indexOf(".") != 0) {
				opts["url"] = DE.contextPath+"/"+opts["url"];
			}
		}

		var response = $.ajax({
			type: opts["type"],
			contentType: opts["contentType"],
			url: opts["url"],
	        async: opts["async"],
	        dataType: opts["dataType"],
	        data: opts["data"],
	        success: successCallBack,
            error: errorCollBack
		});
		
		if (!opts["async"]) {
			return response["responseJSON"];
		}
	};
	
	
	DE.code = {};
	DE.code.load = function() {
		DE.ajax.call(
			{async:true, url:DE.contextPath+"/deresources/findAllCdByGroup"}, 
			function(data){
				DE.code["data"] = data["data"];
				var cdGrpId = "";
				$.each(data["data"], function(index, value){
					if (cdGrpId !== value["cdGrpId"]) {
						eval("DE.code[\""+ value["cdGrpId"] +"\"] = [];")
						cdGrpId = value["cdGrpId"];
					}
					eval("DE.code[\""+ value["cdGrpId"] +"\"]").push(value);
				})
		});
	};
	DE.code.get = function(cdGrpId) {
		return DE.code[cdGrpId];
	};
	DE.code.getGridEditOptions = function(cdGrpId) {
		if (DE.code[cdGrpId] !== undefined) {
			var s = "";
			$.each(DE.code[cdGrpId], function(index, value) {
				if (index) {
					s += ";"+value["cdId"]+":"+value["cdNm"]	
				} else {
					s += value["cdId"]+":"+value["cdNm"];
				}
			});
			return s;
		}
	};
	DE.code.load();
	
	DE.objType = {data:[]};
	DE.objType.load = function() {
		DE.ajax.call(
			{async:true, url:DE.contextPath+"/deresources/objType"}, 
			function(response){
				$.extend(DE.objType.data, response["data"]);
		});
	};
	DE.objType.getName = function(objTypeId) {
		var objTypeNm = "";
		$.each(DE.objType.data, function(index, value){
			if (value["objTypeId"] === objTypeId) {
				objTypeNm = value["objTypeNm"];
				return false;
			}
		})
		return objTypeNm;
	};
	DE.objType.load();
	
	DE.config = {};
	DE.config.load = function() {
		DE.ajax.call(
			{async:false, url:DE.contextPath+"/deresources/dataeye/settings"}, 
			function(response){
				$.extend(DE.config, response["data"]);
		});
	};
	DE.config.load();
	
	DE.i18n = {};
	DE.i18n.loadBundles = function(lang) {
		if (lang == undefined) {
			$.ajaxSetup({async:false});
			$.get(DE.contextPath+"/deresources/locale/lang", function( data ) {
				lang = data;
			});
		}
		
        jQuery.i18n.properties({
            name:'messages', 
            path:DE.contextPath+"/deresources/i18n/", 
            mode:'both',
            language:lang,
            callback: function() {}
	    });
	}; 
	DE.i18n.prop = function(msg) {
		return $.i18n.prop.apply(this, arguments);
	};
	DE.i18n.loadBundles();
	
	DE.content = {};
	DE.content.setHeader = function(target) {
		var menuNm = $(target).text();
		
		var iconCls = "";
		var parents = $(target).parents(".treeview");
		
		$(".content-wrapper .content-header .breadcrumb").empty().prepend("<li class=\"active\">"+menuNm+"</li>");
		if(parents.length == 0) {
			var ownObj = $(target);
			
			iconCls = $(" i:first",ownObj).prop("class");
			$(".content-wrapper .content-header .breadcrumb").html("<li><a href=\"#\"><i class=\""+iconCls+"\"></i>"+menuNm+"</a></li>");
			
			
		} else {
			for ( var i=0, len=parents.length; i<len; i++ ) {
				var menuPath = $("a:first > span:first", parents.eq(i)).text();
				iconCls = $("a:first > i:first", parents.eq(i)).prop("class");
				if (len === (i+1)) {
					$(".content-wrapper .content-header .breadcrumb").prepend("<li><a href=\"#\"><i class=\""+iconCls+"\"></i>"+menuPath+"</a></li>");
				} else {
					$(".content-wrapper .content-header .breadcrumb").prepend("<li><a href=\"#\">"+menuPath+"</a></li>");
				}
			}				
		}

		$(".content-wrapper .content-header h1").html("<i class=\""+iconCls+"\"></i> " + menuNm);
	
	} 
	
	DE.jqgrid = {};
	DE.jqgrid.render = function (grid, opts) {
		var isPaging = true;
		if (opts.isPaging !== undefined) {
			isPaging = grid.getGridParam("isPaging");
		}
		if (!isPaging & !$(grid).jqGrid("getGridParam", "treeGrid")) {
			$.extend(opts, {"loadonce": true,"scroll": -1});
		};
		return $(grid).jqGrid(opts);
	};
	DE.jqgrid.navGrid = function (grid, pager, opts) {
		var defaultNavOpts = {
			navOptions:{
				cloneToTop:false,
				view:false,
				add:false,
				edit:false,
				del:false,
				refresh:false,
				search:false
			}
		};
		opts = $.extend({}, defaultNavOpts, opts);
		var navOptions = $.extend({}, $.jgrid.nav, opts.navOptions);
		var editOptions = $.extend({}, $.jgrid.defaultRegional["edit"], opts.editOptions);
		var addOptions = $.extend({}, $.jgrid.defaultRegional["edit"], opts.addOptions);
		var delOptions = $.extend({}, $.jgrid.defaultRegional["del"], opts.delOptions);
		var searchOptions = $.extend({}, $.jgrid.defaultRegional["search"], opts.searchOptions);
		var viewOptions = $.extend({}, $.jgrid.defaultRegional["view"], opts.viewOptions);
		var navButtonAddOptions = $.extend({}, opts.navButtonAddOptions);
		
		var excelExportButtonOption = {
	    	caption:"",
	    	title:"Excel Export",
	    	buttonicon:"fa fa-file-excel-o", 
	    	onClickButton: function(){
	    		/*$(this).jqGrid("excelExport", {
	    			"url":DE.contextPath+"/jqgrid/excelExport", 
	    			"postData":$(this).jqGrid("getGridParam")
	    		});*/
//	    		DE.ajax.call({"url":"jqgrid/excelExport", data:$(this).jqGrid("getGridParam")});
	    		
	    		var action = DE.contextPath+"/jqgrid/excelExport"
	    		var $frm = $("#gridToExcelExportForm");
		    	var $gridParam = $("#gridParam");
		    	var $file = $("#file");
		    	var $frame =  $("#_frame");
		    	
		    	if ($frm.length == 0) {
			    	$frm =  $("<form id='gridToExcelExportForm' name='gridToExcelExportForm' action='"+action+"' target='_frame' class='gridToExcelExportForm' method='post'></form>").css("display", "none");
			        $frm.appendTo("body");
			    }
		    	
		    	var gridParam = $(this).jqGrid("getGridParam");
		    	if ($("#gridParam", $frm).length === 0) {
		    		$gridParam =  $("<input type='hidden' id='gridParam' name='gridParam'/>");
		    		$gridParam.appendTo($frm);
			    }
		    	$gridParam.attr("value", JSON.stringify(gridParam));

		    	if ($("#file", $frm).length == 0) {
			    	$file =  $("<input type='hidden' id='file' name='file'/>");
			    	$file.appendTo($frm);
			    }
		    	$file.attr("value", "grid_data_"+DE.fn.guid.get()+".xls");
		    	
		    	if ($frame.length === 0) {
		        	$frame =  $("<iframe id='_frame' name='_frame'></iframe>").css("display", "none");
		            $frame.appendTo("body");
		        }
		    	
		    	$("form.gridToExcelExportForm").on("submit", function (e) {
		    	    $.fileDownload($(this).prop('action'), {
		    	        preparingMessageHtml: "엑셀 다운로드 진행중 ...",
		    	        failMessageHtml: "엑셀 다운로드 진행중 오류가 발생하였습니다. 다시 시도하여 주시기 바랍니다.",
		    	        httpMethod: "POST",
		    	        data: $(this).serialize()
		    	    });
		    	    e.preventDefault();
		    	    $("form.gridToExcelExportForm").off("submit");
		    	});
		    	
		    	$frm.submit();
		    	
	    	}, 
	    	position:"last"
	    };
		
		var pagerId = pager;
		if ($.type(pager) === "object") {
			pagerId = pager.prop("id");
		}
		var ro = $(grid).navGrid("#"+pagerId, navOptions, editOptions, addOptions, delOptions, searchOptions, viewOptions);
		$.each(navButtonAddOptions, function(index, value){
			ro.navButtonAdd("#"+pagerId, value);
		});
		ro.navButtonAdd("#"+pagerId, excelExportButtonOption);
		
		if (navOptions.cloneToTop) {
			$.each(navButtonAddOptions, function(index, value){
				ro.navButtonAdd("#"+$(grid).prop("id")+"_toppager", value);
			});
			$(grid).navButtonAdd("#"+$(grid).prop("id")+"_toppager", excelExportButtonOption);
		}
	};
	DE.jqgrid.excelExport = function (grid, opts) {
		
	};
	DE.jqgrid.reload = function (grid, postData, cb) {
		$(grid).jqGrid(
			"setGridParam", {
				"datatype": 'json',
				"postData":postData
			}
		).trigger('reloadGrid');
		DE.jqgrid.resetFilter(grid);
	};
	DE.jqgrid.reloadLocal = function (grid, data, cb) {
   	    $(grid).clearGridData();
   	    $(grid).jqGrid(
			"setGridParam", {
				"datatype": 'local',
				"data": data
			}
		).trigger('reloadGrid');
		DE.jqgrid.resetFilter(grid);
	};
	DE.jqgrid.checkAll = function(source, event) {
		var grid = $(source).closest(".ui-jqgrid-view").find(".ui-jqgrid-bdiv table.ui-jqgrid-btable").jqGrid();
        var CHK = $(source).is(":checked");
        if (CHK) {
            grid.jqGrid("setLabel", "CHK", "<input type='checkbox' id='"+$(source).prop("id")+"' name='"+$(source).prop("name")+"' onclick='javascript:DE.jqgrid.checkAll(this, event);' checked/>");
        } else {
            grid.jqGrid("setLabel", "CHK", "<input type='checkbox' id='"+$(source).prop("id")+"' name='"+$(source).prop("name")+"' onclick='javascript:DE.jqgrid.checkAll(this, event);'/>");
        }
        
        var data = grid.jqGrid("getGridParam", "data");
        for(var i=0; i<data.length; i++) {
            data[i]["CHK"] = CHK.toString();
        }
        grid.jqGrid().trigger('reloadGrid');
    };
    DE.jqgrid.checkedHandler = function(grid, chk) {
    	if (chk == undefined) chk = "CHK";
        var id = grid.prop("id");
        var chkbox = grid.jqGrid().find("td[aria-describedby="+id+"_"+chk+"] > input[type=checkbox]");
        for(var i=0; i<chkbox.length; i++) {
            var rowid = chkbox.eq(i).closest("tr").prop("id");
            var row = grid.getRowData(rowid);
            if (row["CHK"] == false.toString()) {
                chkbox.eq(i).prop("checked", false);
            } else {
                chkbox.eq(i).prop("checked", true);
            }
        }
        
        grid.undelegate("input[type=checkbox]:not([id=chkAll])", "change");
        grid.delegate("input[type=checkbox]:not([id=chkAll])", "change", function(e){
        	var rowid = $(e.target).closest("tr").prop("id");
        	var data = grid.jqGrid("getGridParam", "data");
        	$.each(data, function(index, value){
        		if (value["_id_"] === rowid) {
        			value["CHK"] = $(e.target).is(":checked").toString();
        			return false;
        		}
        	}); 
        });
    };
    DE.jqgrid.colModel = {};
    DE.jqgrid.colModel.objTypeIcon = function(pOpts) {
    	this.defaults = {
    		index:"OBJ_TYPE_ID",
    		name:"OBJ_TYPE_ID",
    		label: "유형",
    		width: 60, 
    		align:"center", 
    		formatter: "objTypeIcon", 
    		fixed:true
		};
    	return $.extend({}, this.defaults, pOpts);
    };
    DE.jqgrid.resetFilter = function (grid) {
    	if ($(grid).jqGrid("getGridParam", "rownumbers") && $(grid).jqGrid("getGridParam", "isFilter")) {
	    	var id = $(grid).prop("id");
			$(".ui-search-toolbar input", "#gbox_"+id).val("");
			$(".ui-search-toolbar", "#gbox_"+id).hide();
    	}
	};
    
	DE.box = {};
	DE.box.alert = function(message, cb) {
		bootbox.alert(message, cb);
	};
	DE.box.confirm = function(message, cb) {
		bootbox.confirm(message, cb);
	}
	DE.box.prompt = function(message, cb) {
		bootbox.prompt(message, cb);
	};
	
	DE.layout = {}
	DE.layout.wc = function(_target) {
		$(_target).layout({
			slidable:true, 
			resizable:false,
			livePaneResizing:true, 
			spacing : {
                open:20,
                closed:20
            }, 
            onresize: function(paneName, paneElement, paneState, paneOptions, layoutName) {
            	console.log('cccccccccccccc onresize') 
            }, 
            onresize_end: function(paneName, paneElement, paneState, paneOptions, layoutName) {
            	console.log('cccccccccccccc onresize') 
            }, 
            west:{
            	size:300, 
                spacing_open:5,
                initHidden: false, 
                resizable:false,
                onresize: function () {
//            		console.log('west onresize') 
                },
                onresize_start: function () {
//                	console.log('west onresize start') 
                },
                onresize_end: function () {
//                	DE.resizer.resize();
                }
            }, 
            center:{
            	onresize: function () {
//            		console.log('center onresize') 
                },
                onresize_start: function () {
//                	console.log('center onresize start') 
                },
                onresize_end: function () {
                	DE.resizer.grid($(arguments[1][0]));
                }
            }
		});
	};

	DE.layout.wc2 = function(_target) {

		$(_target).layout({
			slidable:false, 
			resizable:false,
			livePaneResizing:false, 
			spacing : {
                open:20,
                closed:20
            }, 
            onresize: function(paneName, paneElement, paneState, paneOptions, layoutName) {
            	console.log('cccccccccccccc onresize') 
            }, 
            onresize_end: function(paneName, paneElement, paneState, paneOptions, layoutName) {
            	console.log('cccccccccccccc onresize') 
            }, 
            west:{
            	size:400,  
                spacing_open:5,
                initHidden: false, 
                resizable:false,
            	livePaneResizing:false, 
                onresize: function () {
//            		console.log('west onresize') 
                },
                onresize_start: function () {
//                	console.log('west onresize start') 
                },
                onresize_end: function () {
//                	DE.resizer.resize();
                }
            }, 
            center:{
            	onresize: function () {
//            		console.log('center onresize') 
                },
                onresize_start: function () {
//                	console.log('center onresize start') 
                },
                onresize_end: function () {
                //	DE.resizer.grid($(arguments[1][0]));
                }
            }
		});
	};
	
	DE.layout.wcs = function(_target) {
		$(_target).layout({
			slidable:true, 
			resizable:false,
			livePaneResizing:true, 
			spacing : {
                open:20,
                closed:20
            }, 
            onresize: function(paneName, paneElement, paneState, paneOptions, layoutName) { 
            }, 
            onresize_end: function(paneName, paneElement, paneState, paneOptions, layoutName) { 
            }, 
            west:{
            	size:300, 
                spacing_open:5,
                initHidden: false, 
                resizable:false,
                onresize: function () { 
                },
                onresize_start: function () { 
                },
                onresize_end: function () {
                }
            }, 
            center:{
            	onresize: function () { 
                },
                onresize_start: function () { 
                },
                onresize_end: function () {
                	DE.resizer.grid($(arguments[1][0]));
                },
                childOptions:	{
                	south: {
	                    		size:300, 
	                            spacing_open:5,
	                            initHidden: false, 
	                            resizable:false,
	                            onresize: function () { 
	                            },
	                            onresize_start: function () { 
	                            },
	                            onresize_end: function () {
	                            }
	                            
                           }
                }
            },
           
		});
	};
	
	DE.layout.cs = function(_target) {
		$(_target).layout({
			slidable:true, 
			resizable:false,
			livePaneResizing:true, 
			spacing : {
                open:20,
                closed:20
            }, 
            onresize: function(paneName, paneElement, paneState, paneOptions, layoutName) { 
            }, 
            onresize_end: function(paneName, paneElement, paneState, paneOptions, layoutName) { 
            }, 
            center: {
            	size:300, 
                spacing_open:5,
                initHidden: false, 
                resizable:false,
                onresize: function () { 
                },
                onresize_start: function () { 
                },
                onresize_end: function () {
                }
                
           },
           south:{  
            	size:auto, 
            	onresize: function () { 
                },
                onresize_start: function () { 
                },
                onresize_end: function () {
                //	DE.resizer.grid($(arguments[1][0]));
                }
            },
           
		});
	};
	
	DE.fn = {}
	DE.fn.guid = {
		s4 : function() {
			return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
		},
		get : function() {
			return this.s4() + this.s4() + '-' + this.s4() + '-' + this.s4() + '-' + this.s4() + '-' + this.s4() + this.s4() + this.s4();
		}
	}
	DE.fn.isempty = function(obj) {
		obj = $.trim(obj);
		if(obj === undefined || obj === null || obj === "" || ( obj !== null && typeof obj === "object" && !Object.keys(obj).length )) {
			return true;
		}
		return false;
	},
	DE.fn.htmlEncode = function(value) {
		return $('<div/>').text(value).html();
	},
	DE.fn.htmlDecode = function(value) {
		return $('<div/>').html(value).text();
	}
	
	DE.fn.dateTimePicker = function($) {
		$.datetimepicker({
			format: 'YYYY-MM-DD',
			locale: 'ko',
			extraFormats: [ 'YYYYMMDD' ]
        });
	};

	DE.fn.commaFormatter =function(value) {
		var result= value;
		if(value){
            result = value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
		return result;
    };
	
	DE.fn.gridDestory = function(id) {		
		$.jgrid.gridUnload("#"+id);		
	};
})(jQuery);