package com.alpha.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Aluno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String matricula;
    private String aluno;
    private String sala;

    @ManyToOne
    @JoinColumn(name = "escola")
    private Escola escola;

    @ManyToOne
    @JoinColumn(name = "serie")
    private Serie serie;

    @ManyToOne
    @JoinColumn(name = "turma")
    private Turma turma;

    @OneToMany(mappedBy = "aluno", targetEntity = Resposta.class)
    private List<Resposta> respostas;

    @OneToMany(mappedBy = "aluno", targetEntity = AlunoProva.class, cascade = CascadeType.ALL)
    private List<AlunoProva> alunoProvas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
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

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }
    @JsonIgnore
    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }
    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }
    @JsonIgnore
    public List<AlunoProva> getAlunoProvas() {
        return alunoProvas;
    }

    public void setAlunoProvas(List<AlunoProva> alunoProvas) {
        this.alunoProvas = alunoProvas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return id.equals(aluno.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
