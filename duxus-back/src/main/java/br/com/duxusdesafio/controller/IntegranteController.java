package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.repository.IntegranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<Map<String, Object>>> listarTodos() {
        List<Map<String, Object>> result = integranteRepository.findAll()
                .stream()
                .map(i -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", i.getId());
                    map.put("nome", i.getNome());
                    map.put("funcao", i.getFuncao());
                    return map;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}