package br.com.fiap.lunchtech.core.exceptions;

public class LoginInvalidoException extends RuntimeException {
    public LoginInvalidoException(String message) {
        super(message);
    }
}
