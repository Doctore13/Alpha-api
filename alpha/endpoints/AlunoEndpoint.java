package com.alpha.endpoints;

import com.alpha.models.Aluno;
import com.alpha.models.Escola;
import com.alpha.models.Serie;
import com.alpha.models.Turma;
import com.alpha.repositories.AlunoRepository;
import com.alpha.repositories.EscolaRepository;
import com.alpha.repositories.SerieRepository;
import com.alpha.repositories.TurmaRepository;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.alpha.models.Disco;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("escola/{id}/alunos")
public class AlunoEndpoint {

    private final AlunoRepository alunoDAO;
    private final EscolaRepository escolaDAO;
    private final SerieRepository serieDAO;
    private final TurmaRepository turmaDAO;
    public String exc = null;

    @Autowired
    private Disco disco;

    private String caminho;

    @Autowired
    public AlunoEndpoint(AlunoRepository alunoDAO, EscolaRepository escolaDAO, SerieRepository serieDAO, TurmaRepository turmaDAO) {
        this.alunoDAO = alunoDAO;
        this.escolaDAO = escolaDAO;
        this.serieDAO = serieDAO;
        this.turmaDAO = turmaDAO;
    }

    private String fileName = this.caminho;

    @PostMapping(path = "/import")
    public void importarAlunos(@PathVariable("id") Long id, @RequestBody Aluno alun) {
        List<Aluno> listaAlunos = new ArrayList<Aluno>();
        Escola esc = escolaDAO.findById(id).get();
        try {
            FileInputStream arquivo = new FileInputStream(new File(fileName));

            HSSFWorkbook workbook = new HSSFWorkbook(arquivo);
            HSSFSheet sheetAlunos = workbook.getSheetAt(0);
            HSSFRow header_row = sheetAlunos.getRow(0);

            Iterator<Row> rowIterator = sheetAlunos.iterator();

            rowIterator.next(); //pula a primeira linha(título da coluna)

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                Aluno aluno = new Aluno();
                aluno.setEscola(esc);
                aluno.setSerie(alun.getSerie());
                aluno.setTurma(alun.getTurma());
                listaAlunos.add(aluno);

                while (cellIterator.hasNext()) {
                    for (int i = 0; i < 3; i++) {
                        DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
                        HSSFCell header_cell = header_row.getCell(i);
                        String header = header_cell.getStringCellValue();
                        Cell cell = cellIterator.next();

                        if (header.equalsIgnoreCase("MATRICULA")) {
                            aluno.setMatricula(formatter.formatCellValue(cell));
                        } else {
                            if (header.equalsIgnoreCase("NOME")) {
                                aluno.setAluno(cell.getStringCellValue());
                            } else {
                                if (header.equalsIgnoreCase("SALA")) {
                                    aluno.setSala(formatter.formatCellValue(cell));
                                } else {
                                    System.out.println("ERRO!");
                                }
                            }
                        }
                    }
                }arquivo.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Arquivo não encontrado!");
        }

        if (listaAlunos.size() == 0) {
            System.out.println("Nenhum aluno encontrado!");
        } else {
            for (Aluno aluno : listaAlunos) {
                alunoDAO.save(aluno);
            }
        }
    }

    @PostMapping(path = "/uploadLista")
    public void upload(@PathVariable("id") Long id, @RequestParam MultipartFile alunos) {
        this.exc = alunos.getOriginalFilename();
        disco.salvarAlunos(alunos);
        this.fileName = disco.caminho;
        System.out.println(this.fileName);
    }

    @GetMapping
    public ResponseEntity<?> getEscolaById(@PathVariable("id") Long id) {
        Escola escola = escolaDAO.findById(id).get();
        Iterable<Aluno> alunos = alunoDAO.findByEscola(escola);
        return new ResponseEntity<>(alunos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saves(@PathVariable("id") Long id, @RequestBody Aluno aluno) {
        Escola escola = escolaDAO.findById(id).get();
        aluno.setEscola(escola);

        Serie serie = serieDAO.findById(aluno.getSerie().getId()).get();
        aluno.setSerie(serie);
        Turma turma = turmaDAO.findById(aluno.getTurma().getId()).get();
        aluno.setTurma(turma);
        return new ResponseEntity<>(alunoDAO.save(aluno), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        alunoDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Aluno aluno) {
        Escola escola = escolaDAO.findById(id).get();
        aluno.setEscola(escola);
        alunoDAO.save(aluno);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}