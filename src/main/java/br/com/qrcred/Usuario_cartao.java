package br.com.qrcred;

public class Usuario_cartao {

    public String cartao, cpf, email, data_criado, token;

    public String getCartao() {
        return cartao;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getData_criado() {
        return data_criado;
    }

    public String getToken() {
        return token;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setData_criado(String data_criado) {
        this.data_criado = data_criado;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario_cartao(){
    }

    public Usuario_cartao(String cartao, String cpf, String email, String data_criado,String token){
        this.cartao = cartao;
        this.cpf = cpf;
        this.email = email;
        this.data_criado = data_criado;
        this.token = token;
    }
}
