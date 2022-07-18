package org.example.security.jwt;


import io.jsonwebtoken.*;
import lombok.Data;
import org.example.VendasApplication;
import org.example.domain.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {


    @Value("${security.jwt.expiracao}")
    private String expiracao;
    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;


    public String gerarToken( Usuario usuario ){

        long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);

        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        return Jwts.builder()
                .setSubject(usuario.getLogin())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();

    }

    public boolean tokenValido( String token ){
        try{
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data =
                    dataExpiracao.
                            toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();

            return !LocalDateTime.now().isAfter(data);
        }catch (Exception e){
            return false;
        }

    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();

    }

    public Claims obterClaims(String token ) throws ExpiredJwtException {
        //.parser() decodifica o token
        //.setSigninKey() -> dizemos a chave que usamos para codificar o tokne
        //.parseClaimsJws -> passamos o token para ele saber qual token deve ser decodificado
        //. getBody contem informacoes do token

        //Quando nos recebemos essa expiredJwtException nos dizemos que o token esta invalido
        return Jwts.parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }



    public static void main(String[]args){
        ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
        JwtService service = contexto.getBean(JwtService.class);
        Usuario usuario = Usuario.builder().login("fulano").build();
        String token = service.gerarToken(usuario);
        System.out.println(token);

        boolean isTokenValido = service.tokenValido(token);
        System.out.printf("O token esta valido?\n " + isTokenValido);
        System.out.printf(service.obterLoginUsuario(token));
    }
}
