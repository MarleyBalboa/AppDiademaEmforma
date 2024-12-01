package br.com.fatec.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Home extends AppCompatActivity {

    CardView cardAgendar;
    CardView cardAcademias;
    CardView cardParques;
    CardView cardPerfil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cardAgendar = findViewById(R.id.cardAgendar);
        cardAcademias = findViewById(R.id.cardAcademias);
        cardParques = findViewById(R.id.cardParques);
        cardPerfil = findViewById(R.id.cardPerfil);


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

