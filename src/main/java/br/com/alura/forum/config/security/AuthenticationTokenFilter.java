package br.com.alura.forum.config.security;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;

    public AuthenticationTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);
        Boolean valido = tokenService.isValidToken(token);

        if (valido) {
            authenticateClient(token);
        }

        filterChain.doFilter(request, response);

    }

    private void authenticateClient(String token) {

        Long usuarioId = tokenService.GetUsuarioId(token);

        Optional<Usuario> opt = usuarioRepository.findById(usuarioId);
        Usuario usuario = opt.orElseThrow(() -> new UsernameNotFoundException("Token Inválido!"));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    private String recuperarToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        return token == null || token.isEmpty() || !token.startsWith("Bearer ")
                ? null
                : token.substring(7, token.length());

    }

}
