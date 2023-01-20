package com.gabrielxavier.gerenciamentopessoa.domain.exceptions;

public class NegocioException extends RuntimeException{

    public NegocioException(String msg) {
        super(msg);
    }
}
