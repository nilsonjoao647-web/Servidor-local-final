package com.labanta.servidorlocal.dto;

public class ServicoResponseDTO {
    private String titulo;
    private double precoFinal;


    public ServicoResponseDTO(String titulo, double precoFinal) {
        this.titulo = titulo;
        this.precoFinal = precoFinal;
    }
    public ServicoResponseDTO(){}

    public String getTitulo() {return titulo;}
    public double getPrecoFinal() {return precoFinal;}

    public void setTitulo(String titulo) {this.titulo = titulo;}
    public void setPrecoFinal(double precoFinal) {this.precoFinal = precoFinal;}

}