package br.com.fatec.projetointegrador.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import br.com.fatec.projetointegrador.Itens.ServicoItem;
import br.com.fatec.projetointegrador.R;

public class ServicosAdapter extends RecyclerView.Adapter<ServicosAdapter.ViewHolder> {

    private final Context context;
    private final List<ServicoItem> originalList;
    private List<ServicoItem> filteredList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ServicoItem item);
    }

    public ServicosAdapter(Context context, List<ServicoItem> servicos, OnItemClickListener listener) {
        this.context = context;
        this.originalList = new ArrayList<>(servicos);
        this.filteredList = new ArrayList<>(servicos);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_servico, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServicoItem item = filteredList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String query) {
        String normalizedQuery = Normalizer.normalize(query.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");

        filteredList = new ArrayList<>();
        for (ServicoItem item : originalList) {
            boolean match = Normalizer.normalize(item.getTitle().toLowerCase(), Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "").contains(normalizedQuery);

            for (String keyword : item.getKeywords()) {
                String normalizedKeyword = Normalizer.normalize(keyword.toLowerCase(), Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "");
                if (normalizedKeyword.contains(normalizedQuery) || normalizedQuery.contains(normalizedKeyword)) {
                    match = true;
                    break;
                }
            }

            if (match) {
                filteredList.add(item);
            }
        }

        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final ImageView icon;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            icon = itemView.findViewById(R.id.ivIcon);
        }

        void bind(final ServicoItem item) {
            title.setText(item.getTitle());
            icon.setImageResource(item.getImageResId());

            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
