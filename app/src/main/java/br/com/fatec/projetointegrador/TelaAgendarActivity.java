package br.com.fatec.projetointegrador;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import br.com.fatec.projetointegrador.Adapter.AgendamentoManager;
import br.com.fatec.projetointegrador.Retrofit.Api.AgendamentoApi;
import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.Agendamento;
import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.AgendamentoRequestDTO;
import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.AgendamentoResponseDTO;
import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.StatusAgendamento;
import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.TipoAgendamento;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        btnAgendar   = findViewById(R.id.btnAgendar);
        btnConsulta  = findViewById(R.id.btnConsulta);
        EditText dataAula       = findViewById(R.id.data_aula);
        EditText horarioAula    = findViewById(R.id.horario_aula);
        Spinner spinnerLocal    = findViewById(R.id.spinner_local);
        Spinner spinnerTipoAgen = findViewById(R.id.spinner_tipoAgen);
        Spinner spinnerProf     = findViewById(R.id.spinner_profissional);

        String[] locaisSalvos = {
                "Sala A - Prédio 1", "Laboratório 3", "Auditório", "Sala 204", "Espaço Maker"
        };
        spinnerLocal.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, locaisSalvos
        ));

        String[] tiposAgendamento = {
                "CONSULTA", "AVALIACAO", "TREINO"
        };
        spinnerTipoAgen.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, tiposAgendamento
        ));

        Map<String, String[]> profPorTipo = new HashMap<>();
        profPorTipo.put("CONSULTA",   new String[]{"Prof. Ana","Prof. Carlos","Prof. Júlia"});
        profPorTipo.put("AVALIACAO",  new String[]{"Prof. Bruno","Prof. Marina"});
        profPorTipo.put("TREINO",     new String[]{"Prof. Roberto","Prof. Helena"});

        Map<String, Long> nomesParaIds = new HashMap<>();
        nomesParaIds.put("Prof. Ana", 2L);
        nomesParaIds.put("Prof. Carlos", 3L);
        nomesParaIds.put("Prof. Júlia", 4L);
        nomesParaIds.put("Prof. Bruno", 5L);
        nomesParaIds.put("Prof. Marina", 6L);
        nomesParaIds.put("Prof. Roberto", 7L);
        nomesParaIds.put("Prof. Helena", 8L);

        ArrayAdapter<String> profAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, new ArrayList<>()
        );
        profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProf.setAdapter(profAdapter);

        spinnerTipoAgen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                profAdapter.clear();
                String key = spinnerTipoAgen.getSelectedItem().toString();
                String[] profs = profPorTipo.get(key);
                if (profs != null) profAdapter.addAll(profs);
                profAdapter.notifyDataSetChanged();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        dataAula.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(
                    this,
                    (DatePicker dp, int y, int m, int d) ->
                            dataAula.setText(String.format("%04d-%02d-%02d", y, m+1, d)),
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        horarioAula.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(
                    this,
                    (TimePicker tp, int h, int m) ->
                            horarioAula.setText(String.format("%02d:%02d", h, m)),
                    c.get(Calendar.HOUR_OF_DAY),
                    c.get(Calendar.MINUTE),
                    true
            ).show();
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // altere se estiver rodando em outro host
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AgendamentoApi api = retrofit.create(AgendamentoApi.class);

        btnAgendar.setOnClickListener(v -> {
            String data  = dataAula.getText().toString();
            String hora  = horarioAula.getText().toString();
            String desc  = "Consulta inicial";
            TipoAgendamento tipo = TipoAgendamento.valueOf(
                    spinnerTipoAgen.getSelectedItem().toString()
            );
            StatusAgendamento status = StatusAgendamento.AGUARDANDO_CONFIRMACAO;
            Long clienteId = 1L;
            String nomeProf = spinnerProf.getSelectedItem().toString();
            Long profId = nomesParaIds.getOrDefault(nomeProf, 2L);

            AgendamentoRequestDTO dto = new AgendamentoRequestDTO(data, hora, desc, tipo.name(), status.name(), clienteId, profId);
            dto.setData(data);
            dto.setHora(hora);
            dto.setDescricao(desc);
            dto.setTipo(tipo.name());
            dto.setStatus(status.name());
            dto.setUsuarioClienteId(clienteId);
            dto.setProfissionalResponsavelId(profId);

            api.criarAgendamento(dto).enqueue(new Callback<AgendamentoResponseDTO>() {
                @Override
                public void onResponse(Call<AgendamentoResponseDTO> call, Response<AgendamentoResponseDTO> r) {
                    if (r.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),"Agendado!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TelaAgendarActivity.this, TelaConsultaActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Erro: "+r.code(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override public void onFailure(Call<AgendamentoResponseDTO> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            "Falha: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnConsulta.setOnClickListener(v -> {
            Intent i = new Intent(this, TelaConsultaActivity.class);
            i.putExtra("data", dataAula.getText().toString());
            i.putExtra("horario", horarioAula.getText().toString());
            i.putExtra("local", spinnerLocal.getSelectedItem().toString());
            i.putExtra("tipoAgendamento", spinnerTipoAgen.getSelectedItem().toString());
            i.putExtra("profissional", spinnerProf.getSelectedItem().toString());
            startActivity(i);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
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

