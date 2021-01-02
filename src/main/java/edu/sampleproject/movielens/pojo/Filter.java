package edu.sampleproject.movielens.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Filter {

    	Map<FilterType, List<String>> filteredColumns;
    	String sortValue; //can be done in application level
    	String search;

    	public Filter(){
    	    List<String> stringList = new ArrayList<String>();
    	    stringList.add("Action");
        }

		public void addQuery(FilterType filterType,List<String> queryParamVal) throws Exception{
    		if(filteredColumns.containsKey(filterType)){
    			//throw excep
			}
    		filteredColumns.put(filterType,queryParamVal);
		}

}
