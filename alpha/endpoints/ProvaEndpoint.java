package com.alpha.endpoints;

import com.alpha.models.*;
import com.alpha.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("escola/{id}/provas")
public class ProvaEndpoint {

    private final ProvaRepository provaDAO;
    private final EscolaRepository escolaDAO;
    private final SerieRepository serieDAO;
    private final QuestaoRepository questaoDAO;
    private final AlunoRepository alunoDAO;
    private final RespostaRepository respostaDAO;
    private final AlunoProvaRepository alunoProvaDAO;
    private final NotaDisciplinaRepository notaDisciplinaDAO;

    @Autowired
    public ProvaEndpoint(ProvaRepository provaDAO, EscolaRepository escolaDAO, SerieRepository serieDAO, QuestaoRepository questaoDAO, AlunoRepository alunoDAO, RespostaRepository respostaDAO, AlunoProvaRepository alunoProvaDAO, NotaDisciplinaRepository notaDisciplinaDAO) {
        this.provaDAO = provaDAO;
        this.escolaDAO = escolaDAO;
        this.serieDAO = serieDAO;
        this.questaoDAO = questaoDAO;
        this.alunoDAO = alunoDAO;
        this.respostaDAO = respostaDAO;
        this.alunoProvaDAO = alunoProvaDAO;
        this.notaDisciplinaDAO = notaDisciplinaDAO;
    }

    @GetMapping
    public ResponseEntity<?> getEscolaById(@PathVariable("id") Long id) {
        Escola escola = escolaDAO.findById(id).get();
        Iterable<Prova> provas = provaDAO.findByEscola(escola);
        return new ResponseEntity<>(provas, HttpStatus.OK);

    }

    @GetMapping(path = "/{idp}/corrige")
    public HttpStatus corrigirProva(@PathVariable("id") Long id, @PathVariable("idp") Long idp) {

        Prova prova = provaDAO.findById(idp).get();
        Serie serie = prova.getSerie();
        Iterable<Questao> questoes = questaoDAO.findByProva(prova);
        Set<Disciplina> disciplinas = new HashSet<>();
        Iterable<Aluno> alunos = alunoDAO.findBySerie(serie);

        //pegar disciplinas da prova
        for (Questao q : questoes) {
            disciplinas.add(q.getDisciplina());
        }

        for (Aluno a : alunos) {
            System.out.println();
            System.out.println(a.getAluno());
            for (Disciplina d : disciplinas) {
                System.out.println();
                System.out.println(d.getDisciplina());
                Iterable<Resposta> respostas = respostaDAO.findByAlunoAndProvaAndDisciplina(a, prova, d);
                for (Resposta r : respostas) {
                    System.out.print(r.getQuestao().getGabarito() + ":" + r.getResposta() + " | ");
                }
            }
        }
        return HttpStatus.OK;
    }

    @GetMapping(path = "/{idp}/corrigir")
    public HttpStatus getAlunosByProva(@PathVariable("id") Long id, @PathVariable("idp") Long idp) {
        Prova prova = provaDAO.findById(idp).get();
        Serie serie = prova.getSerie();
        Iterable<Questao> questoes = questaoDAO.findByProva(prova);
        ArrayList<Questao> questions = (ArrayList<Questao>) questoes;
        Iterable<Aluno> alunos = alunoDAO.findBySerie(serie);
        Set<Disciplina> set = new HashSet<>();

        for (Aluno a : alunos) {
            Iterable<Resposta> respostas = respostaDAO.findByAlunoAndProva(a, prova);
            ArrayList<Resposta> r = (ArrayList<Resposta>) respostas;
            double nota = new Double(0);
            int acertos = 0;
            int erros = 0;
            int brancos = 0;
            int nulos = 0;
            System.out.println("Aluno: " + a.getAluno());
            for (int i = 0; i < r.size(); i++) {
                System.out.println("Resposta: " + r.get(i).getResposta());
                System.out.println("Gabarito: " + questions.get(i).getGabarito());
                set.add(questions.get(i).getDisciplina());
                r.get(i).setQuestao(questions.get(i));
                r.get(i).setDisciplina(questions.get(i).getDisciplina());
                String branco = "*";
                String brancob = "***";
                switch (questions.get(i).getFator()) {

                    case 0:
                        if (r.get(i).getResposta().equals(questions.get(i).getGabarito())) {
                            r.get(i).setNota(questions.get(i).getValor());
                            acertos++;
                            nota = nota + questions.get(i).getValor();
                        } else if (r.get(i).getResposta().equals(brancob)) {
                            r.get(i).setNota((float) 0);
                            nota = nota + 0;
                            brancos++;
                        } else {
                            erros++;
                        }

                        break;

                    case 1:
                        if (r.get(i).getResposta().equals(questions.get(i).getGabarito())) {
                            r.get(i).setNota(questions.get(i).getValor());
                            nota = nota + questions.get(i).getValor();
                            acertos++;
                        } else if (r.get(i).getResposta().equals(branco)) {
                            r.get(i).setNota((float) 0);
                            nota = nota + 0;
                            brancos++;
                        } else {
                            nota = nota + questions.get(i).getValor() * -1;
                            r.get(i).setNota(questions.get(i).getValor() * -1);
                            erros++;
                        }

                        break;
                    case 2:
                        if (r.get(i).getResposta().equals(questions.get(i).getGabarito())) {
                            r.get(i).setNota(questions.get(i).getValor());
                            nota = nota + questions.get(i).getValor();
                            acertos++;
                        } else if (r.get(i).getResposta().equals(branco)) {
                            r.get(i).setNota((float) 0);
                            nota = nota + 0;
                            brancos++;
                        } else {
                            nota = nota + questions.get(i).getValor() * -0.5;
                            r.get(i).setNota((float) (questions.get(i).getValor() * -0.5));
                            erros++;
                        }

                        break;

                    case 3:
                        if (r.get(i).getResposta().equals(questions.get(i).getGabarito())) {
                            r.get(i).setNota(questions.get(i).getValor());
                            nota = nota + questions.get(i).getValor();
                            brancos++;
                        } else {
                            nota = nota + questions.get(i).getValor() * -.333;
                            r.get(i).setNota((float) (questions.get(i).getValor() * -.333));
                            erros++;
                        }
                        break;

                    default:
                        System.out.println("ERRO NO FATOR");
                }
            }

            AlunoProva alunoProva = new AlunoProva();
            alunoProva.setAluno(a);
            alunoProva.setProva(prova);
            alunoProva.setAcertos(acertos);
            alunoProva.setErros(erros);
            alunoProva.setBrancos(brancos);
            alunoProva.setNulos(nulos);
            alunoProva.setRendimento(String.valueOf((nota / prova.getValor()) * 100));

            alunoProva.setNota((float) nota);
            System.out.println("Aluno: " + a.getAluno() + " Nota: " + nota);

            alunoProvaDAO.save(alunoProva);

            //Notas Disciplinas
            Aluno aluno = new Aluno();

            for (Disciplina d : set) {
                Iterable<Resposta> resposta = respostaDAO.findByAlunoAndProvaAndDisciplina(a, prova, d);
                List<Resposta> lista = (List<Resposta>) resposta;
                double nd = 0;
                for (Resposta resp : resposta) {
                    nd = nd + resp.getNota();
                    aluno = resp.getAluno();
                }
                NotaDisciplina notaDisciplina = new NotaDisciplina();
                notaDisciplina.setAluno(aluno);
                notaDisciplina.setProva(prova);
                notaDisciplina.setDisciplina(d);
                notaDisciplina.setNota((float) nd);

                notaDisciplinaDAO.save(notaDisciplina);
                System.out.println("Disciplina: " + notaDisciplina.getDisciplina().getDisciplina() + " Nota: " + notaDisciplina.getNota());
            }

        }


        return HttpStatus.OK;
    }

    @PostMapping
    public ResponseEntity<?> saves(@PathVariable("id") Long id, @RequestBody Prova prova) {
        Escola escola = escolaDAO.findById(id).get();
        prova.setEscola(escola);

        Serie serie = serieDAO.findById(prova.getSerie().getId()).get();
        prova.setSerie(serie);

        int contador = 0;
        int nq = 1;
        Disciplina disciplina = new Disciplina();
        disciplina.setEscola(escola);
        disciplina.setDisciplina(prova.getProva());

        disciplina.setSigla(prova.getProva().substring(0, 6).replace(" ", ""));
        while (contador < prova.getQtdQuestoes()) {

            Questao q = new Questao();
            q.setProva(prova);
            q.setValor((float) (prova.getValor() / prova.getQtdQuestoes()));
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
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Prova prova) {
        Escola escola = escolaDAO.findById(id).get();
        prova.setEscola(escola);

        Serie serie = serieDAO.findById(prova.getSerie().getId()).get();
        prova.setSerie(serie);

        return new ResponseEntity<>(provaDAO.save(prova), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        provaDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}