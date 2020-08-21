package com.alpha.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Disciplina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String disciplina;
    private String sigla;

    @ManyToOne
    @JoinColumn(name = "escola")
    private Escola escola;

    @OneToMany(mappedBy = "disciplina", targetEntity = Questao.class, cascade = CascadeType.ALL)
    private List<Questao> questoes;

    @OneToMany(mappedBy = "disciplina", targetEntity = Resposta.class, cascade = CascadeType.ALL)
    private List<Resposta> respostas;

    @OneToMany(mappedBy = "disciplina", targetEntity = NotaDisciplina.class, cascade = CascadeType.ALL)
    private List<NotaDisciplina> notasDisciplina;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    @JsonIgnore
    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }
    @JsonIgnore
    public List<Questao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
    }
    @JsonIgnore
    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
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
        Disciplina that = (Disciplina) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
