package br.com.fiap.lunchtech.infrastructure;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.infra.database.datasource.EnderecoDataSource;
import br.com.fiap.lunchtech.infra.database.datasource.RestauranteDataSource;
import br.com.fiap.lunchtech.infra.database.datasource.UsuarioDataSource;
import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import br.com.fiap.lunchtech.infra.database.entities.RestauranteEntity;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.IRestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestauranteDataSourceTest {

    private RestauranteDataSource restauranteDataSource;

    @Mock
    private IRestauranteRepository restauranteRepository;
    @Mock
    private EnderecoDataSource enderecoDataSource;
    @Mock
    private UsuarioDataSource usuarioDataSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restauranteDataSource = new RestauranteDataSource(restauranteRepository, enderecoDataSource, usuarioDataSource);
    }

    @Test
    void deveBuscarRestaurantePorNome() {
        // Arrange
        RestauranteEntity restauranteEntity = new RestauranteEntity();
        restauranteEntity.setDonoRestaurante(new UsuarioEntity());
        restauranteEntity.setEndereco(new EnderecoEntity());
        when(restauranteRepository.findByNome("nome")).thenReturn(restauranteEntity);
        when(enderecoDataSource.entityToDtoEndereco(any())).thenReturn(new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"));
        when(usuarioDataSource.entityToDonoDtoUsuario(any())).thenReturn(new UsuarioDonoRestauranteDTO("login", "nome"));

        // Act
        RestauranteDTO result = restauranteDataSource.buscarRestaurantePorNome("nome");

        // Assert
        assertNotNull(result);
    }

    @Test
    void deveDeletarRestaurante() {
        // Arrange
        RestauranteEntity restauranteEntity = new RestauranteEntity();
        when(restauranteRepository.findByNome("nome")).thenReturn(restauranteEntity);
        doNothing().when(restauranteRepository).delete(restauranteEntity);

        // Act
        restauranteDataSource.deletarRestaurante("nome");

        // Assert
        verify(restauranteRepository, times(1)).delete(restauranteEntity);
    }

    @Test
    void deveAlterarRestaurante() {
        // Arrange
        RestauranteAlteracaoDTO restauranteAlteracaoDTO = new RestauranteAlteracaoDTO(1L, "nome", "cozinha", new Date(), new Date(), new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), new UsuarioDonoRestauranteDTO("login", "nome"));
        when(usuarioDataSource.findByLogin("login")).thenReturn(new UsuarioEntity());
        when(enderecoDataSource.updateFromRestaurante(any(), any())).thenReturn(new EnderecoEntity());
        when(restauranteRepository.save(any(RestauranteEntity.class))).thenAnswer(i -> i.getArgument(0));
        when(enderecoDataSource.restauranteEntityToEnderecoDTO(any())).thenReturn(new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"));
        when(usuarioDataSource.restauranteDonoToDTO(any())).thenReturn(new UsuarioDonoRestauranteDTO("login", "nome"));
        when(enderecoDataSource.mapToDomainRestaurante(any(), any(), any())).thenCallRealMethod();


        // Act
        RestauranteDTO result = restauranteDataSource.alterarRestaurante(restauranteAlteracaoDTO);

        // Assert
        assertNotNull(result);
        assertEquals("nome", result.nomeRestaurante());
        verify(restauranteRepository, times(1)).save(any(RestauranteEntity.class));
    }

    @Test
    void deveIncluirNovoRestaurante() {
        // Arrange
        NovoRestauranteDTO novoRestauranteDTO = new NovoRestauranteDTO("nome", "cozinha", new Date(), new Date(), new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), new UsuarioDonoRestauranteDTO("login", "nome"));
        when(usuarioDataSource.findByLogin("login")).thenReturn(new UsuarioEntity());
        when(enderecoDataSource.save(any(EnderecoDTO.class))).thenReturn(new EnderecoEntity());
        when(restauranteRepository.save(any(RestauranteEntity.class))).thenAnswer(i -> i.getArgument(0));
        when(enderecoDataSource.restauranteEntityToEnderecoDTO(any())).thenReturn(new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"));
        when(usuarioDataSource.restauranteDonoToDTO(any())).thenReturn(new UsuarioDonoRestauranteDTO("login", "nome"));
        when(enderecoDataSource.mapToDomainRestaurante(any(), any(), any())).thenCallRealMethod();

        // Act
        RestauranteDTO result = restauranteDataSource.incluirNovoRestaurante(novoRestauranteDTO);

        // Assert
        assertNotNull(result);
        assertEquals("nome", result.nomeRestaurante());
        verify(restauranteRepository, times(1)).save(any(RestauranteEntity.class));
    }

    @Test
    void deveBuscarRestaurantePorId() {
        // Arrange
        RestauranteEntity restauranteEntity = new RestauranteEntity();
        restauranteEntity.setDonoRestaurante(new UsuarioEntity());
        restauranteEntity.setEndereco(new EnderecoEntity());
        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restauranteEntity));
        when(enderecoDataSource.entityToDtoEndereco(any())).thenReturn(new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"));
        when(usuarioDataSource.entityToDonoDtoUsuario(any())).thenReturn(new UsuarioDonoRestauranteDTO("login", "nome"));

        // Act
        RestauranteDTO result = restauranteDataSource.buscarRestaurantePorId(1L);

        // Assert
        assertNotNull(result);
    }
}
