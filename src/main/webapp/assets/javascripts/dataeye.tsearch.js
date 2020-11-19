const koreg = /[\u3131-\u314e|\u314f-\u3163|\uac00-\ud7a3]/;
const compkor = /[\uac00-\ud7a3]/;

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
         * @literal    tsearch literal
         * @memberOf module:DE.tsearch.object
         * @return    {object}    DE.tsearch
         * @description        새로운 DE.tsearch 인스턴스를 생성한다.
         */
        DE.tsearch = {
        	findDashboardObjTypes: function(_callback) {
            	var rsp = DE.ajax.call({"async":false, "url":"tsearch?oper=findDashboardObjTypes"});
        		var result = [];
        		console.log(rsp);
        		$.each(rsp .hits.hits, function(index, value) {
        			var checked = index == 0 ? "checked" : "";
        			var label = $("<label>").appendTo("#tsearchObjTypeGroup");
        			label.append("<input type=\"radio\" name=\"tsearchObjType\" class=\"minimal-red\" value=\""+value["_source"]["obj_type_id"]+"\" objTypeNm=\""+value["_source"]["obj_type_nm"]+"\" "+checked+">"+value["_source"]["obj_type_nm"]);
        		});
        		
        		$('input[name="iCheck"]').on('ifClicked', function (event) {
        	        alert("You clicked " + this.value);
        	    });
            },
            findDashboardTreeMap: function(_callback) {
            	var objTypeId = $("input[name=tsearchObjType]:checked", "#tsearchObjTypeGroup").val();
            	var rsp = DE.ajax.call({"async":false, "url":"tsearch?oper=findDashboardTreeMap", "data":{"objTypeId":objTypeId}});
        		return rsp;
            },
            renderingTreemap: function($radio) {
            	var defaults = {
            	    margin: {top: 24, right: 0, bottom: 0, left: 0},
            	    rootname: "TOP",
            	    format: ",d",
            	    title: "",
            	    width: 1250,
            	    height: 500
            	};

            	function main(o, data) {
            	  var root,
            	      opts = $.extend(true, {}, defaults, o),
            	      formatNumber = d3.format(opts.format),
            	      rname = opts.rootname,
            	      margin = opts.margin,
            	      theight = 36 + 16;

            	  $('#tsearch-treemap').empty();
            	  $('#tsearch-treemap').width(opts.width).height(opts.height);
            	  var width = opts.width - margin.left - margin.right,
            	      height = opts.height - margin.top - margin.bottom - theight,
            	      transitioning;
            	  
            	  var color = d3.scale.category20b();
            	  
            	  var x = d3.scale.linear()
            	      .domain([0, width])
            	      .range([0, width]);
            	  
            	  var y = d3.scale.linear()
            	      .domain([0, height])
            	      .range([0, height]);

            	  var treemap = d3.layout.treemap()
            	  .value(value)
            	  .children(function(d, depth) { 
            		  return depth ? null : d._children; 
            		})
            	  .sort(function(a, b) { 
            		  return a.doc_count - b.doc_count; 
            	  }).ratio(height / width * 0.5 * (1 + Math.sqrt(5)))
            	  .round(false);
            	  
            	  var svg = d3.select("#tsearch-treemap").append("svg")
            	      .attr("width", width + margin.left + margin.right)
            	      .attr("height", height + margin.bottom + margin.top)
            	      .style("margin-left", -margin.left + "px")
            	      .style("margin.right", -margin.right + "px")
            	    .append("g")
            	      .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
            	      .style("shape-rendering", "crispEdges");
            	  
            	  var grandparent = svg.append("g")
            	      .attr("class", "grandparent");
            	  
            	  grandparent.append("rect")
            	      .attr("y", -margin.top)
            	      .attr("width", width)
            	      .attr("height", margin.top);
            	  
            	  grandparent.append("text")
            	      .attr("x", 6)
            	      .attr("y", 6 - margin.top)
            	      .attr("dy", ".75em");

            	  if (opts.title) {
            	    $("#tsearch-treemap").prepend("<p class='title'>" + opts.title + "</p>");
            	  }
            	  if (data instanceof Array) {
            	    root = { objId: rname, children: data };
            	  } else {
            	    root = data;
            	  }
            	    
            	  initialize(root);
            	  accumulate(root);
            	  layout(root);
            	  display(root);

            	  if (window.parent !== window) {
            	    var myheight = document.documentElement.scrollHeight || document.body.scrollHeight;
            	    window.parent.postMessage({height: myheight}, '*');
            	  }

            	  function initialize(root) {
            	    root.x = root.y = 0;
            	    root.dx = width;
            	    root.dy = height;
            	    root.depth = 0;
            	    root.doc_count = 0;
            	  }

            	  // Aggregate the children for internal nodes. This is normally done by the
            	  // treemap layout, but not here because of our custom implementation.
            	  // We also take a snapshot of the original children (_children) to avoid
            	  // the children being overwritten when when layout is computed.
            	  function accumulate(d) {
            			return (d._children = d.children)
            		      	? d.doc_count = d.children.reduce(
            		    			function(p, v) { 
            		    				return p + accumulate(v); 
            		    			}, 0)
            		    	: d.doc_count ? d.doc_count : 0;
            	  }

            	  // Compute the treemap layout recursively such that each group of siblings
            	  // uses the same size (1×1) rather than the dimensions of the parent cell.
            	  // This optimizes the layout for the current zoom state. Note that a wrapper
            	  // object is created for the parent node for each group of siblings so that
            	  // the parent’s dimensions are not discarded as we recurse. Since each group
            	  // of sibling was laid out in 1×1, we must rescale to fit using absolute
            	  // coordinates. This lets us use a viewport to zoom.
            	  function layout(d) {
            	    if (d._children) {
            	      treemap.nodes({_children: d._children});
            	      d._children.forEach(function(c) {
            	        c.x = d.x + c.x * d.dx;
            	        c.y = d.y + c.y * d.dy;
            	        c.dx *= d.dx;
            	        c.dy *= d.dy;
            	        c.parent = d;
            	        layout(c);
            	      });
            	    }
            	  }

            	  function display(d) {
            	    	grandparent
            	        	.datum(d.parent)
            	        	.on("click", transition)  //treemap header click
            	      		.select("text")
            	        	.text(name(d));

            	    var g1 = svg.insert("g", ".grandparent")
            	        .datum(d)
            	        .attr("class", "depth");

            	    var g = g1.selectAll("g")
            	        .data(d._children)
            	      	.enter().append("g")
            	      	.on("click", function(d){
            	    	  	if (d._children) {
            	    	  		transition(d)	
            	    	  	} else {
            	    	  		//tsearch(d)
            	    	  	}
            	    	  	
            			});

            	    g.filter(function(d) { return d._children; })
            	        .classed("children", true);

            	    var children = g.selectAll(".child")
            			.data(function(d) { return d._children || [d]; })
            	      	.enter().append("g");
            		
            	    children.append("rect")
            	        .attr("class", "child")
            	        .call(rect)
            	      .append("title")
            	        .text(function(d) {
            	        	return d.objNm + " (" + formatNumber(d.doc_count) + ")"; 
            	        });

            	    children.append("text")
            	        .attr("class", "ctext")
            	        .text(function(d) { return d.objNm; })
            	        .call(text2);

            	    g.append("rect")
            	        .attr("class", "parent")
            	        .call(rect);

            	    var t = g.append("text")
            	        .attr("class", "ptext")
            	        .attr("dy", ".75em")
            	        

            	    t.append("tspan")
            	        .text(function(d) { return d.objNm; });
            	    t.append("tspan")
            	        .attr("dy", "1.0em")
            	        .text(function(d) { return formatNumber(d.doc_count); });
            	    t.call(text);

            	    g.selectAll("rect")
            	        .style("fill", function(d) { return color(d.objId); });
            		
            	    function transition(d) {
            	      if (transitioning || !d) return;
            	      transitioning = true;

            	      var g2 = display(d),
            	          t1 = g1.transition().duration(1000),
            	          t2 = g2.transition().duration(1000);
            	      g2.on
            	      // Update the domain only after entering new elements.
            	      x.domain([d.x, d.x + d.dx]);
            	      y.domain([d.y, d.y + d.dy]);

            	      // Enable anti-aliasing during the transition.
            	      svg.style("shape-rendering", null);

            	      // Draw child nodes on top of parent nodes.
            	      svg.selectAll(".depth").sort(function(a, b) { return a.depth - b.depth; });

            	      // Fade-in entering text.
            	      g2.selectAll("text").style("fill-opacity", 0);

            	      // Transition to the new view.
            	      t1.selectAll(".ptext").call(text).style("fill-opacity", 0);
            	      t1.selectAll(".ctext").call(text2).style("fill-opacity", 0);
            	      t2.selectAll(".ptext").call(text).style("fill-opacity", 1);
            	      t2.selectAll(".ctext").call(text2).style("fill-opacity", 1);
            	      t1.selectAll("rect").call(rect);
            	      t2.selectAll("rect").call(rect);

            	      // Remove the old node when the transition is finished.
            	      t1.remove().each("end", function() {
            	        svg.style("shape-rendering", "crispEdges");
            	        transitioning = false;
            	      });
            	    }
            	    
            	    return g;
            	  }

            	  function text(text) {
            	    text.selectAll("tspan")
            	        .attr("x", function(d) { return x(d.x) + 6; })
            	    text.attr("x", function(d) { return x(d.x) + 6; })
            	        .attr("y", function(d) { return y(d.y) + 6; })
            	        .style("opacity", function(d) { return this.getComputedTextLength() < x(d.x + d.dx) - x(d.x) ? 1 : 0; });
            	  }

            	  function text2(text) {
            	    text.attr("x", function(d) { return x(d.x + d.dx) - this.getComputedTextLength() - 6; })
            	        .attr("y", function(d) { return y(d.y + d.dy) - 6; })
            	        .style("opacity", function(d) { return this.getComputedTextLength() < x(d.x + d.dx) - x(d.x) ? 1 : 0; });
            	  }

            	  function rect(rect) {
            	    rect.attr("x", function(d) { return x(d.x); })
            	        .attr("y", function(d) { return y(d.y); })
            	        .attr("width", function(d) { return x(d.x + d.dx) - x(d.x); })
            	        .attr("height", function(d) { return y(d.y + d.dy) - y(d.y); });
            	  }

            	  function value(d) {
            	    return d.doc_count;
            	  }
            	  
            	  function name(d) {
            	    return d.parent
            	        ? name(d.parent) + " / " + d.objNm + " (" + formatNumber(d.doc_count) + ")"
            	        : d.objNm + " (" + formatNumber(d.doc_count) + ")";
            	  }
            	}
            	
        		var objTypeId = $radio.val();
        		var objTypeNm = $radio.attr("objTypeNm");
        	    main({/*title: "World Population"*/}, {objNm: objTypeNm, children: DE.ajax.call({"async":false, "url":"tsearch?oper=findDashboardTreeMap", "data":{"objTypeId":objTypeId}})});
            },
            searchMain: function(_type, _keyword) {
            	var post = {
          	  		"viewId": "portal/tsearch",
          	  		"data": JSON.stringify({"type":_type, "keyword":_keyword})
          	  	};
          	  	$(".content-wrapper").load(DE.contextPath+"/portal/view", post, function(response, status, xhr) {
          	  		$(document).off("autoResize");
          	  		if (status === "error") {
          	  			var rsp = null;
          	  			var message = "";
          	  			try {
          	  				rsp = JSON.parse(xhr.responseText);
          	  				message = rsp["message"];
          	  			} catch(e) {}
          	  			if (message === "") {
          	  				DE.box.alert(DE.i18n.prop("err.message.undefined"));
          	  			} else {
          	  				DE.box.alert(message);
          	  			}
          	  		} else {
    	      	  		$(window).trigger("resize");
          	  		}
          	  	});
            },
            autocomplete: function(el, btn) {
    			$(el).autocomplete({
    				source: function( request, response ) {
    					$.ajax({
    			            url: DE.contextPath+"/tsearch?oper=suggest",
    			            contentType: "application/json; charset=utf-8",
    			            data: JSON.stringify({ "keyword" : request["term"] }),
    			            dataType: "json",
    			            type:"POST",
    					    success: function( data ) {
    					    	console.log(data);
    					    	response( $.map( data["hits"]["hits"], function( item ) {
    			            		return {
    									label: item["highlight"]["__text"][0],
    									value: item["_source"]["__text"]
    								}
    			                }));
    			            }
    			        });
    			    },
    			    minLength: 1,
    			    select: function( event, ui ) {
    			    	$(event.target).val(ui.item.value);
    			    	$(btn).trigger("click");
    			    	return false;
    			    },
    			    open: function() {
    			    	$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
    			    },
    			    close: function() {
    			    	$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
    			    },
    			    error: function(xhr, ajaxOptions, thrownError){ alert(thrownError);  alert(xhr.responseText); }
    			}).autocomplete("instance")._renderItem = function( ul, item ) {
    			    return $( "<li style='cursor:hand; cursor:pointer;'></li>" )
    			        .data( "item.autocomplete", item )
    			        .append("<a href=\"#\">"  + unescape(item.label) + "</a>")
    			    .appendTo( ul );
    			};
            },
            makeSearchTab: function(type) {
            	$("#tsearch-header").empty();
            	if (type === "ALL") {
	        		$("#tsearch-header").append("<li class=\"active\"><a href=\"#ALL_SEARCH\" objTypeId=\"ALL\" objTypeNm=\"전체\" data-toggle=\"tab\">전체</a></li>");
	        		$("#tsearch-content").append("<div class=\"tab-pane active\" id=\"ALL_SEARCH\">");
            	} else {
            		$("#tsearch-header").append("<li><a href=\"#ALL_SEARCH\" objTypeId=\"ALL\" objTypeNm=\"전체\" data-toggle=\"tab\">전체</a></li>");
	        		$("#tsearch-content").append("<div class=\"tab-pane\" id=\"ALL_SEARCH\">");
            	}
        		var rsp = DE.ajax.call({"async":false, "url":"tsearch?oper=getTabCategory"});
        		$.each(rsp, function(index, value){
        			var objTypeId = value["objTypeId"];
        			var objTypeNm = value["objTypeNm"];
        			if (type === objTypeId) {
        				$("#tsearch-header").append("<li class=\"active\"><a href=\"#tab_"+objTypeId+"\" objTypeId=\""+objTypeId+"\" objTypeNm=\""+objTypeNm+"\" data-toggle=\"tab\">"+objTypeNm+"</a></li>");
            			$("#tsearch-content").append("<div class=\"tab-pane active\" id=\"tab_"+objTypeId+"\"></div>");
        			} else {
	        			$("#tsearch-header").append("<li><a href=\"#tab_"+objTypeId+"\" objTypeId=\""+objTypeId+"\" objTypeNm=\""+objTypeNm+"\" data-toggle=\"tab\">"+objTypeNm+"</a></li>");
	        			$("#tsearch-content").append("<div class=\"tab-pane\" id=\"tab_"+objTypeId+"\"></div>");
        			}
        		});
        		
        		// $("#tsearch-header").append("<li class=\"pull-right\"><a href=\"#\" class=\"text-muted\"><i class=\"fa fa-gear\"></i></a></li>");
            },            
            msearch: function(type, keyword, page) {
            	$("#tSearch").data({"keyword":keyword})
            	var post = {
          	  		"type": type,
          	  		"keyword": keyword,
          	  		"page": page
          	  	};
            	var rsp = DE.ajax.call({"async":false, "url":"tsearch?oper=msearch", "data":post});
            	
            	//total count 
            	var totCnt = 0;
            	$.each(rsp["responses"], function(index, value){
            		totCnt += value["hits"]["total"];
            	});
            	
            	//tab search count setting
            	var pagingTotCnt = 1;
            	$.each($("#tsearch-header li a[objTypeId]"), function(index, value) {
            		var objTypeId = $(value).attr("objTypeId");
            		if (objTypeId === "ALL") {
            			var txt = $(value).attr("objTypeNm")+"("+totCnt+")";
            			$(value).text(txt);
            		} else {
            			var cnt = rsp["responses"][index-1]["hits"]["total"];
            			var txt = $(value).attr("objTypeNm")+"("+cnt+")";
            			$(value).text(txt);
            			
            			if (objTypeId === type && cnt !== 0) {
            				pagingTotCnt = cnt;
            			}
            		}
            	});

            	//search result
            	var activeTab = $("ul#tsearch-header li.active");
            	var activeTabId = $("a", activeTab).attr("href");
            	var activeObjTypeId = $("a", activeTab).attr("objTypeId");
            	var activeObjTypeNm = $("a", activeTab).attr("objTypeNm");
            	var keyword = $("#tSearch").val();
            	
        		$(activeTabId, "#tsearch-content").empty();
        		var $content = $("<div class=\"col-xs-12\">").appendTo($(activeTabId, "#tsearch-content"));
            	$.each($("#tsearch-header li a[objTypeId][objTypeId!=ALL]"), function(index, value) {
            		var objTypeId =  $(value).attr("objTypeId");
            		if (activeObjTypeId !== "ALL" && type !== objTypeId) return true;
                		
            		var objTypeNm =  $(value).attr("objTypeNm");
            		var cnt = rsp["responses"][index]["hits"]["total"];

            		var $box       = $("    <div class=\"box\">");
            		var $boxHeader = $("        <div class=\"box-header\">");
            		                 $("            <h5 class=\"box-title\"><i class=\"fa fa-quote-left\" style=\"font-size:8px; vertical-align:top; color:#bfbfbf;\"></i> "+objTypeNm+" <i class=\"fa fa-quote-right\" style=\"font-size:8px; vertical-align:top; color:#bfbfbf;\"></i><small><i class=\"fa fa-search\" aria-hidden=\"true\"></i>\""+keyword+"\" 에 관한  <label>"+cnt+"</label>개의 검색 결과를 찾았습니다.</small></h5>").appendTo($boxHeader);
            		if ("ALL" === type) {
            		                 $("            <button id=\"btnResultMore\" objTypeId=\""+objTypeId+"\" type=\"button\" class=\"search_more_btn pull-right\"><span class=\"caret\"></span> 검색결과 더보기</button>").appendTo($boxHeader);
            		}
			            
            		var $boxBody   = $("        <div class=\"box-body\">");
            		    $box.append($boxHeader);
            		    $box.append($boxBody);
            		    
            		    $content.append($box);
            		
            		var getObjNm = function(value) {
            			if (value["highlight"]["obj_nm"] == undefined) {
            				return value["_source"]["obj_nm"];
            			} else {
            				return value["highlight"]["obj_nm"];
            			}
            		};
            		var getObjDesc = function(value) {
            			if (value["highlight"]["obj_desc"] == undefined) {
            				return value["_source"]["obj_desc"] === null ? "N/A" : value["_source"]["obj_desc"];
            			} else {
            				return value["highlight"]["obj_desc"];
            			}
            		};
            		if (rsp["responses"][index]["hits"]["total"] === 0) {            			
            			var $noresult = $("<div class=\"noresult-out noresult-out-info\">");
            			                $("<h4><i class=\"fa  fa-exclamation-triangle\"></i>\""+keyword+"\" 에 대한 검색 결과가 없습니다.</h4>").appendTo($noresult);
            			$noresult.appendTo($boxBody);
            		} else {
            			if ("010102L" === objTypeId) {	//보고서
                			$.each(rsp["responses"][index]["hits"]["hits"], function(index, value){
                				var objId = value["_source"]["obj_id"];
                				var objNm = getObjNm(value);
                				var chgDt = value["_source"]["chg_dt"] === null ? "" : value["_source"]["chg_dt"].substring(0,10);
                				var objDesc = getObjDesc(value);
                				var pathObjNm = value["_source"]["path_obj_nm"] === null ? "N/A" : value["_source"]["path_obj_nm"];
                				var atrVal103 = value["_source"]["atr_val_103"] === null ? "N/A" : value["_source"]["atr_val_103"];
                				
	                			var $itemBox       = $("    <div class=\"item-box\">");
		                		var $itemBoxHeader = $("        <div class=\"item-box-header\">");
				                                     $("            <h5 class=\"item-box-title\"><a href=\"javascript:void(0);\" objTypeId=\""+objTypeId+"\" objId=\""+objId+"\">"+objNm+"</a><small>"+chgDt+"</small></h5>").appendTo($itemBoxHeader);
				                var $itemBoxBody   = $("        <div class=\"item-box-body\">");
				                                     $("            <strong>[설명/목적]</strong>").appendTo($itemBoxBody);
				                                     $("            <p class=\"text-muted\">"+objDesc+"</p>").appendTo($itemBoxBody);
				                                     $("            <strong>[주제영역]</strong>").appendTo($itemBoxBody);
				                                     $("            <p class=\"text-muted\">"+pathObjNm+"</p>").appendTo($itemBoxBody);
				                                     $("            <strong>[활용목적]</strong>").appendTo($itemBoxBody);
				                                     $("            <p class=\"text-muted\">"+atrVal103+"</p>").appendTo($itemBoxBody);
				                    $itemBox.append($itemBoxHeader);
				                    $itemBox.append($itemBoxBody);

				                    $boxBody.append($itemBox);
                			});
            			} else if ("010104L" === objTypeId) {	//지표
                			$.each(rsp["responses"][index]["hits"]["hits"], function(index, value){
                				var objId = value["_source"]["obj_id"];
                				var objNm = getObjNm(value);
                				var chgDt = value["_source"]["chg_dt"] === null ? "" : value["_source"]["chg_dt"].substring(0,10);
                				var objDesc = getObjDesc(value);
                				var pathObjNm = value["_source"]["path_obj_nm"] === null ? "N/A" : value["_source"]["path_obj_nm"];
                				var atrVal101 = value["_source"]["atr_val_101"] === null ? "N/A" : value["_source"]["atr_val_101"];
                				
	                			var $itemBox       = $("    <div class=\"item-box\">");
		                		var $itemBoxHeader = $("        <div class=\"item-box-header\">");
                                                     $("            <h5 class=\"item-box-title\"><a href=\"javascript:void(0);\" objTypeId=\""+objTypeId+"\" objId=\""+objId+"\">"+objNm+"</a><small>"+chgDt+"</small></h5>").appendTo($itemBoxHeader);
				                var $itemBoxBody   = $("        <div class=\"item-box-body\">");
				                                     $("            <strong>[설명]</strong>").appendTo($itemBoxBody);
				                                     $("            <p class=\"text-muted\">"+objDesc+"</p>").appendTo($itemBoxBody);
				                                     $("            <strong>[주제영역]</strong>").appendTo($itemBoxBody);
				                                     $("            <p class=\"text-muted\">"+pathObjNm+"</p>").appendTo($itemBoxBody);
				                                     $("            <strong>[계산식]</strong>").appendTo($itemBoxBody);
				                                     $("            <p class=\"text-muted\">"+atrVal101+"</p>").appendTo($itemBoxBody);
				                    $itemBox.append($itemBoxHeader);
				                    $itemBox.append($itemBoxBody);

				                    $boxBody.append($itemBox);
                			});
            			} else if ("010105L" === objTypeId) {	//관점
                			$.each(rsp["responses"][index]["hits"]["hits"], function(index, value){
                				var objId = value["_source"]["obj_id"];
                				var objNm = getObjNm(value);
                				var chgDt = value["_source"]["chg_dt"] === null ? "" : value["_source"]["chg_dt"].substring(0,10);
                				var objDesc = getObjDesc(value);
                				var atrVal101 = value["_source"]["atr_val_101"] === null ? "N/A" : value["_source"]["atr_val_101"];
                				
	                			var $itemBox       = $("    <div class=\"item-box\">");
		                		var $itemBoxHeader = $("        <div class=\"item-box-header\">");
                                					 $("            <h5 class=\"item-box-title\"><a href=\"javascript:void(0);\" objTypeId=\""+objTypeId+"\" objId=\""+objId+"\">"+objNm+"</a><small>"+chgDt+"</small></h5>").appendTo($itemBoxHeader);
				                var $itemBoxBody   = $("        <div class=\"item-box-body\">");
				                                     $("            <strong>[설명]</strong>").appendTo($itemBoxBody);
				                                     $("            <p class=\"text-muted\">"+objDesc+"</p>").appendTo($itemBoxBody);
				                                     $("            <strong>[샘플데이터]</strong>").appendTo($itemBoxBody);
				                                     $("            <p class=\"text-muted\">"+atrVal101+"</p>").appendTo($itemBoxBody);
				                    $itemBox.append($itemBoxHeader);
				                    $itemBox.append($itemBoxBody);

				                    $boxBody.append($itemBox);
                			});
            			} else if ("010301L" === objTypeId) {	//표준단어
                			$.each(rsp["responses"][index]["hits"]["hits"], function(index, value){
                				var objId = value["_source"]["obj_id"];
                				var objNm = getObjNm(value);
                				var chgDt = value["_source"]["chg_dt"] === null ? "" : value["_source"]["chg_dt"].substring(0,10);
                				var admObjId = value["_source"]["adm_obj_id"] === null ? "N/A" : value["_source"]["adm_obj_id"];
                				var objAbbrNm = value["_source"]["obj_abbr_nm"] === null ? "N/A" : value["_source"]["obj_abbr_nm"];
                				var objDesc = getObjDesc(value);
                				
	                			var $itemBox       = $("    <div class=\"item-box\">");
		                		var $itemBoxHeader = $("        <div class=\"item-box-header\">");
                                					 $("            <h5 class=\"item-box-title\"><a href=\"javascript:void(0);\" objTypeId=\""+objTypeId+"\" objId=\""+objId+"\">"+objNm+"</a><small>"+chgDt+"</small></h5>").appendTo($itemBoxHeader);
				                var $itemBoxBody   = $("        <div class=\"item-box-body\">");
				                                     $("            <strong>[영문명]</strong>").appendTo($itemBoxBody);
				                                     $("            <p class=\"text-muted\">"+admObjId+"</p>").appendTo($itemBoxBody);
				                                     $("            <strong>[정식명]</strong>").appendTo($itemBoxBody);
				                                     $("            <p class=\"text-muted\">"+objAbbrNm+"</p>").appendTo($itemBoxBody);
				                                     $("            <strong>[설명]</strong>").appendTo($itemBoxBody);
				                                     $("            <p class=\"text-muted\">"+objDesc+"</p>").appendTo($itemBoxBody);
				                    $itemBox.append($itemBoxHeader);
				                    $itemBox.append($itemBoxBody);

				                    $boxBody.append($itemBox);
                			});
            			} else if ("010302L" === objTypeId) {	//표준용어
                			$.each(rsp["responses"][index]["hits"]["hits"], function(index, value){
                				var objId = value["_source"]["obj_id"];
                				var objNm = getObjNm(value);
                				var chgDt = value["_source"]["chg_dt"] === null ? "" : value["_source"]["chg_dt"].substring(0,10);
                				var admObjId = value["_source"]["adm_obj_id"] === null ? "N/A" : value["_source"]["adm_obj_id"];
                				var pathObjNm = value["_source"]["path_obj_nm"] === null ? "N/A" : value["_source"]["path_obj_nm"];
                				var atrVal101 = value["_source"]["atr_val_101"] === null ? "N/A" : value["_source"]["atr_val_101"];
                				
	                			var $itemBox       = $("    <div class=\"item-box\">");
		                		var $itemBoxHeader = $("        <div class=\"item-box-header\">");
                                					 $("            <h5 class=\"item-box-title\"><a href=\"javascript:void(0);\" objTypeId=\""+objTypeId+"\" objId=\""+objId+"\">"+objNm+"</a><small>"+chgDt+"</small></h5>").appendTo($itemBoxHeader);
				                var $itemBoxBody   = $("        <div class=\"item-box-body\">");
				                                     $("            <strong>[영문명]</strong>").appendTo($itemBoxBody);
				                                     $("            <p class=\"text-muted\">"+admObjId+"</p>").appendTo($itemBoxBody);
				                                     $("            <strong>[도메인]</strong>").appendTo($itemBoxBody);
				                                     $("            <p class=\"text-muted\">"+atrVal101+"</p>").appendTo($itemBoxBody);
				                                     $("            <strong>[분류어]</strong>").appendTo($itemBoxBody);
				                                     $("            <p class=\"text-muted\">"+pathObjNm+"</p>").appendTo($itemBoxBody);
				                    $itemBox.append($itemBoxHeader);
				                    $itemBox.append($itemBoxBody);

				                    $boxBody.append($itemBox);
                			});
            			}
            		}
        		});
            	
            	//not all paging
            	if ("ALL" !== type) {
            		var $pagination = $("<p class=\"tsearch-paging\"></p>");
            		$pagination.bootpag({
            		    total: Math.ceil(pagingTotCnt/rsp["pagination"]["defaultSize"]),
            		    page: page,
            		    maxVisible: 5,
            		    leaps: true,
            		    firstLastUse: true,
            		    first: '<span aria-hidden="true">&larr;</span>',
            		    last: '<span aria-hidden="true">&rarr;</span>',
            		    wrapClass: 'pagination',
            		    activeClass: 'active',
            		    disabledClass: 'disabled',
            		    nextClass: 'next',
            		    prevClass: 'prev',
            		    lastClass: 'last',
            		    firstClass: 'first'
            		}).on("page", function(event, num){
            			var objTypeId = rsp["pagination"]["type"];
            			var keyword = rsp["pagination"]["keyword"];
            			$("#tSearch").val(keyword);
                    	DE.tsearch.msearch(objTypeId, keyword, num);
            		});
            		
            		$pagination.appendTo($content);
            	}
            },
            elsearch: function(type, keyword, page) {
            	$("#tSearch").data({"keyword":keyword})
            	var post = {
          	  		"type": type,
          	  		"keyword": keyword,
          	  		"page": page
          	  	};
            	var rsp = DE.ajax.call({"async":false, "url":"tsearch?oper=elsearch", "data":post});
            	//total count 
            	var totCnt = rsp.tot_cnt || 0;
            	if(type === "ALL"){
            		let alltab = $("[objTypeId=ALL]");
            		alltab.text(alltab.attr("objTypeNm")+"("+totCnt+")"); 
            	}
            	//tab search count setting
            	var pagingTotCnt = 1;
            	var objTypeId =  type;
            	pagingTotCnt = totCnt;

            	//search result
            	var activeTab = $("ul#tsearch-header li.active");
            	var activeTabId = $("a", activeTab).attr("href");
            	var activeObjTypeId = $("a", activeTab).attr("objTypeId");
            	var activeObjTypeNm = $("a", activeTab).attr("objTypeNm");
            	var keyword = $("#tSearch").val();
            	
        		$(activeTabId, "#tsearch-content").empty();
        		var $content = $("<div class=\"col-xs-12\">").appendTo($(activeTabId, "#tsearch-content"));
        		var $box       = $("    <div class=\"box\">");
    		    $content.append($box);
            		
        		if (rsp.tot_cnt === 0) {            			
        			var $noresult = $("<div class=\"noresult-out noresult-out-info\">");
        			                $("<h4><i class=\"fa  fa-exclamation-triangle\"></i>\""+keyword+"\" 에 대한 검색 결과가 없습니다.</h4>").appendTo($noresult);
        			$noresult.appendTo($boxBody);
        		} else {
            		for(var i in rsp.obj_ids){
            			let objId = rsp.obj_ids[i];
            			if(type === "ALL" || type === objId){
	            			var tabEle = $("[objTypeId="+objId+"]");
	            			var objTypeNm =  tabEle.attr("objTypeNm");
	            			let objTotCnt = +rsp[objId+"_tot_cnt"];
	                    	var txt = tabEle.attr("objTypeNm")+"("+objTotCnt+")";
	                    	tabEle.text(txt);
	                    	
	            			var $boxHeader = $("        <div class=\"box-header\">");
	            			$("<h5 class=\"box-title\"><i class=\"fa fa-quote-left\" style=\"font-size:8px; vertical-align:top; color:#bfbfbf;\"></i> "+objTypeNm+" <i class=\"fa fa-quote-right\" style=\"font-size:8px; vertical-align:top; color:#bfbfbf;\"></i><small><i class=\"fa fa-search\" aria-hidden=\"true\"></i>\""+keyword+"\" 에 관한  <label>"+objTotCnt+"</label>개의 검색 결과를 찾았습니다.</small></h5>")
	            			.appendTo($boxHeader);
	            			$box.append($boxHeader);
	            			var $boxBody   = $("<div class=\"box-body\">");
	            			$box.append($boxBody);
	
	            			$.each(rsp[objId], function(index, value){
	            				var objNm = value["obj_nm"];
	            				var chgDt = value["chg_dt"] === null ? "" : value["chg_dt"].substring(0,10);
	            				var objDesc = value["obj_desc"];
	            				var pathObjNm = ""; //value["_source"]["path_obj_nm"] === null ? "N/A" : value["_source"]["path_obj_nm"];
	            				var atrVal103 = ""; //value["_source"]["atr_val_103"] === null ? "N/A" : value["_source"]["atr_val_103"];
	            				
	            				
	            				
	            				var $itemBox       = $("    <div class=\"item-box\">");
	            				var $itemBoxHeader = $("        <div class=\"item-box-header\">");
	            				$("            <h5 class=\"item-box-title\"><a href=\"javascript:void(0);\" objTypeId=\""+objTypeId+"\" objId=\""+objId+"\">"+objNm+"</a><small>"+chgDt+"</small></h5>").appendTo($itemBoxHeader);
	            				var $itemBoxBody   = $("        <div class=\"item-box-body\">");
	            				if(objDesc){
	            					$("            <p class=\"text-muted\">"+objDesc+"</p>").appendTo($itemBoxBody);
	            				}
	            				$("            <p class=\"text-muted\">"+pathObjNm+"</p>").appendTo($itemBoxBody);
	            				$("            <p class=\"text-muted\">"+atrVal103+"</p>").appendTo($itemBoxBody);
	            				$itemBox.append($itemBoxHeader);
	            				$itemBox.append($itemBoxBody);
	            				
	            				$boxBody.append($itemBox);
	            			});
            			}
            		}
        		}
        		if(type !== "ALL"){
	        		var $pagination = $("<p class=\"tsearch-paging\"></p>");
	        		$pagination.bootpag({
	        		    total: Math.ceil(pagingTotCnt/rsp["pagination"]["defaultSize"]),
	        		    page: page,
	        		    maxVisible: 5,
	        		    leaps: true,
	        		    firstLastUse: true,
	        		    first: '<span aria-hidden="true">&larr;</span>',
	        		    last: '<span aria-hidden="true">&rarr;</span>',
	        		    wrapClass: 'pagination',
	        		    activeClass: 'active',
	        		    disabledClass: 'disabled',
	        		    nextClass: 'next',
	        		    prevClass: 'prev',
	        		    lastClass: 'last',
	        		    firstClass: 'first'
	        		}).on("page", function(event, num){
	        			var objTypeId = rsp["pagination"]["type"];
	        			var keyword = rsp["pagination"]["keyword"];
	        			$("#tSearch").val(keyword);
	                	DE.tsearch.elsearch(objTypeId, keyword, num);
	                	
	                	$("#tsearch-content").scrollTop(0);
	        		});
            		$pagination.appendTo($content);
            	}
            },
            autokeyword: function(el, btn) {
    			$(el).autocomplete({
    				source: function( request, response ) {
    					let str = request["term"];
    					let lastStr = str.charAt(str.length-1);
    					if(koreg.test(lastStr)){
    						if(!compkor.test(lastStr)){
    							return false;
    						}
    					}
    					
    					$.ajax({
    			            url: DE.contextPath+"/tsearch?oper=elsuggest",
    			            contentType: "application/json; charset=utf-8",
    			            data: JSON.stringify({ "keyword" : request["term"] }),
    			            dataType: "json",
    			            type:"POST",
    					    success: function( data ) {
    					    	response( $.map( data, function( item ) {
    			            		return {
    									label: item,
    									value: item
    								}
    			                }));
    			            }
    			        });
    			    },
    			    focus: function(event, ui) {
    			    	return false;
    		            //event.preventDefault();
    		        },
    			    minLength: 1,
    			    select: function( event, ui ) {
    			    	$(event.target).val(ui.item.value);
    			    	$(btn).trigger("click");
    			    	return false;
    			    },
    			    open: function() {
    			    	$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
    			    },
    			    close: function() {
    			    	$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
    			    },
    			    error: function(xhr, ajaxOptions, thrownError){ alert(thrownError);  alert(xhr.responseText); }
    			}).autocomplete("instance")._renderItem = function( ul, item ) {
    			    return $( "<li style='cursor:hand; cursor:pointer;'></li>" )
    			        .data( "item.autocomplete", item )
    			        .append("<a href=\"#\">"  + unescape(item.label) + "</a>")
    			    .appendTo( ul );
    			};
            }
        };

        return DE.tsearch;
    });

}).call(this);
