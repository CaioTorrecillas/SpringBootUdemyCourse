package org.example.domain.entity;



import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Produto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descricao")
    @NotEmpty(message="{campo.descricao.obrigatorio}")
    private String descricao;

    @Column(name="preco_unitario")
    @NotNull(message="{campo.preco.obrigatorio}")
    private BigDecimal preco; //Do pacote java.math


//--------------------------------------------------------------------

}
