package br.com.fatec.projetointegrador;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {

    CardView cardHome;
    CardView cardPesquisar;
    CardView cardAgendar;
    CardView cardFavorito;
    CardView cardMensagem;
    CardView cardPerfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cardHome = findViewById(R.id.cardHome);
        cardPesquisar = findViewById(R.id.cardPesquisar);
        cardAgendar = findViewById(R.id.cardAgendar);
        cardFavorito = findViewById(R.id.cardFavorito);
        cardMensagem = findViewById(R.id.cardMensagem);
        cardPerfil = findViewById(R.id.cardPerfil);

        cardHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Home foi clicado");
            }
        });
        cardPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Pesquisar clicado");
            }
        });
        cardAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Agendar foi clicado");
            }
        });
        cardFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Favorito foi clicado");
            }
        });
        cardMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Mensagem foi clicado");
            }
        });
        cardPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Perfil foi clicado");
            }
        });
    };
    private void showToast(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}

