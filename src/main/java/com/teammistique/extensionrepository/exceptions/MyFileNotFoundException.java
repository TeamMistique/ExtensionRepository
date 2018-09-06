package com.teammistique.extensionrepository.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends Throwable {
    public MyFileNotFoundException(String message){
        super(message);
    }
}
