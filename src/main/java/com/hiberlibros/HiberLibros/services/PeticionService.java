package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.Peticion;
import com.hiberlibros.HiberLibros.entities.Usuario;
import com.hiberlibros.HiberLibros.entities.UsuarioLibro;
import com.hiberlibros.HiberLibros.interfaces.IPeticionService;
import com.hiberlibros.HiberLibros.repositories.PeticionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hiberlibros.HiberLibros.interfaces.IUsuarioLibroService;

@Service
public class PeticionService implements IPeticionService {

    @Autowired
    private PeticionRepository peticionRepository;
    @Autowired
    private IUsuarioLibroService usuarioLibroService;

    

    @Override
    public List<Peticion> consultaTodasPeticiones() {
        return peticionRepository.findAll();
    }
    
    @Override
    public Peticion consultarPeticionId(Integer id) {
        return peticionRepository.findById(id).get();
    }

    @Override
    public void insertaPeticion(Peticion p, Integer id_ul, Usuario u) { //guarda la petición y obtiene aquí los objetos UL y Usuario
        p.setIdUsuarioLibro(usuarioLibroService.encontrarId(id_ul));
        p.setIdUsuarioSolicitante(u);
        p.setAceptacion(false);
        p.setPendienteTratar(true);

        peticionRepository.save(p);
    }

    @Override
    public void insertaModificaPeticion(Peticion p) {

        peticionRepository.save(p);
    }

    @Override
    public void eliminaPeticion(Peticion p) {
        peticionRepository.deleteById(p.getId());
    }

    @Override
    public void eliminarId(Integer id) {
        peticionRepository.deleteById(id);;
    }

    @Override
    public void aceptarPeticion(Peticion p) {
        p.setAceptacion(Boolean.TRUE);
        p.setPendienteTratar(Boolean.FALSE);
        peticionRepository.save(p);
    }

    @Override
    public void rechazarPeticion(Integer id) {
        Peticion p = peticionRepository.findById(id).get();
        p.setAceptacion(Boolean.FALSE);
        p.setPendienteTratar(Boolean.FALSE);
        peticionRepository.save(p);
    }

    @Override
    public List<Peticion> consultarPeticionesPendientes(Usuario u) {
        return peticionRepository.findByPendienteTratar(Boolean.TRUE).stream()
                .filter(x -> x.getIdUsuarioSolicitante()
                        .equals(u.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Peticion> consultarPeticionesAceptadas(Usuario u) {
        return peticionRepository.findByAceptacion(Boolean.TRUE).stream()
                .filter(x -> x.getIdUsuarioSolicitante()
                        .equals(u.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Peticion> consultarPeticionesRechazadas(Usuario u) {
        return peticionRepository.findByAceptacion(Boolean.FALSE).stream()
                .filter(x -> x.getIdUsuarioSolicitante()
                        .equals(u.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Peticion> consutarPeticionesUsuarioPendientes(Usuario u) {
        return peticionRepository.findByPendienteTratarAndIdUsuarioSolicitante(Boolean.TRUE, u);
    }

    @Override
    public List<Peticion> consultarPeticonesRecibidas(Usuario u) {
        List<Peticion> p = new ArrayList<>();
        List<UsuarioLibro> ul = usuarioLibroService.buscarUsuario(u);//busca la lista de libros de un usuario
        ul.forEach(x -> {
            List<Peticion> pAux = peticionRepository.findByIdUsuarioLibroAndPendienteTratar(x, Boolean.TRUE); //busca por UsuarioLibro y que este pendiente de tratar
            pAux.forEach(y -> {
                p.add(y);//lo va almacenando hasta tener todos. 
            });
        });
        return p;
    }

    @Override
    public void borrarPorUsuarioSolicitante(Usuario u) {
        peticionRepository.deleteByIdUsuarioSolicitante(u);
    }

    @Override
    public void borrarPorUsuarioLibro(UsuarioLibro ul) {
        peticionRepository.deleteByIdUsuarioLibro(ul);
    }
}
