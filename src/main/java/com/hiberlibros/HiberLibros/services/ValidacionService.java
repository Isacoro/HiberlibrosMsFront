package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.dtos.UsuarioDto;
import com.hiberlibros.HiberLibros.dtos.UsuarioSeguridadDto;
import com.hiberlibros.HiberLibros.dtos.UsuarioSeguridadDtoFeign;
import com.hiberlibros.HiberLibros.feign.UsuarioFeign;
import com.hiberlibros.HiberLibros.feign.UsuarioSeguridadFeign;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ValidacionService implements UserDetailsService {

    
    @Autowired
    private UsuarioFeign usuarioFeign;
    @Autowired
    private UsuarioSeguridadFeign usuarioSeguridadFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UsuarioDto> usuario =Optional.of(usuarioFeign.usuarioSeguridadMail(username));
        if (usuario.isPresent()) {

            Optional<UsuarioSeguridadDtoFeign> usuarioSeguridad = Optional.of(usuarioSeguridadFeign.usuarioSeguridadSecurity(usuario.get().getId()));

            if (usuario.isPresent()) {

                UsuarioSeguridadDto obj = new UsuarioSeguridadDto();
                obj.setUserId(usuarioSeguridad.get().getIdUsuario());
                obj.setUsername(usuarioSeguridad.get().getMail());
                obj.setPassword(usuarioSeguridad.get().getPassword());
                List<SimpleGrantedAuthority> roles = usuarioSeguridad.get().getRoles()
                        .stream()
                        .map(x -> new SimpleGrantedAuthority("ROLE_" + x.getNombre_rol()))
                        .collect(Collectors.toList());
                obj.setRoles(roles);
                return obj;
            } else {
                throw new UsernameNotFoundException("Usuario/Password incorrecto");
            }

        } else {
            throw new UsernameNotFoundException("Usuario/Password incorrecto");
        }
    }
}
