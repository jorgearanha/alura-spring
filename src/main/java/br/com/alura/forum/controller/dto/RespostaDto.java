package br.com.alura.forum.controller.dto;

import br.com.alura.forum.modelo.Resposta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class RespostaDto {

    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String nomeAutor;

    public RespostaDto(Resposta resposta) {
        this.id = resposta.getId();
        this.mensagem = resposta.getMensagem();
        this.dataCriacao = resposta.getDataCriacao();
        this.nomeAutor = resposta.getMensagem();
    }

    public static List<RespostaDto> converter(List<Resposta> respostas) {
        return respostas.stream().map(RespostaDto::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

}
