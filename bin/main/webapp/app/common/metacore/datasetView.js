$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
    
	if (reqParam != null && reqParam.title != undefined) {
		$("#header").css("display", "block");
		$("#header #popup_title").find("h5").append(reqParam.title);
	} else {
		$("#header").remove();
	}
	
	var getData = function() {
		var param = {
			"objTypeId" : reqParam["objTypeId"],
			"objId" : reqParam["objId"]
		};
		
		var diagramData = {};
		var nodeDataArray = [];
		var linkDataArray = [];
		
		var rsp = DE.ajax.call({async:false, url:"metapublic/list", data:{"queryId":"bdp_custom.datasetFlow", "objTypeId":reqParam["objTypeId"], "objId":reqParam["objId"]}})
		$.each(rsp["data"], function(index, value) {
			if (value["lvl"] != 1) {
				value["key"] = value["unqObjId"];
				nodeDataArray.push(value);
				
				if (value["lvl"] == 2) {
					var link = {
						"from":value["pobjId"],
						"to":value["unqObjId"],
					};
					linkDataArray.push(link);
				} else if (value["lvl"] == 3) {
					var link = {
						"from":value["pobjId"],
						"to":value["unqObjId"],
						"text":$.trim(value["datasetAttr"])+"("+$.trim(value["datasetAttrEng"])+")"
					};
					linkDataArray.push(link);
				}
			}
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
		return "../assets/images/icon/objtype/"+objTypeId+".png";
	}
    
    var fillConverter = function(lvl) {
    	if (lvl === 1) {
    		return "#fef0e9";
    	} else if (lvl === 2) {
    		return "#f8fcdb";
    	} else if (lvl === 3) {
	    	return "#f7eff7";
    	}
    };
    var strokeConverter = function(lvl) {
    	if (lvl === 1) {
    		return "#b83b25";
    	} else if (lvl === 2) {
    		return "#689a19";
    	} else if (lvl === 3) {
	    	return "#ae5bb3";
    	}
    };
    var textStrokeConverter = function(lvl) {
    	if (lvl === 1) {
    		return "#b43725";
    	} else if (lvl === 2) {
    		return "#4f6925";
    	} else if (lvl === 3) {
	    	return "#69316c";
    	}
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
	
    var zoomInButtonClickCount = 1; // number of zooms
    var scaleLevelChange = 0.25; // percent zoom
    $("#btnZoomIn").on("click", function(e){
    	debugger;
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
			["datasetViewNewPopup", reqParam["objTypeId"], reqParam["objId"]],
			{
				viewname:'common/metacore/datasetView',
				objTypeId:reqParam["objTypeId"],
				objId:reqParam["objId"]
			},
			"width="+screen.availWidth+", height="+screen.availHeight+", toolbar=no, menubar=no, location=no"
		);
    });
    
    var cxElement = document.getElementById("datasetContextMenu");
	function showContextMenu(obj, diagram, tool) {
    	if (obj.data["objTypeId"] == "010201L" || obj.data["objTypeId"] == "010205L") {
    		cxElement.style.display = "none";
    	} else {
    		cxElement.style.display = "block";
    	}

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
			layout: $make(ContinuousForceDirectedLayout, {
				isOngoing:true,
				maxIterations: 200, 
				defaultSpringLength: 150, 
				defaultElectricalCharge: 100
			}),
			"SelectionMoved": function(e) { e.diagram.layout.invalidateLayout(); },
			"undoManager.isEnabled": true,
			"commandHandler.copiesTree": false,  // for the copy command
			"commandHandler.deletesTree": false, // for the delete command
			"draggingTool.dragsTree": false  // dragging for both move and copy
		});   
	    
		// dragging a node invalidates the Diagram.layout, causing a layout during the drag
//		diagram.toolManager.draggingTool.doMouseMove = function() {
//	      go.DraggingTool.prototype.doMouseMove.call(this);
//	      if (this.isActive) { this.diagram.layout.invalidateLayout(); }
//	    }
	    
		var datasetContextMenu = $make(go.HTMLInfo, {
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
				contextMenu: datasetContextMenu
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
				new go.Binding("fill", "lvl", fillConverter),
				new go.Binding("stroke", "lvl", strokeConverter)
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
			        new go.Binding("text", "objNm", makeObjNm),
			        new go.Binding("stroke", "lvl", textStrokeConverter)
				)
			)
		);

		diagram.linkTemplate = $make(go.Link,  // the whole link panel
		    { 
				selectable: true,
				routing: go.Link.Normal,
				curve: go.Link.JumpOver 
		    },
		    $make(go.Shape),
		    $make(go.Panel, "Auto",
	    		{ cursor: "pointer" },  // visual hint that the user can do something with this link label
	    		$make(go.Shape,  // the label background, which becomes transparent around the edges		    				
	    			{
	    				fill: $make(go.Brush, "Radial"),
	    				stroke: null
	    			}
	    		),
				$make(go.TextBlock, "", 
					{	    						
						alignmentFocus: go.Spot.Bottom,
						textAlign: "center",
						font: basefont,
						stroke: "black",
						margin: -1,
						width: 100,
						editable: true  // editing the text automatically updates the model data
    				},
    				new go.Binding("text", "text").makeTwoWay()
				),
				new go.Binding("segmentOffset", "segmentOffset", go.Point.parse).makeTwoWay(go.Point.stringify)
	    	)
		);  // the link shape

		diagram.toolManager.mouseMoveTools.insertAt(2, new DragZoomingTool());
		diagram.nodeTemplate.contextMenu = datasetContextMenu;
		cxElement.addEventListener("contextmenu", function(e) {
			e.preventDefault();
	    	return false;
	    }, false);
		
//	    loading =
//	        $(go.Part,
//	          { selectable: false, location: new go.Point(0, 0) },
//	          $(go.TextBlock, "loading...",
//	            { stroke: "red", font: "20pt sans-serif" }));
//	    // temporarily add the status indicator
//	    diagram.add(loading);
	    
	    diagram.model = go.Model.fromJson(JSON.stringify(getData()));
	    //diagram.remove(loading);
  	}
	init();
	
	$(window).resize(function(){
		$(diagram.div).css("height", $(window).height()-$(diagram.div).offset().top-10);
		diagram.requestUpdate();
	}).resize();
});