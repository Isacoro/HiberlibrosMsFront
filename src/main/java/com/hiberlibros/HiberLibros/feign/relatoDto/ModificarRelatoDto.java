
package com.hiberlibros.HiberLibros.feign.relatoDto;

import com.hiberlibros.HiberLibros.entities.Genero;
import com.hiberlibros.HiberLibros.entities.Relato;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificarRelatoDto {
    
    private Relato relato;
    private List<Genero> generos;
    
}
