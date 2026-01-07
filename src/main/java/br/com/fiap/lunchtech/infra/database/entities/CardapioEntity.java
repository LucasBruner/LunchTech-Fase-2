package br.com.fiap.lunchtech.infra.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Cardapio")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardapioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private Double preco;

    @Column(name= "apenas_presencial")
    private boolean apenasPresencial;

    @Column(name = "foto_prato")
    private String fotoPrato;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private RestauranteEntity restaurante;
}
