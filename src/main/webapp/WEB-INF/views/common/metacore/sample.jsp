<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>HTML Context Menu</title>
<meta name="description" content="Context menus implemented in HTML rather than as GoJS objects." />
<!-- Copyright 1998-2017 by Northwoods Software Corporation. -->
<meta charset="UTF-8">
<style type="text/css">
  /* CSS for the traditional context menu */
  #contextMenu {
    z-index: 300;
    position: absolute;
    left: 5px;
    border: 1px solid #444;
    background-color: #F5F5F5;
    display: none;
    box-shadow: 0 0 10px rgba( 0, 0, 0, .4 );
    font-size: 12px;
    font-family: sans-serif;
    font-weight: bold;
  }
  #contextMenu ul {
    list-style: none;
    top: 0;
    left: 0;
    margin: 0;
    padding: 0;
  }
  #contextMenu li a {
    position: relative;
    min-width: 60px;
    color: #444;
    display: inline-block;
    padding: 6px;
    text-decoration: none;
    cursor: pointer;
  }
  #contextMenu li:hover {
    background: #CEDFF2;
    color: #EEE;
  }
  #contextMenu li ul li {
    display: none;
  }
  #contextMenu li ul li a {
    position: relative;
    min-width: 60px;
    padding: 6px;
    text-decoration: none;
    cursor: pointer;
  }
  #contextMenu li:hover ul li {
    display: block;
    margin-left: 0px;
    margin-top: 0px;
  }
</style>

<script src="/dataeye_dev/assets/javascripts-lib/gojs/go.js"></script>
<script id="code">
var myDiagram = null;
function init() {
  if (window.goSamples) goSamples();  // init for these samples -- you don't need to call this
  var $ = go.GraphObject.make;  // for conciseness in defining templates
  myDiagram =
    $(go.Diagram, "myDiagramDiv",  // create a Diagram for the DIV HTML element
      { initialContentAlignment: go.Spot.Center,
		autoScale: go.Diagram.UniformToFill,
		contentAlignment: go.Spot.Center,
		layout: $(go.LayeredDigraphLayout, {isOngoing:true, layerSpacing:100 }),
		"undoManager.isEnabled": true,
		"commandHandler.copiesTree": false,  // for the copy command
		"commandHandler.deletesTree": false, // for the delete command
		"draggingTool.dragsTree": true  // dragging for both move and copy
	});
  // This is the actual HTML context menu:
  var cxElement = document.getElementById("contextMenu");
  // Since we have only one main element, we don't have to declare a hide method,
  // we can set mainElement and GoJS will hide it automatically
  var myContextMenu = $(go.HTMLInfo, {
    show: showContextMenu,
    mainElement: cxElement
  });
  // define a simple Node template (but use the default Link template)
  myDiagram.nodeTemplate =
    $(go.Node, "Auto",
      { contextMenu: myContextMenu },
      $(go.Shape, "RoundedRectangle",
        // Shape.fill is bound to Node.data.color
        new go.Binding("fill", "color")),
      $(go.TextBlock,
        { margin: 3, editable:true},  // some room around the text
        // TextBlock.text is bound to Node.data.key
        new go.Binding("text", "key"))
    );
  // create the model data that will be represented by Nodes and Links
  myDiagram.model = new go.GraphLinksModel(
  [
    { key: "Alpha", color: "crimson" },
    { key: "Beta", color: "chartreuse" },
    { key: "Gamma", color: "aquamarine" },
    { key: "Delta", color: "gold" }
  ],
  [
    { from: "Alpha", to: "Beta" },
    { from: "Alpha", to: "Gamma" },
    { from: "Beta", to: "Beta" },
    { from: "Gamma", to: "Delta" },
    { from: "Delta", to: "Alpha" }
  ]);
  myDiagram.contextMenu = myContextMenu;
  // We don't want the div acting as a context menu to have a (browser) context menu!
  cxElement.addEventListener("contextmenu", function(e) {
    e.preventDefault();
    return false;
  }, false);
  function showContextMenu(obj, diagram, tool) {
    // Show only the relevant buttons given the current state.
    var cmd = diagram.commandHandler;
    document.getElementById("cut").style.display = cmd.canCutSelection() ? "block" : "none";
    document.getElementById("copy").style.display = cmd.canCopySelection() ? "block" : "none";
    document.getElementById("paste").style.display = cmd.canPasteSelection() ? "block" : "none";
    document.getElementById("delete").style.display = cmd.canDeleteSelection() ? "block" : "none";
    document.getElementById("color").style.display = (obj !== null ? "block" : "none");
    // Now show the whole context menu element
    cxElement.style.display = "block";
    // we don't bother overriding positionContextMenu, we just do it here:
    var mousePt = diagram.lastInput.viewPoint;
    cxElement.style.left = mousePt.x + "px";
    cxElement.style.top = mousePt.y + "px";
  }
}
// This is the general menu command handler, parameterized by the name of the command.
function cxcommand(event, val) {
  if (val === undefined) val = event.currentTarget.id;
  var diagram = myDiagram;
  switch (val) {
    case "cut": diagram.commandHandler.cutSelection(); break;
    case "copy": diagram.commandHandler.copySelection(); break;
    case "paste": diagram.commandHandler.pasteSelection(diagram.lastInput.documentPoint); break;
    case "delete": diagram.commandHandler.deleteSelection(); break;
    case "color": {
        var color = window.getComputedStyle(document.elementFromPoint(event.clientX, event.clientY).parentElement)['background-color'];
        changeColor(diagram, color); break;
    }
  }
  diagram.currentTool.stopTool();
}
// A custom command, for changing the color of the selected node(s).
function changeColor(diagram, color) {
  // Always make changes in a transaction, except when initializing the diagram.
  diagram.startTransaction("change color");
  diagram.selection.each(function(node) {
    if (node instanceof go.Node) {  // ignore any selected Links and simple Parts
        // Examine and modify the data, not the Node directly.
        var data = node.data;
        // Call setDataProperty to support undo/redo as well as
        // automatically evaluating any relevant bindings.
        diagram.model.setDataProperty(data, "color", color);
    }
  });
  diagram.commitTransaction("change color");
}
</script>
</head>
<body onload="init()">
<!-- UI Object -->
<div id="container">
    <!-- HEADER -->
    <div id="header" style="display: none;">
        <div class="popup_Title_Area">
            <div class="popup_title"></div>
        </div>
    </div>
    <!-- //HEADER -->
	<section class="content">
		<div class="row">
			<div class="col-xs-12">
				<div class="box">
		            <div class="box-body">
      <div id="myDiagramDiv" style="border: solid 1px black; width:400px; height:400px"></div>
      <div id="contextMenu">
        <ul>
          <li id="cut" onclick="cxcommand(event)"><a href="#" target="_self">Cut</a></li>
          <li id="copy" onclick="cxcommand(event)"><a href="#" target="_self">Copy</a></li>
          <li id="paste" onclick="cxcommand(event)"><a href="#" target="_self">Paste</a></li>
          <li id="delete" onclick="cxcommand(event)"><a href="#" target="_self">Delete</a></li>
          <li id="color" class="hasSubMenu"><a href="#" target="_self">Color</a>
            <ul class="subMenu" id="colorSubMenu">
                <li style="background: crimson;" onclick="cxcommand(event, 'color')"><a href="#" target="_self">Red</a></li>
                <li style="background: chartreuse;" onclick="cxcommand(event, 'color')"><a href="#" target="_self">Green</a></li>
                <li style="background: aquamarine;" onclick="cxcommand(event, 'color')"><a href="#" target="_self">Blue</a></li>
                <li style="background: gold;" onclick="cxcommand(event, 'color')"><a href="#" target="_self">Yellow</a></li>
            </ul>
          </li>
        </ul>
      </div>
				    </div>
		            <!-- /.box-body -->
		          </div>
		          <!-- /.box -->				
			</div>
		</div>
	</section>
</div>
</body>
</html>