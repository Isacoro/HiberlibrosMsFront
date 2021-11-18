package com.hiberlibros.HiberLibros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLibroDto {
    
    private Integer id;
    private String estadoConservacion;
    private String estadoPrestamo;
    private String quieroTengo;
    private Boolean desactivado;
    private UsuarioDto usuario;
    private LibroDto libro;
}
