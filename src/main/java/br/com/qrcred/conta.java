package br.com.qrcred;

import java.io.Serializable;

public class conta implements Serializable {
    private String Associado;
    private String Nome;
    private String RazaoSocial;
    private String NomeFantasia;
    private String Valor;
    private String Mes;
    private String parcela;
    private String Dia;
    private String hora;
    private String uri_cupom;
    private String cnpj;
    public conta(String Associado, String Nome, String RazaoSocial, String Valor,
                    String Mes, String parcela, String   Dia, String hora, String uri_cupom,String Nomefantasia,String cnpj){
        this.Associado = Associado;
        this.Nome = Nome;
        this.RazaoSocial = RazaoSocial;
        this.NomeFantasia = Nomefantasia;
        this.Valor = Valor;
        this.Mes = Mes;
        this.parcela = parcela;
        this.Dia = Dia;
        this.hora = hora;
        this.uri_cupom = uri_cupom;
        this.cnpj = cnpj;
    }

    public String getAssociado() {
        return Associado;
    }

    public void setAssociado(String associado) {
        Associado = associado;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getRazaoSocial() {
        return RazaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        RazaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return NomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        NomeFantasia = nomeFantasia;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }

    public String getMes() {
        return Mes;
    }

    public void setMes(String mes) {
        Mes = mes;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getDia() {
        return Dia;
    }

    public void setDia(String dia) {
        Dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getUri_cupom() {
        return uri_cupom;
    }

    public void setUri_cupom(String uri_cupom) {
        this.uri_cupom = uri_cupom;
    }
    @Override
    public String toString(){
        return uri_cupom;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

}
