package com.alpha.repositories;

import com.alpha.models.Prova;
import com.alpha.models.Questao;
import com.alpha.models.Resposta;
import org.springframework.data.repository.CrudRepository;

public interface RespostaRepository extends CrudRepository<Resposta, Long> {
    Iterable<Resposta> findByProva(Prova prova);
}
