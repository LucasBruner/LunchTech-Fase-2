package br.com.fiap.lunchtech.infra.database.datasource;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.*;
import br.com.fiap.lunchtech.core.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioDataSource implements IUsuarioDataSource {
    private final IUsuarioRepository usuarioRepository;
    private final TipoUsuarioDataSource tipoUsuarioDataSource;
    private final EnderecoDataSource enderecoDataSource;

    public UsuarioDataSource(IUsuarioRepository usuarioRepository,
                             TipoUsuarioDataSource tipoUsuarioDataSource,
                             EnderecoDataSource enderecoDataSource) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioDataSource = tipoUsuarioDataSource;
        this.enderecoDataSource = enderecoDataSource;
    }

    @Override
    public UsuarioDTO obterUsuarioPorLogin(String login) {
        try{
            UsuarioEntity usuario = findByLogin(login);
            EnderecoDTO endereco = usuarioEntityToEnderecoDTO(usuario);
            return mapToDomainUsuario(usuario, endereco);
        } catch (NullPointerException _) {
            return null;
        }
    }

    @Override
    public UsuarioDTO incluirNovoUsuario(NovoUsuarioDTO novoUsuarioDTO) {
        UsuarioEntity novoUsuario = new UsuarioEntity();
        TipoUsuarioEntity tipoUsuario = buscarTipoUsuario(novoUsuarioDTO.tipoDeUsuario());

        // Incluir endereço
        EnderecoEntity novoEndereco = enderecoDataSource.save(novoUsuarioDTO.endereco());

        // Incluir usuário
        novoUsuario.setNome(novoUsuarioDTO.nomeUsuario());
        novoUsuario.setEmail(novoUsuarioDTO.enderecoEmail());
        novoUsuario.setLogin(novoUsuarioDTO.login());
        novoUsuario.setSenha(novoUsuarioDTO.senha());
        novoUsuario.setTipoUsuario(tipoUsuario);
        novoUsuario.setEndereco(novoEndereco);
        novoUsuario.setDataAtualizacao(novoUsuarioDTO.dataAtualizacao());

        usuarioRepository.save(novoUsuario);

        return mapToDomainUsuario(novoUsuario, entityToDtoEndereco(novoEndereco));
    }

    @Override
    public List<UsuarioDTO> buscarUsuariosPorNome(String nomeUsuario) {
        List<UsuarioEntity> listUsuarios = usuarioRepository.findByNome(nomeUsuario);

        return listUsuarios.stream()
                .map(usuario -> mapToDomainUsuario(usuario, usuarioEntityToEnderecoDTO(usuario)))
                .toList();
    }

    @Override
    public List<UsuarioDTO> buscarUsuarios() {
        List<UsuarioEntity> listUsuarios = usuarioRepository.findAll();

        return listUsuarios.stream()
                .map(usuario -> mapToDomainUsuario(usuario, usuarioEntityToEnderecoDTO(usuario)))
                .toList();
    }

    @Override
    public UsuarioDTO buscarUsuarioPorEmail(String emailUsuario) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(emailUsuario);
        EnderecoDTO endereco = usuarioEntityToEnderecoDTO(usuario);
        return mapToDomainUsuario(usuario, endereco);
    }

    @Override
    public UsuarioDTO alterarUsuario(UsuarioAlteracaoDTO usuarioAlteracaoDTO) {
        try {
            UsuarioEntity usuarioAlterar = findByLogin(usuarioAlteracaoDTO.login());

            // Atualizar endereço
            EnderecoEntity enderecoAlterar =
                    enderecoDataSource.updateFromUsuario(usuarioAlteracaoDTO.endereco(), usuarioAlterar.getId());

            // Atualizar user
            usuarioAlterar.setNome(usuarioAlteracaoDTO.nomeUsuario());
            usuarioAlterar.setEmail(usuarioAlteracaoDTO.enderecoEmail());
            usuarioAlterar.setLogin(usuarioAlteracaoDTO.login());
            usuarioAlterar.setTipoUsuario(buscarTipoUsuario(usuarioAlteracaoDTO.tipoDeUsuario()));
            usuarioAlterar.setDataAtualizacao(usuarioAlteracaoDTO.dataAtualizacao());
            usuarioRepository.save(usuarioAlterar);

            EnderecoDTO enderecoDTO = usuarioEntityToEnderecoDTO(usuarioAlterar);
            return mapToDomainUsuario(usuarioAlterar, enderecoDTO);

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Usuário não encontrado!");
        }
    }

    @Override
    public void deletarUsuario(String login) {
        try {
            UsuarioEntity usuarioDelete = findByLogin(login);
            usuarioRepository.delete(usuarioDelete);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Tipo de usuário não encontrado!", e);
        }
    }

    @Override
    public void alterarSenhaUsuario(UsuarioSenhaDTO usuarioSenhaDTO) {
        try {
            UsuarioEntity usuario = findByLogin(usuarioSenhaDTO.login());
            usuario.setSenha(usuarioSenhaDTO.senha());
            usuario.setDataAtualizacao(usuarioSenhaDTO.dataAtualizacao());
            usuarioRepository.save(usuario);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Usuário não encontrado!");
        }
    }

    @Override
    public UsuarioSenhaDTO buscarDadosUsuarioPorLogin(String login) {
        UsuarioEntity usuario = findByLogin(login);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado!");
        }
        return new UsuarioSenhaDTO(usuario.getLogin(), usuario.getSenha(), usuario.getDataAtualizacao());
    }

    public UsuarioEntity findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    private UsuarioDTO mapToDomainUsuario(UsuarioEntity usuario, EnderecoDTO endereco){
        if (usuario == null) return null;
        return new UsuarioDTO(usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getTipoUsuario().getTipoUsuario(),
                endereco);
    }

    public UsuarioDonoRestauranteDTO entityToDonoDtoUsuario(UsuarioEntity usuario){
        return new UsuarioDonoRestauranteDTO(usuario.getLogin(), usuario.getNome());
    }

    public UsuarioDonoRestauranteDTO restauranteDonoToDTO(UsuarioEntity donoRestaurante) {
        return new UsuarioDonoRestauranteDTO(donoRestaurante.getLogin(), donoRestaurante.getNome());
    }

    private EnderecoDTO usuarioEntityToEnderecoDTO(UsuarioEntity usuario){
        return enderecoDataSource.usuarioEntityToEnderecoDTO(usuario);
    }

    private EnderecoDTO entityToDtoEndereco(EnderecoEntity enderecoEntity){
        return enderecoDataSource.entityToDtoEndereco(enderecoEntity);
    }

    private TipoUsuarioEntity buscarTipoUsuario(String tipo){
        return tipoUsuarioDataSource.buscarTipoUsuario(tipo);
    }
}
