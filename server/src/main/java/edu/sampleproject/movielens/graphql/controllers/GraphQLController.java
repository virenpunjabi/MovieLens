package edu.sampleproject.movielens.graphql.controllers;

import graphql.ExecutionResult;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GraphQLController {

    @Autowired
    GraphQL graphQL;

    @RequestMapping(value = "/graphql")
    public ResponseEntity<Object> Execute(@RequestBody String query) {
        ExecutionResult executionResult = graphQL.execute(query);
        return new ResponseEntity<Object>(executionResult, HttpStatus.OK);
    }
}
