package org.example;


import org.example.domain.entity.Cliente;
import org.example.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@ComponentScan(basePackages = {"org.example.repository ", "org.example.service", "pacote.projetoForaDoEscopo"})
@RestController
public class VendasApplication extends SpringBootServletInitializer {


        @Bean
        public CommandLineRunner commandLineRunner(@Autowired Clientes clientes){
            return args -> {
                Cliente c = new Cliente(null ,"Fulano", "64982021066");
                clientes.save(c);
            };
        }
    /*@Bean
    public CommandLineRunner init(@Autowired Clientes clientes,
    @Autowired Pedidos pedidos) {
        return args -> {
            System.out.printf("Salvando clientes\n");
            Cliente fulano = new Cliente("Fulano");

            clientes.save(fulano);

            Pedido p = new Pedido();
            p.setCliente(fulano);
            //LocalData é uma api do java 8
            p.setDataPedido(LocalDate.now());
            p.setTotal(BigDecimal.valueOf(100));
            pedidos.save(p);


            //List<Cliente> resultados = clientes.encontrarPorNome("Cliente1");
           *//* Cliente cliente = clientes.findClienteFetchPedidos(fulano.getId());
            System.out.println(cliente);
            System.out.println(cliente.getPedidos());
*//*

            pedidos.findByCliente(fulano).forEach(System.out::println);

            //System.out.printf("Existe um cliente com o nome Cliente1?  " + existe);


            *//*System.out.printf("Atualizando Clientes");
            todosOsClientes.forEach(c -> {
                c.setNome(c.getNome() + "atualizado.");
                clientes.save(c);
            });
            System.out.printf("Buscando clientes");
            clientes.findByNomeLike("Cli").forEach(System.out::println);

            System.out.println("Deleteando os clientes");

            clientes.findAll().forEach(c -> {
                clientes.delete(c);
            });


            todosOsClientes = clientes.findAll();
            if(todosOsClientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado");
            }else{
                todosOsClientes.forEach(System.out::println);
            }*//*


        };

    }*/
    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);


    }


}

