package br.com.fatec.projetointegrador;

public class Agendamento {
    private String data;
    private String horario;
    private String local;
    private String tipoAgendamento;
    private String profissional;

    public Agendamento(String data, String horario, String local, String tipoAgendamento, String profissional) {
        this.data = data;
        this.horario = horario;
        this.local = local;
        this.tipoAgendamento = tipoAgendamento;
        this.profissional = profissional;
    }

    public String getData() {
        return data;
    }

    public String getHorario() {
        return horario;
    }

    public String getLocal() {
        return local;
    }

    public String getTipoAgendamento() {
        return tipoAgendamento;
    }

    public String getProfissional() {
        return profissional;
    }
}
