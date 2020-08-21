package com.alpha.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Resposta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer nr;
    private String resposta;
    private Integer questao;


    @ManyToOne
    @JoinColumn(name = "aluno")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "prova")
    private Prova prova;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Integer getQuestao() {
        return questao;
    }

    public void setQuestao(Integer questao) {
        this.questao = questao;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Prova getProva() {
        return prova;
    }

    public void setProva(Prova prova) {
        this.prova = prova;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resposta resposta = (Resposta) o;
        return id.equals(resposta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
