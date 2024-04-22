package br.com.qrcred;


public class Convenios_categorias {
    private int CODIGO;
    private String NOMEFANTASIA;
    private String ENDERECO;
    private String NUMERO;
    private String BAIRRO;
    private String TELEFONE;
    private String CELULAR;
    private String LATITUDE;
    private String LONGITUDE;

    public Convenios_categorias(String NOMEFANTASIA, String ENDERECO, String BAIRRO, String TELEFONE, String CELULAR, int CODIGO, String LATITUDE, String LONGITUDE,String NUMERO) {
        this.NOMEFANTASIA = NOMEFANTASIA;
        this.ENDERECO     = ENDERECO;
        this.BAIRRO       = BAIRRO;
        this.CODIGO       = CODIGO;
        this.TELEFONE     = TELEFONE;
        this.CELULAR      = CELULAR;
        this.LATITUDE     = LATITUDE;
        this.LONGITUDE    = LONGITUDE;
        this.NUMERO       = NUMERO;
    }

    public String getTELEFONE() {
        return TELEFONE;
    }

    public void setTELEFONE(String TELEFONE) {
        this.TELEFONE = TELEFONE;
    }

    public String getCELULAR() {
        return CELULAR;
    }

    public void setCELULAR(String CELULAR) {
        this.CELULAR = CELULAR;
    }

    public String getNOMEFANTASIA() {
        return NOMEFANTASIA;
    }

    public void setNOMEFANTASIA(String NOMEFANTASIA) {
        this.NOMEFANTASIA = NOMEFANTASIA;
    }

    public String getENDERECO() {
        return ENDERECO;
    }

    public void setENDERECO(String ENDERECO) {
        this.ENDERECO = ENDERECO;
    }

    public String getNUMERO() {
        return NUMERO;
    }

    public void setNUMERO(String NUMERO) {
        this.NUMERO = NUMERO;
    }

    public String getBAIRRO() {
        return BAIRRO;
    }

    public void setBAIRRO(String BAIRRO) {
        this.BAIRRO = BAIRRO;
    }

    public int getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(int CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() { return LONGITUDE; }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }
}
