package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.UsuarioComEmailJaCadastradoException;
import br.com.fiap.lunchtech.core.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AlterarUsuarioUseCaseTest {

    private AlterarUsuarioUseCase alterarUsuarioUseCase;

    @Mock
    private IUsuarioGateway usuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alterarUsuarioUseCase = AlterarUsuarioUseCase.create(usuarioGateway);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        UsuarioAlteracaoDTO usuarioAlteracaoDTO = new UsuarioAlteracaoDTO("nome",
                "email@teste.com",
                "login_invalido",
                "TIPO",
                new EnderecoDTO("logradouro",
                        1, "bairro",
                        "cidade",
                        "estado",
                        "cep"),
                LocalDateTime.now());
        when(usuarioGateway.buscarPorLogin("login_invalido")).thenReturn(null);

        // Act & Assert
        assertThrows(UsuarioNaoEncontradoException.class, () -> alterarUsuarioUseCase.run(usuarioAlteracaoDTO));
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaCadastrado() {
        // Arrange
        UsuarioAlteracaoDTO usuarioAlteracaoDTO = new UsuarioAlteracaoDTO("nome",
                "email@teste.com",
                "login",
                "TIPO",
                new EnderecoDTO("logradouro",
                        1, "bairro",
                        "cidade",
                        "estado",
                        "12344555"),
                LocalDateTime.now());

        Usuario usuario = Usuario.create(
                "nome",
                "email@teste.com",
                "login",
                TipoUsuario.create("tipo"));

        when(usuarioGateway.buscarPorLogin("login")).thenReturn(usuario);
        when(usuarioGateway.buscarSeEmailExistente("email@teste.com", "login")).thenReturn(true);

        // Act & Assert
        assertThrows(UsuarioComEmailJaCadastradoException.class, () -> alterarUsuarioUseCase.run(usuarioAlteracaoDTO));
    }

    @Test
    void deveAlterarUsuarioComSucesso() {
        // Arrange
        UsuarioAlteracaoDTO usuarioAlteracaoDTO = new UsuarioAlteracaoDTO("novo_nome",
                "novo_email@teste.com",
                "login",
                "NOVO_TIPO",
                new EnderecoDTO("logradouro",
                        1, "bairro",
                        "cidade",
                        "estado",
                        "12344555"),
                LocalDateTime.now());
        when(usuarioGateway.buscarPorLogin("login")).thenReturn(mock(Usuario.class));
        when(usuarioGateway.buscarSeEmailExistente("novo_email@teste.com", "login")).thenReturn(false);
        when(usuarioGateway.alterar(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Usuario result = alterarUsuarioUseCase.run(usuarioAlteracaoDTO);

        // Assert
        assertNotNull(result);
        assertEquals("novo_nome", result.getNome());
        verify(usuarioGateway, times(1)).alterar(any(Usuario.class));
    }
}
