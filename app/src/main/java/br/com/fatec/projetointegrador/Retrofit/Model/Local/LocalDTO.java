package br.com.fatec.projetointegrador.Retrofit.Model.Local;

import com.google.gson.annotations.SerializedName;

public class LocalDTO {

    @SerializedName("id")
    private Long id;

    @SerializedName("nome")
    public String nome;

    public LocalDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
