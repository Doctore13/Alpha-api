package com.alpha.repositories;

import com.alpha.models.Aluno;
import com.alpha.models.AlunoProva;

import com.alpha.models.Prova;
import org.springframework.data.repository.CrudRepository;

public interface AlunoProvaRepository extends CrudRepository <AlunoProva, Long> {
    AlunoProva findByAlunoAndProva(Aluno aluno, Prova prova);
}
