package com.hiberlibros.HiberLibros.dtos;

import java.util.List;

import com.hiberlibros.HiberLibros.entities.Autor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerAutoresDto {

    private List<Autor> autores;
    private Autor autorForm;
}
