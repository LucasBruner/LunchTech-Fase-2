package br.com.fiap.lunchtech.infra.database.service;

import br.com.fiap.lunchtech.infra.database.dto.usuario.AlterarUsuarioDTO;
import br.com.fiap.lunchtech.infra.database.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;

    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioEntity save(NovoUsuarioDTO usuarioDTO) {
        var usuario = new UsuarioEntity();

        usuario.setNome(usuarioDTO.nomeUsuario());
        usuario.setEmail(usuarioDTO.enderecoEmail());
        usuario.setLogin(usuarioDTO.login());
        usuario.setSenha(usuarioDTO.senha());
        usuario.setTipoUsuario(usuarioDTO.tipoDeUsuario());
        usuario.setEndereco(usuarioDTO.endereco());

        return usuarioRepository.save(usuario);
    }

    public void update(AlterarUsuarioDTO usuarioDTO) {
        var usuario = usuarioRepository.findById(usuarioDTO.id()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));

        usuario.setNome(usuarioDTO.nomeUsuario());
        usuario.setEmail(usuarioDTO.enderecoEmail());
        usuario.setLogin(usuarioDTO.login());
        usuario.setTipoUsuario(usuarioDTO.tipoDeUsuario());
        usuario.setEndereco(usuarioDTO.endereco());

        usuarioRepository.save(usuario);
    }

    public void delete(Long id) {
        var usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
        usuarioRepository.delete(usuario);
    }

    public UsuarioEntity getById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
    }

    public List<UsuarioEntity> getAll() {
        return usuarioRepository.findAll();
    }
}
