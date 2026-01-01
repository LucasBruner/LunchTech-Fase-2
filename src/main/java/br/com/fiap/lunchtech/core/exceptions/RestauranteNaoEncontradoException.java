package br.com.fiap.lunchtech.core.exceptions;

public class RestauranteNaoEncontradoException extends RuntimeException {
    public RestauranteNaoEncontradoException(String e) {
        super(e);
    }
}
