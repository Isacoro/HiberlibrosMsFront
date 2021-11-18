package com.hiberlibros.HiberLibros.dtos;

import com.hiberlibros.HiberLibros.entities.Libro;
import com.hiberlibros.HiberLibros.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForoLibroDto {

    private Integer id;
    private Boolean desactivado;
    private String  tituloForo;
    private Libro   idLibro;
    private Usuario usuarioCreador;
}
