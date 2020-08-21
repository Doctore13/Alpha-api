package com.alpha.repositories;

import com.alpha.models.Escola;
import com.alpha.models.Turma;
import org.springframework.data.repository.CrudRepository;

public interface TurmaRepository extends CrudRepository<Turma, Long> {

    Iterable<Turma> findByEscola(Escola escola);
}