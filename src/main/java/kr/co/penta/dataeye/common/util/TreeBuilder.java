package kr.co.penta.dataeye.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TreeBuilder {
	private Collection<Map<String, Object>> collection = null;
	private String id = null;
	private String pid = null;

	public TreeBuilder(String id, String pid, Collection<Map<String, Object>> collection) {
		super();
		this.collection = collection;
		this.id = id;
		this.pid = pid;
	}
	
	public List<Map<String, Object>> build() {
		List<Map<String, Object>> treeNodes = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> rootNodes = getRootNodes();
		for (Map<String, Object> rootNode : rootNodes) {
			buildChildNodes(rootNode);
			treeNodes.add(rootNode);
		}
		return treeNodes;
	}
	
	public List<Map<String, Object>> getRootNodes() {
		List<Map<String, Object>> rootNodes = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> n : collection){
			if (rootNode(n)) {
				rootNodes.add(n);
			}
		}
		return rootNodes;
	}
	
	public boolean rootNode(Map<String, Object> node) {
		boolean isRootNode = true;
		for (Map<String, Object> n : collection){
			if (n.get(id).equals(node.get(pid))) {
				isRootNode= false;
				break;
			}
		}
		return isRootNode;
	}
	
	public void buildChildNodes(Map<String, Object> node) {
		List<Map<String, Object>> children = getChildNodes(node);
		if (!children.isEmpty()) {
			for(Map<String, Object> child : children) {
				buildChildNodes(child);
			}
			node.put("children", children);
		}
	}
	
	public List<Map<String, Object>> getChildNodes(Map<String, Object> pnode) {
		List<Map<String, Object>> childNodes = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> n : collection){
			if (pnode.get(id).equals(n.get(pid))) {
				childNodes.add(n);
			}
		}
		return childNodes;
	}
}