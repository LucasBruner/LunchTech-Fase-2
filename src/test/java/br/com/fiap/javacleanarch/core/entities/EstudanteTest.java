package br.com.fiap.javacleanarch.core.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstudanteTest {

    @DisplayName("Cria estudante com sucesso")
    @Test
    void testEstudanteOk() {
        //arrange
        String nomeJoao = "João";
        String enderecoEmailJoao = "joão@fiap.com.br";
        int idadeJoao = 31;

        //act
        var estudante = Estudante.create("João", "joão@fiap.com.br",31);

        //assert
        assertEquals(nomeJoao, estudante.getNome());
        assertEquals(enderecoEmailJoao, estudante.getEnderecoEmail());
        assertEquals(idadeJoao, estudante.getIdade());
        assertNull(estudante.getIdentificacaoInterna());
    }

    @DisplayName("Criar um estudante com identificação")
    @Test
    void testEstudanteIdentificacaoOk() {
        //arrange
        String nomeJoao = "João";
        String enderecoEmailJoao = "joão@fiap.com.br";
        String identificacaoInternaJoao = "abc";
        int idadeJoao = 31;

        //act
        var estudante = Estudante.create("abc", "João", "joão@fiap.com.br",31);

        //assert
        assertEquals(nomeJoao, estudante.getNome());
        assertEquals(enderecoEmailJoao, estudante.getEnderecoEmail());
        assertEquals(idadeJoao, estudante.getIdade());
        assertEquals(identificacaoInternaJoao, estudante.getIdentificacaoInterna());
    }
}