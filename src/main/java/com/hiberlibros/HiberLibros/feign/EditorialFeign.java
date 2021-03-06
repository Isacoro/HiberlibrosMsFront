package com.hiberlibros.HiberLibros.feign;

import com.hiberlibros.HiberLibros.dtos.ConsultaEditorialesDto;
import com.hiberlibros.HiberLibros.entities.Editorial;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(contextId = "sEditorial", name = "HiberLibrosBack", url="http://localhost:8092")
@RequestMapping("/editorialesback")
public interface EditorialFeign {
    
    @GetMapping(value = "/editoriales")
    public ConsultaEditorialesDto editorialesGet(@SpringQueryMap Editorial editorial );
    
    @PostMapping(value = "/editoriales")
    public ConsultaEditorialesDto editorialesPost(@SpringQueryMap Editorial editorial );
    
    @PostMapping("/alta")
    public ConsultaEditorialesDto editorialesAlta(@SpringQueryMap Editorial ed);
    
    @GetMapping("/eliminarEditorial")
    public String eliminarEditorial(@RequestParam Integer id);
    
    @PostMapping("/modificacion")
    public void editorialModificacion(@SpringQueryMap Editorial ed);
    
    @PostMapping("/consulta")
    public Editorial editorialesConsulta(@RequestParam String id);
    
    @GetMapping("/listarAdmin")
    public ConsultaEditorialesDto listaAdmin(@RequestParam String borrado);
}
