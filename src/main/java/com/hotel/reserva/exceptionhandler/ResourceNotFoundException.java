package com.hotel.reserva.exceptionhandler;

public class ResourceNotFoundException  extends  RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}