package br.com.fatec.projetointegrador.Retrofit.Model.Agendamento;

import com.google.gson.annotations.SerializedName;

public class AgendamentoRequestDTO {
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

    @SerializedName("usuarioClienteId")
    private Long usuarioClienteId;

    @SerializedName("profissionalResponsavelId")
    private Long profissionalResponsavelId;

    public AgendamentoRequestDTO(String data, String hora, String descricao, String tipo, String status, Long usuarioClienteId, Long profissionalResponsavelId) {
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
        this.tipo = tipo;
        this.status = status;
        this.usuarioClienteId = usuarioClienteId;
        this.profissionalResponsavelId = profissionalResponsavelId;
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

    public String getTipo() {
        return tipo;
    }

    public String getStatus() {
        return status;
    }

    public Long getUsuarioClienteId() {
        return usuarioClienteId;
    }

    public Long getProfissionalResponsavelId() {
        return profissionalResponsavelId;
    }
}
