package com.hiberlibros.HiberLibros.controllers;

import com.hiberlibros.HiberLibros.dtos.ConsultaEditorialesDto;
import com.hiberlibros.HiberLibros.entities.Editorial;
import com.hiberlibros.HiberLibros.feign.EditorialFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/editoriales")
public class EditorialController {

    @Autowired
    private EditorialFeign editorialFeign;

    @PostMapping(value = "/editoriales")
    public String editoriales(Model m, Editorial editorial) {
        ConsultaEditorialesDto consultaEditoriales = editorialFeign.editorialesPost(editorial);
        m.addAttribute("editorial", consultaEditoriales.getEditorial());
        m.addAttribute("editoriales", consultaEditoriales.getEditoriales());
        return "/editoriales/editoriales";
    }

    @GetMapping(value = "/editoriales")
    public String editorialesGet(Model m, Editorial editorial) {
        ConsultaEditorialesDto consultaEditoriales = editorialFeign.editorialesGet(editorial);
        m.addAttribute("editorial", consultaEditoriales.getEditorial());
        m.addAttribute("editoriales", consultaEditoriales.getEditoriales());
        return "/editoriales/editoriales";
    }

    @PostMapping("/alta")
    public String editorialesAlta(Model m, Editorial ed) {
        ConsultaEditorialesDto consultaEditoriales = editorialFeign.editorialesAlta(ed);
        m.addAttribute("errMensaje", consultaEditoriales.getErrMensaje());
        m.addAttribute("editorial", consultaEditoriales.getEditorial());
        return "redirect:/editoriales/editoriales";
    }

    @GetMapping("/eliminarEditorial")
    public String editorialesBaja(Model m, Integer id) {
        String borrado = editorialFeign.eliminarEditorial(id);
        return "redirect:/editoriales/listarAdmin?borrado=" + borrado;
    }

    @PostMapping("/modificacion")
    public String editorialesModificacion(Model m, Editorial ed) {
        editorialFeign.editorialModificacion(ed);
        return "redirect:/editoriales/listarAdmin";
    }

    @PostMapping("/consulta")
    public String editorialesConsulta(Model m, String id) {
        m.addAttribute("editorial", editorialFeign.editorialesConsulta(id));
        return "forward:/editoriales/editoriales";
    }

    @GetMapping("/listarAdmin")
    public String listaAdmin(Model m, String borrado) {
        ConsultaEditorialesDto consultaEditoriales = editorialFeign.listaAdmin(borrado);
        m.addAttribute("borrado", consultaEditoriales.getBorrado());
        m.addAttribute("editoriales", consultaEditoriales.getEditoriales());
        return "administrador/editoriales";
    }

    @GetMapping("/editar")
    @ResponseBody
    public Editorial editarEdit(Integer id) {
        //    Editorial edit = serviceEditorial.consultaPorIdEditorial(id);
        Editorial edit = editorialFeign.editorialesConsulta(id.toString());
        return edit;
    }
}
