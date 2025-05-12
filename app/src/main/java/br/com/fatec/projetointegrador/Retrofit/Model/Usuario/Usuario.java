package br.com.fatec.projetointegrador.Retrofit.Model.Usuario;

import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("id")
    private Long id;

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("email")
    private String email;

    @SerializedName("senha")
    private String senha;

    @SerializedName("telefone")
    private String telefone;

    @SerializedName("dataCriacao")
    private String dataCriacao;

    @SerializedName("dataAtualizacao")
    private String dataAtualizacao;

    @SerializedName("papel")
    private Papel papel;

    @SerializedName("especialidade")
    private Especialidade especialidade;

    public Usuario(String usuario, String email, String senha, Papel papel, Especialidade especialidade) {
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
        this.papel = papel;
        this.especialidade = especialidade;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
}
