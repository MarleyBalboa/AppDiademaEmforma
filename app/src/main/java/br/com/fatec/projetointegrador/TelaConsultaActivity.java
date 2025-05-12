package br.com.fatec.projetointegrador;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.fatec.projetointegrador.Adapter.AgendamentoAdapter;
import br.com.fatec.projetointegrador.Adapter.AgendamentoManager;
import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.Agendamento;

public class TelaConsultaActivity extends AppCompatActivity {

    private List<Agendamento> agendamentos = new ArrayList<>();
    private AgendamentoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_consulta);

        // Habilitar seta no ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        // Configurar o RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAgendamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Recuperar os agendamentos acumulados
        List<Agendamento> agendamentos = AgendamentoManager.getAgendamentos();
        AgendamentoAdapter adapter = new AgendamentoAdapter(agendamentos);
        recyclerView.setAdapter(adapter);

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
}
