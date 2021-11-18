package com.hiberlibros.HiberLibros.dtos;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForoComentariosDto {
    
    private ForoLibroDto foro;
    private List<ComentarioForoDto> comentarios;
}
