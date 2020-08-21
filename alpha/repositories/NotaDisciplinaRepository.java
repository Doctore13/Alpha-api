package com.alpha.repositories;

import com.alpha.models.Aluno;
import com.alpha.models.Disciplina;
import com.alpha.models.NotaDisciplina;
import com.alpha.models.Prova;
import org.springframework.data.repository.CrudRepository;

public interface NotaDisciplinaRepository extends CrudRepository<NotaDisciplina, Long> {
    Iterable<NotaDisciplina> findByProvaAndDisciplina( Prova prova, Disciplina disciplina);
    Iterable<NotaDisciplina> findByProva(Prova prova);
}
