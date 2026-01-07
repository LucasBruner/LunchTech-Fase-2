package br.com.fiap.lunchtech.core.exceptions;

public class TipoUsuarioJaExisteException extends RuntimeException {
    public TipoUsuarioJaExisteException(String e) {
        super(e);
    }
}
