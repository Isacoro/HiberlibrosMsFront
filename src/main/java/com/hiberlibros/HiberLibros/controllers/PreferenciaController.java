package com.hiberlibros.HiberLibros.controllers;


import com.hiberlibros.HiberLibros.feign.PreferenciaFeign;
import com.hiberlibros.HiberLibros.feign.preferenciaDto.VerPreferenciasDto;
import com.hiberlibros.HiberLibros.interfaces.IGeneroService;
import com.hiberlibros.HiberLibros.interfaces.IPreferenciaService;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/preferencia")
public class PreferenciaController {

    @Autowired
    private IPreferenciaService prefService;

    @Autowired
    private IGeneroService serviceGenero;

    @Autowired
    private IUsuarioService usuServ;

    @Autowired
    private ISeguridadService serviceSeguridad;

    @Autowired
    private PreferenciaFeign preFeign;

    @GetMapping
    public String verPreferencias(Model model) {

        VerPreferenciasDto vp = preFeign.verPreferencias(serviceSeguridad.getMailFromContext());

        model.addAttribute("preferencias", vp.getPreferencias());
        model.addAttribute("generos", vp.getGeneros());
        model.addAttribute("formulario", vp.getFormulario());

        return "/preferencias/preferencia";

    }

    @PostMapping("/guardar")
    public String anadirPreferencia(Integer id_genero) {

        preFeign.anadirPreferencia(id_genero, serviceSeguridad.getMailFromContext());

        return "redirect:/preferencia";
    }


    @GetMapping("/borrar/{id}")
    public String borrarPreferencia(@PathVariable Integer id) {
        
        preFeign.borrarPreferencia(id);

        return "redirect:/preferencia";
    }
}