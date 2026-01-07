package br.com.fiap.lunchtech.infra.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="Usuario")
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
    private LocalDate dataAtualizacao;

    @OneToOne
    @JoinColumn(name= "tipo_usuario_id",
            unique = true)
    private TipoUsuarioEntity tipoUsuario;

    @OneToOne
    @JoinColumn(
            name = "endereco_id",
            unique = true)
    private EnderecoEntity endereco;
}
