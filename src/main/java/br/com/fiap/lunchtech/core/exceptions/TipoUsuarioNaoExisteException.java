package br.com.fiap.lunchtech.core.exceptions;

public class TipoUsuarioNaoExisteException extends RuntimeException {
    public TipoUsuarioNaoExisteException(String e) {
        super(e);
    }
}
