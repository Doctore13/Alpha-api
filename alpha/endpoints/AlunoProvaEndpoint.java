package com.alpha.endpoints;

import com.alpha.models.Aluno;
import com.alpha.models.AlunoProva;
import com.alpha.models.Prova;
import com.alpha.repositories.AlunoProvaRepository;
import com.alpha.repositories.AlunoRepository;
import com.alpha.repositories.ProvaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("escola/{id}/prova/{idp}/notas")
public class AlunoProvaEndpoint {

    private final AlunoProvaRepository alunoProvaDAO;
    private final ProvaRepository provaDAO;
    private final AlunoRepository alunoDAO;


    @Autowired
    public AlunoProvaEndpoint(AlunoProvaRepository alunoProvaDAO, ProvaRepository provaDAO, AlunoRepository alunoDAO) {
        this.alunoProvaDAO = alunoProvaDAO;
        this.provaDAO = provaDAO;
        this.alunoDAO = alunoDAO;
    }



    @GetMapping
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(alunoProvaDAO.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{ida}")
    public ResponseEntity<?> listar(@PathVariable Long id, @PathVariable Long idp, @PathVariable Long ida) {
        Prova prova = provaDAO.findById(idp).get();
        Aluno aluno = alunoDAO.findById(ida).get();
        AlunoProva alunoProva = alunoProvaDAO.findByAlunoAndProva(aluno, prova);
        System.out.println(alunoProva.getNotasDisciplinas().size());

        return new ResponseEntity<>(alunoProva, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody AlunoProva alunoProva){
        return new ResponseEntity<>(alunoProvaDAO.save(alunoProva), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody AlunoProva alunoProva){
        return new ResponseEntity<>(alunoProvaDAO.save(alunoProva), HttpStatus.OK);
    }


}
