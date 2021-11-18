package com.hiberlibros.HiberLibros.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaEditorialesDto {

    private EditorialDto editorial;
    private List<EditorialDto> editoriales;
    private String errMensaje;
    private String borrado;
}
