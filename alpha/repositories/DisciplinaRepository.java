package com.alpha.repositories;

import com.alpha.models.Disciplina;
import com.alpha.models.Escola;
import org.springframework.data.repository.CrudRepository;

public interface DisciplinaRepository extends CrudRepository<Disciplina, Long> {
    Iterable<Disciplina> findByEscola(Escola escola);

}