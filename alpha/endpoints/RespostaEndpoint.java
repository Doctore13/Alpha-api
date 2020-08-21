package com.alpha.endpoints;

import com.alpha.models.*;
import com.alpha.repositories.AlunoRepository;
import com.alpha.repositories.ProvaRepository;
import com.alpha.repositories.QuestaoRepository;
import com.alpha.repositories.RespostaRepository;
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
    private final QuestaoRepository questaoDAO;
    @Autowired
    private Disco disco;
    private String caminho;

    @Autowired
    public RespostaEndpoint(RespostaRepository respostaDAO, ProvaRepository provaDAO, AlunoRepository alunoDAO, QuestaoRepository questaoDAO) {
        this.respostaDAO = respostaDAO;
        this.provaDAO = provaDAO;
        this.alunoDAO = alunoDAO;
        this.questaoDAO = questaoDAO;
    }

    private String fileName = this.caminho;


    @GetMapping
    public ResponseEntity<?> getRespostas(@PathVariable("id") Long id, @PathVariable("idp") Long idp){

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

            Iterator<Row> rowIterator = sheetRespostas.iterator();

            rowIterator.next(); //pula a primeira linha(título da coluna)

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();



                while (cellIterator.hasNext()) {
                    for (int i = 0; i < 6; i++) {
                        DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
                        HSSFCell header_cell = header_row.getCell(i);
                        String header = header_cell.getStringCellValue();
                        Cell cell = cellIterator.next();
                        Resposta resposta = new Resposta();
                        resposta.setProva(prova);



                        switch (String.valueOf(header_cell)) {

                            case "ID":
                                aluno = alunoDAO.findById(Long.valueOf(formatter.formatCellValue(cell))).get();

                                break;

                            case "Q1":
                                resposta.setQuestao(1);
                                resposta.setAluno(aluno);
                                resposta.setNr(1);
                                resposta.setResposta(formatter.formatCellValue(cell));
                                listaRespostas.add(resposta);
                                break;

                            case "Q2":
                                resposta.setQuestao(2);
                                resposta.setAluno(aluno);
                                resposta.setNr(2);
                                resposta.setResposta(formatter.formatCellValue(cell));
                                listaRespostas.add(resposta);
                                break;

                            case "Q3":
                                resposta.setQuestao(3);
                                resposta.setAluno(aluno);
                                resposta.setNr(3);
                                resposta.setResposta(formatter.formatCellValue(cell));
                                listaRespostas.add(resposta);
                                break;

                            case "Q4":
                                resposta.setQuestao(4);
                                resposta.setAluno(aluno);
                                resposta.setNr(4);
                                resposta.setResposta(formatter.formatCellValue(cell));
                                listaRespostas.add(resposta);
                                break;

                            case "Q5":
                                resposta.setQuestao(5);
                                resposta.setAluno(aluno);
                                resposta.setNr(5);
                                resposta.setResposta(formatter.formatCellValue(cell));
                                listaRespostas.add(resposta);
                                break;

                            default:
                                System.out.println("ërro");


                        }

                    }
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
                System.out.println(resposta.getResposta());
                respostaDAO.save(resposta);
            }
            System.out.println(listaRespostas.size());
        }
    }
}
