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

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario localizaPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResoucerNotFoundException("Usuario nao encontrado"));
    }



    @Transactional
    public Usuario atualizarDadosUsuario(String token, Usuario usuario) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuarioentity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResoucerNotFoundException("Email não encontrado"));

        if (usuario.getCpf() != null && !usuario.getCpf().equals(usuarioentity.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF não pode ser alterado");
        }
        if (usuario.getEmail() != null && !usuario.getEmail().equals(usuarioentity.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail não pode ser alterado");
        }

        if (usuario.getNome() != null) {
            usuarioentity.setNome(usuario.getNome());
        }
        if (usuario.getDataDeNascimento() != null) {
            validaIdadeMinima(usuario.getDataDeNascimento());
            usuarioentity.setDataDeNascimento(usuario.getDataDeNascimento());
        }
        if (usuario.getSenha() != null) {
            validaSenha(usuario.getSenha());
            usuarioentity.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        if (usuario.getObjetivo() != null) {
            usuarioentity.setObjetivo(usuario.getObjetivo());
        }
        if (usuario.getVolume() != null) {
            usuarioentity.setVolume(usuario.getVolume());
        }
        if (usuario.getPerfil() != null) {
            usuarioentity.setPerfil(usuario.getPerfil());
        }
        return usuarioRepository.save(usuarioentity);
    }

    @Transactional
    public Telefone cadastraTelefone(String token, Telefone telefone) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResoucerNotFoundException("Email não existe " + email));
        // O vínculo (usuario_id) vem da lista do Usuario, por causa do @JoinColumn em Usuario
        Telefone salvo = telefoneRepository.save(telefone);
        usuario.getTelefone().add(salvo);
        return salvo;
    }

    public Telefone atualizaTelefone(Long id, Telefone telefone) {
        Telefone entity = telefoneRepository.findById(id).orElseThrow(() ->
                new ResoucerNotFoundException("Telefone não encontrado"));
        if (telefone.getDdd() != null) {//Caso venha vazio no body - atualizacao parcial
            entity.setDdd(telefone.getDdd());
        }
        if (telefone.getNumero() != null) {
            entity.setNumero(telefone.getNumero());
        }
        return telefoneRepository.save(entity);
    }

    @Transactional
    public Endereco cadastraEndereco(String token, Endereco endereco) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResoucerNotFoundException("Email não localizado " + email));
        // O vínculo (usuario_id) vem da lista do Usuario, por causa do @JoinColumn em Usuario
        Endereco salvo = enderecoRepository.save(endereco);
        usuario.getEndereco().add(salvo);
        return salvo;
    }

    public Endereco atualizaEndereco(Long id, Endereco endereco) {
        Endereco entity = enderecoRepository.findById(id).orElseThrow(() ->
                new ResoucerNotFoundException("Endereco não encontrado " + id));
        if (endereco.getRua() != null) {//Caso venha vazio no body - atualizacao parcial
            entity.setRua(endereco.getRua());
        }
        if (endereco.getNumero() != null) {
            entity.setNumero(endereco.getNumero());
        }
        if (endereco.getComplemento() != null) {
            entity.setComplemento(endereco.getComplemento());
        }
        if (endereco.getCidade() != null) {
            entity.setCidade(endereco.getCidade());
        }
        if (endereco.getEstado() != null) {
            entity.setEstado(endereco.getEstado());
        }
        if (endereco.getCep() != null) {
            entity.setCep(endereco.getCep());
        }
        return enderecoRepository.save(entity);
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
