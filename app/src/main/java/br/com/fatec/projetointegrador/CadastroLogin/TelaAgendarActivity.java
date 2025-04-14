package br.com.fatec.projetointegrador.CadastroLogin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

        EditText dataAula = findViewById(R.id.data_aula);
        EditText horarioAula = findViewById(R.id.horario_aula);
        Spinner spinnerLocal = findViewById(R.id.spinner_local);
        Spinner spinnerTipoAgen = findViewById(R.id.spinner_tipoAgen);
        Spinner spinnerProf = findViewById(R.id.spinner_profissional);

        // Preenchendo Spinner de Locais
        String[] locaisSalvos = {"Sala A - Prédio 1", "Laboratório 3", "Auditório", "Sala 204", "Espaço Maker"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locaisSalvos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocal.setAdapter(adapter);

        // Preenchendo Spinner de Tipo de Agendamento
        String[] tiposAgendamento = {"Aula prática", "Mentoria", "Plantão de dúvidas"};
        ArrayAdapter<String> tipoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposAgendamento);
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoAgen.setAdapter(tipoAdapter);

        // Mapeando profissionais por tipo
        Map<String, String[]> profissionaisPorTipo = new HashMap<>();
        profissionaisPorTipo.put("Aula prática", new String[]{"Prof. Ana", "Prof. Carlos", "Prof. Júlia"});
        profissionaisPorTipo.put("Mentoria", new String[]{"Prof. Bruno", "Prof. Marina"});
        profissionaisPorTipo.put("Plantão de dúvidas", new String[]{"Prof. Roberto", "Prof. Helena"});

        // Adapter para Spinner de Profissionais
        ArrayAdapter<String> profAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProf.setAdapter(profAdapter);

        // Atualizar profissionais ao selecionar tipo de agendamento
        spinnerTipoAgen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                String tipoSelecionado = spinnerTipoAgen.getSelectedItem().toString();
                String[] profissionais = profissionaisPorTipo.get(tipoSelecionado);

                profAdapter.clear();
                if (profissionais != null) {
                    profAdapter.addAll(profissionais);
                }
                profAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Ao clicar no campo de data
        dataAula.setOnClickListener(v -> {
            final Calendar calendario = Calendar.getInstance();
            int ano = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    TelaAgendarActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        String dataFormatada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        dataAula.setText(dataFormatada);
                    },
                    ano, mes, dia
            );

            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        // Ao clicar no campo de horário
        horarioAula.setOnClickListener(v -> {
            final Calendar calendario = Calendar.getInstance();
            int hora = calendario.get(Calendar.HOUR_OF_DAY);
            int minuto = calendario.get(Calendar.MINUTE);

            android.app.TimePickerDialog timePickerDialog = new android.app.TimePickerDialog(
                    TelaAgendarActivity.this,
                    (view, selectedHour, selectedMinute) -> {
                        String horaFormatada = String.format("%02d:%02d", selectedHour, selectedMinute);
                        horarioAula.setText(horaFormatada);
                    },
                    hora, minuto, true
            );

            timePickerDialog.show();
        });

        // Botão Agendar
        btnAgendar.setOnClickListener(v -> {
            String data = dataAula.getText().toString();
            String horario = horarioAula.getText().toString();
            String local = spinnerLocal.getSelectedItem().toString();
            String tipoAgendamento = spinnerTipoAgen.getSelectedItem().toString();
            String profissional = spinnerProf.getSelectedItem().toString();

            Agendamento novoAgendamento = new Agendamento(data, horario, local, tipoAgendamento, profissional);
            AgendamentoManager.adicionarAgendamento(novoAgendamento);

            Intent intent = new Intent(TelaAgendarActivity.this, TelaConsultaActivity.class);
            startActivity(intent);
        });

        // Botão Consultar
        btnConsulta.setOnClickListener(v -> {
            String data = dataAula.getText().toString();
            String horario = horarioAula.getText().toString();
            String local = spinnerLocal.getSelectedItem().toString();
            String tipoAgendamento = spinnerTipoAgen.getSelectedItem().toString();
            String profissional = spinnerProf.getSelectedItem().toString();

            Intent intent = new Intent(TelaAgendarActivity.this, TelaConsultaActivity.class);
            intent.putExtra("data", data);
            intent.putExtra("horario", horario);
            intent.putExtra("local", local);
            intent.putExtra("tipoAgendamento", tipoAgendamento);
            intent.putExtra("profissional", profissional);
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
