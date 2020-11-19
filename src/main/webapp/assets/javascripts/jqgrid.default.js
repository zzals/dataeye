(function ($) {
    'use strict';

    $.jgrid.defaults.loadtext='';
    
    $.extend($.jgrid.defaults, {
		datatype: 'local',
		mtype:"POST",
		url:DE.contextPath+"/jqgrid/list",
		styleUI : 'Bootstrap',
	    autoencode:true,
		forceFit:false,		/* col 사이즈 조절시 인접 col 사이즈 조정됨(shrinkToFit 호환안됨) */
		shrinkToFit:true,	/* width에 맞게 col 사이즈가 비율로 조정됨 */
		autowidth:true,
		height:500,   //DE.config.jqgrid.defaultHeight,
		searchOperators:true,
		sortorder: "asc",
		loadonce: false,	/*true:한번만 서버에서 데이터로딩(로딩후 datatype 'local'로 변경됨), */
		scroll: DE.config.jqgrid.scroll,	/* 동적 스클롤 그리드, true: 모든항목 유지, */
	   	scrollrows:true,	/* true: setSelection 사용시 선택한 행이 표시되도록 스크롤 이동 */
	   	rowNum: 100, //DE.config.jqgrid.rowNum,
		rownumbers: true,
	    rownumWidth: 60,
	    rowList:[1, 5, 20, 50, 100, 1000, 10000],
	    toppager: DE.config.jqgrid.topPager,
	    cmTemplate: { title: false, sortable: true },	/* tooltip off */
		altRows: false,
		gridview: true, /*true로 설정하면 스크롤속도 증가함.(treeGrid, subGrid 또는 afterInsertRow 사용못함.) */
	   	editurl: "clientArray",
		viewsortcols : [false,'vertical',true],
		viewrecords: true,	/* true: 레코드수 표시 */
	   	pgbuttons: true,	/* true: 페이징 버튼 표시 */
	   	pginput:false,	/* true: 사용자가 페이지 번호 입력하게 함 */
	   	pagerpos: 'center',	/* 페이징 위치 */
		jsonReader: {
			root: "rows",
			page: "page",
			total: "total",
			records: "records",
			repeatitems: false,
			cell: "cell",
			id: "id",
			userdata: "userdata",
			subgrid: {root:"rows", repeatitems: true, cell:"cell"}
		},
		localReader: {
			root: "rows",
			page: "page",
			total: "total",
			records: "records",
			repeatitems: false,
			cell: "cell",
			id: "id",
			userdata: "userdata",
			subgrid: {root:"rows", repeatitems: true, cell:"cell"}
		},
	   	ajaxGridOptions: {
	   		async:true, 
	   		contentType: "application/json; charset=utf-8"
	   	},
	   	serializeGridData: function (postData) {
			return JSON.stringify(postData);
		},
	    loadError: function (jqXHR, textStatus, errorThrown) {
	    	var message = jqXHR.responseJSON["message"];
	    	if (message !== undefined) {
	    		DE.box.alert(message);
	    	}
	    },
		isPaging:false,
		isFilter:true,
		isResize:true
	});
        
	$.jgrid.nav = {};
	$.extend($.jgrid.nav, {
		edit:DE.config.jqgrid.navEditEnable, 
		add:DE.config.jqgrid.navAddEnable, 
		del:DE.config.jqgrid.navDelEnable, 
		search:DE.config.jqgrid.navSearchEnable,
		refresh:DE.config.jqgrid.navRefreshEnable,
		view:DE.config.jqgrid.navViewEnable,
		cloneToTop:DE.config.jqgrid.topPager
	});
	
	$.jgrid.defaultRegional = $.jgrid.regional["en"] || $.jgrid.regional["kr"];
	$.extend($.jgrid.defaultRegional["edit"], {
	    bSubmit: "저장",
		bCancel: "취소",
		width: 500,
		recreateForm: true,
		closeAfterAdd: true,
		closeAfterEdit: true,
		closeOnEscape: true,
		reloadAfterSubmit:true,
		beforeSubmit : function(postdata, formid) {
			return [true, ""];
		},
		afterSubmit: function(response, postdata) {
			return [true, "", ""];
		},
		errorTextFormat: function(data) {
			if (data["responseJSON"]["errorCode"] == null) {
				return DE.i18n.prop(data["responseJSON"]["message"]);
			} else {
				return DE.i18n.prop(data["responseJSON"]["errorCode"]);
			}
		},
		onClose: function() {}
	});
	$.extend($.jgrid.defaultRegional["del"], {
	    bSubmit: "삭제",
		bCancel: "취소",
		width: 500,
		recreateForm: true,
		reloadAfterSubmit:true,
		afterShowForm: true,
		beforeSubmit : function(postdata, formid) { 
			return [true, ""];
		},
		onclickSubmit:function(params, rowid){},
		afterSubmit: function(response, postdata) {
			return [true, "", ""];
		},
		errorTextFormat: function(data) {
			if (data["responseJSON"]["errorCode"] == null) {
				return DE.i18n.prop(data["responseJSON"]["message"]);
			} else {
				return DE.i18n.prop(data["responseJSON"]["errorCode"]);
			}
		},
		onClose: function() {}
	});
	
	$.extend($.jgrid.defaultRegional["search"], {
    	odata: [{ oper:'eq', text:"="},{ oper:'ne', text:"<>"},{ oper:'lt', text:"작다"},{ oper:'le', text:"작거나 같다"},{ oper:'gt', text:"크다"},{ oper:'ge', text:"크거나 같다"},{ oper:'bw', text:"LIKE 검색어%"},{ oper:'bn', text:"NOT LIKE 검색어%"},/*{ oper:'in', text:"내에 있다"},{ oper:'ni', text:"내에 있지 않다"},*/{ oper:'ew', text:"LIKE %검색어"},{ oper:'en', text:"NOT LIKE %검색어"},{ oper:'cn', text:"LIKE %검색어%"},{ oper:'nc', text:"NOT LIKE '%검색어%'"},{ oper:'nu', text:'IS NULL'},{ oper:'nn', text:'IS NOT NULL'}, {oper:'bt', text:'BETWEEN'}],
    	groupOps: [	{ op: "AND", text: "AND" },	{ op: "OR",  text: "OR" }	],
    	closeAfterSearch : true,
    	multipleSearch: true,
    	showQuery: true,
    	multipleGroup: false
	});
	
	$.extend($.fn.fmatter, {
	    dynamicLink: function (cellValue, options, rowObject) {
	        // href, target, rel, title, onclick
	        // other attributes like media, hreflang, type are not supported currently
	        var op = {url: '#'};
	
	        if (typeof options.colModel.formatoptions !== 'undefined') {
	            op = $.extend({}, op, options.colModel.formatoptions);
	        }
	        if ($.isFunction(op.target)) {
	            op.target = op.target.call(this, cellValue, options.rowId, rowObject, options);
	        }
	        if ($.isFunction(op.url)) {
	            op.url = op.url.call(this, cellValue, options.rowId, rowObject, options);
	        }
	        if ($.isFunction(op.cellValue)) {
	            cellValue = op.cellValue.call(this, cellValue, options.rowId, rowObject, options);
	        }
	        if (cellValue !== null && cellValue === "") {
	        	cellValue = "[N/A]";
	        }
	        if (op.nothingValue !== undefined && cellValue === op.nothingValue) {
	        	return cellValue;
	        } else {
		        if ($.fmatter.isString(cellValue) || $.fmatter.isNumber(cellValue)) {
		        	var encodeCellValue = DE.fn.htmlEncode (cellValue);
		            return '<a class=dynamicLink' +
		                (op.target ? ' target=' + op.target : '') +
		                (op.onClick ? ' onclick="return $.fn.fmatter.dynamicLink.onClick.call(this, arguments[0]);"' : '') +
		                ' href="' + op.url + '">' +
		                (encodeCellValue || '&nbsp;') + '</a>'/*+'<i class="fa fa-copy"></i>'*/;
		        } else {
		            return '&nbsp;';
		        }
	        }
	    },
	    customButton: function (cellValue, options, rowObject) {
	    	var op = {
	    		caption: '저장',
		    	style: '',
		    	value: ''
	    	};
	    	if (typeof options.colModel.formatoptions !== 'undefined') {
	            op = $.extend({}, op, options.colModel.formatoptions);
	        }

			cellValue = op.caption;

			if (op.nothingKey !== undefined && op.nothingValue !== undefined) {
				if (rowObject[op.nothingKey] === op.nothingValue) {
					return "";
				}
			}
			
			return '<input type="button" ' +
	            'value="' + op.caption + '" ' +
	            (op.onClick ? ' onclick="return $.fn.fmatter.dynamicLink.onClick.call(this, arguments[0]);"' : '') +
	            '">';
		},
	    customRadio: function (cellValue, options, rowObject) {
	    	var op = {};
	    	if (typeof options.colModel.formatoptions !== 'undefined') {
	            op = $.extend({}, op, options.colModel.formatoptions);
	        }

			return "<input type=\"radio\" " + "name=\"radio_" + options.gid + "\" " + "\>";
		},
	    objTypeIcon: function (cellValue, options, rowObject) {
	    	return DE.ui.getObjTypeImgTag(cellValue);
		},
		timeToDate : function(cellvalue, options, rowobject) {
			if (cellvalue == -1) {
	    		return "";
	    	} else {
	    		return new Date(cellvalue).format("yyyy-MM-dd HH:mm:ss");
	    	}
	    },
	    dateTimeFormat : function(cellvalue, options, rowobject) {
	    	if (cellvalue) {
	    		return cellvalue.replace(/([0-9]{4})([0-9]{2})([0-9]{2})([0-9]{2})([0-9]{2})([0-9]{2})/, "$1-$2-$3 $4:$5:$6");
	    	} else {
	    		return "";
	    	}
	    }
	});
	$.extend($.fn.fmatter.dynamicLink, {
	    unformat: function (cellValue, options, elem) {
	        var text = $(elem).text();
	        return text === '&nbsp;' ? '' : text;
	    },
	    onClick: function (e) {
	        var $cell = $(this).closest('td'),
	            $row = $cell.closest('tr.jqgrow'),
	            $grid = $row.closest('table.ui-jqgrid-btable'),
	            p,
	            colModel,
	            iCol,
	            rowid;
	
	        if ($grid.length === 1) {
	            p = $grid[0].p;
	            if (p) {
	                iCol = $.jgrid.getCellIndex($cell[0]);
	                colModel = p.colModel;
	                colModel[iCol].formatoptions.onClick.call($grid[0], $row.attr('id'), $row[0].rowIndex, iCol, $cell.text(), e);
	                $grid.jqGrid("setSelection", $row.attr('id'));
	            }
	        }
	        return false;
	    }
	});
	$.extend($.fn.fmatter.customButton, {
		unformat: function (cellValue, options, cellObject) {
			var ret = $('input[type=button]', cellObject).val();
			return ret;
		},
	    onClick: function (e) {
	        console.log("un implement click");
	        return false;
	    }
	});
}(jQuery));

(function($) {
	var oriJqGrid = $.fn.jqGrid;
	var jqGridBinder = function( pin ) {
    	$.fn.jqGrid = oriJqGrid;
    	var obj = $.fn.jqGrid.apply(this, arguments);
    	$.fn.jqGrid = jqGridBinder;
		
    	var noResultMessageDisplay = function(grid) {
			if ($(".ui-jqgrid-bdiv div[id=jqgrid_no_result]", "#gbox_"+$(grid).prop("id")).length === 0) {
    			var noResultDiv = "<div id=\"jqgrid_no_result\" class=\"col-md-12\">";
					noResultDiv += "<div class=\"\">";
					noResultDiv += "<div class=\"no_data\">";  					
					noResultDiv += "<i class=\"fa fa-exclamation-triangle\"></i> 조회된 <span>데이터</span>가 없습니다.";
					noResultDiv += "</div>";
					noResultDiv += "</div>";
					noResultDiv += "</div>";
					$(".ui-jqgrid-bdiv", "#gbox_"+$(grid).prop("id")).append(noResultDiv);
			}
		};
		var noResultMessageRemove = function(grid) {
			$(".ui-jqgrid-bdiv div[id=jqgrid_no_result]", "#gbox_"+$(grid).prop("id")).remove();
		};
		
    	if (typeof pin === 'string') {
			return obj;
		} else {
			obj.bind("jqGridLoadComplete", function(e, data) {
				if ($(this).jqGrid("getGridParam", "treeGrid")) {
	       			if (data === undefined) {
	       				data = $(this).jqGrid("getGridParam", "data");
	       			}
	       			if (data.rows.length === 0 && $(this).jqGrid("getGridParam", "data").length === 0) {
	       				noResultMessageDisplay(this);
	       			} else {
	       				noResultMessageRemove(this);
	       			}
				} else {
	        		//if (data.rows.length === 0) {
	        		//	noResultMessageDisplay(this);
	        		//} else {
	        		//	noResultMessageRemove(this);
	        		//}
				}
			});
			obj.bind("jqGridGridComplete", function(e, data) {
	       		var $grid = $(this);
	       		var isTreeGrid = $grid.jqGrid("getGridParam", "treeGrid");
	       		var isRownumbers = $grid.jqGrid("getGridParam", "rownumbers");
	       		if (!isTreeGrid && isRownumbers) {
		       		var id = $grid.prop("id");
			        	if ($("#de-grid-filter", "#gbox_"+id).length === 0) {
			        		$(this).jqGrid(
		       				'filterToolbar',{
		       					stringResult: true, 
		       					groupOp:'AND',
		       					searchOnEnter: true, 
		       					defaultSearch: 'cn'
		       				}
		       			);
			        		
		        		$(".ui-jqgrid-htable #"+id+"_rn > .ui-th-div", "#gbox_"+id).prepend("<a id=\"de-grid-filter\" href=\"#\"><i class=\"fa fa-filter\" aria-hidden=\"true\"></i></a>");
		        		$("#de-grid-filter", "#gbox_"+id).on("click", function(){
		        			$(".ui-search-toolbar", "#gbox_"+id).toggle();
		        			var gridHeight = parseInt($(".ui-jqgrid-bdiv", '#gbox_'+id).height());
		        			if ($(".ui-search-toolbar", "#gbox_"+id).is(":visible")) {
		        				$grid.setGridHeight(gridHeight-35);
		        			} else {
		        				$grid.setGridHeight(gridHeight+35);
		        			}
		        		});
		        		
		        		$(".ui-search-toolbar input", "#gbox_"+id).val("");
		        		$(".ui-search-toolbar", "#gbox_"+id).toggle();
		       		}
	       		}
	       		
	       		if ($grid.jqGrid("getGridParam", "reccount") === 0) {
	       			noResultMessageDisplay(this);
	       		} else {
	       			noResultMessageRemove(this);
	       		}
			});
			return obj;
        }
    }
    
    $.fn.jqGrid = jqGridBinder;
    
    //[cjsxowls] $.fn.jqGrid binder 문제 발생하여 조치한 내용
    //module begin
    $.jgrid.extend({
	    setSubGrid : function () {
	    	return this.each(function (){
	    		var $t = this, cm, i,
	    		classes = $.jgrid.styleUI[($t.p.styleUI || 'jQueryUI')].subgrid,
	    		suboptions = {
	    			plusicon : classes.icon_plus,
	    			minusicon : classes.icon_minus,
	    			openicon:  classes.icon_open,
	    			expandOnLoad:  false,
	    			delayOnLoad : 50,
	    			selectOnExpand : false,
	    			selectOnCollapse : false,
	    			reloadOnExpand : true
	    		};
	    		$t.p.subGridOptions = $.extend(suboptions, $t.p.subGridOptions || {});
	    		$t.p.colNames.unshift("");
	    		$t.p.colModel.unshift({name:'subgrid',width: $.jgrid.cell_width ?  $t.p.subGridWidth+$t.p.cellLayout : $t.p.subGridWidth,sortable: false,resizable:false,hidedlg:true,search:false,fixed:true});
	    		cm = $t.p.subGridModel;
	    		if(cm[0]) {
	    			cm[0].align = $.extend([],cm[0].align || []);
	    			for(i=0;i<cm[0].name.length;i++) { cm[0].align[i] = cm[0].align[i] || 'left';}
	    		}
	    	});
	    },
	    addSubGridCell :function (pos,iRow) {
	    	var prp='', ic, sid, icb ;
	    	this.each(function(){
	    		prp = this.formatCol(pos,iRow);
	    		sid= this.p.id;
	    		ic = this.p.subGridOptions.plusicon;
	    		icb = $.jgrid.styleUI[(this.p.styleUI || 'jQueryUI')].common;
	    	});
	    	return "<td role=\"gridcell\" aria-describedby=\""+sid+"_subgrid\" class=\"ui-sgcollapsed sgcollapsed\" "+prp+"><a style='cursor:pointer;' class='ui-sghref'><span class='" + icb.icon_base +" "+ic+"'></span></a></td>";
	    },
	    addSubGrid : function( pos, sind ) {
	    	return this.each(function(){
	    		var ts = this;
	    		if (!ts.grid ) { return; }
	    		var base = $.jgrid.styleUI[(ts.p.styleUI || 'jQueryUI')].base,
	    			common = $.jgrid.styleUI[(ts.p.styleUI || 'jQueryUI')].common;
	    		//-------------------------
	    		var subGridCell = function(trdiv,cell,pos)
	    		{
	    			var tddiv = $("<td align='"+ts.p.subGridModel[0].align[pos]+"'></td>").html(cell);
	    			$(trdiv).append(tddiv);
	    		};
	    		var subGridXml = function(sjxml, sbid){
	    			var tddiv, i,  sgmap,
	    			dummy = $("<table class='" + base.rowTable + " ui-common-table'><tbody></tbody></table>"),
	    			trdiv = $("<tr></tr>");
	    			for (i = 0; i<ts.p.subGridModel[0].name.length; i++) {
	    				tddiv = $("<th class='" + base.headerBox+" ui-th-subgrid ui-th-column ui-th-"+ts.p.direction+"'></th>");
	    				$(tddiv).html(ts.p.subGridModel[0].name[i]);
	    				$(tddiv).width( ts.p.subGridModel[0].width[i]);
	    				$(trdiv).append(tddiv);
	    			}
	    			$(dummy).append(trdiv);
	    			if (sjxml){
	    				sgmap = ts.p.xmlReader.subgrid;
	    				$(sgmap.root+" "+sgmap.row, sjxml).each( function(){
	    					trdiv = $("<tr class='" + common.content+" ui-subtblcell'></tr>");
	    					if(sgmap.repeatitems === true) {
	    						$(sgmap.cell,this).each( function(i) {
	    							subGridCell(trdiv, $(this).text() || '&#160;',i);
	    						});
	    					} else {
	    						var f = ts.p.subGridModel[0].mapping || ts.p.subGridModel[0].name;
	    						if (f) {
	    							for (i=0;i<f.length;i++) {
	    								subGridCell(trdiv, $(f[i],this).text() || '&#160;',i);
	    							}
	    						}
	    					}
	    					$(dummy).append(trdiv);
	    				});
	    			}
	    			var pID = $("table:first",ts.grid.bDiv).attr("id")+"_";
	    			$("#"+$.jgrid.jqID(pID+sbid)).append(dummy);
	    			ts.grid.hDiv.loading = false;
	    			$("#load_"+$.jgrid.jqID(ts.p.id)).hide();
	    			return false;
	    		};
	    		var subGridJson = function(sjxml, sbid){
	    			var tddiv,result,i,cur, sgmap,j,
	    			dummy = $("<table class='" + base.rowTable + " ui-common-table'><tbody></tbody></table>"),
	    			trdiv = $("<tr></tr>");
	    			for (i = 0; i<ts.p.subGridModel[0].name.length; i++) {
	    				tddiv = $("<th class='" + base.headerBox + " ui-th-subgrid ui-th-column ui-th-"+ts.p.direction+"'></th>");
	    				$(tddiv).html(ts.p.subGridModel[0].name[i]);
	    				$(tddiv).width( ts.p.subGridModel[0].width[i]);
	    				$(trdiv).append(tddiv);
	    			}
	    			$(dummy).append(trdiv);
	    			if (sjxml){
	    				sgmap = ts.p.jsonReader.subgrid;
	    				result = $.jgrid.getAccessor(sjxml, sgmap.root);
	    				if ( result !== undefined ) {
	    					for (i=0;i<result.length;i++) {
	    						cur = result[i];
	    						trdiv = $("<tr class='" + common.content+" ui-subtblcell'></tr>");
	    						if(sgmap.repeatitems === true) {
	    							if(sgmap.cell) { cur=cur[sgmap.cell]; }
	    							for (j=0;j<cur.length;j++) {
	    								subGridCell(trdiv, cur[j] || '&#160;',j);
	    							}
	    						} else {
	    							var f = ts.p.subGridModel[0].mapping || ts.p.subGridModel[0].name;
	    							if(f.length) {
	    								for (j=0;j<f.length;j++) {
	    									subGridCell(trdiv, cur[f[j]] || '&#160;',j);
	    								}
	    							}
	    						}
	    						$(dummy).append(trdiv);
	    					}
	    				}
	    			}
	    			var pID = $("table:first",ts.grid.bDiv).attr("id")+"_";
	    			$("#"+$.jgrid.jqID(pID+sbid)).append(dummy);
	    			ts.grid.hDiv.loading = false;
	    			$("#load_"+$.jgrid.jqID(ts.p.id)).hide();
	    			return false;
	    		};
	    		var populatesubgrid = function( rd )
	    		{
	    			var sid,dp, i, j;
	    			sid = $(rd).attr("id");
	    			dp = {nd_: (new Date().getTime())};
	    			dp[ts.p.prmNames.subgridid]=sid;
	    			if(!ts.p.subGridModel[0]) { return false; }
	    			if(ts.p.subGridModel[0].params) {
	    				for(j=0; j < ts.p.subGridModel[0].params.length; j++) {
	    					for(i=0; i<ts.p.colModel.length; i++) {
	    						if(ts.p.colModel[i].name === ts.p.subGridModel[0].params[j]) {
	    							dp[ts.p.colModel[i].name]= $("td:eq("+i+")",rd).text().replace(/\&#160\;/ig,'');
	    						}
	    					}
	    				}
	    			}
	    			if(!ts.grid.hDiv.loading) {
	    				ts.grid.hDiv.loading = true;
	    				$("#load_"+$.jgrid.jqID(ts.p.id)).show();
	    				if(!ts.p.subgridtype) { ts.p.subgridtype = ts.p.datatype; }
	    				if($.isFunction(ts.p.subgridtype)) {
	    					ts.p.subgridtype.call(ts, dp);
	    				} else {
	    					ts.p.subgridtype = ts.p.subgridtype.toLowerCase();
	    				}
	    				switch(ts.p.subgridtype) {
	    					case "xml":
	    					case "json":
	    					$.ajax($.extend({
	    						type:ts.p.mtype,
	    						url: $.isFunction(ts.p.subGridUrl) ? ts.p.subGridUrl.call(ts, dp) : ts.p.subGridUrl,
	    						dataType:ts.p.subgridtype,
	    						data: $.isFunction(ts.p.serializeSubGridData)? ts.p.serializeSubGridData.call(ts, dp) : dp,
	    						complete: function(sxml) {
	    							if(ts.p.subgridtype === "xml") {
	    								subGridXml(sxml.responseXML, sid);
	    							} else {
	    								subGridJson($.jgrid.parse(sxml.responseText),sid);
	    							}
	    							sxml=null;
	    						}
	    					}, $.jgrid.ajaxOptions, ts.p.ajaxSubgridOptions || {}));
	    					break;
	    				}
	    			}
	    			return false;
	    		};
	    		var _id, pID,atd, nhc=0, bfsc, $r;
	    		$.each(ts.p.colModel,function(){
	    			if(this.hidden === true || this.name === 'rn' || this.name === 'cb') {
	    				nhc++;
	    			}
	    		});
	    		var len = ts.rows.length, i=1,hsret, ishsg = $.isFunction(ts.p.isHasSubGrid);
	    		if( sind !== undefined && sind > 0) {
	    			i = sind;
	    			len = sind+1;
	    		}
	    		while(i < len) {
	    			if($(ts.rows[i]).hasClass('jqgrow')) {
	    				if(ts.p.scroll) {
	    					$(ts.rows[i].cells[pos]).off('click');
	    				}
	    				hsret = null;
	    				if(ishsg) {
	    					var hsret = ts.p.isHasSubGrid.call(ts, ts.rows[i].id);
	    				}
	    				if(hsret === false) {
	    					ts.rows[i].cells[pos].innerHTML = "";
	    				} else {
	    					$(ts.rows[i].cells[pos]).on('click', function() {
	    						var tr = $(this).parent("tr")[0];
	    						pID = ts.p.id;
	    						_id = tr.id;
	    						$r = $("#" + pID + "_" + _id + "_expandedContent");
	    						if($(this).hasClass("sgcollapsed")) {
	    							bfsc = $(ts).triggerHandler("jqGridSubGridBeforeExpand", [pID + "_" + _id, _id]);
	    							bfsc = (bfsc === false || bfsc === 'stop') ? false : true;
	    							if(bfsc && $.isFunction(ts.p.subGridBeforeExpand)) {
	    								bfsc = ts.p.subGridBeforeExpand.call(ts, pID+"_"+_id,_id);
	    							}
	    							if(bfsc === false) {return false;}
	
	    							if(ts.p.subGridOptions.reloadOnExpand === true || ( ts.p.subGridOptions.reloadOnExpand === false && !$r.hasClass('ui-subgrid') ) ) {
	    								atd = pos >=1 ? "<td colspan='"+pos+"'>&#160;</td>":"";
	    								$(tr).after( "<tr role='row' id='" + pID + "_" + _id + "_expandedContent" + "' class='ui-subgrid ui-sg-expanded'>"+atd+"<td class='" + common.content +" subgrid-cell'><span class='" + common.icon_base +" "+ts.p.subGridOptions.openicon+"'></span></td><td colspan='"+parseInt(ts.p.colNames.length-1-nhc,10)+"' class='" + common.content +" subgrid-data'><div id="+pID+"_"+_id+" class='tablediv'></div></td></tr>" );
	    								$(ts).triggerHandler("jqGridSubGridRowExpanded", [pID + "_" + _id, _id]);
	    								if( $.isFunction(ts.p.subGridRowExpanded)) {
	    									ts.p.subGridRowExpanded.call(ts, pID+"_"+ _id,_id);
	    								} else {
	    									populatesubgrid(tr);
	    								}
	    							} else {
	    								$r.show().removeClass("ui-sg-collapsed").addClass("ui-sg-expanded");
	    							}
	    							$(this).html("<a style='cursor:pointer;' class='ui-sghref'><span class='" + common.icon_base +" "+ts.p.subGridOptions.minusicon+"'></span></a>").removeClass("sgcollapsed").addClass("sgexpanded");
	    							if(ts.p.subGridOptions.selectOnExpand) {
	    								$(ts).jqGrid('setSelection',_id);
	    							}
	    						} else if($(this).hasClass("sgexpanded")) {
	    							bfsc = $(ts).triggerHandler("jqGridSubGridRowColapsed", [pID + "_" + _id, _id]);
	    							bfsc = (bfsc === false || bfsc === 'stop') ? false : true;
	    							if( bfsc &&  $.isFunction(ts.p.subGridRowColapsed)) {
	    								bfsc = ts.p.subGridRowColapsed.call(ts, pID+"_"+_id,_id );
	    							}
	    							if(bfsc===false) {return false;}
	    							if(ts.p.subGridOptions.reloadOnExpand === true) {
	    								$r.remove(".ui-subgrid");
	    							} else if($r.hasClass('ui-subgrid')) { // incase of dynamic deleting
	    								$r.hide().addClass("ui-sg-collapsed").removeClass("ui-sg-expanded");
	    							}
	    							$(this).html("<a style='cursor:pointer;' class='ui-sghref'><span class='"+common.icon_base +" "+ts.p.subGridOptions.plusicon+"'></span></a>").removeClass("sgexpanded").addClass("sgcollapsed");
	    							if(ts.p.subGridOptions.selectOnCollapse) {
	    								$(ts).jqGrid('setSelection',_id);
	    							}
	    						}
	    						return false;
	    					});
	    				}
	    			}
	    			i++;
	    		}
	    		if(ts.p.subGridOptions.expandOnLoad === true) {
	    			$(ts.rows).filter('.jqgrow').each(function(index,row){
	    				$(row.cells[0]).click();
	    			});
	    		}
	    		ts.subGridXml = function(xml,sid) {subGridXml(xml,sid);};
	    		ts.subGridJson = function(json,sid) {subGridJson(json,sid);};
	    	});
	    },
	    expandSubGridRow : function(rowid) {
	    	return this.each(function () {
	    		var $t = this;
	    		if(!$t.grid && !rowid) {return;}
	    		if($t.p.subGrid===true) {
	    			var rc = $(this).jqGrid("getInd",rowid,true);
	    			if(rc) {
	    				var sgc = $("td.sgcollapsed",rc)[0];
	    				if(sgc) {
	    					$(sgc).trigger("click");
	    				}
	    			}
	    		}
	    	});
	    },
	    collapseSubGridRow : function(rowid) {
	    	return this.each(function () {
	    		var $t = this;
	    		if(!$t.grid && !rowid) {return;}
	    		if($t.p.subGrid===true) {
	    			var rc = $(this).jqGrid("getInd",rowid,true);
	    			if(rc) {
	    				var sgc = $("td.sgexpanded",rc)[0];
	    				if(sgc) {
	    					$(sgc).trigger("click");
	    				}
	    			}
	    		}
	    	});
	    },
	    toggleSubGridRow : function(rowid) {
	    	return this.each(function () {
	    		var $t = this;
	    		if(!$t.grid && !rowid) {return;}
	    		if($t.p.subGrid===true) {
	    			var rc = $(this).jqGrid("getInd",rowid,true);
	    			if(rc) {
	    				var sgc = $("td.sgcollapsed",rc)[0];
	    				if(sgc) {
	    					$(sgc).trigger("click");
	    				} else {
	    					sgc = $("td.sgexpanded",rc)[0];
	    					if(sgc) {
	    						$(sgc).trigger("click");
	    					}
	    				}
	    			}
	    		}
	    	});
	    }
    });
}(jQuery));