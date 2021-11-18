package com.hiberlibros.HiberLibros.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "foros_libros")
public class ForoLibro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private Boolean desactivado;
    private String  tituloForo;
    
    @ManyToOne
    @JoinColumn(name= "id_libro")
    private Libro   idLibro;   // libro del que es el foro
    
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuarioCreador;  //id Usuario creador del hilo

    @OneToMany(mappedBy = "id" ,fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ComentarioForo> comentarios; //usuario que genera el foro
}
