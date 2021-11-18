package com.hiberlibros.HiberLibros.feign.generoDto;

import com.hiberlibros.HiberLibros.entities.Genero;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerGenerosDto {

    private List<Genero> generos;
    private Genero generoForm;
}
