package br.com.fiap.lunchtech.core.exceptions;

public class UsuarioComEmailJaCadastradoException extends RuntimeException {
    public UsuarioComEmailJaCadastradoException(String message) {
        super(message);
    }
}
