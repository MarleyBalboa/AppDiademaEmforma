package br.com.fatec.projetointegrador.Retrofit.Model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

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
    private LocalDateTime dataCriacao;

    @SerializedName("dataAtualizacao")
    private LocalDateTime dataAtualizacao;

    @SerializedName("papel")
    private Papel Papel;

    @SerializedName("especialidade")
    private Especialidade Especialidade;

    public enum Papel {
        ADMINISTRADOR("administrador"),
        USUARIO_COMUM("usuario_comum"),
        USUARIO_PROFISSIONAL("usuario_profissional");

        private final String papel;

        Papel(String papel) {
            this.papel = papel;
        }

        public String getPapel() {
            return papel;
        }
    }

    public enum Especialidade {
        NUTRICIONISTA,
        PERSONAL_TRAINER,
        FISIOTERAPEUTA,
        PSICOLOGO,
        OUTROS
    }

    public Usuario(Long id, String usuario, String email, String senha, String telefone, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao, Papel papel, Especialidade especialidade) {
        this.id = id;
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        Papel = papel;
        Especialidade = especialidade;
    }

    public Usuario(String usuario, String email, String senha) {
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Papel getPapel() {
        return Papel;
    }

    public void setPapel(Papel papel) {
        Papel = papel;
    }

    public Especialidade getEspecialidade() {
        return Especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        Especialidade = especialidade;
    }
}
