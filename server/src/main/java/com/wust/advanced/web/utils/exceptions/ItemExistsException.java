package com.wust.advanced.web.utils.exceptions;

public class ItemExistsException extends RuntimeException{
    public ItemExistsException(String message){
        super(message);
    }
}
