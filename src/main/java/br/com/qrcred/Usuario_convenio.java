package br.com.qrcred;

public class Usuario_convenio {
    public String codigo, cpf, cnpj, email, data_criado, token_convenio,senha_convenio;

    public String getCodigo() {
        return codigo;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getEmail() {
        return email;
    }

    public String getData_criado() {
        return data_criado;
    }

    public String getToken_convenio() {
        return token_convenio;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setData_criado(String data_criado) {
        this.data_criado = data_criado;
    }

    public void setToken_convenio(String token_convenio) {
        this.token_convenio = token_convenio;
    }

    public Usuario_convenio(){
    }
    public Usuario_convenio(String codigo, String cpf, String cnpj, String email, String data_criado ,String token_convenio){
        this.codigo = codigo;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.email = email;
        this.data_criado = data_criado;
        this.token_convenio = token_convenio;
    }
}
