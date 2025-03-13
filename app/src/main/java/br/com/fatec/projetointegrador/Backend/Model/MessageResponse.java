package br.com.fatec.projetointegrador.Backend.Model;

import com.google.gson.annotations.SerializedName;

public class MessageResponse {

    @SerializedName("mensagem")
    private String mensagem;

    @SerializedName("usuario")
    private Usuario usuario;

    public MessageResponse(String mensagem, Usuario usuario) {
        this.mensagem = mensagem;
        this.usuario = usuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
