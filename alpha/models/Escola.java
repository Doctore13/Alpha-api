package com.alpha.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Escola implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String escola;
    private String email;
    private String local;
    private String logo;

    @OneToMany(mappedBy = "escola", targetEntity = Aluno.class, cascade = CascadeType.ALL)
    private List<Aluno> alunos;

    @OneToMany(mappedBy = "escola", targetEntity = Serie.class, cascade = CascadeType.ALL)
    private List<Serie> series;

    @OneToMany(mappedBy = "escola", targetEntity = Turma.class, cascade = CascadeType.ALL)
    private List<Turma> turmas;

    @OneToMany(mappedBy = "escola", targetEntity = Prova.class, cascade = CascadeType.ALL)
    private List<Prova> provas;

    @OneToMany(mappedBy = "escola", targetEntity = Disciplina.class, cascade = CascadeType.ALL)
    private List<Disciplina> disciplinas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    @JsonIgnore
    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }
    @JsonIgnore
    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }
    @JsonIgnore
    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }
    @JsonIgnore
    public List<Prova> getProvas() {
        return provas;
    }

    public void setProvas(List<Prova> provas) {
        this.provas = provas;
    }
    @JsonIgnore
    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Escola escola = (Escola) o;
        return id.equals(escola.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
