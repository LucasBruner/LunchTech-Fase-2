package br.com.fiap.lunchtech.infra.database.repositories;

import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEnderecoRepository extends JpaRepository<EnderecoEntity, Long> {
}
