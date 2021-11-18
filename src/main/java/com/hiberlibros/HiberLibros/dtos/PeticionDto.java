package com.hiberlibros.HiberLibros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeticionDto {

    private Integer id;   
    private UsuarioLibroDto idUsuarioLibro;
    private UsuarioDto idUsuarioSolicitante;
    private Boolean aceptacion;
    private Boolean pendienteTratar;
    
}
