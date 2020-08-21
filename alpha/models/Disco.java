package com.alpha.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class Disco {

    public String caminho;

    @Value("${escola.disco.raiz}")
    private String raiz;

    @Value("${escola.disco.diretorio-logos}")
    private String diretorioLogos;

    public void salvarLogo(MultipartFile logo) {
        this.salvar(this.diretorioLogos, logo);
    }


    @Value("${escola.disco.diretorio-alunos}")
    private String diretorioAlunos;




    public void salvarAlunos(MultipartFile alunos) {

        this.salvar(this.diretorioAlunos, alunos);
    }

    @Value("${escola.disco.diretorio-respostas}")
    private String diretorioRespostas;


    public void salvarRespostas(MultipartFile respostas) {

        this.salvarRespostas(this.diretorioRespostas, respostas);
    }

    public void salvar(String diretorio, MultipartFile arquivo) {
        Path diretorioPath = Paths.get(this.raiz, diretorio);
        Path arquivoPath = diretorioPath.resolve(arquivo.getOriginalFilename());

        try {
            Files.createDirectories(diretorioPath);
            arquivo.transferTo(arquivoPath.toFile());
            this.caminho = raiz + diretorioAlunos + "/" + arquivo.getOriginalFilename();


        } catch (IOException e) {
            throw new RuntimeException("Problemas na tentativa de salvar arquivo.", e);
        }
    }

    public void salvarRespostas(String diretorio, MultipartFile arquivo) {
        Path diretorioPath = Paths.get(this.raiz, diretorio);
        Path arquivoPath = diretorioPath.resolve(arquivo.getOriginalFilename());

        try {
            Files.createDirectories(diretorioPath);
            arquivo.transferTo(arquivoPath.toFile());
            this.caminho = raiz + diretorioRespostas + "/" + arquivo.getOriginalFilename();


        } catch (IOException e) {
            throw new RuntimeException("Problemas na tentativa de salvar arquivo.", e);
        }
    }
}