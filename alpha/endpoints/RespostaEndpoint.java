package com.alpha.endpoints;

import com.alpha.models.*;
import com.alpha.repositories.*;
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

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("escola/{id}/prova/{idp}/respostas")
public class RespostaEndpoint {
    public String log = null;
    private final RespostaRepository respostaDAO;
    private final ProvaRepository provaDAO;
    private final AlunoRepository alunoDAO;

    @Autowired
    private Disco disco;
    private String caminho;

    @Autowired
    public RespostaEndpoint(RespostaRepository respostaDAO, ProvaRepository provaDAO, AlunoRepository alunoDAO, QuestaoRepository questaoDAO, DisciplinaRepository disciplinaDAO, EscolaRepository escolaDAO, NotaDisciplinaRepository notaDisciplinaDAO, AlunoProvaRepository alunoProvaDAO) {
        this.respostaDAO = respostaDAO;
        this.provaDAO = provaDAO;
        this.alunoDAO = alunoDAO;
    }

    private String fileName = this.caminho;

    @GetMapping
    public ResponseEntity<?> getRespostas(@PathVariable("id") Long id, @PathVariable("idp") Long idp) {
        Prova prova = provaDAO.findById(idp).get();
        Iterable<Resposta> respostas = respostaDAO.findByProva(prova);
        return new ResponseEntity<>(respostas, HttpStatus.OK);
    }

    @PostMapping(path = "/upload")
    public void upload(@RequestParam MultipartFile respostas) {
        this.log = respostas.getOriginalFilename();
        disco.salvarRespostas(respostas);
        this.fileName = disco.caminho;
        System.out.println(this.fileName);
    }

    @PostMapping(path = "/import")
    public void importarRespostas(@PathVariable("id") Long id, @PathVariable("idp") Long idp) {
        List<Resposta> listaRespostas = new ArrayList<Resposta>();
        Prova prova = provaDAO.findById(idp).get();
        Aluno aluno = new Aluno();
        try {
            FileInputStream arquivo = new FileInputStream(new File(fileName));
            HSSFWorkbook workbook = new HSSFWorkbook(arquivo);
            HSSFSheet sheetRespostas = workbook.getSheetAt(0);
            HSSFRow header_row = sheetRespostas.getRow(0);
            int linhas = sheetRespostas.getPhysicalNumberOfRows();
            int colunas = sheetRespostas.getRow(0).getPhysicalNumberOfCells();
            System.out.println(colunas + " colunas");
            System.out.println(linhas + " linhas");
            Iterator<Row> rowIterator = sheetRespostas.iterator();
            rowIterator.next(); //pula a primeira linha(título da coluna)

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                int i = 0;
                while (cellIterator.hasNext()) {
                    DataFormatter formatter = new DataFormatter();
                    HSSFCell header_cell = header_row.getCell(i);
                    String header = header_cell.getStringCellValue();
                    Cell cell = cellIterator.next();
                    Resposta resposta = new Resposta();
                    resposta.setProva(prova);
                    resposta.setNota((float) 0);

                    System.out.print(header + ": " + cell + " | ");

                    for (int n = 1; n < colunas; n++) {
                        String q = "Q" + n;
                        if (header.equalsIgnoreCase("ID")) {
                            aluno = alunoDAO.findById(Long.valueOf(formatter.formatCellValue(cell))).get();
                        }
                        if (header.equalsIgnoreCase(q)) {
                            resposta.setAluno(aluno);
                            resposta.setNr(n);
                            resposta.setResposta(formatter.formatCellValue(cell));
                            listaRespostas.add(resposta);
                        }
                    }
                    i++;
                }
            }
            arquivo.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Arquivo não encontrado!");
        }

        if (listaRespostas.size() == 0) {
            System.out.println("Nenhuma resposta encontrada!");
        } else {
            for (Resposta resposta : listaRespostas) {
                respostaDAO.save(resposta);
            }
            System.out.println(listaRespostas.size());
        }
    }
}
