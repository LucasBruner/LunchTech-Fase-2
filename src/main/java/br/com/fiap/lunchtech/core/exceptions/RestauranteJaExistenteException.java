package br.com.fiap.lunchtech.core.exceptions;

import java.sql.SQLException;

public class RestauranteJaExistenteException extends RuntimeException {
    public RestauranteJaExistenteException(String e) {
        super(e);
    }

    public RestauranteJaExistenteException(String restauranteJaExiste, SQLException e) {
        super(restauranteJaExiste, e);
    }
}
