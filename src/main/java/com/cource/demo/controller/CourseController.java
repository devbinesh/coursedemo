package com.cource.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cource.demo.dto.CourseDto;
import com.cource.demo.dto.CourseFilter;
import com.cource.demo.model.Course;
import com.cource.demo.service.CourseService;

@RestController
@RequestMapping("/api")
public class CourseController {
	
	@Autowired
	CourseService service;
	
	@PostMapping("/page")
	public  List<Course> getCourses(@RequestBody CourseFilter filter) {
		
		return service.getCourses(filter);
		
	}
	@PostMapping("/update")
	public  ResponseEntity<String> update(@RequestBody CourseDto request) {
		service.updateCourse(request);
		return ResponseEntity.ok("");
		
	}
}
