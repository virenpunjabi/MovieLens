package edu.sampleproject.movielens.pojo;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

public class Filter {

    	Map<FilterType, List<String>> filteredColumns;
    	String sortValue; //can be done in application level
    	String search;

    	public Filter(){
    	    List<String> stringList = new ArrayList<String>();
    	    stringList.add("Action");
        }

    public void addQuery(FilterType filterType, List<String> queryParamVal) throws Exception {
        if (filteredColumns.containsKey(filterType)) {
            //throw exception
        }
//    	if(queryParamVal.isEmpty())
//    		    throw new Exception("");
        filteredColumns.put(filterType, queryParamVal);
    }

    public BoolQueryBuilder getQuery() {
        BoolQueryBuilder query = new BoolQueryBuilder();
        for (Map.Entry<FilterType, List<String>> entry : filteredColumns.entrySet()) {
            BoolQueryBuilder innerQuery = new BoolQueryBuilder();
            for (String str : entry.getValue())
                innerQuery.should(QueryBuilders.matchQuery(String.valueOf(entry.getKey()), str));//todo: should replace filter
            query.must(innerQuery);
        }
        filteredColumns.clear();
        return query;
    }

}
