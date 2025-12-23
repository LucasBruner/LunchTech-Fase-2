package br.com.fiap.javacleanarch.core.exceptions;

public class EstudanteNaoEncontradoException extends RuntimeException {
    public EstudanteNaoEncontradoException(String nomeEstudante) {
        super("Usuario " + nomeEstudante + " não existe.");
    }
}
