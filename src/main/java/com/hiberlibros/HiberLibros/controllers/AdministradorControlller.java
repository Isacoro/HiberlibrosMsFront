package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.CalendarioDto;
import com.hiberlibros.HiberLibros.dtos.EventoDto;
import com.hiberlibros.HiberLibros.feign.AdministradorFeign;
import com.hiberlibros.HiberLibros.feign.inicioDto.AdminHubDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/hiberlibros/paneladmin")
public class AdministradorControlller {

    @Autowired
    private AdministradorFeign administradorFeign;


    @GetMapping
    public String adminHub(Model m) {
           AdminHubDto ahd = administradorFeign.adminHub();
           m.addAttribute("numUsuarios",ahd.getNumUsuarios());
           m.addAttribute("numLibros",ahd.getNumLibros());
           m.addAttribute("eventos", ahd.getEventos());
        return  "administrador/adminPanel";
    } 
    
    @GetMapping("/addEvent")
     public String formEvento(){
        return "administrador/eventoForm";
    }
   
    @PostMapping("/evento")
     public String addEvento(CalendarioDto e){
        administradorFeign.addEvento(e.getId(),e.getStartDate(),e.getEndDate(),e.getSummary());
        
        return "redirect:/hiberlibros/paneladmin";
    }
     
    @GetMapping("/deleteEvento")
    @ResponseBody
      public void eliminar(Integer id){
          administradorFeign.eliminar(id);
      }
      
    @GetMapping("/buscarEvento")
    @ResponseBody
     public List<EventoDto> buscar(String search){
       return administradorFeign.buscar(search);
    }
    
    @GetMapping("/contacto")
     public String adminContacto(Model m) {
        return "administrador/contacto";
    }
}
