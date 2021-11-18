package com.hiberlibros.HiberLibros.feign;

import com.hiberlibros.HiberLibros.dtos.EventoDto;
import com.hiberlibros.HiberLibros.feign.inicioDto.AdminHubDto;
import java.util.Date;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "sAdministrador", name = "HiberLibrosBack", url="http://localhost:8092")
@RequestMapping("/hiberlibros/paneladminBack")
public interface AdministradorFeign {
    
    @GetMapping
    public AdminHubDto adminHub();
    
    @PostMapping("/evento")
    public void addEvento(@RequestParam Integer id,
                          @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd")
                          Date startDate,
                          @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd")
                          Date endDate,
                          @RequestParam String summary);
   
    @GetMapping("/deleteEvento")
    public void eliminar(@RequestParam Integer id);
    
    @GetMapping("/buscarEvento")
    public List<EventoDto> buscar(@RequestParam String search);
}
