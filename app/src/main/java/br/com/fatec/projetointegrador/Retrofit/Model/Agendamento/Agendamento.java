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
}