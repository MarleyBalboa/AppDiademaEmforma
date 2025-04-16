package br.com.fatec.projetointegrador.CadastroLogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.util.Log;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.gson.Gson;

import br.com.fatec.projetointegrador.Configuration.RetrofitClient;
import br.com.fatec.projetointegrador.Home;
import br.com.fatec.projetointegrador.R;
import br.com.fatec.projetointegrador.Retrofit.Api.UsuarioApi;
import br.com.fatec.projetointegrador.Retrofit.Model.LoginRequest;
import br.com.fatec.projetointegrador.Retrofit.Model.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaLoginActivity extends AppCompatActivity {

    private AppCompatEditText emailInput, passwordInput;
    private AppCompatButton loginButton;
    private TextView btnGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();

        loginButton.setOnClickListener(view -> loginUser());
        btnGoToRegister.setOnClickListener(view -> {
            startActivity(new Intent(this, TelaCadastroActivity.class));
            finish();
        });
    }

    private void initializeComponents() {
        emailInput = findViewById(R.id.editTextUsername);
        passwordInput = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.btnEntrar);
        btnGoToRegister = findViewById(R.id.btnCadastrese);
    }

    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();

        if (!validateInputs(email, password)) {
            return;
        }

        LoginRequest loginRequest = new LoginRequest(email, password);
        UsuarioApi usuarioApi = new RetrofitClient().getRetrofit().create(UsuarioApi.class);

        usuarioApi.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    LoginResponse loginResponse = response.body();
                    Long id = loginResponse.getId();

                    // Salva o ID do usuário logado no SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("USER_ID", loginResponse.getId());
                    editor.putString("USER_NOME", loginResponse.getNome());
                    editor.putString("USER_DATA_NASCIMENTO", loginResponse.getDataNascimento());
                    editor.putString("USER_TELEFONE", loginResponse.getTelefone());
                    editor.apply();

                    startActivity(new Intent(TelaLoginActivity.this, Home.class)
                            .putExtra("NOME_USUARIO", loginResponse.getNome()));
                    finish();
                } else {
                    Toast.makeText(TelaLoginActivity.this, "Email ou senha inválidos!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(TelaLoginActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("TelaLoginActivity", "Erro: " + t.getMessage());
            }
        });

        Log.d("TelaLoginActivity", "Enviando login: " + new Gson().toJson(loginRequest));
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "E-mail inválido!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
