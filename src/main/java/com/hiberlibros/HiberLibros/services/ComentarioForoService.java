/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hiberlibros.HiberLibros.services;

import com.hiberlibros.HiberLibros.entities.ComentarioForo;
import com.hiberlibros.HiberLibros.entities.ForoLibro;
import com.hiberlibros.HiberLibros.interfaces.IComentarioForoService;
import com.hiberlibros.HiberLibros.repositories.ComentarioForoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ComentarioForoService implements IComentarioForoService{

    @Autowired
    private ComentarioForoRepository comentarioForoRepository;
    
    @Override
    public void altaComentario(ComentarioForo c) {
        comentarioForoRepository.save(c);
    }

    @Override
    public void bajaComentario(Integer idComentario) {
        comentarioForoRepository.deleteById(idComentario);
    }

    @Override
    public void eliminarComentariosForo(ForoLibro fl) {
       comentarioForoRepository.deleteByForoLibro(fl);
    }

    @Override
    public List<ComentarioForo> consultarComentariosPorForo(ForoLibro foroLibro) {
        return comentarioForoRepository.findByForoLibro(foroLibro);
    }
}
