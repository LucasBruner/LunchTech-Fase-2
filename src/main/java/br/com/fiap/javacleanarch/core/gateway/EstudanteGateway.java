package br.com.fiap.javacleanarch.core.gateway;

import br.com.fiap.javacleanarch.core.dto.EstudanteDTO;
import br.com.fiap.javacleanarch.core.dto.NovoEstudanteDTO;
import br.com.fiap.javacleanarch.core.entities.Estudante;
import br.com.fiap.javacleanarch.core.exceptions.EstudanteNaoEncontradoException;
import br.com.fiap.javacleanarch.core.interfaces.IDataSource;
import br.com.fiap.javacleanarch.core.interfaces.IEstudanteGateway;

public class EstudanteGateway implements IEstudanteGateway {

    private final IDataSource dataStorageSource;

    private EstudanteGateway(IDataSource dataStorageSource) {
        this.dataStorageSource = dataStorageSource;
    }

    public static EstudanteGateway create(IDataSource dataStorageSource) {
        return new EstudanteGateway(dataStorageSource);
    }

    @Override
    public Estudante buscarPorNome(String nomeEstudante) {
        EstudanteDTO estudanteDTO = this.dataStorageSource.obterEstudantePorNome(nomeEstudante);
        if(estudanteDTO == null) {
            throw new EstudanteNaoEncontradoException("Estudante com nome " + nomeEstudante + " não encontrado!");
        }
        return Estudante.create(estudanteDTO.identificacaoInterna(),
                estudanteDTO.nome(),
                estudanteDTO.enderecoEmail(),
                estudanteDTO.idade());
    }

    @Override
    public Estudante incluir(Estudante novoEstudante) {
        final NovoEstudanteDTO novoEstudanteDTO = new NovoEstudanteDTO(novoEstudante.getNome(),
                novoEstudante.getEnderecoEmail(),
                novoEstudante.getIdade());

        EstudanteDTO estudanteCriado = this.dataStorageSource.incluirNovoEstudante(novoEstudanteDTO);

        return Estudante.create(estudanteCriado.identificacaoInterna(),
                estudanteCriado.nome(),
                estudanteCriado.enderecoEmail(),
                estudanteCriado.idade());
    }
}
