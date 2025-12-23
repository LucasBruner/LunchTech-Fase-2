package br.com.fiap.javacleanarch.core.usecases;

import br.com.fiap.javacleanarch.core.entities.Estudante;
import br.com.fiap.javacleanarch.core.exceptions.EstudanteNaoEncontradoException;
import br.com.fiap.javacleanarch.core.interfaces.IEstudanteGateway;

public class BuscarEstudantePorNomeUseCase {
    private final IEstudanteGateway estudanteGateway;

    private BuscarEstudantePorNomeUseCase(IEstudanteGateway estudanteGateway) {
        this.estudanteGateway = estudanteGateway;
    }

    public static BuscarEstudantePorNomeUseCase create(IEstudanteGateway estudanteGateway) {
        return new BuscarEstudantePorNomeUseCase(estudanteGateway);
    }

    public Estudante run(String nomeEstudante) {
        Estudante estudante = this.estudanteGateway.buscarPorNome(nomeEstudante);

        if (estudante == null) {
            throw new EstudanteNaoEncontradoException(nomeEstudante);
        }

        return estudante;
    }
}
