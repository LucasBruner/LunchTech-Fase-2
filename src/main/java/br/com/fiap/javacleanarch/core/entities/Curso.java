package br.com.fiap.javacleanarch.core.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class Curso {
    private String nome;
    private boolean ativo;

    private List<Estudante> estudantes = new ArrayList<>();

    public Curso(String nome, boolean ativo) {
        this.nome = nome;
        this.ativo = ativo;
    }
    public Curso(String nome) {
        nomeValido(nome);
        this.nome = nome;
        this.ativo = true;
    }

    private boolean nomeValido(String nome) {
        if(nome.trim().isBlank()) {
            throw new IllegalArgumentException("Nome do curso inválido!");
        }
        return true;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
