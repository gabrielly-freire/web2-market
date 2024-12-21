package br.ufrn.imd.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.market.dto.AuthDTO;
import br.ufrn.imd.market.dto.LoginResponseDTO;
import br.ufrn.imd.market.dto.RegisterDTO;
import br.ufrn.imd.market.model.Usuario;
import br.ufrn.imd.market.repository.UsuarioRepository;
import br.ufrn.imd.market.service.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth") 
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO dto) {
        if (this.repository.findByLogin(dto.login()) != null) {
            return ResponseEntity.badRequest().body("Login already exists");
        }
        System.out.println(dto);
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        Usuario usuario = new Usuario(dto.login(), encryptedPassword, dto.role());

        this.repository.save(usuario);

        return ResponseEntity.ok().build();

    }

}
