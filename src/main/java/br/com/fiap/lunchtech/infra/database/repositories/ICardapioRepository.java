package br.com.fiap.lunchtech.infra.database.repositories;

import br.com.fiap.lunchtech.infra.database.entities.CardapioEntity;
import br.com.fiap.lunchtech.infra.database.entities.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICardapioRepository extends JpaRepository<CardapioEntity, Long> {

    @Query("SELECT c FROM CardapioEntity c WHERE c.nome = :nome AND c.restaurante = :restaurante")
    CardapioEntity findByNomeProdutoAndRestauranteId(@Param("nome") String nomeProduto, @Param("restaurante") RestauranteEntity restauranteId);

    List<CardapioEntity> findAllByRestauranteId(Long restauranteId);
}
