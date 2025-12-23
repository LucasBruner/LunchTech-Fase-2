package br.com.fiap.javacleanarch.core.usecases;

import br.com.fiap.javacleanarch.core.dto.NovoEstudanteDTO;
import br.com.fiap.javacleanarch.core.entities.Estudante;
import br.com.fiap.javacleanarch.core.interfaces.IEstudanteGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CadastrarEstudanteUseCaseTest {

    @DisplayName("Cadastrar com sucesso")
    @Test
    void testRegistrandoOK() {
        //arrange
        String nomeTestar = "Lucas";
        int idadeTestar = 31;
        String enderecoEmailTestar = "lucas@fiap.com.br";
        IEstudanteGateway estudanteGateway = mock(IEstudanteGateway.class);
        when(estudanteGateway.buscarPorNome(anyString())).thenReturn(null);
        when(estudanteGateway.incluir(any())).thenReturn(Estudante.create("abc", nomeTestar, enderecoEmailTestar, idadeTestar));

        //act
        final Estudante estudante = CadastrarEstudanteUseCase.create(estudanteGateway)
                .run(new NovoEstudanteDTO(nomeTestar, enderecoEmailTestar, idadeTestar));

        //assert
        assertEquals(nomeTestar, estudante.getNome());
        assertEquals(enderecoEmailTestar, estudante.getEnderecoEmail());
        assertEquals(idadeTestar, estudante.getIdade());
        assertEquals("abc", estudante.getIdentificacaoInterna());
    }

}