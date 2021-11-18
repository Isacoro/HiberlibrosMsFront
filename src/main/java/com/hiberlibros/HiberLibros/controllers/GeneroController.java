package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.GeneroDto;
import com.hiberlibros.HiberLibros.feign.generoDto.VerGenerosDto;
import com.hiberlibros.HiberLibros.feign.GeneroFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/genero")
public class GeneroController {


    @Autowired
    private GeneroFeign generoFeign;


    @GetMapping
    public String verGeneros(Model model) {
        VerGenerosDto res = generoFeign.verGeneros();
        model.addAttribute("generos", res.getGeneros());
        model.addAttribute("generoForm", res.getGeneroForm());

        return "/generos/genero";
    }

    @PostMapping("/guardar")
    public String formulario(GeneroDto genero) {
        generoFeign.formulario(genero);

        return "redirect:listarAdmin";
    }

    @GetMapping("/editar")
    @ResponseBody
    public GeneroDto editarGenero(Integer id) {
        GeneroDto gen = generoFeign.editarGenero(id);
        return gen;
    }

    @GetMapping("/borrar")
    public String borrarGenero(Model m, Integer id) {
        String borrado = "";
        if (generoFeign.borrarGenero(id)) {
            borrado = "Género borrado";
        } else {
            borrado = "Error, no es posible borrar este género";
        }
        return "redirect:/genero/listarAdmin?borrado=" + borrado;
    }

    @GetMapping("/listarAdmin")
    private String listarTodo(Model m, String borrado) {
        VerGenerosDto res = generoFeign.verGeneros();
    
        m.addAttribute("generos", res.getGeneros());
        m.addAttribute("generoForm", res.getGeneroForm());
        if (borrado != null) {
            m.addAttribute("borrado", borrado);
        }
        return "/administrador/generos";
    }
}
