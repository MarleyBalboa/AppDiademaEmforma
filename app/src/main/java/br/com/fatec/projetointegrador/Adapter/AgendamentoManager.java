package br.com.fatec.projetointegrador.Adapter;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.projetointegrador.Agendamento;

public class AgendamentoManager {
    private static final List<Agendamento> agendamentos = new ArrayList<>();

    public static void adicionarAgendamento(Agendamento agendamento) {
        agendamentos.add(agendamento);
    }

    public static void removerAgendamento(Agendamento agendamento) {
        agendamentos.remove(agendamento);
    }

    public static List<Agendamento> getAgendamentos() {
        return new ArrayList<>(agendamentos);
    }
}