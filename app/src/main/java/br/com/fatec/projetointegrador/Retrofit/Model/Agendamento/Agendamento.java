package br.com.fatec.projetointegrador.Retrofit.Model.Agendamento;

import com.google.gson.annotations.SerializedName;

import br.com.fatec.projetointegrador.Retrofit.Model.Usuario.ProfissionalResumoComLocalDTO;
import br.com.fatec.projetointegrador.Retrofit.Model.Usuario.UsuarioResumoDTO;

public class Agendamento {

    @SerializedName("id")
    private Long id;

    @SerializedName("data")
    private String data;

    @SerializedName("hora")
    private String hora;

    @SerializedName("descricao")
    private String descricao;

    @SerializedName("tipo")
    private TipoAgendamento tipo;

    @SerializedName("status")
    private StatusAgendamento status;

    @SerializedName("usuarioCliente")
    private UsuarioResumoDTO usuarioCliente;

    @SerializedName("profissionalResponsavel")
    private ProfissionalResumoComLocalDTO profissionalResponsavel;

    @SerializedName("dataCriacao")
    private String dataCriacao;

    @SerializedName("dataAtualizacao")
    private String dataAtualizacao;

    public Agendamento(Long id, String data, String hora, String descricao, TipoAgendamento tipo, StatusAgendamento status, UsuarioResumoDTO usuarioCliente, ProfissionalResumoComLocalDTO profissionalResponsavel, String dataCriacao, String dataAtualizacao) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
        this.tipo = tipo;
        this.status = status;
        this.usuarioCliente = usuarioCliente;
        this.profissionalResponsavel = profissionalResponsavel;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    public Long getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoAgendamento getTipo() {
        return tipo;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public UsuarioResumoDTO getUsuarioCliente() {
        return usuarioCliente;
    }

    public ProfissionalResumoComLocalDTO getProfissionalResponsavel() {
        return profissionalResponsavel;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public String getDataAtualizacao() {
        return dataAtualizacao;
    }
}