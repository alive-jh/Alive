package com.wechat.util;

import java.io.Serializable;
import java.util.List;



public class Page<T> implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	// 每页默认数据总数
	public final static Integer PAGESIZE = 30;
	// 每页数据总数
	private Integer pageSize = PAGESIZE;
	// 数据集合
	private List<T> items;
	//统计信息
	private T stat;

	// 总记录数
	private Integer totalCount;
	// 计算索引数组 每页多少条记录
	private Integer indexes =0;
	// 起始索引 当前页数
	private Integer startIndex = 0;
	// 页码数组
	private Integer[] pageNums = new Integer[0];
	private Integer pageNum = 1;



	public Page(List<T> items, Integer totalCount, Integer pageCount,Integer startIndex,Integer rowsPerPage) {
		setPageSize(rowsPerPage);
		setTotalCount(totalCount);
		setItems(items);
		this.startIndex = startIndex;
		setIndexes(pageCount);
	}

	

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
	
	public T getStat() {
		return stat;
	}

	public void setStat(T stat) {
		this.stat = stat;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * 计算总记录数
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(Integer totalCount) {

		this.totalCount = totalCount;
	}

	public Integer getIndexes() {
		return indexes;
	}

	public void setIndexes(Integer indexes) {
		this.indexes = indexes;
	}

	public Integer getStartIndex() {
		return startIndex;
	}
	
	public int getTotalPageCount(){
		int res = 0;
		try{
			res = StringUtil.totalPageCount(this.totalCount.toString(), String.valueOf(this.getPageSize()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}





	public Integer getPageNum(int startIndex, int pageSize) {
		int pageNum = 1;
		if (startIndex != 0) {
			if (pageSize != 0) {
				pageNum = startIndex / pageSize;
			}
			pageNum+=1;
		}
		if(pageNum==0){
			pageNum=1;
		}
		return pageNum;
	}

	public Integer getPageNum() {
		return getPageNum(startIndex,pageSize);
	}

	

	
}
