package br.com.fiap.lunchtech.domain;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.core.exceptions.UsuarioComEmailJaCadastradoException;
import br.com.fiap.lunchtech.core.exceptions.UsuarioJaExisteException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;
import br.com.fiap.lunchtech.core.usecases.usuario.CadastrarUsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CadastrarUsuarioUseCaseTest {

    private CadastrarUsuarioUseCase cadastrarUsuarioUseCase;

    @Mock
    private IUsuarioGateway usuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cadastrarUsuarioUseCase = CadastrarUsuarioUseCase.create(usuarioGateway);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioJaExiste() {
        // Arrange
        NovoUsuarioDTO novoUsuarioDTO = new NovoUsuarioDTO("usuario", "email@teste.com", "login", "senha", "TIPO", new EnderecoDTO("logradouro", 9, "bairro", "cidade", "estado", "cep"));
        when(usuarioGateway.buscarPorLoginExistente("login")).thenReturn(mock(Usuario.class));

        // Act & Assert
        assertThrows(UsuarioJaExisteException.class, () -> cadastrarUsuarioUseCase.run(novoUsuarioDTO));
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaCadastrado() {
        // Arrange
        NovoUsuarioDTO novoUsuarioDTO = new NovoUsuarioDTO("usuario", "email@teste.com", "login", "senha", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "cep"));
        when(usuarioGateway.buscarPorLoginExistente("login")).thenReturn(null);
        when(usuarioGateway.buscarPorEmail("email@teste.com")).thenReturn(true);

        // Act & Assert
        assertThrows(UsuarioComEmailJaCadastradoException.class, () -> cadastrarUsuarioUseCase.run(novoUsuarioDTO));
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoExiste() {
        // Arrange
        NovoUsuarioDTO novoUsuarioDTO = new NovoUsuarioDTO("usuario", "email@teste.com", "login", "senha", "TIPO_INEXISTENTE", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "cep"));
        when(usuarioGateway.buscarPorLoginExistente("login")).thenReturn(null);
        when(usuarioGateway.buscarPorEmail("email@teste.com")).thenReturn(false);
        when(usuarioGateway.buscarSeTipoUsuarioExistente("TIPO_INEXISTENTE")).thenReturn(false);

        // Act & Assert
        assertThrows(TipoUsuarioNaoExisteException.class, () -> cadastrarUsuarioUseCase.run(novoUsuarioDTO));
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        // Arrange
        NovoUsuarioDTO novoUsuarioDTO = new NovoUsuarioDTO("usuario", "email@teste.com", "login", "senha", "TIPO_EXISTENTE", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "cep"));
        when(usuarioGateway.buscarPorLoginExistente("login")).thenReturn(null);
        when(usuarioGateway.buscarPorEmail("email@teste.com")).thenReturn(false);
        when(usuarioGateway.buscarSeTipoUsuarioExistente("TIPO_EXISTENTE")).thenReturn(true);
        when(usuarioGateway.incluir(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Usuario result = cadastrarUsuarioUseCase.run(novoUsuarioDTO);

        // Assert
        assertNotNull(result);
        assertEquals("usuario", result.getNome());
        verify(usuarioGateway, times(1)).incluir(any(Usuario.class));
    }
}
