package br.com.fiap.lunchtech.infra.database.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="usuario")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String login;

    private String senha;

    @Column(unique = true)
    private String email;

    @Column(name= "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(
            name= "tipo_usuario_id",
            nullable = false)
    private TipoUsuarioEntity tipoUsuario;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(
            name = "endereco_id",
            unique = true)
    private EnderecoEntity endereco;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(
            mappedBy = "donoRestaurante",
            fetch = FetchType.LAZY)
    private List<RestauranteEntity> restaurantes;
}
