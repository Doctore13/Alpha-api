package com.alpha.endpoints;


import com.alpha.models.Prova;
import com.alpha.models.Questao;
import com.alpha.repositories.EscolaRepository;
import com.alpha.repositories.ProvaRepository;
import com.alpha.repositories.QuestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("escola/{id}/prova/{idp}/questoes")
public class QuestaoEndpoint {

    private final QuestaoRepository questaoDAO;
    private final EscolaRepository escolaDAO;
    private final ProvaRepository provaDAO;
    @Autowired
    public QuestaoEndpoint(QuestaoRepository questaoDAO, EscolaRepository escolaDAO, ProvaRepository provaDAO) {
        this.questaoDAO = questaoDAO;
        this.escolaDAO = escolaDAO;
        this.provaDAO = provaDAO;
    }

    @GetMapping
    public ResponseEntity<?> getQuestoes(@PathVariable("id") Long id, @PathVariable("idp") Long idp){

        Prova prova = provaDAO.findById(idp).get();
        Iterable<Questao> questoes = questaoDAO.findByProva(prova);
        return new ResponseEntity<>(questoes, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<?> salvar(@PathVariable("id") Long id, @PathVariable("idp") Long idp, @RequestBody List<Questao> questoes){

        for (int i = 0; i < questoes.size(); i++) {
            Questao qt =  questoes.get(i);
            Prova prova = provaDAO.findById(idp).get();
            qt.setProva(prova);
            questaoDAO.save(qt);
            System.out.println(qt.getId());


        }return new ResponseEntity<>(questoes, HttpStatus.OK);
    }


}