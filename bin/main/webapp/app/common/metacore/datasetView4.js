$(document).ready(function() {
	var reqParam = $("input#reqParam").data();
    
	if (reqParam != null && reqParam.title != undefined) {
		$(".header").css("display", "block");
		$(".header #popup_title").find("h5").append(reqParam.title);
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
			value["key"] = value["objId"];
			nodeDataArray.push(value);
			
			if (value["lvl"] == 2) {
				var link = {
					"from":value["pobjId"],
					"to":value["objId"],
				};
				linkDataArray.push(link);
			} else if (value["lvl"] == 3) {
				var link = {
					"from":value["pobjId"],
					"to":value["objId"],
					"text":value["atrNm"]
				};
				linkDataArray.push(link);
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
	
	function openObjInfo(e, obj) {
		var clicked = obj.part;
	  	if (clicked !== null) {
        	var data = clicked.data;
        	DE.ui.open.popup(
				"view",
				[data.OBJ_TYPE_ID, data.OBJ_ID],
				{
					viewname:'common/metacore/objectInfoTab',
					objTypeId:data.OBJ_TYPE_ID,
					objId:data.OBJ_ID,
					mode:'R'
				},
				null
			);
      	}
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
						mode:'R'
					},
					null
				);
    		}
    	});
    });
	
	var basefont = "bold 9pt 'Malgun Gothic', '맑은 고딕', sans-serif";
    
	function init() {
		go.licenseKey = "2bf845ebb36658c511895a2540383bbe5aa16f23c8834ea70c5245f6b95f6b162390ba2854d282c5d2f848f51b7ad2c9dac73a7a961b553cb533d0db43b68fa9b6317ae21408418fa45025c59bfd7aa8ee6877a792b377f08a799ee2e8a9c39a43eaecd7";
		$make = go.GraphObject.make;
		diagram = $make(go.Diagram, "datasetDiagram", { 
			initialContentAlignment: go.Spot.Center,
			autoScale: go.Diagram.UniformToFill,
			contentAlignment: go.Spot.Center,
			layout: $make(go.LayeredDigraphLayout, {isOngoing:true, layerSpacing:100 }),
			"undoManager.isEnabled": true,
			"commandHandler.copiesTree": false,  // for the copy command
			"commandHandler.deletesTree": false, // for the delete command
			"draggingTool.dragsTree": true  // dragging for both move and copy
		});   
	    
		var cxElement = document.getElementById("datasetContextMenu");
		var datasetContextMenu = $make(go.HTMLInfo, {
		    show: showContextMenu,
		    mainElement: cxElement
		});
		
	    diagram.nodeTemplate = $make(
			go.Node, 
			go.Panel.Auto,
			{ 
				doubleClick: openObjInfo,
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
		    		margin: 10, 
		    		maxSize: new go.Size(200, NaN)
	    		},
	    		$make(go.RowColumnDefinition, { 
	            	column: 0,
	                stretch: go.GraphObject.Horizontal,
	                alignment: go.Spot.Left
	            }),
			    $make( go.Picture, 
			    	{ 
			    		row: 0, 
			    		column: 1, 
			    		margin: 2,
			            desiredSize: new go.Size(16, 16),
			            imageStretch: go.GraphObject.Uniform,
			            alignment: go.Spot.TopLeft 
					},
			        new go.Binding("source", "objTypeId", makeObjTypeIcon)),
			    $make( go.TextBlock, 
			    	{
			    		row: 0,
			    		column: 2,
			    		margin: 2,
			            font: basefont,
			            editable: true,
			            editable: true,
			            width:'100%',
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
				routing: go.Link.Normal
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
						width: 60,
						editable: false  // editing the text automatically updates the model data
    				},
    				new go.Binding("text", "text").makeTwoWay()
				),
				new go.Binding("segmentOffset", "segmentOffset", go.Point.parse).makeTwoWay(go.Point.stringify)
	    	)
		);  // the link shape

		diagram.model = go.Model.fromJson(JSON.stringify(getData()));
		diagram.nodeTemplate.contextMenu = datasetContextMenu;
		cxElement.addEventListener("contextmenu", function(e) {
			e.preventDefault();
	    	return false;
	    }, false);
		
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
  	}
	init();
	
	$(window).resize(function(){
		$(diagram.div).css("height", $(window).height()-$(diagram.div).position().top-40);
		diagram.requestUpdate();
	}).resize();
});