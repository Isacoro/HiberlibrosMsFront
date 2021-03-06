package com.hiberlibros.HiberLibros.feign.inicioDto;

import com.hiberlibros.HiberLibros.dtos.IntercambioDto;
import com.hiberlibros.HiberLibros.dtos.PeticionDto;
import com.hiberlibros.HiberLibros.dtos.RelatoDto;
import com.hiberlibros.HiberLibros.dtos.UsuarioDto;
import com.hiberlibros.HiberLibros.dtos.UsuarioLibroDto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PanelUsuarioDto {

    private List<RelatoDto> relatos;
    private UsuarioDto usuario;
    private List<UsuarioLibroDto> libros;
    private List<PeticionDto> misPeticiones;
    private List<PeticionDto> petiRecibidas;
    private List<IntercambioDto> intercambiosPropios;
    private List<IntercambioDto> intercambiosPeticiones;
    private Integer librosUsuario;
    private Integer numIntercambioPendiente;
}
