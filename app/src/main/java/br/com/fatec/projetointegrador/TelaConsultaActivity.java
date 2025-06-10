package br.com.fatec.projetointegrador;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.fatec.projetointegrador.Adapter.AgendamentoAdapter;
import br.com.fatec.projetointegrador.Configuration.RetrofitClient;
import br.com.fatec.projetointegrador.Retrofit.Api.AgendamentoApi;
import br.com.fatec.projetointegrador.Retrofit.Model.Agendamento.Agendamento;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaConsultaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AgendamentoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_consulta);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        recyclerView = findViewById(R.id.recyclerViewAgendamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        carregarAgendamentos();
    }

    private void carregarAgendamentos() {
        AgendamentoApi api = new RetrofitClient().getRetrofit().create(AgendamentoApi.class);
        api.buscarTodosAgendamentos().enqueue(new Callback<List<Agendamento>>() {
            @Override
            public void onResponse(Call<List<Agendamento>> call, Response<List<Agendamento>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Agendamento> agendamentos = response.body();
                    adapter = new AgendamentoAdapter(agendamentos);
                    recyclerView.setAdapter(adapter);

                    adapter.setOnAgendamentoLongClickListener(ag -> {
                        AlertDialog dialog = new AlertDialog.Builder(TelaConsultaActivity.this)
                                .setTitle("Opções do Agendamento")
                                .setMessage("Deseja editar ou excluir este agendamento?")
                                .setPositiveButton("Editar", (d, w) -> {
                                    Toast.makeText(TelaConsultaActivity.this,
                                            "Edição ainda não implementada",
                                            Toast.LENGTH_SHORT).show();
                                })
                                .setNegativeButton("Excluir", (d, w) -> deletarAgendamento(ag.getId()))
                                .setNeutralButton("Cancelar", null)
                                .create();
                        dialog.show();

                        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                .setTextColor(Color.BLACK);
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                                .setTextColor(Color.BLACK);
                        dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
                                .setTextColor(Color.BLACK);

                        TextView messageView = dialog.findViewById(android.R.id.message);
                        if (messageView != null) {
                            messageView.setTextColor(Color.BLACK);
                        }

                        int titleId = dialog.getContext()
                                .getResources()
                                .getIdentifier("alertTitle", "id", "android");
                        TextView titleView = dialog.findViewById(titleId);
                        if (titleView != null) {
                            titleView.setTextColor(Color.BLACK);
                        }
                    });

                } else {
                    Toast.makeText(TelaConsultaActivity.this, "Erro ao carregar agendamentos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Agendamento>> call, Throwable t) {
                Toast.makeText(TelaConsultaActivity.this, "Falha na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deletarAgendamento(Long id) {
        AgendamentoApi api = new RetrofitClient().getRetrofit().create(AgendamentoApi.class);
        api.deletarAgendamento(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> resp) {
                if (resp.isSuccessful()) {
                    Toast.makeText(TelaConsultaActivity.this,
                            "Agendamento deletado com sucesso!",
                            Toast.LENGTH_SHORT).show();
                    //carregarAgendamentos();
                    adapter.removeById(id);
                } else {
                    Toast.makeText(TelaConsultaActivity.this,
                            "Erro ao deletar agendamento",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TelaConsultaActivity.this,
                        "Falha na requisição: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
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
