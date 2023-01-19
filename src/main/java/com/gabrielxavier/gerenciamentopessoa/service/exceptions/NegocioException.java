package com.gabrielxavier.gerenciamentopessoa.service.exceptions;

public class NegocioException extends RuntimeException{

    public NegocioException(String msg) {
        super(msg);
    }
}
