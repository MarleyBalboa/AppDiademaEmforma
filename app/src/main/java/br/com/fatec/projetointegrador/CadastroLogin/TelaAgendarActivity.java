package br.com.fatec.projetointegrador.CadastroLogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import br.com.fatec.projetointegrador.Adapter.AgendamentoManager;
import br.com.fatec.projetointegrador.Agendamento;
import br.com.fatec.projetointegrador.R;
import br.com.fatec.projetointegrador.TelaConsultaActivity;

public class TelaAgendarActivity extends AppCompatActivity {

    private Button btnAgendar;
    private TextView btnConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_agendar);

        // Habilitar seta no ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Botão Voltar
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        // Inicializando os botões
        btnAgendar = findViewById(R.id.btnAgendar);
        btnConsulta = findViewById(R.id.btnConsulta);

        // Configurando o clique no botão "Agendar"
        btnAgendar.setOnClickListener(v -> {
            EditText dataAula = findViewById(R.id.data_aula);
            EditText horarioAula = findViewById(R.id.horario_aula);
            EditText localAula = findViewById(R.id.local_aula);

            String data = dataAula.getText().toString();
            String horario = horarioAula.getText().toString();
            String local = localAula.getText().toString();

            Intent intent = new Intent(TelaAgendarActivity.this, TelaConsultaActivity.class);
            intent.putExtra("data", data);
            intent.putExtra("horario", horario);
            intent.putExtra("local", local);
            startActivity(intent);
        });

        // Configurando o clique no botão "Consultar"
        btnConsulta.setOnClickListener(v -> {
            EditText dataAula = findViewById(R.id.data_aula);
            EditText horarioAula = findViewById(R.id.horario_aula);
            EditText localAula = findViewById(R.id.local_aula);

            String data = dataAula.getText().toString();
            String horario = horarioAula.getText().toString();
            String local = localAula.getText().toString();

            Intent intent = new Intent(TelaAgendarActivity.this, TelaConsultaActivity.class);
            intent.putExtra("data", data);
            intent.putExtra("horario", horario);
            intent.putExtra("local", local);
            startActivity(intent);
        });

        btnAgendar.setOnClickListener(v -> {
            EditText dataAula = findViewById(R.id.data_aula);
            EditText horarioAula = findViewById(R.id.horario_aula);
            EditText localAula = findViewById(R.id.local_aula);

            String data = dataAula.getText().toString();
            String horario = horarioAula.getText().toString();
            String local = localAula.getText().toString();

            // Adicionar o agendamento à lista global
            Agendamento novoAgendamento = new Agendamento(data, horario, local);
            AgendamentoManager.adicionarAgendamento(novoAgendamento);

            // Abrir a TelaConsultaActivity
            Intent intent = new Intent(TelaAgendarActivity.this, TelaConsultaActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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

