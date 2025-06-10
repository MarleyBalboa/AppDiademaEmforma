package br.com.fatec.projetointegrador.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.Agendamento;
import br.com.fatec.projetointegrador.R;

public class AgendamentoAdapter extends RecyclerView.Adapter<AgendamentoAdapter.AgendamentoViewHolder> {
    private List<Agendamento> agendamentos;

    public interface OnAgendamentoLongClickListener {
        void onLongClick(Agendamento agendamento);
    }

    private OnAgendamentoLongClickListener longClickListener;

    public void setOnAgendamentoLongClickListener(OnAgendamentoLongClickListener listener) {
        this.longClickListener = listener;
    }

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
        Agendamento a = agendamentos.get(position);
        holder.textData.setText("Data: " + a.getData());
        holder.textHorario.setText("Horário: " + a.getHora());
        holder.textTipoAgen.setText("Tipo: " + a.getTipo().name());
        holder.textLocal.setText("Local: " + a.getProfissionalResponsavel().getLocalNome());
        holder.textProf.setText("Profissional: " + a.getProfissionalResponsavel().getUsuario());

        holder.itemView.setOnLongClickListener(view -> {
            if (longClickListener != null) {
                longClickListener.onLongClick(a);
            }
            return true;
        });
    }

    public void removeById(Long id) {
        int idx = -1;
        for (int i = 0; i < agendamentos.size(); i++) {
            if (agendamentos.get(i).getId().equals(id)) {
                idx = i;
                break;
            }
        }
        if (idx != -1) {
            agendamentos.remove(idx);
            notifyItemRemoved(idx);
        }
    }

    @Override
    public int getItemCount() {
        return agendamentos.size();
    }

    static class AgendamentoViewHolder extends RecyclerView.ViewHolder {
        TextView textData, textHorario, textLocal, textTipoAgen, textProf;

        AgendamentoViewHolder(@NonNull View itemView) {
            super(itemView);
            textData     = itemView.findViewById(R.id.textData);
            textHorario  = itemView.findViewById(R.id.textHorario);
            textTipoAgen = itemView.findViewById(R.id.textTipoAgen);
            textLocal    = itemView.findViewById(R.id.textLocal);
            textProf     = itemView.findViewById(R.id.textProf);
        }
    }
}
