package com.yy.yellow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page {
	private int pageSize;  //每页显示的记录数
	private int pageNo = 1;    //当前页
	private int rowCount;  //总记录数
	private int showCount = 5; //显示当前页，前几页，或后几页的页数，默认为5
	
	public Page(int pageSize) {
		setPageSize(pageSize);
	}
	
	public Page(int pageSize, int pageNo) {
		this(pageSize);
		setPageNo(pageNo);
	}
	
	public Page(int pageSize, int pageNo, int showCount) {
		this(pageSize, pageNo);
		this.showCount = showCount;
	}
	
	//设置pageSize
	public void setPageSize(int pageSize) {
		if(pageSize <= 0) {
			throw new RuntimeException("pageSize必须大于零");
		}
		this.pageSize = pageSize;
	}
	
	//设置pageNo
	public void setPageNo(int pageNo) {
		if(pageNo <= 0) {
			throw new RuntimeException("pageNo必须大于零");
		}
		this.pageNo = pageNo;
	}
	
	//设置rowCount
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	
	//设置showCount
	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}
	
	//得到查询起始索引，从0开始
	public int getBeginIndex() {
		return (this.pageNo - 1) * this.pageSize;
	}
	
	//返回pageSize;
	public int getPageSize() {
		return pageSize;
	}
	
	//得到总页数
	public int getPageCount() {
		if(this.rowCount % this.pageSize == 0) {
			return this.rowCount / this.pageSize;
		} else {
			return this.rowCount / this.pageSize + 1;
		}
	}
	
	//是否有下一页
	public boolean isNext() {
		return this.pageNo < this.getPageCount();
	}
	//得到后几页的页数
	public List<Integer> getNextPage() {
		List<Integer> list = new ArrayList<Integer>();
		for(int i = this.pageNo + 1; i <= this.pageNo + this.showCount && i <= this.getPageCount(); i++) {
			list.add(i);
		}
		return list;
	}
	
	//是否有上一页
	public boolean isPrevious() {
		return this.pageNo > 1;
	}
	//得到前几页的页数
	public List<Integer> getPreviousPage() {
		List<Integer> list = new ArrayList<Integer>();
		for(int i = this.pageNo - this.showCount; i < this.pageNo; i++) {
			if(i > 0) {
				list.add(i);
			}
		}
		return list;
	}

	//转换成map
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageSize", this.pageSize);
		map.put("pageNo", this.pageNo);
		map.put("rowCount", this.rowCount);
		map.put("pageCount", this.getPageCount());
		map.put("isPrevious", this.isPrevious());
		map.put("previousPage", this.getPreviousPage());
		map.put("isNext", this.isNext());
		map.put("nextPage", this.getNextPage());
		return map;
	}
}