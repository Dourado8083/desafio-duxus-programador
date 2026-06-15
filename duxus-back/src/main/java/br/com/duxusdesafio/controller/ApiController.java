package br.com.duxusdesafio.controller;

import br.com.duxusdesafio.exception.NenhumResultadoEncontradoException;
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
        return ResponseEntity.ok(apiService.timeDaData(data, todosOsTimes));
    }

    @GetMapping("/integrante-mais-usado")
    public ResponseEntity<Integrante> integranteMaisUsado(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Time> todosOsTimes = timeRepository.findAll();
        return ResponseEntity.ok(apiService.integranteMaisUsado(dataInicial, dataFinal, todosOsTimes));
    }

    @GetMapping("/integrantes-time-mais-recorrente")
    public ResponseEntity<List<String>> integrantesDoTimeMaisRecorrente(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Time> todosOsTimes = timeRepository.findAll();
        return ResponseEntity.ok(apiService.integrantesDoTimeMaisRecorrente(dataInicial, dataFinal, todosOsTimes));
    }

    @GetMapping("/funcao-mais-recorrente")
    public ResponseEntity<String> funcaoMaisRecorrente(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Time> todosOsTimes = timeRepository.findAll();
        return ResponseEntity.ok(apiService.funcaoMaisRecorrente(dataInicial, dataFinal, todosOsTimes));
    }

    @GetMapping("/clube-mais-recorrente")
    public ResponseEntity<String> clubeMaisRecorrente(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Time> todosOsTimes = timeRepository.findAll();
        return ResponseEntity.ok(apiService.clubeMaisRecorrente(dataInicial, dataFinal, todosOsTimes));
    }

    @GetMapping("/contagem-clubes")
    public ResponseEntity<Map<String, Long>> contagemDeClubesNoPeriodo(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Time> todosOsTimes = timeRepository.findAll();
        return ResponseEntity.ok(apiService.contagemDeClubesNoPeriodo(dataInicial, dataFinal, todosOsTimes));
    }

    @GetMapping("/contagem-funcoes")
    public ResponseEntity<Map<String, Long>> contagemPorFuncao(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        List<Time> todosOsTimes = timeRepository.findAll();
        return ResponseEntity.ok(apiService.contagemPorFuncao(dataInicial, dataFinal, todosOsTimes));
    }

    @PostMapping("/integrantes" )
    public ResponseEntity<Integrante> cadastrarIntegrante(@RequestBody Integrante integrante) {
        return ResponseEntity.status(201).body(integranteRepository.save(integrante));
    }

    @PostMapping
    public ResponseEntity<Time> cadastrarTime(@RequestBody Time time) {
        if (time.getComposicaoTime() != null) {
            time.getComposicaoTime().forEach(composicao -> {
                composicao.setTime(time);
                // busca o integrante completo pelo id
                Integrante integrante = integranteRepository.findById(composicao.getIntegrante().getId())
                        .orElseThrow(() -> new NenhumResultadoEncontradoException("Integrante não encontrado!"));
                composicao.setIntegrante(integrante);
            });

        }
        return ResponseEntity.status(201).body(timeRepository.save(time));
    }
}