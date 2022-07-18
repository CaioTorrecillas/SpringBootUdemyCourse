package org.example.domain.repository;


import org.example.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//Mudamos essa classe para o tipo interface e fazemos ela extender o JpaRepository
//Tiramos a anotacao @Repository tambem
//Vamos comentar todos as nossas operacoes que usamos diretamente o entitty manager
//eo jdbcTemplate

//Primeiro nos usamos o JDBCTEMPLATE fizemos queries SQL
// depois usamos o entity manager com algumas queries JPQL
public interface Clientes extends JpaRepository<Cliente, Integer> {

    //Query Methods
    //essa consulta é como se tivessemos escrevendo: select c from CLIENTE c where c.nome like :nome

    //Podemos usar a Query com formato de SQL nativo tambem logo abaixo
    @Query(value = "select * from Cliente c where c.nome like '%nome%'", nativeQuery=true)
    //@Query(value = " select c from Cliente c where c.nome like :nome ")
        List<Cliente> encontrarPorNome(@Param("nome") String nome);


    @Query("delete from Cliente c where c.nome=:nome")
    //Anotamos com @Modifying pois vamos fazr uma transacao na tabela
    @Modifying
    void deleteByNome(String nome);

    //Outros exemplos de query methods o nome dos metodos sao importantes para a realizacao da pesquisa

    // É importante a ordem dos parametro ser igual ao nome dos query methods

    //List<Cliente> findByNomeOrId(String nome, Integer id);

    //List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);



    Cliente findOneByNome(String nome);

    boolean existsByNome(String nome);

    @Query("select c from Cliente c left join fetch c.pedidos p where c.id =:id")
    Cliente findClienteFetchPedidos(@Param("id")Integer id );

    @Query("select c from Cliente c where c.nome =:nome")
    Cliente findByNome(@Param("nome") String nome);

    /*Scripts SQL usando JDBC - usar o JDBC não é objetivo do curso --------
    Esse ponto de interrogacao esta ai pois ainda vamos definir os valores
    private static String INSERT = "insert into cliente (nome) values (?)";
    private static String SELECT_ALL = "select * from CLIENTE";
    private static String UPDATE = "update CLIENTE set nome = ? where id = ?";
    private static String DELETE = "delete from CLIENTE where id = ?";
    private static String FIND_BY_NAME= "select nome = ? from cliente";
    ----------------------------------------------------------------------


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;


    Usando o JDBC para ATUALIZAR-------------------------------------------------------------
    public Cliente atualizar(Cliente cliente){
        jdbcTemplate.update(UPDATE, new Object[]{cliente.getNome(), cliente.getId()} );
        return cliente;
    }-----------------------------------------------------------------------------------------

    Usando o JDBC para SALVAR--------------------------------------------------------------
    public Cliente salvar(Cliente cliente){
        //Nesse metodo .update podemos salvar, atualizar e deletar dados e recebe dois parametros
        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        return cliente;
    }-----------------------------------------------------------------------------------------



    @Transactional
    public Cliente salvar(Cliente cliente){

        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente atualizar(Cliente cliente){
        entityManager.merge(cliente);

        return cliente;
    }

    @Transactional
    public void deletar(Cliente cliente){
        if(!entityManager.contains(cliente)){
            //merge serve para sincronizar
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

    @Transactional
    public void deletar(Integer id){
        Cliente cliente = entityManager.find(Cliente.class, id);
        deletar(cliente);

    }
    @Transactional
    public List<Cliente> buscarPorNome(String nome){
        String jpql = "select c from Cliente c where c.nome like = :nome";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);

        query.setParameter("nome", "%" + nome + "%d");
        return query.getResultList();
    }

    @Transactional
    public List<Cliente> obterTodos(){
        return entityManager
                .createQuery("from Cliente", Cliente.class)
                .getResultList();
    }



    Usando o JDBC para DELETAR--------------------------------------------------------------
    public void deletar(Integer id){
        jdbcTemplate.update(DELETE, new Object[]{id});

    }
    public void deletar(Cliente cliente){
        deletar(cliente.getId());

    }-----------------------------------------------------------------------------------------


    Usando o JDBC para FINDBYNOME--------------------------------------------------------------
    public List<Cliente> buscarPorNome(String nome){
       return jdbcTemplate.query(SELECT_ALL.concat(" where nome like ?"),
                new Object[]{"%" + nome + "%"},
                obterClienteMapper());
    }-----------------------------------------------------------------------------------------

    Usando o JDBC para FINDALL--------------------------------------------------------------
    RowMapper mapeia os resultados de um banco de dados para um classe
    public List<Cliente> obterTodos(){
        return jdbcTemplate.query(SELECT_ALL, obterClienteMapper());

    }

    private RowMapper<Cliente> obterClienteMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                //O GetString vai pegar o valor String da coluna que veio do resultado do select
                return new Cliente(id, nome);

            }
        };
    }*/
}
