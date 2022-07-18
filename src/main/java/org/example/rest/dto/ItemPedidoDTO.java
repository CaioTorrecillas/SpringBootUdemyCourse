package org.example.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ItemPedidoDTO {
    private Integer produto;
    private Integer quantidade;

}
