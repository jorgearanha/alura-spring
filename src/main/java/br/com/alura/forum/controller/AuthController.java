package br.com.alura.forum.controller;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.controller.dto.TokenDto;
import br.com.alura.forum.controller.form.LoginForm;
import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Profile("prod")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping()
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm login, BCryptPasswordEncoder encoder) {

        UsernamePasswordAuthenticationToken dadosLogin = login.converter();

        try {

            Authentication auth = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(auth);
            return ResponseEntity.ok().body(new TokenDto(token, "Bearer"));

        } catch (AuthenticationException e) {

            return ResponseEntity.badRequest().build();

        }
//
//        Optional<UserDetails> opt = usuarioRepository.findByEmail(login.getEmail());
//        UserDetails usuario = opt.orElseThrow(() -> new UsernameNotFoundException("Não encontrado!"));
//
//        if (encoder.matches(login.getSenha(), usuario.getPassword())) {
//            return ResponseEntity.ok().body(encoder.encode("token"));
//        }
//
//        return ResponseEntity.badRequest().body("Não autorizado!");

    }

}
