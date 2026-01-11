package br.com.fiap.lunchtech.infra.database.repositories;

import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEnderecoRepository extends JpaRepository<EnderecoEntity, Long> {

    @Query(
            value = "SELECT * FROM endereco WHERE id = (SELECT endereco_id FROM usuario WHERE id = :idUsuario)",
            nativeQuery = true)
    EnderecoEntity findByUsuarioId(Long idUsuario);

    @Query(
            value = "SELECT * FROM endereco WHERE id = (SELECT endereco_id FROM restaurante WHERE id = :idRestaurante)",
            nativeQuery = true)
    EnderecoEntity findByRestauranteId(Long idRestaurante);
}
