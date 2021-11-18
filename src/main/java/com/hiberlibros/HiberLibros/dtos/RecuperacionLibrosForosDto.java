package com.hiberlibros.HiberLibros.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecuperacionLibrosForosDto {

    private ForoLibroDto foro;
    private List<LibroDto> libros;
    private List<ForoLibroDto> foros;
}
