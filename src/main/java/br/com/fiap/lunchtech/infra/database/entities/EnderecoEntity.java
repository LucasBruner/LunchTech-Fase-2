package br.com.fiap.lunchtech.infra.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="endereco")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;
    private String bairro;
    private String cep;
    private Integer numero;
    private String cidade;
    private String estado;

    @OneToOne(mappedBy = "endereco")
    private RestauranteEntity restaurante;

    @OneToOne(mappedBy = "endereco")
    private UsuarioEntity usuario;
}
