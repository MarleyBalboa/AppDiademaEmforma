package br.com.fatec.projetointegrador.Retrofit.Model.Usuario;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ProfissionalResumoDTO {

    @SerializedName("profissionalId")
    private Long profissionalId;

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("especialidade")
    private String especialidade;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProfissionalResumoDTO that = (ProfissionalResumoDTO) o;
        return Objects.equals(profissionalId, that.profissionalId) && Objects.equals(usuario, that.usuario) && Objects.equals(especialidade, that.especialidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profissionalId, usuario, especialidade);
    }
}
