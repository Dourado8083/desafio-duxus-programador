package br.com.duxusdesafio.dto;

import java.time.LocalDate;
import java.util.List;

public class TimeDTO {
    private Long id;
    private String nomeDoClube;
    private LocalDate data;
    private List<String> integrantes;

    public TimeDTO(Long id, String nomeDoClube, LocalDate data, List<String> integrantes) {
        this.id = id;
        this.nomeDoClube = nomeDoClube;
        this.data = data;
        this.integrantes = integrantes;
    }

    // getters
    public Long getId() { return id; }
    public String getNomeDoClube() { return nomeDoClube; }
    public LocalDate getData() { return data; }
    public List<String> getIntegrantes() { return integrantes; }
}