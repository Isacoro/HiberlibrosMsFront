package com.hiberlibros.HiberLibros.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSeguridadDtoFeign {

    private Integer id;
    private Integer idUsuario;
    private String mail;
    private String password;
    private List<RolDto> roles;
}
