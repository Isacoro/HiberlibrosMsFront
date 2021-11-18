package com.hiberlibros.HiberLibros.feign.relatoDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatoParamDto {

    private Integer id;
    private String fichero;
    private String emailUsuario;
    private String titulo;
    private Double valoracionUsuarios;
    private Integer numeroValoraciones;
    private Integer idGenero;    
}
