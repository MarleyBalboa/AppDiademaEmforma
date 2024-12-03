package br.com.fatec.projetointegrador.CadastroLogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.fatec.projetointegrador.R;

public class TelaInicioBemVindo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Defina o layout da Activity primeiro
        setContentView(R.layout.activity_tela_inicio_bem_vindo);

        // Agora, as views podem ser encontradas
        TextView btn = findViewById(R.id.btnCadastrese);
        AppCompatButton btnLogin = findViewById(R.id.btn_login);

        // Defina os listeners
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaInicioBemVindo.this, TelaCadastroActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaInicioBemVindo.this, TelaLoginActivity.class));
            }
        });

        // Ativando EdgeToEdge
        EdgeToEdge.enable(this);

        // Adicionando tratamento de insets para ajustar a UI com as barras de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
