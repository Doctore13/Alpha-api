package com.alpha.repositories;

import com.alpha.models.Prova;
import com.alpha.models.Questao;
import org.springframework.data.repository.CrudRepository;

public interface QuestaoRepository extends CrudRepository<Questao,Long> {
    Iterable<Questao> findByProva(Prova prova);


}