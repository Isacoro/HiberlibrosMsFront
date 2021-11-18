package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.UsuarioDto;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.feign.UsuarioFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioFeign usuarioFeign;


    @PostMapping("/guardarUsuario")
    public String usuarioRegistrar(UsuarioDto u, String password) {
        System.out.println(u.getNombre());
        String resultado = usuarioFeign.usuarioRegistrar(u, password);
        if (resultado.contains("Error")) {
            return "redirect:/hiberlibros?error=" + resultado;//mail existente, mail no válido
        } else {
            return "redirect:/hiberlibros";
        }
    }

    //edita usuario, manda el usuario para rellenar el formulario
    @PostMapping("/editarUsuario")
    public String usuarioEditar(Usuario u) {
        usuarioFeign.usuarioEditar(u);
        return "redirect:/hiberlibros/panelUsuario";
    }

    //borra usuario por ID en panel administrador
    @GetMapping("/borrar")
    public String borrar(Integer id) {
        String borrado = "";
        if (usuarioFeign.borrar(id)) {
            borrado = "Usuario borrado";
        } else {
            borrado = "Error, no es posible borrar este usuario";
        }
        return "redirect:listarAdmin?borrado=" + borrado;
    }

    //borra usuario por ID en web usuarios
    @GetMapping("/borrarUsuario")
    public String borrarUsuario(Integer id) {
        String error = "";
        if (usuarioFeign.borrar(id)) {
            error = "Usuario borrado";
        } else {
            error = "Error, no es posible borrar este usuario";
        }
        return "redirect:/hiberlibros?error=" + error;
    }

    @GetMapping("/listarAdmin")
    private String listarTodo(Model m, String borrado) {
        m.addAttribute("usuarios", usuarioFeign.listarTodo());
        if (borrado != null) {
            m.addAttribute("borrado", borrado);
        }
        return "/administrador/usuarios";
    }

    @PostMapping("/altaAdmin")
    public String altaAdmin(Usuario u, String password) {
        String resultado = usuarioFeign.altaAdmin(u, password);
        return "redirect:listarAdmin?borrado=" + resultado;
    }

    @PostMapping("/imagenPerfil")
    public String imagenPerfil(Model m, Integer id, MultipartFile ficheroImagen) {
        usuarioFeign.imagenPerfil(ficheroImagen, id);
        return "redirect:/hiberlibros/panelUsuario";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> mostrarImagen(String imagen) {

        return usuarioFeign.mostrarImagen(imagen);
    }
}
