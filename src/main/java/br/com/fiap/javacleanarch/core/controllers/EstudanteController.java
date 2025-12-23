package br.com.fiap.javacleanarch.core.controllers;

import br.com.fiap.javacleanarch.core.dto.EstudanteDTO;
import br.com.fiap.javacleanarch.core.dto.NovoEstudanteDTO;
import br.com.fiap.javacleanarch.core.exceptions.EstudanteJaExisteException;
import br.com.fiap.javacleanarch.core.gateway.EstudanteGateway;
import br.com.fiap.javacleanarch.core.interfaces.IDataSource;
import br.com.fiap.javacleanarch.core.presenters.EstudantePresenter;
import br.com.fiap.javacleanarch.core.usecases.CadastrarEstudanteUseCase;

public class EstudanteController {

    private final IDataSource dataStorageSource;

    private EstudanteController(IDataSource dataSource) {
        this.dataStorageSource = dataSource;
    }

    public static EstudanteController create(IDataSource dataSource) {
        return new EstudanteController(dataSource);
    }

    public EstudanteDTO cadastrar(NovoEstudanteDTO novoEstudanteDTO) {
        var estudanteGateway = EstudanteGateway.create(this.dataStorageSource);
        var useCase = CadastrarEstudanteUseCase.create(estudanteGateway);

        try {
            var estudante = useCase.run(novoEstudanteDTO);
            var estudanteDTO = EstudantePresenter.toDto(estudante);
            return estudanteDTO;
        } catch (EstudanteJaExisteException e) {
            return null;
        }
    }
}
