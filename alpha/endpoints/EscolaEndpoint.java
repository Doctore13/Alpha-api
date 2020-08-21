package com.alpha.endpoints;

import com.alpha.models.Disco;
import com.alpha.models.Escola;
import com.alpha.repositories.EscolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("escolas")
public class EscolaEndpoint {

    private final EscolaRepository escolaDAO;
    public String log = null;

    @Autowired
    public EscolaEndpoint(EscolaRepository escolaDAO) {
        this.escolaDAO = escolaDAO;
    }

    @Autowired
    private Disco disco;

    @GetMapping
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(escolaDAO.findAll(), HttpStatus.OK);
    }

    @PostMapping(path = "/logo")
    public void upload(@RequestParam MultipartFile logo){
        this.log = logo.getOriginalFilename();
        disco.salvarLogo(logo);
        System.out.println(log);

    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Escola escola){
        return new ResponseEntity<>(escolaDAO.save(escola), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Escola escola) {
        escolaDAO.save(escola);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        escolaDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
