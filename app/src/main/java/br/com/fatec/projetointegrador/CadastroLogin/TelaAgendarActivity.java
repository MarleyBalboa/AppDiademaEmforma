package br.com.fatec.projetointegrador.CadastroLogin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.Toast;

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(view -> finish());

        btnAgendar = findViewById(R.id.btnAgendar);
        btnConsulta = findViewById(R.id.btnConsulta);

        EditText dataAula = findViewById(R.id.data_aula);
        EditText horarioAula = findViewById(R.id.horario_aula);
        Spinner spinnerLocal = findViewById(R.id.spinner_local);
        Spinner spinnerTipoAgen = findViewById(R.id.spinner_tipoAgen);
        Spinner spinnerProf = findViewById(R.id.spinner_profissional);

        String[] locaisSalvos = {"Sala A - Prédio 1", "Laboratório 3", "Auditório", "Sala 204", "Espaço Maker"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locaisSalvos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocal.setAdapter(adapter);

        String[] tiposAgendamento = {"Aula prática", "Mentoria", "Plantão de dúvidas"};
        ArrayAdapter<String> tipoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposAgendamento);
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoAgen.setAdapter(tipoAdapter);

        Map<String, String[]> profissionaisPorTipo = new HashMap<>();
        profissionaisPorTipo.put("Aula prática", new String[]{"Prof. Ana", "Prof. Carlos", "Prof. Júlia"});
        profissionaisPorTipo.put("Mentoria", new String[]{"Prof. Bruno", "Prof. Marina"});
        profissionaisPorTipo.put("Plantão de dúvidas", new String[]{"Prof. Roberto", "Prof. Helena"});

        ArrayAdapter<String> profAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProf.setAdapter(profAdapter);

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

        btnAgendar.setOnClickListener(v -> {
            String data = dataAula.getText().toString().trim();
            String horario = horarioAula.getText().toString().trim();
            String local = spinnerLocal.getSelectedItem() != null ? spinnerLocal.getSelectedItem().toString() : "";
            String tipoAgendamento = spinnerTipoAgen.getSelectedItem() != null ? spinnerTipoAgen.getSelectedItem().toString() : "";
            String profissional = spinnerProf.getSelectedItem() != null ? spinnerProf.getSelectedItem().toString() : "";

            // Validação dos campos obrigatórios
            if (data.isEmpty()) {
                Toast.makeText(this, "Por favor, selecione a data.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (horario.isEmpty()) {
                Toast.makeText(this, "Por favor, selecione o horário.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (local.isEmpty()) {
                Toast.makeText(this, "Por favor, selecione o local.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (tipoAgendamento.isEmpty()) {
                Toast.makeText(this, "Por favor, selecione o tipo de agendamento.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (profissional.isEmpty()) {
                Toast.makeText(this, "Por favor, selecione o profissional.", Toast.LENGTH_SHORT).show();
                return;
            }

            Agendamento novoAgendamento = new Agendamento(data, horario, local, tipoAgendamento, profissional);
            AgendamentoManager.adicionarAgendamento(novoAgendamento);

            Intent intent = new Intent(TelaAgendarActivity.this, TelaConsultaActivity.class);
            startActivity(intent);
        });

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

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.laranjaClaro, null));
        }
    }
}
