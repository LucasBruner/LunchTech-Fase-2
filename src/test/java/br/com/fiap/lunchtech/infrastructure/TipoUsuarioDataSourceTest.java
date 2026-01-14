package br.com.fiap.lunchtech.infrastructure;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.infra.database.datasource.TipoUsuarioDataSource;
import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.ITipoUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TipoUsuarioDataSourceTest {

    private TipoUsuarioDataSource tipoUsuarioDataSource;

    @Mock
    private ITipoUsuarioRepository tipoUsuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tipoUsuarioDataSource = new TipoUsuarioDataSource(tipoUsuarioRepository);
    }

    @Test
    void deveIncluirNovoTipoUsuario() {
        // Arrange
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("NOVO_TIPO");
        TipoUsuarioEntity tipoUsuarioEntity = new TipoUsuarioEntity();
        tipoUsuarioEntity.setTipoUsuario("NOVO_TIPO");
        when(tipoUsuarioRepository.save(any(TipoUsuarioEntity.class))).thenReturn(tipoUsuarioEntity);

        // Act
        TipoUsuarioDTO result = tipoUsuarioDataSource.incluirNovoTipoUsuario(tipoUsuarioDTO);

        // Assert
        assertNotNull(result);
        assertEquals("NOVO_TIPO", result.tipoUsuario());
        verify(tipoUsuarioRepository, times(1)).save(any(TipoUsuarioEntity.class));
    }

    @Test
    void deveAlterarTipoUsuario() {
        // Arrange
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("NOVO_TIPO");
        TipoUsuarioEntity tipoUsuarioEntity = new TipoUsuarioEntity();
        tipoUsuarioEntity.setTipoUsuario("TIPO_ANTIGO");
        when(tipoUsuarioRepository.findByTipoUsuario("TIPO_ANTIGO")).thenReturn(tipoUsuarioEntity);
        when(tipoUsuarioRepository.save(any(TipoUsuarioEntity.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        TipoUsuarioDTO result = tipoUsuarioDataSource.alterarTipoUsuario(tipoUsuarioDTO, "TIPO_ANTIGO");

        // Assert
        assertNotNull(result);
        assertEquals("NOVO_TIPO", result.tipoUsuario());
        verify(tipoUsuarioRepository, times(1)).save(any(TipoUsuarioEntity.class));
    }

    @Test
    void deveLancarExcecaoAoAlterarTipoUsuarioInexistente() {
        // Arrange
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("NOVO_TIPO");
        when(tipoUsuarioRepository.findByTipoUsuario("TIPO_INEXISTENTE")).thenThrow(new EntityNotFoundException());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> tipoUsuarioDataSource.alterarTipoUsuario(tipoUsuarioDTO, "TIPO_INEXISTENTE"));
    }

    @Test
    void deveDeletarTipoUsuario() {
        // Arrange
        TipoUsuarioEntity tipoUsuarioEntity = new TipoUsuarioEntity();
        tipoUsuarioEntity.setTipoUsuario("TIPO_EXISTENTE");
        when(tipoUsuarioRepository.findByTipoUsuario("TIPO_EXISTENTE")).thenReturn(tipoUsuarioEntity);
        doNothing().when(tipoUsuarioRepository).delete(tipoUsuarioEntity);

        // Act
        tipoUsuarioDataSource.deletarTipoUsuario("TIPO_EXISTENTE");

        // Assert
        verify(tipoUsuarioRepository, times(1)).delete(tipoUsuarioEntity);
    }

    @Test
    void deveLancarExcecaoAoDeletarTipoUsuarioInexistente() {
        // Arrange
        when(tipoUsuarioRepository.findByTipoUsuario("TIPO_INEXISTENTE")).thenThrow(new EntityNotFoundException());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> tipoUsuarioDataSource.deletarTipoUsuario("TIPO_INEXISTENTE"));
    }

    @Test
    void deveBuscarTipoUsuarioPorNome() {
        // Arrange
        TipoUsuarioEntity tipoUsuarioEntity = new TipoUsuarioEntity();
        tipoUsuarioEntity.setTipoUsuario("TIPO_EXISTENTE");
        when(tipoUsuarioRepository.findByTipoUsuario("TIPO_EXISTENTE")).thenReturn(tipoUsuarioEntity);

        // Act
        TipoUsuarioDTO result = tipoUsuarioDataSource.buscarTipoUsuarioPorNome("TIPO_EXISTENTE");

        // Assert
        assertNotNull(result);
        assertEquals("TIPO_EXISTENTE", result.tipoUsuario());
    }

    @Test
    void deveLancarExcecaoAoBuscarTipoUsuarioPorNomeInexistente() {
        // Arrange
        when(tipoUsuarioRepository.findByTipoUsuario("TIPO_INEXISTENTE")).thenReturn(null);

        // Act & Assert
        assertThrows(TipoUsuarioNaoExisteException.class, () -> tipoUsuarioDataSource.buscarTipoUsuarioPorNome("TIPO_INEXISTENTE"));
    }

    @Test
    void deveBuscarTodosTiposUsuario() {
        // Arrange
        TipoUsuarioEntity tipo1 = new TipoUsuarioEntity();
        tipo1.setTipoUsuario("TIPO1");
        TipoUsuarioEntity tipo2 = new TipoUsuarioEntity();
        tipo2.setTipoUsuario("TIPO2");
        when(tipoUsuarioRepository.findAll()).thenReturn(Arrays.asList(tipo1, tipo2));

        // Act
        List<TipoUsuarioDTO> result = tipoUsuarioDataSource.buscarTodosTipoUsuario();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
