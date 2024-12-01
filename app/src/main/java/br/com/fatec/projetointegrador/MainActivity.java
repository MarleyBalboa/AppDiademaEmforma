package br.com.fatec.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

public class MainActivity extends AppCompatActivity {

    private AppCompatEditText editTextText3, editTextTextPassword;
    private AppCompatButton btnEntrar;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializando os elementos da interface
        editTextText3 = findViewById(R.id.editTextText3);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        btnEntrar = findViewById(R.id.btnEntrar);

        // Inicializando o helper do banco de dados
        databaseHelper = new DatabaseHelper(this);

        // Ação do botão de login
        btnEntrar.setOnClickListener(view -> {

            String usuarioInput = editTextText3.getText().toString().trim();
            String senhaInput = editTextTextPassword.getText().toString();

            if (usuarioInput.isEmpty() || senhaInput.isEmpty()) {
                Toast.makeText(MainActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                // Verificando no banco de dados
                boolean isValidUser = databaseHelper.checkUser(usuarioInput, senhaInput);
                if (isValidUser) {
                    // Usuário autenticado com sucesso
                    Intent intent = new Intent(MainActivity.this, Home.class);  // Alterar para a Activity desejada
                    startActivity(intent);
                    finish();  // Finaliza a tela de login
                } else {
                    // Usuário ou senha incorretos
                    Toast.makeText(MainActivity.this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Ação para ir para a tela de cadastro
        TextView btn = findViewById(R.id.btnCadastrese);
        btn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TelaCadastroActivity.class)));
    }
}
