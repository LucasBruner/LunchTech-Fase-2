package br.com.fiap.javacleanarch.core.interfaces;

import br.com.fiap.javacleanarch.core.dto.EstudanteDTO;
import br.com.fiap.javacleanarch.core.dto.NovoEstudanteDTO;

public interface IDataSource {
    EstudanteDTO obterEstudantePorNome(String nomeEstudante);

    EstudanteDTO incluirNovoEstudante(NovoEstudanteDTO novoEstudante);
}
