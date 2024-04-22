package br.com.qrcred;

public class Conta_app2 {

    private String codcarteira;
    private String codconvenio;
    private String datacad;
    private String hora;
    private String matricula;
    private String mes_seq;
    private String nome;
    private String numeroparcelas;
    private String razaosocial;
    private String registrolan;
    private String userconv;
    private String valor_pedido;
    public Conta_app2(){

    }
    public Conta_app2(String codcarteira, String codconvenio, String datacad, String hora, String matricula, String mes_seq, String nome, String numeroparcelas, String razaosocial, String registrolan, String userconv, String valor_pedido) {
        this.codcarteira = codcarteira;
        this.codconvenio = codconvenio;
        this.datacad = datacad;
        this.hora = hora;
        this.matricula = matricula;
        this.mes_seq = mes_seq;
        this.nome = nome;
        this.numeroparcelas = numeroparcelas;
        this.razaosocial = razaosocial;
        this.registrolan = registrolan;
        this.userconv = userconv;
        this.valor_pedido = valor_pedido;
    }


    public String getcodcarteira() {
        return codcarteira;
    }

    public void setcodcarteira(String codcarteira) {
        this.codcarteira = codcarteira;
    }

    public String getCodconvenio() {
        return codconvenio;
    }

    public void setcodconvenio(String codconvenio) {
        this.codconvenio = codconvenio;
    }

    public String getDatacad() {
        return datacad;
    }

    public void setdatacad(String datacad) {
        this.datacad = datacad;
    }

    public String getHora() {
        return hora;
    }

    public void sethora(String hora) {
        this.hora = hora;
    }

    public String getmatricula() {
        return matricula;
    }

    public void setmatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getmes_seq() { return mes_seq; }

    public void setmes_seq(String mes_seq) {
        this.mes_seq = mes_seq;
    }

    public String getnome() {
        return nome;
    }

    public void setnome(String nome) {
        this.nome = nome;
    }

    public String getnumeroparcelas() {
        return numeroparcelas;
    }

    public void setnumeroparcelas(String numeroparcelas) {
        this.numeroparcelas = numeroparcelas;
    }

    public String getrazaosocial() {
        return razaosocial;
    }

    public void setrazaosocial(String razaosocial) {
        this.razaosocial = razaosocial;
    }

    public String getregistrolan() {
        return registrolan;
    }

    public void setregistrolan(String registrolan) {
        this.registrolan = registrolan;
    }

    public String getuserconv() {
        return userconv;
    }

    public void setuserconv(String userconv) {
        this.userconv = userconv;
    }

    public String getvalor_pedido() {
        return valor_pedido;
    }

    public void setvalor_pedido(String valor_pedido) {
        this.valor_pedido = valor_pedido;
    }

}
