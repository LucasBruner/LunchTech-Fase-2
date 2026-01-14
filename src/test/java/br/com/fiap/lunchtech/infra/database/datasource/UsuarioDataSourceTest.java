package br.com.fiap.lunchtech.infra.database.datasource;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.IUsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioDataSourceTest {

    private UsuarioDataSource usuarioDataSource;

    @Mock
    private IUsuarioRepository usuarioRepository;
    @Mock
    private TipoUsuarioDataSource tipoUsuarioDataSource;
    @Mock
    private EnderecoDataSource enderecoDataSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioDataSource = new UsuarioDataSource(usuarioRepository, tipoUsuarioDataSource, enderecoDataSource);
    }

    private UsuarioEntity createUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setTipoUsuario(new TipoUsuarioEntity());
        usuarioEntity.setEndereco(new EnderecoEntity());
        return usuarioEntity;
    }

    @Test
    void deveObterUsuarioPorLogin() {
        // Arrange
        UsuarioEntity usuarioEntity = createUsuarioEntity();
        usuarioEntity.setLogin("login");
        when(usuarioRepository.findByLogin("login")).thenReturn(usuarioEntity);
        when(enderecoDataSource.usuarioEntityToEnderecoDTO(any())).thenReturn(new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"));

        // Act
        UsuarioDTO result = usuarioDataSource.obterUsuarioPorLogin("login");

        // Assert
        assertNotNull(result);
        assertEquals("login", result.login());
    }

    @Test
    void deveIncluirNovoUsuario() {
        // Arrange
        NovoUsuarioDTO novoUsuarioDTO = new NovoUsuarioDTO("nome", "email", "login", "senha", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), LocalDateTime.now());
        when(tipoUsuarioDataSource.buscarTipoUsuario(anyString())).thenReturn(new TipoUsuarioEntity());
        when(enderecoDataSource.save(any(EnderecoDTO.class))).thenReturn(new EnderecoEntity());
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenAnswer(i -> i.getArgument(0));
        when(enderecoDataSource.entityToDtoEndereco(any())).thenReturn(new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"));

        // Act
        UsuarioDTO result = usuarioDataSource.incluirNovoUsuario(novoUsuarioDTO);

        // Assert
        assertNotNull(result);
        assertEquals("login", result.login());
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    void deveBuscarUsuariosPorNome() {
        // Arrange
        UsuarioEntity usuarioEntity = createUsuarioEntity();
        when(usuarioRepository.findByNome("nome")).thenReturn(Arrays.asList(usuarioEntity));
        when(enderecoDataSource.usuarioEntityToEnderecoDTO(any(UsuarioEntity.class))).thenReturn(new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"));

        // Act
        List<UsuarioDTO> result = usuarioDataSource.buscarUsuariosPorNome("nome");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void deveBuscarTodosUsuarios() {
        // Arrange
        UsuarioEntity usuarioEntity = createUsuarioEntity();
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuarioEntity));
        when(enderecoDataSource.usuarioEntityToEnderecoDTO(any(UsuarioEntity.class))).thenReturn(new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"));

        // Act
        List<UsuarioDTO> result = usuarioDataSource.buscarUsuarios();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void deveBuscarUsuarioPorEmail() {
        // Arrange
        UsuarioEntity usuarioEntity = createUsuarioEntity();
        when(usuarioRepository.findByEmail("email")).thenReturn(usuarioEntity);
        when(enderecoDataSource.usuarioEntityToEnderecoDTO(any(UsuarioEntity.class))).thenReturn(new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"));

        // Act
        UsuarioDTO result = usuarioDataSource.buscarUsuarioPorEmail("email");

        // Assert
        assertNotNull(result);
    }

    @Test
    void deveAlterarUsuario() {
        // Arrange
        UsuarioAlteracaoDTO usuarioAlteracaoDTO = new UsuarioAlteracaoDTO("nome", "email", "login", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), LocalDateTime.now());
        UsuarioEntity usuarioEntity = createUsuarioEntity();
        when(usuarioRepository.findByLogin("login")).thenReturn(usuarioEntity);
        when(enderecoDataSource.updateFromUsuario(any(EnderecoDTO.class), any())).thenReturn(new EnderecoEntity());
        when(tipoUsuarioDataSource.buscarTipoUsuario(anyString())).thenReturn(new TipoUsuarioEntity());
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenAnswer(i -> i.getArgument(0));
        when(enderecoDataSource.usuarioEntityToEnderecoDTO(any(UsuarioEntity.class))).thenReturn(new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"));

        // Act
        UsuarioDTO result = usuarioDataSource.alterarUsuario(usuarioAlteracaoDTO);

        // Assert
        assertNotNull(result);
        assertEquals("login", result.login());
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    void deveDeletarUsuario() {
        // Arrange
        UsuarioEntity usuarioEntity = createUsuarioEntity();
        when(usuarioRepository.findByLogin("login")).thenReturn(usuarioEntity);
        doNothing().when(usuarioRepository).delete(usuarioEntity);

        // Act
        usuarioDataSource.deletarUsuario("login");

        // Assert
        verify(usuarioRepository, times(1)).delete(usuarioEntity);
    }

    @Test
    void deveAlterarSenhaUsuario() {
        // Arrange
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO("login", "nova_senha", LocalDateTime.now());
        UsuarioEntity usuarioEntity = createUsuarioEntity();
        when(usuarioRepository.findByLogin("login")).thenReturn(usuarioEntity);
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        usuarioDataSource.alterarSenhaUsuario(usuarioSenhaDTO);

        // Assert
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    void deveBuscarDadosUsuarioPorLogin() {
        // Arrange
        UsuarioEntity usuarioEntity = createUsuarioEntity();
        usuarioEntity.setLogin("login");
        usuarioEntity.setSenha("senha");
        when(usuarioRepository.findByLogin("login")).thenReturn(usuarioEntity);

        // Act
        UsuarioSenhaDTO result = usuarioDataSource.buscarDadosUsuarioPorLogin("login");

        // Assert
        assertNotNull(result);
        assertEquals("login", result.login());
        assertEquals("senha", result.senha());
    }
}