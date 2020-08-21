package com.alpha.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Prova implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String prova;
    private String subtitulo;
    private Integer qtdQuestoes;
    private Double valor;
    private String data;

    @ManyToOne
    @JoinColumn(name = "escola")
    private Escola escola;

    @ManyToOne
    @JoinColumn(name = "serie")
    private  Serie serie;

    @OneToMany(mappedBy = "prova", targetEntity = Questao.class, cascade = CascadeType.ALL)
    private List<Questao> questoes;

    @OneToMany(mappedBy = "prova", targetEntity = Resposta.class, cascade = CascadeType.ALL)
    private List<Resposta> respostas;

    @OneToMany(mappedBy = "prova", targetEntity = AlunoProva.class, cascade = CascadeType.ALL)
    private List<AlunoProva> alunoProvas;

    @OneToMany(mappedBy = "prova", targetEntity = NotaDisciplina.class, cascade = CascadeType.ALL)
    private List<NotaDisciplina> notasDisciplina;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProva() {
        return prova;
    }

    public void setProva(String prova) {
        this.prova = prova;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public Integer getQtdQuestoes() {
        return qtdQuestoes;
    }

    public void setQtdQuestoes(Integer qtdQuestoes) {
        this.qtdQuestoes = qtdQuestoes;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    @JsonIgnore
    public Escola getEscola() {
        return escola;
    }
    @JsonIgnore
    public void setEscola(Escola escola) {
        this.escola = escola;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }
    @JsonIgnore
    public List<Questao> getQuestoes() {
        return questoes;
    }
    @JsonIgnore
    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
    }
    @JsonIgnore
    public List<Resposta> getRespostas() {
        return respostas;
    }
    @JsonIgnore
    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public List<AlunoProva> getAlunoProvas() {
        return alunoProvas;
    }

    public void setAlunoProvas(List<AlunoProva> alunoProvas) {
        this.alunoProvas = alunoProvas;
    }
    @JsonIgnore
    public List<NotaDisciplina> getNotasDisciplina() {
        return notasDisciplina;
    }

    public void setNotasDisciplina(List<NotaDisciplina> notasDisciplina) {
        this.notasDisciplina = notasDisciplina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prova prova = (Prova) o;
        return id.equals(prova.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
