package com.alpha.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Serie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String serie;

    @ManyToOne
    @JoinColumn(name = "escola")
    private Escola escola;

    @OneToMany(mappedBy = "serie", targetEntity = Aluno.class, cascade = CascadeType.ALL)
    private List<Aluno> alunos;

    @OneToMany(mappedBy = "serie", targetEntity = Prova.class, cascade = CascadeType.ALL)
    private List<Prova> provas;

    @OneToMany(mappedBy = "serie", targetEntity = Turma.class, cascade = CascadeType.ALL)
    private List<Turma> turmas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }
    @JsonIgnore
    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }
    @JsonIgnore
    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }
    @JsonIgnore
    public List<Prova> getProvas() {
        return provas;
    }

    public void setProvas(List<Prova> provas) {
        this.provas = provas;
    }
    @JsonIgnore
    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serie serie = (Serie) o;
        return id.equals(serie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
