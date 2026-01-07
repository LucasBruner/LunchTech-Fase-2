package br.com.fiap.lunchtech.infra.database.repositories;

import br.com.fiap.lunchtech.infra.database.entities.CardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardapioRepository extends JpaRepository<CardapioEntity, String> {
}
