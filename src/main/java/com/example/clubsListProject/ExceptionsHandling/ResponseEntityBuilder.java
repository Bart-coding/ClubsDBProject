package com.example.clubsListProject.ExceptionsHandling;

import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {
    public static ResponseEntity build(ApiError apiError) {
        return new ResponseEntity<ApiError>(apiError, apiError.getHTTPStatus());
    }
}
