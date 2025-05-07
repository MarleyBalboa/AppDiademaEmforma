package br.com.fatec.projetointegrador;

import android.content.Intent;
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
import br.com.fatec.projetointegrador.Configuration.SessionManager;
import br.com.fatec.projetointegrador.Retrofit.Api.UsuarioApi;
import br.com.fatec.projetointegrador.Retrofit.Model.Usuario;
import br.com.fatec.projetointegrador.Configuration.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    private CardView cardSaudeMental, cardNutricao, cardAgendar, cardAcademias, cardParques, cardPerfil;
    private TextView nomeTextView, nascimentoTextView, telefoneTextView;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        session = new SessionManager(this);

        cardAgendar = findViewById(R.id.cardAgendar);
        cardAcademias = findViewById(R.id.cardAcademias);
        cardParques = findViewById(R.id.cardParques);
        cardPerfil = findViewById(R.id.cardPerfil);
        cardNutricao = findViewById(R.id.cardNutricao);
        cardSaudeMental = findViewById(R.id.cardSaudeMental);

        nomeTextView = findViewById(R.id.nomeTextView);
        nascimentoTextView = findViewById(R.id.nascimentoTextView);
        telefoneTextView = findViewById(R.id.telefoneTextView);

        long userId = session.getUserId();
        String nomeUsuario = session.getUserName();
        // --- verificar se o usuário está logado ---
        if (userId != -1 && nomeUsuario != null) {
            nomeTextView.setText("Olá, " + nomeUsuario);

            // Busca os dados atualizados do backend
            UsuarioApi api = new RetrofitClient()
                    .getRetrofit()
                    .create(UsuarioApi.class);

            api.buscarUsuarioPorId(userId)
                    .enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call,
                                               Response<Usuario> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Usuario u = response.body();
                                nomeTextView.setText("Olá, " + u.getUsuario());
                                nascimentoTextView.setText(
                                        "Nascimento: " + u.getDataCriacao().substring(0, 10));
                                telefoneTextView.setText("Telefone: " + u.getTelefone());
                            }
                        }
                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            showToast("Erro ao buscar dados do usuário");
                        }
                    });
        } else {
            nomeTextView.setText("Olá, Usuário");
        }

        // --- listeners dos cards ---
        cardNutricao.setOnClickListener(v ->
                startActivity(new Intent(this, TelaBuscaNutricionista.class)));
        cardSaudeMental.setOnClickListener(v ->
                startActivity(new Intent(this, TelaBuscaSaudeMental.class)));
        cardAgendar.setOnClickListener(v ->
                startActivity(new Intent(this, TelaAgendarActivity.class)));
        cardAcademias.setOnClickListener(v ->
                startActivity(new Intent(this, TelaBuscaAcademias.class)));
        cardParques.setOnClickListener(v ->
                startActivity(new Intent(this, TelaBuscaParques.class)));
        cardPerfil.setOnClickListener(v ->
                startActivity(new Intent(this, TelaPerfil.class)));
    }

    private void showToast(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}
