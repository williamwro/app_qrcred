package br.com.qrcred;

public class Categorias_class {

    private String codigo;
    private String nome;

    public Categorias_class(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}