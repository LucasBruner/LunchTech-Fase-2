package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

import java.util.List;

public class DeletarUsuarioUseCase {
    IUsuarioGateway usuarioGateway;
    IRestauranteGateway restauranteGateway;

    private DeletarUsuarioUseCase(IUsuarioGateway usuarioGateway,
                                  IRestauranteGateway restauranteGateway) {
        this.usuarioGateway = usuarioGateway;
        this.restauranteGateway = restauranteGateway;
    }

    public static DeletarUsuarioUseCase create (IUsuarioGateway usuarioGateway,
                                                IRestauranteGateway restauranteGateway) {
        return new DeletarUsuarioUseCase(usuarioGateway, restauranteGateway);
    }

    public void run (String login) {
        Usuario usuarioExiste = usuarioGateway.buscarPorLogin(login);

        if (usuarioExiste == null) {
           throw new IllegalArgumentException("Usuário não existe.");
        }
        verificaUsuarioPossuiRestaurante(usuarioExiste);

        usuarioGateway.deletar(login);
    }

    private void verificaUsuarioPossuiRestaurante(Usuario usuario) {
        List<Restaurante> restaurantes = restauranteGateway.buscarRestaurantesPorLogin(usuario);

        if(restaurantes.isEmpty()) {
            throw new IllegalArgumentException("Usuário vinculado a um restaurante, não é possível realizar a exclusão!");
        }
    }
}
