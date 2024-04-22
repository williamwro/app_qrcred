package br.com.qrcred;

import java.util.ArrayList;

public class Categorias{
    private String nome;
    private ArrayList<Convenios_categorias> conveniosList;

    public Categorias(String nome, ArrayList<Convenios_categorias> conveniosList) {
        super();
        this.nome = nome;
        this.conveniosList = conveniosList;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Convenios_categorias> getConvenios() {
        return conveniosList;
    }

    public void setConvenios(ArrayList<Convenios_categorias> conveniosList) {
        this.conveniosList = conveniosList;
    }
}
