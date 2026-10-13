package com.wallance.api.exception;

public class ResourceNotFoundException extends RuntimeException{
    
    public ResourceNotFoundException(String resource, Object id){
        super("Resource: " + resource + ", ID: " + id + ". Do not exist");
    }
}
