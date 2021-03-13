package edu.sampleproject.movielens.graphql.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GraphQLRequestBody {
    private String query;
    private String operationName;
    private Map<String,Object> variables;
}
