package org.example.service;


import org.example.domain.entity.Pedido;
import org.example.domain.enums.StatusPedido;
import org.example.rest.dto.AtualizacaoStatusPedidoDTO;
import org.example.rest.dto.InformacoesPedidosDTO;
import org.example.rest.dto.PedidoDTO;

import javax.sound.sampled.Line;
import java.util.Optional;

public interface PedidoService {

    Pedido salvar( PedidoDTO dto );


    Optional<Pedido> obterPedidoCompleto( Integer id );

    void AtualizarStatus(Integer id,  StatusPedido status);
}
