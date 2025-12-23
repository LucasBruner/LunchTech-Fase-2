package br.com.fiap.javacleanarch.core.exceptions;

public class EstudanteJaExisteException extends RuntimeException{
    public EstudanteJaExisteException(String message) {
        super(message);
    }
}
