package br.com.fatec.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

public class TelaCadastroActivity extends AppCompatActivity {

    private AppCompatEditText usernameInput, emailInput, passwordInput, confirmPasswordInput;
    private AppCompatButton registerButton;
    private DatabaseHelper databaseHelper;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define o layout da Activity
        setContentView(R.layout.tela_cadastro);

        // Inicializa os elementos da interface
        usernameInput = findViewById(R.id.editTextText3);
        emailInput = findViewById(R.id.editTextTextPassword);
        passwordInput = findViewById(R.id.editTextTextPassword2);
        confirmPasswordInput = findViewById(R.id.editTextTextPassword3);
        registerButton = findViewById(R.id.btnRegistrar);

        // Inicializa o banco de dados
        databaseHelper = new DatabaseHelper(this);

        // Configura a ação do botão de cadastro
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString();
                String confirmPassword = confirmPasswordInput.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(TelaCadastroActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(TelaCadastroActivity.this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = databaseHelper.addUser(username, email, password);
                    if (isInserted) {
                        Toast.makeText(TelaCadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        clearFields();

                        // Voltar para a tela de login
                        Intent intent = new Intent(TelaCadastroActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Finaliza a tela de cadastro para evitar retorno
                    } else {
                        Toast.makeText(TelaCadastroActivity.this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private void clearFields() {
                usernameInput.setText("");
                emailInput.setText("");
                passwordInput.setText("");
                confirmPasswordInput.setText("");
            }

            private void setupKeyboardAdjustment() {
                final View rootView = findViewById(android.R.id.content);
                rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
                    int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                    boolean isKeyboardVisible = heightDiff > dpToPx(200); // Ajuste conforme necessário
                    if (isKeyboardVisible) {
                        scrollView.smoothScrollTo(0, scrollView.getBottom());
                    }
                });
            }

            private int dpToPx(int dp) {
                float density = getResources().getDisplayMetrics().density;
                return Math.round(dp * density);
            }
        });
    }
}
