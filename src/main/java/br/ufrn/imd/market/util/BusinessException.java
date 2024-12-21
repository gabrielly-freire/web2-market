package br.ufrn.imd.market.util;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class BusinessException extends RuntimeException {
    @Getter
    private final HttpStatus status;

    public BusinessException (String message, HttpStatus status){
        super(message);
        this.status = status;
    }

}