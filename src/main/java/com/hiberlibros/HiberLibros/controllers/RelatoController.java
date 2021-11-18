package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.entities.Relato;
import com.hiberlibros.HiberLibros.feign.RelatoFeign;
import com.hiberlibros.HiberLibros.feign.relatoDto.ModificarRelatoDto;
import com.hiberlibros.HiberLibros.feign.relatoDto.RelatoAdminDto;
import com.hiberlibros.HiberLibros.feign.relatoDto.TablaRelatoDto;
import com.hiberlibros.HiberLibros.interfaces.IGeneroService;

import com.hiberlibros.HiberLibros.repositories.RelatoRepository;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/relato")
public class RelatoController {

    @Autowired
    private RelatoRepository relatoRepository;
    @Autowired
    private IGeneroService generoService;
    @Autowired
    private RelatoFeign relatoFeign;


    @GetMapping
    public String prueba(Model model) {

        model.addAttribute("generos", generoService.getGeneros());
        model.addAttribute("relatos", relatoRepository.findAll());
        return "/principal/relato";
    }

    @GetMapping("/listaRelatos")
    public String mostrarRelatos(Model model) {

        return "/principal/buscarRelatos";
    }

    @GetMapping("/eliminarRelato")
    public String eliminarRelato(Model m, Integer id) {
        relatoFeign.eliminarRelato(id);
        return "redirect:/hiberlibros/panelUsuario";
    }

    @PostMapping("/addValoracion")
    public String addValoracion(Model m, Double valoracion, Integer id, Integer idUsuario) {
        relatoFeign.addValoracion(valoracion, id, idUsuario);
        return "redirect:/relato/listaRelatos?id=" + idUsuario;
    }

    //metodo para calcular el numero de valoraciones y calcular la media entre ellas
    public void calcularValoracion(int id, Double valoracion) {
        Optional<Relato> relato = relatoRepository.findById(id);
        if (relato.isPresent()) {
            relato.get().setNumeroValoraciones(relato.get().getNumeroValoraciones() + 1);
            Double val = (relato.get().getValoracionUsuarios() * (relato.get().getNumeroValoraciones() - 1) + valoracion)
                    / relato.get().getNumeroValoraciones();
            double redondeo = Math.round(val * 100.0) / 100.0;
            relato.get().setValoracionUsuarios(redondeo);
            relatoRepository.save(relato.get());
        }
    }

    @GetMapping("/relato/{id}")
    public String buscarPorID(Model m, @PathVariable Integer id) {
        m.addAttribute("relato", relatoFeign.buscarPorID(id));
        return "/relato/relato";
    }

    @GetMapping("/modificar")
    public String modificarRelato(Model m, Integer id, Integer idGenero) {
        ModificarRelatoDto res = relatoFeign.modificarRelato(id);
        m.addAttribute("relato", res.getRelato());
        m.addAttribute("generos", res.getGeneros());
        return "relato/modificarRelato";
    }

    @PostMapping("/modificarRelato")
    public String modificarRelato(Integer id, Integer genero, String titulo) {
        relatoFeign.modificarRelato(id, genero, titulo);

        return "redirect:listarAdmin";
    }

    @GetMapping("/listarAdmin")
    private String listarTodo(Model m) {
        List<RelatoAdminDto> relatos = relatoFeign.listarTodo();
        m.addAttribute("relatos", relatos);
        return "/administrador/relatos";
    }

    @GetMapping("/eliminarAdmin")
    public String eliminarRelatoAdmin(Model m, Integer id) {
        relatoFeign.eliminarRelatoAdmin(id);
        return "redirect:listarAdmin";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> descargar(Integer id) throws IOException {
        Relato rel = relatoRepository.findById(id).get();
        String descarga = rel.getFichero();
        File file = new File(descarga);

        String titulo = rel.getTitulo();
        String extension = descarga.substring(descarga.lastIndexOf("."));

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "attachment; filename=" + titulo + "." + extension);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(descarga));
            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/buscarRelato")
    public String buscarRelato(Model m, Integer id, String busqueda) {

        return "/principal/buscarRelatos";
    }

    @GetMapping("/tablaRelato")
    @ResponseBody
    public List<TablaRelatoDto> tablaRelato() {

        return relatoFeign.tablaRelato();
    }
}
