package br.com.fatec.projetointegrador;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.projetointegrador.Adapter.AgendamentoAdapter;
import br.com.fatec.projetointegrador.Adapter.AgendamentoManager;

public class TelaConsultaActivity extends AppCompatActivity {

    private List<Agendamento> agendamentos = new ArrayList<>();
    private AgendamentoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_consulta);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(view -> finish());

        RecyclerView recyclerView = findViewById(R.id.recyclerViewAgendamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Usa o campo da classe, não cria variável local!
        agendamentos = AgendamentoManager.getAgendamentos();

        adapter = new AgendamentoAdapter(agendamentos);
        recyclerView.setAdapter(adapter);

        // Configura o listener do botão delete
        adapter.setOnDeleteClickListener(position -> {
            Agendamento agendamentoParaExcluir = agendamentos.get(position);

            // Remover do gerenciador (banco/dados)
            AgendamentoManager.removerAgendamento(agendamentoParaExcluir);

            // Remover da lista local e atualizar RecyclerView
            agendamentos.remove(position);
            adapter.notifyItemRemoved(position);
        });
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Voltar para a tela anterior
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.laranjaClaro, null));
        }
    }
}
