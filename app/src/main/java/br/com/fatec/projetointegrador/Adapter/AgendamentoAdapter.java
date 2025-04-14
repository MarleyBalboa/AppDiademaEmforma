package br.com.fatec.projetointegrador.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.fatec.projetointegrador.Agendamento;
import br.com.fatec.projetointegrador.R;

public class AgendamentoAdapter extends RecyclerView.Adapter<AgendamentoAdapter.AgendamentoViewHolder> {

    private List<Agendamento> agendamentos;

    public AgendamentoAdapter(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    @NonNull
    @Override
    public AgendamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_agendamento, parent, false);
        return new AgendamentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendamentoViewHolder holder, int position) {
        Agendamento agendamento = agendamentos.get(position);
        holder.textData.setText("Data: " + agendamento.getData());
        holder.textHorario.setText("Horário: " + agendamento.getHorario());
        holder.textLocal.setText("Local: " + agendamento.getLocal());
        holder.textTipoAgen.setText("Tipo de agendamento: " + agendamento.getTipoAgendamento());
        holder.textProf.setText("Profissional: " + agendamento.getProfissional());
    }

    @Override
    public int getItemCount() {
        return agendamentos.size();
    }

    static class AgendamentoViewHolder extends RecyclerView.ViewHolder {
        TextView textData, textHorario, textLocal, textTipoAgen, textProf;

        public AgendamentoViewHolder(@NonNull View itemView) {
            super(itemView);
            textData = itemView.findViewById(R.id.textData);
            textHorario = itemView.findViewById(R.id.textHorario);
            textLocal = itemView.findViewById(R.id.textLocal);
            textTipoAgen = itemView.findViewById(R.id.textTipoAgen);
            textProf = itemView.findViewById(R.id.textProf);
        }
    }
}
