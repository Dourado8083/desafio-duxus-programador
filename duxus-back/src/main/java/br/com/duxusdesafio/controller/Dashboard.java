package br.com.duxusdesafio.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

// Imports do Spring Framework (Web e Formatação)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// ⚠️ ATENÇÃO: Ajuste estes 3 imports abaixo com o pacote real do seu projeto!
import br.com.duxusdesafio.service.ApiService;
import br.com.duxusdesafio.repository.TimeRepository;
import br.com.duxusdesafio.model.Time;


@RestController
@RequestMapping("/api/dashboard")
public class Dashboard {

    @Autowired
    private ApiService apiService;

    @Autowired
    private TimeRepository timeRepository; // Ou o service que busca os times

    @GetMapping("/contagem-clubes")
    public ResponseEntity<Map<String, Long>> getContagemClubes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {

        List<Time> todosOsTimes = timeRepository.findAll();
        Map<String, Long> resultado = apiService.contagemDeClubesNoPeriodo(dataInicial, dataFinal, todosOsTimes);

        return ResponseEntity.ok(resultado);
    }
}