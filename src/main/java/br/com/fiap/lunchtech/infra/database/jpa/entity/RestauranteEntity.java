package br.com.fiap.lunchtech.infra.database.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="Restaurante")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name= "tipo_cozinha")
    private String tipoCozinha;

    @Column(name= "horario_funcionamento")
    private String horarioFuncionamento;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity donoRestaurante;

    @OneToOne
    @JoinColumn(
            name = "endereco_id",
            unique = true)
    private EnderecoEntity endereco;

    @OneToMany(mappedBy = "restaurante")
    private List<CardapioEntity> itensCardapio;
}
