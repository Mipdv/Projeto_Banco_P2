package com.business;


import com.infrastructure.entity.Conta;
import com.repository.ContaRepository;
import com.repository.EnderecoRepository;
import com.repository.TelefoneRepository;
import com.repository.UsuarioRepository;
import com.exceptions.ConflictException;
import com.exceptions.ResoucerNotFoundException;
import com.infrastructure.entity.Endereco;
import com.infrastructure.entity.Telefone;
import com.infrastructure.entity.Usuario;
import com.infrastructure.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class BancoService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final ContaRepository contaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private static final int IDADE_MINIMA = 18;


    @Transactional
    public Usuario cadastraUsuario(Usuario usuario) {
        validaIdadeMinima(usuario.getDataDeNascimento());
        validaEmail(usuario.getEmail());
        validaSenha(usuario.getSenha());
        validaCpf(usuario.getCpf());
        emailExiste(usuario.getEmail());
        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new ConflictException("CPF já cadastrado");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario salvo = usuarioRepository.save(usuario);
        Conta conta = Conta.builder()
                .usuario(salvo)
                .saldo(BigDecimal.ZERO)
                .limiteCartao(BigDecimal.ZERO)
                .build();
        contaRepository.save(conta);
        return salvo;
    }

    public void validaIdadeMinima(LocalDateTime dataDeNascimento) {
        if (dataDeNascimento == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de nascimento é obrigatória");
        }
        int idade = Period.between(dataDeNascimento.toLocalDate(), LocalDate.now()).getYears();
        if (idade < IDADE_MINIMA) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "É necessário ter pelo menos " + IDADE_MINIMA + " anos para criar uma conta");
        }
    }

    public void validaEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Obrigatório inserir um Email");
        }
        int arroba = email.indexOf("@");
        if (arroba <= 0 || arroba != email.lastIndexOf("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        String dominio = email.substring(arroba + 1);
        int ponto = dominio.lastIndexOf(".");
        if (ponto <= 0 || ponto == dominio.length() - 1) {
            throw new IllegalArgumentException("Email inválido");
        }
    }

    public void validaSenha(String senha){
        if(senha == null || senha.isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Obrigatório inserir uma senha");
        }
    }

    public String validaCpf(String cpf){
        if(cpf == null || cpf.isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Obrigatório inserir o CPF");
        }
        return String.valueOf(cpf.matches( "^(\\d{3}\\.?){3}-?\\d{2}$"));
    }

    public void emailExiste(String email) {
        try {
            boolean existe = emailexiste(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado");
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado");
        }
    }

    public boolean emailexiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Optional<Usuario> listarUsuario(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario localizaPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
    }

    public Usuario atualizaUsuarioPorId(Long id, Usuario usuarioatuAlizado) {
        if (usuarioatuAlizado == null) {
            throw new IllegalArgumentException("Usuario não pode ser nulo");
        }
        if (usuarioatuAlizado.getNome() == null
                || usuarioatuAlizado.getCpf() == null
                || usuarioatuAlizado.getEmail() == null
                || usuarioatuAlizado.getDataDeNascimento() == null
                || usuarioatuAlizado.getTelefone() == null
                || usuarioatuAlizado.getEndereco() == null) {
            throw new IllegalArgumentException("Campo não pode ser nulo");
        }
        Usuario usuario = localizaPorId(id);
        usuario.setNome(usuarioatuAlizado.getNome());
        usuario.setSenha(usuarioatuAlizado.getSenha());
        usuario.setCpf(usuarioatuAlizado.getCpf());
        usuario.setEmail(usuarioatuAlizado.getEmail());
        usuario.setDataDeNascimento(usuarioatuAlizado.getDataDeNascimento());
        usuario.setTelefone(usuarioatuAlizado.getTelefone());
        usuario.setEndereco(usuarioatuAlizado.getEndereco());
        return usuarioRepository.save(usuario);
    }

//     public Usuario atualizaParcialPorId(Long id, Usuario parcial){
//        if(parcial == null){
//            throw new IllegalArgumentException("Usuario nao pode ser nul!");
//        }
//        Usuario usuario = localizaPorId(id);
//        usuario.setNome(parcial.getNome());
//        usuario.setSenha(parcial.getSenha());
//        usuario.setEmail(parcial.getEmail());
//        usuario.setDataDeNascimento(parcial.getDataDeNascimento());
//        usuario.setTelefone(parcial.getTelefone());
//        usuario.setEndereco(parcial.getEndereco());
//        return usuarioRepository.save(usuario);
//     }

    public Usuario atualizarDadosUsuario(String token, Usuario usuario) {
        String email = jwtUtil.extractUsername(token.substring(7));
        usuario.setSenha(usuario.getSenha() != null ? passwordEncoder.encode(usuario.getSenha()) : null);
        Usuario usuarioentity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResoucerNotFoundException("Email não encontrado"));
        return usuarioRepository.save(usuario);
    }

    public Telefone cadastraTelefone(String token, Telefone telefone) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResoucerNotFoundException("Email não existe " + email));
        return telefoneRepository.save(telefone);
    }

    public Telefone atualizaTelefone(Long id, Telefone telefone) {
        Telefone entity = telefoneRepository.findById(id).orElseThrow(() ->
                new ResoucerNotFoundException("Telefone não encontrado " + id));
        return telefoneRepository.save(telefone);
    }

    public Endereco cadastraEndereco(String token, Endereco endereco) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResoucerNotFoundException("Email não localizado " + email));
        return enderecoRepository.save(endereco);
    }

    public Endereco atualizaEndereco(Long id, Endereco endereco) {
        Endereco entity = enderecoRepository.findById(id).orElseThrow(() ->
                new ResoucerNotFoundException("Endereco não encontrado " + endereco));
        return enderecoRepository.save(endereco);
    }

    public void deletaPorEmail(String email) {
        try {
            if (!usuarioRepository.existsByEmail(email)) {//Nao for true
                throw new RuntimeException("Usuario nao existente");//rollback
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Usuario nao existente");//rollback
        }
        usuarioRepository.deleteByEmail(email);//commit
    }
}
