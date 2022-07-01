package com.cource.demo.dto;

import java.util.List;

public class CourseFilter {
	
	private String searchHeader;
	private String searchValue;
	private Long pageNo;
	private Long limit;
	public String getSearchHeader() {
		return searchHeader;
	}
	public void setSearchHeader(String searchHeader) {
		this.searchHeader = searchHeader;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public Long getPageNo() {
		return pageNo;
	}
	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}
	public Long getLimit() {
		return limit;
	}
	public void setLimit(Long limit) {
		this.limit = limit;
	}
	 

}
