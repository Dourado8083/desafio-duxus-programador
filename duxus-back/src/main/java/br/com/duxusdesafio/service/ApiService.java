package br.com.duxusdesafio.service;

import br.com.duxusdesafio.exception.DataInvalidaException;
import br.com.duxusdesafio.exception.NenhumResultadoEncontradoException;
import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApiService {

    public Time timeDaData(LocalDate data, List<Time> todosOsTimes) {
        return todosOsTimes.stream()
                .filter(t -> t.getData().equals(data))
                .findFirst()
                .orElseThrow(() -> new NenhumResultadoEncontradoException("Nenhum time encontrado para a data: " + data));
    }

    public Integrante integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        validarPeriodo(dataInicial, dataFinal);

        return filtrarPorPeriodo(todosOsTimes, dataInicial, dataFinal)
                .flatMap(t -> t.getComposicaoTime().stream())
                .map(ComposicaoTime::getIntegrante)
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new NenhumResultadoEncontradoException("Nenhum integrante encontrado no período."));
    }

    public List<String> integrantesDoTimeMaisRecorrente(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        validarPeriodo(dataInicial, dataFinal);

        String clubeMaisRecorrente = clubeMaisRecorrente(dataInicial, dataFinal, todosOsTimes);

        return filtrarPorPeriodo(todosOsTimes, dataInicial, dataFinal)
                .filter(t -> t.getNomeDoClube().equals(clubeMaisRecorrente))
                .findFirst()
                .map(t -> t.getComposicaoTime().stream()
                        .map(ct -> ct.getIntegrante().getNome())
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new NenhumResultadoEncontradoException("Nenhum time recorrente encontrado no período."));
    }

    public String funcaoMaisRecorrente(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        validarPeriodo(dataInicial, dataFinal);

        return filtrarPorPeriodo(todosOsTimes, dataInicial, dataFinal)
                .flatMap(t -> t.getComposicaoTime().stream())
                .map(ct -> ct.getIntegrante().getFuncao())
                .collect(Collectors.groupingBy(f -> f, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new NenhumResultadoEncontradoException("Nenhuma função encontrada no período."));
    }

    public String clubeMaisRecorrente(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        validarPeriodo(dataInicial, dataFinal);

        return filtrarPorPeriodo(todosOsTimes, dataInicial, dataFinal)
                .collect(Collectors.groupingBy(Time::getNomeDoClube, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new NenhumResultadoEncontradoException("Nenhum clube encontrado no período."));
    }

    public Map<String, Long> contagemDeClubesNoPeriodo(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        validarPeriodo(dataInicial, dataFinal);

        Map<String, Long> contagem = filtrarPorPeriodo(todosOsTimes, dataInicial, dataFinal)
                .collect(Collectors.groupingBy(Time::getNomeDoClube, Collectors.counting()));

        if (contagem.isEmpty()) throw new NenhumResultadoEncontradoException("Nenhum clube encontrado no período.");

        return contagem;
    }

    public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
        validarPeriodo(dataInicial, dataFinal);

        Map<String, Long> contagem = filtrarPorPeriodo(todosOsTimes, dataInicial, dataFinal)
                .flatMap(t -> t.getComposicaoTime().stream())
                .map(ct -> ct.getIntegrante())
                .distinct() // evita contar o mesmo integrante mais de uma vez
                .map(Integrante::getFuncao)
                .collect(Collectors.groupingBy(f -> f, Collectors.counting()));

        if (contagem.isEmpty()) throw new NenhumResultadoEncontradoException("Nenhuma função encontrada no período.");

        return contagem;
    }

    // Métodos auxiliares

    private java.util.stream.Stream<Time> filtrarPorPeriodo(List<Time> times, LocalDate dataInicial, LocalDate dataFinal) {
        return times.stream()
                .filter(t -> (dataInicial == null || !t.getData().isBefore(dataInicial))
                        && (dataFinal == null || !t.getData().isAfter(dataFinal)));
    }

    private void validarPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial != null && dataFinal != null && dataInicial.isAfter(dataFinal)) {
            throw new DataInvalidaException("Data inicial não pode ser maior que a data final.");
        }
    }
}