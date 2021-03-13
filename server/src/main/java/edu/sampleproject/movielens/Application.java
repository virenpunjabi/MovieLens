package edu.sampleproject.movielens;

import edu.sampleproject.movielens.graphql.configurations.ScalarConfig;
import edu.sampleproject.movielens.graphql.service.GraphQLService;
import graphql.GraphQL;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@SpringBootApplication
public class Application {

    @Autowired(required = true)
    GraphQLService graphQLService;
    @Autowired
    ScalarConfig scalarConfig;

    @Autowired
    GraphQLScalarType graphQLScalarType;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public GraphQL graphQL() throws IOException {
        SchemaParser schemaParser = new SchemaParser();
        ClassPathResource schema = new ClassPathResource("schema.graphql");
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema.getInputStream());
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .scalar(graphQLScalarType)
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getMovie", graphQLService.getMovie()))
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getNRecentMovies", graphQLService.getNRecentMovies()))
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getNReviewsForMovie", graphQLService.getNReviewsForMovie()))
                .build();
        SchemaGenerator generator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = generator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        return GraphQL.newGraphQL(graphQLSchema).build();

    }
}
