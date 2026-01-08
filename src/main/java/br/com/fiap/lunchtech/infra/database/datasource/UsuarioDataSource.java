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
        var usuario = usuarioRepository.findByLogin(login);

        EnderecoDTO endereco = usuarioEntityToEnderecoDTO(usuario);
        return mapToDomainUsuario(usuario, endereco);
    }

    @Override
    public UsuarioDTO incluirNovoUsuario(NovoUsuarioDTO novoUsuarioDTO) {
        UsuarioEntity novoUsuario = new UsuarioEntity();
        TipoUsuarioEntity tipoUsuario = buscarTipoUsuario(novoUsuarioDTO.tipoDeUsuario());
        EnderecoEntity novoEndereco = new EnderecoEntity();

        novoEndereco.setLogradouro(novoUsuarioDTO.endereco().logradouro());
        novoEndereco.setBairro(novoUsuarioDTO.endereco().bairro());
        novoEndereco.setCep(Integer.valueOf(novoUsuarioDTO.endereco().cep()));
        novoEndereco.setNumero(novoUsuarioDTO.endereco().numero());
        novoEndereco.setCidade(novoUsuarioDTO.endereco().cidade());
        novoEndereco.setEstado(novoUsuarioDTO.endereco().estado());
        enderecoRepository.save(novoEndereco);

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

        //AJUSTAR CONVERSÃO DE TIPOS DE RETURN
        EnderecoDTO endereco = usuarioEntityToEnderecoDTO(listUsuarios.getFirst());

        return listUsuarios.stream()
                .map(x -> new UsuarioDTO(x.getNome(), x.getEmail(), x.getLogin(), x.getTipoUsuario().getTipoUsuario(), endereco))
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
            UsuarioEntity usuarioExisteste = usuarioRepository.findByLogin(usuarioAlteracaoDTO.login());

            //FINALIZAR

            UsuarioEntity usuarioEntity = new UsuarioEntity();
            usuarioEntity.setNome(usuarioAlteracaoDTO.nomeUsuario());
            usuarioEntity.setEmail(usuarioAlteracaoDTO.enderecoEmail());
            usuarioEntity.setLogin(usuarioAlteracaoDTO.login());
            //usuarioEntity.setTipoUsuario(usuarioAlteracaoDTO.tipoDeUsuario());
            //usuarioEntity.setEndereco(usuarioAlteracaoDTO.endereco());

            UsuarioEntity usuario = usuarioRepository.save(usuarioExisteste);

            EnderecoDTO endereco = usuarioEntityToEnderecoDTO(usuario);
            return mapToDomainUsuario(usuario, endereco);

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Tipo de usuário não encontrado!");
        }
    }

    @Override
    public void deletarUsuario(String login) {
        try {
            UsuarioEntity tipoUsuarioDelete = usuarioRepository.findByLogin(login);
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
