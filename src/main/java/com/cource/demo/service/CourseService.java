package com.cource.demo.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cource.demo.dto.CourseFilter;
import com.cource.demo.model.Course; 

@Service
public class CourseService {
	 
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public List<Course> getCourses(CourseFilter filter) {
		 
		String filterHeader = "tableData."+filter.getSearchHeader();
		//Slice<Course> courses = repo.searchCourse(filterHeader, filter.getSearchValue(), pageReq);
		Criteria matchCriteria = new Criteria(filterHeader).is(filter.getSearchValue());
		List<AggregationOperation> stages = new ArrayList<>();
		UnwindOperation unwindStage = Aggregation.unwind("$tableData",  "index", false);
		stages.add(unwindStage);
		if(StringUtils.hasText(filter.getSearchHeader()) ) {
			stages.add( Aggregation.match(matchCriteria));
		} 
		 
		stages.add( Aggregation.skip( (filter.getPageNo()-1) * filter.getLimit()));
		stages.add( Aggregation.limit(filter.getLimit())); 
		Aggregation aggregation = Aggregation.newAggregation(stages) ;
		AggregationResults<Course> output  = mongoTemplate
				.aggregate(aggregation,"course", Course.class);
		
		return output.getMappedResults();
		 
	}

}
