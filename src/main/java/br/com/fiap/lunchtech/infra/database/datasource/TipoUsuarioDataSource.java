package br.com.fiap.lunchtech.infra.database.datasource;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.ITipoUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TipoUsuarioDataSource implements ITipoUsuarioDataSource {
    private ITipoUsuarioRepository tipoUsuarioRepository;

    public TipoUsuarioDataSource(ITipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    @Override
    public TipoUsuarioDTO incluirNovoTipoUsuario(TipoUsuarioDTO tipoUsuarioDTO) {
        var tipoUsuario = new TipoUsuarioEntity();

        tipoUsuario.setTipoUsuario(tipoUsuarioDTO.tipoUsuario());

        return new TipoUsuarioDTO(tipoUsuarioRepository.save(tipoUsuario).getTipoUsuario());
    }

    @Override
    public TipoUsuarioDTO alterarTipoUsuario(TipoUsuarioDTO tipoUsuarioDTO) {
        try {
            var tipoUsuario = tipoUsuarioRepository.findByTipoUsuario(tipoUsuarioDTO.tipoUsuario());
            return new TipoUsuarioDTO(tipoUsuarioRepository.save(tipoUsuario).getTipoUsuario());

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Tipo de usuário não encontrado!");
        }
    }

    @Override
    public void deletarTipoUsuario(String tipoUsuario) {
        try {
            var tipoUsuarioDelete = tipoUsuarioRepository.findByTipoUsuario(tipoUsuario);
            tipoUsuarioRepository.delete(tipoUsuarioDelete);

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Tipo de usuário não encontrado!");
        }
    }

    @Override
    public TipoUsuarioDTO buscarTipoUsuarioPorNome(String tipoUsuario) {
        try {
            var tipoUsuarioNome = tipoUsuarioRepository.findByTipoUsuario(tipoUsuario);
            return new TipoUsuarioDTO(tipoUsuarioNome.getTipoUsuario());

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Tipo de usuário não encontrado!");
        }
    }
}
