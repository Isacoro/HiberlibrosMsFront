package com.hiberlibros.HiberLibros.feign.relatoDto;

import com.hiberlibros.HiberLibros.dtos.GeneroDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatoAdminDto {

    private Integer id;
    private String fichero;
    private GeneroDto genero;
    private String titulo;
    private Double valoracionUsuarios;
    private Integer numeroValoraciones;
}
