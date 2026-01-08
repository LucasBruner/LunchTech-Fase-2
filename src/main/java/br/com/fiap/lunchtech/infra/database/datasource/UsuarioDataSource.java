package br.com.fiap.lunchtech.infra.database.datasource;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioDataSource implements IUsuarioDataSource {
    private final IUsuarioRepository usuarioRepository;

    public UsuarioDataSource(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioDTO obterUsuarioPorLogin(String login) {
        var usuario = usuarioRepository.findByLogin(login);

        EnderecoDTO endereco = mapToDomainEndereco(usuario);
        return mapToDomainUsuario(usuario, endereco);
    }

    @Override
    public UsuarioDTO incluirNovoUsuario(NovoUsuarioDTO novoUsuarioDTO) {
        return null;
    }

    @Override
    public List<UsuarioDTO> buscarUsuariosPorNome(String nomeUsuario) {
        List<UsuarioEntity> listUsuarios = usuarioRepository.findByNome(nomeUsuario);

        //AJUSTAR CONVERSÃO DE TIPOS DE RETURN
        EnderecoDTO endereco = mapToDomainEndereco(listUsuarios.getFirst());

        return listUsuarios.stream()
                .map(x -> new UsuarioDTO(x.getNome(), x.getEmail(), x.getLogin(), x.getTipoUsuario().getTipoUsuario(), endereco))
                .collect(Collectors.toList());

    }

    @Override
    public UsuarioDTO buscarUsuarioPorEmail(String emailUsuario) {
        var usuario = usuarioRepository.findByEmail(emailUsuario);

        EnderecoDTO endereco = mapToDomainEndereco(usuario);
        return mapToDomainUsuario(usuario, endereco);
    }

    @Override
    public UsuarioDTO alterarUsuario(UsuarioAlteracaoDTO usuarioAlteracaoDTO) {
        try {
            var usuarioExisteste = usuarioRepository.findByLogin(usuarioAlteracaoDTO.login());

            //FINALIZAR

            UsuarioEntity usuarioEntity = new UsuarioEntity();
            usuarioEntity.setNome(usuarioAlteracaoDTO.nomeUsuario());
            usuarioEntity.setEmail(usuarioAlteracaoDTO.enderecoEmail());
            usuarioEntity.setLogin(usuarioAlteracaoDTO.login());
            //usuarioEntity.setTipoUsuario(usuarioAlteracaoDTO.tipoDeUsuario());
            //usuarioEntity.setEndereco(usuarioAlteracaoDTO.endereco());

            UsuarioEntity usuario = usuarioRepository.save(usuarioExisteste);

            EnderecoDTO endereco = mapToDomainEndereco(usuario);
            return mapToDomainUsuario(usuario, endereco);

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Tipo de usuário não encontrado!");
        }
    }

    @Override
    public void deletarUsuario(String login) {
        try {
            var tipoUsuarioDelete = usuarioRepository.findByLogin(login);
            usuarioRepository.delete(tipoUsuarioDelete);

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Tipo de usuário não encontrado!");
        }
    }

    @Override
    public void alterarSenhaUsuario(UsuarioSenhaDTO usuarioSenhaDTO) {

    }

    @Override
    public UsuarioSenhaDTO buscarDadosUsuarioPorLogin(String login) {
        return null;
    }

    private UsuarioDTO mapToDomainUsuario(UsuarioEntity usuario, EnderecoDTO endereco){
        return new UsuarioDTO(usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getTipoUsuario().getTipoUsuario(),
                endereco);
    }

    private EnderecoDTO mapToDomainEndereco(UsuarioEntity usuario){
        return new EnderecoDTO(usuario.getEndereco().getLogradouro(),
                usuario.getEndereco().getNumero(),
                usuario.getEndereco().getBairro(),
                usuario.getEndereco().getCidade(),
                usuario.getEndereco().getEstado(),
                usuario.getEndereco().getCep().toString());
    }
}
