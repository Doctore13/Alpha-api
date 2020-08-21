package com.alpha.endpoints;

import com.alpha.models.Escola;
import com.alpha.models.Serie;
import com.alpha.models.Turma;
import com.alpha.repositories.EscolaRepository;
import com.alpha.repositories.SerieRepository;
import com.alpha.repositories.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("escola/{id}/turmas")
public class TurmaEndpoint {

    private final TurmaRepository turmaDAO;
    private final EscolaRepository escolaDAO;
    private final SerieRepository serieDAO;

    @Autowired
    public TurmaEndpoint(TurmaRepository turmaDAO, EscolaRepository escolaDAO, SerieRepository serieDAO) {
        this.turmaDAO = turmaDAO;
        this.escolaDAO = escolaDAO;
        this.serieDAO = serieDAO;
    }

    @GetMapping
    public ResponseEntity<?> getEscolaById(@PathVariable("id") Long id){
        Escola escola = escolaDAO.findById(id).get();
        Iterable<Turma> turmas = turmaDAO.findByEscola(escola);
        return new ResponseEntity<>(turmas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saves(@PathVariable("id") Long id, @RequestBody Turma turma){
        Escola escola = escolaDAO.findById(id).get();
        turma.setEscola(escola);
        Serie serie = serieDAO.findById(turma.getSerie().getId()).get();
        turma.setSerie(serie);
        return new ResponseEntity<>(turmaDAO.save(turma), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Turma turma) {
        Escola escola = escolaDAO.findById(id).get();
        turma.setEscola(escola);
        turmaDAO.save(turma);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        turmaDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
