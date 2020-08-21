package com.alpha.repositories;

import com.alpha.models.Escola;
import com.alpha.models.Serie;
import org.springframework.data.repository.CrudRepository;

public interface SerieRepository extends CrudRepository<Serie, Long> {

    Iterable<Serie> findByEscola(Escola escola);
}
