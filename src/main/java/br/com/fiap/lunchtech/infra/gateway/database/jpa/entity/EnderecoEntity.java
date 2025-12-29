package br.com.fiap.lunchtech.infra.gateway.database.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Endereco")
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
    private Integer cep;
    private Integer numero;
    private String cidade;
    private String estado;
}
