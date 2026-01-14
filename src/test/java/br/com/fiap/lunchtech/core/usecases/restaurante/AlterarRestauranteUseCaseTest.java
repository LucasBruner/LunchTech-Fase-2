package br.com.fiap.lunchtech.core.usecases.restaurante;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.RestauranteJaExistenteException;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AlterarRestauranteUseCaseTest {

    private AlterarRestauranteUseCase alterarRestauranteUseCase;

    @Mock
    private IRestauranteGateway restauranteGateway;
    @Mock
    private IUsuarioGateway usuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alterarRestauranteUseCase = AlterarRestauranteUseCase.create(restauranteGateway, usuarioGateway);
    }

    @Test
    void deveLancarExcecaoQuandoNovoNomeRestauranteJaExiste() {
        // Arrange
        LocalTime horarioFuncionamentoInicio = LocalTime.parse("11:30:00");
        LocalTime horarioFuncionamentoFim = LocalTime.parse("22:00:00");
        RestauranteAlteracaoDTO restauranteAlteracaoDTO = new RestauranteAlteracaoDTO(1L,
                "novo_nome",
                "cozinha",
                horarioFuncionamentoInicio,
                horarioFuncionamentoFim,
                new EnderecoDTO("logradouro",
                        1,
                        "bairro",
                        "cidade",
                        "estado",
                        "01001000"),
                new UsuarioDonoRestauranteDTO("login", "nome"));
        Restaurante restauranteAtual = mock(Restaurante.class);
        when(restauranteAtual.getNome()).thenReturn("nome_antigo");
        when(restauranteGateway.buscarRestaurantePorId(1L)).thenReturn(restauranteAtual);
        when(restauranteGateway.buscarPorNome("novo_nome")).thenReturn(mock(Restaurante.class));

        // Act & Assert
        assertThrows(RestauranteJaExistenteException.class, () -> alterarRestauranteUseCase.run(restauranteAlteracaoDTO));
    }

    @Test
    void deveAlterarRestauranteComSucesso() {
        // Arrange
        LocalTime horarioFuncionamentoInicio = LocalTime.parse("11:30:00");
        LocalTime horarioFuncionamentoFim = LocalTime.parse("22:00:00");
        RestauranteAlteracaoDTO restauranteAlteracaoDTO = new RestauranteAlteracaoDTO(1L,
                "novo_nome",
                "cozinha",
                horarioFuncionamentoInicio,
                horarioFuncionamentoFim,
                new EnderecoDTO("logradouro",
                        1,
                        "bairro",
                        "cidade",
                        "estado",
                        "01001000"),
                new UsuarioDonoRestauranteDTO("login",
                        "nome"));
        Restaurante restauranteAtual = mock(Restaurante.class);
        Usuario usuarioMock = mock(Usuario.class);
        TipoUsuario tipoUsuarioMock = mock(TipoUsuario.class);
        when(tipoUsuarioMock.getTipoUsuario()).thenReturn("DONO_RESTAURANTE");
        when(usuarioMock.getTipoDeUsuario()).thenReturn(tipoUsuarioMock);
        when(restauranteAtual.getNome()).thenReturn("nome_antigo");
        when(restauranteGateway.buscarRestaurantePorId(1L)).thenReturn(restauranteAtual);
        when(restauranteGateway.buscarPorNome("novo_nome")).thenReturn(null);
        when(usuarioGateway.buscarPorLogin("login")).thenReturn(usuarioMock);
        when(restauranteGateway.alterar(any(Restaurante.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Restaurante result = alterarRestauranteUseCase.run(restauranteAlteracaoDTO);

        // Assert
        assertNotNull(result);
        assertEquals("novo_nome", result.getNome());
        verify(restauranteGateway, times(1)).alterar(any(Restaurante.class));
    }
}
