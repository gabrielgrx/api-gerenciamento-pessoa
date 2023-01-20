package com.gabrielxavier.gerenciamentopessoa.api.exceptionhandler;

import com.gabrielxavier.gerenciamentopessoa.domain.exceptions.NegocioException;
import com.gabrielxavier.gerenciamentopessoa.domain.exceptions.PessoaNaoEncontradaException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<Problema.Campo> campos = new ArrayList<>();

        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            String nome = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            Object parametro = ((FieldError) error).getRejectedValue();

            campos.add(new Problema.Campo(nome, mensagem, parametro));
        }

        Problema problema = new Problema();
        problema.setStatus(status.value());
        problema.setDataHora(LocalDateTime.now());
        problema.setTitulo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.");
        problema.setCampos(campos);

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(ex, getProblema(ex, status), new HttpHeaders(), status, request);
    }

    @ExceptionHandler(PessoaNaoEncontradaException.class)
    public ResponseEntity<Object> handlePessoaNaoEncontrada(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(ex, getProblema(ex, status), new HttpHeaders(), status, request);
    }

    private Problema getProblema(NegocioException ex, HttpStatus httpStatus) {
        Problema problem = new Problema();

        problem.setStatus(httpStatus.value());
        problem.setTitulo(ex.getMessage());
        problem.setDataHora(LocalDateTime.now());

        return problem;
    }
}
