package org.example.exception;

public class PedidoNaoEncontradoException extends RuntimeException{

    public PedidoNaoEncontradoException(){
        super("Pedido nao foi encontrado");
    }
}
