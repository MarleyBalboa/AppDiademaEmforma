package br.com.fatec.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TelaAgendarActivity extends AppCompatActivity {

    private Button btnAgendar;  // Adiciona uma variável para o botão

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_agendar); // Certifique-se de que está usando o layout correto

        // Inicializando o botão btnAgendar
        btnAgendar = findViewById(R.id.btnAgendar);

        // Configurando o clique no botão "Agendar"
        btnAgendar.setOnClickListener(v -> {
            // Ao clicar no botão, redireciona para TelaConsultaActivity
            startActivity(new Intent(TelaAgendarActivity.this, TelaConsultaActivity.class));
        });
    }
}
