package com.aluracursos.adopet.api.controller;

import com.aluracursos.adopet.api.dto.MascotaDto;
import com.aluracursos.adopet.api.dto.RefugioDto;
import com.aluracursos.adopet.api.dto.RegistroRefugioDto;
import com.aluracursos.adopet.api.dto.RegistroMascotaDto;
import com.aluracursos.adopet.api.exceptions.ValidacionException;
import com.aluracursos.adopet.api.model.Refugio;
import com.aluracursos.adopet.api.service.MascotaService;
import com.aluracursos.adopet.api.service.RefugioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/refugios")
public class RefugioController {

    @Autowired
    private RefugioService refugioService;

    @Autowired
    private MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<List<RefugioDto>> listar() {
        List<RefugioDto> refugios = refugioService.listar();
        return ResponseEntity.ok(refugios);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> registrar(@RequestBody @Valid RegistroRefugioDto dto) {
        try{
            refugioService.registrar(dto);
            return ResponseEntity.ok().build();
        }catch (ValidacionException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/{idONombre}/mascotas")
    public ResponseEntity<List<MascotaDto>> listarMascotas(@PathVariable String idONombre) {
        try {
            List<MascotaDto> mascotasDelRefugio = refugioService.listarMascotasDelRefugio(idONombre);
            return ResponseEntity.ok(mascotasDelRefugio);
        } catch (ValidacionException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idONombre}/mascotas")
    @Transactional
    public ResponseEntity<String> registrarMascota(@PathVariable String idONombre, @RequestBody @Valid RegistroMascotaDto dto) {
        try {
            Refugio refugio = refugioService.cargarRefugio(idONombre);
            mascotaService.registrarMascota(refugio, dto);
            return ResponseEntity.ok().build();
        } catch (ValidacionException exception) {
            return ResponseEntity.notFound().build();
        }
    }

}
