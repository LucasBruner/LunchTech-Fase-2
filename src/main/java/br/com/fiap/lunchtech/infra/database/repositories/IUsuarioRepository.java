package br.com.fiap.lunchtech.infra.database.repositories;

import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, String> {
}
