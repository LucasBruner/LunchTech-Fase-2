package br.com.fiap.lunchtech.infra.database.datasource;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioInfoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;
import br.com.fiap.lunchtech.core.interfaces.ICardapioDataSource;
import br.com.fiap.lunchtech.infra.database.repositories.ICardapioRepository;
import org.springframework.stereotype.Component;

@Component
public class CardapioDataSource implements ICardapioDataSource {
    private ICardapioRepository cardapioRepository;

    public CardapioDataSource(ICardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    @Override
    public CardapioInfoDTO incluirNovoProdutoCardapio(NovoCardapioDTO novoCardapioDTO) {
        return null;
    }

    @Override
    public CardapioInfoDTO alterarProdutoCardapio(CardapioAlteradoDTO cardapioAlteradoDTO) {
        return null;
    }

    @Override
    public void deletarProduto(String nomeProduto, String nomeRestaurante) {

    }

    @Override
    public CardapioDTO buscarProdutoPorNome(String nomeProduto, String nomeRestaurante) {
        return null;
    }
}
