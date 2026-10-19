package com.controller;

import com.business.BancoService;
//import com.business.ConversaoService;
import com.infrastructure.entity.CotacaoRequest;
import com.infrastructure.entity.Endereco;
import com.infrastructure.entity.Telefone;
import com.infrastructure.entity.Usuario;
import com.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")


public class UsuarioController {
    private final BancoService bancoService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
//    private final ConversaoService conversaoService;

    @PostMapping
    ResponseEntity<Usuario> criaUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(bancoService.cadastraUsuario(usuario));
    }

    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getEmail(),
                        usuario.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

//    @GetMapping
//    public ResponseEntity<BigDecimal> converter(@RequestParam String moedaOrigem,
//                                                @RequestParam String moedaDestino,
//                                                @RequestParam BigDecimal valor){
//        CotacaoRequest cotacaoRequest = new CotacaoRequest(moedaOrigem, moedaDestino, valor);
//        return ResponseEntity.ok(conversaoService.converter(cotacaoRequest));
//    }

    @GetMapping("/{id}")
    ResponseEntity<Usuario> buscarUsuario(@RequestBody Long id){
        return ResponseEntity.ok(bancoService.localizaPorId(id));
    }
    @GetMapping
    ResponseEntity<Optional<Usuario>> listaDeUsuarios(@RequestBody String email){
        return ResponseEntity.ok(bancoService.listarUsuario(email));
    }
    @PutMapping
    ResponseEntity<Usuario> atualizaParcialmenteUsuario(@RequestBody Usuario usuario,
                                                        @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(bancoService.atualizarDadosUsuario(token, usuario));
    }

    @PostMapping("/telefone")
     public ResponseEntity<Telefone> cadastraTelefone(@RequestBody Telefone telefone,
                                                      @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(bancoService.cadastraTelefone(token, telefone));
    }

    @PutMapping("/telefone")
    ResponseEntity<Telefone> atualizaTelefone(@RequestBody Telefone telefone,
                                              @RequestParam("id") Long id){
        return ResponseEntity.ok(bancoService.atualizaTelefone(id, telefone));
    }

    @PostMapping("/endereco")
    public ResponseEntity<Endereco> cadastraEndereco(@RequestBody Endereco endereco,
                                                     @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(bancoService.cadastraEndereco(token, endereco));
    }

    @PutMapping("/endereco")
    public ResponseEntity<Endereco> atualizaEndereco(@RequestBody Endereco endereco,
                                                     @RequestParam("id") Long id){
        return ResponseEntity.ok(bancoService.atualizaEndereco(id, endereco));
    }

    @PatchMapping
    ResponseEntity<Usuario> atualizaCompleto(Usuario usuario, Long id){
        return ResponseEntity.ok(bancoService.atualizaUsuarioPorId(id, usuario));
    }

    @DeleteMapping("/{email}")
    ResponseEntity<Void> deletaPorEmail(@PathVariable String email){
        bancoService.deletaPorEmail(email);
        return ResponseEntity.noContent().build();
    }
}
