package com.alpha.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class AlunoProva implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Float nota;
    private Integer erros;
    private Integer acertos;
    private Integer brancos;
    private Integer nulos;
    private String rendimento;


    @OneToMany(mappedBy = "disciplina", targetEntity = NotaDisciplina.class, cascade = CascadeType.ALL)
    private List<NotaDisciplina> notasDisciplinas;

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

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    @JsonIgnore
    public Prova getProva() {
        return prova;
    }

    public void setProva(Prova prova) {
        this.prova = prova;
    }
    @JsonIgnore
    public List<NotaDisciplina> getNotasDisciplinas() {
        return notasDisciplinas;
    }

    public void setNotasDisciplinas(List<NotaDisciplina> notasDisciplinas) {
        this.notasDisciplinas = notasDisciplinas;
    }

    public Integer getErros() {
        return erros;
    }

    public void setErros(Integer erros) {
        this.erros = erros;
    }

    public Integer getAcertos() {
        return acertos;
    }

    public void setAcertos(Integer acertos) {
        this.acertos = acertos;
    }

    public Integer getBrancos() {
        return brancos;
    }

    public void setBrancos(Integer brancos) {
        this.brancos = brancos;
    }

    public Integer getNulos() {
        return nulos;
    }

    public void setNulos(Integer nulos) {
        this.nulos = nulos;
    }

    public String getRendimento() {
        return rendimento;
    }

    public void setRendimento(String rendimento) {
        this.rendimento = rendimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlunoProva that = (AlunoProva) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
