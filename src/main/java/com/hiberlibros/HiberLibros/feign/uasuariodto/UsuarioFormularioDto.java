package com.hiberlibros.HiberLibros.feign.uasuariodto;

import com.hiberlibros.HiberLibros.dtos.UsuarioDto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioFormularioDto {

    private String registro;
    private List<UsuarioDto> usuarios;
}
