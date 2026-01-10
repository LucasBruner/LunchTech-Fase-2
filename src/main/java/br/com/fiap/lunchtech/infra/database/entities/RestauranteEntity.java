package br.com.fiap.lunchtech.infra.database.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="restaurante")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @Column(name= "tipo_cozinha")
    private String tipoCozinha;

    @Column(name= "horario_inicio")
    private Date horarioFuncionamentoInicio;

    @Column(name= "horario_fim")
    private Date horarioFuncionamentoFim;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity donoRestaurante;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(
            name = "endereco_id",
            unique = true)
    private EnderecoEntity endereco;

    @OneToMany(mappedBy = "restaurante",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CardapioEntity> itensCardapio;
}
