package org.example.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validation.NotEmptyList;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/*{
        "cliente":1,
        "valor":100,
        "item":[{

        "produto":1,
        "quantidade": 10

         }]
     }
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    @NotNull(message="{campo.codigo-cliente.obrigatorio}")
    private Integer cliente;
    @NotNull(message="{campo.total-pedido.obrigatorio}")
    private BigDecimal total;
    @NotEmptyList(message="{campo.items-pedido.obrigatorio}")
    private List<ItemPedidoDTO> items;

}
