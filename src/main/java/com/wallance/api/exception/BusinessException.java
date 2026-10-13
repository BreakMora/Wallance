package com.wallance.api.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * {@BusinessException}
 * Esta clase atrapa 2 datos: el mensaje y el status HTTP.
 * El mensaje lo hereda de RuntimeException, que ya posee su propio metodo getMesagge().
 * El status es el campo que se agrega al mensaje.
 */
@Getter // Requiere getter para que la clase GlobaHandlerException pueda recibir el mensaje y responder con el status correspondiente
public class BusinessException extends RuntimeException{

    private final HttpStatus status;
    
    public BusinessException(HttpStatus status, String message){
        super(message);
        this.status=status;
    }
}
