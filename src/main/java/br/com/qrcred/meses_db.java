package br.com.qrcred;

public class meses_db {
    private String ABREVIACAO;
    private String DATA;
    private String COMPLETO;
    private String PERIODO;


    public meses_db(String ABREVIACAO, String DATA, String COMPLETO, String PERIODO){
        this.ABREVIACAO = ABREVIACAO;
        this.DATA = DATA;
        this.COMPLETO = COMPLETO;
        this.PERIODO = PERIODO;

    }

    public String getABREVIACAO() {
        return ABREVIACAO;
    }

    public void setABREVIACAO(String ABREVIACAO) {
        this.ABREVIACAO = ABREVIACAO;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getCOMPLETO() {
        return COMPLETO;
    }

    public void setCOMPLETO(String COMPLETO) {
        this.COMPLETO = COMPLETO;
    }

    public String getPERIODO() {
        return PERIODO;
    }

    public void setPERIODO(String PERIODO) {
        this.PERIODO = PERIODO;
    }
    @Override
    public String toString(){
        return ABREVIACAO;
    }
}
