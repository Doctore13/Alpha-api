package com.alpha.repositories;

import com.alpha.models.Escola;
import com.alpha.models.Prova;
import org.springframework.data.repository.CrudRepository;

public interface ProvaRepository extends CrudRepository<Prova, Long> {
    Iterable<Prova> findByEscola(Escola escola);
}