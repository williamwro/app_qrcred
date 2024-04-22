package br.com.qrcred;

import java.util.List;

public class convenios_categorias_response {

    private final boolean error;
    private final List<Convenios_categorias> convenios;

    public convenios_categorias_response(boolean error, List<Convenios_categorias> convenios) {
        this.error = error;
        this.convenios = convenios;
    }

    public boolean isError() {
        return error;
    }

    public List<Convenios_categorias> getConvenios() {
        return convenios;
    }
}
