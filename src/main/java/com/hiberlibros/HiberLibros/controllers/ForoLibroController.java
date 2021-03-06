package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.RecuperacionLibrosForosDto;
import com.hiberlibros.HiberLibros.entities.ForoLibro;
import com.hiberlibros.HiberLibros.feign.ForoLibroFeign;
import com.hiberlibros.HiberLibros.interfaces.IForoLibroService;
import com.hiberlibros.HiberLibros.interfaces.ILibroService;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/foros")
public class ForoLibroController {

    @Autowired 
    private ISeguridadService seguridadService;
    @Autowired
    private ForoLibroFeign foroLibroFeign;


    @GetMapping("/libro")
    public String recuperarForosPorLibro(Model m, Integer id) {
        
        m.addAttribute("foros", foroLibroFeign.recuperarForosPorLibro(id).getForos());
        m.addAttribute("libros", foroLibroFeign.recuperarForosPorLibro(id).getLibros());
        return "/principal/foro";
    }
    
    @GetMapping()
    public String recuperarForos(Model m) {
        RecuperacionLibrosForosDto recuperacionLibrosForos = foroLibroFeign.recuperarForos();
        
        m.addAttribute("foro", recuperacionLibrosForos.getForo());
        m.addAttribute("libros", recuperacionLibrosForos.getLibros());
        m.addAttribute("foros", recuperacionLibrosForos.getForos());
        return "/principal/foro";
    }
    
    @GetMapping("/alta")
    public String altaForo (Model m, ForoLibro l){
        foroLibroFeign.altaForo(l.getTituloForo(),l.getIdLibro().getId(), seguridadService.getMailFromContext());
      
        RecuperacionLibrosForosDto recuperacionLibrosForos = foroLibroFeign.recuperarForos();
        
        m.addAttribute("foro", recuperacionLibrosForos.getForo());
        m.addAttribute("libros", recuperacionLibrosForos.getLibros());
        m.addAttribute("foros", recuperacionLibrosForos.getForos());

        return "/principal/foro";
    }

    @GetMapping("/baja")
    public String bajaForo (Integer id){
        foroLibroFeign.bajaForo(id);
        return "/principal/altaForo";
    }
}
