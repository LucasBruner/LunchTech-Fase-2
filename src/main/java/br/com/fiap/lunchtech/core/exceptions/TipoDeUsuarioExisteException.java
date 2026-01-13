package br.com.fiap.lunchtech.core.exceptions;

public class TipoDeUsuarioExisteException extends RuntimeException {
    public TipoDeUsuarioExisteException(String e) {
        super(e);
    }
}
