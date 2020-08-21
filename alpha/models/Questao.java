package com.alpha.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Questao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer questao;
    private String tipo;
    private Integer fator;
    private Float valor;
    private String gabarito;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "prova_id")
    private Prova prova;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "disciplina")
    private Disciplina disciplina;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuestao() {
        return questao;
    }

    public void setQuestao(Integer questao) {
        this.questao = questao;
    }

    public Integer getFator() {
        return fator;
    }

    public void setFator(Integer fator) {
        this.fator = fator;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getGabarito() {
        return gabarito;
    }

    public void setGabarito(String gabarito) {
        this.gabarito = gabarito;
    }

    public Prova getProva() {
        return prova;
    }

    public void setProva(Prova prova) {
        this.prova = prova;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Questao questao = (Questao) o;
        return id.equals(questao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
