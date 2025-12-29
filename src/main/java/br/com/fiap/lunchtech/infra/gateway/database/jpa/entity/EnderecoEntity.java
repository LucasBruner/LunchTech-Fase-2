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
    private String rua;
    private Integer cep;
    private Integer numero;
    private String cidade;
}
