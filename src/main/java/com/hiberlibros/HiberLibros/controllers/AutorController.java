package com.hiberlibros.HiberLibros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hiberlibros.HiberLibros.dtos.LibrosAutorDto;
import com.hiberlibros.HiberLibros.dtos.VerAutoresDto;
import com.hiberlibros.HiberLibros.entities.Autor;
import com.hiberlibros.HiberLibros.feign.AutorFeign;

@Controller
@RequestMapping
public class AutorController {
    
    @Autowired
    private AutorFeign autorFeign;

    @GetMapping("/autores/listarAdmin")
    public String listaAdmin(Model m, String borrado) {
    	VerAutoresDto map = autorFeign.listaAdmin(borrado);
        m.addAttribute("autores", map.getAutores());
        if(borrado!=null){
            m.addAttribute("borrado", borrado);
        }
        return "administrador/autores";
    }

    @GetMapping("/librosAutor")
    public String LibrosDeAutor(Model m, Integer id) {
    	LibrosAutorDto map = autorFeign.LibrosDeAutor(id);
        m.addAttribute("libros", map.getLibros());
        return "administrador/librosAutor";
    }

    @GetMapping("/editarAutor")
    public String editarAutor(Model m, Integer id) {
        Autor autor =  autorFeign.editarAutor(id);
        m.addAttribute("autor", autor);
        return "administrador/editAutor";
    }
    @GetMapping("/newAutor")
    public String crearAutor(Model m, Integer id) {
        m.addAttribute("autor", new Autor());
        return "administrador/editAutor";
    }

    @PostMapping("/guardarAutor")
    public String guardarAutor(Model m, Autor autor) {
        autorFeign.guardarAutor(autor);
        return "redirect:autores/listarAdmin";
    }

    @GetMapping("/eliminarAutor")
    public String eliminarAutorAdmin(Model m, Integer id) {
        String borrado="";
        if (autorFeign.eliminarAutorAdmin(id)) {
            borrado="Autor borrado";
        } else {
            borrado="Error, no es posible borrar este autor";
        }
        return "redirect:autores/listarAdmin?borrado="+borrado;
    }
}
