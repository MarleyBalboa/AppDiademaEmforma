package br.com.fatec.projetointegrador;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.fatec.projetointegrador.Configuration.RetrofitClient;
import br.com.fatec.projetointegrador.Configuration.SessionManager;
import br.com.fatec.projetointegrador.Retrofit.Api.AgendamentoApi;
import br.com.fatec.projetointegrador.Retrofit.Api.LocalApi;
import br.com.fatec.projetointegrador.Retrofit.Api.UsuarioApi;
import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.AgendamentoRequestDTO;
import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.AgendamentoResponseDTO;
import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.StatusAgendamento;
import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.TipoAgendamento;
import br.com.fatec.projetointegrador.Retrofit.Model.Local.LocalDTO;
import br.com.fatec.projetointegrador.Retrofit.Model.Usuario.ProfissionalDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.widget.Spinner;

public class TelaAgendarActivity extends AppCompatActivity {

    private SessionManager session;
    private AppCompatEditText descricaoInput, dataInput, horaInput;
    private Spinner spinnerLocal, spinnerTipo, spinnerProfissional;
    private AppCompatButton btnAgendar;

    private List<LocalDTO> locais = new ArrayList<>();
    private List<ProfissionalDTO> profissionais = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_agendar);

        session = new SessionManager(this);
        initializeComponents();
        setupSpinners();

        dataInput.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this,
                    (view, year, month, day) -> {
                        String d = String.format("%04d-%02d-%02d", year, month + 1, day);
                        dataInput.setText(d);
                    },
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        horaInput.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this,
                    (view, hour, minute) -> {
                        String h = String.format("%02d:%02d", hour, minute);
                        horaInput.setText(h);
                    },
                    c.get(Calendar.HOUR_OF_DAY),
                    c.get(Calendar.MINUTE),
                    true
            ).show();
        });

        btnAgendar.setOnClickListener(view -> criarAgendamento());
    }

    private void initializeComponents() {
        descricaoInput = findViewById(R.id.spinner_descricao);
        dataInput = findViewById(R.id.data_aula);
        horaInput = findViewById(R.id.horario_aula);
        spinnerLocal = findViewById(R.id.spinner_local);
        spinnerTipo = findViewById(R.id.spinner_tipoAgen);
        spinnerProfissional = findViewById(R.id.spinner_profissional);
        btnAgendar = findViewById(R.id.btnAgendar);
    }

    private void setupSpinners() {
        // TipoAgendamento (estático)
        spinnerTipo.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                TipoAgendamento.values()
        ));

        // Carregar locais do backend
        LocalApi localApi = new RetrofitClient().getRetrofit().create(LocalApi.class);
        localApi.buscarTodosLocais().enqueue(new Callback<List<LocalDTO>>() {
            @Override
            public void onResponse(Call<List<LocalDTO>> call, Response<List<LocalDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    locais = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            TelaAgendarActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            getNomesLocais(locais)
                    );
                    spinnerLocal.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<LocalDTO>> call, Throwable t) {
                Toast.makeText(TelaAgendarActivity.this, "Erro ao carregar locais", Toast.LENGTH_SHORT).show();
            }
        });

        // Carregar profissionais do backend
        UsuarioApi usuarioApi = new RetrofitClient().getRetrofit().create(UsuarioApi.class);
        usuarioApi.buscarTodosUsuariosProfissionais().enqueue(new Callback<List<ProfissionalDTO>>() {
            @Override
            public void onResponse(Call<List<ProfissionalDTO>> call, Response<List<ProfissionalDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    profissionais = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            TelaAgendarActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            getNomesProfissionais(profissionais)
                    );
                    spinnerProfissional.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ProfissionalDTO>> call, Throwable t) {
                Toast.makeText(TelaAgendarActivity.this, "Erro ao carregar profissionais", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void btnConsulta(android.view.View view) {
        startActivity(new android.content.Intent(this, TelaConsultaActivity.class));
    }

    private void criarAgendamento() {
        String dataAula = dataInput.getText().toString().trim();
        String horaAula = horaInput.getText().toString().trim();
        String descricao = descricaoInput.getText().toString().trim();

        Long localId = locais.get(spinnerLocal.getSelectedItemPosition()).getId();
        Long profissionalId = profissionais.get(spinnerProfissional.getSelectedItemPosition()).getId();
        long usuarioClienteId = session.getUserId();
        if (usuarioClienteId == -1L) {
            Toast.makeText(this, "Usuário não está logado.", Toast.LENGTH_SHORT).show();
            return;
        }

        AgendamentoRequestDTO agendamento = new AgendamentoRequestDTO(
                dataAula,
                horaAula,
                descricao,
                ((TipoAgendamento) spinnerTipo.getSelectedItem()).name(),
                StatusAgendamento.AGUARDANDO_CONFIRMACAO.name(),
                usuarioClienteId,
                profissionalId,
                localId
        );

        AgendamentoApi api = new RetrofitClient()
                .getRetrofit()
                .create(AgendamentoApi.class);

        api.criarAgendamento(agendamento).enqueue(new Callback<AgendamentoResponseDTO>() {
            @Override
            public void onResponse(Call<AgendamentoResponseDTO> call, Response<AgendamentoResponseDTO> r) {
                if (r.isSuccessful()) {
                    Toast.makeText(TelaAgendarActivity.this, "Agendamento criado!", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(TelaAgendarActivity.this, "Erro ao criar agendamento", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AgendamentoResponseDTO> call, Throwable t) {
                Toast.makeText(TelaAgendarActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void clearFields() {
        descricaoInput.setText("");
        dataInput.setText("");
        horaInput.setText("");
        spinnerLocal.setSelection(0);
        spinnerTipo.setSelection(0);
        spinnerProfissional.setSelection(0);
    }

    private List<String> getNomesLocais(List<LocalDTO> locais) {
        List<String> nomes = new ArrayList<>();
        for (LocalDTO local : locais) {
            nomes.add(local.getNome());
        }
        return nomes;
    }

    private List<String> getNomesProfissionais(List<ProfissionalDTO> lista) {
        List<String> nomes = new ArrayList<>();
        for (ProfissionalDTO p : lista) {
            if (p.getUsuario() != null) {
                nomes.add(p.getUsuario());
            }
            /*
            if (p != null && p.getNome() != null) {
                nomes.add(p.getNome());
            } */
            else {
                nomes.add("Profissional desconhecido");
            }
        }
        return nomes;
    }

}
