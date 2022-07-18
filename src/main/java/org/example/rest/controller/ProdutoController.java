package org.example.rest.controller;


import org.example.domain.entity.Produto;
import org.example.domain.repository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;


import static org.springframework.http.HttpStatus.*;
@RestController
@RequestMapping("/api/produto/")
public class ProdutoController {


    //Por conta anotacao de lasse @RestController nao precisamos colocara a anotation autowired
    //podemos colocar um construtor tambem
    @Autowired
    private Produtos produtos;



    @PostMapping
    @ResponseStatus(CREATED)

    public Produto save(@RequestBody @Valid Produto produto){
        return this.produtos.save(produto);
    }

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public Produto getById(@PathVariable Integer id){
        return this.produtos.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto update( @PathVariable Integer id, @RequestBody @Valid Produto produto){

         return produtos.findById(id)
                 .map( produtoNovo -> {
                        produtoNovo.setDescricao(produto.getDescricao());
                        produtoNovo.setPreco(produto.getPreco());
                        produtos.save(produtoNovo);
                        return produtoNovo;
         }).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nao pode encontrar o produto"));

    }

    @DeleteMapping("{id}")

    public void delete(@PathVariable Integer id){
        produtos.findById(id).map(produto -> {
            produtos.delete(produto);
            return produto;

        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado para a delecao"));
    }

    /*@GetMapping
    @ResponseStatus(OK)
    public List<Produto> find(){
        return this.produtos.findAll();
    }
*/

    @GetMapping
    public List<Produto> find(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase(

        ).withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        return produtos.findAll(example);
    }
}
