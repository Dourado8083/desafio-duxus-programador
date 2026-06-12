package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.repository.IntegranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/integrantes")
public class IntegranteController {

    @Autowired
    private IntegranteRepository integranteRepository;

    @PostMapping
    public ResponseEntity<Integrante> cadastrar(@RequestBody Integrante integrante) {
        return ResponseEntity.status(201).body(integranteRepository.save(integrante));
    }

    @GetMapping
    public ResponseEntity<List<Integrante>> listarTodos() {
        return ResponseEntity.ok(integranteRepository.findAll());
    }
}