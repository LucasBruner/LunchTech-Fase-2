package br.com.fiap.lunchtech.core.entities;

import br.com.fiap.lunchtech.core.exceptions.UsuarioComInformacaoInvalidaException;

public class TipoUsuario {
    private String tipoUsuario;

    public static TipoUsuario create(String tipoUsuario) {
        validaTipoUsuario(tipoUsuario);

        TipoUsuario tipoDeUsuario = new TipoUsuario();
        tipoDeUsuario.setTipoUsuario(tipoUsuario);

        return tipoDeUsuario;
    }

    private static void validaTipoUsuario(String tipoDeUsuario) {
        if (tipoDeUsuario == null || tipoDeUsuario.trim().isEmpty()) {
            throw new UsuarioComInformacaoInvalidaException("Tipo de usuário inválido");
        }
    }

    public void setTipoUsuario(String tipoUsuario) {
        validaTipoUsuario(tipoUsuario);
        this.tipoUsuario = tipoUsuario.toUpperCase();
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
}
