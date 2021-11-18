package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.AutorDto;
import com.hiberlibros.HiberLibros.dtos.LibroDto;
import com.hiberlibros.HiberLibros.dtos.TablaLibrosDto;
import com.hiberlibros.HiberLibros.dtos.UsuarioLibroDto;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.feign.InicioFeign;
import com.hiberlibros.HiberLibros.feign.inicioDto.FormularioLibroDto;
import com.hiberlibros.HiberLibros.feign.inicioDto.GestionarPeticionDto;
import com.hiberlibros.HiberLibros.feign.inicioDto.PanelUsuarioDto;
import com.hiberlibros.HiberLibros.feign.inicioDto.RelatoEnvioDto;
import com.hiberlibros.HiberLibros.feign.inicioDto.RelatosInsertarDto;
import com.hiberlibros.HiberLibros.interfaces.ISeguridadService;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/hiberlibros")
public class InicioController {


    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private ISeguridadService seguridadService;
    @Autowired
    private InicioFeign inicioFeign;


    @GetMapping
    public String inicio(Model m, String error) {
        if (error != null) {
            m.addAttribute("error", error);
        }
        return "/principal/login";
    }

    @GetMapping("/pruebaContexto")
    @ResponseBody
    public String pruebaContexto() {
        return seguridadService.getMailFromContext();
    }

    @PostMapping("/loginentrar")
    public String inicio(Model m, String username, String password) {
        String pass[]=password.split(",");
        String usu[]=username.split(",");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(usu[0], pass[0]);
        Authentication auth = manager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        List<String> roles = auth.getAuthorities().stream().map(x -> x.getAuthority()).collect(Collectors.toList());
        for (String rol : roles) {
            if ("ROLE_Administrador".equals(rol)) {
                return "redirect:/hiberlibros/paneladmin";
            } else {
                if ("ROLE_Usuario".equals(rol)) {
                    return "redirect:/hiberlibros/panelUsuario";
                }
            }
        }
        String error = "Usuario no registrado";
        return "redirect:/hiberlibros?error=" + error;
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "/principal/logout";
    }

    @GetMapping("/panelUsuario") //entrada al panel principal de usuario, se pasan todos los elementos que se han de mostrar
    public String panelUsuario(Model m) {
        PanelUsuarioDto pu= inicioFeign.panelUsuario(seguridadService.getMailFromContext());
        m.addAttribute("relatos", pu.getRelatos());
        m.addAttribute("usuario", pu.getUsuario());
        m.addAttribute("libros", pu.getLibros());
        m.addAttribute("misPeticiones", pu.getMisPeticiones());
        m.addAttribute("petiRecibidas", pu.getPetiRecibidas());
        m.addAttribute("intercambiosPropios", pu.getIntercambiosPropios());
        m.addAttribute("intercambiosPeticiones", pu.getIntercambiosPeticiones());
        m.addAttribute("librosUsuario", pu.getLibrosUsuario());
        m.addAttribute("numIntercambioPendiente", pu.getNumIntercambioPendiente());

        return "principal/usuarioPanel";
    }

    @GetMapping("/guardarLibro") //Guarda libros en la base de datos. Primero guarda un libro, y posteriormente lo mete en la tabla Usuario Libros
    public String formularioLibro(Model m, String buscador) {
        FormularioLibroDto fld= inicioFeign.formularioLibro(buscador);
        m.addAttribute("libro", new LibroDto());
        m.addAttribute("autores", fld.getAutores());
        m.addAttribute("autor", new AutorDto());
        m.addAttribute("generos", fld.getGeneros());
        m.addAttribute("editoriales", fld.getEditoriales());
        m.addAttribute("buscador", buscador);
        m.addAttribute("libros", fld.getLibros()); 
        m.addAttribute("noLibros", fld.getNoLibros());
        return "principal/guardarLibro";
    }

    @PostMapping("/guardarLibro") //guarda un libro en el UsuarioLibro si ese libro existe previamente en la base de datos
    public String guardarLibro(Integer idLibro, UsuarioLibroDto ul) {
        inicioFeign.guardarLibro(idLibro, ul, seguridadService.getMailFromContext());
        return "redirect:/hiberlibros/panelUsuario";
    }

    @PostMapping("/saveAutor")//Guarda un autor y vuelve a la página de registrar libro
    public String insertarAutor(AutorDto autor) {
        inicioFeign.insertarAutor(autor);
        return "redirect:/hiberlibros/guardarLibro?buscador=XXX";
    } 
 
    @PostMapping("/registroLibro")//Guarda un libro nuevo y luego lo guarda en Usuario Libro
    public String registrarLibro(LibroDto l, Integer id_genero, Integer id_editorial, Integer id_autor, String quieroTengo, String estadoConservacion) {
        inicioFeign.registrarLibro(quieroTengo,estadoConservacion, l, id_genero, id_editorial, id_autor, seguridadService.getMailFromContext());
        return "redirect:/hiberlibros/panelUsuario";//vuelve a la página inicial
    } 

    @GetMapping("/buscarLibro")//Muestra la lita de libros, todos o los buscados si está relleno el campo buscador
    public String buscarLibro() {
        return "principal/buscarLibro";
    }

    @GetMapping("/tablaBuscarLibro")
    @ResponseBody
    public List<TablaLibrosDto> tablaBuscarLibro() {//    
        List<TablaLibrosDto> tld = inicioFeign.tablaBuscarLibro(seguridadService.getMailFromContext());
        return tld;
    }

    @PostMapping("/guardarRelato")//no funciona de momento
    public String formularioRelato(RelatoEnvioDto relato, MultipartFile ficherosubido) {
        relato.setEmail(seguridadService.getMailFromContext());
        inicioFeign.formularioRelato(ficherosubido, relato);
        return "redirect:/hiberlibros/panelUsuario";
    }

    @GetMapping("/relato")
    public String insertarRelato(Model model, Integer id) {
        RelatosInsertarDto ri= inicioFeign.insertarRelato(id);
        model.addAttribute("generos", ri.getGeneros());
        model.addAttribute("relatos", ri.getRelatos());
        model.addAttribute("usuario", ri.getUsuario());
        return "principal/relato";
    }

    @GetMapping("/borrarUL")//borra un libro de UsuarioLibro sin eliminarlo de la tabla de Libros
    public String borrarUsuLibro(Model m, Integer id) {
        String borrado= inicioFeign.borrarUsuLibro(id);
        m.addAttribute("borrado", borrado);
        return "redirect:/hiberlibros/panelUsuario";
    }

    @GetMapping("/gestionarPeticion") //por aquí voy
    public String gestionarPeticion(Model m, Integer id) { 
        GestionarPeticionDto map = inicioFeign.gestionarPeticion(id);
        m.addAttribute("peticiones", map.getPeticiones() );
        m.addAttribute("librosSolicitante",map.getLibrosSolicitante());
        return "principal/formPeticion";
    }

    @PostMapping("/realizarIntercambio")
    public String realizarIntercambio(Integer id_peticion, Integer usuarioPrestatario) {
        inicioFeign.realizarIntercambio(id_peticion, usuarioPrestatario);
        return "redirect:/hiberlibros/panelUsuario";
    }

    @GetMapping("/rechazarIntercambio")
    public String rechazarIntercambio(Integer id) {
        inicioFeign.rechazarIntercambio(id);
        return "redirect:/hiberlibros/panelUsuario";
    }

    @GetMapping("/finIntercambio")
    public String finIntercambio(Integer id) {
        inicioFeign.finIntercambio(id);
        return "redirect:/hiberlibros/panelUsuario";
    }

    @GetMapping("/editarUsuario")
    @ResponseBody
    public Usuario editar() {
        return inicioFeign.editar(seguridadService.getMailFromContext());
    }
}
