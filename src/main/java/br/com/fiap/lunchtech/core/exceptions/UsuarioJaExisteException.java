package br.com.fiap.lunchtech.core.exceptions;

public class UsuarioJaExisteException extends RuntimeException {
    public UsuarioJaExisteException(String message) {
        super(message);
    }
}
