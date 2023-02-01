package com.gabrielxavier.gerenciamentopessoa.domain.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(String msg) {
        super(msg);
    }
}
