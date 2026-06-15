package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.dto.TimeDTO;
import br.com.duxusdesafio.exception.NenhumResultadoEncontradoException;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repository.IntegranteRepository;
import br.com.duxusdesafio.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/times")
public class TimeController {

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private IntegranteRepository integranteRepository;

    @PostMapping
    public ResponseEntity<Time> cadastrar(@RequestBody Time time) {
        if (time.getComposicaoTime() != null) {
            time.getComposicaoTime().forEach(composicao -> {
                composicao.setTime(time);
                Integrante integrante = integranteRepository.findById(composicao.getIntegrante().getId())
                        .orElseThrow(() -> new NenhumResultadoEncontradoException("Integrante não encontrado!"));
                composicao.setIntegrante(integrante);
            });
        }
        return ResponseEntity.status(201).body(timeRepository.save(time));
    }
    @GetMapping
    public ResponseEntity<List<TimeDTO>> listarTodos() {
        List<TimeDTO> times = timeRepository.findAll().stream()
                .map(t -> new TimeDTO(
                        t.getId(),
                        t.getNomeDoClube(),
                        t.getData(),
                        t.getComposicaoTime().stream()
                                .map(ct -> ct.getIntegrante().getNome())
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(times);
    }
}