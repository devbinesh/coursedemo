package com.cource.demo.dto;

import org.springframework.data.mongodb.core.mapping.Field;

public class CourseDto {

	private String storyTableId;
	private String searchHeader;
	private String searchValue;
	private String updateHeader;
	private String  updateValue;
	public String getStoryTableId() {
		return storyTableId;
	}
	public void setStoryTableId(String storyTableId) {
		this.storyTableId = storyTableId;
	}
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
	public String getUpdateHeader() {
		return updateHeader;
	}
	public void setUpdateHeader(String updateHeader) {
		this.updateHeader = updateHeader;
	}
	public String getUpdateValue() {
		return updateValue;
	}
	public void setUpdateValue(String updateValue) {
		this.updateValue = updateValue;
	}
	
}
