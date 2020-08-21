package com.alpha.endpoints;

import com.alpha.models.Escola;
import com.alpha.models.Serie;
import com.alpha.repositories.EscolaRepository;
import com.alpha.repositories.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("escola/{id}/series")
public class SerieEndpoint {

    private final SerieRepository serieDAO;
    private final EscolaRepository escolaDAO;

    @Autowired
    public SerieEndpoint(SerieRepository serieDAO, EscolaRepository escolaDAO) {
        this.serieDAO = serieDAO;
        this.escolaDAO = escolaDAO;
    }

    @GetMapping
    public ResponseEntity<?> getEscolaById(@PathVariable("id") Long id){
        Escola escola = escolaDAO.findById(id).get();
        Iterable<Serie> series = serieDAO.findByEscola(escola);
        return new ResponseEntity<>(series, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saves(@PathVariable("id") Long id, @RequestBody Serie serie){
        Escola escola = escolaDAO.findById(id).get();
        serie.setEscola(escola);
        return new ResponseEntity<>(serieDAO.save(serie), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Serie serie) {
        Escola escola = escolaDAO.findById(id).get();
        serie.setEscola(escola);
        serieDAO.save(serie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        serieDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
