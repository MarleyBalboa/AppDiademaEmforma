package br.com.fatec.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTextEmailAddress2;
    private EditText editTextTextPassword;
    private Button btnEntrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editTextTextEmailAddress2 = findViewById(R.id.editTextTextEmailAddress2);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(view -> {

            String usuarioInput = editTextTextEmailAddress2.getText().toString();
            String senhaInput = editTextTextPassword.getText().toString();

            if (usuarioInput.equals("admin") && senhaInput.equals("admin")) {
                Intent intent = new Intent(this, Home.class);
                startActivity(intent);
            } else {
                Toast.makeText(
                        MainActivity.this, "Usuário ou senha incorretos",
                                Toast.LENGTH_SHORT
                ).show();
            }
        });

        TextView btn = findViewById(R.id.btnCadastrese);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TelaCadastroActivity.class));
            }
        });
    }
}
