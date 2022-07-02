package com.cource.demo.dto;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank; 

public class CourseFilter {
	
	  
	private String storyTableId;
	
	
	private String searchHeader;
	private String searchValue;
	 
	private Long pageNo = 0L;
	private Long limit = 50L;
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
	public String getStoryTableId() {
		return storyTableId;
	}
	public void setStoryTableId(String storyTableId) {
		this.storyTableId = storyTableId;
	}
	 

}
