package org.example.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="item_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="pedido")
    private Pedido pedido;


    @ManyToOne
    @JoinColumn(name="produto_id")
    private Produto produto;

    private Integer quantidade;

//--------------------------------------------------------------------

}
