package org.example.rest.controller;


import lombok.RequiredArgsConstructor;
import org.example.domain.entity.Usuario;
import org.example.exception.SenhaInvalidaException;
import org.example.rest.dto.CredenciaisDTO;
import org.example.rest.dto.TokenDTO;
import org.example.security.jwt.JwtService;
import org.example.service.impl.UsuarioServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuario/")
@RequiredArgsConstructor
public class UsuarioController {


    private final UsuarioServiceImpl service;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    /*@PostMapping
    public User save(Usuario usuario){

        return service.save(usuario);

    }*/

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar( @RequestBody @Valid Usuario usuario){
        String senhaCriptografada = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        return service.salvar(usuario);


    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();

            UserDetails usuarioAutenticador = service.autenticar(usuario);
            String token = jwtService.gerarToken( usuario );
            return new TokenDTO(usuario.getLogin(), token);

        } catch (UsernameNotFoundException | SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());

        }
    }

}
