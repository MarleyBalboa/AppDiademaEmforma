package br.com.fatec.projetointegrador.Retrofit.Model.Usuario;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ProfissionalResumoComLocalDTO {

    @SerializedName("profissionalId")
    private Long profissionalId;

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("especialidade")
    private String especialidade;

    @SerializedName("localNome")
    private String localNome;

    public Long getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getLocalNome() {
        return localNome;
    }

    public void setLocalNome(String localNome) {
        this.localNome = localNome;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProfissionalResumoComLocalDTO that = (ProfissionalResumoComLocalDTO) o;
        return Objects.equals(profissionalId, that.profissionalId) && Objects.equals(usuario, that.usuario) && Objects.equals(especialidade, that.especialidade) && Objects.equals(localNome, that.localNome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profissionalId, usuario, especialidade, localNome);
    }
}
