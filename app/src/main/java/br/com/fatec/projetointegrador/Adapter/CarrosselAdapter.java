package br.com.fatec.projetointegrador.Adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.fatec.projetointegrador.R;

public class CarrosselAdapter extends RecyclerView.Adapter<CarrosselAdapter.CarrosselViewHolder> {

    private List<Integer> imagens;

    public CarrosselAdapter(List<Integer> imagens) {
        this.imagens = imagens;
    }

    @NonNull
    @Override
    public CarrosselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrossel, parent, false);
        return new CarrosselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrosselViewHolder holder, int position) {
        int imagemResId = imagens.get(position);
        holder.itemView.setBackgroundResource(imagemResId);
    }

    @Override
    public int getItemCount() {
        return imagens.size();
    }

    public class CarrosselViewHolder extends RecyclerView.ViewHolder {
        public CarrosselViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
