package br.com.fiap.lunchtech.infra.database.service;

import br.com.fiap.lunchtech.infra.database.dto.tipoUsuario.AlterarTipoUsuarioDTO;
import br.com.fiap.lunchtech.infra.database.dto.tipoUsuario.NovoTipoUsuarioDTO;
import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.ITipoUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoUsuarioService {

    private final ITipoUsuarioRepository tipoUsuarioRepository;

    public TipoUsuarioService(ITipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    public TipoUsuarioEntity save(NovoTipoUsuarioDTO tipoUsuarioDTO) {
        var tipoUsuario = new TipoUsuarioEntity();

        tipoUsuario.setTipoUsuario(tipoUsuarioDTO.tipoUsuario());

        return tipoUsuarioRepository.save(tipoUsuario);
    }

    public void update(AlterarTipoUsuarioDTO tipoUsuarioDTO) {
        var tipoUsuario = tipoUsuarioRepository.findById(tipoUsuarioDTO.id()).orElseThrow(() -> new EntityNotFoundException("Tipo de usuário não encontrado!"));

        tipoUsuario.setTipoUsuario(tipoUsuarioDTO.tipoUsuario());

        tipoUsuarioRepository.save(tipoUsuario);
    }

    public void delete(Long id) {
        var tipoUsuario = tipoUsuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tipo de usuário não encontrado!"));
        tipoUsuarioRepository.delete(tipoUsuario);
    }

    public TipoUsuarioEntity getTipoUsuario(Long id) {
        return tipoUsuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tipo de usuário não encontrado!"));
    }

    public List<TipoUsuarioEntity> getAllTipoUsuario() {
        return tipoUsuarioRepository.findAll();
    }
}
