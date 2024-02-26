//package com.example.mybookstore_backend.controller;
//
//
//import com.example.mybookstore_backend.service.BookService;
//import graphql.ExecutionInput;
//import graphql.ExecutionResult;
//import graphql.GraphQL;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/graphql")
//public class GraphQLController {
//
//    @Autowired
//    private final GraphQL graphQL;
//    @Autowired
//    BookService bookService;
//
//    public GraphQLController(GraphQL graphQL) {
//        this.graphQL = graphQL;
//    }
//
//    @PostMapping
//    public Object graphql(@RequestBody String query) {
//        ExecutionResult executionResult = graphQL.execute(ExecutionInput.newExecutionInput().query(query).build());
//        return executionResult.toSpecification();
//    }
//}
