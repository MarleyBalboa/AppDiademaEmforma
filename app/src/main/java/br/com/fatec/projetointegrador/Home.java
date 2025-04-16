package br.com.fatec.projetointegrador;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import br.com.fatec.projetointegrador.Buscar.TelaBuscaAcademias;
import br.com.fatec.projetointegrador.Buscar.TelaBuscaNutricionista;
import br.com.fatec.projetointegrador.Buscar.TelaBuscaParques;
import br.com.fatec.projetointegrador.Buscar.TelaBuscaSaudeMental;
import br.com.fatec.projetointegrador.Buscar.TelaPerfil;
import br.com.fatec.projetointegrador.CadastroLogin.TelaAgendarActivity;
import br.com.fatec.projetointegrador.Retrofit.Api.UsuarioApi;
import br.com.fatec.projetointegrador.Retrofit.Model.Usuario;
import br.com.fatec.projetointegrador.Configuration.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    CardView cardSaudeMental;
    CardView cardNutricao;
    CardView cardAgendar;
    CardView cardAcademias;
    CardView cardParques;
    CardView cardPerfil;
    TextView nomeTextView, nascimentoTextView, telefoneTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ApiService para fazer as requisições do Backend
        // UsuarioApi = Faz requisições do Backend para buscar e enviar dados para a classe de Usuario
        cardAgendar = findViewById(R.id.cardAgendar);
        cardAcademias = findViewById(R.id.cardAcademias);
        cardParques = findViewById(R.id.cardParques);
        cardPerfil = findViewById(R.id.cardPerfil);
        cardNutricao = findViewById(R.id.cardNutricao);
        cardSaudeMental = findViewById(R.id.cardSaudeMental);

        // Inicialização dos TextViews
        nomeTextView = findViewById(R.id.nomeTextView);
        nascimentoTextView = findViewById(R.id.nascimentoTextView);
        telefoneTextView = findViewById(R.id.telefoneTextView);

        // Busca os textos de informação do usuário
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        long userId = sharedPreferences.getLong("USER_ID", -1);
        String nomeUsuario = sharedPreferences.getString("USER_NOME", "Usuário");
        String dataNascimento = sharedPreferences.getString("USER_DATA_NASCIMENTO", "Data de Nascimento");
        String telefone = sharedPreferences.getString("USER_TELEFONE", "Telefone");

        nomeTextView.setText("Olá, " + nomeUsuario);
        nascimentoTextView.setText("Nascimento: " + dataNascimento);
        telefoneTextView.setText("Telefone: " + telefone);

        if (userId != -1) {
            UsuarioApi usuarioApi = new RetrofitClient().getRetrofit().create(UsuarioApi.class);

            usuarioApi.buscarUsuarioPorId(userId).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()) {
                        Usuario usuario = response.body();
                        nomeTextView.setText("Olá, " + usuario.getUsuario());

                        nascimentoTextView.setText("Nascimento: " + usuario.getDataCriacao().substring(0, 10)); // ou outro campo se tiver data nascimento
                        telefoneTextView.setText("Telefone: " + usuario.getTelefone());
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    showToast("Erro ao buscar dados do usuário");
                }
            });
        }


        cardNutricao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, TelaBuscaNutricionista.class));
            }
        });
        cardSaudeMental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, TelaBuscaSaudeMental.class));
            }
        });
        cardAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, TelaAgendarActivity.class));
            }
        });
        cardAcademias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, TelaBuscaAcademias.class));
            }
        });
        cardParques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, TelaBuscaParques.class));
            }
        });
        cardPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, TelaPerfil.class));
            }
        });
    };
    private void showToast(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}

