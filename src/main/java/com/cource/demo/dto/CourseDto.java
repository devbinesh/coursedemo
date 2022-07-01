package com.cource.demo.dto;

import org.springframework.data.mongodb.core.mapping.Field;

public class CourseDto {

	private String id;

	private String courseId;
	
	public CourseDto(String id, String courseId) {
		super();
		this.id = id;
		this.courseId = courseId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

}
