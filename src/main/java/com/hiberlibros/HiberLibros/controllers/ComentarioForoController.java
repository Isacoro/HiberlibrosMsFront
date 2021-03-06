/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.ForoComentariosDto;
import com.hiberlibros.HiberLibros.feign.ComentarioForoFeign;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("hilos")
public class ComentarioForoController {

    @Autowired
    private ISeguridadService seguridadService;
    @Autowired
    private ComentarioForoFeign comentarioForoFeign;


    @GetMapping("/consultarPorForo")
    public String consultarComentariosPorForo(Model m, Integer idForo) {
        ForoComentariosDto foroComentarios = comentarioForoFeign.consultarComentariosPorForo(idForo);
        m.addAttribute("foro", foroComentarios.getForo());
        m.addAttribute("comentarios", foroComentarios.getComentarios());

        return "principal/comentarios";
    }

    @PostMapping("/alta")
    public String altaComentario(Model m, Integer idForoLibro, String comentario) {

        ForoComentariosDto foroComentarios = comentarioForoFeign.altaComentario(idForoLibro, comentario, seguridadService.getMailFromContext());

        m.addAttribute("foro", foroComentarios.getForo());
        m.addAttribute("comentarios", foroComentarios.getComentarios());

        return "principal/comentarios";
    }
}
