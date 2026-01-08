package br.com.fiap.lunchtech.infra.database.repositories;

import br.com.fiap.lunchtech.infra.database.entities.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRestauranteRepository extends JpaRepository<RestauranteEntity, Long> {
}
