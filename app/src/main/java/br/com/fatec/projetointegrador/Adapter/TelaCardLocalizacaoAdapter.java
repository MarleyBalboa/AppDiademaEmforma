package br.com.fatec.projetointegrador.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.projetointegrador.R;
import br.com.fatec.projetointegrador.Itens.TelaCardLocalizacao;

public class TelaCardLocalizacaoAdapter extends RecyclerView.Adapter<TelaCardLocalizacaoAdapter.TelaCardLocalizacaoHolder> {

    private List<TelaCardLocalizacao> TelaCardLocalizacoes;

    public TelaCardLocalizacaoAdapter(List<TelaCardLocalizacao> telaCardLocalizacoes) {
        if (telaCardLocalizacoes != null) {
            TelaCardLocalizacoes = telaCardLocalizacoes;
        } else {
            TelaCardLocalizacoes = new ArrayList<>(); // Se a lista for nula, inicializa como uma lista vazia
        }
    }

    @NonNull
    @Override
    public TelaCardLocalizacaoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_location, parent, false);
        return new TelaCardLocalizacaoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TelaCardLocalizacaoHolder holder, int position) {
        holder.setLocationData(TelaCardLocalizacoes.get(position));
    }

    @Override
    public int getItemCount() {
        return TelaCardLocalizacoes.size();  // A lista nunca será nula aqui
    }

    static class TelaCardLocalizacaoHolder extends RecyclerView.ViewHolder {

        private ImageView imageLocations;
        private TextView textTitleLocation, textLocation, textStarRating;

        TelaCardLocalizacaoHolder(@NonNull View itemView) {
            super(itemView);
            imageLocations = itemView.findViewById(R.id.imageLocations);  // Troquei para ImageView
            textTitleLocation = itemView.findViewById(R.id.textTitleLocation);
            textLocation = itemView.findViewById(R.id.textLocation);
            textStarRating = itemView.findViewById(R.id.textStarRating);
        }

        void setLocationData(TelaCardLocalizacao telaCardLocalizacao) {
            // Verifique se a URL da imagem é válida
            if (telaCardLocalizacao != null && telaCardLocalizacao.imageUrl != null && !telaCardLocalizacao.imageUrl.isEmpty()) {
                Picasso.get()
                        .load(telaCardLocalizacao.imageUrl)
                        .into(imageLocations);
            }
            textTitleLocation.setText(telaCardLocalizacao.title);
            textLocation.setText(telaCardLocalizacao.location);
            textStarRating.setText(String.valueOf(telaCardLocalizacao.starRating));
        }
    }
}
