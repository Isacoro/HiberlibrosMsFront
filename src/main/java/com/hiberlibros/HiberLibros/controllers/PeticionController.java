package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.PeticionDto;
import com.hiberlibros.HiberLibros.dtos.UsuarioDto;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.feign.PeticionFeing;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioService;


@Controller
@RequestMapping("peticion")
public class PeticionController {


    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private ISeguridadService seguridadService;
    @Autowired 
    private PeticionFeing peticionFeing;
    
    @GetMapping(value = "/peticion")
    public String peticion(Model m, PeticionDto p){
        m.addAttribute("peticiones", peticionFeing.consultaTodasPeticiones(p));
        return "/peticion/peticion";
    }

    //Recibe los integer y crea una nueva petición, vuelve al panel de usuario
    @GetMapping(value = "/alta")
    public String peticionAlta(Model m, Integer id_ul){
        Usuario u = usuarioService.usuarioRegistrado(seguridadService.getMailFromContext());

        peticionFeing.peticionAlta(id_ul, u.getMail());  // recuperamos idUsuario del contexto
        return "redirect:/hiberlibros/panelUsuario";
    }
    
    @PostMapping(value = "/baja")
    public String peticionBaja(Model m, PeticionDto p){
        peticionFeing.peticionBaja(p);
        return "redirect:/peticion/peticion";
    }

    //Retira una solicitud solo con el ID de la petición para no tener que mandar un objeto petición
    @GetMapping("/baja")
    public String retirarSolicitud(Integer id){
        peticionFeing.retirarSolicitud(id);
        return "redirect:/hiberlibros/panelUsuario";//vuelve al panel
    }

    @PostMapping(value = "/modificacion")
    public String peticionModificacion(Model m, PeticionDto p){
        peticionFeing.peticionModificacion(p);
        return "redirect:/peticion/peticion";
    }
    
    @PostMapping(value = "/aceptar")
    public String aceptarPeticion(Model m, PeticionDto p, UsuarioDto u){
        peticionFeing.aceptarPeticion(p,u);
        return "redirect:/peticion/peticion";
    }
            
    @PostMapping(value = "/rechazar")
    public String rechazarPeticion(Integer id, UsuarioDto u){
        peticionFeing.rechazarPeticion(id, u);
        return "redirect:/peticion/peticion";
    }
    
    @PostMapping(value = "/peticionesPendientes")
    public String consultarTodasPeticionesPendientes(Model m,UsuarioDto u){
         m.addAttribute("peticionesPendientes", peticionFeing.consultarTodasPeticionesPendientes(u));
         return "redirect:/peticion/peticion";
    }
}
