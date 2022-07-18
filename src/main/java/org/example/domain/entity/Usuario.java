package org.example.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validation.NotEmptyList;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotEmpty(message = "{campo.login.obrigatorio}")
    private String login;
    @Column
    @NotEmpty(message = "{campo.senha.obrigatorio}")
    private String senha;
    @Column
    private boolean admin;
}
