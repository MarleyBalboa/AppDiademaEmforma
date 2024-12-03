package br.com.fatec.projetointegrador.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;

import java.util.ArrayList;

import br.com.fatec.projetointegrador.Itens.DicasESugestoes;
import br.com.fatec.projetointegrador.R;

public class DicasESugestoesAdapter extends RecyclerView.Adapter<DicasESugestoesAdapter.ViewHolder> {

    ArrayList<DicasESugestoes> items;
    Context context;

    public DicasESugestoesAdapter(ArrayList<DicasESugestoes> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public DicasESugestoesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflator = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_dicas_list,parent,false);
        context = parent.getContext();
        return new ViewHolder(inflator);
    }

    @Override
    public void onBindViewHolder(@NonNull DicasESugestoesAdapter.ViewHolder holder, int position) {

        holder.title.setText(items.get(position).getTitle());
        holder.subtitle.setText(items.get(position).getSubtitle());

        int drawableResourceId = holder.itemView.getResources().getIdentifier(items.get(position).getPicAdress(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.pic);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, subtitle;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            subtitle = itemView.findViewById(R.id.subtitleTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
