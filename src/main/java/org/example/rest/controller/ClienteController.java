package org.example.rest.controller;


import io.swagger.annotations.*;
import org.example.domain.entity.Cliente;
import org.example.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

//@Controller
@RestController // vantagem de restController: ja vem com respondeBody nao precisamos anotar todos os metodos com
                //@RespondeBody
@RequestMapping("/api/cliente/")
@Api("Api Clientes")
public class ClienteController {

    @Autowired
    private Clientes clientes;
    /*@RequestMapping(
            value="/api/clientes/hello/{nome}",
            method = RequestMethod.GET,
            //nao vale a pena usar um consumes no get entao mudamos para post
            //quando usamos esse consumes permitimos que o nosso Cliente possa
            // receber um json ou um xml
            *//*consumes = {"application/json" , "application/xml"},
            produces = { "application/json" , "application/xml" }*//*
    )*/

    //Usaremos o verbo GET para obter dados
    @GetMapping("{id}")
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
        @ApiResponse( code = 200, message = "Cliente encontrado"),
        @ApiResponse(code = 204, message = "Cliente nao encontrado para o ID informado")
    })
    //@ResponseBody Nao precisamos pois agora temos RestController como anotation da classe. Antes era @Controller
    //pathvariable diz que vamos receber uma variavel na url
    public Cliente  getClienteById( @PathVariable @ApiParam("Id do cliente") Integer id ){
        return clientes.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.OK,
                "Cliente nao encontrado"));


       //if(cliente.isPresent()){
           /* ------------------------------OUTRA FORMA---------------------------------------------------------
            Podemos escrever de algumas formas. ResponseEntity<>(passamos o corpo da resposta);
            ResposeEntity<Cliente>  responseEntity = new ResponseEntity<>(cliente.get(), HttpStatus.OK);
            -------------------------------------TOKEN--------------------------------------------------------
            Podemos mandar o HEADERS tambem
            HttpHeaders headers = new HttpHeaders();
            headers.out("Authorization", "token");
            ResposeEntity<Cliente>  responseEntity = new ResponseEntity<>(cliente.get(),headers,  HttpStatus.OK);*/
            //return ResponseEntity.status(HttpStatus.OK).body(cliente.get() );
          //  return ResponseEntity.ok(cliente.get());

        //}





        //Retorna o 404. Precisamos usar o build()
       // return ResponseEntity.notFound().build();

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um novo cliente")
    @ApiResponses({
            @ApiResponse( code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validacao")
    })
    //@ResponseBody Nao precisamos pois agora temos RestController como anotation da classe. Antes era @Controller
    public  Cliente save( @RequestBody @Valid  Cliente cliente) throws MethodArgumentNotValidException {
        //return ResponseEntity.status(HttpStatus.CREATED).body(this.clientes.save(cliente));
        return clientes.save(cliente);

    }


    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable Integer id){

        clientes.findById(id).map(cliente -> {clientes.delete(cliente);
                    return cliente;
       })
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));



    }


    //Nos estamos retornando um ResponseEntity que vai receber um Cliente e um id. O id pegaremos como
    //PathVariable na URL e o Cliente trataremos ele no .map. Nos retornaremos um clientes.findById(id).map
    //Usaremos o .map pois temos um Optional no repository. No .map usaremos uma variavel chamada clienteM
    //@ResponseBody Nao precisamos pois agora temos RestController como anotation da classe. Antes era @Controller
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody  @Valid Cliente cliente, @PathVariable Integer id){
        /*Optional<Cliente> clienteNovo = this.clientes.findById(id);
        clienteNovo.get().setNome(cliente.getNome());
        if(clienteNovo.isPresent()){
            return ResponseEntity.ok().body(this.clientes.save(clienteNovo.get()));
        }

        return ResponseEntity.notFound().build();*/

      clientes.findById(id).map(clienteExistente -> {
          cliente.setNome(clienteExistente.getNome());
          clientes.save(cliente);
          return clienteExistente;
      }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));//orElseGet recebe nenhum parametro e retorna qualquer coisa




    }


    //@ResponseBody Nao precisamos pois agora temos RestController como anotation da classe. Antes era @Controller
    @GetMapping("{nome}")
    public ResponseEntity find(@RequestBody @PathVariable String nome){
        Cliente clienteComNome = this.clientes.findByNome(nome);
        return ResponseEntity.ok().body(clienteComNome);
    }


    /*Get all do meu jeito
    @ResponseBody
    @GetMapping("/api/clientesTodos")
    public List<Cliente> find(){
        List<Cliente> clienteTodos = this.clientes.findAll();
        return clienteTodos;
    }*/


    @GetMapping
    public List<Cliente> find(Cliente filtro ){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase().withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);



        return clientes.findAll(example);
    }







}
