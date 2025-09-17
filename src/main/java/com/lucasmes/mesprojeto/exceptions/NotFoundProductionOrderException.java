package com.lucasmes.mesprojeto.exceptions;

public class NotFoundProductionOrderException extends RuntimeException {
    public NotFoundProductionOrderException(String message){
        super(message);
    }

}
