package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.LibroParamDto;

import com.hiberlibros.HiberLibros.feign.LibroFeign;
import com.hiberlibros.HiberLibros.feign.inicioDto.ListarAdminDto;
import com.hiberlibros.HiberLibros.feign.inicioDto.ModificarLibroDto;
import com.hiberlibros.HiberLibros.feign.inicioDto.MostrarFormularioLibrosDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroFeign libroFeign;

    @GetMapping("/libros")
    public String mostrarFormulario(Model m) {
        MostrarFormularioLibrosDto mfld = libroFeign.mostrarFormulario();
        m.addAttribute("libros", mfld.getLibros());
        m.addAttribute("generos", mfld.getGeneros());
        m.addAttribute("editoriales", mfld.getEditoriales());
        m.addAttribute("autores", mfld.getAutores());
        return "libros/VistaLibro";
    }

    @PostMapping("/guardar")
    public String guardarLibro(LibroParamDto libro) {
        libroFeign.guardarLibro(libro);
        return "redirect:/libros";
    }

    @GetMapping("/eliminar")
    public String eliminarLibro(Model m, Integer id) {
        String borrado="";
        if (libroFeign.eliminarLibro(id)) {
           borrado="Libro borrado"; 
        } else {
           borrado="Error, no es posible borrar este libro";
        }
        return "redirect:listarAdmin?borrado="+borrado;
    }

    @GetMapping("/modificar")
    public String modificarLibro(Model m, Integer id) {
        ModificarLibroDto  mld = libroFeign.modificarLibro(id);
        m.addAttribute("imagen", mld.getLibro().getUriPortada());
        m.addAttribute("libro", mld.getLibro());
        m.addAttribute("generos", mld.getGeneros());
        m.addAttribute("editoriales", mld.getEditoriales());
        m.addAttribute("autores", mld.getAutores());

        return "/libros/modificar";
    }

    @GetMapping("/listarAdmin")
    public String listarTodo(Model m, String borrado) {
        ListarAdminDto lad = libroFeign.listarTodo(borrado);
        m.addAttribute("libros", lad.getLibros());
        m.addAttribute("generos", lad.getGeneros());
        m.addAttribute("editoriales", lad.getEditoriales());
        m.addAttribute("autores", lad.getAutores());
        if(borrado!=null){
            m.addAttribute("borrado",borrado);
        }

        return "/administrador/libros";
    }

    @PostMapping("/guardarAdmin")
    public String guardarAdmin(LibroParamDto libro) {
        libroFeign.guardarAdmin(libro);
        return "redirect:listarAdmin";
    }

    @GetMapping("/eliminarAdmin")
    public String eliminarAdmin(Integer id) {
        String borrado="";
        if (libroFeign.eliminarLibro(id)) {
            borrado="Libro borrado";
        } else {
            borrado = "Error, no es posible borrar este autor";
        }
        return "redirect:listarAdmin?borrado=" + borrado;
    }

    @PostMapping("/addValoracionLibro")
    public String addValoracionLibro(Integer id, Integer valoracion) {  
        libroFeign.addValoracionLibro(id, valoracion);
        return "redirect:/hiberlibros/buscarLibro";
    }
}