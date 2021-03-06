package com.hiberlibros.HiberLibros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Integer id;
    private String nombre;
    private String apellido;
    private String direccion;
    private String ciudad;
    private String mail;
    private String telef;
    private Double valoracionUsuario;
    private Double longitud;
    private Double latitud;
    private String uriFoto;
    private Boolean desactivado;
}
