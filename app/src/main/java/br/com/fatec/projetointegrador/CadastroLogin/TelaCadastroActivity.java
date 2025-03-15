package br.com.fatec.projetointegrador.CadastroLogin;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import br.com.fatec.projetointegrador.Configuration.RetrofitClient;
import br.com.fatec.projetointegrador.R;
import br.com.fatec.projetointegrador.Retrofit.Api.UsuarioApi;
import br.com.fatec.projetointegrador.Retrofit.Model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaCadastroActivity extends AppCompatActivity {

    private AppCompatEditText usernameInput, emailInput, passwordInput, confirmPasswordInput;
    private AppCompatButton registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);
        initializeComponents();
        registerButton.setOnClickListener(view -> registerUser());
    }

    private void initializeComponents() {
        usernameInput = findViewById(R.id.editTextText3);
        emailInput = findViewById(R.id.editTextTextEmail);
        passwordInput = findViewById(R.id.editTextTextPassword);
        confirmPasswordInput = findViewById(R.id.editTextTextConfirmPassword);
        registerButton = findViewById(R.id.btnRegistrar);
    }

    private void registerUser() {
        String username = usernameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (!validateInputs(username, email, password, confirmPassword)) {
            return;
        }

        Usuario usuario = new Usuario(username, email, password);
        UsuarioApi usuarioApi = new RetrofitClient().getRetrofit().create(UsuarioApi.class);
        usuarioApi.criarUsuario(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    createSuccessDialog(username).show();
                    clearFields();
                } else {
                    Toast.makeText(TelaCadastroActivity.this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(TelaCadastroActivity.this, "Falha na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TelaCadastroActivity", "Erro: " + t.getMessage());
            }
        });
    }

    private boolean validateInputs(String username, String email, String password, String confirmPassword) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "E-mail inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private AlertDialog createSuccessDialog(String username) {
        return new AlertDialog.Builder(this)
                .setMessage("Cadastro realizado com sucesso!\nSeja bem-vindo(a) " + username + "!")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    startActivity(new Intent(this, TelaLoginActivity.class));
                    finish();
                })
                .create();
    }

    private void clearFields() {
        usernameInput.setText("");
        emailInput.setText("");
        passwordInput.setText("");
        confirmPasswordInput.setText("");
    }
}
