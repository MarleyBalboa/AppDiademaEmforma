package br.com.fatec.projetointegrador.Retrofit.Model.Agendamento;

import com.google.gson.annotations.SerializedName;

import br.com.fatec.projetointegrador.Retrofit.Model.Usuario.ProfissionalResumoComLocalDTO;
import br.com.fatec.projetointegrador.Retrofit.Model.Usuario.UsuarioResumoDTO;

public class AgendamentoResponseDTO {

    @SerializedName("id")
    private Long id;

    @SerializedName("data")
    private String data;

    @SerializedName("hora")
    private String hora;

    @SerializedName("descricao")
    private String descricao;

    @SerializedName("tipo")
    private String tipo;

    @SerializedName("status")
    private String status;

    @SerializedName("usuarioCliente")
    private UsuarioResumoDTO usuarioCliente;

    @SerializedName("profissionalResponsavel")
    private ProfissionalResumoComLocalDTO profissionalResponsavel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UsuarioResumoDTO getUsuarioCliente() {
        return usuarioCliente;
    }

    public void setUsuarioCliente(UsuarioResumoDTO usuarioCliente) {
        this.usuarioCliente = usuarioCliente;
    }

    public ProfissionalResumoComLocalDTO getProfissionalResponsavel() {
        return profissionalResponsavel;
    }

    public void setProfissionalResponsavel(ProfissionalResumoComLocalDTO profissionalResponsavel) {
        this.profissionalResponsavel = profissionalResponsavel;
    }
}
