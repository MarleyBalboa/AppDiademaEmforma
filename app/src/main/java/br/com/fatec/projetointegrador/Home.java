package br.com.fatec.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import br.com.fatec.projetointegrador.Backend.Api.UsuarioApi;
import br.com.fatec.projetointegrador.Buscar.TelaBuscaAcademias;
import br.com.fatec.projetointegrador.Buscar.TelaBuscaNutricionista;
import br.com.fatec.projetointegrador.Buscar.TelaBuscaParques;
import br.com.fatec.projetointegrador.Buscar.TelaBuscaSaudeMental;
import br.com.fatec.projetointegrador.Buscar.TelaPerfil;
import br.com.fatec.projetointegrador.CadastroLogin.TelaAgendarActivity;
import br.com.fatec.projetointegrador.Configuration.RetrofitClient;

public class Home extends AppCompatActivity {

    CardView cardSaudeMental;
    CardView cardNutricao;
    CardView cardAgendar;
    CardView cardAcademias;
    CardView cardParques;
    CardView cardPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ApiService para fazer as requisições do Backend
        // UsuarioApi = Faz requisições do Backend para buscar e enviar dados para a classe de Usuario
        UsuarioApi usuarioApi = RetrofitClient.getUsuarioApi().create(UsuarioApi.class);


        cardAgendar = findViewById(R.id.cardAgendar);
        cardAcademias = findViewById(R.id.cardAcademias);
        cardParques = findViewById(R.id.cardParques);
        cardPerfil = findViewById(R.id.cardPerfil);
        cardNutricao = findViewById(R.id.cardNutricao);
        cardSaudeMental = findViewById(R.id.cardSaudeMental);


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

