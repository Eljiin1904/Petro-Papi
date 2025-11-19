package com.example.petropapi.network;

import java.util.Map;

public class GraphQLRequest {
    private final String operationName;
    private final Map<String, Object> variables;
    private final String query;

    public GraphQLRequest(String operationName, Map<String, Object> variables, String query) {
        this.operationName = operationName;
        this.variables = variables;
        this.query = query;
    }

    public String getOperationName() {
        return operationName;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public String getQuery() {
        return query;
    }
}
