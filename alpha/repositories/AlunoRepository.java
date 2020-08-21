package com.alpha.repositories;

import com.alpha.models.Aluno;
import com.alpha.models.Escola;
import org.springframework.data.repository.CrudRepository;

public interface AlunoRepository extends CrudRepository<Aluno, Long> {

    Iterable<Aluno> findByEscola(Escola escola);

}