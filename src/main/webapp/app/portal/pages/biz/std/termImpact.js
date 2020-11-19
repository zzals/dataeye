$(document).ready(function() {
	var parentObj = opener || parent;
	var reqParam = $("input#reqParam").data();
	
	var objM = DE.ajax.call({
		"async":false, 
		"url":"metapublic/map", 
		"data":{
			"queryId":"metacore.getObjM", 
			"objTypeId":reqParam["objTypeId"], 
			"objId":reqParam["objId"]}
		}
	);
	
	var getData = function() {
    	var diagramData = {};
		var nodeDataArray = [];
		var linkDataArray = [];
		
		var rsp = DE.ajax.call({async:false, url:"metapublic/list", data:{"queryId":"leneageandimpact.termImpactStep1", "objTypeId":reqParam["objTypeId"], "objId":reqParam["objId"]}});
		if (rsp["status"] !== "SUCCESS") {
			DE.box.alert(rsp["message"]);
			return;
		}
		
		$.each(rsp["data"], function(index, value) {
			var duplObj = $.grep(nodeDataArray, function(node) {
				return node["key"] === value["FROM_OBJ_ID"]; 
			});
			if ($.isEmptyObject(duplObj)) {
				var data = {
					"key" : value["FROM_OBJ_ID"],
					"text" : value["FROM_OBJ_NM"],
					"objTypeId" : value["FROM_OBJ_TYPE_ID"],
					"objId" : value["FROM_OBJ_ID"]
				};
				nodeDataArray.push(data);
			}
			duplObj = $.grep(nodeDataArray, function(node) {
				return node["key"] === value["TO_OBJ_ID"]; 
			});
			if ($.isEmptyObject(duplObj)) {
				var text = value["TO_OBJ_NM"];
				if (value["TO_OBJ_TYPE_ID"] === "020101L") {
					text += (" [" + value["USED_CNT"] + "]");
				}
				
				var data = {
					"key" : value["TO_OBJ_ID"],
					"text" : text,
					"objTypeId" : value["TO_OBJ_TYPE_ID"],
					"objId" : value["TO_OBJ_ID"]
				};
				nodeDataArray.push(data);
			}
			
			var link = {
				"from":value["FROM_OBJ_ID"],
				"to":value["TO_OBJ_ID"]
			};
			linkDataArray.push(link);
		});
		return {
			"nodeDataArray" : nodeDataArray,
			"linkDataArray" : linkDataArray
		};
	}
	
	function makeObjNm(objNm) {
		return objNm;
	}
	function makeObjTypeIcon(objTypeId) {
		return DE.contextPath+"/assets/images/icon/objtype/"+objTypeId+".png";
	}
    
    var fillConverter = function(lvl) {
    	return "#fef0e9";
    };
    var strokeConverter = function(lvl) {
    	return "#b83b25";
    };
    var textStrokeConverter = function(lvl) {
    	return "#b43725";
    };
	
    $("#viewDetail").on("click", function(e){
    	diagram.selection.each(function (node) {
    		if (node instanceof go.Node) {
    			DE.ui.open.popup(
					"view",
					[node.data["objTypeId"], node.data["objId"]],
					{
						viewname:'common/metacore/objectInfoTab',
						objTypeId:node.data["objTypeId"],
						objId:node.data["objId"],
						menuId:reqParam["menuId"],
						mode:'R'
					},
					null
				);
    		}
    	});
    });
	
    $("#viewImpact").on("click", function(e){
    	diagram.selection.each(function (node) {
    		if (node instanceof go.Node) {
    			var rsp = DE.ajax.call({async:false, url:"metapublic/list", data:{"queryId":"leneageandimpact.termImpactStep2", "dbObjTypeId":node["data"]["objTypeId"], "dbObjId":node["data"]["objId"], "termEngNm":objM["data"]["admObjId"]}});
    			$.each(rsp["data"], function(index, value){
	  				var data = {
  						"key" : value["TO_OBJ_ID"],
  						"text" : value["TO_OBJ_NM"],
  						"objTypeId" : value["TO_OBJ_TYPE_ID"],
  						"objId" : value["TO_OBJ_ID"]
  					};
	  				if (diagram.findNodeForKey(data.key) === null) {
	  					diagram.model.addNodeData(data);
	  				}
	  				diagram.model.addLinkData({from:node["data"]["key"], to:data["key"]});
    			});
    		    diagram.commitTransaction("Add Node");	  
    		}
    	});
    });
    
    var zoomInButtonClickCount = 1; // number of zooms
    var scaleLevelChange = 0.25; // percent zoom
    $("#btnZoomIn").on("click", function(e){
    	if(diagram.commandHandler.canIncreaseZoom()){
    		scaleLevel = diagram.scale + scaleLevelChange; 
    		// Set scale level to number of times button clicked
    		diagram.commandHandler.increaseZoom(scaleLevel);
    		// Scale the diagram 
    		diagram.scale = scaleLevel;
    	}
    });
    $("#btnZoomOut").on("click", function(e){
    	if(diagram.commandHandler.canIncreaseZoom()){
    		scaleLevel = diagram.scale - scaleLevelChange; 
    		// Set scale level to number of times button clicked
    		diagram.commandHandler.increaseZoom(scaleLevel);
    		// Scale the diagram 
    		diagram.scale = scaleLevel;
    	}
    });
    $("#btnNewWindow").on("click", function(e){
    	DE.ui.open.popup(
			"view",
			["termImpact"],
			{
				viewname:'portal/biz/std/termImpact',
				objTypeId:reqParam["objTypeId"],
				objId:reqParam["objId"],
				mode:reqParam["mode"],
				uiId:reqParam["uiId"],
				menuId:reqParam["menuId"],
			},
			"width="+screen.availWidth+", height="+screen.availHeight+", toolbar=no, menubar=no, location=no"
		);
    });
    
    var cxElement = document.getElementById("influenceViewContextMenu");
	function showContextMenu(obj, diagram, tool) {
		if (obj.data["objTypeId"] === "020101L") {
			$("#viewImpact").css("display", "block");
		} else {
			$("#viewImpact").css("display", "none");
		}
		
    	cxElement.style.display = "block";
    	var mousePt = diagram.lastInput.viewPoint;
        cxElement.style.left = mousePt.x + "px";
        cxElement.style.top = mousePt.y + "px";
    }
    
	var basefont = "bold 9pt 'Malgun Gothic', '맑은 고딕', sans-serif";
	
	function ContinuousForceDirectedLayout() {
		go.ForceDirectedLayout.call(this);
		this._isObserving = false;
	}
	go.Diagram.inherit(ContinuousForceDirectedLayout, go.ForceDirectedLayout);

	/** @override */
	ContinuousForceDirectedLayout.prototype.isFixed = function(v) {
		return v.node.isSelected;
	}

	// optimization: reuse the ForceDirectedNetwork rather than re-create it each time
	/** @override */
	ContinuousForceDirectedLayout.prototype.doLayout = function(coll) {
		if (!this._isObserving) {
			this._isObserving = true;
			// cacheing the network means we need to recreate it if nodes or links have been added or removed or relinked,
			// so we need to track structural model changes to discard the saved network.
			var lay = this;
			this.diagram.addModelChangedListener(function (e) {
				// modelChanges include a few cases that we don't actually care about, such as
				// "nodeCategory" or "linkToPortId", but we'll go ahead and recreate the network anyway.
				// Also clear the network when replacing the model.
				if (e.modelChange !== "" ||
						(e.change === go.ChangedEvent.Transaction && e.propertyName === "StartingFirstTransaction")) {
					lay.network = null;
				}
			});
		}
		var net = this.network;
		if (net === null) {  // the first time, just create the network as normal
			this.network = net = this.makeNetwork(coll);
		} else {  // but on reuse we need to update the LayoutVertex.bounds for selected nodes
			this.diagram.nodes.each(function (n) {
				var v = net.findVertex(n);
				if (v !== null) v.bounds = n.actualBounds;
			});
		}
		// now perform the normal layout
		go.ForceDirectedLayout.prototype.doLayout.call(this, coll);
		// doLayout normally discards the LayoutNetwork by setting Layout.network to null;
		// here we remember it for next time
		this.network = net;
	}
	function init() {
		go.licenseKey = "2bf845ebb36658c511895a2540383bbe5aa16f23c8834ea70c5245f6b95f6b162390ba2854d282c5d2f848f51b7ad2c9dac73a7a961b553cb533d0db43b68fa9b6317ae21408418fa45025c59bfd7aa8ee6877a792b377f08a799ee2e8a9c39a43eaecd7";
		$make = go.GraphObject.make;
		diagram = $make(go.Diagram, "datasetDiagram", { 
			initialDocumentSpot: go.Spot.Center,
            initialViewportSpot: go.Spot.Center,
            initialAutoScale: go.Diagram.Uniform,
            initialContentAlignment: go.Spot.Center,
            layout: $make(go.LayeredDigraphLayout, {
				isOngoing:true
			}),
			"SelectionMoved": function(e) { e.diagram.layout.invalidateLayout(); },
			"undoManager.isEnabled": true,
			"commandHandler.copiesTree": false,  // for the copy command
			"commandHandler.deletesTree": false, // for the delete command
			"draggingTool.dragsTree": false  // dragging for both move and copy
		});   
	    
		var influenceViewContextMenu = $make(go.HTMLInfo, {
		    show: showContextMenu,
		    mainElement: cxElement
		});
		
	    diagram.nodeTemplate = $make(
			go.Node, 
			go.Panel.Auto,
			{ 
				doubleClick: function() {$("#viewDetail").trigger("click")},
				locationSpot: go.Spot.Center,
				selectionAdornmentTemplate: $make(
			    	go.Adornment, 
			    	go.Panel.Spot,
			        $make(
			        	go.Panel, 
			        	go.Panel.Auto,
			            $make(
			            	go.Shape, 
			            	{fill: null, stroke:"#ffffff", strokeWidth:1}
			            ),
			            $make(go.Placeholder))
				),
				contextMenu: influenceViewContextMenu
			},
			$make(
		    	go.Shape, 
		    	"RoundedRectangle",
		        {
		    		strokeWidth:.3,
		            portId: null, 
		            fromLinkable: true, 
		            toLinkable: true, 
		            cursor: "pointer"
				},
				new go.Binding("fill", "objTypeId", fillConverter),
				new go.Binding("stroke", "objTypeId", strokeConverter)
		    ),
		    $make(go.Panel, "Table",
	    		{ 
		    		margin: 0, 
		    		maxSize: new go.Size(300, NaN),
		    		defaultAlignment: go.Spot.Center
	    		},
	    		$make(go.RowColumnDefinition, { 
	    			column: 0,
	    			width:20,
	                stretch: go.GraphObject.Horizontal,
	                alignment: go.Spot.Left
	            }),
			    $make( go.Picture, 
			    	{ 
			    		row: 0, 
			    		column: 0,
			            margin: 0,
			            desiredSize: new go.Size(16, 16),
			            imageStretch: go.GraphObject.Uniform,
			            alignment: go.Spot.TopLeft
					},
			        new go.Binding("source", "objTypeId", makeObjTypeIcon)),
			    $make( go.TextBlock, 
			    	{
			    		row: 0,
			    		column: 1,
			    		margin: 0,
			            font: basefont,
			            editable: true,
			            isMultiline:true,
			            text: 'N/A',
			            cursor: "pointer"
					},
			        new go.Binding("text", "text", makeObjNm),
			        new go.Binding("stroke", "objTypeId", textStrokeConverter)
				)
			)
		);

	    diagram.linkTemplate = $make(
			go.Link,  // the whole link panel
			{
				curve: go.Link.JumpGap, 
				toShortLength: 30
			},
		    new go.Binding("curviness", "curviness"),
		    $make(
		    	go.Shape,  // the link shape
		        {
		    		isPanelMain: true,
		            stroke: "#000000", 
		            strokeWidth: 1.2
		        }
		    ),
		    $make(
		    	go.Shape,  // the arrowhead
		        {
		    		toArrow:"Standard", 
		    		fill:'#000000',
		            stroke: null, 
		            scale: 1.5
		        }
		    )
		);

		diagram.toolManager.mouseMoveTools.insertAt(2, new DragZoomingTool());
		diagram.nodeTemplate.contextMenu = influenceViewContextMenu;
		cxElement.addEventListener("contextmenu", function(e) {
			e.preventDefault();
	    	return false;
	    }, false);
		
	    diagram.model = go.Model.fromJson(JSON.stringify(getData()));
  	}
	init();
	
	$(window).resize(function(){
		$(diagram.div).css("height", $(window).height()-$(diagram.div).offset().top-10);
		diagram.requestUpdate();
	}).resize();	
});