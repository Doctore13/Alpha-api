package com.alpha.endpoints;

import com.alpha.models.Disciplina;
import com.alpha.models.Escola;
import com.alpha.repositories.DisciplinaRepository;
import com.alpha.repositories.EscolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("escola/{id}/disciplinas")
public class DisciplinaEndpoint {

    private final DisciplinaRepository disciplinaDAO;
    private final EscolaRepository escolaDAO;

    @Autowired
    public DisciplinaEndpoint(DisciplinaRepository disciplinaDAO, EscolaRepository escolaDAO) {
        this.disciplinaDAO = disciplinaDAO;
        this.escolaDAO = escolaDAO;
    }

    @GetMapping
    public ResponseEntity<?> getDisciplinas(@PathVariable("id") Long id) {
        Escola escola = escolaDAO.findById(id).get();
        Iterable<Disciplina> disciplinas = disciplinaDAO.findByEscola(escola);
        return new ResponseEntity<>(disciplinas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saves(@PathVariable("id") Long id, @RequestBody Disciplina disciplina) {
        Escola escola = escolaDAO.findById(id).get();
        disciplina.setEscola(escola);
        return new ResponseEntity<>(disciplinaDAO.save(disciplina), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Disciplina disciplina) {
        Escola escola = escolaDAO.findById(id).get();
        disciplina.setEscola(escola);
        return new ResponseEntity<>(disciplinaDAO.save(disciplina), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{idd}")
    public ResponseEntity<?> deleteDisciplina(@PathVariable Long idd) {
        disciplinaDAO.deleteById(idd);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}