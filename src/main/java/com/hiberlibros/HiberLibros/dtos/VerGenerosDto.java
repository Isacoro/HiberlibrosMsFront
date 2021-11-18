package com.hiberlibros.HiberLibros.dtos;

import com.hiberlibros.HiberLibros.entities.Genero;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerGenerosDto {

    private List<Genero> generos;
    private Genero generoForm;
}
