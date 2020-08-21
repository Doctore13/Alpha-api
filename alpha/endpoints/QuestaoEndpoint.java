package com.alpha.endpoints;


import com.alpha.models.Disciplina;
import com.alpha.models.Prova;
import com.alpha.models.Questao;
import com.alpha.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("escola/{id}/prova/{idp}/questoes")
public class QuestaoEndpoint {

    private final QuestaoRepository questaoDAO;
    private final EscolaRepository escolaDAO;
    private final ProvaRepository provaDAO;
    private final AlunoRepository alunoDAO;
    private final DisciplinaRepository disciplinaDAO;

    @Autowired
    public QuestaoEndpoint(QuestaoRepository questaoDAO, EscolaRepository escolaDAO, ProvaRepository provaDAO, AlunoRepository alunoDAO, DisciplinaRepository disciplinaDAO) {
        this.questaoDAO = questaoDAO;
        this.escolaDAO = escolaDAO;
        this.provaDAO = provaDAO;
        this.alunoDAO = alunoDAO;
        this.disciplinaDAO = disciplinaDAO;
    }

    @GetMapping
    public ResponseEntity<?> getQuestoes(@PathVariable("id") Long id, @PathVariable("idp") Long idp){

        Prova prova = provaDAO.findById(idp).get();
        Iterable<Questao> questoes = questaoDAO.findByProva(prova);
        //List<Questao> questions = (List<Questao>) questoes;
        //System.out.println(questions.size());
        //List<Disciplina> disciplinas = new ArrayList<Disciplina>();
        return new ResponseEntity<>(questoes, HttpStatus.OK);

    }

    @GetMapping(path = "/{idd}/disciplina")
    public ResponseEntity<?> getQuestoesDisciplina(@PathVariable("id") Long id, @PathVariable("idp") Long idp, @PathVariable("idd") Long idd){

        Prova prova = provaDAO.findById(idp).get();
        Disciplina disciplina = disciplinaDAO.findById(idd).get();
        Iterable<Questao> questoes = questaoDAO.findByProvaAndDisciplina(prova , disciplina);
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