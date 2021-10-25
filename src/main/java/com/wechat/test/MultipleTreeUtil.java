package com.wechat.test;

import com.wechat.util.JsonResult;
import com.wechat.util.JsonTimeStampValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import java.sql.Timestamp;
import java.util.*;

/**
 * 多叉树类
 */
public class MultipleTreeUtil {

	private List dataList;

	private static Integer ROOTID = 0;

	public static String JsonArrayToStr(Object data) {
		JSONArray result = new JSONArray();
		try {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Timestamp.class,
					new JsonTimeStampValueProcessor());
			result = result.element(data, jsonConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}


	public String getMultipleTree(Integer parentId) {

		// 节点列表（散列表，用于临时存储节点对象）
		HashMap nodeList = new HashMap();
		// 根节点
		Node root = null;
		// 根据结果集构造节点列表（存入散列表）
		for (Iterator it = dataList.iterator(); it.hasNext();) {
			Map dataRecord = (Map) it.next();
			Node node = new Node();
			node.id = (Integer) dataRecord.get("id");
			node.propertis = (HashMap) dataRecord.get("propertis");
			node.parentId =  (Integer) dataRecord.get("parentId");
			node.name = (String) dataRecord.get("name");
			node.sort = (Integer) dataRecord.get("sort");
			nodeList.put(node.id, node);
		}
		// 构造无序的多叉树
		Set entrySet = nodeList.entrySet();
		for (Iterator it = entrySet.iterator(); it.hasNext();) {
			Node node = (Node) ((Map.Entry) it.next()).getValue();
			if (node.parentId == MultipleTreeUtil.ROOTID) {
				root = node;
			} else {
				((Node) nodeList.get(node.parentId)).addChild(node);
			}
		}
		// 输出无序的树形菜单的JSON字符串
		// //System.out.println(root.toString());
		// 对多叉树进行横向排序
		root.sortChildren();
		// 输出有序的树形菜单的JSON字符串
		// //System.out.println(JsonResult.JsonResultToStr(root.toString()));
		if (null != nodeList.get(parentId)) {
			return nodeList.get(parentId).toString();
		}
		return JsonResult.JsonResultToStr("没有此分类");

	}

	public String getMultipleTreeChildIdList(Integer parentId, List data,
			StringBuffer childIdList) {
		for (int i = 0; i < data.size(); i++) {
			HashMap map = (HashMap) data.get(i);
			Integer myParentId = Integer.parseInt((String) map.get("parentId"));
			if (myParentId != MultipleTreeUtil.ROOTID
					&& myParentId.equals(parentId)) {
				Integer id = Integer.parseInt((String) map.get("id"));
				childIdList = childIdList.append(id + ",");
				getMultipleTreeChildIdList(id, data, childIdList);
			}
		}
		return childIdList.toString().substring(0,
				childIdList.toString().length() - 1);
	}


	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		
		this.dataList = dataList;
	}

}

/**
 * 节点类
 */
class Node {
	/**
	 * 节点编号
	 */
	public Integer id;

	/**
	 * 节点属性
	 */
	public Map propertis;

	/**
	 * 节点名称
	 */
	public String name;

	/**
	 * 节点排序
	 */
	public Integer sort;

	/**
	 * 父节点编号
	 */
	public Integer parentId;

	/**
	 * 孩子节点列表
	 */
	private Children children = new Children();

	// 先序遍历，拼接JSON字符串
	@Override
	public String toString() {
		String result = "{" + " \"id\" : " + id + " , \"parentId\" : "
				+ parentId + " , \"name\" : \"" + name + "\"" + " , \"sort\" : "
				+ sort  + " , \"propertis\" : "
				+ MultipleTreeUtil.JsonArrayToStr(propertis);
		if (children != null && children.getSize() != 0) {
			result += " , \"children\" : " + children.toString();
			result += " , \"leaf\" : true";
		} else {
			result += " , \"leaf\" : false";
		}
		result = result + "}";
		return result;
	}

	// 兄弟节点横向排序
	public void sortChildren() {
		if (children != null && children.getSize() != 0) {
			children.sortChildren();
		}
	}

	// 添加孩子节点
	public void addChild(Node node) {
		this.children.addChild(node);
	}
}

/**
 * 孩子列表类
 */
class Children {
	private List list = new ArrayList();

	public int getSize() {
		return list.size();
	}

	public void addChild(Node node) {
		list.add(node);
	}

	// 拼接孩子节点的JSON字符串
	@Override
	public String toString() {
		String result = "[";
		for (Iterator it = list.iterator(); it.hasNext();) {
			result += ((Node) it.next()).toString();
			result += ",";
		}
		result = result.substring(0, result.length() - 1);
		result += "]";
		return result;
	}

	// 孩子节点排序
	public void sortChildren() {
		// 对本层节点进行排序
		// 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器
		Collections.sort(list, new NodeSortComparator());
		// 对每个节点的下一层节点进行排序
		for (Iterator it = list.iterator(); it.hasNext();) {
			((Node) it.next()).sortChildren();
		}
	}
}

/**
 * 节点比较器
 */
class NodeSortComparator implements Comparator {
	// 按照节点编号比较
	@Override
	public int compare(Object o1, Object o2) {
		int j1 = ((Node) o1).sort;
		int j2 = ((Node) o2).sort;
		return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
	}
}
