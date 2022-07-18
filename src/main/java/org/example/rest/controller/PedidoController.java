package org.example.rest.controller;

import org.example.domain.entity.ItemPedido;
import org.example.domain.entity.Pedido;
import org.example.domain.entity.Produto;
import org.example.domain.enums.StatusPedido;
import org.example.domain.repository.Produtos;
import org.example.exception.RegraNegocioException;
import org.example.rest.dto.*;
import org.example.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/api/pedidos/")
public class PedidoController {


    @Autowired
    private PedidoService service;



    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = service.salvar(dto);

        return pedido.getId();


    }

    @GetMapping("{id}")
    public InformacoesPedidosDTO getById(@PathVariable Integer id){

       return service.obterPedidoCompleto(id).map(p -> converter(p))
               .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido nao encontrado"));

    }


    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus( @PathVariable Integer id, @RequestBody @Valid AtualizacaoStatusPedidoDTO dto ){


         String novoStatus = dto.getNovoStatus();
         this.service.AtualizarStatus(id, StatusPedido.valueOf(novoStatus));


    }

    private InformacoesPedidosDTO converter(Pedido pedido){
        return InformacoesPedidosDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .items(converterLista(pedido.getItens()))
                .status(pedido.getStatus().name())
                .build();

    }

    private List<InformacoesItemsPedidosDTO> converterLista(List<ItemPedido> itens ){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map(item -> InformacoesItemsPedidosDTO.builder()
                .descricao(item.getProduto().getDescricao())
                .precoUnitario(item.getProduto().getPreco())
                .build()

        ).collect(Collectors.toList());

    }



}
