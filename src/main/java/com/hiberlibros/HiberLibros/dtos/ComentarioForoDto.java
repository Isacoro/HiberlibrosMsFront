package com.hiberlibros.HiberLibros.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioForoDto {

    private Integer id;
    private String  comentarioForo;
    private UsuarioDto usuarioComentario;
    private ForoLibroDto foroLibro;
}
