package com.hiberlibros.HiberLibros.feign;

import com.hiberlibros.HiberLibros.dtos.ForoComentariosDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(contextId = "sComentarioForo", name = "HiberLibrosBack", url="http://localhost:8092")
@RequestMapping("/hilosback")
public interface ComentarioForoFeign {
    
    @GetMapping("/consultarPorForo")
    public ForoComentariosDto consultarComentariosPorForo(@RequestParam Integer idForo);
    
    @PostMapping("/alta")
    public ForoComentariosDto altaComentario(@RequestParam Integer idForoLibro,@RequestParam String comentario,@RequestParam String email);
}
