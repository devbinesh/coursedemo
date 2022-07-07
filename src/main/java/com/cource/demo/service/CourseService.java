package com.cource.demo.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.aggregation.FacetOperation;
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
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class CourseService {
	 
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public Object getCourses(CourseFilter filter) {
		 
		String filterHeader = filter.getSearchHeader();
		  
		List<AggregationOperation> stages = new ArrayList<>();
		stages.add( Aggregation.match( new Criteria("_id").is(new ObjectId(filter.getStoryTableId()))));
		UnwindOperation unwindStage = Aggregation.unwind("$tableData",  "index", false);
		stages.add(unwindStage);
		
		 stages.add(Aggregation.replaceRoot("$tableData"));
		
		if(StringUtils.hasText(filter.getSearchHeader()) ) {
			stages.add( Aggregation.match(new Criteria(filterHeader).is(filter.getSearchValue())));
		} 
		
		Long skip = (filter.getPageNo()-1) * filter.getLimit();
		ArithmeticOperators.Ceil arOp =	ArithmeticOperators.Ceil
				.ceilValueOf( ArithmeticOperators.Divide.valueOf("total").divideBy(filter.getLimit())) ;
		
		
		AggregationOperation ops = addFields().addField("page")
		.withValue(filter.getPageNo())
		.addField("limit")
		.withValue(filter.getLimit())
		.addField("pages").withValueOf(ConvertOperators.valueOf(arOp).convertToLong())
		
		.build();
	 
		FacetOperation facet = facet().and(
				Aggregation.count().as("total"),
				ops)
				.as("pageable")
				.and(Aggregation.skip(skip ),
						Aggregation.limit(filter.getLimit())	)
				.as("tableData");
		stages.add( facet);
		stages.add( unwind("pageable"));
	 
		Aggregation aggregation = Aggregation.newAggregation(stages) ;
		AggregationResults<Object> output  = mongoTemplate
				.aggregate(aggregation,"course", Object.class);
		
		return output.getUniqueMappedResult();
		 
	}

	public boolean updateCourse(CourseDto request) {
		 
		if(StringUtils.hasText(request.getSearchHeader())) {
			
			Query query = new Query();
			ObjectId objID = new ObjectId(request.getStoryTableId()); 
			query.addCriteria(Criteria.where("_id").is(objID));
		    Update update = new Update();
		    update.set("tableData.$[element]."+request.getUpdateHeader(), request.getUpdateValue())
		    .filterArray(Criteria.where("element."+request.getSearchHeader()).is(request.getSearchValue()));
		     
		    mongoTemplate.updateMulti(query, update, "course");
		    return true;
		    
		} else {
			 
			Query query = new Query();
			ObjectId objID = new ObjectId(request.getStoryTableId()); 
			query.addCriteria(Criteria.where("_id").is(objID));
			Course course = mongoTemplate.findById(objID, Course.class, "course");
			List<String> headers = course.getTableHead(); 
			if(headers.contains(request.getUpdateHeader())) {
				headers.add(headers.indexOf(request.getUpdateHeader()), request.getUpdateValue());
				headers.remove(request.getUpdateHeader());
				 
				List<Map<String, Object>> tableData = course.getTableData();
				tableData.forEach(t -> {

					 Object currentValue = t.get(request.getUpdateHeader());
					 
					//update the value
					 t.put(request.getUpdateValue(), currentValue);
					 //remove old key
					 t.remove(request.getUpdateHeader());
					 
				});
				Update updates = new Update();
				updates.set("tableHead", headers);
				updates.set("tableData", tableData);
				mongoTemplate.updateFirst(query, updates, "course");
				 return true;
			}else {
				 return false;
			}
			
			  
			
		} 
	    
	}
}
