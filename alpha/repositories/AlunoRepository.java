package com.alpha.repositories;

import com.alpha.models.Aluno;
import com.alpha.models.Escola;
import com.alpha.models.Prova;
import com.alpha.models.Serie;
import org.springframework.data.repository.CrudRepository;

public interface AlunoRepository extends CrudRepository<Aluno, Long> {

    Iterable<Aluno> findByEscola(Escola escola);
    Iterable<Aluno> findBySerie(Serie serie);
}