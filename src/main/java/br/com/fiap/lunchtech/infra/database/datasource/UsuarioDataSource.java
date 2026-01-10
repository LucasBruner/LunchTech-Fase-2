package br.com.fiap.lunchtech.infra.database.datasource;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.IEnderecoRepository;
import br.com.fiap.lunchtech.infra.database.repositories.ITipoUsuarioRepository;
import br.com.fiap.lunchtech.infra.database.repositories.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsuarioDataSource implements IUsuarioDataSource {
    private final IUsuarioRepository usuarioRepository;
    private final ITipoUsuarioRepository tipoUsuarioRepository;
    private final IEnderecoRepository enderecoRepository;

    public UsuarioDataSource(IUsuarioRepository usuarioRepository, ITipoUsuarioRepository tipoUsuarioRepository, IEnderecoRepository enderecoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public UsuarioDTO obterUsuarioPorLogin(String login) {
        UsuarioEntity usuario = usuarioRepository.findByLogin(login);
        EnderecoDTO endereco = usuarioEntityToEnderecoDTO(usuario);
        return mapToDomainUsuario(usuario, endereco);
    }

    @Override
    public UsuarioDTO incluirNovoUsuario(NovoUsuarioDTO novoUsuarioDTO) {
        UsuarioEntity novoUsuario = new UsuarioEntity();
        TipoUsuarioEntity tipoUsuario = buscarTipoUsuario(novoUsuarioDTO.tipoDeUsuario());
        EnderecoEntity novoEndereco = new EnderecoEntity();

        // Incluir endereço
        novoEndereco.setLogradouro(novoUsuarioDTO.endereco().logradouro());
        novoEndereco.setBairro(novoUsuarioDTO.endereco().bairro());
        novoEndereco.setCep(Integer.valueOf(novoUsuarioDTO.endereco().cep()));
        novoEndereco.setNumero(novoUsuarioDTO.endereco().numero());
        novoEndereco.setCidade(novoUsuarioDTO.endereco().cidade());
        novoEndereco.setEstado(novoUsuarioDTO.endereco().estado());
        enderecoRepository.save(novoEndereco);

        // Incluir usuário
        novoUsuario.setNome(novoUsuarioDTO.nomeUsuario());
        novoUsuario.setEmail(novoUsuarioDTO.enderecoEmail());
        novoUsuario.setLogin(novoUsuarioDTO.login());
        novoUsuario.setSenha(novoUsuarioDTO.senha());
        novoUsuario.setTipoUsuario(tipoUsuario);
        novoUsuario.setEndereco(novoEndereco);
        usuarioRepository.save(novoUsuario);

        return mapToDomainUsuario(novoUsuario, entityToDtoEndereco(novoEndereco));
    }

    @Override
    public List<UsuarioDTO> buscarUsuariosPorNome(String nomeUsuario) {
        List<UsuarioEntity> listUsuarios = usuarioRepository.findByNome(nomeUsuario);

        return listUsuarios.stream()
                .map(usuario -> mapToDomainUsuario(usuario, usuarioEntityToEnderecoDTO(usuario)))
                .collect(Collectors.toList());
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
            UsuarioEntity usuarioAlterar = usuarioRepository.findByLogin(usuarioAlteracaoDTO.login());

            // Atualizar endereço
            EnderecoEntity enderecoAlterar = usuarioAlterar.getEndereco();
            enderecoAlterar.setLogradouro(usuarioAlteracaoDTO.endereco().logradouro());
            enderecoAlterar.setBairro(usuarioAlteracaoDTO.endereco().bairro());
            enderecoAlterar.setCep(Integer.valueOf(usuarioAlteracaoDTO.endereco().cep()));
            enderecoAlterar.setNumero(usuarioAlteracaoDTO.endereco().numero());
            enderecoAlterar.setCidade(usuarioAlteracaoDTO.endereco().cidade());
            enderecoAlterar.setEstado(usuarioAlteracaoDTO.endereco().estado());
            enderecoRepository.save(enderecoAlterar);

            // Atualizar usuário
            usuarioAlterar.setNome(usuarioAlteracaoDTO.nomeUsuario());
            usuarioAlterar.setEmail(usuarioAlteracaoDTO.enderecoEmail());
            usuarioAlterar.setLogin(usuarioAlteracaoDTO.login());
            usuarioAlterar.setTipoUsuario(buscarTipoUsuario(usuarioAlteracaoDTO.tipoDeUsuario()));
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
            UsuarioEntity usuarioDelete = usuarioRepository.findByLogin(login);

            enderecoRepository.deleteById(usuarioDelete.getEndereco().getId());
            usuarioRepository.delete(usuarioDelete);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Tipo de usuário não encontrado!");
        }
    }

    @Override
    public void alterarSenhaUsuario(UsuarioSenhaDTO usuarioSenhaDTO) {
        try {
            UsuarioEntity usuario = usuarioRepository.findByLogin(usuarioSenhaDTO.login());
            usuario.setSenha(usuarioSenhaDTO.senha());
            usuarioRepository.save(usuario);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Usuário não encontrado!");
        }
    }

    @Override
    public UsuarioSenhaDTO buscarDadosUsuarioPorLogin(String login) {
        try {
            UsuarioEntity usuario = usuarioRepository.findByLogin(login);
            return new UsuarioSenhaDTO(usuario.getLogin(), usuario.getSenha());

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Usuário não encontrado!");
        }
    }

    private UsuarioDTO mapToDomainUsuario(UsuarioEntity usuario, EnderecoDTO endereco){
        return new UsuarioDTO(usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getTipoUsuario().getTipoUsuario(),
                endereco);
    }

    private EnderecoDTO usuarioEntityToEnderecoDTO(UsuarioEntity usuario){
        return new EnderecoDTO(usuario.getEndereco().getLogradouro(),
                usuario.getEndereco().getNumero(),
                usuario.getEndereco().getBairro(),
                usuario.getEndereco().getCidade(),
                usuario.getEndereco().getEstado(),
                usuario.getEndereco().getCep().toString());
    }

    private EnderecoDTO entityToDtoEndereco(EnderecoEntity enderecoEntity){
        return new EnderecoDTO(enderecoEntity.getLogradouro(),
                enderecoEntity.getNumero(),
                enderecoEntity.getBairro(),
                enderecoEntity.getCidade(),
                enderecoEntity.getEstado(),
                enderecoEntity.getCep().toString());
    }

    private TipoUsuarioEntity buscarTipoUsuario(String tipo){
        return tipoUsuarioRepository.findByTipoUsuario(tipo);
    }
}
