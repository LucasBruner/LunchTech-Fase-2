package br.com.fiap.javacleanarch.core.presenters;

import br.com.fiap.javacleanarch.core.dto.EstudanteDTO;
import br.com.fiap.javacleanarch.core.entities.Estudante;

public class EstudantePresenter {

    public static EstudanteDTO toDto(Estudante estudante) {
        final String identificacao = estudante.getIdentificacaoInterna();
        final String identificacaoOfuscada = identificacao.charAt(1) + "..." + identificacao.charAt(identificacao.length() -1);

        return new EstudanteDTO(estudante.getNome(),
                estudante.getEnderecoEmail(),
                estudante.getIdade(),
                identificacaoOfuscada);
    }
}
