package org.example.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.enums.StatusPedido;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;

    @Column(name="data_pedido")
    private LocalDate dataPedido;

    @Column(name =  "total",precision = 20, scale = 2)
    private BigDecimal total;


    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @OneToMany(mappedBy = "pedido") 
    private List<ItemPedido> itens;






}
