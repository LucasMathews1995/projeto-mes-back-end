package com.lucasmes.mesprojeto.exceptions;

public class NotFoundProductionOrder extends RuntimeException {
    public NotFoundProductionOrder(String message){
        super(message);
    }

}
