package com.cource.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult; 
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cource.demo.dto.CourseDto;
import com.cource.demo.dto.CourseFilter;
import com.cource.demo.model.Course;
import com.cource.demo.service.CourseService;

@RestController
@RequestMapping("/api")
@Validated
public class CourseController {
	
	@Autowired
	CourseService service;
	 
	
	@PostMapping("/page")
	public  ResponseEntity<Object> getCourses( @Valid @RequestBody CourseFilter filter) {
	     
		return ResponseEntity.ok(service.getCourses(filter));
		
	}
	@PostMapping("/update")
	public  ResponseEntity<String> update(@RequestBody @Valid CourseDto request) {
		 boolean updated = service.updateCourse(request);
		 if(updated) {
			 return ResponseEntity.ok(" records updated successfully");
		 }else {
			 return ResponseEntity.ok("Not able to update table header. header does not exist");
		 }
		
		 
		
	}
}
