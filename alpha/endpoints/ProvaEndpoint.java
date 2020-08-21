package com.alpha.endpoints;

import com.alpha.models.*;
import com.alpha.repositories.EscolaRepository;
import com.alpha.repositories.ProvaRepository;
import com.alpha.repositories.QuestaoRepository;
import com.alpha.repositories.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("escola/{id}/provas")
public class ProvaEndpoint {



    private final ProvaRepository provaDAO;
    private final EscolaRepository escolaDAO;
    private final SerieRepository serieDAO;
    private final QuestaoRepository questaoDAO;
    @Autowired
    public ProvaEndpoint(ProvaRepository provaDAO, EscolaRepository escolaDAO, SerieRepository serieDAO, QuestaoRepository questaoDAO) {
        this.provaDAO = provaDAO;
        this.escolaDAO = escolaDAO;
        this.serieDAO = serieDAO;

        this.questaoDAO = questaoDAO;
    }

    @GetMapping
    public ResponseEntity<?> getEscolaById(@PathVariable("id") Long id){
        Escola escola = escolaDAO.findById(id).get();
        Iterable<Prova> provas = provaDAO.findByEscola(escola);
        return new ResponseEntity<>(provas,  HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> saves(@PathVariable("id") Long id, @RequestBody Prova prova){
        Escola escola = escolaDAO.findById(id).get();
        prova.setEscola(escola);

        Serie serie = serieDAO.findById(prova.getSerie().getId()).get();
        prova.setSerie(serie);

        int contador = 0;
        int nq = 1;
        Disciplina disciplina = new Disciplina();
        disciplina.setDisciplina(prova.getProva());
        disciplina.setSigla(prova.getProva());
        while (contador < prova.getQtdQuestoes()){

            Questao q = new Questao();
            q.setProva(prova);
            q.setValor((float) (prova.getValor()/prova.getQtdQuestoes()));
            q.setQuestao(nq);
            q.setDisciplina(disciplina);
            q.setFator(0);
            q.setTipo("A");


            contador++;
            nq++;
            questaoDAO.save(q);
        }
        return new ResponseEntity<>(provaDAO.save(prova), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Prova prova){
        Escola escola = escolaDAO.findById(id).get();
        prova.setEscola(escola);

        Serie serie = serieDAO.findById(prova.getSerie().getId()).get();
        prova.setSerie(serie);

        return new ResponseEntity<>(provaDAO.save(prova), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        provaDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}