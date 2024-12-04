package br.com.fatec.projetointegrador;

public class Agendamento {
    private String data;
    private String horario;
    private String local;

    public Agendamento(String data, String horario, String local) {
        this.data = data;
        this.horario = horario;
        this.local = local;
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
}
