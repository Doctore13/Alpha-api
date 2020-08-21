package com.alpha.repositories;

import com.alpha.models.*;
import org.springframework.data.repository.CrudRepository;

public interface RespostaRepository extends CrudRepository<Resposta, Long> {
    Iterable<Resposta> findByProva(Prova prova);
    Iterable<Resposta> findByAlunoAndProva(Aluno aluno, Prova prova);
    Iterable<Resposta> findByAlunoAndProvaAndDisciplina(Aluno aluno, Prova prova, Disciplina disciplina);
    Iterable<Resposta> findByProvaAndDisciplina(Prova prova, Disciplina disciplina);
}
