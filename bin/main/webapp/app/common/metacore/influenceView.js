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
		
		var result = DE.ajax.call({async:false, url:"metacore/objectrel?oper=objInfluence", data:{"objTypeId":reqParam["objTypeId"], "objId":reqParam["objId"]}})
		$.each(result["data"], function(index, value){
			if (value["REL_DIV"] == "ROOT") {
				value["key"] = value["ID"];
				nodeDataArray.push(value);
			} else if (value["REL_DIV"] == "UP") {
				var link = {
					"from":value["PID"],
					"to":value["ID"]
				};
				linkDataArray.push(link);
				
				value["key"] = value["ID"];
				nodeDataArray.push(value);
			} else if (value["REL_DIV"] == "DOWN") {
				var link = {
					"from":value["ID"],
					"to":value["PID"]
				};
				linkDataArray.push(link);
				
				value["key"] = value["ID"];
				nodeDataArray.push(value);
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
    
    var fillConverter = function() {
    	return "#fef0e9";
    };
    var strokeConverter = function() {
    	return "#b83b25";
    };
    var textStrokeConverter = function() {
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
    
    $("#viewLineage").on("click", function(e){
    	diagram.selection.each(function (node) {
    		if (node instanceof go.Node) {
	  			var rsp = DE.ajax.call({async:false, url:"metacore/objectrel?oper=objInfluenceSub", data:{"objTypeId":node.data.OBJ_TYPE_ID, "objId":node.data.OBJ_ID, "relType":"DOWN"}});
	  			$.each(rsp["data"], function(index, value){
	  				value.key = value["ID"]; 
	  				if (diagram.findNodeForKey(value.key) === null) {
	  					diagram.model.addNodeData(value);
	  				}
	  		    	diagram.model.addLinkData({from:value["ID"],to:node.data.key});
	  			});
	  		    diagram.commitTransaction("Add Node");	   
    		}
    	});
    });
    
    $("#viewImpact").on("click", function(e){
    	diagram.selection.each(function (node) {
    		if (node instanceof go.Node) {
    			var rsp = DE.ajax.call({async:false, url:"metacore/objectrel?oper=objInfluenceSub", data:{"objTypeId":node.data.OBJ_TYPE_ID, "objId":node.data.OBJ_ID, "relType":"UP"}});
    			$.each(rsp["data"], function(index, value){
	  				value.key = value["ID"];
	  				if (diagram.findNodeForKey(value.key) === null) {
	  					diagram.model.addNodeData(value);
	  				}
	  				diagram.model.addLinkData({from:node.data.key,to:value["ID"]});
    			});
    		    diagram.commitTransaction("Add Node");	  
    		}
    	});
    });
	
    var cxElement = document.getElementById("influenceContextMenu");
	function showContextMenu(obj, diagram, tool) {
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
		diagram = $make(go.Diagram, "influenceDiagramDiv", {
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
	
		var contextMenu = $make(go.HTMLInfo, {
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
				contextMenu: contextMenu
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
			        new go.Binding("source", "OBJ_TYPE_ID", makeObjTypeIcon)),
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
			        new go.Binding("text", "OBJ_NM", makeObjNm),
			        new go.Binding("stroke", "lvl", textStrokeConverter)
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
		diagram.nodeTemplate.contextMenu = contextMenu;
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