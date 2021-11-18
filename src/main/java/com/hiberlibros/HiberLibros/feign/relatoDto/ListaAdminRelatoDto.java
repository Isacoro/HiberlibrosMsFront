package com.hiberlibros.HiberLibros.feign.relatoDto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListaAdminRelatoDto {
    
        public List<RelatoAdminDto> relatos;
}
