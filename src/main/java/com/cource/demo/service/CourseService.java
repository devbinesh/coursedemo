package com.cource.demo.service;


import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cource.demo.dto.CourseDto;
import com.cource.demo.dto.CourseFilter;
import com.cource.demo.model.Course;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.internal.bulk.UpdateRequest; 

@Service
public class CourseService {
	 
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public List<Course> getCourses(CourseFilter filter) {
		 
		String filterHeader = "tableData."+filter.getSearchHeader();
		  
		List<AggregationOperation> stages = new ArrayList<>();
		stages.add( Aggregation.match( new Criteria("_id").is(new ObjectId(filter.getStoryTableId()))));
		UnwindOperation unwindStage = Aggregation.unwind("$tableData",  "index", false);
		stages.add(unwindStage);
		
		if(StringUtils.hasText(filter.getSearchHeader()) ) {
			stages.add( Aggregation.match(new Criteria(filterHeader).is(filter.getSearchValue())));
		} 
		
		stages.add( Aggregation.skip( (filter.getPageNo()-1) * filter.getLimit()));
		stages.add( Aggregation.limit(filter.getLimit())); 
		Aggregation aggregation = Aggregation.newAggregation(stages) ;
		AggregationResults<Course> output  = mongoTemplate
				.aggregate(aggregation,"course", Course.class);
		
		return output.getMappedResults();
		 
	}

	public Long updateCourse(CourseDto request) {
		
		Query query = new Query();
		ObjectId objID = new ObjectId(request.getStoryTableId()); 
		query.addCriteria(Criteria.where("_id").is(objID));
	    Update update = new Update();
	    update.set("tableData.$[element]."+request.getUpdateHeader(), request.getUpdateValue())
	    .filterArray(Criteria.where("element."+request.getSearchHeader()).is(request.getSearchValue()));
	     
	    UpdateResult updateResult = mongoTemplate.updateMulti(query, update, "course");
	    return updateResult.getModifiedCount();
	    
	    
	}
}
