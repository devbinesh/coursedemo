package com.cource.demo.model;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Document(collection = "course")
public class Course {

    @MongoId(targetType = FieldType.OBJECT_ID)
	private String id;
    
    @Field(name = "courseId")
	private String courseId;
    
    private List<String> tableHead;
    
    private List<Map<String, Object>> tableData;
     

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

	public List<String>  getTableHead() {
		return tableHead;
	}

	public void setTableHead(List<String> tableHead) {
		this.tableHead = tableHead;
	}

	public List<Map<String, Object>> getTableData() {
		return tableData;
	}

	public void setTableData(List<Map<String, Object>> tableData) {
		this.tableData = tableData;
	}
 
    
}
