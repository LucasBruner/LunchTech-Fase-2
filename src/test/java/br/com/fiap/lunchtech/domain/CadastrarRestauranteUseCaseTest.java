package br.com.fiap.lunchtech.domain;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.RestauranteJaExistenteException;
import br.com.fiap.lunchtech.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;
import br.com.fiap.lunchtech.core.usecases.restaurante.CadastrarRestauranteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CadastrarRestauranteUseCaseTest {

    private CadastrarRestauranteUseCase cadastrarRestauranteUseCase;

    @Mock
    private IRestauranteGateway restauranteGateway;
    @Mock
    private IUsuarioGateway usuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cadastrarRestauranteUseCase = CadastrarRestauranteUseCase.create(restauranteGateway, usuarioGateway);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteJaExiste() {
        // Arrange
        Date horarioFuncionamentoInicio = null;
        Date horarioFuncionamentoFim = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            horarioFuncionamentoInicio = sdf.parse("10:00");
            horarioFuncionamentoFim = sdf.parse("22:00");
        } catch (ParseException e){
            e.printStackTrace();
        }

        NovoRestauranteDTO novoRestauranteDTO = new NovoRestauranteDTO("restaurante",
                "cozinha",
                horarioFuncionamentoInicio,
                horarioFuncionamentoFim,
                new EnderecoDTO("logradouro",
                        1,
                        "bairro",
                        "cidade",
                        "estado",
                        "cep"), new UsuarioDonoRestauranteDTO("Teste",
                "nome"));
        when(restauranteGateway.buscarPorNome("restaurante")).thenReturn(mock(Restaurante.class));

        // Act & Assert
        assertThrows(RestauranteJaExistenteException.class, () -> cadastrarRestauranteUseCase.run(novoRestauranteDTO));
    }

    @Test
    void deveCadastrarRestauranteComSucesso() {
        // Arrange
        Date horarioFuncionamentoInicio = null;
        Date horarioFuncionamentoFim = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            horarioFuncionamentoInicio = sdf.parse("10:00");
            horarioFuncionamentoFim = sdf.parse("22:00");
        } catch (ParseException e){
            e.printStackTrace();
        }
        NovoRestauranteDTO novoRestauranteDTO = new NovoRestauranteDTO("restaurante",
                "cozinha",
                horarioFuncionamentoInicio,
                horarioFuncionamentoFim,
                new EnderecoDTO("logradouro",
                        1, "bairro",
                        "cidade",
                        "estado",
                        "cep"),
                new UsuarioDonoRestauranteDTO("Teste",
                        "nome"));
        when(restauranteGateway.buscarPorNome("restaurante")).thenThrow(new RestauranteNaoEncontradoException(""));
        when(usuarioGateway.buscarPorLogin("login")).thenReturn(mock(Usuario.class));
        when(restauranteGateway.incluir(any(Restaurante.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Restaurante result = cadastrarRestauranteUseCase.run(novoRestauranteDTO);

        // Assert
        assertNotNull(result);
        assertEquals("restaurante", result.getNome());
        verify(restauranteGateway, times(1)).incluir(any(Restaurante.class));
    }
}
