package br.com.fiap.lunchtech.infra.database.repositories;

import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    UsuarioEntity findByLogin(String login);
    UsuarioEntity findByEmail(String email);
    List<UsuarioEntity> findByNome(String nome);
}
