package com.alpha.endpoints;


import com.alpha.models.*;
import com.alpha.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("escola/{id}/prova/{idp}/notasd")
public class NotaDisciplinaEndpoint {

    private final NotaDisciplinaRepository notaDisciplinaDAO;
    private final ProvaRepository provaDAO;
    private final QuestaoRepository questaoDAO;
    private final RespostaRepository respostaDAO;

    @Autowired
    public NotaDisciplinaEndpoint(NotaDisciplinaRepository notaDisciplinaDAO, ProvaRepository provaDAO, AlunoRepository alunoDAO, QuestaoRepository questaoDAO, RespostaRepository respostaDAO) {
        this.notaDisciplinaDAO = notaDisciplinaDAO;
        this.provaDAO = provaDAO;
        this.questaoDAO = questaoDAO;

        this.respostaDAO = respostaDAO;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(notaDisciplinaDAO.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/gerar")
    public HttpStatus listarProvaAluno(@PathVariable("id") Long id, @PathVariable("idp") Long idp) {
        Prova prova = provaDAO.findById(idp).get();
        Iterable<Questao> questoes = questaoDAO.findByProva(prova);
        Set<Disciplina> disciplinas = new HashSet<>();
        for (Questao q : questoes) {
            disciplinas.add(q.getDisciplina());
        }

        for (Disciplina d : disciplinas) {
            ArrayList<NotaDisciplina> nd = (ArrayList<NotaDisciplina>) notaDisciplinaDAO.findByProvaAndDisciplina(prova, d);
            ArrayList<Questao> qts = (ArrayList<Questao>) questaoDAO.findByProvaAndDisciplina(prova, d);
            ArrayList<Resposta> resp = (ArrayList<Resposta>) respostaDAO.findByProvaAndDisciplina(prova, d);
            double max = 0;
            double maiorNota = 0;
            double menorNota = 1000;
            double media = 0;
            double rendimento =0;
            double somaNota = 0;





            System.out.println("Disciplina: " + d.getDisciplina() + ": " + somaNota);
            for (Questao q : qts) {
                max = max + q.getValor();
            }

            for (NotaDisciplina n : nd) {
                if (n.getNota() > maiorNota) {
                    maiorNota = n.getNota();
                }
                if (n.getNota() < menorNota) {
                    menorNota = n.getNota();
                }
            }

            for (int i = 0; i < nd.size(); i++) {
                somaNota = somaNota + nd.get(i).getNota();
                media = somaNota / nd.size();
                //rendimento = (nd.get(i).getNota())/max*100;
                //System.out.println("rendimento: " + nd.get(i).getNota()+ " Maximo: " + max);
               // rendimento = nd.get(i).getNota()/max * 100;
              //  System.out.println("rendimento: " + rendimento);
            }

            for (NotaDisciplina n : nd){
                n.setMenor(menorNota);
                n.setMaior(maiorNota);
                n.setValor(max);
                n.setMedia(media);
                n.setRendimento(String.valueOf(n.getNota()/max * 100));
                update(n);

            }






        }

        return HttpStatus.OK;
    }


    @PutMapping
    public ResponseEntity<?> update(@RequestBody NotaDisciplina notaDisciplina) {
        notaDisciplinaDAO.save(notaDisciplina);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
