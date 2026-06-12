package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repository.IntegranteRepository;
import br.com.duxusdesafio.service.ApiService;
import br.com.duxusdesafio.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/times")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private TimeRepository timeRepository;
    @Autowired
    private IntegranteRepository integranteRepository;

    @GetMapping("/por-data")
    public ResponseEntity<Time> timeDaData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<Time> todosOsTimes = timeRepository.findAll();
        Time time = apiService.timeDaData(data, todosOsTimes);
        return ResponseEntity.ok(time);
    }

    @GetMapping("/integrante-mais-usado")
    public ResponseEntity<Integrante> integranteMaisUsado(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Time> todosOsTimes = timeRepository.findAll();
        Integrante integrante = apiService.integranteMaisUsado(dataInicial, dataFinal, todosOsTimes);
        return ResponseEntity.ok(integrante);
    }

    @GetMapping("/integrantes-time-mais-recorrente")
    public ResponseEntity<List<String>> integrantesDoTimeMaisRecorrente(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Time> todosOsTimes = timeRepository.findAll();
        List<String> integrantes = apiService.integrantesDoTimeMaisRecorrente(dataInicial, dataFinal, todosOsTimes);
        return ResponseEntity.ok(integrantes);
    }

    @GetMapping("/funcao-mais-recorrente")
    public ResponseEntity<String> funcaoMaisRecorrente(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Time> todosOsTimes = timeRepository.findAll();
        String funcao = apiService.funcaoMaisRecorrente(dataInicial, dataFinal, todosOsTimes);
        return ResponseEntity.ok(funcao);
    }

    @GetMapping("/clube-mais-recorrente")
    public ResponseEntity<String> clubeMaisRecorrente(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Time> todosOsTimes = timeRepository.findAll();
        String clube = apiService.clubeMaisRecorrente(dataInicial, dataFinal, todosOsTimes);
        return ResponseEntity.ok(clube);
    }

    @GetMapping("/contagem-clubes")
    public ResponseEntity<Map<String, Long>> contagemDeClubesNoPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Time> todosOsTimes = timeRepository.findAll();
        Map<String, Long> contagem = apiService.contagemDeClubesNoPeriodo(dataInicial, dataFinal, todosOsTimes);
        return ResponseEntity.ok(contagem);
    }

    @GetMapping("/contagem-funcoes")
    public ResponseEntity<Map<String, Long>> contagemPorFuncao(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Time> todosOsTimes = timeRepository.findAll();
        Map<String, Long> contagem = apiService.contagemPorFuncao(dataInicial, dataFinal, todosOsTimes);
        return ResponseEntity.ok(contagem);
    }

    @PostMapping("/integrantes")
    public ResponseEntity<Integrante> cadastrarIntegrante(@RequestBody Integrante integrante) {
        Integrante novoIntegrante = integranteRepository.save(integrante);
        return ResponseEntity.status(201).body(novoIntegrante);
    }

    @PostMapping
    public ResponseEntity<Time> cadastrarTime(@RequestBody Time time) {
        if (time.getComposicaoTime() != null) {
            time.getComposicaoTime().forEach(composicao -> composicao.setTime(time));
        }
        Time novoTime = timeRepository.save(time);
        return ResponseEntity.status(201).body(novoTime);
    }
}