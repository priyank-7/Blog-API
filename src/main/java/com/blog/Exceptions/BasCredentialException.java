package com.blog.Exceptions;

public class BasCredentialException extends RuntimeException{

    public BasCredentialException(String message){
        super(message);
    }

    public BasCredentialException(){}
}
