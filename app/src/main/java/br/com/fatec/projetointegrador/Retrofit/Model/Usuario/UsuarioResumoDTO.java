package br.com.fatec.projetointegrador.Retrofit.Model.Usuario;

import com.google.gson.annotations.SerializedName;

public class UsuarioResumoDTO {
    @SerializedName("id")
    private Long id;

    @SerializedName("user")
    private String usuario;

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
}
