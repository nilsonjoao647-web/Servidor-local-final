package com.labanta.servidorlocal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ServicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private Double preco;
    private Boolean estaAtivo;
    private Double precoComDesconto;
    private String imagemCapa;

    public ServicoModel() {}

    public  ServicoModel(String titulo, String descricao, double preco, Boolean estaAtivo, double precoComDesconto, String imagemCapa) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
        this.estaAtivo = estaAtivo;
        this.precoComDesconto = precoComDesconto;
        this.imagemCapa = imagemCapa;
    }

    public String getTitulo() {return this.titulo;}
    public String getDescricao() {return this.descricao;}
    public double getPreco() {return this.preco;}
    public boolean getEstaAtivo() {return this.estaAtivo;}
    public Double getPrecoComDesconto() {return precoComDesconto;}
    public String getImagemCapa() {return imagemCapa;}


    public void setTitulo(String titulo) {this.titulo = titulo;}
    public void setDescricao(String descricao) {this.descricao = descricao;}
    public void setPreco(double preco) {this.preco = preco;}
    public void setEstaAtivo(boolean estaAtivo) {this.estaAtivo = estaAtivo;}
    public void setPrecoComDesconto(Double precoComDesconto) {this.precoComDesconto = precoComDesconto;}
    public void setImagemCapa(String imagemCap) {this.imagemCapa = imagemCapa;}

}
